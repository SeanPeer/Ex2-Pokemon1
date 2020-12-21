 package Tests;




import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import api.*;
import gameClient.*;

class DWGraph_AlgoTest {

	private directed_weighted_graph connectGraph() {
		directed_weighted_graph graph = new DWGraph_DS();
		graph.addNode(new Node(4));
		graph.addNode(new Node(5));
		graph.addNode(new Node(6));
		graph.connect(4, 5, 10);
		graph.connect(5, 4, 10);

		graph.connect(4,6,8);
		graph.connect(6,4,8);
		graph.connect(5,6,7);
		graph.connect(6,5,7);
		return graph;
	}

	private DWGraph_DS notConnectGraph() {
		DWGraph_DS graph = new DWGraph_DS();
		graph.addNode(new Node(1));
		graph.addNode(new Node(2));
		graph.addNode(new Node(3));
		graph.connect(1, 3, 1);
		graph.connect(1, 2, 1);


		return graph;
	}
	@Test
	void testInit() {
		directed_weighted_graph graph = new DWGraph_DS();
		graph.addNode(new Node(4));
		graph.addNode(new Node(5));
		graph.addNode(new Node(6));
		graph.connect(4, 5, 10);
		graph.connect(5, 4, 10);
		dw_graph_algorithms algo= new DWGraph_Algo();
		algo.init(graph);
		
		algo.getGraph().equals(graph);
	}

	@Test
	void testGetGraph() {
		directed_weighted_graph graph = new DWGraph_DS();
		graph.addNode(new Node(4));
		graph.addNode(new Node(5));
		graph.addNode(new Node(6));
		graph.connect(4, 5, 10);
		graph.connect(5, 4, 10);
		dw_graph_algorithms algo= new DWGraph_Algo();
		algo.init(graph);
		assertTrue(algo.getGraph().equals(graph));
		;
	}

	@Test
	void testCopy() {
		directed_weighted_graph graph = new DWGraph_DS();
		graph.addNode(new Node(4));
		graph.addNode(new Node(5));
		graph.addNode(new Node(6));
		graph.connect(4, 5, 10);
		graph.connect(5, 4, 10);
		dw_graph_algorithms algo1= new DWGraph_Algo();
		algo1.init(graph);
		directed_weighted_graph graph2=  algo1.copy() ;
		
		assertTrue(algo1.getGraph().equals(graph2));
	}

//	@Test
//	void testDjikstra() {
//		fail("Not yet implemented");
//	}

	@Test
	void testIsConnected() {
		directed_weighted_graph con_graph = new DWGraph_DS();

		con_graph.addNode(new Node(0));
		con_graph.addNode(new Node(1));


		dw_graph_algorithms algo = new DWGraph_Algo();
		algo.init(con_graph);

		assertFalse(algo.isConnected());
		con_graph.connect(1, 0, 9);
		assertTrue(algo.isConnected());/// need to finish........
		
		con_graph.connect(1, 2, 2);
		con_graph.connect(2, 3, 3);
//		assertFalse(algo.isConnected()); /// need to finish........
	

	}

	@Test/// need to finish........
	void testShortestPathDist() {
		//(1) --> 3 --> (2)
		DWGraph_DS graph = new DWGraph_DS();
		graph.addNode(new Node(1));
		graph.addNode(new Node(2));
		graph.connect(1, 2, 3);
		//dw_graph_algorithms algo = DWGraph_Algo(graph);

	//	double dist = algo.shortestPathDist(1, 2);
//		assertEquals(3, dist);

//	//	graph = connectGraph();
//		algo.init(graph);
//		dist = algo.shortestPathDist(1, 3);
//		assertEquals(2, dist);
	}

	@Test
	void testShortestPath() {/// need to finish........
//		DGraph graph = new DGraph();
//		graph.addNode(new Node(1));
//
//		graph.addNode(new Node(3));
//		graph.addNode(new Node(4));

//		graph.connect(1, 3, 5);
//		graph.connect(3, 4, 5);
//		graph.connect(4, 1, 3);
//
//		graph.addNode(new Node(2));
//		graph.connect(1, 2, 70);
//		graph.connect(2, 3, 10);
//		DWGraph_Algo algo = new DWGraph_Algo(graph);
//		List<node_data> l = algo.shortestPath(1, 4);
//
//		assertEquals("[Node [key=4, location=null], Node [key=3, location=null], Node [key=1, location=null]]",l.toString());
	}

	@Test
	void testSaveload() {

		directed_weighted_graph conGraph = connectGraph();//
		directed_weighted_graph NConGraph = notConnectGraph();

		dw_graph_algorithms algo1 = new DWGraph_Algo();

		algo1.init(conGraph);

		System.out.println("before");

		algo1.save("A0_test");
//		algo2.save("test2.json");
		
///		System.out.println(algo1.graph.getEdge(0, 1).getWeight());

		algo1.load("A0_test");
		
		//assertEquals(true, algo1.load("test1.json"));
		
//		System.out.println("after");
		//algo1.graph.getEdge(0, 1).getWeight();
	//	System.out.println(algo1.graph.getEdge(0, 1).getWeight());
//		graph.addNode(1);
//
//		graph.addNode(3);
//		graph.addNode(4);
//
//		graph.connect(1, 3, 5);
//		graph.connect(3, 4, 5);
//		graph.connect(4, 1, 3);
//
//		graph.addNode(2);
//		graph.connect(1, 2, 70);
//		graph.connect(2, 3, 10);
//        weighted_graph_algorithms ag0 = new WGraph_Algo();
//        ag0.init(graph);
//        String str = "g0.obj";
//        ag0.save(str);
//        WGraph_DS g1 = new WGraph_DS();
//		g1.addNode(1);
//
//		g1.addNode(3);
//		g1.addNode(4);
//
//		g1.connect(1, 3, 5);
//		g1.connect(3, 4, 5);
//		g1.connect(4, 1, 3);
//
//		g1.addNode(2);
//		g1.connect(1, 2, 70);
//		g1.connect(2, 3, 10);
//        ag0.load(str);
//              assertEquals(graph, g1);
              
	}

//	private void DWGraph_AlgoTest() {
//		// TODO Auto-generated method stub
//		
//	}

}
