package fr.miage.sid.agentinternaute.controller;

import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.miage.sid.agentinternaute.entity.Profile;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SearchController {

}
