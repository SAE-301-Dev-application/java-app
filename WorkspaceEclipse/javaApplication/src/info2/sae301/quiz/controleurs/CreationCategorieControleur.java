package info2.sae301.quiz.controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreationCategorieControleur {

	@FXML
	private TextField nouveauNomCategorie;
	
	@FXML
	private void boutonAide() {
		ControleurNavigation.changerVue("GestionDesCategories.fxml");
	}
	
	@FXML
	private void boutonAnnuler() {
		ControleurNavigation.changerVue("Categories.fxml");
	}
	
	@FXML
	private void boutonEnregistrer() {
		
	}
	
}
