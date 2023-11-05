package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.gestion.GestionCategories;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class CreationCategoriesControleur {
	
	private static final String TITRE_ALERTE = "Erreur de création";
	
	@FXML
	private TextField nouveauNomCategorie;
	
	@FXML
	private void boutonAide() {
//		ControleurNavigation.changerVue("GestionDesCategories.fxml");
	}
	
	@FXML
	private void boutonAnnuler() {
		ControleurNavigation.changerVue("Categories.fxml");
	}
	
	@FXML
	private void boutonEnregistrer() {
		try {
			GestionCategories.creer(nouveauNomCategorie.getText());
			ControleurNavigation.changerVue("Categories.fxml");
		} catch (IllegalArgumentException e) {
			ControleurAlerte.autreAlerte(e.getMessage(),
										 TITRE_ALERTE, AlertType.ERROR);
		}
	}
	
}
