package fr.miage.sid.agentinternaute.api;

import java.util.logging.Logger;

import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.miage.sid.agentinternaute.entity.Purchase;
import fr.miage.sid.agentinternaute.service.PurchaseService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/purchases", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Purchase.class)
@RequiredArgsConstructor
@CrossOrigin 
public class PurchaseController {
	/* ========================================= Global ================================================ */ /*=========================================*/

	private static final Logger LOGGER = Logger.getLogger(PurchaseController.class.getName());

	/* ========================================= Attributs ============================================= */ /*=========================================*/

	private final PurchaseService service;

	/* ========================================= Constructeurs ========================================= */ /*=========================================*/
	
    /* ========================================= Methodes ============================================== */ /*=========================================*/

	@GetMapping
    public Iterable<Purchase> getAllHistory(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "20") int size) {
		LOGGER.info("GET on /purchases?page=0&size=20");

        return service.findPaged(page, size);
    }
	
	@PostMapping
    public Purchase create(@RequestBody Purchase h) {
		LOGGER.info("POST on /purchases");
        return null;
    }

    @PutMapping(value = "/{id}")
    public Purchase update(@RequestBody Purchase h, @PathVariable int id) {
    	LOGGER.info("PUT on /purchases/"+id);
        return null;
    }
	
	/* ========================================= Accesseurs ============================================ */ /*=========================================*/

	/* ========================================= Main ================================================== */ /*=========================================*/
}
