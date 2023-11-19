/*
 * Serialisation.java             									19 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.serialisation;

import java.io.FileInputStream; 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import info2.sae301.quiz.modeles.Jeu;

/**
 * Classe de sérialisation pour les objets de type "Jeu".
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class Serialisation {
	
	/** Chemin du dossier pour les sauvegardes */
    public static final String CHEMIN_DOSSIER
	= "../javaApplication/src/info2/sae301/quiz/serialisation/sauvegardes/";
	
	/**
	 * Sérialise les instances de type Jeu et les enregistre dans un fichier
	 * de sauvegarde
	 * @param aSerialiser instance à sérialiser 
	 * @param nomFichier nom du fichier dans lequel sera la sauvegarde
	 */
	public static void serialiser(Jeu aSerialiser, String nomFichier) {
		try {
			// Déclaration et création du fichier qui recevra les objets
			FileOutputStream fileOutputStream = new FileOutputStream(CHEMIN_DOSSIER
					                                                 + nomFichier);
			ObjectOutputStream fluxEcriture = new ObjectOutputStream(fileOutputStream);

			fluxEcriture.writeObject(aSerialiser);
			fluxEcriture.close();
		} catch (IOException e) {
			System.out.println("-- Erreur de sérialisation : ");
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
	        FileInputStream fileInputStream = new FileInputStream(CHEMIN_DOSSIER
	        		                                              + nomFichier);
	        ObjectInputStream fluxLecture = new ObjectInputStream(fileInputStream);
	        
	        jeuEnCours = (Jeu) fluxLecture.readObject();
			fluxLecture.close();

		} catch (IOException e) { 
			System.out.println("-- Erreur de désérialisation : ");
			System.out.println("Problème d'accès au fichier " + nomFichier);
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {

			// exception levée si l'objet lu n'est pas de type Jeu
			System.out.println("Problème lors de la lecture du fichier "
				           	   + nomFichier);
		}
		return jeuEnCours;
	}
}
