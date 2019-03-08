package com.satou.somath.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.satou.webAvancee.model.Utilisateur;
import com.satou.webAvancee.dao.DAOUtilitaire;;

public class UserDaoImpl implements UserDao {
	
	private DAOFactory daoFactory;
	
    UserDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

	@Override
	public boolean identification(String Pseudo, String mdp) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}
}
