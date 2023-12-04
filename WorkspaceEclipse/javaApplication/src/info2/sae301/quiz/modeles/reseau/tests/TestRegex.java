package info2.sae301.quiz.modeles.reseau.tests;

public class TestRegex {
	
	public static void main(String[] args) {
		String regex = ";(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

	    String ligneCSV
	    = """
	      Variable;2;"Comment se nomme la ligne de code suivante :             int entier; ";une déclaration de variable;une initialisation de variable;cette ligne n'a pas d'appellation particulière;une affectation de variable;;Sur cette ligne, on précise le type de la variable et son nom
	      """;

	    String[] donnees = ligneCSV.split(regex, -1);
	    
	    // Créer un nouveau tableau avec les 9 premiers éléments
	    String[] nouveauxDonnees = new String[9];
	    System.arraycopy(donnees, 0, nouveauxDonnees, 0, 9);

	    for (int i = 0; i < nouveauxDonnees.length; i++) {
	    	nouveauxDonnees[i] = nouveauxDonnees[i].replaceAll("^\"|\"$", ""); // Supprimer les guillemets doubles
	        System.out.println(nouveauxDonnees[i]);
	        System.out.println("----------------");
	    }
	}
}
