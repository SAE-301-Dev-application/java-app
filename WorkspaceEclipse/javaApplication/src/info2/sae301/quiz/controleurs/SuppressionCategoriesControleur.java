package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Categorie;
import info2.sae301.quiz.gestion.GestionCategories;
import info2.sae301.quiz.gestion.GestionQuestions;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SuppressionCategoriesControleur {
	
	@FXML
	private VBox vBox;
	
	@FXML
	private void initialize() {
		ArrayList<Categorie> toutesLesCategories = GestionCategories.getListeToutesCategories();
		
		HBox ligneCategorie;
		CheckBox caseCategorie;
		Label categorieCourante;
		
		for (Categorie categorie : toutesLesCategories) {
			ligneCategorie = new HBox();
			
			caseCategorie = new CheckBox();
			
			categorieCourante = new Label(categorie.getIntitule());
			categorieCourante.getStyleClass().add("intituleCategorieQuestion");
			
			ligneCategorie.getChildren().add(caseCategorie);
			ligneCategorie.getChildren().add(categorieCourante);
			
			vBox.getChildren().add(ligneCategorie);
		}
	}

	@FXML
	private void actionBoutonAide() {
//		ControleurNavigation.changerVue("GestionDesQuestions.fxml");  // TODO: implémenter la fenêtre d'aide
	}
	
	@FXML
	private void actionBoutonPrecedent() {
	}
	
	@FXML
	private void actionBoutonSuivant() {
	}
	
	@FXML
	private void actionBoutonAnnuler() {
		ControleurNavigation.changerVue("AffichageCategories.fxml");
	}
	
	@FXML
	private void actionBoutonSupprimer() {
		// TODO: suppression à implémenter
	}
	
	
}
