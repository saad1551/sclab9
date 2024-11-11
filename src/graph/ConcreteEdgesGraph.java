/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    //   AF(vertices, edges) = a graph where 'vertices' is the set of all vertices and 'edges' contains all edges between vertices with specific weights.
    // Representation invariant:
    //   - For every edge in 'edges', both edge.getSource() and edge.getTarget() are in 'vertices'.
    //   - No two edges in 'edges' have the same source and target.
    // Safety from rep exposure:
    //   - 'vertices' and 'edges' are private and final.
    //   - Methods return copies of collections to avoid exposing internal references.  
    
    // TODO constructor
    /**
     * Create a new empty graph.
     */
    public ConcreteEdgesGraph() {
        checkRep();
    }
    
    // TODO checkRep
    /**
     * Check the representation invariant.
     * @throws AssertionError if the representation invariant is violated
     */
    private void checkRep() {
        for (Edge edge : edges) {
            assert vertices.contains(edge.getSource()) : "Source vertex not in vertices";
            assert vertices.contains(edge.getTarget()) : "Target vertex not in vertices";
        }
        // Ensure non duplication of edges
        Set<String> edgeSet = new HashSet<>();
        for (Edge edge : edges) {
            String edgeString = edge.toString();
            assert !edgeSet.contains(edgeString) : "Duplicate edge";
            edgeSet.add(edgeString);
        }
    }
    
    @Override public boolean add(String vertex) {
        return vertices.add(vertex);
    }
    
    @Override public int set(String source, String target, int weight) {
        Edge newEdge = new Edge(source, target, weight);
        // Add vertices
        vertices.add(source);
        vertices.add(target);
        int previousWeight = 0;
        // Remove any previosly existing edges
        Iterator<Edge> iterator = edges.iterator();
        while (iterator.hasNext()) {
            Edge edge = iterator.next();
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                previousWeight = edge.getWeight();
                iterator.remove();
                break;
            }
        }
        if (weight != 0) {
            edges.add(newEdge);
        }
        checkRep();
        return previousWeight;
    }
    
    @Override public boolean remove(String vertex) {
        boolean removed = vertices.remove(vertex);
        if (removed) {
            // Remove all edges with the vertex
            List<Edge> edgesToRemove = new ArrayList<>();
            for (Edge edge : edges) {
                if (edge.getSource().equals(vertex) || edge.getTarget().equals(vertex)) {
                    edgesToRemove.add(edge);
                }
            }
            edges.removeAll(edgesToRemove);
        }
        checkRep();
        return removed;
    }
    
    @Override public Set<String> vertices() {
        // Return a copy of vertices set
        return new HashSet<>(vertices);
    }
    
    @Override public Map<String, Integer> sources(String target) {
        // Check all edges with the target
        Map<String, Integer> sources = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        return sources;
    }
    
    @Override public Map<String, Integer> targets(String source) {
        // Check all edges with the source
        Map<String, Integer> targets = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        return targets;
    }
    
    // TODO toString()
    @Override
    public String toString() {
        StringBuilder graphString = new StringBuilder();
        // Add vertices
        graphString.append("Vertices:\n");
        for (String vertex : vertices) {
            graphString.append(vertex);
            graphString.append("\n");
        }
        // Add edges
        graphString.append("Edges:\n");
        for (Edge edge : edges) {
            graphString.append(edge.toString());
            graphString.append("\n");
        }
        return graphString.toString();
    }   
}

/**
 * TODO specification
 * Immutable.
 * Weight should be postive i.e. > 0.
 * source and target must be a non null string.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge {
    
    // TODO fields
    private final String source;
    private final String target;
    private final int weight;

    // Abstraction function:
    //   AF(source, target, weight) = an edge from 'source' to 'target' with a weight 'weight' in the graph.
    // Representation invariant:
    //   - weight >= 0
    //   - source != null
    //   - target != null
    // Safety from rep exposure:
    //   - All fields are private and final.
    //   - No methods return mutable references to fields.
    
    // TODO constructor
    /**
     * Create a new edge.
     * @param source the source vertex
     * @param target the target vertex
     * @param weight the weight of the edge 
     */
    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }
    
    // TODO checkRep
    /**
     * Check the representation invariant.
     * @throws AssertionError if the representation invariant is violated
     */
    private void checkRep() {
        assert source != null : "Source cannot be null";
        assert target != null : "Target cannot be null";
        assert weight >= 0 : "Weight must be non-negative";
    }
    
    // TODO methods
    /**
     * Get the source vertex of the edge.
     * @return the source vertex
     */
    public String getSource() {
        return source;
    }
    
    /**
     * Get the target vertex of the edge.
     * @return the target vertex
     */
    public String getTarget() {
        return target;
    }
    
    /**
     * Get the weight of the edge.
     * @return the weight of the edge
     */
    public int getWeight() {
        return weight;
    }

    // TODO toString()
    @Override
    public String toString() {
        return source + " -> " + target + " : " + weight;
    }
}
