/*
 * TestParametresPartie.java             							14 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles.tests;

import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Question;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.ParametresPartie;
import info2.sae301.quiz.modeles.PartieEnCours;

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
class TestPartieEnCours {
	
	 private PartieEnCours partieTest;
	 
	 private ArrayList<Question> setDeQuestions1;
	 
	 private ParametresPartie parametres;
	 
	 private ArrayList<Categorie> categorie1;
	 
	 private ArrayList<Question> questions1;
	
	
	@BeforeEach
	void init() {
		partieTest = new PartieEnCours();
		parametres = new ParametresPartie();
		categorie1 = new ArrayList<Categorie>();
		questions1 = new ArrayList<Question>();
		
		for (int i = 0; i < 5; i++) {
			Question question1
			= new Question("q" + i, "rjuste" + i,
					       new String[] {"rf0", "rf1", "rf2", "rf3"},
					       1, categorie1.get(0));
			
			questions1.add(question1);
			categorie1.get(0).ajouterQuestion(question1);
		}
		
		parametres.setCategoriesSelectionnees(categorie1);
		partieTest.setParametresPartie(parametres);
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.PartieEnCours#PartieEnCours()}.
	 */
	@Test
	public void testConstructeur() {
	    // TODO le test
		assertEquals(0, partieTest.getQuestionsProposees().size());
	    assertEquals(0, partieTest.getIndiceQuestionCourante());
	    assertEquals(5, partieTest.getReponsesUtilisateur().size());
	    assertEquals(5, partieTest.getParametresPartie().getNombreQuestions());
	}
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.PartieEnCours#getQuestionsProposees()}.
	 */
	@Test
	public void testGetQuestionsProposees() {
		// TODO le test
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.PartieEnCours#getReponsesUtilisateur()}.
	 */
	@Test
	public void testGetReponsesUtilisateur() {
		// TODO le test
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.PartieEnCours#getParametresPartie()}.
	 */
	@Test
	public void testGetParametresPartie() {
		// TODO le test
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.PartieEnCours#getIndiceQuestionCourante()}.
	 */
	@Test
	public void testGetIndiceQuestionCourante() {
		// TODO le test
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.PartieEnCours#setIndiceQuestionCourante(int)}.
	 */
	@Test
	public void testSetIndiceQuestionCourante() {
		// TODO le test
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.PartieEnCours#toString()}.
	 */
	@Test
	public void testToString() {
		// TODO le test
	}
	
}
