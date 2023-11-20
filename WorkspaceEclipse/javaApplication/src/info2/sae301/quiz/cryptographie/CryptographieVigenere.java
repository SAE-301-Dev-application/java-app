/*
 * Cryptographie.java									            20 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.cryptographie;

/**
 * Cryptographie d'un fichier CSV avec la méthode de Vigenère.
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class CryptographieVigenere {
	
	/**
	 * Alphabet sur lequel on pourra crypter les caractères.
	 */
	final String[] alphabet = {};
	
    /**
     * Méthode de recherche linéaire afin de trouver l'indice 
     * d'un élément dans un tableau.
     * @param alphabet Tableau sur lequel on cherche l'indice d'un élément.
     * @param lettre   Lettre pour laquelle on cherche l'indice.
     * @return 
     */
    public static int trouverLettre(int alphabet[], int lettre) {

    	int indice,
    	    longueur;
    	
    	if (alphabet == null || alphabet.length == 0) { 
            return -1; 
        }
    	
        indice = -1;
        longueur = alphabet.length; 
        
        for (int i = 0; i < longueur; i++) {
            if (alphabet[i] == lettre) { 
                indice = i; 
            } else { 
                i = i + 1; 
            } 
        }
        return indice; 
    } 
	
}
