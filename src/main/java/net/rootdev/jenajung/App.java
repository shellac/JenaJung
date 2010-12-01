package net.rootdev.jenajung;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.RadialTreeLayout;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.RenderContext;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Hello world!
 *
 */
public class App {

    private static Property getN(String s) {
        return ResourceFactory.createProperty("http://example.com/ns#", s);
    }

    public static void main(String[] args) {
        Model model = ModelFactory.createDefaultModel();
        model.add(getN("a"), getN("p"), getN("b"));
        model.add(getN("b"), getN("p"), getN("c"));
        model.add(getN("d"), getN("p"), getN("e"));
        model.add(getN("e"), getN("p"), getN("f"));
        model.add(getN("d"), getN("p"), getN("g"));
        model.add(getN("h"), getN("p"), getN("i"));
        model.add(getN("i"), getN("p"), getN("j"));
        model.add(getN("i"), getN("p"), getN("k"));
        model.setNsPrefix("ns", "http://example.com/ns#");

        Graph<RDFNode, Statement> g = new JenaJungGraph(model);
        DelegateForest<RDFNode, Statement> f =
                new DelegateForest<RDFNode, Statement>((DirectedGraph<RDFNode, Statement>) g);
        Layout<RDFNode, Statement> layout = new RadialTreeLayout(f);//new FRLayout(g);
        layout.setSize(new Dimension(300, 300));
        BasicVisualizationServer<RDFNode, Statement> viz =
                new BasicVisualizationServer<RDFNode, Statement>(layout);
        RenderContext context = viz.getRenderContext();
        context.setEdgeLabelTransformer(Transformers.EDGE);
        context.setVertexLabelTransformer(Transformers.NODE);
        viz.setPreferredSize(new Dimension(350, 350));
        JFrame frame = new JFrame("Jena Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(viz);
        frame.pack();
        frame.setVisible(true);
    }
}
