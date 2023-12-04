/*
 * ImportControleur.java							                28 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import static info2.sae301.quiz.controleurs.AlerteControleur.autreAlerte; 

import info2.sae301.quiz.modeles.reseau.Import;
import info2.sae301.quiz.exceptions.AdresseIPInvalideException;
import info2.sae301.quiz.exceptions.FormatCSVInvalideException;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Contrôleur FXML de la vue Import qui permet d'importer depuis un fichier
 * CSV local ou depuis un ordinateur distant des questions et catégories.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class ImportControleur {
	
	private final static String ERREUR_IMPORT_TITRE
	= "IMPORT IMPOSSIBLE";
	
	private final static String ERREUR_INATTENDUE
	= "Une erreur inattendue s'est produite. Veuillez réessayer.";
	
	private final static String ERREUR_CHEMIN_INEXISTANT_TITRE
	= "ERREUR AU CHARGEMENT D'UN FICHIER";
	
	private final static String ERREUR_CHEMIN_INEXISTANT_MESSAGE
	= "Le chemin spécifié n'existe pas ou plus. Veuillez réessayer.";
	
	private final static String ERREUR_FORMAT_INVALIDE_TITRE
	= "FORMAT DU CSV INVALIDE";
	
	private final static String ERREUR_AUCUN_CHEMIN_TITRE
	= "AUCUN CHEMIN NI ADRESSE IP SPÉCIFIÉ";
	
	private final static String ERREUR_AUCUN_CHEMIN_MESSAGE
	= "Importation impossible. Aucun chemin ni aucune adresse IP n'a été spécifié.";
	
	private final static String AIDE_TITRE = "IMPORTATION DE QUESTIONS";
	
	private final static String AIDE_TEXTE
	= """
	  Il est possible d’importer des questions sur le jeu
	  de deux manières différentes :
	  - localement (en sélectionnant le fichier de données csv au format UTF-8)
	  - en ligne (via un ordinateur émetteur sur le même réseau).
	  
	  Une fois l’importation effectuée les ajouts sont directement
	  utilisables sur le jeu, en créant une nouvelle partie, par exemple.
	  """;
	
	/** Expression régulière d'une adresse IPv4. */
	protected static final String REGEX_IPV4 = "^([0-9.]+)$";
	
	private Import importation;
	
	private boolean importEnCours;
	
	/** 
	 * Champ de saisie de l'adresse IPv4 pour 
	 * l'importation en ligne. 
	 */
	@FXML
	private TextField champIpServeur;
	
	@FXML
	private Label cheminCourant;
	
	@FXML
	private Label texteEnAttente;

	@FXML
	private void initialize() {
		this.importEnCours = false;
		this.importation = new Import();
	}
	
	
	/**
	 * Retourne si l'adresse IP fournie respecte
	 * l'expression régulière IPv4 définie.
	 * 
	 * @param ip Adresse IP à vérifier
	 * @return Si l'adresse IP est une IPv4 valide
	 */
	protected static boolean isIpValide(String ip) {
		return ip.matches(REGEX_IPV4);
	}
	
	
	/**
	 * Ouverture de la fenêtre native d'ouverture de 
	 * document.
	 */
	@FXML
	private void actionBoutonParcourir() {
		String nomFichierSelectionne;
		
		Path objetCheminCourant;
		
		this.cheminCourant.setText("Aucun");
		
		if (!this.importEnCours) {
			try {
				this.importation.parcourirFichiers();
				
				objetCheminCourant = Path.of(this.importation.getCheminFichier());
				nomFichierSelectionne = objetCheminCourant.getFileName().toString();
				
				this.cheminCourant.setText(nomFichierSelectionne);
			} catch (FileNotFoundException e) {
				erreurCheminInexistant();
			}			
		}
	}
	
	
	/**
	 * Affichage d'une pop-up d'aide concernant l'import de questions.
	 */
	@FXML
	private void actionBoutonAider() {
		AlerteControleur.aide(AIDE_TITRE, AIDE_TEXTE);
	}
	
	
	/**
	 * Retour au menu principal.
	 */
	@FXML
	private void actionBoutonAnnuler() {
		if (!this.importEnCours) {
			NavigationControleur.changerVue("MenuPrincipal.fxml");			
		}
	}
	
	
	/**
	 * Action du bouton "Importer".
	 * Import local si un fichier a été sélectionné ou sinon import distant si
	 * une adresse ip a été renseignée.
	 * Sinon une pop-up d'erreur est affichée.
	 */
	@FXML
	private void actionBoutonImporter() {
		String cheminCourant,
		       ipEntree;
		
		cheminCourant = this.importation.getCheminFichier();
		
		ipEntree = this.champIpServeur.getText();
		
		if (!this.importEnCours) {
			if (cheminCourant != null
					&& !cheminCourant.isBlank()) {
				
				demarrerImportLocal();
				
			} else if (ipEntree != null && !ipEntree.isBlank()) {
				
				demarrerImportDistant(ipEntree);
				
			} else {
				autreAlerte(ERREUR_AUCUN_CHEMIN_MESSAGE, 
						ERREUR_AUCUN_CHEMIN_TITRE, AlertType.ERROR);
			}			
		}
	}
	
	
	/**
	 * Choix d'un import local à partir des données d'un fichier CSV
	 * qui vont être lues et analysées afin de déterminer les erreurs.
	 */
	private void demarrerImportLocal() {
		try {
			this.importation.importerLocalement();
			redirectionEnregistrementImports();
		} catch (FormatCSVInvalideException e) {
			autreAlerte(e.getMessage(), ERREUR_FORMAT_INVALIDE_TITRE,
                        AlertType.ERROR);
		} catch (IllegalArgumentException e) {
			autreAlerte(e.getMessage(), ERREUR_FORMAT_INVALIDE_TITRE,
                        AlertType.ERROR);
	    } catch (Exception e) {
			autreAlerte(ERREUR_INATTENDUE, ERREUR_IMPORT_TITRE,
                        AlertType.ERROR);
	    }
	}
	
	
	/**
	 * Choix d'un import distant à partir des données envoyées par le serveur
	 * dont l'adresse IPV4 est en paramètre.
	 * 
	 * @param ipEntree Adresse IPV4 du serveur distant devant envoyer
	 *                 les questions à importer.
	 */
	private void demarrerImportDistant(String ipEntree) {
		try {
			importation.verifierAdresseIPV4Valide(ipEntree);				
			
			texteEnAttente.setVisible(true);

	        CompletableFuture.supplyAsync(() -> {
	            try {
	                this.importation.importerADistance(ipEntree);
	                
	                return "Succes";
	            } catch (Exception e) {
	                return e.getMessage();
	            }
	        }).thenAccept(resultat -> {
	            Platform.runLater(() -> {
	                texteEnAttente.setVisible(false);

	                if (resultat.equals("Succes")) {
	                	redirectionEnregistrementImports();
	                } else {
	                	autreAlerte(resultat, ERREUR_IMPORT_TITRE,
	                			    AlertType.ERROR);
	                }
	            });
	        });
		} catch (AdresseIPInvalideException e) {
			autreAlerte(e.getMessage(), ERREUR_FORMAT_INVALIDE_TITRE,
                        AlertType.ERROR);
	    }
	}
	
	
	/**
	 * Affichage d'une pop-up d'erreur indiquant que le chemin spécifié
	 * pour accéder au CSV est invalide.
	 */
	private static void erreurCheminInexistant() {
		autreAlerte(ERREUR_CHEMIN_INEXISTANT_MESSAGE,
				    ERREUR_CHEMIN_INEXISTANT_TITRE,
					AlertType.ERROR);
	}
	
	
	/**
	 * Redirection vers la vue de sélection des éléments 
	 * importés à enregistrer.
	 */
	private static void redirectionEnregistrementImports() {
		NavigationControleur.changerVue("SelectionQuestionsImportees.fxml");
	}
	
}
