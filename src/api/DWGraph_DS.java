package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class DWGraph_DS implements directed_weighted_graph, Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HashMap<Integer, node_data> graph;
    
    private HashMap<Integer, HashMap<Integer, edge_data>> neighbors;
    
    private HashMap<Integer, List<Integer>> connected_to;
    
    private int edgeCounter;
    private int mc;

    public DWGraph_DS() {
        this.graph = new HashMap<>();
        this.neighbors = new HashMap<>();
        this.connected_to = new HashMap<>();
    }
    
    
    public DWGraph_DS(HashMap<Integer, node_data> graph, HashMap<Integer, HashMap<Integer, edge_data>> neighbors,
			HashMap<Integer, List<Integer>> connected_to, int edgeCounter, int mc) {
		
		this.graph = graph;
		this.neighbors = neighbors;
		this.connected_to = connected_to;
		this.edgeCounter = edgeCounter;
		this.mc = mc;
	}


	@Override
    public node_data getNode(int key) {
        if(this.graph.containsKey(key))
            return this.graph.get(key);
        return null;
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        if(this.neighbors.get(src).containsKey(dest))
            return this.neighbors.get(src).get(dest);
        return null;
    }

    @Override
    public void addNode(node_data n) {
        if(!this.graph.containsKey(n.getKey())) {
        	
            this.graph.put(n.getKey(), n);
            this.neighbors.put(n.getKey(), new HashMap<>());
            this.connected_to.put(n.getKey(), new ArrayList<>());
        }
    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if(src != dest && !neighbors.get(src).containsKey(dest)){
        	
            edge_data edge =  new EdgeData(src,dest,w);
            neighbors.get(src).put(dest,edge);
            
            connected_to.get(dest).add(src);
            
            edgeCounter++;
        }
        
        /*
         * we might need to consider updating weight if and edge
         * already exists between src and dest.
         */

    }

	/**
	 * This method returns a pointer (shallow copy) for the
	 * collection representing all the nodes in the graph. 
	 * Note: this method should run in O(1) time.
	 * @return Collection<node_data>
	 */
    @Override
    public Collection<node_data> getV() {
        return this.graph.values();
    }

	/**
	 * This method returns a pointer (shallow copy) for the
	 * collection representing all the edges getting out of 
	 * the given node (all the edges starting (source) at the given node). 
	 * Note: this method should run in O(k) time, k being the collection size.
	 * @return Collection<edge_data>
	 */
    @Override
    public Collection<edge_data> getE(int node_id) {
        return this.neighbors.get(node_id).values(); //o(k)
    }

	/**
	 * Deletes the node (with the given ID) from the graph -
	 * and removes all edges which starts or ends at this node.
	 * This method should run in O(k), V.degree=k, as all the edges should be removed.
	 * @return the data of the removed node (null if none). 
	 * @param key
	 */
    @Override
    public node_data removeNode(int key) {
//        if(this.graph.containsKey(key)){
//            //while - all the node toward him
//            if(this.neighbors.get(key) != null){
//                ArrayList<edge_data> ni = new ArrayList<edge_data>(this.neighbors.get(key).values()); //o(k)
//
//                while(ni.size()!= 0) { //o(k)
//                    int n = ni.get(0).getDest();
//                    removeEdge(key, n);
//                    ni.remove(0);
//                }
//            }
//        }
//        return null;
    	
    	if (this.graph.containsKey(key)) {
    		
    		//go to connected remove
    		
    		for (Integer v : this.connected_to.get(key)) {
				
    			this.neighbors.get(v).remove(key);
    			
    			this.edgeCounter--;
    			this.mc++;
    			
			}
    		
    		//remove all neighbors
    		this.edgeCounter -= this.neighbors.get(key).size();
    		this.mc += this.neighbors.get(key).size();

    		this.neighbors.get(key).clear();
    		
    		return this.graph.remove(key);
    		//return node
    		
    	}
    	
    	return null;
    	
    }

	/**
	 * Deletes the edge from the graph,
	 * Note: this method should run in O(1) time.
	 * @param src
	 * @param dest
	 * @return the data of the removed edge (null if none).
	 */
    @Override
    public edge_data removeEdge(int src, int dest) {
    	
        if(src != dest && this.neighbors.get(src).containsKey(dest)){
        	
            this.neighbors.get(src).remove(dest);
            
            this.connected_to.get(dest).remove(src);
            
            mc++;
            edgeCounter--;
        }
        
        return null;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connected_to == null) ? 0 : connected_to.hashCode());
		result = prime * result + edgeCounter;
		result = prime * result + ((graph == null) ? 0 : graph.hashCode());
		result = prime * result + mc;
		result = prime * result + ((neighbors == null) ? 0 : neighbors.hashCode());
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
		if (!(obj instanceof DWGraph_DS)) {
			return false;
		}
		DWGraph_DS other = (DWGraph_DS) obj;
		if (connected_to == null) {
			if (other.connected_to != null) {
				return false;
			}
		} else if (!connected_to.equals(other.connected_to)) {
			return false;
		}
		if (edgeCounter != other.edgeCounter) {
			return false;
		}
		if (graph == null) {
			if (other.graph != null) {
				return false;
			}
		} else if (!graph.equals(other.graph)) {
			return false;
		}
		if (mc != other.mc) {
			return false;
		}
		if (neighbors == null) {
			if (other.neighbors != null) {
				return false;
			}
		} else if (!neighbors.equals(other.neighbors)) {
			return false;
		}
		return true;
	}
	@Override
    public int nodeSize() {
        return this.graph.size();
    }

    @Override
    public int edgeSize() {
        return this.edgeCounter;
    }

    @Override
    public int getMC() {
        return this.mc;
    }

