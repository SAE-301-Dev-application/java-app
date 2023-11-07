/*
 * Quiz.java									                     06 oct.2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz;

import java.io.IOException;

import info2.sae301.quiz.controleurs.NavigationControleur;
import info2.sae301.quiz.modeles.Jeu;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

/**
 * Application JavaFX de quiz pour les étudiants.
 * 
 * @author Loïc Faugières
 * @author Simon Guiraud
 * @author Florian Fabre
 * @author Samuel Lacam
 * @author Jonathan Guil
 */
public class Quiz extends Application {
	
	/**
	 * Instance de jeu permettant de stocker les questions et catégories.
	 * Pas d'accès via un getter ici car il faut pouvoir modifier directement
	 * dans l'instance les valeurs via les setters et méthodes.
	 * */
	public static Jeu jeu;
	
	/**
	 * Chargement des vues et affichage du menu principal.
	 * 
	 * @param primaryStage Stage principal créé par JavaFX.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("vues/MenuPrincipal.fxml"));
			
			Scene scene = new Scene(root, 800, 600);
			NavigationControleur.setSceneCourante(scene);
			
			primaryStage.setTitle("Jeu de Quiz");
			// primaryStage.getIcons().add(new Image("info2/sae301/quiz/vues/JeuDeQuiz.png"));
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Lancement du jeu de quiz et création d'une instance de Jeu permettant
	 * le stockage de paramètres.
	 * 
	 * @param args Paramètre inutilisé.
	 */
	public static void main(String[] args) {
		jeu = new Jeu();
		launch(args);
	}
}
