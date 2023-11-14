package info2.sae301.quiz.controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class PartieEnCoursControlerur {

	private static final String AIDE_TITRE = "PARTIE EN COURS";
	
	private static final String AIDE_TEXTE =
			"""
			La partie en cours se compose d’une suite de questions posées. À 
			chacune d’elles, il est demandé au joueur de sélectionner une seule
			réponse parmi celles proposées.
			
			Le niveau de difficulté est affiché pour chacune de ces questions,
			afin de renseigner davantage l’utilisateur.
			
			L’utilisateur peut passer la question si aucune réponse n’est
			sélectionnée, a contrario, il validera la question avec la réponse
			sélectionnée. Il peut également retourner sur les questions
			précédentes via le bouton précédent.
			""";
	
	private static final String MESSAGE_ALERTE_QUITTER =
			"Vous êtes sur le point de quitter la partie, si vous confirmer "
			+ "votre choix vous perdrez votre progression, voulez-vous continuer ?";

	private static final String TITRE_ALERTE_QUITTER = "Quitter partie";
	
	@FXML
	private Label intituleQuestion;
	
	@FXML
	private ScrollPane layoutAffichageReponse;
	
	@FXML
	private void intialize() {
		
	}
	
	@FXML
	private void actionBoutonAide() {
		AlerteControleur.autreAlerte(AIDE_TEXTE, AIDE_TITRE, AlertType.INFORMATION);
	}
	
	@FXML
	private void actionBoutonAbandonner() {
		if (AlerteControleur.alerteConfirmation(
				MESSAGE_ALERTE_QUITTER,TITRE_ALERTE_QUITTER)) {
			NavigationControleur.changerVue("MenuPrincipal.fxml");
		}
	}
	
	@FXML
	private void actionBoutonPrecedent() {
		

	}
	@FXML
	private void actionBoutonSuivant() {
		
	}
}
