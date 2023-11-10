/**
 * TestQuestion.java									3 nov. 2023
 * IUT de Rodez, no copyright ni "copyleft"
 */
package info2.sae301.quiz.modeles.tests;

import static org.junit.jupiter.api.Assertions.*; 

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Question;

/**
 * Classe de test de Question.java
 * @author FABRE Florian
 */
class TestQuestion {
	
	/** Categories nommée "orthographe" et "grammaire"*/
	static Categorie orthographe,grammaire;
	
	/** Question sans feedback (SF) et avec feedback (AF)*/
	static Question questionSF,questionAF;
	
	
	@BeforeEach
	void init() throws Exception {
		
		orthographe = new Categorie("orthographe");
		grammaire = new Categorie("grammaire");
		
		questionSF = new Question("Quelle est la bonne orthographe? ",
				"chat",new String[]{"chatt","shat","chât"},2,orthographe);
		
		questionAF = new Question("Quel est le choix correct pour completer '...-de-chaussée' ? ",
				"rez",new String[]{"raie","raient"},1,"rez car vieux mot",orthographe);
		
		orthographe.ajouterQuestion(questionSF);
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#Question(java.lang.String, java.lang.String, java.lang.String[], int, info2.sae301.quiz.modeles.Categorie)}.
	 */
	@Test
	void testQuestionSansFeedBack() {
		assertTrue(!questionSF.getIntitule().equals(null) && !questionSF.getCategorie().equals(null)
				&& questionSF.getDifficulte() != 0 && !questionSF.getReponseJuste().equals(null) 
				&& !questionSF.getReponsesFausses().equals(null));
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#Question(java.lang.String, java.lang.String, java.lang.String[], int, java.lang.String, info2.sae301.quiz.modeles.Categorie)}.
	 */
	@Test
	void testQuestionAvecFeedback() {
		assertTrue(!questionAF.getIntitule().equals(null) && !questionAF.getCategorie().equals(null)
				&& questionAF.getDifficulte() != 0 && !questionAF.getFeedback().equals(null)
				&& !questionAF.getReponseJuste().equals(null) 
				&& !questionAF.getReponsesFausses().equals(null));
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#getIntitule()}.
	 */
	@Test
	void testGetIntitule() {
		assertEquals("Quelle est la bonne orthographe? ",questionSF.getIntitule());
		assertNotEquals("Quelle est la bonne orthographe?",questionSF.getIntitule());
		assertEquals("Quel est le choix correct pour completer '...-de-chaussée' ? ",
				questionAF.getIntitule());
		assertNotEquals("",questionAF.getIntitule());
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#getReponseJuste()}.
	 */
	@Test
	void testGetReponseJuste() {
		assertEquals("chat",questionSF.getReponseJuste());
		assertNotEquals("rené",questionSF.getReponseJuste());

		assertEquals("rez",questionAF.getReponseJuste());
		assertNotEquals("",questionAF.getReponseJuste());
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#getReponsesFausses()}.
	 */
	@Test
	void testGetReponsesFausses() {
		String[][] repFaussesSF = {{"chatt","shat","chât"},{"chat","shat","chât","chien","chinchilla"},{}};
		String[][] repFaussesAF = {{"raie","raient"},{"raie"},{}};
		assertTrue(Arrays.equals(repFaussesSF[0],questionSF.getReponsesFausses()));
		assertFalse(Arrays.equals(repFaussesSF[1],questionSF.getReponsesFausses()));
		assertFalse(Arrays.equals(repFaussesSF[2],questionSF.getReponsesFausses()));
		
		assertTrue(Arrays.equals(repFaussesAF[0],questionAF.getReponsesFausses()));
		assertFalse(Arrays.equals(repFaussesSF[1],questionSF.getReponsesFausses()));
		assertFalse(Arrays.equals(repFaussesSF[2],questionSF.getReponsesFausses()));
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#getDifficulte()}.
	 */
	@Test
	void testGetDifficulte() {
		assertEquals(2,questionSF.getDifficulte());
		assertNotEquals(0,questionAF.getDifficulte());
		assertNotEquals(6,questionSF.getDifficulte());
		assertNotEquals(Integer.MAX_VALUE,questionAF.getDifficulte());
		assertNotEquals(Integer.MIN_VALUE,questionSF.getDifficulte());
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#getFeedback()}.
	 */
	@Test
	void testGetFeedback() {
		assertEquals(null,questionSF.getFeedback());
		assertNotEquals("un feedback",questionSF.getFeedback());
		assertEquals("rez car vieux mot",questionAF.getFeedback());
		assertNotEquals("",questionAF.getFeedback());
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#getCategorie()}.
	 */
	@Test
	void testGetCategorie() {
		assertNotEquals(null,questionSF.getCategorie());
		assertEquals(orthographe,questionSF.getCategorie());
		assertNotEquals(grammaire,questionAF.getFeedback());
	}

	
	/**
	 * génère une chaîne de caractères de taille X
	 * @param X
	 * @return une chaîne de caractères de taille X
	 */
	String genererStringTailleX(int taille) {
		String str = "";
		for (int i = 0; i < taille; i++) {
			str += "x";
		}
		return str;
	}
	
	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#setIntitule(java.lang.String)}.
	 */
	@Test
	void testSetIntitule() {
		/*Réponse trop longue (301 char et plus)*/
		String intituleTropLong = genererStringTailleX(358);
		
		questionAF.setIntitule("Quel est le bon choix ?");
		assertEquals("Quel est le bon choix ?",questionAF.getIntitule());
		assertNotEquals("Quelle est la bonne orthographe? ",questionAF.getIntitule());
		
		questionSF.setIntitule(intituleTropLong);
		assertNotEquals(intituleTropLong,questionSF.getIntitule());
		questionSF.setIntitule("choisissez entre ces choix:");
		assertEquals("choisissez entre ces choix:",questionSF.getIntitule());
	}

	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#setReponseJuste(java.lang.String)}.
	 */
	@Test
	void testSetReponseJuste() {
		/*Réponse trop longue (201 char et plus)*/
		String repTropLongue = genererStringTailleX(250);
		questionAF.setReponseJuste(repTropLongue);
		assertNotEquals(repTropLongue,questionAF.getReponseJuste());
		
		questionAF.setReponseJuste("chien");
		assertEquals("chien",questionAF.getReponseJuste());
		assertNotEquals("",questionAF.getReponseJuste());
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#setReponsesFausses(java.lang.String[])}.
	 */
	@Test
	void testSetReponsesFausses() {
		/*Réponse trop longue (201 char et plus)*/
		String repTropLongue = genererStringTailleX(201);
		String[][] repFaussesSF = {{"chatt","shat","chât"},{"chat","shat","chât","chien","chinchilla"},{}};
		String[][] repFaussesAF = {{"raie","raient"},{repTropLongue},{}};
		
		/* Test d'insertion de tableau de taille incorrecte*/
		questionSF.setReponsesFausses(repFaussesSF[1]);
		assertFalse(Arrays.equals(repFaussesSF[1],questionSF.getReponsesFausses()));
		
		questionSF.setReponsesFausses(repFaussesSF[2]);
		assertFalse(Arrays.equals(repFaussesSF[2],questionSF.getReponsesFausses()));
		assertTrue(Arrays.equals(repFaussesSF[0],questionSF.getReponsesFausses()));
		
		/* Test d'insertion de réponses de taille incorrecte*/
		questionAF.setReponsesFausses(repFaussesAF[1]);
		assertFalse(Arrays.equals(repFaussesAF[1],questionAF.getReponsesFausses()));
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#setDifficulte(int)}.
	 */
	@Test
	void testSetDifficulte() {
		
		/*Valeurs invalides*/
		questionSF.setDifficulte(0);
		assertFalse(0 == questionSF.getDifficulte());
		questionSF.setDifficulte(6);
		assertFalse(6 == questionAF.getDifficulte());
		questionAF.setDifficulte(Integer.MAX_VALUE);
		assertFalse(Integer.MAX_VALUE == questionAF.getDifficulte());
		
		/* Valeur valide*/
		questionAF.setDifficulte(3);
		assertTrue(questionAF.getDifficulte() == 3);
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#setFeedback(java.lang.String)}.
	 */
	@Test
	void testSetFeedback() {
		/*Réponse trop longue (501 char et plus)*/
		String feedbackTropLong = genererStringTailleX(523);
		
		questionAF.setFeedback("C'est la bonne réponse un point c'est tout");
		assertEquals("C'est la bonne réponse un point c'est tout",
				questionAF.getFeedback());
		assertNotEquals("c'est pas ca",questionAF.getFeedback());
		
		questionSF.setFeedback(feedbackTropLong);
		assertNotEquals(feedbackTropLong ,questionSF.getFeedback());
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#setCategorie(info2.sae301.quiz.modeles.Categorie)}.
	 */
	@Test
	void testSetCategorie() {
		questionAF.setCategorie(grammaire);
		assertEquals(grammaire,questionAF.getCategorie());
		assertNotEquals(orthographe,questionAF.getCategorie());
	}
}
