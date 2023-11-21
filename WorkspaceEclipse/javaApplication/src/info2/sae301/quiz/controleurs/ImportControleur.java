package info2.sae301.quiz.controleurs;

import info2.sae301.quiz.reseau.ImportLocal;
import javafx.fxml.FXML;

public class ImportControleur {

	@FXML
	private void actionBoutonParcourir() {
		ImportLocal.parcourirFichier();
	}
	
	@FXML
	private void actionBoutonAnnuler() {
		NavigationControleur.changerVue("MenuPrincipal.fxml");
	}
	
	@FXML
	private void actionBoutonImporter() {
		
	}
}
