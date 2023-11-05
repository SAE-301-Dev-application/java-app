/*
 * GestionQuestions.java 									         5 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.gestion;

import info2.sae301.quiz.Question;

import java.util.ArrayList;

/**
 * Gestion des questions créées par l'utilisateur.
 * 
 * @author FAUGIERES Loïc
 * @author GUIRAUD Simon
 */
public class GestionQuestions {
	
	/** Toutes les questions qui ont été créées sur le jeu. */
	private static ArrayList<Question> listeToutesQuestions = new ArrayList<>();
	
	/** @return La liste des questions créées. */
	public static ArrayList<Question> getListeToutesQuestions() {
		return listeToutesQuestions;
	}
	
	/** Réinitialise/Vide la liste des questions. */
	public static void viderListeToutesQuestions() {
		listeToutesQuestions = new ArrayList<>();
	}
	
	/**
	 * Ajoute une nouvelle question dans la liste des questions 
	 * si celle-ci n'est pas déjà présente.
	 * @param aAjouter La question à ajouter.
	 */
	public static void ajouter(Question aAjouter) {
		if (questionExiste(aAjouter.getIntitule()) == -1) {
			listeToutesQuestions.add(aAjouter);
		}
	}
	
	/**
	 * Supprime de la liste des questions les questions spécifiées dans la
	 * liste en paramètre.
	 * @param aSupprimer Liste des questions à supprimer.
	 */
	public static void supprimer(Question[] aSupprimer) {
		for (Question questionCourante : aSupprimer) {
			int indiceQuestion = questionExiste(questionCourante.getIntitule());
			if (indiceQuestion != -1) {
				listeToutesQuestions.remove(indiceQuestion);
			}
		}
	}

	/**
	 * Vérification de l'existence d'une question dans la liste des questions.
	 * @param intitule L'intitulé de la question à chercher.
	 * @return L'indice dans la liste des questions de la question ayant pour
	 *         intitulé celui en paramètre ou -1 si la question n'existe pas.
	 */
 	public static int questionExiste(String intitule) {
		int resultat = -1;
		for (int i = 0;
			 i < listeToutesQuestions.size()
			 && resultat == -1;
			 i++) {
			if (listeToutesQuestions.get(i).getIntitule().equals(intitule)) {
				resultat = i;
			}
		}
		return resultat;
	}
	
}