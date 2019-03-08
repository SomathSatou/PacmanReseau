package com.satou.somath.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GameDaoImpl implements GameDao {
	
	private DAOFactory daoFactory;

	public GameDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	private static final String SQL_INSERT = "INSERT INTO Game (map, joueur, resultat, score, datejeu) VALUES (?, ?, ?, ?, ?)";
	@Override
	public void enrengistrerPartie(String map, String joueur, String resultat, int score) throws DAOException {

	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = DAOUtilitaire.initialisationRequetePreparee( connexion, SQL_INSERT, true, map, joueur, resultat, score );
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DAOException( "Échec de l'insertion de la partie, aucune ligne ajoutée dans la table." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        DAOUtilitaire.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}}
