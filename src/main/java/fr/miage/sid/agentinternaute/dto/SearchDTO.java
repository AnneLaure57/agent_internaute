package fr.miage.sid.agentinternaute.dto;

import fr.miage.sid.agentinternaute.entity.Profile;
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
}
