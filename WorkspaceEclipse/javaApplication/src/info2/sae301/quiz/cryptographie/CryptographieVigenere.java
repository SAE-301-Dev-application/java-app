/**
 * Cryptographie.java									20 nov. 2023
 * IUT de Rodez, no copyright ni "copyleft"
 */
package info2.sae301.quiz.cryptographie;

/**
 * Cryptographie d'un fichier CSV avec la méthode de Vigenère
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class CryptographieVigenere {
	
	final String[] alphabet = {};
	
    /**
     * Méthode de recherche linéaire afin de trouver l'indice 
     * d'un élément dans un tableau
     * @param alphabet 	tableau sur lequel on cherche l'indice
     * 				   	d'un élément
     * @param lettre 	lettre à qui on cherche l'indice
     * @return 
     */
    public static int trouverLettre(int alphabet[], int lettre) { 
       
    	if (alphabet == null) { 
            return -1; 
        }
    	
    	int indice = -1;
        int longueur = alphabet.length; 
        
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
