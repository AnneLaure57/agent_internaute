package fr.miage.sid.agentinternaute.dto;

import java.util.ArrayList;

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
public class SearchDTO {	
	
	private Integer profileId;
	private String searchField;
	private Boolean movies;
	private Boolean musics;
	private Boolean tvShows;
	
	private ArrayList<Genre> selectedVideoGenres;
	private ArrayList<Integer> selectedMusicGenres;
	private ArrayList<Artist> selectedArtists;
	private ArrayList<Actor> selectedActors;
	private ArrayList<Director> selectedDirectors;
}
