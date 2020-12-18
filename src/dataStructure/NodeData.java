package dataStructure;

import java.io.Serializable;
import java.util.Objects;

import api.geo_location;
import api.node_data;

/**
 * This  class allows you to create nodes with unique key and do various actions.
 * weight- holds the cheapest weight.
 * and info - indicates if we visited this node.
 *@author George kouzy and Dolev Saadia. 
 */

public class NodeData implements node_data,Serializable {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

	}
	private int _key;
	private static int _k=0;
	private geo_location _Location ;
	private double _Weight;
	private String Info; 
	private int Tag;

	public NodeData() {
		_key=_k++;
		_Location=null;
		_Weight=0;
		Tag=0;
	}
	public NodeData(int k) {
		_key=k;
		_Location=null;
		_Weight=0;
		Tag=0;
	}
	/**
	 * Returns the key (id) associated with this node.
	 * @return
	 */
	@Override
	public int getKey() {
		return _key;
	}
	/** Returns the location of this node, if
	 * if no Location we return null.
	 * @return _Location- Point3D x,y,z
	 */
	@Override
	public geo_location getLocation() {
		if(_Location!=null)
			return _Location;
		else
			return null;
	}
	/** Allows changing this node's location.
	 * @param p - new new location  (position) of this node.
	 */
	@Override
	public void setLocation(geo_location p) {
		_Location= p;
	}
	/**
	 * Returns the weight associated with this node.
	 * @return
	 */
	@Override
	public double getWeight() {
		return _Weight ;
	}
	/**
	 * Allows changing this node's weight.
	 * @param w - the new weight
	 */
	@Override
	public void setWeight(double w) {
		_Weight=w;
	}

	/**
	 * Returns the remark (meta data) associated with this node.
	 * use to know if visit in this node or not.
	 * @return
	 */
	@Override
	public String getInfo() {
		return Info;
	}

	/**
	 * Allows changing the remark (meta data) associated with this node.
	 * use to know if visit in this node or not -set from true to false or from false to true.
	 * @param s
	 */
	@Override
	public void setInfo(String s) {
		Info=s;		
	}

	/**
	 * Temporal data (aka color: e,g, white, gray, black) 
	 * which can be used be algorithms 
	 * @return
	 */
	@Override
	public int getTag() {
		return Tag;
	}
	/** 
	 * Allows setting the "tag" value for temporal marking an node - common
	 * practice for marking by algorithms.
	 * @param t - the new value of the tag
	 */
	@Override
	public void setTag(int t) {
		Tag=t;		
	}
	/**
	 * toString function.
	 */
	public String toString() {
		return "Node = "+ _key;
	}

	/**
	 * hash code by key node
	 * @return - obj hash by key
	 */

	public int hashCode() {
		return Objects.hash(_key);
	}

	/**
	 * this function equal between two node if they have same key return true else false.
	 * @return true or false. 
	 */

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof NodeData)) { 
			return false;
		}
		NodeData otherObject = (NodeData) object;
		if (_key != otherObject._key) {
			return false;
		}
		if(_Location.x()!=otherObject._Location.x()&&_Location.y()!=otherObject._Location.y()&&_Location.z()!=otherObject._Location.z()) {
			return false;
		}
		else
			return true;
	}
}
