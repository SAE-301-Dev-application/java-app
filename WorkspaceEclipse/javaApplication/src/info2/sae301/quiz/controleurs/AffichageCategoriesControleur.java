package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.Categorie;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AffichageCategoriesControleur {
	
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
	
	@FXML
	private void initialize() {
		ControleurNavigation.getScene().getStylesheets()
		.add(getClass().getResource("/info2/sae301/quiz/vues/application.css")
				       .toExternalForm());
		
		if (toutesLesCategories.size() < 10) {
			jeu.creerCategorie("2ème catégorie");
			jeu.creerCategorie("3ème catégorie");
			jeu.creerCategorie("4ème catégorie");
			jeu.creerCategorie("5ème catégorie");
			jeu.creerCategorie("6ème catégorie");
			jeu.creerCategorie("7ème catégorie");
			jeu.creerCategorie("8ème catégorie");
			jeu.creerCategorie("9ème catégorie");
			jeu.creerCategorie("10ème catégorie");
			jeu.creerCategorie("11ème catégorie");
			jeu.creerCategorie("12ème catégorie");
			jeu.creerCategorie("13ème catégorie");
			jeu.creerCategorie("14ème catégorie");
			jeu.creerCategorie("15ème catégorie");
			jeu.creerCategorie("16ème catégorie");
			jeu.creerCategorie("17ème catégorie");
			jeu.creerCategorie("18ème catégorie");
			jeu.creerCategorie("19ème catégorie");
			jeu.creerCategorie("20ème catégorie");
			jeu.creerCategorie("21ème catégorie");
			jeu.creerCategorie("22ème catégorie");
			jeu.creerCategorie("23ème catégorie");
			jeu.creerCategorie("24ème catégorie");
			jeu.creerCategorie("25ème catégorie");
			jeu.creerCategorie("26ème catégorie");
			jeu.creerCategorie("27ème catégorie");
			jeu.creerCategorie("28ème catégorie");
			jeu.creerCategorie("29ème catégorie");
			jeu.creerCategorie("30ème catégorie");
			
			toutesLesCategories = jeu.getToutesLesCategories();
		}
		
		if (indiceCategorie < 10) {
			boutonPrecedent.setVisible(false);
		}

		boutonSuivant.setVisible(toutesLesCategories.size() <= 10 ? false : true);
		
		for (int indiceCategorieCourante = 0;
			 indiceCategorieCourante < toutesLesCategories.size()
			 && indiceCategorieCourante < 10;
			 indiceCategorieCourante++) {
			categorieCourante = new Label(toutesLesCategories.get(indiceCategorieCourante).getIntitule());
			categorieCourante.getStyleClass().add("intituleCategorieQuestion");
			vBoxCategories.getChildren().add(categorieCourante);	
		}
	}
	
	@FXML
	private void actionBoutonPrecedent() {
		// On recule de 10 catégories.
		indiceCategorie -= 10;
		
	    // Calcul de l'indice de début pour les 10 questions précédentes
	    int indiceDebut = indiceCategorie;
	    int indiceFin = Math.min(indiceDebut + 10, toutesLesCategories.size());

	    // Effacer le contenu actuel du VBox
	    vBoxCategories.getChildren().clear();

	    // Afficher les 10 questions précédentes
	    for (int i = indiceDebut; i < indiceFin; i++) {
	        categorieCourante = new Label(toutesLesCategories.get(i).getIntitule());
	        categorieCourante.getStyleClass().add("intituleCategorieQuestion");
	        vBoxCategories.getChildren().add(categorieCourante);
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
		
	    // Calcul de l'indice de départ pour les 10 prochaines questions
	    int indiceDebut = indiceCategorie;
	    int indiceFin = Math.min(indiceDebut + 10, toutesLesCategories.size());

	    // Effacer le contenu actuel du VBox
	    vBoxCategories.getChildren().clear();

	    // Afficher les 10 prochaines questions
	    for (int i = indiceDebut; i < indiceFin; i++) {
	        categorieCourante = new Label(toutesLesCategories.get(i).getIntitule());
	        categorieCourante.getStyleClass().add("intituleCategorieQuestion");
	        vBoxCategories.getChildren().add(categorieCourante);
	    }
	    
	    // Cacher le bouton "Suivant" s'il n'y a plus de questions
	    if (indiceFin >= toutesLesCategories.size()) {
	        boutonSuivant.setVisible(false);
	    }
	    boutonPrecedent.setVisible(true);
	}
	
	@FXML
	private void actionBoutonAide() {
//		ControleurNavigation.changerVue("GestionDesCategories.fxml");
	}
	
	@FXML
	private void actionBoutonRenommer() {
		ControleurNavigation.changerVue("ChoixRenommerCategories.fxml");
	}
	
	@FXML
	private void actionBoutonSupprimer() {
		ControleurNavigation.changerVue("SuppressionCategories.fxml");
	}
	
	@FXML
	private void actionBoutonRetour() {
		ControleurNavigation.changerVue("MenuPrincipal.fxml");
	}
	
	@FXML
	private void actionBoutonCreer() {
		ControleurNavigation.changerVue("CreationCategories.fxml");
	}
	
}
