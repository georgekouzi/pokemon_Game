package gameClient;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Server.Game_Server_Ex2;
import algorithms.DWGraph_AlgoGW;
import api.dw_graph_algorithms;
import api.game_service;
import api.node_data;

public class Pokemon_Game implements Runnable {
	private static dw_graph_algorithms algo;
	private static Arena _Arena;
	static long timeToSlip=0;
	private static List<CL_Pokemon>_Pokemon_data;
	private static HashMap<Integer,List<node_data>> agentpath=new HashMap<Integer,List<node_data>>(); 
	private static HashMap<Integer,CL_Pokemon> agentopokimon=new HashMap<Integer,CL_Pokemon>(); 

	
	public Pokemon_Game() {
		_Arena=new Arena();
		algo= new DWGraph_AlgoGW();
	}

	@Override
	public void  run() {
		int scenario_num = 23;
		game_service game= Game_Server_Ex2.getServer(scenario_num);		
//		game.login(311450068);

		//make json file and load it from file 
		reade_data(game.getGraph(),"graph_game");
		//This function allows you to put the Pokemon on the graph and in addition place the agents on the graph.
		putOnBoard(game);

		game.startGame();

		play(game);


		System.out.println(game.toString());
		System.exit(0);

	}

	private static void play(game_service game) {
		while(game.isRunning()) {
			moveAgants(game);
			try {
				Thread.sleep(timeToSlip);
			}
			catch(Exception e) {
				e.printStackTrace();
			}

		}

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



	/*
	 * 
	 */

	private static void moveAgants(game_service game) {
		String lg = game.move();
		List<CL_Agent> AgentsList = Arena.getAgents(lg,algo.getGraph());
		_Pokemon_data=up(game);
		_Arena.setAgents(AgentsList);
		_Arena.setPokemons(_Pokemon_data);


		for(int i=0;i<AgentsList.size();i++) {
			CL_Agent Agent=AgentsList.get(i);
			if(Agent.getNextNode()==-1) {
				int newDest=chooseNextNode(Agent.getSrcNode(),Agent.getID(),Agent,game);
				game.chooseNextEdge(Agent.getID(),newDest);
				System.out.println("Agent: "+Agent.getID()+", val: "+Agent.getValue()+"   turned to node: "+newDest);
			}
		}

	}


	private static int chooseNextNode(int src,int id, CL_Agent a,game_service game){

		if(agentpath.containsKey(id)) {

			int nextDest= agentpath.get(id).remove(0).getKey();


			timeToSlip=(long) (algo.getGraph().getEdge(src,nextDest).getWeight()*250/a.getSpeed());

			if(agentpath.get(id).isEmpty()) {
				agentpath.remove(id);
				agentopokimon.remove(id);


			}

			return nextDest;


		}
		else {
			upPokimon();
			int sortpht=Integer.MAX_VALUE;
			for(int i =0 ; i<_Pokemon_data.size();i++) {
				List<node_data> p1=algo.shortestPath(src, _Pokemon_data.get(i).get_edge().getDest());
				List<node_data> p=algo.shortestPath(src, _Pokemon_data.get(i).get_edge().getSrc());

				if(p.size()<p1.size()) {
					if(p1.size()>1&&p1.size()< sortpht) {
						sortpht=p1.size();
						agentpath.put(id, p1);
						agentopokimon.put(id, _Pokemon_data.get(i));

					}
				}
				else if(p.size()>1&&p.size()<sortpht) {
					sortpht=p.size();
					agentpath.put(id, p);
					agentopokimon.put(id, _Pokemon_data.get(i));

				}
			}
			agentpath.get(id).remove(0);
			int newdest=agentpath.get(id).remove(0).getKey();
			if(agentpath.get(id).isEmpty()) {agentpath.remove(id);
			agentopokimon.remove(id);}

			timeToSlip=(long) (algo.getGraph().getEdge(src,newdest).getWeight()*250/a.getSpeed());

			return newdest;

		}		
	}



	private static void putOnBoard(game_service game) {	
		_Arena.setGraph(algo.getGraph());
		_Pokemon_data=up(game);
		_Arena.setPokemons(_Pokemon_data);
		putAgents(_Pokemon_data,game);
	}
	private static void upPokimon() {
		for(CL_Pokemon a: agentopokimon.values()) {
			Iterator <CL_Pokemon>it = _Pokemon_data.iterator();
			while(it.hasNext()) {
				CL_Pokemon p =it.next();
				if(a.getLocation().x()==p.getLocation().x()&&a.getLocation().y()==p.getLocation().y()) 
					it.remove();

			}

		}

	}


	public static  List<CL_Pokemon>  up(game_service game){
		List<CL_Pokemon> _Pokemon_data=Arena.json2Pokemons(game.getPokemons());;

		Collections.sort(_Pokemon_data, new maxValue());

		for (CL_Pokemon p: _Pokemon_data) {
			Arena.updateEdge(p,algo.getGraph());

		}
		return _Pokemon_data;
	}

	private static  void putAgents(List<CL_Pokemon> _Pokemon_data, game_service game) {
		JsonObject g =new JsonParser().parse(game.toString()).getAsJsonObject();
		int numOfAgents=g.get("GameServer").getAsJsonObject().get("agents").getAsInt();

		for (int i=0;i<numOfAgents;i++) {	
			game.addAgent(_Pokemon_data.get(i).get_edge().getSrc());
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
