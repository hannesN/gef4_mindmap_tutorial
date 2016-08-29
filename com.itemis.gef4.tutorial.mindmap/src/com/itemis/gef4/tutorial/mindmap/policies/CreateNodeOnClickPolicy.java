package com.itemis.gef4.tutorial.mindmap.policies;

import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.policies.IFXOnClickPolicy;
import org.eclipse.gef.mvc.parts.IContentPart;
import org.eclipse.gef.mvc.parts.IRootPart;
import org.eclipse.gef.mvc.parts.IVisualPart;
import org.eclipse.gef.mvc.policies.AbstractInteractionPolicy;
import org.eclipse.gef.mvc.policies.CreationPolicy;
import org.eclipse.gef.mvc.viewer.IViewer;

import com.google.common.collect.HashMultimap;
import com.google.common.reflect.TypeToken;
import com.itemis.gef4.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef4.tutorial.mindmap.models.ItemCreationModel;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapPart;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CreateNodeOnClickPolicy extends AbstractInteractionPolicy<Node> implements IFXOnClickPolicy {

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

		switch (creationModel.getType()) {
		case Node:
			createNode(e, creationModel);
			return;
		case None:
		default:
			return;
		}

	}

	private IViewer<Node> getContentViewer() {
		return getHost().getRoot().getViewer();
	}

	@SuppressWarnings("serial")
	private void createNode(MouseEvent e, ItemCreationModel creationModel) {
		// find model part
		IRootPart<Node, ? extends Node> contentRoot = getContentViewer().getRootPart();
		IVisualPart<Node, ? extends Node> modelPart = contentRoot.getChildrenUnmodifiable().get(0);
		
		if (!(modelPart instanceof MindMapPart)) {
			throw new IllegalStateException("Cannot find MindMapPart.");
		}

		MindMapNode newNode = new MindMapNode();
		newNode.setTite("New node");
		newNode.setDescription("No description");
		newNode.setColor(Color.GREENYELLOW);
		newNode.setBounds(new Rectangle(0, 0, 50, 30));

		// determine coordinates of prototype's origin in model coordinates
		Point2D mouseInLocal = modelPart.getVisual().sceneToLocal(e.getSceneX(), e.getSceneY());
		newNode.getBounds().setLocation(mouseInLocal.getX(), mouseInLocal.getY());

		// create copy of host's geometry using CreationPolicy from root part
		CreationPolicy<Node> creationPolicy = contentRoot.getAdapter(new TypeToken<CreationPolicy<Node>>() {});
		init(creationPolicy);
		
		MindMapNodePart createdPart = (MindMapNodePart) creationPolicy.create(newNode, (MindMapPart) modelPart,
				HashMultimap.<IContentPart<Node, ? extends Node>, String>create());
		
		if (createdPart!=null) {
			commit(creationPolicy);
		
			if (!e.isShiftDown()) {
				creationModel.clearSettings();
			}
		}
	}

}
