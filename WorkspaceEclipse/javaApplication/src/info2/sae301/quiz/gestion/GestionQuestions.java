package info2.sae301.quiz.gestion;

import java.util.ArrayList;

import info2.sae301.quiz.Question;

/**
 * TODO comment types
 * @author Simon
 */
public class GestionQuestions {
	
	/**
	 * Liste des questions existantes
	 */
	private static ArrayList<Question> listeToutesQuestions = new ArrayList<>();
	
	/**
	 * Supprime de la liste des questions les questions 
	 * spécifiées dans la liste donnée en entrée
	 * @param aSupprimer liste des questions à supprimer
	 */
	public static void supprimer(Question[] aSupprimer) {
		for (int i = 0; i < aSupprimer.length;i++) {
			if (listeToutesQuestions.contains(aSupprimer[i])) {
				listeToutesQuestions.remove(listeToutesQuestions.indexOf(aSupprimer[i]));
			}
		}
	}
	
	/**
	 * Ajoute une nouvelle questions dans la liste 
	 * si celle-ci n'était pas déjà présente
	 * @param aAjouter la question à ajouter
	 */
	public static void ajouter(Question aAjouter) {
		if (!(listeToutesQuestions.contains(aAjouter))) {
			listeToutesQuestions.add(aAjouter);
		}
	}
	
	/**
	 * Accesseur de listeToutesQuestions
	 * @return listeToutesQuestions
	 */
	public static ArrayList<Question> getListeToutesQuestions() {
		return listeToutesQuestions;
	}

}
