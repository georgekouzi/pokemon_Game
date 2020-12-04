package api;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Map.Entry;

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

import gameClient.util.Point3D;







public class DWGraph_AlgoGW implements dw_graph_algorithms  {

	public static void main(String[] args) {

		directed_weighted_graph g=new DWGraph_DS(),g1;
		g.addNode(new NodeData());
		g.addNode(new NodeData());
		g.addNode(new NodeData());
		g.addNode(new NodeData());
		//		g.connect(5, 6, 3.0);
		g.connect(0, 1, 3.0);
		g.connect(0, 1, 3.0);

		//
		//		g.connect(6, 7, 3.0);
		//		g.connect(8, 5, 3.0);
		//		g.connect(8, 6, 3.0);
		//		g.connect(8, 8, 3.0);

		//		System.out.println(g.edgeSize());
		dw_graph_algorithms G= new DWGraph_AlgoGW();
		       G.init(g);
				G.save("hg.json");
				G.load("hg.json");

//		G.load("A0");
//				g1=G.copy();
//		//		g.removeNode(5);
//				System.out.println(g1);
		//		System.out.println(g);
//		g1=G.copy();
//		System.out.println(g1);
	}
	private directed_weighted_graph _DWGraph;
	// count the number of visited
	private static int _count_vis;

	//////Constructor///////////////
	public DWGraph_AlgoGW(){
		_DWGraph=new DWGraph_DS();	
	}


	@Override
	public void init(directed_weighted_graph g) {
		_DWGraph=g;
	}

	@Override
	public directed_weighted_graph getGraph() {
		return _DWGraph;
	}

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
	 * Returns true if and only if (iff) there is a valid path from each node to each
	 * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
	 * @return
	 */
	@Override
	public boolean isConnected() {
		return false;
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
		if(_DWGraph.getNode(src)==null||_DWGraph.getNode(dest)==null) 
			return-1;	
		if(src==dest) { 
			return 0.0;
		}	
		ClearEverything();
		dijkstraAlgo(src);
		//if there no path we return -1 like Infinite
		if(_DWGraph.getNode(dest).getTag()==-1)
			return -1;
		//the distance from the source to the destination save in destination tag
		return _DWGraph.getNode(dest).getWeight();
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

		if(_DWGraph.getNode(src)==null||_DWGraph.getNode(dest)==null) 
			return null;
		List<node_data>  c = new ArrayList<node_data>();

		if(src==dest) {
			c.add(_DWGraph.getNode(src));
			return c;
		}

		ClearEverything();
		dijkstraAlgo(src);
		//if the parent of node dest equal to -1 we returns null.
		if(_DWGraph.getNode(dest).getTag()==-1) return null;
		//Create a list that will store the cheapest list from the destination node to the source node.
		node_data n = _DWGraph.getNode(dest);
		//run from destination node to the source node.
		while(n!=_DWGraph.getNode(src)) {
			//add destination--> add parent destination-->add parent of parent destination-->add...Until n is equal to the source node
			c.add(n);
			//Parent update of node
			n=_DWGraph.getNode(n.getTag());
		}
		//Adding the source node to the list
		c.add(_DWGraph.getNode(src));
		//Arrange a list in reverse order
		Collections.reverse(c);

		return c;


	}

	@Override
	public boolean save(String file) {

		try {

			Gson gson =  new GsonBuilder().create();
			String j=	gson.toJson(new serialize().serialize(_DWGraph, getClass(), null));
			FileWriter fw = new FileWriter(file);
			fw.write(j);
			fw.flush();
			fw.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}			

	}


