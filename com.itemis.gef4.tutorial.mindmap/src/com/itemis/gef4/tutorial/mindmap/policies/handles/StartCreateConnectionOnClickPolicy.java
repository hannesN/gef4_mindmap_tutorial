package com.itemis.gef4.tutorial.mindmap.policies.handles;

import org.eclipse.gef.mvc.parts.IContentPart;
import org.eclipse.gef.mvc.viewer.IViewer;

import com.itemis.gef4.tutorial.mindmap.models.ItemCreationModel;
import com.itemis.gef4.tutorial.mindmap.models.ItemCreationModel.Type;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class StartCreateConnectionOnClickPolicy extends AbstactHandleClickPolicy {

	@Override
	public void click(MouseEvent e) {
		if (!e.isPrimaryButtonDown()) {
			return;
		}
		
		IViewer<Node> viewer = getHost().getRoot().getViewer();

		ItemCreationModel creationModel = viewer.getAdapter(ItemCreationModel.class);
		if (creationModel==null) {
			throw new IllegalStateException("No ItemCreatioNModel bound in viewer");
		}
		
		creationModel.setType(Type.Connection);
		creationModel.setSource(getAnchoredPart());
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected IContentPart<Node, Node> getAnchoredPart() {
		// TODO Auto-generated method stub
		return (IContentPart<Node, Node>) super.getAnchoredPart();
	}

}
