/*
 * Jeu.java             									         6 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Gestion des catégories et des questions créées par l'utilisateur.
 * 
 * @author FAUGIERES Loïc
 * @author GUIRAUD Simon
 */
public class Jeu {

	/**
	 * Toutes les catégories qui ont été créées sur le jeu. La 1ère catégorie
	 * est par défaut "Général".
	 */
	private ArrayList<Categorie> toutesLesCategories;
	
	/** Toutes les questions qui ont été créées sur le jeu. */
	private ArrayList<Question> toutesLesQuestions;
	
	/** Intitulé de la catégorie à renommer qui a été sélectionnée. */
	private String intituleCategorieSelectionnee;

	/**
	 * Construction d'une session de jeu initialisant ses questions
	 * et catégories à vide.
	 */
	public Jeu() {
		this.toutesLesCategories
		= new ArrayList<>(Arrays.asList(new Categorie("Général")));
		
		this.toutesLesQuestions = new ArrayList<>();
		
		this.intituleCategorieSelectionnee = null;
	}
	
	/** @return La liste des catégories créées. */
	public ArrayList<Categorie> getToutesLesCategories() {
		return toutesLesCategories;
	}
	
	/** @return La liste des questions créées. */
	public ArrayList<Question> getToutesLesQuestions() {
		return toutesLesQuestions;
	}
	
	/** @return L'intitulé de la catégorie sélectionnée. */
	public String getIntituleCategorieSelectionnee() {
		return intituleCategorieSelectionnee;
	}
	
	/** @param nom L'intitulé de la catégorie sélectionnée. */
	public void setIntituleCategorieSelectionnee(String intitule) {
		this.intituleCategorieSelectionnee = intitule;
	}
	
	/**
	 * Renomme la catégorie sélectionnée avec l'intitulé en paramètre.
	 * 
	 * @param nouveauIntitule Le nouveau intitulé de la catégorie.
	 */
	public void renommerCategorieSelectionnee(String nouveauIntitule) {
		final String CATEGORIE_INEXISTANTE
		= "La catégorie sélectionnée est inexistante en mémoire ou ne peut pas "
		  + "être renommée.";
		
		final String TAILLE_INVALIDE
		= "La taille d'un intitulé de catégorie doit être comprise entre 1 et 20.";
		
		if (nouveauIntitule.length() < 1 || nouveauIntitule.length() > 20) {
			throw new IllegalArgumentException(TAILLE_INVALIDE);
		}
		
		int indiceCategorie = categorieExiste(this.intituleCategorieSelectionnee);
		
		if (indiceCategorie > 0) {
			this.toutesLesCategories.get(indiceCategorie).setIntitule(nouveauIntitule);
			// Désélection de la catégorie pour le changement de vue
			this.intituleCategorieSelectionnee = null;
		} else {
			throw new IllegalArgumentException(CATEGORIE_INEXISTANTE);
		}
	}
	
	/**
	 * Réinitialise/Vide la liste des catégories. Seule la catégorie
	 * "Général" restera. 
	 */
	public void supprimerToutesCategories() {
		toutesLesCategories
		= new ArrayList<>(Arrays.asList(new Categorie("Général")));
	}
	
	/** Réinitialise/Vide la liste des questions. */
	public void supprimerToutesQuestions() {
		toutesLesQuestions = new ArrayList<>();
	}
	
	/**
	 * Crée une nouvelle catégorie et l'ajoute à la liste des catégories.
	 * @param intitule L'intitulé de la catégorie à créer.
	 */
	public void creerCategorie(String intitule) {
		if (categorieExiste(intitule) == -1) {
			Categorie categorieCreee = new Categorie(intitule);
			toutesLesCategories.add(categorieCreee);
		} else {
			throw new IllegalArgumentException("Cette catégorie existe déjà");
		}
	}
	
	/**
	 * Ajoute une nouvelle question dans la liste des questions 
	 * si celle-ci n'est pas déjà présente.
	 * @param aAjouter La question à ajouter.
	 */
	public void ajouterQuestion(Question aAjouter) {
		if (questionExiste(aAjouter.getIntitule()) == -1) {
			toutesLesQuestions.add(aAjouter);
		}
	}
	
	/**
	 * Supprime de la liste des catégories les catégories spécifiées dans la
	 * liste en paramètre.
	 * @param aSupprimer Liste des catégories à supprimer.
	 */
	public void supprimer(Categorie[] aSupprimer) {
		for (Categorie categorieCourante : aSupprimer) {
			int indiceCategorie = categorieExiste(categorieCourante.getIntitule());
			if (indiceCategorie != -1
				&& !categorieCourante.getIntitule().equals("Général")) {
				toutesLesCategories.remove(indiceCategorie);
			}
		}
	}
	
	/**
	 * Supprime de la liste des questions les questions spécifiées dans la
	 * liste en paramètre.
	 * @param aSupprimer Liste des questions à supprimer.
	 */
	public void supprimer(Question[] aSupprimer) {
		for (Question questionCourante : aSupprimer) {
			int indiceQuestion = questionExiste(questionCourante.getIntitule());
			if (indiceQuestion != -1) {
				toutesLesQuestions.remove(indiceQuestion);
			}
		}
	}
	
	/**
	 * Vérification de l'existence d'une catégorie dans la liste des catégories.
	 * @param intitule L'intitulé de la catégorie à chercher.
	 * @return L'indice dans la liste des catégories de la catégorie ayant pour
	 *         intitulé celui en paramètre ou -1 si la catégorie n'existe pas.
	 */
 	public int categorieExiste(String intitule) {
		int resultat = -1;
		for (int i = 0;
			 i < toutesLesCategories.size()
			 && resultat == -1;
			 i++) {
			if (toutesLesCategories.get(i).getIntitule().equals(intitule)) {
				resultat = i;
			}
		}
		return resultat;
	}
 	
	/**
	 * Vérification de l'existence d'une question dans la liste des questions.
	 * @param intitule L'intitulé de la question à chercher.
	 * @return L'indice dans la liste des questions de la question ayant pour
	 *         intitulé celui en paramètre ou -1 si la question n'existe pas.
	 */
 	public int questionExiste(String intitule) {
		int resultat = -1;
		for (int i = 0;
			 i < toutesLesQuestions.size()
			 && resultat == -1;
			 i++) {
			if (toutesLesQuestions.get(i).getIntitule().equals(intitule)) {
				resultat = i;
			}
		}
		return resultat;
	}

}