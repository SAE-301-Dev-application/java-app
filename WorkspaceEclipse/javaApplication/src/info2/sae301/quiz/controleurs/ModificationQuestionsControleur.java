package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ModificationQuestionsControleur {

	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private Jeu jeu = Quiz.jeu;
	
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
		NavigationControleur.changerVue("EditerQuestion.fxml");
	}
	
	@FXML
	private void boutonEnregistrer() {
		
	}
	
	@FXML
	private void boutonAide() {
		
	}
	
}
