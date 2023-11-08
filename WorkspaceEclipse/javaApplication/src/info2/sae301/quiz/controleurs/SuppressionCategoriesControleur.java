/*
 * SuppressionCategoriesControleur.java								 7 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;


import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.Categorie;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Contrôleur FXML de la vue SuppressionCategories qui affiche la liste des
 * catégories avec des checkbox pour sélectionner celles à supprimer.
 */
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
	
	/** Toutes les catégories dont la checkbox de sélection a été cochée. */
	private ArrayList<String> categoriesSelectionnees = new ArrayList<String>();
	
	private Label categorieCourante;
	
	private HBox ligneCategorie;
	
	private CheckBox checkBoxCategorie;
	
	/** Les checkbox ajoutées devant les catégories. */
	private ArrayList<CheckBox> toutesLesCheckBox = new ArrayList<CheckBox>();
	
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
	    vBoxCategories.getStyleClass().add("vbox-categories-questions");
	    vBoxCategories.getChildren().clear();
		
	    // Afficher les (indiceFin - indiceDebut) catégories
	    for (int i = indiceDebut; i < indiceFin; i++) {
			ligneCategorie = new HBox();
			
			String intituleCategorie = toutesLesCategories.get(i).getIntitule();
			
			checkBoxCategorie = new CheckBox();
			checkBoxCategorie.getStyleClass().add("checkbox-margin");
			checkBoxCategorie.setId("" + i);
			
			// Si la checkbox n'a pas déjà été ajoutée
			if (i >= toutesLesCheckBox.size()) {
				toutesLesCheckBox.add(checkBoxCategorie);
			} else {
				checkBoxCategorie = toutesLesCheckBox.get(i);
			}
			
	        categorieCourante = new Label(intituleCategorie);
	        categorieCourante.getStyleClass().add("intituleCategorieQuestion");
	        categorieCourante.getStyleClass().add("intitule-padding-left");
	        
	        ligneCategorie.getChildren().add(checkBoxCategorie);
	        
	        if (!intituleCategorie.equals("Général")) {
	        	final int INDICE = i;
				
				checkBoxCategorie.setOnMouseClicked(event -> {
					selectionnerCategorie(INDICE);
				});
			} else {
				checkBoxCategorie.setDisable(true);
			}
	        
			ligneCategorie.getChildren().add(categorieCourante);
			vBoxCategories.getChildren().add(ligneCategorie);
	    }
	    // Cacher le bouton "Précédent" s'il n'y a plus de catégories précédentes
	    boutonPrecedent.setVisible(!(indiceCategorie < 10));
	    
	    // Cacher le bouton "Suivant" s'il n'y a plus de catégories suivantes
	    boutonSuivant.setVisible(toutesLesCategories.size() > 10
	    		                 && indiceFin < toutesLesCategories.size());
	}
	
	/**
	 * Ajout de la catégorie correspondante à l'indiceen paramètre à la liste
	 * des catégories sélectionnées.
	 * 
	 * @param indice Indice de la catégorie cliquée.
	 */
	private void selectionnerCategorie(int indice) {
		
		if (toutesLesCheckBox.get(indice).isSelected()) {
			categoriesSelectionnees.add(jeu.getToutesLesCategories()
                                   .get(indice).getIntitule());	
		} else {
			categoriesSelectionnees.remove(jeu.getToutesLesCategories()
                                              .get(indice).getIntitule());	
		}
		System.out.println("Catégories sélectionnées : ");
		for (String categorie : categoriesSelectionnees) {
			System.out.println("- " + categorie);
		}
		System.out.println();
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
	 * Redirection vers la vue AffichageCategories.
     */
	@FXML
	private void actionBoutonAnnuler() {
		NavigationControleur.changerVue("AffichageCategories.fxml");
	}
	
	@FXML
	private void actionBoutonSupprimer() {
		final String TITRE_SELECTION_VIDE = "Aucun catégorie sélectionnée";
		final String MESSAGE_SELECTION_VIDE = "Veuillez sélectionner une catégorie"
											+ " à supprimer ou cliquer sur Annuler.";
		
		final String TITRE_POPUP_CONFIRM = "Confirmer la suppression";
		final String DEMANDE_CONFIRMATION = "Etes-vous sûr(e) de vouloir supprimer "
											+ categoriesSelectionnees.size()
											+ " catégories ?";
		
		if (categoriesSelectionnees.size() == 0) {
			AlerteControleur.autreAlerte(MESSAGE_SELECTION_VIDE,
										 TITRE_SELECTION_VIDE,
										 AlertType.ERROR);
		} else {
			boolean confirmerSuppression
			= AlerteControleur.alerteConfirmation(DEMANDE_CONFIRMATION,
					                              TITRE_POPUP_CONFIRM);
			
			if (confirmerSuppression) {
				jeu.supprimer(jeu.getCategoriesParIntitules(categoriesSelectionnees));
				NavigationControleur.changerVue("AffichageCategories.fxml");
			}	
		}
	}
	
}
