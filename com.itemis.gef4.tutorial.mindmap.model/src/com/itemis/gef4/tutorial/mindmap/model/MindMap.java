package com.itemis.gef4.tutorial.mindmap.model;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * A mindmap is the container of all our nodes and connections
 * 
 * @author hniederhausen
 *
 */
public class MindMap extends AbstractMindMapModel {
	
	private static final long serialVersionUID = 4667064215236604843L;

	public static final String PROP_CHILD_ELEMENTS = "childElements";
	
	private List<AbstractMindMapModel> childElements = Lists.newArrayList();
	
	public List<AbstractMindMapModel> getChildElements() {
		return childElements;
	}
	
	public void addChildElement(AbstractMindMapModel node) {
		childElements.add(node);
		pcs.firePropertyChange(PROP_CHILD_ELEMENTS, null, node);
	}
	
	public void addChildElement(AbstractMindMapModel node, int idx) {
		childElements.add(idx, node);
		pcs.firePropertyChange(PROP_CHILD_ELEMENTS, null, node);
	}

	public void removeChildElement(AbstractMindMapModel node) {
		childElements.remove(node);
		pcs.firePropertyChange(PROP_CHILD_ELEMENTS, node, null);
	}

}
