/*
 * ServeurVigenere.java								     1 dec. 2023
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

import info2.sae301.quiz.exceptions.ClientDejaConnecteException;
import info2.sae301.quiz.modeles.Question;
import info2.sae301.quiz.modeles.cryptographie.Vigenere;

/**
 * Serveur permettant d'exporter les données des questions et 
 * catégories vers un client connecté.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class ServeurVigenere {
	
	/** Timeout mettant fin à la tentative de connexion après 10s. */
	private final static int TIMEOUT_CONNEXION = 10000;
	
	private static final String CONNEXION_OUVERTE
	= "Le serveur est connecté sur le port %d.\nEn attente de la"
	  + " connexion d'un client.\n";
	
	private final static String INDICATION_REPONSE
	= "\nRéponse du client : ";
	
	/**
	 * Délimiteur de séparation de toutes les questions dans l'objet 
	 * envoyé.
	 * Le délimiteur contient volontairement le caractère ◄ non 
	 * chiffrable (cf dictionnaire chiffrable) afin d'éviter que 
	 * l'utilisateur crée une question contenant ce délimiteur dans 
	 * un intitulé et qu'un problème d'import apparaisse.
	 */
	private final static String DELIMITEUR = "/delimiteur◄/";
	
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

	
	/**
	 * Initialisation d'un serveur dont le port vaut par défaut 55432.
	 */
	public ServeurVigenere() {
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
	 * @throws SocketTimeoutException Si la connexion n'est pas 
	 * réalisée en 10s.
	 * @throws ClientDejaConnecteException Si un client est déjà 
	 * connecté.
	 */
	public void accepterConnexion()
	throws IOException, SocketTimeoutException, ClientDejaConnecteException {
		if (this.socketClient != null && !this.socketClient.isClosed()) {
			fermerSockets();
			throw new ClientDejaConnecteException();
		}
		
		try {
			this.socketClient = this.socketServeur.accept();
			System.out.println("serv connexion ok");
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
	 * @throws SocketTimeoutException Si la connexion n'est pas 
	 * réalisée en 10s.
	 * @throws ClientDejaConnecteException Si un client est déjà 
	 * connecté.
	 */
	private void creerFluxEntree()
	throws IOException, SocketTimeoutException, ClientDejaConnecteException {
		System.out.println("serv créer flux entrée");
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
	 * @throws SocketTimeoutException Si la connexion n'est pas 
	 * réalisée en 10s.
	 * @throws ClientDejaConnecteException Si un client est déjà 
	 * connecté.
	 */
	private void creerFluxSortie()
	throws IOException, SocketTimeoutException, ClientDejaConnecteException {
		this.fluxSortie = new ObjectOutputStream(this.socketClient.getOutputStream());
	}
	
	
	/**
	 * Fermeture du flux de sortie créé pour envoyer des 
	 * objets (String) au client.
	 * 
	 * @throws IOException si le flux ne peut être fermé.
	 */
	private void fermerFluxSortie() throws IOException {
		this.fluxSortie.close();
	}
	
	
	/**
	 * Envoie au client la clé générée par Vigenère.
	 * 
	 * @throws IOException Si l'envoi ou la réception d'un objet 
	 * échoue.
	 * @throws ClassNotFoundException Si l'objet reçu n'est pas 
	 * un String.
	 * @throws SocketTimeoutException Si la connexion n'est pas 
	 * réalisée en 10s.
	 * @throws ClientDejaConnecteException Si un client est déjà 
	 * connecté.
	 */
	private void envoyerCleVigenere()
	throws IOException, ClassNotFoundException, SocketTimeoutException,
	       ClientDejaConnecteException {	
		String reponseClient;
			
		this.cleVigenere = Vigenere.getCle();
		
		System.out.println("Envoi de la clé de vigenère générée :\n"
		                   + this.cleVigenere);
		
		creerFluxSortie();
		
		// Envoi au client de la clé de chiffrement
        this.fluxSortie.writeObject("CLE = " + this.cleVigenere);
        
        creerFluxEntree();
        
        /*
         * Lecture de la réponse du client
         */
        creerFluxEntree();
        
		reponseClient = (String) this.fluxEntree.readObject();
		
		System.out.println(INDICATION_REPONSE + reponseClient + "\n");
	}
	
	
	/**
	 * Chiffre via la méthode
	 * {@link info2.sae301.quiz.modeles.cryptographie.Vigenere#chiffrer(String)}
	 * les données des questions en paramètre.
	 * Envoie ensuite via fluxSortie les données cryptées 
	 * des questions.
	 * 
	 * @param questions Les questions à envoyer.
	 * @throws IOException Si l'envoi ou la réception d'un objet 
	 * échoue.
	 * @throws ClassNotFoundException Si l'objet reçu n'est pas 
	 * un String.
	 * @throws SocketTimeoutException Si la connexion n'est pas 
	 * réalisée en 10s.
	 * @throws ClientDejaConnecteException Si un client est déjà 
	 * connecté.
	 */
	public void envoyerQuestions(ArrayList<Question> questions)
	throws IOException, ClassNotFoundException, SocketTimeoutException,
	       ClientDejaConnecteException {
		
        String donneesQuestion,
               toutesLesQuestionsCryptees;
        
        StringBuilder toutesLesQuestions;
        
    	creerServeur();
    	
    	accepterConnexion();
    	
    	System.out.println(String.format(CONNEXION_OUVERTE, this.portServeur));
        
        envoyerCleVigenere();
        
        System.out.println("Envoi des données des questions :");
        
        toutesLesQuestions = new StringBuilder();

        for (Question questionCourante : questions) {
            donneesQuestion = questionCourante.donneesToString();
            
            System.out.println(donneesQuestion);

            toutesLesQuestions.append(donneesQuestion).append(DELIMITEUR);
        }
        
        // Supprimer le délimiteur à la fin de toutes les questions
        toutesLesQuestions
        .setLength(toutesLesQuestions.length() - DELIMITEUR.length());
        
        System.out.println();
        
        toutesLesQuestionsCryptees
        = Vigenere.chiffrer(toutesLesQuestions.toString(), this.cleVigenere);
        
        this.fluxSortie.writeObject(toutesLesQuestionsCryptees);
        
        // Attente réception questions
        this.fluxEntree.readObject();
        
        fermerFluxEntree();
        
        fermerFluxSortie();
        
        fermerSockets();
	}
	
	
	/**
	 * Fermeture des flux et sockets précédemment créées.
	 * 
	 * @throws IOException si la fermeture des flux et sockets échoue
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
