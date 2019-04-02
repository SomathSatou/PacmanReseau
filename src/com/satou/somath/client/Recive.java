package com.satou.somath.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.Agent.Type;
import fr.univangers.pacman.model.PacmanGameClient;
import fr.univangers.pacman.model.PositionAgent;
import fr.univangers.pacman.view.ViewSettings;

public class Recive implements Runnable {
    String               msg;
    final Socket         so;
    final BufferedReader entree;
    final PrintWriter    sortie;
    PacmanGameClient     pacmanGame;

    public Recive( Socket so2, BufferedReader entree2, PrintWriter sortie2 ) {
        // TODO Auto-generated constructor stub
        this.so = so2;
        this.entree = entree2;
        this.sortie = sortie2;
        this.pacmanGame = new PacmanGameClient( 1 );

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            while ( so.isConnected() ) {
                msg = entree.readLine();
                // methode pour normaliser msg
                StringTokenizer st = new StringTokenizer( msg );
                if ( msg != null ) {
                    System.out.println( msg );
                }
                switch ( st.nextToken() ) {
                case "game":
                    new ViewSettings( sortie, entree, so, this );
                    break;
                case "Pseudo":
                    break;
                case "Password":
                    break;
                case "gamestate":
                    String tmp;
                    do {
                        tmp = st.nextToken();
                        List<Agent> pacmans = new ArrayList<Agent>();
                        List<Agent> ghosts = new ArrayList<Agent>();
                        List<PositionAgent> pacmans_pos = new ArrayList<PositionAgent>();
                        List<PositionAgent> ghosts_pos = new ArrayList<PositionAgent>();
                        List<PositionAgent> foods = new ArrayList<PositionAgent>();
                        List<PositionAgent> caps = new ArrayList<PositionAgent>();

                        if ( tmp.equalsIgnoreCase( "P" ) ) {
                            tmp = st.nextToken();
                            while ( !tmp.equalsIgnoreCase( "G" ) && !tmp.equalsIgnoreCase( "FIN" ) ) {
                                int x = Integer.parseInt( tmp );
                                int y = Integer.parseInt( st.nextToken() );
                                /*
                                 * switch(st.nextToken()) { case " }
                                 */
                                PositionAgent postmp = new PositionAgent( x, y );
                                Agent ageTmp = new Agent( Type.PACMAN, postmp );
                                pacmans.add( ageTmp );
                                pacmans_pos.add( postmp );
                                tmp = st.nextToken();
                            }
                            tmp = st.nextToken();
                            pacmanGame.setPacman( pacmans, pacmans_pos );
                            while ( !tmp.equalsIgnoreCase( "F" ) && !tmp.equalsIgnoreCase( "FIN" ) ) {
                                int x = Integer.parseInt( tmp );
                                int y = Integer.parseInt( st.nextToken() );
                                PositionAgent postmp = new PositionAgent( x, y );
                                ghosts.add( new Agent( Type.GHOST, postmp ) );
                                ghosts_pos.add( postmp );
                                tmp = st.nextToken();
                            }
                            tmp = st.nextToken();
                            pacmanGame.setGhost( ghosts, ghosts_pos );
                            while ( !tmp.equalsIgnoreCase( "C" ) && !tmp.equalsIgnoreCase( "FIN" ) ) {
                                int x = Integer.parseInt( tmp );
                                int y = Integer.parseInt( st.nextToken() );
                                PositionAgent postmp = new PositionAgent( x, y );
                                foods.add( postmp );
                                tmp = st.nextToken();
                            }
                            pacmanGame.setFoods( foods );
                            tmp = st.nextToken();
                            while ( !tmp.equalsIgnoreCase( "FIN" ) && !tmp.equalsIgnoreCase( "S" ) ) {
                                int x = Integer.parseInt( tmp );
                                int y = Integer.parseInt( st.nextToken() );
                                PositionAgent postmp = new PositionAgent( x, y );
                                caps.add( postmp );
                                tmp = st.nextToken();
                            }
                            pacmanGame.setCaps( caps );
                            tmp = st.nextToken();
                            if ( !tmp.equalsIgnoreCase( "1" ) ) {
                                pacmanGame.setScared( false );
                            } else
                                pacmanGame.setScared( true );
                            ;
                        }

                    } while ( tmp == "FIN" );
                    pacmanGame.notifyViews();
                    break;
                case "gagne":
                    // fin de partie avec victoire
                    break;
                case "perdu":
                    // fin de partie avec défaite
                    break;
                default:
                    System.out.println( "le serveur m'as envoyer un message mais je ne le comprends pas : " + msg );
                    break;

                }
            }
        } catch (

        IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void set( PacmanGameClient pacmanGame2 ) {
        // TODO Auto-generated method stub
        this.pacmanGame = pacmanGame2;
    }
}
