package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.Categorie;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SuppressionCategoriesControleur {
	
	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private Jeu jeu = Quiz.jeu;
	
	@FXML
	private VBox vBoxCategories;
	
	@FXML
	private Button boutonPrecedent;
	
	@FXML
	private Button boutonSuivant;
	
	/** Indice de la première catégorie affichée sur la "page" courante. */
	private int indiceCategorie = 0; 
	
	private ArrayList<Categorie> toutesLesCategories = jeu.getToutesLesCategories();
	
	private Label categorieCourante;
	
	private HBox ligneCategorie;
	
	private CheckBox caseCategorie;
	
	@FXML
	private void initialize() {
		
		if (indiceCategorie < 10) {
			boutonPrecedent.setVisible(false);
		}

		boutonSuivant.setVisible(toutesLesCategories.size() <= 10 ? false : true);
		
		for (int indiceCategorieCourante = 0;
			 indiceCategorieCourante < toutesLesCategories.size()
			 && indiceCategorieCourante < 10;
			 indiceCategorieCourante++) {
			
			ligneCategorie = new HBox();
			caseCategorie = new CheckBox();
			
			categorieCourante = new Label(toutesLesCategories.get(indiceCategorieCourante).getIntitule());
			categorieCourante.getStyleClass().add("intituleCategorieQuestion");
			
			if (!toutesLesCategories.get(indiceCategorieCourante)
					                .getIntitule().equals("Général")) {
				ligneCategorie.getChildren().add(caseCategorie);
			}
			ligneCategorie.getChildren().add(categorieCourante);
			
			vBoxCategories.getChildren().add(ligneCategorie);
		}
	}

	@FXML
	private void actionBoutonPrecedent() {
		// On recule de 10 catégories.
		indiceCategorie -= 10;
		
		System.out.println("indiceCategorie : " + indiceCategorie);
		
	    // Calcul de l'indice de début pour les 10 questions précédentes
	    int indiceDebut = indiceCategorie;
	    int indiceFin = Math.min(indiceDebut + 10, toutesLesCategories.size());

	    // Effacer le contenu actuel du VBox
	    vBoxCategories.getChildren().clear();

	    // Afficher les 10 questions précédentes
	    for (int i = indiceDebut; i < indiceFin; i++) {
	        categorieCourante = new Label(toutesLesCategories.get(i).getIntitule());
	        categorieCourante.getStyleClass().add("intituleCategorieQuestion");
	        
			ligneCategorie = new HBox();
			caseCategorie = new CheckBox();
	        
			if (!toutesLesCategories.get(i).getIntitule().equals("Général")) {
				ligneCategorie.getChildren().add(caseCategorie);
			}
			ligneCategorie.getChildren().add(categorieCourante);
			
			vBoxCategories.getChildren().add(ligneCategorie);
	    }
	    
	    // Cacher le bouton "Précédent" s'il n'y a plus de questions précédentes
	    boutonPrecedent.setVisible(indiceCategorie < 10 ? false : true);
	    
	    // Afficher le bouton "Suivant" si il y a plus de 10 questions
	    if (toutesLesCategories.size() > 10) {
	        boutonSuivant.setVisible(true);
	    }
	}
	
	@FXML
	private void actionBoutonSuivant() {
		// On passe les 10 catégories précédentes.
		indiceCategorie += 10;
		
		System.out.println("indiceCategorie : " + indiceCategorie);
		
	    // Calcul de l'indice de départ pour les 10 prochaines questions
	    int indiceDebut = indiceCategorie;
	    int indiceFin = Math.min(indiceDebut + 10, toutesLesCategories.size());

	    // Effacer le contenu actuel du VBox
	    vBoxCategories.getChildren().clear();

	    // Afficher les 10 prochaines questions
	    for (int i = indiceDebut; i < indiceFin; i++) {
	        categorieCourante = new Label(toutesLesCategories.get(i).getIntitule());
	        categorieCourante.getStyleClass().add("intituleCategorieQuestion");
	        
			ligneCategorie = new HBox();
			caseCategorie = new CheckBox();
	        
			if (!toutesLesCategories.get(i).getIntitule().equals("Général")) {
				ligneCategorie.getChildren().add(caseCategorie);
			}
			ligneCategorie.getChildren().add(categorieCourante);
			
			vBoxCategories.getChildren().add(ligneCategorie);
	    }
	    
	    // Cacher le bouton "Suivant" s'il n'y a plus de questions
	    if (indiceFin >= toutesLesCategories.size()) {
	        boutonSuivant.setVisible(false);
	    }
	    boutonPrecedent.setVisible(true);
	}
	
	@FXML
	private void actionBoutonAide() {
//		ControleurNavigation.changerVue("GestionDesQuestions.fxml");  // TODO: implémenter la fenêtre d'aide
	}
	
	@FXML
	private void actionBoutonAnnuler() {
		NavigationControleur.changerVue("AffichageCategories.fxml");
	}
	
	@FXML
	private void actionBoutonSupprimer() {
		// TODO: suppression à implémenter
	}
	
	
}
