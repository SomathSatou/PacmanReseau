package fr.univangers.pacman.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.Agent.Type;
import fr.univangers.pacman.model.PacmanGameClient;
import fr.univangers.pacman.model.PositionAgent;

public class PacmanGameControleurListener implements Runnable {
    String                   msg;
    final Socket             so;
    final BufferedReader     entree;
    final PrintWriter        sortie;
    private PacmanGameClient pacmanGame;

    public PacmanGameControleurListener( Socket so2, BufferedReader entree2, PrintWriter sortie2,
            PacmanGameClient pacmanGame ) {
        // TODO Auto-generated constructor stub
        this.so = so2;
        this.entree = entree2;
        this.sortie = sortie2;
        this.pacmanGame = pacmanGame;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            while ( so.isConnected() ) {
                msg = entree.readLine();
                // methode pour normaliser msg
                StringTokenizer st = new StringTokenizer( msg );

                switch ( st.nextToken() ) {
                case "gamestate":
                    String tmp;
                    do {
                        tmp = st.nextToken();
                        List<Agent> pacmans = Collections.emptyList();
                        List<Agent> ghosts = Collections.emptyList();
                        List<PositionAgent> pacmans_pos = Collections.emptyList();
                        List<PositionAgent> ghosts_pos = Collections.emptyList();
                        List<PositionAgent> foods = Collections.emptyList();

                        if ( tmp == "P" ) {
                            while ( ( tmp = st.nextToken() ) != "G" || tmp == "FIN" ) {
                                int x = Integer.parseInt( tmp );
                                int y = Integer.parseInt( st.nextToken() );
                                PositionAgent postmp = new PositionAgent( x, y );
                                pacmans.add( new Agent( Type.PACMAN, postmp ) );
                                pacmans_pos.add( postmp );
                            }
                            pacmanGame.setPacman( pacmans, pacmans_pos );
                            while ( ( tmp = st.nextToken() ) != "F" || tmp == "FIN" ) {
                                int x = Integer.parseInt( tmp );
                                int y = Integer.parseInt( st.nextToken() );
                                PositionAgent postmp = new PositionAgent( x, y );
                                ghosts.add( new Agent( Type.GHOST, postmp ) );
                                ghosts_pos.add( postmp );
                            }
                            pacmanGame.setGhost( ghosts, ghosts_pos );
                            while ( ( tmp = st.nextToken() ) != "C" || tmp == "FIN" ) {
                                int x = Integer.parseInt( tmp );
                                int y = Integer.parseInt( st.nextToken() );
                                PositionAgent postmp = new PositionAgent( x, y );
                                foods.add( postmp );
                            }
                            pacmanGame.setFoods( foods );
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
                }

            }
        } catch ( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}