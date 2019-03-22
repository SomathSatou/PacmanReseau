package com.satou.somath.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
	
	private DAOFactory daoFactory;
	
    UserDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    private static final String SQL_SELECT = "SELECT pseudo, mot_de_passe FROM User WHERE pseudo = ? AND mot_de_passe = ?";
	@Override
	public boolean identification(String Pseudo, String mdp) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	    boolean flag = false;
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = DAOUtilitaire.initialisationRequetePreparee( connexion, SQL_SELECT, false, Pseudo, mdp );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données des éventuel ResulSet retourné */
	        if ( resultSet.next() ) {
	            flag = true;
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        DAOUtilitaire.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return flag;
	}
}
