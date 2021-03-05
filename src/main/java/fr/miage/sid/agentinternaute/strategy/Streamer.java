package fr.miage.sid.agentinternaute.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.miage.sid.agentinternaute.dto.OfferDTO;
import fr.miage.sid.agentinternaute.entity.Profile;

public class Streamer {

	// method compareOffers
	private JSONArray compareOffers(JSONObject response) {
		
		JSONArray offres = response.getJSONArray("abonnements");
		JSONArray sortedSubscriptions = new JSONArray();

		// On met les oeuvres du JSONArray dans une liste
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		for (int i = 0; i < offres.length(); i++) {
			jsonValues.add(offres.getJSONObject(i));
		}

		// On trie
		Collections.sort(jsonValues, new Comparator<JSONObject>() {

			@Override
			public int compare(JSONObject a, JSONObject b) {
				Integer durationA = 0, durationB = 0;
				Double priceA = 10000000.0, priceB = 10000000.0;

				try {
					durationA = a.getInt("duree");
					durationB = b.getInt("duree");
					priceA = a.getDouble("prix");
					priceB = b.getDouble("prix");
				} catch (JSONException e) {
					// LOGGER.severe("Cannot sort JSONArray");
				}

				if (durationA == durationB) {
					// sort by price
					return priceA.compareTo(priceB);
				} else {
					// sort by duration
					return durationB.compareTo(durationA);
				}
			}
		});

		// On remet les objets dans le JSONArray
		for (int i = 0; i < offres.length(); i++) {
			sortedSubscriptions.put(jsonValues.get(i));
		}

		return sortedSubscriptions;
	}

	// method main
	// Not final version
	public JSONObject streamerStrategy(Profile profil, JSONObject response) {
		JSONArray result = null;

		// return result into JSONObject
		JSONArray oeuvres = response.getJSONArray("oeuvres");
		JSONArray sortedSubscriptions = compareOffers(response);

		JSONObject sorted = new JSONObject();
		sorted.put("oeuvres", oeuvres);
		sorted.put("abonnements", sortedSubscriptions);

		return sorted;
	}

}