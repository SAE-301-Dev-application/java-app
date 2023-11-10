/*
 * MenuPrincipalControleur.java							            10 nov. 2023
 * IUT de Rodez, pas de copyright, ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import javafx.fxml.FXML;

/**
 * Contrôleur FXML de la vue MenuPrincipal lancée par défaut lors du
 * démarrage de l'application.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class MenuPrincipalControleur {
	
	private final String AIDE_TITRE = "COMMENT JOUER ?";
	
	private final String AIDE_TEXTE 
	= """
      Bienvenue sur le jeu de quiz !

      Créez des questions, répondez à celles existantes et prenez note du résume des résultats.

      Triez les questions par catégories pour personnaliser votre jeu.

      Vous pouvez également importer localement ou depuis un ordinateur, 
      ou même exporter sur un autre ordinateur des questions et des catégories, au format CSV.
      """;

	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private Jeu jeu = Quiz.jeu;
	
	@FXML
	private void boutonAide() {
		AlerteControleur.aide(AIDE_TITRE, AIDE_TEXTE);
	}
	
	@FXML
	private void boutonUser() {
		//ControleurNavigation.changerVue("GestionNomUtilisateur.fxml");
	}
	
	@FXML
	private void boutonJouer() {
		
	}
	
	@FXML
	private void boutonCategories() {
		NavigationControleur.changerVue("AffichageCategories.fxml");
	}
	
	@FXML
	private void boutonQuestions() {
		NavigationControleur.changerVue("AffichageQuestions.fxml");
	}

	/**
	 * Redirection vers la vue Importation.fxml
	 */
	@FXML
	private void actionBoutonImportation() {
		
	}
	
	/**
	 * Redirection vers la vue Exportation.fxml
	 */
	@FXML
	private void actionBoutonExportation() {
		
	}
	
}