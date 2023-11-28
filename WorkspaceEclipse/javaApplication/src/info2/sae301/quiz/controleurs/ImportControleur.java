package info2.sae301.quiz.controleurs;

import java.io.FileNotFoundException;
import java.io.IOException;

import info2.sae301.quiz.reseau.Import;
import info2.sae301.quiz.reseau.ImportLocal;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class ImportControleur {
	
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
	
	public void initialize() {
		this.importation = new Import();
	}
	
	private Import importation;
	
	/** 
	 * Champ de saisie de l'adresse IPv4 pour 
	 * l'importation en ligne. 
	 */
	@FXML
	private TextField champIpServeur;

	/**
	 * Ouverture de la fenêtre native d'ouverture de 
	 * document.
	 */
	@FXML
	private void actionBoutonParcourir() {
		try {
			this.importation.importation();
		} catch (IOException e) {
			AlerteControleur.autreAlerte("msg", "titre", AlertType.ERROR);  // TODO
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
		if (ImportLocal.getCheminFichier() != null
			&& !ImportLocal.getCheminFichier().isBlank()) {
			
			ImportLocal.importation();
			
			System.out.println("Question non ajoutées : "
					+ Import.getQuestionNonAjoutes());
			
		}
	}
}
