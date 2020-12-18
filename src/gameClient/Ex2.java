package gameClient;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import Server.Game_Server_Ex2;
import api.game_service;
/**
 * This class allows you to run the Pokemon game by running 
 * several classes at the same time as a Pokemon_Game class,MyMusic class and more.
 * @author George kouzy and Dolev Saadia.
 */
public class Ex2 implements Runnable  {
	private final static AtomicBoolean run = new AtomicBoolean(false);
	private static int scenaio=-1;
	private static GUI _win;
	private static File folderInput = new File("src\\images\\winningImage.png");
	private static long id;
	public static void main(String args[]){
		
		if (args[1] == null) {
			StartWithManu();
		}
		id=Integer.parseInt(args[1]);
		scenaio=Integer.parseInt(args[1]);
		Thread client = new Thread(new Ex2());
		Thread Music = new Thread(new MyMusic("file//Pokemon.mp3"));
		client.start();
		Music.start();
		if(!client.isAlive()) 
		
			run.set(false);	
		
        } 

	
	private static void StartWithManu() {
		MainManu main =new MainManu();
		while(!main.get_start()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while(!main.getLoginPanel().get_start()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 

		scenaio=main.getLoginPanel().get_scenario();
		id=main.getLoginPanel().get_id();
	
	}

	@Override
	public void  run() {


		game_service game= Game_Server_Ex2.getServer(scenaio);		
		game.login(id);
		Pokemon_Game Pokemon =new Pokemon_Game(game);

		//make json file and load it from file 
		Pokemon.reade_data(game.getGraph(),"graph_game");
		//This function allows you to put the Pokemon on the graph and in addition place the agents on the graph.
		Pokemon.PutOnBoard(game);
		//
		up(Pokemon.getArena(),game);
		
		_win.show();
		game.startGame();
		int ind=0;

		while(game.isRunning()) {		
			if(ind%1==0) {_win.repaint();}

			try {
				Pokemon.moveAgants(game);
				Thread.sleep( Pokemon.getTimeToSlip() );
				ind++;

			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		}
		System.out.println(game.toString());
		
		System.exit(0);

	}

	
	public void up(Arena arena,game_service game) {
		_win = new GUI(game);
		_win.update(arena);
		
	}
	
	
}
