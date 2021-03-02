package fr.miage.sid.agentinternaute.dto;

import java.util.ArrayList;
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
	
	public static Comparator<OfferDTO> ComparatorDur = new Comparator<OfferDTO>() {
		@Override
		public int compare(OfferDTO o1, OfferDTO o2) {
			return (o1.getDuration() - o2.getDuration());
		}
	};

}
