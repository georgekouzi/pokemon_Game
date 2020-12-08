package api;

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

import gameClient.util.Point3D;


public class DWGraph_AlgoGW implements dw_graph_algorithms  {

	public static void main(String[] args) {

		directed_weighted_graph g=new DWGraph_DS();

		//		
		//		for(int i=0;i<1000000;i++) {
		//		g.addNode(new NodeData());
		//		}
		g.addNode(new NodeData());
		g.addNode(new NodeData());
		g.addNode(new NodeData());
		g.addNode(new NodeData());
		g.addNode(new NodeData());
		//				g.addNode(new NodeData());

		g.connect(0, 3, 8.22);
		g.connect(1, 2, 4.0);
		g.connect(1, 4, 6.34);
		g.connect(2, 3, 7.877);
		g.connect(3, 1, 6.565);
		g.connect(4, 2, 1.2);
		g.connect(4, 0, 7.5);
		//		g.connect(0, 2, 8.22);
		//		g.connect(4, 3, 1.2);
		//
		//		directed_weighted_graph g1=new DWGraph_DS();
		//		int size =  1000*1000;
		//		int ten=1;
		//		for (int i = 0; i <size; i++) {
		//			g1.addNode(new NodeData());
		//		}
		//
		//		for (int i = 0; i <size*10; i++) {
		//			int dest=i;
		//			g1.connect(size-2, i, 0.23); 
		//			g1.connect(i, size-2, 0.23); 
		//			g1.connect(i, size-4, 0.23); 
		//			g1.connect(size-4, i, 0.23); 
		//			g1.connect(i, size-8, 0.23); 
		//			g1.connect(size-8, i, 0.23); 
		//			g1.connect(i, size-10, 0.23); 
		//			g1.connect(size-10, i, 0.23); 
		//			g1.connect(i, size-140, 0.23); 
		//			g1.connect(size-12, i, 0.23); 
		//			g1.connect(i, size-1, 0.23); 
		//			g1.connect(size-1, i, 0.23); 
		////			if(i<size-1){
		////				g1.connect(i,++dest,0.78);
		////			}
		////			if(i%2==0&&i<size-2) {
		////				g1.connect(i,2+dest,0.94);
		////			}	
		////
		////			if(ten==i&&(i%2==0)) {
		////				for (int j =0 ; j <size; j++) {
		////					g1.connect(ten, j,0.56);
		////					g1.connect(ten-2, j, 0.4);
		////
		////				}
		////
		////				ten=ten*10;
		////			}


		//}
		dw_graph_algorithms algo= new DWGraph_AlgoGW();
		algo.init(g);
		//		System.out.println(algo.shortestPath(1, 3));
		//		algo.shortestPath(1, 3);

		//		for(node_data n :algo.shortestPath(0, 2)) {
		//			System.out.println(n.getKey());
		//		}
		//		System.out.println(algo.shortestPathDist(0, 2));
		//		System.out.println();
		//		//
		//		//g.removeNode(3);
		//		for(node_data n :algo.shortestPath(0, 4)) {
		//			System.out.println(n.getKey());
		//		}
		System.out.println(algo.isConnected());
		System.out.println(g.edgeSize());

		//		dw_graph_algorithms G= new DWGraph_AlgoGW();
		//		       G.init(g);
		//				G.save("hg.json");
		//				G.load("hg.json");

		//		G.load("A0");
		//				g1=G.copy();
		//		//		g.removeNode(5);
		//				System.out.println(g1);
		//		System.out.println(g);
		//		g1=G.copy();
		//		System.out.println(g1);
	}
	private directed_weighted_graph _DWGraph;
	private static int _count_connected_graphs=0;
	private HashMap<Integer,node_algo> node_info;
	private HashMap<Integer,Integer> lowlink;
	private Stack<node_data> stack;
	private int dijkstraSrc;
	private int modeCount;
	private int count;


	//////Constructor///////////////
	public DWGraph_AlgoGW(){
		_DWGraph=new DWGraph_DS();	
		dijkstraSrc=-1;
	}


