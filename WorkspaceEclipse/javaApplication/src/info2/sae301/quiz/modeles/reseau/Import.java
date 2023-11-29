/*
 * Import.java									                    27 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles.reseau;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import javafx.stage.FileChooser;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.fichiers.OutilsCSV;
import info2.sae301.quiz.exceptions.FormatCSVInvalideException;

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
	 * Lecture du CSV ligne par ligne,
	 * Pour chaque lignes, les données sont extraites
	 * et envoyait à la méthode ajoutDonnees() pour que celle-ci
	 * soient ajouter à l'application.
	 */
	public void importerLocalement()
	throws IOException, FormatCSVInvalideException {
		
		final String ENTETE_ATTENDUE
		= "Catégorie;Niveau;Libellé;juste;faux1;faux2;faux3;faux4;feedback";
		
		final String ERREUR_FORMAT_INVALIDE
		= "Le format du fichier CSV sélectionné ne correspond pas au format "
		  + "attendu.\nLa première ligne de votre fichier devrait être :\n"
		  + ENTETE_ATTENDUE;
		
		String premiereLigne,
		       ligneCourante;
		
		BufferedReader contenuFichier;
		
		contenuFichier = new BufferedReader(new FileReader(this.cheminFichier));
		
		/*
		 * Ne pas affecter le contenu de la première ligne et vérifier
		 * s'il correspond bien au CSV attendu.
		 */
		premiereLigne = (String) contenuFichier.readLine();
		
		if (premiereLigne == null || !premiereLigne.equals(ENTETE_ATTENDUE)) {
			contenuFichier.close();
			
			throw new FormatCSVInvalideException(ERREUR_FORMAT_INVALIDE);
		} else {
			OutilsCSV.initialiserFichierCSV();
			
			while ((ligneCourante = contenuFichier.readLine()) != null) {
				creationQuestion(ligneCourante);
				OutilsCSV.ecrireLigneCSV(ligneCourante);
			}
		}
		
		contenuFichier.close();
		
	}
	
	
	/**
	 * Créé un client avec l'adresse IP renseignée dans la vue afin
	 * de se connecter à un serveur et récupérer les questions proposées.
	 * 
	 * @throws ClassNotFoundException si le cast des données reçues échoue.
	 * @throws IOException si la création de la socket échoue.
	 * @throws SocketTimeoutException si le timeout expire avant la connexion.
	 */
	public void importerADistance(String adresseServeur)
	throws ClassNotFoundException, SocketTimeoutException, IOException {
		String[] nomsCategories;
		
		nomsCategories = new Client().recevoirCategories(adresseServeur);
		
		System.out.println("Catégories à créer : ");
		
		for (int i = 0; i < nomsCategories.length; i++) {
			System.out.println(nomsCategories[i]);
		}
		
		this.creationCategories(nomsCategories);
	}
	
	
	/**
	 * Créé et ajoute à la liste des catégories en mémoire les catégories dont
	 * les noms sont en paramètre.
	 * 
	 * @param nomsCategories Chaîne de caractères contenant tous les noms
	 *        des catégories à créer.
	 */
	public void creationCategories(String[] nomsCategories) {
		for (String nomCategorie: nomsCategories) {	
			nomCategorie = nomCategorie.trim();
			
			if (jeu.indiceCategorie(nomCategorie) == -1) {
				jeu.creerCategorie(nomCategorie);
			}
		}		
	}
	
	
	/**
	 * Créé et ajoute à la liste des questions en mémoire la question dont
	 * les données sont en paramètre sous forme d'une chaîne de caractères.
	 * 
	 * @param donneesQuestion Chaîne de caractères contenant
	 *                        les données d'une question à créer.
	 */
	public void creationQuestion(String donneesQuestion) {
		final String REGEX_DONNEE_ENTIERE = ";(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
		
		String intituleCategorie,
		       intituleQuestion,
		       reponseJuste,
		       feedback;
		
		String[] donneesDecoupees,
		         reponsesFausses;
		
		int niveauDifficulte;
		
		donneesDecoupees
		= donneesQuestion.split(REGEX_DONNEE_ENTIERE, -1);
		
		
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
        choixFichier.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("CSV", "*.csv"));

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


	/** @return Chemin du fichier courant. */
	public String getCheminFichier() {
		return this.cheminFichier;
	}
	
}
