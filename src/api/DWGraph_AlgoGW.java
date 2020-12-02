package api;

import java.util.List;

import ex1.src.WGraph_DS;
import ex1.src.weighted_graph;

public class DWGraph_AlgoGW implements dw_graph_algorithms  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private DWGraph_DS _DWGraph;
	// count the number of visited
	private static int _count_vis;

	//////Constructor///////////////
	public DWGraph_AlgoGW(){
		_DWGraph=new DWGraph_DS();	
	}


	@Override
	public void init(directed_weighted_graph g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public directed_weighted_graph getGraph() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public directed_weighted_graph copy() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean load(String file) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
