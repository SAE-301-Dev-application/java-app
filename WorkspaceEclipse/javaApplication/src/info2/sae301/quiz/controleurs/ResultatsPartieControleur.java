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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

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
	
	
	/** Message de conclusion affiché si le taux de réussite est 
	 *  inférieur à 31%. */
	private static final String MESSAGE_CONCLUSION_RATE
	= """
	  Vous n'avez pas réussi à répondre juste à une des questions posées. Réessayez encore, %s !
	  """;
	
	
	/** Message de conclusion affiché si le taux de réussite est 
	 *  inférieur à 31%. */
	private static final String MESSAGE_CONCLUSION_MAUVAIS
	= """
	  Peu de réponses justes... Vous pouvez mieux faire, %s !
	  """;
	
	
	/** Message de conclusion affiché si le taux de réussite est 
	 *  compris entre 31% et 60%. */
	private static final String MESSAGE_CONCLUSION_MOYEN
	= """
	  Des résultats moyens, vous pouvez mieux faire, %s !
	  """;
	
	
	/** Message de conclusion affiché si le taux de réussite est 
	 *  compris entre 61% et 89%. */
	private static final String MESSAGE_CONCLUSION_BON
	= """
	  De bons résultats malgré quelques erreurs, félicitations, %s !
	  """;
	
	
	/** Message de conclusion affiché si le taux de réussite est 
	 *  supérieur à 89%. */
	private static final String MESSAGE_CONCLUSION_PARFAIT
	= """
	  Très bons résultats, félicitations, %s !
	  """;
	
	
	/** Instance courante du jeu (application). */
	private static Jeu jeu;
	
	
	/** Instance de la partie en cours. */
	private static PartieEnCours partieCourante;
	
	
	/**
	 * @return Le nombre de questions du quiz courant
	 */
	public static int getNombreQuestions() {
		return partieCourante.getQuestionsProposees().size();
	}
	
	
	/**
	 * @return Le nombre de questions correctement répondues par
	 * 		   le joueur
	 */
	public static int getNombreQuestionsReussies() {
		final ArrayList<String> REPONSES_QUESTIONS
		= partieCourante.getReponsesUtilisateur();
		
		final ArrayList<Question> QUESTIONS_PROPOSEES
		= partieCourante.getQuestionsProposees();
		
		final int NOMBRE_QUESTIONS = getNombreQuestions();
		
		int nombreQuestionsReussies;
		
		String reponseDonnee;
		
		Question questionCourante;
		
		nombreQuestionsReussies = 0;
		
		for (int indiceQuestion = 0; 
			 indiceQuestion < NOMBRE_QUESTIONS; 
			 indiceQuestion++) {
			
			questionCourante = QUESTIONS_PROPOSEES.get(indiceQuestion);
			reponseDonnee = REPONSES_QUESTIONS.get(indiceQuestion);
			
			if (questionCourante.verifierReponse(reponseDonnee)) {
				
				nombreQuestionsReussies++;
				
			}
				
		}
		
		return nombreQuestionsReussies;
	}
	
	
	public static int getNombreQuestionsRatees() {
		return getNombreQuestions() - getNombreQuestionsReussies();
	}
	

	/**
	 * @return Pourcentage de réussite du joueur 
	 * 		   pour la partie courante
	 */
	public static double getPourcentageReussite() {
		return (double) getNombreQuestionsReussies() / getNombreQuestions() * 100.;
	}
	
	
	/**
	 * @return Le message de conclusion lié au taux de réussite
	 */
	public static String getMessageConclusion() {
		final double POURCENTAGE_REUSSITE = getPourcentageReussite();
		
		String messageConclusion;
		
		if (POURCENTAGE_REUSSITE < 1.) {
			messageConclusion = MESSAGE_CONCLUSION_RATE;
		} else if (POURCENTAGE_REUSSITE >= 1. && POURCENTAGE_REUSSITE <= 29.) {
			messageConclusion = MESSAGE_CONCLUSION_MAUVAIS;
		} else if (POURCENTAGE_REUSSITE >= 30. && POURCENTAGE_REUSSITE <= 59.) {
			messageConclusion = MESSAGE_CONCLUSION_MOYEN;
		} else if (POURCENTAGE_REUSSITE >= 60. && POURCENTAGE_REUSSITE <= 89.) {
			messageConclusion = MESSAGE_CONCLUSION_BON;
		} else {
			messageConclusion = MESSAGE_CONCLUSION_PARFAIT;
		}
		
		return String.format(messageConclusion, jeu.getPseudo());
	}
	
	
	/**
	 * Gestion du pluriel, ajout du 's' à la fin des mots fournis 
	 * si le nombre est supérieur strictement à 1.
	 * 
	 * @param phrase
	 * @param nombre
	 * @return La phrase avec gestion du pluriel
	 */
	private static String gestionPluriel(String phrase, int nombre) {
		String terminaison;
		terminaison = nombre > 1
				? "s"
				: "";
		
		return String.format(phrase, terminaison, terminaison);
	}
	
	
	/** Label d'affichage du pourcentage de réussite. */
	@FXML
	private Label pourcentageReussite;
	
	
	/** Label d'affichage du nombre de questions réussies. */
	@FXML
	private Label nombreQuestionsReussies;
	
	
	@FXML
	private Label labelSecondaireNombreQuestionsReussies;
	
	
	/** Label d'affichage du nombre de questions ratées. */
	@FXML
	private Label nombreQuestionsRatees;
	
	
	@FXML
	private Label labelSecondaireNombreQuestionsRatees;
	
	
	/** Label d'affichage du message personnalisé de conclusion. */
	@FXML
	private Text message;
	
	
	/** Bouton de redirection vers le feedback de la partie. */
	@FXML
	private Button boutonFeedback;

	
	/** Bouton de redirection vers le menu principal. */
	@FXML
	private Button boutonMenuPrincipal;
	
	
	/**
	 * Initialisation du nombre de questions, de la difficulté et 
	 * des catégories existantes à sélectionner.
	 */
	@FXML
	private void initialize() {
		
		/* Récupération du jeu et de la partie courante. */
		jeu = Quiz.jeu;
		partieCourante = Quiz.partieCourante;

		/* Affichage du pourcentage de réussite. */
		this.pourcentageReussite
		    .setText(String.format("%d%%", (int) getPourcentageReussite()));
		
		/* Affichage du nombre de questions réussies. */
		this.nombreQuestionsReussies
		    .setText(String.valueOf(getNombreQuestionsReussies()));
		
		this.gestionPlurielQuestionsReussies();
		
		/* Affichage du nombre de questions ratées. */
		this.nombreQuestionsRatees
	    	.setText(String.valueOf(getNombreQuestionsRatees()));
		
		this.gestionPlurielQuestionsRatees();
		
		/* Affichage du message de conclusion de partie. */
		this.message.setText(getMessageConclusion());
		
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
		Quiz.partieCourante = new PartieEnCours(); 
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
	
	
	/**
	 * Gestion du pluriel pour le label secondaire du 
	 * nombre de questions réussies.
	 */
	private void gestionPlurielQuestionsReussies() {
		String contenuLabel;
		contenuLabel 
		= gestionPluriel("question%s réussie%s", 
						 getNombreQuestionsReussies());
		
		this.labelSecondaireNombreQuestionsReussies
		    .setText(contenuLabel);
	}
	
	
	/**
	 * Gestion du pluriel pour le label secondaire du 
	 * nombre de questions ratées.
	 */
	private void gestionPlurielQuestionsRatees() {
		String contenuLabel;
		contenuLabel 
		= gestionPluriel("question%s ratée%s", 
						 getNombreQuestionsRatees());
		
		this.labelSecondaireNombreQuestionsRatees
		    .setText(contenuLabel);
	}
}