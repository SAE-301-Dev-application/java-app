package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Categorie;
import info2.sae301.quiz.gestion.GestionCategories;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CategoriesControleur {
	
	@FXML
	private VBox vBox;
	
	private int index; 
	
	private ArrayList<Categorie> toutesLesCategories = GestionCategories.getListeToutesCategories();
	
	private Label categorieCourante;
	
	@FXML
	private void initialize() {
		ControleurNavigation.getScene().getStylesheets().add(getClass().getResource("/info2/sae301/quiz/vues/application.css").toExternalForm());
		
		
		for (index = 0; index < toutesLesCategories.size() && index < 10; index++) {
			categorieCourante = new Label(toutesLesCategories.get(index).getIntitule());
			categorieCourante.getStyleClass().add("intituleCategorieQuestion");
			vBox.getChildren().add(categorieCourante);
			
		}
//		for (Categorie categorie : toutesLesCategories) {
//			
//		}
		
	}

	@FXML
	private void boutonAide() {
//		ControleurNavigation.changerVue("GestionDesCategories.fxml");
	}
	@FXML
	private void boutonRenommer() {
//		ControleurNavigation.changerVue("ModidicationCategories.fxml");
	}
	@FXML
	private void boutonSupprimer() {
		ControleurNavigation.changerVue("SuppressionCategories.fxml");
	}
	
	@FXML
	private void boutonPrecedent() {
		if (index >= 10) {
			for (index = index / 10 - 10; index < index + 10; index++) {
				categorieCourante = new Label(toutesLesCategories.get(index).getIntitule());
				categorieCourante.getStyleClass().add("intituleCategorieQuestion");
				vBox.getChildren().add(categorieCourante);
				
			}
		}
	}
	
	@FXML
	private void boutonSuivant() {
		
	}
	
	@FXML
	private void boutonRetour() {
		ControleurNavigation.changerVue("MenuPrincipal.fxml");
	}
	
	@FXML
	private void boutonCreer() {
		ControleurNavigation.changerVue("CreationCategories.fxml");
	}
	
	
}
