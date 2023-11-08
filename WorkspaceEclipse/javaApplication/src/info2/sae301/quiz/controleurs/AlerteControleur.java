package info2.sae301.quiz.controleurs;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AlerteControleur {

	public static boolean alerteConfirmation(String messageAlerte, String titreFenetre) {
		Alert boiteDialogue;
		Optional<ButtonType> option;
		
		boiteDialogue = new Alert(AlertType.CONFIRMATION);
		boiteDialogue.setTitle(titreFenetre);
		boiteDialogue.setHeaderText(messageAlerte);
		option = boiteDialogue.showAndWait();
		
		return option.get() == ButtonType.OK;
	}
	
	public static void autreAlerte(String messageAlerte, String titreFenetre, AlertType typeAlerte) {
		Alert boiteDialogue;
		boiteDialogue = new Alert(typeAlerte);
		boiteDialogue.setTitle(titreFenetre);
		boiteDialogue.setHeaderText(messageAlerte);
		boiteDialogue.showAndWait();
	}
}
