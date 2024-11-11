/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   Represents a graph where each Vertex object in 'vertices' contains a vertex and its outgoing edges.
    // Representation invariant:
    //   - No two vertices in 'vertices' have the same source. i.e. vertices are not repeated.
    // Safety from rep exposure:
    //   - vertices is private and final.
    //   - Only copies of vertex labels and edge mappings are exposed.
    
    // TODO constructor
    /**
     * Create a new empty graph.
     */
    public ConcreteVerticesGraph() {
        checkRep();
    }
    
    // TODO checkRep
    /**
     * Check the representation invariant.
     * @throws AssertionError if the representation invariant is violated
     */
    private void checkRep() {
        // Ensure non duplication of vertices
        Set<String> vertexSet = new HashSet<>();
        for (Vertex vertex : vertices) {
            String vertexString = vertex.getSource();
            assert !vertexSet.contains(vertexString) : "Duplicate vertex";
            vertexSet.add(vertexString);
        }
    }
    
    @Override public boolean add(String vertex) {
        // Check if vertex already exists
        for (Vertex v : vertices) {
            if (v.getSource().equals(vertex)) {
                return false;
            }
        }
        // Else add it
        vertices.add(new Vertex(vertex));
        checkRep();
        return true;
    }
    
    @Override public int set(String source, String target, int weight) {
        // Check if source and target vertices exist
        Vertex sourceVertex = null;
        Vertex targetVertex = null;
        for (Vertex v : vertices) {
            if (v.getSource().equals(source)) {
                sourceVertex = v;
            }
            if (v.getSource().equals(target)) {
                targetVertex = v;
            }
        }
        // If they don't exist, create them
        if (sourceVertex == null) {
            sourceVertex = new Vertex(source);
            vertices.add(sourceVertex);
        }
        if (targetVertex == null) {
            targetVertex = new Vertex(target);
            vertices.add(targetVertex);
        }
        // Check if the outEdge already exists
        int previousWeight = 0;
        Map<String, Integer> outEdges = sourceVertex.getOutEdges();
        if (outEdges.containsKey(target)) {
            previousWeight = outEdges.get(target);
        }
        if (weight == 0) {
            sourceVertex.removeOutEdge(target);
        } else {
            sourceVertex.addOutEdge(target, weight);
        }
        checkRep();
        return previousWeight;
    }
    
    @Override public boolean remove(String vertex) {
        // Check if vertex exists
        Vertex vertexToRemove = null;
        for (Vertex v : vertices) {
            if (v.getSource().equals(vertex)) {
                vertexToRemove = v;
            }
        }
        if (vertexToRemove == null) {
            return false;
        }
        // Remove the vertex
        vertices.remove(vertexToRemove);
        // Remove all edges with the vertex
        for (Vertex v : vertices) {
            v.removeOutEdge(vertex);
        }
        checkRep();
        return true;
    }
    
    @Override public Set<String> vertices() {
        Set<String> vertexSet = new HashSet<>();
        for (Vertex v : vertices) {
            vertexSet.add(v.getSource());
        }
        return vertexSet;
    }
    
    @Override public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        // Check all vertices for edges with the target
        for (Vertex v : vertices) {
            Map<String, Integer> outEdges = v.getOutEdges();
            if (outEdges.containsKey(target)) {
                sources.put(v.getSource(), outEdges.get(target));
            }
        }
        return sources;
    }
    
    @Override public Map<String, Integer> targets(String source) {  
        // Check if source vertex exists
        Vertex sourceVertex = null;
        for (Vertex v : vertices) {
            if (v.getSource().equals(source)) {
                sourceVertex = v;
            }
        }
        // Return empty hash map if source does not exist
        if (sourceVertex == null) {
            return new HashMap<>();
        }
        return sourceVertex.getOutEdges();
    }
    
    // TODO toString()
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex v : vertices) {
            sb.append(v.toString());
        }
        return sb.toString();
    }
}

/**
 * TODO specification
 * Mutable.
 * Source should not be null.
 * Out edges should not contain null keys or values.
 * Weights should be > 0.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex {
    
    // TODO fields
    private final String source;
    // Create a map containing pairs, the first element is the target and the second element is the weight
    private final Map<String, Integer> outEdges;
    
    // Abstraction function:
    //   Represents a vertex in a graph, where 'source' is the vertex label, and 'outEdges'
    //   is a map of edges with target vertices and their corresponding weights.
    // Representation invariant:
    //   - source is non-null.
    //   - outEdges does not contain null keys or values, and all weights are > 0.
    // Safety from rep exposure:
    //   - Fields are private and final where applicable.
    //   - outEdges is exposed only as a copy to prevent external modification.
    
    // TODO constructor
    Vertex(String source) {
        this.source = source;
        this.outEdges = new HashMap<>();
        checkRep();
    }
    
    // TODO checkRep
    private void checkRep() {
        assert source != null;
        assert outEdges != null;
        for (String target : outEdges.keySet()) {
            assert target != null;
            assert outEdges.get(target) > 0;
        }
    }
    
    // TODO methods
    /**
     * Get the source vertex.
     * @return the source vertex
     */
    public String getSource() {
        return source;
    }

    /**
     * Get a copy of the out edges.
     * @return a copy of the out edges
     */
    public Map<String, Integer> getOutEdges() {
        return new HashMap<>(outEdges);
    }

    /**
     * Add an out edge to the vertex.
     * @param target the target vertex
     * @param weight the weight of the edge
     */
    public void addOutEdge(String target, int weight) {
        outEdges.put(target, weight);
        checkRep();
    }

    /**
     * Remove an out edge from the vertex.
     * @param target the target vertex
     */
    public void removeOutEdge(String target) {
        outEdges.remove(target);
        checkRep();
    }
    
    // TODO toString()
    @Override
    public String toString() {
        if (outEdges.isEmpty()) {
            return source + " -> \n";
        }
        StringBuilder sb = new StringBuilder();
        for (String target : outEdges.keySet()) {
            sb.append(source).append(" -> ").append(target).append(" : ").append(outEdges.get(target)).append("\n");
        }
        return sb.toString();
    }
}
