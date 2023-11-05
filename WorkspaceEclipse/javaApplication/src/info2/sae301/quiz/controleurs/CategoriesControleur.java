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
	
	@FXML
	private void initialize() {
		ArrayList<Categorie> toutesLesCategories = GestionCategories.getListeToutesCategories();
		Label categorieCourante;
		for (Categorie categorie : toutesLesCategories) {
			categorieCourante = new Label(categorie.getIntitule());
			vBox.getChildren().add(categorieCourante);
			
		}
		
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
