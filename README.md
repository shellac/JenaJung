Welcome to JenaJung
===================

[JUNG](http://jung.sourceforge.net/) is a very useful toolkit for working with and visualising graphs in java. JenaJung provides little glue to make Jena models work as (read only) JUNG graphs.

An Example
----------

	Model model = FileManager.get().loadModel("http://example.com/data.rdf");
	Graph<RDFNode, Statement> g = new JenaJungGraph(model);
	
	Layout<RDFNode, Statement> layout = new FRLayout(g);
	layout.setSize(new Dimension(300, 300));
	BasicVisualizationServer<RDFNode, Statement> viz =
	        new BasicVisualizationServer<RDFNode, Statement>(layout);
	RenderContext context = viz.getRenderContext();
	context.setEdgeLabelTransformer(Transformers.EDGE); // property label
	context.setVertexLabelTransformer(Transformers.NODE); // node label
	viz.setPreferredSize(new Dimension(350, 350));
	JFrame frame = new JFrame("Jena Graph");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().add(viz);
	frame.pack();
	frame.setVisible(true);

See the [examples](http://github.com/shellac/JenaJung/tree/master/src/main/java/net/rootdev/jenajung/examples), which show how to produce png and svg images from JUNG.

ToDo
----

There are undoubtedly bugs. It's also pretty inefficient.