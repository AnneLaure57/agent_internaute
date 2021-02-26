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
public class Purchase {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
    
    private Date data;
    
    private Double rating;
    
    private String itemId;
	
    private String itemTitle;
    
    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="profile_id", referencedColumnName="id")
	@JsonIgnore
    private Profile profile;
}