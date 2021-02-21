package fr.miage.sid.agentinternaute.repository;

import fr.miage.sid.agentinternaute.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, String> {

}
