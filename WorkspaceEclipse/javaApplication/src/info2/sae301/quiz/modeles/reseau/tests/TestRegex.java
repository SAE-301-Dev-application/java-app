/*
 * TestRegex.java										             4 déc. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles.reseau.tests;

/**
 * Classe de test du regex découpant les lignes du fichier CSV afin
 * d'en extraire les données.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class TestRegex {
	
	private final static String REGEX = ";(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
	
	private final static String LIGNE_CSV
	= "Variable;2;\"Comment se nomme la ligne de code suivante :             "
	  + "int entier; \";une déclaration de variable;une initialisation de "
	  + "variable;cette ligne n'a pas d'appellation particulière;une "
	  + "affectation de variable;;Sur cette ligne, on précise le type de la "
	  + "variable et son nom";
	
	/**
	 * Méthode principale de test.
	 * 
	 * @param args inutilisé.
	 */
	public static void main(String[] args) {
	    String[] donnees = LIGNE_CSV.split(REGEX, -1);
	    
	    // Créer un nouveau tableau avec les 9 premiers éléments
	    String[] nouveauxDonnees = new String[9];
	    System.arraycopy(donnees, 0, nouveauxDonnees, 0, 9);

	    for (int i = 0; i < nouveauxDonnees.length; i++) {
	    	// Supprimer les guillemets doubles
	    	nouveauxDonnees[i] = nouveauxDonnees[i].replaceAll("^\"|\"$", "");
	    	
	        System.out.println(nouveauxDonnees[i]);
	        System.out.println("----------------");
	    }
	}
}
