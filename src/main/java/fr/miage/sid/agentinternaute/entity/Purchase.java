package fr.miage.sid.agentinternaute.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
public class Purchase implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private Integer id;
	private String itemType;
	private Integer itemId;
	private Double itemRating;
	private Double price;
	private Date viewDate;
	private String titre;
	
	@Lob
	private String description;
	
	private Integer dateSortie;
	private Double note;
	private String distributeurId;
	private Double distributeurRating;
	private String producteurId;
	private Double producteurRating;
	
	@Lob
	private ArrayList<Genre> genres;
	
	@Lob
	private ArrayList<Actor> acteurs;
	
	@Lob
	private ArrayList<Director> realisateurs;
	
	@Lob
	private ArrayList<Artist> artistes;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_id", referencedColumnName = "id")
	@JsonIgnore
	private Profile profile;

	public Purchase(String itemType, Integer itemId, Double price, String titre, String description, Integer dateSortie, Double note,
			String distributeur, String producteur, ArrayList<Genre> genres, ArrayList<Actor> acteurs, ArrayList<Director> realisateurs,
			ArrayList<Artist> artistes, Profile profile) {
		super();
		this.itemType = itemType;
		this.itemId = itemId;
		this.itemRating = 0.0;
		this.viewDate = new Date();
		this.price = price;
		this.titre = titre;
		this.description = description;
		this.dateSortie = dateSortie;
		this.note = note;
		this.distributeurId = distributeur;
		this.distributeurRating = 0.0;
		this.producteurId = producteur;
		this.producteurRating = 0.0;
		this.genres = genres;
		this.acteurs = acteurs;
		this.realisateurs = realisateurs;
		this.artistes = artistes;
		this.profile = profile;
	}
	
	
}