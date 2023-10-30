

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

	public String getIntitule() {
		return intitule;
	}

	public String getReponseJuste() {
		return reponseJuste;
	}

	public String[] getReponsesFausses() {
		return reponsesFausses;
	}

	public int getDifficulte() {
		return difficulte;
	}

	public String getFeedback() {
		return feedback;
	}

	public Categorie getCategorie() {
		return categorie;
	}
}
