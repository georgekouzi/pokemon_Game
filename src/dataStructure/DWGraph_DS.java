package dataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import api.directed_weighted_graph;
import api.edge_data;
import api.node_data;





public class DWGraph_DS implements directed_weighted_graph,Serializable  {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		HashMap<Integer,HashMap<Integer,Double>> _edgeData1223= new HashMap<Integer,HashMap<Integer,Double>>();;
		_edgeData1223.put(1, new HashMap<Integer,Double>());
		_edgeData1223.get(1).put(2, 5.6);


		if(_edgeData1223.get(1).containsKey(2)) {
			System.out.println("inn");
		}
	}

	// HashMap in HashMap hold all the edges and the weights in the graph: first key node_info(src), next key node_info(dest) and the value is double (weight).
	private HashMap<node_data,HashMap<node_data,edge_data>> _edgeData;
	// This HashMap hold all the nodes in the graph.  
	private HashMap<Integer,node_data> _nodeData;
	//count the number of edge
	private int sizeOfEdge;
	//count the changes in the graph
	private int modeCount;


	///Constructor///
	public DWGraph_DS() {
		_edgeData= new HashMap<node_data,HashMap<node_data,edge_data>>();
		_nodeData= new HashMap<Integer,node_data>();	
		sizeOfEdge=0;
		modeCount=0;
	}

	/**
	 * return the node_info by the node id if key node exist.
	 * run time- O(1).
	 * @param key - the node id
	 * @return the node_data by the node_id,return null if the node didn't create .
	 */
	@Override
	public node_data getNode(int key) {
		if(_nodeData.containsKey(key))
			return _nodeData.get(key) ;
		else 
			return null;

	}
	/**
	 * returns the data of the edge (src,dest), null if none.
	 * Note: this method should run in O(1) time.
	 * @param src
	 * @param dest
	 * @return
	 */
	@Override
	public edge_data getEdge(int src, int dest) {
		if(_nodeData.containsKey(src)&&_nodeData.containsKey(dest) &&_edgeData.containsKey(_nodeData.get(src))&&_edgeData.get(_nodeData.get(src)).containsKey(_nodeData.get(dest))) {
			return _edgeData.get(_nodeData.get(src)).get(_nodeData.get(dest));
		}
		else return null;
	}
	/**
	 * adds a new node to the graph with the given node_data.
	 * Note: this method should run in O(1) time.
	 * @param n
	 */
	@Override
	public void addNode(node_data n) {
		try {
			if(n.getKey()<0)
				throw new Exception("The key must be positive");
		} catch (Exception e) {e.printStackTrace(); return;	}	

		if(!(_nodeData.containsKey(n.getKey()))) {
			_nodeData.put(n.getKey(),n);		
			modeCount++;
		}
		else return;
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
		if(src==dest) {
			return;
		}
		//weight < 0 throw exception
		try {
			if(w<0)
				throw new Exception("The weight must be positive");
		} catch (Exception e) {e.printStackTrace(); return;	}	


		if(_nodeData.containsKey(src)&&_nodeData.containsKey(dest)) {
			// if the edge  already exists - the method simply updates the weight of the edge.
			if(_edgeData.containsKey(_nodeData.get(src))&&_edgeData.get(_nodeData.get(src)).containsKey(_nodeData.get(dest))) {
				//if this  weight not equal to the new equal we update mc.
				if(_edgeData.get(_nodeData.get(src)).get(_nodeData.get(dest)).getWeight()!=w) {
					modeCount++;
				}
				_edgeData.get(_nodeData.get(src)).put(_nodeData.get(dest),new EdgeData(src,dest,w));
				return;
			}
			else
				if(!_edgeData.containsKey(_nodeData.get(src)))	
					_edgeData.put(_nodeData.get(src), new HashMap<node_data,edge_data>());

			_edgeData.get(_nodeData.get(src)).put(_nodeData.get(dest),new EdgeData(src,dest,w) );
			sizeOfEdge++;
			modeCount++;

		}
		else 
			return;



	}
	/**
	 * This method returns a pointer (shallow copy) for the
	 * collection representing all the nodes in the graph. 
	 * Note: this method should run in O(1) time.
	 * @return Collection<node_data>
	 */
	@Override
	public Collection<node_data> getV() {
		return _nodeData.values();
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
		if(_nodeData.containsKey(node_id)&&_edgeData.containsKey(_nodeData.get(node_id))) {

			return _edgeData.get(_nodeData.get(node_id)).values() ;
		}
		else {
			Collection<edge_data> nib =new ArrayList<edge_data>();
			//return empty Collection
			return nib;
		}
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
		if(_nodeData.containsKey(key)) {
			//if node(key) have neighbors.
			node_data removeNode=_nodeData.get(key);
			//			if(_edgeData.containsKey(_nodeData.get(key))) {
			//System.out.println("innn");
			for(node_data n: getV()) {
				if(_edgeData.containsKey(n)&&_edgeData.get(n).containsKey(_nodeData.get(key))) {		
					_edgeData.get(n).remove(_nodeData.get(key));
					sizeOfEdge--;
					modeCount++;
				}
			}
			//			}
			sizeOfEdge=sizeOfEdge-_edgeData.get(_nodeData.get(key)).size();
			modeCount=modeCount+_edgeData.get(_nodeData.get(key)).size();
			_edgeData.remove(_nodeData.get(key));
			_nodeData.remove(key);
			return removeNode; 
		}
		else
			return null;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		if(_nodeData.containsKey(src)&&_nodeData.containsKey(dest) &&_edgeData.containsKey(_nodeData.get(src))&&_edgeData.get(_nodeData.get(src)).containsKey(_nodeData.get(dest))) {
			edge_data ed= _edgeData.get(_nodeData.get(src)).get(_nodeData.get(dest));
			_edgeData.get(_nodeData.get(src)).remove(_nodeData.get(dest));
			//count the edge size and mode count.
			sizeOfEdge--;
			modeCount++;
			return ed; 
		}
		else return null;


	}

	@Override
	public int nodeSize() {
		return _nodeData.size();
	}

	@Override
	public int edgeSize() {
		return sizeOfEdge;
	}

	@Override
	public int getMC() {
		return modeCount;
	}

	/**
	 * string function return all the data of nodes and edge on the graph
	 * @return s
	 */
	public String toString() {

		String s="";
		for(node_data i : getV()) {
			s+="\n"+"Node= "+ i.getKey()+" in position: "+i.getLocation()+"\n";
			if(!_edgeData.containsKey(i))
				s+="The node has no connections"+"\n";
			else {
				for(edge_data j : getE(i.getKey())) {
					s+="connect to node: "+j.getDest()+" , the edge is: < "+j.getSrc()+","+j.getDest()+" >"+"and the weight between them is: "+j.getWeight() +"\n";
				}	
			}
		}
		return s; 

	}

}
