/*
 * GestionPseudoControleur.java			        		            15 nov. 2023
 * IUT de Rodez, pas de copyright, ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.exceptions.TaillePseudoInvalideException;
import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

/**
 * Contrôleur FXML de la vue GestionPseudo qui permet la création d'un pseudo
 * pour l'utilisateur.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class GestionPseudoControleur {
	
	private static final String TITRE_ALERTE = "Erreur de pseudonyme";
	
	private static final String AIDE_TITRE = "PSEUDONYME";
	
	private static final String AIDE_TEXTE
	= """
	  Afin de personnaliser l’expérience de l’utilisateur,
	  il est possible de modifier son nom d’utilisateur.

	  Ce nom d’utilisateur sera affiché dans toutes les interfaces
	  montrant des messages à l’égard du joueur.
	  """;
	
	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private Jeu jeu = Quiz.jeu;
	
	@FXML
	private TextField pseudoEntre;
	
	/**
	 * TODO : coder aide
	 */
	@FXML
	private void actionBoutonAide() {
		AlerteControleur.aide(AIDE_TITRE, AIDE_TEXTE);
	}
	
	/**
	 * Redirection vers la vue MenuPrincipal.fxml
	 */
	@FXML
	private void actionBoutonAnnuler() {
		NavigationControleur.changerVue("MenuPrincipal.fxml");
	}
	
	/**
	 * Enregistrer le nouveau pseudonyme d'utilisateur.
	 */
	@FXML
	private void actionBoutonEnregistrer() {
		try {
			jeu.setPseudo(pseudoEntre.getText());
			NavigationControleur.changerVue("MenuPrincipal.fxml");
		} catch (TaillePseudoInvalideException e) {
			AlerteControleur.autreAlerte(e.getMessage(),
										 TITRE_ALERTE, AlertType.ERROR);
		}
	}
	
}
