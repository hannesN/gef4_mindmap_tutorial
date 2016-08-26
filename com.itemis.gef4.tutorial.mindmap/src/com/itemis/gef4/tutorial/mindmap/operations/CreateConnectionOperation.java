package com.itemis.gef4.tutorial.mindmap.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.mvc.operations.ITransactionalOperation;

import com.itemis.gef4.tutorial.mindmap.model.Connection;
import com.itemis.gef4.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapPart;

/**
 * Operation to create a new connection
 * @author hniederhausen
 *
 */
public class CreateConnectionOperation extends AbstractOperation implements ITransactionalOperation {

	private final MindMapPart parent;
	private final MindMapNode source;
	private final MindMapNode target;
	
	private final Connection connection;
	
	public CreateConnectionOperation(MindMapPart parent, MindMapNode source, MindMapNode target) {
		super("Create mind map connection");
		this.parent = parent;
		this.source = source;
		this.target = target;
		
		connection = new Connection();
	}

	@Override
	public boolean isContentRelevant() {
		return true;
	}

	@Override
	public boolean isNoOp() {
		return source.equals(target);
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		connection.connect(source, target);
		parent.addContentChild(connection, parent.getContentChildrenUnmodifiable().size());
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		connection.disconnect();
		parent.removeContentChild(connection);
		return Status.OK_STATUS;
	}

	
	
}
