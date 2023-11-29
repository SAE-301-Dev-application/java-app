/*
 * ImportControleur.java							                28 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import static info2.sae301.quiz.controleurs.AlerteControleur.autreAlerte;

import info2.sae301.quiz.modeles.reseau.Import;
import info2.sae301.quiz.exceptions.FormatCSVInvalideException;

import java.io.FileNotFoundException; 
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.file.Path;

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
	
	private final static String ERREUR_CHEMIN_INEXISTANT_TITRE
	= "ERREUR AU CHARGEMENT D'UN FICHIER";
	
	private final static String ERREUR_CHEMIN_INEXISTANT_MESSAGE
	= "Le chemin spécifié n'existe pas ou plus. Veuillez réessayer.";
	
	private final static String ERREUR_SERVEUR_TITRE
	= "ERREUR DE CONNEXION AU SERVEUR";
	
	private final static String ERREUR_SERVEUR_INCONNU_MESSAGE
	= "Aucun serveur n'est connu avec l'adresse IP spécifiée.";
	
	private final static String ERREUR_SERVEUR_INDISPONIBLE_MESSAGE
	= "Le serveur dont l'adresse IP a été renseignée ne répond pas.";
	
	private final static String ERREUR_FORMAT_INVALIDE_TITRE
	= "FORMAT DU CSV INVALIDE";
	
	private final static String ERREUR_CARACTERE_INTERDIT_TITRE
	= "CARACTÈRE INTERDIT DÉTECTÉ";
	
	private final static String IMPORTATION_SUCCESS_TITRE
	= "IMPORTATION TERMINÉE";
	
	private final static String IMPORTATION_SUCCESS_MESSAGE
	= "L'importation de %d questions s'est terminée avec succès.";
	
	private final static String ERREUR_AUCUN_CHEMIN_TITRE
	= "AUCUN CHEMIN SPÉCIFIÉ";
	
	private final static String ERREUR_AUCUN_CHEMIN_MESSAGE
	= "Importation impossible. Aucun chemin n'a été spécifié.";
	
	private final static String QUESTIONS_NON_IMPORTEES
	= "\n\nNéanmoins, %d questions n'ont pas pu être importées :";
	
	/** Expression régulière d'une adresse IPv4. */
	protected static final String REGEX_IPV4 = "^([0-9.]+)$";
	
	private Import importation;
	
	/** 
	 * Champ de saisie de l'adresse IPv4 pour 
	 * l'importation en ligne. 
	 */
	@FXML
	private TextField champIpServeur;
	
	@FXML
	private Label cheminCourant;

	@FXML
	private void initialize() {
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
		
		try {
			this.importation.parcourirFichiers();
			
			objetCheminCourant = Path.of(this.importation.getCheminFichier());
			nomFichierSelectionne = objetCheminCourant.getFileName().toString();
			
			this.cheminCourant.setText(nomFichierSelectionne);
		} catch (FileNotFoundException e) {
			erreurCheminInexistant();
		}
	}
	
	
	/**
	 * Retour au menu principal.
	 */
	@FXML
	private void actionBoutonAnnuler() {
		NavigationControleur.changerVue("MenuPrincipal.fxml");
	}
	
	
	/**
	 * Action du bouton "Importer".
	 * Import local si un fichier a été sélectionné ou sinon import distant si
	 * une adresse ip a été renseignée.
	 * Sinon une pop-up d'erreur est affichée.
	 */
	@FXML
	private void actionBoutonImporter() {
		String cheminCourant;
		
		cheminCourant = this.importation.getCheminFichier();
		
		try {
			/*
			 * Import local
			 */
			if (cheminCourant != null
				&& !cheminCourant.isBlank()) {
				
				this.importation.importerLocalement();
				indicationStatutImportation();
				
			}
			/*
			 * Import distant
			 */
			else if (this.champIpServeur.getText() != null
					   && !this.champIpServeur.getText().isBlank()) {
				
				this.importation.importerADistance(this.champIpServeur.getText());
				indicationStatutImportation();
				
			} else {
				
				autreAlerte(ERREUR_AUCUN_CHEMIN_MESSAGE, 
							ERREUR_AUCUN_CHEMIN_TITRE, AlertType.ERROR);
			}	
		} catch (IllegalArgumentException e) {
		    autreAlerte(e.getMessage(), ERREUR_CARACTERE_INTERDIT_TITRE,
						AlertType.ERROR);
		} catch (FormatCSVInvalideException e) {
			autreAlerte(e.getMessage(), ERREUR_FORMAT_INVALIDE_TITRE,
                        AlertType.ERROR);
		} catch (ClassNotFoundException e) {
			erreurServeurInconnu();
		} catch (SocketTimeoutException e) {
			erreurServeurIndisponible();
		} catch (IOException e) {
			erreurServeurInconnu();
		}
	}
	
	
	/**
	 * Indication via une pop-up du nombre de questions importées
	 * après la réussite de l'import.
	 */
	private void indicationStatutImportation() {
		int nombreQuestionsImportees,
		    nombreQuestionsNonImportees,
		    nombreQuestionsNonImporteesAAfficher;
		
		String messageImportationSucces;
		
		nombreQuestionsNonImportees 
		= this.importation.getQuestionsNonAjoutees().size();
		
		nombreQuestionsImportees 
		= this.importation.getNombreTotalQuestions() 
		  - nombreQuestionsNonImportees;
		
		if (nombreQuestionsImportees == 0) {
			messageImportationSucces
			= "Aucune question n'a été importée.";
			
		} else if (nombreQuestionsNonImportees > 0) {
			messageImportationSucces 
			= String.format(IMPORTATION_SUCCESS_MESSAGE 
							+ QUESTIONS_NON_IMPORTEES, 
							nombreQuestionsImportees, 
							nombreQuestionsNonImportees);
			
			nombreQuestionsNonImporteesAAfficher
			= Math.min(10, nombreQuestionsNonImportees);
			
			for (int i = 0; 
				 i < nombreQuestionsNonImporteesAAfficher; 
				 i++) {
				
				messageImportationSucces 
				+= "\n— " 
				   + this.importation.getQuestionsNonAjoutees().get(i);
				
			}
			
			if (nombreQuestionsNonImporteesAAfficher 
				< nombreQuestionsNonImportees) {
				
				messageImportationSucces 
				+= String.format("\nEt %d autres questions...", 
								 nombreQuestionsNonImportees 
								 - nombreQuestionsNonImporteesAAfficher);
			}
		} else {
			messageImportationSucces 
			= String.format(IMPORTATION_SUCCESS_MESSAGE, 
							nombreQuestionsImportees);
		}

		autreAlerte(messageImportationSucces, IMPORTATION_SUCCESS_TITRE, 
					AlertType.INFORMATION);
		
		NavigationControleur.changerVue("Import.fxml");
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
	 * Affichage d'une pop-up d'erreur indiquant que le serveur dont l'adresse
	 * IP a été spécifiée n'est pas sur le réseau.
	 */
	private static void erreurServeurInconnu() {
		autreAlerte(ERREUR_SERVEUR_INCONNU_MESSAGE, ERREUR_SERVEUR_TITRE,
				    AlertType.ERROR);
	}
	
	
	/**
	 * Affichage d'une pop-up d'erreur indiquant que le serveur dont l'adresse
	 * IP a été spécifiée ne répond pas.
	 */
	private static void erreurServeurIndisponible() {
		autreAlerte(ERREUR_SERVEUR_INDISPONIBLE_MESSAGE, ERREUR_SERVEUR_TITRE,
					AlertType.ERROR);
	}
	
}
