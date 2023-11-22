/*
 * DiffieHellman.java									22 nov. 2023 
 * IUT de Rodez, no copyright ni "copyleft"
 */
package info2.sae301.quiz.cryptographie;

/**
 * Échange sécurisé d'un fichier CSV avec l'algorithme de 
 * Diffie Hellman.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class DiffieHellman {
	
	/*
	 * TODO
	 * - Un entier premier p, les calculs se feront modulo p
	 * - Un entier g qui peut être une constante compris en [1 ; p-1]
	 * - Méthode PGCD
	 * - méthode trouver classe inversible
	 * - entier aléatoire à envoyer à l’autre 
	 * - isCleValide
	 * - 2 entiers a et b qui élèveront g a la puissance. 
	 * Cependant, y a peut être de la merde simon nous trompe, 
	 * les illuminatis sont présents
	 * Simon le clutch !!!!!!!!!!!!!!!
	 * Jojo comprend, simon devient cringe, puis modulo p
	 * AAAAAAAAAAAAAAAHHHHHHHHHHHHHHH!!!!!
	 */
	
	/** Constante pour le modulo P. */
	private static final long P = 145236992287L;
	
	/** Constante G choisie arbitrairement. */
	private static final long G = 20165487598L;
	
	/**
	 * @param entier1
	 * @param entier2
	 * @return Le PGCD sachant les deux entiers donnés en argument
	 */
	private static int pgcd(int dividende, int diviseur) {
		int reste;

		reste = 0;
		
		while (diviseur > 0) {
			reste = dividende % diviseur;
			dividende = diviseur;
			diviseur = reste;
		}
		
		return dividende;
	}
	
}
