package algorithms;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.edge_data;
import api.geo_location;
import api.node_data;
import dataStructure.DWGraph_DS;
import dataStructure.NodeData;
import gameClient.util.Point3D;
/**
 *This class knows how to do operations on graphs.
 *We can check if the graph is Strongly Connected, check the cheap path between two nodes and the cheap distance between two nodes.
 *We can save the graph on json file and load the graph from json file.
 *This class uses a DFS algorithm that allows running on all nodes in the graph and do transpoz to the graph edge in run time O(n+m).
 *This class uses a Dijkstra algorithm that allows running on all nodes in the graph and find the cheap path two nodes in run time O(E*log(V)).
 *https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm.
 *https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm.
 *@author George kouzy and Dolev Saadia. *
 */

public class DWGraph_AlgoGW implements dw_graph_algorithms  {

	private directed_weighted_graph _DWGraph;
	private TarjanAlgo Tarjan_Algo;
	private HashMap<Integer,nodeAlgo> node_info;

	//We save the modeCount and the source node so that we do not need to  join the dijkstra algorithm again. 
	//if the graph has not changed and we look for the short distances or short list path to the same source node.
	//////////////////////////////////////////////
	private int dijkstraSrc;
	private int modeCount;
	//////////////////////////////////////////////


	//////Constructor///////////////
	public DWGraph_AlgoGW(){
		_DWGraph=new DWGraph_DS();	
		dijkstraSrc=-1;
	}
	/**
	 * This function allows you to point this graph.
	 * @param g
	 */

	@Override
	public void init(directed_weighted_graph g) {
		_DWGraph=g;
		modeCount=_DWGraph.getMC();
	}
	/**
	 * This function return this graph.
	 * @return -this graph
	 */
	@Override
	public directed_weighted_graph getGraph() {
		return _DWGraph;
	}
	/**
	 * This function returns a deep copy of the graph
	 * run time o(m+n)
	 * @return - new graph.
	 */
	@Override
	public directed_weighted_graph copy() {
		directed_weighted_graph g =new DWGraph_DS();
		for ( node_data i: _DWGraph.getV()) {
			g.addNode(new NodeData(i.getKey()));
			g.getNode(i.getKey()).setLocation(i.getLocation());
			for ( edge_data j:_DWGraph.getE(i.getKey())) {			
				if(g.getNode(j.getDest())!=null)
					g.connect(j.getSrc(), j.getDest(), j.getWeight());
				else {
					g.addNode(new NodeData(j.getDest()));
					g.connect(j.getSrc(), j.getDest(), j.getWeight());
				}

			}

		}

		return g;
	}


	/**
	 *   This function checks if there is a Path from each node to all the nodes With Dfs Algorithm-
	 *   this is an improved DFS called Tarjan Algorithm.
	 *   after DFS Algorithm  we checking the number of connected graphs(_count_connected_graphs) if _count_connected_graphs equal to 1
	 *   So the graph is strongly connected and can be reached from any node to any other node.
	 *   we use inner class Tarjan_Algo- serves data Structure: stack ,lowlink ,count and _count_connected_graphs.
	 *   run time O(E + V): E- the number of ribs, V- the number of nodes.  
	 *   @return -True if and only if (iff) there is a valid path from each node to each else return false
	 */


	@Override
	public boolean isConnected() {
		if(_DWGraph.nodeSize()==0) {
			return true;
		}
		Tarjan_Algo= new TarjanAlgo();
		for (node_data n :_DWGraph.getV()) {
			if (n.getInfo().equals("false")) 
				DFS(n);
		}
		return (Tarjan_Algo._count_connected_graphs == 1);

	}



