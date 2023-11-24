/*
 * RenommerCategoriesControleur.java                                10 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

/**
 * Contrôleur FXML de la vue RenommerCategories permettant de renommer
 * une catégorie sélectionnée.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class RenommerCategoriesControleur {
	
	private static final String TITRE_ALERTE = "Erreur de renommage";
	
	@FXML
	private TextField entreeNomCategorie;
	
	@FXML
	private void initialize() {
		entreeNomCategorie
		.setText(ChoixRenommerCategorieControleur.
				 getIntituleCategorieSelectionnee());
	}
	
	/**
	 * Redirection vers la pop-up d'aide pour renommer des questions
	 */
	@FXML
	private void actionBoutonAide() {
		AlerteControleur.aide(AffichageCategoriesControleur.AIDE_TITRE, 
							  AffichageCategoriesControleur.AIDE_TEXTE);
	}
	
	/**
	 * Redirection vers la vue AffichageCategories.fxml
	 */
	@FXML
	private void actionBoutonAnnuler() {
		NavigationControleur.changerVue("AffichageCategories.fxml");
	}
	
	/**
	 * Clic sur le bouton renommer, renomme la catégorie et 
	 * change de vue.
	 */
	@FXML
	private void actionBoutonRenommer() {
		try {
			ChoixRenommerCategorieControleur
			.renommerCategorieSelectionnee(entreeNomCategorie.getText());
			NavigationControleur.changerVue("AffichageCategories.fxml");
		} catch (IllegalArgumentException e) {
			AlerteControleur.autreAlerte(e.getMessage(),
										 TITRE_ALERTE, AlertType.ERROR);
		}
	}
	
}
