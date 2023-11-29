/*
 * OutilsCSV.java									                    28 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */


package info2.sae301.quiz.modeles.fichiers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Outils et méthodes pouvant être utiles pour des actions sur 
 * @author Samuel Lacam
 */
public class OutilsCSV {
	
	/**
	 * En-tête par défaut du fichier CSV.
	 */
	private static final String ENTETE_FICHIER
	= "Catégorie;Niveau;Libellé;juste;faux1;faux2;faux3;faux4;feedback";

	/**
	 * Chemin du dossier pour les sauvegardes.
	 */
	private static final String CHEMIN_DOSSIER
	= "../javaApplication/src/info2/sae301/quiz/sauvegardes/";

	/**
	 * Nom du fichier à créer pour restituer les données importées.
	 */
	private static final String NOM_FICHIER_CSV = "donneesImportees.csv";

	private static File fichierCSV;

	/**
	 * Initialise le fichier CSV en créant le dossier et le fichier 
	 * s'ils n'existent pas.
	 * Si le dossier existe déjà, il ne sera pas recréé.
	 * Si le fichier existe déjà, aucune action n'est effectuée.
	 * @throws IOException 
	 */
	public static void initialiserFichierCSV() throws IOException {
		// Création du dossier s'il n'existe pas
		File dossier = new File(CHEMIN_DOSSIER);
		if (!dossier.exists()) {
			dossier.mkdirs();
		}

		// Création du fichier s'il n'existe pas
		fichierCSV = new File(CHEMIN_DOSSIER + NOM_FICHIER_CSV);
		
		if (!fichierCSV.exists()) {
			fichierCSV.createNewFile();

			// Écrire la première ligne ENTETE_FICHIER
			FileWriter writer = new FileWriter(fichierCSV);
			writer.write(ENTETE_FICHIER);
			writer.close();
		} else {
			// Si le fichier existe, le vider et écrire la première ligne ENTETE_FICHIER
			// Le paramètre false indique de récrire le fichier
			FileWriter writer = new FileWriter(fichierCSV, false);
			writer.write(ENTETE_FICHIER);
			writer.close();
		}
	}

	/**
	 * Écrit une ligne dans le fichier CSV.
	 *
	 * @param ligneRecue La ligne à écrire dans le fichier CSV.
	 *        La ligne doit être au format CSV, avec les colonnes
	 *        séparées par des points-virgules.
	 *        Par exemple :
	 *        "Catégorie;Niveau;Libellé;juste;faux1;faux2;faux3;faux4;feedback"
	 */
	public static void ecrireLigneCSV(String ligneRecue)
			throws IOException {
		FileWriter writer = new FileWriter(fichierCSV, true);
		writer.write("\n" + ligneRecue);
		writer.close();
	}
}