	@Override
	public void init(directed_weighted_graph g) {
		_DWGraph=g;
		modeCount=_DWGraph.getMC();
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
		if(dijkstraSrc!=src||modeCount!=_DWGraph.getMC()) {
			Update(src);
			dijkstraAlgo(src);
		}
		//if there no path we return -1 like Infinite
		if(node_info.get(dest).pereants==-1)
			return -1;
		//the distance from the source to the destination save in destination tag
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

		if(_DWGraph.getNode(src)==null||_DWGraph.getNode(dest)==null) {
			return null;
		}
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
		if(node_info.get(dest).pereants==-1) return null;
		//Create a list that will store the cheapest list from the destination node to the source node.
		node_algo n = node_info.get(dest);
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
	/**
	 * Returns true if and only if (iff) there is a valid path from each node to each
	 * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
	 * @return
	 */
	@Override
	public boolean isConnected() {
		if(_DWGraph.nodeSize()==0) {
			return true;
		}
		SCCTarjan();
		return _count_connected_graphs == 1;
	}

	private void SCCTarjan() {
		ClearEverything();
		for (node_data n :_DWGraph.getV())
			if (n.getInfo().equals("false")) {
				dfs(n);
			}
	}





	void dfs(node_data n) {
		stack.add(n);
		lowlink.put(n.getKey(), count++);

		boolean isComponentRoot = true;
		n.setInfo("true");
		for (edge_data  v : getGraph().getE(n.getKey())) {
			if (getGraph().getNode(v.getDest()).getInfo().equals("false")) {
				dfs(getGraph().getNode(v.getDest()));
			}
			if (lowlink.get(n.getKey()) > lowlink.get(v.getDest())) {
				lowlink.replace(n.getKey(),lowlink.get(v.getDest()));
				isComponentRoot = false;
			}
		}


		if (isComponentRoot) {
			while (true) {
				node_data node= stack.pop();
				lowlink.replace(n.getKey(),Integer.MAX_VALUE);
				if (node.getKey() == n.getKey())
					break;
			}
			_count_connected_graphs++;
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
	 * This algorithm makes it possible to go over a weighted directed graph
	 * And find the cheapest ways from the source node to the rest of the graph nodes.
	 * The weights in the graph symbolize distance. 
	 * The shortest route between two points means the route with the lowest amount of weights between the two vertices.
	 * Ran time- O(E*log(V)) because we create PriorityQueue and compare the node by the minimum distance .
	 * @param src - the source node
	 */

	private void  dijkstraAlgo(int src) {
		System.out.println("inn");

		PriorityQueue<node_algo> p = new PriorityQueue<>(new minDistanse());
		node_info.put(src,new node_algo(src));
		node_info.get(src).dist=0.0;
		p.add(node_info.get(src));

		while(!p.isEmpty()) {
			//update the n node at the cheapest node and delete it from the Priority Queue p 
			node_algo n = p.poll();
			//running on the neighbors of n node
			for(edge_data neighbor: _DWGraph.getE(n.id)) {

				if(!node_info.containsKey(neighbor.getDest())) {
					node_info.put(neighbor.getDest(), new node_algo(neighbor.getDest()));
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
	private class node_algo{
		private	final int id;
		private	int pereants; 
		private double dist;
		private boolean vis;
		node_algo(int _id){
			id=_id;
			dist=Double.MAX_VALUE;
			vis=false;
			pereants=-1;
		}

	}

	/**
	 * inner class allows as to compare between two node distance.
	 */
	private class minDistanse implements Comparator<node_algo> {
		public int compare(node_algo dist1, node_algo dist2) {
			return  (int) (dist1.dist-dist2.dist);

		}

	}

	/**
	 * This function cleans all the node tag,info and Parent
	 */
	private void Update(int src) {
		node_info=new HashMap<Integer,node_algo>();
		modeCount=_DWGraph.getMC();
		dijkstraSrc=src;
	}

	private void ClearEverything() {
		lowlink= new HashMap<Integer,Integer>();
		stack = new Stack<node_data>();
		count=0;
		for (node_data i : _DWGraph.getV()) {
			i.setInfo("false");
			lowlink.put(i.getKey(), 0);
		}


	}






}
