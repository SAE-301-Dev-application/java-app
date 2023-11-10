/*
 * ChoixEditionQuestionControleur.java							     7 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.Question;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Contrôleur FXML de la vue ChoixEditionQuestion qui affiche la liste des
 * questions sous forme de boutons.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class ChoixEditionQuestionControleur {
	
	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private static Jeu jeu = Quiz.jeu;
	
	/** Indice de la question à renommer qui a été sélectionnée. */
	private static int indiceQuestionSelectionnee;
	
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
	
	/** Nom de la première catégorie affichée sur la "page" courante. */
	private String categorieCourante = "Toutes les catégories";
	
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
		
		menuFiltre.getItems().add("Toutes les catégories");
		// Affichage des catégories dans le menu déroulant de filtre
		for (Categorie categorieCourante : jeu.getToutesLesCategories()) {
			menuFiltre.getItems().add(categorieCourante.getIntitule());
		}
		// Toutes les catégories par défaut
		menuFiltre.setValue("Toutes les catégories");
		
		afficherQuestions();
	}
	
	/** @return L'intitulé de la question sélectionnée. */
	public static int getIndiceQuestionSelectionnee() {
		return indiceQuestionSelectionnee;
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
	 * Affiche 5 questions au maximum et gère l'affichage des boutons
	 * précédent et suivant en fonction du nombre de questions précédentes
	 * et suivantes.
	 */
	private void afficherQuestions() {
		ArrayList<Question> questionsAAfficher
		= jeu.questionsCategorie(menuFiltre.getValue());
		
	    // Calcul des indices pour l'affichage des questions
	    int indiceDebut = indiceQuestion;
	    int indiceFin = Math.min(indiceDebut + 5, questionsAAfficher.size());
	    
	    // Effacer le contenu actuel du VBox
	    vBoxQuestions.getChildren().clear();
		
	    // Afficher les (indiceFin - indiceDebut) questions
	    for (int i = indiceDebut; i < indiceFin; i++) {
	    	Question question = questionsAAfficher.get(i);
	    	
	        questionCourante = new Label(question.getIntitule().replaceAll("\n", " "));
	        questionCourante.getStyleClass().add("intituleQuestionQuestion");
	        questionCourante.getStyleClass().add("labelCliquable");
	        vBoxQuestions.getChildren().add(questionCourante);
	        
	        final String intitule = question.getIntitule();
	        final String reponseJuste = question.getReponseJuste();
	        final String[] reponsesFausses = question.getReponsesFausses();
	        final int difficulte = question.getDifficulte();
	        final String feedback = question.getFeedback();
	        final String intituleCategorie = question.getCategorie().getIntitule();
			questionCourante.setOnMouseClicked(event -> {
			    actionEditerQuestion(intitule, reponseJuste, reponsesFausses,
			    		             difficulte, feedback, intituleCategorie);
			});
	    }
	    // Cacher le bouton "Précédent" s'il n'y a plus de questions précédentes
	    boutonPrecedent.setVisible(!(indiceQuestion < 5));
	    
	    // Cacher le bouton "Suivant" s'il n'y a plus de questions suivantes
	    boutonSuivant.setVisible(questionsAAfficher.size() > 5
	    		                 && indiceFin < questionsAAfficher.size());
	}
	
	/**
	 * Changement de vue et modification de l'attribut de la catégorie sélectionnée
	 * dans la classe de sauvegarde des paramètres, questions et questions.
	 * 
	 * @param intitule L'intitulé de la catégorie sélectionnée à sauvegarder.
	 */
	private void actionEditerQuestion(String intitule, String reponseJuste,
			                          String[] reponsesFausses, int difficulte,
			                          String feedback, String categorie) {
		indiceQuestionSelectionnee = jeu.indiceQuestion(intitule, categorie,
				                                        reponseJuste, reponsesFausses);
		
		NavigationControleur.changerVue("EditionQuestions.fxml");
	}
	
	/**
	 * Retrait de 5 questions à l'indice de la première catégorie à afficher
	 * et affichage des 5 questions précédentes. 
	 */
	@FXML
	private void actionBoutonPrecedent() {
		// On recule de 5 questions
		indiceQuestion -= 5;
		afficherQuestions();
	}
	
	/**
	 * Ajout de 5 questions à l'indice de la première catégorie à afficher
	 * et affichage des 5 questions suivantes. 
	 */
	@FXML
	private void actionBoutonSuivant() {
		// On avance de 5 questions
		indiceQuestion += 5;
	    afficherQuestions();
	}
	
	/**
	 * TODO : coder action bouton aide
	 */
	@FXML
	private void actionBoutonAide() {
		AlerteControleur.aide(AffichageCategoriesControleur.AIDE_TITRE, AffichageCategoriesControleur.AIDE_TEXTE);
	}
	
	/**
	 * Redirection vers la vue AffichageQuestions.
	 */
	@FXML
	private void actionBoutonAnnuler() {
		NavigationControleur.changerVue("AffichageQuestions.fxml");
	}
	
}
