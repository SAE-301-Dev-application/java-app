/*
p * SuppressionCategoriesControleur.java								 7 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Jeu;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

/**
 * Contrôleur FXML de la vue SuppressionCategories qui affiche la 
 * liste des catégories avec des checkbox pour sélectionner celles
 * à supprimer.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class SuppressionCategoriesControleur {
	
	/**
	 * Récupération de l'instance du jeu créée dans la 
	 * classe Quiz.
	 * Cette instance permet la gestion des questions 
	 * et catégories.
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
	private ArrayList<String> categoriesSelectionnees = new ArrayList<>();
	
	/** Les checkbox ajoutées devant les catégories. */
	private ArrayList<CheckBox> toutesLesCheckBoxs = new ArrayList<>();
	
	/**
	 * Initialisation de la vue avec le style css correspondant 
	 * et l'affichage des catégories et du bouton suivant.
	 */
	@FXML
	private void initialize() {
		indiceCategorie = AffichageCategoriesControleur.indiceCategorie;
		NavigationControleur.getScene().getStylesheets()
		.add(getClass().getResource("/info2/sae301/quiz/vues/application.css")
				       .toExternalForm());
		initialiserToutesLesCheckboxs();
		afficherCategories();
	}
	/**
	 * Initialisation de toutes les checkboxs représentent
	 * les catégories.
	 */
	private void initialiserToutesLesCheckboxs() {
		toutesLesCheckBoxs.clear();
		for (int i = 0; i < toutesLesCategories.size(); i++) {
			CheckBox checkBoxCategorie = new CheckBox();
	    	checkBoxCategorie.setId("" + i);
	    	checkBoxCategorie.setText(toutesLesCategories.get(i).getIntitule());
	    	checkBoxCategorie.getStyleClass().add("checkbox-margin");
			checkBoxCategorie.getStyleClass().add("intituleCategorieQuestion");
			checkBoxCategorie.getStyleClass().add("intitule-padding-left");
//			checkBoxQuestion.setVisible(false);
			this.toutesLesCheckBoxs.add(checkBoxCategorie);
			if (!checkBoxCategorie.getText().equals("Général")) {
	        	final int INDICE = i;
				
	        	checkBoxCategorie.setOnMouseClicked(event -> {
					selectionnerCategorie(INDICE);
				});
			} else {
				checkBoxCategorie.setDisable(true);
			}
		}
	}

	/**
	 * Affiche 10 catégories au maximum et gère l'affichage des boutons
	 * précédent et suivant en fonction du nombre de catégories 
	 * précédentes et suivantes.
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
	        vBoxCategories.getChildren().add(toutesLesCheckBoxs.get(i));
	        
	    }
	    // Cacher le bouton "Précédent" s'il n'y a plus de catégories précédentes
	    boutonPrecedent.setVisible(!(indiceCategorie < 10));
	    
	    // Cacher le bouton "Suivant" s'il n'y a plus de catégories suivantes
	    boutonSuivant.setVisible(toutesLesCategories.size() > 10
	    		                 && indiceFin < toutesLesCategories.size());
	}
	
	/**
	 * Ajout de la catégorie correspondante à l'indiceen paramètre 
	 * à la liste des catégories sélectionnées.
	 * 
	 * @param indice Indice de la catégorie cliquée.
	 */
	private void selectionnerCategorie(int indice) {
		final String INTITULE_CATEGORIE
		= jeu.getToutesLesCategories().get(indice).getIntitule();
		
		if (toutesLesCheckBoxs.get(indice).isSelected()) {
			categoriesSelectionnees.add(INTITULE_CATEGORIE);	
		} else {
			categoriesSelectionnees.remove(INTITULE_CATEGORIE);	
		}
		
		System.out.println("Catégories sélectionnées : ");
		
		for (String categorie : categoriesSelectionnees) {
			System.out.println("- " + categorie);
		}
		System.out.println();
	}
	
	/**
	 * Retrait de 10 catégories à l'indice de la première catégorie 
	 * à afficher et affichage des 10 catégories précédentes. 
	 */
	@FXML
	private void actionBoutonPrecedent() {
		// On recule de 10 catégories
		indiceCategorie -= 10;
	    afficherCategories();
	}
	
	/**
	 * Ajout de 10 catégories à l'indice de la première catégorie 
	 * à afficher et affichage des 10 catégories suivantes. 
	 */
	@FXML
	private void actionBoutonSuivant() {
		// On avance de 10 catégories
		indiceCategorie += 10;
	    afficherCategories();
	}
	
	/**
	 * Redirection vers le pop-up pour la suppression des categories
	 */
	@FXML
	private void actionBoutonAide() {
		AlerteControleur.aide(AffichageCategoriesControleur.AIDE_TITRE,
							  AffichageCategoriesControleur.AIDE_TEXTE);
	}
	
    /**
	 * Redirection vers la vue AffichageCategories.
     */
	@FXML
	private void actionBoutonAnnuler() {
		AffichageCategoriesControleur.indiceCategorie = indiceCategorie;
		NavigationControleur.changerVue("AffichageCategories.fxml");
	}
	
	@FXML
	private void actionBoutonSupprimer() {
		final String TITRE_SELECTION_VIDE = "Aucune catégorie n'est sélectionnée";
		final String MESSAGE_SELECTION_VIDE = "Veuillez sélectionner une catégorie"
											+ " à supprimer ou cliquer sur Annuler.";
		
		final String TITRE_POPUP_CONFIRM = "Confirmer la suppression";
		final String DEMANDE_CONFIRMATION = "Êtes-vous sûr(e) de vouloir supprimer "
											+ categoriesSelectionnees.size()
											+ " catégorie(s) ?\nLes questions dans ces"
											+ " catégories seront également supprimées.";
		
		boolean confirmerSuppression;
		
		if (categoriesSelectionnees.size() == 0) {
			AlerteControleur.autreAlerte(MESSAGE_SELECTION_VIDE,
										 TITRE_SELECTION_VIDE,
										 AlertType.ERROR);
		} else {
			confirmerSuppression
			= AlerteControleur.alerteConfirmation(DEMANDE_CONFIRMATION,
					                              TITRE_POPUP_CONFIRM);
			
			if (confirmerSuppression) {
				jeu.supprimer(jeu.getCategoriesParIntitules(categoriesSelectionnees));
				indiceCategorieApresSuppression();
				NavigationControleur.changerVue("AffichageCategories.fxml");
			}	
		}
	}
	
	/**
	 * Adapte indiceCategorie en fonction du nombre de
	 * suppression de catégorie et de la page courante.
	 */
	private void indiceCategorieApresSuppression() {
		for (;indiceCategorie >= toutesLesCheckBoxs.size()
				- categoriesSelectionnees.size(); indiceCategorie-=10);
		AffichageCategoriesControleur.indiceCategorie = indiceCategorie;
		
	}
	
}
