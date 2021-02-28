package fr.miage.sid.agentinternaute.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private Integer id;
	private String itemId;
	private String itemType;
	private String itemTitle;
	private Date purchaseDate;

	// Notes de l'internaute
	private Double mediumRating;
	private HashMap<Integer, Double> distributorRating;
	private HashMap<Integer, Double> productorRating;
	private HashMap<Integer, Double> artistsRating;
	private HashMap<Integer, Double> actorsRating;
	private HashMap<Integer, Double> directorsRating;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_id", referencedColumnName = "id")
	@JsonIgnore
	private Profile profile;

	public Purchase(String itemId, String itemType, String itemTitle, Integer distributorId, Integer productorId, List<Integer> artistsIds, List<Integer> actorsIds, List<Integer> directorsIds, Profile profile) {
		super();
		this.purchaseDate = new Date();		
		this.itemId = itemId;
		this.itemType = itemType;
		this.itemTitle = itemTitle;
		this.profile = profile;

		this.mediumRating = 0.0;
		
		this.distributorRating = new HashMap<Integer, Double>();
		this.distributorRating.put(distributorId, 0.0);
		
		this.productorRating = new HashMap<Integer, Double>();
		this.productorRating.put(productorId, 0.0);
		
		this.artistsRating =  new HashMap<Integer, Double>();
		for(Integer artistId: artistsIds)  this.artistsRating.put(artistId, 0.0);
		
		this.actorsRating =  new HashMap<Integer, Double>();
		for(Integer actorId: actorsIds)  this.actorsRating.put(actorId, 0.0);
		
		this.directorsRating =  new HashMap<Integer, Double>();
		for(Integer directorId: directorsIds)  this.directorsRating.put(directorId, 0.0);
	}
}