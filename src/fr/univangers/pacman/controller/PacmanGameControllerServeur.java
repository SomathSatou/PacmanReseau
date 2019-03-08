package fr.univangers.pacman.controller;

import java.io.PrintWriter;

import fr.univangers.pacman.model.PacmanGameServeur;
import fr.univangers.pacman.model.PositionAgent.Dir;

/**
 * Classe qui permet de lier l'interface graphique du Pacman et le jeu Pacman On
 * a des fonctions qui permettent de mettre à jour le temps, et en fonction du
 * bouton l'utilisant, mettre en pause, relancer le jeu à l'état courant,
 * relancer la partie depuis le début ou avancer d'un tour et de contrôler le
 * joueur
 */
public class PacmanGameControllerServeur implements GameController {

    private static final long serialVersionUID = 7744355889303690019L;
    private PacmanGameServeur pacmanGame;
    private PrintWriter       sortie;

    /**
     * 
     * @param pacmanGame
     */
    public PacmanGameControllerServeur( PacmanGameServeur pacmanGame ) {
        this.pacmanGame = pacmanGame;
    }

    public PacmanGameControllerServeur( PacmanGameServeur pacmanGame, PrintWriter sortie ) {
        this.pacmanGame = pacmanGame;
        this.sortie = sortie;
    }

    @Override
    public void setTime( int time ) {
        pacmanGame.setTime( time );
    }

    @Override
    public void pause() {
        pacmanGame.stop();
    }

    @Override
    public void restart() {
        pacmanGame.init();
    }

    @Override
    public void run() {
        pacmanGame.launch();
    }

    @Override
    public void step() {
        pacmanGame.step();
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
