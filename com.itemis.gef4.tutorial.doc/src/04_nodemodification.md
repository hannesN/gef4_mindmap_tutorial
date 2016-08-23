# Step 5 Resizing and Moving MindMapNodes

1. We will install policies to move and resize nodes.

## Move a node

1. Imlement IResizableContentPart in class MindMapNodePart
2. Add the Policies to the modules ```bindMindMapNodePartAdapters```

    // bind drag policies
    adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FXTransformPolicy.class);
	adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FXTranslateSelectedOnDragPolicy.class);// bind drag policies
	adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FXTransformPolicy.class);
	adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FXTranslateSelectedOnDragPolicy.class);

## resize node

1. Implement IResizableContentPart interface in `MindMapnodePart`
2. New class `MindMapNodeResizePolicy` inheriting from FXResizePolicy (and the operation to used to save the size in the model).
3. Register it to the module

	// bind our resize policy
	adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(MindMapNodeResizePolicy.class);
	// specify the factory to create the geometry object for the selection handles
	role = AdapterKey.role(FXDefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER);
	adapterMapBinder.addBinding(role).to(ShapeOutlineProvider.class);

4. bindSegmentHandler for the Handles in the selection outline

  a. Add method

	protected void bindFXSquareSegmentHandlePartPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FXResizeTranslateFirstAnchorageOnHandleDragPolicy.class);
	}
	
  b. Call method in configure::
    
    // with this binding we create the handles
	bindFXSquareSegmentHandlePartPartAdapter(AdapterMaps.getAdapterMapBinder(binder(), FXSquareSegmentHandlePart.class));
	



## Direct Editing

This implementation is experimental, not sure if it will stay in the tutorial.

