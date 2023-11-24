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
	
	/** Port sur lequel le serveur est accessible. */
	private final static int PORT_SERVEUR = 65432;
	
	/** Socket permettant la connexion au serveur. */
	private static Socket socket;
	
	/** TODO */
	private static BufferedReader entree;
	
	/** TODO */
	private static PrintWriter sortie;
	
	
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
        entree
        = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        // Création d'un flux de sortie pour le serveur
        sortie = new PrintWriter(socket.getOutputStream(), true);
	}
	
	
    public static void main(String[] args) {      
        try {
        	creerSocket();
        	
        	creerFluxEntreeSortie();
            
            // Read input from the user and send it to the server
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while ((message = userInput.readLine()) != null) {
                sortie.println(message);
                String response = entree.readLine();
                System.out.println("Server response: " + response);
            }
            
            // Close the client socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
