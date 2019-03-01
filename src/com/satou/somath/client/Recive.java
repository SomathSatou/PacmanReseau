package com.satou.somath.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Recive implements Runnable {
    String               msg;
    final Socket         so;
    final BufferedReader entree;
    final PrintWriter    sortie;
    final Scanner        sc;
    PacmanClient         pc;

    public Recive( Socket so2, BufferedReader entree2, PrintWriter sortie2, Scanner sc2 ) {
        // TODO Auto-generated constructor stub
        this.so = so2;
        this.entree = entree2;
        this.sortie = sortie2;
        this.sc = sc2;
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
                    // new ViewSettings();
                    break;
                case "gagne":
                    // fin de partie avec victoire
                    break;
                case "perdu":
                    // fin de partie avec défaite
                    break;
                case "Pseudo":
                    break;
                case "Password":
                    break;
                /**
                 * case "gameState" : traitement du message mise a jour du
                 * modèle prime break;
                 */
                default:
                    System.out.println( "le serveur m'as envoyer un message mais je ne le comprends pas : " + msg );
                    break;

                }
            }
        } catch ( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
