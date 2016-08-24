package com.itemis.gef4.tutorial.mindmap.policies;

import org.eclipse.gef.mvc.fx.policies.IFXOnClickPolicy;
import org.eclipse.gef.mvc.parts.IContentPart;
import org.eclipse.gef.mvc.parts.IRootPart;
import org.eclipse.gef.mvc.parts.IVisualPart;
import org.eclipse.gef.mvc.policies.AbstractInteractionPolicy;
import org.eclipse.gef.mvc.policies.CreationPolicy;
import org.eclipse.gef.mvc.viewer.IViewer;

import com.google.common.collect.HashMultimap;
import com.google.common.reflect.TypeToken;
import com.itemis.gef4.tutorial.mindmap.model.Connection;
import com.itemis.gef4.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef4.tutorial.mindmap.models.ItemCreationModel;
import com.itemis.gef4.tutorial.mindmap.models.ItemCreationModel.Type;
import com.itemis.gef4.tutorial.mindmap.parts.ConnectionPart;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapPart;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class CreateConnectionOnClickPolicy extends AbstractInteractionPolicy<Node> implements IFXOnClickPolicy {

	@Override
	public void click(MouseEvent e) {

		if (e.getClickCount() != 1) {
			return;
		}
		
		if (!e.isPrimaryButtonDown()) {
			return;
		}
		
		// get the creation model!
		IViewer<Node> contentViewer = getContentViewer();
		ItemCreationModel creationModel = contentViewer.getAdapter(ItemCreationModel.class);
		if (creationModel == null) {
			throw new IllegalStateException("A ItemCreationModel must be registered");
		}

		if (creationModel.getType()!=Type.Connection) {
			return;
		}
		
		if (creationModel.getSource()==null) {
			creationModel.setSource(getHost());
			return; // wait for next click
		}

		MindMapNode source = (MindMapNode) creationModel.getSource().getContent();
		MindMapNode target = getHost().getContent();

		if (source.equals(target)) {
			creationModel.clearSettings();
			return;
		}
		
		// find model part
		IRootPart<Node, ? extends Node> contentRoot = getContentViewer().getRootPart();
		IVisualPart<Node, ? extends Node> modelPart = contentRoot.getChildrenUnmodifiable().get(0);
		
		if (!(modelPart instanceof MindMapPart)) {
			throw new IllegalStateException("Cannot find MindMapPart.");
		}

		Connection newConn = new Connection();
		newConn.setSource(source);
		newConn.setTarget(target);


		// create copy of host's geometry using CreationPolicy from root part
		@SuppressWarnings("serial")
		CreationPolicy<Node> creationPolicy = contentRoot.getAdapter(new TypeToken<CreationPolicy<Node>>() {});
		init(creationPolicy);
		ConnectionPart createdPart = (ConnectionPart) creationPolicy.create(newConn, (MindMapPart) modelPart,
				HashMultimap.<IContentPart<Node, ? extends Node>, String>create());
		
		if (createdPart!=null) {
			commit(creationPolicy);
		
			if (e.isPrimaryButtonDown()) {
				creationModel.clearSettings();
			}
		}
		
	}
	
	@Override
	public MindMapNodePart getHost() {
		return (MindMapNodePart) super.getHost();
	}
	
	private IViewer<Node> getContentViewer() {
		return getHost().getRoot().getViewer();
	}

}
