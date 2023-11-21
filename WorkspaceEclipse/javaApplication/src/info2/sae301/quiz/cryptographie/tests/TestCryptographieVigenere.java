package info2.sae301.quiz.cryptographie.tests;

import static info2.sae301.quiz.cryptographie.CryptographieVigenere.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestCryptographieVigenere {

	static String phrase1, phrase2, phrase3, phrase4, phrase5;
	
	
	static String[] phrases = {phrase1, phrase2, phrase3, phrase4, phrase5};
	
	@BeforeEach
	void setUp() throws Exception {
		phrase1 = "Je remercie Loïc pour ce bug !";
		phrase2 = "Pour rétrospective: revérifier le data qu'un collègue donne";
		phrase3 = "Numéro de téléphone (BUT informatique de Rodez) : 05 65 77 15 62.";
		phrase4 = "try {\n\tif (data ok) {\n\t\treturn ok;\n\t}\n"
				+ "catch(e LoicException){\n\tLoïc.pv = 0;\n\tverifierData();\n}";
		phrase5 = "'0', '⁰', '1', '¹', '2', '²', '3', '4', '⁴', '5', '⁵', '6', '⁶', '7', '⁷',\n"
				+ "'8', '⁸', '9', '⁹', '+', '⁺', '-', '⁻', '%', '/', '*'";
	}

	@Test
	void testGenererCle() {
		for (int i = 1; i <= 100; i++) {
			String cle = genererCle();
			assertTrue(cle.length() >= 40);
			assertTrue(cle.length() <= 70); 
		}
	}

	@Test
	void testChiffrer() {
		setCle("bonsoir");
		assertEquals(chiffrer("bonsoir"),"Ê⁸⁴?⁸S£");
		setCle("bâtiments");
		assertEquals(chiffrer("aaaaaaaaaaaaa"),"CÄUÌNÉÑUTCÄUÌ");
		setCle("anticonstitutionnellement");
		assertEquals(chiffrer(phrases[0]),"jSÏ⁶I⁵S¤YS0J\nŸ1PDX⁴~ŸÇISÏC{⁶ÀB");
		setCle("Loic m'a fait perdre une heure");
		assertEquals(chiffrer(phrases[1]),"⁹⁸⁹Ww :UÌWT⁴0@£ÒaÎP^Ì1)JÌRN⁹ŸŸb⁴Ò@}NlÀw2Ùã!DHI#P/K<>S^}YS{Ÿ");
		setCle("Mon Simon est mon bro");
		assertEquals(chiffrer(phrases[2]),"2`3$$ŸÇTSw00\t$⁻Y⁶DHÌfÖ|\"w7XÛ⁸\tÇÉ.⁸I#UD}HÌ\"5TSÖñÀKÉ,R^èèwàãDõ⁻Ìä.M");
		setCle("bâtiments");
		assertEquals(chiffrer(phrases[3]),"CÄUÌNÉÑUTCÄUÌ");
		setCle("bâtiments");
		assertEquals(chiffrer(phrases[4]),"CÄUÌNÉÑUTCÄUÌ");
		
	}

	@Test
	void testDechiffrer() {
		fail("Not yet implemented");
	}

	@Test
	void testTrouverLettre() {
		fail("Not yet implemented");
	}

}
