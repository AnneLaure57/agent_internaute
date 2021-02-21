package fr.miage.sid.agentinternaute.controller;

import java.util.logging.Logger;

import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.miage.sid.agentinternaute.entity.History;
import fr.miage.sid.agentinternaute.service.HistoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(History.class)
@RequiredArgsConstructor
public class HistoryController {

	private static final Logger LOGGER = Logger.getLogger(HistoryController.class.getName());

	private final HistoryService service;

	@GetMapping
    public Iterable<History> getAllHistory(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "20") int size) {

        return service.findPaged(page, size);
    }
	
	@PostMapping
    public History create(@RequestBody History h) {
        return null;
    }

    @PutMapping(value = "/{id}")
    public History update(@RequestBody History h, @PathVariable int id) {
        return null;
    }
}
