package info2.sae301.quiz.controleurs;

import javafx.fxml.FXML;

public class CategoriesControleur {

	@FXML
	private void boutonAide() {
		ControleurNavigation.changerVue("GestionDesCategories.fxml");
	}
	@FXML
	private void boutonRenommer() {
		ControleurNavigation.changerVue("ModidicationCategories.fxml");
	}
	@FXML
	private void boutonSupprimer() {
		ControleurNavigation.changerVue("SuppressionCategories.fxml");
	}
	
	@FXML
	private void boutonPrecedent() {
	}
	
	@FXML
	private void boutonSuivant() {
	}
	
	@FXML
	private void boutonRetour() {
	}
	
	@FXML
	private void boutonCreer() {
		ControleurNavigation.changerVue("CreationCategorie.fxml");
	}
	
	
}
