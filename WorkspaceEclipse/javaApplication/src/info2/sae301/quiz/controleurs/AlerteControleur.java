/*
 * AlerteControleur.java									        11 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	public static boolean alerteConfirmation(String messageAlerte, String titreFenetre) {
		Alert boiteDialogue;
		Optional<ButtonType> option;
		
		boiteDialogue = new Alert(AlertType.CONFIRMATION);
		boiteDialogue.setTitle(titreFenetre);
		boiteDialogue.setHeaderText(messageAlerte);
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
	public static void autreAlerte(String messageAlerte, String titreFenetre, AlertType typeAlerte) {
		Alert boiteDialogue;
		
		boiteDialogue = new Alert(typeAlerte);
		boiteDialogue.setTitle(titreFenetre);
		boiteDialogue.setHeaderText(messageAlerte);
		boiteDialogue.showAndWait();
	}
}
