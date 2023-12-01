/*
 * SuppressionQuestionsControleur.java								 7 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;
import java.util.HashMap;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
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
	
	/** Indice de la première question affichée sur la "page" courante. */
	private int indiceQuestion; 
	
	/** Nom de la première catégorie affichée sur la "page" courante. */
	private String categorieCourante;
	
	/** Les questions de la catégorie courante à afficher */
	private ArrayList<Question> questionsCategorie = new ArrayList<>();
	
	/** Association de toutes les questions à leur checkbox */
	private HashMap<Question, CheckBox> toutesLesQuestions = new HashMap<>();
	/**
	 * Initialisation de la vue avec le style css correspondant et 
	 * l'affichage des questions et du bouton suivant.
	 */
	@FXML
	private void initialize() {
		indiceQuestion = AffichageQuestionsControleur.indiceQuestion;
		categorieCourante = AffichageQuestionsControleur.categorieCourante;
		
		NavigationControleur.getScene().getStylesheets()
		.add(getClass().getResource("/info2/sae301/quiz/vues/application.css")
				       .toExternalForm());
		
		menuFiltre.getItems().add("Toutes les catégories");
		// Affichage des catégories dans le menu déroulant de filtre
		for (Categorie categorieCourante : jeu.getToutesLesCategories()) {
			menuFiltre.getItems().add(categorieCourante.getIntitule());
		}
		
		initialiserToutesLesQuestions();
		/* l'apelle à cette méthode apelle
		implicitement selectionFiltre */
		menuFiltre.setValue(categorieCourante);
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
		initialiserQuestionsCategorie();
		afficherQuestions();
	}
	
	/**
	 * initialisation de toutes les checkboxs représentent
	 * les questions selon le filtre de catégorie.
	 */
	private void initialiserToutesLesQuestions() {
		toutesLesQuestions.clear();
		for (int i = 0; i < jeu.getToutesLesQuestions().size(); i++) {
			Question questionCourante = jeu.getToutesLesQuestions().get(i);
			CheckBox checkBoxQuestion = new CheckBox();
			toutesLesQuestions.put(questionCourante, checkBoxQuestion);
	    	checkBoxQuestion.setId("" + i);
	    	checkBoxQuestion.setText(questionCourante.getIntitule().replaceAll("\n", " "));
	    	checkBoxQuestion.getStyleClass().add("checkbox-margin");
			checkBoxQuestion.getStyleClass().add("intituleCategorieQuestion");
			checkBoxQuestion.getStyleClass().add("intitule-padding-left");
			checkBoxQuestion.setOnMouseClicked(event -> {
				selectionnerQuestion(questionCourante, checkBoxQuestion);
			});
		}
	}
	/**
	 * Initialisation de questionCategorie en fonction du
	 * filtre de catégorie actuelle.
	 */
	private void initialiserQuestionsCategorie() {
		questionsCategorie = new ArrayList<Question>();
		if (categorieCourante.equals("Toutes les catégories")) {
			questionsCategorie = jeu.getToutesLesQuestions();	
		} else {
			questionsCategorie = jeu.questionsCategorie(categorieCourante);
		}
	}
	
	/**
	 * Affiche 10 questions au maximum et gère l'affichage des boutons
	 * précédent et suivant en fonction du nombre de questions 
	 * précédentes et suivantes.
	 */
	private void afficherQuestions() {
		
	    // Calcul des indices pour l'affichage des questions
	    int indiceDebut = indiceQuestion;
	    int indiceFin = Math.min(indiceDebut + 10, questionsCategorie.size());
	    
	    // Effacer le contenu actuel du VBox
	    vBoxQuestions.getChildren().clear();
		
	    // Afficher les (indiceFin - indiceDebut) questions
	    for (int i = indiceDebut; i < indiceFin; i++) {
	        vBoxQuestions.getChildren().add(
	        				toutesLesQuestions.get(questionsCategorie.get(i)));
	    }
	    
	    // Cacher le bouton "Précédent" s'il n'y a plus de questions précédentes
	    boutonPrecedent.setVisible(!(indiceQuestion < 10));
	    
	    // Cacher le bouton "Suivant" s'il n'y a plus de questions suivantes
	    boutonSuivant.setVisible(questionsCategorie.size() > 10
	    		                 && indiceFin < questionsCategorie.size());
	}
	
	/**
	 * Clic sur la checkbox afin de sélectionner une question.
	 * 
	 * @param indiceQestion Indice de la question sélectionnée.
	 */
	private void selectionnerQuestion(Question question, CheckBox checkBox) {
		if (checkBox.isSelected()) {
			this.questionsSelectionnees.add(question);
		} else {
			this.questionsSelectionnees.remove(question);
		}
		
		System.out.println("Questions sélectionnées : ");
		
		for (Question qSelectCourante : this.questionsSelectionnees) {
			System.out.println("- " + qSelectCourante.getIntitule());
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
	    System.out.println("Suppression : " + indiceQuestion);
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
		System.out.println("Suppression : " + indiceQuestion);
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
				
				AffichageQuestionsControleur.categorieCourante = categorieCourante;
				indiceQuestionApresSuppression();
				NavigationControleur.changerVue("AffichageQuestions.fxml");
			}
		}
	}
	
    /**
	 * Redirection vers la vue AffichageQuestions.
     */
	@FXML
	private void actionBoutonAnnuler() {
		AffichageQuestionsControleur.categorieCourante = categorieCourante;
		AffichageQuestionsControleur.indiceQuestion = indiceQuestion;
		NavigationControleur.changerVue("AffichageQuestions.fxml");
	}
	
	/**
	 * Adapte indiceQuestion en fonction du nombre de
	 * suppression de question et de la page courante.
	 */
	private void indiceQuestionApresSuppression() {
		for (;indiceQuestion >= questionsCategorie.size(); indiceQuestion-=10);
		AffichageQuestionsControleur.indiceQuestion = indiceQuestion;
		
	}
}
