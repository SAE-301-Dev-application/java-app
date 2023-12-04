/*
 * TestJeu.java             									     6 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles.tests;

import info2.sae301.quiz.exceptions.TaillePseudoInvalideException;
import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Question;
import info2.sae301.quiz.modeles.Jeu;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires de la classe {@link info2.sae301.quiz.modeles.Jeu}.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
class TestJeu {
	
	private Jeu jeu = new Jeu();
	
	private String[] nomsCategories = {
		"Général", "Java", "Orthographe", "Grammaire", "Commentaire",
		"Affichage", "Affectation", "Variable", "Type", "Condition"
	};
	
	private ArrayList<Categorie> listeInitiale = new ArrayList<Categorie>();
	
	private ArrayList<Categorie> listeCategoriesTest = new ArrayList<Categorie>();
	
	private ArrayList<Question> listeVide = new ArrayList<Question>();
	
	private ArrayList<Question> listeQuestionsTest = new ArrayList<Question>();
	
	Categorie tests;
	
	@BeforeEach
	void init() {
		jeu.supprimerToutesCategories();
		listeInitiale.add(new Categorie(nomsCategories[0]));
		for (int i = 0; i < nomsCategories.length; i++) {
			listeCategoriesTest.add(new Categorie(nomsCategories[i]));
			if (!nomsCategories[i].equals("Général")) {
				jeu.creerCategorie(nomsCategories[i].trim());
			}
		}
		
		listeCategoriesTest.add(new Categorie("Tests"));
		tests = jeu.creerCategorie("Tests");
		
		jeu.supprimerToutesQuestions();
		for (int i = 0; i <= 10; i++) {
			listeQuestionsTest.add(
			    new Question("Intitulé de question " + i,
			    		     "Ceci est la réponse juste",
			    		     new String[] {
			    		         "Réponse fausse 1", "Réponse fausse 2",
			    		         "Réponse fausse 3"
			    		     }, 2, "feedback", tests));
			jeu.creerQuestion("Intitulé de question " + i,
	    		     		  "Ceci est la réponse juste",
				    		  new String[] {
				    		      "Réponse fausse 1", "Réponse fausse 2",
				    		      "Réponse fausse 3"
				    		  }, 2, "feedback", tests.getIntitule());
		}
	}
	
	
	/**
	 * Vérification que les listes en paramètre aient les mêmes 
	 * catégories.
	 * 
	 * @param liste1 La première liste à vérifier.
	 * @param liste2 La seconde liste à vérifier.
	 * @return true si les listes ont les mêmes catégories, false sinon.
	 */
	private boolean listesMemesCategories(ArrayList<Categorie> liste1,
										  ArrayList<Categorie> liste2) {
		if (liste1.size() != liste2.size()) {
			return false;
		}
		
		boolean resultat = true;
		for (int i = 0; i < liste1.size() && resultat; i++) {
			if (!liste1.get(i).getIntitule()
				       .equals(liste2.get(i).getIntitule())) {
				System.out.println(liste1.get(i).getIntitule()
						           + " != "
						           + liste2.get(i).getIntitule());
				resultat = false;
			}
		}
		return resultat;
	}
	
	/**
	 * Vérification que les listes en paramètre aient les mêmes 
	 * questions
	 * .
	 * @param liste1 La première liste à vérifier.
	 * @param liste2 La seconde liste à vérifier.
	 * @return true si les listes ont les mêmes questions, false sinon.
	 */
	private boolean listesMemesQuestions(ArrayList<Question> liste1,
										 ArrayList<Question> liste2) {
		if (liste1.size() != liste2.size()) {
			return false;
		}
		
		boolean resultat = true;
		for (int i = 0; i < liste1.size() && resultat; i++) {
			if (!liste1.get(i).getIntitule()
				       .equals(liste2.get(i).getIntitule())) {
				System.out.println(liste1.get(i).getIntitule()
						           + " != "
						           + liste2.get(i).getIntitule());
				resultat = false;
			}
		}
		return resultat;
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#getToutesLesCategories()}.
	 */
    @Test
    public void testGetToutesLesCategories() {
    	ArrayList<Categorie> categories
    	= new ArrayList<Categorie>(listeInitiale);
    	
    	jeu.supprimerToutesCategories();
    	
    	assertTrue(listesMemesCategories(categories,
    			                         jeu.getToutesLesCategories()));
    	
    	for (int i = 1; i < nomsCategories.length; i++) {
    		jeu.creerCategorie(nomsCategories[i]);
    		categories.add(new Categorie(nomsCategories[i]));
    
    		assertEquals(categories.get(i).getIntitule(),
    				     jeu.getToutesLesCategories().get(i).getIntitule());
    	}
    }
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#getToutesLesQuestions()}.
	 */
    @Test
    public void testGetToutesLesQuestions() {
    	ArrayList<Question> listeQuestions
    	= new ArrayList<Question>();
    	
    	jeu.supprimerToutesQuestions();
    	
    	assertTrue(listesMemesQuestions(listeQuestions,
    			                        jeu.getToutesLesQuestions()));
    	
    	for (int i = 0; i < listeQuestionsTest.size(); i++) {
    		Question question = listeQuestionsTest.get(i);
    		jeu.creerQuestion(question.getIntitule(), question.getReponseJuste(),
    					      question.getReponsesFausses(), question.getDifficulte(),
    					      question.getFeedback(), question.getCategorie().getIntitule());
    		listeQuestions.add(listeQuestionsTest.get(i));
    
    		assertEquals(listeQuestions.get(i).getIntitule(),
    				     jeu.getToutesLesQuestions().get(i).getIntitule());
    	}
    }
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#getPseudo(String)}.
	 */
    @Test
    public void testGetPseudo() {
    	jeu.setPseudo("Florian");
    	assertEquals("Florian", jeu.getPseudo());
    	
    }
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#setPseudo(String)}.
	 */
    @Test
    public void testSetPseudo() {
    	jeu.setPseudo("Florian");
    	assertEquals("Florian", jeu.getPseudo());
    	
    	assertThrows(TaillePseudoInvalideException.class, () -> {
    		jeu.setPseudo("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    	});
    	
    	assertThrows(TaillePseudoInvalideException.class, () -> {
    		jeu.setPseudo("");
    	});
    	assertThrows(TaillePseudoInvalideException.class, () -> {
    		jeu.setPseudo(null);
    	});
    }
	
	
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#questionsCategorie(String)}.
	 */
    @Test
    public void testQuestionsCategorie() {
    	ArrayList<Question> questions = jeu.questionsCategorie("Tests");
    	for (int i = 0; i < questions.size(); i++) {
    		assertEquals(listeQuestionsTest.get(i).getIntitule(),
    				     questions.get(i).getIntitule());
    	}
    	assertEquals(jeu.getToutesLesQuestions(),
    			     jeu.questionsCategorie("Toutes les catégories"));
    }

	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#getCategorieParIntitule(String)}.
	 */
    @Test
    public void testGetCategorieParIntitule() {
    	Categorie categorie = jeu.getToutesLesCategories().get(0);
    	
    	assertEquals(categorie, jeu.getCategorieParIntitule(categorie.getIntitule()));
    }
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#getCategoriesParIntitules(ArrayList)}.
	 */
    @Test
    public void testGetCategoriesParIntitules() {
    	Categorie[] categories
    	= jeu.getToutesLesCategories().toArray(new Categorie[listeCategoriesTest
    	                                                     .size()]);
    	
    	ArrayList<String> intitules = new ArrayList<String>();
    	
    	for (Categorie categorie : jeu.getToutesLesCategories()) {
    		intitules.add(categorie.getIntitule());
    	}
    	
    	assertArrayEquals(categories, jeu.getCategoriesParIntitules(intitules));
    }
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#supprimerToutesCategories()}.
	 */
    @Test
    public void testSupprimerToutesCategories() {
    	jeu.supprimerToutesCategories();
    	
    	for (int i = 1; i < nomsCategories.length; i++) {
    		jeu.creerCategorie(nomsCategories[i]);
    	}
    	assertFalse(listesMemesCategories(listeInitiale,
    				 				      jeu.getToutesLesCategories()));
    	jeu.supprimerToutesCategories();
    	assertTrue(listesMemesCategories(listeInitiale,
    			   						 jeu.getToutesLesCategories()));
    }
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#supprimerToutesQuestions()}.
	 */
    @Test
    public void testSupprimerToutesQuestions() {
    	jeu.supprimerToutesQuestions();
    	for (int i = 0; i < listeQuestionsTest.size(); i++) {
    		Question question = listeQuestionsTest.get(i);
    		jeu.creerQuestion(question.getIntitule(), question.getReponseJuste(),
    			              question.getReponsesFausses(), question.getDifficulte(),
    			              question.getFeedback(), question.getCategorie().getIntitule());
    	}
    	assertFalse(listesMemesQuestions(listeVide,
    				 				     jeu.getToutesLesQuestions()));
    	jeu.supprimerToutesQuestions();
    	assertTrue(listesMemesQuestions(listeVide,
    			   						jeu.getToutesLesQuestions()));
    }
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#creerCategorie(String)}.
	 */
    @Test
    public void testCreerCategorie() {
    	assertThrows(IllegalArgumentException.class, () -> {
    		jeu.creerCategorie("Général");
    	});
    	
    	for (int i = 1; i < nomsCategories.length; i++) {
    		final int INDICE = i;
    		assertThrows(IllegalArgumentException.class, () -> {
    			jeu.creerCategorie(nomsCategories[INDICE]);
    		});
    	}
    	jeu.supprimerToutesCategories();
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> {
    		jeu.getToutesLesCategories().get(1);
    	});
    }
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#creerQuestion(String, String, String[],
	 *                                                    int, String, Categorie).
	 */
    @Test
    public void testCreerQuestion() {
    	// Question non existante sans erreurs d'argument
    	jeu.creerQuestion("Intitulé de la question", "Réponse Juste",
    		   			  new String[] {"réponse fausse 1", "réponse fausse 2"},
    		   			  1, null, "Tests");
        assertThrows(IllegalArgumentException.class, () -> {
            jeu.creerQuestion("Intitulé de la question", "Réponse Juste",
           		              new String[] {"réponse fausse 1",
           		              		        "réponse fausse 2"},
           		              1, null, "Tests");
        });
    }
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#supprimer(Categorie[])}.
	 */
    @Test
    public void testSupprimerCategorie() {
    	jeu.supprimerToutesCategories();
    	
    	// Test de suppression de la catégorie "Général"
    	jeu.supprimer(listeInitiale.toArray(new Categorie[0]));
    	assertTrue(listesMemesCategories(listeInitiale,
    			                         jeu.getToutesLesCategories()));
    	
    	listeCategoriesTest = new ArrayList<Categorie>(Arrays.asList(new Categorie("Général")));
    	for (int i = 1; i < nomsCategories.length; i++) {
    		listeCategoriesTest.add(new Categorie(nomsCategories[i]));
    		jeu.creerCategorie(nomsCategories[i]);
    	}
    	
    	// Tests de suppression de 1 catégorie en 1 catégorie
    	for (int i = 1; i < nomsCategories.length / 2; i++) {
    		
    		Categorie[] categoriesASupprimer = new Categorie[1];
    		categoriesASupprimer[0] = new Categorie(nomsCategories[i]);
    		
    		jeu.supprimer(categoriesASupprimer);
    		listeCategoriesTest.remove(1);
    		
    		assertTrue(listesMemesCategories(listeCategoriesTest,
    	  	                                 jeu.getToutesLesCategories()));
    	}
    	
    	// Test de suppression de plusieurs catégories
    	int deuxiemeMoitie = nomsCategories.length / 2;
    	Categorie[] categoriesASupprimer = new Categorie[deuxiemeMoitie];
    	for (int i = deuxiemeMoitie, j = 0; i < nomsCategories.length; i++, j++) {
    		categoriesASupprimer[j] = new Categorie(nomsCategories[i]);
    		listeCategoriesTest.remove(1);
    	}
    	jeu.supprimer(categoriesASupprimer); 
    	
    	assertTrue(listesMemesCategories(listeCategoriesTest,
    			 						 jeu.getToutesLesCategories()));
    }
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#supprimer(Question[])}.
	 */
    @Test
    public void testSupprimerQuestion() {
    	ArrayList<Question> questionsTest = listeQuestionsTest;
    	
    	jeu.supprimerToutesQuestions();
    	
    	// Ajout à jeu des questions de questionsTest.
    	for (int i = 0; i < questionsTest.size(); i++) {
    		Question question = questionsTest.get(i);
    		jeu.creerQuestion(question.getIntitule(), question.getReponseJuste(),
    			      question.getReponsesFausses(), question.getDifficulte(),
    			      question.getFeedback(), question.getCategorie().getIntitule());
    	}
    	
    	// Tests de suppression de 1 question en 1 question
    	for (int i = 0; i < listeQuestionsTest.size() / 2; i++) {
    		Question[] questionsASupprimer = {questionsTest.get(0)};
    		
    		jeu.supprimer(questionsASupprimer);
    		questionsTest.remove(0);
    		
    		assertTrue(listesMemesQuestions(questionsTest,
    	  	                                jeu.getToutesLesQuestions()));
    	}
    	
    	// Test de suppression de plusieurs questions
    	Question[] questionsASupprimer = new Question[listeQuestionsTest.size() / 2];
    	
    	for (int i = 0; i < questionsASupprimer.length; i++) {
    		questionsASupprimer[i] = questionsTest.get(0);
    		questionsTest.remove(0);
    	}
    	jeu.supprimer(questionsASupprimer); 
    	
    	assertTrue(listesMemesQuestions(questionsTest,
    			 						jeu.getToutesLesQuestions()));
    }

	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#renommerCategorie(String, String)}.
	 */
    @Test
    public void testRenommerCategorie() {
    	assertThrows(IllegalArgumentException.class, () -> {
    		final String INTITULE = jeu.getToutesLesCategories().get(0).getIntitule();
    		jeu.renommerCategorie(INTITULE, "Autre");	
    	});
    	
    	String intituleInitial = jeu.getToutesLesCategories().get(1).getIntitule();
    	
    	jeu.renommerCategorie(intituleInitial, "AutreIntitule");
    	
    	assertNotEquals(intituleInitial,
    					jeu.getToutesLesCategories().get(1).getIntitule());
    	assertEquals("AutreIntitule", jeu.getToutesLesCategories().get(1).getIntitule());
    }
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#editerQuestion(int, String, String,
	 *                                                     String[], int, String,
	 *                                                     String)}.
	 */
    @Test
    public void testEditerQuestion() {
    	Question question = jeu.getToutesLesQuestions().get(0);
    	jeu.editerQuestion(0, "testt", "réponse juste",
    			           question.getReponsesFausses(), 1, "feedbackk",
    			           question.getCategorie().getIntitule());
    	assertEquals("réponse juste", question.getReponseJuste());
    	assertEquals("testt", question.getIntitule());
    	assertEquals(1, question.getDifficulte());
    	assertEquals("feedbackk", question.getFeedback());
    	jeu.editerQuestion(0, "testt", "réponse juste",
    	           question.getReponsesFausses(), 1, "",
    	           question.getCategorie().getIntitule());
    	assertEquals("", question.getFeedback());
    }
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#indiceCategorie(String)}.
	 */
    @Test
    public void testIndiceCategorie() {
    	for (String nom : nomsCategories) {
    		if (!nom.equals("Général")) {
    			assertNotEquals(-1, jeu.indiceCategorie(nom));
    		}
    	}
    	jeu.supprimerToutesCategories();
    	for (String nom : nomsCategories) {
    		if (!nom.equals("Général")) {
    			assertEquals(-1, jeu.indiceCategorie(nom));				
    		}
    	}
    }
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#indiceQuestion(String)}.
	 */
    @Test
    public void testIndiceQuestion() {
    	jeu.supprimerToutesQuestions();
    	for (int i = 0; i < listeQuestionsTest.size(); i++) {
    		Question question = listeQuestionsTest.get(i);
    		assertEquals(-1, jeu.indiceQuestion(question.getIntitule(),
    											question.getCategorie().getIntitule(),
    											question.getReponseJuste(),
    											question.getReponsesFausses()));
    		jeu.creerQuestion(question.getIntitule(),
    				          question.getReponseJuste(),
    				          question.getReponsesFausses(),
    				          question.getDifficulte(),
    				          question.getFeedback(),
    				          question.getCategorie().getIntitule());
    		assertNotEquals(-1, jeu.indiceQuestion(question.getIntitule(),
    				   						       question.getCategorie().getIntitule(),
    				   						       question.getReponseJuste(),
    				   						       question.getReponsesFausses()));
    	}
    }
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.modeles.Jeu#equals(Object)}.
	 */
    @Test
    public void testEquals() {
    	Jeu jeuTest = new Jeu();
    	assertTrue(jeuTest.equals(new Jeu()));
    }
}
