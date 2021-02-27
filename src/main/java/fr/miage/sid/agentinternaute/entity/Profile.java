package fr.miage.sid.agentinternaute.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(unique = true)
	private String name;

	private String sex;
	private Integer age;
	private Integer currentConsumptionTime;
	private Integer averageConsumptionTime;
	private Integer currentExpenses;
	private Integer maxBudget;

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

	public Profile(String name, String sex, Integer age, Integer averageConsumptionTime, Integer maxBudget) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.averageConsumptionTime = averageConsumptionTime;
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
