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
		//String subscribe = json.getString("abonnements");
		//System.out.println("notre json" + response);
		
		JSONArray subscribes = response.getJSONArray("abonnements");
		JSONArray artworks = response.getJSONArray("oeuvres");
		
		ArrayList<OfferDTO> offers = new ArrayList<OfferDTO>(); 
		
		double price = 0;
		
		//First Step => get the list of artworks
		for(int i=0; i<artworks.length(); i++){
			//get the subscribe
			JSONObject artwork = (JSONObject) artworks.get(i);
			
			// Get id
			Long ID = Long.valueOf(artwork.getString("id"));
			//Get price
			if (artwork.has("prix")) {
				price = (double) ((Integer) artwork.get("prix")).intValue();
			} else {
				price = 0.0;
			}
		
            //Get release date
            int releaseDate = (int) artwork.get("dateSortie");
            OfferDTO offer = new OfferDTO(ID, releaseDate, price);
            
            //on ajoute dans la liste des offres
            offers.add(offer);  
        }
		
		//sort by release date and price
		Collections.sort(offers, OfferDTO.ComparatorDatPrice);
		
		//return the last element, with the most recent date
		return offers.get(offers.size() - 1);
	}
		
	//method main
		public JSONObject exigentStrategy(Profile profil, JSONObject response) {
			OfferDTO result = null;

			// compare offers with the string message from distributors (can be change it necessary to JSON) or List<ResultDTO>
			result = compareOffers(response);
			//return result into JSONObject
			JSONObject jsonResult = new JSONObject(result);
			return jsonResult;
		}

}