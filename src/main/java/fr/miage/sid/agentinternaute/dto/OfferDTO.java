package fr.miage.sid.agentinternaute.dto;

import java.util.Comparator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferDTO {
	
	Long id;
	int duration;
	Double price;

	/*@Override
	public int compareTo(OfferDTO o) {
		//compare the duration
		return (this.duration - o.duration);
	}*/
	
	public static Comparator<OfferDTO> ComparatorDurPrice = new Comparator<OfferDTO>() {
		@Override
		public int compare(OfferDTO o1, OfferDTO o2) {
			int duration = o1.getDuration() - o2.getDuration();
			
			// If duration is same for the offers
			if(duration == 0) { 
				//check price
				return o1.getPrice().compareTo(o2.getPrice());
	        } else { 
	          // duration is not null
	          // sort by duration
	        	if (duration < 0 || duration > 0) {
	        		return (int) (o1.getPrice()- o2.getPrice());
	        	}
	          return (int) (o1.getDuration()- o2.getDuration());
	        }
		}
	};
	
	public static Comparator<OfferDTO> ComparatorDatPrice = new Comparator<OfferDTO>() {
		@Override
		public int compare(OfferDTO o1, OfferDTO o2) {
			int duration = o1.getDuration() - o2.getDuration();
			
			// If duration is same for the offers
			if(duration == 0) { 
				//check price
				return o1.getPrice().compareTo(o2.getPrice());
	        } else { 
	          // duration is not null
	          // sort by duration
	        	if (duration < 0 || duration > 0) {
	        		return (int) (o1.getPrice()- o2.getPrice());
	        	}
	          return (int) (o1.getDuration()- o2.getDuration());
	        }
		}
	};
}
