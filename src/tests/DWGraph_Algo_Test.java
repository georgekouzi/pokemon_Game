package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import algorithms.DWGraph_AlgoGW;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import dataStructure.DWGraph_DS;
import dataStructure.NodeData;


class DWGraph_Algo_Test {

	@Test
	void testEmptyGraph() {
		directed_weighted_graph g = new DWGraph_DS();
		dw_graph_algorithms algo = new DWGraph_AlgoGW();
		algo.init(g);
		assertTrue(algo.isConnected());
		assertNull(algo.shortestPath(1, 0));
		assertEquals(algo.shortestPathDist(1, 0),-1);
		assertTrue(algo.save("testEmptyGraph"));

	}

	@Test
	void testOneNodeGraph() {
		directed_weighted_graph g = new DWGraph_DS();
		dw_graph_algorithms algo = new DWGraph_AlgoGW();

		algo.init(g);
		algo.getGraph().addNode(new NodeData(0));

		assertTrue(algo.isConnected());
		assertNull(algo.shortestPath(1, 0));
		assertEquals(algo.shortestPathDist(1, 0),-1);
		assertTrue(algo.save("testOneNodeGraph"));

	}



}
