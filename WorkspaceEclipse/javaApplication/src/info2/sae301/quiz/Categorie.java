package info2.sae301.quiz;

import java.util.ArrayList; 

/**
 * TODO comment types
 * @author flori
 */
public class Categorie {
    
	/** l'intitulé de la catégorie */
    private String intitule;
    
    /** contient toutes les questions de la catégorie */
    private ArrayList<Question> listeQuestions = new ArrayList<Question> (); 
    
    

    /**
     *  Nouvelle catégorie de questions identifiée par son intitulé
	 * @param intitule
	 * @param questions
	 */
	public Categorie(String intitule, ArrayList<Question> questions) {
		this.intitule = intitule;
		this.listeQuestions = questions;
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
    	return listeQuestions.add(aAjouter);
    	
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
		this.intitule = intitule;
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
