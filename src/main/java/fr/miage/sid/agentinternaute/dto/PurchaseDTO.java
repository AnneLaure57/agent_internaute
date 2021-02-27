package fr.miage.sid.agentinternaute.dto;

import java.util.Date;

import fr.miage.sid.agentinternaute.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
	
    private Double rating;
    private String itemId;
    private String itemTitle;
    private Integer profileId;
}
