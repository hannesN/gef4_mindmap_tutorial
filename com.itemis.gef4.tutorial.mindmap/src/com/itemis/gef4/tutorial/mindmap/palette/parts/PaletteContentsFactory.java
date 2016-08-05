package com.itemis.gef4.tutorial.mindmap.palette.parts;

import java.util.Map;

import org.eclipse.gef4.mvc.behaviors.IBehavior;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IContentPartFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.itemis.gef4.tutorial.mindmap.palette.model.PaletteNodeEntry;
import com.itemis.gef4.tutorial.mindmap.palette.model.PaletteModel;

import javafx.scene.Node;

public class PaletteContentsFactory implements IContentPartFactory<Node> {

	@Inject
	private Injector injector;
	
	@Override
	public IContentPart<Node, ? extends Node> createContentPart(Object content, IBehavior<Node> contextBehavior,
			Map<Object, Object> contextMap) {
		
		if (content==null)
			throw new IllegalArgumentException("Content must not be null!");
		
		if (content instanceof PaletteModel) {
			return injector.getInstance(PaletteModelPart.class);
		} else if (content instanceof PaletteNodeEntry) {
			return injector.getInstance(PaletteEntryPart.class);
		} else {
			throw new IllegalArgumentException("Uknwon content type <"+content.getClass().getName()+">");
		}
	}

}
