Step 8 - Removing nodes

Goal: We want to be able to remove node from the Mind Map. We will create an icon whcih is apearing in the top right corner when hovering over a node. On click the node will be removed.

0. First we steel the icsons shamelessly from the GEF Logo Example. Copy the whole images folder into the mindmap project and add it to the build.properties. Also set the images folder as source path, so we can load them via the class loader.

1. The delete icon to show needs also a part. For a better overview, we will put handle parts in to the package `parts.handles`.
Create a Part class `DeleteMindMapNodeHandlePart` and a `MindMapHandleRootPart`, which contains the handles , using a VBox. We are using an `AbstractMindMapHandlePart` to manage some anchorage changes.
TODO explain what it does).

2. Create a factory, which creates our handles. We will need a factory for the selection handles and the hover handles, although the code is almost the same, their context is different.

3. Add the factories to the module:

	@Override
	protected void bindHoverHandlePartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// adding our handle factory
		adapterMapBinder.addBinding(AdapterKey.role(HoverBehavior.HOVER_HANDLE_PART_FACTORY))
				.to(MindMapHoverHandleFactory.class);
	}

	@Override
	protected void bindSelectionHandlePartFactoryAsContentViewerAdapter(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.role(SelectionBehavior.SELECTION_HANDLE_PART_FACTORY))
				.to(MindMapSelectionHandleFactory.class);
	}
	
And make sure to provide `ShapeBoundProvider` for the factories, by adding them to the `MindMapNodeAdapters`:

	protected void bindMindMapNodePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {

		...
		
		// add a bounds provider for the HoverHandleFactory
		role = AdapterKey.role(FXDefaultHoverHandlePartFactory.HOVER_HANDLES_GEOMETRY_PROVIDER);
		adapterMapBinder.addBinding(role).to(ShapeBoundsProvider.class);

		// add a bounds provider for the SelectionHandleFactory
		role = AdapterKey.role(FXDefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER);
		adapterMapBinder.addBinding(role).to(ShapeBoundsProvider.class);

		...
	}
	
4. Create a new Policy: `DeleteMindMapNodeOnClickPolicy` and register it into the `DeleteMindMapNodeHandlePart` in the module.

And that's it. Later we will create another handle, which will create a new connection with the anchored node as source...