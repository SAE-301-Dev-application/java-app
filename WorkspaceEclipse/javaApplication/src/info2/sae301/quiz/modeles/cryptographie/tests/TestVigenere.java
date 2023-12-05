package info2.sae301.quiz.modeles.cryptographie.tests;

import static info2.sae301.quiz.modeles.cryptographie.Vigenere.*;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestVigenere {
	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.cryptographie.Vigenere#genererCle()}.
	 */
	@Test
	void testGenererCle() {
		for (int i = 1; i <= 100; i++) {
			String cle = genererCle();
			assertTrue(cle.length() >= 40);
			assertTrue(cle.length() <= 70); 
		}
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.cryptographie.Vigenere#getCle()}.
	 */
	@Test
	void testGetCle() {
		setCle("abceeeeeeeeeeeeeeeeeeeeeeeeeeeeeeedefghrr");
		assertEquals("abceeeeeeeeeeeeeeeeeeeeeeeeeeeeeeedefghrr",getCle());
	}
	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.cryptographie.Vigenere#setCle()}.
	 */
	@Test
	void testSetCle() {
		assertThrows(IllegalArgumentException.class, () -> { 
			setCle("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		});
		
		assertThrows(IllegalArgumentException.class, () -> {
			setCle("ert");
		});
	}
	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.cryptographie.Vigenere#chiffrer(String,String)}.
	 */
	@Test
	void testChiffrer() {
		assertEquals(chiffrer("bonsoir", "bonsoir"),"Ê⁸⁴,⁸S£");
		assertEquals(chiffrer("aaaaaaaaaaaaa", "bâtiments"),"CÄUÌNÉÑUTCÄUÌ");
		assertEquals(chiffrer("if (allOk) {\n\treturn allOk;\n}", "sxdcfvgbnhj:;,nb"),"⁷/}¤G`Ûp1ÇÆ	⁺+	H;æYP!WÛÖ6ÛÌ-`");
		assertEquals(chiffrer("589653258796524877965896", "Loic m'a fait perdre une heure"),"!æ; R!S⁵U=⁹£èœc[è#ë#Rgã#");
		assertEquals(chiffrer("&é/*-\".<>,;:!?/§'(-è_çà)=", "Mon Simon est mon bro"),"DÙÇWHäïÑm9Cpr⁸CNè0{0hœÔH5");
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.cryptographie.Vigenere#dechiffrer(String,String)}.
	 */
	@Test
	void testDechiffrer() {
		assertEquals(dechiffrer("Ê⁸⁴,⁸S£", "bonsoir"),"bonsoir");
		assertEquals(dechiffrer("CÄUÌNÉÑUTCÄUÌ", "bâtiments"),"aaaaaaaaaaaaa");
		assertEquals(dechiffrer("⁷/}¤G`Ûp1ÇÆ	⁺+	H;æYP!WÛÖ6ÛÌ-`", "sxdcfvgbnhj:;,nb"),"if (allOk) {\n\treturn allOk;\n}");
		assertEquals(dechiffrer("!æ; R!S⁵U=⁹£èœc[è#ë#Rgã#", "Loic m'a fait perdre une heure"),"589653258796524877965896");
		assertEquals(dechiffrer("DÙÇWHäïÑm9Cpr⁸CNè0{0hœÔH5", "Mon Simon est mon bro"),"&é/*-\".<>,;:!?/§'(-è_çà)=");
	}
	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.cryptographie.Vigenere#chiffrerCle(int)}.
	 */
	@Test
	void testChiffrerCle() {
		setCle("germaine12duRodez#lameilleuremamieEnCostumeDumonde");
		assertEquals(chiffrerCle(2563),"(%C.¹_:%îï-Eb>-%Ìq?¹.%_??%EC%.¹._%⁻:⁸>ÇDE.%⁺E.>:-%");
		assertEquals(chiffrerCle(2601),"¨{èà⁷¤â{NÑ#fÈã#{lÛa⁷à{¤aa{fè{à⁷à¤{'â\nãêëfà{\"fàãâ#{");
		assertEquals(chiffrerCle(444),"ÏFÛÔÆKÖF67ËWùŒËF⁴@ÒÆÔFKÒÒFWÛFÔÆÔKFëÖéŒÜVWÔFêWÔŒÖËF");
		assertEquals(chiffrerCle(999999),"6z?&t8\"zçdÿ!¤(ÿzCk\nt&z8\n\nz!?z&t&8zZ\"X(.:!&zŸ!&(\"ÿz");
		assertEquals(chiffrerCle(7),"2X=⁻Q⁴/XÆBW¤]\tWXäÎ⁺Q⁻X⁴⁺⁺X¤=X⁻Q⁻⁴Xw/û\t¨£¤⁻Xv¤⁻	/WX");
		
		
	}
	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.cryptographie.Vigenere#dechiffrerCle(int)}.
	 */
	@Test
	void testDechiffrerCle() {
		setCle("(%C.¹_:%îï-Eb>-%Ìq?¹.%_??%EC%.¹._%⁻:⁸>ÇDE.%⁺E.>:-%");
		assertEquals(dechiffrerCle(2563),"germaine12duRodez#lameilleuremamieEnCostumeDumonde");
		
		setCle("¨{èà⁷¤â{NÑ#fÈã#{lÛa⁷à{¤aa{fè{à⁷à¤{'â\nãêëfà{\"fàãâ#{");
		assertEquals(dechiffrerCle(2601),"germaine12duRodez#lameilleuremamieEnCostumeDumonde");
		
		setCle("ÏFÛÔÆKÖF67ËWùŒËF⁴@ÒÆÔFKÒÒFWÛFÔÆÔKFëÖéŒÜVWÔFêWÔŒÖËF");
		assertEquals(dechiffrerCle(444),"germaine12duRodez#lameilleuremamieEnCostumeDumonde");
		
		setCle("6z?&t8\"zçdÿ!¤(ÿzCk\nt&z8\n\nz!?z&t&8zZ\"X(.:!&zŸ!&(\"ÿz");
		assertEquals(dechiffrerCle(999999),"germaine12duRodez#lameilleuremamieEnCostumeDumonde");
		
		setCle("2X=⁻Q⁴/XÆBW¤]\tWXäÎ⁺Q⁻X⁴⁺⁺X¤=X⁻Q⁻⁴Xw/û\t¨£¤⁻Xv¤⁻	/WX");
		assertEquals(dechiffrerCle(7),"germaine12duRodez#lameilleuremamieEnCostumeDumonde");
	}
}
