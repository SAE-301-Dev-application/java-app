/*
 * ParametresPartie.java             							    14 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles;

import info2.sae301.quiz.exceptions.NbInsuffisantQuestionsException;
import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.exceptions.AucuneQuestionCorrespondanteException;
import info2.sae301.quiz.exceptions.DifficulteInvalideException;
import info2.sae301.quiz.exceptions.NombreQuestionsInvalideException;

import java.util.ArrayList;
import java.util.Collections;

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

	public static final String DIFFICULTE_INVALIDE
	= """
	  La difficulté sélectionnée est incorrect.
	  
	  Les difficultés existantes sont :
	  0. Indifférent
	  1. Facile
	  2. Moyen
	  3. Difficile
	  """;
	
	public static final String NOMBRE_INVALIDE
	= """
	  Le nombre de questions sélectionné est incorrect.
	  
	  Le nombre de questions à proposer doit être 5, 10 ou 20.";
	  """;
	
	/** Les catégories de questions sélectionnées. */
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
		setCategoriesSelectionnees(new ArrayList<Categorie>());
		setDifficulteQuestions(0); // Indifférente
		setNombreQuestions(5);
	}
	
	
	/**
	 * Paramètres d'une partie de jeu nouvellement initialisée avec les catégories
	 * sélectionnées, la difficulté et le nombre de questions en paramètres.
	 * 
	 * @param categoriesSelectionnees Les catégories sélectionnées.
	 * @param difficulteQuestions La difficulté des questions.
	 * @param nombreQuestions Le nombre de questions.
	 */
	public ParametresPartie(ArrayList<Categorie> categoriesSelectionnees,
			                int difficulteQuestions, int nombreQuestions) {
		
		setCategoriesSelectionnees(categoriesSelectionnees);
		setDifficulteQuestions(difficulteQuestions);
		setNombreQuestions(nombreQuestions);
		aAssezQuestions();
		
		Quiz.partieCourante.setQuestionsProposees(choisirQuestionsProposees());
		Quiz.partieCourante.melangerQuestionsProposees();
		
		System.out.println("Catégories sélectionnées : "
		                   + getCategoriesSelectionnees());
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
	 */
	public void aAssezQuestions()
	throws IllegalArgumentException, NumberFormatException {
		final int NOMBRE_QUESTIONS
		= choisirQuestionsProposees().size();
		
		final String MOINS_QUESTIONS
		= "Seulement " + NOMBRE_QUESTIONS + " questions correspondent à vos "
		  + "critères. Souhaitez-vous tout de même jouer ?";
		
		String texteDifficulte;
		switch (this.difficulteQuestions) {
		default:
		case 0:
			texteDifficulte = "indifférente";
			break;
		case 1:
			texteDifficulte = "facile";
			break;
		case 2:
			texteDifficulte = "moyenne";
			break;
		case 3:
			texteDifficulte = "difficile";
			break;
		}
		
		String AUCUNE_QUESTION
		= "Il n'y a aucune question dans les catégories sélectionnées dont "
		  + "la difficulté est " + texteDifficulte + ".\n"
		  + "Veuillez entrer d'autres paramètres ou créer des questions.";
		
		if (NOMBRE_QUESTIONS == 0) {
			throw new AucuneQuestionCorrespondanteException(AUCUNE_QUESTION);
		}
		if (this.nombreQuestions > NOMBRE_QUESTIONS) {
			throw new NbInsuffisantQuestionsException(MOINS_QUESTIONS);
		}
	}
	
	
	/**
	 * Récupère en fonction des paramètres de la partie courante des questions
	 * à proposer à l'utilisateur parmi les catégories sélectionnées.
	 * 
	 * @return La liste des questions correspondantes aux paramètres.
	 */
	public ArrayList<Question> recupQuestionsValides() {
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
	
	
	/**
	 * Mélange la liste des questions correspondantes aux paramètres et 
	 * ne récupère que le nombre de questions autorisé et choisi par
	 * l'utilisateur
	 * 
	 * @return La liste des questions correspondantes aux paramètres.
	 */
	public ArrayList<Question> choisirQuestionsProposees() {
		ArrayList<Question> questionsValides = recupQuestionsValides();
		Collections.shuffle(questionsValides);
		
		switch (nombreQuestions) {
		case 20:
			questionsValides.subList(0, 20);
		case 10:
			questionsValides.subList(0, 10);
		case 5:
			questionsValides.subList(0, 5);
		default:
		}
		return questionsValides;
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

	
	/**
	 * @param difficulteQuestions La difficulté des questions à proposer.
	 * @throws DifficulteInvalideException si la difficulté est invalide.
	 */
	public void setDifficulteQuestions(int difficulteQuestions)
	throws DifficulteInvalideException {
		if (difficulteQuestions < 0 || difficulteQuestions > 3) {
			throw new DifficulteInvalideException(DIFFICULTE_INVALIDE);
		}
		this.difficulteQuestions = difficulteQuestions;
	}

	
	/** @return Le nombre de questions paramétré. */
	public int getNombreQuestions() {
		return nombreQuestions;
	}

	
	/**
	 * @param nombreQuestions Le nombre de questions à proposer.
	 * @throws NombreQuestionsInvalideException si le nombre de questions
	 * n'est pas 5, 10 ou 20.
	 */
	public void setNombreQuestions(int nombreQuestions)
	throws NombreQuestionsInvalideException {
		
		if (nombreQuestions != 5 && nombreQuestions != 10
		    && nombreQuestions != 20) {
			throw new NombreQuestionsInvalideException(NOMBRE_INVALIDE);
		}
		this.nombreQuestions = nombreQuestions;
	}
	
}