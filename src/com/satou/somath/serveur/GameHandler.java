package com.satou.somath.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.satou.somath.dao.DAOFactory;
import com.satou.somath.dao.UserDao;

import fr.univangers.pacman.model.Game;
import fr.univangers.pacman.model.PositionAgent;

public class GameHandler extends Thread {

    private ArrayList<GameHandler> _listeClient;
    private Socket                 _so;
    private BufferedReader         entree;
    private PrintWriter            sortie;
    private String                 name;
    private static int             ID = 0;
    private int                    _id;
    private String                 pseudo;
    private String                 login;
    private Game                   _jeu;
    private DAOFactory             daoFactory;
    
    public GameHandler( Socket so, ArrayList<GameHandler> listeClient, DAOFactory daofactory ) {
        // TODO Auto-generated constructor stub
        _id = ID++;
        _listeClient = listeClient;
        _so = so;
        daoFactory = daofactory;
        try {
            entree = new BufferedReader( new InputStreamReader( _so.getInputStream() ) );
            sortie = new PrintWriter( _so.getOutputStream() );
        } catch ( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void run() {
    	// TODO Auto-generated method stub
    	System.out.println( "je commence le thread" );

    	UserDao userDao = daoFactory.getUserDao();

    	do {
    		sortie.println( "Pseudo :" );
    		sortie.flush();
    		try {
    			pseudo = entree.readLine();
    			sortie.println( "Password :" );
    			sortie.flush();
    			login = entree.readLine();
    		} catch ( IOException e ) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}while(/*!userDao.identification(pseudo, login)*/ false);

        System.out.println( "ce client est " + pseudo + " sont mdp est " + login );
        /***
         * envoie message de lancement de l'interface de jeux
         */
        sortie.println( "game" );
        sortie.flush();
        Thread recevoir = new Thread( new ReciveServeur( _so, entree, sortie ) );
        recevoir.start();
        while ( !_so.isClosed() ) {

        }

    }

    public String get_name() {
        // TODO Auto-generated method stub
        return name;
    }

    public PrintWriter getSortie() {
        return sortie;
    }

    public void setSortie( PrintWriter sortie ) {
        this.sortie = sortie;
    }

    public Socket get_so() {
        return _so;
    }

    public void set_so( Socket _so ) {
        this._so = _so;
    }

    public void testMove( PositionAgent.Dir dir ) {
        switch ( dir ) {
        case NORTH:
            System.out.println( "dir nord" );
            // appeler la methode move sur le jeu
            break;
        case SOUTH:
            System.out.println( "dir sud" );
            // appeler la methode move sur le jeu
            break;
        case EAST:
            System.out.println( "dir est" );
            // appeler la methode move sur le jeu
            break;
        case WEST:
            System.out.println( "dir ouest" );
            // appeler la methode move sur le jeu
            break;
        default:
            System.out.println( "erreur" );
            break;

        }
    }

}
