package entity;

import java.time.Duration;
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
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String nom;
	
	private String sexe;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_naissance")
	private Date dateNaissance;
	
	private Duration tempsConsoMoyen;
	
	private Integer budgetMax;
	
	private boolean telechargementPrefereFilm;
	
	private boolean telechargementPrefereSeries;
	
	private boolean telechargementPrefereMusique;
	
	private boolean telechargementPrefereJeux;
	
	private ArrayList<String> genresPreferesFilms;
	
	private ArrayList<String> genresPreferesSeries;
	
	private ArrayList<String> genresPreferesMusique;
	
	private ArrayList<String> genresPreferesJeux;
	
	private ArrayList<Artiste> artistesPreferes;
	
}
