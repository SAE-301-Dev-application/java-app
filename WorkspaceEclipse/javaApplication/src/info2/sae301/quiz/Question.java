/**
 * Question.java									31 oct. 2023 
 * IUT de Rodez, no copyright ni "copyleft"
 */

package info2.sae301.quiz;


/**
 * Objet Question composé d'un intitulé, de réponses, de feedback et de difficulté
 * directement lié à une catégorie
 * @author FABRE Florian
 */
public class Question {
	
	/** Message si erreur sur les tailles de champ*/
	final String ERR_TAILLE_ARG = "La taille max %s est de %d caractères";
	
	/** Message si erreur sur la taille du tableau de réponse fausses*/
	final String ERR_TAILLE_TAB_REP_FAUSSES = "Le nombre de réponses fausses doit"
			+ " être compris entre 1 et 4";
	
	/** Message si erreur sur la difficulté*/
	final String ERR_DIFFICULTE = "La difficulté doit être soit 1 , 2 ou 3";
	
	/** l'intitulé de la question (max 300 caractères) */
    private String intitule;
    
    /** la réponse juste (200 caractères)*/
    private String reponseJuste;
    
    /** les réponses fausses (200 car / repFausse) */
    private String[] reponsesFausses = new String[4];
    
    /** la difficulté de la question (1 : facile, 2 : moyen, 3 : difficile) */
    private int difficulte;
    
    /** 
     * le feedback à afficher lors de la correction 
     * de la réponse à cette question (500 caractères) 
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
		
		if (intitule.length() > 300) {
			throw new IllegalArgumentException(String.format(ERR_TAILLE_ARG
												,"d'un intitulé de question",300));
		} else {
			this.intitule = intitule;			
		}
		
		if (reponseJuste.length() > 200) {
			throw new IllegalArgumentException(String.format(ERR_TAILLE_ARG
												,"d'une réponse",200));
		} else {
			this.reponseJuste = reponseJuste;
		}
		
		if (reponsesFausses.length == 0 || reponsesFausses.length > 4) {
			throw new IllegalArgumentException(ERR_TAILLE_TAB_REP_FAUSSES);
		} else {
			for (String reponse : reponsesFausses) {
				if (reponse.length() > 200) {
					throw new IllegalArgumentException(String.format(ERR_TAILLE_ARG
							,"d'une réponse",200));
				}
			}
			this.reponsesFausses = reponsesFausses;
		}
		
		if (difficulte < 1 || difficulte > 3) {
			throw new IllegalArgumentException(ERR_DIFFICULTE);
		} else {
			this.difficulte = difficulte;
		}
		
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
		if (intitule.length() > 300) {
			throw new IllegalArgumentException(String.format(ERR_TAILLE_ARG
												,"d'un intitulé",300));
		} else {
			this.intitule = intitule;			
		}
		
		if (reponseJuste.length() > 200) {
			throw new IllegalArgumentException(String.format(ERR_TAILLE_ARG
												,"d'une réponse",200));
		} else {
			this.reponseJuste = reponseJuste;
		}
		
		if (reponsesFausses.length == 0 || reponsesFausses.length > 4) {
			throw new IllegalArgumentException(ERR_TAILLE_TAB_REP_FAUSSES);
		} else {
			for (String reponse : reponsesFausses) {
				if (reponse.length() > 200) {
					throw new IllegalArgumentException(String.format(ERR_TAILLE_ARG
							,"d'une réponse",200));
				}
			}
			this.reponsesFausses = reponsesFausses;
		}
		
		if (difficulte < 1 || difficulte > 3) {
			throw new IllegalArgumentException(ERR_DIFFICULTE);
		} else {
			this.difficulte = difficulte;
		}
		
		if (feedback.length() > 500) {
			throw new IllegalArgumentException(String.format(ERR_TAILLE_ARG
					,"d'un feedback",500));
		} else {
			this.feedback = feedback;
		}
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
		if (intitule.length() < 300) {
			this.intitule = intitule;
		}
	}


	/** @param reponseJuste the reponseJuste à changer */
	public void setReponseJuste(String reponseJuste) {
		if (reponseJuste.length() < 200) {
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
		if (feedback.length() < 500) {
			this.feedback = feedback;			
		}
	}


	/** @param categorie the categorie à changer */
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
}
