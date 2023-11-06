package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import javafx.fxml.FXML;

public class MenuPrincipalControleur {

	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private Jeu jeu = Quiz.jeu;
	
	@FXML
	private void boutonAide() {
		//ControleurNavigation.changerVue("PresentationDuJeu.fxml");
	}
	
	@FXML
	private void boutonUser() {
		//ControleurNavigation.changerVue("GestionNomUtilisateur.fxml");
	}
	
	@FXML
	private void boutonJouer() {
		
	}
	
	@FXML
	private void boutonCategories() {
		ControleurNavigation.changerVue("AffichageCategories.fxml");
	}
	
	@FXML
	private void boutonQuestions() {
		ControleurNavigation.changerVue("AffichageQuestions.fxml");
	}


	@FXML
	private void boutonImportation() {
		
	}
	
	@FXML
	private void boutonExportation() {
		
	}
	
	
}
