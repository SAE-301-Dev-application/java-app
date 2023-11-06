package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Question;
import info2.sae301.quiz.gestion.GestionCategories;
import info2.sae301.quiz.gestion.GestionQuestions;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SupprimerQuestionsControleur {
	
	@FXML
	private VBox vBox;
	
	@FXML
	private void initialize() {
		ArrayList<Question> toutesLesQuestions = GestionQuestions.getListeToutesQuestions();
		
		HBox ligneQuestion;
		CheckBox caseQuestion;
		Label questionCourante;
		
		for (Question question : toutesLesQuestions) {
			ligneQuestion = new HBox();
			
			caseQuestion = new CheckBox();
			
			questionCourante = new Label(question.getIntitule());
			questionCourante.getStyleClass().add("intituleCategorieQuestion");
			
			ligneQuestion.getChildren().add(caseQuestion);
			ligneQuestion.getChildren().add(questionCourante);
			
			vBox.getChildren().add(ligneQuestion);
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
		ControleurNavigation.changerVue("AffichageQuestions.fxml");
	}
	
	@FXML
	private void boutonSupprimer() {
		// TODO: suppression à implémenter
	}
	
	
}
