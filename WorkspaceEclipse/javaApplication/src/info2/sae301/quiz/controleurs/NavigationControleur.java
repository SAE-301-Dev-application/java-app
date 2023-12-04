/*
 * NavigationControleur.java 						                10 nov. 2023
 * IUT de Rodez, pas de copyright, ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Classe permettant la navigation entre les vues de l'application.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class NavigationControleur {
	
	/**
	 * Chemin vers le dossier racine des vues à partir du 
	 * contrôleur courant.
	 */
	private static final String RACINE_VUES = "/info2/sae301/quiz/vues/";

	/**
	 * Scène courante définie.
	 * 
	 * <p>
	 * La scène par défaut est celle définie directement 
	 * par le Main.java au lancement de l'application : 
	 * le menu principal.
	 */
	private static Scene sceneCourante;
	
	/**
	 * Le chemin de la vue courante.
	 */
	private static String vueCourante;
	
	
	/**
	 * (Re)définie directement l'objet de la scène
	 * courante. Cette information est utile lors du 
	 * changement de scène via la méthode de changement
	 * de vue.
	 * @param nouvelleScene
	 */
	public static void setSceneCourante(Scene nouvelleScene) {
		sceneCourante = nouvelleScene;
	}
	
	
	/**
	 * Change de vue vers celle envoyée en 
	 * paramètre.
	 * @param routeVueFXML Nom du fichier FXML de la vue cible
	 */
	public static void changerVue(String routeVueFXML) {
		if (sceneCourante == null) {
			System.out.println("Erreur : aucune scène courante !");
		} else {
			try {
				/*
				System.out.println(NavigationControleur.class.getResource(
								   RACINE_VUES + routeVueFXML));
								   */
				Parent racine;
				racine = FXMLLoader.load(NavigationControleur.class
						              .getResource(RACINE_VUES + routeVueFXML));
				
				sceneCourante.setRoot(racine);
				vueCourante = routeVueFXML;
			} catch (IOException e) {
				System.out.println("ERREUR CHARGEMENT VUE : \n" + e.getMessage());
			}
		}
	}
	
	
	/** @return La scène courante. */
	public static Scene getScene() {
		return sceneCourante;
	}
	
	
	/** @return La vue courante. */
	public static String getVueCourante() {
		return vueCourante;
	}
}
