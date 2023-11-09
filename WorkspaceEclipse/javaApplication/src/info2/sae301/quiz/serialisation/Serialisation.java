/**
 * Serialisation.java									8 nov. 2023 
 * IUT de Rodez, no copyright ni "copyleft"
 */
package info2.sae301.quiz.serialisation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import info2.sae301.quiz.modeles.Jeu;

/**
 * Classe de sérialisation pour les objets de type "Jeu" 
 * @author FABRE Florian
 */
public class Serialisation implements Serializable{

	/** Numéro de sérialisation : clé de hachage */
	private static final long serialVersionUID = 7823106592908394070L;



	/**
	 * Sérialise les instances de type Jeu et les enregistre dans un fichier
	 * de sauvegarde
	 * @param nomSauvegarde nom de la sauvegarde à créer
	 */
	public void serialiser(String nomSauvegarde) {

		try {

			// Déclaration et création du fichier qui recevra les objets
			ObjectOutputStream flux = new ObjectOutputStream(
					new FileOutputStream(nomSauvegarde));


			System.out.print("Ecriture de " + this.toString());
			flux.writeObject(this);
			
			// Fermeture du fichier
			flux.close();
		} catch (IOException e) { 
			System.out.println("Problème d'accès au fichier " + nomSauvegarde);
		}
	}


	/**
	 * Restaure les objets jeux sérialisés et renvoie l'instance de Jeu
	 * ainsi récupérée
	 * @param nomSauvegarde nom de la sauvegarde à restaurer
	 */
	public static Jeu deserialisation(String nomSauvegarde) {
		
		// Variable qui recevra l'objet sauvegardé en mémoire
		Jeu jeuEnCours = null;
		// déclaration du fichier et lecture dans le fichier
		try {

			// Déclaration et ouverture du fichier qui contient les objets
			ObjectInputStream fluxLecture = new ObjectInputStream(
					new FileInputStream(nomSauvegarde));
			
			
			jeuEnCours = (Jeu) fluxLecture.readObject();
			
			// Fermeture du fichier
			fluxLecture.close();
			System.out.println(jeuEnCours);
		
			
		} catch (IOException e) { // problème fichier
			System.out.println("Problème d'accès au fichier " + nomSauvegarde);
		} catch (ClassNotFoundException e) {

			// exception levée si l'objet lu n'est pas de type Point
			System.out.println("Problème lors de la lecture du fichier "
					+ nomSauvegarde);
		}
		return jeuEnCours;
	}
}
