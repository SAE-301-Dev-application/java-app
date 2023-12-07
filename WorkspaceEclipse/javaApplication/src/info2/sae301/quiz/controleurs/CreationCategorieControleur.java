/*
 * CreationCategorieControleur.java			        		         9 nov. 2023
 * IUT de Rodez, pas de copyright, ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

/**
 * Contrôleur FXML de la vue CreationCategorie qui permet la création
 * d'une nouvelle catégorie.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class CreationCategorieControleur {
	
	/** String pour le titre de l'alerte */
	private static final String TITRE_ALERTE = "Erreur de création";
	
	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private Jeu jeu = Quiz.jeu;
	
	@FXML
	private TextField nouveauNomCategorie;
	
	
	/**
	 * Permet d'afficher une pop up d'aide concernant la création
	 * des catégories
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
	 * Enregistrer le nouveau nom de la catégorie.
	 */
	@FXML
	private void actionBoutonEnregistrer() {
		try {
			jeu.creerCategorie(nouveauNomCategorie.getText().trim());
			NavigationControleur.changerVue("AffichageCategories.fxml");
		} catch (IllegalArgumentException e) {
			AlerteControleur.autreAlerte(e.getMessage(),
										 TITRE_ALERTE, AlertType.ERROR);
		}
	}	
}
