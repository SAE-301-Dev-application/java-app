package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.controleurs.ChoixRenommerCategorieControleur;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class RenommerCategoriesControleur {
	
	private static final String TITRE_ALERTE = "Erreur de renommage";
	
	@FXML
	private TextField entreeNomCategorie;
	
	@FXML
	private void initialize() {
		entreeNomCategorie
		.setText(ChoixRenommerCategorieControleur.getIntituleCategorieSelectionnee());
	}
	
	@FXML
	private void actionBoutonAide() {
//		ControleurNavigation.changerVue("GestionDesCategories.fxml");
	}
	
	@FXML
	private void actionBoutonAnnuler() {
		NavigationControleur.changerVue("AffichageCategories.fxml");
	}
	
	@FXML
	private void actionBoutonRenommer() {
		try {
			ChoixRenommerCategorieControleur.renommerCategorieSelectionnee(entreeNomCategorie.getText());
			NavigationControleur.changerVue("AffichageCategories.fxml");
		} catch (IllegalArgumentException e) {
			AlerteControleur.autreAlerte(e.getMessage(),
										 TITRE_ALERTE, AlertType.ERROR);
		}
	}
	
}
