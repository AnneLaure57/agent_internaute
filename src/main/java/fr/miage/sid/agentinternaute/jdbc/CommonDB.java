package fr.miage.sid.agentinternaute.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommonDB {

	private static final Logger LOGGER = Logger.getLogger(CommonDB.class.getName());

	static Connection ConnectionBDD = null;
	static PreparedStatement PreparedStatementBdd = null;

	private static void makeJDBCConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE,
					"Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly.");
			e.printStackTrace();
			return;
		}

		try {
			ConnectionBDD = DriverManager.getConnection(
					"jdbc:mysql://mysql-agentmiage2021.alwaysdata.net/agentmiage2021_db", "227750", "mdpUser");
			if (ConnectionBDD == null) {
				LOGGER.log(Level.SEVERE, "Failed to make connection!");
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "MySQL Connection Failed!");
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * Get movies with exact match
	 * @param title
	 */
	private static void getMovies(String title) {

		try {
			makeJDBCConnection();
			
			String statement = "SELECT * FROM perso_movies where titre = " + title;

			PreparedStatementBdd = ConnectionBDD.prepareStatement(statement);
			ResultSet rs = PreparedStatementBdd.executeQuery();

			while (rs.next()) {
				int idFilm = rs.getInt("id_film");
				String originalTitle = rs.getString("title");
				String titre = rs.getString("titre");
				String resume = rs.getString("resume");
			}

			PreparedStatementBdd.close();
			ConnectionBDD.close();
			
			return ;

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "MySQL query Failed!");
			e.printStackTrace();
		}
	}
}
