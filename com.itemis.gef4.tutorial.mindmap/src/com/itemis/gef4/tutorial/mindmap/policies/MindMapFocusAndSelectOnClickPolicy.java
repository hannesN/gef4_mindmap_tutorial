/*******************************************************************************
 * Copyright (c) 2014, 2016 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Nyßen (itemis AG) - initial API and implementation
 *
 *******************************************************************************/
package com.itemis.gef4.tutorial.mindmap.policies;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gef.mvc.fx.policies.AbstractFXInteractionPolicy;
import org.eclipse.gef.mvc.fx.policies.FXFocusAndSelectOnClickPolicy;
import org.eclipse.gef.mvc.fx.policies.IFXOnClickPolicy;
import org.eclipse.gef.mvc.models.FocusModel;
import org.eclipse.gef.mvc.models.SelectionModel;
import org.eclipse.gef.mvc.operations.ChangeFocusOperation;
import org.eclipse.gef.mvc.operations.ChangeSelectionOperation;
import org.eclipse.gef.mvc.operations.DeselectOperation;
import org.eclipse.gef.mvc.operations.ITransactionalOperation;
import org.eclipse.gef.mvc.operations.SelectOperation;
import org.eclipse.gef.mvc.parts.IContentPart;
import org.eclipse.gef.mvc.parts.IRootPart;
import org.eclipse.gef.mvc.parts.IVisualPart;
import org.eclipse.gef.mvc.viewer.IViewer;

import com.google.common.reflect.TypeToken;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * This class is based on the {@link FXFocusAndSelectOnClickPolicy} but doesn't store the changes in the operation history.
 * 
 * Therefor selection and focus is not undoable
 *
 * @author anyssen
 *
 */
public class MindMapFocusAndSelectOnClickPolicy extends AbstractFXInteractionPolicy
		implements IFXOnClickPolicy {

	@SuppressWarnings("serial")
	@Override
	public void click(MouseEvent e) {
		// focus and select are only done on single click
		if (!isFocusAndSelect(e)) {
			return;
		}

		IVisualPart<Node, ? extends Node> host = getHost();
		IViewer<Node> viewer = host.getRoot().getViewer();
		SelectionModel<Node> selectionModel = viewer
				.getAdapter(new TypeToken<SelectionModel<Node>>() {
				});

		// perform different changes depending on host type
		if (host instanceof IContentPart) {
			IContentPart<Node, ? extends Node> contentPart = (IContentPart<Node, ? extends Node>) host;

			// check if the host is the explicit event target
			if (isRegistered(e.getTarget())
					&& !isRegisteredForHost(e.getTarget())) {
				// do not process events for other parts
				return;
			}

			// determine if replacing or extending the selection
			boolean append = e.isControlDown();
			List<IContentPart<Node, ? extends Node>> singletonHostList = Collections
					.<IContentPart<Node, ? extends Node>> singletonList(
							contentPart);

			// create selection change operation(s)
			boolean wasDeselected = false;
			ITransactionalOperation selectionChangeOperation = null;
			if (selectionModel.isSelected(contentPart)) {
				if (append) {
					// deselect the host
					selectionChangeOperation = new DeselectOperation<>(viewer,
							singletonHostList);
					wasDeselected = true;
				}
			} else if (contentPart.isSelectable()) {
				if (append) {
					// prepend host to current selection (as new primary)
					selectionChangeOperation = new SelectOperation<>(viewer,
							singletonHostList);
				} else {
					// clear old selection, host becomes the only selected
					selectionChangeOperation = new ChangeSelectionOperation<>(
							viewer, singletonHostList);
				}
			}

			// execute selection changes
			if (selectionChangeOperation != null) {
				try {
					selectionChangeOperation.execute(null, null);
				} catch (ExecutionException e1) {
					throw new IllegalStateException(e1);
				}
			}

			// change focus depending on selection changes
			ChangeFocusOperation<Node> changeFocusOperation = null;
			ObservableList<IContentPart<Node, ? extends Node>> selection = selectionModel
					.getSelectionUnmodifiable();
			if (wasDeselected && selection.isEmpty()) {
				// unfocus when the only selected part was deselected
				changeFocusOperation = new ChangeFocusOperation<>(viewer, null);
			} else {
				// focus new primary selection
				IContentPart<Node, ? extends Node> primarySelection = selection
						.get(0);
				if (primarySelection.isFocusable()) {
					FocusModel<Node> focusModel = viewer
							.getAdapter(new TypeToken<FocusModel<Node>>() {
							});
					if (focusModel.getFocus() == primarySelection) {
						primarySelection.getVisual().requestFocus();
					} else {
						changeFocusOperation = new ChangeFocusOperation<>(
								viewer, primarySelection);
					}
				}
			}

			// execute focus change
			if (changeFocusOperation != null) {
				try {
					changeFocusOperation.execute(null, null);
				} catch (ExecutionException e1) {
					throw new IllegalStateException(e1);
				}
			}
		} else if (host instanceof IRootPart) {
			// check if click on background (either one of the root visuals, or
			// an unregistered visual)
			if (!isRegistered(e.getTarget())
					|| isRegisteredForHost(e.getTarget())) {
				// unset focus and clear selection
				try {
					FocusModel<Node> focusModel = viewer
							.getAdapter(new TypeToken<FocusModel<Node>>() {
							});
					if (focusModel.getFocus() == null) {
						// no focus change needed, only update feedback
						viewer.getRootPart().getVisual().requestFocus();
					} else {
						// change focus, will update feedback via behavior
						new ChangeFocusOperation<>(viewer, null).execute(new NullProgressMonitor(), null);
					}
					new DeselectOperation<>(viewer, selectionModel.getSelectionUnmodifiable())
							.execute(new NullProgressMonitor(), null);
				} catch (ExecutionException e1) {
					throw new IllegalStateException(e1);
				}
			}
		}
	}

	/**
	 * Returns <code>true</code> if the given {@link MouseEvent} should trigger
	 * focus and select. Otherwise returns <code>false</code>. Per default
	 * returns <code>true</code> if a single mouse click is performed.
	 *
	 * @param event
	 *            The {@link MouseEvent} in question.
	 * @return <code>true</code> if the given {@link MouseEvent} should trigger
	 *         focus and select, otherwise <code>false</code>.
	 */
	protected boolean isFocusAndSelect(MouseEvent event) {
		return event.getClickCount() <= 1;
	}

}
