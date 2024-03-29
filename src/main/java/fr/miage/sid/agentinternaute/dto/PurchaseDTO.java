package fr.miage.sid.agentinternaute.dto;

import java.util.ArrayList;
import java.util.Comparator;

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
public class PurchaseDTO {
	
	private Double prix;
	private String itemType;
	private Integer id; // id du truc qu'on achète côté distributeur
	private String titre;
	private String description;
	private Integer dateSortie;
	private Double note;
	private String distributeur;
	private String producteur;
	private ArrayList<Genre> genres;
	private ArrayList<Actor> acteurs;
	private ArrayList<Director> realisateurs;
	private ArrayList<Artist> artistes;
	private Integer profileId;
	private Integer subscriptionId;
	private Integer subscriptionPrice;
	
	public static Comparator<PurchaseDTO> ComparatorDate = new Comparator<PurchaseDTO>() {
		@Override
		public int compare(PurchaseDTO p1, PurchaseDTO p2) {
			return p1.getDateSortie().compareTo(p2.getDateSortie());
		}
	};
}
