package info2.sae301.quiz.modeles.cryptographie.tests;

import static info2.sae301.quiz.modeles.cryptographie.Vigenere.*;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestVigenere {
	
	@Test
	void testGenererCle() {
		for (int i = 1; i <= 100; i++) {
			String cle = genererCle();
			assertTrue(cle.length() >= 40);
			assertTrue(cle.length() <= 70); 
		}
	}

	@Test
	void testGetCle() {
		setCle("abceeeeeeeeeeeeeeeeeeeeeeeeeeeeeeedefghrr");
		assertEquals("abceeeeeeeeeeeeeeeeeeeeeeeeeeeeeeedefghrr",getCle());
	}
	
	@Test
	void testSetCle() {
		assertThrows(IllegalArgumentException.class, () -> { 
			setCle("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		});
		
		assertThrows(IllegalArgumentException.class, () -> { 
			setCle("ert");
		});
	}
	
	@Test
	void testChiffrer() {
		assertEquals(chiffrer("bonsoir", "bonsoir"),"Ê⁸⁴,⁸S£");
		assertEquals(chiffrer("aaaaaaaaaaaaa", "bâtiments"),"CÄUÌNÉÑUTCÄUÌ");
		assertEquals(chiffrer("if (allOk) {\n\treturn allOk;\n}", "sxdcfvgbnhj:;,nb"),"⁷/}¤G`Ûp1ÇÆ	⁺+	H;æYP!WÛÖ6ÛÌ-`");
		assertEquals(chiffrer("589653258796524877965896", "Loic m'a fait perdre une heure"),"!æ; R!S⁵U=⁹£èœc[è#ë#Rgã#");
		assertEquals(chiffrer("&é/*-\".<>,;:!?/§'(-è_çà)=", "Mon Simon est mon bro"),"DÙÇWHäïÑm9Cpr⁸CNè0{0hœÔH5");
	}

	@Test
	void testDechiffrer() {
		assertEquals(dechiffrer("Ê⁸⁴,⁸S£", "bonsoir"),"bonsoir");
		assertEquals(dechiffrer("CÄUÌNÉÑUTCÄUÌ", "bâtiments"),"aaaaaaaaaaaaa");
		assertEquals(dechiffrer("⁷/}¤G`Ûp1ÇÆ	⁺+	H;æYP!WÛÖ6ÛÌ-`", "sxdcfvgbnhj:;,nb"),"if (allOk) {\n\treturn allOk;\n}");
		assertEquals(dechiffrer("!æ; R!S⁵U=⁹£èœc[è#ë#Rgã#", "Loic m'a fait perdre une heure"),"589653258796524877965896");
		assertEquals(dechiffrer("DÙÇWHäïÑm9Cpr⁸CNè0{0hœÔH5", "Mon Simon est mon bro"),"&é/*-\".<>,;:!?/§'(-è_çà)=");
	}
}
