/*
 * ExportControleur.java					                        28 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.ParametresPartie;
import info2.sae301.quiz.modeles.reseau.Serveur;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


/**
 * Contrôleur de la vue d'export de l'application.
 * - Gestion de la saisie de l'adresse IP du destinataire.
 * - Recherche et affichage de l'IP privée sur le réseau
 *   de la machine courante.
 * - Envoi des données à exporter au destinataire valide
 *   indiqué.
 *   
 * @author Jonathan GUIL
 * @author Simon GUIRAUD
 * @author Florian FABRE
 * @author Loïc FAUGIERES
 * @author Samuel LACAM
 */
public class ExportControleur {
	
	/** 
	 * Titre du message d'erreur de recherche de 
	 * l'adresse IP privée. 
	 */
	private static final String ERREUR_IP_PRIVEE_TITRE
	= "Erreur dans la recherche de l'IP locale";
	
	/**
	 * Message d'erreur de recherche de l'adresse
	 * IP privée.
	 */
	private static final String ERREUR_IP_PRIVEE_MESSAGE
	= """
	  Il est impossible au programme de trouver votre adresse IP sur le réseau.
	  Veuillez vérifier que vous êtes connecté à un réseau.
	  """;
	
	/** Affichage de l'adresse IP privée. */
	private static final String MODELE_LABEL_IP_PRIVEE
	= "Mon adresse IP : %s";
	
	
	/** Choix : sélectionner des catégories. */
	@FXML
	private CheckBox choixCategories;
	
	
	/** Choix : sélectionner des questions. */
	@FXML
	private CheckBox choixQuestions;
	
	/** Grille d'affichage des catégories/questions. */
	@FXML 
	private GridPane grilleSelection;
	
	/**
	 * Recherche et retourne l'adresse IP de la machine sur 
	 * le réseau.
	 * 
	 * @return Adresse IP de la machine sur le réseau (IP privée)
	 */
	private static String ipPrivee() {
		String ip;
		ip = null;
		
		try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();

                // Filtrer les interfaces loopback et les interfaces désactivées
                if (iface.isLoopback() || !iface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                
                while (addresses.hasMoreElements() && ip == null) {
                    InetAddress addr;
                    
                    String ipTrouvee;
                    
                    addr = addresses.nextElement();
                    ipTrouvee = addr.getHostAddress();
                    
                    if (!addr.isLinkLocalAddress() 
                		&& !addr.isLoopbackAddress() 
                		&& addr.isSiteLocalAddress()
                		&& ipTrouvee.matches(ImportControleur.REGEX_IPV4)) {
                    	
                    	ip = ipTrouvee;
                    	
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return ip;
	}
	
	
	/** Label d'affichage de l'IP privée. */
	@FXML
	private Label affichageIPPrivee;
	
	
	/**
	 * Récupération de l'instance du jeu créée dans la classe Quiz.
	 * Cette instance permet la gestion des questions et catégories.
	 */
	private Jeu jeu;
	
	/**
	 * Caractère représentant le mode de sélection :
	 * - C => Catégories
	 * - Q => Questions
	 */
	private char choixSelection;
	
	/** Initialisation du contrôleur. */
	@FXML
	private void initialize() {
		jeu = Quiz.jeu;
		
		this.choixSelectionnerCategories();
	}
	
	
	/** Affichage de la fenêtre d'aide liée à la vue. */
	@FXML
	private void actionBoutonAide() {
		// TODO: dialogbox d'aide.
	}
	
	
	/** Affichage de l'IP privée de la machine courante. */
	@FXML
	private void actionBoutonAfficherMonIP() {
		String adresseIPPrivee,
			   messageAdresseIPPrivee;
		
		adresseIPPrivee = ipPrivee();
		System.out.println("IP privée = " + adresseIPPrivee);
		
		if (adresseIPPrivee != null) {
			messageAdresseIPPrivee = String.format(MODELE_LABEL_IP_PRIVEE, 
					   							   adresseIPPrivee);
			
			this.affichageIPPrivee.setText(messageAdresseIPPrivee);
		} else {
			AlerteControleur.autreAlerte(ERREUR_IP_PRIVEE_MESSAGE,
										 ERREUR_IP_PRIVEE_TITRE, 
										 AlertType.ERROR);
		}
	}
	
	
	/** Sélectionner des catégories. */
	@FXML
	private void choixSelectionnerCategories() {
		this.choixCategories.setSelected(true);
		this.choixQuestions.setSelected(false);
		
		if (this.choixSelection != 'C') {
			this.choixSelection = 'C';
			System.out.println("CHARGEMENT DES CATÉGORIES !");
		}
	}
	
	
	/** Sélectionner des questions. */
	@FXML
	private void choixSelectionnerQuestions() {
		this.choixQuestions.setSelected(true);
		this.choixCategories.setSelected(false);
		
		if (this.choixSelection != 'Q') {
			this.choixSelection = 'Q';
			System.out.println("CHARGEMENT DES QUESTIONS !");
		}
	}
	
	
	/** Retour au menu principal de l'application. */
	@FXML
	private void actionBoutonRetour() {
		NavigationControleur.changerVue("MenuPrincipal.fxml");
	}
	
	
	/** Export des données au destinataire indiqué. */
	@FXML
	private void actionBoutonExporter() {
		try {
			// TODO exporter soit des catégories soit des
			// questions (préalablement sélectionnées)
			new Serveur().envoyerCategories(jeu.getToutesLesCategories());			
		} catch (IOException e) {
			// TODO afficher pop-up export impossible
		} catch (ClassNotFoundException e) {
			
		}
	}
}
