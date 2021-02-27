package fr.miage.sid.agentinternaute.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

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
	
	private static final Logger LOGGER = Logger.getLogger(SearchServiceTest.class.getName());

	/* ========================================= Attributs ============================================= */ /*=========================================*/

	private SearchService service;
	
	/* ========================================= Constructeurs ========================================= */ /*=========================================*/

	public void TestMockito(@Mock SearchService service) {
		LOGGER.info("Init SearchService Mock.");
		this.service = service;
	}
	
	/* ========================================= Methodes ============================================== */ /*=========================================*/

	/* ========================================= Accesseurs ============================================ */ /*=========================================*/

}
