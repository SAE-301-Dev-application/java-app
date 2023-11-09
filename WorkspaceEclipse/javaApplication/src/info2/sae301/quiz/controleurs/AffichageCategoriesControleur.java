/*
 * AffichageCategoriesControleur.java								 7 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.Categorie;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Contrôleur FXML de la vue AffichageCategories qui affiche la liste des
 * catégories et propose d'en créer, renommer et supprimer.
 */
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
	
	/**
	 * Initialisation de la vue avec le style css correspondant et l'affichage
	 * des catégories et du bouton suivant.
	 */
	@FXML
	private void initialize() {
		NavigationControleur.getScene().getStylesheets()
		.add(getClass().getResource("/info2/sae301/quiz/vues/application.css")
				       .toExternalForm());
		
		afficherCategories();
	}
	
	/**
	 * Affiche 10 catégories au maximum et gère l'affichage des boutons
	 * précédent et suivant en fonction du nombre de catégories précédentes
	 * et suivantes.
	 */
	private void afficherCategories() {
	    // Calcul des indices pour l'affichage des catégories
	    int indiceDebut = indiceCategorie;
	    int indiceFin = Math.min(indiceDebut + 10, toutesLesCategories.size());
	    
	    // Effacer le contenu actuel du VBox
	    vBoxCategories.getChildren().clear();
	    
	    vBoxCategories.getStyleClass().add("vbox-categories-questions");
		
	    // Afficher les (indiceFin - indiceDebut) catégories
	    for (int i = indiceDebut; i < indiceFin; i++) {
	        categorieCourante = new Label(toutesLesCategories.get(i).getIntitule());
	        categorieCourante.getStyleClass().add("intituleCategorieQuestion");
	        vBoxCategories.getChildren().add(categorieCourante);
	    }
	    // Cacher le bouton "Précédent" s'il n'y a plus de catégories précédentes
	    boutonPrecedent.setVisible(!(indiceCategorie < 10));
	    
	    // Cacher le bouton "Suivant" s'il n'y a plus de catégories suivantes
	    boutonSuivant.setVisible(toutesLesCategories.size() > 10
	    		                 && indiceFin < toutesLesCategories.size());
	}
	
	/**
	 * Retrait de 10 catégories à l'indice de la première catégorie à afficher
	 * et affichage des 10 catégories précédentes. 
	 */
	@FXML
	private void actionBoutonPrecedent() {
		// On recule de 10 catégories
		indiceCategorie -= 10;
	    afficherCategories();
	}
	
	/**
	 * Ajout de 10 catégories à l'indice de la première catégorie à afficher
	 * et affichage des 10 catégories suivantes. 
	 */
	@FXML
	private void actionBoutonSuivant() {
		// On avance de 10 catégories
		indiceCategorie += 10;
	    afficherCategories();
	}
	
	/**
	 * TODO : coder action bouton aide
	 */
	@FXML
	private void actionBoutonAide() {
//		ControleurNavigation.changerVue("GestionDesCategories.fxml");
	}
	
	/**
	 * Redirection vers la vue ChoixRenommerCategories pour sélectionner la
	 * catégorie à renommer.
	 */
	@FXML
	private void actionBoutonRenommer() {
		NavigationControleur.changerVue("ChoixRenommerCategories.fxml");
	}
	
	/**
	 * Redirection vers la vue SuppressionCategories pour sélectionner la ou les
	 * catégorie(s) à supprimer.
	 */
	@FXML
	private void actionBoutonSupprimer() {
		NavigationControleur.changerVue("SuppressionCategories.fxml");
	}
	
    /**
	 * Redirection vers la vue MenuPrincipal.
     */
	@FXML
	private void actionBoutonRetour() {
		NavigationControleur.changerVue("MenuPrincipal.fxml");
	}
	
    /**
	 * Redirection vers la vue CreationCategories pour créer de nouvelles
	 * catégories.
     */
	@FXML
	private void actionBoutonCreer() {
		NavigationControleur.changerVue("CreationCategories.fxml");
	}
	
}
