package fr.miage.sid.agentinternaute.dto;

import java.util.List;

import fr.miage.sid.agentinternaute.entity.Actor;
import fr.miage.sid.agentinternaute.entity.Artist;
import fr.miage.sid.agentinternaute.entity.Director;
import fr.miage.sid.agentinternaute.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
	
	private Double price;
	private String itemType;
	private Integer itemId;
	private String titre;
	private String description;
	private Integer dateSortie;
	private Double note;
	private String distributeur;
	private String producteur;
	private List<Genre> genres;
	private List<Actor> acteurs;
	private List<Director> realisateurs;
	private List<Artist> artistes;
	private Integer profileId;
}
