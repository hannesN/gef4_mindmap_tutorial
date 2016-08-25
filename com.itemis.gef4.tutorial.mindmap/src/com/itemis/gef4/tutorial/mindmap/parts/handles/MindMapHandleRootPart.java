package com.itemis.gef4.tutorial.mindmap.parts.handles;

import java.util.Map.Entry;

import org.eclipse.gef.mvc.fx.policies.FXHoverOnHoverPolicy;
import org.eclipse.gef.mvc.parts.IVisualPart;
import org.eclipse.gef.mvc.viewer.IViewer;

import com.google.common.collect.SetMultimap;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class MindMapHandleRootPart extends AbstractMindMapHandlePart<VBox> {

	public MindMapHandleRootPart() {
		setAdapter(new FXHoverOnHoverPolicy() {
			@Override
			public void hover(MouseEvent e) {
				// XXX: deactivate hover for this part
			}
		});
	}

	@Override
	protected void addChildVisual(IVisualPart<Node, ? extends Node> child,
			int index) {
		getVisual().getChildren().add(index, child.getVisual());
		for (Entry<IVisualPart<Node, ? extends Node>, String> anchorage : getAnchoragesUnmodifiable()
				.entries()) {
			child.attachToAnchorage(anchorage.getKey(), anchorage.getValue());
		}
	}

	@Override
	protected void attachToAnchorageVisual(
			IVisualPart<Node, ? extends Node> anchorage, String role) {
		super.attachToAnchorageVisual(anchorage, role);
		for (IVisualPart<Node, ? extends Node> child : getChildrenUnmodifiable()) {
			child.attachToAnchorage(anchorage, role);
		}
	}

	@Override
	protected VBox createVisual() {
		VBox vBox = new VBox();
		
		vBox.setPickOnBounds(true);
		return vBox;
	}

	@Override
	protected void detachFromAnchorageVisual(
			IVisualPart<Node, ? extends Node> anchorage, String role) {
		super.detachFromAnchorageVisual(anchorage, role);
		for (IVisualPart<Node, ? extends Node> child : getChildrenUnmodifiable()) {
			child.detachFromAnchorage(anchorage, role);
		}
	}

	@Override
	protected void doRefreshVisual(VBox visual) {
		// check if we have a host
		SetMultimap<IVisualPart<Node, ? extends Node>, String> anchorages = getAnchoragesUnmodifiable();
		if (anchorages.isEmpty()) {
			return;
		}

		// determine center location of host visual
		IVisualPart<Node, ? extends Node> anchorage = anchorages.keys()
				.iterator().next();
		refreshHandleLocation(anchorage.getVisual());
	}

	protected void refreshHandleLocation(Node hostVisual) {
		// position vbox top-right next to the host
		Bounds hostBounds = hostVisual.getBoundsInParent();
		Parent parent = hostVisual.getParent();
		if (parent != null) {
			hostBounds = parent.localToScene(hostBounds);
		}
		Point2D location = getVisual().getParent()
				.sceneToLocal(hostBounds.getMaxX(), hostBounds.getMinY());
		getVisual().setLayoutX(location.getX());
		getVisual().setLayoutY(location.getY());
	}

	@Override
	protected void registerAtVisualPartMap(IViewer<Node> viewer, VBox visual) {
	}

	@Override
	protected void removeChildVisual(IVisualPart<Node, ? extends Node> child,
			int index) {
		getVisual().getChildren().remove(index);
	}

	@Override
	protected void unregisterFromVisualPartMap(IViewer<Node> viewer,
			VBox visual) {
	}

}
