package fr.miage.sid.agentinternaute.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artist {

	@Id
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	private Integer id;
	
	private String artistId;

	@Column(unique = true)
	private String name;
	
	private Double rating;

	public Artist(String artistId, String name) {
		super();
		this.artistId = artistId;
		this.name = name;
		this.rating = 0.0;
	}
}
