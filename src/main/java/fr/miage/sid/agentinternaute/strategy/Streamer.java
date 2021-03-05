package fr.miage.sid.agentinternaute.strategy;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.miage.sid.agentinternaute.dto.OfferDTO;
import fr.miage.sid.agentinternaute.entity.Profile;

public class Streamer {
	
	//method compareOffers
	private ArrayList<OfferDTO> compareOffers(JSONObject response) {
		//String subscribe = json.getString("abonnements");
		//System.out.println("notre json" + response);
		JSONArray artworks = response.getJSONArray("oeuvres");
		JSONArray subscribes = response.getJSONArray("abonnements");
		
		ArrayList<OfferDTO> offers = new ArrayList<OfferDTO>(); 
		
		//First Step => get the list of subcribes
		for(int i=0; i<subscribes.length(); i++){
			//get the subscribe
			JSONObject subscribe = (JSONObject) subscribes.get(i);
			
			// Get id
			Long subID = Long.valueOf(subscribe.getString("id"));
			
			//Get price
            Double subPrice = subscribe.getDouble("prix");
            
            //Get duration
            Integer subDur = subscribe.getInt("duree");
            OfferDTO offer = new OfferDTO(subID, subDur, subPrice);
            
            //on ajoute dans la liste des offres
            offers.add(offer);  
        }
		
		//sort by duration and price
		Collections.sort(offers, OfferDTO.ComparatorDurPrice);
		
		//System.out.println("offres " + offers.get(0));
		System.out.println(offers);
		return offers;
	}
	
	//method main
	// Not final version
	public JSONObject streamerStrategy (Profile profil, JSONObject response) {
		ArrayList<OfferDTO> result = null;
		
		// check the preferences of the user profile
		if (profil.isPreferDownloadsForVideos() == false) {
			// compare offers with the string message from distributors (can be change it necessary to JSON) or List<ResultDTO>
			result = compareOffers(response);
		} else {
			profil.setPreferDownloadsForVideos(false);
			streamerStrategy(profil, response);
		}
		
		//return result into JSONObject
		JSONArray sortedSubscribes = new JSONArray();
		
		// On remet les objets dans le JSONArray
		for (int i = 0; i < result.size(); i++) {
			sortedSubscribes.put(result.get(i));
		}
		
		JSONArray oeuvres = response.getJSONArray("oeuvres");
		
		JSONObject sorted = new JSONObject();
		sorted.put("oeuvres", oeuvres);
		sorted.put("abonnements", sortedSubscribes);
		
		return sorted;
	}
	
}