package com.itemis.gef4.tutorial.mindmap.policies;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.gef.mvc.domain.IDomain;
import org.eclipse.gef.mvc.fx.policies.IFXOnTypePolicy;
import org.eclipse.gef.mvc.policies.AbstractInteractionPolicy;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Policy to implement some short cuts. Sadly the implementation is dependent on a buggy
 * keyEvent. In the german keyboard layout you have to press CMD-Y to get the event for CMD-Z.
 * 
 * 
 * @author hniederhausen
 *
 */
public class ShortcutHandlingPolicy extends AbstractInteractionPolicy<Node> implements IFXOnTypePolicy {

	@Override
	public void pressed(KeyEvent event) {
		
		if (event.getCode()==KeyCode.Z && event.isMetaDown()) {
			if (event.isShiftDown()) {
				redo();
			} else {
				undo();
			}
				
		}
	}

	@Override
	public void released(KeyEvent event) {
	}

	@Override
	public void typed(KeyEvent event) {		
	}

	@Override
	public void unfocus() {
	}

	private void undo() {
		IDomain<Node> domain = getDomain();
		IUndoContext context = domain.getUndoContext();
		if (domain.getOperationHistory().canUndo(context)) {
			try {
				domain.getOperationHistory().undo(context, null, null);
			} catch (ExecutionException e) {
			}
		}
	}

	private void redo() {
		IDomain<Node> domain = getDomain();
		IUndoContext context = domain.getUndoContext();
		if (domain.getOperationHistory().canRedo(context)) {
			try {
				domain.getOperationHistory().redo(context, null, null);
			} catch (ExecutionException e) {
			}
		}
		
	}

	private IDomain<Node> getDomain() {
		return getHost().getRoot().getViewer().getDomain();
	}

}
