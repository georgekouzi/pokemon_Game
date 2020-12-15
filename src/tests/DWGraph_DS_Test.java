package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import api.directed_weighted_graph;
import dataStructure.DWGraph_DS;
import dataStructure.NodeData;
import ex1.src.WGraph_DS;
import ex1.src.weighted_graph;


class DWGraph_DS_Test {

	@Test
	void TestAddNode() {
		directed_weighted_graph g=new DWGraph_DS();

		g.addNode(new NodeData());
		g.addNode(new NodeData());
		g.addNode(new NodeData());
		g.addNode(new NodeData());
		g.addNode(new NodeData());
		g.addNode(new NodeData());
		assertNotNull(g.getNode(0));
		assertNotNull(g.getNode(1));
		assertNotNull(g.getNode(2));
		assertNotNull(g.getNode(3));
		assertNull(g.getNode(-4));
		assertEquals(g.nodeSize(),6);
		assertEquals(g.edgeSize(),0);	
	
	}


	@Test
	void testConnect() {
		directed_weighted_graph g=new DWGraph_DS();
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
		g.addNode(new NodeData(3));
		g.addNode(new NodeData(4));
		g.connect(1, 2, 95.3);
		//		System.out.println(g);
		assertEquals(g.getEdge(1, 2).getWeight(),95.3);		
 
		
		
		g.connect(2, 1, 45.6);
		//		System.out.println(g);
//		assertEquals(g.getEdge(2, 1),45.6);
//		assertEquals(g.edgeSize(),1);
//		assertEquals(g.getEdge(0,2 ),-1);
//		assertTrue(g.getEdge(1, 2));
//		assertTrue(g.getEdge(2, 1));
//		assertFalse(g.getEdge(1, 0));

	}















}
