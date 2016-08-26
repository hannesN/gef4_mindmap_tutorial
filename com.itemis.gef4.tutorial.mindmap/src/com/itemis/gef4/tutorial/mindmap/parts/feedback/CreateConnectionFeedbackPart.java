package com.itemis.gef4.tutorial.mindmap.parts.feedback;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.fx.anchors.StaticAnchor;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.parts.AbstractFeedbackPart;
import org.eclipse.gef.mvc.parts.IVisualPart;

import com.google.common.reflect.TypeToken;
import com.google.inject.Provider;
import com.itemis.gef4.tutorial.mindmap.visuals.ConnectionVisual;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class CreateConnectionFeedbackPart extends AbstractFeedbackPart<Node, Node>{

	@Override
	protected Node createVisual() {
		return new ConnectionVisual();
	}

	@Override
	protected void doRefreshVisual(Node visual) {
	}

	@Override
	protected void attachToAnchorageVisual(IVisualPart<Node, ? extends Node> anchorage, String role) {
		
		System.out.println("Adding anchorage");
		
		// find a anchor provider, which must be registered in the module
		// be aware to use the right interfaces (Proviser is used a lot)
		@SuppressWarnings("serial")
		Provider<? extends IAnchor> adapter = anchorage.getAdapter(AdapterKey.get(new TypeToken<Provider<? extends IAnchor>>() {}));
		if (adapter == null) {
			throw new IllegalStateException("No adapter  found for <" + anchorage.getClass() + "> found.");
		}
		// set the start anchor
		IAnchor anchor = adapter.get();
		getVisual().setStartAnchor(anchor);

		MousePositionAnchor endAnchor = new MousePositionAnchor(new Point(0,0));
		getVisual().setEndAnchor(endAnchor);

		// listen to any mouse move and reset the anchor
		getRoot().getVisual().getScene().setOnMouseMoved(endAnchor);
		
	}

	@Override
	protected void detachFromAnchorageVisual(IVisualPart<Node, ? extends Node> anchorage, String role) {
		getVisual().setStartPoint(getVisual().getStartPoint());
		getVisual().setEndPoint(getVisual().getEndPoint());
		
	}
	
	@Override
	public ConnectionVisual getVisual() {
		return (ConnectionVisual) super.getVisual();
	}
	
	
	
	private class MousePositionAnchor extends StaticAnchor implements EventHandler<MouseEvent>{

		public MousePositionAnchor(Point referencePositionInScene) {
			super(referencePositionInScene);
		}

		@Override
		public void handle(MouseEvent event) {
			Point v = new Point(event.getSceneX(), event.getSceneY());
			referencePositionProperty().setValue(v);
		}
		
	}
}
