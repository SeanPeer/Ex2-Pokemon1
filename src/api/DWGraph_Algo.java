package api;
import java.awt.Window.Type;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;




import org.json.*;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import gameClient.util.Point3D;

/**
 * This interface represents a Directed (positive) Weighted Graph Theory Algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected(); // strongly (all ordered pais connected)
 * 3. double shortestPathDist(int src, int dest);
 * 4. List<node_data> shortestPath(int src, int dest);
 * 5. Save(file); // JSON file
 * 6. Load(file); // JSON file
 *
 * @author boaz.benmoshe
 *
 */
public class DWGraph_Algo implements dw_graph_algorithms {

	public directed_weighted_graph graph;

	private HashMap<Integer, Double> dist;
	private HashMap<Integer, Integer> prev;

	private HashMap<Integer, Double> dist_transpose;
	private HashMap<Integer, Integer> prev_transpose;

	/**
	 * Init the graph on which this set of algorithms operates on.
	 *
	 * @param g
	 */
	public void init(directed_weighted_graph g) {
		this.graph = g;

		this.dist = new HashMap<>();
		this.prev = new HashMap<>();

		this.dist_transpose = new HashMap<>();
		this.prev_transpose = new HashMap<>();
	}

	public DWGraph_Algo() {

		this.graph = null;
		this.dist = null;
		this.prev = null;
	}


//	public DWGraph_Algo(DWGraph_DS g) {
//		this.graph = g;
//
//		this.dist = new HashMap<>();
//		this.prev = new HashMap<>();
//	}

	/**
	 * Return the underlying graph of which this class works.
	 *
	 * @return
	 */
	public directed_weighted_graph getGraph() {
		return graph;
	}

	/**
	 * Compute a deep copy of this weighted graph.
	 *
	 * @return
	 */
	public directed_weighted_graph copy() {

		directed_weighted_graph deep_copy = new DWGraph_DS();

		for (node_data vertex : this.graph.getV()) {

			deep_copy.addNode(vertex);

		}

		for (node_data vertex : this.graph.getV()) { //for each vertex in graph

			for (edge_data edge : this.graph.getE(vertex.getKey())) { //for each neighbor of vertex

				deep_copy.connect(edge.getSrc(), edge.getDest(), edge.getWeight());

			}
		}

		return deep_copy;
	}


	public void djikstra(directed_weighted_graph graph, int source) {
		//google "djikstra psuedo code" (wikipedia)
		//https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

		//create priority Queue
		PriorityQueue<node_data> q = new PriorityQueue<>();

		for (node_data vertex : graph.getV()) { //for each node in graph

			if (vertex.getKey() == source) {

				this.dist_transpose.put(source, 0.0); //dist[source] -> 0
				vertex.setWeight(0.0); //for comperable in priority queue

			} else {

				this.dist_transpose.put(vertex.getKey(), Double.MAX_VALUE); //dist[node] -> inf
				this.prev_transpose.put(vertex.getKey(), null); //prev[node] -> null
				vertex.setWeight(Double.MAX_VALUE); //for comperable in priority queue
			}

			q.add(vertex);
		}

		//add source to Q

		while (!q.isEmpty()) { //Q is not empty

			node_data u = q.poll(); // Q.poll(); minheap.poll()

			for (edge_data edge : graph.getE(u.getKey())) { //for each neighbor of node


				double alt = this.dist_transpose.get(u.getKey()) + edge.getWeight();

				if (alt < this.dist_transpose.get(edge.getDest())) {

					this.dist_transpose.put(edge.getDest(), alt);
					this.prev_transpose.put(edge.getDest(), u.getKey());

					graph.getNode(edge.getDest()).setWeight(alt);

					q.remove(graph.getNode(edge.getDest())); //remove neighbor from Q
					q.add(graph.getNode(edge.getDest())); //insert into correct place (decrease priority)

					//https://stackoverflow.com/questions/1871253/updating-java-priorityqueue-when-its-elements-change-priority

				}

			}

		}

	}

