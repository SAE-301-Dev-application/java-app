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
	
	/** Port utilisé par le serveur */
	public static final int PORT = 65432;
	
    public static void main(String[] args) {
        int port = PORT;
        
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Le serveur tourne sur le port " + port);
            boolean aTester = true;
            while (aTester == true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté : " + clientSocket.getInetAddress().getHostAddress());
                
                // Create input and output streams for the client
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                // Handle client messages
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Message reçu du client : " + message);
                    
                    // You can process the message here or send a response
                    out.println("Le serveur a reçu : " + message);
                }
                
                // Close the client socket
                aTester = false;
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
