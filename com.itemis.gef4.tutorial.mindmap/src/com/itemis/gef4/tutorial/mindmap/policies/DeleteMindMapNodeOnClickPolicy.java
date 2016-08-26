package com.itemis.gef4.tutorial.mindmap.policies;

import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gef.common.collections.ObservableSetMultimap;
import org.eclipse.gef.mvc.domain.IDomain;
import org.eclipse.gef.mvc.fx.policies.IFXOnClickPolicy;
import org.eclipse.gef.mvc.parts.IRootPart;
import org.eclipse.gef.mvc.parts.IVisualPart;
import org.eclipse.gef.mvc.policies.AbstractInteractionPolicy;
import org.eclipse.gef.mvc.viewer.IViewer;

import com.itemis.gef4.tutorial.mindmap.operations.DeleteMindMapNodeOperation;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapPart;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class DeleteMindMapNodeOnClickPolicy extends AbstractInteractionPolicy<Node> implements IFXOnClickPolicy {

	@Override
	public void click(MouseEvent e) {

		IVisualPart<Node, ? extends Node> host = getHost();  // the DeleteHandlerPart
		ObservableSetMultimap<IVisualPart<Node, ? extends Node>, String> anchorages = host.getAnchoragesUnmodifiable(); // get the registered anchorages - which is only the MindMapNodePart
		Set<IVisualPart<Node, ? extends Node>> keySet = anchorages.keySet(); // and it is the key ...
		IVisualPart<Node, ? extends Node> key = keySet.iterator().next(); // ... so we retrieve it from the key set
		
		
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
