package fr.miage.sid.agentinternaute.agent.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.miage.sid.agentinternaute.dto.OfferDTO;
import fr.miage.sid.agentinternaute.entity.Profile;

public class Streamer {
	
	//method compareOffers
	private OfferDTO compareOffers(JSONObject response) {
		//String subscribe = json.getString("abonnements");
		//System.out.println("notre json" + response);
		JSONArray subscribes = response.getJSONArray("abonnements");
		
		ArrayList<OfferDTO> offers = new ArrayList<OfferDTO>(); 
		
		//First Step => get the list subcribe
		for(int i=0; i<subscribes.length(); i++){
			//get the subscribe
			JSONObject subscribe = (JSONObject) subscribes.get(i);
			
			// Get id
			Long subID = Long.valueOf(subscribe.getString("id"));
			
			//Get price
            Double subPrice = (Double) subscribe.get("prix");
            
            //Get duration
            int subDur = (int) subscribe.get("duree");
            OfferDTO offer = new OfferDTO(subID, subDur, subPrice);
            
            //on ajoute dans la liste des offres
            offers.add(offer);  
        }
		
		//sort by duration and price
		Collections.sort(offers, OfferDTO.ComparatorDurPrice);
		
		//return the first offer
		//take the biggest duration
		/*int bestDuration = 0;
		//return the element
		Long index = null;
		for (OfferDTO o : offers) {
			if (o.getDuration() > bestDuration) {
				bestDuration = o.getDuration();
				System.out.println(bestDuration);
			}
			index = o.getId();
		}
		int element = Arrays.asList(offers).indexOf(index);
		System.out.println("index : " +  Arrays.asList(offers).indexOf(index));*/
		
		//System.out.println("index : " +  Collections.sort(offers, OfferDTO.ComparatorDurPrice));
	
		//System.out.println("index : " + offers.get(0));

		return offers.get(0);
	}
	
	//method main
	// Not final verison
	public JSONObject streamerStrategy (Profile profil, JSONObject response) {
		OfferDTO result = null;
		// check the preferences of the user profile
		if (profil.isPreferDownloadsForVideos() == false) {
			// compare offers with the string message from distributors (can be change it necessary to JSON) or List<ResultDTO>
			result = compareOffers(response);
		}
		//return result into JSONObject
		JSONObject jsonResult = new JSONObject(result);
		return jsonResult;
	}
	
}
