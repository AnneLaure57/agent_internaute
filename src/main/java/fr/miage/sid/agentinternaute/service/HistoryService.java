package fr.miage.sid.agentinternaute.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.entity.History;
import fr.miage.sid.agentinternaute.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {
	private final HistoryRepository repo;

	public Iterable<History> findPaged(int page, int size) {
		if (page < 0) page = 0;

		return repo.findAll(PageRequest.of(page, size)).getContent();
	}
}
