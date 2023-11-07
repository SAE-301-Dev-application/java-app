package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class RenommerCategoriesControleur {
	
	private static final String TITRE_ALERTE = "Erreur de renommage";
	
	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private Jeu jeu = Quiz.jeu;
	
	@FXML
	private TextField entreeNomCategorie;
	
	@FXML
	private void initialize() {
		entreeNomCategorie.setText(jeu.getIntituleCategorieSelectionnee());
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
			jeu.renommerCategorieSelectionnee(entreeNomCategorie.getText());
			NavigationControleur.changerVue("AffichageCategories.fxml");
		} catch (IllegalArgumentException e) {
			AlerteControleur.autreAlerte(e.getMessage(),
										 TITRE_ALERTE, AlertType.ERROR);
		}
	}
	
}
