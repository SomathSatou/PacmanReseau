package com.satou.somath.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PacmanClient {
	
static boolean isRunning = true;
	
    public static void isNotRunning(){
    	isRunning = false;
    }
	
	public static void main(String arg[]) {

		final Socket so;
		final BufferedReader entree;
	    final PrintWriter sortie;
	    final Scanner sc=new Scanner(System.in);

	
			try{
				// ouverture du socket et connexion au serveur
				so = new Socket("localhost",5002); // on crée le serveur (serveur, port)
				System.out.println("client connécter au serveur ");
				
				entree = new BufferedReader(new InputStreamReader(so.getInputStream()));
				sortie = new PrintWriter (so.getOutputStream());
				
				Thread envoi = new Thread(new Runnable(){
					String msg;
	
					@Override
					public void run(){
						while(so.isConnected()){
							
							msg = sc.nextLine();
							sortie.println(msg);	
							sortie.flush();
						}
					}
				});
				envoi.start();
				
				Thread recevoir = new Thread(new Runnable() {
	
					String msg;
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							while(so.isConnected()){
								msg = entree.readLine();
								if (msg != null){
									System.out.println(msg);}
								if (msg == "aurevoir"){
									System.out.println("je capte");
									so.close();
									isNotRunning();
								}
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				});
				recevoir.start();
				/*while(isRunning){ // tentative pour fermer correctement le client
					
				}
				System.out.println("je n'attends plus");
				recevoir.stop();
				envoi.stop();*/
				} catch (IOException e) { System.out.println("problème\n"+e);}

			return;
		}
}
