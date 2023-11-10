/**
 * TestCategorie.java									3 nov. 2023
 * IUT de Rodez, no copyright ni "copyleft"
 */
package info2.sae301.quiz.modeles.tests;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;  

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Question;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe de test de Categorie.java
 * @author FABRE Florian
 */
class TestCategorie {

	/** Categories nommée "orthographe", "grammaire" et "francais"*/
	static Categorie orthographe,grammaire,francais;
	
	/** Question sans feedback (SF) et avec feedback (AF)*/
	static Question questionSF,questionAF,questionAjout;
	
	
	@BeforeEach
	void setUp() throws Exception {
		orthographe = new Categorie("orthographe");
		grammaire = new Categorie("grammaire");
		
		questionSF = new Question("Quelle est la bonne orthographe? ",
				"chat",new String[]{"chatt","shat","chât"},2,orthographe);
		
		questionAF = new Question("Quel est le choix correct pour completer '...-de-chaussée' ? ",
				"rez",new String[]{"raie","raient"},1,"rez car vieux mot",orthographe);
		
		questionAjout = new Question("Quelle est la bonne orthographe? ",
				"chat",new String[]{"chatt","shat","chât"},2,francais);
		
		orthographe.ajouterQuestion(questionSF);
		francais = new Categorie("francais",new ArrayList<Question>(
				Arrays.asList(questionAF)));
	}
	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Categorie#Categorie(java.lang.String, java.util.ArrayList)}.
	 */
	@Test
	void testCategorieCree() {
		assertEquals(francais.getIntitule(), "francais"); 
		assertNotEquals(francais.getListeQuestions(), null);
		assertEquals(francais.getListeQuestions().size(), 1);
		assertEquals(grammaire.getIntitule(), "grammaire");
		assertNotEquals(grammaire.getListeQuestions(), null);
		assertEquals(grammaire.getListeQuestions().size(), 0);
		assertEquals(orthographe.getIntitule(), "orthographe"); 
		assertNotEquals(orthographe.getListeQuestions(), null);
		assertEquals(orthographe.getListeQuestions().size(), 1);
		
		/* Créations de catégories générant des exceptions */
		assertThrows(IllegalArgumentException.class, () -> { 
			new Categorie("La grammaire français"); // 21 caractères
		});
		assertThrows(IllegalArgumentException.class, () -> { 
			new Categorie("La grammaire français", new ArrayList<Question>(
					Arrays.asList(questionAF)));
		});
		assertThrows(IllegalArgumentException.class, () -> { 
			new Categorie("");
		});
		assertThrows(IllegalArgumentException.class, () -> { 
			new Categorie("", new ArrayList<Question>(
					Arrays.asList(questionAF)));
		});
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
	 * Test method for {@link info2.sae301.quiz.modeles.Categorie#getIntitule()}.
	 */
	@Test
	void testGetIntitule() {
		assertEquals("francais",francais.getIntitule());
		assertNotEquals("grammaire",orthographe.getIntitule());
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Categorie#ajouterQuestion(info2.sae301.quiz.modeles.Question)}.
	 */
	@Test
	void testAjouterQuestion() {
		assertTrue(francais.ajouterQuestion(questionAjout));
		
		/*Impossible car question déjà dans une autre catégorie*/
		assertFalse(francais.ajouterQuestion(questionSF));
		
		/*Impossible car question déjà dans la catégorie*/
		assertFalse(francais.ajouterQuestion(questionAjout));
		
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Categorie#estVide()}.
	 */
	@Test
	void testEstVide() {
		assertTrue(grammaire.estVide());
		assertFalse(orthographe.estVide());
		assertFalse(francais.estVide());
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Categorie#nbQuestions()}.
	 */
	@Test
	void testNbQuestions() {
		assertEquals(1,francais.nbQuestions());
		assertNotEquals(1,grammaire.nbQuestions());
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Categorie#getListeQuestions()}.
	 */
	@Test
	void testGetListeQuestions() {
		assertEquals(francais.getListeQuestions().size(), 1);
		assertEquals(grammaire.getListeQuestions().size(), 0);
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Categorie#setIntitule(java.lang.String)}.
	 */
	@Test
	void testSetIntitule() {
		/*Réponse trop longue (21 char et plus)*/
		String intituleTropLong = genererStringTailleX(25);
		String tropCourt = "";
		
		francais.setIntitule("italien");
		assertEquals("italien",francais.getIntitule());
		assertNotEquals("francais",francais.getIntitule());
		
		/* Test d'insertion d'intitulé de taille incorrecte*/
		grammaire.setIntitule(intituleTropLong);
		assertNotEquals(intituleTropLong,grammaire.getIntitule());
		assertEquals("grammaire",grammaire.getIntitule());
		grammaire.setIntitule(tropCourt);
		assertNotEquals(tropCourt,grammaire.getIntitule());
		assertEquals("grammaire",grammaire.getIntitule());
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Categorie#supprimerQuestion(info2.sae301.quiz.modeles.Question)}.
	 */
	@Test
	void testSupprimerQuestion() {
		assertTrue(francais.supprimerQuestion(questionAF));
		assertFalse(francais.supprimerQuestion(questionSF));
		
		/* Cas de liste de questions vide*/
		assertFalse(grammaire.supprimerQuestion(questionAF));
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.Categorie#supprimerToutesQuestions()}.
	 */
	@Test
	void testSupprimerToutesQuestions() {
		assertTrue(francais.supprimerToutesQuestions());
		assertEquals(francais.getListeQuestions().size(), 0);
		assertTrue(grammaire.supprimerToutesQuestions());
		assertEquals(grammaire.getListeQuestions().size(), 0);
		assertTrue(orthographe.supprimerToutesQuestions());
		assertEquals(orthographe.getListeQuestions().size(), 0);
	}
}
