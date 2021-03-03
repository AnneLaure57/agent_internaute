package fr.miage.sid.agentinternaute.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.miage.sid.agentinternaute.dto.OfferDTO;
import fr.miage.sid.agentinternaute.entity.Profile;

public class Exigent {

	//method compareOffers
	private OfferDTO compareOffers(JSONObject response) {
		
		JSONObject json = new JSONObject(response);
		JSONArray subscribes = json.getJSONArray("oeuvres");
		
		ArrayList<OfferDTO> offers = new ArrayList<OfferDTO>(); 
		
		//First Step => get the list of artworks
		for(int i=0; i<subscribes.length(); i++){
			//get the subscribe
			JSONObject subscribe = (JSONObject) subscribes.get(i);
			
			// Get id
			Long ID = Long.valueOf(subscribe.getString("id"));
			
			//Get price
            Double price = Double.valueOf(subscribe.getString("prix"));
            
            //Get release date
            int releaseDate = Integer.parseInt(subscribe.getString("dateSortie"));
            OfferDTO offer = new OfferDTO(ID, releaseDate, price);
            
            //on ajoute dans la liste des offres
            offers.add(offer);  
        }
		
		//sort by release date and price
		Collections.sort(offers, OfferDTO.ComparatorDurPrice);
		
		//return the first offer
		//take the biggest duration
		int bestDuration = 0;
		
		for (OfferDTO o : offers) {
			if (o.getDuration() > bestDuration) {
				bestDuration = o.getDuration();
			}
		}
		
		//return the element
		int index = Arrays.asList(offers).indexOf(bestDuration);
		return offers.get(index);
	}
		
	//method main
		public JSONObject exigentStrategy (Profile profil, JSONObject response) {
			OfferDTO result = null;
			// check the preferences of the user profile
			if (profil.isPreferDownloadsForVideos() == true) {
				// compare offers with the string message from distributors (can be change it necessary to JSON) or List<ResultDTO>
				result = compareOffers(response);
			} else {
				//If no case -> force to set download call the method
				profil.setPreferDownloadsForVideos(true);
				exigentStrategy(profil, response);
			}
			//return result into JSONObject
			JSONObject jsonResult = new JSONObject(result);
			return jsonResult;
		}

}
