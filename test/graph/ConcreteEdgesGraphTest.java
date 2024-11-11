/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   Empty graph
    //   Graph with one vertex, no edge
    //   Graph with two vertex, no edge
    //   Graph with two vertices, one edge
    //   Graph with three vertices, two edges
    
    // TODO tests for ConcreteEdgesGraph.toString()
    @Test 
    public void testConcreteEdgesGraphToString() {
        Graph<String> graph = emptyInstance();
        assertEquals("Vertices:\nEdges:\n", graph.toString());

        graph.add("a");
        assertEquals("Vertices:\na\nEdges:\n", graph.toString());

        graph.add("b");
        assertEquals("Vertices:\na\nb\nEdges:\n", graph.toString());

        graph.set("a", "b", 1);
        assertEquals("Vertices:\na\nb\nEdges:\na -> b : 1\n", graph.toString());

        graph.add("c");
        graph.set("b", "c", 2);
        assertEquals("Vertices:\na\nb\nc\nEdges:\na -> b : 1\nb -> c : 2\n", graph.toString());
    }


    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   source.length() : 0, 1, > 1
    //   target.length() : 0, 1, > 1
    //   weight : 0, 1, > 1

    // Edge created successfully
    // Also tests the getSource, getTarget and getWeight methods
    @Test 
    public void testEdge() {
        Edge edge = new Edge("a", "b", 1);
        assertEquals("a", edge.getSource());
        assertEquals("b", edge.getTarget());
        assertEquals(1, edge.getWeight());

        edge = new Edge("ab", "", 0);
        assertEquals("ab", edge.getSource());
        assertEquals("", edge.getTarget());
        assertEquals(0, edge.getWeight());

        edge = new Edge("a", "b", 100);
        assertEquals("a", edge.getSource());
        assertEquals("b", edge.getTarget());
        assertEquals(100, edge.getWeight());
    }

    
    // TODO tests for operations of Edge
    @Test
    public void testEdgeToString() {
        Edge edge = new Edge("a", "b", 1);
        assertEquals("a -> b : 1", edge.toString());
        
        edge = new Edge("ab", "", 0);
        assertEquals("ab ->  : 0", edge.toString());
        
        edge = new Edge("a", "b", 100);
        assertEquals("a -> b : 100", edge.toString());
    }
}
