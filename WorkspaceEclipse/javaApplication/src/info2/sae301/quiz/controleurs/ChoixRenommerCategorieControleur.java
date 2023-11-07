package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.Categorie;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ChoixRenommerCategorieControleur {
	
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
		NavigationControleur.getScene().getStylesheets()
		.add(getClass().getResource("/info2/sae301/quiz/vues/application.css")
				       .toExternalForm());
		
		if (toutesLesCategories.size() < 5) {
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
		
		if (indiceCategorie < 5) {
			boutonPrecedent.setVisible(false);
		}

		boutonSuivant.setVisible(toutesLesCategories.size() <= 5 ? false : true);
		
		for (int indiceCategorieCourante = 0;
			 indiceCategorieCourante < toutesLesCategories.size()
			 && indiceCategorieCourante < 5;
			 indiceCategorieCourante++) {
			String intituleCategorie = toutesLesCategories.get(indiceCategorieCourante).getIntitule();
			
			categorieCourante = new Label(intituleCategorie);
			categorieCourante.getStyleClass().add("intituleCategorieQuestion");
			
			if (!intituleCategorie.equals("Général")) {
				categorieCourante.getStyleClass().add("labelCliquable");
			}
			
			vBoxCategories.getChildren().add(categorieCourante);
			
			if (!intituleCategorie.equals("Général")) {
				final String intitule = intituleCategorie;
				categorieCourante.setOnMouseClicked(event -> {
				    actionRenommerCategorie(intitule);
				});
			}
		}
	}
	
	/**
	 * Changement de vue et modification de l'attribut de la catégorie sélectionnée
	 * dans la classe de sauvegarde des paramètres, catégories et questions.
	 * 
	 * @param intitule L'intitulé de la catégorie sélectionnée à sauvegarder.
	 */
	private void actionRenommerCategorie(String intitule) {
		this.jeu.setIntituleCategorieSelectionnee(intitule);
		NavigationControleur.changerVue("RenommerCategories.fxml");
	}
	
	@FXML
	private void actionBoutonPrecedent() {
		// On recule de 5 catégories.
		indiceCategorie -= 5;
		
	    // Calcul de l'indice de début pour les 5 questions précédentes
	    int indiceDebut = indiceCategorie;
	    int indiceFin = Math.min(indiceDebut + 5, toutesLesCategories.size());

	    // Effacer le contenu actuel du VBox
	    vBoxCategories.getChildren().clear();

	    // Afficher les 5 questions précédentes
	    for (int i = indiceDebut; i < indiceFin; i++) {
			String intituleCategorie = toutesLesCategories.get(i).getIntitule();
			
	        categorieCourante = new Label(intituleCategorie);
	        categorieCourante.getStyleClass().add("intituleCategorieQuestion");
	        
	        if (!intituleCategorie.equals("Général")) {
	        	categorieCourante.getStyleClass().add("labelCliquable");
	        }
	        
			vBoxCategories.getChildren().add(categorieCourante);
			
			if (!intituleCategorie.equals("Général")) {
				final String intitule = intituleCategorie;
				categorieCourante.setOnMouseClicked(event -> {
				    actionRenommerCategorie(intitule);
				});
			}
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
		
	    // Calcul de l'indice de départ pour les 5 prochaines questions
	    int indiceDebut = indiceCategorie;
	    int indiceFin = Math.min(indiceDebut + 5, toutesLesCategories.size());

	    // Effacer le contenu actuel du VBox
	    vBoxCategories.getChildren().clear();

	    // Afficher les 5 prochaines questions
	    for (int i = indiceDebut; i < indiceFin; i++) {
			String intituleCategorie = toutesLesCategories.get(i).getIntitule();
			
	        categorieCourante = new Label(intituleCategorie);
	        categorieCourante.getStyleClass().add("intituleCategorieQuestion");
	        
	        if (!intituleCategorie.equals("Général")) {
	        	categorieCourante.getStyleClass().add("labelCliquable");
	        }
	        
	        vBoxCategories.getChildren().add(categorieCourante);
	        
			if (!intituleCategorie.equals("Général")) {
				final String intitule = intituleCategorie;
				categorieCourante.setOnMouseClicked(event -> {
				    actionRenommerCategorie(intitule);
				});
			}
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
		NavigationControleur.changerVue("AffichageCategories.fxml");
	}
	
}
