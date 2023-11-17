/**
 * TestPartieEnCours.java									14 nov. 2023
 * IUT de Rodez, no copyright ni "copyleft"
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

import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.PartieEnCours;
import info2.sae301.quiz.modeles.Question;

/**
 * Gestion des catégories et des questions créées par l'utilisateur.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
class TestPartieEnCours {

	
	private String[] nomsCategories = {
			"catégorie1", "caté2", "caté3", "caté4", "caté5"
		};
	
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
		
		for (String nom : nomsCategories) {
			categorie1.add(new Categorie(nom));
		}
		
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
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#PartieEnCours()}.
	 */
	@Test
	public void testConstructeur() {
		assertEquals(0, partieTest.getQuestionsProposees().size());
	    assertEquals(0, partieTest.getIndiceQuestionCourante());
	    assertEquals(0, partieTest.getReponsesUtilisateur().size());
	}

	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#getQuestionsProposees()}.
	 */
	@Test
	void testGetQuestionsProposees() { //TODO finir test
		
	}

	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#setQuestionsProposees(java.util.ArrayList)}.
	 */
	@Test
	public void testGetQuestionsProposees() {
		
		assertEquals(0, partieTest.getQuestionsProposees().size());
		parametres.setDifficulteQuestions(1);
		parametres.setCategoriesSelectionnees(categorie1);
		
		ArrayList<Question> questionsProposees
		= parametres.choisirQuestionsProposees();
		
		partieTest.setQuestionsProposees(questionsProposees);
		
		assertEquals(questionsProposees, partieTest.getQuestionsProposees());
		
		
	}

	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#getReponsesUtilisateur()}.
	 */
	@Test
	void testGetReponsesUtilisateur() { //TODO finir test
		fail("Not yet implemented");
	}

	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#getParametresPartie()}.
	 */
	@Test
	void testGetParametresPartie() { //TODO finir test
		fail("Not yet implemented");
	}

	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#setParametresPartie(info2.sae301.quiz.modeles.ParametresPartie)}.
	 */
	@Test
	void testSetParametresPartie() { //TODO finir test
		fail("Not yet implemented");
	}

	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#getIndiceQuestionCourante()}.
	 */
	@Test
	void testGetIndiceQuestionCourante() { //TODO finir test
		fail("Not yet implemented");
	}

	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#setIndiceQuestionCourante(int)}.
	 */
	@Test
	void testSetIndiceQuestionCourante() { //TODO finir test
		fail("Not yet implemented");
	}

	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#melangerQuestionsProposees()}.
	 */
	@Test
	void testMelangerQuestionsProposees() { //TODO finir test
		fail("Not yet implemented");
	}

	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#ajouterReponseUtilisateur(java.lang.String)}.
	 */
	@Test
	void testAjouterReponseUtilisateur() { //TODO finir test
		fail("Not yet implemented");
	}

	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#passerQuestionSuivante()}.
	 */
	@Test
	void testPasserQuestionSuivante() { //TODO finir test
		fail("Not yet implemented");
	}

	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#retourQuestionPrecedente()}.
	 */
	@Test
	void testRetourQuestionPrecedente() { //TODO finir test
		fail("Not yet implemented");
	}

	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#verifierReponse(info2.sae301.quiz.modeles.Question, java.lang.String)}.
	 */
	@Test
	void testVerifierReponse() { //TODO finir test
		fail("Not yet implemented");
	}

	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#nbReponsesJustes()}.
	 */
	@Test
	void testNbReponsesJustes() { //TODO finir test
		fail("Not yet implemented");
	}

}
