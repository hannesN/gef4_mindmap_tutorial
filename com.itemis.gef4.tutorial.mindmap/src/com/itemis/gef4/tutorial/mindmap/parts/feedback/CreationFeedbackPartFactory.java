package com.itemis.gef4.tutorial.mindmap.parts.feedback;

import java.util.List;
import java.util.Map;

import org.eclipse.gef.mvc.behaviors.IBehavior;
import org.eclipse.gef.mvc.parts.IFeedbackPart;
import org.eclipse.gef.mvc.parts.IFeedbackPartFactory;
import org.eclipse.gef.mvc.parts.IRootPart;
import org.eclipse.gef.mvc.parts.IVisualPart;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;

import javafx.scene.Node;

public class CreationFeedbackPartFactory implements IFeedbackPartFactory<Node> {

	@Inject
	Injector injector;
	
	@Override
	public List<IFeedbackPart<Node, ? extends Node>> createFeedbackParts(
			List<? extends IVisualPart<Node, ? extends Node>> targets, IBehavior<Node> contextBehavior,
			Map<Object, Object> contextMap) {
		
		List<IFeedbackPart<Node, ? extends Node>> parts = Lists.newArrayList();
		
		if (targets.isEmpty())
			return parts; // shouldn't happen, just to be sure
		
		// we just expect one target
		IVisualPart<Node, ? extends Node> target = targets.get(0);
		
		if (target instanceof IRootPart) {
			// if the target is a rootpart, we want to create a CreateNodeFeedbackPart
			CreateNodeFeedbackPart part = new CreateNodeFeedbackPart();
			injector.injectMembers(part);
			parts.add(part);
		} else if (target instanceof MindMapNodePart) {
			// a MindMapNode target is the source of a connection so we create the connection feedback
			CreateConnectionFeedbackPart part = new CreateConnectionFeedbackPart();
			injector.injectMembers(part);
			parts.add(part);
		}

		return parts;
	}

}
