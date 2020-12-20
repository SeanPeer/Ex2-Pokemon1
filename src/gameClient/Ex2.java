package gameClient;

import GUI.Menu;
import Server.Game_Server_Ex2;
import api.*;
import gameClient.util.Point3D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Ex2 implements Runnable{
    private static MyFrame _win;
    private static Arena _ar;

    private static dw_graph_algorithms algo;
    private static directed_weighted_graph gg;

    private static HashMap<Integer, CL_Pokemon> pair;
    private static HashMap<Integer, List<node_data>> paths;

//    private static HashMap<Integer, Boolean> lock;

    private static int count_dest;
    private static boolean agent_eat_fruit;
//    private static HashMap<Boolean, Integer> test;


//    private static HashMap<CL_Agent, CL_Pokemon> pair;
//
//    private static HashMap<CL_Pokemon, Boolean> pair;

    private static int level;
    private static long id;

    private static long dt_gloabl=100;
    private static boolean flag;

    public static void main(String[] a) {

        if (a.length > 0) {//i have arguments

            id = Integer.parseInt(a[0]);
            level = Integer.parseInt(a[1]);

            Thread client = new Thread(new Ex2());
            client.start();

        }

        else {

            Menu menu = new Menu("Pokemon Game");

            boolean start = menu.getStatus();

            while (!start) {

                start = menu.getStatus();

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (start) {
                    level = menu.getLevel();
                    id = menu.getID();
                    break;

                }

            }

            Thread client = new Thread(new Ex2());
            client.start();


        }

    }

    @Override
    public void run() {


        System.out.println("You choose level: " + level + ", and your ID: " + id);

        game_service game = Game_Server_Ex2.getServer(level); // you have [0,23] games
        game.login(id);



       
        init(game);
        game.startGame();

        _ar.setAgents(Arena.getAgents(game.getAgents(), gg));

        _win.setTitle("Ex2 - OOP: (our Solution) "+game.toString());
        int ind=0;

//        long dt = 100;

        boolean f = false;

        while(game.isRunning()) {

            List<String> info = new ArrayList<>();

            for (CL_Agent a : _ar.getAgents()) {
                info.add("Agent id: " + a.getID() + ", speed: " + a.getSpeed() + ", value: " + a.getValue()
                + ", loc: " + a.getLocation());

            }

            info.add("time to finish: " + game.timeToEnd());

            _ar.set_info(info);

            try {
                moveAgants(game, gg);



                if (!f && flag) {

                    f = true;

                    
                    dt_gloabl -=18;

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                if(ind%1==0) {_win.repaint();}
                Thread.sleep(dt_gloabl);
              // game.timeToEnd()
                ind++;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }


    private directed_weighted_graph build_Graph(String g) {

        directed_weighted_graph graph = new DWGraph_DS();

        algo = new DWGraph_Algo();

        JSONObject graph_json;

        try {
            graph_json = new JSONObject(g);

            JSONArray edges = graph_json.getJSONArray("Edges");
            JSONArray nodes = graph_json.getJSONArray("Nodes");

            for (int i = 0; i < nodes.length(); i++) {

                int id = nodes.getJSONObject(i).getInt("id");
                String pos = nodes.getJSONObject(i).getString("pos");
System.out.println("pos in String :"+pos);
                node_data n = new Node(id);

                n.setLocation(new Point3D(pos));
                System.out.println("loc in String :"+n.getLocation());
                graph.addNode(n);
            }

            for (int i = 0; i < edges.length(); i++) {

                int src = edges.getJSONObject(i).getInt("src");
                int dest = edges.getJSONObject(i).getInt("dest");
                double w = edges.getJSONObject(i).getDouble("w");

                graph.connect(src, dest, w);
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        algo.init(graph);
       geo_location p3= algo.getGraph().getNode(0).getLocation();
       System.out.println(p3.x());
       System.out.println(p3.y());
       System.out.println(p3.z());
        return graph;
    }


    private static void find_agent_paths(List<CL_Agent> agents, List<CL_Pokemon> fruits) {
    	
//    	///////////////////for loops to check , debug////////////////////////////////
//    	for (int i = 0; i < fruits.size(); i++)
//    	{
////
////			System.out.print(fruits.get(i));
////	    	System.out.println("   on edge : "+fruits.get(i).get_edge().getSrc()+"--"+fruits.get(i).get_edge().getDest());
//		}
//    	for (int i = 0; i <agents.size(); i++)
//    	{
////
////    		System.out.print("agent munber "+agents.get(i).getID());
////        	System.out.println("   in node src"+agents.get(i).getSrcNode());
//		}
//// //////////////////////////////////////////////////////////////////////////////////////////////////////////   

        for (int k = 0; k < fruits.size(); k++) {//

        }


        pair = new HashMap<>(); // { agent_id = fuirt }// //

        // {a1 2, a2 1}
        for (int i = 0; i < agents.size(); i++) {/// 

            double min = Double.MAX_VALUE;
            CL_Agent ag = null;
            CL_Pokemon f = null;
            int a = -1;

            for (int j = 0; j < agents.size(); j++) {/// on agents 

                if (!agents.get(j).getStatus()) {

                   
                    	for (int k = 0; k < fruits.size(); k++) {//
                        if (!fruits.get(k).getStatus()) {
                        	

                            double d = algo.shortestPathDist(agents.get(j).getSrcNode(),fruits.get(k).get_edge().getSrc())
                                    + fruits.get(k).get_edge().getWeight();

                            d = d / (fruits.get(k).getValue()*(agents.get(j).getSpeed())); // consider agent speed

//                            System.out.print("  agent id = "+j);
//                            System.out.print("   d = "+d);
//                            System.out.println(" fruit num  k = "+k);

                            if (d < min) {
                                min = d;
//                                System.out.println("inter  if new min below :");
//                                System.out.print("d = "+d);
//                                System.out.print("   k = "+k);
//                                System.out.print("   f = "+fruits.get(k));
//                                System.out.println("  agent id = "+j);
//                                System.out.println("");
                                f = fruits.get(k);
                                a = agents.get(j).getID();
                                ag = agents.get(j);
                               
//                                System.out.println("time to end : "+game.);
                                
                            }
                        }

                    }
                }

            }


            assert f != null;
            f.setStatus(true);
            ag.setStatus(true);
            System.out.println(" pair number "+i+" : agent in src : "+ag.getSrcNode()+" to  fruit in edge : "+f.get_edge().getSrc()+" - "+f.get_edge().getDest()+" distance = "+min);
            pair.put(a, f);
            
//            System.out.println("agent num "+agents.get(j).getID()+ "srcNode num : "+agents.get(j).getSrcNode());
//            System.out.println("fruit : "+fruits.get(k).get_edge().getSrc()+" - "+fruits.get(k).get_edge().getDest());
//            System.out.println("minimum dist = "+d);
            List<node_data> l = algo.shortestPath(ag.getSrcNode(), f.get_edge().getSrc());
            l.add(gg.getNode(f.get_edge().getDest()));
            System.out.println(a+" agent go to "+f.get_edge().getDest()+"node");
            System.out.println(l);

            paths.put(a, l);


        }
//       System.out.println("pair  /////////////"+pair.toString());
//       System.out.println("path  /////////////"+paths.toString());
    }


    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgants(game_service game, directed_weighted_graph gg) throws InterruptedException {

        String lg = game.move();

        List<CL_Agent> agents = Arena.getAgents(lg, gg); // list of agents

        _ar.setAgents(agents);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
        String fs =  game.getPokemons();
       
        List<CL_Pokemon> fruit_list = Arena.json2Pokemons(fs); // list of pokemons

        _ar.setPokemons(fruit_list);
      
//        find_agent_paths(agents, fruit_list); this is not the first run

        for(int i = 0; i < agents.size() ;i++) {

            CL_Agent ag = agents.get(i);

            int id = ag.getID();

            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v = ag.getValue();

            
            if (ag.getSpeed() >= 5) flag = true;

           

            /**
             *
             * This is the main core for an Agent.
             * if agent.dest == -1 => The Agent is on a node, and
             * wants to know where to go.
             *
             */

            if(dest==-1) {

                count_dest++;

                /**
                 *
                 * dest = find() => your algorithm to find the best node
                 * for the Agent to advance.
                 *
                 */

            // dest = nextNode(gg, src);

                if (paths.get(id).isEmpty() || paths.get(id).size() == 1) {
                    agent_eat_fruit = true;
//                    test.put(agent_eat_fruit, id);
//                    find_agent_paths(agents, fruit_list);
                }
                
               // game.chooseNextEdge(ag.getID(), nextNode(gg, src));
                if (count_dest >= agents.size() && agent_eat_fruit) {
                    count_dest = 0;
                    System.out.println("count dest");
                    find_agent_paths(agents, fruit_list);

                    game.chooseNextEdge(ag.getID(), paths.get(id).remove(1).getKey());
                }
                else if (paths.get(id).size() > 1){
                    System.out.println("id: " + id + ", agent speed: " + ag.getSpeed());
                    game.chooseNextEdge(ag.getID(), paths.get(id).remove(1).getKey());
                }

              //  System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);

            }
        }

       // Thread.sleep(500000);
    }

    /**
     * a very simple random walk implementation!
     * @param g
     * @param src
     * @return
     */
    private static int nextNode(directed_weighted_graph g, int src) {
        int ans = -1;
        Collection<edge_data> ee = g.getE(src);
        Iterator<edge_data> itr = ee.iterator();
        int s = ee.size();
        int r = (int)(Math.random()*s);
        int i=0;
        while(i<r) {itr.next();i++;}
        ans = itr.next().getDest();
        return ans;
    }

    private void init(game_service game) {
    	
        paths = new HashMap<>();

        count_dest = 0;
        agent_eat_fruit = false;
        
        
        String g = game.getGraph();

        gg = build_Graph(g);

        
        String fs = game.getPokemons();

        _ar = new Arena();
        _ar.setGraph(gg);

        ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());

        _ar.setPokemons(cl_fs);
        _win = new MyFrame("test Ex2");
        _win.setSize(1000, 700);
        _win.update(_ar);


        _win.show();
        String info = game.toString();
        JSONObject line;
   ///     System.out.println(line.toString());
        
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            
            System.out.println(info);

            System.out.println(game.getPokemons());
            int src_node = 0;  // arbitrary node, you should start at one of the pokemon

            for(int a = 0;a<cl_fs.size();a++) {
                Arena.updateEdge(cl_fs.get(a),gg);
            }

            for(int a = 0;a<rs;a++) {

                double max = Double.MIN_VALUE;
                CL_Pokemon temp = null;

                for (CL_Pokemon fruit : cl_fs) {

                    if (!fruit.getFirstLocation()) {

//                        double x = fruit.get_edge().getWeight() / fruit.getValue();
                        double x = fruit.getValue();


                        if (x > max) {
                            max = x;
                            temp = fruit;
                        }

                    }

                }

                assert temp != null;
                temp.setFirstLocation(true);
               
              //  System.out.println("a_id: " + a +"a_edge: "+ttt.get(key)+ "fruit src " + temp.get_edge().getSrc());

                game.addAgent(temp.get_edge().getSrc());
            }
        }
        catch (JSONException e) {e.printStackTrace();}

        find_agent_paths(Arena.getAgents(game.getAgents(), gg), Arena.json2Pokemons(game.getPokemons()));
    }
}