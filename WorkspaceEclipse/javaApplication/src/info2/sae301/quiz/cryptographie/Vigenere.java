/*
 * Cryptographie.java									            20 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.cryptographie;

import java.util.HashMap;
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
public class Vigenere {
	
	/** Constante pour la taille minimale d'une clé */
	final static int TAILLE_MIN_CLE = 40;
	
	/** Constante pour la taille maximale  d'une clé */
	final static int TAILLE_MAX_CLE = 70;
	
	/** Texte d'erreur pour une taille invalide de la clé rentrée */
	final static String TAILLE_INVALIDE 
			= "La taille doit être comprise entre %s et %s caractères";
	
	/** Tableau permettant de remplir les HashMap. */
	public final static char[] dicoString = {
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
        // Chiffres et opérateurs
        '0', '⁰', '1', '¹', '2', '²', '3', '4', '⁴', '5', '⁵', '6', '⁶', '7', '⁷',
        '8', '⁸', '9', '⁹', '+', '⁺', '-', '⁻', '%', '/', '*',
        // Caractères spéciaux
        ' ', '\n', '\t', '&', '~', '"', '#', '\'', '{', '(', '[', '|',
        '`', '_', '\\', '@', ')', ']', '=', '}', '¨', '^', '£', '$',
        '¤', '?', ',', '.', ';', ':', '§', '!', '<', '>'
    };
	
	/**
	 * Dictionnaire permettant de récupérer les lettres 
	 * en fonction des indices
	 */
	public final static HashMap<Integer,Character> dictionnaire 
								= new HashMap<>();
	
	/**
	 * Dictionnaire permettant de récupérer les indices des lettres
	 * en fonction de leur place dans la HashMap
	 */
	public final static HashMap<Character,Integer> dictionnaireReversed 
								= new HashMap<>();
	
	/**
	 * Initialise les HashMap pour le dictionnaire
	 */
	static {
		for (int i = 0; i < dicoString.length;i++) {
			dictionnaire.put(i,dicoString[i]);
			dictionnaireReversed.put(dicoString[i],i);
		};
    }
		
	/** Clé pour Vigenère */
	private static String cle = genererCle();
	
	
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
			int rnd = new Random().nextInt(dictionnaire.size());
		    cleGeneree += dictionnaire.get(rnd);
		}
		return cleGeneree;
	}
	
	
	/**
	 * Récupère l'attribut 'cle'
	 * @return cle la clé de Vigenère
	 */
	public static String getCle() {
		return cle;
	}


	/**
	 * Setter de la clé de Vigenère
	 * @param cle
	 */
	public static void setCle(String cle) {
		if (cle.length() < TAILLE_MIN_CLE || cle.length() > TAILLE_MAX_CLE) {
			throw new IllegalArgumentException(String.format(TAILLE_INVALIDE, 
												TAILLE_MIN_CLE,TAILLE_MAX_CLE));
		}
		Vigenere.cle = cle;
	}


	/**
	 * Chiffre un message selon la clé générée plus tôt
	 * 
	 * @param message message à crypter
	 * @return le message crypté
	 */
	public static String chiffrer(String message, String cle) {
		String messageC = "";
		for (int i = 0; i < message.length(); i++) {
			int nbCaractere;
			char caractereC;
			
			nbCaractere = (dictionnaireReversed.get(message.charAt(i))
					+ dictionnaireReversed.get(cle.charAt(i%cle.length())))
					% dictionnaire.size();
			
			caractereC = dictionnaire.get(nbCaractere);
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
	public static String dechiffrer(String messageC, String cle) {
		String messageD = "";
		for (int i = 0; i < messageC.length(); i++) {
			int nbCaractere;
			char caractereD;
			
			nbCaractere = (dictionnaireReversed.get(messageC.charAt(i))
					- dictionnaireReversed.get(cle.charAt(i%cle.length())))
					% dictionnaire.size();
			
			caractereD = nbCaractere < 0 ? 
					dictionnaire.get(nbCaractere + dictionnaire.size()) 
							: dictionnaire.get(nbCaractere);
			messageD += caractereD;
		}
		return messageD;
	}
}