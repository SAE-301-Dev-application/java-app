/*
 * NouvellePartie.java							            10 nov. 2023
 * IUT de Rodez, pas de copyright, ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
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
	
	/** Indice du niveau de difficulté "Indifférent". */
	private static final int DIFFICULTE_INDIFFERENT = 0;
	
	/** Indice du niveau de difficulté "Facile". */
	private static final int DIFFICULTE_FACILE = 1;
	
	/** Indice du niveau de difficulté "Moyen". */
	private static final int DIFFICULTE_MOYEN = 2;
	
	/** Indice du niveau de difficulté "Difficile". */
	private static final int DIFFICULTE_DIFFICILE = 3;
	
	/** Instance du jeu. */
	private static Jeu jeu = Quiz.jeu;
	
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
	
	/** Catégories sélectionnées pour le futur quiz. */
	private ArrayList<Categorie> categoriesSelectionnees;
	
	/** Instance des paramètres de la future partie. */
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
		
		this.choixNombreQuestions(5);
		this.choixDifficulte(0);
		this.categoriesSelectionnees = new ArrayList<>();
		
		/*
		 * Choix du nombre de questions.
		 */
		
		this.checkBox5Questions.setOnAction(event -> {
			this.choixNombreQuestions(5);
		});
		
		this.checkBox10Questions.setOnAction(event -> {
			this.choixNombreQuestions(10);
		});
		
		this.checkBox20Questions.setOnAction(event -> {
			this.choixNombreQuestions(20);
		});
		
		/*
		 * Choix des difficultés.
		 */
		
		this.checkBoxDifficulteIndifferent.setOnAction(event -> {
			this.choixDifficulte(0);
		});
		
		this.checkBoxDifficulteFacile.setOnAction(event -> {
			this.choixDifficulte(1);
		});
		
		this.checkBoxDifficulteMoyen.setOnAction(event -> {
			this.choixDifficulte(2);
		});
		
		this.checkBoxDifficulteDifficile.setOnAction(event -> {
			this.choixDifficulte(3);
		});
		
		/*
		 * Chargement des catégories de question.
		 */
		
		CheckBox checkBoxCategorie;
		
		for (Categorie categorieCourante: jeu.getToutesLesCategories()) {
			checkBoxCategorie = new CheckBox();
			checkBoxCategorie.setOnAction(event -> {
				this.selectionCategorie(categorieCourante);
			});
			
			this.conteneurCategories.getChildren().add(checkBoxCategorie);
		}
	}
	
	/**
	 * Choix du nombre de questions pour le quiz.
	 */
	private void choixNombreQuestions(int nombre) {
		if (nombre != 5 && nombre != 10 && nombre != 20) {
			// TODO: erreur.
		} else {
			this.nombreQuestions = nombre;

			this.checkBox5Questions.setSelected(nombre == 5);
			this.checkBox10Questions.setSelected(nombre == 10);
			this.checkBox20Questions.setSelected(nombre == 20);
		}
	}

	/**
	 * Choix du niveau de difficilté "Indifférent" pour le quiz.
	 */
	private void choixDifficulte(int difficulte) {
		if (difficulte < 0 || difficulte > 3) {
			// TODO: erreur.
		} else {
			boolean checkBoxDifficulteIndifferent,
					checkBoxDifficulteFacile,
					checkBoxDifficulteMoyen,
					checkBoxDifficulteDifficile;
			
			checkBoxDifficulteIndifferent = difficulte == DIFFICULTE_INDIFFERENT;
			checkBoxDifficulteFacile = difficulte == DIFFICULTE_FACILE;
			checkBoxDifficulteMoyen = difficulte == DIFFICULTE_MOYEN;
			checkBoxDifficulteDifficile = difficulte == DIFFICULTE_DIFFICILE;
			
			this.difficulte = difficulte;
			
			this.checkBoxDifficulteIndifferent.setSelected(checkBoxDifficulteIndifferent);
			this.checkBoxDifficulteFacile.setSelected(checkBoxDifficulteFacile);
			this.checkBoxDifficulteMoyen.setSelected(checkBoxDifficulteMoyen);
			this.checkBoxDifficulteDifficile.setSelected(checkBoxDifficulteDifficile);		
		}
	}
	
	private void selectionCategorie(Categorie categorieConcernee) {
		this.categoriesSelectionnees = this.parametres.getCategoriesSelectionnees();
		
		/*
		if (categoriesSelectionnees.contains(categorieConcernee)) {
			this.parametres.deselectionnerCategorie(categorieConcernee);
		} else {
			this.parametres.selectionnerCategorie(categorieConcernee);
		}
		*/
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