package com.itemis.gef4.tutorial.mindmap.parts;

import java.util.Map;

import org.eclipse.gef4.mvc.behaviors.IBehavior;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IContentPartFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.itemis.gef4.tutorial.mindmap.model.Connection;
import com.itemis.gef4.tutorial.mindmap.model.MindMap;
import com.itemis.gef4.tutorial.mindmap.model.MindMapNode;

import javafx.scene.Node;


/**
 * The factory creates the part instances based on the given content type.
 * @author hniederhausen
 *
 */
public class MindMapContentsFactory implements IContentPartFactory<Node> {

	@Inject
	private Injector injector;
	
	@Override
	public IContentPart<Node, ? extends Node> createContentPart(Object content, IBehavior<Node> contextBehavior,
			Map<Object, Object> contextMap) {

		
		if (content==null)
			throw new IllegalArgumentException("Content must not be null!");
		
		if (content instanceof MindMap) {
			return injector.getInstance(MindMapPart.class);
		} else if (content instanceof MindMapNode) {
			return injector.getInstance(MindMapNodePart.class);
		} else if (content instanceof Connection) {
			return injector.getInstance(ConnectionPart.class);
		} else {
			throw new IllegalArgumentException("Uknwon content type <"+content.getClass().getName()+">");
		}
		
	}

}
