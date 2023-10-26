/*
 * Quiz.java									                     06 oct.2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

/**
 * Application JavaFX de quiz pour les étudiants.
 * 
 * @author Loïc Faugières
 * @author Simon Guiraud
 * @author Florien Fabre
 * @author Samuel Lacam
 * @author Jonathan Guild
 */
public class Quiz extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("vues/MenuPrincipal.fxml"));
			
			primaryStage.show();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}