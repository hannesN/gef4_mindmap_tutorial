package com.itemis.gef4.tutorial.mindmap.policies;

import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.mvc.fx.policies.IFXOnClickPolicy;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.policies.AbstractInteractionPolicy;

import com.google.common.reflect.TypeToken;
import com.itemis.gef4.tutorial.mindmap.MindMapModule;
import com.itemis.gef4.tutorial.mindmap.model.ConnectionCreationModel;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Policy, which creates a connection in two steps. if 
 * @author hniederhausen
 *
 */
public class CreateConnectionOnClickPolicy extends AbstractInteractionPolicy<Node> implements IFXOnClickPolicy {

	@SuppressWarnings("serial")
	@Override
	public void click(MouseEvent e) {
		// TODO Auto-generated method stub
		FXViewer viewer = getHost().getRoot().getViewer().getDomain()
				.getAdapter(AdapterKey.get(FXViewer.class, MindMapModule.PALETTE_VIEWER_ROLE));
		
		SelectionModel<Node> selectionModel = viewer.getAdapter(new TypeToken<SelectionModel<Node>>() {
		});

		if (selectionModel.getSelectionUnmodifiable().isEmpty()) {
			return;
		}
		
		
		ConnectionCreationModel model = viewer.getAdapter(ConnectionCreationModel.class);
		
	}

}
