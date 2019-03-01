package com.satou.somath.serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PacmanServeur {
    public static ArrayList<GameHandler> listeClient = new ArrayList<>();

    public static void main( String arg[] ) {
        int p = 5002; // le port d’écoute
        final ServerSocket ecoute;
        Socket so;

        try {
            ecoute = new ServerSocket( p ); // on crée le serveur
            System.out.println( "serveur Pacman mis en place " );
            // ajouter un while et redefinir les thread dans des classe
            while ( true ) {
                so = ecoute.accept();
                // lancement du thread des clients
                System.out.println( "connexion d'un nouveau client" );
                GameHandler ch = new GameHandler( so, listeClient );
                listeClient.add( ch );
                ch.start();
            }
        } catch ( IOException e ) {
            System.out.println( "problème\n" + e );
        }

    }

    public static synchronized void remove( GameHandler GameHandler ) {
        // TODO Auto-generated method stub
        listeClient.remove( GameHandler );
    }

    public static synchronized void envoi( String msg, GameHandler cli ) {
        // TODO Auto-generated method stub

        ArrayList<GameHandler> copy = (ArrayList<GameHandler>) listeClient.clone();
        for ( GameHandler elt : copy ) {
            if ( elt.get_so() != cli.get_so() ) {
                elt.getSortie().println( cli.get_name() + " : " + msg );
                elt.getSortie().flush();
            }
        }
    }

    public static synchronized void envoiS( String msg ) {
        // TODO Auto-generated method stub

        ArrayList<GameHandler> copy = (ArrayList<GameHandler>) listeClient.clone();
        for ( GameHandler elt : copy ) {
            elt.getSortie().println( "[serveur] : " + msg );
            elt.getSortie().flush();
        }
    }

    public static synchronized void combat( GameHandler cli ) {
        for ( GameHandler elt : listeClient ) {
            elt.getSortie().println( "[serveur] : \" Notre héros " + cli.get_name() + " a rencontrer un slime" +
                    "venez l'aider dans ce combat épique \" ( !join pour rejoindre le combat ) " );
        }

    }
}
