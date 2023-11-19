/**
 * TestSerialisation.java									        19 nov. 2023
 * IUT de Rodez, pas de copyright ni de "copyleft".
 */

package info2.sae301.quiz.serialisation.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import info2.sae301.quiz.modeles.Jeu;
import info2.sae301.quiz.serialisation.Serialisation;

import static info2.sae301.quiz.serialisation.Serialisation.*;

/**
 * Classe de test de "TestSerialisation.java"
 * @author FABRE Florian, LACAM Samuel
 */
class TestSerialisation {
	
	/** Instance de Jeu qui sera sérialisé et qui a été modifié*/
	private static Jeu jeuSerialiseModifie;
	
	/** Instance de Jeu qui sera sérialisé et qui n'a pas été modifié*/
	private static Jeu jeuSerialiseNonModifie;
	
	/** Chemin du dossier pour les sauvegardes */
	private static final String CHEMIN_DOSSIER
	= Serialisation.CHEMIN_DOSSIER;
	
	/** Nom du fichier de sauvegarde des données */
	private static final String FICHIER_SAUVEGARDE = "testDonneesModifiees.ser";
	
	/** Nom du fichier de sauvegarde des données non modifiées */
	private static final String FICHIER_SAUVEGARDE_VIDE = "testDonneesNonModifiees.ser";
	
	@BeforeEach
	void init() throws Exception {
		jeuSerialiseModifie = new Jeu();
		jeuSerialiseModifie.creerCategorie("Florian");
		jeuSerialiseModifie.creerCategorie("Simon");
		
		jeuSerialiseModifie
		.creerQuestion("Jonathan, tu est tout palichon ?", "vrai", 
				       new String[] {"non", "2eme non"}, 3, "", "Simon");
		jeuSerialiseModifie
		.creerQuestion("Question2", "C'est Vrai", 
				       new String[] {"non", "peut être"}, 3, 
				       "c'est cette réponse car...", "Simon");
		
		jeuSerialiseNonModifie = new Jeu();
	}

	
	@Test
	void testSerialiser() { 
		serialiser(jeuSerialiseModifie, FICHIER_SAUVEGARDE);
		String cheminFichier = CHEMIN_DOSSIER + FICHIER_SAUVEGARDE;
		File fichier = new File(cheminFichier);
		assertTrue(fichier.exists());
		
		serialiser(jeuSerialiseNonModifie, FICHIER_SAUVEGARDE_VIDE);
		String cheminFichierSansModif = CHEMIN_DOSSIER + FICHIER_SAUVEGARDE_VIDE;
		File fichierAucuneModif = new File(cheminFichierSansModif);
		assertTrue(fichierAucuneModif.exists());
	}

	
	@Test
	void testDeserialiser() {
		assertEquals(jeuSerialiseModifie,
				     Serialisation.deserialiser(FICHIER_SAUVEGARDE));
		assertEquals(jeuSerialiseNonModifie,
				     Serialisation.deserialiser(FICHIER_SAUVEGARDE_VIDE));
	}
}
