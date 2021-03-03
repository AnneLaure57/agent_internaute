package fr.miage.sid.agentinternaute.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre implements Serializable  {

	private static final long serialVersionUID = -2015890134428805605L;

	@Id
	private Integer id;

	@Column(unique = true)
	private String nom;	
}
