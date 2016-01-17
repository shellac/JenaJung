/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.rootdev.jenajung;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.iterator.ClosableIterator;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author pldms
 */
public class JenaJungGraph implements DirectedGraph<RDFNode, Statement>
{
    final static boolean log = false;
    private final Model model;

    public JenaJungGraph(final Model model) {
        this.model = model;
    }

    private static final <T> Collection<T> asCollection(final ClosableIterator<? extends T> it) {
        final Collection<T> toReturn = new HashSet<T>();
        while (it.hasNext()) toReturn.add((T) it.next());
        it.close();
        return toReturn;
    }

    public Collection<Statement> getInEdges(RDFNode vertex) {
        if (log) System.err.println("getInEdges");
        return asCollection(model.listStatements(null, null, vertex));
    }

    public Collection<Statement> getOutEdges(RDFNode vertex) {
        if (log) System.err.println("getOutEdges");
        if (vertex.isLiteral()) return Collections.EMPTY_LIST;
        else return asCollection(model.listStatements((Resource) vertex, null, (RDFNode) null));
    }

    public Collection<RDFNode> getPredecessors(RDFNode vertex) {
        if (log) System.err.println("getPredecessors");
        // Generics can be ugly
        return JenaJungGraph.<RDFNode>asCollection(model.listResourcesWithProperty(null, vertex));
    }

    public Collection<RDFNode> getSuccessors(RDFNode vertex) {
        if (log) System.err.println("getSucessors");
        if (vertex.isLiteral()) return Collections.EMPTY_LIST;
        else return asCollection(model.listObjectsOfProperty((Resource) vertex, null));
    }

    public int inDegree(RDFNode vertex) {
        if (log) System.err.println("inDegree");
        return getInEdges(vertex).size();
    }

    public int outDegree(RDFNode vertex) {
        if (log) System.err.println("outDegree");
        return getOutEdges(vertex).size();
    }

    public boolean isPredecessor(RDFNode v1, RDFNode v2) {
        if (log) System.err.println("isPredecessor");
        if (v1.isLiteral()) return false;
        else return model.contains((Resource) v1, null, v2);
    }

    public boolean isSuccessor(RDFNode v1, RDFNode v2) {
        if (log) System.err.println("isSuccessor");
        return isPredecessor(v2, v1);
    }

    public int getPredecessorCount(RDFNode vertex) {
        if (log) System.err.println("getPredecessorCount");
        return getPredecessors(vertex).size();
    }

    public int getSuccessorCount(RDFNode vertex) {
        if (log) System.err.println("getSucessorCount");
        return getSuccessors(vertex).size();
    }

    public RDFNode getSource(Statement directed_edge) {
        if (log) System.err.println("getSource");
        return directed_edge.getSubject();
    }

    public RDFNode getDest(Statement directed_edge) {
        if (log) System.err.println("getDest");
        return directed_edge.getObject();
    }

    public boolean isSource(RDFNode vertex, Statement edge) {
        if (log) System.err.println("isSource");
        return vertex.equals(edge.getSubject());
    }

    public boolean isDest(RDFNode vertex, Statement edge) {
        if (log) System.err.println("isDest");
        return vertex.equals(edge.getObject());
    }