	public void djikstra(int source) {
		//google "djikstra psuedo code" (wikipedia)
		//https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

		//create priority Queue
		PriorityQueue<node_data> q = new PriorityQueue<>();

		for (node_data vertex : this.graph.getV()) { //for each node in graph

			if (vertex.getKey() == source) {

				this.dist.put(source, 0.0); //dist[source] -> 0
				vertex.setWeight(0.0); //for comperable in priority queue

			} else {

				this.dist.put(vertex.getKey(), Double.MAX_VALUE); //dist[node] -> inf
				this.prev.put(vertex.getKey(), null); //prev[node] -> null
				vertex.setWeight(Double.MAX_VALUE); //for comperable in priority queue
			}

			q.add(vertex);
		}

		//add source to Q

		while (!q.isEmpty()) { //Q is not empty

			node_data u = q.poll(); // Q.poll(); minheap.poll()

			for (edge_data edge : this.graph.getE(u.getKey())) { //for each neighbor of node


				double alt = this.dist.get(u.getKey()) + edge.getWeight();

				if (alt < this.dist.get(edge.getDest())) {

					this.dist.put(edge.getDest(), alt);
					this.prev.put(edge.getDest(), u.getKey());

					this.graph.getNode(edge.getDest()).setWeight(alt);

					q.remove(this.graph.getNode(edge.getDest())); //remove neighbor from Q
					q.add(this.graph.getNode(edge.getDest())); //insert into correct place (decrease priority)

					//https://stackoverflow.com/questions/1871253/updating-java-priorityqueue-when-its-elements-change-priority

				}

			}

		}

	}



	private directed_weighted_graph transpose_graph(directed_weighted_graph graph) {


		directed_weighted_graph transpose = new DWGraph_DS();

		for (node_data vertex : this.graph.getV()) {

			transpose.addNode(vertex);

		}

		for (node_data vertex : this.graph.getV()) { //for each vertex in graph

			for (edge_data edge : this.graph.getE(vertex.getKey())) { //for each neighbor of vertex

				transpose.connect(edge.getDest(), edge.getSrc(), edge.getWeight());

			}
		}

		return transpose;

	}


	/**
	 * Returns true if and only if (iff) there is a valid path from each node to each
	 * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
	 *
	 * @return
	 */
	public boolean isConnected() {

		if (this.graph.nodeSize() == 1 || this.graph.nodeSize() == 0) return true;

		int k_ = 0;

		for (node_data node : this.graph.getV()) {

			djikstra(node.getKey());
			k_ = node.getKey();
			break;
		}

		if (!this.dist.containsValue(Double.MAX_VALUE)) {

			directed_weighted_graph g_t = transpose_graph(this.graph);

			djikstra(g_t, k_);

			return !this.dist_transpose.containsValue(Double.MAX_VALUE);
		}

		return false;
	}

	/**
	 * returns the length of the shortest path between src to dest
	 * Note: if no such path --> returns -1
	 *
	 * @param src  - start node
	 * @param dest - end (target) node
	 * @return
	 */
	public double shortestPathDist(int src, int dest) {
		djikstra(src);
		return this.dist.get(dest);
	}

	/**
	 * returns the the shortest path between src to dest - as an ordered List of nodes:
	 * src--> n1-->n2-->...dest
	 * see: https://en.wikipedia.org/wiki/Shortest_path_problem
	 * Note if no such path --> returns null;
	 *
	 * @param src  - start node
	 * @param dest - end (target) node
	 * @return
	 */
	public List<node_data> shortestPath(int src, int dest) {

		List<node_data> res = new ArrayList<>();

		List<node_data> back_res = new ArrayList<>();

		djikstra(src);

		int current = dest;

		res.add(this.graph.getNode(current));

		while (true) {

			if (current == src) {
				break;
			}

			res.add(this.graph.getNode(this.prev.get(current)));
			current = this.prev.get(current);

		}

		for (int i = res.size() - 1; i >= 0; i--) {
			back_res.add(res.get(i));
		}

		return back_res;

	}

	/**
	 * Saves this weighted (directed) graph to the given
	 * file name - in JSON format
	 *
	 * @param file - the file name (may include a relative path).
	 * @return true - iff the file was successfully saved
	 */
	public boolean save(String file) {

		try {

			JSONArray nodes = new JSONArray();
			JSONArray edges = new JSONArray();

			for (node_data v : getGraph().getV()) {

				JSONObject n = new JSONObject();

				n.put("pos", v.getLocation().toString());
				n.put("id", v.getKey());

				nodes.put(n);

				for (edge_data e : getGraph().getE(v.getKey())) {

					JSONObject e_ = new JSONObject();

					e_.put("src", e.getSrc());
					e_.put("w", e.getWeight());
					e_.put("dest", e.getDest());

					edges.put(e_);

				}

			}

			FileWriter fw = new FileWriter(file);

			JSONObject s = new JSONObject();

			s.put("Edges", edges);
			s.put("Nodes", nodes);

			fw.write(s.toString());
			fw.flush();

			fw.close();

		} catch (IOException | JSONException ex) {
			return false;
		}

		return true;
	}

