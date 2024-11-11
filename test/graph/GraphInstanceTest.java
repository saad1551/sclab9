/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   Vertex does not exist, vertex exists
    //   Edge does not exist, edge exists
    //   Number of vertices == 0, number of vertices > 0
    //   Number of edges == 0, number of edges > 0, number of edges == 1
    //   Number of sources == 0, number of sources > 0
    //   Number of targets == 0, number of targets > 0
    //   Weight == 0, weight > 0
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    // Tests the add method
    // Covers vertices == 0, vertices > 0
    // Vertex already exists
    @Test
    public void testAdd() {
        Graph<String> graph = emptyInstance();
        // Add a vertex to the graph
        assertTrue(graph.add("a"));
        assertTrue(graph.vertices().contains("a"));
        // Try adding the same vertex again
        assertFalse(graph.add("a"));
        // Add another vertex to the graph
        assertTrue(graph.add("b"));
        assertTrue(graph.vertices().contains("b"));
    }

    // Tests the set method
    // Covers weight == 0, weight > 0
    // Edge already exists
    // Edge does not exist
    // Number of edges == 1, number of edges > 1
    @Test 
    public void testSet() {
        Graph<String> graph = emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");
        // Add an edge to the graph
        assertEquals(0, graph.set("a", "b", 1));
        assertTrue(graph.sources("b").containsKey("a") && graph.sources("b").get("a") == 1);
        // Try modifying the edge
        assertEquals(1, graph.set("a", "b", 2));
        assertTrue(graph.sources("b").containsKey("a") && graph.sources("b").get("a") == 2);
        // Try adding another edge
        assertEquals(0, graph.set("a", "c", 3));
        assertTrue(graph.sources("c").containsKey("a") && graph.sources("c").get("a") == 3);
        // Try removing the edge
        assertEquals(2, graph.set("a", "b", 0));
        assertFalse(graph.sources("b").containsKey("a"));
    }

    // Tests the remove method
    // Covers vertex does not exist, vertex exists
    // Number of vertices == 1, number of vertices > 1
    // Number of edges == 0, number of edges > 1, number of edges == 1
    @Test
    public void testRemove() {
        Graph<String> graph = emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");
        graph.add("d");
        graph.set("a", "b", 1);
        graph.set("a", "c", 2);
        graph.set("b", "c", 3);
        // Remove a vertex from the graph, edges should also be removed
        assertTrue(graph.remove("a"));
        assertFalse(graph.vertices().contains("a"));
        assertFalse(graph.sources("b").containsKey("a"));
        assertFalse(graph.sources("c").containsKey("a"));
        // Try removing the same vertex again
        assertFalse(graph.remove("a"));
        // Remove another vertex from the graph
        assertTrue(graph.remove("b"));
        assertFalse(graph.vertices().contains("b"));
        assertFalse(graph.sources("c").containsKey("b"));
        // Remove a vertex that has no edge
        assertTrue(graph.remove("d"));
        assertFalse(graph.vertices().contains("d"));
    }

    // Tests the vertices method
    // Covers vertices.size() == 0, vertices.size() == 1, vertices.size() > 1
    @Test
    public void testVertices() {
        Graph<String> graph = emptyInstance();
        // Check the vertices of the graph
        assertEquals(Collections.emptySet(), graph.vertices());
        // Check single vertex
        graph.add("a");
        assertEquals(Collections.singleton("a"), graph.vertices());
        // Check multiple vertices
        graph.add("b");
        assertEquals(Set.of("a", "b"), graph.vertices());
    }

    // Tests the sources method
    // Covers target does not exist, target exists
    // Number of sources == 0, number of sources > 0
    @Test
    public void testSources() {
        Graph<String> graph = emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");
        graph.set("a", "b", 1);
        graph.set("a", "c", 2);
        graph.set("b", "c", 3);
        // Vertex does not exist
        assertEquals(Collections.emptyMap(), graph.sources("d"));
        // Vertex exists, but no source
        assertEquals(Collections.emptyMap(), graph.sources("a"));
        // Vertex exists, with single source
        assertEquals(Collections.singletonMap("a", 1), graph.sources("b"));
        // Vertex exists, with multiple sources
        assertEquals(Map.of("a", 2, "b", 3), graph.sources("c"));
    }

    // Tests the targets method
    // Covers source does not exist, source exists
    // Number of targets == 0, number of targets > 0
    @Test
    public void testTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");
        graph.set("a", "b", 1);
        graph.set("a", "c", 2);
        graph.set("b", "c", 3);
        // Vertex does not exist
        assertEquals(Collections.emptyMap(), graph.targets("d"));
        // Vertex exists, but no target
        assertEquals(Collections.emptyMap(), graph.targets("c"));
        // Vertex exists, with single target
        assertEquals(Collections.singletonMap("c", 3), graph.targets("b"));
        // Vertex exists, with multiple targets
        assertEquals(Map.of("b", 1, "c", 2), graph.targets("a"));
    }
}
