/**
 * 
 */
package com.itemis.gef4.tutorial.mindmap.models;

import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Describes a field of an IInlineEditablePart, which is actually editable.
 * 
 * @author hniederhausen
 *
 */
public abstract class AbstractInlineEditableField implements IInlineEditableField {

	/**
	 * The name of the field
	 */
	private final String propertyName;
	
	/**
	 * The original read only node in the parts visual
	 */
	private final Node readOnlyNode;
	
	/**
	 * The editor node, e.g. a {@link TextField} or {@link TextArea}
	 */
	private Node editorNode;
	
	public AbstractInlineEditableField(String propertyName, Node readOnlyNode) {
		super();
		this.propertyName = propertyName;
		this.readOnlyNode = readOnlyNode;
	}

	/* (non-Javadoc)
	 * @see com.itemis.gef4.tutorial.mindmap.models.IInlineEditableField#getPropertyName()
	 */
	@Override
	public String getPropertyName() {
		return propertyName;
	}
	
	/* (non-Javadoc)
	 * @see com.itemis.gef4.tutorial.mindmap.models.IInlineEditableField#getReadOnlyNode()
	 */
	@Override
	public Node getReadOnlyNode() {
		return readOnlyNode;
	}
	
	/* (non-Javadoc)
	 * @see com.itemis.gef4.tutorial.mindmap.models.IInlineEditableField#isTarget(javafx.event.EventTarget)
	 */
	@Override
	public boolean isTarget(EventTarget target) {
		return readOnlyNode==target;
	}
	
	/* (non-Javadoc)
	 * @see com.itemis.gef4.tutorial.mindmap.models.IInlineEditableField#setEditorNode(javafx.scene.Node)
	 */
	@Override
	public void setEditorNode(Node editorNode) {
		this.editorNode = editorNode;
	}
	
	/* (non-Javadoc)
	 * @see com.itemis.gef4.tutorial.mindmap.models.IInlineEditableField#getEditorNode()
	 */
	@Override
	public Node getEditorNode() {
		return editorNode;
	}
	
	/* (non-Javadoc)
	 * @see com.itemis.gef4.tutorial.mindmap.models.IInlineEditableField#getNewValue()
	 */
	@Override
	public abstract Object getNewValue();
	
	/* (non-Javadoc)
	 * @see com.itemis.gef4.tutorial.mindmap.models.IInlineEditableField#getOldValue()
	 */
	@Override
	public abstract Object getOldValue();
}
