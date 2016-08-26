package com.itemis.gef4.tutorial.mindmap.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.mvc.operations.ITransactionalOperation;

import com.itemis.gef4.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef4.tutorial.mindmap.parts.ConnectionPart;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapPart;

public class DeleteConnectionOperation  extends AbstractOperation implements ITransactionalOperation {

	private final ConnectionPart connectionPart;
	private final MindMapPart parentPart;
	
	private final MindMapNode source;
	private final MindMapNode target;
	
	private int childIdx;
	
	public DeleteConnectionOperation(MindMapPart parentPart, ConnectionPart connectionPart) {
		super("Remove Connection");
		this.parentPart = parentPart;
		this.connectionPart = connectionPart;
		
		this.source = connectionPart.getContent().getSource();
		this.target = connectionPart.getContent().getTarget();
	}
	
	@Override
	public boolean isContentRelevant() {
		return true;
	}

	@Override
	public boolean isNoOp() {
		return false;
	}
	
	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		childIdx = parentPart.getChildrenUnmodifiable().indexOf(connectionPart);
		System.out.println("Found child at: "+childIdx);
		connectionPart.getContent().disconnect();
		parentPart.removeChild(connectionPart);
		parentPart.refreshVisual();
		
		
		
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		connectionPart.getContent().connect(source, target);
		System.out.println("Undo delete Connection");
		parentPart.addChild(connectionPart, childIdx);
		
		return Status.OK_STATUS;
	}

}
