package com.itemis.gef4.tutorial.mindmap.models;

import javafx.event.EventTarget;
import javafx.scene.Node;

public interface IInlineEditableField {

	String getPropertyName();

	Node getReadOnlyNode();

	boolean isTarget(EventTarget target);

	void setEditorNode(Node editorNode);

	Node getEditorNode();

	Object getNewValue();

	Object getOldValue();

}