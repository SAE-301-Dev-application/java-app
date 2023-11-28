/*
 * OutilsCSV.java									28 nov. 2023
 * IUT de Rodez, no copyright ni "copyleft"
 */
package info2.sae301.quiz.reseau;

import java.io.FileWriter;
import java.util.Iterator;

/**
 * Outils et méthodes pouvant être utiles pour des actions sur 
 * un fichier CSV
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class OutilsCSV {
	
	/** Délimiteurs qui doivent être dans le fichier CSV */
	private static final String DELIMITEUR = ",";
	
	/** Séparateurs qui doivent être dans le fichier CSV */
	private static final String SEPARATEUR = "\n";

	/** En-tête de fichier */
	private static final String LIGNE_1 = "Title,Author,Year";


	public static void ecritureCSV(String[] donneesCategories,
			String[] donneesQuestions) {

	}
}
