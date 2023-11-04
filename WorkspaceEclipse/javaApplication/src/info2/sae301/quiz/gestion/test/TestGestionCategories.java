package info2.sae301.quiz.gestion.test;

import info2.sae301.quiz.Categorie;
import info2.sae301.quiz.gestion.GestionCategories;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires de la classe GestionCategories
 * 
 * @author Simon Guiraud
 */
class TestGestionCategories {
	
	
	private Categorie[] listeCategories = new Categorie[10];
	
	private ArrayList<Categorie> listeCategoriesTest = new ArrayList<>();
	
	@BeforeEach
	void init() {
		listeCategories[0] = new Categorie("Java");
		listeCategories[1] = new Categorie("Orthographe");
		listeCategories[2] = new Categorie("Grammaire");
		listeCategories[3] = new Categorie("Commentaire");
		listeCategories[4] = new Categorie("Affichage");
		listeCategories[5] = new Categorie("Affectation");
		listeCategories[6] = new Categorie("Variable");
		listeCategories[7] = new Categorie("Type");
		listeCategories[8] = new Categorie("If");
		listeCategories[9] = new Categorie("Condition");

		listeCategoriesTest.add(new Categorie("Java"));
		listeCategoriesTest.add(new Categorie("Orthographe"));
		listeCategoriesTest.add(new Categorie("Grammaire"));
		listeCategoriesTest.add(new Categorie("Commentaire"));
		listeCategoriesTest.add(new Categorie("Affichage"));
		listeCategoriesTest.add(new Categorie("Affectation"));
		listeCategoriesTest.add(new Categorie("Variable"));
		listeCategoriesTest.add(new Categorie("Type"));
		listeCategoriesTest.add(new Categorie("If"));
		listeCategoriesTest.add(new Categorie("Condition"));
	}
	
	@Test
	public void testCreer() {
		for (int i = 0; i < listeCategories.length; i++) {
			GestionCategories.creer(listeCategories[i]);
			assertEquals(GestionCategories.getListeToutesCategories().get(i),listeCategoriesTest.get(i));
		}
		assertEquals(GestionCategories.getListeToutesCategories(),listeCategoriesTest);
	}
	
	@Test
	public void testSupprimer() {
		for (int i = 0; i < listeCategories.length/2; i++) {
			Categorie[] CategoriesASupprimer = new Categorie[1];
			CategoriesASupprimer[0] = listeCategories[i];
			GestionCategories.supprimer(CategoriesASupprimer);
			listeCategoriesTest.remove(0);
			assertEquals(GestionCategories.getListeToutesCategories(),listeCategoriesTest);
		}
		int deuxiemeMoitie = listeCategories.length/2;
		Categorie[] CategoriesASupprimer = new Categorie[deuxiemeMoitie];
		for (int i = deuxiemeMoitie, j = 0; i < listeCategories.length; i++, j++) {
			CategoriesASupprimer[j] = listeCategories[i];
			listeCategoriesTest.remove(0);
		}
		GestionCategories.supprimer(CategoriesASupprimer); 
		assertEquals(GestionCategories.getListeToutesCategories(),listeCategoriesTest);
	} 

}
