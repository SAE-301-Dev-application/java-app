package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.gestion.GestionCategories;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class CreationCategoriesControleur {
	
	private static final String TITRE_ALERTE = "Erreur de création";

	private static final String MESSAGE_ERREUR_TROP_DE_CARACTERE =
			"Vous ne pouvez pas mettre plus de 20 caractères";
	
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
		try {
			GestionCategories.creer(nouveauNomCategorie.getText());
		} catch (Exception e) {
			ControleurAlerte.autreAlerte(MESSAGE_ERREUR_TROP_DE_CARACTERE,
										 TITRE_ALERTE, AlertType.ERROR);
		}
	}
	
}
