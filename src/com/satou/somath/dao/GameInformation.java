package com.satou.somath.dao;

public class GameInformation {
	
	String map;
	String joueur;
	String resultat;
	int score;
	DAOFactory daoFactory;
	
	public GameInformation(DAOFactory daoFactory) {
		this.daoFactory=daoFactory;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public void setJoueur(String joueur) {
		this.joueur = joueur;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void enrengistrerPartie() {
		GameDao gameDao = daoFactory.getGameDao();
		
		if(map.length()>40) {
			map = map.substring(map.length()-40);
		}
		
		if(joueur.length()>15) {
			joueur = joueur.substring(0, 15);
		}
		
		if(resultat.length()>1) {
			resultat = resultat.substring(0, 1);
		}
		
		gameDao.enrengistrerPartie(map, joueur, resultat, score);
	}
}
