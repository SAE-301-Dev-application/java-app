/*
 * SelectionQuestionsImporteesControleur.java			 1 déc. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import info2.sae301.quiz.modeles.fichiers.OutilsCSV;
import info2.sae301.quiz.modeles.reseau.Import;
import static info2.sae301.quiz.controleurs.AlerteControleur.autreAlerte;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
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
	
	private final static String IMPORTATION_SUCCESS_TITRE
	= "IMPORTATION TERMINÉE";
	
	private final static String IMPORTATION_SUCCESS_MESSAGE
	= "L'importation de %d question(s) s'est terminée avec succès.";
	
	private final static String ERREUR_QUESTIONS_EXISTANTES_TITRE
	= "AUCUNE QUESTION A IMPORTER";
	
	private final static String ERREUR_QUESTIONS_EXISTANTES_MESSAGE
	= "Toutes les questions importées ont déjà été créées ou importées.";
	
	private final static String ERREUR_AUCUNE_SELECTION
	= "Aucune question n'a été importée.";
	
	private final static String ERREUR_IMPORT_IMPOSSIBLE_TITRE
	= "IMPORTATION IMPOSSIBLE";
	
	private final static String ERREUR_IMPORT_IMPOSSIBLE_MESSAGE
	= "L'importation des questions a échoué. Celles-ci ne respectent pas le "
	  + "format demandé du fichier CSV.";
	
	private final static String AIDE_TITRE = "SELECTION DE QUESTIONS";
	
	private final static String AIDE_TEXTE
	= """
	  Vous devez sélectionner les CheckBox en face des questions
	  que vous souhaitez importer sur votre jeu.
	  
	  Les questions n'étant pas affichées existent déjà dans vos données.
	  """;
	
	/** Toutes les questions dont la CheckBox a été sélectionnée. */
	private ArrayList<String> questionsSelectionnees = new ArrayList<>();
	
	/** Indice de la première question affichée sur la "page" courante. */
	private int indiceQuestion; 
	
	/** Association de toutes les questions à leur CheckBox */
	private HashMap<String, CheckBox> toutesLesQuestions = new HashMap<>();
	
	private ArrayList<String> listeQuestionsImportees;
	
	private ArrayList<String> donneesToutesQuestions;
	
	private int nombreQuestionsDejaExistantes;
	
	@FXML
	private VBox vBoxQuestions;
	
	@FXML
	private Button boutonPrecedent;
	
	@FXML
	private Button boutonSuivant;
	
	@FXML
	private CheckBox boutonToutesQuestions;
	
	
	/**
	 * Initialisation de la vue avec le style CSS correspondant et 
	 * l'affichage des questions et du bouton suivant.
	 */
	@FXML
	private void initialize() {
		listeQuestionsImportees = Import.getQuestionsImportees();
		
		if (listeQuestionsImportees.size() <= 0) {
			erreurImportationImpossible();
		}
		
		this.nombreQuestionsDejaExistantes = 0;
		
		indiceQuestion = 0;
		
		donneesToutesQuestions = new ArrayList<String>();
		
		CompletableFuture.supplyAsync(() -> {
			try {
				initialiserToutesLesQuestions();
			} catch (Exception e) {
				return e.toString();
			}
			return "ok";
        }).thenAccept(result -> {
        	
        	if (!result.equals("ok")) {
        		Platform.runLater(() -> {
        			System.out.println("ERREUR GEREE IMPORT :\n" + result);
        			erreurImportationImpossible();
				});	
        	} else if (this.nombreQuestionsDejaExistantes
			    == this.listeQuestionsImportees.size()) {
				
				this.listeQuestionsImportees = null;
				
				Platform.runLater(() -> {
					erreurQuestionsDejaExistantes();
					
					NavigationControleur.changerVue("Import.fxml");
				});
			} else {
				Platform.runLater(() -> {
					afficherQuestions();
				});	
			}
        });
	}
	
	/**
	 * Initialisation de toutes les CheckBoxs représentant
	 * les questions selon le filtre de catégorie.
	 */
	private void initialiserToutesLesQuestions() {
		String questionCourante,
		       intituleQuestionC;
		
		String[] donneesQuestionCourante;
		
		toutesLesQuestions.clear();

		for (int i = 0; i < this.listeQuestionsImportees.size(); i++) {
			
			questionCourante = this.listeQuestionsImportees.get(i);
			
			// Pour résoudre l'erreur de l'évènement onMouseClicked
			final String DONNEES_QUESTION = questionCourante;
			
			donneesQuestionCourante
			= Import.extraireDonneesQuestion(questionCourante);
			
			for (int indiceDonnee = 0;
				 indiceDonnee < donneesQuestionCourante.length;
			     indiceDonnee++) {
				
				donneesQuestionCourante[indiceDonnee]
				= Import.retirerGuillemetsInvalides(
						               donneesQuestionCourante[indiceDonnee]);
			}
			
			intituleQuestionC = donneesQuestionCourante[2];
			
			if (Import.verificationQuestionExiste(donneesQuestionCourante)) {
				nombreQuestionsDejaExistantes++;
			} else {
				CheckBox checkBoxQuestion = new CheckBox();
				
				toutesLesQuestions.put(intituleQuestionC, checkBoxQuestion);
				
				checkBoxQuestion.setId("" + i);
				checkBoxQuestion.setText(intituleQuestionC.replaceAll("\n", " "));
				checkBoxQuestion.getStyleClass().add("checkbox-margin");
				checkBoxQuestion.getStyleClass().add("intituleCategorieQuestion");
				checkBoxQuestion.getStyleClass().add("intitule-padding-left");
				
				checkBoxQuestion.setOnMouseClicked(event -> {
					selectionnerQuestion(DONNEES_QUESTION, checkBoxQuestion);
				});
				
				donneesToutesQuestions.add(DONNEES_QUESTION);
			}			
		}
	}
	
	/**
	 * Affiche 9 questions au maximum et gère l'affichage des boutons
	 * précédent et suivant en fonction du nombre de questions 
	 * précédentes et suivantes.
	 */
	private void afficherQuestions() {
		
	    // Calcul des indices pour l'affichage des questions
	    int indiceDebut = indiceQuestion;
	    int indiceFin = Math.min(indiceDebut + 9, this.toutesLesQuestions.size());
	    
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
	    
	    // Cacher le bouton "Précédent" s'il n'y a plus 
	    // de questions précédentes
	    boutonPrecedent.setVisible(!(indiceQuestion < 9));
	    
	    // Cacher le bouton "Suivant" s'il n'y a plus 
	    // de questions suivantes
	    boutonSuivant.setVisible(this.toutesLesQuestions.size() > 9
	    		                 && indiceFin < this.toutesLesQuestions.size());
	}
	
	/**
	 * Clic sur la CheckBox afin de sélectionner une question.
	 * 
	 * @param donneesQuestion Données de la question à sélectionner.
	 * @param checkBox à cocher
	 */
	private void selectionnerQuestion(String donneesQuestion, CheckBox checkBox) {
		if (checkBox.isSelected()) {
			this.questionsSelectionnees.add(donneesQuestion);
		} else {
			this.questionsSelectionnees.remove(donneesQuestion);
		}
	}
	
	/**
	 * Retrait de 9 questions à l'indice de la première question 
	 * à afficher et affichage des 9 questions précédentes.
	 */
	@FXML
	private void actionBoutonPrecedent() {
		// On recule de 9 questions
		indiceQuestion -= 9;
	    afficherQuestions();
	}
	
	/**
	 * Ajout de 9 questions à l'indice de la première question à 
	 * afficher et affichage des 9 questions suivantes. 
	 */
	@FXML
	private void actionBoutonSuivant() {
		// On avance de 9 questions
		indiceQuestion += 9;
		afficherQuestions();
	}
	
	
	/**
	 * Sélection de toutes les questions.
	 */
	@FXML
	private void actionSelectionnerToutesQuestions() {
	
		this.questionsSelectionnees = new ArrayList<>();
				
		for (Map.Entry<String, CheckBox> paire : this.toutesLesQuestions.entrySet()) {
			paire.getValue().setSelected(this.boutonToutesQuestions.isSelected());
			paire.getValue().setDisable(this.boutonToutesQuestions.isSelected());
		}
		
		if (this.boutonToutesQuestions.isSelected()) {
			this.questionsSelectionnees = this.donneesToutesQuestions;
		}
		
	}
	
	
	/**
	 * Affichage d'une pop-up d'aide concernant la sélection des questions
	 * à importer.
	 */
	@FXML
	private void actionBoutonAider() {
		AlerteControleur.aide(AIDE_TITRE, AIDE_TEXTE);
	}
	
	
    /**
	 * Redirection vers la vue AffichageQuestions.
     */
	@FXML
	private void actionBoutonAnnuler() {
		AffichageQuestionsControleur.indiceQuestion = indiceQuestion;
		NavigationControleur.changerVue("Import.fxml");
	}
	
	
	/**
	 * Enregistre en mémoire les questions sélectionnées.
	 * 
	 * @throws IOException si l'écriture du CSV échoue.
	 */
	@FXML
	private void actionBoutonEnregistrer() throws IOException {
		int nombreQuestionsCrees;
		
		nombreQuestionsCrees = 0;
		
		System.out.println("\nEnregistrement des questions sélectionnées"
				           + "\n\nQuestions à créer :");
		
		for (String questionCourante: questionsSelectionnees) {
			Import.creerQuestion(questionCourante);				
			nombreQuestionsCrees++;
		}
		
		OutilsCSV.ecrireFichierCSV(
			Import.getLignesQuestionsCrees().toArray(new String[0])
		);
		
		indicationStatutImportation(nombreQuestionsCrees);
		
		NavigationControleur.changerVue("Import.fxml");
	}
	
	/**
	 * Indication via une pop-up du nombre de questions importées
	 * après la réussite de l'import.
	 * 
	 * @param nombreQuestionsCrees 
	 */
	private void indicationStatutImportation(int nombreQuestionsCrees) {
		String messageImportationSucces;
		
		if (this.questionsSelectionnees.size() <= 0) {
			messageImportationSucces
			= ERREUR_AUCUNE_SELECTION;
			
			autreAlerte(messageImportationSucces, ERREUR_QUESTIONS_EXISTANTES_TITRE, 
				        AlertType.INFORMATION);
		} else {
			messageImportationSucces 
			= String.format(IMPORTATION_SUCCESS_MESSAGE, nombreQuestionsCrees);	
			
			autreAlerte(messageImportationSucces, IMPORTATION_SUCCESS_TITRE, 
					    AlertType.INFORMATION);
		}
	}
	
	
	/**
	 * Affiche une pop-up d'erreur indiquant que toutes les questions
	 * importées existent déjà.
	 */
	private static void erreurQuestionsDejaExistantes() {
		AlerteControleur.autreAlerte(ERREUR_QUESTIONS_EXISTANTES_MESSAGE,
                                     ERREUR_QUESTIONS_EXISTANTES_TITRE,
                                     AlertType.ERROR);
	}
	
	
	/**
	 * Affiche une pop-up d'erreur indiquant que l'import des questions
	 * n'a pas fonctionné.
	 */
	private static void erreurImportationImpossible() {
		AlerteControleur.autreAlerte(ERREUR_IMPORT_IMPOSSIBLE_MESSAGE,
				                     ERREUR_IMPORT_IMPOSSIBLE_TITRE,
                                     AlertType.ERROR);
		
		NavigationControleur.changerVue("Import.fxml");
	}
}
