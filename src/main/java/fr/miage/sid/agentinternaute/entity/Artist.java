package fr.miage.sid.agentinternaute.entity;

import java.io.Serializable;

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
public class Artist implements Serializable  {

	private static final long serialVersionUID = -1682582090262364342L;

	@Id
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	private String artistId;
	
	private Integer id;
	
	private String prenom;
	private String nom;
	
	private Double rating = 0.0;

}
