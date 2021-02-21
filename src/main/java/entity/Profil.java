package entity;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profil {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String name;

	private String sex;

	private Integer age;

	private Integer averageConsumptionTime;

	private Integer maxBudget;

	private ArrayList<Director> preferedMoviesDirectors;
	private ArrayList<Actor> preferedMoviesActors;
	private ArrayList<String> preferedMoviesGenres;

	private ArrayList<String> preferedTvShowsGenres;
	private ArrayList<Director> preferedTvShowsDirectors;
	private ArrayList<Actor> preferedTvShowsActors;

	private ArrayList<String> preferedMusicsGenres;
	private ArrayList<Artist> preferedMusicsArtists;

}
