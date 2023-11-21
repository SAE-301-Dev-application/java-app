/*
 * Quiz.java									                     06 oct.2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz;

import info2.sae301.quiz.controleurs.AlerteControleur;
import info2.sae301.quiz.controleurs.NavigationControleur;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.PartieEnCours;
import info2.sae301.quiz.serialisation.Serialisation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
// import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

/**
 * Application JavaFX de quiz pour les étudiants.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class Quiz extends Application {
	
	/**
	 * Instance de Jeu permettant de stocker les questions et catégories.
	 * Pas d'accès via un getter ici car il faut pouvoir modifier directement
	 * dans l'instance les valeurs via les setters et méthodes.
	 * */
	public static Jeu jeu;
	
	/**
	 * Instance de PartieEnCours permettant de stocker les paramètres de la
	 * partie démarrée ainsi que la question courante, les réponses aux questions
	 * et l'ordre des réponses de chaque question proposée.
	 * */
	public static PartieEnCours partieCourante;
	
	
	/**
	 * Assure que la fermeture de l'application via Windows fera la même action
	 * que la fermeture via le bouton dans le menu principal.
	 * 
	 * @param stageCourant Le stage sur lequel définir l'évènement.
	 */
	public static void processusFermetureApp(Stage stageCourant) {
		stageCourant.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				if (!fermerApplication()) {
					event.consume();
				}
			}
		});
	}
	
	
	/**
	 * Fermeture propre de l'application (avec sérialisation) lorsque l'utilisateur
	 * la ferme via la croix de la fenêtre Windows ou le bouton quitter de l'app.
	 */
	public static boolean fermerApplication() {
		final String QUITTER_TITRE = "QUITTER LE JEU";
		final String QUITTER_TEXTE = "Vous êtes sur le point de quitter le jeu."
				                     + "\nÊtes-vous sûr(e) de vouloir faire cela ?"
				                     + "\n\nVos données locales seront sauvegardées"
				                     + " automatiquement.";
		
		boolean confirmationFermeture
		= AlerteControleur.alerteConfirmation(QUITTER_TEXTE, QUITTER_TITRE);
		
		if (confirmationFermeture) {
			Serialisation.serialiser(jeu, Serialisation.NOM_FICHIER_SAUVEGARDE);
			Platform.exit();
		}
		return confirmationFermeture;
	}
	
	
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
			
			processusFermetureApp(primaryStage);
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Erreur Quiz#start(Stage) : " + e.getMessage());
		}
	}
	
	/**
	 * Lancement du jeu de quiz et création d'une instance de Jeu permettant
	 * le stockage de paramètres.
	 * 
	 * @param args Paramètre inutilisé.
	 */
	public static void main(String[] args) {
		jeu = Serialisation.deserialiser(Serialisation.NOM_FICHIER_SAUVEGARDE);
		partieCourante = new PartieEnCours();
		launch(args);
	}
}
