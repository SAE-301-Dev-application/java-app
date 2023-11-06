package info2.sae301.quiz.controleurs;

import javafx.fxml.FXML;

public class MenuPrincipalControleur {

	@FXML
	private void boutonAide() {
		ControleurNavigation.changerVue("PresentationDuJeu.fxml");
	}
	
	@FXML
	private void boutonUser() {
		ControleurNavigation.changerVue("GestionDuNomUtilisateur.fxml");
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
		ControleurNavigation.changerVue("Questions.fxml");
	}


	@FXML
	private void boutonImportation() {
		
	}
	
	@FXML
	private void boutonExportation() {
		
	}
	
	
}
