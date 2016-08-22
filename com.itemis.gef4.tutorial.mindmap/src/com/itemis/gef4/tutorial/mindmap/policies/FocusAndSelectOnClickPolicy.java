/**
 * 
 */
package com.itemis.gef4.tutorial.mindmap.policies;

import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.mvc.fx.policies.FXFocusAndSelectOnClickPolicy;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.models.SelectionModel;

import com.google.common.reflect.TypeToken;
import com.itemis.gef4.tutorial.mindmap.MindMapModule;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Checks if an element in the palette is selected before it selects an element.
 * 
 * A selection only occurs, when the palettes selectionmodel is empty (indicating we don't want to create a new element).
 * 
 * 
 * @author hniederhausen
 *
 */
public class FocusAndSelectOnClickPolicy extends FXFocusAndSelectOnClickPolicy {

	
	@SuppressWarnings("serial")
	@Override
	protected boolean isFocusAndSelect(MouseEvent event) {
		// get the palette viewer 
		FXViewer viewer = getHost().getRoot().getViewer().getDomain()
				.getAdapter(AdapterKey.get(FXViewer.class, MindMapModule.PALETTE_VIEWER_ROLE));
		
		// get the selection model from the viewer
		SelectionModel<Node> selectionModel = viewer.getAdapter(new TypeToken<SelectionModel<Node>>() {});

		// check if the selection model of the palette viewer is empty
		return super.isFocusAndSelect(event) && selectionModel.getSelectionUnmodifiable().isEmpty();
	}
}
