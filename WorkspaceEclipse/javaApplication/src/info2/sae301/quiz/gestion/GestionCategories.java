/*
 * GestionCategories.java 									         5 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.gestion;

import info2.sae301.quiz.Categorie;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Gestion des catégories de questions créées par l'utilisateur.
 * 
 * @author FAUGIERES Loïc
 * @author GUIRAUD Simon
 */
public class GestionCategories {

	/**
	 * Toutes les catégories qui ont été créées sur le jeu. La 1ère catégorie
	 * est par défaut "Général".
	 */
	private static ArrayList<Categorie> listeToutesCategories
	= new ArrayList<>(Arrays.asList(new Categorie("Général")));
	
	/** @return La liste des catégories créées. */
	public static ArrayList<Categorie> getListeToutesCategories() {
		return listeToutesCategories;
	}
	
	/**
	 * Réinitialise/Vide la liste des catégories. Seule la catégorie
	 * "Général" restera. 
	 */
	public static void viderListeToutesCategories() {
		listeToutesCategories
		= new ArrayList<>(Arrays.asList(new Categorie("Général")));
	}
	
	/**
	 * Crée une nouvelle catégorie et l'ajoute à la liste des catégories.
	 * @param intitule L'intitulé de la catégorie à créer.
	 */
	public static void creer(String intitule) {
		if (categorieExiste(intitule) == -1) {
			Categorie categorieCreee = new Categorie(intitule);
			listeToutesCategories.add(categorieCreee);
		}
	}
	
	/**
	 * Supprime de la liste des catégories les catégories spécifiées dans la
	 * liste en paramètre.
	 * @param aSupprimer Liste des catégories à supprimer.
	 */
	public static void supprimer(Categorie[] aSupprimer) {
		for (Categorie categorieCourante : aSupprimer) {
			int indiceCategorie = categorieExiste(categorieCourante.getIntitule());
			if (indiceCategorie != -1
				&& !categorieCourante.getIntitule().equals("Général")) {
				listeToutesCategories.remove(indiceCategorie);
			}
		}
	}
	
	/**
	 * Vérification de l'existence d'une catégorie dans la liste des catégories.
	 * @param intitule L'intitulé de la catégorie à chercher.
	 * @return L'indice dans la liste des catégories de la catégorie ayant pour
	 *         intitulé celui en paramètre ou -1 si la catégorie n'existe pas.
	 */
 	public static int categorieExiste(String intitule) {
		int resultat = -1;
		for (int i = 0;
			 i < listeToutesCategories.size()
			 && resultat == -1;
			 i++) {
			if (listeToutesCategories.get(i).getIntitule().equals(intitule)) {
				resultat = i;
			}
		}
		return resultat;
	}

}