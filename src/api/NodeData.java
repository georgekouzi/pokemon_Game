package api;

import java.io.Serializable;

import gameClient.util.Point3D;

public class NodeData implements node_data,Serializable {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

	}
	private int _key;
	private static int _k=0;
	private Point3D _Location ;
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

	@Override
	public int getKey() {
		return _key;
	}

	@Override
	public geo_location getLocation() {
		return _Location;
	}

	@Override
	public void setLocation(geo_location p) {
		_Location=(Point3D) p;
	}

	@Override
	public double getWeight() {
		return _Weight ;
	}

	@Override
	public void setWeight(double w) {
		_Weight=w;
	}


	@Override
	public String getInfo() {
		return Info;
	}


	@Override
	public void setInfo(String s) {
		Info=s;		
	}


	@Override
	public int getTag() {
		return Tag;
	}

	@Override
	public void setTag(int t) {
		Tag=t;		
	}

	public String toString() {
		return "Node = "+ _key;
	}

}
