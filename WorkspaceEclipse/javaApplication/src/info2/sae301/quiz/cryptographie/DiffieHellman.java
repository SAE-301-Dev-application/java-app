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
	private static final int P = 6301;
	
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
     * Trouve le modulo inverse d'un nombre 'a' modulo 'm'.
     *
     * @param a Le nombre pour lequel on veut trouver le modulo inverse.
     * @param m Le modulo.
     * @return Le modulo inverse de 'a' modulo 'm'.
     * @throws ArithmeticException Si le modulo inverse n'existe pas (c'est-à-dire si 'a' et 'm' ne sont pas premiers entre eux).
     */
    public static int trouverModuloInverse(int a, int m) {
        // Calcule et stocke les coefficients de l'identité de Bézout
        int[] resultat = algorithmeEuclideEtendu(a, m);

        // Si le PGCD de 'a' et 'm' n'est pas égal à 1, alors le modulo inverse n'existe pas
        if (resultat[0] != 1) {
            throw new ArithmeticException("Le modulo inverse n'existe pas");
        }

        // Assure que le résultat est positif
        int x = (resultat[1] % m + m) % m;
        return x;
    }

    /**
     * Algorithme d'Euclide étendu pour trouver les coefficients de l'identité de Bézout.
     *
     * @param a Le premier entier.
     * @param b Le deuxième entier.
     * @return Un tableau d'entiers où le premier élément est le PGCD et les deux éléments suivants sont les coefficients de Bézout.
     */
    private static int[] algorithmeEuclideEtendu(int a, int b) {
        if (b == 0) {
            // Le PGCD(a, 0) est 'a', et les coefficients sont (1, 0)
            return new int[]{a, 1, 0};
        } else {
            // Récursivement trouve les coefficients et le PGCD
            int[] values = algorithmeEuclideEtendu(b, a % b);
            int pgcd = values[0];
            int x1 = values[2];
            int y1 = values[1] - (a / b) * values[2];

            // Retourne le PGCD et les coefficients (x et y)
            return new int[]{pgcd, x1, y1};
        }
    }
    
    public static int genererPuissance() {
    	return new Random().nextInt((70-10)+1)+10;
    }

    public static void main(String[] args) {
        // Exemple d'utilisation :
        int a = 2711;
        int m = 6301;

        try {
            int inverse = trouverModuloInverse(a, m);
            System.out.println("Le modulo inverse de " + a + " modulo " + m + " est : " + inverse);
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
        
        for (int i = 0; i < 100; i++) {
        	System.out.println(DiffieHellman.genererPuissance());
        }
    }
}
