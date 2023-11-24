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
	
	/** TODO */
	private static ServerSocket socketServeur;
	
	/** TODO */
	private static Socket socketClient;
	
	/**
	 * Crée un serveur sur le réseau local.
	 * 
	 * @throws IOException
	 */
	private static void creerServeur() throws IOException {
		socketServeur = new ServerSocket(PORT);
		
        System.out.println(CONNEXION_OUVERTE);
	}
	
	
    public static void main(String[] args) {
        try {
            creerServeur();
            
            socketClient = socketServeur.accept();
            System.out.println("Client connecté : " + socketClient.getInetAddress().getHostAddress());
            
            // Create input and output streams for the client
            BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            PrintWriter out = new PrintWriter(socketClient.getOutputStream(), true);
            
            // Handle client messages
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Message reçu du client : " + message);
                
                // You can process the message here or send a response
                out.println("Le serveur a reçu : " + message);
            }
            
            // Close the client socket
            socketClient.close();
            socketServeur.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
