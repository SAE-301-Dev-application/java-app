/*
 * SuppressionQuestionsControleur.java								 7 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.Question;
import info2.sae301.quiz.modeles.Categorie;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Contrôleur FXML de la vue SuppressionQuestions qui affiche 
 * la liste des questions avec des checkbox pour les supprimer.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class SuppressionQuestionsControleur {
	
	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private Jeu jeu = Quiz.jeu;
	
	@FXML
	private VBox vBoxQuestions;
	
	@FXML
	private ChoiceBox<String> menuFiltre;
	
	@FXML
	private Button boutonPrecedent;
	
	@FXML
	private Button boutonSuivant;
	
	/** Toutes les questions dont la checkbox a été sélectionnée. */
	private ArrayList<Question> questionsSelectionnees = new ArrayList<>();
	
	private HBox ligneQuestion;
	
	private CheckBox checkBoxQuestion;
	
	/** Indice de la première question affichée sur la "page" courante. */
	private int indiceQuestion = 0; 
	
	/** Nom de la première catégorie affichée sur la "page" courante. */
	private String categorieCourante = "Toutes les catégories";
	
	private Label questionCourante;
	
	private ArrayList<CheckBox> toutesLesCheckBox = new ArrayList<>();
	
	/**
	 * Initialisation de la vue avec le style css correspondant et 
	 * l'affichage des questions et du bouton suivant.
	 */
	@FXML
	private void initialize() {
		NavigationControleur.getScene().getStylesheets()
		.add(getClass().getResource("/info2/sae301/quiz/vues/application.css")
				       .toExternalForm());
		
		menuFiltre.getItems().add("Toutes les catégories");
		// Affichage des catégories dans le menu déroulant de filtre
		for (Categorie categorieCourante : jeu.getToutesLesCategories()) {
			menuFiltre.getItems().add(categorieCourante.getIntitule());
		}
		// Toutes les catégories par défaut
		menuFiltre.setValue("Toutes les catégories");	
		
		afficherQuestions();
	}
	
	/**
	 * Réaffichage des questions lorsqu'une catégorie est sélectionnée.
	 */
	@FXML
	public void selectionFiltre() {
		System.out.println("Nouvelle catégorie sélectionnée : " + menuFiltre.getValue());
		if (!categorieCourante.equals(menuFiltre.getValue())) {
			indiceQuestion = 0;
			categorieCourante = menuFiltre.getValue();
		}
		afficherQuestions();
	}
	
	/**
	 * Affiche 10 questions au maximum et gère l'affichage des boutons
	 * précédent et suivant en fonction du nombre de questions 
	 * précédentes et suivantes.
	 */
	private void afficherQuestions() {
		ArrayList<Question> questionsAAfficher
		= jeu.questionsCategorie(menuFiltre.getValue());
		
	    // Calcul des indices pour l'affichage des questions
	    int indiceDebut = indiceQuestion;
	    int indiceFin = Math.min(indiceDebut + 10, questionsAAfficher.size());
	    
	    // Effacer le contenu actuel du VBox
	    vBoxQuestions.getChildren().clear();
		
	    // Afficher les (indiceFin - indiceDebut) catégories
	    for (int i = indiceDebut; i < indiceFin; i++) {
	    	ligneQuestion = new HBox();
	    	
	    	checkBoxQuestion = new CheckBox();
	    	checkBoxQuestion.getStyleClass().add("checkbox-margin");
	    	checkBoxQuestion.setId("" + i);
	    	
	    	if (i >= toutesLesCheckBox.size()) {
	    		toutesLesCheckBox.add(checkBoxQuestion);
	    	} else {
	    		checkBoxQuestion = toutesLesCheckBox.get(i);
	    	}
	    	
	    	questionCourante = new Label(questionsAAfficher.get(i)
	    			                     .getIntitule().replaceAll("\n", " "));
	        questionCourante.getStyleClass().add("intituleCategorieQuestion");
	        questionCourante.getStyleClass().add("intitule-padding-left");
	        
	        final int INDICE = i;
	        
	        checkBoxQuestion.setOnMouseClicked(event -> {
	        	selectionnerQuestion(INDICE);
	        });
	        
	        ligneQuestion.getChildren().add(checkBoxQuestion);
	        ligneQuestion.getChildren().add(questionCourante);
	        
	        vBoxQuestions.getChildren().add(ligneQuestion);
	    }
	    
	    // Cacher le bouton "Précédent" s'il n'y a plus de questions précédentes
	    boutonPrecedent.setVisible(!(indiceQuestion < 10));
	    
	    // Cacher le bouton "Suivant" s'il n'y a plus de questions suivantes
	    boutonSuivant.setVisible(questionsAAfficher.size() > 10
	    		                 && indiceFin < questionsAAfficher.size());
	}
	
	/**
	 * Clic sur la checkbox afin de sélectionner une question.
	 * 
	 * @param indice Indice de la question sélectionnée.
	 */
	private void selectionnerQuestion(int indice) {
		final Question QUESTION 
		= jeu.getToutesLesQuestions().get(indice); 
		
		if (toutesLesCheckBox.get(indice).isSelected()) {
			this.questionsSelectionnees.add(QUESTION);
		} else {
			this.questionsSelectionnees.remove(QUESTION);
		}
		
		System.out.println("Questions sélectionnées : ");
		
		for (Question question: this.questionsSelectionnees) {
			System.out.println("- " + question.getIntitule());
		}
		System.out.println();
	}
	
	/**
	 * Retrait de 10 questions à l'indice de la première question 
	 * à afficher et affichage des 10 questions précédentes.
	 */
	@FXML
	private void actionBoutonPrecedent() {
		// On recule de 10 questions
		indiceQuestion -= 10;
	    afficherQuestions();
	}
	
	/**
	 * Ajout de 10 questions à l'indice de la première question à 
	 * afficher et affichage des 10 questions suivantes. 
	 */
	@FXML
	private void actionBoutonSuivant() {
		// On avance de 10 catégories
		indiceQuestion += 10;
		afficherQuestions();
	}
	
	/**
	 * Redirection vers la pop-up de la suppression des questions
	 */
	@FXML
	private void actionBoutonAider() {
		AlerteControleur.aide(AffichageQuestionsControleur.AIDE_TITRE,
				              AffichageQuestionsControleur.AIDE_TEXTE);
	}
	
	/**
	 * Redirection vers la vue SuppressionQuestions pour sélectionner
	 * la ou les question(s) à supprimer.
	 */
	@FXML
	private void actionBoutonSupprimer() {
		final String TITRE_SELECTION_VIDE = "Aucune question n'est sélectionnée";
		final String MESSAGE_SELECTION_VIDE = "Veuillez sélectionner une question"
											+ " à supprimer ou cliquer sur Annuler.";
		
		final String TITRE_POPUP_CONFIRM = "Confirmer la suppression";
		final String DEMANDE_CONFIRMATION = "Êtes-vous sûr(e) de vouloir supprimer "
											+ this.questionsSelectionnees.size()
											+ " question(s) ?";
		
		boolean confirmerSuppression;
		
		if (this.questionsSelectionnees.size() == 0) {
			AlerteControleur.autreAlerte(MESSAGE_SELECTION_VIDE, 
										 TITRE_SELECTION_VIDE, 
										 AlertType.ERROR);
		} else {
			confirmerSuppression
			= AlerteControleur.alerteConfirmation(DEMANDE_CONFIRMATION, 
												  TITRE_POPUP_CONFIRM);
			
			if (confirmerSuppression) {
				jeu.supprimer(this.questionsSelectionnees.toArray(new Question[this.questionsSelectionnees.size()]));
				
				for (Question questionASupprimer: this.questionsSelectionnees) {
					questionASupprimer.getCategorie().supprimerQuestion(questionASupprimer);
				}
				
				NavigationControleur.changerVue("AffichageQuestions.fxml");
			}
		}
	}
	
    /**
	 * Redirection vers la vue MenuPrincipal.
     */
	@FXML
	private void actionBoutonAnnuler() {
		NavigationControleur.changerVue("AffichageQuestions.fxml");
	}
	
}
