package info2.sae301.quiz.controleurs;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;


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
	
	/**
	 * Recherche et retourne l'adresse IP de la machine sur 
	 * le réseau.
	 * 
	 * @return Adresse IP de la machine sur le réseau (IP privée)
	 */
	private static String ipPrivee() {
		final String IP_RESEAU = "192.168.1.1";
		
		final int PORT_RESEAU = 80;
		
		Socket socket;
		
		String ipPrivee;
		ipPrivee = null;
		
		try {
			socket = new Socket(IP_RESEAU, PORT_RESEAU);
			ipPrivee = socket.getInetAddress().getHostAddress();
		} catch (IOException e) {
			System.out.println("Erreur IO : " + e.getMessage());
		}
		
		return ipPrivee;
	}
	
	/** Label d'affichage de l'IP privée. */
	@FXML
	private Label affichageIPPrivee;
	
	/** Initialisation du contrôleur. */
	@FXML
	private void initialize() {
		
		//
		
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
	
	@FXML
	private void saisieIPDestinataire() {
		// TODO
		System.out.println("Saisie enregistrée.");
	}
	
	/** Retour au menu principal de l'application. */
	@FXML
	private void actionBoutonRetour() {
		NavigationControleur.changerVue("MenuPrincipal.fxml");
	}
	
	/** Export des données au destinataire indiqué. */
	@FXML
	private void actionBoutonExporter() {
		// TODO: script méthode export.
	}
}
