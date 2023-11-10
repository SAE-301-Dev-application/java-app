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
 * Contrôleur FXML de la vue EditionQuestion qui permet l'édition d'une question.
 * 
 * @author FAUGIERES Loïc
 */
public class EditionQuestionControleur {
	
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
	
	@FXML
	private void initialize() {
		// Affichage des catégories dans le menu déroulant de filtre
		for (Categorie categorieCourante : jeu.getToutesLesCategories()) {
			intituleCategorie.getItems().add(categorieCourante.getIntitule());
		}
		
		Question question
		= jeu.getToutesLesQuestions()
		     .get(ChoixEditionQuestionControleur.getIndiceQuestionSelectionnee());
		
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
	 * TODO javadoc
	 * @param question
	 * @param reponsesFausses
	 */
	private void afficherReponsesFausses(Question question, TextArea[] reponsesFausses) {
		for (int i = 0; i < reponsesFausses.length; i++) {
			String reponse = question.getReponsesFausses().length > 1
					         && !question.getReponsesFausses()[i + 1].isBlank()
					         ? question.getReponsesFausses()[i + 1]
							 : "";
			reponsesFausses[i].setText(reponse);
		}
	}
	
	@FXML
	private void actionBoutonAide() {
		// ControleurNavigation.changerVue("GestionDesCategories.fxml");  // TODO: implémenter aide
	}
	
	@FXML
	private void actionBoutonAnnuler() {
		NavigationControleur.changerVue("AffichageQuestions.fxml");
	}
	
	/**
	 * TODO : javadoc
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
		
		indiceCategorie = jeu.indiceCategorie(this.intituleCategorie.getValue().toString());
		categorie = jeu.getToutesLesCategories().get(indiceCategorie);
		
		reponsesFausses.add(reponseFausse1.getText());
		reponsesFausses.add(reponseFausse2.getText());
		reponsesFausses.add(reponseFausse3.getText());
		reponsesFausses.add(reponseFausse4.getText());
			
		intituleQuestionEntre = this.intituleQuestion.getText();
		feedbackEntre = this.feedback.getText();
		
		reponseJusteEntree = this.reponseJuste.getText();
		
		difficulteEntree = Integer.parseInt("" + this.difficulte.getValue().charAt(0));
		
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
