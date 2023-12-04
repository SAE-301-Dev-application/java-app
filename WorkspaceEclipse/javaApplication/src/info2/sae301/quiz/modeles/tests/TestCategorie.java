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
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
class TestCategorie {

	/** Categories nommée "orthographe", "grammaire" et "francais"*/
	static Categorie orthographe,grammaire,francais,conjugaison;
	
	/** Questions sans feedback (SF) et avec feedback (AF)*/
	static Question questionSF,questionAF,questionAjout,questionAjout2,questionAjout3;
	
	
	@BeforeEach
	void setUp() throws Exception {
		orthographe = new Categorie("orthographe");
		grammaire = new Categorie("grammaire");
		conjugaison = new Categorie ("conjugaison");
		
		questionSF = new Question("Quelle est la bonne orthographe? ",
				"chat",new String[]{"chatt","shat","chât"},2,orthographe);
		
		questionAF = new Question("Quel est le choix correct pour completer '...-de-chaussée' ? ",
				"rez",new String[]{"raie","raient"},1,"rez car vieux mot",orthographe);
		
		questionAjout = new Question("Quelle est la bonne orthographe ? ",
				"chat",new String[]{"chatt","shat","chât"},2,francais);
		
		questionAjout2 = new Question("Quelle est la bonne écriture de être pour le pronom 'il' ? ",
				"est",new String[]{"es","ai","et"},2,conjugaison);
		
		questionAjout3 = new Question("Quelle est la bonne écriture de avoir pour le pronom 'je' ? ",
				"ai",new String[]{"es","est","et"},2,conjugaison);
		
		orthographe.ajouterQuestion(questionSF);
		conjugaison.ajouterQuestion(questionAjout2);
		conjugaison.ajouterQuestion(questionAjout3);
		
		francais = new Categorie("francais",new ArrayList<Question>(
				Arrays.asList(questionAF)));
	}
	
	/**
	 * Test method for 
	 * {@link info2.sae301.quiz.modeles.Categorie#Categorie(java.lang.String, java.util.ArrayList)}.
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
	 * 
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
	 * Test method for 
	 * {@link info2.sae301.quiz.modeles.Categorie#getIntitule()}.
	 */
	@Test
	void testGetIntitule() {
		assertEquals("francais",francais.getIntitule());
		assertNotEquals("grammaire",orthographe.getIntitule());
	}

	/**
	 * Test method for 
	 * {@link info2.sae301.quiz.modeles.Categorie#ajouterQuestion(info2.sae301.quiz.modeles.Question)}.
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
	 * Test method for 
	 * {@link info2.sae301.quiz.modeles.Categorie#estVide()}.
	 */
	@Test
	void testEstVide() {
		assertTrue(grammaire.estVide());
		assertFalse(orthographe.estVide());
		assertFalse(francais.estVide());
	}

	/**
	 * Test method for 
	 * {@link info2.sae301.quiz.modeles.Categorie#nbQuestions()}.
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
	 * Test method for 
	 * {@link info2.sae301.quiz.modeles.Categorie#setIntitule(java.lang.String)}.
	 */
	@Test
	void testSetIntitule() {
		/*Réponse trop longue (21 char et plus)*/
		String intituleTropLong = genererStringTailleX(25);
		String tropCourt = " ";
		
		francais.setIntitule("italien");
		assertEquals("italien",francais.getIntitule());
		assertNotEquals("francais",francais.getIntitule());
		
		/* Test d'insertion d'intitulé incorrect*/
		assertThrows(IllegalArgumentException.class, () -> { 
			grammaire.setIntitule(intituleTropLong);
		});
		
		assertThrows(IllegalArgumentException.class, () -> { 
			grammaire.setIntitule(tropCourt);
		});
		
		assertThrows(IllegalArgumentException.class, () -> { 
			grammaire.setIntitule(null);
		});
		
		assertThrows(IllegalArgumentException.class, () -> { 
			grammaire.setIntitule("   ");
		});
	}

