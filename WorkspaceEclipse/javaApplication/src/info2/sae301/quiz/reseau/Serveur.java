/*
 * Serveur.java								                        24 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.reseau;

import info2.sae301.quiz.cryptographie.Vigenere;
import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Question;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
	
	private static final String INDICATION_CLE_VIGENERE
	= "\nClé de vigenère reçue :\n";
	
	private static final String INDICATION_RECEPTION_SERVEUR
	= "Le serveur a reçu : ";
	
	private final static String MESSAGE_FERMETURE
	= "\nLes sockets sont en cours de fermeture.";
	
	/** Socket pour créer le serveur sur le réseau. */
	private static ServerSocket socketServeur;
	
	/** Socket permettant la connexion au client. */
	private static Socket socketClient;
	
	/** Message reçu du client. */
	private static ObjectInputStream entreeSocket;
	
	/** Message envoyé au client. */
	private static ObjectOutputStream sortieSocket;
	
	/** Clé de Vigenère permettant de crypter le CSV de données. */
	private static String cleVigenere;
	
	
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
        = new ObjectInputStream(socketClient.getInputStream());
        
        // Création d'un flux de sortie pour le serveur
        sortieSocket = new ObjectOutputStream(socketClient.getOutputStream());
	}

	
	/**
	 * Lecture de la clé de vigenère envoyée par le client
	 * et affichage sur console texte.
	 * 
	 * @throws IOException si la lecture renvoie une erreur.
	 * @throws ClassNotFoundException 
	 */
	private static void receptionCleVigenere()
	throws IOException, ClassNotFoundException {	
		cleVigenere = "";
		
		cleVigenere = (String) entreeSocket.readObject();
		cleVigenere = cleVigenere.substring(6);
				
        System.out.println(INDICATION_CLE_VIGENERE + cleVigenere);
        
        sortieSocket.writeObject(INDICATION_RECEPTION_SERVEUR + cleVigenere);
	}
	
	
	/**
	 * Chiffre via la méthode
	 * {@link info2.sae301.quiz.cryptographie.Vigenere#chiffrer(String)}
	 * les noms des catégories en paramètre.
	 * Envoie ensuite via sortieSocket chaque nom de catégorie crypté.
	 * 
	 * @param categories Les catégories à envoyer.
	 * @throws IOException 
	 */
	private static void envoyerCategories(ArrayList<Categorie> categories)
	throws IOException {
		
        String nomCategorie,
               nomCategorieCrypte;
        
        System.out.println("\nEnvoi de noms de catégories :\n"
        		           + "Nom initial\tNom crypté\n"
        		           + "_____________________________");
        
        for (Categorie categorieCourante: categories) {
        	nomCategorie = categorieCourante.getIntitule();
 
            nomCategorieCrypte = Vigenere.chiffrer(nomCategorie, cleVigenere);
            
            System.out.println(nomCategorie + "\t" + nomCategorieCrypte);
			
		    sortieSocket.writeObject(nomCategorieCrypte);
        }
        
        sortieSocket.writeObject("finCategories");
	}
	
	
	/**
	 * Chiffre via la méthode
	 * {@link info2.sae301.quiz.cryptographie.Vigenere#chiffrer(String)}
	 * les données des questions en paramètre.
	 * Envoie ensuite via sortieSocket les données cryptées des questions.
	 * 
	 * @param questions Les questions à envoyer.
	 * @throws IOException 
	 */
	private static void envoyerQuestions(ArrayList<Question> questions)
	throws IOException {
		
        String donneesQuestion,
               donneesCrypteesQuestion;
        
        System.out.println("\nEnvoi de données de questions :\n"
        		           + "Données initiales\n-----\nDonnées cryptées\n"
        		           + "_____________________");
        
        for (Question questionCourante: questions) {
        	donneesQuestion = questionCourante.donneesToString();
 
        	donneesCrypteesQuestion = Vigenere.chiffrer(donneesQuestion,
        			                                    cleVigenere);
            
            System.out.println(donneesQuestion + "\n-----\n"
                               + donneesCrypteesQuestion + "\n");
			
		    sortieSocket.writeObject(donneesCrypteesQuestion);
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
    	
    	ArrayList<Categorie> listeCategoriesTemp;
    	
    	ArrayList<Question> listeQuestionsTemp;
    	
    	listeCategoriesTemp = new ArrayList<Categorie>();
    	
    	listeQuestionsTemp = new ArrayList<Question>();
    	
    	listeCategoriesTemp.add(new Categorie("Test"));
    	listeCategoriesTemp.add(new Categorie("Test2"));
    	listeCategoriesTemp.add(new Categorie("Test3"));
    	listeCategoriesTemp.add(new Categorie("Test4"));
    	listeCategoriesTemp.add(new Categorie("Test5"));
    	
    	for (int i = 1; i <= 4; i++) {
    		listeQuestionsTemp.add(new Question("intituléQ" + i,
    				                            "réponse juste Q" + i,
    				                            new String[] {
    				                            	"Réponse fausse 1 Q" + i,
    				                            	"Réponse fausse 2 Q" + i,
    				                            	"Réponse fausse 3 Q" + i,
    				                            	"Réponse fausse 4 Q" + i,
    				                            },
    				                            2,
    				                            "feedback Q" + i,
    				                            listeCategoriesTemp.get(i)));    		
    	}
    	
        try {
            creerServeur();
            
            accepterConnexion();
            
            creerFluxEntreeSortie();
            
            receptionCleVigenere();
            
            //envoyerCategories(listeCategoriesTemp);
            
            //envoyerQuestions(listeQuestionsTemp);
            
            fermerSockets();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
