package fr.miage.sid.agentinternaute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Louis MASSICARD (user name : louis)
 * @version 
 * @since %G% - %U% (%I%)
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SatisfactionDTO {

	private Integer profileId;
	private Double meanSatisfaction;
}
