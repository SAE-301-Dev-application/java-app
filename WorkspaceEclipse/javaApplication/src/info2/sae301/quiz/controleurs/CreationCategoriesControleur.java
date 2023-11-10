/*
 * CreationCategoriesControleur.java			    		         9 nov. 2023
 * IUT de Rodez, pas de copyright, ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

/**
 * Contrôleur FXML de la vue CreationCategories qui permet la création d'une
 * nouvelle catégorie.
 * 
 * @author FAUGIERES Loïc
 * @author GUIL Jonathan
 */
public class CreationCategoriesControleur {
	
	private static final String TITRE_ALERTE = "Erreur de création";
	
	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private Jeu jeu = Quiz.jeu;
	
	@FXML
	private TextField nouveauNomCategorie;
	
	@FXML
	private void boutonAide() {
//		ControleurNavigation.changerVue("GestionDesCategories.fxml");
	}
	
	@FXML
	private void boutonAnnuler() {
		NavigationControleur.changerVue("AffichageCategories.fxml");
	}
	
	@FXML
	private void boutonEnregistrer() {
		try {
			jeu.creerCategorie(nouveauNomCategorie.getText());
			NavigationControleur.changerVue("AffichageCategories.fxml");
		} catch (IllegalArgumentException e) {
			AlerteControleur.autreAlerte(e.getMessage(),
										 TITRE_ALERTE, AlertType.ERROR);
		}
	}
	
}