	/**
	 * returns the length of the shortest path between src to dest
	 * if src==dest we return 0 or if no path between the node return -1.
	 * In order to find the shortest distance from source node to destination node  we will use the algorithm of dijkstra.
	 * @param src - source node.
	 * @param dest -  destination node.
	 * @return -shortest distance.
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		if(_DWGraph.getE(src).isEmpty()||_DWGraph.getNode(src)==null||_DWGraph.getNode(dest)==null) {

			return-1;	
		}
		if(src==dest) { 
			return 0.0;
		}	
		if(dijkstraSrc!=src||modeCount!=_DWGraph.getMC()) {
			Update(src);
			dijkstraAlgo(src);
		}

		//if there no path we return -1 like Infinite
		if(node_info.get(dest).pereants==-1) {
			return -1;
		}
		//the distance from the source to the destination save in destination dist in inner class nodeAlgo.
		return node_info.get(dest).dist;
	}
	/**
	 * returns the the shortest path between src to dest - as an ordered List of nodes:
	 * source--> n1-->n2-->...destination
	 * if no such path --> returns null;
	 * In order to find the shortest path from source node to destination node we will use the algorithm of dijkstra.
	 * @param src - source node
	 * @param dest - destination  node
	 * @return - list of the shortest path between source to destination
	 */
	@Override
	public List<node_data> shortestPath(int src, int dest) {

		if(_DWGraph.getNode(src)==null||_DWGraph.getNode(dest)==null||_DWGraph.getE(src).isEmpty()) {
			return null;
		}
		//Create a list that will store the cheapest list from the destination node to the source node.
		List<node_data>  c = new ArrayList<>();

		if(src==dest) {
			c.add(_DWGraph.getNode(src));
			return c;
		}
		if(dijkstraSrc!=src||modeCount!=_DWGraph.getMC()) {
			Update(src);
			dijkstraAlgo(src);
		}

		//if the parent of node  equal to -1 we returns null.
		if(node_info.get(dest).pereants==-1) { return null;};

		nodeAlgo n = node_info.get(dest);
		//run from destination node to the source node.
		while(n.id!=src) {
			//add destination--> add parent destination-->add parent of parent destination-->add...Until n is equal to the source node
			c.add(_DWGraph.getNode(n.id));
			//Parent update of node
			n=node_info.get(n.pereants);
		}
		//Adding the source node to the list
		c.add(_DWGraph.getNode(src));
		//Arrange a list in reverse order
		Collections.reverse(c);

		return c;


	}
	/**
	 * Saves this directed weighted graph to this json file name(String file).
	 * This function path of json file name on the computer. 
	 * The default path to this file is in the project folder.
	 * we use inner class call serialize- Which allows us to extract the information we need and write it into the json file.
	 * The information we want to keep is:
	 *array of all edges and array of all nodes.
	 *run time: O(E*V)- E - edges and V-nodes.
	 *we use gson jar to get json file saved.
	 * if the save successfully The default path to this file is in the project folder.
	 * @param  String file - The file name.
	 * @return  - if the file was successfully saved return true else false
	 */

	@Override
	public boolean save(String file) {

		try {

			Gson gson =  new GsonBuilder().create();
			String stringJson=	gson.toJson(new serialize().serialize(_DWGraph, getClass(), null));
			FileWriter fw = new FileWriter(file);

			fw.write(stringJson);
			fw.flush();
			fw.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}			

	}

	/**
	 * Load this json file (directed weighted graph).
	 * This function path of json file name on the computer. 
	 * The default path to this file is in the project folder.
	 * we use inner class call deserialize- Which allows us to extract the information from the json file that we need and read it and create a new graph .
	 * and its data can be used to recreate the object in memory.
	 *The information we want to load is: array of all edges and array of all nodes.
	 *run time: O(E*V)- E - edges and V-nodes.
	 *we use gson jar to get json file load.
	 * @param  String file - The file name.
	 * @return  - if the file was successfully load return true else false
	 */
	@Override
	public boolean load(String file) {
		try {
			GsonBuilder build = new GsonBuilder();
			build.registerTypeAdapter(directed_weighted_graph.class,new deserialize());
			Gson gson = build.create();			
			FileReader reader = new FileReader(file);
			directed_weighted_graph newg= gson.fromJson(reader, directed_weighted_graph.class) ; 
			init(newg);
			reader.close();  
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}			

	}



