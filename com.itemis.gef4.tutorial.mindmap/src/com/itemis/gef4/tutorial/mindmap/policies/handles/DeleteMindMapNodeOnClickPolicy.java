package com.itemis.gef4.tutorial.mindmap.policies.handles;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gef.mvc.domain.IDomain;
import org.eclipse.gef.mvc.parts.IRootPart;
import org.eclipse.gef.mvc.parts.IVisualPart;
import org.eclipse.gef.mvc.viewer.IViewer;

import com.itemis.gef4.tutorial.mindmap.operations.DeleteMindMapNodeOperation;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapPart;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class DeleteMindMapNodeOnClickPolicy extends AbstactHandleClickPolicy {

	@Override
	public void click(MouseEvent e) {

		IVisualPart<Node, ? extends Node> key = getAnchoredPart();
		
		
		IVisualPart<Node, ? extends Node> targetPart = key;
		if (targetPart instanceof MindMapNodePart) {
			// delete the part
			MindMapPart parent = (MindMapPart) targetPart.getParent();
			
			DeleteMindMapNodeOperation op = new DeleteMindMapNodeOperation(parent, (MindMapNodePart) targetPart);
			
			try {

				IRootPart<Node, ? extends Node> root = targetPart.getRoot();
				IViewer<Node> viewer = root.getViewer();
				IDomain<Node> domain = viewer.getDomain();
				domain.execute(op, null);
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			}

		}

	}

}
