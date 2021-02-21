package fr.miage.sid.agentinternaute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.miage.sid.agentinternaute.entity.Person;

public interface PersonRepository extends JpaRepository<Person, String> {

}
