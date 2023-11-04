package info2.sae301.quiz.gestion.test;

import info2.sae301.quiz.Categorie;
import info2.sae301.quiz.Question;
import info2.sae301.quiz.gestion.GestionQuestions;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires de la classe GestionCategories
 * 
 * @author Simon Guiraud
 */
class TestGestionQuestions {
	
	private Question[] listeQuestions = new Question[10];
	
	private ArrayList<Question> listeQuestionsTest = new ArrayList<>();
	
	Categorie Tests = new Categorie("Tests");
	
	
	@BeforeEach
	void init() {
		listeQuestions[0] = new Question("Intitulé de question 1", "Ceci est la réponse juste", 
						new String[]{"Réponse fausse 1","Réponse fausse 2","Réponse fausse 3"}, 2, Tests);
		listeQuestions[1] = new Question("Intitulé de question 2", "Ceci est la réponse juste", 
						new String[]{"Réponse fausse 1","Réponse fausse 2"}, 2, Tests);
		listeQuestions[2] = new Question("Intitulé de question 3", "Ceci est la réponse juste", 
						new String[]{"Réponse fausse"}, 2, Tests);
		listeQuestions[3] = new Question("Intitulé de question 4", "Ceci est la réponse juste", 
						new String[]{"Réponse fausse 1","Réponse fausse 2","Réponse fausse 3"}, 2, Tests);
		listeQuestions[4] = new Question("Intitulé de question 5", "Ceci est la réponse juste", 
						new String[]{"Réponse fausse 1","Réponse fausse 2","Réponse fausse 3"}, 2, Tests);
		listeQuestions[5] = new Question("Intitulé de question 6", "Ceci est la réponse juste", 
						new String[]{"Réponse fausse"}, 2, Tests);
		listeQuestions[6] = new Question("Intitulé de question 7", "Ceci est la réponse juste", 
						new String[]{"Réponse fausse"}, 2, Tests);
		listeQuestions[7] = new Question("Intitulé de question 8", "Ceci est la réponse juste", 
						new String[]{"Réponse fausse 1","Réponse fausse 2"}, 2, Tests);
		listeQuestions[8] = new Question("Intitulé de question 9", "Ceci est la réponse juste", 
						new String[]{"Réponse fausse 1","Réponse fausse 2","Réponse fausse 3"}, 2, Tests);
		listeQuestions[9] = new Question("Intitulé de question 10", "Ceci est la réponse juste", 
						new String[]{"Réponse fausse 1","Réponse fausse 2"}, 2, Tests);

		listeQuestionsTest.add(new Question("Intitulé de question 1", "Ceci est la réponse juste", 
				new String[]{"Réponse fausse 1","Réponse fausse 2","Réponse fausse 3"}, 2, Tests));
		listeQuestionsTest.add(new Question("Intitulé de question 2", "Ceci est la réponse juste", 
				new String[]{"Réponse fausse 1","Réponse fausse 2"}, 2, Tests));
		listeQuestionsTest.add(new Question("Intitulé de question 3", "Ceci est la réponse juste", 
				new String[]{"Réponse fausse"}, 2, Tests));
		listeQuestionsTest.add(new Question("Intitulé de question 4", "Ceci est la réponse juste", 
				new String[]{"Réponse fausse 1","Réponse fausse 2","Réponse fausse 3"}, 2, Tests));
		listeQuestionsTest.add(new Question("Intitulé de question 5", "Ceci est la réponse juste", 
				new String[]{"Réponse fausse 1","Réponse fausse 2","Réponse fausse 3"}, 2, Tests));
		listeQuestionsTest.add(new Question("Intitulé de question 6", "Ceci est la réponse juste", 
				new String[]{"Réponse fausse"}, 2, Tests));
		listeQuestionsTest.add(new Question("Intitulé de question 7", "Ceci est la réponse juste", 
				new String[]{"Réponse fausse"}, 2, Tests));
		listeQuestionsTest.add(new Question("Intitulé de question 8", "Ceci est la réponse juste", 
				new String[]{"Réponse fausse 1","Réponse fausse 2"}, 2, Tests));
		listeQuestionsTest.add(new Question("Intitulé de question 9", "Ceci est la réponse juste", 
				new String[]{"Réponse fausse 1","Réponse fausse 2","Réponse fausse 3"}, 2, Tests));
		listeQuestionsTest.add(new Question("Intitulé de question 10", "Ceci est la réponse juste", 
				new String[]{"Réponse fausse 1","Réponse fausse 2"}, 2, Tests));
	}
	
	@Test
	public void testCreer() {
		for (int i = 0; i < listeQuestions.length; i++) {
			GestionQuestions.ajouter(listeQuestions[i]);
			assertEquals(GestionQuestions.getListeToutesQuestions().get(i),listeQuestionsTest.get(i));
		}
		assertEquals(GestionQuestions.getListeToutesQuestions(),listeQuestionsTest);
	}
	
	@Test
	public void testSupprimer() {
		for (int i = 0; i < listeQuestions.length/2; i++) {
			Question[] CategoriesASupprimer = new Question[1];
			CategoriesASupprimer[0] = listeQuestions[i];
			GestionQuestions.supprimer(CategoriesASupprimer);
			listeQuestionsTest.remove(0);
			assertEquals(GestionQuestions.getListeToutesQuestions(),listeQuestionsTest);
		}
		int deuxiemeMoitie = listeQuestions.length/2;
		Question[] CategoriesASupprimer = new Question[deuxiemeMoitie];
		for (int i = deuxiemeMoitie, j = 0; i < listeQuestions.length; i++, j++) {
			CategoriesASupprimer[j] = listeQuestions[i];
			listeQuestionsTest.remove(0);
		}
		GestionQuestions.supprimer(CategoriesASupprimer); 
		assertEquals(GestionQuestions.getListeToutesQuestions(),listeQuestionsTest);
	}

}
