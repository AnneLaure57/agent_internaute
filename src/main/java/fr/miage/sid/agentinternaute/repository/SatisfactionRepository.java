package fr.miage.sid.agentinternaute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.miage.sid.agentinternaute.entity.Satisfaction;

/**
 * @author Louis MASSICARD (user name : louis)
 * @version 
 * @since %G% - %U% (%I%)
 *
 */
public interface SatisfactionRepository extends JpaRepository<Satisfaction, String> {

	List<Satisfaction> findByProfileId(Integer profileId);
}