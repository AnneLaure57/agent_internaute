package fr.miage.sid.agentinternaute.agent.strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.miage.sid.agentinternaute.dto.OfferDTO;
import fr.miage.sid.agentinternaute.entity.Profile;

public class Streamer {
	
	public Boolean checkPreferences(Profile profil) {
		boolean pref = false;
		if (!profil.isPreferDownloadsForVideos()) {
			profil.setPreferDownloadsForVideos(true);
			return pref = profil.isPreferDownloadsForVideos();
		}
		return pref;
	}
	
	//getAllOffers
	//Check part abonnement
	//get duree et prix
	//compare
	//take the less expensive with the biggest duration
	
	public void compareOffers(String response) {
		JSONObject json = new JSONObject(response);
		//String subscribe = json.getString("abonnements");

		JSONArray subscribes = json.getJSONArray("abonnements");
		
		ArrayList<OfferDTO> offers = new ArrayList<OfferDTO>(); 
		
		//First Step => get the list subcribe
		for(int i=0; i<subscribes.length(); i++){
			//get the subscribe
			JSONObject subscribe = (JSONObject) subscribes.get(i);
			
			// Get id
			Long subID = Long.valueOf(subscribe.getString("id"));
			
			//Get price
            Double subPrice = Double.valueOf(subscribe.getString("prix"));
            
            //Get duration
            int subDur = Integer.parseInt(subscribe.getString("duree"));
            OfferDTO offer = new OfferDTO(subID, subDur, subPrice);
            
            //on ajoute dans la liste des offres
            offers.add(offer);  
        }
		
		//sort by duration
		Collections.sort(offers, OfferDTO.ComparatorDur);

	}
}
