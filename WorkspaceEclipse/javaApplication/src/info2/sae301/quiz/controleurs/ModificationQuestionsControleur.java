package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class ModificationQuestionsControleur {

	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private Jeu jeu = Quiz.jeu;
	
	@FXML
	private TextArea intituleQuestion;
	
	@FXML
	private ChoiceBox<String> intituleCategorie;
	
	@FXML
	private ChoiceBox<String> niveauDiffilculte;
	
	@FXML
	private TextArea feedback;
	
	@FXML
	private TextArea reponseJuste;
	
	@FXML
	private TextArea reponseFausse1;
	
	@FXML
	private TextArea reponseFausse2;
	
	@FXML
	private TextArea reponseFausse3;
	
	@FXML
	private TextArea reponseFausse4;
	
	@FXML
	private void boutonAide() {
		
	}
	
	@FXML
	private void boutonAnnuler() {
		NavigationControleur.changerVue("EditerQuestion.fxml");
	}
	
	@FXML
	private void boutonEnregistrer() {
		verificationDesChamps();
	}
	
	/**
	 * Vérifie que les champs obligatoire sont bien initialisé
	 * et que tous les champs respectent la limite de caractère.
	 * @return true si tous les champs sont conforme
	 */
	private boolean verificationDesChamps() {
		return false;
	}
	
	
}
