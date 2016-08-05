/**
 * 
 */
package com.itemis.gef4.tutorial.mindmap.policies;

import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.mvc.fx.policies.FXFocusAndSelectOnClickPolicy;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.models.SelectionModel;

import com.google.common.reflect.TypeToken;
import com.itemis.gef4.tutorial.mindmap.MindMapPaletteModuleExtension;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Checks if an element in the palette is selected before it selects an element.
 * 
 * 
 * @author hniederhausen
 *
 */
public class FocusAndSelectOnClickPolicy extends FXFocusAndSelectOnClickPolicy {

	@SuppressWarnings("serial")
	@Override
	protected boolean isFocusAndSelect(MouseEvent event) {
		// check if the selection model of the palette viewer is empty
		FXViewer viewer = getHost().getRoot().getViewer().getDomain()
				.getAdapter(AdapterKey.get(FXViewer.class, MindMapPaletteModuleExtension.PALETTE_VIEWER_ROLE));
		SelectionModel<Node> selectionModel = viewer.getAdapter(new TypeToken<SelectionModel<Node>>() {});
		return super.isFocusAndSelect(event) && selectionModel.getSelectionUnmodifiable().isEmpty();
	}
}
