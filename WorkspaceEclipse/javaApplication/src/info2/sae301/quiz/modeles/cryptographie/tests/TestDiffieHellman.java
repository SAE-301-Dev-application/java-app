/**
 * 
 */
package info2.sae301.quiz.modeles.cryptographie.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import info2.sae301.quiz.modeles.cryptographie.DiffieHellman;

/**
 * 
 */
class TestDiffieHellman {
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void init(){
		
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.cryptographie.DiffieHellman#getModulo()}.
	 */
	@Test
	void testGetModulo() {
		assertEquals(DiffieHellman.getModulo(), 6301);
		
	}
	
	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.cryptographie.DiffieHellman#getGenerateur()}.
	 */
	@Test
	void testGetGenerateur() {
		assertEquals(DiffieHellman.getGenerateur(), 2711);
		
	}
	
	
	/**
	 * Test method for {@link info2.sae301.quiz.modeles.cryptographie.DiffieHellman#getPuissanceSecrete()}.
	 */
	@Test
	void testGetPuissanceSecrete() {
		DiffieHellman.setPuissanceSecrete(0);
		assertEquals(DiffieHellman.getPuissanceSecrete(), 0);
		
		DiffieHellman.setPuissanceSecrete(1000);
		assertEquals(DiffieHellman.getPuissanceSecrete(), 1000);
		
		DiffieHellman.setPuissanceSecrete(999999);
		assertEquals(DiffieHellman.getPuissanceSecrete(), 999999);
		
		DiffieHellman.setPuissanceSecrete(589235);
		assertEquals(DiffieHellman.getPuissanceSecrete(), 589235);
		
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.cryptographie.DiffieHellman#getcleRecue()}.
	 */
	@Test
	void testGetCleRecue() {
		DiffieHellman.setCleRecue(0);
		assertEquals(DiffieHellman.getCleRecue(), 0);
		
		DiffieHellman.setCleRecue(1000);
		assertEquals(DiffieHellman.getCleRecue(), 1000);
		
		DiffieHellman.setCleRecue(999999);
		assertEquals(DiffieHellman.getCleRecue(), 999999);
		
		DiffieHellman.setCleRecue(589235);
		assertEquals(DiffieHellman.getCleRecue(), 589235);
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.cryptographie.DiffieHellman#setPuissanceSecrete(int)}.
	 */
	@Test
	void testSetPuissanceSecrete() {
		DiffieHellman.setPuissanceSecrete(0);
		assertEquals(DiffieHellman.getPuissanceSecrete(), 0);
		
		DiffieHellman.setPuissanceSecrete(1000);
		assertEquals(DiffieHellman.getPuissanceSecrete(), 1000);
		
		DiffieHellman.setPuissanceSecrete(999999);
		assertEquals(DiffieHellman.getPuissanceSecrete(), 999999);
		
		DiffieHellman.setPuissanceSecrete(589235);
		assertEquals(DiffieHellman.getPuissanceSecrete(), 589235);
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.cryptographie.DiffieHellman#setcleRecue(int)}.
	 */
	@Test
	void testSetCleRecue() {
		DiffieHellman.setCleRecue(0);
		assertEquals(DiffieHellman.getCleRecue(), 0);
		
		DiffieHellman.setCleRecue(1000);
		assertEquals(DiffieHellman.getCleRecue(), 1000);
		
		DiffieHellman.setCleRecue(999999);
		assertEquals(DiffieHellman.getCleRecue(), 999999);
		
		DiffieHellman.setCleRecue(589235);
		assertEquals(DiffieHellman.getCleRecue(), 589235);
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.cryptographie.DiffieHellman#genererPuissance()}.
	 */
	@Test
	void testGenererPuissance() {
		int puissanceTest;
		for (int i = 0; i < 10000; i++) {
			puissanceTest = DiffieHellman.genererPuissance();
			assertTrue(100000 <= puissanceTest && puissanceTest <= 10000007);
		}
		
	}

	/**
	 * Test method for {@link info2.sae301.quiz.modeles.cryptographie.DiffieHellman#puissanceNR(int, int)}.
	 */
	@Test
	void testPuissanceNR() {
		int cleTest;
		int puissanceTest;
		for (int i = 0; i < 10000; i++) {
			puissanceTest = DiffieHellman.genererPuissance();
			cleTest = DiffieHellman.puissanceNR(DiffieHellman.getGenerateur(), puissanceTest);
			assertTrue(0 <= cleTest && cleTest <= DiffieHellman.getModulo());
		}
	}

}
