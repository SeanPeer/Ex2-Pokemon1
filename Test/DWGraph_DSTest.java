package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.plaf.basic.BasicScrollPaneUI.VSBChangeListener;

import org.junit.jupiter.api.Test;

import api.*;
//import api.DWGraph_DS.EdgeData;

class DWGraph_DSTest {

	@Test
	void testDWGraph_DS() {
		Node n1=new Node ( 1);
		
		DWGraph_DS a=new DWGraph_DS();
	
		a.addNode(n1);
		
		assertEquals(n1,a.getNode(1) );
	}

	@Test
	void testGetNode() {
		Node n1=new Node ( 1);
		
		DWGraph_DS a=new DWGraph_DS();
	
		a.addNode(n1);
		
		assertEquals(n1,a.getNode(1) );
		a.addNode(n1);
		
		assertEquals(null,a.getNode(2) );
	}

	@Test
	void testGetEdge() {
		Node n1=new Node ( 1);
		Node n2=new Node ( 2);
		
		DWGraph_DS a=new DWGraph_DS();
		a.addNode(n1);
		a.addNode(n2);
		a.connect(n1.getKey(), n2.getKey(), 4.5);
		edge_data e= new EdgeData(1,2,4.5);//
//		
		assertEquals(e,a.getEdge(1, 2));
		assertEquals(null,a.getEdge(1, 3));
		
		//assertEquals(e.toString(),a.getEdge(1, 2).toString() );//without to string its not make it 

	}

	@Test
	void testAddNode() {
		Node n1=new Node ( 1);
		Node n2=new Node ( 2);
		
		DWGraph_DS a=new DWGraph_DS();
		a.addNode(n1);
		a.addNode(n2);
		Node n=new Node ( 1);
		assertEquals(n,a.getNode(1));
	}

	@Test
	void testConnect() {
		Node n1=new Node ( 1);
		Node n2=new Node ( 2);
		
		DWGraph_DS a=new DWGraph_DS();
		a.addNode(n1);
		a.addNode(n2);
		a.connect(n1.getKey(), n2.getKey(), 4.5);
		edge_data e= new EdgeData(1,2,4.5);//
//		
		assertEquals(e,a.getEdge(1, 2));
	}

	@Test
	void testGetV() {
		Node n1=new Node ( 1);
		Node n2=new Node ( 2);
		Node n3=new Node ( 3);
		Node n4=new Node ( 4);
		DWGraph_DS a=new DWGraph_DS();
		a.addNode(n1);
		a.addNode(n2);
		a.addNode(n3);
		HashMap<Integer, node_data> Vs= new HashMap<Integer, node_data>();
		Vs.put(1, n1);
		Vs.put(2, n2);
		Vs.put(3, n3);
		assertEquals(Vs.size(),a.getV().size());
		assertEquals(Vs.containsValue(n1),a.getV().contains(n1));
		assertEquals(Vs.containsValue(n2),a.getV().contains(n2));
		assertEquals(Vs.containsValue(n3),a.getV().contains(n3));
		assertEquals(Vs.containsValue(n4),a.getV().contains(n4));
	}

	@Test
	void testGetE() {
		Node n1=new Node ( 1);
		Node n2=new Node ( 2);
		Node n3=new Node ( 3);
		DWGraph_DS a=new DWGraph_DS();
		a.addNode(n1);
		a.addNode(n2);
		a.addNode(n3);
		
		HashMap<Integer, edge_data> Vs= new HashMap<Integer, edge_data>();
		
		edge_data e1= new EdgeData(1,2,4.5);
		edge_data e2= new EdgeData(2,3,4.5);
		a.connect(n1.getKey(), n2.getKey(), 4.5);
		a.connect(n2.getKey(), n3.getKey(), 5.5);
		Vs.put(1, e1);
		assertEquals(Vs.size(),a.getE(1).size());
		assertTrue(a.getE(1).contains(e1));
		assertFalse(a.getE(1).contains(e2));

		
	}

	@Test
	void testRemoveNode() {
		Node n1=new Node ( 1);
		Node n2=new Node ( 2);
		Node n3=new Node ( 3);
		DWGraph_DS a=new DWGraph_DS();
		a.addNode(n1);
		a.addNode(n2);
		a.addNode(n3);
		a.connect(1, 2, 4.5);
		a.connect(2, 3, 7);
		
		assertEquals(null,a.removeNode(4));
		assertEquals(n2,a.removeNode(2));
		assertEquals(null,a.getEdge(1, 2));
		
	}

	@Test
	void testRemoveEdge() {
		
		Node n1=new Node ( 1);
		Node n2=new Node ( 2);
		Node n3=new Node ( 3);
		
		DWGraph_DS a=new DWGraph_DS();
		a.addNode(n1);
		a.addNode(n2);
		a.addNode(n3);
		a.connect(1, 2, 4.5);
		a.connect(2, 3, 7);
		a.connect(3, 1, 7);
		a.connect(1, 3, 4);
		edge_data e1= new EdgeData(1,2,4.5);
		edge_data e2=a.removeEdge(1, 2);
		assertEquals(null,a.removeEdge(3, 2));
		assertEquals(e1,e2);
		assertEquals(null,a.removeEdge(1, 2));
	}

	@Test
	void testNodeSize() {
		
		Node n1=new Node ( 1);
		Node n2=new Node ( 2);
		Node n3=new Node ( 3);
		DWGraph_DS a=new DWGraph_DS();
		a.addNode(n1);
		a.addNode(n2);
		a.addNode(n3);
		a.connect(1, 2, 4.5);
		a.connect(2, 3, 7);
		a.removeNode(4);
		a.removeNode(3);
		a.removeEdge(1, 2);
		assertEquals(2,a.nodeSize());
	}
	

	@Test
	void testEdgeSize() {
		Node n1=new Node ( 1);
		Node n2=new Node ( 2);
		Node n3=new Node ( 3);


		DWGraph_DS a=new DWGraph_DS();

		a.addNode(n1);
		a.addNode(n2);
		a.addNode(n3);
		a.connect(1, 2, 4.5);
		a.connect(2, 3, 7);
		a.removeNode(1);
		assertEquals(1, a.edgeSize());
		
	}

	@Test
	void testGetMC() {
		Node n1=new Node ( 1);
		Node n2=new Node ( 2);
		Node n3=new Node ( 3);


		DWGraph_DS a=new DWGraph_DS();

		a.addNode(n1);
		a.addNode(n2);
		a.addNode(n3);
		a.connect(1, 2, 4.5);
		a.connect(2, 3, 7);
		a.connect(3, 2, 7);
		
		assertEquals(6,a.getMC());
		a.removeEdge(3, 2);
		assertEquals(7,a.getMC());
		
		
		a.removeNode(2);
		assertEquals(10,a.getMC());
	}

}
