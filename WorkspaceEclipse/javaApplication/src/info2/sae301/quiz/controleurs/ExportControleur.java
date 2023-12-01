/*
 * ExportControleur.java					                        28 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.Quiz;
import info2.sae301.quiz.modeles.Categorie;
import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.modeles.Question;
import info2.sae301.quiz.modeles.reseau.Serveur;

import static info2.sae301.quiz.controleurs.AlerteControleur.autreAlerte;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import javafx.application.Platform;
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
	
	private final static String EXPORT_REUSSI_TITRE
	= "CONFIRMATION EXPORT QUESTIONS";
	
	private final static String EXPORT_REUSSI_MESSAGE
	= "Votre export de %d questions a été effectué avec succès.";
	
	private final static String ERREUR_EXPORT_TITRE
	= "ERREUR EXPORT QUESTIONS";
	
	private final static String ERREUR_TIMEOUT_MESSAGE
	= "Aucun client ne s'est connecté après 10 secondes d'attente.\nVeuillez"
	  + " réessayer l'export.";
	
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
	  Il est impossible de trouver votre adresse IP sur le réseau.
	  Veuillez vérifier que vous êtes connecté à un réseau ou vous connecter
	  à internet si possible.
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
     * Récupération de l'adresse IP locale de la machine en créant un socket UDP
     * temporaire. Le socket choisit l'interface réseau nécessaire à la la sortie
     * en se connectant à un serveur DNS externe.
     * On choisit l'adresse IP 8.8.8.8 de Google afin d'être presque sûr
     * d'avoir une réponse si l'ordinateur a bien une connexion internet.
     * Si uine erreur est renvoyée (ex: aucune connexion internet) on tente
     * d'accéder à l'adresse IP locale avec la méthode ipPrivee().
     * 
     * @return Une chaîne de caractères représentant l'adresse IP locale.
	 * @throws UnknownHostException 
	 * @throws SocketException 
	 * @throws  
     */
    public static String adresseIpLocale()
    throws UnknownHostException, SocketException {

        DatagramSocket socket = new DatagramSocket();
        socket.connect(InetAddress.getByName("8.8.8.8"), 10002);

        String ip = socket.getLocalAddress().getHostAddress();

        socket.close();
        
        return ip;
    }
    
	
	/** Label d'affichage de l'IP privée. */
	@FXML
	private Label affichageIPPrivee;
	
	@FXML
	private Label titre;
	
	
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
	
	/** Coordonnée X du prochain élément de la grille. */
	private int prochainXGrilleSelection;
	
	/** Coordonnée Y du prochain élément de la grille. */
	private int prochainYGrilleSelection;
	
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
		
		adresseIPPrivee = null;
		
		try {
			adresseIPPrivee = adresseIpLocale();			
			System.out.println("\nIP privée = " + adresseIPPrivee);
		} catch (Exception e) {
			erreurAccesIpLocale();
		}
		
		if (adresseIPPrivee != null) {
			messageAdresseIPPrivee = String.format(MODELE_LABEL_IP_PRIVEE, 
					   							   adresseIPPrivee);
			
			this.affichageIPPrivee.setText(messageAdresseIPPrivee);
		} else {
			erreurAccesIpLocale();
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
			this.chargementSelectionCategories();
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
			this.chargementSelectionQuestions();
		}
	}
	
	
	/** 
	 * Charge les différentes catégories sélectionnables. 
	 * 
	 * @param listeCategories
	 */
	private void chargementSelectionCategories() {
		CheckBox choixCourant;
		
		this.prochainXGrilleSelection = 0;
		this.prochainYGrilleSelection = 0;
		
		for (Categorie categorieCourante: this.jeu.getToutesLesCategories()) {
			choixCourant = new CheckBox();
			choixCourant.setText(categorieCourante.getIntitule());
			
			this.grilleSelection.add(choixCourant, prochainXGrilleSelection, prochainYGrilleSelection);
			
			if (prochainXGrilleSelection == 2) {
				prochainYGrilleSelection++;
				prochainXGrilleSelection = 0;
			} else {
				prochainXGrilleSelection++;
			}
		}
	}
	
	
	/** 
	 * Charge les différentes catégories sélectionnables. 
	 * 
	 * @param listeCategories
	 */
	private void chargementSelectionQuestions() {
		CheckBox choixCourant;
		
		this.prochainXGrilleSelection = 0;
		this.prochainYGrilleSelection = 0;
		
		for (Question questionCourante: this.jeu.getToutesLesQuestions()) {
			choixCourant = new CheckBox();
			choixCourant.setText(questionCourante.getIntitule());
			
			this.grilleSelection.add(choixCourant, prochainXGrilleSelection, prochainYGrilleSelection);
			
			if (prochainXGrilleSelection == 2) {
				prochainYGrilleSelection++;
				prochainXGrilleSelection = 0;
			} else {
				prochainXGrilleSelection++;
			}

		}
	}
	
	
	/** Retour au menu principal de l'application. */
	@FXML
	private void actionBoutonRetour() {
		NavigationControleur.changerVue("MenuPrincipal.fxml");
	}
	
	
	/**
	 * Export des données à travers un serveur auquel le client doit se connecter.
	 * Modification du titre de la vue en fonction de l'état de l'export
	 * pour indiquer à l'utilisateur l'action en cours.
	 */
	@FXML
	private void actionBoutonExporter() {
		int nombreQuestionsExportees;
		
		Serveur serveur;
		
		ArrayList<Question> questionsAExporter;
		
		serveur = new Serveur();
		
		// TODO remplacer jeu.getToutesLesQuestions() par les questions
		// sélectionnées
		questionsAExporter = jeu.getToutesLesQuestions(); // STUB
		
		nombreQuestionsExportees = questionsAExporter.size();
		
		titre.setText("EXPORTATION EN COURS...");
		
		CompletableFuture.supplyAsync(() -> {
            try {
            	serveur.envoyerQuestions(questionsAExporter);
                return "Succes";
            } catch (SocketTimeoutException e) {
            	return ERREUR_TIMEOUT_MESSAGE;
            } catch (ClassNotFoundException e) {
            	return ERREUR_TIMEOUT_MESSAGE;
            } catch (Exception e) {
                return e.getMessage();
            }
        }).thenAccept(resultat -> {
            Platform.runLater(() -> {
            	gestionResultatExport(resultat, nombreQuestionsExportees);
                
        		CompletableFuture.supplyAsync(() -> {
                    return null;
                }).thenAccept(result -> {
                    Platform.runLater(() -> {
                    	titre.setText("EXPORTATION");
                    });
                });
            });
        });
	}
	
	
	/**
	 * Après avoir terminé l'export, le titre est modifié et une pop-up
	 * de confirmation ou d'erreur est affichée.
	 * En cas d'erreur le message diffusé est le contenu du paramètre resultat.
	 * 
	 * @param resultat Le résulat de l'export.
	 * @param nombreQuestions Le nombre de questions exportées.
	 */
	private void gestionResultatExport(String resultat, int nombreQuestions) {
		titre.setText("EXPORTATION TERMINEE");

        if (resultat.equals("Succes")) {
        	afficherConfirmationExport(nombreQuestions);
        } else {
        	autreAlerte(resultat, ERREUR_EXPORT_TITRE,
        			    AlertType.ERROR);
        }
	}
	
	
	/**
	 * Affichage d'une pop-up de confirmation indiquant que l'export
	 * a été effectué correctement.
	 */
	private static void afficherConfirmationExport(int nombreQuestionsExportees) {
		AlerteControleur.autreAlerte(String.format(EXPORT_REUSSI_MESSAGE,
				                                   nombreQuestionsExportees),
				                     EXPORT_REUSSI_TITRE,
					                 AlertType.INFORMATION);
	}
	
	
	/**
	 * Affichage d'une pop-up d'erreur indiquant que l'affichage de l'ip locale
	 * a échoué.
	 */
	private static void erreurAccesIpLocale() {
		AlerteControleur.autreAlerte(ERREUR_IP_PRIVEE_MESSAGE,
                                     ERREUR_IP_PRIVEE_TITRE, 
                                     AlertType.ERROR);
	}
}