package dataStructure;

import java.io.Serializable;

import api.edge_data;
/**
 *This class produces a edge object
 *Each edge has a source node and target node and weight.
 *@author George kouzy and Dolev Saadia. 
 *
 */
public class EdgeData implements edge_data,Serializable {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

	}
	int _src;
	int _dest;
	double _w;
	String _info;
	int _tag;

	EdgeData(int s,int d,double w){
		_src=s;
		_dest=d;
		_w=w;
	}
	/**
	 * The id of the source node of this edge.
	 * @return - src node.
	 */

	@Override
	public int getSrc() {
		return _src;
	}
	/**
	 * The id of the destination node of this edge
	 * @return - dest node.
	 */
	@Override
	public int getDest() {
		return _dest;
	}
	/**
	 * @return the weight of this edge - need to be positive value.
	 */
	@Override
	public double getWeight() {
		return _w;
	}
	/**
	 * This method allows setting/update the the weight.
	 * The new weight need to be positive value. 
	 * @param w - the new weight.
	 * @return
	 */
	public void setWeight(double w) {
		 _w=w;
	}
	/**
	 * Returns the remark (meta data) associated with this edge.
	 * @return
	 */
	@Override
	public String getInfo() {
		return _info;
	}
	/**
	 * Allows changing the remark (meta data) associated with this edge.
	 * @param s
	 */
	@Override
	public void setInfo(String s) {
		_info=s;
	}
	/**
	 * Temporal data (aka color: e,g, white, gray, black) 
	 * which can be used be algorithms 
	 * @return
	 */
	@Override
	public int getTag() {
		return _tag;
	}
	/** 
	 * This method allows setting the "tag" value for temporal marking an edge - common
	 * practice for marking by algorithms.
	 * @param t - the new value of the tag
	 */
	@Override
	public void setTag(int t) {
		_tag=t;
	}

	public String toString() {
		return "w= "+_w;
	}
	
	
}
