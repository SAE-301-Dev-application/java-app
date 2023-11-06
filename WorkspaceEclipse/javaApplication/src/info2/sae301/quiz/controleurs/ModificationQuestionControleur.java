package info2.sae301.quiz.controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ModificationQuestionControleur {

	@FXML
	private TextField intituleQuestion;
	
	@FXML
	private ChoiceBox<String> intituleCategorie;
	
	@FXML
	private ChoiceBox<String> niveauDiffilculte;
	
	@FXML
	private TextField feedback;
	
	@FXML
	private TextField reponseJuste;
	
	@FXML
	private TextField reponseFausse1;
	
	@FXML
	private TextField reponseFausse2;
	
	@FXML
	private TextField reponseFausse3;
	
	@FXML
	private TextField reponseFausse4;
	
	@FXML
	private void boutonAnnuler() {
		ControleurNavigation.changerVue("EditerQuestion.fxml");
	}
	
	@FXML
	private void boutonEnregistrer() {
		
	}
	
	@FXML
	private void boutonAide() {
		
	}
	
}
