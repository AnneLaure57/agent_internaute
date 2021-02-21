package fr.miage.sid.agentinternaute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.miage.sid.agentinternaute.entity.Music;

public interface MusicRepository extends JpaRepository<Music, String> {

}
