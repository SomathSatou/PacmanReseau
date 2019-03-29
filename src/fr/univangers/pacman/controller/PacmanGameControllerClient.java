package fr.univangers.pacman.controller;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import fr.univangers.pacman.model.PacmanGameClient;
import fr.univangers.pacman.model.PositionAgent.Dir;

/**
 * Classe qui permet de lier l'interface graphique du Pacman et le jeu Pacman On
 * a des fonctions qui permettent de mettre à jour le temps, et en fonction du
 * bouton l'utilisant, mettre en pause, relancer le jeu à l'état courant,
 * relancer la partie depuis le début ou avancer d'un tour et de contrôler le
 * joueur
 */
public class PacmanGameControllerClient implements GameController {

    private static final long serialVersionUID = 7744355889303690019L;
    private PacmanGameClient  pacmanGame;
    private PrintWriter       sortie;

    /**
     * 
     * @param pacmanGame
     */
    public PacmanGameControllerClient( PacmanGameClient pacmanGame ) {
        this.pacmanGame = pacmanGame;
    }

    public PacmanGameControllerClient( PacmanGameClient pacmanGame, PrintWriter sortie, Socket so,
            BufferedReader entree ) {
        this.pacmanGame = pacmanGame;
        this.sortie = sortie;
    }

    @Override
    public void setTime( int time ) {
        pacmanGame.setTime( time );
        sortie.println( "time " + time );
        sortie.flush();
    }

    @Override
    public void pause() {
        pacmanGame.stop();
        sortie.println( "stop" );
        sortie.flush();
    }

    @Override
    public void restart() {
        pacmanGame.init();
        sortie.println( "restart" );
        sortie.flush();
    }

    @Override
    public void run() {
        pacmanGame.launch();
        sortie.println( "launch" );
        sortie.flush();
    }

    @Override
    public void step() {
        pacmanGame.step();
        sortie.println( "step" );
        sortie.flush();
    }

    @Override
    public void movePlayer1( Dir dir ) {
        pacmanGame.movePacmanPlayer1( dir );
    }

    @Override
    public void movePlayer2( Dir dir ) {
        pacmanGame.movePacmanPlayer2( dir );
    }

    @Override
    public void send( String mes ) {
        // TODO Auto-generated method stub
        sortie.println( mes );
        sortie.flush();
    }

}
