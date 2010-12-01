package net.rootdev.jenajung.examples;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.rootdev.jenajung.JenaJungGraph;
import net.rootdev.jenajung.Transformers;

/**
 * Hello world!
 *
 */
public class ToImage {

    public static void main(String[] args) {
        
        String resource = "http://bio2rdf.org/geneid:15275";
        
        Model model = FileManager.get().loadModel(resource);

        Graph<RDFNode, Statement> g = new JenaJungGraph(model);

        Layout<RDFNode, Statement> layout = new FRLayout(g);

        int width = 1400;
        int height = 800;

        layout.setSize(new Dimension(width, height));
        VisualizationImageServer<RDFNode, Statement> viz =
               new VisualizationImageServer(layout, new Dimension(width, height));
        RenderContext context = viz.getRenderContext();
        context.setEdgeLabelTransformer(Transformers.EDGE);
        context.setVertexLabelTransformer(Transformers.NODE);


        viz.setPreferredSize(new Dimension(width, height));
        Image img = viz.getImage(new Point(width/2, height/2), new Dimension(width, height));

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bi.createGraphics();
        // Fill the background in white
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);

        // Draw the image
        g2d.setColor(Color.white);
        g2d.drawImage(img, 0, 0, width, height, Color.blue, null);

        try {
            // Save the image to a file
            ImageIO.write(bi, "PNG", new File("graph.png"));
            System.out.println("Image saved");
        } catch (IOException ex) {
            Logger.getLogger(ToImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
