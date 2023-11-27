/*
 * Client.java								                        27 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.reseau;

import info2.sae301.quiz.cryptographie.Vigenere;
import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Question;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

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
	
	/** Port sur lequel le serveur est accessible. */
	private final static int PORT_SERVEUR = 55432;
	
	/** Timeout mettant fin à la tentative de connexion après 5s. */
	private final static int TIMEOUT_CONNEXION = 5000;
	
	private static final String CONNEXION_OUVERTE
	= "Socket créée à l'adresse %s et sur le port %d.\n";
	
	private static final String CREATION_FLUX_ENTREE
	= "Création d'un flux d'entrée (réception objets du serveur).\n";
	
	private static final String FERMETURE_FLUX_ENTREE
	= "Fermeture d'un flux d'entrée.\n";
	
	private static final String CREATION_FLUX_SORTIE
	= "Création d'un flux de sortie (envoi objets au serveur).\n";
	
	private static final String FERMETURE_FLUX_SORTIE
	= "Fermeture d'un flux de sortie.\n";
	
	private final static String INDICATION_REPONSE
	= "Réponse du serveur : ";
	
	private final static String MESSAGE_FERMETURE_SOCKET
	= "Fermeture de la socket.";
	
	/** Socket permettant la connexion au serveur. */
	private Socket socket;
	
	/** Flux par lequel les messages du serveur sont reçus. */
	private ObjectInputStream fluxEntree;
	
	/** Flux par lequel envoyer des messages au serveur. */
	private ObjectOutputStream fluxSortie;
	
	private String cleVigenere;
	
	/**
	 * Adresse IP du serveur.
	 * 127.0.0.1 signifie que le serveur est sûr le réseau courant.
	 */
	private String adresseServeur;
	
