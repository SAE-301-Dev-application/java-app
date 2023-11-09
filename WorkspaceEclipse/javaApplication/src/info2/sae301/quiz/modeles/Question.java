/**
 * Question.java									31 oct. 2023 
 * IUT de Rodez, no copyright ni "copyleft"
 */

package info2.sae301.quiz.modeles;

/**
 * Objet Question composé d'un intitulé, de réponses, de feedback et de difficulté
 * directement lié à une catégorie
 * @author FABRE Florian
 */
public class Question {
	
	/** Message si erreur sur les tailles de champ */
	final static String TAILLE_INVALIDE
	= "La taille %s doit être comprise entre %d et %d.";
	
	/** Message si erreur sur la taille du tableau de réponse fausses */
	final static String NB_REPONSES_FAUSSES_INVALIDE
	= "Le nombre de réponses fausses rédigées doit être compris entre 1 et 4.";
	
	/** Message si erreur sur la difficulté */
	final static String DIFFICULTE_INVALIDE
	= "La difficulté doit être comprise entre 1 et 3 : 1 - Facile, 2 - Moyenne,"
	  + " 3 - Difficile";
	
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
	 */
	public Question(String intitule, String reponseJuste,
			        String[] reponsesFausses, int difficulte, String feedback,
			        Categorie categorie) {

		verifierAttributs(intitule, reponseJuste, reponsesFausses, difficulte,
				          feedback);
		this.intitule = intitule;
		this.reponseJuste = reponseJuste;
		this.reponsesFausses = reponsesFausses;
		this.difficulte = difficulte;
		this.categorie = categorie;
		this.feedback = feedback;
	}
	
	/**
	 * Vérification puis initialisation des attributs pour les deux constructeurs.
	 * 
	 * @param intitule L'intitulé de la question.
	 * @param reponseJuste La réponse juste.
	 * @param reponsesFausses Les réponses fausses.
	 * @param difficulte La difficulté (1, 2 ou 3).
	 * @throws IllegalArgumentException si les attributs ne respectent pas
	 *                                  les tailles demandées.
	 */
	public static void verifierAttributs(String intitule, String reponseJuste,
							             String[] reponsesFausses, int difficulte)
	            throws IllegalArgumentException {
		assurerTaille(intitule, "d'un intitulé", 1, 300);
		assurerTaille(reponseJuste, "d'une réponse juste", 1, 200);
		
		if (reponsesFausses.length == 0 || reponsesFausses.length > 4) {
			throw new IllegalArgumentException(NB_REPONSES_FAUSSES_INVALIDE);
		}
		assurerTaille(reponsesFausses[0], "d'une réponse fausse", 1, 200);
		for (int i = 1; i < reponsesFausses.length; i++) {
			if (!reponsesFausses[i].isBlank()) {
				assurerTaille(reponsesFausses[i], "d'une réponse fausse", 1, 200);				
			}
		}
		if (difficulte < 1 || difficulte > 3) {
			throw new IllegalArgumentException(DIFFICULTE_INVALIDE);
		}
	}
	
	/**
	 * Vérification puis initialisation des attributs pour les deux constructeurs.
	 * 
	 * @param intitule L'intitulé de la question.
	 * @param reponseJuste La réponse juste.
	 * @param reponsesFausses Les réponses fausses.
	 * @param difficulte La difficulté (1, 2 ou 3).
	 * @param feedback Le feedback.
	 * @throws IllegalArgumentException si les attributs ne respectent pas
	 *                                  les tailles demandées.
	 */
	public static void verifierAttributs(String intitule, String reponseJuste,
							             String[] reponsesFausses, int difficulte,
			                             String feedback)
	              throws IllegalArgumentException {
		verifierAttributs(intitule, reponseJuste, reponsesFausses, difficulte);
		assurerTaille(feedback, "d'un feedback", 1, 500);
	}
	
	/**
	 * Vérification que la taille d'un élément soit supérieure à tailleMin
	 * et inférieure à tailleMax.
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
		if (element.length() < tailleMin || element.length() > tailleMax) {
			throw new IllegalArgumentException(String.format(TAILLE_INVALIDE,
											                 titre, tailleMin,
											                 tailleMax));
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
		if (intitule.length() <= 300) {
			this.intitule = intitule;
		}
	}


	/** @param reponseJuste the reponseJuste à changer */
	public void setReponseJuste(String reponseJuste) {
		if (reponseJuste.length() <= 200) {
			this.reponseJuste = reponseJuste;			
		}
	}


	/** @param reponsesFausses the reponsesFausses à changer */
	public void setReponsesFausses(String[] reponsesFausses) {
		boolean estPossible = true;
		
		if (reponsesFausses.length == 0 || reponsesFausses.length > 4) {
			estPossible = false;
		} else {
			for (String reponse : reponsesFausses) {
				if (reponse.length() > 200) {
					estPossible = false;
				}
			}
		}
		
		if (estPossible) {
			this.reponsesFausses = reponsesFausses;
		}
	}


	/** @param difficulte the difficulte à changer */
	public void setDifficulte(int difficulte) {
		if ( 0 < difficulte && difficulte < 4) {
			this.difficulte = difficulte;
		}
	}


	/** @param feedback the feedback à changer */
	public void setFeedback(String feedback) {
		if (feedback.length() <= 500) {
			this.feedback = feedback;			
		}
	}


	/** @param categorie the categorie à changer */
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
}
