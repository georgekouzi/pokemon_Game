package dataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import api.directed_weighted_graph;
import api.edge_data;
import api.node_data;


/**
 This class creates an directed weighted graph that holds the addition of nodes
 * You can add new nodes, connect two nodes by ribs, delete nodes, delete ribs between two nodes, 
 * you can check if a rib exists, you can get a particular node, 
 * you can get the list of all the neighbors of a particular node and the list of nodes.
 * In addition, you can know the number of nodes in the graph, the number of ribs 
 * and the number of actions performed in the graph (such as deleting the addition of a node)
 * Each node has the option to keep its neighbors in HashMap of HashMap- (_edgeData) and all the node that we add to the graph save in HashMap- (_nodeData).   
 *@author George kouzy and Dolev Saadia. 
 */


public class DWGraph_DS implements directed_weighted_graph,Serializable  {

	private static final long serialVersionUID = 1L;
	// HashMap in HashMap hold all the edges and the weights in the graph: first key node_info(src), next key node_info(_nodeData) and the value is obj (edge_data).
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
	 *check if src exist and dest exist and if there is an edge between them. 
	 *In case there is no such edge - should return -null
	 * returns the data of the edge [src , dest , Weight].
	 * Note: this method should run in O(1) time.
	 * @param src
	 * @param dest
	 * @return - obj-edge data.
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
	 * run time- O(1).
	 * if there is already a node with such a key no action be performed.
	 * This is an action that is done on the graph, so mode count need to be updated(+1).
	 * @param n
	 */
	@Override
	public void addNode(node_data n) {
		if(!(_nodeData.containsKey(n.getKey()))) {
			_nodeData.put(n.getKey(),n);		
			modeCount++;
		}
		else return;
	}
	/**
	 * Connects an edge with weight w between node src to node dest.
	 * run time- O(1) .
	 * if the edge src-dest already exists - the method simply updates the weight of the edge object.
	 * Connect an edge between src and dest, with an edge with weight >=0.
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
			else if(_edgeData.containsKey(_nodeData.get(dest))&&_edgeData.get(_nodeData.get(dest)).containsKey(_nodeData.get(src))) {
				if(!_edgeData.containsKey(_nodeData.get(src)))	
					_edgeData.put(_nodeData.get(src), new HashMap<node_data,edge_data>());
				_edgeData.get(_nodeData.get(src)).put(_nodeData.get(dest),new EdgeData(src,dest,w));				
				_edgeData.get(_nodeData.get(dest)).put(_nodeData.get(src),new EdgeData(dest,src,w));				
				sizeOfEdge++;
				modeCount++;
				return;
			}
			else  
				if(!_edgeData.containsKey(_nodeData.get(src))) {	
					_edgeData.put(_nodeData.get(src), new HashMap<node_data,edge_data>());
				}
			_edgeData.get(_nodeData.get(src)).put(_nodeData.get(dest),new EdgeData(src,dest,w));
			sizeOfEdge++;
			modeCount++;

		}
		else 
			return;



	}
	/**
	 * This method returns a pointer (shallow copy) for the
	 * collection representing all the nodes in the graph. 
	 * run time O(1).
	 * @return Collection of all the node in this graph.
	 */
	@Override
	public Collection<node_data> getV() {
		return _nodeData.values();
	}
	/**
	 * This method returns a pointer (shallow copy) for the
	 * collection representing all the edges getting out of 
	 * the given node (all the edges starting (source) at the given node). 
	 * run time O(1).
	 * @return Collection<node_info> else return empty Collection.
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
	 *run time O(1).
	 * @return the data of the removed node (null if none). 
	 * @param key
	 */

	@Override
	public node_data removeNode(int key) {
		if(_nodeData.containsKey(key)) {

			//if node(key) have neighbors.
			node_data removeNode=_nodeData.get(key);

			for(node_data n: getV()) {
				if(_edgeData.containsKey(n)&&_edgeData.get(n).containsKey(_nodeData.get(key))) {		
					_edgeData.get(n).remove(_nodeData.get(key));
					sizeOfEdge--;
					modeCount++;
				}
			}
			if(_edgeData.containsKey(_nodeData.get(key))) {		       
				sizeOfEdge=sizeOfEdge-_edgeData.get(_nodeData.get(key)).size();
				modeCount=modeCount+_edgeData.get(_nodeData.get(key)).size();
				_edgeData.remove(_nodeData.get(key));

			}			
			_nodeData.remove(key);

			return removeNode; 
		}
		else
			return null;
	}
	/**
	 * Deletes the edge from the graph,
	 * run time O(1).
	 * @param src
	 * @param dest
	 * @return the data of the removed edge if not exist return null.
	 */
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
	/** return the number of vertices (nodes) in the graph.
	 * run time O(1).
	 * @return - int number of node in the graph.
	 */
	@Override
	public int nodeSize() {
		return _nodeData.size();
	}
	/**
	 * return the number of edges (directed graph).
	 * run time O(1).
	 * @return int edges size.
	 */
	@Override
	public int edgeSize() {
		return sizeOfEdge;
	}
	/**
	 * return the Mode Count - for testing changes in the graph.
	 * Any change(remove node and edge,add node, connect between two nodes and update weight between two nodes that already exist)
	 * in the inner state of the graph should cause an increment in the ModeCount
	 * @return - int mode count.
	 */

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
	/**
	 * equals function -  if two graph are equal return true
	 * @return true if this edge_data equal to other edge_data and this node_data equal to other node_data
	 */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DWGraph_DS)) {
			return false;
		}
		DWGraph_DS otherObject =  (DWGraph_DS) obj;
		if (!_nodeData.equals(otherObject._nodeData)||!(_edgeData.equals(otherObject._edgeData))) {
			return false;
		}
		else
			return true;

	}
}
