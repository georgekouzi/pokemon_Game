package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import api.DWGraph_DS;
import api.NodeData;
import api.directed_weighted_graph;

class graph_test {

	@Test
	void testAddNode() {
		directed_weighted_graph g =new DWGraph_DS();
		g.addNode(new NodeData());
		g.addNode(new NodeData());
		g.addNode(new NodeData());
		g.addNode(new NodeData());
		//		System.out.println(g);
		assertNotNull(g.getNode(0));
		assertNotNull(g.getNode(1));
		assertNotNull(g.getNode(2));
		assertNotNull(g.getNode(3));
		assertNull(g.getNode(-4));
		assertEquals(g.nodeSize(),4);
		assertEquals(g.edgeSize(),0);

	}
	@Test
	void testConnect() {
		directed_weighted_graph g1 =new DWGraph_DS();
		g1.addNode(new NodeData());
		g1.addNode(new NodeData());
		g1.addNode(new NodeData());
		g1.addNode(new NodeData());
		System.out.println(g1.getNode(4).getKey());
		//			g.connect(1, 2, 95.3);
		//		System.out.println(g);
		//			assertEquals(g.getEdge(1, 2).getWeight(),95.3);		
		//			g.connect(2, 1, 45.6);
		//		System.out.println(g);
		//			assertEquals(g.getEdge(2, 1),45.6);
		//			assertEquals(g.edgeSize(),1);
		//			assertEquals(g.getEdge(0,2 ),-1);
		//			assertTrue(g.hasEdge(1, 2));
		//			assertTrue(g.hasEdge(2, 1));
		//			assertFalse(g.hasEdge(1, 0));

	}
	//		@Test
	//		void testRemove() {
	//			directed_weighted_graph g =new DWGraph_DS();
	//			g.addNode(new NodeData());
	//			g.addNode(new NodeData());
	//			g.addNode(new NodeData());
	//			g.addNode(new NodeData());
	//			g.connect(1, 2, 95.3);
	//			g.connect(1, 3, 20.4);
	//			g.connect(3, 2, 9);
	//			g.connect(0, 1, 100.543);
	//			g.connect(0, 2, 10.7);
	//			//System.out.println(g);
	//			assertEquals(g.getEdge(1, 2),95.3);		
	//			g.connect(2, 1, 45.6);
	//			//System.out.println(g);
	//			assertEquals(g.getEdge(1, 2),45.6);
	//			assertEquals(g.edgeSize(),5);
	//			assertEquals(g.nodeSize(),4);
	////			assertTrue(g.hasEdge(1, 2));
	////			assertTrue(g.hasEdge(2, 1));
	////			assertTrue(g.hasEdge(2, 3));
	////			assertTrue(g.hasEdge(3, 2));
	//			assertEquals(g.getNode(2),g.removeNode(2));
	//			assertEquals(g.edgeSize(),2);
	//			assertEquals(g.nodeSize(),3);
	////			assertFalse(g.hasEdge(1, 2));
	////			assertFalse(g.hasEdge(2, 1));
	////			assertFalse(g.hasEdge(2, 3));
	////			assertFalse(g.hasEdge(3, 2));
	////			g.addNode(2);
	////			assertFalse(g.hasEdge(1, 2));
	////			g.connect(1, 2, 95.3);
	////			assertTrue(g.hasEdge(2, 1));
	////			assertTrue(g.hasEdge(1, 2));
	////			assertEquals(g.edgeSize(),3);
	////			assertEquals(g.nodeSize(),4);
	////			g.removeEdge(2, 1);
	////			assertEquals(g.edgeSize(),2);
	////			assertEquals(g.nodeSize(),4);
	////			assertFalse(g.hasEdge(1, 2));
	////			assertFalse(g.hasEdge(2, 1));
	//
	//		}
	//
	//		@Test
	//		void testGetV() {
	//			directed_weighted_graph g =new DWGraph_DS();
	//			g.addNode(new NodeData());
	//			g.addNode(new NodeData());
	//			g.addNode(new NodeData());
	//			g.addNode(new NodeData());
	//			g.connect(1, 2, 95.3);
	//			g.connect(1, 3, 20.4);
	//			g.connect(3, 2, 9);
	//			g.connect(0, 1, 100.543);
	//			g.connect(0, 2, 10.7);
	//			int i=0;
	////			for(node_data n: g.getV()) {
	////				assertEquals(n.getKey(),i++);
	////
	////			}
	////			 i=1;
	////			for(node_data n: g.getE(0)) {
	////				assertEquals(n.getKey(),i++);
	////
	////			}
	//		
	//		
	//		
	//		
	//		}
}

