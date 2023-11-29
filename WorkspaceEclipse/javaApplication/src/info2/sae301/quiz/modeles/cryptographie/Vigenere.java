/*
 * Cryptographie.java									            20 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.modeles.cryptographie;

import static info2.sae301.quiz.modeles.Dictionnaire.*;

import java.util.Random;

import info2.sae301.quiz.modeles.Dictionnaire;

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
			int rnd = new Random().nextInt(getDictionnaire().size());
		    cleGeneree += getDictionnaire().get(rnd);
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
			int indiceCaractere;
			char caractereC;
			
			indiceCaractere = (getDictionnaireReversed().get(message.charAt(i))
					+ getDictionnaireReversed().get(cle.charAt(i%cle.length())))
					% getDictionnaire().size();
			
			caractereC = getDictionnaire().get(indiceCaractere);
			messageC += caractereC;
		}
		return messageC;
	}
	
	
	/**
	 * Chiffre la cle par Diffie-Hellman
	 * 
	 * @param entierSecret la clé de chiffrement
	 * @return la clé de Vigenère chiffrée
	 */
	public static String chiffrerCle(int entierSecret) {
    	String cleChiffree = "";
    	
    	int tailleDictionnaire = getDictionnaire().size();
    	
		for (int i = 0; i < cle.length(); i++) {
			int indiceCaractere;
			char caractereC;
			
			indiceCaractere
			= (getDictionnaireReversed().get(cle.charAt(i))
			   + getDictionnaire().get((entierSecret) % tailleDictionnaire))
			  % tailleDictionnaire;
			
			caractereC = getDictionnaire().get(indiceCaractere);
			cleChiffree += caractereC;
		}
		return cleChiffree;
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
			int indiceCaractere;
			char caractereD;
			
			indiceCaractere = (getDictionnaireReversed().get(messageC.charAt(i))
					- getDictionnaireReversed().get(cle.charAt(i%cle.length())))
					% getDictionnaire().size();
			
			caractereD = indiceCaractere < 0 ? 
					getDictionnaire().get(indiceCaractere + getDictionnaire().size()) 
							: getDictionnaire().get(indiceCaractere);
			messageD += caractereD;
		}
		return messageD;
	}
	
	
	/**
	 * Déchiffre une cle chiffrée par Diffie-Hellman
	 * 
	 * @param entierSecret la clé de déchiffrement
	 * @return la cle de Vigenère décryptée
	 */
	public static String dechiffrerCle(int entierSecret) {
		String cleDechiffree = "";
		
		int tailleDictionnaire = getDictionnaire().size();
		
		for (int i = 0; i < cle.length(); i++) {
			int indiceCaractere;
			char caractereDechiffre;
			
			indiceCaractere 
			= (getDictionnaireReversed().get(cle.charAt(i))
			   - getDictionnaire().get((entierSecret) % tailleDictionnaire))
			  % tailleDictionnaire;
			
			caractereDechiffre
			= indiceCaractere < 0
			  ? getDictionnaire().get(indiceCaractere + tailleDictionnaire) 
			  : getDictionnaire().get(indiceCaractere);
			
			cleDechiffree += caractereDechiffre;
		}
		return cleDechiffree;
	}
}