/*
 * Serveur.java								                        27 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles.reseau;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Question;
import info2.sae301.quiz.modeles.cryptographie.DiffieHellman;
import info2.sae301.quiz.modeles.cryptographie.Vigenere;

/**
 * Serveur permettant d'exporter les données des questions et catégories
 * vers un client connecté.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class Serveur {
	
	/** Timeout mettant fin à la tentative de connexion après 10s. */
	private final static int TIMEOUT_CONNEXION = 10000;
	
	private static final String CONNEXION_OUVERTE
	= "Le serveur est connecté sur le port %d.\nEn attente de la"
	  + " connexion d'un client.\n";
	
	private final static String INDICATION_REPONSE
	= "\nRéponse du client : ";
	
	/**
	 * Délimiteur de séparation de toutes les questions dans l'objet envoyé.
	 * Le délimiteur contient volontairement le caractère … non chiffrable
	 * (cf dictionnaire chiffrable) afin d'éviter que l'utilisateur crée une
	 * question contenant ce délimiteur dans un intitulé et qu'un problème
	 * d'import apparaisse.
	 */
	private final static String DELIMITEUR = "/delimiteur…/";
	
	/** Socket pour créer le serveur sur le réseau. */
	private ServerSocket socketServeur;
	
	/** Socket permettant la connexion au client. */
	private Socket socketClient;
	
	/** Message reçu du client. */
	private ObjectInputStream fluxEntree;
	
	/** Message envoyé au client. */
	private ObjectOutputStream fluxSortie;
	
	/** Clé de Vigenère permettant de crypter le CSV de données. */
	private String cleVigenere;
	
	/** Port utilisé par le serveur sur le réseau local. */
	private int portServeur;
	
	/** Puissance utilisée pour la méthode de Diffie Hellman */
	private int puissanceSecrete;
	
	/** Entier secret utilisé pour déchiffrer la clé de vigenère. */
	private int entierClient;
	
	/** Entier utilisé pour chiffrer et déchiffrer la clé de vigenère. */
	private int entierSecret;
	
	
	/**
	 * Initialisation d'un serveur dont le port vaut par défaut 55432.
	 */
	public Serveur() {
		this.portServeur = 55432;
	}
	
	
	/**
	 * Crée un serveur sur le réseau local.
	 * 
	 * @throws IOException si la socket ne peut être créée.
	 */
	public void creerServeur() throws IOException {
		this.socketServeur = new ServerSocket(this.portServeur);
		this.socketServeur.setSoTimeout(TIMEOUT_CONNEXION);
	}
	
	
	/**
	 * Accepte la connexion d'un éventuel client.
	 * 
	 * @throws IOException Si la connexion ne peut être établie.
	 * @throws SocketTimeoutException Si la connexion n'est pas réalisée en 10s.
	 */
	public void accepterConnexion() throws IOException, SocketTimeoutException {
		try {
			this.socketClient = this.socketServeur.accept();			
		} catch (IOException e) {
			fermerSockets();
			throw new SocketTimeoutException();
		}
	}
	
	
	/**
	 * Création d'un flux d'entrée pour recevoir les objets (String)
	 * envoyés par le client.
	 * 
	 * @throws IOException Si la création du flux d'entrée échoue.
	 * @throws SocketTimeoutException Si la connexion n'est pas réalisée en 10s.
	 */
	private void creerFluxEntree() throws IOException, SocketTimeoutException {
		accepterConnexion();
		this.fluxEntree = new ObjectInputStream(this.socketClient.getInputStream());
	}
	
	
	/**
	 * Fermeture du flux d'entrée créé pour la réception
	 * des objets envoyés par le client.
	 * 
	 * @throws IOException si le flux ne peut être fermé.
	 */
	private void fermerFluxEntree() throws IOException {
		this.fluxEntree.close();
	}
	
	
	/**
	 * Création d'un flux de sortie pour envoyer des objets (String)
	 * au client.
	 * 
	 * @throws IOException Si la création du flux de sortie échoue.
	 * @throws SocketTimeoutException Si la connexion n'est pas réalisée en 10s.
	 */
	private void creerFluxSortie() throws IOException, SocketTimeoutException {
		accepterConnexion();
		this.fluxSortie = new ObjectOutputStream(this.socketClient.getOutputStream());
	}
	
	
	/**
	 * Fermeture du flux de sortie créé pour envoyer des objets (String)
	 * au client.
	 * 
	 * @throws IOException si le flux ne peut être fermé.
	 */
	private void fermerFluxSortie() throws IOException {
		this.fluxSortie.close();
	}
	
	
	/**
	 * Envoi de l'entier du serveur et réception de l'entier du client
	 * afin de calculer l'entier secret de Diffie Hellman.
	 * 
	 * @throws IOException Si l'envoi ou la réception d'un objet échoue.
	 * @throws ClassNotFoundException Si l'objet reçu n'est pas un String.
	 * @throws SocketTimeoutException Si la connexion n'est pas réalisée en 10s.
	 */
	private void envoyerRecevoirEntier()
	throws IOException, ClassNotFoundException, SocketTimeoutException {
		int entierAEnvoyer;
		
		this.puissanceSecrete = DiffieHellman.genererPuissance();
		
		entierAEnvoyer = DiffieHellman.puissanceNR(DiffieHellman.getGenerateur(),
				                                   this.puissanceSecrete);
		
		creerFluxSortie();
		
		System.out.println("Envoi de l'entier du serveur au client : "
		                   + entierAEnvoyer);
		
		// Envoi au client de l'entier
        this.fluxSortie.writeObject(entierAEnvoyer);
        
        fermerFluxSortie();
        
        creerFluxEntree();
        
        this.entierClient = (int) this.fluxEntree.readObject();
        
        System.out.println("\nRéception de l'entier du client : "
                           + this.entierClient);
        
        fermerFluxEntree();
        
        this.entierSecret
        = DiffieHellman.puissanceNR(this.entierClient, puissanceSecrete);
	}
	
	
	/**
	 * Envoie au client la clé gérénée par Vigenère.
	 * 
	 * @throws IOException Si l'envoi ou la réception d'un objet échoue.
	 * @throws ClassNotFoundException Si l'objet reçu n'est pas un String.
	 * @throws SocketTimeoutException Si la connexion n'est pas réalisée en 10s.
	 */
	private void envoyerCleVigenere()
	throws IOException, ClassNotFoundException, SocketTimeoutException {	
		String reponseClient;
		
		System.out.println("\nEntier secret du serveur : " + this.entierSecret);
		
		this.cleVigenere = Vigenere.chiffrerCle(this.entierSecret);
		
		creerFluxSortie();
		
		System.out.println("\nClé de vigenère créée :\n" + Vigenere.getCle());
		
		System.out.println("\nEnvoi de la clé de vigenère générée :\n"
		                   + this.cleVigenere);
		
		// Envoi au client de la clé de chiffrement
        this.fluxSortie.writeObject("CLE = " + this.cleVigenere);
        
        fermerFluxSortie();
        
        creerFluxEntree();
        
        /*
         * Lecture de la réponse du client
         */
		reponseClient = (String) this.fluxEntree.readObject();
		
		System.out.println(INDICATION_REPONSE + reponseClient + "\n");
		
		fermerFluxEntree();
		
		this.cleVigenere = Vigenere.getCle();
	}
	
	
	/**
	 * Sélectionne toutes les questions des catégories en paramètres et les
	 * envoie ensuite de manière sécurisée.
	 * 
	 * @param categories Les catégories à envoyer.
	 * @throws IOException Si l'envoi ou la réception d'un objet échoue.
	 * @throws ClassNotFoundException Si l'objet reçu n'est pas un String.
	 * @throws SocketTimeoutException Si la connexion n'est pas réalisée en 10s.
	 */
	public void envoyerCategories(ArrayList<Categorie> categories)
	throws IOException, ClassNotFoundException, SocketTimeoutException {
		
        ArrayList<Question> questions;
        
        questions = new ArrayList<Question>();
        
        for (Categorie categorieCourante: categories) {
        	questions.addAll(categorieCourante.getListeQuestions());
        }
        
        envoyerQuestions(questions);
	}
	
	
	/**
	 * Chiffre via la méthode
	 * {@link info2.sae301.quiz.modeles.cryptographie.Vigenere#chiffrer(String)}
	 * les données des questions en paramètre.
	 * Envoie ensuite via fluxSortie les données cryptées des questions.
	 * 
	 * @param questions Les questions à envoyer.
	 * @throws IOException Si l'envoi ou la réception d'un objet échoue.
	 * @throws ClassNotFoundException Si l'objet reçu n'est pas un String.
	 * @throws SocketTimeoutException Si la connexion n'est pas réalisée en 10s.
	 */
	public void envoyerQuestions(ArrayList<Question> questions)
	throws IOException, ClassNotFoundException, SocketTimeoutException {
		
        String donneesQuestion,
               toutesLesQuestionsCryptees;
        
        StringBuilder toutesLesQuestions;
        
        creerServeur();
        
        System.out.println(String.format(CONNEXION_OUVERTE, this.portServeur));
        
        envoyerRecevoirEntier();
        
        envoyerCleVigenere();
        
        creerFluxSortie();
        
        System.out.println("Envoi des données des questions :");
        
        toutesLesQuestions = new StringBuilder();

        for (Question questionCourante : questions) {
            donneesQuestion = questionCourante.donneesToString();
            
            System.out.println(donneesQuestion);

            toutesLesQuestions.append(donneesQuestion).append(DELIMITEUR);
        }
        
        System.out.println();
        
        toutesLesQuestionsCryptees
        = Vigenere.chiffrer(toutesLesQuestions.toString(), this.cleVigenere);
        
        this.fluxSortie.writeObject(toutesLesQuestionsCryptees);
        
        fermerFluxSortie();
        
        fermerSockets();
	}
	
	
	/**
	 * Fermeture des flux et sockets précédemment créées.
	 * 
	 * @throws IOException si la fermeture des flux et sockets échoue.
	 */
	public void fermerSockets() throws IOException {
        // Fermeture de la socket du client
		if (this.socketClient != null) {
			this.socketClient.close();			
		}
        
        // Fermeture de la socket du serveur
		if (this.socketServeur != null) {
			this.socketServeur.close();			
		}
	}
}