package com.itemis.gef4.tutorial.mindmap.parts.feedback;

import org.eclipse.gef.mvc.fx.policies.FXTransformPolicy;
import org.eclipse.gef.mvc.parts.AbstractFeedbackPart;
import org.eclipse.gef.mvc.parts.IVisualPart;
import org.eclipse.gef.mvc.viewer.IViewer;

import com.itemis.gef4.tutorial.mindmap.visuals.MindMapNodeVisual;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class CreateNodeFeedbackPart extends AbstractFeedbackPart<Node, MindMapNodeVisual> {

	private double posX = 0;
	private double posY = 0;
	
	@Override
	protected MindMapNodeVisual createVisual() {
		MindMapNodeVisual visual = new MindMapNodeVisual();

		visual.setTitle("New node");
		visual.setDescription("No description");
		visual.setColor(Color.GREENYELLOW);

		return visual;

	}
	
	@Override
	protected void doRefreshVisual(MindMapNodeVisual visual) {
		System.out.println("refreshing visual");

		// do not forget to bin a Transform provider to the this FeedbackPart!
		Affine affine = getAdapter(FXTransformPolicy.TRANSFORM_PROVIDER_KEY).get();
		// set the center to the visual
		Bounds b = visual.getBoundsInLocal();
		b.getWidth();
		affine.setTx(posX-b.getWidth()/2); 
		affine.setTy(posY-b.getHeight()/2);
	}

	@Override
	protected void attachToAnchorageVisual(IVisualPart<Node, ? extends Node> anchorage, String role) {
		// listen to any mouse move and reposition the anchor
		getRoot().getVisual().getScene().setOnMouseMoved((MouseEvent e) -> {
			
			Point2D sceneToLocal = getVisual().getParent().sceneToLocal(e.getX(), e.getY());
			System.out.println(sceneToLocal);
			
			posX = sceneToLocal.getX();
			posY = sceneToLocal.getY();
			
			refreshVisual();
		});
	}
	
	@Override
	protected void unregisterFromVisualPartMap(IViewer<Node> viewer, MindMapNodeVisual visual) {
		System.out.println("Unregister");
		super.unregisterFromVisualPartMap(viewer, visual);
	}

	@Override
	protected void detachFromAnchorageVisual(IVisualPart<Node, ? extends Node> anchorage, String role) {
		getRoot().getVisual().getScene().setOnMouseMoved(null);
	}

	@Override
	public MindMapNodeVisual getVisual() {
		return (MindMapNodeVisual) super.getVisual();
	}
}
