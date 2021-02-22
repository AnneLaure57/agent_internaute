package fr.miage.sid.agentinternaute.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
    
    private Date addDate;
    
    private Float rating;
    
    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="movie_id", referencedColumnName="id")
	@JsonIgnore
    private Movie movie;
    
    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="profile_id", referencedColumnName="id")
	@JsonIgnore
    private Profile profile;
    
}