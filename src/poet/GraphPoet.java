package poet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import graph.Graph;
import graph.ConcreteVerticesGraph; // Choose one of the implementations

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */


public class GraphPoet {
    
    private final Graph<String> graph;
    private final Map<String, String> lowerCaseWords = new HashMap<>();

    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        this.graph = new ConcreteVerticesGraph(); // Use the appropriate Graph implementation
        constructGraph(corpus);
    }
    
    // Construct the graph from the corpus
    private void constructGraph(File corpus) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(corpus.getPath()));
        for (String line : lines) {
            String[] words = line.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                String word1 = words[i].toLowerCase();
                lowerCaseWords.put(word1, words[i]); // Store original case
                
                // Add words to graph
                graph.add(word1);
                
                if (i < words.length - 1) {
                    String word2 = words[i + 1].toLowerCase();
                    graph.add(word2);
                    graph.set(word1, word2, graph.targets(word1).getOrDefault(word2, 0) + 1);
                }
            }
        }
    }

    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String[] inputWords = input.split("\\s+");
        StringBuilder poemBuilder = new StringBuilder();

        for (int i = 0; i < inputWords.length; i++) {
            String currentWord = inputWords[i];

            // Retain original case
            poemBuilder.append(currentWord).append(" ");

            // Check for bridge word
            if (i < inputWords.length - 1) {
                String nextWord = inputWords[i + 1].toLowerCase();
                String bridgeWord = findBestBridge(currentWord.toLowerCase(), nextWord);
                if (bridgeWord != null) {
                    poemBuilder.append(bridgeWord).append(" ");
                }
            }
        }
        return poemBuilder.toString().trim();
    }

    // Find the best bridge word between two words
    private String findBestBridge(String w1, String w2) {
        String bestBridge = null;
        int maxWeight = 0;

        // Get neighbors of the first word
        Map<String, Integer> targets = graph.targets(w1);
        for (String intermediate : targets.keySet()) {
            int weight = targets.get(intermediate) + graph.sources(w2).getOrDefault(intermediate, 0);
            if (weight > maxWeight) {
                maxWeight = weight;
                bestBridge = intermediate;
            }
        }

        return bestBridge != null ? lowerCaseWords.get(bestBridge) : null;
    }

    @Override
    public String toString() {
        return "GraphPoet with graph: " + graph.toString();
    }
}
