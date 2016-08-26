package com.itemis.gef4.tutorial.mindmap.parts.feedback;

import java.util.List;
import java.util.Map;

import org.eclipse.gef.mvc.behaviors.IBehavior;
import org.eclipse.gef.mvc.parts.IFeedbackPart;
import org.eclipse.gef.mvc.parts.IFeedbackPartFactory;
import org.eclipse.gef.mvc.parts.IVisualPart;

import com.google.common.collect.Lists;

import javafx.scene.Node;

public class ConnectionFeedbackPartFactory implements IFeedbackPartFactory<Node> {

	@Override
	public List<IFeedbackPart<Node, ? extends Node>> createFeedbackParts(
			List<? extends IVisualPart<Node, ? extends Node>> targets, IBehavior<Node> contextBehavior,
			Map<Object, Object> contextMap) {

		List<IFeedbackPart<Node, ? extends Node>> parts = Lists.newArrayList();
		parts.add(new CreateConnectionFeedbackPart());

		return parts;
	}

}
