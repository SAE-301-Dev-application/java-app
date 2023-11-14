/*
 * ParametresPartie.java             							    14 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles;

import java.util.ArrayList;

/**
 * Les paramètres d'une partie de jeu.
 * - Difficulté des questions
 * - Nombre de questions
 * - Catégories des questions
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class ParametresPartie {

	/** TODO javadoc */
	private ArrayList<Categorie> categoriesSelectionnees;
	
	/**
	 * La difficulté des questions à proposer à l'utilisateur :
	 * <ul>
	 *     <li>0 : Indifférente</li>
	 *     <li>1 : Facile</li>
	 *     <li>2 : Moyenne</li>
     *     <li>3: Difficile</li>
     * </ul>
	 */
	private int difficulteQuestions;
	
	/**
	 * Le nombre de questions à proposer à l'utilisateur :
	 * <ul>
	 *     <li>5</li>
	 *     <li>10</li>
	 *     <li>20</li>
     * </ul>
	 */
	private int nombreQuestions;
	
	/**
	 * Paramètres d'une partie de jeu nouvellement initialisée avec une difficulté
	 * indifférente et 10 questions affichées dans le jeu par défaut.
	 */
	public ParametresPartie() {
		this.categoriesSelectionnees = new ArrayList<Categorie>();
		this.difficulteQuestions = 0; // Indifférente
		this.nombreQuestions = 10;
	}
	
	/**
	 * Ajoute les catégories en paramètre à la liste des catégories
	 * sélectionnées.
	 * 
	 * @param aSelectionner La liste des catégories à sélectionner.
	 */
	public void selectionnerCategories(ArrayList<Categorie> aSelectionner) {
		this.categoriesSelectionnees.addAll(aSelectionner);
	}
	
	/**
	 * Vérification du fait qu'il y ait assez de questions dont la difficulté est
	 * difficulteQuestions dans les catégories sélectionnées afin
	 * d'afficher nombreQuestions questions.
	 * 
	 * @throws IllegalArgumentException si aucune question ne correspond aux critères.
	 * @throws NumberFormatException si moins de questions que le nombre de questions
	 * souhaitées correspondent aux critères.
	 * @return true si il y a assez de questions.
	 */
	public void aAssezQuestions()
	throws IllegalArgumentException, NumberFormatException {
		final int NOMBRE_QUESTIONS
		= choisirQuestionsProposees().size();
		
		final String AUCUNE_QUESTION
		= "Il n'y a aucune question dans les catégories sélectionnées dont"
		  + "la difficulté est égale à " + this.difficulteQuestions + ".\n"
		  + "Veuillez entrer d'autres paramètres ou créer des questions.";
		
		final String MOINS_QUESTIONS
		= "Seulement " + NOMBRE_QUESTIONS + " questions correspondent à vos "
		  + "critères. Souhaitez-vous tout de même jouer ?";
		
		if (NOMBRE_QUESTIONS == 0) {
			throw new IllegalArgumentException(AUCUNE_QUESTION);
		}
		if (this.nombreQuestions > NOMBRE_QUESTIONS) {
			throw new NumberFormatException(MOINS_QUESTIONS);
		}
	}
	
	/**
	 * Choisis en fonction des paramètres de la partie courante des questions
	 * à proposer à l'utilisateur parmi les catégories sélectionnées.
	 * 
	 * @return La liste des questions correspondantes aux paramètres.
	 */
	public ArrayList<Question> choisirQuestionsProposees() {
		ArrayList<Question> questions = new ArrayList<Question>();
		
		for (Categorie categorie : this.categoriesSelectionnees) {
			if (difficulteQuestions == 0) {
				questions.addAll(categorie.getListeQuestions());
			} else {
				// Recherche des questions dont la difficulté est en paramètre.
				for (Question question : categorie.getListeQuestions()) {
					if (question.getDifficulte() == this.getDifficulteQuestions()) {
						questions.add(question);
					}
				}
		    }
		}
		return questions;
	}

	/** @return Les catégories sélectionnées. */
	public ArrayList<Categorie> getCategoriesSelectionnees() {
		return categoriesSelectionnees;
	}

	/** @param categoriesSelectionnees Les catégories de questions sélectionnées. */
	public void setCategoriesSelectionnees(ArrayList<Categorie> categoriesSelectionnees) {
		this.categoriesSelectionnees = categoriesSelectionnees;
	}

	/** @return La difficulté des questions paramétrée. */
	public int getDifficulteQuestions() {
		return difficulteQuestions;
	}

	/** @param difficulteQuestions La difficulté des questions à proposer. */
	public void setDifficulteQuestions(int difficulteQuestions) {
		this.difficulteQuestions = difficulteQuestions;
	}

	/** @return Le nombre de questions paramétré. */
	public int getNombreQuestions() {
		return nombreQuestions;
	}

	/** @param nombreQuestions Le nombre de questions à proposer. */
	public void setNombreQuestions(int nombreQuestions) {
		this.nombreQuestions = nombreQuestions;
	}
	
}