# Step 6 Creating new nodes

## Creating a Palette

A palette is a tool, which provides the mechanism to create new elements in our mind map. For now, we will create the palette as a button bar on the left side of your window.
In later tutorials, we will exchange the button bar with a graphical viewer, which uses the same Nodes as the FXViewer of the mind map does.

1. To create the palette, we have to extend the MindMapApplication. The method `hookViewers` creates the JavaFX Scene.
instead of adding the ÌnifiniteCanvas to the scene, we create a new BorderPane, filing it with our palette node and the canvas.

	private void hookViewers() {
		
		BorderPane pane = new BorderPane(getContentViewer().getCanvas());
		pane.setLeft(createPaletteNode());

		pane.setPrefSize(800, 600);
		
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
	}

	private Node createPaletteNode() {
		ToggleButton addNodeButton = new ToggleButton("New Node");
		addNodeButton.setPrefHeight(80);
		
		return new VBox(addNodeButton);
	}

2. We create a creation model, which stores the the kind of creation (right now we only have the nodes, but we might extend the whole mind map with comments and of course, we need to create connections).

3. Add the model to the FXViewer similar to the SelectionModel.

4. Let's add a property listener to the selection property and set the model, if the button is pressed or released.

5. Now create a new Policy which listens to clicks in the MindMap, checks whether the creation model is configured for a type and create a new one. If we pressed the orimary button we clear the selection. The secondary button will stay in the creation mode and we can create more nodes.

6. Now we bind the policy to our ContentRootPart. 



