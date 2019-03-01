package com.satou.somath.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PacmanClient {
    static String INPUT_UP       = "inputs up";
    static String INPUT_DOWN     = "inputs down";
    static String INPUT_RIGHT    = "inputs right";
    static String INPUT_LEFT     = "inputs left";
    static String commandeEnvoie = "rien";

    public String getCommandeEnvoie() {
        return commandeEnvoie;
    }

    public void setCommandeEnvoie( String commandeEnvoie ) {
        this.commandeEnvoie = commandeEnvoie;
    }

    static boolean isRunning = true;

    public static void isNotRunning() {
        isRunning = false;
    }

    public static void main( String arg[] ) {

        final Socket so;
        final BufferedReader entree;
        final PrintWriter sortie;
        final Scanner sc = new Scanner( System.in );

        try {
            // ouverture du socket et connexion au serveur
            so = new Socket( "localhost", 5002 ); // on crée le serveur
                                                  // (serveur, port)
            System.out.println( "client connécter au serveur " );

            entree = new BufferedReader( new InputStreamReader( so.getInputStream() ) );
            sortie = new PrintWriter( so.getOutputStream() );

            Thread envoi = new Thread( new Runnable() {

                String msg;

                @Override
                public void run() {
                    while ( so.isConnected() ) {

                        msg = sc.nextLine();
                        sortie.println( msg );
                        sortie.flush();

                        switch ( commandeEnvoie ) {
                        case "bas":
                            sortie.println( INPUT_DOWN );
                            sortie.flush();
                            break;
                        case "haut":
                            sortie.println( INPUT_UP );
                            sortie.flush();
                            break;
                        case "gauche":
                            sortie.println( INPUT_LEFT );
                            sortie.flush();
                            break;
                        case "droite":
                            sortie.println( INPUT_RIGHT );
                            sortie.flush();
                            break;
                        default:
                            break;

                        }
                    }
                }
            } );

            envoi.start();

            Thread recevoir = new Thread( new Recive( so, entree, sortie, sc ) );
            recevoir.start();
            /*
             * while(isRunning){ // tentative pour fermer correctement le client
             * 
             * } System.out.println("je n'attends plus"); recevoir.stop();
             * envoi.stop();
             */
        } catch (

        IOException e ) {
            System.out.println( "problème\n" + e );
        }

        return;
    }
}