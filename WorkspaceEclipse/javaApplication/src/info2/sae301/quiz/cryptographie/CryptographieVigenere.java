/*
 * Cryptographie.java									            20 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.cryptographie;

import java.util.Random;

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
	
	/** Constante pour la taille minimale d'une clé */
	final static int TAILLE_MIN_CLE = 40;
	
	/** Constante pour la taille maximale  d'une clé */
	final static int TAILLE_MAX_CLE = 70;
	
	/**
	 * Dictionnaire sur lequel on pourra crypter les caractères.
	 */
	public final static char[] dictionnaire = {
		// Lettres et accents
        'A', 'a', 'À', 'à', 'Â', 'â', 'Ä', 'ä', 'Ã', 'ã', 'Æ', 'æ',
        'B', 'b',
        'C', 'c', 'Ç', 'ç',
        'D', 'd',
        'E', 'e', 'É', 'é', 'È', 'è', 'Ê', 'ê', 'Ë', 'ë',
        'F', 'f',
        'G', 'g',
        'H', 'h',
        'I', 'i', 'Ì', 'ì', 'Î', 'î', 'Ï', 'ï',
        'J', 'j',
        'K', 'k',
        'L', 'l',
        'M', 'm',
        'N', 'n', 'Ñ', 'ñ',
        'O', 'o', 'Ò', 'ò', 'Ô', 'ô', 'Ö', 'ö', 'Õ', 'õ', 'Œ', 'œ',
        'P', 'p',
        'Q', 'q',
        'R', 'r',
        'S', 's',
        'T', 't',
        'U', 'u', 'Ù', 'ù', 'Û', 'û', 'Ü', 'ü',
        'V', 'v',
        'W', 'w',
        'X', 'x',
        'Y', 'y', 'Ÿ', 'ÿ',
        'Z', 'z',
        //Chiffres et opérateurs
        '0', '⁰', '1', '¹', '2', '²', '3', '4', '⁴', '5', '⁵', '6', '⁶', '7', '⁷',
        '8', '⁸', '9', '⁹', '+', '⁺', '-', '⁻', '%', '/', '*',
        //Caractères spéciaux
        ' ', '\n', '\t', '&', '~', '"', '#', '\'', '{', '(', '[', '|',
        '`', '_', '\\', '@', ')', ']', '=', '}', '¨', '^', '£', '$',
        '¤', 'µ', '?', ',', '.', ';', ':', '§', '!', '<', '>'
    };

	/** Clé pour Vigenère */
	static String cle = genererCle();
	
	
	/**
	 * Génère une clé pseudo-aléatoire de taille n
	 * 
	 * @return le clé de taille "taille" générée
	 */
	public static String genererCle() {
		String cleGeneree;
		cleGeneree = "";
		int tailleCle = new Random().nextInt(TAILLE_MAX_CLE - TAILLE_MIN_CLE + 1)
												+ TAILLE_MIN_CLE;
		for(int i = 0; i < tailleCle; i++) {
			int rnd = new Random().nextInt(dictionnaire.length);
		    cleGeneree += dictionnaire[rnd];
		}
		return cleGeneree;
	}
	
	
	/**
	 * Chiffre un message selon la clé générée plus tôt
	 * 
	 * @param message message à crypter
	 * @return le message crypté
	 */
	public static String chiffrer(String message) {
		String messageC = "";
		for (int i = 0; i < message.length(); i++) {
			int nbCaractere;
			char caractereC;
			
			nbCaractere = (trouverLettre(dictionnaire, message.charAt(i))
					+ trouverLettre(dictionnaire, cle.charAt(i%cle.length()))) 
					% dictionnaire.length;                          
			
			caractereC = dictionnaire[nbCaractere];
			messageC += caractereC;
		}
		return messageC;
	}
	
	
	/**
	 * Déchiffre un message selon la clé générée plus tôt
	 * 
	 * @param message messageC à décrypter
	 * @return le message décrypté
	 */
	public static String dechiffrer(String messageC) {
		String messageD = "";
		for (int i = 0; i < messageC.length(); i++) {
			int nbCaractere;
			char caractereD;
			
			nbCaractere = (trouverLettre(dictionnaire, messageC.charAt(i)) 
					- trouverLettre(dictionnaire, cle.charAt(i%cle.length()))) 
					% dictionnaire.length;
			
			caractereD = nbCaractere < 0 ? 
					dictionnaire[nbCaractere + dictionnaire.length] 
							: dictionnaire[nbCaractere];
			messageD += caractereD;
		}
		return messageD;
	}
	
	
    /**
     * Méthode de recherche linéaire afin de trouver l'indice 
     * d'un élément dans un tableau.
     * @param alphabet Tableau sur lequel on cherche l'indice d'un élément.
     * @param lettre   Lettre pour laquelle on cherche l'indice.
     * @return 
     */
	public static int trouverLettre(char[] alphabet, char lettre) {
	    int indice = -1;

	    if (alphabet == null || alphabet.length == 0) {
	        return indice;
	    }

	    for (int i = 0; i < alphabet.length; i++) {
	        if (Character.compare(alphabet[i], lettre) == 0) {
	            indice = i;
	        }
	    }
	    return indice;
	}
    
	
	public static void main(String[] args) {
	    String originalMessage = "Je suis le meilleur\" et simon est trop cool";
	    System.out.println("Original Message: " + originalMessage);

	    System.out.println("Generated Key: " + cle);

	    String encryptedMessage = chiffrer(originalMessage);
	    System.out.println("Encrypted Message: " + encryptedMessage);

	    String decryptedMessage = dechiffrer(encryptedMessage);
	    System.out.println("Decrypted Message: " + decryptedMessage);
	}

}
