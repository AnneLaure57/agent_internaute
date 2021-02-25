package fr.miage.sid.agentinternaute.api;

import java.util.logging.Logger;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.miage.sid.agentinternaute.service.ProfileService;
import jade.core.AID;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin
public class SearchController {
	
	private static final Logger LOGGER = Logger.getLogger(SearchController.class.getName());

	private final SearchController service;


}
