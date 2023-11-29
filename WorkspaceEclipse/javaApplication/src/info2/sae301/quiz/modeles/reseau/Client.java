/*
 * Client.java								                        27 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles.reseau;

import info2.sae301.quiz.modeles.cryptographie.DiffieHellman;
import info2.sae301.quiz.modeles.cryptographie.Vigenere;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Client permettant de se connecter à un serveur afin d'importer les données
 * des questions et catégories proposées par le serveur.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class Client {
	
	/** Port sur lequel le serveur est accessible. */
	private final static int PORT_SERVEUR = 55432;
	
	/** Timeout mettant fin à la tentative de connexion après 5s. */
	private final static int TIMEOUT_CONNEXION = 5000;
	
	private static final String CONNEXION_OUVERTE
	= "Socket créée à l'adresse %s et sur le port %d.\n";
	
	private static final String INDICATION_RECEPTION_CLIENT
	= "Le client a reçu : ";
	
	private static final String INDICATION_CLE_VIGENERE_CHIFFREE
	= "\nClé de vigenère reçue :\n";
	
	private static final String INDICATION_CLE_VIGENERE_CLAIRE
	= "\nClé de vigenère déchiffrée :\n";
	
	private static final String INDICATION_CONFIRMATION_CLE
	= "Envoi confirmation réception clé vigenère.";
	
	/** Socket permettant la connexion au serveur. */
	private Socket socket;
	
	/** Flux par lequel les messages du serveur sont reçus. */
	private ObjectInputStream fluxEntree;
	
	/** Flux par lequel envoyer des messages au serveur. */
	private ObjectOutputStream fluxSortie;
	
	private String cleVigenere;
	
	/**
	 * Adresse IP du serveur.
	 * 127.0.0.1 signifie que le serveur est sûr le réseau courant.
	 */
	private String adresseServeur;
	
	/** Puissance utilisée pour la méthode de Diffie Hellman */
	private int puissanceSecrete;
	
	/** Entier secret utilisé pour déchiffrer la clé de vigenère. */
	private int entierServeur;
	
	/** Entier utilisé pour chiffrer et déchiffrer la clé de vigenère. */
	private int entierSecret;
	
	/**
	 * Création d'une socket qui va se connecter à un serveur dont l'adresse IP
	 * et le port sont spécifiés dans les paramètres d'instanciation.
	 * 
	 * @throws IOException si la création de la socket échoue.
	 * @throws SocketTimeoutException si le timeout expire avant la connexion.
	 */
	private void creerSocket() throws IOException, SocketTimeoutException {
        this.socket = new Socket();
        
        InetSocketAddress adresse
        = new InetSocketAddress(this.adresseServeur, PORT_SERVEUR);
        
        this.socket.connect(adresse, TIMEOUT_CONNEXION);
	}
	
	
	/**
	 * Création d'un flux d'entrée pour recevoir les objets (String)
	 * envoyés par le serveur.
	 * 
	 * @throws IOException si le flux ne peut être créé.
	 * @throws SocketTimeoutException si le timeout expire avant la connexion.
	 */
	private void creerFluxEntree() throws IOException, SocketTimeoutException {
		creerSocket();
		
        this.fluxEntree = new ObjectInputStream(this.socket.getInputStream());
	}
	
	
	/**
	 * Fermeture du flux d'entrée créé pour la réception
	 * des objets envoyés par le serveur.
	 * 
	 * @throws IOException si le flux ne peut être fermé.
	 */
	private void fermerFluxEntree() throws IOException {
		this.fluxEntree.close();
		
		fermerSocket();
	}
	
	
	/**
	 * Création d'un flux de sortie pour envoyer des objets (String)
	 * au serveur.
	 * 
	 * @throws IOException si le flux ne peut être créé.
	 * @throws SocketTimeoutException si le timeout expire avant la connexion.
	 */
	private void creerFluxSortie() throws IOException, SocketTimeoutException {
		creerSocket();
		
        this.fluxSortie = new ObjectOutputStream(this.socket.getOutputStream());
	}
	
	
	/**
	 * Fermeture du flux de sortie créé pour envoyer des objets (String)
	 * au serveur.
	 * 
	 * @throws IOException si le flux ne peut être fermé.
	 */
	private void fermerFluxSortie() throws IOException {
		this.fluxSortie.close();
		
		fermerSocket();
	}
	
	
	/**
	 * Envoi de l'entier du serveur et réception de l'entier du client
	 * afin de calculer l'entier secret de Diffie Hellman.
	 * 
	 * @throws IOException si l'envoi échoue.
	 * @throws ClassNotFoundException si le cast de la donnée reçue échoue.
	 */
	private void recevoirEnvoyerEntier()
	throws IOException, ClassNotFoundException {
		int entierAEnvoyer;
		
		this.puissanceSecrete = DiffieHellman.genererPuissance();
		
		entierAEnvoyer = DiffieHellman.puissanceNR(DiffieHellman.getGenerateur(),
				                                   this.puissanceSecrete);
        
        creerFluxEntree();
        
		System.out.println(String.format(CONNEXION_OUVERTE,
                           this.adresseServeur, PORT_SERVEUR));
        
        this.entierServeur = (int) this.fluxEntree.readObject();
        
        System.out.println("Réception de l'entier du serveur : "
                           + this.entierServeur);
        
        fermerFluxEntree();
        
		creerFluxSortie();
		
		System.out.println("\nEnvoi de l'entier du client au serveur : "
		                   + entierAEnvoyer);
		
		// Envoi au client de l'entier
        this.fluxSortie.writeObject(entierAEnvoyer);
        
        fermerFluxSortie();
        
        this.entierSecret
        = DiffieHellman.puissanceNR(this.entierServeur, puissanceSecrete);
	}
	
	
	/**
	 * Lecture de la clé de vigenère envoyée par le client
	 * et affichage sur console texte.
	 * 
	 * @throws IOException si la lecture ou la réponse échoue.
	 * @throws ClassNotFoundException si le cast de la clé échoue.
	 * @throws SocketTimeoutException si le timeout expire avant la connexion.
	 */
	public void recevoirCleVigenere()
	throws IOException, ClassNotFoundException, SocketTimeoutException {
		
		this.cleVigenere = "";
		
		creerFluxEntree();
		
		this.cleVigenere = ((String) this.fluxEntree.readObject()).substring(6);

		System.out.println(INDICATION_CLE_VIGENERE_CHIFFREE
				           + this.cleVigenere + "\n");
		
		fermerFluxEntree();
		
		System.out.println(INDICATION_CONFIRMATION_CLE);

        creerFluxSortie();
        
        this.fluxSortie.writeObject(INDICATION_RECEPTION_CLIENT + this.cleVigenere);
        
        fermerFluxSortie();
        
        System.out.println("\nEntier secret du client : " + this.entierSecret);
        
        Vigenere.setCle(this.cleVigenere);
		this.cleVigenere = Vigenere.dechiffrerCle(this.entierSecret);
		
        System.out.println(INDICATION_CLE_VIGENERE_CLAIRE
                           + this.cleVigenere + "\n");
	}
	
	
	/**
	 * Réception et déchiffrage des catégories cryptées envoyées par le serveur.
	 * 
	 * @param adresseServeur L'adresse IP sur laquelle le serveur est démarré.
	 * @throws IOException si l'import échoue.
	 * @throws ClassNotFoundException si le cast permettant de transformer
	 *         l'objet reçu en string renvoie une erreur.
	 * @throws SocketTimeoutException si le timeout expire avant la connexion.
	 * @return les noms des catégories reçues.
	 */
	public String[] recevoirCategories(String adresseServeur)
	throws IOException, ClassNotFoundException, SocketTimeoutException {		
		boolean envoiFini;
		
		String nomCategorieCrypte,
		       nomCategorieDecrypte;
		
		ArrayList<String> nomsCategories;
		
		nomsCategories = new ArrayList<String>();
		
		this.adresseServeur = adresseServeur;
		
		System.out.println("Tentative de connexion au serveur en cours.\n");
		
		recevoirEnvoyerEntier();
		
		recevoirCleVigenere();
		
		envoiFini = false;
		
		System.out.println("Réception des noms des catégories :\n"
				           + "Nom crypté\tNom décrypté\n"
				           + "_____________________________");
		
		creerFluxEntree();
        
        // Lecture du nom de catégorie crypté envoyé par le serveur
		while (!envoiFini
			   && (nomCategorieCrypte = (String) this.fluxEntree.readObject())
			      != null) {
			
			if (nomCategorieCrypte.equals("finCategories")) {
				System.out.println();
				envoiFini = true;
			} else {
				// Décryptage du nom de catégorie crypté reçu
				nomCategorieDecrypte = Vigenere.dechiffrer(nomCategorieCrypte,
						                                   this.cleVigenere);
				
				System.out.println(nomCategorieCrypte + "\t" + nomCategorieDecrypte);
		        
				nomsCategories.add(nomCategorieDecrypte);
			}
		}
		
		fermerFluxEntree();
		
		return nomsCategories.toArray(new String[0]);
	}
	
	
	/**
	 * Réception et déchiffrage des questions cryptées envoyées par le serveur.
	 * 
	 * @throws IOException si la lecture renvoie une erreur.
	 * @return les noms des catégories reçues.
	 * @throws ClassNotFoundException si le cast échoue.
	 */
	private String[] recevoirQuestions()
	throws IOException, ClassNotFoundException {		
		boolean envoiFini;
		
		String donneesCrypteesQuestion,
		       donneesDecrypteesQuestion;
		
		String[] donneesQuestions = {""};
		
		envoiFini = false;
		
		System.out.println("Réception des données des questions :\n"
				           + "Données cryptées\n-----\nDonnées décryptées\n"
				           + "_________________________________");
		
		creerFluxEntree();
        
        // Lecture des données cryptées des questions envoyées par le serveur
		while (!envoiFini
			   && (donneesCrypteesQuestion = (String) this.fluxEntree.readObject())
			      != null) {
			
			if (donneesCrypteesQuestion.equals("finQuestions")) {
				envoiFini = true;
			} else {
				// Décryptage des données cryptées reçues
				donneesDecrypteesQuestion
				= Vigenere.dechiffrer(donneesCrypteesQuestion, this.cleVigenere);
				
				System.out.println(donneesCrypteesQuestion + "\n-----\n"
				                   + donneesDecrypteesQuestion + "\n");
		        
				donneesQuestions[donneesQuestions.length - 1]
				= donneesDecrypteesQuestion;
			}
		}
		
		fermerFluxEntree();
		
		return donneesQuestions;
	}
	
	
	/**
	 * Fermeture de la socket précédemment créée.
	 * 
	 * @throws IOException si la fermeture de la socket échoue.
	 */
	private void fermerSocket() throws IOException {
        this.socket.close();
	}
	
	
	/**
	 * Import des questions depuis un serveur.
	 * 
	 * @param adresseServeur L'adresse IP sur laquelle le serveur est démarré.
	 * @throws IOException si l'import échoue.
	 * @throws ClassNotFoundException si le cast permettant de transformer
	 *         l'objet reçu en string renvoie une erreur.
	 */
	public void importerQuestions(String adresseServeur)
	throws IOException, ClassNotFoundException {
		String[] questionsFormatStr;
		
		Import importation;

		importation = new Import();
		
		this.adresseServeur = adresseServeur;
		
		System.out.println(String.format(CONNEXION_OUVERTE,
               			                 this.adresseServeur, PORT_SERVEUR));
		
		recevoirEnvoyerEntier();
		
		recevoirCleVigenere();
		
		questionsFormatStr = this.recevoirQuestions();
		
		for (String questionCourante : questionsFormatStr) {
			importation.creationQuestion(questionCourante);
		}
	}
}