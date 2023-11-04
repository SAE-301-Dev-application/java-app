package info2.sae301.quiz.gestion;

import info2.sae301.quiz.Categorie;

import java.util.ArrayList;

/**
 * TODO comment types
 * @author Simon
 */
public class GestionCategories {

	/**
	 * Liste des catégories existantes
	 */
	private static ArrayList<Categorie> listeToutesCategories = new ArrayList<>();
	
	/**
	 * Supprime de la liste des catégories les categories 
	 * spécifiées dans la liste donnée en entrée
	 * @param aSupprimer liste des catégories à supprimer
	 */
	public static void supprimer(Categorie[] aSupprimer) {
		for (int i = 0; i < aSupprimer.length; i++) {
			if (listeToutesCategories.contains(aSupprimer[i])) {
				listeToutesCategories.remove(listeToutesCategories.indexOf(aSupprimer[i]));
			}
		}
	}
	
	/**
	 * Ajoute une nouvelle catégorie dans la liste 
	 * si celle-ci n'était pas déjà présente
	 * @param aCreer la catégorie à ajouter
	 */
	public static void creer(Categorie aCreer) {
		if (!(listeToutesCategories.contains(aCreer))) {
			listeToutesCategories.add(aCreer);
		}
	}
	
	/**
	 * Accesseur de listeToutesCategories
	 * @return listeToutesCategories
	 */
	public static ArrayList<Categorie> getListeToutesCategories() {
		return listeToutesCategories;
	}

}
