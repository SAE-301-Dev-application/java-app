/*
 * Jeu.java             									         6 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Gestion des catégories et des questions créées par l'utilisateur.
 * 
 * @author FAUGIERES Loïc
 * @author GUIRAUD Simon
 */
public class Jeu implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Toutes les catégories qui ont été créées sur le jeu. La 1ère catégorie
	 * est par défaut "Général".
	 */
	private ArrayList<Categorie> toutesLesCategories;
	
	/** Toutes les questions qui ont été créées sur le jeu. */
	private ArrayList<Question> toutesLesQuestions;

	/**
	 * Construction d'une session de jeu initialisant ses questions
	 * et catégories à vide.
	 */
	public Jeu() {
		this.toutesLesCategories
		= new ArrayList<>(Arrays.asList(new Categorie("Général")));
		
		for (int i = 2; i <= 30; i++) {
			creerCategorie("" + i + "ème catégorie");
		}
		
		this.toutesLesQuestions = new ArrayList<>();
		
		for (int i = 1; i <= 30; i++) {
			creerQuestion("" + i + (i != 1 ? "ème" : "ère") + " question",
					      "Réponse vraie",
					      new String[] {"Réponse fausse 1", "Réponse fausse 2",
					    		        "Réponse fausse 3", "Réponse fausse 4"},
						  2, "Feedback très court",
                          i % 2 == 0
                          ? toutesLesCategories.get(0).getIntitule()
                          : "2ème catégorie");
		}
	}
	
	/** @return La liste des catégories créées. */
	public ArrayList<Categorie> getToutesLesCategories() {
		return toutesLesCategories;
	}
	
	/** @return La liste des questions créées. */
	public ArrayList<Question> getToutesLesQuestions() {
		return toutesLesQuestions;
	}
	
	/**
	 * Accès aux questions d'une catégorie dont l'intitulé est en paramètre.
	 * 
	 * @param intituleCategorie L'intitulé de la catégorie de laquelle retourner
	 *                          les questions.
	 * @return La liste des questions de la catégorie en paramètre.
	 */
	public ArrayList<Question> questionsCategorie(String intituleCategorie) {
		if (intituleCategorie.equals("Toutes les catégories")) {
			return toutesLesQuestions;
		}
		return toutesLesCategories.get(indiceCategorie(intituleCategorie)).getListeQuestions();
	}
	
	/**
	 * @param categories L'ArrayList des intitulés des catégories à retourner.
	 * @return la liste des catégories dont l'intitulé est dans la liste en paramètre.
	 */
	public Categorie[] getCategoriesParIntitules(ArrayList<String> categories) {
		Categorie[] categoriesARetourner = new Categorie[categories.size()];
		
		int indice = 0;
		// Get puis ajout de chaque catégorie à categoriesARetourner
		for (String nomCategorie : categories) {
			categoriesARetourner[indice]
			= toutesLesCategories.get(indiceCategorie(nomCategorie));
			indice++;
		}
		return categoriesARetourner;
	}
	
	/**
	 * @param categorie Intitulé de la catégorie à retourner.
	 * @return La catégorie dont l'intitulé est dans le paramètre.
	 */
	public Categorie getCategorieParIntitule(String intituleCategorie) {
		return toutesLesCategories.get(indiceCategorie(intituleCategorie));
	}
	
	/**
	 * Réinitialise/Vide la liste des catégories. Seule la catégorie
	 * "Général" restera. 
	 */
	public void supprimerToutesCategories() {
		toutesLesCategories
		= new ArrayList<>(Arrays.asList(new Categorie("Général")));
	}
	
	/** Réinitialise/Vide la liste des questions. */
	public void supprimerToutesQuestions() {
		toutesLesQuestions = new ArrayList<>();
	}
	
	/**
	 * Crée une nouvelle catégorie et l'ajoute à la liste des catégories.
	 * 
	 * @param intitule L'intitulé de la catégorie à créer.
	 */
	public Categorie creerCategorie(String intitule) {
		if (indiceCategorie(intitule) == -1) {
			Categorie categorieCreee = new Categorie(intitule);
			toutesLesCategories.add(categorieCreee);
			return categorieCreee;
		} else {
			throw new IllegalArgumentException("Cette catégorie existe déjà.");
		}
	}
		
	/**
	 * Crée une nouvelle question et l'ajoute à la liste des questions.
	 * 
	 * @param intitule L'intitulé de la question à créer.
	 * @param reponseJuste La réponse juste.
	 * @param reponsesFausses Les réponses fausses.
	 * @param difficulte La difficulté.
	 * @param feedback Le feedback.
	 * @param categorie La catégorie contenant la question.
	 */
	public void creerQuestion(String intitule, String reponseJuste,
			                  String[] reponsesFausses, int difficulte,
			                  String feedback, String intituleCategorie) {
		
		Categorie categorie = getCategorieParIntitule(intituleCategorie);
		
		if (indiceQuestion(intitule, intituleCategorie,
						      reponseJuste, reponsesFausses) == -1) {
			
			Question questionCreee;
			if (feedback == null || feedback.isBlank()) {
				questionCreee = new Question(intitule, reponseJuste,
						                     reponsesFausses, difficulte, categorie);
			} else {
				questionCreee = new Question(intitule, reponseJuste,
						                     reponsesFausses, difficulte,
						                     feedback, categorie);
			}
			toutesLesQuestions.add(questionCreee);
			categorie.ajouterQuestion(questionCreee);
		} else {
			throw new IllegalArgumentException("Cette question existe déjà.");
		}
	}
	
	/**
	 * Supprime de la liste des catégories les catégories spécifiées dans la
	 * liste en paramètre.
	 * @param aSupprimer Liste des catégories à supprimer.
	 */
	public void supprimer(Categorie[] aSupprimer) {
		for (Categorie categorieCourante : aSupprimer) {
			int indiceCategorie = indiceCategorie(categorieCourante.getIntitule());
			if (indiceCategorie != -1
				&& !categorieCourante.getIntitule().equals("Général")) {
				toutesLesQuestions.removeAll(toutesLesCategories
						                     .get(indiceCategorie)
						                     .getListeQuestions());
				toutesLesCategories.remove(indiceCategorie);
			}
		}
	}
	
	/**
	 * Supprime de la liste des questions les questions spécifiées dans la
	 * liste en paramètre.
	 * @param aSupprimer Liste des questions à supprimer.
	 */
	public void supprimer(Question[] aSupprimer) {
		for (Question questionCourante : aSupprimer) {
			int indiceQuestion
			= indiceQuestion(questionCourante.getIntitule(),
					         questionCourante.getCategorie().getIntitule(),
							 questionCourante.getReponseJuste(),
							 questionCourante.getReponsesFausses());
			if (indiceQuestion != -1) {
				questionCourante.getCategorie().supprimerQuestion(questionCourante);
				toutesLesQuestions.remove(indiceQuestion);
			}
		}
	}
	
	/**
	 * Renomme la catégorie sélectionnée avec l'intitulé en paramètre.
	 * 
	 * @param nouveauIntitule Le nouveau intitulé de la catégorie.
	 * @throws IllegalArgumentException si la taille est invalide ou qu'une catégorie
	 *                                  du nouveau nom existe déjà.
	 */
	public void renommerCategorie(String ancienIntitule,
			                      String nouveauIntitule)
	                   throws IllegalArgumentException {
		final String CATEGORIE_INEXISTANTE
		= "La catégorie sélectionnée est inexistante en mémoire ou ne peut pas "
		  + "être renommée.";
		
		final String CATEGORIE_DEJA_EXISTANTE
		= "L'intitulé entré existe déjà pour une autre catégorie.";
		
		final String TAILLE_INVALIDE
		= "La taille d'un intitulé de catégorie doit être comprise entre 1 et 20.";
		
		if (nouveauIntitule.length() < 1 || nouveauIntitule.length() > 20) {
			throw new IllegalArgumentException(TAILLE_INVALIDE);
		}
		
		// Si une catégorie ayant le même intitulé existe.
		if (indiceCategorie(nouveauIntitule) >= 0) {
			throw new IllegalArgumentException(CATEGORIE_DEJA_EXISTANTE);
		}
		
		int indiceCategorie = indiceCategorie(ancienIntitule);
		
		if (indiceCategorie > 0) {
			toutesLesCategories.get(indiceCategorie).setIntitule(nouveauIntitule);
		} else {
			throw new IllegalArgumentException(CATEGORIE_INEXISTANTE);
		}
	}
	
	/**
	 * Edite la question sélectionnée.
     *
	 * @param indiceQuestion L'indice de la question à éditer.
	 * @param nouveauIntitule Le nouveau intitulé de la question.
	 * @param reponseJuste La réponse juste.
	 * @param reponsesFausses Les réponses fausses.
	 * @param difficulte La difficulté.
	 * @param feedback Le feedback.
	 * @param categorie La catégorie contenant la question.
	 * @throws IllegalArgumentException si la taille est invalide ou qu'une question
	 *         ayant les mêmes paramètres existe déjà dans la même catégorie.
	 */
	public void editerQuestion(int indiceQuestion, String nouveauIntitule,
			                   String reponseJuste, String[] reponsesFausses,
			                   int difficulte, String feedback,
			                   String intituleCategorie)
	            throws IllegalArgumentException {	
		
		final String CATEGORIE_INEXISTANTE
		= "La catégorie sélectionnée est inexistante en mémoire ou ne peut pas "
		  + "être renommée.";
		
		final String QUESTION_DEJA_EXISTANTE
		= "Une autre question avec le même intitulé, la même catégorie et"
		  + " les mêmes réponses existe déjà.";
		
		int indiceQuestionTrouve
		= indiceQuestion(nouveauIntitule, intituleCategorie, reponseJuste,
				         reponsesFausses);
		
		// Si une question ayant les mêmes paramètres existe.
		if (indiceQuestionTrouve >= 0 && indiceQuestionTrouve != indiceQuestion) {
			throw new IllegalArgumentException(QUESTION_DEJA_EXISTANTE);
		}
		
		if (feedback != null && !feedback.isBlank()) {
			Question.verifierAttributs(nouveauIntitule, reponseJuste,
					                   reponsesFausses, difficulte, feedback);
		} else {
			Question.verifierAttributs(nouveauIntitule, reponseJuste,
					                   reponsesFausses, difficulte);

		}
		
		int indiceCategorie = indiceCategorie(intituleCategorie);
		if (indiceCategorie < 0) {
			throw new IllegalArgumentException(CATEGORIE_INEXISTANTE);
		}
		
		Question question = toutesLesQuestions.get(indiceQuestion);
		question.setIntitule(nouveauIntitule);
		question.setReponseJuste(reponseJuste);
		question.setReponsesFausses(reponsesFausses);
		question.setDifficulte(difficulte);
		question.setFeedback(feedback);
		
		// Retrait de la liste des questions de l'ancienne catégorie
		question.getCategorie().supprimerQuestion(question);
		question.setCategorie(toutesLesCategories.get(indiceCategorie));
		// Ajout à la liste des questions de la nouvelle catégorie
		question.getCategorie().ajouterQuestion(question);
	}
	
	/**
	 * Vérification de l'existence d'une catégorie dans la liste des catégories.
	 * 
	 * @param intitule L'intitulé de la catégorie à chercher.
	 * @return L'indice dans la liste des catégories de la catégorie ayant pour
	 *         intitulé celui en paramètre ou -1 si la catégorie n'existe pas.
	 */
 	public int indiceCategorie(String intitule) {
		int resultat = -1;
		for (int i = 0;
			 i < toutesLesCategories.size()
			 && resultat == -1;
			 i++) {
			if (toutesLesCategories.get(i).getIntitule().equals(intitule)) {
				resultat = i;
			}
		}
		return resultat;
	}
 	
	/**
	 * Vérification de l'existence d'une question dans la liste des questions d'une
	 * catégorie dont l'intitulé est en paramètre.
	 * 
	 * @param intituleQuestion L'intitulé de la question à chercher.
	 * @param intituleCategorie L'intitulé de la catégorie contenant la question.
	 * @param reponseJuste La réponse juste à la question à chercher.
	 * @param reponsesFausses Les réponses fausses à la question à chercher.
	 * @return L'indice dans la liste des questions de la question ayant pour
	 *         intitulé celui en paramètre ou -1 si la question n'existe pas.
	 */
 	public int indiceQuestion(String intituleQuestion, String intituleCategorie,
 			                  String reponseJuste, String[] reponsesFausses) {
 		
 		int resultat = -1;
		
		int indiceCategorie = indiceCategorie(intituleCategorie);
		Categorie categorieQuestion = toutesLesCategories.get(indiceCategorie);
		
		ArrayList<Question> questionsCategorie = categorieQuestion.getListeQuestions();
		
		for (int i = 0;
		     i < questionsCategorie.size()
			 && resultat == -1;
			 i++) {
			Question questionCourante = questionsCategorie.get(i);
			
			if (questionCourante.getIntitule().equals(intituleQuestion)
				&& questionCourante.getReponseJuste().equals(reponseJuste)
				&& memesReponsesFausses(questionCourante.getReponsesFausses(), reponsesFausses)) {
				
				resultat = getToutesLesQuestions().indexOf(questionCourante); 
			}
		}
		return resultat;
	}
 	
 	/**
 	 * Teste si les réponses fausses sont les mêmes dans les deux listes.
 	 * 
 	 * @param reponsesFausses1 Les réponses fausses à comparer.
 	 * @param reponsesFausses2 Les secondes réponses fausses à comparer.
 	 * @return true si les liste contiennent les mêmes réponses fausses, false sinon.
 	 */
 	public boolean memesReponsesFausses(String[] reponsesFausses1, String[] reponsesFausses2) {
 		boolean resultatFinal = true;
 		if (reponsesFausses1.length != reponsesFausses2.length) {
 			return false;
 		}
 		for (int i = 0; i < reponsesFausses1.length && resultatFinal; i++) {
 			boolean reponseEgaleTrouvee = false;
 			for (int j = 0; j < reponsesFausses2.length && !reponseEgaleTrouvee; j++) {
 				reponseEgaleTrouvee
 				= reponsesFausses1[i].equals(reponsesFausses2[j]);
 			}
 			if (!reponseEgaleTrouvee) {
 				resultatFinal = reponseEgaleTrouvee;
 			}
 		}
 		return resultatFinal;
 	}

}