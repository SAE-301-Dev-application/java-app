/*
 * Serveur.java								                        27 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles.reseau;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Question;
import info2.sae301.quiz.modeles.cryptographie.Vigenere;

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
	
	private static final String CONNEXION_OUVERTE
	= "Le serveur est connecté sur le port %d.\nEn attente de la"
	  + " connexion d'un client.\n";
	
	private final static String INDICATION_REPONSE
	= "Réponse du client : ";
	
	/** Socket pour créer le serveur sur le réseau. */
	private ServerSocket socketServeur;
	
	/** Socket permettant la connexion au client. */
	private Socket socketClient;
	
	/** Message reçu du client. */
	private ObjectInputStream fluxEntree;
	
	/** Message envoyé au client. */
	private ObjectOutputStream fluxSortie;
	
	/** Clé de Vigenère permettant de crypter le CSV de données. */
	private String cleVigenere;
	
	/** Port utilisé par le serveur sur le réseau local. */
	private int portServeur;
	
	
	/**
	 * Initialisation d'un serveur dont le port vaut par défaut 55432.
	 */
	public Serveur() {
		this.portServeur = 55432;
	}
	
	
	/**
	 * Crée un serveur sur le réseau local.
	 * 
	 * @throws IOException si la socket ne peut être créée.
	 */
	public void creerServeur() throws IOException {
		this.socketServeur = new ServerSocket(this.portServeur);
	}
	
	
	/**
	 * Accepte la connexion d'un éventuel client.
	 * 
	 * @throws IOException si la connexion ne peut être établie.
	 */
	public void accepterConnexion() throws IOException {
        this.socketClient = this.socketServeur.accept();
        
        System.out.println("Client connecté : "
                           + this.socketClient.getInetAddress().getHostAddress()
                           + "\n");
	}
	
	
	/**
	 * Création d'un flux d'entrée pour recevoir les objets (String)
	 * envoyés par le client.
	 * 
	 * @throws IOException si le flux ne peut être créé.
	 */
	private void creerFluxEntree() throws IOException {
		accepterConnexion();
		this.fluxEntree = new ObjectInputStream(this.socketClient.getInputStream());
	}
	
	
	/**
	 * Fermeture du flux d'entrée créé pour la réception
	 * des objets envoyés par le client.
	 * 
	 * @throws IOException si le flux ne peut être fermé.
	 */
	private void fermerFluxEntree() throws IOException {
		this.fluxEntree.close();
	}
	
	
	/**
	 * Création d'un flux de sortie pour envoyer des objets (String)
	 * au client.
	 * 
	 * @throws IOException si le flux ne peut être créé.
	 */
	private void creerFluxSortie() throws IOException {
		accepterConnexion();
		this.fluxSortie = new ObjectOutputStream(this.socketClient.getOutputStream());
	}
	
	
	/**
	 * Fermeture du flux de sortie créé pour envoyer des objets (String)
	 * au client.
	 * 
	 * @throws IOException si le flux ne peut être fermé.
	 */
	private void fermerFluxSortie() throws IOException {
		this.fluxSortie.close();
	}
	
	
	/**
	 * Envoie au client la clé gérénée par Vigenère.
	 * 
	 * @throws IOException si l'envoi échoue.
	 * @throws ClassNotFoundException si le cast de la réponse échoue.
	 */
	private void envoyerCleVigenere()
	throws IOException, ClassNotFoundException {	
		String reponseClient;
		
		this.cleVigenere = Vigenere.genererCle();
		
		creerFluxSortie();
		
		System.out.println("Envoi de la clé de vigenère générée :\n"
		                   + this.cleVigenere);
		
		// Envoi au client de la clé de chiffrement
        this.fluxSortie.writeObject("CLE = " + this.cleVigenere);
        
        fermerFluxSortie();
        
        creerFluxEntree();
        
        /*
         * Lecture de la réponse du client
         */
		reponseClient = (String) this.fluxEntree.readObject();
		
		System.out.println(INDICATION_REPONSE + reponseClient + "\n");
		
		fermerFluxEntree();
	}
	
	
	/**
	 * Chiffre via la méthode
	 * {@link info2.sae301.quiz.modeles.cryptographie.Vigenere#chiffrer(String)}
	 * les noms des catégories en paramètre.
	 * Envoie ensuite via fluxSortie chaque nom de catégorie crypté.
	 * 
	 * @param categories Les catégories à envoyer.
	 * @throws IOException si l'envoi échoue.
	 * @throws ClassNotFoundException 
	 */
	public void envoyerCategories(ArrayList<Categorie> categories)
	throws IOException, ClassNotFoundException {
		
        String nomCategorie,
               nomCategorieCrypte;
        
        creerServeur();
        
        System.out.println(String.format(CONNEXION_OUVERTE, this.portServeur));
        
        envoyerCleVigenere();
        
        System.out.println("Envoi de noms de catégories :\n"
        		           + "Nom initial\tNom crypté\n"
        		           + "_____________________________");
        
        creerFluxSortie();
        
        for (Categorie categorieCourante: categories) {
        	nomCategorie = categorieCourante.getIntitule();
 
            nomCategorieCrypte = Vigenere.chiffrer(nomCategorie, this.cleVigenere);
            
            System.out.println(nomCategorie + "\t" + nomCategorieCrypte);
			
		    this.fluxSortie.writeObject(nomCategorieCrypte);
        }
        
        System.out.println();
        this.fluxSortie.writeObject("finCategories");
        
        fermerFluxSortie();
        
        fermerSockets();
	}
	
	
	/**
	 * Chiffre via la méthode
	 * {@link info2.sae301.quiz.modeles.cryptographie.Vigenere#chiffrer(String)}
	 * les données des questions en paramètre.
	 * Envoie ensuite via fluxSortie les données cryptées des questions.
	 * 
	 * @param questions Les questions à envoyer.
	 * @throws IOException si l'envoi échoue.
	 * @throws ClassNotFoundException 
	 */
	public void envoyerQuestions(ArrayList<Question> questions)
	throws IOException, ClassNotFoundException {
		
        String donneesQuestion,
               donneesCrypteesQuestion;
        
        creerServeur();
        
        System.out.println(String.format(CONNEXION_OUVERTE, this.portServeur));
        
        envoyerCleVigenere();
        
        creerFluxSortie();
        
        System.out.println("Envoi de données de questions :\n"
        		           + "Données initiales\n-----\nDonnées cryptées\n"
        		           + "_____________________");
        
        
        for (Question questionCourante: questions) {
        	donneesQuestion = questionCourante.donneesToString();
 
        	donneesCrypteesQuestion = Vigenere.chiffrer(donneesQuestion,
        			                                    this.cleVigenere);
            
            System.out.println(donneesQuestion + "\n-----\n"
                               + donneesCrypteesQuestion + "\n");
			
		    this.fluxSortie.writeObject(donneesCrypteesQuestion);
        }
        
        this.fluxSortie.writeObject("finQuestions");
        
        fermerFluxSortie();
        
        fermerSockets();
	}
	
	
	/**
	 * Fermeture des flux et sockets précédemment créées.
	 * 
	 * @throws IOException si la fermeture des flux et sockets échoue.
	 */
	public void fermerSockets() throws IOException {
        // Fermeture de la socket du client
        this.socketClient.close();
        
        // Fermeture de la socket du serveur
        this.socketServeur.close();
	}
}