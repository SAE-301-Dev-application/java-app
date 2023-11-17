package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.PartieEnCours;
import info2.sae301.quiz.modeles.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class PartieEnCoursControleur {

	private static final String AIDE_TITRE = "PARTIE EN COURS";
	
	private static final String AIDE_TEXTE
	= """
	  La partie en cours se compose d’une suite de questions posées. À 
	  chacune d’elles, il est demandé au joueur de sélectionner une seule
	  réponse parmi celles proposées.
	  
	  Le niveau de difficulté est affiché pour chacune de ces questions,
	  afin de renseigner davantage l’utilisateur.
	  
	  L’utilisateur peut passer la question si aucune réponse n’est
	  sélectionnée, à contrario, il validera la question avec la réponse
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
	private Label labelDifficulte;
	
	@FXML
	private VBox vBoxQuestionReponses;
	
	private PartieEnCours partieCourante;
	
	private Question questionCourante;
	
	@FXML
	private void initialize() {
		//Initialisation des données
		partieCourante = Quiz.partieCourante;
		questionCourante = partieCourante.getQuestionsProposees()
				.get(partieCourante.getIndiceQuestionCourante());
		
		//Initialisation de la vue
		initDifficulteQuestion();
		initQuestionReponse();
	}
	 

	/**
	 * Affichage d'une pop-up d'aide concernant les réponses à une question.
	 */
	@FXML
	private void actionBoutonAide() {
		AlerteControleur.aide(AIDE_TITRE, AIDE_TEXTE);
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
	private void actionBoutonValider() {
		
	}
	
	/**
	 * Initialisation de la vue qui affiche la
	 * difficulte de la question avec sa couleur
	 * représentative.
	 */
	private void initDifficulteQuestion() {
		switch (questionCourante.getDifficulte()) {
		//Si question Indifférente
		case 0:
			labelDifficulte.getStyleClass().add("questionIndifferente");
			labelDifficulte.setText("Indifférente");
			break;
			
		//Si question Facile
		case 1:
			labelDifficulte.getStyleClass().add("questionFacile");
			labelDifficulte.setText("Facile");
			break;
			
		//Si question Moyenne
		case 2:
			labelDifficulte.getStyleClass().add("questionMoyenne");
			labelDifficulte.setText("Moyenne");
			break;		
			
		//Si question Difficile
		case 3:
			labelDifficulte.getStyleClass().add("questionDifficile");
			labelDifficulte.setText("Difficile");
			break;
		
		default:
			throw new IllegalArgumentException
				("difficulte invalide, non compris entre 0 et 3");
		}
	}
	
	/**
	 * Initialisation de la vue qui affiche
	 * la questions courante et
	 * les questions mélangées.
	 */
	private void initQuestionReponse() {
		intituleQuestion.setText(questionCourante.getIntitule());
		
		ArrayList<String> reponsesMelange = questionCourante.melangerReponses();
		for (String reponse : reponsesMelange) {
			Label afficherReponse = new Label(reponse);
			vBoxQuestionReponses.getChildren().add(afficherReponse);
		}
	}
	
}
