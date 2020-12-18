package gameClient;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import algorithms.DWGraph_AlgoGW;
import api.dw_graph_algorithms;
import api.game_service;
import api.node_data;

public class AgentMove implements Runnable {
	CL_Agent _Agent;
	game_service _game;
	int new_Dest;
	AgentMove(CL_Agent Agent,int newDest,game_service game){
		_Agent =Agent;
		_game=game;
		algo= new DWGraph_AlgoGW();
		AgentPath=new HashMap<Integer,List<node_data>>();
		PokemonToAgent=new HashMap<Integer,CL_Pokemon>(); 
		timeToSlip=0;
		new_Dest=newDest;
	}


	private static long timeToSlip;
	//List of the Pokemons on the graph. 
	private static List<CL_Pokemon>_Pokemon_data;
	// This HashMap Keeps me the agent's ID inside a key and the value of that agent will be a list of the route between him and the Pokemon.
	private static HashMap<Integer,List<node_data>> AgentPath; 
	// This HashMap Keeps the agent's ID inside a key and the value of that agent will be the Pokemon that agent looking for.
	private static HashMap<Integer,CL_Pokemon> PokemonToAgent; 

	private static dw_graph_algorithms algo;


	/**
	 * This function checks if a route has already been set for the agent and then sends the agent to the next node.
	 * If this agent has no Path at all it will send it to the NewPath function 
	 * which will set a new Path for it by using different algorithms and send a new node to this agent.
	 * In addition this function calculates the estimated waiting time by the formula: the agent's way  divide by agent's speed, equal to the waiting time.
	 * @param Agent
	 * @param game
	 * @return -return the next destination.
	 */
	public synchronized int chooseNextNode( CL_Agent Agent,game_service game){

		if(AgentPath.containsKey(Agent.getID())) {
			int nextDest= AgentPath.get(Agent.getID()).remove(0).getKey();
			//			timeToSlip=(long) (algo.getGraph().getEdge(Agent.getSrcNode(),nextDest).getWeight()*260/Agent.getSpeed());
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

	public synchronized int NewPath(CL_Agent Agent) {
		UntrackedPokemon();


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
			else if(p.size()>p1.size()) { 
				if(w>0.0&&w < sortpht) {
					sortpht=w;
					AgentPath.put(Agent.getID(), p);
					PokemonToAgent.put(Agent.getID(), _Pokemon_data.get(i));
				}
			}
			else 
				AgentPath.put(Agent.getID(), p);

		}

		AgentPath.get(Agent.getID()).remove(0);
		//		}
		int newdest=AgentPath.get(Agent.getID()).remove(0).getKey();

		if(AgentPath.get(Agent.getID()).isEmpty()) {
			AgentPath.remove(Agent.getID());
			PokemonToAgent.remove(Agent.getID());
		}
		timeToSlip=(long) (algo.getGraph().getEdge(Agent.getSrcNode(),newdest).getWeight()*260/Agent.getSpeed());

		return newdest;

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












	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}





















	@Override
	public void run() {
		if(_Agent.getNextNode()==-1) {

			_game.chooseNextEdge(_Agent.getID(),new_Dest);
			System.out.println("Agent: "+_Agent.getID()+", val: "+_Agent.getValue()+"   turned to node: "+new_Dest);		

		}
	}
}
