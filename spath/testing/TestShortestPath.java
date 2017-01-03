package spath.testing;

import static org.junit.Assert.*;


import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import spath.ShortestPaths;
import spath.graphs.DirectedGraph;
import spath.graphs.Edge;

public class TestShortestPath {
	private GraphGenerator gg;
	private LinkedList<Edge> expectedPath;
	private LinkedList<Edge> outputPath;

	@Rule
	public genGraphVizOnFail viz = new genGraphVizOnFail();

	@Test
	public void test() {
		int[] shortestPathLengths = { 3,  4,  5,  6,  8, 10,  50, 100,  300};
		int[] totalVertices       = { 5,  8, 10, 12, 16, 20,  75, 200, 1000};
		int[] totalEdges          = {10, 16, 20, 24, 32, 50, 300, 400, 5000};

		for(int i = 0; i < totalEdges.length; i++) {
			//System.out.println("i=  "+i);
			gg = new GraphGenerator(totalVertices[i], totalEdges[i], shortestPathLengths[i]);
			gg.genShortestPath();

			genGraphAndTest();

			while(gg.getNumVerts() < totalVertices[i]) {
				gg.addEdgeWithVertex();
				genGraphAndTest();
			}

			while(gg.getNumEdges() < totalEdges[i]) {
				gg.addEdge();
				genGraphAndTest();
			}
		}
	}

	private void genGraphAndTest() {
		DirectedGraph g = gg.getGraph();	
		ShortestPaths sp = new ShortestPaths(g, gg.start(), gg.weights());
		sp.run();
		//System.out.println("i am here");
		expectedPath = gg.getShortestPath();
		//System.out.println("after shortestpath");
		outputPath   = sp.returnPath(gg.end());
		//System.out.println("returnPath");
		
		
		int i = 0;
		for(Edge e : expectedPath) {
			//System.out.println("correct = "+e.toString()+"  your path edge is = "+outputPath.get(i).toString());
			assertEquals("Shortest path contained an incorrect edge.", e, outputPath.get(i++));
			//System.out.println("after path contained correct edge!!!");
		}
		//System.out.println("the correct length = "+gg.getShortestPathValue()+" your answer is = "+sp.returnValue(gg.end()));
		assertEquals("Length of the shortest path was incorrect.", gg.getShortestPathValue(), 
				sp.returnValue(gg.end()));
		//System.out.println("after path contained correct value!!!!!!!!!!!!!!");
	}

	private class genGraphVizOnFail extends TestWatcher {
		@Override
		protected void failed(Throwable e, Description description) {
			GraphViz<Integer> viz = new GraphViz<Integer>(gg.getGraph(), gg.weights(), gg.start(), gg.end(), expectedPath, outputPath);
			viz.print();
			JOptionPane.showMessageDialog(null, "Press OK when done");
		}
	}
}
