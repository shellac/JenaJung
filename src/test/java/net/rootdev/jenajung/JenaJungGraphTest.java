/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.rootdev.jenajung;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.FileManager;
import edu.uci.ics.jung.graph.util.Pair;
import java.net.URL;
import java.util.Collection;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pldms
 */
public class JenaJungGraphTest {
    private static JenaJungGraph graph;

    public JenaJungGraphTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        URL t = JenaJungGraphTest.class.getResource("/model.ttl");
        Model model = FileManager.get().loadModel(t.toExternalForm());
        graph = new JenaJungGraph(model);
    }

    private static Property getN(String s) {
        return ResourceFactory.createProperty("http://example.com/ns#", s);
    }

    private static Statement getS(String s, String p, String o) {
        return ResourceFactory.createStatement(
                getN(s),
                getN(p),
                getN(o));
    }

    /**
     * Test of getInEdges method, of class JenaJungGraph.
     */
    @Test
    public void testGetInEdges() {

    }

    /**
     * Test of getOutEdges method, of class JenaJungGraph.
     */
    @Test
    public void testGetOutEdges() {
        Collection<Statement> a = graph.getOutEdges(getN("a"));
        assertEquals(3, a.size());
    }

    /**
     * Test of getPredecessors method, of class JenaJungGraph.
     */
    @Test
    public void testGetPredecessors() {
        Collection<RDFNode> a = graph.getPredecessors(getN("a"));
        assertTrue(a.isEmpty());
        Collection<RDFNode> c = graph.getPredecessors(getN("c"));
        assertEquals(1, c.size());
    }

    /**
     * Test of getSuccessors method, of class JenaJungGraph.
     */
    @Test
    public void testGetSuccessors() {
        Collection<RDFNode> a = graph.getSuccessors(getN("a"));
        assertEquals(3, a.size());
        Collection<RDFNode> c = graph.getSuccessors(getN("c"));
        assertEquals(0, c.size());
    }

    /**
     * Test of inDegree method, of class JenaJungGraph.
     */
    @Test
    public void testInDegree() {
        assertEquals(0, graph.inDegree(getN("a")));
        assertEquals(1, graph.inDegree(getN("c")));
    }

    /**
     * Test of outDegree method, of class JenaJungGraph.
     */
    @Test
    public void testOutDegree() {
        assertEquals(3, graph.outDegree(getN("a")));
        assertEquals(0, graph.outDegree(getN("c")));
    }

    /**
     * Test of isPredecessor method, of class JenaJungGraph.
     */
    @Test
    public void testIsPredecessor() {
        assertTrue(graph.isPredecessor(getN("a"), getN("c")));
        assertFalse(graph.isPredecessor(getN("c"), getN("a")));
    }

    /**
     * Test of isSuccessor method, of class JenaJungGraph.
     */
    @Test
    public void testIsSuccessor() {
        assertTrue(graph.isSuccessor(getN("c"), getN("a")));
        assertFalse(graph.isSuccessor(getN("a"), getN("c")));
    }

    /**
     * Test of getPredecessorCount method, of class JenaJungGraph.
     */
    @Test
    public void testGetPredecessorCount() {
        assertEquals(0, graph.getPredecessorCount(getN("a")));
        assertEquals(1, graph.getPredecessorCount(getN("c")));
    }

    /**
     * Test of getSuccessorCount method, of class JenaJungGraph.
     */
    @Test
    public void testGetSuccessorCount() {
        assertEquals(3, graph.getSuccessorCount(getN("a")));
        assertEquals(0, graph.getSuccessorCount(getN("c")));
    }

    /**
     * Test of getSource method, of class JenaJungGraph.
     */
    @Test
    public void testGetSource() {
        assertEquals(getN("a"), graph.getSource(getS("a","b","c")));
    }

    /**
     * Test of getDest method, of class JenaJungGraph.
     */
    @Test
    public void testGetDest() {
        assertEquals(getN("c"), graph.getDest(getS("a","b","c")));
    }

    /**
     * Test of isSource method, of class JenaJungGraph.
     */
    @Test
    public void testIsSource() {
        assertTrue(graph.isSource(getN("a"), getS("a","b","c")));
        assertFalse(graph.isSource(getN("c"), getS("a","b","c")));
    }

    /**
     * Test of isDest method, of class JenaJungGraph.
     */
    @Test
    public void testIsDest() {
        assertTrue(graph.isDest(getN("c"), getS("a","b","c")));
        assertFalse(graph.isDest(getN("a"), getS("a","b","c")));
    }

    /**
     * Test of getEndpoints method, of class JenaJungGraph.
     */
    @Test
    public void testGetEndpoints() {
        assertEquals(new Pair(getN("a"), getN("c")),
                graph.getEndpoints(getS("a","b","c")));
    }

    /**
     * Test of getOpposite method, of class JenaJungGraph.
     */
    @Test
    public void testGetOpposite() {
        assertEquals(getN("a"),
                graph.getOpposite(getN("c"), getS("a","b","c")));
        assertEquals(getN("c"),
                graph.getOpposite(getN("a"), getS("a","b","c")));
    }

    /**
     * Test of getEdges method, of class JenaJungGraph.
     */
    @Test
    public void testGetEdges_0args() {
        Collection<Statement> e = graph.getEdges();
        assertEquals(3, e.size());
        assertTrue(e.contains(getS("a","b","c")));
        assertTrue(e.contains(getS("a","b","d")));
        assertTrue(e.contains(getS("a","b","e")));
    }

    /**
     * Test of getVertices method, of class JenaJungGraph.
     */
    @Test
    public void testGetVertices() {
        Collection<RDFNode> v = graph.getVertices();
        assertEquals(4, v.size());
        assertTrue(v.contains(getN("a")));
        assertTrue(v.contains(getN("c")));
        assertTrue(v.contains(getN("d")));
        assertTrue(v.contains(getN("e")));
    }

    /**
     * Test of containsVertex method, of class JenaJungGraph.
     */
    @Test
    public void testContainsVertex() {
        assertTrue(graph.containsVertex(getN("d")));
        assertFalse(graph.containsVertex(getN("xxx")));
    }

    /**
     * Test of containsEdge method, of class JenaJungGraph.
     */
    @Test
    public void testContainsEdge() {
        assertTrue(graph.containsEdge(getS("a","b","d")));
        assertFalse(graph.containsEdge(getS("a","b","xxx")));
    }

    /**
     * Test of getEdgeCount method, of class JenaJungGraph.
     */
    @Test
    public void testGetEdgeCount_0args() {
        assertEquals(3, graph.getEdgeCount());
    }

    /**
     * Test of getVertexCount method, of class JenaJungGraph.
     */
    @Test
    public void testGetVertexCount() {
        assertEquals(4, graph.getVertexCount());
    }

    /**
     * Test of getNeighbors method, of class JenaJungGraph.
     */
    @Test
    public void testGetNeighbors() {
        Collection<RDFNode> n = graph.getNeighbors(getN("a"));
        assertEquals(3, n.size());
        assertTrue(n.contains(getN("c")));
        assertTrue(n.contains(getN("d")));
        assertTrue(n.contains(getN("e")));

        n = graph.getNeighbors(getN("c"));
        assertEquals(1, n.size());
        assertTrue(n.contains(getN("a")));
    }

    /**
     * Test of getIncidentEdges method, of class JenaJungGraph.
     */
    @Test
    public void testGetIncidentEdges() {
        Collection<Statement> e = graph.getIncidentEdges(getN("a"));
        assertEquals(3, e.size());
        assertTrue(e.contains(getS("a","b","c")));
        assertTrue(e.contains(getS("a","b","d")));
        assertTrue(e.contains(getS("a","b","e")));

        e = graph.getIncidentEdges(getN("d"));
        assertEquals(1, e.size());
        assertTrue(e.contains(getS("a","b","d")));
    }

    /**
     * Test of getIncidentVertices method, of class JenaJungGraph.
     */
    @Test
    public void testGetIncidentVertices() {
        Collection<RDFNode> v = graph.getIncidentVertices(getS("a","b","e"));
        assertEquals(2, v.size());
        assertTrue(v.contains(getN("a")));
        assertTrue(v.contains(getN("e")));
    }

    /**
     * Test of findEdge method, of class JenaJungGraph.
     */
    @Test
    public void testFindEdge() {
        Statement s = graph.findEdge(getN("a"), getN("d"));
        assertEquals(getS("a","b","d"), s);

        s = graph.findEdge(getN("e"), getN("a"));
        assertEquals(getS("a","b","e"), s);

        s = graph.findEdge(getN("a"), getN("xxx"));
        assertNull(s);
    }

    /**
     * Test of findEdgeSet method, of class JenaJungGraph.
     */
    @Test
    public void testFindEdgeSet() {
        Collection<Statement> e = graph.findEdgeSet(getN("a"), getN("d"));
        assertEquals(1, e.size());
        assertTrue(e.contains(getS("a","b","d")));
    }

    /**
     * Test of isNeighbor method, of class JenaJungGraph.
     */
    @Test
    public void testIsNeighbor() {
        assertTrue(graph.isNeighbor(getN("a"), getN("c")));
        assertTrue(graph.isNeighbor(getN("c"), getN("a")));
        assertTrue(graph.isNeighbor(getN("a"), getN("d")));
        assertTrue(graph.isNeighbor(getN("d"), getN("a")));
        assertFalse(graph.isNeighbor(getN("d"), getN("c")));
    }

    /**
     * Test of isIncident method, of class JenaJungGraph.
     */
    @Test
    public void testIsIncident() {
        assertTrue(graph.isIncident(getN("a"), getS("a", "b", "c")));
        assertTrue(graph.isIncident(getN("c"), getS("a", "b", "c")));
        assertFalse(graph.isIncident(getN("d"), getS("a", "b", "c")));
    }

    /**
     * Test of degree method, of class JenaJungGraph.
     */
    @Test
    public void testDegree() {
        assertEquals(3, graph.degree(getN("a")));
        assertEquals(1, graph.degree(getN("e")));
    }

    /**
     * Test of getNeighborCount method, of class JenaJungGraph.
     */
    @Test
    public void testGetNeighborCount() {
        assertEquals(3, graph.getNeighborCount(getN("a")));
        assertEquals(1, graph.getNeighborCount(getN("e")));
    }

    /**
     * Test of getIncidentCount method, of class JenaJungGraph.
     */
    @Test
    public void testGetIncidentCount() {
        assertEquals(2, graph.getIncidentCount(getS("a","b","c")));
    }

    /**
     * Test of getEdgeType method, of class JenaJungGraph.
     */
    @Test
    public void testGetEdgeType() {
    }

    /**
     * Test of getDefaultEdgeType method, of class JenaJungGraph.
     */
    @Test
    public void testGetDefaultEdgeType() {
    }

    /**
     * Test of getEdges method, of class JenaJungGraph.
     */
    @Test
    public void testGetEdges_EdgeType() {
    }

    /**
     * Test of getEdgeCount method, of class JenaJungGraph.
     */
    @Test
    public void testGetEdgeCount_EdgeType() {
    }

}