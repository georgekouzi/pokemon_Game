package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import api.directed_weighted_graph;
import api.edge_data;
import api.node_data;
import dataStructure.DWGraph_DS;
import dataStructure.NodeData;



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
		assertEquals(g.getEdge(2, 1).getWeight(),45.6);
		assertEquals(g.getEdge(1, 2).getWeight(),45.6);
		assertEquals(g.edgeSize(),2);
		assertNull(g.getEdge(0,2));
		assertNull(g.getEdge(1,0));

	}


	@Test
	void testRemove() {
		directed_weighted_graph g=new DWGraph_DS();
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
		g.addNode(new NodeData(3));
		g.addNode(new NodeData(0));
		g.connect(1, 2, 95.3);
		g.connect(1, 3, 20.4);
		g.connect(3, 2, 9);
		g.connect(0, 1, 100.543);
		g.connect(0, 2, 10.7);
		//System.out.println(g);
		assertEquals(g.getEdge(1, 2).getWeight(),95.3);		
		g.connect(2, 1, 45.6);
		//System.out.println(g);
		assertEquals(g.getEdge(1, 2).getWeight(),45.6);
		assertEquals(g.edgeSize(),6);
		assertEquals(g.nodeSize(),4);
		assertEquals(g.getEdge(1, 2).getWeight(),45.6);
		assertEquals(g.getEdge(2, 1).getWeight(),45.6);
		assertNull(g.getEdge(2, 3));
		assertEquals(g.getEdge(3, 2).getWeight(),9);
		assertEquals(g.getNode(2),g.removeNode(2));
		
		assertEquals(g.edgeSize(),2);
		
		assertEquals(g.nodeSize(),3);
		assertNull(g.getEdge(1, 2));
		assertNull(g.getEdge(2, 1));
		assertNull(g.getEdge(2, 3));
		assertNull(g.getEdge(3, 2));
		g.addNode(new NodeData(2));
		assertNull(g.getEdge(1, 2));
		g.connect(1, 2, 95.3);
		assertNull(g.getEdge(2, 1));
		assertEquals(g.getEdge(1, 2).getWeight(),95.3);
		assertEquals(g.edgeSize(),3);
		assertEquals(g.nodeSize(),4);
		assertNull(g.removeEdge(2, 1));
		g.removeEdge(1, 2);
		assertEquals(g.edgeSize(),2);
		assertEquals(g.nodeSize(),4);
		assertNull(g.getEdge(1, 2));
		assertNull(g.getEdge(2, 1));

	}

	@Test
	void testGetV_testGetE() {
		directed_weighted_graph g=new DWGraph_DS();
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
		g.addNode(new NodeData(3));
		g.addNode(new NodeData(0));
		g.connect(1, 2, 95.3);
		g.connect(1, 3, 20.4);
		g.connect(3, 2, 9);
		g.connect(0, 1, 100.543);
		g.connect(0, 2, 10.7);
		int i=0;
		for(node_data n: g.getV()) {
			assertEquals(n.getKey(),i++);

		}
		 i=1;
		for(edge_data n: g.getE(0)) {
			assertEquals(n.getDest(),i++);

		}
	
	
	
	
	}











}
