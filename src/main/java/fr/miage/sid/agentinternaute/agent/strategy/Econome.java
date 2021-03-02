package fr.miage.sid.agentinternaute.agent.strategy;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.miage.sid.agentinternaute.dto.OfferDTO;
import fr.miage.sid.agentinternaute.entity.Profile;

public class Econome {

	// Parametre dois  être JSONObject (JSONArray juste pour le test)
	public JSONObject economeResponse(JSONArray response, Profile profil) {
	
		boolean pref = profil.isPreferDownloadsForVideos();
	
		// C'est ce qui est bon avec l'agent distributeur
//		JSONObject json = new JSONObject(response);
//		JSONArray oeuvres = json.getJSONArray("oeuvres");
//		JSONArray offres = json.getJSONArray("abonnements");
		
		// Juste pour tester la stratégie
		JSONArray oeuvres = response;
		JSONArray offres = response;
		
		// Si il prefère le téléchargement 
		if(pref) {
			System.out.println("L'utilisateur préfère le télchargemlent");
			return( this.getMinPrix(oeuvres));
			
		}else {
			JSONObject choixOeuvre = this.getMinPrix(oeuvres);
			JSONObject choixOffre = this.getMinPrix(offres);
			
			if (choixOeuvre.getDouble("prix") >= choixOffre.getDouble("prix")) {
				return choixOffre;
			}
			else {
				return choixOeuvre;
			}
		}
			
	}
	
	public JSONObject getMinPrix(JSONArray elements) {
		
		JSONObject choix = new JSONObject();
		Double prix_min = 0.0;
		int index_choix = 0;
		int j = 0;
		
		for (int i = 0; i < elements.length(); i++) {
			JSONObject element = (JSONObject) elements.get(i);
			// Première itération
			if( element.has("prix")) {
				if(i == j) {
					prix_min = element.getDouble("prix");
					index_choix = i;
				} else {
					
					if (prix_min > element.getDouble("prix")) {
						prix_min = element.getDouble("prix");
						index_choix = i;
					}	
					
				}
			} else {
				j++;
			}
		}
		
		choix = elements.getJSONObject(index_choix);
		
		return choix;
	}
	
}
