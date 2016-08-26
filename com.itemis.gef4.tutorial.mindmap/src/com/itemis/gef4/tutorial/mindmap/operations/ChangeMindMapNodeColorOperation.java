package com.itemis.gef4.tutorial.mindmap.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.mvc.operations.ITransactionalOperation;

import com.itemis.gef4.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;

import javafx.scene.paint.Color;

/**
 * Changes the color of a {@link MindMapNode} and provides undo functionality.
 * @author hniederhausen
 *
 */
public class ChangeMindMapNodeColorOperation extends AbstractOperation implements ITransactionalOperation {

	private final Color newColor;
	private final Color oldColor;
	private final MindMapNodePart mindMapNodePart;
	
	public ChangeMindMapNodeColorOperation(MindMapNodePart mindMapNodePart, Color newColor) {
		super("Change color for Node: "+mindMapNodePart.getContent().getTite());
		this.mindMapNodePart = mindMapNodePart;
		this.newColor = newColor;
		this.oldColor = mindMapNodePart.getContent().getColor();
	}

	@Override
	public boolean isContentRelevant() {
		return true;
	}

	@Override
	public boolean isNoOp() {
		return newColor.equals(oldColor);
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		mindMapNodePart.getContent().setColor(newColor);
		mindMapNodePart.refreshVisual();
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		mindMapNodePart.getContent().setColor(oldColor);
		mindMapNodePart.refreshVisual();
		return Status.OK_STATUS;
	}
	
	

}
