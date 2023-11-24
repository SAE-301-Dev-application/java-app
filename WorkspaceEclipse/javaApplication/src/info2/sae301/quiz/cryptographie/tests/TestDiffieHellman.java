/**
 * 
 */
package info2.sae301.quiz.cryptographie.tests;

import info2.sae301.quiz.cryptographie.DiffieHellman;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	 * Test method for {@link info2.sae301.quiz.cryptographie.DiffieHellman#getPuissanceSecrete()}.
	 */
	@Test
	void testGetPuissanceSecrete() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link info2.sae301.quiz.cryptographie.DiffieHellman#getcleRecue()}.
	 */
	@Test
	void testGetcleRecue() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link info2.sae301.quiz.cryptographie.DiffieHellman#setPuissanceSecrete(int)}.
	 */
	@Test
	void testSetPuissanceSecrete() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link info2.sae301.quiz.cryptographie.DiffieHellman#setcleRecue(int)}.
	 */
	@Test
	void testSetcleRecue() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link info2.sae301.quiz.cryptographie.DiffieHellman#genererPuissance()}.
	 */
	@Test
	void testGenererPuissance() {
		int puissanceTest;
		for (int i = 0; i < 100; i++) {
			puissanceTest = DiffieHellman.genererPuissance();
			assertTrue(100000 <= puissanceTest && puissanceTest <= 10000007);
		}
		
	}

	/**
	 * Test method for {@link info2.sae301.quiz.cryptographie.DiffieHellman#puissanceNR(int, int)}.
	 */
	@Test
	void testPuissanceNR() {
		
	}

}
