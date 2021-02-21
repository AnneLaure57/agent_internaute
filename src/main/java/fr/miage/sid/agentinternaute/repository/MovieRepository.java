package fr.miage.sid.agentinternaute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.miage.sid.agentinternaute.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, String> {

}
