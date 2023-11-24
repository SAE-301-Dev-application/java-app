package info2.sae301.quiz.controleurs;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ExportControleur {
	
	private static final String MODELE_LABEL_IP_LOCALE
	= "Mon adresse IP : %s";
	
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
		String adresseIPLocale;
		
		InetAddress localhost;
		
		try {
			localhost = InetAddress.getLocalHost();
			
			adresseIPLocale = String.format(MODELE_LABEL_IP_LOCALE, 
											localhost.getHostAddress());
			
			affichageIPLocale.setText(adresseIPLocale);
		} catch (UnknownHostException e) {
			System.out.println("Erreur : " + e.getMessage());
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
