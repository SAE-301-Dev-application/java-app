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
	
	/** TODO JDoc */
	private final static String ADRESSE_SERVEUR = "127.0.0.1";
	
    public static void main(String[] args) {
        String serverAddress = ADRESSE_SERVEUR; // Change this to the server's address if not running locally
        int serverPort = 65432;
        
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            
            // Create input and output streams for the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            // Read input from the user and send it to the server
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while ((message = userInput.readLine()) != null) {
                out.println(message);
                String response = in.readLine();
                System.out.println("Server response: " + response);
            }
            
            // Close the client socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
