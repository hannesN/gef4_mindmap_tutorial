package com.itemis.gef4.tutorial.mindmap.policies;

import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.policies.FXResizePolicy;
import org.eclipse.gef.mvc.operations.ForwardUndoCompositeOperation;
import org.eclipse.gef.mvc.operations.ITransactionalOperation;

import com.itemis.gef4.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef4.tutorial.mindmap.operations.ResizeMindMapNodeOperation;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;
import com.itemis.gef4.tutorial.mindmap.visuals.MindMapNodeVisual;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public class MindMapNodeResizePolicy extends FXResizePolicy {

	@Override
	protected Node getVisualToResize() {
		return getHost().getVisual().getShape();
	}
	
	@Override
	protected double getMinimumHeight() {
		MindMapNodeVisual mmv = getHost().getVisual();
		return Math.max(10, mmv.getMinimumHeight());
	}
	
	@Override
	protected double getMinimumWidth() {
		MindMapNodeVisual mmv = getHost().getVisual();
		return Math.max(10, mmv.getMinimumWidth());
	}
	
	@Override
	public MindMapNodePart getHost() {
		return (MindMapNodePart) super.getHost();
	}
	
	/**
	 * Super class just commits the visual changes.
	 * We need to add another operation to store the changes in the model.
	 */
	@Override
	public ITransactionalOperation commit() {
		ITransactionalOperation updateVisualOperation = super.commit();
		if (updateVisualOperation==null) {
//			nothing done
			return null;
		}
		
		MindMapNodePart host = getHost();
		MindMapNode content = host.getContent();
		MindMapNodeVisual visual = host.getVisual();
		Bounds layoutBounds = visual.getShape().getLayoutBounds();
		Rectangle oldBounds = content.getBounds();
		Rectangle newBounds = new Rectangle(oldBounds.getX(), oldBounds.getY(),
				layoutBounds.getWidth(), layoutBounds.getHeight());
		
		ITransactionalOperation updateModelOperation = new ResizeMindMapNodeOperation(host, oldBounds, newBounds);
		
		// compose the operation
		ForwardUndoCompositeOperation op = new ForwardUndoCompositeOperation(updateVisualOperation.getLabel());
		op.add(updateVisualOperation);
		op.add(updateModelOperation);
		
		
		return op;
	}
	
}
