/*
 * Client.java								                        24 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.reseau;

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
		try {
            socket = new Socket(ADRESSE_SERVEUR, PORT_SERVEUR);
		} catch (IOException e) {
			throw new IOException(e);
		}
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
	 * Lecture de l'entrée envoyé dans la console texte par l'utilisateur
	 * et envoi au serveur du message.
	 */
	private static void lectureEnvoiMessage() throws IOException {		
		String messageAEnvoyer,
		       reponseServeur;
		
		System.out.println(INSTRUCTION_CLIENT);
        entreeUtilisateur = new BufferedReader(new InputStreamReader(System.in));
        
        while ((messageAEnvoyer = entreeUtilisateur.readLine()) != null) {
        	// Envoi au serveur du message
            sortieSocket.println(messageAEnvoyer);
            
            // Lecture de la réponse du serveur
            reponseServeur = entreeSocket.readLine();
            System.out.println("Réponse du serveur : " + reponseServeur);
        }
	}
	
	
	/**
	 * Fermeture de la socket précédemment créée.
	 * 
	 * @throws IOException si la création de la socket échoue.
	 */
	private static void fermerSocket() throws IOException {
		try {
            socket.close();
		} catch (IOException e) {
			throw new IOException(e);
		}
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
            
            lectureEnvoiMessage();
            
            fermerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
