/*
 * NouvellePartie.java							                    17 nov. 2023
 * IUT de Rodez, pas de copyright, ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.exceptions.AucuneQuestionCorrespondanteException;
import info2.sae301.quiz.exceptions.NbInsuffisantQuestionsException;
import info2.sae301.quiz.exceptions.DifficulteInvalideException;
import info2.sae301.quiz.exceptions.NombreQuestionsInvalideException;

import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.ParametresPartie;
import info2.sae301.quiz.modeles.PartieEnCours;
import info2.sae301.quiz.modeles.Question;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

/**
 * Contrôleur FXML de la vue ResultatsPartie.fxml permettant de 
 * la conclusion de la partie en cours en affichant les statistiques
 * liées.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class ResultatsPartieControleur {
	
	/** Titre d'erreur pour l'alerte de la création partie */
	private static final String AIDE_TITRE = "LES RÉSULTATS";

	/** Texte de l'aide */
	private static final String AIDE_TEXTE
	= """
	  À la suite de la validation de la dernière question du quiz, la page des résultats s’affiche, quatre informations sont transmises au joueur :

	  Le pourcentage de réussite représente une sorte de notation sur la réussite du joueur.
	
	  Le nombre de questions réussies et ratées.
	
	  Un message de feedback afin de conclure la partie avec un message relatif aux résultats obtenus.
	  """;
	
	/** Instance du jeu. */
	private static Jeu jeu = Quiz.jeu;
	
	/** Instance de la partie en cours. */
	private static PartieEnCours partieCourante = Quiz.partieCourante;
	
	/**
	 * @return Pourcentage de réussite du joueur 
	 * 		   pour la partie courante
	 */
	private static double getPourcentageReussite() {
		final ArrayList<Question> QUESTIONS_PROPOSEES
		= partieCourante.getQuestionsProposees();
		
		final ArrayList<String> REPONSES_QUESTIONS
		= partieCourante.getReponsesUtilisateur();
		
		final int NOMBRE_QUESTIONS 
		= QUESTIONS_PROPOSEES.size();
		
		int nombreQuestionsReussies;
		
		nombreQuestionsReussies = 0;
		
		for (int indiceQuestion = 0; 
			 indiceQuestion < NOMBRE_QUESTIONS; 
			 indiceQuestion++) {
			
			if (QUESTIONS_PROPOSEES.get(indiceQuestion)
					.verifierReponse(REPONSES_QUESTIONS.get(indiceQuestion))) {
				
				nombreQuestionsReussies++;
				
			}
				
		}
		
		return nombreQuestionsReussies / NOMBRE_QUESTIONS;
	}
	
	/** Label d'affichage du pourcentage de réussite. */
	@FXML
	private Label pourcentageReussite;
	
	/** Label d'affichage du nombre de questions réussies. */
	@FXML
	private Label nombreQuestionsReussies;
	
	/** Label d'affichage du nombre de questions ratées. */
	@FXML
	private Label nombreQuestionsRatees;
	
	/** Label d'affichage du message personnalisé de conclusion. */
	@FXML
	private Label message;
	
	
	/**
	 * Initialisation du nombre de questions, de la difficulté et 
	 * des catégories existantes à sélectionner.
	 */
	@FXML
	private void initialize() {

		this.pourcentageReussite.setText(getPourcentageReussite() + "%");
		
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
	private void actionBoutonMenuPrincipal() {
		NavigationControleur.changerVue("MenuPrincipal.fxml");
	}
	
	
	/**
	 * Vérification que les paramètres soient valides et lancement 
	 * d'une partie avec ces paramètres.
	 */
	@FXML
	private void actionBoutonFeedback() {
		//
	}
}