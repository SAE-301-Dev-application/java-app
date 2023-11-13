/**
 * TestSerialisation.java									9 nov. 2023v
 * IUT de Rodez, no copyright ni "copyleft"
 */
package info2.sae301.quiz.serialisation.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	
	
	/* Instance de jeu qui ne sera sérialisé*/
	private static Jeu jeuNonSerialise;
	
	@BeforeEach
	void setUp() throws Exception {
		jeuSerialiseModifie = new Jeu();
		jeuSerialiseModifie.creerCategorie("Florian");
		jeuSerialiseModifie.creerCategorie("Simon");
		
		jeuSerialiseModifie.creerQuestion("Jonathan, tu est tout palichon ?", "vrai", 
				new String[] {"non", "2eme non"}, 3, "", "Simon");
		jeuSerialiseModifie.creerQuestion("Question2", "C'est Vrai", 
				new String[] {"non", "peut être"}, 3, 
				"c'est cette réponse car...", "Simon");
		
		jeuSerialiseNonModifie = new Jeu();
		jeuNonSerialise = new Jeu();
	}

	
	@Test
	void testSerialiser() { 
		serialiser(jeuSerialiseModifie, "01.ser");
		String cheminFichier = "../sauvegarde/01.ser";
		File file = new File(cheminFichier);
		assertTrue(file.exists());
		
		serialiser(jeuSerialiseNonModifie, "02.ser");
		String cheminFichier2 = "../sauvegarde/02.ser";
		File file2 = new File(cheminFichier2);
		assertTrue(file.exists());
	}

	
	@Test
	void testDeserialiser() {
		assertEquals(jeuSerialiseModifie, Serialisation.deserialiser("../sauvegarde/01.ser"));
		assertEquals(jeuSerialiseNonModifie, Serialisation.deserialiser("../sauvegarde/02.ser"));
		
	}
}
