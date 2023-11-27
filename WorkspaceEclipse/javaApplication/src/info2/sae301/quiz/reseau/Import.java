package info2.sae301.quiz.reseau;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;

public class Import {
	
	private static Jeu jeu;
	
	private static ArrayList<String> questionNonAjoutes;
	
	static void importation(String cheminFichier) {
		// initialisation
		jeu = Quiz.jeu;
		questionNonAjoutes = new ArrayList<String>();
		
		extractionDonnees(cheminFichier);
	}
	
	private static void ajoutDonnes(String[] donnees) {
		String intituleCategorie,
		       intituleQuestion,
		       reponseJuste,
		       feedback;
		
		String[] reponsesFausses;
		
		int niveauDifficulte;
		
		for (int i = 0; i < donnees.length; i++) {
			donnees[i] = retirerGuillemetsInvalides(donnees[i]);
			System.out.print(donnees[i] + "\t");
		}
		
		intituleCategorie = donnees[0].trim();
		
		try {
			niveauDifficulte = Integer.parseInt(donnees[1].trim()); //peut générer une erreur
		} catch (NumberFormatException e) {
			niveauDifficulte = 1;
		}
		
		intituleQuestion = donnees[2].trim();
		reponseJuste = donnees[3].trim();
		
		reponsesFausses = new String[]
							{donnees[4].trim(), donnees[5].trim(),
							 donnees[6].trim(), donnees[7].trim()};
		
		feedback = donnees[8].trim();
		
		if (jeu.indiceCategorie(intituleCategorie) == -1) {
			jeu.creerCategorie(intituleCategorie);
		}
		
		if (jeu.indiceQuestion(intituleQuestion, intituleCategorie,
				reponseJuste, reponsesFausses) == -1) {
			jeu.creerQuestion(intituleQuestion, reponseJuste,
					reponsesFausses, niveauDifficulte,
					feedback, intituleCategorie);
		} else {
			questionNonAjoutes.add(intituleQuestion);
		}
	}
	
//	private static ArrayList<String> repFaussesFacultativeInitialise(String[] repFaussesFacultative) {
//		ArrayList<String> repFaussesInitialise = new ArrayList<String>();
//		for (String reponse : repFaussesFacultative) {
//			if (reponse != null && !reponse.isBlank()) {
//				repFaussesInitialise.add(reponse);
//			}
//		}
//		return repFaussesInitialise;
//	}
	
	private static String retirerGuillemetsInvalides(String phrase) {
		String resultat;
		resultat = "";
		final char caractereQuelconque = 'µ';
		
		for (int i = 0; i < phrase.length(); i++) {
			char caractereCourant = phrase.charAt(i);
			char caractereSuivant = i + 1 < phrase.length()
					                ? phrase.charAt(i + 1)
					                : caractereQuelconque;
			
			if (caractereCourant == '"'
			    && caractereSuivant == '"') {
				resultat += caractereCourant;
				if (i + 2 < phrase.length()) {
					i++;
				}
			} else if (caractereCourant != '"') {
				resultat += caractereCourant;
			}
		}
		return resultat;
	}
	
	private static void extractionDonnees(String cheminFichier) {
		String ligne;
		String[] donnees;
		try (BufferedReader fichier = new BufferedReader
							(new FileReader(cheminFichier))) {
			//ne pas affecté le contenue de la première ligne;
			fichier.readLine();
			while ((ligne = fichier.readLine()) != null) {
				donnees = ligne.split(";(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

				ajoutDonnes(donnees);
				System.out.println();
			}
		} catch (IOException e) {
			//Cette façon de gérer cette erreur est temporaire.
			System.out.println(e.getMessage());
		}
	}
	
	public static ArrayList<String> getQuestionNonAjoutes() {
		return questionNonAjoutes;
	}
	
}
