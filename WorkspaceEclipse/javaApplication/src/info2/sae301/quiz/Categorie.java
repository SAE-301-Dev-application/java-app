/**
 * Categorie.java									31 oct. 2023 
 * IUT de Rodez, no copyright ni "copyleft"
 */

package info2.sae301.quiz;

import java.util.ArrayList; 

/**
 * Objet Catégorie composé d'un intitulé et d'une liste contenant les 
 * références des questions liées à la catégorie
 * @author FABRE Florian
 */
public class Categorie {
    
	/** Message si erreur sur les tailles de champ*/
	final String ERR_TAILLE_ARG = "La taille max %s est de %d caractères";
	
	/** l'intitulé de la catégorie (max 20 char)*/
    private String intitule;
    
    /** contient toutes les questions de la catégorie */
    private ArrayList<Question> listeQuestions;; 
    
    

    /**
     * Nouvelle catégorie de questions identifiée par son intitulé
	 * @param intitule
	 * @param questions
	 */
	public Categorie(String intitule) {
		this.intitule = intitule;
	}
    
    
    /**
     *  Nouvelle catégorie de questions identifiée par son intitulé
	 * @param intitule
	 * @param questions
	 */
	public Categorie(String intitule, ArrayList<Question> questions) {
		if (intitule.length() > 20) {
			throw new IllegalArgumentException(String.format(ERR_TAILLE_ARG
					,"d'un intitulé de catégorie",20));
		} else {
			this.intitule = intitule;
		}
		this.listeQuestions = questions;
	}


	/**
	 * Nouvelle catégorie de questions identifiée par son intitulé 
	 * sans liste de questions prédéfinie
	 * @param intitule
	 */
	public Categorie(String intitule) {
		if (intitule.length() > 20) {
			throw new IllegalArgumentException(String.format(ERR_TAILLE_ARG
					,"d'un intitulé de catégorie",20));
		} else {
			this.intitule = intitule;
		}
		this.listeQuestions = new ArrayList<Question>();
	}


	/** @return l'intitulé de la catégorie */
	public String getIntitule() {
        return intitule;
    }

	
    /**
     * Ajoute à listeQuestions la question en paramètre
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
		if (intitule.length() < 20) {
			this.intitule = intitule;			
		}
	}


	/**
	 * Supprime de listeQuestions la question en paramètre
	 * @param aSupprimer la question à supprimer de la catégorie
	 * @return true si enlèvement réussi, false sinon
	 */
	public boolean supprimerQuestion(Question aSupprimer) {
		return listeQuestions.remove(aSupprimer);
    }


    /**
     * Supprime toutes les questions de listeQuestions
     * @return true si questions enlevées, false sinon
     */
    public boolean supprimerToutesQuestions() {
    	while (!listeQuestions.isEmpty()) {
    		listeQuestions.remove(listeQuestions.get(0));
    	}
    	return listeQuestions.isEmpty();
    }
}
