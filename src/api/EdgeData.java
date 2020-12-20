package api;
/**
 * This class represents the set of operations applicable on a 
 * directional edge(src,dest) in a (directional) weighted graph.
 * @author amos.sean
 *
 */
//import api.DWGraph_DS;
//import api.edge_data;

import java.io.Serializable;

public class EdgeData implements edge_data, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int source;
    private int destination;
    private int tag;
    private double weight;
    private String info;

    /**
	 *Initialize Constructor -get int source, int destination, double weight associated with this edge.
	 * @return EdgeData,
	 */
    public EdgeData(int source, int destination, double weight){
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.info = "";
        this.tag = 0;
    }
    /**
	 *Initialize empty Constructor - not get param associated with this edge.
	 * @return EdgeData,
	 */
    public EdgeData() {
        this.source =0;
        this.destination = 0;
        this.weight =0;
        this.info = "";
        this.tag = 0;
    }
    /**
   	 *Initialize copy Constructor -edge_data e .
   	 * @return EdgeData,
   	 */
    public EdgeData(edge_data e) {
    	
      this.source=e.getSrc();
        this.destination = e.getDest();
        this.weight =e.getWeight();
        this.info = e.getInfo();
        this.tag = e.getTag();
    }
    /**
   	 *check if this EdgeData equal to other EdgeData
   	 * @return boolean,
   	 * @param this and Object obj
   	 */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof EdgeData)) {
            return false;
        }
        EdgeData other = (EdgeData) obj;


        if (destination != other.destination) {
            return false;
        }
        if (info == null) {
            if (other.info != null) {
                return false;
            }
        } else if (!info.equals(other.info)) {
            return false;
        }
        if (source != other.source) {
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
	 * Returns the key (id) associated with this EdgeData  source node.
	 * @return int;
	 */
    @Override
    public int getSrc() {
        return this.source;
    }
    /**
   	 * Returns the key (id) associated with this EdgeData destination node.
   	 * @return int;
   	 */
    @Override
    public int getDest() {
        return this.destination;
    }
    /**
   	 * Returns the double weight associated with this  EdgeData.
   	 * @return double;
   	 */
    @Override
    public double getWeight() {
        return this.weight;
    }
    /**
   	 * Returns the String info associated with this  EdgeData.
   	 * @return String;
   	 */
    @Override
    public String getInfo() {
        return this.info;
    }
    /**
   	 * update the String info associated with this  EdgeData.
   	 * @return String;
   	 */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }
    /**
   	 * Returns the int  associated with this  EdgeData tag.
   	 * @return String;
   	 */
    @Override
    public int getTag() {
        return this.tag;
    }
    /**
   	 * update the int  associated with this  EdgeData tag.
   	 * @return String;
   	 */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

}