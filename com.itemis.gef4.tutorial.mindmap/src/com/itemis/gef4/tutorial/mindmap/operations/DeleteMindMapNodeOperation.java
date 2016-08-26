package com.itemis.gef4.tutorial.mindmap.operations;

import java.util.ArrayList;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.mvc.models.SelectionModel;
import org.eclipse.gef.mvc.operations.ChangeFocusOperation;
import org.eclipse.gef.mvc.operations.ITransactionalOperation;
import org.eclipse.gef.mvc.operations.ReverseUndoCompositeOperation;
import org.eclipse.gef.mvc.parts.IVisualPart;
import org.eclipse.gef.mvc.viewer.IViewer;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.itemis.gef4.tutorial.mindmap.model.Connection;
import com.itemis.gef4.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef4.tutorial.mindmap.parts.ConnectionPart;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapPart;

import javafx.scene.Node;

public class DeleteMindMapNodeOperation extends ReverseUndoCompositeOperation {

	private final MindMapPart parentPart;
	private final MindMapNodePart partToRemove;

	public DeleteMindMapNodeOperation(MindMapPart parentPart, MindMapNodePart parttoRemove) {
		super("Remove node: " + parttoRemove.getContent().getTite());

		this.parentPart = parentPart;
		this.partToRemove = parttoRemove;

		prepare();
	}

	@SuppressWarnings("serial")
	private void prepare() {
		MindMapNode content = partToRemove.getContent();
		IViewer<Node> viewer = parentPart.getRoot().getViewer();

		SelectionModel<Node> selectionModel = viewer.getAdapter(new TypeToken<SelectionModel<Node>>() {
		});

		// remove Focus from everything...
		add(new ChangeFocusOperation<>(viewer, null));
		
		
		ArrayList<Connection> allConnections = Lists.newArrayList(content.getIncomingConnections());
		allConnections.addAll(content.getOutgoingConnections());

		for (Connection c : allConnections) {
			ConnectionPart part = getPartForConnection(c);

			if (selectionModel.getSelectionUnmodifiable().contains(part)) {
				// we don't need the selection back in undo...
				selectionModel.removeFromSelection(part);
			}

			add(new DeleteConnectionOperation(parentPart, part));
		}

		if (selectionModel.getSelectionUnmodifiable().contains(partToRemove)) {
			selectionModel.removeFromSelection(partToRemove);
		}

		add(new DeleteNodeOperation());
	}

	private ConnectionPart getPartForConnection(Connection connection) {
		for (IVisualPart<Node, ? extends Node> child : parentPart.getChildrenUnmodifiable()) {
			if (child instanceof ConnectionPart) {
				if (((ConnectionPart) child).getContent().equals(connection))
					return (ConnectionPart) child;
			}
		}
		return null;
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return super.execute(monitor, info);
	}

	/**
	 * The operation removing the node, used by the parent class only.
	 * 
	 * @author hniederhausen
	 *
	 */
	private class DeleteNodeOperation extends AbstractOperation implements ITransactionalOperation {

		private int childIdx;

		public DeleteNodeOperation() {
			super("Remove Node Part");
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

			// remove from the Focus and Selection model if it is in there
			childIdx = parentPart.getChildrenUnmodifiable().indexOf(partToRemove);
			
			parentPart.removeChild(partToRemove);
			parentPart.refreshVisual();
			return Status.OK_STATUS;
		}

		@Override
		public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			parentPart.removeChild(partToRemove);
			parentPart.refreshVisual();
			return Status.OK_STATUS;
		}

		@Override
		public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			System.out.println("Undo Child adding");
			parentPart.addChild(partToRemove, childIdx);
			parentPart.refreshVisual();
			return Status.OK_STATUS;
		}

	}
}
