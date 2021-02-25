package fr.miage.sid.agentinternaute.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<History> moviesWatched;

	private ArrayList<String> preferedVideoGenres;
	private ArrayList<String> preferedDirectors;
	private ArrayList<String> preferedActors;

	private ArrayList<String> preferedMusicGenres;
	private ArrayList<String> preferedMusicArtists;

	public Profile(String name, String sex, Integer age, Integer averageConsumptionTime, Integer maxBudget) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.averageConsumptionTime = averageConsumptionTime;
		this.maxBudget = maxBudget;
		this.preferedVideoGenres = new ArrayList<String>();
		this.preferedDirectors = new ArrayList<String>();
		this.preferedActors = new ArrayList<String>();
		this.preferedMusicGenres = new ArrayList<String>();
		this.preferedMusicArtists = new ArrayList<String>();
	}
}
