package info2.sae301.quiz.controleurs;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class ExportControleur {
	
	private static final String ERREUR_IP_LOCALE_TITRE
	= "Erreur dans la recherche de l'IP locale";
	
	private static final String ERREUR_IP_LOCALE_MESSAGE
	= """
	  Il est impossible au programme de trouver votre adresse IP sur le réseau.
	  Veuillez vérifier que vous êtes connecté à un réseau.
	  """;
	
	private static final String MODELE_LABEL_IP_LOCALE
	= "Mon adresse IP : %s";
	
	private static String ipLocale() {
		final String IP_RESEAU = "192.168.1.1";
		
		final int PORT_RESEAU = 80;
		
		Socket socket;
		
		String ipLocale;
		ipLocale = null;
		
		try {
			socket = new Socket(IP_RESEAU, PORT_RESEAU);
			ipLocale = socket.getLocalAddress().getHostAddress();
		} catch (IOException e) {
			System.out.println("Erreur IO : " + e.getMessage());
		}
		
		return ipLocale;
	}
	
	@FXML
	private Label affichageIPLocale;
	
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
		String adresseIPLocale,
			   messageAdresseIPLocale;
		
		adresseIPLocale = ipLocale();
		
		if (adresseIPLocale != null) {
			messageAdresseIPLocale = String.format(MODELE_LABEL_IP_LOCALE, 
					   							   adresseIPLocale);
			
			affichageIPLocale.setText(messageAdresseIPLocale);
		} else {
			AlerteControleur.autreAlerte(ERREUR_IP_LOCALE_MESSAGE,
										 ERREUR_IP_LOCALE_TITRE, 
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
