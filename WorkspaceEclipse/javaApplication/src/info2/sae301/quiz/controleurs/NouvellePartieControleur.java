/*
 * NouvellePartie.java							            10 nov. 2023
 * IUT de Rodez, pas de copyright, ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

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
	
	@FXML
	private void initialize() {
		this.choix5Questions();
	}
	
	/**
	 * Choix de 5 questions pour le quiz.
	 */
	@FXML
	private void choix5Questions() {
		this.nombreQuestions = 5;
		
		this.checkBox5Questions.setSelected(true);
		this.checkBox10Questions.setSelected(false);
		this.checkBox20Questions.setSelected(false);
	}
	
	/**
	 * Choix de 10 questions pour le quiz.
	 */
	@FXML
	private void choix10Questions() {
		this.nombreQuestions = 10;
		
		this.checkBox5Questions.setSelected(false);
		this.checkBox10Questions.setSelected(true);
		this.checkBox20Questions.setSelected(false);
	}
	
	/**
	 * Choix de 20 questions pour le quiz.
	 */
	@FXML
	private void choix20Questions() {
		this.nombreQuestions = 20;
		
		this.checkBox5Questions.setSelected(false);
		this.checkBox10Questions.setSelected(false);
		this.checkBox20Questions.setSelected(true);
	}

	/**
	 * Choix du niveau de difficilté "Indifférent" pour le quiz.
	 */
	@FXML
	private void choixDifficulteIndifferent() {
		this.difficulte = 0;
		
		this.checkBoxDifficulteIndifferent.setSelected(true);
		this.checkBoxDifficulteFacile.setSelected(false);
		this.checkBoxDifficulteMoyen.setSelected(false);
		this.checkBoxDifficulteDifficile.setSelected(false);
	}
	
	/**
	 * Choix du niveau de difficilté "Facile" pour le quiz.
	 */
	@FXML
	private void choixDifficulteFacile() {
		this.difficulte = 1;
		
		this.checkBoxDifficulteIndifferent.setSelected(false);
		this.checkBoxDifficulteFacile.setSelected(true);
		this.checkBoxDifficulteMoyen.setSelected(false);
		this.checkBoxDifficulteDifficile.setSelected(false);
	}
	
	/**
	 * Choix du niveau de difficilté "Moyen" pour le quiz.
	 */
	@FXML
	private void choixDifficulteMoyen() {
		this.difficulte = 2;
		
		this.checkBoxDifficulteIndifferent.setSelected(false);
		this.checkBoxDifficulteFacile.setSelected(false);
		this.checkBoxDifficulteMoyen.setSelected(true);
		this.checkBoxDifficulteDifficile.setSelected(false);
	}
	
	/**
	 * Choix du niveau de difficilté "Difficile" pour le quiz.
	 */
	@FXML
	private void choixDifficulteDifficile() {
		this.difficulte = 3;
		
		this.checkBoxDifficulteIndifferent.setSelected(false);
		this.checkBoxDifficulteFacile.setSelected(false);
		this.checkBoxDifficulteMoyen.setSelected(false);
		this.checkBoxDifficulteDifficile.setSelected(true);
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