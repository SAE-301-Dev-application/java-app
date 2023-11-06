package info2.sae301.quiz.controleurs;

import java.util.ArrayList;
import java.util.Arrays;

import info2.sae301.quiz.Categorie;
import info2.sae301.quiz.Question;
import info2.sae301.quiz.gestion.GestionCategories;
import info2.sae301.quiz.gestion.GestionQuestions;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreationQuestionControleur {
	
	private static final String TITRE_ALERTE = "Erreur de création";

	private static final String MESSAGE_ERREUR_TROP_DE_CARACTERE =
			"Vous ne pouvez pas mettre plus de 20 caractères";
	
	@FXML
	private TextField intitule;
	
	@FXML
	private ChoiceBox nomCategorie;
	
	@FXML
	private ChoiceBox difficulte;
	
	@FXML
	private TextArea feedback;
	
	@FXML
	private TextField reponseJuste;
	
	@FXML
	private TextField[] reponsesFausses;
	
	@FXML
	private void boutonAide() {
		// ControleurNavigation.changerVue("GestionDesCategories.fxml");  // TODO: implémenter aide
	}
	
	@FXML
	private void boutonAnnuler() {
		ControleurNavigation.changerVue("AffichageQuestions.fxml");
	}
	
	@FXML
	private void boutonEnregistrer() {
		
		ControleurAlerte.autreAlerte(MESSAGE_ERREUR_TROP_DE_CARACTERE,
				 					 TITRE_ALERTE, AlertType.ERROR);
		
		ArrayList<String> reponsesFausses = new ArrayList<String>();
		
		String intitule,
			   nomCategorie,
			   feedback,
			   reponseJuste;
		
		int difficulte,
		    indiceCategorie;
		
		Categorie categorie;
		
		nomCategorie = this.nomCategorie.getValue().toString();
		
		indiceCategorie = GestionCategories.categorieExiste(nomCategorie);
		
		if (indiceCategorie == -1) {
			// TODO: implémenter dialogbox avec erreur.
		}
		categorie = GestionCategories.getListeToutesCategories().get(indiceCategorie);
		
		
		for (TextField reponseFausse: this.reponsesFausses) {
			reponsesFausses.add(reponseFausse.getText());
			System.out.println(reponseFausse.getText());
		}
		
		intitule = this.intitule.getText();
		feedback = this.feedback.getText();
		reponseJuste = this.reponseJuste.getText();
		
		difficulte = Integer.parseInt(this.difficulte.getValue().toString());
		
		try {
			Question nouvelleQuestion
			= new Question(intitule, reponseJuste,
					       reponsesFausses.toArray(new String[reponsesFausses.size()]),
					       difficulte, categorie);
			
			GestionQuestions.ajouter(nouvelleQuestion);
		} catch (Exception e) {
			ControleurAlerte.autreAlerte(MESSAGE_ERREUR_TROP_DE_CARACTERE,
										 TITRE_ALERTE, AlertType.ERROR);
		}
	}
	
}
