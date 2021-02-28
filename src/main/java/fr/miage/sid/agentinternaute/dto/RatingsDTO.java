package fr.miage.sid.agentinternaute.dto;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingsDTO {
	private Integer purchaseId;
	private Integer itemId;
	private String itemType;
	private Double mediumRating;
    private HashMap<Integer, Double> distributorRating;
    private HashMap<Integer, Double> productorRating;
    private HashMap<Integer, Double> artistsRating;
    private HashMap<Integer, Double> actorsRating;
    private HashMap<Integer, Double> directorsRating;
}
