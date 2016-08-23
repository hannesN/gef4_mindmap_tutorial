package com.itemis.gef4.tutorial.mindmap.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.operations.ITransactionalOperation;

import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;

/**
 * Operation to resize a {@link MindMapNodePart}
 * 
 * @author hniederhausen
 *
 */
public class ResizeMindMapNodeOperation extends AbstractOperation implements ITransactionalOperation {

	private MindMapNodePart host;
	private Rectangle newBounds;
	private Rectangle oldBounds;

	public ResizeMindMapNodeOperation(MindMapNodePart host, Rectangle oldBounds, Rectangle newBounds) {
		super("Resize Mindmap Node");
		this.host = host;
		this.newBounds = newBounds;
		this.oldBounds = oldBounds;
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		// copy the old location, so we don't accidently move the part
		Rectangle bounds = host.getContent().getBounds();
		newBounds.setX(bounds.getX());
		newBounds.setY(bounds.getY());

		host.getContent().setBounds(newBounds);

		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		// copy the old location, so we don't accidently move the part
		Rectangle bounds = host.getContent().getBounds();
		oldBounds.setX(bounds.getX());
		oldBounds.setY(bounds.getY());

		host.getContent().setBounds(oldBounds);

		return Status.OK_STATUS;
	}

	@Override
	public boolean isContentRelevant() {
		return true;
	}

	@Override
	public boolean isNoOp() {
		return newBounds.equals(oldBounds);
	}

}
