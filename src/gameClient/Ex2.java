package gameClient;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Server.Game_Server_Ex2;
import api.DWGraph_AlgoGW;
import api.dw_graph_algorithms;
import api.game_service;
import api.node_data;

public class Ex2 implements Runnable {
	private static dw_graph_algorithms algo;
	private static Arena _Arena;

	public static void main(String[] args) {
		Thread client = new Thread(new Ex2());
		client.start();
	}
	public Ex2() {
		_Arena=new Arena();
		algo= new DWGraph_AlgoGW();

	}
	private void reade_data(String JsonGraph,String fileName) {
		try {
			FileWriter graph_game = new FileWriter(fileName);
			graph_game.write(JsonGraph);
			graph_game.flush();
			graph_game.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		algo.load("graph_game");


	}
	@Override
	public void  run() {
		int scenario_num = 1;
		game_service game= Game_Server_Ex2.getServer(scenario_num);		
		//	game.login(311450068);

		//make json file and load it from file 
		reade_data(game.getGraph(),"graph_game");

		putOnBoard(game);

		game.startGame();

		play(game);

		String res = game.toString();

		System.out.println(res);
		//		System.exit(0);

	}

	private static void play(game_service game) {

		while(game.isRunning()) {

			moveAgants(game);

			try {
				Thread.sleep(100);
			}
			catch(Exception e) {
				e.printStackTrace();
			}

		}

	}

	private static   void moveAgants(game_service game) {
		String lg = game.move();
		List<CL_Agent> AgentsList = Arena.getAgents(lg,algo.getGraph());
		List<CL_Pokemon> PokemonList =Arena.json2Pokemons(game.getPokemons());
		_Arena.setAgents(AgentsList);
		_Arena.setPokemons(PokemonList);
		for(int i=0;i<AgentsList.size();i++) {
			CL_Agent Agent=AgentsList.get(i);
			if(Agent.getNextNode()==-1) {

				int newDest=chooseNextNode(Agent.getSrcNode(),Agent.getID(),game);
				game.chooseNextEdge(Agent.getID(),newDest);
				System.out.println("Agent: "+Agent.getID()+", val: "+Agent.getValue()+"   turned to node: "+newDest);
				//				System.out.println(+game.getAgents());
			}
		}

	}

	private static HashMap<Integer,List<node_data>> agentpath=new HashMap<Integer,List<node_data>>(); 
	private static HashMap<Integer,HashMap<Integer,Integer>> agentopokimon=new HashMap<Integer,HashMap<Integer,Integer>>(); 

	private static int chooseNextNode(int src,int id,game_service game){


		ArrayList<CL_Pokemon>_Pokemon_data= up(game);


		if(agentpath.containsKey(id)) {

			int nextDest= agentpath.get(id).remove(0).getKey();
			System.out.println("innn");
			//			System.out.println(nextDest);
			if(agentpath.get(id).isEmpty()) {
				agentpath.remove(id);
				agentopokimon.remove(id);

			}

			return nextDest;


		}
		else {
			System.out.println("out");

			int sortpht=Integer.MAX_VALUE;
			CL_Pokemon c = null;
			for(int i =0 ; i<_Pokemon_data.size();i++) {

				List<node_data> p=algo.shortestPath(src, _Pokemon_data.get(i).get_edge().getDest());
				List<node_data> p1=algo.shortestPath(src, _Pokemon_data.get(i).get_edge().getSrc());

				if(p.size()<p1.size()) {
					if(p1.size()!=1&&p1.size() < sortpht) {
						sortpht=p1.size();
						agentpath.put(id, p1);
//						agentopokimon.put(id, _Pokemon_data.get(i));
						 c=_Pokemon_data.get(i); 
					}
				}
				else if(p.size()!=1&&p.size()<sortpht) {
					sortpht=p.size();
					agentpath.put(id, p);
//					agentopokimon.put(id, _Pokemon_data.get(i));
					 c=_Pokemon_data.get(i); 

				}
//				System.out.println(p);
//				System.out.println(p1);
			}
//			for(int i=0;i<_Pokemon_data.size();i++) {
//							System.out.println("kkk "+_Pokemon_data.get(i).get_edge().getSrc()+","+_Pokemon_data.get(i).get_edge().getDest());
//						}
//			_Pokemon_data.
			System.out.println(c.get_edge().getSrc()+" , "+c.get_edge().getDest());
			agentpath.get(id).remove(0);
			int newdest=agentpath.get(id).remove(0).getKey();
			if(agentpath.get(id).isEmpty()) {agentpath.remove(id);
//			agentopokimon.remove(id);
			}
			return newdest;

		}		
	}



	private static void putOnBoard(game_service game) {	
		//		System.out.println(algo.getGraph());
		_Arena.setGraph(algo.getGraph());
		ArrayList<CL_Pokemon>_Pokemon_data= up(game);
		_Arena.setPokemons(_Pokemon_data);
		putAgents(_Pokemon_data,game);

		

	}
	public static  ArrayList<CL_Pokemon> up(game_service game){
		ArrayList<CL_Pokemon>_Pokemon_data=Arena.json2Pokemons(game.getPokemons());;
		Collections.sort(_Pokemon_data, new maxValue());

		for (CL_Pokemon p: _Pokemon_data) {
			Arena.updateEdge(p,algo.getGraph());

		}
		//		System.out.println(_Pokemon_data);
		//		for(int i=0;i<_Pokemon_data.size();i++) {
		//			System.out.println("kkk "+_Pokemon_data.get(i).get_edge().getSrc()+","+_Pokemon_data.get(i).get_edge().getDest());
		//		}
		return _Pokemon_data;

	}

	private static  void putAgents( ArrayList<CL_Pokemon> _Pokemon_data,game_service game) {
		JsonObject g =new JsonParser().parse(game.toString()).getAsJsonObject();
		int numOfAgents=g.get("GameServer").getAsJsonObject().get("agents").getAsInt();
		//		System.out.println("numOfAgents= "+numOfAgents);
		//			System.out.println("inn");
		for (int i=0;i<numOfAgents;i++) {
			//			System.out.println("agg = "+_Pokemon_data.get(i).get_edge().getSrc());
			game.addAgent(_Pokemon_data.get(i).get_edge().getSrc());
			//System.out.print ln(game.getAgents());
		}

	}
	/**
	 * inner class allows as to compare between two Pokemon value.
	 */
	private static class maxValue implements Comparator<CL_Pokemon> {

		@Override
		public int compare(CL_Pokemon o1, CL_Pokemon o2) {
			return (int)(o2.getValue()-o1.getValue());
		}

	}
} 
