package fr.miage.sid.agentinternaute.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile implements Serializable {
	
	private static final long serialVersionUID = 1675238169966533190L;

	@Id
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	private Integer id;

	@Column(unique = true)
	private String name;

	private String sex;
	private Integer age;
	private Double currentConsumptionTime;
	private Integer averageConsumptionTime;
	private Double currentExpenses;
	private Double maxBudget;

	@Lob
	private ArrayList<Integer> preferedVideoGenres;
	@Lob
	private ArrayList<Integer> preferedDirectors;
	@Lob
	private ArrayList<Integer> preferedActors;

	@Lob
	private ArrayList<Integer> preferedMusicGenres;
	@Lob
	private ArrayList<Integer> preferedMusicArtists;
	
	private boolean preferDownloadsForVideos;
	private boolean preferDownloadsForMusics;
	
	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Purchase> moviesWatched;
	
	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Satisfaction> satisfactionHistory;

	public Profile(String name, String sex, Integer age, Integer averageConsumptionTime, Double maxBudget) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.currentConsumptionTime = 0.0;
		this.averageConsumptionTime = averageConsumptionTime;
		this.currentExpenses = 0.0;
		this.maxBudget = maxBudget;
		this.preferedVideoGenres = new ArrayList<Integer>();
		this.preferedDirectors = new ArrayList<Integer>();
		this.preferedActors = new ArrayList<Integer>();
		this.preferedMusicGenres = new ArrayList<Integer>();
		this.preferedMusicArtists = new ArrayList<Integer>();
		this.preferDownloadsForVideos = false;
		this.preferDownloadsForMusics = false;
	}
}
