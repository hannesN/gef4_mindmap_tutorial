package com.itemis.gef4.tutorial.mindmap.model;

import com.itemis.gef4.tutorial.mindmap.parts.IDirectEditablePart;

import javafx.scene.Node;

public class EditModel {

	private Object oldValue;
	
	private IDirectEditablePart host;
	
	private Node field;

	public Object getOldValue() {
		return oldValue;
	}

	public void setOldValue(Object oldValue) {
		this.oldValue = oldValue;
	}

	public IDirectEditablePart getHost() {
		return host;
	}

	public void setHost(IDirectEditablePart host) {
		this.host = host;
	}

	public Node getField() {
		return field;
	}

	public void setField(Node field) {
		this.field = field;
		this.oldValue = this.host.getEntryValue(field);
	}

	public void cancel() {
		this.host.cancelEditing(this.field);
		this.host=null;
		this.field=null;
		this.oldValue=null;
	}
	
	
	
}