    public boolean addEdge(Statement e, RDFNode v1, RDFNode v2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean addEdge(Statement e, RDFNode v1, RDFNode v2, EdgeType edgeType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Pair<RDFNode> getEndpoints(Statement edge) {
        if (log) System.err.println("getEndpoints: " + edge);
        return new Pair(edge.getSubject(), edge.getObject());
    }

    public RDFNode getOpposite(RDFNode vertex, Statement edge) {
        if (log) System.err.println("getOpposite");
        if (edge.getSubject().equals(vertex)) return edge.getObject();
        else return edge.getSubject();
    }

    public Collection<Statement> getEdges() {
        if (log) System.err.println("getEdges");
        return asCollection(model.listStatements());
    }

    public Collection<RDFNode> getVertices() {
        if (log) System.err.println("getVertices");
        Collection<RDFNode> toReturn = asCollection(model.listObjects());
        toReturn.addAll(asCollection(model.listSubjects()));
        return toReturn;
    }

    public boolean containsVertex(RDFNode vertex) {
        if (log) System.err.println("containsVertex");
        if (vertex.isResource() &&
                model.contains((Resource) vertex, null, (RDFNode) null)) return true;
        return model.contains(null, null, vertex);
    }

    public boolean containsEdge(Statement edge) {
        if (log) System.err.println("containsEdge");
        return model.contains(edge);
    }

    public int getEdgeCount() {
        if (log) System.err.println("getEdgeCount");
        return (int) model.size();
    }

    public int getVertexCount() {
        if (log) System.err.println("getVertexCount");
        return getVertices().size();
    }

    public Collection<RDFNode> getNeighbors(RDFNode vertex) {
        if (log) System.err.print("getNeighbours");
        Collection<RDFNode> i = new HashSet<RDFNode>();
        i.addAll(getSuccessors(vertex));
        i.addAll(getPredecessors(vertex));
        return i;
    }

    public Collection<Statement> getIncidentEdges(RDFNode vertex) {
        if (log) System.err.println("getIncidentEdges");
        Collection<Statement> most =
                asCollection(model.listStatements(null, null, vertex));
        if (vertex.isResource()) most.addAll(
                asCollection(model.listStatements((Resource) vertex, null, (RDFNode) null))
                );

        return most;
    }

    public Collection<RDFNode> getIncidentVertices(Statement edge) {
        if (log) System.err.println("getIncidentVertices");
        return Arrays.asList(edge.getSubject(), edge.getObject());
    }

    public Statement findEdge(RDFNode v1, RDFNode v2) {
        if (log) System.err.println("findEdgeSet");
        Collection<Statement> i = findEdgeSet(v1, v2);
        if (i.isEmpty()) return null;
        return i.iterator().next(); // TODO don't do this
    }

    public Collection<Statement> findEdgeSet(RDFNode v1, RDFNode v2) {
        if (log) System.err.println("findEdgeSet");
        Collection<Statement> c = new HashSet<Statement>();
        if (v1.isResource()) c.addAll(
                asCollection(model.listStatements((Resource) v1, null, v2)));
        if (v2.isResource()) c.addAll(
                asCollection(model.listStatements((Resource) v2, null, v1)));
        return c;
    }

    public boolean addVertex(RDFNode vertex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean addEdge(Statement edge, Collection<? extends RDFNode> vertices) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean addEdge(Statement edge, Collection<? extends RDFNode> vertices, EdgeType edge_type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean removeVertex(RDFNode vertex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean removeEdge(Statement edge) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isNeighbor(RDFNode v1, RDFNode v2) {
        if (log) System.err.println("isNeighbour");
        return this.getNeighbors(v1).contains(v2);
    }

    public boolean isIncident(RDFNode vertex, Statement edge) {
        System.err.println("isIncident");
        return (edge.getSubject().equals(vertex) ||
                edge.getObject().equals(vertex));
    }

    public int degree(RDFNode vertex) {
        if (log) System.err.println("degree");
        return inDegree(vertex) + outDegree(vertex);
    }

    public int getNeighborCount(RDFNode vertex) {
        if (log) System.err.println("neighbour count");
        return this.getNeighbors(vertex).size();
    }

    public int getIncidentCount(Statement edge) {
        if (edge.getSubject().equals(edge.getObject())) return 1;
        return 2;
    }

    public EdgeType getEdgeType(Statement edge) {
        if (log) System.err.println("getEdgeType");
        return EdgeType.DIRECTED;
    }

    public EdgeType getDefaultEdgeType() {
        if (log) System.err.println("getDefaultEdgeType");
        return EdgeType.DIRECTED;
    }

    public Collection<Statement> getEdges(EdgeType edge_type) {
        if (log) System.err.println("getEdges(type)");
        if (edge_type.equals(EdgeType.DIRECTED)) return getEdges();
        else return Collections.EMPTY_LIST;
    }

    public int getEdgeCount(EdgeType edge_type) {
        if (log) System.err.println("getEdgeCount(type)");
        if (edge_type.equals(EdgeType.DIRECTED)) return (int) model.size();
        else return 0;
    }

}
