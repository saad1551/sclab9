package poet;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

// Testing Strategy

/** 
 * Corpus with multiple words
 * Test initialization of GraphPoet
 * Test initialization of GraphPoet with an invalid file
 * Test case with simple input
 * Test case with no bridge word
 * Test for case insensitivity
 */

public class GraphPoetTest {
    
    private static final String CORPUS_PATH = "test/poet/test_corpus.txt";

    // Ensure assertions are enabled
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // Make sure assertions are enabled with VM argument: -ea
    }

    // Test initialization with a valid corpus
    @Test
    public void testGraphPoetInitialization() throws IOException {
        File corpusFile = new File(CORPUS_PATH);
        GraphPoet poet = new GraphPoet(corpusFile);
        assertNotNull(poet);
    }

    // Test initialization with an invalid corpus
    @Test(expected = IOException.class)
    public void testGraphPoetInitializationInvalidFile() throws IOException {
        File invalidFile = new File("invalid_path.txt");
        new GraphPoet(invalidFile);
    }

    // Test the poem generation with simple corpus
    @Test
    public void testPoemGeneration() throws IOException {
        File corpusFile = new File(CORPUS_PATH);
        GraphPoet poet = new GraphPoet(corpusFile);
        
        String input = "Hello world";
        String expectedOutput = "Hello beautiful world"; // Example expected output
        String output = poet.poem(input);
        
        assertEquals(expectedOutput, output);
    }

    // Test with a single word corpus, output should have no change
    @Test public void testPoetSingleWordCorpus() throws IOException {
        File corpusFile = new File("test/poet/single_word_corpus.txt");
        GraphPoet poet = new GraphPoet(corpusFile);

        String input = "This is testing input";
        String expectedOutput = "This is testing input"; // No bridge word added
        String output = poet.poem(input);

        assertEquals(expectedOutput, output);
    }

    // Test case where no bridge word exists
    @Test
    public void testPoemGenerationNoBridge() throws IOException {
        File corpusFile = new File(CORPUS_PATH);
        GraphPoet poet = new GraphPoet(corpusFile);
        
        String input = "No bridge here";
        String expectedOutput = "No bridge here"; // Expecting same output as input
        String output = poet.poem(input);
        
        assertEquals(expectedOutput, output);
    }

    // Test for case insensitivity in word adjacency
    @Test
    public void testCaseInsensitivity() throws IOException {
        File corpusFile = new File(CORPUS_PATH);
        GraphPoet poet = new GraphPoet(corpusFile);
        
        String input = "hello WELCOME";
        String expectedOutput = "hello and WELCOME"; // Example output with bridge
        String output = poet.poem(input);
        
        assertEquals(expectedOutput, output);
    }

    // More tests can be added as necessary...
}
