package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import Server.Game_Server_Ex2;
import algorithms.DWGraph_AlgoGW;
import api.dw_graph_algorithms;
import api.game_service;
import gameClient.Arena;
import gameClient.CL_Pokemon;
import gameClient.Pokemon_Game;

public class pokemon_game_Test {
	game_service game= Game_Server_Ex2.getServer(0);		
	Arena _Arena=new Arena();
	dw_graph_algorithms algo1 = new DWGraph_AlgoGW();
	Pokemon_Game Pokemon =new Pokemon_Game(game);



	@Test
	void testReade_data() {	
		Pokemon.reade_data(game.getGraph(),"graph_Test");
		algo1.load("graph_Test");
		int edge =algo1.getGraph().edgeSize();
		int nodes= algo1.getGraph().nodeSize();
		assertEquals(edge,22);
		assertEquals(nodes,11);
		assertTrue(algo1.isConnected());

	}

	@Test
	void testPutOnBoard() {
		Pokemon_Game Pokemon =new Pokemon_Game(game);
		game_service game= Game_Server_Ex2.getServer(0);		

		Pokemon.reade_data(game.getGraph(),"graph_Test");
		algo1.load("graph_Test");
		Pokemon.PutOnBoard(game);
		game.startGame();
		Pokemon.moveAgants(game);
		_Arena=Pokemon.getArena();
		assertEquals(_Arena.getAgents(),Pokemon.getArena().getAgents());
		assertEquals(_Arena.getPokemons(),Pokemon.getArena().getPokemons());
		assertEquals(_Arena.getPokemons().size(),Pokemon.getArena().getPokemons().size());
		assertEquals(_Arena.getAgents().size(),Pokemon.getArena().getAgents().size());
		Pokemon.moveAgants(game);

		game.stopGame();



	}
	void testNewPath() {
		Pokemon_Game Pokemon =new Pokemon_Game(game);
		game_service game= Game_Server_Ex2.getServer(22);		

		Pokemon.reade_data(game.getGraph(),"graph_Test");
		algo1.load("graph_Test");
		Pokemon.PutOnBoard(game);
		game.startGame();
		Pokemon.moveAgants(game);
		_Arena=Pokemon.getArena();

		assertEquals(Pokemon.getArena().getAgents().get(0).getID(),_Arena.getAgents().get(0).getID());
		assertEquals(Pokemon.getArena().getAgents().get(0).getSrcNode(),7);
		assertEquals(Pokemon.getArena().getAgents().get(0).getSrcNode(),8);
		Pokemon.moveAgants(game);
		assertEquals(Pokemon.getArena().getAgents().get(0).getID(),_Arena.getAgents().get(0).getID());
		assertEquals(Pokemon.getArena().getAgents().get(0).getSrcNode(),9);
		assertEquals(Pokemon.getArena().getAgents().get(0).getSrcNode(),10);

		game.stopGame();



	}
	void testUntrackedPokemon() {

		Pokemon_Game Pokemon =new Pokemon_Game(game);
		game_service game= Game_Server_Ex2.getServer(22);		

		Pokemon.reade_data(game.getGraph(),"graph_Test");
		algo1.load("graph_Test");
		Pokemon.PutOnBoard(game);
		game.startGame();
		Pokemon.moveAgants(game);
		_Arena.setPokemons(Pokemon.getArena().getPokemons());
		int i=1;
		boolean b =true;
		for(CL_Pokemon a:_Arena.getPokemons() ){

			if(a.getValue()>Pokemon.getArena().getPokemons().get(i++).getValue())
				b=true;
			else { 
				b=false;
			}
		}
		assertTrue(b);



	}

}
