/*
 * TestGestionQuestions.java									     5 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.gestion.tests;

import info2.sae301.quiz.Categorie;
import info2.sae301.quiz.Question;
import info2.sae301.quiz.gestion.GestionCategories;
import info2.sae301.quiz.gestion.GestionQuestions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires de la classe {@link info2.sae301.quiz.gestion.GestionQuestions}.
 * 
 * @author FAUGIERES Loïc
 * @author GUIRAUD Simon
 */
class TestGestionQuestions {
	
	private ArrayList<Question> listeVide = new ArrayList<Question>();
	
	private ArrayList<Question> listeQuestionsTest = new ArrayList<Question>();
	
	Categorie Tests = new Categorie("Tests");
	
	@BeforeEach
	void init() {
		GestionQuestions.viderListeToutesQuestions();
		for (int i = 0; i <= 10; i++) {
			listeQuestionsTest.add(
			    new Question("Intitulé de question " + i,
			    		     "Ceci est la réponse juste",
			    		     new String[]{"Réponse fausse 1", "Réponse fausse 2",
			    		    		      "Réponse fausse 3"},
			    		     2,
			    		     Tests));
		}
	}
	
	/**
	 * Vérification que les listes en paramètre aient les mêmes questions.
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
	 * {@link info2.sae301.quiz.gestion.GestionQuestions#getListeToutesQuestions()}.
	 */
	@Test
	public void testGetListeToutesQuestions() {
		ArrayList<Question> listeQuestions
		= new ArrayList<Question>();
		
		assertTrue(listesMemesQuestions(listeQuestions,
				                        GestionQuestions
				                        .getListeToutesQuestions()));
		
		for (int i = 0; i < listeQuestionsTest.size(); i++) {
			GestionQuestions.ajouter(listeQuestionsTest.get(i));
			listeQuestions.add(listeQuestionsTest.get(i));

			assertEquals(listeQuestions.get(i).getIntitule(),
					     GestionQuestions.getListeToutesQuestions()
					                      .get(i).getIntitule());
		}
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.gestion.GestionQuestions#viderListeToutesQuestions()}.
	 */
	@Test
	public void testViderListeToutesQuestions() {
		for (int i = 0; i < listeQuestionsTest.size(); i++) {
			GestionQuestions.ajouter(listeQuestionsTest.get(i));
		}
		assertFalse(listesMemesQuestions(listeVide,
					 				     GestionQuestions
					                     .getListeToutesQuestions()));
		GestionQuestions.viderListeToutesQuestions();
		assertTrue(listesMemesQuestions(listeVide,
				   						GestionQuestions
				   						.getListeToutesQuestions()));
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.gestion.GestionQuestions#ajouter(Question)}.
	 */
	@Test
	public void testAjouter() {
		for (int i = 0; i < listeQuestionsTest.size(); i++) {
			GestionQuestions.ajouter(listeQuestionsTest.get(i));
			assertEquals(GestionQuestions.getListeToutesQuestions()
										 .get(i).getIntitule(),
					     listeQuestionsTest.get(i).getIntitule());
		}
		GestionQuestions.ajouter(listeQuestionsTest.get(2));
		assertTrue(listesMemesQuestions(listeQuestionsTest,
									    GestionQuestions
					                    .getListeToutesQuestions()));
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.gestion.GestionQuestions#supprimer(Question[])}.
	 */
	@Test
	public void testSupprimer() {
		ArrayList<Question> questionsTest = listeQuestionsTest;
		
		System.out.println("Liste méthode : ");
		for (int i = 0; i < listeQuestionsTest.size(); i++) {
			GestionQuestions.ajouter(listeQuestionsTest.get(i));
			System.out.println(listeQuestionsTest.get(i).getIntitule());
		}
		System.out.println("\n\nListe test : ");
		
		for (Question q : questionsTest) {
			System.out.println(q.getIntitule());
		}
		
		System.out.println("\n\n");
		
		// Tests de suppression de 1 question en 1 question
		for (int i = 0; i < listeQuestionsTest.size() / 2; i++) {
			System.out.println("\nETAPE N°" + i);
			Question[] questionsASupprimer = new Question[1];
			questionsASupprimer[0] = listeQuestionsTest.get(i);
			System.out.println("Question à supprimer = " + questionsASupprimer[0].getIntitule());
			System.out.println(GestionQuestions.getListeToutesQuestions().get(0).getIntitule());
			System.out.println(questionsTest.get(0).getIntitule());
			
			GestionQuestions.supprimer(questionsASupprimer);
			questionsTest.remove(0);
			
			System.out.println(GestionQuestions.getListeToutesQuestions().size());
			System.out.println(questionsTest.size());
			
			assertTrue(listesMemesQuestions(questionsTest,
		  	                                GestionQuestions
    			  	                        .getListeToutesQuestions()));
		}
		
		// Test de suppression de plusieurs questions
//		int deuxiemeMoitie = questions.length / 2;
//		Categorie[] categoriesASupprimer = new Categorie[deuxiemeMoitie];
//		for (int i = deuxiemeMoitie, j = 0; i < questions.length; i++, j++) {
//			categoriesASupprimer[j] = new Categorie(questions[i]);
//			listeQuestionsTest.remove(1);
//		}
//		GestionQuestions.supprimer(categoriesASupprimer); 
//		
//		assertTrue(listesMemesQuestions(listeQuestionsTest,
//				 						 GestionQuestions
//				 						 .getListeToutesQuestions()));
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.gestion.GestionQuestions#questionExiste(String)}.
	 */
	@Test
	public void testQuestionExiste() {
		for (int i = 0; i < listeQuestionsTest.size(); i++) {
			assertEquals(-1, GestionQuestions
							 .questionExiste(listeQuestionsTest
									 		 .get(i).getIntitule()));
			GestionQuestions.ajouter(listeQuestionsTest.get(i));
			assertNotEquals(-1, GestionQuestions
					 			.questionExiste(listeQuestionsTest
					 			.get(i).getIntitule()));
		}
	}

}
