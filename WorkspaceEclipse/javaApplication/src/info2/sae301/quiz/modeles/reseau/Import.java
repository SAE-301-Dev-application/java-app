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
import info2.sae301.quiz.modeles.Question;
import info2.sae301.quiz.exceptions.FormatCSVInvalideException;
import info2.sae301.quiz.exceptions.AdresseIPInvalideException;

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
	
	/** Message d'erreur en cas de serveur inconnu. */
	private final static String ERREUR_SERVEUR_INCONNU_MESSAGE
	= "Aucun serveur n'est connu avec l'adresse IP spécifiée.";
	
	/** Message d'erreur en cas de chemin inexistant à la sélection. */
	private final static String ERREUR_CHEMIN_INEXISTANT
	= "Ce chemin n'existe pas ou plus.";
	
	private static final String ERREUR_AUCUNE_QUESTION_IMPORTEE
	= "Aucune question n'a été importée.";
	
	/** Instance de jeu courante. */
	private static Jeu jeu;
	
	private static int nombreTotalQuestionsCrees = 0;
	
	private static ArrayList<String> questionsImportees = new ArrayList<>();
	
	private static ArrayList<String> lignesQuestionsCrees = new ArrayList<>();
	
	/** Chemin du fichier sélectionné. */
	private String cheminFichier;
	
	
	/**
	 * Initialisation de l'import sans données.
	 */
	public Import() {
		jeu = Quiz.jeu;
	}
	
	
	/**
	 * Lecture du CSV ligne par ligne,
	 * Pour chaque lignes, les données sont extraites
	 * et envoyait à la méthode ajoutDonnees() pour que celle-ci
	 * soient ajouter à l'application.
	 * 
	 * @throws FormatCSVInvalideException si le format du CSV à importer
	 *         ne correspond pas à un fichier contenant des questions de quiz.
	 * @throws IllegalArgumentException si un des caractères n'est pas chiffrable.
	 * @throws Exception si la lecture du CSV échoue.
	 */
	public void importerLocalement()
	throws FormatCSVInvalideException, IllegalArgumentException, Exception {
		
		final String REGEX_8_POINTS_VIRGULES
		= "^(?:[^;]*;){8,}[^;]*$";
		
		final String ENTETE_ATTENDUE
		= "Catégorie;Niveau;Libellé;juste;faux1;faux2;faux3;faux4;feedback";
		
		final String ERREUR_FORMAT_INVALIDE
		= "Le format du fichier CSV sélectionné ne correspond pas au format "
		  + "attendu.\nLa première ligne de votre fichier devrait être :\n"
		  + ENTETE_ATTENDUE;
		
		final String ERREUR_LIGNE_INVALIDE
		= "Le format du fichier CSV sélectionné ne correspond pas au format "
		  + "attendu.\nLa ligne numéro %d ne contient pas assez de données.";
		
		String premiereLigne,
		       ligneCourante;
		
		ArrayList<String> lignesCSV;
		
		int numeroLigneCourante;
		
		BufferedReader contenuFichier;
		
		lignesCSV = new ArrayList<String>();
		
		numeroLigneCourante = 1;
		
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
			while ((ligneCourante = contenuFichier.readLine()) != null) {
				numeroLigneCourante++;
				
				if (!ligneCourante.matches(REGEX_8_POINTS_VIRGULES)) {
					contenuFichier.close();
					throw new FormatCSVInvalideException(
						String.format(ERREUR_LIGNE_INVALIDE, numeroLigneCourante)
					);
				} else {
					lignesCSV.add(ligneCourante);
				}
			}
			
			for (String questionCourante : lignesCSV.toArray(new String[0])) {
				questionsImportees.add(questionCourante);
			}
		}
		
		contenuFichier.close();
		
		if (questionsImportees.size() <= 0) {
			throw new IllegalArgumentException(ERREUR_AUCUNE_QUESTION_IMPORTEE);
		}
	}
	
	
	/**
	 * Vérification que l'addresse IPV4 en paramètre respecte bien le format
	 * conventionnel d'une IPV4.
	 * 
	 * @param adresseIPV4 L'adresse IPV4 à vérifier.
	 * @throws AdresseIPInvalideException si l'adresse est invalide.
	 */
	public void verifierAdresseIPV4Valide(String adresseIPV4)
	throws AdresseIPInvalideException {
		final String REGEX_IPV4 = "^(\\d{1,3}.){3}\\d{1,3}$";
		
		final String ERREUR_IP_INVALIDE
		= "L'adresse IP " + adresseIPV4 + " ne correspond pas au format d'une"
	      + " adresse IPV4.\nExemple d'adresse IPV4 valide : 10.11.12.13";
		
		if (!adresseIPV4.matches(REGEX_IPV4)) {
			throw new AdresseIPInvalideException(ERREUR_IP_INVALIDE);
		}
	}
	
	
	/**
	 * Créé un client avec l'adresse IP renseignée dans la vue afin
	 * de se connecter à un serveur et récupérer les questions proposées.
	 * 
	 * @param adresseServeur L'adresse du serveur qui envoie les données
	 * @throws SocketTimeoutException si le timeout expire avant la connexion.
	 * @throws IllegalArgumentException si 
	 * @throws Exception si la création de la socket échoue.
	 */
	public void importerADistance(String adresseServeur)
	throws SocketTimeoutException, IllegalArgumentException, Exception {
		
		// Echange réseau avec Diffie Hellman
		Client client;

		client = new Client();
	
	    /*
		 * ___________________________________________________
		 * Echange réseau sans Diffie Hellman (juste Vigenère)
		 * ---------------------------------------------------
		 *
		 * ClientVigenere client;
		 *
		 * client = new ClientVigenere();
		 */

		try {
			for (String questionCourante : client.recevoirQuestions(adresseServeur)) {
				questionsImportees.add(questionCourante);
			}
			
			if (questionsImportees.size() <= 0) {
				throw new IllegalArgumentException(ERREUR_AUCUNE_QUESTION_IMPORTEE);
			}
			
		} catch (SocketTimeoutException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw new IOException(ERREUR_SERVEUR_INCONNU_MESSAGE);
		}
	}
	
	
	/**
	 * Extrait les données d'une question écrite sous forme de chaîne de
	 * caractères et les renvoie dans un tableau.
	 * 
	 * @param donneesQuestion Les données de la question à extraire.
	 * @return Un tableau contenant les données extraites de la question
	 *         sous forme de chaîne de caractères en paramètre.
	 */
	public static String[] extraireDonneesQuestion(String donneesQuestion) {
		final String REGEX_DONNEE_ENTIERE
		= ";(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
	    
	    String[] donnees;
	    
	    donnees = donneesQuestion.split(REGEX_DONNEE_ENTIERE, -1);
	    
	    // Créer un nouveau tableau avec les 9 premiers éléments
	    String[] nouvellesDonnees = new String[9];
	    System.arraycopy(donnees, 0, nouvellesDonnees, 0, 9);

	    for (int i = 0; i < nouvellesDonnees.length; i++) {
	    	// Supprimer les guillemets doubles
	    	nouvellesDonnees[i] = nouvellesDonnees[i].replaceAll("^\"|\"$", "");
	    }
		
		if (nouvellesDonnees[0] == null || nouvellesDonnees[0].isBlank()) {
			nouvellesDonnees[0] = "Général";
		}
		
		return nouvellesDonnees;
	}
	
	
	/**
	 * Vérification de l'existance d'une question en mémoire.
	 * 
	 * @param donneesQuestion Les données de la question à vérifier.
	 * @return true si la question existe en mémoire, false sinon.
	 */
	public static boolean verificationQuestionExiste(String[] donneesQuestion) {
		String intituleQuestion,
		       intituleCategorie,
		       reponseJuste;
		
		String[] reponsesFausses;
		
		intituleQuestion = donneesQuestion[2].trim();
		intituleCategorie = donneesQuestion[0].trim();
		reponseJuste = donneesQuestion[3].trim();
		
		if (jeu.indiceCategorie(intituleCategorie) == -1) {
			return false;
		}
		
		reponsesFausses = new String[] {
			donneesQuestion[4] != null ? donneesQuestion[4].trim() : null,
			donneesQuestion[5] != null ? donneesQuestion[5].trim() : null,
			donneesQuestion[6] != null ? donneesQuestion[6].trim() : null,
			donneesQuestion[7] != null ? donneesQuestion[7].trim() : null
		};
		
		return jeu.indiceQuestion(intituleQuestion, intituleCategorie,
				                  reponseJuste, reponsesFausses) != -1;
	}
	
	
	/**
	 * Créé et ajoute à la liste des questions en mémoire la question dont
	 * les données sont en paramètre sous forme de chaînes de caractères.
	 * 
	 * @param questions Chaînes de caractères contenant
	 *                  les données de la question à créer.
	 * @throws IllegalArgumentException si un des caractères n'est pas chiffrable.
	 */
	public static void creerQuestion(String donneesQuestion)
	throws IllegalArgumentException {
		String intituleCategorie,
		       intituleQuestion,
		       reponseJuste,
		       feedback;
		
		String[] donneesQuestionCourante,
		         reponsesFausses;
		
		Question questionCreee;
		
		int niveauDifficulte;
	
		donneesQuestionCourante
		= extraireDonneesQuestion(donneesQuestion);
		
		for (int indiceDonnee = 0;
			 indiceDonnee < donneesQuestionCourante.length;
			 indiceDonnee++) {
			
			donneesQuestionCourante[indiceDonnee]
			= retirerGuillemetsInvalides(donneesQuestionCourante[indiceDonnee]);
		}
		
		intituleCategorie = donneesQuestionCourante[0].trim();
		
		try {
			niveauDifficulte
			= Integer.parseInt(donneesQuestionCourante[1].trim());
		} catch (NumberFormatException e) {
			niveauDifficulte = 1;
		}
		
		intituleQuestion = donneesQuestionCourante[2].trim();
		reponseJuste = donneesQuestionCourante[3].trim();
		
		reponsesFausses = new String[] {
			donneesQuestionCourante[4].trim(), 
			donneesQuestionCourante[5].trim(),
			donneesQuestionCourante[6].trim(), 
			donneesQuestionCourante[7].trim()
		};
		
		feedback = donneesQuestionCourante[8].trim();
		
		if (jeu.indiceCategorie(intituleCategorie) == -1) {
			jeu.creerCategorie(intituleCategorie);
		}
		
		if (jeu.indiceQuestion(intituleQuestion, intituleCategorie,
				               reponseJuste, reponsesFausses) == -1) {
			
			System.out.println("- " + intituleQuestion);
			
			questionCreee
			= jeu.creerQuestion(intituleQuestion, reponseJuste,
					            reponsesFausses, niveauDifficulte,
					            feedback, intituleCategorie);
			
			nombreTotalQuestionsCrees++;
			
			lignesQuestionsCrees.add(questionCreee.donneesToString());
		
		}
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
	
	
	/**
	 * Retrait des guillemets doublés par le formattage CSV d'une phrase
	 * passée en paramètre.
	 * 
	 * @param phrase Phrase à modifier
	 * @return La phrase avec des guillemets initiaux
	 */
	public static String retirerGuillemetsInvalides(String phrase) {
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
	
	
	/** @return Nombre total de questions créées. */
	public static int getNombreTotalQuestionsCrees() {
		return nombreTotalQuestionsCrees;
	}


	/** @return Chemin du fichier courant. */
	public String getCheminFichier() {
		return this.cheminFichier;
	}
	
	
	/** @return Liste des questions importées */
	public static ArrayList<String> getQuestionsImportees() {
		return questionsImportees;
	}
	
	
	/** @return Liste lignes des questions créées. */
	public static ArrayList<String> getLignesQuestionsCrees() {
		return lignesQuestionsCrees;
	}
	
}
