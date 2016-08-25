package com.itemis.gef4.tutorial.mindmap.models;

import org.eclipse.gef.mvc.parts.IContentPart;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.scene.control.ToggleButton;

/**
 * The {@link ItemCreationModel} is used to configure the creation actions in the Viewer.
 * @author hniederhausen
 *
 */

public class ItemCreationModel {
	public enum Type {
		None,
		Node,
		Connection
	}

	/**
	 * The kind of item to create
	 */
	private SimpleObjectProperty<ItemCreationModel.Type> typeProperty;
	
	/**
	 * The button, which configured the current state
	 */
	private ToggleButton pressedButton;
	
	private SimpleObjectProperty<IContentPart<?, ?>> sourceProperty;
	
	public ItemCreationModel() {
		typeProperty = new SimpleObjectProperty<ItemCreationModel.Type>(this, "type", Type.None);
		sourceProperty = new SimpleObjectProperty<IContentPart<?,?>>(this, "source", null);
	}
	
	public void setType(Type type) {
		typeProperty.set(type);
	}
	
	public Type getType() {
		return typeProperty.getValue();
	}
	
	public ObservableObjectValue<Type> getTypeProperty() {
		return typeProperty;
	}
	
	public void setPressedButton(ToggleButton pressedButton) {
		this.pressedButton = pressedButton;
	}
	
	public void clearSettings() {
		if (pressedButton!=null  && pressedButton.isSelected()) {
			pressedButton.setSelected(false);
		}
		pressedButton = null;
		sourceProperty.setValue(null);
		setType(Type.None);
	}
	
	public void setSource(IContentPart<?, ?> source) {
		sourceProperty.setValue(source);
	}
	
	public IContentPart<?, ?> getSource() {
		return sourceProperty.get();
	}
	
	public SimpleObjectProperty<IContentPart<?, ?>> getSourceProperty() {
		return sourceProperty;
	}
}
