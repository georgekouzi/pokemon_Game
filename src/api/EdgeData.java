package api;

import java.io.Serializable;

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


	@Override
	public int getSrc() {
		return _src;
	}

	@Override
	public int getDest() {
		return _dest;
	}

	@Override
	public double getWeight() {
		return _w;
	}
	
	public double setWeight(double w) {
		return _w=w;
	}
	
	@Override
	public String getInfo() {
		return _info;
	}

	@Override
	public void setInfo(String s) {
		_info=s;
	}

	@Override
	public int getTag() {
		return _tag;
	}

	@Override
	public void setTag(int t) {
		_tag=t;
	}

	public String toString() {
		return "hi";
	}
	
	
}
