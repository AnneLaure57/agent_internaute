package fr.miage.sid.agentinternaute.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

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
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	private Integer id;
    
    private Date date;
    
    private Double rating;
    
    private String itemId;
	
    private String itemTitle;
    
    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="profile_id", referencedColumnName="id")
	@JsonIgnore
    private Profile profile;

	public Purchase(Double rating, String itemId, String itemTitle, Profile profile) {
		super();
		this.date = new Date();
		this.rating = rating;
		this.itemId = itemId;
		this.itemTitle = itemTitle;
		this.profile = profile;
	}
}