/*
 * DiffieHellman.java									22 nov. 2023 
 * IUT de Rodez, no copyright ni "copyleft"
 */
package info2.sae301.quiz.cryptographie;

import java.util.Random;

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
	private static final int P = 10000007;
	
	/** Constante G choisie arbitrairement. */
	private static final int G = 2711;
	
	/**
	 * Calcule le plus grand commun diviseur entre deux entiers
	 * 
	 * @param dividende
	 * @param diviseur
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
	
	
    /**
     * Génère une puissance aléatoire dans l'intervalle [100000;P]
     * 
     * @return la puissance générée
     */
    public static int genererPuissance() {
    	return new Random().nextInt((P-100000)+1)+100000;
    }
    
    
   
    /**
     * Calcule la puissance d'une valeur selon l'exposant en 
     * paramètre, le tout modulo P
     * 
     * @param valeur
     * @param exposant
     * @return le résultat de la valeur 
     * 		   à la puissance exposant modulo P
     */
    public static int puissance(int valeur, int exposant) {
    	int resultat;
    	
    	valeur %= P;
    	
    	if (exposant == 0) {
    		resultat = 1;
    	} else if (exposant % 2 == 0) {
    		resultat = puissance(valeur * valeur, exposant / 2);
    	} else {
    		resultat = valeur * puissance(valeur * valeur, (exposant - 1) / 2) % P;
    	}
    	
		return resultat;
    }

    public static void main(String[] args) {
        int puis = genererPuissance();
        int test = puissance(G, puis);
        System.out.println(puis);
        System.out.println(test);
    }
}
