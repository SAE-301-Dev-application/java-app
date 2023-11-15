/*
 * TestParametresPartie.java             							14 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles.tests;

import info2.sae301.quiz.modeles.ParametresPartie;
import info2.sae301.quiz.modeles.Question;
import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.exceptions.NbInsuffisantQuestionsException;
import info2.sae301.quiz.exceptions.AucuneQuestionCorrespondanteException;

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
	
	private ArrayList<Categorie> categorie1 = new ArrayList<Categorie>();
	
	private ArrayList<Categorie> categorie2 = new ArrayList<Categorie>();
	
	@BeforeEach
	void init() {
		parametresTest = new ParametresPartie();
		categorie1 = new ArrayList<Categorie>();
		categorie2 = new ArrayList<Categorie>();
		
		for (String nom : nomsCategories) {
			categorie1.add(new Categorie(nom));
			categorie2.add(new Categorie(nom));
		}
		
		for (int i = 0; i < nomsCategories.length; i++) {
			Question question1
			= new Question("q" + i, "rjuste" + i,
					       new String[] {"rf0", "rf1", "rf2", "rf3"},
					       1, categorie1.get(i));
			
			categorie1.get(i).ajouterQuestion(question1);
			
			Question question2
			= new Question("q" + i, "rjuste" + i,
					       new String[] {"rf0", "rf1", "rf2", "rf3"},
					       3, categorie2.get(i));
			
			categorie2.get(i).ajouterQuestion(question2);
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
		
		parametresTest.setCategoriesSelectionnees(categorie1);
		assertEquals(nomsCategories.length,
				     parametresTest.getCategoriesSelectionnees().size());
	}
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#aAssezQuestions()}.
	 */
	@Test
	public void testAAssezQuestions() {
		parametresTest.setCategoriesSelectionnees(categorie2);
		parametresTest.setDifficulteQuestions(1);
		
		assertThrows(AucuneQuestionCorrespondanteException.class, () -> {
		    parametresTest.aAssezQuestions();
	    });
		
		Categorie categorie = new Categorie("TestCate");
		Question question
		= new Question("questiontest", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           1, categorie);
		
		categorie.ajouterQuestion(question);
		
		categorie2.add(categorie);
		parametresTest.setCategoriesSelectionnees(categorie2);
		parametresTest.setNombreQuestions(5);
		
		assertThrows(NbInsuffisantQuestionsException.class, () -> {
		    parametresTest.aAssezQuestions();
	    });
	}
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#choisirQuestionsProposees()}.
	 */
	@Test
	public void testChoisirQuestionsProposees() {
		parametresTest.setCategoriesSelectionnees(categorie1);
		parametresTest.setDifficulteQuestions(0);
		
		assertEquals(nomsCategories.length, parametresTest.choisirQuestionsProposees().size());
		
		parametresTest.setCategoriesSelectionnees(categorie2);
		parametresTest.setDifficulteQuestions(1);
		
		assertEquals(0, parametresTest.choisirQuestionsProposees().size());
		
		Categorie categorie = new Categorie("TestCate");
		Question question
		= new Question("questiontest", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           1, categorie);
		
		categorie.ajouterQuestion(question);
		
		categorie2.add(categorie);
		parametresTest.setCategoriesSelectionnees(categorie2);
		
		assertEquals(1, parametresTest.choisirQuestionsProposees().size());
	}
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#getCategoriesSelectionnees()}.
	 */
	@Test
	public void testGetCategoriesSelectionnees() {
		parametresTest.setCategoriesSelectionnees(categorie1);
		assertEquals(categorie1.size(),
				     parametresTest.getCategoriesSelectionnees().size());
	}
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#setCategoriesSelectionnees(ArrayList)}.
	 */
	@Test
	public void testSetCategoriesSelectionnees() {
		parametresTest.setCategoriesSelectionnees(categorie1);
		assertEquals(categorie1.size(),
				     parametresTest.getCategoriesSelectionnees().size());
	}
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#getDifficulteQuestions()}.
	 */
	@Test
	public void testGetDifficulteQuestions() {
		parametresTest.setDifficulteQuestions(0);
		assertEquals(0, parametresTest.getDifficulteQuestions());
		
		parametresTest.setDifficulteQuestions(2);
		assertEquals(2, parametresTest.getDifficulteQuestions());
	}
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#setDifficulteQuestions(int)}.
	 */
	@Test
	public void testSetDifficulteQuestions() {
		parametresTest.setDifficulteQuestions(0);
		assertEquals(0, parametresTest.getDifficulteQuestions());
		
		parametresTest.setDifficulteQuestions(2);
		assertEquals(2, parametresTest.getDifficulteQuestions());
	}
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#getNombreQuestions()}.
	 */
	@Test
	public void testGetNombreQuestions() {
		parametresTest.setNombreQuestions(5);
		assertEquals(5, parametresTest.getNombreQuestions());
		
		parametresTest.setNombreQuestions(20);
		assertEquals(20, parametresTest.getNombreQuestions());
	}
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#setNombreQuestions(int)}.
	 */
	@Test
	public void testSetNombreQuestions() {
		parametresTest.setNombreQuestions(5);
		assertEquals(5, parametresTest.getNombreQuestions());
		
		parametresTest.setNombreQuestions(20);
		assertEquals(20, parametresTest.getNombreQuestions());
		
		assertThrows(IllegalArgumentException.class, () -> {
			parametresTest.setNombreQuestions(2);
		});
	}
	
}