	private void DFS(node_data n) {

		Tarjan_Algo.stack.add(n);
		Tarjan_Algo.lowlink.put(n.getKey(),Tarjan_Algo.count++);

		boolean isComponentRoot = true;
		n.setInfo("true");

		for (edge_data v : getGraph().getE(n.getKey())) {
			if (getGraph().getNode(v.getDest()).getInfo().equals("false")) {
				DFS(getGraph().getNode(v.getDest()));
			}
			if (Tarjan_Algo.lowlink.get(n.getKey()) > Tarjan_Algo.lowlink.get(v.getDest())) {
				Tarjan_Algo.lowlink.replace(n.getKey(),Tarjan_Algo.lowlink.get(v.getDest()));
				isComponentRoot = false;
			}
		}


		if (isComponentRoot) {
			while (true) {
				node_data node=Tarjan_Algo.stack.pop();
				Tarjan_Algo.lowlink.replace(node.getKey(),Integer.MAX_VALUE);
				if(node.getKey() == n.getKey())
					break;
			}
			Tarjan_Algo._count_connected_graphs++;
		}
	}





	/**
	 * This algorithm makes it possible to go over a weighted directed graph
	 * And find the cheapest ways from the source node to the rest of the graph nodes.
	 * The weights in the graph symbolize distance. 
	 * The shortest route between two points means the route with the lowest amount of weights between the two vertices.
	 * we use inner class that call nodeAlgo to save all the  data that dijkstra algorithm need.
	 * Ran time- O(E*log(V)) because we create PriorityQueue and compare the node by the minimum distance .
	 * @param src - the source node
	 */

	private void  dijkstraAlgo(int src) {

		PriorityQueue<nodeAlgo> p = new PriorityQueue<>(new minDistanse());
		node_info.put(src,new nodeAlgo(src));
		node_info.get(src).dist=0.0;
		p.add(node_info.get(src));

		while(!p.isEmpty()) {
			//update the n node at the cheapest node and delete it from the Priority Queue p 
			nodeAlgo n = p.poll();

			//running on the neighbors of n node
			for(edge_data neighbor: _DWGraph.getE(n.id)) {
				if(!node_info.containsKey(neighbor.getDest())) {
					node_info.put(neighbor.getDest(), new nodeAlgo(neighbor.getDest()));
				}
				//If we did not visited in this node we will enter to the if
				if(!node_info.get(neighbor.getDest()).vis) {
					//Calculate the updated price(Tag) of n node + the price of the side between n and its neighbor	(neighbor)				
					double total =node_info.get(n.id).dist+neighbor.getWeight();
					if(node_info.get(neighbor.getDest()).dist>total) {
						//updated tag,PriorityQueue p and parent of node neighbor.
						node_info.get(neighbor.getDest()).dist=total;
						p.add(node_info.get(neighbor.getDest()));
						node_info.get(neighbor.getDest()).pereants=n.id;

					}

				}

				//when we finish to visit in the node n we don't wont to visit in this node again 
				n.vis=true;

			}
		}
	}


	/**
	 * This function crate all the node tag,info and Parent
	 * @param src -
	 */
	private void Update(int src) {
		node_info=new HashMap<Integer,nodeAlgo>();
		//
		modeCount=_DWGraph.getMC();
		dijkstraSrc=src;
	}

	/**
	 * This inner class serves as an auxiliary class for the dijkstra algorithm enabling the creation of smart nodes
	 * It keep the ID(id) of each node in our graph ,keep the node(pereants) that comes before the this node in the shortest path, 
	 * also keeps the distance(dist) from the source point to the current node, 
	 * and in addition keeps a Boolean variable(vis) that informs the algorithm if he visited this node or not.
	 */

	private class nodeAlgo{
		private	final int id;
		private	int pereants; 
		private double dist;
		private boolean vis;
		nodeAlgo(int _id){
			id=_id;
			dist=Double.MAX_VALUE;
			vis=false;
			pereants=-1;
		}

	}



