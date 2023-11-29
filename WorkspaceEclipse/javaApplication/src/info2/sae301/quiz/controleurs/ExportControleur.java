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
	
	
	/** CheckBox "exportCategorie". */
	@FXML
	private CheckBox choixCategories;
	
	
	/** CheckBox "10 questions". */
	@FXML
	private CheckBox choixQuestions;
	
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
	
	private int choixExport;
	
	/** Initialisation du contrôleur. */
	@FXML
	private void initialize() {
		this.choixExport = 0;
		jeu = Quiz.jeu;
		
		/*
		 * Choix pour export en sélectionnant des questions 
		 * ou des catégories
		 */
		this.choixCategories.setOnAction(event -> {
			this.choixExportQuestionCategories(0);
		});
		
		this.choixQuestions.setOnAction(event -> {
			this.choixExportQuestionCategories(1);
		});
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
	
	
	/**
	 * Affichage de l'erreur :
	 * Le nombre de questions sélectionné ne vaut ni 5, 10 et 20.
	 */
	private static void erreurNombreQuestions() {
		AlerteControleur.autreAlerte(ParametresPartie.NOMBRE_INVALIDE, 
				 					 "Caca", 
				 					 AlertType.ERROR);
	}
	
	/**
	 * Choix du nombre de questions pour le quiz.
	 * 
	 * @param nombre
	 */
	@FXML
	private void choixExportQuestionCategories(int nombre) {
		if (nombre != 0 && nombre != 1) {
			erreurNombreQuestions();
		} else {
			this.choixExport = nombre;

			this.choixCategories.setSelected(nombre == 0);
			this.choixQuestions.setSelected(nombre == 1);
		}
	}
}
