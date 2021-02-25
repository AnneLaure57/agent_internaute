package fr.miage.sid.agentinternaute.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.miage.sid.agentinternaute.entity.History;

public interface HistoryRepository extends JpaRepository<History, String> {

}
