package info2.sae301.quiz.serialisation.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import info2.sae301.quiz.modeles.Jeu;

class TestSerialisationSamSam {

	@BeforeAll
	void setUpBeforeClass() {
		Jeu jeu = new Jeu();
		jeu.creerCategorie("Florian est si beau");
		jeu.creerCategorie("Simon est si incroyable");
		
		jeu.creerQuestion("Jonthan, tu est tout palichon ?", "vrai", new String[] {"non", "2eme non"}, 3, "", "Simon est si incroyable");
	}
	
	@Test
	void testSerialiser() {
		
	}

}
