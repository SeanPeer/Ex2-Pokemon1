package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import api.*;
import gameClient.util.Point3D;

class NodeTest {

	@Test
	void testNodeInt() {
		Node n=new Node ( 0);
		Node n2=new Node ( 0);
		System.out.println(n.toString());
		//assertEquals("Node [key=0]",n.toString() );
		assertEquals(n2,n );
	}

	@Test
	void testNode_data() {
		
	}

	@Test
	void testGetKey() {
		Node n=new Node ( 3);
		assertEquals(n.getKey(),3 );
		Node n2=new Node ( );
		assertEquals(n2.getKey(),0 );
	}

	@Test
	void testGetAndSetLocation() {
		Node n=new Node ( 3);
		assertEquals(n.getLocation(),null );
		Point3D p=new Point3D("2.26809,3.67576,5.924687");
		n.setLocation((geo_location)p);
		assertEquals(n.getLocation(),(geo_location)p );
	}

	
	@Test
	void testGetAndSetWeight() {
		Node n=new Node ( 3);
		String message= "stam1";
		assertEquals(n.getWeight(),0.0,0.1);
		double p=2.26809;
		n.setWeight(p);
		 message="stam2";
		assertEquals(n.getWeight(),2.26809,0.1);
	}

	
	@Test
	void testGetAndSetInfo() {
		Node n=new Node ( 3);
		assertEquals(n.getInfo(),"");
		String p="just checking";
		n.setInfo(p);
		assertEquals(n.getInfo(),p );
	}

	
	@Test
	void testGetAndSetTag() {
		Node n=new Node ( 3);
		assertEquals(n.getTag(),0);
		int t=1;
		n.setTag(t);
		assertEquals(n.getTag(),t );
	}


	
	@Test
	void testEquals() {
		node_data n=new Node ( 3);
		int t=1;
		n.setTag(t);
		String p="just checking";
		n.setInfo(p);
		double p1=2.26809;
		n.setWeight(p1);
		node_data b=new Node(n) ;
		
		assertTrue(b.equals(n) );
		b.setTag(-1);
		assertFalse(b.equals(n) );
	}

}
