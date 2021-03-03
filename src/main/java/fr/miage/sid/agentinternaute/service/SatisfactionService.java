package fr.miage.sid.agentinternaute.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.dto.PurchaseDTO;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.entity.Purchase;
import fr.miage.sid.agentinternaute.entity.Satisfaction;
import fr.miage.sid.agentinternaute.repository.SatisfactionRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Louis MASSICARD (user name : louis)
 * @version 
 * @since %G% - %U% (%I%)
 *
 */
@Service
@RequiredArgsConstructor
public class SatisfactionService {
	/* ========================================= Global ================================================ */ /*=========================================*/

	private final Logger LOGGER = Logger.getLogger(SatisfactionService.class.getName());

	/* ========================================= Attributs ============================================= */ /*=========================================*/

	private final SatisfactionRepository repo;
	
	/* ========================================= Methodes ============================================== */ /*=========================================*/

	public Satisfaction createSatisfaction(Profile profile, Double meanSatisfaction) {
		// Persist in db
		Satisfaction satisfaction = new Satisfaction(profile, meanSatisfaction);
		repo.save(satisfaction);
		return satisfaction;
	}
	
	public List<Satisfaction> findSatisfactionsProfile(Integer profileID) {
		LOGGER.info("Service return all satisfactions for profileID : " + profileID);
		return repo.findByProfileId(profileID);
	}
}
