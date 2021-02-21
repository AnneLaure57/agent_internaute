package fr.miage.sid.agentinternaute.entity;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	public Profile(String name, String sex, Integer age, Integer averageConsumptionTime, Integer maxBudget) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.averageConsumptionTime = averageConsumptionTime;
		this.maxBudget = maxBudget;
	}
	private ArrayList<Person> preferedMoviesDirectors;
	private ArrayList<Person> preferedMoviesActors;
	private ArrayList<String> preferedMoviesGenres;

	private ArrayList<String> preferedTvShowsGenres;
	private ArrayList<Person> preferedTvShowsDirectors;
	private ArrayList<Person> preferedTvShowsActors;

	private ArrayList<String> preferedMusicsGenres;
	private ArrayList<Person> preferedMusicsArtists;
}
