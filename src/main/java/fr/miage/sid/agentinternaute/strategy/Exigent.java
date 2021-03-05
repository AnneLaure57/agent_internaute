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

public class Exigent {

	// method compareOffers
	private JSONArray compareOffers(JSONObject response) {
		// String subscribe = json.getString("abonnements");
		// System.out.println("notre json" + response);

		JSONArray oeuvres = response.getJSONArray("oeuvres");
		JSONArray sortedOeuvres = new JSONArray();

		// On met les oeuvres du JSONArray dans une liste
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		for (int i = 0; i < oeuvres.length(); i++) {
			jsonValues.add(oeuvres.getJSONObject(i));
		}

		// On trie
		Collections.sort(jsonValues, new Comparator<JSONObject>() {

			@Override
			public int compare(JSONObject a, JSONObject b) {
				Integer releaseA = 0, releaseB = 0;
				Double priceA = 10000000.0, priceB = 10000000.0;

				try {
					releaseA = a.getInt("dateSortie");
					releaseB = b.getInt("dateSortie");
					priceA = a.getDouble("prix");
					priceB = b.getDouble("prix");
				} catch (JSONException e) {
//							LOGGER.severe("Cannot sort JSONArray");
				}

				if (releaseA == releaseB) {
					// sort by price
					return priceA.compareTo(priceB);
				} else {
					// sort by duration
					return releaseB.compareTo(releaseA);
				}
			}
		});

		// On remet les objets dans le JSONArray
		for (int i = 0; i < oeuvres.length(); i++) {
			sortedOeuvres.put(jsonValues.get(i));
		}

		return sortedOeuvres;
	}

	// method main
	public JSONObject exigentStrategy(Profile profil, JSONObject response) {

		// return result into JSONObject
		JSONArray sortedOeuvres = compareOffers(response);
		JSONArray subscribes = response.getJSONArray("abonnements");

		JSONObject sorted = new JSONObject();
		sorted.put("oeuvres", sortedOeuvres);
		sorted.put("abonnements", subscribes);

		return sorted;
	}
}