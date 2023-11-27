package info2.sae301.quiz.reseau.tests;

public class TestRegex {
	
	public static void main(String[] args) {
		String ligneCSV = "eee\\;bb;gg";
		String[] donnees;
		
		donnees = ligneCSV.split("(?<!\\\\);(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		for (String donnee : donnees) {
			System.out.println(donnee);
		}
	}
}
