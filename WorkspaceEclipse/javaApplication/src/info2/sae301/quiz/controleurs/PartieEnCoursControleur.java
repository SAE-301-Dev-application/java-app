package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Parsed;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.PartieEnCours;
import info2.sae301.quiz.modeles.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
	private Label numQuestion;
	
	@FXML
	private VBox vBoxQuestionReponses;
	
	@FXML
	private Button boutonPrecedent;
	
	@FXML
	private Button boutonValider;
	
	private PartieEnCours partieCourante;
	
	private Question questionCourante;
	
	private ArrayList<RadioButton> touteslesRadioReponses;
	
	@FXML
	private void initialize() {
		//Initialisation des données
		partieCourante = Quiz.partieCourante;
		questionCourante = partieCourante.getQuestionsProposees()
				.get(partieCourante.getIndiceQuestionCourante());
		
		//Initialisation de la vue
		initLabelNumQuestion();
		initDifficulteQuestion();
		initQuestionReponse();
		initBoutonsPrecedentSuivant();
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
			
			// remplace l'ancienne partieEnCours
			Quiz.partieCourante = new PartieEnCours();
			NavigationControleur.changerVue("MenuPrincipal.fxml");
		}
	}
	
	@FXML
	private void actionBoutonPrecedent() {
		partieCourante.retourQuestionPrecedente();
		NavigationControleur.changerVue("PartieEnCours.fxml");
	}
	
	@FXML
	private void actionBoutonValider() {
		//chaîne vide par défaut si l'utilisateur coche aucune réponses
		String reponseUtilisateur = "";
		for (RadioButton reponse : touteslesRadioReponses) {
			if (reponse.isSelected()) {
				reponseUtilisateur = reponse.getText();
			}
		}
		
		//permet de savoir si on ajoute ou modofie une réponse précédemment donné
		if (partieCourante.getIndiceDerniereQuestionVue()
				== partieCourante.getIndiceQuestionCourante()){
			
			partieCourante.ajouterReponseUtilisateur(reponseUtilisateur);
			partieCourante.setIndiceDerniereQuestionVue(
					partieCourante.getIndiceDerniereQuestionVue() +1);
		} else {
			partieCourante.modifierReponseUtilisateur(reponseUtilisateur);
		}

		System.out.println(partieCourante.getReponsesUtilisateur());
		partieCourante.passerQuestionSuivante();
		NavigationControleur.changerVue("PartieEnCours.fxml");
		
	}
	
	/**
	 * Initialisation de la vue qui affiche la
	 * difficulte de la question avec sa couleur
	 * représentative.
	 */
	private void initDifficulteQuestion() {
		switch (questionCourante.getDifficulte()) {
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
		touteslesRadioReponses = new ArrayList<RadioButton>();
		ToggleGroup radioGroupe = new ToggleGroup();
		
		ArrayList<String> reponsesMelange = questionCourante.melangerReponses();
		for (int i = 0; i < reponsesMelange.size(); i++) {
			RadioButton afficherReponse = new RadioButton(reponsesMelange.get(i));
			if (partieCourante.radioDejaSelectionne(reponsesMelange.get(i))) {
				afficherReponse.setSelected(true);
			}
			afficherReponse.getStyleClass().add("reponse");
			afficherReponse.setId("" + i);
			afficherReponse.setToggleGroup(radioGroupe);
			vBoxQuestionReponses.getChildren().add(afficherReponse);
			touteslesRadioReponses.add(afficherReponse);
		}
	}
	
	
	/**
	 * Rend non visible boutonPrecedent si on est
	 * à la première question de la partie
	 * Rend non visible boutonValider si on est
	 * à la dernière question de la partie
	 */
	private void initBoutonsPrecedentSuivant() {
		System.out.println(partieCourante.getIndiceQuestionCourante());
		System.out.println(partieCourante.getQuestionsProposees().size() -1);
		if (partieCourante.getIndiceQuestionCourante() == 0) {
			boutonPrecedent.setVisible(false);
		}
		
		if (partieCourante.getIndiceQuestionCourante()
				== partieCourante.getQuestionsProposees().size() -1) {
			boutonValider.setVisible(false);
		}
	}
	
	/**
	 * Initialisation du numéro de question actuelle
	 * de la série de question
	 */
	private void initLabelNumQuestion() {
		int numero = partieCourante.getIndiceQuestionCourante() +1;
		int nbQuestionPartie = partieCourante.getQuestionsProposees().size();
		numQuestion.setText(numero + "/" + nbQuestionPartie);
	}
	
}
