/*
 * 
 * 
 */

package info2.sae301.quiz.reseau;

import java.io.File;

import javafx.stage.FileChooser;

/**
 * 
 */
public class ImportLocal {
	
	private static String cheminFichier;
	
	
	/**
	 * 
	 */
	public static void importation() {
		Import.importation(cheminFichier);
		cheminFichier = null;
	}
	
	
	/**
	 * 
	 */
	public static void parcourirFichier() {
		final FileChooser choixFichier = new FileChooser();
		
		// Titre de l'explorateur de fichier
        choixFichier.setTitle("Importer des données");
        
        // Filtre des extensions de fichier
        choixFichier.getExtensionFilters().add(
        		new FileChooser.ExtensionFilter("CSV", "*.csv"));

        // Ouverture de l'explorateur de fichier à la racine
        choixFichier.setInitialDirectory(new File(System.getProperty("user.home")));
        
        File fichierSelectionne = choixFichier.showOpenDialog(null);
        
        if (fichierSelectionne != null) {
        	cheminFichier = fichierSelectionne.getPath();
        }
	}

	
	/**
	 * 
	 * @return
	 */
	public static String getCheminFichier() {
		return cheminFichier;
	}
	
}
