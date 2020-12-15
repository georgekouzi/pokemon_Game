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
import algorithms.DWGraph_AlgoGW;
import api.dw_graph_algorithms;
import api.game_service;
import api.node_data;
/**
 * This class allows you to run the Pokemon game and display the data on the screen.
 * The information displayed on the screen is:
 * displays the transitions of each agent from one node to the other and the score he accumulated until he reached that node.
 * The score is obtained by catching Pokemon on the ribs.
 * This class inherits from the Runnable interface to allow parallel work.
 * In this class we will use several classes:DWGraph_AlgoGW class, node_data class ,Arena class and more.
 * We use inner class maxValue that allows as to compare between two Pokemon value.
 * This class has several functions: reade_data,PutOnBoard,play,UntrackedPokemon,moveAgants,chooseNextNode,PutOnBoard,putAgents and PokemonUpdate class.
 * In addition, a stop must be made between the call moveAgants function in order to allow all Sunnis to cross the rib
 * We will use the algorithm of the shortest path in order to optimize the movement of the agents.
 *
 * @author George kouzy and Dolev Saadia.
 *
 */
public class Pokemon_Game {
	private static dw_graph_algorithms algo;
	private static Arena _Arena;
	//The time to wait for the agents to reach the next node
	private static long timeToSlip;
	//List of the Pokemons on the graph. 
	private static List<CL_Pokemon>_Pokemon_data;
	// This HashMap Keeps me the agent's ID inside a key and the value of that agent will be a list of the route between him and the Pokemon.
	private static HashMap<Integer,List<node_data>> AgentPath; 
	// This HashMap Keeps the agent's ID inside a key and the value of that agent will be the Pokemon that agent looking for.
	private static HashMap<Integer,CL_Pokemon> PokemonToAgent; 
	
	public Pokemon_Game() {
		_Arena=new Arena();
		algo= new DWGraph_AlgoGW();
		AgentPath=new HashMap<Integer,List<node_data>>();
		PokemonToAgent=new HashMap<Integer,CL_Pokemon>(); 
		timeToSlip=0;	
	
		
		
	}
	public Pokemon_Game(int scenaio) {
		_Arena=new Arena();
		algo= new DWGraph_AlgoGW();
		AgentPath=new HashMap<Integer,List<node_data>>();
		PokemonToAgent=new HashMap<Integer,CL_Pokemon>(); 
		timeToSlip=0;	
		
		
	}
	
	/**
	 * This function allows us to move the agents and wait until the agents reach the next node.
	 * we use Thread with sleep function that accepts the variable - timeToSlip.  
	 * @param game
	 */
//	public static void play(game_service game) {
//		int ind=0;
//		int time =1000/60;
//		
//		while(game.isRunning()) {
//			moveAgants(game);
//			
//
//			try {
//				if(ind%1==0) {
//				Thread.sleep(time);
////				 _win.repaint();
//				ind++;
//				}
//				
//			}
//
//			catch(Exception e) {
//				e.printStackTrace();
//			}
//
//		}
//
//	}
	public  long getTimeToSlip() {
		return timeToSlip;
	}
	
	
	 /**
	 * This function allows us to write a Json file indicating the graph of the given game service 
	 * and load it with the load function from the DWGraph_AlgoGW class.
	 * @param JsonGraph
	 * @param fileName
	 */

