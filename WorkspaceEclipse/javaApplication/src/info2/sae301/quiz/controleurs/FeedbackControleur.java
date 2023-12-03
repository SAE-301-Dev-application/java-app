/*
 * NouvellePartie.java							                    17 nov. 2023
 * IUT de Rodez, pas de copyright, ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.PartieEnCours;
import info2.sae301.quiz.modeles.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Contrôleur FXML de la vue Feedback.FXML permettant 
 * une conclusion relative aux réponses aux questions.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class FeedbackControleur {
	
	/** Titre de la pop-up d'aide concernant les résultats */
	private static final String AIDE_TITRE = "LE FEEDBACK";

	
	/** Texte de l'aide */
	private static final String AIDE_TEXTE
	= """
	  Section subsidiaire de celle des résultats, la section de feedback permet un résumé 
	  davantage approfondi au sujet des réponses apportées par le joueur aux questions posées : 

	  La question posée est rappelée, suivie des réponses donnée et attendue et, 
	  dans le cas d’une mauvaise réponse, un message d’explication au sujet de la réponse attendue.
	  """;
	
	/** Instance de la partie en cours. */
	private static PartieEnCours partieCourante;
	
	/** Box verticale d'affichage des feedbacks. */
	@FXML
	private VBox affichageFeedback;
	
	/** Bouton de redirection vers le menu principal. */
	@FXML
	private Button boutonRetour;
	
	
	/**
	 * Initialisation du nombre de questions, de la difficulté et 
	 * des catégories existantes à sélectionner.
	 */
	@FXML
	private void initialize() {
		
		/* Récupération du jeu et de la partie courante. */
		partieCourante = Quiz.partieCourante;
		
		for (int indiceQuestion = 0; 
			 indiceQuestion 
			 	< this.partieCourante.getQuestionsProposees().size(); 
			 indiceQuestion++) {
			
			System.out.println(this.partieCourante.getQuestionsProposees().get(indiceQuestion).getIntitule());
			this.genererFeedback(indiceQuestion);
			
		}
		
	}
	
	
	private void genererFeedback(int indiceQuestionCourante) {
		final String STATUT_QUESTION_REUSSIE
		= "Réussie";
		
		final String STATUT_QUESTION_RATEE
		= "Ratée";
		
		final String STATUT_QUESTION_REUSSIE_CLASSE
		= "statut-reussi";
		
		final String STATUT_QUESTION_RATEE_CLASSE
		= "statut-rate";
		
		final String CHEMIN_RELATIF_IMAGES
		= "../images/";
		
		final String NOM_FICHIER_COCHE
		= "coche.png";
		
		final String NOM_FICHIER_CROIX
		= "croix.png";
		
		final String REPONSE_UTILISATEUR_VIDE_REMPLACEMENT
		= "[ Question passée ]";
		
		Question questionCourante;
		
		VBox feedbackConteneur;
		
		HBox intituleStatutConteneur,
			 conteneurReponseFausse,
			 conteneurReponseVraie;
		
		Label intituleQuestion,
			  statutQuestion,
			  reponseFausse,
			  reponseVraie;
		
		ImageView iconeReponseFausse,
				  iconeReponseVraie;
		
		Image sourceIconeReponseFausse,
			  sourceIconeReponseVraie;
		
		String reponseQuestionUtilisateur,
			   texteStatutQuestion,
			   classeStatutQuestion;
		
		boolean questionReussie;
		
		questionCourante 
		= partieCourante
			  .getQuestionsProposees()
			  .get(indiceQuestionCourante);
		
		reponseQuestionUtilisateur
		= partieCourante
			  .getReponsesUtilisateur()
			  .get(indiceQuestionCourante);
		
		if (reponseQuestionUtilisateur.length() == 0) {
			reponseQuestionUtilisateur = REPONSE_UTILISATEUR_VIDE_REMPLACEMENT;
		}
		
		questionReussie 
		= questionCourante.verifierReponse(reponseQuestionUtilisateur);
		
		texteStatutQuestion = questionReussie
			? STATUT_QUESTION_REUSSIE
			: STATUT_QUESTION_RATEE;
		
		classeStatutQuestion = questionReussie
			? STATUT_QUESTION_REUSSIE_CLASSE
			: STATUT_QUESTION_RATEE_CLASSE;
		
		sourceIconeReponseFausse 
		= new Image(this.getClass()
						.getResource(CHEMIN_RELATIF_IMAGES 
								     + NOM_FICHIER_CROIX).toString());
		
		sourceIconeReponseVraie
		= new Image(this.getClass()
						.getResource(CHEMIN_RELATIF_IMAGES 
								     + NOM_FICHIER_COCHE).toString());
		
		intituleQuestion = new Label();
		intituleQuestion.getStyleClass().add("intitule-question");
		intituleQuestion.getStyleClass().add("longueur-majeure");
		intituleQuestion.setText(questionCourante.getIntitule());
		
		statutQuestion = new Label();
		statutQuestion.getStyleClass().add("question-statut");
		statutQuestion.getStyleClass().add("centrer");
		statutQuestion.getStyleClass().add(classeStatutQuestion);
		statutQuestion.setText(texteStatutQuestion);
		
		intituleStatutConteneur = new HBox();
		intituleStatutConteneur.getStyleClass().add("feedback-conteneur");
		intituleStatutConteneur.getStyleClass().add("centrer");
		intituleStatutConteneur.getChildren().add(intituleQuestion);
		intituleStatutConteneur.getChildren().add(statutQuestion);
		
		feedbackConteneur = new VBox();
		feedbackConteneur.getStyleClass().add("feedback-conteneur");
		feedbackConteneur.getChildren().add(intituleStatutConteneur);
		
		if (!questionReussie) {
			iconeReponseFausse = new ImageView();
			iconeReponseFausse.setPreserveRatio(true);
			iconeReponseFausse.setFitWidth(32);
			iconeReponseFausse.setImage(sourceIconeReponseFausse);
			
			reponseFausse = new Label();
			reponseFausse.getStyleClass().add("reponse-fausse");
			reponseFausse.setText(reponseQuestionUtilisateur);
			
			conteneurReponseFausse = new HBox();
			conteneurReponseFausse.getStyleClass().add("longueur-majeure");
			conteneurReponseFausse.getChildren().add(iconeReponseFausse);
			conteneurReponseFausse.getChildren().add(reponseFausse);
			
			feedbackConteneur.getChildren().add(conteneurReponseFausse);
		}
		
		iconeReponseVraie = new ImageView();
		iconeReponseVraie.setPreserveRatio(true);
		iconeReponseVraie.setFitWidth(32);
		iconeReponseVraie.setImage(sourceIconeReponseVraie);
		
		reponseVraie = new Label();
		reponseVraie.getStyleClass().add("reponse-vraie");
		reponseVraie.setText(questionCourante.getReponseJuste());
		
		conteneurReponseVraie = new HBox();
		conteneurReponseVraie.getStyleClass().add("longueur-majeure");
		conteneurReponseVraie.getChildren().add(iconeReponseVraie);
		conteneurReponseVraie.getChildren().add(reponseVraie);
		
		feedbackConteneur.getChildren().add(conteneurReponseVraie);
		
		this.affichageFeedback.getChildren().add(feedbackConteneur);
	}
	
	
	/**
	 * Affichage d'une pop-up d'aide concernant le paramétrage de 
	 * la partie.
	 */
	@FXML
	private void actionBoutonAide() {
		AlerteControleur.aide(AIDE_TITRE, AIDE_TEXTE);
	}
	
	
	/**
	 * Retour vers la vue MenuPrincipal.
	 */
	@FXML
	private void actionBoutonRetour() {
		NavigationControleur.changerVue("ResultatsPartie.fxml");
	}
	
}