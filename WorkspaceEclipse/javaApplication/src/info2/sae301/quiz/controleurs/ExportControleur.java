package info2.sae301.quiz.controleurs;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

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
			ipPrivee = socket.getLocalAddress().getHostAddress();
		} catch (IOException e) {
			System.out.println("Erreur IO : " + e.getMessage());
		}
		
		return ipPrivee;
	}
	
	/** Label d'affichage de l'IP privée. */
	@FXML
	private Label affichageIPPrivee;
	
	@FXML
	private void initialize() {
		
		//
		
	}
	
	@FXML
	private void actionBoutonAide() {
		// TODO: dialogbox d'aide.
	}
	
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
	private void actionBoutonRetour() {
		NavigationControleur.changerVue("MenuPrincipal.fxml");
	}
	
	@FXML
	private void actionBoutonExporter() {
		// TODO: script méthode export.
	}
}