	/**
	 * Test method for 
	 * {@link info2.sae301.quiz.modeles.Categorie#supprimerQuestion(info2.sae301.quiz.modeles.Question)}.
	 */
	@Test
	void testSupprimerQuestion() {
		assertTrue(francais.supprimerQuestion(questionAF));
		assertFalse(francais.supprimerQuestion(questionSF));
		
		/* Cas de liste de questions vide*/
		assertFalse(grammaire.supprimerQuestion(questionAF));
		
		/* Cas de catégorie non présente dans la liste*/
		assertFalse(orthographe.supprimerQuestion(questionAF));
	}

	/**
	 * Test method for 
	 * {@link info2.sae301.quiz.modeles.Categorie#supprimerToutesQuestions()}.
	 */
	@Test
	void testSupprimerToutesQuestions() {
		assertTrue(francais.supprimerToutesQuestions());
		assertEquals(francais.getListeQuestions().size(), 0);
		assertTrue(grammaire.supprimerToutesQuestions());
		assertEquals(grammaire.getListeQuestions().size(), 0);
		assertTrue(orthographe.supprimerToutesQuestions());
		assertEquals(orthographe.getListeQuestions().size(), 0);
		assertEquals(conjugaison.getListeQuestions().size(), 2);
		assertTrue(conjugaison.supprimerToutesQuestions());
		assertEquals(conjugaison.getListeQuestions().size(), 0);
	}
	
	
	/**
	 * Test method for 
	 * {@link info2.sae301.quiz.modeles.Categorie#assurerCaracteres()}.
	 */
	@Test
	void testAssurerCaracteres() {
		String[] chainesValides = {"azertyu", "|\\:;.?!\n{}*/-+=\t^@,", "la chaîne est valide"};
		String[] chainesInvalides = {"azer☺tyu", "|\\:;.└↓!\n{}*/-+=\t^@,", "la🤌🫵 ch😊aîne est invalide"};
		
		for (int i = 0; i < chainesValides.length; i++) {
			final int INDICE = i;
			assertDoesNotThrow(() -> {Question.assurerCaracteres(chainesValides[INDICE]);});
		}
		
		for (int i = 0; i < chainesInvalides.length; i++) {
			final int INDICE = i;
			assertThrows(IllegalArgumentException.class,() -> {
				Question.assurerCaracteres(chainesInvalides[INDICE]);
			});
		}
		
	}
	
	/**
	 * Test method for 
	 * {@link info2.sae301.quiz.modeles.Categorie#equals(Object)}.
	 */
	@Test
	void testEquals() {
		//instance de 2 categorie indentique
		Categorie categorie1 = new Categorie("Maths");
		Categorie categorie2 = new Categorie("Maths");
		questionAjout.setCategorie(categorie1);
		Question questionAjoutIdentique = new Question("Quelle est la bonne orthographe? ",
				"chat",new String[]{"chatt","shat","chât"},2,categorie2);
		categorie1.ajouterQuestion(questionAjoutIdentique);
		categorie1.ajouterQuestion(questionSF);
		categorie1.ajouterQuestion(questionAF);
		categorie2.ajouterQuestion(questionAjoutIdentique);
		categorie2.ajouterQuestion(questionSF);
		categorie2.ajouterQuestion(questionAF);
		
		assertEquals(categorie1, categorie2);
		assertEquals(categorie1, categorie1);
		assertNotEquals(orthographe, categorie1);
		assertNotEquals(categorie1, null);
		assertNotEquals(categorie1, questionAF);
	}
	
	/**
	 * Test method for 
	 * {@link info2.sae301.quiz.modeles.Categorie#toString()}.
	 */
	@Test
	void testToString() {
		assertEquals(orthographe.toString(), "orthographe");
		assertEquals(francais.toString(), "francais");
		assertEquals(conjugaison.toString(), "conjugaison");
		assertEquals(grammaire.toString(), "grammaire");
	}
}
