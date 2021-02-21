package fr.miage.sid.agentinternaute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.miage.sid.agentinternaute.entity.TvShow;

public interface TvShowRepository extends JpaRepository<TvShow, String> {

}
