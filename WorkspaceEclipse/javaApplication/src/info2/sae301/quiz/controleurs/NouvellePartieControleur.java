/*
 * NouvellePartie.java							                    17 nov. 2023
 * IUT de Rodez, pas de copyright, ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.exceptions.AucuneQuestionCorrespondanteException;
import info2.sae301.quiz.exceptions.NbInsuffisantQuestionsException;
import info2.sae301.quiz.exceptions.DifficulteInvalideException;
import info2.sae301.quiz.exceptions.NombreQuestionsInvalideException;

import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.ParametresPartie;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

/**
 * Contrôleur FXML de la vue ParametresPartie.fxml permettant de paramétrer
 * une partie de jeu.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class NouvellePartieControleur {
	
	private static final String AIDE_TITRE = "CRÉER UNE PARTIE";

	private static final String AIDE_TEXTE
	= """
	  Trois paramètres sont à renseigner afin de créer une partie :
	  - les catégories qui seront incluse dans le quiz,
	  - le nombre de questions,
	  - le ou les niveaux de difficultés acceptés.

      Le choix du niveau de difficulté est optionnel
      puisque le choix indifférent sélectionné par défaut.
      
      Afin de personnaliser davantage la partie, il est possible
      de choisir une ou plusieurs difficultés entre facile, moyenne et difficile.
	  """;
	
	private static final String ERREUR_NOMBRE_QUESTIONS_TITRE 
	= "Nombre de questions incorrect";
	
	private static final String ERREUR_DIFFICULTE_TITRE 
	= "Difficulté invalide";
	
	private static final String INDICATION_NB_QUESTIONS
	= "Total de questions%s dans les catégories sélectionnées : %d";
	
	private static final String ERREUR_AUCUNE_QUESTION_TITRE
	= "Aucune question";
	
	private static final String ERREUR_MOINS_QUESTIONS_TITRE
	= "Pas assez de questions";
	
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
		AlerteControleur.autreAlerte(ParametresPartie.NOMBRE_INVALIDE, 
				 					 ERREUR_NOMBRE_QUESTIONS_TITRE, 
				 					 AlertType.ERROR);
	}
	
	/**
	 * Affichage de l'erreur :
	 * La difficulté est invalide, inexistante.
	 */
	private static void erreurDifficulte() {
		AlerteControleur.autreAlerte(ParametresPartie.DIFFICULTE_INVALIDE,
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
	private Label indicationNbQuestions;
	
	/**
	 * Initialisation du nombre de questions, de la difficulté et des catégories
	 * existantes à sélectionner.
	 */
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
		 * Affichage indication du nombre de questions sélectionnées
		 */
		
		indicationNbQuestions.setText(
		    String.format(INDICATION_NB_QUESTIONS,
		    		      ParametresPartie.texteDifficulte(difficulte), 0));
		
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
			majNombreQuestionsCategories();
		});
		
		this.checkBoxDifficulteFacile.setOnAction(event -> {
			this.choixDifficulte(1);
			majNombreQuestionsCategories();
		});
		
		this.checkBoxDifficulteMoyen.setOnAction(event -> {
			this.choixDifficulte(2);
			majNombreQuestionsCategories();
		});
		
		this.checkBoxDifficulteDifficile.setOnAction(event -> {
			this.choixDifficulte(3);
			majNombreQuestionsCategories();
		});
		
		/*
		 * Chargement des catégories de question.
		 */
		
		CheckBox checkBoxCategorie;
		
		/* Affichage des catégories */
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
	 * Met à jour l'indication du nombre de questions
	 * dans les catégories sélectionnées.
	 */
	private void majNombreQuestionsCategories() {
		String texteDifficulte = ParametresPartie.texteDifficulte(difficulte);
		
		if (!texteDifficulte.isEmpty()) {
			texteDifficulte = " " + texteDifficulte + "s";
		}
		
		String texteFinal
		= (difficulte != 0 ? " " : "")
		  + String.format(INDICATION_NB_QUESTIONS,
				        texteDifficulte,
  		                ParametresPartie
  		                .recupQuestionsValides(this.difficulte,
	    		                               this.categoriesSelectionnees)
  		                .size());
		
		indicationNbQuestions.setText(texteFinal);
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
	 * Choix du niveau de difficulté "Indifférent" pour le quiz.
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
	
	/**
	 * Clic sur une checkbox permettant d'ajouter une catégorie
	 * à la liste des catégories sélectionnées.
	 * 
	 * @param categorieConcernee La catégorie dont la checkbox a été cliquée.
	 */
	private void selectionCategorie(Categorie categorieConcernee) {
		if (categoriesSelectionnees.contains(categorieConcernee)) {
			this.categoriesSelectionnees.remove(categorieConcernee);
		} else {
			this.categoriesSelectionnees.add(categorieConcernee);
		}
		majNombreQuestionsCategories();
	}
	
	
	/**
	 * Affichage d'une pop-up d'aide concernant le paramétrage de la partie.
	 */
	@FXML
	private void actionBoutonAide() {
		AlerteControleur.aide(AIDE_TITRE, AIDE_TEXTE);
	}
	
	
	/**
	 * Retour vers la vue MenuPrincipal.
	 */
	@FXML
	private void actionBoutonRetour() {
		NavigationControleur.changerVue("MenuPrincipal.fxml");
	}
	
	
	/**
	 * Vérification que les paramètres soient valides et lancement d'une partie
	 * avec ces paramètres.
	 */
	@FXML
	private void actionBoutonCreer() {
		boolean lancerPartie = true;
		
		try {
			ParametresPartie.aAssezQuestions(this.difficulte,
											 this.nombreQuestions,
					                         this.categoriesSelectionnees);
		} catch (AucuneQuestionCorrespondanteException e) {
			AlerteControleur.autreAlerte(e.getMessage(),
										 ERREUR_AUCUNE_QUESTION_TITRE,
                                         AlertType.ERROR);
		} catch (NbInsuffisantQuestionsException e) {
			lancerPartie
			= AlerteControleur.alerteConfirmation(e.getMessage(), 
					   							  ERREUR_MOINS_QUESTIONS_TITRE);
		}
		
		if (lancerPartie) {
			try {
				ParametresPartie nouveauxParametres
				= new ParametresPartie(this.categoriesSelectionnees, this.difficulte,
						               this.nombreQuestions);
				
				Quiz.partieCourante.setParametresPartie(nouveauxParametres);
				Quiz.partieCourante.setIndiceDerniereQuestionVue(0);
				NavigationControleur.changerVue("PartieEnCours.fxml");
				
			} catch (DifficulteInvalideException e) {
				erreurDifficulte();
				
			} catch (NombreQuestionsInvalideException e) {
				erreurNombreQuestions();
			}
		}
	}
	
}