	public  void reade_data(String JsonGraph,String fileName) {
		try {
			FileWriter graph_game = new FileWriter(fileName);
			System.out.println("inn");

			graph_game.write(JsonGraph);
			graph_game.flush();
			graph_game.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		algo.load(fileName);


	}

	/**
	 * This function moves all our agents to their next destination by the move function- 
	 * move function:this function returns a string that contains all the new information of the graph: where each agent should be located, 
	 * In addition, it creates new Pokemon in new locations in case agents catch them.
	 * We will then update the board (Arena) on the location of the agents and the location of the Pokemon by the functions setAgents and setPokemons.
	 * Then, the function will go through all the agents we have in the graph and look for the next target of each agent.
	 * @param game
	 */

	public  void moveAgants(game_service game) {
		String lg = game.move();
		List<CL_Agent> AgentsList = Arena.getAgents(lg,algo.getGraph());
		PokemonUpdate(game);
		_Arena.setAgents(AgentsList);
		_Arena.setPokemons(_Pokemon_data);
		
		for(int i=0;i<AgentsList.size();i++) {
			CL_Agent Agent=AgentsList.get(i);
			//If the agent reached the previous destination
			if(Agent.getNextNode()==-1) {
				int newDest=chooseNextNode(Agent,game);
				game.chooseNextEdge(Agent.getID(),newDest);
				System.out.println("Agent: "+Agent.getID()+", val: "+Agent.getValue()+"   turned to node: "+newDest);
			}
		}

	}

	/**
	 * This function checks if a route has already been set for the agent and then sends the agent to the next node.
	 * If this agent has no Path at all it will send it to the NewPath function 
	 * which will set a new Path for it by using different algorithms and send a new node to this agent.
	 * In addition this function calculates the estimated waiting time by the formula: the agent's way  divide by agent's speed, equal to the waiting time.
	 * @param Agent
	 * @param game
	 * @return -return the next destination.
	 */
	public int chooseNextNode( CL_Agent Agent,game_service game){

		if(AgentPath.containsKey(Agent.getID())) {
			int nextDest= AgentPath.get(Agent.getID()).remove(0).getKey();
			timeToSlip=(long) (algo.getGraph().getEdge(Agent.getSrcNode(),nextDest).getWeight()*250/Agent.getSpeed());
			if(AgentPath.get(Agent.getID()).isEmpty()) {
				AgentPath.remove(Agent.getID());
				PokemonToAgent.remove(Agent.getID());
			}
			return nextDest;
		}
		else return NewPath(Agent);

	}

	/**
	 *In the first step this function will send our Pokemon list in order to update it to a short list of Pokemon that other agents are not looking for.
	 * Next we will go over the Pokemon list and look for the shortest path between the Agent and the Pokemons.
	 * Since it is not known where the Pokemon is in relation to the agent we will create two lists of the shortest path 
	 * and take the longer one which will ensure that we reach the desired node in order to catch the Pokemon.
	 * Then we update the agent's path (AgentPath) 
	 * and the Pokemon that the agent is on his way to capture.
	 * we remove the first node on the sorted path because we do not need the starting node because the agent is already on it.
	 * In addition this function calculates the estimated waiting time by the formula: the agent's way  divide by agent's speed, equal to the waiting time.
	 * @param Agent
	 * @return -return the next destination.
	 */

	public int NewPath(CL_Agent Agent) {
		UntrackedPokemon();


		//		int sortpht=Integer.MAX_VALUE;
		double sortpht=Double.MAX_VALUE;

		for(int i =0 ; i<_Pokemon_data.size();i++) {
			List<node_data> p1=algo.shortestPath(Agent.getSrcNode(), _Pokemon_data.get(i).get_edge().getDest());
			List<node_data> p=algo.shortestPath(Agent.getSrcNode(), _Pokemon_data.get(i).get_edge().getSrc());
			double w1=algo.shortestPathDist(Agent.getSrcNode(), _Pokemon_data.get(i).get_edge().getDest());
			double w=algo.shortestPathDist(Agent.getSrcNode(), _Pokemon_data.get(i).get_edge().getSrc());


			if(p.size()<p1.size()) {
				if(w1>0.0&&w1< sortpht) {
					sortpht=w1;
					AgentPath.put(Agent.getID(), p1);
					PokemonToAgent.put(Agent.getID(), _Pokemon_data.get(i));
				}
			}
			else if(w>0.0&&w< sortpht) {
				sortpht=w;
				AgentPath.put(Agent.getID(), p);
				PokemonToAgent.put(Agent.getID(), _Pokemon_data.get(i));

			}
		}
		AgentPath.get(Agent.getID()).remove(0);
		int newdest=AgentPath.get(Agent.getID()).remove(0).getKey();
		if(AgentPath.get(Agent.getID()).isEmpty()) {
			AgentPath.remove(Agent.getID());
			PokemonToAgent.remove(Agent.getID());
		}
		timeToSlip=(long) (algo.getGraph().getEdge(Agent.getSrcNode(),newdest).getWeight()*250/Agent.getSpeed());
		return newdest;

	}


	/**
	 * This function updates the board in the graph of the current phase,
	 * and places the Pokemon on the same graph and sent to the putAgents function to place the agents on the graph.
	 * @param game
	 */
	public void PutOnBoard(game_service game) {	
		_Arena.setGraph(algo.getGraph());
		PokemonUpdate(game);
		_Arena.setPokemons(_Pokemon_data);
		putAgents(game);
	
//		_win = new GUI();
//		_win.setSize(1000, 700);
//		_win.update(_Arena);
//		_win.setVisible(true);
//		_win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
		
	}


	/**
	 * This function allows you to update the list of _Pokemon_data that no agent is yet looking for.
	 */
	public void UntrackedPokemon() {
		for(CL_Pokemon a: PokemonToAgent.values()) {
			Iterator <CL_Pokemon>it = _Pokemon_data.iterator();
			while(it.hasNext()) {
				CL_Pokemon p =it.next();
				if(a.getLocation().x()==p.getLocation().x()&&a.getLocation().y()==p.getLocation().y()) 
					it.remove();

			}

		}

	}
	/**
	 * Updated list of all the Pokemon we haven't caught yet 
	 * And sorts them by their score from the big to the small score.
	 * @param game
	 */

	public void PokemonUpdate(game_service game){
		_Pokemon_data=Arena.json2Pokemons(game.getPokemons());;
		Collections.sort(_Pokemon_data, new maxValue());
		for (CL_Pokemon p: _Pokemon_data) {
			Arena.updateEdge(p,algo.getGraph());
			
		}
	}
	/**
	 * This function is used by us for the beginning of the game:
	 * it allows the agents to be placed on the graph.
	 * we can know the number of agents by read json information of the game.
	 * We place the agents at the node that closest to the Pokemons.
	 */
	public void putAgents(game_service game) {
		JsonObject g =new JsonParser().parse(game.toString()).getAsJsonObject();
		int numOfAgents=g.get("GameServer").getAsJsonObject().get("agents").getAsInt();

		for (int i=0;i<numOfAgents;i++) {	
			game.addAgent(_Pokemon_data.get(i).get_edge().getSrc());
		}

	}
	/**
	 * inner class allows as to compare between two Pokemon value.
	 */
	public  class maxValue implements Comparator<CL_Pokemon> {

		@Override
		public int compare(CL_Pokemon pokemon_1, CL_Pokemon pokemon_2) {
			return (int)(pokemon_2.getValue()-pokemon_1.getValue());
		}

	}
} 
