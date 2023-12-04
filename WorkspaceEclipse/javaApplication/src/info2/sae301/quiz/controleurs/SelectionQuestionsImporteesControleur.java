/*
 * SelectionQuestionsImporteesControleur.java								 1 déc. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;
import java.util.HashMap;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.Question;
import info2.sae301.quiz.modeles.reseau.Import;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

/**
 * Contrôleur FXML de la vue SelectionQuestionsImportees qui permet
 * la sélection et la sauvegarde sur la machine des questions reçues
 * via import.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class SelectionQuestionsImporteesControleur {
	
	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private Jeu jeu = Quiz.jeu;
	
	@FXML
	private VBox vBoxQuestions;
	
	@FXML
	private Button boutonPrecedent;
	
	@FXML
	private Button boutonSuivant;
	
	
	/** Toutes les questions dont la checkbox a été sélectionnée. */
	private ArrayList<Question> questionsSelectionnees = new ArrayList<>();
	
	/** Indice de la première question affichée sur la "page" courante. */
	private int indiceQuestion; 
	
	/** Association de toutes les questions à leur checkbox */
	private HashMap<Question, CheckBox> toutesLesQuestions = new HashMap<>();
	
	private ArrayList<Question> listeQuestionsImportees;
	
	/**
	 * Initialisation de la vue avec le style css correspondant et 
	 * l'affichage des questions et du bouton suivant.
	 */
	@FXML
	private void initialize() {
		listeQuestionsImportees = Import.getQuestionsImportees();
		
		indiceQuestion = AffichageQuestionsControleur.indiceQuestion;

		System.out.println(this.listeQuestionsImportees);
		
		initialiserToutesLesQuestions();
		afficherQuestions();
	}
	
	/**
	 * initialisation de toutes les checkboxs représentent
	 * les questions selon le filtre de catégorie.
	 */
	private void initialiserToutesLesQuestions() {
		toutesLesQuestions.clear();

		for (int i = 0; i < this.listeQuestionsImportees.size(); i++) {
			
			Question questionCourante = this.listeQuestionsImportees.get(i);
			CheckBox checkBoxQuestion = new CheckBox();
			
			toutesLesQuestions.put(questionCourante, checkBoxQuestion);
			
	    	checkBoxQuestion.setId("" + i);
	    	checkBoxQuestion.setText(questionCourante.getIntitule().replaceAll("\n", " "));
	    	checkBoxQuestion.getStyleClass().add("checkbox-margin");
			checkBoxQuestion.getStyleClass().add("intituleCategorieQuestion");
			checkBoxQuestion.getStyleClass().add("intitule-padding-left");
			checkBoxQuestion.setOnMouseClicked(event -> {
				selectionnerQuestion(questionCourante, checkBoxQuestion);
			});
		}
	}
	
	/**
	 * Affiche 10 questions au maximum et gère l'affichage des boutons
	 * précédent et suivant en fonction du nombre de questions 
	 * précédentes et suivantes.
	 */
	private void afficherQuestions() {
		
	    // Calcul des indices pour l'affichage des questions
	    int indiceDebut = indiceQuestion;
	    int indiceFin = Math.min(indiceDebut + 10, this.toutesLesQuestions.size());
	    
	    // Effacer le contenu actuel du VBox
	    vBoxQuestions.getChildren().clear();
		
	    // Afficher les (indiceFin - indiceDebut) questions
	    for (int i = indiceDebut; i < indiceFin; i++) {
	        vBoxQuestions.getChildren().add(
				toutesLesQuestions.get(
						this.toutesLesQuestions.keySet().toArray()[i]
				)
			);
	    }
	    
	    // Cacher le bouton "Précédent" s'il n'y a plus de questions précédentes
	    boutonPrecedent.setVisible(!(indiceQuestion < 10));
	    
	    // Cacher le bouton "Suivant" s'il n'y a plus de questions suivantes
	    boutonSuivant.setVisible(this.toutesLesQuestions.size() > 10
	    		                 && indiceFin < this.toutesLesQuestions.size());
	}
	
	/**
	 * Clic sur la checkbox afin de sélectionner une question.
	 * 
	 * @param indiceQestion Indice de la question sélectionnée.
	 */
	private void selectionnerQuestion(Question question, CheckBox checkBox) {
		if (checkBox.isSelected()) {
			this.questionsSelectionnees.add(question);
		} else {
			this.questionsSelectionnees.remove(question);
		}
		
		System.out.println("Questions sélectionnées : ");
		
		for (Question qSelectCourante : this.questionsSelectionnees) {
			System.out.println("- " + qSelectCourante.getIntitule());
		}
		System.out.println();
	}
	
	/**
	 * Retrait de 10 questions à l'indice de la première question 
	 * à afficher et affichage des 10 questions précédentes.
	 */
	@FXML
	private void actionBoutonPrecedent() {
		// On recule de 10 questions
		indiceQuestion -= 10;
	    afficherQuestions();
	    System.out.println("Suppression : " + indiceQuestion);
	}
	
	/**
	 * Ajout de 10 questions à l'indice de la première question à 
	 * afficher et affichage des 10 questions suivantes. 
	 */
	@FXML
	private void actionBoutonSuivant() {
		// On avance de 10 catégories
		indiceQuestion += 10;
		afficherQuestions();
		System.out.println("Suppression : " + indiceQuestion);
	}
	
	/**
	 * Redirection vers la pop-up de la suppression des questions
	 */
	@FXML
	private void actionBoutonAider() {
		AlerteControleur.aide(AffichageQuestionsControleur.AIDE_TITRE,
				              AffichageQuestionsControleur.AIDE_TEXTE);
	}
	
    /**
	 * Redirection vers la vue AffichageQuestions.
     */
	@FXML
	private void actionBoutonAnnuler() {
		AffichageQuestionsControleur.indiceQuestion = indiceQuestion;
		NavigationControleur.changerVue("AffichageQuestions.fxml");
	}
	
	@FXML
	private void actionBoutonEnregistrer() {
		System.out.println("ENREGISTREMENT...");
		
		for (Question questionCourante: this.questionsSelectionnees) {
			//
			// OutilsCSV.ecrireFichierCSV(lignesAjoutees);
		}
	}
}
