package fr.miage.sid.agentinternaute.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
	
    private String itemId;
    private String itemTitle;
    private Integer profileId;
    
    private Integer distributorId;
    private Integer productorId;
    private List<Integer> actorsIds;
    private List<Integer> directorsIds;
}
