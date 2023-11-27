/*
 * Import.java									                    27 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.reseau;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;

/**
 * Importation des données d'un fichier CSV et ajout aux données
 * existantes de l'application.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class Import {
	
	private static Jeu jeu;
	
	/**
	 * Les questions du fichier CSV non ajoutées à la liste
	 * des questions existantes.
	 */
	private static ArrayList<String> questionsNonAjoutees;
	
	/**
	 * TODO Jdoc
	 * 
	 * @param cheminFichier
	 */
	static void importation(String cheminFichier) {
		// initialisation
		jeu = Quiz.jeu;
		questionsNonAjoutees = new ArrayList<String>();
		
		extractionDonnees(cheminFichier);
	}
	
	/**
<<<<<<< Updated upstream
	 * Créé et ajoute à la liste des questions en mémoire la question dont
	 * les données sont en paramètre sous forme d'une chaîne de caractères.
=======
	 * Ajout des données dans l'application
>>>>>>> Stashed changes
	 * 
	 * @param donneesQuestion Chaîne de caractères contenant
	 *                        les données d'une question à créer.
	 */
	public static void creationQuestion(String donneesQuestion) {
		String intituleCategorie,
		       intituleQuestion,
		       reponseJuste,
		       feedback;
		
		String[] donneesDecoupees,
		         reponsesFausses;
		
		int niveauDifficulte;
		
		donneesDecoupees
		= donneesQuestion.split(";(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		
		
		for (int i = 0; i < donneesDecoupees.length; i++) {
			donneesDecoupees[i] = retirerGuillemetsInvalides(donneesDecoupees[i]);
			System.out.print(donneesDecoupees[i] + "\t");
		}
		
		intituleCategorie = donneesDecoupees[0].trim();
		
		try {
			niveauDifficulte = Integer.parseInt(donneesDecoupees[1].trim()); //peut générer une erreur
		} catch (NumberFormatException e) {
			niveauDifficulte = 1;
		}
		
		intituleQuestion = donneesDecoupees[2].trim();
		reponseJuste = donneesDecoupees[3].trim();
		
		reponsesFausses = new String[] {
			donneesDecoupees[4].trim(), donneesDecoupees[5].trim(),
			donneesDecoupees[6].trim(), donneesDecoupees[7].trim()};
		
		feedback = donneesDecoupees[8].trim();
		
		if (jeu.indiceCategorie(intituleCategorie) == -1) {
			jeu.creerCategorie(intituleCategorie);
		}
		
		if (jeu.indiceQuestion(intituleQuestion, intituleCategorie,
				               reponseJuste, reponsesFausses) == -1) {
			jeu.creerQuestion(intituleQuestion, reponseJuste,
					          reponsesFausses, niveauDifficulte,
					          feedback, intituleCategorie);
		} else {
			questionsNonAjoutees.add(intituleQuestion);
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
	
	/**
	 * Retrait des guillemets doublés par le formattage CSV d'une phrase
	 * passée en paramètre.
	 * 
	 * @param phrase Phrase à modifier.
	 * @return la phrase avec des guillemets initiaux.
	 */
	private static String retirerGuillemetsInvalides(String phrase) {
		final char CARACTERE_QUELCONQUE = 'µ';
		
		final char GUILLEMET = '"';
		
		String resultat;
		
		resultat = "";
		
		for (int i = 0; i < phrase.length(); i++) {
			char caractereCourant = phrase.charAt(i);
			char caractereSuivant = i + 1 < phrase.length()
					                ? phrase.charAt(i + 1)
					                : CARACTERE_QUELCONQUE;
			
			if (caractereCourant == GUILLEMET
			    && caractereSuivant == GUILLEMET) {
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
	
	/**
	 * Lecture du CSV ligne par ligne,
	 * Pour chaque lignes, les données sont extraites
	 * et envoyait à la méthode ajoutDonnees() pour que celle-ci
	 * soient ajouter à l'application.
	 * @param cheminFichier
	 */
	private static void extractionDonnees(String cheminFichier) {
		String ligne;
		String[] donnees;
		try (BufferedReader fichier = new BufferedReader
							(new FileReader(cheminFichier))) {
			//ne pas affecté le contenue de la première ligne;
			fichier.readLine();
			while ((ligne = fichier.readLine()) != null) {
				creationQuestion(ligne);
				System.out.println();
			}
		} catch (IOException e) {
			//Cette façon de gérer cette erreur est temporaire.
			System.out.println(e.getMessage());
		}
	}
	
	/** @return Les questions non ajoutées (déjà présentes ou autre). */
	public static ArrayList<String> getQuestionNonAjoutes() {
		return questionsNonAjoutees;
	}
	
}
