/*
 * AlerteControleur.java							     11 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;

/**
 * Gestion des fenêtres d'erreur et d'information.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class AlerteControleur {

	/**
	 * Fenêtre de confirmation avec un bouton OK et Annuler.
	 * 
	 * @param messageAlerte Le message de la fenêtre.
	 * @param titreFenetre Le titre de la fenêtre.
	 * @return La réponse positive (true) ou négative (false).
	 */
	public static boolean alerteConfirmation(String messageAlerte,
			                                 String titreFenetre) {
		Alert boiteDialogue;
		
		Optional<ButtonType> option;
		
		Stage stage;
		
		boiteDialogue = new Alert(AlertType.CONFIRMATION);
		boiteDialogue.setTitle(titreFenetre);
		boiteDialogue.setHeaderText(messageAlerte);
		
		stage = (Stage) boiteDialogue.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("info2/sae301/quiz/images/quiz.png"));
		
		option = boiteDialogue.showAndWait();
		
		return option.get() == ButtonType.OK;
	}
	
	
	/**
	 * Fenêtre d'alerte avec un message et un titre à afficher.
	 * 
	 * @param messageAlerte Le message de la fenêtre.
	 * @param titreFenetre Le titre de la fenêtre.
	 * @param typeAlerte Le type d'alerte.
	 */
	public static void autreAlerte(String messageAlerte, String titreFenetre,
			                       AlertType typeAlerte) {
		Alert boiteDialogue;
		
		Stage stage;
		
		boiteDialogue = new Alert(typeAlerte);
		boiteDialogue.setTitle(titreFenetre);
		boiteDialogue.setHeaderText(messageAlerte);
		
		stage = (Stage) boiteDialogue.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("info2/sae301/quiz/images/quiz.png"));
		
		boiteDialogue.showAndWait();
	}
	
	
	/**
	 * Fenêtre d'alerte afin d'afficher une fenêtre d'aide
	 * 
	 * @param titre Le titre de la fenêtre.
	 * @param texte le texte d'aide à mettre dans la fenêtre
	 */
	public static void aide(String titre, String texte) {
		Alert boiteDialogue;
		
		Stage stage;
		
		boiteDialogue = new Alert(AlertType.INFORMATION);
		boiteDialogue.setTitle(titre);
		boiteDialogue.setHeaderText(texte);
		
		stage = (Stage) boiteDialogue.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("info2/sae301/quiz/images/quiz.png"));
		
		boiteDialogue.showAndWait();
	}
}
