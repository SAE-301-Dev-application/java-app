/*
 * Serveur.java								                        24 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
	
	/** Port utilisé par le serveur sur le réseau local. */
	private static final int PORT = 65432;
	
	private static final String CONNEXION_OUVERTE
	= "Le serveur est connecté sur le port " + PORT;
	
	private static final String INDICATION_MESSAGE_CLIENT
	= "\nMessage envoyé par le client :\n";
	
	private static final String INDICATION_RECEPTION_SERVEUR
	= "Le serveur a reçu : ";
	
	private final static String MESSAGE_FERMETURE
	= "\nLes sockets sont en cours de fermeture.";
	
	/** Socket pour créer le serveur sur le réseau. */
	private static ServerSocket socketServeur;
	
	/** Socket permettant la connexion au client. */
	private static Socket socketClient;
	
	/** Message reçu du client. */
	private static BufferedReader entreeSocket;
	
	/** Message envoyé au client. */
	private static PrintWriter sortieSocket;
	
	
	/**
	 * Crée un serveur sur le réseau local.
	 * 
	 * @throws IOException si le socket ne peut être créé.
	 */
	private static void creerServeur() throws IOException {
		socketServeur = new ServerSocket(PORT);
		
        System.out.println(CONNEXION_OUVERTE);
	}
	
	
	/**
	 * Accepte la connexion d'un éventuel client.
	 * 
	 * @throws IOException si la connexion ne peut être établie.
	 */
	private static void accepterConnexion() throws IOException {
        socketClient = socketServeur.accept();
        
        System.out.println("Client connecté : " + socketClient.getInetAddress()
                                                              .getHostAddress());
	}
	
	
	/**
	 * Création d'un flux d'entrée et d'un flux de sortie pour le client.
	 * 
	 * @throws IOException si les flux ne peuvent être créés.
	 */
	private static void creerFluxEntreeSortie() throws IOException {
        // Création d'un flux d'entrée pour le serveur
        entreeSocket
        = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        
        // Création d'un flux de sortie pour le serveur
        sortieSocket = new PrintWriter(socketClient.getOutputStream(), true);
	}
	
	
	/**
	 * Lecture du message envoyé par le client et affichage sur console texte.
	 * 
	 * @throws IOException si la lecture renvoie une erreur.
	 */
	private static void lectureEnvoiMessage() throws IOException {		
		String messageClient;
		
		boolean socketsOuvertes;
		
		socketsOuvertes = true;
        
        while (socketsOuvertes
        	   && (messageClient = entreeSocket.readLine()) != null) {
            System.out.println(INDICATION_MESSAGE_CLIENT + messageClient);
            
            sortieSocket.println(INDICATION_RECEPTION_SERVEUR + messageClient);
            
            if (messageClient.equals("fin")) {
            	socketsOuvertes = false;
            }
        }
	}
	
	
	/**
	 * Fermeture des sockets précédemment créées.
	 * 
	 * @throws IOException si la fermeture des sockets échoue.
	 */
	private static void fermerSockets() throws IOException {
		System.out.println(MESSAGE_FERMETURE);
		
		entreeSocket.close();
		sortieSocket.close();
		
        // Fermeture de la socket du client
        socketClient.close();
        
        // Fermeture de la socket du serveur
        socketServeur.close();
	}
	
	
	/**
	 * Etablissement d'une connexion entre le serveur et le client.
	 * 
	 * @param args inutilisé.
	 */
    public static void main(String[] args) {
        try {
            creerServeur();
            
            accepterConnexion();
            
            creerFluxEntreeSortie();
            
            lectureEnvoiMessage();
            
            fermerSockets();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
