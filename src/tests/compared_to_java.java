package tests;

import java.util.Date;

import algorithms.DWGraph_AlgoGW;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import dataStructure.DWGraph_DS;

public class compared_to_java {

	public static void run_time(String file,int src,int dest) {
		
		long start;
		long end;
		double dt = 0;
		dw_graph_algorithms algo = new DWGraph_AlgoGW();

		for (int i =0; i<100;i++) {
		start = new Date().getTime();
		algo.load(file);
        end = new Date().getTime();
        dt+=(end-start)*0.001;
		}
        System.out.println(algo.getGraph().nodeSize()+" , "+algo.getGraph().edgeSize());
        System.out.println("run time of read from json file and build the graph: "+ dt/100);
        dt=0;

        for (int i =0; i<100;i++) {

		start = new Date().getTime();
		algo.connected_components();
        end = new Date().getTime();
        dt+=(end-start)*0.001;
		}
        System.out.println("run time of connected_components: "+ dt/100);
        dt=0;

		for (int i =0; i<100;i++) {

        start = new Date().getTime();
		algo.connected_component(1);
        end = new Date().getTime();
        dt+=(end-start)*0.001;
		}
        System.out.println("run time of connected_component: "+ dt/100);
        dt=0;
		for (int i =0; i<100;i++) {
        start = new Date().getTime();
		algo.shortestPath(src, dest);
        end = new Date().getTime();
        dt+=(end-start)*0.001;
		}
        System.out.println("run time of shortes tPath: "+ dt/100);
        
        System.out.println();
		
	}

	
	public static void main(String[] args) {
		run_time("data/G_10_80_1.json",1,9);
		run_time("data/G_100_800_1.json",1,99);
		run_time("data/G_1000_8000_1.json",1,999);
		run_time("data/G_10000_80000_1.json",1,9999);
		run_time("data/G_20000_160000_1.json",1,19999);
		run_time("data/G_30000_240000_1.json",1,29999);

		
		
        
        
        
        
        
        
        
        

	}

	
	
	
	
	
	
}
