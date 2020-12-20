package api;
/**
 * This package represents the set of operations applicable on a 
 * node (vertex) in a (directional) weighted graph.
 * @author amos.sean
 *
 */
import java.io.Serializable;

import gameClient.util.Point3D;

public class Node implements node_data, Serializable, Comparable<node_data> {

	/*
	 * Class Node will represent the information of a given node in a graph
	 * 
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private int key;
	private String info;
	private int tag;
	private double weight;
	private geo_location location;
	
	@Override
	public String toString() {
		return "Node [key=" + key + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + key;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + tag;
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Node)) {
			return false;
		}
		Node other = (Node) obj;
		if (info == null) {
			if (other.info != null) {
				return false;
			}
		} else if (!info.equals(other.info)) {
			return false;
		}
		if (key != other.key) {
			return false;
		}
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		if (tag != other.tag) {
			return false;
		}
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight)) {
			return false;
		}
		return true;
	}
	/**
	 * Constructor -get key (id) associated with this node.
	 * @return node_data
	 */
	public Node(int key) {		
		this.key = key;
		this.info = "";
		this.tag = 0;
		this.weight = 0;
//		Point3D pos=null;
		this.location = new Point3D(0, 0, 0);
	}
	
	
	public Node() {
		super();
	}
	/**
	 * Constructor -get node_data associated with this node.
	 * @return node_data, but in deep copy
	 */
	public Node(node_data n) {		
		this.key = n.getKey();
		this.info = n.getInfo();
		this.tag = n.getTag();
		this.weight = n.getWeight();
		this.location = n.getLocation();
	}
	
	/**
	 * Returns the key (id) associated with this node.
	 * @return int;
	 */
	@Override
	public int getKey() {
		return this.key;
	}

	/** Returns the location of this node, if
	 * none return null.
	 * 
	 * @return
	 */
	@Override
	public geo_location getLocation() {
		return this.location;
	}

	/** Allows changing this node's location.
	 * @param p - new new location  (position) of this node.
	 */
	@Override
	public void setLocation(geo_location p) {
		this.location = p;
	}

	/**
	 * Returns the weight associated with this node.
	 * @return double weight
	 */
	@Override
	public double getWeight() {
		return this.weight;
	}

	/**
	 * Allows changing this node's weight.
	 * @param w - the new weight
	 */
	@Override
	public void setWeight(double w) {
		this.weight = w;
	}

	/**
	 * Returns the remark (meta data) associated with this node.
	 * @return
	 */
	@Override
	public String getInfo() {
		return this.info;
	}

	/**
	 * Allows changing the remark (meta data) associated with this node.
	 * @param s
	 */
	@Override
	public void setInfo(String s) {
		this.info = s;
	}

	/**
	 * Temporal data (aka color: e,g, white, gray, black) 
	 * which can be used be algorithms 
	 * @return
	 */
	@Override
	public int getTag() {
		return this.tag;
	}

	/** 
	 * Allows setting the "tag" value for temporal marking an node - common
	 * practice for marking by algorithms.
	 * @param t - the new value of the tag
	 */
	@Override
	public void setTag(int t) {
		this.tag = t;
	}

	@Override
	public int compareTo(node_data o) {
		Double w1 = this.getWeight();
		Double w2 = o.getWeight();
		
		return w1 > w2 ? 1 : w1 < w2 ? -1 : 0;
	}
	
}