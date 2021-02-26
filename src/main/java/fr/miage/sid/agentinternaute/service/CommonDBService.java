package fr.miage.sid.agentinternaute.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonDBService {
	
	private final Logger LOGGER = Logger.getLogger(CommonDBService.class.getName());

	static Connection ConnectionBDD = null;
	static PreparedStatement PreparedStatementBdd = null;

	private void makeJDBCConnection() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE,
					"Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly.");
			e.printStackTrace();
			return;
		}

		try {
			ConnectionBDD = DriverManager.getConnection(
					"jdbc:mysql://mysql-agentmiage2021.alwaysdata.net/agentmiage2021_dbpropre", "227750", "mdpUser");
			if (ConnectionBDD == null) {
				LOGGER.log(Level.SEVERE, "Failed to make connection!");
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "MySQL Connection Failed!");
			e.printStackTrace();
			return;
		}
	}
	
	/*
	 * Récupération des acteurs
	 */
	public JSONArray getActors() {
		JSONArray actors = new JSONArray();
//		try {
//			makeJDBCConnection();
//			
//			String statement = "SELECT * FROM personne INNER JOIN acteurs ON acteurs.id_personne = personne.id ";
//
//			PreparedStatementBdd = ConnectionBDD.prepareStatement(statement);
//			PreparedStatementBdd.execute();
//			ResultSet rs = PreparedStatementBdd.getResultSet();
//			
//			JSONObject acteur;
//			while (rs.next()) {
//				acteur = new JSONObject();
//				acteur.put("id", rs.getInt("id"));
//				acteur.put("last_name", rs.getString("nom"));
//				acteur.put("first_name", rs.getString("prenom"));
//				actors.put(acteur);
//			}
//
//			PreparedStatementBdd.close();
//			ConnectionBDD.close();
////			
////			System.out.println("---------------Acteurs----------------");
////			System.out.println(actors.toString());
//			return actors;
//			
//		} catch (SQLException e) {
//			LOGGER.log(Level.SEVERE, "MySQL query Failed!");
//			e.printStackTrace();
//		}
		return null;
	}
	
	/*
	 * Récupération des réalisateurs
	 */
	public JSONArray getDirectors() {
		JSONArray directors = new JSONArray();
//		try {
//			makeJDBCConnection();
//			
//			String statement = "SELECT * FROM personne INNER JOIN acteurs ON realisateurs.id_personne = personne.id";
//
//			PreparedStatementBdd = ConnectionBDD.prepareStatement(statement);
//			PreparedStatementBdd.execute();
//			ResultSet rs = PreparedStatementBdd.getResultSet();
//			JSONObject realisateur;
//			
//			while (rs.next()) {
//				realisateur = new JSONObject();
//				realisateur.put("id", rs.getInt("id"));
//				realisateur.put("last_name", rs.getString("nom"));
//				realisateur.put("first_name", rs.getString("prenom"));
//				directors.put(realisateur);
//			}
//
//			PreparedStatementBdd.close();
//			ConnectionBDD.close();
//			
//			System.out.println("---------------Réalisateurs----------------");
//			System.out.println(directors.toString());
//			return directors;
//
//		} catch (SQLException e) {
//			LOGGER.log(Level.SEVERE, "MySQL query Failed!");
//			e.printStackTrace();
//		}
		return null;
	}
	
	/*
	 * Récupération des artistes de musiques
	 */
	public JSONArray getArtists() {
		JSONArray artists = new JSONArray();
//		try {
//			makeJDBCConnection();
//			
//			String statement = "SELECT * FROM artists ORDER BY name";
//
//			PreparedStatementBdd = ConnectionBDD.prepareStatement(statement);
//			PreparedStatementBdd.execute();
//			ResultSet rs = PreparedStatementBdd.getResultSet();
//			JSONObject artist;
//			
//			while (rs.next()) {
//				artist = new JSONObject();
//				artist.put("id", rs.getInt("id"));
//				artist.put("name", rs.getString("name"));
//				artists.put(artist);
//			}
//
//			PreparedStatementBdd.close();
//			ConnectionBDD.close();
//			
////			System.out.println("---------------Artists musique----------------");
////			System.out.println(artists.toString());
////			return artists;
//
//		} catch (SQLException e) {
//			LOGGER.log(Level.SEVERE, "MySQL query Failed!");
//			e.printStackTrace();
//		}
		return null;
	}
	
	/*
	 * Récupération des genres des films (/série)
	 */
	public JSONArray getVideoGenres() {
		JSONArray genres = new JSONArray();
//		try {
//			makeJDBCConnection();
//			
//			String statement = "SELECT * FROM genre ORDER BY nom";
//
//			PreparedStatementBdd = ConnectionBDD.prepareStatement(statement);
//			PreparedStatementBdd.execute();
//			ResultSet rs = PreparedStatementBdd.getResultSet();
//			JSONObject genre;
//			
//			while (rs.next()) {
//				genre = new JSONObject();
//				genre.put("id", rs.getInt("id"));
//				genre.put("name", rs.getString("nom"));
//				genres.put(genre);
//			}
//
//			PreparedStatementBdd.close();
//			ConnectionBDD.close();
////			System.out.println("---------------Genre de films----------------");
////			System.out.println(genres.toString());
//			return genres;
//
//		} catch (SQLException e) {
//			LOGGER.log(Level.SEVERE, "MySQL query Failed!");
//			e.printStackTrace();
//		}
		return null;
	}
	
	/*
	 * Récupération de genre de musiques  
	 */
	public JSONArray getMusicGenres() {
		try {
			JSONArray genres = new JSONArray();
			
			makeJDBCConnection();
			
			String statement = "SELECT * FROM musical_genres ORDER BY name";

			PreparedStatementBdd = ConnectionBDD.prepareStatement(statement);
			PreparedStatementBdd.execute();
			ResultSet rs = PreparedStatementBdd.getResultSet();
			JSONObject genre;
			
			while (rs.next()) {
				genre = new JSONObject();
				genre.put("id", rs.getInt("id"));
				genre.put("name", rs.getString("name"));
				genres.put(genre);
			}

			PreparedStatementBdd.close();
			ConnectionBDD.close();
			
			return genres;

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "MySQL query Failed!");
			e.printStackTrace();
		}
		return null;
	}
}