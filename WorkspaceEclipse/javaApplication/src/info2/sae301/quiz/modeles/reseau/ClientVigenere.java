/*
 * ClientVigenere.java								               01 dec. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles.reseau;

import info2.sae301.quiz.modeles.cryptographie.Vigenere;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

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
public class ClientVigenere {
	
	/** Port sur lequel le serveur est accessible. */
	private final static int PORT_SERVEUR = 55432;
	
	/** Timeout mettant fin à la tentative de connexion après 5s. */
	private final static int TIMEOUT_CONNEXION = 5000;
	
	/**
	 * Délimiteur de séparation de toutes les questions dans l'objet envoyé.
	 * Le délimiteur contient volontairement le caractère ◄ non chiffrable
	 * (cf dictionnaire chiffrable) afin d'éviter que l'utilisateur crée une
	 * question contenant ce délimiteur dans un intitulé et qu'un problème
	 * d'import apparaisse.
	 */
	private final static String DELIMITEUR = "/delimiteur◄/";
	
	private final static String ERREUR_SERVEUR_INDISPONIBLE_MESSAGE
	= "Le serveur dont l'adresse IP a été renseignée ne répond pas.";
	
	private static final String CONNEXION_OUVERTE
	= "Socket créée à l'adresse %s et sur le port %d.\n";
	
	private static final String INDICATION_RECEPTION_CLIENT
	= "Le client a reçu : ";
	
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
        
        try {
        	this.socket.connect(adresse, TIMEOUT_CONNEXION);
        } catch (SocketTimeoutException e) {
        	throw new SocketTimeoutException(ERREUR_SERVEUR_INDISPONIBLE_MESSAGE);
        }
	}
	
	
	/**
	 * Création d'un flux d'entrée pour recevoir les objets (String)
	 * envoyés par le serveur.
	 * 
	 * @throws IOException si le flux ne peut être créé.
	 * @throws SocketTimeoutException si le timeout expire avant la connexion.
	 */
	private void creerFluxEntree() throws IOException, SocketTimeoutException {
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
	}
	
	
	/**
	 * Création d'un flux de sortie pour envoyer des objets (String)
	 * au serveur.
	 * 
	 * @throws IOException si le flux ne peut être créé.
	 * @throws SocketTimeoutException si le timeout expire avant la connexion.
	 */
	private void creerFluxSortie() throws IOException, SocketTimeoutException {
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
	
		System.out.println(INDICATION_CONFIRMATION_CLE);
		
		creerFluxSortie();
        
        this.fluxSortie.writeObject(INDICATION_RECEPTION_CLIENT + this.cleVigenere);
        
        Vigenere.setCle(this.cleVigenere);
		
        System.out.println(INDICATION_CLE_VIGENERE_CLAIRE
                           + this.cleVigenere + "\n");
	}
	
	
	/**
	 * Réception et déchiffrage des questions cryptées envoyées par le serveur.
	 * 
	 * @param adresseServeur L'adresse IP sur laquelle le serveur est démarré.
	 * @return La liste des questions reçues.
	 * @throws IOException Si la lecture renvoie une erreur.
	 * @throws ClassNotFoundException si le cast des données échoue.
	 * @throws SocketTimeoutException si le timeout expire avant la connexion.
	 */
	public String[] recevoirQuestions(String adresseServeur)
	throws IOException, ClassNotFoundException, SocketTimeoutException {
		String questionsCryptees,
		       questionsDecryptees;
	
		String[] donneesQuestions;
		
		this.adresseServeur = adresseServeur;
		
		creerSocket();
        
		System.out.println(String.format(CONNEXION_OUVERTE,
                           				 this.adresseServeur, PORT_SERVEUR));
		
		recevoirCleVigenere();
		
		System.out.println("Réception des données des questions :");
		
		// Lecture de l'unique objet envoyé par le serveur
		questionsCryptees = (String) this.fluxEntree.readObject();

	    // Décryptage des données cryptées reçues
	    questionsDecryptees
	    = Vigenere.dechiffrer(questionsCryptees, this.cleVigenere);
		
		System.out.println(questionsDecryptees + "\n");
		
		// Utilisation du délimiteur pour diviser les questions,
		// en excluant la dernière entrée qui est vide (cf envoi serveur)
	    donneesQuestions = questionsDecryptees.split(DELIMITEUR);
	    
	    this.fluxSortie.writeObject("Questions reçues");
	    
	    fermerFluxEntree();
	    
	    fermerFluxSortie();
	    
	    fermerSocket();
		
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
	
}