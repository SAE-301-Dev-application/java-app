/*
 * TestPartieEnCours.java             								14 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */
package info2.sae301.quiz.modeles.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.ParametresPartie;
import info2.sae301.quiz.modeles.PartieEnCours;
import info2.sae301.quiz.modeles.Question;

/**
 * Classe de  test de Partie de jeu de Quiz en cours contenant les 
 * questions posées, les réponses de l'utilisateur, les paramètres 
 * ainsi que la question courante.
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

	private ParametresPartie parametres;

	private ArrayList<Categorie> categories1;

	private ArrayList<Question> questions1;


	@BeforeEach
	void init() {
		partieTest = new PartieEnCours();
		parametres = new ParametresPartie();
		categories1 = new ArrayList<Categorie>();
		questions1 = new ArrayList<Question>();

		for (String nom : nomsCategories) {
			categories1.add(new Categorie(nom));
		}

		for (int i = 0; i < 5; i++) {
			Question question1
			= new Question("q" + i, "rjuste" + i,
					new String[] {"rf0", "rf1", "rf2", "rf3"},
					1, categories1.get(0));

			questions1.add(question1);
			categories1.get(0).ajouterQuestion(question1);
		}

		parametres.setCategoriesSelectionnees(categories1);
		partieTest.setParametresPartie(parametres);
	}


	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#PartieEnCours()}.
	 */
    @Test
    void testPartieEnCours() {
    	assertEquals(0, partieTest.getQuestionsProposees().size());
    	assertEquals(0, partieTest.getIndiceQuestionCourante());
    	assertEquals(0, partieTest.getReponsesUtilisateur().size());
    }

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#getIndiceDerniereQuestionVue()}.
	 */
    @Test
    void testGetIndiceDerniereQuestionVue() {
    	partieTest.setIndiceDerniereQuestionVue(4);
    	assertEquals(4, partieTest.getIndiceDerniereQuestionVue());
    	assertNotEquals(30, partieTest.getIndiceDerniereQuestionVue());
    
    	partieTest.setIndiceDerniereQuestionVue(30);
    	assertEquals(30, partieTest.getIndiceDerniereQuestionVue());
    }

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#setIndiceDerniereQuestionVue(int)}.
	 */
    @Test
    void testSetIndiceDerniereQuestionVue() {
    
    	/*
    	 * Mêmes tests que pour le getter étant donné qu'il n'y a pas
    	 * de condition dans le setter pour la nouvelle valeur
    	 */
    	partieTest.setIndiceDerniereQuestionVue(4);
    	assertEquals(4, partieTest.getIndiceDerniereQuestionVue());
    	assertNotEquals(30, partieTest.getIndiceDerniereQuestionVue());
    
    	partieTest.setIndiceDerniereQuestionVue(30);
    	assertEquals(30, partieTest.getIndiceDerniereQuestionVue());
    }

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#incrementerIndiceDerniereQuestionVue()}.
	 */
    @Test
    void testIncrementerIndiceDerniereQuestionVue() {
    	partieTest.setIndiceDerniereQuestionVue(4);
    	partieTest.incrementerIndiceDerniereQuestionVue();
    	assertEquals(5, partieTest.getIndiceDerniereQuestionVue());
    	assertNotEquals(4, partieTest.getIndiceDerniereQuestionVue());
    
    	partieTest.setIndiceDerniereQuestionVue(30);
    	partieTest.incrementerIndiceDerniereQuestionVue();
    	assertEquals(31, partieTest.getIndiceDerniereQuestionVue());
    }

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#setQuestionsProposees(java.util.ArrayList)}.
	 */
    @Test
    void testGetQuestionsProposees() {
    
    	assertEquals(0, partieTest.getQuestionsProposees().size());
    	parametres.setDifficulteQuestions(1);
    	parametres.setCategoriesSelectionnees(categories1);
    
    	ArrayList<Question> questionsProposees
    	= parametres.choisirQuestionsProposees();
    
    	partieTest.setQuestionsProposees(questionsProposees);
    
    	assertEquals(questionsProposees, partieTest.getQuestionsProposees());
    }

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#setQuestionsProposees(java.util.ArrayList)}.
	 */
    @Test
    void testSetQuestionsProposees() {
    
    	/*
    	 * Mêmes tests que pour le getter étant donné qu'il n'y a pas
    	 * de condition dans le setter pour la nouvelle valeur
    	 */
    	parametres.setDifficulteQuestions(1);
    	parametres.setCategoriesSelectionnees(categories1);
    
    	ArrayList<Question> questionsProposees
    	= parametres.choisirQuestionsProposees();
    
    	partieTest.setQuestionsProposees(questionsProposees);
    
    	assertEquals(questionsProposees, partieTest.getQuestionsProposees());
    }

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#getReponsesUtilisateur()}.
	 */
    @Test
    void testGetReponsesUtilisateur() {
    	assertEquals(0, partieTest.getReponsesUtilisateur().size());
    
    	ArrayList<String> reponsesTest = new ArrayList<>();
    
    	for (int i = 0; i < 5; i++) {
    		reponsesTest.add(questions1.get(i).getReponsesFausses()[0]);
    	}
    
    	for (int i = 0; i < 5; i++) {
    		partieTest.ajouterReponseUtilisateur(questions1.get(i).getReponsesFausses()[0]);
    	}
    
    	assertEquals(reponsesTest, partieTest.getReponsesUtilisateur());
    }

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#getParametresPartie()}.
	 */
    @Test
    void testGetParametresPartie() {
    	partieTest.setParametresPartie(parametres);
    	assertEquals(parametres, partieTest.getParametresPartie());
    }

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#setParametresPartie(info2.sae301.quiz.modeles.ParametresPartie)}.
	 */
    @Test
    void testSetParametresPartie() {
    
    	/*
    	 * Mêmes tests que pour le getter étant donné qu'il n'y a pas
    	 * de condition dans le setter pour la nouvelle valeur
    	 */
    	partieTest.setParametresPartie(parametres);
    	assertEquals(parametres, partieTest.getParametresPartie());
    }

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#getIndiceQuestionCourante()}.
	 */
    @Test
    void testGetIndiceQuestionCourante() {
    
    	//initialisation d'une instance de partie en cours
    	assertEquals(0, partieTest.getIndiceQuestionCourante());
    
    	partieTest.setIndiceQuestionCourante(5);
    	assertEquals(5, partieTest.getIndiceQuestionCourante());
    	assertNotEquals(15, partieTest.getIndiceQuestionCourante());
    }


	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#setIndiceQuestionCourante(int)}.
	 */
    @Test
    void testSetIndiceQuestionCourante() {
    
    	partieTest.setIndiceQuestionCourante(45);
    	assertEquals(45, partieTest.getIndiceQuestionCourante());
    	assertNotEquals(73, partieTest.getIndiceQuestionCourante());
    }

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#ajouterReponseUtilisateur(java.lang.String)}.
	 */
    @Test
    void testAjouterReponseUtilisateur() {
    	assertEquals(new ArrayList<String>(),partieTest.getReponsesUtilisateur());
    	
    	ArrayList<String> reponsesUser = new ArrayList<>();
    	
    	for (int i = 0; i < 20; i++) {
    		reponsesUser.add("Je suis à la réponse n°" + i);
    		partieTest.ajouterReponseUtilisateur("Je suis à la réponse n°" + i);
    		assertEquals(reponsesUser, partieTest.getReponsesUtilisateur());
    	}
    }

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#modifierReponseUtilisateur(java.lang.String)}.
	 */
    @Test
    void testModifierReponseUtilisateur() {
    	
    	for (int i = 0; i < 20; i++) {
    		partieTest.setIndiceQuestionCourante(i);
    		partieTest.ajouterReponseUtilisateur("Je suis à la réponse n°" + i);
    		partieTest.modifierReponseUtilisateur("Je suis à la réponse n°" + (i+100));
    		
    		assertEquals(partieTest.getReponsesUtilisateur().get(i),"Je suis à la réponse n°" + (i+100));
    	}
    }

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#passerQuestionSuivante()}.
	 */
    @Test
    void testPasserQuestionSuivante() {
    	partieTest.passerQuestionSuivante();
    	assertEquals(1, partieTest.getIndiceQuestionCourante());
    	
    	partieTest.passerQuestionSuivante();
    	partieTest.passerQuestionSuivante();
    	assertEquals(3, partieTest.getIndiceQuestionCourante());
    	
    	partieTest.setIndiceQuestionCourante(30);
    	partieTest.passerQuestionSuivante();
    	assertEquals(31, partieTest.getIndiceQuestionCourante());
    }

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#retourQuestionPrecedente()}.
	 */
    @Test
    void testRetourQuestionPrecedente() {
    	partieTest.retourQuestionPrecedente();
    	assertNotEquals(-1, partieTest.getIndiceQuestionCourante());
    	assertEquals(0, partieTest.getIndiceQuestionCourante());
    	
    	partieTest.setIndiceQuestionCourante(3);
    	partieTest.retourQuestionPrecedente();
    	assertEquals(2, partieTest.getIndiceQuestionCourante());
    }

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#radioDejaSelectionne(java.lang.String)}.
	 */
    @Test
    void testRadioDejaSelectionne() {
    	assertFalse(partieTest.radioDejaSelectionne("0"));
    	
    	for (int i = 0; i < 4; i++) {
    		partieTest.ajouterReponseUtilisateur(i+"");
    	}
    	partieTest.setIndiceQuestionCourante(0);
    	assertTrue(partieTest.radioDejaSelectionne("0"));
    	
    	partieTest.setIndiceQuestionCourante(2);
    	assertTrue(partieTest.radioDejaSelectionne("2"));
    	
    	partieTest.setIndiceQuestionCourante(6);
    	assertFalse(partieTest.radioDejaSelectionne("6"));
    	
    }

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.PartieEnCours#nbReponsesJustes()}.
	 */
    @Test
    void testNbReponsesJustes() {
    	assertEquals(0,partieTest.nbReponsesJustes());
    	
    	partieTest.setQuestionsProposees(questions1);
    	
    	for (int i = 0; i < 5; i++) {
    		if (i%2 == 0) {
    			partieTest.ajouterReponseUtilisateur("rjuste"+i);
    		} else {
    			partieTest.ajouterReponseUtilisateur("rf1");				
    		}
    	}
    	assertEquals(3,partieTest.nbReponsesJustes());
    	assertNotEquals(5, partieTest.nbReponsesJustes());
    }
}
