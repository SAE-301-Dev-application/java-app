/*
 * EditionQuestionControleur.java							         9 nov. 2023
 * IUT de Rodez, pas de copyright, ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Question;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

/**
 * Contrôleur FXML de la vue EditionQuestion qui permet l'édition 
 * d'une question.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class EditionQuestionControleur {
	
	/** Titre d'erreur pour l'alerte d'édition question */
	private static final String TITRE_ALERTE = "Erreur d'édition de question";
	
	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private Jeu jeu = Quiz.jeu;
	
	@FXML
	private TextArea intituleQuestion;
	
	@FXML
	private ChoiceBox<String> intituleCategorie;
	
	@FXML
	private ChoiceBox<String> difficulte;
	
	@FXML
	private TextArea feedback;
	
	@FXML
	private TextArea reponseJuste;
	
	@FXML
	private TextArea reponseFausse1;
	
	@FXML
	private TextArea reponseFausse2;
	
	@FXML
	private TextArea reponseFausse3;
	
	@FXML
	private TextArea reponseFausse4;
	
	
	/**
	 * Initialisation de la fenêtre d'édition en ajoutant les 
	 * catégories et difficultés dans les ChoiceBox.
	 */
	@FXML
	private void initialize() {
		// Affichage des catégories dans le menu déroulant de filtre
		for (Categorie categorieCourante : jeu.getToutesLesCategories()) {
			intituleCategorie.getItems().add(categorieCourante.getIntitule());
		}
		
		Question question = jeu.getToutesLesQuestions()
				.get(ChoixEditionQuestionControleur
		    	.getIndiceQuestionSelectionnee());
		
		// Catégorie général par défaut
		intituleCategorie.setValue(question.getCategorie().getIntitule());
		
		String[] difficultes = {"1 - Facile", "2 - Moyenne",
				                "3 - Difficile"};
		
		// Affichage des difficultés dans le menu déroulant
		for (String niveau : difficultes) {
			difficulte.getItems().add(niveau);
		}
		difficulte.setValue(difficultes[question.getDifficulte() - 1]);
		
		intituleQuestion.setText(question.getIntitule());
		feedback.setText(question.getFeedback());
		reponseJuste.setText(question.getReponseJuste());
		
		reponseFausse1.setText(question.getReponsesFausses()[0]);
		
		afficherReponsesFausses(question,
				                new TextArea[] {reponseFausse2, reponseFausse3,
				                		        reponseFausse4});
	}
	
	
	/**
	 * Affichage des réponses fausses de la question sélectionnée 
	 * en fonction de leur validité.
	 * 
	 * @param question La question contenant les réponses fausses.
	 * @param reponsesFausses Les TextArea de réponses fausses.
	 */
	private void afficherReponsesFausses(Question question,
										TextArea[] reponsesFausses) {
		for (int i = 0; i < reponsesFausses.length; i++) {
			String reponse = question.getReponsesFausses().length > 1
					         && !question.getReponsesFausses()[i + 1].isBlank()
					         ? question.getReponsesFausses()[i + 1]
							 : "";
			reponsesFausses[i].setText(reponse);
		}
	}
	
	
	/**
	 * Permet d'afficher une pop up d'aide concernant l'édition
	 * des questions
	 */
	@FXML
	private void actionBoutonAide() {
		AlerteControleur.aide(AffichageQuestionsControleur.AIDE_TITRE,
							  AffichageQuestionsControleur.AIDE_TEXTE);
	}
	
	/**
	 * Redirection vers la vue AffichageQuestions.fxml
	 */
	@FXML
	private void actionBoutonAnnuler() {
		NavigationControleur.changerVue("AffichageQuestions.fxml");
	}
	
	/**
	 * Enregistre les modifications de la questions ou affiche 
	 * une pop-up d'erreur éventuellement.
	 */
	@FXML
	private void actionBoutonEnregistrer() {
		
		ArrayList<String> reponsesFausses = new ArrayList<String>();
		
		String intituleQuestionEntre,
			   feedbackEntre,
			   reponseJusteEntree;
		
		int difficulteEntree,
		    indiceCategorie;
		
		Categorie categorie;
		
		indiceCategorie = jeu.indiceCategorie(this.intituleCategorie
											.getValue().toString());
		categorie = jeu.getToutesLesCategories().get(indiceCategorie);
		
		reponsesFausses.add(reponseFausse1.getText().trim());
		reponsesFausses.add(reponseFausse2.getText().trim());
		reponsesFausses.add(reponseFausse3.getText().trim());
		reponsesFausses.add(reponseFausse4.getText().trim());
			
		intituleQuestionEntre = this.intituleQuestion.getText().trim();
		feedbackEntre = this.feedback.getText().trim();
		
		reponseJusteEntree = this.reponseJuste.getText().trim();
		
		difficulteEntree = Integer.parseInt("" + this.difficulte
											.getValue().charAt(0));
		
		try {
			jeu.editerQuestion(ChoixEditionQuestionControleur.getIndiceQuestionSelectionnee(),
					           intituleQuestionEntre, reponseJusteEntree,
					           reponsesFausses.toArray(new String[reponsesFausses.size()]),
					           difficulteEntree, feedbackEntre,
					           categorie.getIntitule());
			NavigationControleur.changerVue("AffichageQuestions.fxml");
		} catch (Exception e) {
			AlerteControleur.autreAlerte(e.getMessage(),
										 TITRE_ALERTE, AlertType.ERROR);
		}
	}
}
