/*
 * ImportControleur.java							                28 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.modeles.reseau.Import;
import info2.sae301.quiz.exceptions.FormatCSVInvalideException;

import java.io.FileNotFoundException; 
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
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
	
	
	private Import importation;
	
	/** 
	 * Champ de saisie de l'adresse IPv4 pour 
	 * l'importation en ligne. 
	 */
	@FXML
	private TextField champIpServeur;

	@FXML
	private void initialize() {
		this.importation = new Import();
	}
	
	
	/**
	 * Ouverture de la fenêtre native d'ouverture de 
	 * document.
	 */
	@FXML
	private void actionBoutonParcourir() {
		try {
			this.importation.parcourirFichiers();
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
	 * Importation 
	 */
	@FXML
	private void actionBoutonImporter() {
		int nombreQuestionsImportees,
			nombreQuestionsNonImportees,
			nombreQuestionsNonImporteesAAfficher;
		
		boolean importationReussie;
		
		String cheminCourant,
			   messageImportationSucces;
		
		cheminCourant = this.importation.getCheminFichier();
		importationReussie = false;
		
		if (cheminCourant != null
			&& !cheminCourant.isBlank()) {
			
			try {
				this.importation.importer();
				importationReussie = true;
			} catch (IOException e) {
				erreurCheminInexistant();
			} catch (IllegalArgumentException e) {
				AlerteControleur.autreAlerte(e.getMessage(), 
											 ERREUR_CARACTERE_INTERDIT_TITRE,
											 AlertType.ERROR);
			} catch (FormatCSVInvalideException e) {
				AlerteControleur.autreAlerte(e.getMessage(),
						                     ERREUR_FORMAT_INVALIDE_TITRE,
						                     AlertType.ERROR);
			}
		} else {
			AlerteControleur.autreAlerte(ERREUR_AUCUN_CHEMIN_MESSAGE, 
					 ERREUR_AUCUN_CHEMIN_TITRE, 
					 AlertType.ERROR);
		}
		
		if (importationReussie) {
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

			AlerteControleur.autreAlerte(messageImportationSucces, 
										 IMPORTATION_SUCCESS_TITRE, 
										 AlertType.INFORMATION);
			
			NavigationControleur.changerVue("Import.fxml");
			
		}
	}
	
	private static void erreurCheminInexistant() {
		AlerteControleur.autreAlerte(ERREUR_CHEMIN_INEXISTANT_MESSAGE,
									 ERREUR_CHEMIN_INEXISTANT_TITRE,
									 AlertType.ERROR);
	}
}
