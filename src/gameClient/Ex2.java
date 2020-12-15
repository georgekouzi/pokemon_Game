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
	private static int scenaio;
	private static GUI _win;
	private static File folderInput = new File("src\\images\\winningImage.png");


	public static void main(String args[]){

		Thread client = new Thread(new Ex2());
		Thread Music = new Thread(new MyMusic("Pokemon.mp3"));
		client.start();
		Music.start();
		if(!client.isAlive()) 
			run.set(false);	

	}


	@Override
	public void  run() {

		Pokemon_Game Pokemon =new Pokemon_Game();

		int scenario_num = 0;
		game_service game= Game_Server_Ex2.getServer(scenario_num);		
		//		game.login(311450068);

		//		game_service game= Game_Server_Ex2.getServer(this.scenaio);		
		//		game.login(311450068);

		//make json file and load it from file 
		Pokemon.reade_data(game.getGraph(),"graph_game");
		//This function allows you to put the Pokemon on the graph and in addition place the agents on the graph.
		Pokemon.PutOnBoard(game);



		//		_win.show();
		System.out.println("ihh");
		game.startGame();
		////		_win.setTitle("Pokemon");
		//		int ind=0;
		//		int time =1000/60;
		while(game.isRunning()) {
			//		
			try {
				//				if(ind%1==0) {}
				Pokemon.moveAgants(game);
				Thread.sleep(Pokemon.getTimeToSlip());
				//				 _win.repaint();
				//				ind++;

			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(game.toString());
		System.exit(0);
		//		
	}


}