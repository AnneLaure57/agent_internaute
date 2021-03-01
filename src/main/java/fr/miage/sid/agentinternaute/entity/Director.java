package fr.miage.sid.agentinternaute.entity;

import java.util.ArrayList;
import java.util.List;

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
public class Director {
	
	@Id
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	private Integer id;
	
	private String directorId;

	@Column(unique = true)
	private String name;
	
	private Double rating;

	public Director(String directorId, String nom, String prenom) {
		super();
		this.directorId = directorId;
		this.name = name;
		this.rating = 0.0;
	}
}
