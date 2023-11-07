/*
 * ChoixRenommerCategorieControleur.java							 7 nov. 2023
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
 * Contrôleur FXML de la vue ChoixRenommerCategories qui affiche la liste des
 * catégories sous forme de boutons.
 */
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
	 * Affiche 5 catégories au maximum et gère l'affichage des boutons
	 * précédent et suivant en fonction du nombre de catégories précédentes
	 * et suivantes.
	 */
	private void afficherCategories() {
	    // Calcul des indices pour l'affichage des catégories
	    int indiceDebut = indiceCategorie;
	    int indiceFin = Math.min(indiceDebut + 5, toutesLesCategories.size());
	    
	    // Effacer le contenu actuel du VBox
	    vBoxCategories.getChildren().clear();
		
	    // Afficher les (indiceFin - indiceDebut) catégories
	    for (int i = indiceDebut; i < indiceFin; i++) {
	    	String intituleCategorie = toutesLesCategories.get(i).getIntitule();
	    	
	        categorieCourante = new Label(intituleCategorie);
	        categorieCourante.getStyleClass().add("intituleCategorieQuestion");
	        categorieCourante.getStyleClass()
	        .add("label" + (intituleCategorie.equals("Général") ? "Non" : "")
	        		     + "Cliquable");
	        vBoxCategories.getChildren().add(categorieCourante);
	        
	        if (!intituleCategorie.equals("Général")) {
		        final String intitule = intituleCategorie;
				categorieCourante.setOnMouseClicked(event -> {
				    actionRenommerCategorie(intitule);
				});	
	        }
	    }
	    // Cacher le bouton "Précédent" s'il n'y a plus de catégories précédentes
	    boutonPrecedent.setVisible(indiceCategorie < 5 ? false : true);
	    
	    // Cacher le bouton "Suivant" s'il n'y a plus de catégories suivantes
	    boutonSuivant.setVisible(toutesLesCategories.size() > 5
	    		                 && indiceFin < toutesLesCategories.size()
	    		                 ? true
	    		                 : false);
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
	
	/**
	 * Retrait de 5 catégories à l'indice de la première catégorie à afficher
	 * et affichage des 5 catégories précédentes. 
	 */
	@FXML
	private void actionBoutonPrecedent() {
		// On recule de 5 catégories
		indiceCategorie -= 5;
		afficherCategories();
	}
	
	/**
	 * Ajout de 5 catégories à l'indice de la première catégorie à afficher
	 * et affichage des 5 catégories suivantes. 
	 */
	@FXML
	private void actionBoutonSuivant() {
		// On avance de 5 catégories
		indiceCategorie += 5;
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
	 * Redirection vers la vue AffichageCategories.
	 */
	@FXML
	private void actionBoutonAnnuler() {
		NavigationControleur.changerVue("AffichageCategories.fxml");
	}
	
}
