package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.Question;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EditerQuestionControleur {
	
	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private Jeu jeu = Quiz.jeu;
	
	@FXML
	private VBox vBox;
	
	@FXML
	private void initialize() {
		ArrayList<Question> toutesLesQuestions = jeu.getToutesLesQuestions();
		
		Label questionCourante;
		
		for (Question question : toutesLesQuestions) {
			questionCourante = new Label(question.getIntitule());
			questionCourante.getStyleClass().add("labelCliquable");
			
			vBox.getChildren().add(questionCourante);
		}
	}

	@FXML
	private void boutonAide() {
//		ControleurNavigation.changerVue("GestionDesQuestions.fxml");  // TODO: implémenter la fenêtre d'aide
	}
	
	@FXML
	private void boutonPrecedent() {
	}
	
	@FXML
	private void boutonSuivant() {
	}
	
	@FXML
	private void boutonRetour() {
		NavigationControleur.changerVue("AffichageQuestions.fxml");
	}
	
	@FXML
	private void editerQuestion() {
		// TODO: implémenter form
	}
	
	
}
