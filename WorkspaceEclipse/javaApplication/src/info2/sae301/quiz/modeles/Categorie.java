/**
 * Categorie.java								      	            10 nov. 2023 
 * IUT de Rodez, no copyright ni "copyleft"
 */

package info2.sae301.quiz.modeles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects; 

/**
 * Objet Catégorie composé d'un intitulé et d'une liste contenant les 
 * références des questions liées à la catégorie
 *
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class Categorie implements Serializable {
    
	/** Numéro de sérialisation : clé de hachage */
	private static final long serialVersionUID = 3793388654168200022L;

	/** Message si erreur sur les tailles de champ */
	private static final String ERR_TAILLE_ARG_MAX 
	= "La taille max d'un intitulé de catégorie est de 20 caractères";
	
	private static final String ERR_TAILLE_ARG_MIN 
	= "L'intitulé de la catégorie ne peut pas être vide.";
	
	/** l'intitulé de la catégorie (max 20 char) */
    private String intitule;
    
    /** contient toutes les questions de la catégorie */
    private ArrayList<Question> listeQuestions;
    

    /**
     * Nouvelle catégorie de questions identifiée par son intitulé
     * 
	 * @param intitule
	 * @param questions
	 */
	public Categorie(String intitule, ArrayList<Question> questions) {
		if (intitule.length() > 20) {
			throw new IllegalArgumentException(String.format(ERR_TAILLE_ARG_MAX));
		}
		if (intitule.isBlank() || intitule.isEmpty()) {
			throw new IllegalArgumentException(String.format(ERR_TAILLE_ARG_MIN));
		}
		
		this.intitule = intitule;
		this.listeQuestions = questions;
	}


	/**
	 * Nouvelle catégorie de questions identifiée par son intitulé 
	 * sans liste de questions prédéfinie
	 * 
	 * @param intitule
	 */
	public Categorie(String intitule) {
		if (intitule == null || intitule.isBlank()) {
			throw new IllegalArgumentException(String.format(ERR_TAILLE_ARG_MIN));
		}
		
		if (intitule.length() > 20) {
			throw new IllegalArgumentException(String.format(ERR_TAILLE_ARG_MAX));
		}
		
		this.intitule = intitule;
		this.listeQuestions = new ArrayList<Question>();
	}


	/** @return l'intitulé de la catégorie */
	public String getIntitule() {
        return intitule;
    }

	
    /**
     * Ajoute à listeQuestions la question en paramètre
     * 
     * @param aAjouter
     * @return true si la question est ajoutée, false sinon
     */
    public boolean ajouterQuestion(Question aAjouter) {
    	if (this.listeQuestions.contains(aAjouter)) {
    		return false;
    	} else if (aAjouter.getCategorie().getIntitule().equals(this.intitule)) {
    		return listeQuestions.add(aAjouter);
    	} 
    	return false;
    }


    /** @return true si listeQuestions est vide, false sinon */
    public boolean estVide() {
        return listeQuestions.isEmpty();
    }


    /**
     * @return le nombre de questions présentes dans listeQuestions
     */
    public int nbQuestions() {
        return listeQuestions.size();
    }


    /**
     * @return la liste des questions de la catégorie
     */
    public ArrayList<Question> getListeQuestions() {
        return listeQuestions;
    }


    /** @param intitule l'intitule à changer */
	public void setIntitule(String intitule) {
		if (intitule == null
		    || intitule.isBlank()) {
			
			throw new IllegalArgumentException(ERR_TAILLE_ARG_MIN);
			
		}
		
		if (intitule.length() > 20) {
			throw new IllegalArgumentException(ERR_TAILLE_ARG_MAX);
		}
		
		if (intitule.length() > 0
		    && !intitule.isBlank() 
		    && intitule.length() <= 20) {
			
			this.intitule = intitule;	
			
		}
	}


	/**
	 * Supprime de listeQuestions la question en paramètre
	 * 
	 * @param aSupprimer la question à supprimer de la catégorie
	 * @return true si enlèvement réussi, false sinon
	 */
	public boolean supprimerQuestion(Question aSupprimer) {
		return listeQuestions.remove(aSupprimer);
    }


    /**
     * Supprime toutes les questions de listeQuestions
     * 
     * @return true si questions enlevées, false sinon
     */
    public boolean supprimerToutesQuestions() {
    	while (!listeQuestions.isEmpty()) {
    		listeQuestions.remove(listeQuestions.get(0));
    	}
    	return listeQuestions.isEmpty();
    }
    

    /**
	 * Crée un hashCode se basant sur les attributs de l'objet auquel cette 
 	 * méthode est appliquée, ici une instance de Categorie.
 	 * Permet une comparaison précise et complète la méthode equals() car si les
 	 * hashCode générés pour les instances comparées sont les mêmes et que 
 	 * la méthode equals renvoie true, alors ces instances sont égales.
 	 * 
 	 * Il n'est pas obligé de l'implémenter dans ce cas car nous n'utilisons pas
 	 * de HashSet ou de HashMap, cependant il est préférable de l'implémenter
 	 * pour une maintenance future du code plus aisée et pour le respect
 	 * des conventions générales de Java.
 	 * 
 	 * @return un hashCode basé sur les attributs de l'instance passée
 	 */
	@Override
	public int hashCode() {
		return Objects.hash(intitule, listeQuestions);
	}
	

	/**
	 * Compare 2 catégories en profondeur sur la totalité de leurs attributs
	 * 
	 * @param aComparer Categorie a comparer
	 * @return true si les categories sont égales, false sinon
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categorie other = (Categorie) obj;
		return Objects.equals(intitule, other.intitule);
	}
	
	
	/** non javadoc - @see {@link java.util.Objects#toString()}. */
	@Override
	public String toString() {
		return "" + intitule;
	}
}