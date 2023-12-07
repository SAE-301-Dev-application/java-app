/*
 * TestParametresPartie.java             							14 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles.tests;

import info2.sae301.quiz.modeles.ParametresPartie;
import info2.sae301.quiz.modeles.Question;
import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.exceptions.NbInsuffisantQuestionsException;
import info2.sae301.quiz.exceptions.NombreQuestionsInvalideException;
import info2.sae301.quiz.exceptions.AucuneQuestionCorrespondanteException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires de la classe 
 * {@link info2.sae301.quiz.modeles.ParametresPartie}.
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
	
	private ParametresPartie parametresTest;
	
	private ArrayList<Categorie> categorie1;
	
	private ArrayList<Categorie> categorie2;
	
	private ArrayList<Categorie> categorie3;
	
	@BeforeEach
	void init() {
		parametresTest = new ParametresPartie();
		categorie1 = new ArrayList<Categorie>();
		categorie2 = new ArrayList<Categorie>();
		categorie3 = new ArrayList<Categorie>();
		
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
	    assertEquals(5, parametresTest.getNombreQuestions());
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
		
		parametresTest.setCategoriesSelectionnees(categorie3);
		parametresTest.setDifficulteQuestions(1);
		
		assertThrows(AucuneQuestionCorrespondanteException.class, () -> {
		    ParametresPartie
		    .aAssezQuestions(parametresTest.getDifficulteQuestions(),
		    			     parametresTest.getNombreQuestions(),
		    			     parametresTest.getCategoriesSelectionnees());
	    });
		
		parametresTest.setDifficulteQuestions(2);
		assertThrows(AucuneQuestionCorrespondanteException.class, () -> {
		    ParametresPartie
		    .aAssezQuestions(parametresTest.getDifficulteQuestions(),
		    			     parametresTest.getNombreQuestions(),
		    			     parametresTest.getCategoriesSelectionnees());
	    });
		
		parametresTest.setDifficulteQuestions(3);
		assertThrows(AucuneQuestionCorrespondanteException.class, () -> {
		    ParametresPartie
		    .aAssezQuestions(parametresTest.getDifficulteQuestions(),
		    			     parametresTest.getNombreQuestions(),
		    			     parametresTest.getCategoriesSelectionnees());
	    });
		
		parametresTest.setDifficulteQuestions(0);
		assertThrows(AucuneQuestionCorrespondanteException.class, () -> {
		    ParametresPartie
		    .aAssezQuestions(parametresTest.getDifficulteQuestions(),
		    			     parametresTest.getNombreQuestions(),
		    			     parametresTest.getCategoriesSelectionnees());
	    });
		
		parametresTest.setNombreQuestions(5);
		
		Categorie categorie = new Categorie("TestCate");
		Question question1
		= new Question("questiontest1", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           1, categorie);
		
		categorie.ajouterQuestion(question1);
		
		categorie3.add(categorie);
		parametresTest.setCategoriesSelectionnees(categorie3);
		
		
		assertThrows(NbInsuffisantQuestionsException.class, () -> {
		    ParametresPartie
		    .aAssezQuestions(parametresTest.getDifficulteQuestions(),
		    			     parametresTest.getNombreQuestions(),
		    			     parametresTest.getCategoriesSelectionnees());
	    });
		
		Question question2
		= new Question("questiontest2", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           1, categorie);
		
		Question question3
		= new Question("questiontest3", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           2, categorie);
		
		Question question4
		= new Question("questiontest4", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           3, categorie);
		
		Question question5
		= new Question("questiontest5", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           2, categorie);
		
		categorie.ajouterQuestion(question2);
		categorie.ajouterQuestion(question3);
		categorie.ajouterQuestion(question4);
		categorie.ajouterQuestion(question5);
		
		assertDoesNotThrow(() -> {
		    ParametresPartie
		    .aAssezQuestions(parametresTest.getDifficulteQuestions(),
		    			     parametresTest.getNombreQuestions(),
		    			     parametresTest.getCategoriesSelectionnees());
		});
		
		Question question6
		= new Question("questiontest6", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           1, categorie);
		
		Question question7
		= new Question("questiontest7", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           1, categorie);
		
		Question question8
		= new Question("questiontest8", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           1, categorie);
		
		Question question9
		= new Question("questiontest9", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           2, categorie);
		
		categorie.ajouterQuestion(question6);
		categorie.ajouterQuestion(question7);
		categorie.ajouterQuestion(question8);
		categorie.ajouterQuestion(question9);
		
		parametresTest.setDifficulteQuestions(1);
		assertDoesNotThrow(() -> {
		    ParametresPartie
		    .aAssezQuestions(parametresTest.getDifficulteQuestions(),
		    			     parametresTest.getNombreQuestions(),
		    			     parametresTest.getCategoriesSelectionnees());
		});
		
		parametresTest.setDifficulteQuestions(2);
		assertThrows(NbInsuffisantQuestionsException.class, () -> {
		    ParametresPartie
		    .aAssezQuestions(parametresTest.getDifficulteQuestions(),
		    			     parametresTest.getNombreQuestions(),
		    			     parametresTest.getCategoriesSelectionnees());
	    });
		
		parametresTest.setDifficulteQuestions(3);
		assertThrows(NbInsuffisantQuestionsException.class, () -> {
		    ParametresPartie
		    .aAssezQuestions(parametresTest.getDifficulteQuestions(),
		    			     parametresTest.getNombreQuestions(),
		    			     parametresTest.getCategoriesSelectionnees());
	    });
		
		Question question10
		= new Question("questiontest10", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           2, categorie);
		
		Question question11
		= new Question("questiontest11", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           2, categorie);
		
		Question question12
		= new Question("questiontest12", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           2, categorie);
		
		Question question13
		= new Question("questiontest13", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           1, categorie);
		
		categorie.ajouterQuestion(question10);
		categorie.ajouterQuestion(question11);
		categorie.ajouterQuestion(question12);
		categorie.ajouterQuestion(question13);
		
		parametresTest.setDifficulteQuestions(2);
		assertDoesNotThrow(() -> {
		    ParametresPartie
		    .aAssezQuestions(parametresTest.getDifficulteQuestions(),
		    			     parametresTest.getNombreQuestions(),
		    			     parametresTest.getCategoriesSelectionnees());
		});
		
		parametresTest.setDifficulteQuestions(1);
		assertDoesNotThrow(() -> {
		    ParametresPartie
		    .aAssezQuestions(parametresTest.getDifficulteQuestions(),
		    			     parametresTest.getNombreQuestions(),
		    			     parametresTest.getCategoriesSelectionnees());
		});
		
		parametresTest.setDifficulteQuestions(3);
		
		assertThrows(NbInsuffisantQuestionsException.class, () -> {
		    ParametresPartie
		    .aAssezQuestions(parametresTest.getDifficulteQuestions(),
		    			     parametresTest.getNombreQuestions(),
		    			     parametresTest.getCategoriesSelectionnees());
	    });
		
		Question question14
		= new Question("questiontest14", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           3, categorie);
		
		Question question15
		= new Question("questiontest15", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           3, categorie);
		
		Question question16
		= new Question("questiontest16", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           3, categorie);
		
		Question question17
		= new Question("questiontest17", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           3, categorie);
		
		categorie.ajouterQuestion(question14);
		categorie.ajouterQuestion(question15);
		categorie.ajouterQuestion(question16);
		categorie.ajouterQuestion(question17);
		
		parametresTest.setDifficulteQuestions(2);
		assertDoesNotThrow(() -> {
		    ParametresPartie
		    .aAssezQuestions(parametresTest.getDifficulteQuestions(),
		    			     parametresTest.getNombreQuestions(),
		    			     parametresTest.getCategoriesSelectionnees());
		});
		
		parametresTest.setDifficulteQuestions(1);
		assertDoesNotThrow(() -> {
		    ParametresPartie
		    .aAssezQuestions(parametresTest.getDifficulteQuestions(),
		    			     parametresTest.getNombreQuestions(),
		    			     parametresTest.getCategoriesSelectionnees());
		});
		
		parametresTest.setDifficulteQuestions(3);
		assertDoesNotThrow(() -> {
		    ParametresPartie
		    .aAssezQuestions(parametresTest.getDifficulteQuestions(),
		    			     parametresTest.getNombreQuestions(),
		    			     parametresTest.getCategoriesSelectionnees());
		});
		
		
	}
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#recupQuestionsValides()}.
	 */
	@Test
	public void testRecupQuestionsValides() {
		parametresTest.setCategoriesSelectionnees(categorie1);
		parametresTest.setDifficulteQuestions(0);
		
		assertEquals(nomsCategories.length,
				     ParametresPartie.recupQuestionsValides(parametresTest
				    		                                .getDifficulteQuestions(),
				    		                                parametresTest
				    		                                .getCategoriesSelectionnees())
				                                            .size());
		
		parametresTest.setCategoriesSelectionnees(categorie2);
		parametresTest.setDifficulteQuestions(1);
		
		assertTrue(ParametresPartie.recupQuestionsValides(parametresTest
											              .getDifficulteQuestions(),
											              parametresTest
											              .getCategoriesSelectionnees())
											              .isEmpty());
		
		Categorie categorie = new Categorie("TestCate");
		Question question
		= new Question("questiontest", "rjuste",
			           new String[] {"rf0", "rf1", "rf2", "rf3"},
			           1, categorie);
		
		categorie.ajouterQuestion(question);
		
		categorie2.add(categorie);
		parametresTest.setCategoriesSelectionnees(categorie2);
		
		assertEquals(1, ParametresPartie.recupQuestionsValides(parametresTest
												               .getDifficulteQuestions(),
												               parametresTest
												               .getCategoriesSelectionnees())
												               .size());
	}
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#choisirQuestionsProposees()}.
	 */
	@Test
	public void testChoisirQuestionsProposees() {
		Categorie categorieTest = new Categorie("Test");
		for (int i = 0; i <= 9; i++) {
			Question question1
			= new Question("q" + i, "rjuste" + i,
					       new String[] {"rf0", "rf1", "rf2", "rf3"},
					       1, categorieTest);
			
			categorieTest.ajouterQuestion(question1);
		}
		categorie3.add(categorieTest);
		parametresTest.setCategoriesSelectionnees(categorie3);
		parametresTest.setDifficulteQuestions(1);
		parametresTest.setNombreQuestions(10);
		
		ArrayList<Question> listeQuestionsProposees = parametresTest.choisirQuestionsProposees();
		assertEquals(10, listeQuestionsProposees.size());
		for (int i = 0; i < categorieTest.getListeQuestions().size(); i++) {
			boolean questionPresente = false;
			for (int j = 0; j < categorieTest.getListeQuestions().size() && !questionPresente; j++) {
				if (listeQuestionsProposees.get(i) == categorieTest.getListeQuestions().get(j)) {
					questionPresente = true;
				}
			}
			assertTrue(questionPresente);
		}
		parametresTest.setNombreQuestions(5);
		listeQuestionsProposees = parametresTest.choisirQuestionsProposees();
		assertEquals(5, listeQuestionsProposees.size());
		for (int i = 0; i < listeQuestionsProposees.size(); i++) {
			assertTrue(categorieTest.getListeQuestions().contains(listeQuestionsProposees.get(i)));
		}
		
		parametresTest.setNombreQuestions(20);
		listeQuestionsProposees = parametresTest.choisirQuestionsProposees();
		assertEquals(10, listeQuestionsProposees.size());
		for (int i = 0; i < listeQuestionsProposees.size(); i++) {
			assertTrue(categorieTest.getListeQuestions().contains(listeQuestionsProposees.get(i)));
		}
		
		
		
		
		
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#getCategoriesSelectionnees()}.
	 */
	@Test
	public void testGetCategoriesSelectionnees() {
		parametresTest.setCategoriesSelectionnees(categorie1);
		for (int i = 0; i < categorie1.size(); i++) {
			assertEquals(categorie1.get(i),
				     parametresTest.getCategoriesSelectionnees().get(i));
		}
		
	}
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#setCategoriesSelectionnees(ArrayList)}.
	 */
	@Test
	public void testSetCategoriesSelectionnees() {
		parametresTest.setCategoriesSelectionnees(categorie2);
		for (int i = 0; i < categorie1.size(); i++) {
			assertEquals(categorie2.get(i),
				     parametresTest.getCategoriesSelectionnees().get(i));
		}
	}
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#getDifficulteQuestions()}.
	 */
	@Test
	public void testGetDifficulteQuestions() {
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
		assertEquals(0, parametresTest.getDifficulteQuestions());
		
		parametresTest.setDifficulteQuestions(1);
		assertEquals(1, parametresTest.getDifficulteQuestions());
		
		parametresTest.setDifficulteQuestions(2);
		assertEquals(2, parametresTest.getDifficulteQuestions());
		
		parametresTest.setDifficulteQuestions(3);
		assertEquals(3, parametresTest.getDifficulteQuestions());
	}
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.ParametresPartie#getNombreQuestions()}.
	 */
	@Test
	public void testGetNombreQuestions() {
		assertEquals(5, parametresTest.getNombreQuestions());
		
		parametresTest.setNombreQuestions(10);
		assertEquals(10, parametresTest.getNombreQuestions());
		
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
		
		parametresTest.setNombreQuestions(10);
		assertEquals(10, parametresTest.getNombreQuestions());
		
		parametresTest.setNombreQuestions(20);
		assertEquals(20, parametresTest.getNombreQuestions());
		
		assertThrows(NombreQuestionsInvalideException.class, () -> {
			parametresTest.setNombreQuestions(1);
		});
		
		assertThrows(NombreQuestionsInvalideException.class, () -> {
			parametresTest.setNombreQuestions(4);
		});
		
		assertThrows(NombreQuestionsInvalideException.class, () -> {
			parametresTest.setNombreQuestions(9);
		});
		
		assertThrows(NombreQuestionsInvalideException.class, () -> {
			parametresTest.setNombreQuestions(19);
		});
	}
	
}