//    public class EdgeData implements edge_data, Serializable{
//        /**
//		 *
//		 */
//		private static final long serialVersionUID = 1L;
//
//		private int source;
//        private int destination;
//        private int tag;
//        private double weight;
//        private String info;
//
//        public EdgeData(int source, int destination, double weight){
//            this.source = source;
//            this.destination = destination;
//            this.weight = weight;
//            this.info = "";
//            this.tag = 0;
//        }
//
//        public EdgeData() {
//        	this.source =0;
//            this.destination = 0;
//            this.weight =0;
//            this.info = "";
//            this.tag = 0;
//		}
//
//		@Override
//		public int hashCode() {
//			final int prime = 31;
//			int result = 1;
//			result = prime * result + getOuterType().hashCode();
//			result = prime * result + destination;
//			result = prime * result + ((info == null) ? 0 : info.hashCode());
//			result = prime * result + source;
//			result = prime * result + tag;
//			long temp;
//			temp = Double.doubleToLongBits(weight);
//			result = prime * result + (int) (temp ^ (temp >>> 32));
//			return result;
//		}
//
//		@Override
//		public boolean equals(Object obj) {
//			if (this == obj) {
//				return true;
//			}
//			if (obj == null) {
//				return false;
//			}
//			if (!(obj instanceof EdgeData)) {
//				return false;
//			}
//			EdgeData other = (EdgeData) obj;
//			if (!getOuterType().equals(other.getOuterType())) {
//				return false;
//			}
//			if (destination != other.destination) {
//				return false;
//			}
//			if (info == null) {
//				if (other.info != null) {
//					return false;
//				}
//			} else if (!info.equals(other.info)) {
//				return false;
//			}
//			if (source != other.source) {
//				return false;
//			}
//			if (tag != other.tag) {
//				return false;
//			}
//			if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight)) {
//				return false;
//			}
//			return true;
//		}
//
//		@Override
//        public int getSrc() {
//            return this.source;
//        }
//
//        @Override
//        public int getDest() {
//            return this.destination;
//        }
//
//        @Override
//        public double getWeight() {
//            return this.weight;
//        }
//
//        @Override
//        public String getInfo() {
//            return this.info;
//        }
//
//        @Override
//        public void setInfo(String s) {
//            this.info = s;
//        }
//
//        @Override
//        public int getTag() {
//            return this.tag;
//        }
//
//        @Override
//        public void setTag(int t) {
//            this.tag = t;
//        }
//
//		private DWGraph_DS getOuterType() {
//			return DWGraph_DS.this;
//		}
//    }
//
//    public class NodeData implements node_data, Comparable<node_data>, Serializable{
//        /**
//		 *
//		 */
//		private static final long serialVersionUID = 1L;
//
//		private int key;
//        private double weight;
//        private int tag;
//        private String info;
//
//        public NodeData(int key){
//            this.key = key;
//            this.weight = 0;
//            this.tag = 0;
//            this.info = "";
//        }
//
//        public NodeData(node_data node){
//            this.key = node.getKey();
//            this.weight = node.getWeight();
//            this.tag = node.getTag();
//            this.info = node.getInfo();
//        }
//
//        @Override
//        public int getKey() {
//            return this.key;
//        }
//
//        @Override
//        public geo_location getLocation() {
//            return null;
//        }
//
//        @Override
//        public void setLocation(geo_location p) {
//
//        }
//
//        @Override
//        public double getWeight() {
//            return this.weight;
//        }
//
//        @Override
//        public void setWeight(double w) {
//            this.weight = w;
//        }
//
//        @Override
//        public String getInfo() {
//            return this.info;
//        }
//
//        @Override
//        public void setInfo(String s) {
//            this.info = s;
//        }
//
//        @Override
//        public int getTag() {
//            return this.tag;
//        }
//
//        @Override
//        public void setTag(int t) {
//            this.tag = t;
//        }
//
//		@Override
//		public int compareTo(node_data o) {
//			// TODO Auto-generated method stub
//
//			Double w1 = this.getWeight();
//			Double w2 = o.getWeight();
//
//			if (w1 > w2) return 1;
//			else if (w1 < w2) return -1;
//
//			return 0; // o1 = o2
//		}
//    }


}
