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
		setCle("sxdcfvgbnhj:;,nb");
		assertEquals(chiffrer("if (allOk) {\n\treturn allOk;\n}"),"⁷/}¤G`Ûp1ÇÆ&-⁺	H.æYP§WÛÖ6Ûì⁻_");
		setCle("Loic m'a fait perdre une heure");
		assertEquals(chiffrer("589653258796524877965896"),"§æ. R§S⁵U=⁹£èœc[è#ë#Rgã#");
		setCle("Mon Simon est mon bro");
		assertEquals(chiffrer("&é/*-\".<>,;:!?/§'(-è_çà)="),"DÙÇWHäJñN⁹cQS9Cnè0{0hœÔH5");
	}

	@Test
	void testDechiffrer() {
		setCle("bonsoir");
		assertEquals(dechiffrer("Ê⁸⁴?⁸S£"),"bonsoir");
		
		setCle("bâtiments");
		assertEquals(dechiffrer("CÄUÌNÉÑUTCÄUÌ"),"aaaaaaaaaaaaa");
		
		setCle("sxdcfvgbnhj:;,nb");
		assertEquals(dechiffrer("⁷/}¤G`Ûp1ÇÆ&-⁺	H.æYP§WÛÖ6Ûì⁻_"),"if (allOk) {\n\treturn allOk;\n}");
		
		setCle("Loic m'a fait perdre une heure");
		assertEquals(dechiffrer("§æ. R§S⁵U=⁹£èœc[è#ë#Rgã#"),"589653258796524877965896");
		
		setCle("Mon Simon est mon bro");
		assertEquals(dechiffrer("DÙÇWHäJñN⁹cQS9Cnè0{0hœÔH5"),"&é/*-\".<>,;:!?/§'(-è_çà)=");
	}
	
	@Test
	void testTrouverLettre() {
		assertTrue(0 == trouverLettre(dictionnaire, 'A'));
		char[] alphabetVide = {};
		assertTrue(-1 == trouverLettre(alphabetVide,'A'));
		assertTrue(-1 == trouverLettre(null,'C'));
		
		assertFalse(55 == trouverLettre(dictionnaire, '¨'));
		assertTrue(55 == trouverLettre(dictionnaire,'ñ'));
		assertFalse(3 == trouverLettre(dictionnaire, '¶'));
	}

}
