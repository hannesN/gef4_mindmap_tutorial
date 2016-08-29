package com.itemis.gef4.tutorial.mindmap.policies;

import org.eclipse.gef.mvc.fx.policies.IFXOnClickPolicy;
import org.eclipse.gef.mvc.fx.policies.IFXOnTypePolicy;

import org.eclipse.gef.mvc.policies.AbstractInteractionPolicy;
import org.eclipse.gef.mvc.viewer.IViewer;

import com.itemis.gef4.tutorial.mindmap.models.ItemCreationModel;
import com.itemis.gef4.tutorial.mindmap.models.ItemCreationModel.Type;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class CancelCreationPolicy extends AbstractInteractionPolicy<Node> implements IFXOnTypePolicy, IFXOnClickPolicy {

	@Override
	public void click(MouseEvent e) {
		if (e.isSecondaryButtonDown()) {
			cancelCreation();
		}
	}

	@Override
	public void pressed(KeyEvent event) {
	}

	@Override
	public void released(KeyEvent event) {
		if (event.getCode()==KeyCode.ESCAPE) {
			cancelCreation();
		}
	}

	@Override
	public void typed(KeyEvent event) {
	}

	@Override
	public void unfocus() {
	}

	
	private void cancelCreation() {
		IViewer<Node> viewer = getHost().getRoot().getViewer();
		ItemCreationModel creationModel = viewer.getAdapter(ItemCreationModel.class);
		
		if (creationModel==null) {
			throw new IllegalStateException("No ItemCreationModel bound to viewer");
		}
		
		if (creationModel.getType()!=Type.None) {
			creationModel.clearSettings();
		}
	}
	
}
