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
	
	
	/** Constante pour le modulo P premier, choisie arbitrairement. */
	private static final int P = 6301;
	
	/** Constante G, générateur sous modulo P, choisi arbitrairement. */
	private static final int G = 2711;
	
	/**
	 * Entier secret servant de puissance à laquelle élever le 
	 * générateur avant de l'envoyer à l'autre machine 
	 */
	private static int puissanceSecrete;
	
	/**
	 * Entier reçu de l'autre machine qu'il faut élever à la 
	 * puissance puissanceSecrete 
	 */
	private static int cleRecue;
	
	
	/** @return le générateur G */
	public static int getGenerateur() {
		return G;
	}
	
	
	/** @return la puissance secrète */
	public static int getPuissanceSecrete() {
		return puissanceSecrete;
	}
	
	
	/** @return la puissance reçue */
	public static int getcleRecue() {
		return cleRecue;
	}
	
	/**
	 * Permet de modifier la puissance secrète
	 *  
	 * @param puissance la nouvelle puissance
	 * */
	public static void setPuissanceSecrete(int puissance) {
		puissanceSecrete = puissance;
	}
	
	
	/**
	 * Permet de modifier la puissance reçue
	 *  
	 * @param puissance la puissance reçue
	 * */
	public static void setcleRecue(int nombreRecu) {
		cleRecue = nombreRecu;
	}
	
	
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
    	return new Random().nextInt((10000007-100000)+1)+100000;
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
    public static int puissanceNR(int valeur, int exposant) {
        int resultat = 1;
        valeur %= P;

        while (exposant > 0) {
            if (exposant % 2 == 1) {
                resultat = (resultat * valeur) % P;
            }
            valeur = (valeur * valeur) % P;
            exposant /= 2;
        }

        return resultat;
    }

    public static void main(String[] args) {
        int puis = genererPuissance();
        int test = puissanceNR(G, puis);
        int test2 = puissanceNR(4950,8495403);
        System.out.println(puis);
        System.out.println(test);
    }
}
