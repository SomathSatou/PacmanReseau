package com.satou.somath.serveur;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import com.satou.somath.dao.GameInformation;
import com.satou.somath.definition.Mode;
import com.satou.somath.definition.StrategyGhost;
import com.satou.somath.definition.StrategyPacman;

import fr.univangers.pacman.controller.PacmanGameControllerServeur;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.PacmanGameServeur;
import fr.univangers.pacman.model.PositionAgent.Dir;
import fr.univangers.pacman.view.ViewGame;

public class ReciveServeur implements Runnable {
    String               msg;
    final Socket         so;
    final BufferedReader entree;
    final PrintWriter    sortie;
    private GameInformation gameInformation;
    // le jeux panmangame a terme mais pacmanwiew en attendant

    public ReciveServeur( Socket so2, BufferedReader entree2, PrintWriter sortie2, GameInformation gameInformation ) {
        // TODO Auto-generated constructor stub
        this.so = so2;
        this.entree = entree2;
        this.sortie = sortie2;
        this.gameInformation = gameInformation;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        File directory = new File( "res/layouts" );
        PacmanGameServeur pacmanGame;
        try {
            pacmanGame = new PacmanGameServeur( 200, new Maze( directory.listFiles()[0].toString() ),
                    StrategyPacman.BASIC, StrategyGhost.TRACKING,
                    Mode.ONEPLAYER, sortie, this.gameInformation );

            while ( so.isConnected() ) {
                msg = entree.readLine();
                // methode pour normaliser msg
                StringTokenizer st = new StringTokenizer( msg );
                // debug
                if ( msg != null ) {
                    System.out.println( msg );
                }
                switch ( st.nextToken() ) {
                /**
                 * refaire le swich pour les message client
                 * 
                 */
                case "game":
                    /*
                     * pacmanGame = new PacmanGame( 200, new Maze(
                     * directory.listFiles()[0].toString() ),
                     * StrategyPacman.BASIC, StrategyGhost.TRACKING,
                     * Mode.ONEPLAYER );
                     */

                    PacmanGameControllerServeur pacmanGameController = new PacmanGameControllerServeur( pacmanGame,
                            sortie );
                    /**
                     * rajout méthode d'envoie des donnée d'initialisation coté
                     * serveur sendInitCS( getNbTurn(),
                     * ((File)listMaze.getSelectedItem() ).getPath() ,
                     * listStrategyGhost.getSelectedIndex() ,
                     * listStrategyPacman.getSelectedIndex() ,
                     * listMode.getSelectedIndex() )
                     */
                    new ViewGame( pacmanGame, pacmanGameController,
                            new Maze( directory.listFiles()[0].toString() ), "PacmanServeur" );
                    break;

                case "gauche":
                    pacmanGame.movePacmanPlayer1( Dir.WEST );
                    break;

                case "droite":
                    pacmanGame.movePacmanPlayer1( Dir.EAST );
                    break;

                case "haut":
                    pacmanGame.movePacmanPlayer1( Dir.NORTH );
                    break;

                case "bas":
                    pacmanGame.movePacmanPlayer1( Dir.SOUTH );
                    break;

                case "time":
                    pacmanGame.setTime( Integer.parseInt( st.nextToken() ) );
                    break;

                case "stop":
                    pacmanGame.stop();
                    break;

                case "restart":
                    pacmanGame.init();
                    break;

                case "launch":
                    pacmanGame.launch();
                    break;

                case "step":
                    pacmanGame.step();
                    break;

                default:
                    System.out.println( "le client m'as envoyer un message mais je ne le comprends pas : " + msg );
                    break;

                }
            }
        } catch ( Exception e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
