package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import algorithms.DWGraph_AlgoGW;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.node_data;
import dataStructure.DWGraph_DS;
import dataStructure.NodeData;



class DWGraph_Algo_Test {
	//
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
	//
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
	@Test
	void testGraph() {
		directed_weighted_graph g = new DWGraph_DS();
		dw_graph_algorithms algo = new DWGraph_AlgoGW();

		algo.init(g);
		algo.getGraph().addNode(new NodeData(1));
		algo.getGraph().addNode(new NodeData(2));

		assertTrue(algo.save("testGraph"));
		assertFalse(algo.isConnected());
		assertNull(algo.shortestPath(1, 2));
		assertEquals(algo.shortestPathDist(1, 2),-1);
		assertEquals(algo.shortestPathDist(2, 1),-1);
		algo.getGraph().connect(2, 1, 56.876);
		assertFalse(algo.isConnected());
		assertEquals(algo.shortestPathDist(1, 2),-1);
		assertEquals(algo.shortestPathDist(2, 1),56.876);
		algo.getGraph().connect(1, 2, 51.340);
		assertTrue(algo.isConnected());
		assertEquals(algo.getGraph().getEdge(2, 1).getWeight(),51.340);
		assertEquals(algo.shortestPathDist(1, 2),51.340);
		assertEquals(algo.shortestPathDist(2, 1),51.340);
		assertNotNull(algo.shortestPath(1, 2));
		assertEquals(algo.shortestPath(1, 2).size(),2);
		assertTrue(algo.save("testGraph_1"));

	}

	@Test
	void testGraph1() {
		directed_weighted_graph g = new DWGraph_DS(),g1;
		dw_graph_algorithms algo = new DWGraph_AlgoGW();
		
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
		g.addNode(new NodeData(3));
		g.addNode(new NodeData(4));
		g.addNode(new NodeData(5));
		g.addNode(new NodeData(6));
		g.connect(1, 3, 9);
		g.connect(1, 2, 7);
		g.connect(1, 6, 14);
		g.connect(2, 3, 10);
		g.connect(2, 4, 15);
		g.connect(3, 6, 2);
		g.connect(3, 4, 11);
		g.connect(4, 5, 6);
		g.connect(6, 5, 9);
		assertTrue(algo.isConnected());
		algo.init(g);
		assertFalse(algo.isConnected());
		g.connect(5, 3, 2.1211);
		g.connect(4, 2, 2.34322);
		g.connect(2, 1, 3.45);
		assertTrue(algo.isConnected());
		assertEquals(algo.shortestPathDist(1, 5),11.79322);
		assertEquals(algo.shortestPathDist(6, 4),22.1211);
		assertEquals(algo.shortestPathDist(3, 5),11);
		g.addNode(new NodeData(7));
		assertFalse(algo.isConnected());
		g.removeNode(7);
		assertTrue(algo.isConnected());
				g.removeEdge(1, 3);
				g.removeEdge(1, 2);
				g.removeEdge(1, 6);
				assertFalse(algo.isConnected());
				assertNotNull(g.getNode(1));
				assertNull(g.getEdge(3, 1));
				g.connect(1, 3, 9);
				g.connect(1, 2, 7);
				g.connect(1, 6, 14);		
				g1=algo.copy();
				assertEquals(g1.nodeSize(),g.nodeSize());
				assertEquals(g1.edgeSize(),g.edgeSize());
				g1.removeNode(3);
				assertTrue(algo.isConnected());
				algo.init(g1);
				assertFalse(algo.isConnected());
				assertNotEquals(g1.nodeSize(),g.nodeSize());
				assertNotEquals(g1.edgeSize(),g.edgeSize());
				assertEquals(g1.nodeSize(),5);
				assertEquals(g1.edgeSize(),7);
				assertEquals(g.nodeSize(),6);
				assertEquals(g.edgeSize(),12);
				algo.init(g);
				assertEquals(algo.shortestPathDist(1, 5),15.34322);
				assertEquals(algo.shortestPathDist(4, 6),10.1211);
				assertEquals(algo.shortestPathDist(0, 10),-1.0);
				int expected []= {4,5,3,6} ;
				int actual [] = new int [4];
				int i=0;
				for(node_data n :algo.shortestPath(4, 6)) {
					actual[i++]=n.getKey();
				}
				assertArrayEquals(expected,actual);
		
				int expected2 []= {1,2,4,5};
				int actual2 [] = new int [4];
				i=0;
				for(node_data n :algo.shortestPath(1, 5)) {
					actual2[i++]=n.getKey();
				}
				assertArrayEquals(expected2,actual2);
		
				assertTrue(algo.save("testGraph1"));
				assertTrue(algo.load("testGraph1"));
//				System.out.println(algo.getGraph());
				assertEquals(algo.shortestPathDist(1, 5),15.34322);
				algo.init(g1);
				assertTrue(algo.save("newfile1"));
				assertEquals(algo.getGraph().nodeSize(),5);
				assertEquals(algo.getGraph().edgeSize(),7);
				assertTrue(algo.load("testGraph1"));
				assertNotEquals(algo.getGraph().nodeSize(),5);
				assertNotEquals(algo.getGraph().edgeSize(),5);
				assertEquals(algo.getGraph().nodeSize(),6);
				assertEquals(algo.getGraph().edgeSize(),12);
				assertTrue(algo.load("testEmptyGraph"));
				assertEquals(algo.getGraph().nodeSize(),0);
				assertEquals(algo.getGraph().edgeSize(),0);
				assertTrue(algo.load("testOneNodeGraph"));
				assertEquals(algo.getGraph().nodeSize(),1);
				assertEquals(algo.getGraph().edgeSize(),0);
				assertTrue(algo.load("testGraph"));
				assertEquals(algo.getGraph().nodeSize(),2);
				assertEquals(algo.getGraph().edgeSize(),0);
				assertTrue(algo.load("testGraph1"));

	}
	@Test
	public void test_Big_Graph() {
		//create graph with 11,999,957 connected and 1,000,000 nodes
		directed_weighted_graph g1=new DWGraph_DS();
		int size =  1000*1000;
		for (int i = 0; i <size; i++) {
			g1.addNode(new NodeData());
		}

		for (int i = 0; i <size*10; i++) {
			g1.connect(size-2, i, 0.23); 
			g1.connect(i, size-2, 0.23); 
			g1.connect(i, size-4, 0.23); 
			g1.connect(size-4, i, 0.23); 
			g1.connect(i, size-8, 0.23); 
			g1.connect(size-8, i, 0.23); 
			g1.connect(i, size-10, 0.23); 
			g1.connect(size-10, i, 0.23); 
			g1.connect(i, size-140, 0.23); 
			g1.connect(size-12, i, 0.23); 
			g1.connect(i, size-1, 0.23); 
			g1.connect(size-1, i, 0.23); 
		}

		dw_graph_algorithms algo = new DWGraph_AlgoGW();
		algo.init(g1);
		assertTrue(algo.isConnected());

	}




}
