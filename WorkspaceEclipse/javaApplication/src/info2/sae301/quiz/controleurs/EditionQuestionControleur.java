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

public class EditionQuestionControleur {
	
	private static final String TITRE_ALERTE = "Erreur de création";

	private static final String MESSAGE_ERREUR_TROP_DE_CARACTERE =
			"Vous ne pouvez pas mettre plus de 20 caractères";
	
	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private Jeu jeu = Quiz.jeu;
	
	@FXML
	private TextArea intitule;
	
	@FXML
	private ChoiceBox<String> nomCategorie;
	
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
	private void actionBoutonAide() {
		// ControleurNavigation.changerVue("GestionDesCategories.fxml");  // TODO: implémenter aide
	}
	
	@FXML
	private void actionBoutonAnnuler() {
		NavigationControleur.changerVue("AffichageQuestions.fxml");
	}
	
	@FXML
	private void actionBoutonEnregistrer() {
		verificationDesChamps();
		
		ArrayList<String> reponsesFausses = new ArrayList<String>();
		
		String intitule,
			   nomCategorie,
			   feedback,
			   reponseJuste;
		
		int difficulte,
		    indiceCategorie;
		
		Categorie categorie;
		
		nomCategorie = this.nomCategorie.getValue().toString();
		
		indiceCategorie = jeu.getIndiceCategorie(nomCategorie);
		
		if (indiceCategorie == -1) {
			// TODO: implémenter dialogbox avec erreur.
		}
		categorie = jeu.getToutesLesCategories().get(indiceCategorie);
		
		reponsesFausses.add(reponseFausse1.getText());
		reponsesFausses.add(reponseFausse2.getText());
		reponsesFausses.add(reponseFausse3.getText());
		reponsesFausses.add(reponseFausse4.getText());
			
		intitule = this.intitule.getText();
		feedback = this.feedback.getText();
		reponseJuste = this.reponseJuste.getText();
		
		difficulte = Integer.parseInt(this.difficulte.getValue().toString());
		
		try {
			Question nouvelleQuestion
			= new Question(intitule, reponseJuste,
					       reponsesFausses.toArray(new String[reponsesFausses.size()]),
					       difficulte, categorie);
			
			jeu.ajouterQuestion(nouvelleQuestion);
		} catch (Exception e) {
			AlerteControleur.autreAlerte(MESSAGE_ERREUR_TROP_DE_CARACTERE,
										 TITRE_ALERTE, AlertType.ERROR);
		}
	}
	
	/**
	 * Vérifie que les champs obligatoire sont bien initialisé
	 * et que tous les champs respectent la limite de caractère.
	 * @return true si tous les champs sont conforme
	 */
	private boolean verificationDesChamps() {
		return false;
	}
	
}
