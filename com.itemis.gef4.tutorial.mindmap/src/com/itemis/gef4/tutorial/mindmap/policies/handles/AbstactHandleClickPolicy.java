package com.itemis.gef4.tutorial.mindmap.policies.handles;

import java.util.Set;

import org.eclipse.gef.common.collections.ObservableSetMultimap;
import org.eclipse.gef.mvc.fx.policies.IFXOnClickPolicy;
import org.eclipse.gef.mvc.parts.IVisualPart;
import org.eclipse.gef.mvc.policies.AbstractInteractionPolicy;

import javafx.scene.Node;

public abstract class AbstactHandleClickPolicy extends AbstractInteractionPolicy<Node> implements IFXOnClickPolicy  {

	public AbstactHandleClickPolicy() {
		super();
	}

	protected IVisualPart<Node, ? extends Node> getAnchoredPart() {
		IVisualPart<Node, ? extends Node> host = getHost();  // the DeleteHandlerPart
		ObservableSetMultimap<IVisualPart<Node, ? extends Node>, String> anchorages = host.getAnchoragesUnmodifiable(); // get the registered anchorages - which is only the MindMapNodePart
		Set<IVisualPart<Node, ? extends Node>> keySet = anchorages.keySet(); // and it is the key ...
		IVisualPart<Node, ? extends Node> key = keySet.iterator().next(); // ... so we retrieve it from the key set
		return key;
	}

}