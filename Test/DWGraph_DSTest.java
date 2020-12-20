package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import api.*;
//import api.DWGraph_DS.EdgeData;

class DWGraph_DSTest {

	@Test
	void testDWGraph_DS() {
		Node n1=new Node ( 1);
		//Node n2=new Node;
		DWGraph_DS a=new DWGraph_DS();
	//	DWGraph_DS b=new DWGraph_DS();
		a.addNode(n1);
		//Node n3=new a.getNode(1);
		assertEquals(n1,a.getNode(1) );
	}

	@Test
	void testGetNode() {
		Node n1=new Node ( 1);
		//Node n2=new Node;
		DWGraph_DS a=new DWGraph_DS();
	///	DWGraph_DS b=new DWGraph_DS();
		a.addNode(n1);
		//Node n3=new a.getNode(1);
		assertEquals(n1,a.getNode(1) );
	}

	@Test
	void testGetEdge() {
		Node n1=new Node ( 1);
		Node n2=new Node ( 2);
		//Node n2=new Node;
		DWGraph_DS a=new DWGraph_DS();
		a.addNode(n1);
		a.addNode(n2);
		a.connect(n1.getKey(), n2.getKey(), 4.5);
//		DWGraph_DS b=new DWGraph_DS();
		//EdgeData e= EdgeData ( 1,2,4.5);
		assertEquals(n2.getKey(),0 );
		
		//assertEquals(e.toString(),a.getEdge(1, 2).toString() );//without to string its not make it 

	}

	@Test
	void testAddNode() {
		fail("Not yet implemented");
	}

	@Test
	void testConnect() {
		fail("Not yet implemented");
	}

	@Test
	void testGetV() {
		fail("Not yet implemented");
	}

	@Test
	void testGetE() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveNode() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveEdge() {
		fail("Not yet implemented");
	}

	@Test
	void testNodeSize() {
		fail("Not yet implemented");
	}

	@Test
	void testEdgeSize() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMC() {
		fail("Not yet implemented");
	}

}
