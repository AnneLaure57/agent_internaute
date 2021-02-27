package fr.miage.sid.agentinternaute.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Louis MASSICARD (user name : louis)
 * @version 
 * @since %G% - %U% (%I%)
 *
 */
@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}

	/* ========================================= Global ================================================ */ /*=========================================*/

	/* ========================================= Attributs ============================================= */ /*=========================================*/

	private SearchService service;
	
	/* ========================================= Constructeurs ========================================= */ /*=========================================*/

	public void TestMockito(@Mock SearchService service) {
		this.service = service;
	}
	
	/* ========================================= Methodes ============================================== */ /*=========================================*/

}
