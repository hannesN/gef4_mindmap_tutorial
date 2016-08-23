/**
 * 
 */
package com.itemis.gef4.tutorial.mindmap.models;

import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import javafx.scene.text.Text;

/**
 * @author hniederhausen
 *
 */
public class InlineEditableTextField extends AbstractInlineEditableField {

	public InlineEditableTextField(String propertyName, Text readOnlyNode) {
		super(propertyName, readOnlyNode);
	}
	
	@Override
	public void setEditorNode(Node editorNode) {
		if (editorNode==null || editorNode instanceof TextInputControl)
			super.setEditorNode(editorNode);
		else 
			throw new IllegalArgumentException("Only TextInputControls are allowed");
	}
	
	@Override
	public TextInputControl getEditorNode() {
		return (TextInputControl) super.getEditorNode();
	}
	
	@Override
	public Text getReadOnlyNode() {
		return (Text) super.getReadOnlyNode();
	}

	/* (non-Javadoc)
	 * @see com.itemis.gef4.tutorial.mindmap.models.InlineEditableField#getNewValue()
	 */
	@Override
	public Object getNewValue() {
		if (getEditorNode()!=null)
			return getEditorNode().getText();
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.itemis.gef4.tutorial.mindmap.models.InlineEditableField#getOldValue()
	 */
	@Override
	public Object getOldValue() {
		return getReadOnlyNode().getText();
	}

}
