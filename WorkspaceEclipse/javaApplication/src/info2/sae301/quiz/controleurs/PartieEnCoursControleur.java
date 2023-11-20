/*
 * PartieEnCours.java			        		            		12 nov. 2023
 * IUT de Rodez, pas de copyright, ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.PartieEnCours;
import info2.sae301.quiz.modeles.Question;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * Contrôleur FXML de la vue PartieEnCours qui permet d'afficher 
 * les questions et d'y répondre
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class PartieEnCoursControleur {

	/** Titre de l'aide de PartieEnCours */
	private static final String AIDE_TITRE = "PARTIE EN COURS";
	
	/** Texte de l'aide de PartieEnCours */
	private static final String AIDE_TEXTE
	= """
	  La partie en cours se compose d’une suite de questions posées. À 
	  chacune d’elles, il est demandé au joueur de sélectionner une seule
	  réponse parmi celles proposées.
	  
	  Le niveau de difficulté est affiché pour chacune de ces questions,
	  afin de renseigner davantage l’utilisateur.
	  
	  L’utilisateur peut passer la question si aucune réponse n’est
	  sélectionnée, au contraire, il validera la question avec la réponse
	  sélectionnée. Il peut également retourner sur les questions
	  précédentes via le bouton précédent.
	  """;
	
	/** Message de confirmation d'abandon de partie */
	private static final String MESSAGE_ALERTE_QUITTER =
			"Vous êtes sur le point de quitter la partie, si vous confirmez "
			+ "votre choix vous perdrez votre progression,"
			+ "voulez-vous continuer ?";

	/** Titre de l'alerte pour quitter la partie */
	private static final String TITRE_ALERTE_QUITTER = "Quitter partie";
	
	/** Label de l'intitule de la question */
	@FXML
	private Label intituleQuestion;
	
	/** Label montrant la difficulté de la question
	 * - facile
	 * - moyenne
	 * - difficile
	 */
	@FXML
	private Label labelDifficulte;
	
	/** Label montrant le numéro de la question courante
	 * sur le nombre de question de la série 
	 * (exemple : 1/20, 2/20, ... */
	@FXML
	private Label numQuestion;
	
	/**
	 * Conteneur où sera affiché l'intitulé de la question
	 * et ses réponses
	 */
	@FXML
	private VBox vBoxQuestionReponses;
	
	/**
	 * Bouton permettant de revoir la question
	 * précedente, et de modifier la réponse saisie
	 */
	@FXML
	private Button boutonPrecedent;
	
	/**
	 * Bouton permettant de valider sa réponse
	 * et de passer à la question suivante.
	 */
	@FXML
	private Button boutonValider;
	
	/**
	 * Instance des informations de la partie actuelle,
	 * les paramètres de la partie et les réponses de
	 * l'utilisateur seront stocké ici.
	 */
	private PartieEnCours partieCourante;
	
	/**
	 * Contient les informations nécessaire 
	 * de la questions courante à afficher :
	 * intitulé de la question, ses réponses etc.
	 */
	private Question questionCourante;
	
	/**
	 * Liste de Radio qui contient toutes les réponses
	 * de la question courante.
	 */
	private ArrayList<RadioButton> touteslesRadioReponses;
	
	/**
	 * Méthode d'initialisation de la vue.
	 */
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
	
	/**
	 * Abandonne la partie, retour au Menu Principal
	 * la progression de la partie sera perdu.
	 */
	@FXML
	private void actionBoutonAbandonner() {
		if (AlerteControleur.alerteConfirmation(
				MESSAGE_ALERTE_QUITTER,TITRE_ALERTE_QUITTER)) {
			
			// remplace l'ancienne partieEnCours
			Quiz.partieCourante = new PartieEnCours();
			NavigationControleur.changerVue("MenuPrincipal.fxml");
		}
	}
	
	/**
	 * Permet de revoir la question précédente
	 * et de mofidier la réponse.
	 */
	@FXML
	private void actionBoutonPrecedent() {
		partieCourante.retourQuestionPrecedente();
		NavigationControleur.changerVue("PartieEnCours.fxml");
	}
	
	/**
	 * Permet de valider la question
	 * et de passer à la question suivante.
	 */
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
