package com.itemis.gef4.tutorial.mindmap.models;

import javafx.scene.control.ToggleButton;

/**
 * The {@link ItemCreationModel} is used to configure the creation actions in the Viewer.
 * @author hniederhausen
 *
 */

public class ItemCreationModel {
	public enum Type {
		None,
		Node
	}

	/**
	 * The kind of item to create
	 */
	private Type type;
	
	/**
	 * The button, which configured the current state
	 */
	private ToggleButton pressedButton;
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setPressedButton(ToggleButton pressedButton) {
		this.pressedButton = pressedButton;
	}
	
	public void clearSettings() {
		if (pressedButton!=null  && pressedButton.isSelected()) {
			pressedButton.setSelected(false);
		}
		pressedButton = null;
		type=Type.None;
	}
	
	
	
}
