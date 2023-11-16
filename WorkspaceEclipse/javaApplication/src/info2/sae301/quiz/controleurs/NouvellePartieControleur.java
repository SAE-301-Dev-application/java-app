/*
 * NouvellePartie.java							            10 nov. 2023
 * IUT de Rodez, pas de copyright, ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.exceptions.AucuneQuestionCorrespondanteException;
import info2.sae301.quiz.exceptions.NbInsuffisantQuestionsException;
import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.ParametresPartie;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

/**
 * Contrôleur FXML de la vue MenuPrincipal lancée par défaut lors du
 * démarrage de l'application.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class NouvellePartieControleur {
	
	private static final String ERREUR_NOMBRE_QUESTIONS_TITRE 
	= "Nombre incorrect de questions";
	
	private static final String ERREUR_NOMBRE_QUESTIONS_MESSAGE
	= """
	  Le nombre sélectionné de questions est incorrect. 
	  Vous ne pouvez sélectionner que 5, 10 ou 20 questions.
	  """;
	
	private static final String ERREUR_DIFFICULTE_TITRE 
	= "Difficulté invalide";
	
	private static final String ERREUR_DIFFICULTE_MESSAGE
	= """
	  La difficulté sélectionnée est invalide.
	  
	  Les difficultés existantes sont :
	  0. Indifférent
	  1. Facile
	  2. Moyen
	  3. Difficile
	  """;
	
	/** Indice du niveau de difficulté "Indifférent". */
	private static final int DIFFICULTE_INDIFFERENT = 0;
	
	/** Indice du niveau de difficulté "Facile". */
	private static final int DIFFICULTE_FACILE = 1;
	
	/** Indice du niveau de difficulté "Moyen". */
	private static final int DIFFICULTE_MOYEN = 2;
	
	/** Indice du niveau de difficulté "Difficile". */
	private static final int DIFFICULTE_DIFFICILE = 3;
	
	/** Instance du jeu. */
	private static Jeu jeu = Quiz.jeu;
	
	/**
	 * Affichage de l'erreur :
	 * Le nombre de questions sélectionné ne vaut ni 5, 10 et 20.
	 */
	private static void erreurNombreQuestions() {
		AlerteControleur.autreAlerte(ERREUR_NOMBRE_QUESTIONS_MESSAGE, 
				 					 ERREUR_NOMBRE_QUESTIONS_TITRE, 
				 					 AlertType.ERROR);
	}
	
	/**
	 * Affichage de l'erreur :
	 * La difficulté est invalide, inexistante.
	 */
	private static void erreurDifficulte() {
		AlerteControleur.autreAlerte(ERREUR_DIFFICULTE_MESSAGE, 
				 					 ERREUR_DIFFICULTE_TITRE, 
				 					 AlertType.ERROR);
	}
	
	/** Nombre de questions du futur quiz. */
	private int nombreQuestions;
	
	/**
	 * Niveau de difficulté du quiz :
	 * - 0 = indifférent
	 * - 1 = facile
	 * - 2 = moyen
	 * - 3 = difficile
	 */
	private int difficulte;
	
	/** Catégories sélectionnées pour le futur quiz. */
	private ArrayList<Categorie> categoriesSelectionnees;
	
	/** Instance des paramètres de la future partie. */
	private ParametresPartie parametres;
	
	/** Checkbox "5 questions". */
	@FXML
	private CheckBox checkBox5Questions;
	
	/** Checkbox "10 questions". */
	@FXML
	private CheckBox checkBox10Questions;
	
	/** Checkbox "20 questions". */
	@FXML
	private CheckBox checkBox20Questions;
	
	/** Checkbox de difficulté "indifférent". */
	@FXML
	private CheckBox checkBoxDifficulteIndifferent;
	
	/** Checkbox de difficulté "facile". */
	@FXML
	private CheckBox checkBoxDifficulteFacile;
	
	/** Checkbox de difficulté "moyen". */
	@FXML
	private CheckBox checkBoxDifficulteMoyen;
	
	/** Checkbox de difficulté "difficile". */
	@FXML
	private CheckBox checkBoxDifficulteDifficile;
	
	/** Conteneur des catégories de question. */
	@FXML
	private VBox listeCategories;
	
	@FXML
	private void initialize() {
		/*
		 * Valeurs par défaut :
		 * - 5 questions
		 * - Difficulté "Indifférent"  
		 */
		
		this.choixNombreQuestions(5);
		this.choixDifficulte(0);
		this.categoriesSelectionnees = new ArrayList<>();
		
		/*
		 * Choix du nombre de questions.
		 */
		
		this.checkBox5Questions.setOnAction(event -> {
			this.choixNombreQuestions(5);
		});
		
		this.checkBox10Questions.setOnAction(event -> {
			this.choixNombreQuestions(10);
		});
		
		this.checkBox20Questions.setOnAction(event -> {
			this.choixNombreQuestions(20);
		});
		
		/*
		 * Choix des difficultés.
		 */
		
		this.checkBoxDifficulteIndifferent.setOnAction(event -> {
			this.choixDifficulte(0);
		});
		
		this.checkBoxDifficulteFacile.setOnAction(event -> {
			this.choixDifficulte(1);
		});
		
		this.checkBoxDifficulteMoyen.setOnAction(event -> {
			this.choixDifficulte(2);
		});
		
		this.checkBoxDifficulteDifficile.setOnAction(event -> {
			this.choixDifficulte(3);
		});
		
		/*
		 * Chargement des catégories de question.
		 */
		
		CheckBox checkBoxCategorie;
		
		for (Categorie categorieCourante: jeu.getToutesLesCategories()) {
			checkBoxCategorie = new CheckBox();
			checkBoxCategorie.setText(categorieCourante.getIntitule());
			
			checkBoxCategorie.setOnAction(event -> {
				this.selectionCategorie(categorieCourante);
			});
			
			this.listeCategories.getChildren().add(checkBoxCategorie);
		}
	}
	
	/**
	 * Choix du nombre de questions pour le quiz.
	 */
	private void choixNombreQuestions(int nombre) {
		if (nombre != 5 && nombre != 10 && nombre != 20) {
			erreurNombreQuestions();
		} else {
			this.nombreQuestions = nombre;

			this.checkBox5Questions.setSelected(nombre == 5);
			this.checkBox10Questions.setSelected(nombre == 10);
			this.checkBox20Questions.setSelected(nombre == 20);
		}
	}

	/**
	 * Choix du niveau de difficilté "Indifférent" pour le quiz.
	 */
	private void choixDifficulte(int difficulte) {
		if (difficulte < 0 || difficulte > 3) {
			erreurDifficulte();
		} else {
			boolean checkBoxDifficulteIndifferent,
					checkBoxDifficulteFacile,
					checkBoxDifficulteMoyen,
					checkBoxDifficulteDifficile;
			
			checkBoxDifficulteIndifferent = difficulte == DIFFICULTE_INDIFFERENT;
			checkBoxDifficulteFacile = difficulte == DIFFICULTE_FACILE;
			checkBoxDifficulteMoyen = difficulte == DIFFICULTE_MOYEN;
			checkBoxDifficulteDifficile = difficulte == DIFFICULTE_DIFFICILE;
			
			this.difficulte = difficulte;
			
			this.checkBoxDifficulteIndifferent.setSelected(checkBoxDifficulteIndifferent);
			this.checkBoxDifficulteFacile.setSelected(checkBoxDifficulteFacile);
			this.checkBoxDifficulteMoyen.setSelected(checkBoxDifficulteMoyen);
			this.checkBoxDifficulteDifficile.setSelected(checkBoxDifficulteDifficile);		
		}
	}
	
	private void selectionCategorie(Categorie categorieConcernee) {
		if (categoriesSelectionnees.contains(categorieConcernee)) {
			this.categoriesSelectionnees.remove(categorieConcernee);
		} else {
			this.categoriesSelectionnees.add(categorieConcernee);
		}
	}
	
	@FXML
	private void actionBoutonAide() {
		//AlerteControleur.aide(AIDE_TITRE, AIDE_TEXTE);
	}
	
	@FXML
	private void actionBoutonRetour() {
		NavigationControleur.changerVue("MenuPrincipal.fxml");
	}
	
	@FXML
	private void actionBoutonCreer() {
		boolean lancerPartie;
	
		try {	
			this.parametres.setCategoriesSelectionnees(this.categoriesSelectionnees);
			this.parametres.aAssezQuestions();
			lancerPartie = true;
		} catch (AucuneQuestionCorrespondanteException e) {
			lancerPartie = false;
			AlerteControleur.autreAlerte(e.getMessage(), "Questions inexistantes", AlertType.ERROR);
		} catch (NbInsuffisantQuestionsException e) {
			lancerPartie 
			= AlerteControleur.alerteConfirmation("Pas assez de questions", e.getMessage());
		}
		
		if (lancerPartie) {
			try {
				this.parametres.setNombreQuestions(this.nombreQuestions);					
			} catch(IllegalArgumentException e) {
				erreurNombreQuestions(e.getMessage());
			}
			
		} else {
			try {
				this.parametres.aAssezQuestions();
				lancerPartie = true;
			} catch (AucuneQuestionCorrespondanteException e) {
				AlerteControleur.autreAlerte(e.getMessage(), "Questions inexistantes", AlertType.ERROR);
			} catch (NbInsuffisantQuestionsException e) {
				lancerPartie 
				= AlerteControleur.alerteConfirmation(e.getMessage(), "Pas assez de questions");
			}
		}
		
		if (lancerPartie) {
			this.parametres = new ParametresPartie();
			this.parametres.setNombreQuestions(this.nombreQuestions);
			this.parametres.setDifficulteQuestions(this.difficulte);
			this.parametres.setCategoriesSelectionnees(this.categoriesSelectionnees);

			Quiz.partieCourante.setQuestionsProposees(this.parametres.choisirQuestionsProposees());
			Quiz.partieCourante.melangerQuestionsProposees();
			
			System.out.println("Catégories sélectionnées : "
			                   + Quiz.partieCourante.getParametresPartie().getCategoriesSelectionnees());
			
			NavigationControleur.changerVue("PartieEnCours.fxml");
		}
	}
	
}