package com.satou.somath.dao;
import java.util.ArrayList;

public interface GameDao {
	
	void enrengistrerPartie(String map, String joueur, String resultat, int score ) throws DAOException;

}
