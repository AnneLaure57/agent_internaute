package fr.miage.sid.agentinternaute.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

	private Integer id;

	@Column(unique = true)
	private String nom;	
}
