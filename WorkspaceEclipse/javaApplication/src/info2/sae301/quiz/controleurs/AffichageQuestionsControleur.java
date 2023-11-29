/*
 * AffichageQuestionsControleur.java								 7 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.Question;
import info2.sae301.quiz.modeles.Categorie;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Contrôleur FXML de la vue AffichageQuestions qui affiche la liste des
 * questions et propose d'en créer, renommer et supprimer.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class AffichageQuestionsControleur {
	
	/** Titre du pop up d'aide */
	protected static final String AIDE_TITRE = "LES QUESTIONS";
	
	/** Texte du pop up d'aide */
	protected static final String AIDE_TEXTE
	= """
	  Regroupées, ou non, dans une catégorie spécifique, les questions 
	  composent le quiz auquel jouera l’utilisateur.

	  Elles possèdent un intitulé, jusqu’à cinq réponses dont une obligatoirement vraie, 
	  d’un niveau de difficulté parmi facile, moyen et difficile, et d’un feedback à 
	  afficher lors de la conclusion d’une partie.
	  """;
	
	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private Jeu jeu = Quiz.jeu;
	
	@FXML
	private VBox vBoxQuestions;
	
	@FXML
	private ChoiceBox<String> menuFiltre;
	
	@FXML
	private Button boutonPrecedent;
	
	@FXML
	private Button boutonSuivant;
	
	private Label questionCourante;
	
	/** Indice de la première question affichée sur la "page" courante. */
	static int indiceQuestion = 0; 
	
	/** Nom de la première catégorie affichée sur la "page" courante. */
	static String categorieCourante = "Toutes les catégories";
	
	
	/**
	 * Initialisation de la vue avec le style CSS correspondant et l'affichage
	 * des questions et du bouton suivant.
	 */
	@FXML
	private void initialize() {
		System.out.println("Affichage : " + indiceQuestion);
		menuFiltre.setValue(categorieCourante);
		
		NavigationControleur.getScene().getStylesheets()
		.add(getClass().getResource("/info2/sae301/quiz/vues/application.css")
				       .toExternalForm());
		
		menuFiltre.getItems().add("Toutes les catégories");
		// Affichage des catégories dans le menu déroulant de filtre
		for (Categorie categorieCourante : jeu.getToutesLesCategories()) {
			menuFiltre.getItems().add(categorieCourante.getIntitule());
		}
		
		afficherQuestions();
	}
	
	
	/**
	 * Réaffichage des questions lorsqu'une catégorie est sélectionnée.
	 */
	@FXML
	public void selectionFiltre() {
		System.out.println("Nouvelle catégorie sélectionnée : " + menuFiltre.getValue());
		if (!categorieCourante.equals(menuFiltre.getValue())) {
			indiceQuestion = 0;
			categorieCourante = menuFiltre.getValue();
		}
		afficherQuestions();
	}
	
	
	/**
	 * Affiche 10 questions au maximum et gère l'affichage des boutons
	 * précédent et suivant en fonction du nombre de questions précédentes
	 * et suivantes.
	 */
	private void afficherQuestions() {
		ArrayList<Question> questionsAAfficher
		= jeu.questionsCategorie(categorieCourante);
		
	    // Calcul des indices pour l'affichage des questions
	    int indiceDebut = indiceQuestion;
	    int indiceFin = Math.min(indiceDebut + 10, questionsAAfficher.size());
	    
	    // Effacer le contenu actuel du VBox
	    vBoxQuestions.getChildren().clear();
		
	    // Afficher les (indiceFin - indiceDebut) catégories
	    for (int i = indiceDebut; i < indiceFin; i++) {
	    	questionCourante
	    	= new Label(questionsAAfficher.get(i).getIntitule().replaceAll("\n", " "));
	        questionCourante.getStyleClass().add("intituleCategorieQuestion");
	        vBoxQuestions.getChildren().add(questionCourante);
	    }
	    // Cacher le bouton "Précédent" s'il n'y a plus de questions précédentes
	    boutonPrecedent.setVisible(!(indiceQuestion < 10));
	    
	    // Cacher le bouton "Suivant" s'il n'y a plus de questions suivantes
	    boutonSuivant.setVisible(questionsAAfficher.size() > 10
	    		                 && indiceFin < questionsAAfficher.size());
	}
	
	
	/**
	 * Retrait de 10 questions à l'indice de la première question à afficher
	 * et affichage des 10 questions précédentes.
	 */
	@FXML
	private void actionBoutonPrecedent() {
		// On recule de 10 questions
		indiceQuestion -= 10;
	    afficherQuestions();
	    System.out.println("Affichage : " + indiceQuestion);
	}
	
	
	/**
	 * Ajout de 10 questions à l'indice de la première question à afficher
	 * et affichage des 10 questions suivantes. 
	 */
	@FXML
	private void actionBoutonSuivant() {
		// On avance de 10 catégories
		indiceQuestion += 10;
		afficherQuestions();
		System.out.println("Affichage : " + indiceQuestion);
	}
	
	
	/**
	 * Permet d'afficher une pop up d'aide pour les questions
	 */
	@FXML
	private void actionBoutonAider() {
		AlerteControleur.aide(AIDE_TITRE, AIDE_TEXTE);
	}
	
	
	/**
	 * Redirection vers la vue EditerQuestions pour sélectionner la
	 * question à éditer.
	 */
	@FXML
	private void actionBoutonEditer() {
		NavigationControleur.changerVue("ChoixEditionQuestion.fxml");
	}
	
	
	/**
	 * Redirection vers la vue SuppressionQuestions pour sélectionner la ou les
	 * question(s) à supprimer.
	 */
	@FXML
	private void actionBoutonSupprimer() {
		NavigationControleur.changerVue("SuppressionQuestions.fxml");
	}
	
	
    /**
	 * Redirection vers la vue MenuPrincipal.
     */
	@FXML
	private void actionBoutonRetour() {
		NavigationControleur.changerVue("MenuPrincipal.fxml");
	}
	
	
    /**
	 * Redirection vers la vue CreationQuestions pour créer de nouvelles
	 * questions.
     */
	@FXML
	private void actionBoutonCreer() {
		NavigationControleur.changerVue("CreationQuestion.fxml");
	}
}
