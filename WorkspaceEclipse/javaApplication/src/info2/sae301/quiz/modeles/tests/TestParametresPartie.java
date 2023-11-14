/*
 * TestParametresPartie.java             							14 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles.tests;

import info2.sae301.quiz.modeles.ParametresPartie;
import info2.sae301.quiz.modeles.Question;
import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Jeu;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires de la classe {@link info2.sae301.quiz.modeles.ParametresPartie}.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
class TestParametresPartie {
	
	private String[] nomsCategories = {
		"catégorie1", "caté2", "caté3", "caté4", "caté5"
	};
	
	private ParametresPartie parametresTest = new ParametresPartie();
	
	private ArrayList<Categorie> categories1 = new ArrayList<Categorie>();
	
	private ArrayList<Categorie> categories2 = new ArrayList<Categorie>();
	
	@BeforeEach
	void init() {
		parametresTest = new ParametresPartie();
		categories1 = new ArrayList<Categorie>();
		categories2 = new ArrayList<Categorie>();
		
		for (String nom : nomsCategories) {
			categories1.add(new Categorie(nom));
			categories2.add(new Categorie(nom));
		}
		
		for (int i = 0; i < nomsCategories.length; i++) {
			Question question1
			= new Question("q" + i, "rjuste" + i,
					       new String[] {"rf0", "rf1", "rf2", "rf3"},
					       1, categories1.get(i));
			
			categories1.get(i).ajouterQuestion(question1);
			
			Question question2
			= new Question("q" + i, "rjuste" + i,
					       new String[] {"rf0", "rf1", "rf2", "rf3"},
					       3, categories2.get(i));
			
			categories2.get(i).ajouterQuestion(question2);
		}
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#ParametresPartie()}.
	 */
	@Test
	public void testConstructeur() {
		assertEquals(0, parametresTest.getCategoriesSelectionnees().size());
	    assertEquals(0, parametresTest.getDifficulteQuestions());
	    assertEquals(10, parametresTest.getNombreQuestions());
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#selectionnerCategories(ArrayList)}.
	 */
	@Test
	public void testSelectionnerCategories() {
		assertEquals(0, parametresTest.getCategoriesSelectionnees().size());
		
		parametresTest.setCategoriesSelectionnees(categories1);
		assertEquals(nomsCategories.length,
				     parametresTest.getCategoriesSelectionnees().size());
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#aAssezQuestions()}.
	 */
	@Test
	public void testAAssezQuestions() {
		parametresTest.setCategoriesSelectionnees(categories2);
		parametresTest.setDifficulteQuestions(1);
		
		assertThrows(IllegalArgumentException.class, () -> {
		    parametresTest.aAssezQuestions();
	    });
		
		Categorie categorie = new Categorie("TestCate");
		Question question
		= new Question("questiontest", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           1, categorie);
		
		categorie.ajouterQuestion(question);
		
		categories2.add(categorie);
		parametresTest.setCategoriesSelectionnees(categories2);
		parametresTest.setNombreQuestions(5);
		
		assertThrows(NumberFormatException.class, () -> {
		    parametresTest.aAssezQuestions();
	    });
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#choisirQuestionsProposees()}.
	 */
	@Test
	public void testChoisirQuestionsProposees() {
		parametresTest.setCategoriesSelectionnees(categories1);
		parametresTest.setDifficulteQuestions(0);
		
		assertEquals(nomsCategories.length, parametresTest.choisirQuestionsProposees().size());
		
		parametresTest.setCategoriesSelectionnees(categories2);
		parametresTest.setDifficulteQuestions(1);
		
		assertEquals(0, parametresTest.choisirQuestionsProposees().size());
		
		Categorie categorie = new Categorie("TestCate");
		Question question
		= new Question("questiontest", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           1, categorie);
		
		categorie.ajouterQuestion(question);
		
		categories2.add(categorie);
		parametresTest.setCategoriesSelectionnees(categories2);
		
		assertEquals(1, parametresTest.choisirQuestionsProposees().size());
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#getCategoriesSelectionnees()}.
	 */
	@Test
	public void testGetCategoriesSelectionnees() {
		// TODO le test
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#setCategoriesSelectionnees(ArrayList)}.
	 */
	@Test
	public void testSetCategoriesSelectionnees() {
		// TODO le test
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#getDifficulteQuestions()}.
	 */
	@Test
	public void testGetDifficulteQuestions() {
		// TODO le test
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#setDifficulteQuestions(int)}.
	 */
	@Test
	public void testSetDifficulteQuestions() {
		// TODO le test
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#getNombreQuestions()}.
	 */
	@Test
	public void testGetNombreQuestions() {
		// TODO le test
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#setNombreQuestions(int)}.
	 */
	@Test
	public void testSetNombreQuestions() {
		// TODO le test
	}
	
}
