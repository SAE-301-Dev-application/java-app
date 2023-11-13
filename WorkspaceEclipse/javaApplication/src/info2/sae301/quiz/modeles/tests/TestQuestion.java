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
import info2.sae301.quiz.modeles.Jeu;

/**
 * Classe de test de Question.java
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
class TestQuestion {

	/** Categories nommée "orthographe" et "grammaire"*/
	static Categorie orthographe, grammaire;
	
	/** Question sans feedback (SF) et avec feedback (AF)*/
	static Question questionSF, questionAF, questionSF2, questionAF2;
	
	static Jeu jeu;
	
	@BeforeEach
	void setUp() throws Exception {
		jeu = new Jeu();
		orthographe = new Categorie("orthographe");
		grammaire = new Categorie("grammaire");
		
		questionSF = new Question("Quelle est la bonne orthographe? ",
				"chat",new String[]{"chatt","shat","chât"},2,orthographe);
		
		questionAF = new Question("Quel est le choix correct pour completer '...-de-chaussée' ? ",
				"rez",new String[]{"raie","raient"},1,"rez car vieux mot",orthographe);
		
		questionSF2 = new Question("Quelle est la bonne orthographe? ",
				"chat",new String[]{"chatt","shat","chât"},2,orthographe);
		
		questionAF2 = new Question("Quel est le choix correct pour completer '...-de-chaussée' ? ",
				"rez",new String[]{"raie","raient"},1,"rez car vieux mot",orthographe);
		
		orthographe.ajouterQuestion(questionSF);
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#Question(java.lang.String, java.lang.String, java.lang.String[], int, info2.sae301.quiz.modeles.Categorie)}.
	 */
	@Test
	void testQuestionSansFeedBack() {
		assertEquals(questionSF.getIntitule(), "Quelle est la bonne orthographe? ");
		assertEquals(questionSF.getReponseJuste(), "chat");
		
		//assertTrue(questionSF.memeReponsesFausses(questionSF.getReponsesFausses(), questionSF2.getReponsesFausses()));
		// À modifier en fonction des modifs dans Question
		assertEquals(questionSF.getDifficulte(), 2);
		assertEquals(questionSF.getCategorie().getIntitule(), "orthographe");
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#Question(java.lang.String, java.lang.String, java.lang.String[], int, java.lang.String, info2.sae301.quiz.modeles.Categorie)}.
	 */
	@Test
	void testQuestionAvecFeedback() {
		
		assertEquals(questionAF.getIntitule(), "Quel est le choix correct pour completer '...-de-chaussée' ? ");
		assertEquals(questionAF.getReponseJuste(), "rez");
		
		//assertTrue(questionSF.memeReponsesFausses(questionSF.getReponsesFausses(), questionSF2.getReponsesFausses()));
		// À modifier en fonction des modifs dans Question
		assertEquals(questionAF.getDifficulte(), 1);
		assertEquals(questionAF.getFeedback(), "rez car vieux mot");
		assertEquals(questionAF.getCategorie().getIntitule(), "orthographe");
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
		String intituleTropLong = genererStringTailleX(301);
		
		questionAF.setIntitule("Quel est le bon choix ?");
		assertEquals("Quel est le bon choix ?",questionAF.getIntitule());
		assertNotEquals("Quelle est la bonne orthographe? ",questionAF.getIntitule());
		
		assertThrows(IllegalArgumentException.class, () -> { 
			questionSF.setIntitule(intituleTropLong);
		});
		
		assertNotEquals(intituleTropLong,questionSF.getIntitule());
		
		assertThrows(IllegalArgumentException.class, () -> { 
			questionSF.setIntitule("");
		});
		
		assertNotEquals("",questionSF.getIntitule());
		
		assertThrows(IllegalArgumentException.class, () -> { 
			questionSF.setIntitule("  ");
		});
		
		assertNotEquals("  ",questionSF.getIntitule());
	}

	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#setReponseJuste(java.lang.String)}.
	 */
	@Test
	void testSetReponseJuste() {
		/*Réponse trop longue (201 char et plus)*/
		String repTropLongue = genererStringTailleX(201);
		
		assertThrows(IllegalArgumentException.class, () -> { 
			questionSF.setReponseJuste(repTropLongue);
		});
		assertNotEquals(repTropLongue,questionAF.getReponseJuste());
		
		/* Reponse trop courte (moins de 1 caractère) */
		assertThrows(IllegalArgumentException.class, () -> { 
			questionSF.setReponseJuste("");
		});
		assertNotEquals("",questionAF.getReponseJuste());
		
		assertThrows(IllegalArgumentException.class, () -> { 
			questionSF.setReponseJuste(" ");
		});
		assertNotEquals(" ",questionAF.getReponseJuste());
		
		questionAF.setReponseJuste("chien");
		assertEquals("chien",questionAF.getReponseJuste());
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#setReponsesFausses(java.lang.String[])}.
	 */
	@Test
	void testSetReponsesFausses() {
		/*Réponse trop longue (201 char et plus)*/
		String repTropLongue = genererStringTailleX(201);
		String[][] repFaussesSF = {{"chatt","shat","chât"},{"chat","shat","chât","chien","chinchilla"},{}};
		String[][] repFaussesAF = {{"raie","raient"},{repTropLongue},{"", "   "}};
		
		/* Test d'insertion de tableau de taille incorrecte*/
		/* Tableau trop long */
		assertThrows(IllegalArgumentException.class, () -> { 
			questionSF.setReponsesFausses(repFaussesSF[1]);
		});
		
		assertNotEquals(repFaussesSF[1], questionSF.getReponsesFausses());
		
		assertFalse(Arrays.equals(repFaussesSF[1], questionSF.getReponsesFausses()));
		
		/* Tableau trop court */
		assertThrows(IllegalArgumentException.class, () -> { 
			questionSF.setReponsesFausses(repFaussesSF[2]);
		});
		
		assertNotEquals(repFaussesSF[1], questionSF.getReponsesFausses());
		
		assertFalse(Arrays.equals(repFaussesSF[2], questionSF.getReponsesFausses()));
		
		/* Test d'insertion de réponses de taille incorrecte*/
		/* Réponse trop longue */
		assertThrows(IllegalArgumentException.class, () -> { 
			questionAF.setReponsesFausses(repFaussesAF[1]);
		});
		assertNotEquals(repFaussesSF[1], questionSF.getReponsesFausses());
		
		/* Réponse trop courte ou vide */
		assertThrows(IllegalArgumentException.class, () -> { 
			questionAF.setReponsesFausses(repFaussesAF[2]);
		});
		assertNotEquals(repFaussesSF[1], questionSF.getReponsesFausses());
		assertFalse(Arrays.equals(repFaussesAF[1], questionAF.getReponsesFausses()));
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#setDifficulte(int)}.
	 */
	@Test
	void testSetDifficulte() {
		
		/*Valeurs invalides*/
		
		assertThrows(IllegalArgumentException.class, () -> { 
			questionSF.setDifficulte(0);
		});
		assertNotEquals(0, questionSF.getDifficulte());
		
		assertThrows(IllegalArgumentException.class, () -> { 
			questionSF.setDifficulte(6);
		});
		assertNotEquals(6, questionSF.getDifficulte());
		
		assertThrows(IllegalArgumentException.class, () -> { 
			questionSF.setDifficulte(Integer.MAX_VALUE);
		});
		assertNotEquals(Integer.MAX_VALUE, questionSF.getDifficulte());
		
		
		/* Valeur valide*/
		questionAF.setDifficulte(2);
		assertEquals(2, questionAF.getDifficulte());
		questionAF.setDifficulte(1);
		assertEquals(1, questionAF.getDifficulte());
		questionAF.setDifficulte(3);
		assertEquals(3, questionAF.getDifficulte());
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
		
		questionAF.setFeedback("");
		assertEquals("",
				questionAF.getFeedback());
		
		questionAF.setFeedback(null);
		assertEquals(null, questionAF.getFeedback());
		
		assertThrows(IllegalArgumentException.class, () -> { 
			questionAF.setFeedback(feedbackTropLong);
		});
		assertNotEquals(feedbackTropLong ,questionSF.getFeedback());
		
		assertThrows(IllegalArgumentException.class, () -> { 
			questionAF.setFeedback("   ");
		});
		assertNotEquals("   " ,questionSF.getFeedback());
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
	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Question#equals(Object)}.
	 */
	@Test
	void testEquals() {
		//instance de 2 Question identiques
		Question question1 = new Question("Question", "vrai",
				new String[] {"faux", "peut-être"}, 1, grammaire);
		Question question2 = new Question("Question", "vrai",
				new String[] {"faux", "peut-être"}, 1, grammaire);
		
		//instance d'une Question avec un intitule different des 2 premières
		Question question3 = new Question("La Question", "vrai",
				new String[] {"faux", "peut-être"}, 1, grammaire);
		
		//instance d'une Question avec une réponse juste differente des 2 premières
		Question question4 = new Question("Question", "je ne sais pas",
				new String[] {"faux", "peut-être"}, 1, grammaire);
		
		//instance d'une Question avec une réponse fausse differente des 2 premières
		Question question5 = new Question("La Question", "vrai",
				new String[] {"je n'en sais rien", "peut-être"}, 1, grammaire);
		
		
		assertEquals(question1, question2);
		assertNotEquals(question3, question1);
		assertNotEquals(question4, question1);
		assertNotEquals(question5, question1);
		assertNotEquals(question3, question2);
		assertNotEquals(question4, question2);
		assertNotEquals(question5, question2);
	}
	
	@Test
	void testAssurerReponsesUniques() {
		String[][] reponsesNonUniques = {{"vrai", "faux", "ne sais pas", "vrai", "peut-être"},
										 {"chat","shat","chât","chien","chat"},
										 {"chat","shat","chât","chât","chien"},
										 {"chat", "", "chât", "chien", "shat"}};
		
		assertThrows(IllegalArgumentException.class, () -> { 
			Question.assurerReponsesUniques(reponsesNonUniques[0]);
		});
		assertThrows(IllegalArgumentException.class, () -> { 
			Question.assurerReponsesUniques(reponsesNonUniques[1]);
		});
		assertThrows(IllegalArgumentException.class, () -> { 
			Question.assurerReponsesUniques(reponsesNonUniques[2]);
		});
		
		// Je pense qu'il y a une erreur dans les condition de assurerReponsesUniques()
		assertDoesNotThrow(() -> {Question.assurerReponsesUniques(reponsesNonUniques[3]);});
		
		
	}
	

	@Test
	void testAssurerValiditeReponsesFausses() {
		String repTropLongue = genererStringTailleX(201);
		String[][] repFausses = {{"chatt","shat","chât"},{"cha","shat","chât","chien","chinchilla"},
								   {""}, {repTropLongue}};
		
		assertThrows(IllegalArgumentException.class, () -> { 
			Question.assurerValiditeReponsesFausses(repFausses[1]);
		});
		assertThrows(IllegalArgumentException.class, () -> { 
			Question.assurerValiditeReponsesFausses(repFausses[2]);
		});
		assertThrows(IllegalArgumentException.class, () -> { 
			Question.assurerValiditeReponsesFausses(repFausses[3]);
		});
		
		assertDoesNotThrow(() -> {Question.assurerValiditeReponsesFausses(repFausses[0]);});
	}
}