<<<<<<< Updated upstream
=======
	/** Port sur lequel le serveur est accessible. */
	private int portServeur;
	
	
	/**
	 * Initialisation d'un client connecté à un serveur dont l'adresse et le
	 * port sont par défaut sur le réseau local.
	 */
	public Client() {
		this.adresseServeur = "127.0.0.1";
		this.portServeur = 55432;
	}
	
	
	/**
<<<<<<< HEAD
	 * Initialisation d'un client connecté à un serveur dont l'adresse et le
	 * port sont passés en paramètres.
	 */
	public Client(String adresseServeur, int portServeur) {
		this.adresseServeur = adresseServeur;
		this.portServeur = portServeur;
=======
	 * Initialisation d'un client connecté à un serveur dont l'adresse est
	 * passée en paramètre et le port est par défaut.
	 */
	public Client(String adresseServeur) {
		this.adresseServeur = adresseServeur;
		this.portServeur = 55432;
>>>>>>> dae00ad1885417aeed7adb5fe398bc9e5feae890
	}
	
	
>>>>>>> Stashed changes
	/**
	 * Création d'une socket qui va se connecter à un serveur dont l'adresse IP
	 * et le port sont spécifiés dans les paramètres d'instanciation.
	 * 
	 * @throws IOException si la création de la socket échoue.
	 */
	
	
	/**
	 * Création d'un flux d'entrée pour recevoir les objets (String)
	 * envoyés par le serveur.
	 * @throws IOException si le flux ne peut être créé.
	 */
	private void creerFluxEntree() throws IOException {
		System.out.println(CREATION_FLUX_ENTREE);
		
        this.fluxEntree = new ObjectInputStream(this.socket.getInputStream());
	}
	
	
	/**
	 * Fermeture du flux d'entrée créé pour la réception
	 * des objets envoyés par le serveur.
	 * 
	 * @throws IOException si le flux ne peut être fermé.
	 */
	private void fermerFluxEntree() throws IOException {
		System.out.println(FERMETURE_FLUX_ENTREE);
		
		this.fluxEntree.close();
	}
	
	
	/**
	 * Création d'un flux de sortie pour envoyer des objets (String)
	 * au serveur.
	 * 
	 * @throws IOException si le flux ne peut être créé.
	 */
	private void creerFluxSortie() throws IOException {
		System.out.println(CREATION_FLUX_SORTIE);
		
        this.fluxSortie = new ObjectOutputStream(this.socket.getOutputStream());
	}
	
	
	/**
	 * Fermeture du flux de sortie créé pour envoyer des objets (String)
	 * au serveur.
	 * 
	 * @throws IOException si le flux ne peut être fermé.
	 */
	private void fermerFluxSortie() throws IOException {
		System.out.println(FERMETURE_FLUX_SORTIE);
		
		this.fluxSortie.close();
	}
	
	
	/**
	 * Envoie au serveur la clé gérénée par Vigenère.
	 * 
	 * @throws IOException si l'envoi échoue.
	 * @throws ClassNotFoundException si le cast de la réponse échoue.
	 */
	private void envoyerCleVigenere()
	throws IOException, ClassNotFoundException {	
		String reponseServeur;
		
		this.cleVigenere = Vigenere.genererCle();
		
		creerFluxSortie();
		
		System.out.println("Envoi de la clé de vigenère générée :\n"
		                   + this.cleVigenere + "\n");
		
		// Envoi au serveur de la clé de chiffrement
        this.fluxSortie.writeObject("CLE = " + this.cleVigenere);
        
        creerFluxEntree();
        
        /*
         * Lecture de la réponse du serveur
         */
		reponseServeur = (String) this.fluxEntree.readObject();
		
		System.out.println(INDICATION_REPONSE + reponseServeur + "\n");
	}
	
	
	/**
	 * Réception et déchiffrage des catégories cryptées envoyées par le serveur.
	 * 
	 * @throws IOException si la lecture renvoie une erreur.
	 * @throws ClassNotFoundException si le cast échoue.
	 * @return les noms des catégories reçues.
	 */
	private String[] recevoirCategories()
	throws IOException, ClassNotFoundException {		
		boolean envoiFini;
		
		String nomCategorieCrypte,
		       nomCategorieDecrypte;
		
		String[] nomsCategories = {""};
		
		envoiFini = false;
		
		System.out.println("Réception des noms des catégories :\n"
				           + "Nom crypté\tNom décrypté\n"
				           + "_____________________________");
        
        // Lecture du nom de catégorie crypté envoyé par le serveur
			}
		
		return nomsCategories;
	}
	
	
	/**
	 * Réception et déchiffrage des questions cryptées envoyées par le serveur.
	 * 
	 * @throws IOException si la lecture renvoie une erreur.
	 * @return les noms des catégories reçues.
	 * @throws ClassNotFoundException si le cast échoue.
	 */
	private String[] recevoirQuestions()
	throws IOException, ClassNotFoundException {		
		boolean envoiFini;
		
		String donneesCrypteesQuestion,
		       donneesDecrypteesQuestion;
		
		String[] donneesQuestions = {""};
		
		envoiFini = false;
		
		System.out.println("Réception des données des questions :\n"
				           + "Données cryptées\n-----\nDonnées décryptées\n"
				           + "_________________________________");
        
        // Lecture des données cryptées des questions envoyées par le serveur
		while (!envoiFini
			   && (donneesCrypteesQuestion = (String) this.fluxEntree.readObject())
			      != null) {
			} else {
				donneesDecrypteesQuestion
				= Vigenere.dechiffrer(donneesCrypteesQuestion, this.cleVigenere);
				
				System.out.println(donneesCrypteesQuestion + "\n-----\n"
				                   + donneesDecrypteesQuestion + "\n");
		        
				donneesQuestions[donneesQuestions.length - 1]
				= donneesDecrypteesQuestion;	
			}
		}
		return donneesQuestions;
	}
	
	
	/**
	 * Fermeture des flux et de la socket précédemment créée.
	 * 
	 * @throws IOException si la fermeture de la socket ou des flux échoue.
	 */
	private void fermerSockets() throws IOException {
		fermerFluxEntree();
		fermerFluxSortie();
		
		System.out.println(MESSAGE_FERMETURE_SOCKET);
        this.socket.close();
	}
	
	/**
	 * 
	 * @param adresseServeur
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void importerCategories(String adresseServeur)
	throws IOException, ClassNotFoundException {
		creerSocket(adresseServeur);
		envoyerCleVigenere();
		recevoirCategories();
		fermerSockets();
	}
	
	/**
	 * 
	 * @param adresseServeur
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void importerQuestions(String adresseServeur)
	throws IOException, ClassNotFoundException {
		String[] questionsFormatStr;
		
		creerSocket(adresseServeur);
		envoyerCleVigenere();
		
		questionsFormatStr = recevoirQuestions();
		
		for (String questionCourante : questionsFormatStr) {
			Import.creationQuestion(questionCourante);
		}
		
		fermerSockets();
	}
}
