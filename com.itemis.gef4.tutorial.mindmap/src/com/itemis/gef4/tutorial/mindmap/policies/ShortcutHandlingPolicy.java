package com.itemis.gef4.tutorial.mindmap.policies;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.gef.mvc.domain.IDomain;
import org.eclipse.gef.mvc.fx.policies.IFXOnTypePolicy;
import org.eclipse.gef.mvc.policies.AbstractInteractionPolicy;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

/**
 * Policy to implement some short cuts. We need to use the typed method because it is the only
 * event, which resolves the pressed key independent from the layout
 * 
 * 
 * @author hniederhausen
 *
 */
public class ShortcutHandlingPolicy extends AbstractInteractionPolicy<Node> implements IFXOnTypePolicy {

	@Override
	public void pressed(KeyEvent event) {
		
	}

	@Override
	public void released(KeyEvent event) {
	}

	@Override
	public void typed(KeyEvent event) {		
		// here the pressed key is the right on
		// so we use the typed event
		if (event.getCharacter().equals("z") && event.isMetaDown()) {
			if (event.isShiftDown()) {
				redo();
			} else {
				undo();
			}
				
		}
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
