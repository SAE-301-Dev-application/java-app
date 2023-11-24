/*
 * Client.java								                        24 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.reseau;

import info2.sae301.quiz.cryptographie.Vigenere;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

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
	
	/**
	 * Adresse IP du serveur.
	 * 127.0.0.1 signifie que le serveur est sûr le réseau courant.
	 */
	private final static String ADRESSE_SERVEUR = "127.0.0.1";
	
	private final static int TIMEOUT_CONNEXION = 5000;
	
	private final static String INDICATION_REPONSE
	= "\nRéponse du serveur : ";
	
	private final static String MESSAGE_FERMETURE
	= "\nLes sockets sont en cours de fermeture.";
	
	/** Port sur lequel le serveur est accessible. */
	private final static int PORT_SERVEUR = 65432;
	
	/** Socket permettant la connexion au serveur. */
	private static Socket socket;
	
	/** Message reçu du serveur */
	private static ObjectInputStream entreeSocket;
	
	/** Message envoyé au serveur */
	private static ObjectOutputStream sortieSocket;
	
	private static String cleVigenere;
	
	
	/**
	 * Création d'une socket qui va se connecter à un serveur dont l'adresse IP
	 * et le port sont spécifiés dans les paramètres d'instanciation.
	 * 
	 * @throws IOException si la création de la socket échoue.
	 */
	private static void creerSocket() throws IOException {
        socket = new Socket();
        
        InetSocketAddress adresseServeur = new InetSocketAddress(ADRESSE_SERVEUR, PORT_SERVEUR);
        socket.connect(adresseServeur, TIMEOUT_CONNEXION);
        
        System.out.println("Socket créée à l'adresse " + ADRESSE_SERVEUR);
	}
	
	
	/**
	 * Création d'un flux d'entrée et d'un flux de sortie pour le serveur.
	 * 
	 * @throws IOException si les flux ne peuvent être créés.
	 */
	private static void creerFluxEntreeSortie() throws IOException {
		System.out.println("Création du flux d'entrées et sorties");
		
		System.out.println(socket.getInputStream());
		
        // Création d'un flux d'entrée pour le serveur
        try {
			entreeSocket
			= new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println(entreeSocket);
        
        // Création d'un flux de sortie pour le serveur
        sortieSocket = new ObjectOutputStream(socket.getOutputStream());
        
        System.out.println("teagz");
	}
	
	
	/**
	 * Envoie au serveur la clé gérénée par Vigenere.
	 * @throws ClassNotFoundException 
	 */
	private static void envoyerCleVigenere()
	throws IOException, ClassNotFoundException {	
		String reponseServeur;
		
		System.out.println("Envoi de la clé de vigenère");
		
		cleVigenere = Vigenere.genererCle();
		
		System.out.println("Envoi de la clé générée : " + cleVigenere);
		
		// Envoi au serveur de la clé de chiffrement
        sortieSocket.writeObject("CLE = " + cleVigenere);
        
        /*
         * Lecture de la réponse du serveur
         */
		reponseServeur = (String) entreeSocket.readObject();
		
        System.out.println(INDICATION_REPONSE + reponseServeur);
	}
	
	
	/**
	 * Réception et déchiffrage des catégories cryptées envoyées par le serveur.
	 * 
	 * @throws IOException si la lecture renvoie une erreur.
	 * @return les noms des catégories reçus.
	 * @throws ClassNotFoundException 
	 */
	private static String[] recevoirCategories()
	throws IOException, ClassNotFoundException {		
		boolean envoiFini;
		
		String nomCategorieCrypte,
		       nomCategorieDecrypte;
		
		String[] nomsCategories = {""};
		
		envoiFini = false;
		
		System.out.println("\nRéception des noms des catégories :\n"
				           + "Nom crypté\tNom décrypté\n"
				           + "_____________________________");
        
        // Lecture du nom de catégorie crypté envoyé par le serveur
		while (!envoiFini
			   && (nomCategorieCrypte = (String) entreeSocket.readObject())
			      != null) {
			
			if (nomCategorieCrypte.equals("finCategories")) {
				envoiFini = true;
			} else {
				// Décryptage du nom de catégorie crypté reçu
				nomCategorieDecrypte = Vigenere.dechiffrer(nomCategorieCrypte,
						                                   cleVigenere);
				
				System.out.println(nomCategorieCrypte + "\t" + nomCategorieDecrypte);
		        
		        nomsCategories[nomsCategories.length - 1] = nomCategorieDecrypte;	
			}
		}
		return nomsCategories;
	}
	
	
	/**
	 * Réception et déchiffrage des questions cryptées envoyées par le serveur.
	 * 
	 * @throws IOException si la lecture renvoie une erreur.
	 * @return les noms des catégories reçus.
	 * @throws ClassNotFoundException 
	 */
	private static String[] recevoirQuestions()
	throws IOException, ClassNotFoundException {		
		boolean envoiFini;
		
		String donneesCrypteesQuestion,
		       donneesDecrypteesQuestion;
		
		String[] donneesQuestions = {""};
		
		envoiFini = false;
		
		System.out.println("\nRéception des données des questions :\n"
				           + "Données cryptées\tDonnées décryptées\n"
				           + "_________________________________");
        
        // Lecture des données cryptées des questions envoyées par le serveur
		while (!envoiFini
			   && (donneesCrypteesQuestion = (String) entreeSocket.readObject())
			      != null) {
			
			if (donneesCrypteesQuestion.equals("finQuestions")) {
				envoiFini = true;
			} else {
				// Décryptage des données cryptées reçues
				donneesDecrypteesQuestion
				= Vigenere.dechiffrer(donneesCrypteesQuestion, cleVigenere);
				
				System.out.println(donneesCrypteesQuestion
						           + "\t" + donneesDecrypteesQuestion);
		        
				donneesQuestions[donneesQuestions.length - 1]
				= donneesDecrypteesQuestion;	
			}
		}
		return donneesQuestions;
	}
	
	
	/**
	 * Fermeture de la socket précédemment créée.
	 * 
	 * @throws IOException si la fermeture de la socket échoue.
	 */
	private static void fermerSockets() throws IOException {
		System.out.println(MESSAGE_FERMETURE);
		
		entreeSocket.close();
		sortieSocket.close();
        socket.close();
	}
	
	/**
	 * Etablissement d'une connexion entre le client et le serveur.
	 * 
	 * @param args inutilisé.
	 */
    public static void main(String[] args) {      
        try {
        	creerSocket();
        	
        	creerFluxEntreeSortie();
        	
        	envoyerCleVigenere();
        	
        	//recevoirCategories();
        	
        	//recevoirQuestions();
        	
        	fermerSockets();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
