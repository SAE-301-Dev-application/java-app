/*
 * TestGestionCategories.java									     5 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.gestion.tests;

import info2.sae301.quiz.Categorie;
import info2.sae301.quiz.gestion.GestionCategories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires de la classe {@link info2.sae301.quiz.gestion.GestionCategories}.
 * 
 * @author FAUGIERES Loïc
 * @author GUIRAUD Simon
 */
class TestGestionCategories {
	
	private String[] nomsCategories = {
		"Général", "Java", "Orthographe", "Grammaire", "Commentaire",
		"Affichage", "Affectation", "Variable", "Type", "Condition"
	};
	
	private ArrayList<Categorie> listeInitiale = new ArrayList<Categorie>();
	
	private ArrayList<Categorie> listeCategoriesTest = new ArrayList<Categorie>();
	
	@BeforeEach
	void init() {
		GestionCategories.viderListeToutesCategories();
		listeInitiale.add(new Categorie(nomsCategories[0]));
		for (int i = 0; i < nomsCategories.length; i++) {
			listeCategoriesTest.add(new Categorie(nomsCategories[i]));
		}
	}
	
	/**
	 * Vérification que les listes en paramètre aient les mêmes catégories.
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
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.gestion.GestionCategories#getListeToutesCategories()}.
	 */
	@Test
	public void testGetListeToutesCategories() {
		ArrayList<Categorie> categories
		= new ArrayList<Categorie>(listeInitiale);
		
		assertTrue(listesMemesCategories(categories,
				                         GestionCategories
				                         .getListeToutesCategories()));
		
		for (int i = 1; i < nomsCategories.length; i++) {
			GestionCategories.creer(nomsCategories[i]);
			categories.add(new Categorie(nomsCategories[i]));

			assertEquals(categories.get(i).getIntitule(),
					     GestionCategories.getListeToutesCategories()
					                      .get(i).getIntitule());
		}
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.gestion.GestionCategories#viderListeToutesCategories()}.
	 */
	@Test
	public void testViderListeToutesCategories() {
		for (String nom : nomsCategories) {
			GestionCategories.creer(nom);
		}
		assertFalse(listesMemesCategories(listeInitiale,
					 				      GestionCategories
					                      .getListeToutesCategories()));
		GestionCategories.viderListeToutesCategories();
		assertTrue(listesMemesCategories(listeInitiale,
				   						 GestionCategories
				   						 .getListeToutesCategories()));
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.gestion.GestionCategories#creer(String)}.
	 */
	@Test
	public void testCreer() {
		GestionCategories.creer("Général");
		
	    assertThrows(IndexOutOfBoundsException.class, () -> {
	        GestionCategories.getListeToutesCategories().get(1);
	    });
		
		for (int i = 0; i < nomsCategories.length; i++) {
			GestionCategories.creer(nomsCategories[i]);
			assertEquals(GestionCategories.getListeToutesCategories()
										  .get(i).getIntitule(),
					     listeCategoriesTest.get(i).getIntitule());
		}
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.gestion.GestionCategories#supprimer(Categorie[])}.
	 */
	@Test
	public void testSupprimer() {
		// Test de suppression de la catégorie "Général"
		GestionCategories.supprimer(listeInitiale.toArray(new Categorie[0]));
		assertTrue(listesMemesCategories(listeInitiale,
				                         GestionCategories
				                         .getListeToutesCategories()));
		
		for (String nom : nomsCategories) {
			GestionCategories.creer(nom);
		}
		
		// Tests de suppression de 1 catégorie en 1 catégorie
		for (int i = 1; i < nomsCategories.length / 2; i++) {
			
			Categorie[] categoriesASupprimer = new Categorie[1];
			categoriesASupprimer[0] = new Categorie(nomsCategories[i]);
			
			GestionCategories.supprimer(categoriesASupprimer);
			listeCategoriesTest.remove(1);
			
			assertTrue(listesMemesCategories(listeCategoriesTest,
		  	                                 GestionCategories
    			  	                         .getListeToutesCategories()));
		}
		
		// Test de suppression de plusieurs catégories
		int deuxiemeMoitie = nomsCategories.length / 2;
		Categorie[] categoriesASupprimer = new Categorie[deuxiemeMoitie];
		for (int i = deuxiemeMoitie, j = 0; i < nomsCategories.length; i++, j++) {
			categoriesASupprimer[j] = new Categorie(nomsCategories[i]);
			listeCategoriesTest.remove(1);
		}
		GestionCategories.supprimer(categoriesASupprimer); 
		
		assertTrue(listesMemesCategories(listeCategoriesTest,
				 						 GestionCategories
				 						 .getListeToutesCategories()));
	}
	
	/**
	 * Méthode de test de la méthode
	 * {@link info2.sae301.quiz.gestion.GestionCategories#categorieExiste(String)}.
	 */
	@Test
	public void testCategorieExiste() {
		for (String nom : nomsCategories) {
			if (!nom.equals("Général")) {
				assertEquals(-1, GestionCategories.categorieExiste(nom));
			}
			GestionCategories.creer(nom);
			assertNotEquals(-1, GestionCategories.categorieExiste(nom));
		}
	}

}
