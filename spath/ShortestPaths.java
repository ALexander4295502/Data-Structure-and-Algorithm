package spath;

import java.nio.file.NotDirectoryException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import heaps.Decreaser;
import heaps.MinHeap;
import heaps.util.HeapToStrings;
import spath.graphs.DirectedGraph;
import spath.graphs.Edge;
import spath.graphs.Vertex;
import timing.Ticker;
import spath.VertexAndDist;


// SHORTESTPATHS.JAVA
// Compute shortest paths in a graph.
//
// Your constructor should compute the actual shortest paths and
// maintain all the information needed to reconstruct them.  The
// returnPath() function should use this information to return the
// appropriate path of edge ID's from the start to the given end.
//
// Note that the start and end ID's should be mapped to vertices using
// the graph's get() function.
//
// You can ignore the input and startTime arguments to the constructor
// unless you are doing the extra credit.
//
public class ShortestPaths {
	private final static Integer inf = Integer.MAX_VALUE;
	private HashMap<Vertex, Decreaser<VertexAndDist>> map;
	private HashMap<Vertex, Edge> toEdge;
	private Map<Edge, Integer> weights;
	private Vertex startVertex;
	private final DirectedGraph g;
	
	
	//
	// constructor
	//
	public ShortestPaths(DirectedGraph g, Vertex startVertex, Map<Edge,Integer> weights) {

		this.map         = new HashMap<Vertex, Decreaser<VertexAndDist>>();
		this.toEdge      = new HashMap<Vertex, Edge>();
		this.weights     = weights;
		this.startVertex = startVertex;
		this.g           = g;
	}
	
	//
	// this method does all the real work
	//
	public void run() {
		Ticker ticker = new Ticker();
		MinHeap<VertexAndDist> pq = new MinHeap<VertexAndDist>(30000, ticker);

		//
		// Initially all vertices are placed in the heap
		//   infinitely far away from the start vertex
		//
		
		for (Vertex v : g.vertices()) {
			toEdge.put(v, null);
			VertexAndDist a = new VertexAndDist(v, inf);
			Decreaser<VertexAndDist> d = pq.insert(a);
			map.put(v, d);
		}


		//
		// Now we decrease the start node's distance from
		//   itself to 0.
		// Note how we look up the decreaser using the map...
		// 
		Decreaser<VertexAndDist> startVertDist = map.get(startVertex);
		//
		// and then decrease it using the Decreaser handle
		//
		startVertDist.decrease(startVertDist.getValue().sameVertexNewDistance(0));
		

		//
		// OK you take it from here
		// Extract nodes from the pq heap
		//   and act upon them as instructed in class and the text.
		//
		// FIXME
		
		
		
		while(!pq.isEmpty()){
			
			//System.out.println("pq is not empty!!!!!!");
			
			VertexAndDist currentVertDist = pq.extractMin();
			Vertex currentVert = currentVertDist.getVertex();
		//	System.out.println(HeapToStrings.toTree(pq));
			for(Edge edge : currentVert.edgesFrom()){
				
			//	System.out.println("Edge edge : currentVert.edgesTo()  "+edge.toString());
				
				Vertex adjacentVert = edge.to;
				Decreaser<VertexAndDist> adjacVertDist = map.get(adjacentVert);
				
				/*if the distance of the decreaser in map is not inf, which means
				  the adjacent vertex already has shortest distance from source vertex*/
				//System.out.println("pq size = "+pq.size());
				if(pq.size()==0){
					continue;
				}
				if(pq.peek(adjacVertDist.loc).getVertex() != adjacentVert){
					//System.out.println("the loc = "+adjacVertDist.loc);
					//System.out.println("in pq vertex= "+pq.peek(adjacVertDist.loc).getVertex()+"||||  but adjacentVert = "+adjacentVert);
					//System.out.println("what????????");
					continue;
				}
				
				int newDistance = currentVertDist.getDistance()+weights.get(edge);
				if(adjacVertDist.getValue().getDistance()>newDistance){
					adjacVertDist.decrease(adjacVertDist.getValue().sameVertexNewDistance(newDistance));
					toEdge.put(adjacentVert, edge);
					//System.out.println(edge.toString());
				}

			}
			
			
		}
		
	}

	
	/**
	 * Return a List of Edges forming a shortest path from the
	 *    startVertex to the specified endVertex.  Do this by tracing
	 *    backwards from the endVertex, using the map you maintain
	 *    during the shortest path algorithm that indicates which
	 *    Edge is used to reach a Vertex on a shortest path from the
	 *    startVertex.
	 * @param endVertex 
	 * @return
	 */
	public LinkedList<Edge> returnPath(Vertex endVertex) {
		LinkedList<Edge> path = new LinkedList<Edge>();
		//
		// FIXME
		//
		
		Vertex stepVert = endVertex;
		//check if a path exists
		
		if(toEdge.get(stepVert) == null){
			System.out.println("path not exists!!!!");
			return null;
		}
		//path.addFirst(null);
		int i=0;

		while(toEdge.get(stepVert) != null)
		{   i++;
			Edge stepEdge = toEdge.get(stepVert);
			//System.out.println("loop??");
			path.addFirst(stepEdge);
			stepVert = stepEdge.from;
			//System.out.println("loop??");
		}
//		for(int j=0;j<path.size();j++)
//       {
//			System.out.println("path["+j+"] = "+path.get(j).toString());
//       }
		return path;
	}
	
	/**
	 * Return the length of all shortest paths.  This method
	 *    is completed for you, using your solution to returnPath.
	 * @param endVertex
	 * @return
	 */
	public int returnValue(Vertex endVertex) {
		LinkedList<Edge> path = returnPath(endVertex);
		int pathValue = 0;
		for(Edge e : path) {
			pathValue += weights.get(e);
		}
		
		return pathValue;
		
	}
}