	@Override
	public boolean load(String file) {
		try {
			GsonBuilder bild = new GsonBuilder();
			bild.registerTypeAdapter(directed_weighted_graph.class,new deserialize());
			Gson gson = bild.create();			
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


	private class deserialize implements JsonDeserializer<directed_weighted_graph>{

		@Override
		public directed_weighted_graph deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)throws JsonParseException {
			JsonObject jsonObject = arg0.getAsJsonObject();
			JsonArray edgeJsonObj = jsonObject.get("Edges").getAsJsonArray();
			JsonArray nodeJsonObj = jsonObject.get("Nodes").getAsJsonArray();
			directed_weighted_graph g =new DWGraph_DS();
			for(JsonElement copy :nodeJsonObj) {
				node_data n = new NodeData(copy.getAsJsonObject().get("id").getAsInt());
				geo_location pos = new Point3D(copy.getAsJsonObject().get("pos").getAsString());
				n.setLocation(pos);
				g.addNode(n);
			}

			for(JsonElement copy :edgeJsonObj) {
				g.connect(copy.getAsJsonObject().get("src").getAsInt(), copy.getAsJsonObject().get("dest").getAsInt(), copy.getAsJsonObject().get("w").getAsDouble());
			}


			return g;

		}

		

	}
	private class serialize implements JsonSerializer<directed_weighted_graph>{
		@Override
		public JsonElement serialize(directed_weighted_graph graph, Type arg1, JsonSerializationContext arg2) {
			JsonObject nodeObject = new JsonObject();
			JsonObject edgeObject = new JsonObject();
			JsonArray edgeJsonArray = new JsonArray();
			JsonArray nodeJsonArray = new JsonArray();
			for(node_data node :graph.getV()) {
				nodeObject.addProperty("pos", node.getLocation().toString());
				nodeObject.addProperty("id", node.getKey());
				nodeJsonArray.add(nodeObject);
				for(edge_data edge :graph.getE(node.getKey())) {
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

	/**
	 * This algorithm makes it possible to go over a weighted undirected graph
	 * And find the cheapest ways from the source node to the rest of the graph nodes.
	 * The weights in the graph symbolize distance. 
	 * The shortest route between two points means the route with the lowest amount of weights between the two vertices.
	 * Ran time- O(E*log(V)) because we create PriorityQueue and compare the node by the minimum distance .
	 * @param src - the source node
	 */
	private void  dijkstraAlgo(int src) {

		PriorityQueue<node_data> p = new PriorityQueue<node_data>(new minDistanse());


		_DWGraph.getNode(src).setWeight(0.0);
		p.add(_DWGraph.getNode(src));

		while(!p.isEmpty()) {

			//update the n node at the cheapest node and delete it from the Priority Queue p 
			node_data n = p.poll();
			//running on the neighbors of n node
			for(edge_data neighbor: _DWGraph.getE(n.getKey())) {
				//If we did not visited in this node we will enter to the if
				if(_DWGraph.getNode(neighbor.getDest()).getInfo()=="false") {
					//				if(neighbor.getInfo()=="false") {
					//Calculate the updated price(Tag) of n node + the price of the side between n and its neighbor	(neighbor)				
					double total =n.getWeight()+neighbor.getWeight();
					if(neighbor.getWeight()>total) {
						//System.out.println(total+"-->"+neighbor.getKey());

						//updated tag,PriorityQueue p and parent of node neighbor.
						_DWGraph.getNode(neighbor.getDest()).setWeight(total);
						//						neighbor.setTag(total);
						p.add(_DWGraph.getNode(neighbor.getDest()));
						_DWGraph.getNode(neighbor.getDest()).setTag(n.getKey());
						//						((node_data) neighbor).setParent(n);

					}

				}

				//when we finish to visit in the node n we don't wont to visit in this node again 
				n.setInfo("true");

			}

		}

	}

	/**
	 * inner class allows as to compare between two node distance.
	 */
	private class minDistanse implements Comparator<node_data> {
		public int compare(node_data dist1, node_data dist2) {
			return  (int) (dist1.getWeight()-dist2.getWeight());

		}

	}

	/**
	 * This function cleans all the node tag,info and Parent
	 */
	private void ClearEverything() {
		for (node_data i : _DWGraph.getV()) { 
			i.setWeight(Double.MAX_VALUE);
			i.setInfo("false");
			i.setTag(-1);
		}
	}












}
