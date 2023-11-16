package info2.sae301.quiz.serialisation;

import java.io.FileInputStream; 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import info2.sae301.quiz.modeles.Jeu;

/**
 * Classe de sérialisation pour les objets de type "Jeu" 
 * @author LACAM Samuel, FABRE Florian
 */
public class Serialisation{
	
	
	
	//Chemin pour les sauvegardes
	private static String cheminDossier = "../sauvegarde/";
	

	/**
	 * Sérialise les instances de type Jeu et les enregistre dans un fichier
	 * de sauvegarde
	 * @param aSerialiser instance à sérialiser 
	 * @param nomFichier nom du fichier dans lequel sera la sauvegarde
	 */
	public static void serialiser(Jeu aSerialiser, String nomFichier) {
		try {

			// Déclaration et création du fichier qui recevra les objets
			System.out.println(cheminDossier + nomFichier);
			FileOutputStream fileOutputStream = new FileOutputStream(cheminDossier + nomFichier);
			ObjectOutputStream fluxEcriture = new ObjectOutputStream(fileOutputStream);

//			System.out.println("Ecriture de " + aSerialiser.toString());
			fluxEcriture.writeObject(aSerialiser);

			// Fermeture du fichier
			fluxEcriture.close();
		} catch (IOException e) { 
			System.out.println("Problème d'accès au fichier " + nomFichier);
		}
	}
	
	/**
	 * Restaure l'objet Jeu sérialisé et renvoie l'instance de Jeu
	 * ainsi récupérée
	 * @param nomFichier nom de la sauvegarde à restaurer
	 */
	public static Jeu deserialiser(String nomFichier) {

		// Variable qui recevra l'objet sauvegardé en mémoire
		Jeu jeuEnCours = null;

		// déclaration du fichier et lecture dans le fichier
		try {
	        FileInputStream fileInputStream = new FileInputStream(cheminDossier + nomFichier);
	        ObjectInputStream fluxLecture = new ObjectInputStream(fileInputStream);
	        
	        jeuEnCours = (Jeu) fluxLecture.readObject();

			// Fermeture du fichier
			fluxLecture.close();
//			System.out.println(jeuEnCours);

		} catch (IOException e) { // problème fichier
			System.out.println("Problème d'accès au fichier " + nomFichier);
		} catch (ClassNotFoundException e) {

			// exception levée si l'objet lu n'est pas de type Jeu
			System.out.println("Problème lors de la lecture du fichier "
					+ nomFichier);
		}
		return jeuEnCours;
	}
}
