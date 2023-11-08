/*
 * ChoixEditerQuestionControleur.java							     7 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.Question;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Contrôleur FXML de la vue ChoixEditerQuestion qui affiche la liste des
 * questions sous forme de boutons.
 */
public class ChoixEditionQuestionControleur {
	
	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private static Jeu jeu = Quiz.jeu;
	
	/** Intitulé de la question à renommer qui a été sélectionnée. */
	private static String intituleQuestionSelectionnee;
	
	@FXML
	private VBox vBoxQuestions;
	
	@FXML
	private Button boutonPrecedent;
	
	@FXML
	private Button boutonSuivant;
	
	/** Indice de la première question affichée sur la "page" courante. */
	private int indiceQuestion = 0; 
	
	private ArrayList<Question> toutesLesQuestions = jeu.getToutesLesQuestions();
	
	private Label questionCourante;
	
	/**
	 * Initialisation de la vue avec le style css correspondant et l'affichage
	 * des questions et du bouton suivant.
	 */
	@FXML
	private void initialize() {
		NavigationControleur.getScene().getStylesheets()
		.add(getClass().getResource("/info2/sae301/quiz/vues/application.css")
				       .toExternalForm());
		
		afficherQuestions();
	}
	
	/** @return L'intitulé de la question sélectionnée. */
	public static String getIntituleQuestionSelectionnee() {
		return intituleQuestionSelectionnee;
	}
	
	/**
	 * Renomme la question sélectionnée avec l'intitulé en paramètre.
	 * 
	 * @param nouveauIntitule Le nouveau intitulé de la question.
	 */
	public static void renommerQuestionSelectionnee(String nouveauIntitule,
													String intituleCategorie,
													String reponseJuste,
													String[] reponsesFausses) {
		final String QUESTION_INEXISTANTE
		= "La question sélectionnée est inexistante en mémoire ou ne peut pas "
		  + "être renommée.";
		
		final String QUESTION_DEJA_EXISTANTE
		= "L'intitulé entré existe déjà pour une autre question.";
		
		final String TAILLE_INVALIDE
		= "La taille d'un intitulé de question doit être comprise entre 1 et 300.";
		
		if (nouveauIntitule.length() < 1 || nouveauIntitule.length() > 300) {
			throw new IllegalArgumentException(TAILLE_INVALIDE);
		}
		
		// Si une question ayant le même intitulé existe.
		if (jeu.getIndiceQuestion(nouveauIntitule, intituleCategorie,
				                  reponseJuste, reponsesFausses) >= 0) {
			throw new IllegalArgumentException(QUESTION_DEJA_EXISTANTE);
		}
		
		int indiceQuestion = 0;//jeu.questionExiste(intituleQuestionSelectionnee);
		
		if (indiceQuestion > 0) {
			jeu.getToutesLesQuestions().get(indiceQuestion).setIntitule(nouveauIntitule);
			// Désélection de la catégorie pour le changement de vue
			intituleQuestionSelectionnee = null;
		} else {
			throw new IllegalArgumentException(QUESTION_INEXISTANTE);
		}
	}
	
	/**
	 * Affiche 5 questions au maximum et gère l'affichage des boutons
	 * précédent et suivant en fonction du nombre de questions précédentes
	 * et suivantes.
	 */
	private void afficherQuestions() {
	    // Calcul des indices pour l'affichage des questions
	    int indiceDebut = indiceQuestion;
	    int indiceFin = Math.min(indiceDebut + 5, toutesLesQuestions.size());
	    
	    // Effacer le contenu actuel du VBox
	    vBoxQuestions.getChildren().clear();
		
	    // Afficher les (indiceFin - indiceDebut) questions
	    for (int i = indiceDebut; i < indiceFin; i++) {
	    	String intituleQuestion = toutesLesQuestions.get(i).getIntitule();
	    	
	        questionCourante = new Label(intituleQuestion);
	        questionCourante.getStyleClass().add("intituleQuestionQuestion");
	        questionCourante.getStyleClass().add("labelCliquable");
	        vBoxQuestions.getChildren().add(questionCourante);
	        
	        final String intitule = intituleQuestion;
			questionCourante.setOnMouseClicked(event -> {
			    actionEditerQuestion(intitule);
			});
	    }
	    // Cacher le bouton "Précédent" s'il n'y a plus de questions précédentes
	    boutonPrecedent.setVisible(indiceQuestion < 5 ? false : true);
	    
	    // Cacher le bouton "Suivant" s'il n'y a plus de questions suivantes
	    boutonSuivant.setVisible(toutesLesQuestions.size() > 5
	    		                 && indiceFin < toutesLesQuestions.size()
	    		                 ? true
	    		                 : false);
	}
	
	/**
	 * Changement de vue et modification de l'attribut de la catégorie sélectionnée
	 * dans la classe de sauvegarde des paramètres, questions et questions.
	 * 
	 * @param intitule L'intitulé de la catégorie sélectionnée à sauvegarder.
	 */
	private void actionEditerQuestion(String intitule) {
		intituleQuestionSelectionnee = intitule;
		NavigationControleur.changerVue("EditionQuestions.fxml");
	}
	
	/**
	 * Retrait de 5 questions à l'indice de la première catégorie à afficher
	 * et affichage des 5 questions précédentes. 
	 */
	@FXML
	private void actionBoutonPrecedent() {
		// On recule de 5 questions
		indiceQuestion -= 5;
		afficherQuestions();
	}
	
	/**
	 * Ajout de 5 questions à l'indice de la première catégorie à afficher
	 * et affichage des 5 questions suivantes. 
	 */
	@FXML
	private void actionBoutonSuivant() {
		// On avance de 5 questions
		indiceQuestion += 5;
	    afficherQuestions();
	}
	
	/**
	 * TODO : coder action bouton aide
	 */
	@FXML
	private void actionBoutonAide() {
//		ControleurNavigation.changerVue("GestionDesQuestions.fxml");
	}
	
	/**
	 * Redirection vers la vue AffichageQuestions.
	 */
	@FXML
	private void actionBoutonAnnuler() {
		NavigationControleur.changerVue("AffichageQuestions.fxml");
	}
	
}
