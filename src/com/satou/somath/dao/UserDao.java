package com.satou.somath.dao;

public interface UserDao {
    
    boolean identification (String Pseudo, String mdp) throws DAOException;

}