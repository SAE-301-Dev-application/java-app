/*
 * Client.java								                        24 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.reseau;

import info2.sae301.quiz.cryptographie.Vigenere;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
	
	/** Message envoyé dans la console du client avant qu'il envoie un message. */
	private final static String INSTRUCTION_CLIENT
	= "Rédigez ci-dessous un message à envoyer au serveur : ";
	
	private final static String INDICATION_REPONSE
	= "\nRéponse du serveur : ";
	
	private final static String MESSAGE_FERMETURE
	= "\nLes sockets sont en cours de fermeture.";
	
	/** Port sur lequel le serveur est accessible. */
	private final static int PORT_SERVEUR = 65432;
	
	/** Socket permettant la connexion au serveur. */
	private static Socket socket;
	
	/** Message entré par l'utilisateur */
	private static BufferedReader entreeUtilisateur;
	
	/** Message reçu du serveur */
	private static BufferedReader entreeSocket;
	
	/** Message envoyé au serveur */
	private static PrintWriter sortieSocket;
	
	
	/**
	 * Création d'une socket qui va se connecter à un serveur dont l'adresse IP
	 * et le port sont spécifiés dans les paramètres d'instanciation.
	 * 
	 * @throws IOException si la création de la socket échoue.
	 */
	private static void creerSocket() throws IOException {
        socket = new Socket(ADRESSE_SERVEUR, PORT_SERVEUR);
	}
	
	
	/**
	 * Création d'un flux d'entrée et d'un flux de sortie pour le serveur.
	 * 
	 * @throws IOException si les flux ne peuvent être créés.
	 */
	private static void creerFluxEntreeSortie() throws IOException {
        // Création d'un flux d'entrée pour le serveur
        entreeSocket
        = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        // Création d'un flux de sortie pour le serveur
        sortieSocket = new PrintWriter(socket.getOutputStream(), true);
	}
	
	
	/**
	 * Envoie au serveur la clé gérénée par Vigenere.
	 */
	private static void envoyerCleVigenere() throws IOException {
		String cleGeneree = Vigenere.genererCle();
		String reponseServeur;
		
		System.out.println("Envoi de la clé générée : " + cleGeneree);
		
		// Envoi au serveur de la clé de chiffrement
        sortieSocket.println("CLE = " + cleGeneree);
        
        // Lecture de la réponse du serveur
        reponseServeur = entreeSocket.readLine();
        System.out.println(INDICATION_REPONSE + reponseServeur);
        
        fermerSockets();
	}
	
	
	/**
	 * Lecture de l'entrée envoyé dans la console texte par l'utilisateur
	 * et envoi au serveur du message.
	 * 
	 * @throws IOException si la lecture renvoie une erreur.
	 */
	private static void lectureEnvoiMessage() throws IOException {		
		String messageAEnvoyer,
		       reponseServeur;
		
		boolean socketOuverte;
		
		System.out.println(INSTRUCTION_CLIENT);
        entreeUtilisateur = new BufferedReader(new InputStreamReader(System.in));
        
        socketOuverte = true;
        
        while (socketOuverte
        	   && (messageAEnvoyer = entreeUtilisateur.readLine()) != null) {
        	// Envoi au serveur du message
            sortieSocket.println(messageAEnvoyer);
            
            // Lecture de la réponse du serveur
            reponseServeur = entreeSocket.readLine();
            System.out.println(INDICATION_REPONSE + reponseServeur);
            
            if (messageAEnvoyer.equals("fin")) {
            	socketOuverte = false;
            }
        }
	}
	
	
	/**
	 * Fermeture de la socket précédemment créée.
	 * 
	 * @throws IOException si la fermeture de la socket échoue.
	 */
	private static void fermerSockets() throws IOException {
		System.out.println(MESSAGE_FERMETURE);
		
		if (entreeUtilisateur != null) {
			entreeUtilisateur.close();
		}
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
            
            // lectureEnvoiMessage();
        	
        	envoyerCleVigenere();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
