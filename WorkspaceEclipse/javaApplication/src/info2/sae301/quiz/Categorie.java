package info2.sae301.quiz;

import java.util.ArrayList; 

public class Categorie {
    
    private String intitule;
    private ArrayList<Question> listeQuestions = new ArrayList<Question> (); 
    
    

    /**
	 * @param intitule
	 * @param questions
	 */
	public Categorie(String intitule, ArrayList<Question> questions) {
		this.intitule = intitule;
		this.listeQuestions = questions;
	}


	public String getIntitule() {
        return intitule;
    }

 
    public boolean ajouterQuestion(final Question aAjouter) {
    	return listeQuestions.add(aAjouter);
    	
    }


    public boolean estVide() {
        return listeQuestions.isEmpty();
    }


    public int nbQuestions() {
        return listeQuestions.size();
    }


    public ArrayList<Question> getListeQuestions() {
        return listeQuestions;
    }


    /** @param intitule the intitule to set */
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}


	public boolean supprimerQuestion(final Question aSupprimer) {
		return listeQuestions.remove(aSupprimer);
    }


    public boolean supprimerToutesQuestions() {
    	while (!listeQuestions.isEmpty()) {
    		listeQuestions.remove(listeQuestions.get(0));
    	}
    	return listeQuestions.isEmpty();
    }

}
