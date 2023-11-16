/**
 * TestPartieEnCours.java									14 nov. 2023
 * IUT de Rodez, no copyright ni "copyleft"
 */
package info2.sae301.quiz.modeles.tests;

import static org.junit.jupiter.api.Assertions.*;

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

	
	static PartieEnCours partie1;
	static PartieEnCours partie2;
	
	private ArrayList<Question> question1 = new ArrayList<>();
	
	private Categorie cat1 = new Categorie("Java");
	
	/**
	 * TODO comment method role and describe it
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void init() {
		
		partie1 = new PartieEnCours();
		partie2 =new PartieEnCours();
		ArrayList<Question> qProposees = new ArrayList<>();
		
		for (int i = 0; i < 24; i++) {
			Question aAjouter = new Question("q" + i, "rjuste" + i,
				       new String[] {"rf0", "rf1", "rf2", "rf3"},
				       1, cat1);
			qProposees.add(aAjouter);
		}
		partie1.setQuestionsProposees(qProposees);
		
		
	}

	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#PartieEnCours()}.
	 */
	@Test
	void testPartieEnCours() {
		assertEquals(new ArrayList<Question>(),partie1.getQuestionsProposees());
		assertEquals(new ArrayList<String>(),partie1.getReponsesUtilisateur());
		assertEquals(0,partie1.getIndiceQuestionCourante());
		assertEquals(null,partie1.getParametresPartie());
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
	void testSetQuestionsProposees() { //TODO finir test
		fail("Not yet implemented");
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
