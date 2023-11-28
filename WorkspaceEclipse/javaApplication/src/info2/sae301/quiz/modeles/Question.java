/**
 * Question.java									31 oct. 2023 
 * IUT de Rodez, no copyright ni "copyleft"
 */

package info2.sae301.quiz.modeles;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects; 

/**
 * Objet Question composé d'un intitulé, de réponses, de feedback et 
 * de difficulté
 * directement lié à une catégorie
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class Question implements Serializable {
	
	/** Message si erreur sur les tailles de champ */
	private static final String TAILLE_INVALIDE
	= "La taille %s doit être comprise entre %d et %d.";
	
	/** Message si erreur sur la taille du tableau de réponse fausses */
	private static final String NB_REPONSES_FAUSSES_INVALIDE
	= "Le nombre de réponses fausses rédigées doit être compris entre 1 et 4.";
	
	/** Message si erreur sur la difficulté */
	private static final String DIFFICULTE_INVALIDE
	= "La difficulté doit être comprise entre 1 et 3 : 1 - Facile, 2 - Moyenne,"
	  + " 3 - Difficile";
	
	/** 
	 * Message si erreur l'utilisateur n'a pas rentré 
	 * de réponses fausses 
	 */
	private static final String REPONSE_FAUSSE_1_VIDE
	= "Vous devez au moins renseigner une réponse fausse, dans le premier "
	  + "champ obligatoirement.";
	
	/** 
	 * Message si erreur car l'utilisateur n'a pas rempli tous
	 * les champs 
	 */
	private static final String VALEUR_VIDE
	= "Les champs requis doivent être remplis.";
	
	/**
	 * Message si erreur car l'utilisateur a rempli un champ avec
	 * des espaces uniquement 
	 */
	private static final String VALEUR_INVALIDE
	= "Un champ rempli d'espaces est invalide.";

	/**
	 * Message si erreur car l'utilisateur a rempli un champ des 
	 * reponses avec des réponses non uniques 
	 */
    private static final String REPONSES_NON_UNIQUES
    = "Chaque réponse (juste et fausse) doit être unique.";
    
    private static final String ERREUR_CARACTERE_INTERDIT
    = "Le caractère %s n'est pas supporté par l'application.";
	
    
	/** L'intitulé de la question (max 300 caractères) */
    private String intitule;
    
    /** La réponse juste (max 200 caractères)*/
    private String reponseJuste;
    
    /** Les réponses fausses (max 200 caractères) */
    private String[] reponsesFausses = new String[4];
    
    /** La difficulté de la question (1 : facile, 2 : moyen, 3 : difficile) */
    private int difficulte;
    
    /** 
     * Le feedback à afficher lors de la correction 
     * de la réponse à cette question (500 caractères) 
     */
    private String feedback;
    
    /** La catégorie dans laquelle est cette question */
    private Categorie categorie;

	/** Numéro de sérialisation : clé de hachage */
	private static final long serialVersionUID = 1304132270193188547L;

	/**
	 * Nouvelle question identifiée par son intitulé ayant une
	 * réponse juste, des réponses fausses, un niveau de difficulté 
	 * et une catégorie associée.
	 * 
	 * @param intitule L'intitulé de la question.
	 * @param reponseJuste La réponse juste.
	 * @param reponsesFausses Les réponses fausses.
	 * @param difficulte La difficulté (1, 2 ou 3).
	 * @param categorie La catégorie contenant la question.
	 * @throws IllegalArgumentException si la question existe déjà.
	 */
	public Question(String intitule, String reponseJuste,
			        String[] reponsesFausses, int difficulte, Categorie categorie)
	                throws IllegalArgumentException {
		
		verifierAttributs(intitule, reponseJuste, reponsesFausses, difficulte);
		this.intitule = intitule;
		this.reponseJuste = reponseJuste;
		this.reponsesFausses = reponsesFausses;
		this.difficulte = difficulte;
		this.categorie = categorie;
		this.feedback = null;
	}


	/**
	 * Nouvelle question identifiée par son intitulé ayant
	 * une réponse juste, des réponses fausses, un niveau de difficulté,
	 * un feedback et une catégorie associée.
	 * 
	 * @param intitule L'intitulé de la question.
	 * @param reponseJuste La réponse juste.
	 * @param reponsesFausses Les réponses fausses.
	 * @param difficulte La difficulté (1, 2 ou 3).
	 * @param feedback Le feedback à afficher pour corriger la réponse.
	 * @param categorie La catégorie contenant la question.
	 * @throws  
	 * @throws IllegalArgumentException 
	 */
	public Question(String intitule, String reponseJuste,
			        String[] reponsesFausses, int difficulte, String feedback,
			        Categorie categorie) throws IllegalArgumentException {

		verifierAttributs(intitule, reponseJuste, reponsesFausses, difficulte,
				          feedback);
		this.intitule = intitule;
		this.reponseJuste = reponseJuste;
		this.reponsesFausses = reponsesFausses;
		this.difficulte = difficulte;
		this.categorie = categorie;
		assurerCaracteres(feedback);
		this.feedback = feedback;
	}
	

	/**
	 * Vérification puis initialisation des attributs pour les deux 
	 * constructeurs.
	 * 
	 * @param intitule L'intitulé de la question.
	 * @param reponseJuste La réponse juste.
	 * @param reponsesFausses Les réponses fausses.
	 * @param difficulte La difficulté (1, 2 ou 3).
	 * @throws IllegalArgumentException si les attributs ne 
	 * respectent pas les tailles demandées.
	 */
	public static void verifierAttributs(String intitule, String reponseJuste,
							             String[] reponsesFausses, int difficulte)
	throws IllegalArgumentException {
		
		assurerTaille(intitule, "d'un intitulé", 1, 300);
		assurerCaracteres(intitule);
		assurerTaille(reponseJuste, "d'une réponse juste", 1, 200);
		assurerCaracteres(reponseJuste);
		// Vérification de la taille des réponses fausses
		assurerValiditeReponsesFausses(reponsesFausses);
		// Vérification de l'unicité des réponses
		assurerReponsesUniques(reponseJuste, reponsesFausses);
		
		if (difficulte < 1 || difficulte > 3) {
			throw new IllegalArgumentException(DIFFICULTE_INVALIDE);
		}	
	}
	
	
	/**
	 * Vérification de la validité des caractères
	 * 
	 * @param aVerfier
	 * @throws IllegalArgumentException 
	 */
	private static void assurerCaracteres(String aVerifier) 
	throws IllegalArgumentException {
		
		String messageErreurCaractereInterdit;
		
		for (int i = 0; i < aVerifier.length(); i++) {
			if (Dictionnaire.getDictionnaireReversed().get(aVerifier.charAt(i)) == null) {
				messageErreurCaractereInterdit
				= String.format(ERREUR_CARACTERE_INTERDIT, 
								aVerifier.charAt(i));
				
				System.out.println(messageErreurCaractereInterdit);
				
				throw new IllegalArgumentException(
					messageErreurCaractereInterdit
				);
			}
		}	
		
	}


	/**
	 * Vérification puis initialisation des attributs pour 
	 * les deux constructeurs.
	 * 
	 * @param intitule L'intitulé de la question.
	 * @param reponseJuste La réponse juste.
	 * @param reponsesFausses Les réponses fausses.
	 * @param difficulte La difficulté (1, 2 ou 3).
	 * @param feedback Le feedback.
	 * @throws IllegalArgumentException si les attributs ne respectent 
	 * pas les tailles demandées.
	 * @throws UnsupportedEncodingException 
	 */
	public static void verifierAttributs(String intitule, String reponseJuste,
							             String[] reponsesFausses, int difficulte,
			                             String feedback)
    throws IllegalArgumentException {
		
		verifierAttributs(intitule, reponseJuste, reponsesFausses, difficulte);
		assurerTaille(feedback, "d'un feedback", 1, 500);
		
	}
	

	/**
	 * Vérification que la taille d'un élément soit supérieure à 
	 * tailleMin et inférieure à tailleMax.
	 * 
	 * @param element Element duquel vérifier la taille.
	 * @param titre Titre de l'élément vérifié.
	 * @param tailleMin Taille minimale de l'élément.
	 * @param tailleMax Taille maximale de l'élément.
	 * @throws IllegalArgumentException si la taille n'est pas bonne.
	 */
	private static void assurerTaille(String element, String titre,
			                          int tailleMin, int tailleMax)
    throws IllegalArgumentException {
		
		if (element == null || element.isEmpty()) {
			throw new IllegalArgumentException(VALEUR_VIDE);
		} else if (element.isBlank()) {
			throw new IllegalArgumentException(VALEUR_INVALIDE);
		}
		
		if (element.length() < tailleMin || element.length() > tailleMax) {
			throw new IllegalArgumentException(String.format(TAILLE_INVALIDE,
															 titre, tailleMin,
															 tailleMax));
		}
	}
	
	
	/**
	 * Ajoute la réponse juste à un tableau contenant également les 
	 * réponses fausses et vérifie l'unicité des réponses du tableau.
	 * 
	 * @param reponses La réponse juste.
	 * @param reponsesFausses Les réponses fausses.
	 * @throws IllegalArgumentException si une des réponses est égale 
	 * à une autre.
	 */
	private static void assurerReponsesUniques(String reponseJuste,
			                                   String[] reponsesFausses)
	throws IllegalArgumentException {
		// Tableau contenant la réponse juste suivie des réponses fausses
        String[] toutesLesReponses = new String[reponsesFausses.length + 1];
        toutesLesReponses[0] = reponseJuste;
        
        System.arraycopy(reponsesFausses, 0, toutesLesReponses, 1, reponsesFausses.length);
        
        assurerReponsesUniques(toutesLesReponses);
	}
	
	
	/**
	 * Vérifie si les réponses en paramètre sont toutes différentes.
	 * 
	 * @param reponses Les réponses à vérifier.
	 * @throws IllegalArgumentException si une des réponses est égale
	 * à une autre.
	 */
	public static void assurerReponsesUniques(String[] reponses)
	throws IllegalArgumentException {
        boolean resultat = true;
        
        for (int i = 0; i < reponses.length && resultat; i++) {
            for (int j = i + 1; j < reponses.length && resultat; j++) {
                if (!reponses[i].isEmpty() && reponses[i].equals(reponses[j])) {
                    resultat = false;
                }
            }
        }
        if (!resultat) {
            throw new IllegalArgumentException(REPONSES_NON_UNIQUES);
        }
	}
	

	/**
	 * Vérifie que les réponses fausses en paramètre soient valides.
	 * La première doit être non nulle et avoir une taille < 200.
	 * Les autres peuvent être nulles.
	 * 
	 * @param reponsesFausses Les réponses fausses à assurer.
	 * @throws IllegalArgumentException si la taille de chaque 
	 * réponse fausse est invalide.
	 */
	public static void assurerValiditeReponsesFausses(String[] reponsesFausses)
	throws IllegalArgumentException {
		
		if (reponsesFausses.length == 0 || reponsesFausses.length > 4) {
			throw new IllegalArgumentException(NB_REPONSES_FAUSSES_INVALIDE);
		}
		
		for (int i = 0; i < reponsesFausses.length; i++) {
			assurerCaracteres(reponsesFausses[i]);
			if (i == 0) {
				if (reponsesFausses[i] == null || reponsesFausses[i].isEmpty()) {
					throw new IllegalArgumentException(REPONSE_FAUSSE_1_VIDE);
				}
				assurerTaille(reponsesFausses[i], "de la 1ère réponse fausse", 1, 200);
			} else if (reponsesFausses[i] != null && !reponsesFausses[i].isEmpty()) {
				assurerTaille(reponsesFausses[i],
						      "de la " + (i + 1) + "ème réponse fausse", 1, 200);
			}
		}
	}
	

	/** @return l'intitule de la question */
	public String getIntitule() {
		return intitule;
	}


	/** @return la reponseJuste */
	public String getReponseJuste() {
		return reponseJuste;
	}


	/** @return les reponsesFausses */
	public String[] getReponsesFausses() {
		return reponsesFausses;
	}


	/** @return la difficulte */
	public int getDifficulte() {
		return difficulte;
	}


	/** @return le feedback s'il existe */
	public String getFeedback() {
		return feedback;
	}


	/** @return la categorie */
	public Categorie getCategorie() {
		return categorie;
	}


	/** @param intitule the intitule à changer */
	public void setIntitule(String intitule) {
		assurerTaille(intitule, "d'un intitulé", 1, 300);
		this.intitule = intitule;
	}


	/** @param reponseJuste the reponseJuste à changer */
	public void setReponseJuste(String reponseJuste) {
		assurerTaille(reponseJuste, "d'une réponse juste", 1, 200);
		this.reponseJuste = reponseJuste;			
	}


	/** @param reponsesFausses the reponsesFausses à changer */
	public void setReponsesFausses(String[] reponsesFausses) {
        assurerValiditeReponsesFausses(reponsesFausses);
		this.reponsesFausses = reponsesFausses;
	}
	

	/** @param difficulte the difficulte à changer */
	public void setDifficulte(int difficulte) {
		if (difficulte < 1 || difficulte > 3) {
			throw new IllegalArgumentException(DIFFICULTE_INVALIDE);
		}
		this.difficulte = difficulte;
	}
	

	/** @param feedback the feedback à changer */
	public void setFeedback(String feedback) {
		if (feedback != null && !feedback.isEmpty()) {
			assurerTaille(feedback, "d'un feedback", 1, 500);			
		}
		this.feedback = feedback;
	}

	/** @param categorie the categorie à changer */
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	

	/**
	 * Teste si les réponses fausses sont les mêmes dans les deux 
	 * listes.
	 * 
	 * @param reponsesFausses1 Les réponses fausses à comparer.
	 * @param reponsesFausses2 Les secondes réponses fausses à 
	 * comparer.
	 * @return true si les liste contiennent les mêmes réponses 
	 * fausses, false sinon.
	 */
	public boolean memesReponsesFausses(Question aComparerRepFausses) {
		boolean resultatFinal = true;
		if (this.reponsesFausses.length != aComparerRepFausses.reponsesFausses.length) {
			return false;
		}
		for (int i = 0; i < this.reponsesFausses.length && resultatFinal; i++) {
			boolean reponseEgaleTrouvee = false;
			for (int j = 0; j < aComparerRepFausses.reponsesFausses.length && !reponseEgaleTrouvee; j++) {
				reponseEgaleTrouvee
				= this.reponsesFausses[i].equals(aComparerRepFausses.reponsesFausses[j]);
			}
			if (!reponseEgaleTrouvee) {
				resultatFinal = reponseEgaleTrouvee;
			}
		}
		return resultatFinal;
	}
	
	
	/**
	 * Mélange de façon pseudo-aléatoire les réponses juste et
	 * fausses à l'aide de la méthode shuffle 
	 * de l'interface Collections
	 * 
	 * @return toutesLesReponses l'ArrayList contenant 
	 * les réponses mélangées
	 */
	public ArrayList<String> melangerReponses() {
		// Tableau contenant la réponse juste suivie des réponses fausses
		ArrayList<String> toutesLesReponses
			= concatenationReponses();
        
        Collections.shuffle(toutesLesReponses);
		return toutesLesReponses; 
	}
	
	
	/**
	 * Crée une ArrayList contenant les réponses de la question
	 * dans l'ordre suivant :
	 * <ul>
	 *     <li>réponse juste</li>
	 *     <li>réponse fausse</li>
	 *     <li>éventuelle réponse fausse</li>
	 *     <li>éventuelle réponse fausse</li>
	 *     <li>éventuelle réponse fausse</li>
	 * </ul>
	 * @return toutesLesReponses l'ArrayList contenant 
	 * les réponses mélangées
	 */
	public ArrayList<String> concatenationReponses() {
		// Tableau contenant la réponse juste suivie des réponses fausses
		ArrayList<String> toutesLesReponses = new ArrayList<>(reponsesFausses.length + 1);
        toutesLesReponses.add(reponseJuste);
        
        for (int i = 0; i < getReponsesFausses().length; i++) {
			toutesLesReponses.add(getReponsesFausses()[i]);
		}
		return toutesLesReponses; 
	}
	
	
	/**
	 * Méthode comparant deux ArrayList pour déterminer 
	 * si elles contiennent les mêmes réponses
	 * Sert à tester melangerReponses()
	 * @param reponses une ArrayList de réponses
	 * @param reponsesMelangees une ArrayList de réponses
	 * @return true si les ArrayList contiennent les mêmes reponses
	 * false sinon
	 */
	public boolean memesReponses(ArrayList<String> reponses, ArrayList<String> reponsesMelangees) {
		
		boolean resultatFinal = true;
		if (reponses.size() != reponsesMelangees.size()) {
			return false;
		}
		for (int i = 0; i < reponsesMelangees.size() && resultatFinal; i++) {
			if (!reponses.contains(reponsesMelangees.get(i))) {
				resultatFinal = false;
			}
		}
		return resultatFinal;
	}
	
	
	/**
	 * Vérifie la réponse de l'utilisateur par rapport à la réponse juste
	 * de la question
	 * 
	 * @param reponseUser réponse de l'utilisateur
	 * @return Si la réponse donnée vaut celle correspondant à la 
	 * 		   question de l'instance
	 */
	public boolean verifierReponse(String reponseDonnee) {
		return this.getReponseJuste().equals(reponseDonnee);
	}
	
	
	/**
	 * Formatte les données de la question courante afin de les rassembler
	 * en une seule chaîne de caractères acceptable pour une ligne de CSV.
	 * 
	 * @return les données sous un format dit CSV.
	 */
	public String donneesToString() {
		String resultat;
		
		resultat = formatterTexte(this.getCategorie().getIntitule())
		           + formatterTexte("" + this.getDifficulte())
		           + formatterTexte(this.getIntitule())
		           + formatterTexte(this.getReponseJuste());
				
		for (int i = 0; i <= 3; i++) {
			String[] reponses = this.getReponsesFausses();
			
			if (i < reponses.length && reponses[i] != null) {
				resultat += formatterTexte(reponses[i]);
			} else {
				resultat += ";";
			}
		}
		
		if (this.getFeedback() != null && !this.getFeedback().isBlank()) {
			resultat += formatterTexte(this.getFeedback());
			resultat = resultat.substring(0, resultat.length() - 1);
		}
		
		return resultat;
	}
	
	
	/**
	 * Formatte le texte en paramètre afin de doubler les guillemets si un
	 * point virgule ou un guillemet est présent.
	 * 
	 * @param texte Le texte à vérifier.
	 * @return le texte formaté.
	 */
	public String formatterTexte(String texte) {
		final String GUILLEMET = "\"";
		
		final String POINT_VIRGULE = ";";
		
		String texteFormatte;
		
		texteFormatte = "";
		
		if (texte.contains(GUILLEMET) || texte.contains(POINT_VIRGULE)) {
			if (texte.contains(GUILLEMET)) {
				// Si un caractère est un guillemet on le double
				for (char caractere: texte.toCharArray()) {
					texteFormatte += "" + caractere
							+ (caractere == GUILLEMET.charAt(0)
							? GUILLEMET
									: "");
				}
			} else {
				texteFormatte = texte;
			}
			
			texteFormatte = GUILLEMET + texteFormatte + GUILLEMET;
		}
		
		return texteFormatte + POINT_VIRGULE;
	}
	
	
	/**
	 * Crée un hashCode se basant sur les attributs de l'objet auquel
	 * cette méthode est appliquée, ici une instance de Question.
 	 * Permet une comparaison précise et complète la méthode equals()
 	 * car si les hashCode générés pour les instances comparées sont 
 	 * les mêmes et que la méthode equals renvoie true, alors ces 
 	 * instances sont égales.
 	 * 
 	 * Il n'est pas obligé de l'implémenter dans ce cas car nous 
 	 * n'utilisons pas de HashSet ou de HashMap, cependant il est 
 	 * préférable de l'implémenter pour une maintenance future du 
 	 * code plus aisée et pour le respect
 	 * des conventions générales de Java.
 	 * 
 	 * @return un hashCode basé sur les attributs de l'instance passée
 	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(reponsesFausses);
		result = prime * result + Objects.hash(categorie, difficulte,
											feedback, intitule, reponseJuste);
		return result;
	}
	

	/**
	 * Méthode permettant de comparer 2 questions en profondeur 
	 * en se basant sur leurs critères d'identification:
	 * - Intitulé question
	 * - Intitule categorie
	 * - reponseJuste
	 * - Liste des réponses fausses
	 * @param aComparer question que l'on compare
	 * @return true si equals, false sinon
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		return Objects.equals(categorie.getIntitule(), other.categorie.getIntitule())
				&& Objects.equals(intitule, other.intitule)
				&& Objects.equals(reponseJuste, other.reponseJuste)
				&& Arrays.equals(reponsesFausses, other.reponsesFausses);
	}
	
	
	/** non javadoc - @see {@link java.util.Objects#toString()}. */
	@Override
	public String toString() {
		return "Intitule : " + intitule 
				+ "\nCategorie : " + categorie 
				+ "\nRéponse juste : " + reponseJuste
				+ "\nRéponses fausses : \n" + reponsesFausses[0] + "\n" 
						+ reponsesFausses[1] + "\n" 
						+ reponsesFausses[2] + "\n" 
						+ reponsesFausses[3]
				+ "\nDifficulté : " + difficulte;
	}
}