package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_AlgoGW;
import api.DWGraph_DS;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.game_service;

public class Ex2 implements Runnable {

	public static void main(String[] args) {
		Thread client = new Thread(new Ex2());
		client.start();
	}

	@Override
	public void run() {
		int scenario_num = 11;
		game_service game= Game_Server_Ex2.getServer(scenario_num);		
		String g = game.getGraph();
//		System.out.println(g);
//		directed_weighted_graph g=new DWGraph_DS();

		game.getClass();
		
//		String pks = game.getPokemons();
		
		
		//System.out.println(g);
		
		dw_graph_algorithms algo= new DWGraph_AlgoGW();
//		algo.load(g.getBytes());
		
		
//		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
//		System.out.println(gg);




	}

} 
