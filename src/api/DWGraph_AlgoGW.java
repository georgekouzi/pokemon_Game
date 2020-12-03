package api;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;




public class DWGraph_AlgoGW implements dw_graph_algorithms  {

	public static void main(String[] args) {

		directed_weighted_graph g=new DWGraph_DS(),g1;
		g.addNode(new NodeData(5));
		g.addNode(new NodeData(6));
		g.addNode(new NodeData(7));
		g.addNode(new NodeData(8));
//		g.connect(5, 6, 3.0);
		g.connect(5, 7, 3.0);
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
		g1=G.copy();
		g.removeNode(5);
		System.out.println(g1);
		System.out.println(g);

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

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(String file) {

		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter fw = new FileWriter(file);
			gson.toJson(_DWGraph, fw);
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


	public class deserialize implements JsonDeserializer<directed_weighted_graph>{

		@Override
		public directed_weighted_graph deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)throws JsonParseException {

			JsonObject jsonObject = arg0.getAsJsonObject();
			JsonObject edgeJsonObj = jsonObject.get("_edgeData").getAsJsonObject();
			JsonObject nodeJsonObj = jsonObject.get("_nodeData").getAsJsonObject();
			directed_weighted_graph g =new DWGraph_DS();
			for(Entry<String, JsonElement> copy :nodeJsonObj.entrySet()) {
				JsonElement Element = copy.getValue();
				node_data n = new NodeData(Element.getAsJsonObject().get("_key").getAsInt());
				g.addNode(n);


			}

			for(Entry<String, JsonElement> copy :edgeJsonObj.entrySet()) {
				Iterator<Entry<String, JsonElement>> it= copy.getValue().getAsJsonObject().entrySet().iterator();
				while(it.hasNext()) {
					JsonElement Element = it.next().getValue();
					g.connect(Element.getAsJsonObject().get("_src").getAsInt(), Element.getAsJsonObject().get("_dest").getAsInt(), Element.getAsJsonObject().get("_w").getAsInt());
				}

			}			
			return g;

		}


	}



















}