	/**
	 * This class serves as an auxiliary class for the Tarjan algorithm that uses an improved dfs algorithm.
	 * This class makes use of: 
	 * 1)_count_connected_graphs- Count how many graphs we have connected, 
	 * if we accept that there is only one graph connected then we can say that our graph is strongly connected.
	 * 2)Stack-
	 * 
	 *
	 */
	private class TarjanAlgo{
		private int _count_connected_graphs;
		private Stack<node_data> stack;
		private HashMap<Integer,Integer> lowlink;
		private int count;


		TarjanAlgo(){
			lowlink= new HashMap<Integer,Integer>();
			stack = new Stack<node_data>();
			_count_connected_graphs=0;
			count=0;

			for (node_data i : _DWGraph.getV()) {
				i.setInfo("false");
				lowlink.put(i.getKey(), 0);

			}

		}


	}

	/**
	 * inner class allows as to compare between two node distance.
	 */
	private class minDistanse implements Comparator<nodeAlgo> {
		public int compare(nodeAlgo dist1, nodeAlgo dist2) {
			return  (int) (dist1.dist-dist2.dist);

		}

	}
	/**
	 * This class is used as an auxiliary class for the load function. Allows you to disassemble a json file into an array
	 * of nodes and array of edges and in the transition to the new graph we insert the new vertices and edges.
	 *This class inherits from JsonDeserializer which makes it easier for the gson 
	 *to build a json file according to the parent in the function of the internal class deserialize.
	 */

	private class deserialize implements JsonDeserializer<directed_weighted_graph>{

		@Override
		public directed_weighted_graph deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)throws JsonParseException {
			JsonObject jsonObject = arg0.getAsJsonObject();
			JsonArray edgeJsonObj = jsonObject.get("Edges").getAsJsonArray();
			JsonArray nodeJsonObj = jsonObject.get("Nodes").getAsJsonArray();
			directed_weighted_graph g =new DWGraph_DS();
			for(JsonElement copy :nodeJsonObj) {
				node_data n = new NodeData(copy.getAsJsonObject().get("id").getAsInt());
				if(copy.getAsJsonObject().has("pos")) {
					geo_location pos = new Point3D(copy.getAsJsonObject().get("pos").getAsString());
					n.setLocation(pos);
				}
				else {
					n.setLocation(null);
				}
				g.addNode(n);
			}

			for(JsonElement copy :edgeJsonObj) {
				g.connect(copy.getAsJsonObject().get("src").getAsInt(), copy.getAsJsonObject().get("dest").getAsInt(), copy.getAsJsonObject().get("w").getAsDouble());
			}

			return g;

		}



	}
	/**
	 * This class is used as an auxiliary class for the save function. Allows you to to read a json array file.
	 * read json array file  of edges and nodes.
	 *This class inherits from JsonSerializer which makes it easier for the gson 
	 *to read a json file according to the parent in the function of the internal class serialize.
	 */
	private class serialize implements JsonSerializer<directed_weighted_graph>{
		@Override
		public JsonElement serialize(directed_weighted_graph graph, Type arg1, JsonSerializationContext arg2) {

			JsonArray edgeJsonArray = new JsonArray();
			JsonArray nodeJsonArray = new JsonArray();
			for(node_data node :graph.getV()) {
				JsonObject nodeObject = new JsonObject();
				if(node.getLocation()!=null) 
					nodeObject.addProperty("pos", node.getLocation().toString());
				nodeObject.addProperty("id", node.getKey());
				nodeJsonArray.add(nodeObject);

				for(edge_data edge :graph.getE(node.getKey())) {
					JsonObject edgeObject = new JsonObject();
					edgeObject.addProperty("src",edge.getSrc());
					edgeObject.addProperty("w",edge.getWeight());
					edgeObject.addProperty("dest",edge.getDest());
					edgeJsonArray.add(edgeObject);

				}
			}

			JsonObject jsonObject = new JsonObject();
			jsonObject.add("Nodes",nodeJsonArray);
			jsonObject.add("Edges",edgeJsonArray);
			return jsonObject;
		}

	}



}
