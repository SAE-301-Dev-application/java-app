package info2.sae301.quiz.reseau;

import info2.sae301.quiz.modeles.Jeu;

public class Import {
	
	private static Jeu jeu;
	
	private static String questionNonAjoutes;
	
	public static void ajoutDonnes(String cheminFichier) {
		extractionParLigne(cheminFichier);
	}
	
	private static String[] extractionParLigne(String cheminFichier) {
		return new String[] {""}; //STUB
	}
	
	public static String getQuestionNonAjoutes() {
		return questionNonAjoutes;
	}
	
}
