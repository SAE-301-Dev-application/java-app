/*
 * MenuPrincipalControleur.java							            10 nov. 2023
 * IUT de Rodez, pas de copyright, ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;

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
	
	/** Titre de l'aide de la page du menu principal  */
	private final String AIDE_TITRE = "COMMENT JOUER ?";
	
	/** Texte de l'aide du menu principal */
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
	private Label pseudo;

	
	@FXML
	private void initialize() {
		pseudo.setText(jeu.getPseudo());
	}
	
	
	@FXML
	private void actionBoutonAide() {
		AlerteControleur.aide(AIDE_TITRE, AIDE_TEXTE);
	}

	
	@FXML
	private void actionBoutonUtilisateur() {
		NavigationControleur.changerVue("GestionPseudo.fxml");
	}
	
	
	@FXML
	private void actionBoutonFerme() {
		Platform.exit();
	}

	
	@FXML
	private void actionBoutonJouer() {
		NavigationControleur.changerVue("NouvellePartie.fxml");
	}

	
	/**
	 * Redirection vers la vue AffichageCategories.fxml
	 */
	@FXML
	private void actionBoutonCategories() {
		NavigationControleur.changerVue("AffichageCategories.fxml");
	}

	
	/**
	 * Redirection vers la vue AffichageQuestions.fxml
	 */
	@FXML
	private void actionBoutonQuestions() {
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