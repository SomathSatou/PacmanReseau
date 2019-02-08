package com.satou.somath.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.satou.somath.serveur.GameHandler;
import fr.univangers.pacman.model.*;

public class GameHandler extends Thread{

	private ArrayList<GameHandler> _listeClient;
	private Socket _so;
	private BufferedReader entree;
    private PrintWriter sortie;
    private String name;
    private static int ID = 0;
	private int _id;
	private String pseudo;
	private String login; 
	private Game _jeu;
    
	public GameHandler(Socket so, ArrayList<GameHandler> listeClient) {
		// TODO Auto-generated constructor stub
		_id = ID++;
		_listeClient = listeClient;	
		_so = so;
		try {
			entree = new BufferedReader(new InputStreamReader(_so.getInputStream()));
			sortie = new PrintWriter (_so.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("je commence le thread");
		sortie.println("Pseudo : ");
		sortie.flush();
		try {
			pseudo = entree.readLine();
			sortie.println("Password : ");
			sortie.flush();
			login = entree.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("ce client est "+pseudo+ " sont mdp est "+login );

		while(!_so.isClosed()){
			
		}
		
	}

	public String get_name() {
		// TODO Auto-generated method stub
		return name;
	}

	public PrintWriter getSortie() {
		return sortie;
	}

	public void setSortie(PrintWriter sortie) {
		this.sortie = sortie;
	}

	public Socket get_so() {
		return _so;
	}

	public void set_so(Socket _so) {
		this._so = _so;
	}

}
