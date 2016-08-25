package com.itemis.gef4.tutorial.mindmap.policies;

import java.util.Set;

import org.eclipse.gef.common.collections.ObservableSetMultimap;
import org.eclipse.gef.mvc.fx.policies.IFXOnClickPolicy;
import org.eclipse.gef.mvc.parts.IContentPart;
import org.eclipse.gef.mvc.parts.IRootPart;
import org.eclipse.gef.mvc.parts.IVisualPart;
import org.eclipse.gef.mvc.policies.AbstractInteractionPolicy;
import org.eclipse.gef.mvc.policies.DeletionPolicy;

import com.google.common.reflect.TypeToken;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class DeleteMindMapNodeOnClickPolicy extends AbstractInteractionPolicy<Node> implements IFXOnClickPolicy {

	@SuppressWarnings("serial")
	@Override
	public void click(MouseEvent e) {

		IVisualPart<Node, ? extends Node> host = getHost();  // the DeleteHandlerPart
		ObservableSetMultimap<IVisualPart<Node, ? extends Node>, String> anchorages = host.getAnchoragesUnmodifiable(); // get the registered anchorages - which is only the MindMapNodePart
		Set<IVisualPart<Node, ? extends Node>> keySet = anchorages.keySet(); // and it is the key ...
		IVisualPart<Node, ? extends Node> key = keySet.iterator().next(); // ... so we retrieve it from the key set
		
		
		IVisualPart<Node, ? extends Node> targetPart = key;
		if (targetPart instanceof IContentPart) {
			// delete the part
			IRootPart<Node, ? extends Node> root = targetPart.getRoot();
			DeletionPolicy<Node> policy = root.getAdapter(new TypeToken<DeletionPolicy<Node>>() {
			});
			if (policy != null) {
				init(policy);
				policy.delete((IContentPart<Node, ? extends Node>) targetPart);
				commit(policy);
			}
		}

	}

}
