package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Categorie;
import info2.sae301.quiz.gestion.GestionCategories;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ControleurSelectionCategorieARenommer {
	
	@FXML
	private VBox vBoxCategories;
	
	@FXML
	private Button boutonPrecedent;
	
	@FXML
	private Button boutonSuivant;
	
	/** Indice de la première catégorie affichée sur la "page" courante. */
	private int indiceCategorie = 0; 
	
	private ArrayList<Categorie> toutesLesCategories = GestionCategories.getListeToutesCategories();
	
	private Label categorieCourante;
	
	@FXML
	private void initialize() {
		ControleurNavigation.getScene().getStylesheets()
		.add(getClass().getResource("/info2/sae301/quiz/vues/application.css")
				       .toExternalForm());
		
		if (toutesLesCategories.size() < 5) {
			GestionCategories.creer("2ème catégorie");
			GestionCategories.creer("3ème catégorie");
			GestionCategories.creer("4ème catégorie");
			GestionCategories.creer("5ème catégorie");
			GestionCategories.creer("6ème catégorie");
			GestionCategories.creer("7ème catégorie");
			GestionCategories.creer("8ème catégorie");
			GestionCategories.creer("9ème catégorie");
			GestionCategories.creer("10ème catégorie");
			GestionCategories.creer("11ème catégorie");
			GestionCategories.creer("12ème catégorie");
			GestionCategories.creer("13ème catégorie");
			GestionCategories.creer("14ème catégorie");
			GestionCategories.creer("15ème catégorie");
			GestionCategories.creer("16ème catégorie");
			GestionCategories.creer("17ème catégorie");
			GestionCategories.creer("18ème catégorie");
			GestionCategories.creer("19ème catégorie");
			GestionCategories.creer("20ème catégorie");
			GestionCategories.creer("21ème catégorie");
			GestionCategories.creer("22ème catégorie");
			GestionCategories.creer("23ème catégorie");
			GestionCategories.creer("24ème catégorie");
			GestionCategories.creer("25ème catégorie");
			GestionCategories.creer("26ème catégorie");
			GestionCategories.creer("27ème catégorie");
			GestionCategories.creer("28ème catégorie");
			GestionCategories.creer("29ème catégorie");
			GestionCategories.creer("30ème catégorie");
			
			toutesLesCategories = GestionCategories.getListeToutesCategories();
		}
		
		if (indiceCategorie < 5) {
			boutonPrecedent.setVisible(false);
		}

		boutonSuivant.setVisible(toutesLesCategories.size() <= 5 ? false : true);
		
		for (int indiceCategorieCourante = 0;
			 indiceCategorieCourante < toutesLesCategories.size()
			 && indiceCategorieCourante < 5;
			 indiceCategorieCourante++) {
			categorieCourante = new Label(toutesLesCategories.get(indiceCategorieCourante).getIntitule());
			categorieCourante.getStyleClass().add("intituleCategorieQuestion");
			
			if (!toutesLesCategories.get(indiceCategorieCourante)
					                .getIntitule().equals("Général")) {
				categorieCourante.getStyleClass().add("labelCliquable");
			}
			
			vBoxCategories.getChildren().add(categorieCourante);
			
			final int indice = indiceCategorieCourante;
			categorieCourante.setOnMouseClicked(event -> {
			    actionRenommerCategorie(indice);
			});
		}
		System.out.println("Indice question après init : " + indiceCategorie);
		
	}
	
	private void actionRenommerCategorie(int indice) {
		ControleurNavigation.changerVue("CreationCategories.fxml");
	}
	
	@FXML
	private void actionBoutonPrecedent() {
		// On recule de 5 catégories.
		indiceCategorie -= 5;
		
		System.out.println("indiceCategorie : " + indiceCategorie);
		
	    // Calcul de l'indice de début pour les 5 questions précédentes
	    int indiceDebut = indiceCategorie;
	    int indiceFin = Math.min(indiceDebut + 5, toutesLesCategories.size());

	    // Effacer le contenu actuel du VBox
	    vBoxCategories.getChildren().clear();

	    // Afficher les 5 questions précédentes
	    for (int i = indiceDebut; i < indiceFin; i++) {
	        categorieCourante = new Label(toutesLesCategories.get(i).getIntitule());
	        categorieCourante.getStyleClass().add("intituleCategorieQuestion");
	        
	        if (!toutesLesCategories.get(i).getIntitule().equals("Général")) {
	        	categorieCourante.getStyleClass().add("labelCliquable");
	        }
	        
	        vBoxCategories.getChildren().add(categorieCourante);
	    }
	    
	    // Cacher le bouton "Précédent" s'il n'y a plus de questions précédentes
	    boutonPrecedent.setVisible(indiceCategorie < 5 ? false : true);
	    
	    // Afficher le bouton "Suivant" si il y a plus de 5 questions
	    if (toutesLesCategories.size() > 5) {
	        boutonSuivant.setVisible(true);
	    }
	}
	
	@FXML
	private void actionBoutonSuivant() {
		// On passe les 5 catégories précédentes.
		indiceCategorie += 5;
		
		System.out.println("indiceCategorie : " + indiceCategorie);
		
	    // Calcul de l'indice de départ pour les 5 prochaines questions
	    int indiceDebut = indiceCategorie;
	    int indiceFin = Math.min(indiceDebut + 5, toutesLesCategories.size());

	    // Effacer le contenu actuel du VBox
	    vBoxCategories.getChildren().clear();

	    // Afficher les 5 prochaines questions
	    for (int i = indiceDebut; i < indiceFin; i++) {
	        categorieCourante = new Label(toutesLesCategories.get(i).getIntitule());
	        categorieCourante.getStyleClass().add("intituleCategorieQuestion");
	        
	        if (!toutesLesCategories.get(i).getIntitule().equals("Général")) {
	        	categorieCourante.getStyleClass().add("labelCliquable");
	        }
	        
	        vBoxCategories.getChildren().add(categorieCourante);
	    }
	    
	    // Cacher le bouton "Suivant" s'il n'y a plus de questions
	    if (indiceFin >= toutesLesCategories.size()) {
	        boutonSuivant.setVisible(false);
	    }
	    boutonPrecedent.setVisible(true);
	}
	
	@FXML
	private void boutonAide() {
//		ControleurNavigation.changerVue("GestionDesCategories.fxml");
	}
	
	@FXML
	private void actionBoutonAnnuler() {
		ControleurNavigation.changerVue("AffichageCategories.fxml");
	}
	
}
