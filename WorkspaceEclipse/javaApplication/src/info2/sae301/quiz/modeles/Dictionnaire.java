/*
 * Dictionnaire.java									28 nov. 2023
 * IUT de Rodez, no copyright ni "copyleft"
 */
package info2.sae301.quiz.modeles;

import java.util.HashMap;

/**
 * Dictionnaire de l'application permettant les vérifications de
 * caractères valides sur les questions ainsi que le chiffrement
 * de Vigenère
 * 
 * @author Florian Fabre
 * @author Loïc Faugières
 * @author Jonathan Guil
 * @author Simon Guiraud
 * @author Samuel Lacam
 */
public class Dictionnaire {
	
	/** Tableau permettant de remplir les HashMap. */
	private final static char[] DICO_STRING = {
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
        '¤', '?', ',', '.', ';', ':', '§', '!', '<', '>','…'
    };
	
	/**
	 * Dictionnaire permettant de récupérer les lettres 
	 * en fonction des indices
	 */
	private static HashMap<Integer,Character> dictionnaire 
								= new HashMap<>();
	
	/**
	 * Dictionnaire permettant de récupérer les indices des lettres
	 * en fonction de leur place dans la HashMap
	 */
	private static HashMap<Character,Integer> dictionnaireReversed 
								= new HashMap<>();
	
	/**
	 * Initialise les HashMap pour le dictionnaire
	 */
	static {
		for (int i = 0; i < DICO_STRING.length;i++) {
			dictionnaire.put(i,DICO_STRING[i]);
			dictionnaireReversed.put(DICO_STRING[i],i);
		};
    }

	/** @return the dicoString */
	public static char[] getDicoString() {
		return DICO_STRING;
	}

	/** @return the dictionnaire */
	public static HashMap<Integer, Character> getDictionnaire() {
		return dictionnaire;
	}

	/** @return the dictionnaireReversed */
	public static HashMap<Character, Integer> getDictionnaireReversed() {
		return dictionnaireReversed;
	}
}
