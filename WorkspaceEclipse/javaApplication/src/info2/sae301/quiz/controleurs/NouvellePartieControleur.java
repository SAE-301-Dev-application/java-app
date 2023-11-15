/*
 * NouvellePartie.java							            10 nov. 2023
 * IUT de Rodez, pas de copyright, ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.ParametresPartie;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 * Contrôleur FXML de la vue MenuPrincipal lancée par défaut lors du
 * démarrage de l'application.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class NouvellePartieControleur {
	
	/** Nombre de questions du futur quiz. */
	private int nombreQuestions;
	
	/**
	 * Niveau de difficulté du quiz :
	 * - 0 = indifférent
	 * - 1 = facile
	 * - 2 = moyen
	 * - 3 = difficile
	 */
	private int difficulte;
	
	private Jeu jeu;
	
	private ParametresPartie parametres;
	
	/** Checkbox "5 questions". */
	@FXML
	private CheckBox checkBox5Questions;
	
	/** Checkbox "10 questions". */
	@FXML
	private CheckBox checkBox10Questions;
	
	/** Checkbox "20 questions". */
	@FXML
	private CheckBox checkBox20Questions;
	
	/** Checkbox de difficulté "indifférent". */
	@FXML
	private CheckBox checkBoxDifficulteIndifferent;
	
	/** Checkbox de difficulté "facile". */
	@FXML
	private CheckBox checkBoxDifficulteFacile;
	
	/** Checkbox de difficulté "moyen". */
	@FXML
	private CheckBox checkBoxDifficulteMoyen;
	
	/** Checkbox de difficulté "difficile". */
	@FXML
	private CheckBox checkBoxDifficulteDifficile;
	
	/** Conteneur des catégories de question. */
	@FXML
	private AnchorPane conteneurCategories;
	
	@FXML
	private void initialize() {
		/*
		 * Valeurs par défaut :
		 * - 5 questions
		 * - Difficulté "Indifférent"  
		 */
		
		this.parametres = new ParametresPartie();
		
		this.choix5Questions();
		this.choixDifficulteIndifferent();
		
		/*
		 * Chargement des catégories de question.
		 */
		
		CheckBox checkBoxCategorie;
		
		for (Categorie categorieCourante: this.jeu.getToutesLesCategories()) {
			checkBoxCategorie = new CheckBox();
			checkBoxCategorie.setOnAction(event -> {
				// TODO
			});
			
			this.conteneurCategories.getChildren().add(checkBoxCategorie);  // TODO: ajouter élément catégorie.
		}
	}
	
	/**
	 * Choix de 5 questions pour le quiz.
	 */
	@FXML
	private void choix5Questions() {
		this.checkBox5Questions.setSelected(true);
		this.checkBox10Questions.setSelected(false);
		this.checkBox20Questions.setSelected(false);
		
		this.parametres.setNombreQuestions(5);
	}
	
	/**
	 * Choix de 10 questions pour le quiz.
	 */
	@FXML
	private void choix10Questions() {
		this.checkBox5Questions.setSelected(false);
		this.checkBox10Questions.setSelected(true);
		this.checkBox20Questions.setSelected(false);
		
		this.parametres.setNombreQuestions(10);
	}
	
	/**
	 * Choix de 20 questions pour le quiz.
	 */
	@FXML
	private void choix20Questions() {
		this.checkBox5Questions.setSelected(false);
		this.checkBox10Questions.setSelected(false);
		this.checkBox20Questions.setSelected(true);

		this.parametres.setNombreQuestions(20);
	}

	/**
	 * Choix du niveau de difficilté "Indifférent" pour le quiz.
	 */
	@FXML
	private void choixDifficulteIndifferent() {
		this.checkBoxDifficulteIndifferent.setSelected(true);
		this.checkBoxDifficulteFacile.setSelected(false);
		this.checkBoxDifficulteMoyen.setSelected(false);
		this.checkBoxDifficulteDifficile.setSelected(false);
		
		this.parametres.setDifficulteQuestions(0);
	}
	
	/**
	 * Choix du niveau de difficilté "Facile" pour le quiz.
	 */
	@FXML
	private void choixDifficulteFacile() {
		this.checkBoxDifficulteIndifferent.setSelected(false);
		this.checkBoxDifficulteFacile.setSelected(true);
		this.checkBoxDifficulteMoyen.setSelected(false);
		this.checkBoxDifficulteDifficile.setSelected(false);
		
		this.parametres.setDifficulteQuestions(1);
	}
	
	/**
	 * Choix du niveau de difficilté "Moyen" pour le quiz.
	 */
	@FXML
	private void choixDifficulteMoyen() {
		this.checkBoxDifficulteIndifferent.setSelected(false);
		this.checkBoxDifficulteFacile.setSelected(false);
		this.checkBoxDifficulteMoyen.setSelected(true);
		this.checkBoxDifficulteDifficile.setSelected(false);
		
		this.parametres.setDifficulteQuestions(2);
	}
	
	/**
	 * Choix du niveau de difficilté "Difficile" pour le quiz.
	 */
	@FXML
	private void choixDifficulteDifficile() {
		this.checkBoxDifficulteIndifferent.setSelected(false);
		this.checkBoxDifficulteFacile.setSelected(false);
		this.checkBoxDifficulteMoyen.setSelected(false);
		this.checkBoxDifficulteDifficile.setSelected(true);
		
		this.parametres.setDifficulteQuestions(3);
	}
	
	@FXML
	private void selectionCategorie(Categorie categorieConcernee) {
		ArrayList<Categorie> categoriesSelectionnees;
		categoriesSelectionnees = this.parametres.getCategoriesSelectionnees();
		
		if (categoriesSelectionnees.contains(categorieConcernee)) {
			this.parametres.deselectionnerCategorie(categorieConcernee);
		} else {
			this.parametres.selectionnerCategorie(categorieConcernee);
		}
	}
	
	@FXML
	private void actionBoutonAide() {
		//AlerteControleur.aide(AIDE_TITRE, AIDE_TEXTE);
	}
	
	@FXML
	private void actionBoutonRetour() {
		//
	}
	
	@FXML
	private void actionBoutonCreer() {
		//
	}
	
}