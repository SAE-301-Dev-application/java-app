package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.reseau.Import;
import info2.sae301.quiz.reseau.ImportLocal;
import javafx.fxml.FXML;

public class ImportControleur {

	@FXML
	private void actionBoutonParcourir() {
		ImportLocal.parcourirFichier();
	}
	
	@FXML
	private void saisieIPServeur() {
		// TODO
		System.out.println("Saisie enregistrée.");
	}
	
	@FXML
	private void actionBoutonAnnuler() {
		NavigationControleur.changerVue("MenuPrincipal.fxml");
	}
	
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
