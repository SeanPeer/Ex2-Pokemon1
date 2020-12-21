package api;
/**
 * This class represents a directional weighted graph.
 * it  has a road-system or communication network in mind - 
 * it should support a large number of nodes (over 100,000).
 * The implementation  based on an efficient compact representation 
 * of three  hashmaps :
 *  "graph" has the nodes
 * neighbors-  has all the edge in graph in hash hashmap
 * connected_to- create to get to easy(efficency) for who my node connect(anti-directional )
 *  
 * 
 *
 */
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

    
    
    /**
   	 *Initialize empty Constructor .
   	 * @return DWGraph_DS,
   	 */
    public DWGraph_DS() {
        this.graph = new HashMap<>();
        this.neighbors = new HashMap<>();
        this.connected_to = new HashMap<>();
    }
    
    /**
   	 *Initialize Constructor -get all fieldes have in graph.
   	 * @return DWGraph_DS,
   	 */
    public DWGraph_DS(HashMap<Integer, node_data> graph, HashMap<Integer, HashMap<Integer, edge_data>> neighbors,
			HashMap<Integer, List<Integer>> connected_to, int edgeCounter, int mc) {
		
		this.graph = graph;
		this.neighbors = neighbors;
		this.connected_to = connected_to;
		this.edgeCounter = edgeCounter;
		this.mc = mc;
	}

    /**
	 * returns the node_data by the node_id,
	 * @param key - the node_id
	 * @return the node_data by the node_id, null if none.
	 */
	@Override
    public node_data getNode(int key) {
        if(this.graph.containsKey(key))
            return this.graph.get(key);
        return null;
    }
	/**
	 * returns the data of the edge (src,dest), null if none.
	 * Note: this method should run in O(1) time.
	 * @param src
	 * @param dest
	 * @return node_data
	 */
    @Override
    public edge_data getEdge(int src, int dest) {
        if(this.neighbors.get(src).containsKey(dest))
            return this.neighbors.get(src).get(dest);
        return null;
    }
    /**
	 * adds a new node to the graph with the given node_data.
	 * Note: this method should run in O(1) time.
	 * @param n
	 */
    @Override
    public void addNode(node_data n) {
        if(!this.graph.containsKey(n.getKey())) {
        	
            this.graph.put(n.getKey(), n);
            this.neighbors.put(n.getKey(), new HashMap<>());
            this.connected_to.put(n.getKey(), new ArrayList<>());
            mc++;
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
            mc++;
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
	 * @return the node_data of the removed node (null if none). 
	 * @param key
	 */
    @Override
    public node_data removeNode(int key) {
	
    	if (this.graph.containsKey(key)) {
    		
    		
    		
    		for (Integer v : this.connected_to.get(key)) {//remove all connected_to key
				
    			this.neighbors.get(v).remove(key);
    			
    			this.edgeCounter--;;//remove all edges connected_to key
    			this.mc++;
    			
			}
    		
    		//remove all neighbors
    		this.edgeCounter -= this.neighbors.get(key).size();
    		this.mc += this.neighbors.get(key).size();//remove all edges neighbors 

    		this.neighbors.get(key).clear();
    		
    		this.mc +=1;//// remove node 
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
	 * @return the EdgeData of the removed edge (null if none).
	 */
    @Override
    public edge_data removeEdge(int src, int dest) {
    	
        if(src != dest && this.neighbors.get(src).containsKey(dest)){
        	
           edgeCounter--;
           mc++;
            this.connected_to.get(dest).remove( this.connected_to.get(dest).indexOf(src));
            
           return  this.neighbors.get(src).remove(dest);
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
	/** Returns the number of vertices (nodes) in the graph.
	 * Note: this method should run in O(1) time. 
	 * @return
	 */
	@Override
    public int nodeSize() {
        return this.graph.size();
    }
	/** 
	 * Returns the number of edges (assume directional graph).
	 * Note: this method should run in O(1) time.
	 * @return
	 */
    @Override
    public int edgeSize() {
        return this.edgeCounter;
    }
    /**
     * Returns the Mode Count - for testing changes in the graph.
     * @return int number of changes
     */
    @Override
    public int getMC() {
        return this.mc;
    }
}