	public boolean load(String file) {

		directed_weighted_graph graph = new DWGraph_DS();

		try {
			String data = new String(Files.readAllBytes(Paths.get(file)));

			JSONObject object;

			object = new JSONObject(data);

			JSONArray nodes = object.getJSONArray("Nodes");
			JSONArray edges = object.getJSONArray("Edges");

			for (int i=0; i < nodes.length(); i++) {

				String pos = nodes.getJSONObject(i).getString("pos");
				int id = nodes.getJSONObject(i).getInt("id");

				node_data n = new Node(id);

				n.setLocation(new Point3D(pos));

				graph.addNode(n);

			}

			for (int i=0; i < edges.length(); i++) {

				int src = edges.getJSONObject(i).getInt("src");
				int dest = edges.getJSONObject(i).getInt("dest");
				double w = edges.getJSONObject(i).getDouble("w");

				graph.connect(src, dest, w);

			}

			init(graph);

			return true;
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

		return false;
	}



//		/**
//         * This method load a graph to this graph algorithm.
//         * if the file was successfully loaded - the underlying graph
//         * of this class will be changed (to the loaded one), in case the
//         * graph was not loaded the original graph should remain "as is".
//         *
//         * @param file - file name of JSON file
//         * @return true - iff the graph was successfully loaded.
//         */
//	public boolean load(String file) {
//
//        		System.out.println("level 1");
//        		JSONObject graph_json;
//
//  				try {
//
//  					graph_json = parseJSONFile(file);
//
//  					JSONObject nodes = graph_json.getJSONObject("graph");
//
//  					HashMap<Integer, node_data> graph = toMapGraph(nodes);///use help function I build specific
//
//  					JSONObject neighborsJson = graph_json.getJSONObject("neighbors");
//  					HashMap<Integer, HashMap<Integer, edge_data>> neighbors = toMapNeighbors(neighborsJson);///use help function I build specific
//
// 		         JSONObject connected_toJson = graph_json.getJSONObject("connected_to");
// 		        HashMap<Integer, List<Integer>> connected_to = toMapConnected(connected_toJson);///use help function I build specific
//
// 		          int edgeCounter = graph_json.getInt("edgeCounter");
// 		         int mc = graph_json.getInt("mc");
// 		        System.out.println("level 2");
//
//
//
//
// 		        directed_weighted_graph gra = new  DWGraph_DS(graph, neighbors, connected_to, edgeCounter, mc);
//
// 		       this.graph=gra;
//
// 		          System.out.println("level 4");
//
//  					} catch (Exception e) {
//  						e.printStackTrace();
//  						return false;
//
//				}
//
//
//
//  return true;
//
//
//
//	}


		public static JSONObject parseJSONFile(String filename) throws JSONException, IOException 
		 {
			 
		        String content = new String(Files.readAllBytes(Paths.get(filename)));
		        System.out.println("before : "+content);
		      //  content=content.substring('&');
		        System.out.println("after : "+content);
		    //    json.getAsJsonPrimitive().getAsString();
		        return new JSONObject(content);
	}
		 
	//	 https://stackoverflow.com/questions/22011200/creating-hashmap-from-a-json-string
		 public static void jsonToMap( JSONObject jObject) throws JSONException {

		        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
		      //  JSONObject jObject = new JSONObject(t);
		        Iterator<?> keys = jObject.keys();

		        while( keys.hasNext() ){
		        	String key1= (String) keys.next();
		            int key = (int)keys.next();
		            Object value = jObject.getString(key1); 
		            map.put(key, value);

		        }

		        System.out.println("json : "+jObject);
		        System.out.println("map : "+map);
		    }
		 
		 
		 
		 
		 
		 
		 
		 public static HashMap<Integer, node_data> toMapGraph(JSONObject object) throws JSONException {
			    HashMap<Integer, node_data> nodes = new HashMap<Integer, node_data>();
			    
			    
			    
			    Iterator<?> keysItr = object.keys();
			    while(keysItr.hasNext()) {
			    	String key1 =   (String) keysItr.next();
			        int key =  Integer.parseInt(key1);
			        
			        JSONObject nodeJson =  (JSONObject) object.get(key1);
			        int id = nodeJson.getInt("key");
		               String info = nodeJson.getString("info");
		              int tag = nodeJson.getInt("tag");
		                double weight = nodeJson.getDouble("weight");
		               JSONObject posJson = nodeJson.getJSONObject("location");
		               double x = posJson.getDouble("_x");
		               double y = posJson.getDouble("_y");
		               double z = posJson.getDouble("_z");
		               node_data n= new Node(id);
		               n.setLocation(new Point3D(x,y,z));
		                n.setInfo(info);
		                n.setTag(tag);
		                n.setWeight(weight);
		               nodes.put(key,n);
			    } 
    
			    return nodes;
			}
		 
		// @SuppressWarnings("unchecked")
		public static HashMap<Integer,  HashMap<Integer, edge_data>> toMapNeighbors(JSONObject object) throws JSONException {
			   HashMap<Integer,  HashMap<Integer, edge_data>> neighbors = new HashMap<Integer,  HashMap<Integer, edge_data>>();

			   
			    Iterator<?> keys1Itr = object.keys();/// over json key of out hashmap 
			    while(keys1Itr.hasNext())
			    {
			    	String key1S =   (String) keys1Itr.next();
			        int key1I	 =  Integer.parseInt(key1S);
			        
			        JSONObject nodeNeibohrsJson =  (JSONObject) object.getJSONObject(key1S);/// over key of inner hashmap 
			        Iterator<?> keys2Itr = nodeNeibohrsJson.keys();
			        HashMap<Integer, edge_data> mapInner = new   HashMap<Integer, edge_data>();
				    while(keys2Itr.hasNext())   /////// over json key of inner hashmap 
				    {
				    	
				    	String key2S =   (String) keys2Itr.next();
				        int key2I =  Integer.parseInt(key2S);
				        JSONObject EdgeJson= (JSONObject) nodeNeibohrsJson.getJSONObject(key2S);
				      
			        int src = EdgeJson.getInt("source");
			        int dest = EdgeJson.getInt("destination");
		             int tag = EdgeJson.getInt("tag");
		              double weight = EdgeJson.getDouble("weight");
		              String info = EdgeJson.getString("info");
		               edge_data e= new EdgeData(src,dest,weight);
		              
		                e.setTag(tag);
		                e.setInfo(info);
		             
		                mapInner.put(key2I,e);
		               
				    }
				    neighbors.put(key1I, mapInner);
			    } 
			    return neighbors;
			}


			public static HashMap<Integer, List<Integer>> toMapConnected(JSONObject object) throws JSONException 
			{
				HashMap<Integer, List<Integer>> Connected_to = new HashMap<Integer, List<Integer>>();

				 Iterator<?> keys1Itr = object.keys();/// over json key of out hashmap 
				    while(keys1Itr.hasNext())
				    {
				    	String key1S =   (String) keys1Itr.next();
				        int key1I	 =  Integer.parseInt(key1S);
				        
				        JSONArray ListJSon =  (JSONArray) object.getJSONArray(key1S);/// over key of inner hashmap 
				     
				        List<Integer> list = new ArrayList<Integer>();
				        for(int i = 0; i < ListJSon.length(); i++)
				        {
					        Integer value = ListJSon.getInt(i);
					        
					        list.add(value);
					    }
					  //  return list;
				  
					 
					    Connected_to.put(key1I, list);
				  
			    
				    }
				    return Connected_to;
			}

			public static List<Object> toList(JSONArray array) throws JSONException {
			    List<Object> list = new ArrayList<Object>();
			    for(int i = 0; i < array.length(); i++) {
			        Object value = array.get(i);
			        if(value instanceof JSONArray) {
			            value = toList((JSONArray) value);
			        }

			        else if(value instanceof JSONObject) {
			            value = toMapConnected((JSONObject) value);
			        }
			        list.add(value);
			    }
			    return list;
			}
			
			
		 public boolean load2(String file) {
//				
				

		        directed_weighted_graph gra = new DWGraph_DS();
		      
		        		System.out.println("level 1");
		  			
		  				try {
		  					
//		  				//	FileOutputStream fos = new FileOutputStream(file);
//		  					Gson gson = new Gson();
//		  				//	String json = gson.fromJson(json,JsonObject);
		  				  // create Gson instance
		  				    Gson gson = new Gson();

		  				    // create a reader
		  				    Reader reader = Files.newBufferedReader(Paths.get(file));

		  				    // convert JSON string to User object
		  				gra = gson.fromJson(reader,DWGraph_DS.class);

		  				    // print user object
		  				    System.out.println(gra);

		  				    // close reader
		  				    reader.close();


		  			//	 gra = gson.fromJson(file, DWGraph_DS.class);
		  				
		 		         
				            
		  					} catch (Exception e) {
		  						e.printStackTrace();
		  						return false;
							// TODO: handle exception
						}
		  
		  

		  				 this.graph=gra;
		  				// System.out.println("loaded graph : "+this.graph.toString());
		  return true;
//		  
//				
//		  
			}
		 
		 
}