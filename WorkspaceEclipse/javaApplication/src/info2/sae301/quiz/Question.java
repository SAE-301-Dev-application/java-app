

package info2.sae301.quiz;

import java.util.ArrayList;

public class Question {
	

    private String intitule;
    private String reponseJuste;
    private String[] reponsesFausses = new String[4];
    private int difficulte;
    private String feedback;
    private Categorie categorie;

    
    /**
	 * Question qui ne contiendra pas de feedback
	 * @param intitule
	 * @param reponseJuste
	 * @param reponsesFausses
	 * @param difficulte
	 * @param categorie
	 */
	public Question(String intitule, String reponseJuste,
			String[] reponsesFausses, int difficulte, Categorie categorie) {
		this.intitule = intitule;
		this.reponseJuste = reponseJuste;
		this.reponsesFausses = reponsesFausses;
		this.difficulte = difficulte;
		this.categorie = categorie;
	}
    
    
	/**
	 * Question contenant cette fois-ci un feedback
	 * @param intitule
	 * @param reponseJuste
	 * @param reponsesFausses
	 * @param difficulte
	 * @param feedback
	 * @param categorie
	 */
	public Question(String intitule, String reponseJuste,
			String[] reponsesFausses, int difficulte, String feedback,
			Categorie categorie) {
		super();
		this.intitule = intitule;
		this.reponseJuste = reponseJuste;
		this.reponsesFausses = reponsesFausses;
		this.difficulte = difficulte;
		this.feedback = feedback;
		this.categorie = categorie;
	}


	/** @return the intitule */
	public String getIntitule() {
		return intitule;
	}


	/** @return the reponseJuste */
	public String getReponseJuste() {
		return reponseJuste;
	}


	/** @return the reponsesFausses */
	public String[] getReponsesFausses() {
		return reponsesFausses;
	}


	/** @return the difficulte */
	public int getDifficulte() {
		return difficulte;
	}


	/** @return the feedback */
	public String getFeedback() {
		return feedback;
	}


	/** @return the categorie */
	public Categorie getCategorie() {
		return categorie;
	}


	/** @param intitule the intitule to set */
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}


	/** @param reponseJuste the reponseJuste to set */
	public void setReponseJuste(String reponseJuste) {
		this.reponseJuste = reponseJuste;
	}


	/** @param reponsesFausses the reponsesFausses to set */
	public void setReponsesFausses(String[] reponsesFausses) {
		this.reponsesFausses = reponsesFausses;
	}


	/** @param difficulte the difficulte to set */
	public void setDifficulte(int difficulte) {
		this.difficulte = difficulte;
	}


	/** @param feedback the feedback to set */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}


	/** @param categorie the categorie to set */
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
}
