package info2.sae301.quiz.cryptographie.tests;

import static info2.sae301.quiz.cryptographie.Vigenere.*;
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
		assertEquals(chiffrer("if (allOk) {\n\treturn allOk;\n}", "sxdcfvgbnhj:;,nb"),"⁷/}¤G`Ûp1çæ&-⁺\tH;BYP!WÛÖ6Ûì⁻_");
		assertEquals(chiffrer("589653258796524877965896", "Loic m'a fait perdre une heure"),"!B; r!s⁵u=⁹£ÊPÇ[Ê#F#rHÆ#");
		assertEquals(chiffrer("&é/*-\".<>,;:!?/§'(-è_çà)=", "Mon Simon est mon bro"),"dÙçwhÃJñN⁹cQS9cnÊ⁰{0IœÔh⁵");
	}

	@Test
	void testDechiffrer() {
		assertEquals(dechiffrer("Ê⁸⁴,⁸S£", "bonsoir"),"bonsoir");
		assertEquals(dechiffrer("CÄUÌNÉÑUTCÄUÌ", "bâtiments"),"aaaaaaaaaaaaa");
		assertEquals(dechiffrer("⁷/}¤G`Ûp1çæ&-⁺\tH;BYP!WÛÖ6Ûì⁻_", "sxdcfvgbnhj:;,nb"),"if (allOk) {\n\treturn allOk;\n}");
		assertEquals(dechiffrer("!B; r!s⁵u=⁹£ÊPÇ[Ê#F#rHÆ#", "Loic m'a fait perdre une heure"),"589653258796524877965896");
		assertEquals(dechiffrer("dÙçwhÃJñN⁹cQS9cnÊ⁰{0IœÔh⁵", "Mon Simon est mon bro"),"&é/*-\".<>,;:!?/§'(-è_çà)=");
	}
}
