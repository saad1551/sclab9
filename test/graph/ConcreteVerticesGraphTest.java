/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   Empty graph
    //   Graph with one vertex, no edge
    //   Graph with two vertex, no edge
    //   Graph with two vertices, one edge
    //   Graph with three vertices, two edges
    
    // TODO tests for ConcreteVerticesGraph.toString()
    @Test
    public void testConcreteVerticesGraphToString() {
        Graph<String> graph = emptyInstance();
        assertEquals("", graph.toString());

        graph.add("a");
        assertEquals("a -> \n", graph.toString());

        graph.add("b");
        assertEquals("a -> \nb -> \n", graph.toString());

        graph.set("a", "b", 1);
        assertEquals("a -> b : 1\nb -> \n", graph.toString());

        graph.add("c");
        graph.set("b", "c", 2);
        assertEquals("a -> b : 1\nb -> c : 2\nc -> \n", graph.toString());
    }


    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   Vertex with no edges
    //   Vertex with one edge
    //   Vertex with multiple edges
    @Test
    public void testVertex() {
        Vertex vertex = new Vertex("a");
        assertEquals("a", vertex.getSource());

        vertex.addOutEdge("b", 1);
        assertTrue(vertex.getOutEdges().containsKey("b"));
        assertEquals(1, vertex.getOutEdges().get("b").intValue());

        vertex.addOutEdge("c", 2);
        assertEquals("a -> b : 1\na -> c : 2\n", vertex.toString());
        assertTrue(vertex.getOutEdges().containsKey("c"));
        assertEquals(2, vertex.getOutEdges().get("c").intValue());
    }

    // TODO tests for operations of Vertex
    @Test
    public void testVertexAddOutEdge() {
        Vertex vertex = new Vertex("a");
        vertex.addOutEdge("b", 1);
        assertTrue(vertex.getOutEdges().containsKey("b"));
        assertEquals(1, vertex.getOutEdges().get("b").intValue());

        vertex.addOutEdge("c", 2);
        assertTrue(vertex.getOutEdges().containsKey("c"));
        assertEquals(2, vertex.getOutEdges().get("c").intValue());
    }

    @Test
    public void testVertexRemoveOutEdge() {
        Vertex vertex = new Vertex("a");
        vertex.addOutEdge("b", 1);
        vertex.addOutEdge("c", 2);

        vertex.removeOutEdge("b");
        assertFalse(vertex.getOutEdges().containsKey("b"));
        assertTrue(vertex.getOutEdges().containsKey("c"));
        assertEquals(2, vertex.getOutEdges().get("c").intValue());

        vertex.removeOutEdge("c");
        assertFalse(vertex.getOutEdges().containsKey("c"));
    }

    @Test
    public void testVertexToString() {
        Vertex vertex = new Vertex("a");
        assertEquals("a -> \n", vertex.toString());

        vertex.addOutEdge("b", 1);
        assertEquals("a -> b : 1\n", vertex.toString());

        vertex.addOutEdge("c", 2);
        assertEquals("a -> b : 1\na -> c : 2\n", vertex.toString());
    }
}
