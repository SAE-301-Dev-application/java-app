/*
 * Import.java									                    27 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.reseau;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.stage.FileChooser;

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
	
	/** Message d'erreur en cas de chemin inexistant à la sélection. */
	private final static String ERREUR_CHEMIN_INEXISTANT
	= "Ce chemin n'existe pas ou plus.";
	
	/** Instance de jeu courante. */
	private static Jeu jeu;
	
	
	private int nombreTotalQuestions;
	
	/**
	 * Les questions du fichier CSV non ajoutées à la liste
	 * des questions existantes.
	 */
	private ArrayList<String> questionsNonAjoutees;
	
	/** Chemin du fichier sélectionné. */
	private String cheminFichier;
	
	
	/**
	 * Initialisation de l'import sans données.
	 */
	public Import() {
		jeu = Quiz.jeu;
		
		this.nombreTotalQuestions = 0;
		this.questionsNonAjoutees = new ArrayList<>();
	}

	
	/**
	 * Permet à l'utilisateur de sélectionner les fichiers 
	 * à importer sur l'application.
	 * 
	 * @return Nombre de questions qui n'ont pas pu 
	 * 		   être ajoutées
	 */
	public void parcourir()
	throws FileNotFoundException, IOException {
		
		this.parcourirFichiers();
		this.importer();
		
	}
	
	
	/**
	 * Lecture du CSV ligne par ligne,
	 * Pour chaque lignes, les données sont extraites
	 * et envoyait à la méthode ajoutDonnees() pour que celle-ci
	 * soient ajouter à l'application.
	 */
	public void importer()
	throws IOException {
		
		String ligne;
		
		BufferedReader contenuFichier;
		
		contenuFichier = new BufferedReader(new FileReader(this.cheminFichier));
		
		// Ne pas affecter le contenu de la première ligne.
		contenuFichier.readLine();
		
		while ((ligne = contenuFichier.readLine()) != null) {
			creationQuestion(ligne);
		}
		
		contenuFichier.close();
		
	}
	
	
	/**
	 * Créé et ajoute à la liste des questions en mémoire la question dont
	 * les données sont en paramètre sous forme d'une chaîne de caractères.
	 * 
	 * @param donneesQuestion Chaîne de caractères contenant
	 *                        les données d'une question à créer.
	 */
	public void creationQuestion(String donneesQuestion) {
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
			niveauDifficulte = Integer.parseInt(donneesDecoupees[1].trim());
		} catch (NumberFormatException e) {
			niveauDifficulte = 1;
		}
		
		intituleQuestion = donneesDecoupees[2].trim();
		reponseJuste = donneesDecoupees[3].trim();
		
		reponsesFausses = new String[] {
			donneesDecoupees[4].trim(), 
			donneesDecoupees[5].trim(),
			donneesDecoupees[6].trim(), 
			donneesDecoupees[7].trim()
		};
		
		feedback = donneesDecoupees[8].trim();
		
		if (jeu.indiceCategorie(intituleCategorie) == -1) {
			jeu.creerCategorie(intituleCategorie);
		}
		
		this.nombreTotalQuestions++;
		
		if (jeu.indiceQuestion(intituleQuestion, intituleCategorie,
				               reponseJuste, reponsesFausses) == -1) {
			
			jeu.creerQuestion(intituleQuestion, reponseJuste,
					          reponsesFausses, niveauDifficulte,
					          feedback, intituleCategorie);
			
		} else {
			this.questionsNonAjoutees.add(intituleQuestion);
		}
		
		System.out.println();  // TODO: retirer en production
	}
	
	
	/**
	 * Sélection de fichier parmi ceux de l'utilisateur 
	 * via une fenêtre native.
	 * 
	 * Sauvegarde du chemin de fichier sélectionné.
	 */
	public void parcourirFichiers()
	throws FileNotFoundException {
		
		final FileChooser choixFichier = new FileChooser();
		
		// Titre de l'explorateur de fichier
        choixFichier.setTitle("Importer des données");
        
        // Filtre des extensions de fichier
        choixFichier.getExtensionFilters().add(
        		new FileChooser.ExtensionFilter("CSV", "*.csv"));

        // Ouverture de l'explorateur de fichier à la racine
        choixFichier.setInitialDirectory(new File(System.getProperty("user.home")));
        
        File fichierSelectionne = choixFichier.showOpenDialog(null);
        
        if (fichierSelectionne == null) {
        	throw new FileNotFoundException(ERREUR_CHEMIN_INEXISTANT);
        }
        
        this.cheminFichier = fichierSelectionne.getPath();
        
	}
	
	
	/** @return Nombre total de questions (ajoutées ou non). */
	public int getNombreTotalQuestions() {
		return this.nombreTotalQuestions;
	}
	
	
	/** 
	 * @return Liste des questions non ajoutées 
	 * (déjà existante ou autre). 
	 */
	public ArrayList<String> getQuestionsNonAjoutees() {
		return this.questionsNonAjoutees;
	}
	
	
	/**
	 * Retrait des guillemets doublés par le formattage CSV d'une phrase
	 * passée en paramètre.
	 * 
	 * @param phrase Phrase à modifier
	 * @return La phrase avec des guillemets initiaux
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
				
			} else if (caractereCourant != GUILLEMET) {
				resultat += caractereCourant;
			}
		}
		
		return resultat;
	}
	
	
	/**
	 * Créé et ajoute à la liste des catégories en mémoire les catégories dont
	 * les noms sont en paramètre.
	 * 
	 * @param nomsCategories Chaîne de caractères contenant tous les noms
	 *        des catégories à créer.
	 */
	public static void creationCategories(String[] nomsCategories) {
		for (String nomCategorie: nomsCategories) {	
			nomCategorie = nomCategorie.trim();
			
			if (jeu.indiceCategorie(nomCategorie) == -1) {
				jeu.creerCategorie(nomCategorie);
			}
		}		
	}


	/** @return Chemin du fichier courant. */
	public String getCheminFichier() {
		return this.cheminFichier;
	}
	
}
