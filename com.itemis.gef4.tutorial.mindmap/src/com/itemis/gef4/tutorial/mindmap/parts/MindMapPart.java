package com.itemis.gef4.tutorial.mindmap.parts;

import java.util.List;

import org.eclipse.gef.mvc.fx.parts.AbstractFXContentPart;
import org.eclipse.gef.mvc.parts.IVisualPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.itemis.gef4.tutorial.mindmap.model.Connection;
import com.itemis.gef4.tutorial.mindmap.model.MindMap;
import com.itemis.gef4.tutorial.mindmap.model.MindMapNode;

import javafx.scene.Group;
import javafx.scene.Node;

public class MindMapPart extends AbstractFXContentPart<Group> {

	@Override
	public MindMap getContent() {
		return (MindMap) super.getContent();
	}
	
	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		List<Object> children = Lists.newArrayList();
		
		children.addAll(getContent().getNodes());
		children.addAll(getContent().getConnections());
		
		return children;
	}

	@Override
	protected Group createVisual() {
		// the visual is just a container for our child visuals (nodes and connections)
		return new Group();
	}

	@Override
	protected void doRefreshVisual(Group visual) {
		// nothing to do
	}

	@Override
	protected void addChildVisual(IVisualPart<Node, ? extends Node> child, int index) {
		getVisual().getChildren().add(child.getVisual());
	}
	
	@Override
	protected void removeChildVisual(IVisualPart<Node, ? extends Node> child, int index) {
		getVisual().getChildren().remove(child.getVisual());
	}
	
	@Override
	protected void doAddContentChild(Object contentChild, int index) {
		if (contentChild instanceof MindMapNode) {
			getContent().addNode((MindMapNode) contentChild, index);
		} else if (contentChild instanceof Connection) {
			getContent().addConnection((Connection) contentChild);
		} else {
			throw new IllegalArgumentException("contentChild has invalid type: "+contentChild.getClass());
		}
	}
	
	@Override
	protected void doRemoveContentChild(Object contentChild) {
		if (contentChild instanceof MindMapNode) {
			getContent().removeNode((MindMapNode) contentChild);
		} else if (contentChild instanceof Connection) {
			getContent().removeConnection((Connection) contentChild);
		} else {
			throw new IllegalArgumentException("contentChild has invalid type: "+contentChild.getClass());
		}
	}
}
