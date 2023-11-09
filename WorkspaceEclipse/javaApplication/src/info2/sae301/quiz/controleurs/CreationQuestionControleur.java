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

public class CreationQuestionControleur {
	
	private static final String TITRE_ALERTE = "Erreur de création";

	private static final String MESSAGE_ERREUR_TROP_DE_CARACTERE =
			"Vous ne pouvez pas mettre plus de 20 caractères";
	
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
		// Catégorie général par défaut
		intituleCategorie.setValue(jeu.getToutesLesCategories().get(0).getIntitule());
		
		String[] difficultes = {"0 - Indifférente", "1 - Facile", "2 - Moyenne",
				                "3 - Difficile"};
		
		// Affichage des difficultés dans le menu déroulant
		for (String niveau : difficultes) {
			difficulte.getItems().add(niveau);
		}
		// Niveau 0
		difficulte.setValue(difficultes[0]);
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
			jeu.creerQuestion(intituleQuestionEntre, reponseJusteEntree,
				              reponsesFausses.toArray(new String[reponsesFausses.size()]),
				              difficulteEntree, feedbackEntre, categorie);
		} catch (Exception e) {
			AlerteControleur.autreAlerte(MESSAGE_ERREUR_TROP_DE_CARACTERE,
										 TITRE_ALERTE, AlertType.ERROR);
		}
	}
	
}
