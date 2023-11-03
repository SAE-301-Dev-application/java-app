package info2.sae301.quiz;

public class Question {
	
	/** l'intitulé de la question */
    private String intitule;
    
    /** la réponse juste */
    private String reponseJuste;
    
    /** les réponses fausses */
    private String[] reponsesFausses = new String[4];
    
    /** la difficulté de la question (1 : facile, 2 : moyen, 3 : difficile */
    private int difficulte;
    
    /** 
     * le feedback à afficher lors de la correction 
     * de la réponse à cette question
     */
    private String feedback;
    
    /** la catégorie dans laquelle est cette question */
    private Categorie categorie;

    
    /**
     * Nouvelle question identifiée par son intitulé ayant une
	 * réponse juste, des réponses fausses, un niveau de difficulté 
	 * et une catégorie associée
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
		if (reponsesFausses.length > 4) {
			throw new IllegalArgumentException("Le nombre max de réponses fausses est 4");
		} else if (reponsesFausses.length < 1) {
			throw new IllegalArgumentException("Le nombre min de réponses fausses est 1");
		} else {
			this.reponsesFausses = reponsesFausses;
		}
		this.difficulte = difficulte;
		this.categorie = categorie;
		this.feedback = null;
	}
    
    
	/**
	 * Nouvelle question identifiée par son intitulé ayant
	 * une réponse juste, des réponses fausses, un niveau de difficulté,
     * un feedback et une catégorie associée
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
		this.intitule = intitule;
	}


	/** @param reponseJuste the reponseJuste à changer */
	public void setReponseJuste(String reponseJuste) {
		this.reponseJuste = reponseJuste;
	}


	/** @param reponsesFausses the reponsesFausses à changer */
	public void setReponsesFausses(String[] reponsesFausses) {
		this.reponsesFausses = reponsesFausses;
	}


	/** @param difficulte the difficulte à changer */
	public void setDifficulte(int difficulte) {
		this.difficulte = difficulte;
	}


	/** @param feedback the feedback à changer */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}


	/** @param categorie the categorie à changer */
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
}
