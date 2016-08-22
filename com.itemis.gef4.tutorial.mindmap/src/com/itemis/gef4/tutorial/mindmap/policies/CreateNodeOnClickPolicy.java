package com.itemis.gef4.tutorial.mindmap.policies;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.mvc.fx.domain.FXDomain;
import org.eclipse.gef4.mvc.fx.policies.IFXOnClickPolicy;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IRootPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gef4.mvc.policies.AbstractInteractionPolicy;
import org.eclipse.gef4.mvc.policies.CreationPolicy;
import org.eclipse.gef4.mvc.viewer.IViewer;

import com.google.common.collect.HashMultimap;
import com.google.common.reflect.TypeToken;
import com.itemis.gef4.tutorial.mindmap.MindMapModule;
import com.itemis.gef4.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef4.tutorial.mindmap.palette.parts.PaletteEntryPart;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapPart;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * This policy create a new node at the click event.
 * 
 * @author hniederhausen
 *
 */
public class CreateNodeOnClickPolicy extends AbstractInteractionPolicy<Node> implements IFXOnClickPolicy {

	@SuppressWarnings("serial")
	@Override
	public void click(MouseEvent e) {
		FXViewer viewer = getHost().getRoot().getViewer().getDomain()
				.getAdapter(AdapterKey.get(FXViewer.class, MindMapModule.PALETTE_VIEWER_ROLE));
		
		SelectionModel<Node> selectionModel = viewer.getAdapter(new TypeToken<SelectionModel<Node>>() {
		});

		if (selectionModel.getSelectionUnmodifiable().isEmpty()) {
			return;
		}

		IContentPart<Node, ? extends Node> part = selectionModel.getSelectionUnmodifiable().get(0);

		PaletteEntryPart palEntryPart = null;
		if (part instanceof PaletteEntryPart) {
			palEntryPart = (PaletteEntryPart) part;
		}

		// find model part
		IRootPart<Node, ? extends Node> contentRoot = getContentViewer().getRootPart();
		IVisualPart<Node, ? extends Node> modelPart = contentRoot.getChildrenUnmodifiable().get(0);
		if (!(modelPart instanceof MindMapPart)) {
			throw new IllegalStateException("Cannot find MindMapPart.");
		}

		// copy the prototype
		MindMapNode copy = new MindMapNode(palEntryPart.getContent().getContent());
		
		// determine coordinates of prototype's origin in model coordinates
		Point2D mouseInLocal = modelPart.getVisual().sceneToLocal(e.getSceneX(), e.getSceneY());
		copy.getBounds().setLocation(mouseInLocal.getX(), mouseInLocal.getY());
		
		// create copy of host's geometry using CreationPolicy from root part
		CreationPolicy<Node> creationPolicy = contentRoot.getAdapter(new TypeToken<CreationPolicy<Node>>() {
		});
		init(creationPolicy);
		
		MindMapNodePart createdPart = (MindMapNodePart) creationPolicy.create(copy, (MindMapPart) modelPart,
				HashMultimap.<IContentPart<Node, ? extends Node>, String>create());
		
		if (createdPart!=null) {
			commit(creationPolicy);
		
			selectionModel.clearSelection();
		}
	}

	protected FXViewer getContentViewer() {
		Map<AdapterKey<? extends IViewer<Node>>, IViewer<Node>> viewers = getHost().getRoot().getViewer().getDomain()
				.getViewers();
		for (Entry<AdapterKey<? extends IViewer<Node>>, IViewer<Node>> e : viewers.entrySet()) {
			if (FXDomain.CONTENT_VIEWER_ROLE.equals(e.getKey().getRole())) {
				return (FXViewer) e.getValue();
			}
		}
		throw new IllegalStateException("Cannot find content viewer!");
	}
	
	@Override
	public IRootPart<Node, ? extends Node> getHost() {
		return (IRootPart<Node, ? extends Node>) super.getHost();
	}
}
