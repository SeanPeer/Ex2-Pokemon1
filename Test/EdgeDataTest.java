package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import api.EdgeData;
import api.Node;
import api.edge_data;
import api.edge_location;
import api.node_data;

class EdgeDataTest {

	@Test
	void testEdgeDataIntIntDouble()
	{
		edge_data n=new EdgeData(3, 5, 3.8);//   ( ,,0);
		
		 String message="stam2";
//		assertEquals(n.getWeight(),3.8,0.1, message);
		}

	@Test
	void testEdgeData() {
		edge_data n=new EdgeData();
		assertEquals(n.getTag(),0);
	}

	@Test
	void testEqualsObject() {
		
		edge_data n=new EdgeData(3, 5, 3.8);
		edge_data b=new EdgeData(3, 5, 3.8);
		
		assertTrue(b.equals(n) );
		int t=9;
		b.setTag(t);
		assertFalse(b.equals(n) );
	}

	@Test
	void testGetSrc() {
		edge_data n=new EdgeData(3, 5, 3.8);
		assertEquals(n.getSrc(),3);
	}

	@Test
	void testGetDest() {
		edge_data n=new EdgeData(3, 5, 3.8);
		assertEquals(n.getDest(),5);
	}

	@Test
	void testGetWeight() {
		edge_data n=new EdgeData(3, 5, 3.8);
		 String message="stam2";
//			assertEquals(n.getWeight(),3.8,0.1, message);
	}

	@Test
	void testGetAndSetInfo() {
		edge_data n=new EdgeData(3, 5, 3.8);
		 String message="stam2";
		n.setInfo(message);
		assertEquals(n.getInfo(),message);
	}


	@Test
	void testGetAndSetTag() {
		edge_data n=new EdgeData(3, 5, 3.8);
		
		int t=7;
		n.setTag(t);
		assertEquals(n.getTag(),7);
		
	}


}
