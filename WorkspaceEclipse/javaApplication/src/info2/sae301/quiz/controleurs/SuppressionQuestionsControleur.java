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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Contrôleur FXML de la vue SuppressionQuestions qui affiche la liste des
 * questions avec des checkbox pour les supprimer.
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
	
	/** Indice de la première question affichée sur la "page" courante. */
	private int indiceQuestion = 0; 
	
	private ArrayList<Question> toutesLesQuestions = jeu.getToutesLesQuestions();
	
	private Label questionCourante;
	
	/**
	 * Initialisation de la vue avec le style css correspondant et l'affichage
	 * des questions et du bouton suivant.
	 */
	@FXML
	private void initialize() {
		NavigationControleur.getScene().getStylesheets()
		.add(getClass().getResource("/info2/sae301/quiz/vues/application.css")
				       .toExternalForm());
		
		afficherQuestions();
		
		// Affichage des catégories dans le menu déroulant de filtre
		for (Categorie categorieCourante : jeu.getToutesLesCategories()) {
			menuFiltre.getItems().add(categorieCourante.getIntitule());
		}
		// Catégorie général par défaut
		menuFiltre.setValue(jeu.getToutesLesCategories().get(0).getIntitule());
	}
	
	/**
	 * Affiche 10 questions au maximum et gère l'affichage des boutons
	 * précédent et suivant en fonction du nombre de questions précédentes
	 * et suivantes.
	 */
	private void afficherQuestions() {
	    // Calcul des indices pour l'affichage des questions
	    int indiceDebut = indiceQuestion;
	    int indiceFin = Math.min(indiceDebut + 10, toutesLesQuestions.size());
	    
	    // Effacer le contenu actuel du VBox
	    vBoxQuestions.getChildren().clear();
		
	    // Afficher les (indiceFin - indiceDebut) catégories
	    for (int i = indiceDebut; i < indiceFin; i++) {
	    	questionCourante = new Label(toutesLesQuestions.get(i).getIntitule());
	        questionCourante.getStyleClass().add("intituleCategorieQuestion");
	        vBoxQuestions.getChildren().add(questionCourante);
	    }
	    // Cacher le bouton "Précédent" s'il n'y a plus de questions précédentes
	    boutonPrecedent.setVisible(!(indiceQuestion < 10));
	    
	    // Cacher le bouton "Suivant" s'il n'y a plus de questions suivantes
	    boutonSuivant.setVisible(toutesLesQuestions.size() > 10
	    		                 && indiceFin < toutesLesQuestions.size());
	}
	
	/**
	 * Retrait de 10 questions à l'indice de la première question à afficher
	 * et affichage des 10 questions précédentes.
	 */
	@FXML
	private void actionBoutonPrecedent() {
		// On recule de 10 questions
		indiceQuestion -= 10;
	    afficherQuestions();
	}
	
	/**
	 * Ajout de 10 questions à l'indice de la première question à afficher
	 * et affichage des 10 questions suivantes. 
	 */
	@FXML
	private void actionBoutonSuivant() {
		// On avance de 10 catégories
		indiceQuestion += 10;
		afficherQuestions();
	}
	
	/**
	 * TODO : coder action bouton aide
	 */
	@FXML
	private void actionBoutonAider() {
//		ControleurNavigation.changerVue("GestionDesQuestions.fxml");
	}
	
	/**
	 * Redirection vers la vue SuppressionQuestions pour sélectionner la ou les
	 * question(s) à supprimer.
	 */
	@FXML
	private void actionBoutonSupprimer() {
		
	}
	
    /**
	 * Redirection vers la vue MenuPrincipal.
     */
	@FXML
	private void actionBoutonAnnuler() {
		NavigationControleur.changerVue("AffichageQuestions.fxml");
	}
	
}
