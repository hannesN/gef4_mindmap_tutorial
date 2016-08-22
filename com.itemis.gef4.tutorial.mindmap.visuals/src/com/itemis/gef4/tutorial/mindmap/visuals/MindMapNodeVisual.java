package com.itemis.gef4.tutorial.mindmap.visuals;
import org.eclipse.gef4.fx.nodes.GeometryNode;
import org.eclipse.gef4.geometry.planar.RoundedRectangle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MindMapNodeVisual extends Group {
	
	protected static final double PADDING = 40;

	private Text titleText;
	
	private Text descriptionText;
	
	private GeometryNode<RoundedRectangle> shape;

	private VBox labelGroup;
	
	public MindMapNodeVisual() {
		shape = new GeometryNode<>(new RoundedRectangle(0, 0, 70, 30, 8, 8));
		shape.setFill(Color.LIGHTGREEN);
		shape.setStroke(Color.BLACK);

		labelGroup = new VBox(5);
		labelGroup.setPadding(new Insets(10, 20, 10, 20));
		
		// create label
		titleText = new Text();
		titleText.setTextOrigin(VPos.TOP);

		// resize shape when the label changes
		titleText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				double width = Math.max(120, titleText.getLayoutBounds().getWidth());
				System.out.println(width);
				descriptionText.setWrappingWidth(width);
				calculateBoundsAndResize();
			}
		});
		
		descriptionText = new Text();
		descriptionText.setTextOrigin(VPos.TOP);

		// resize shape when the label changes
		descriptionText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				calculateBoundsAndResize();
			}
		});

		
		labelGroup.getChildren().addAll(titleText, descriptionText);
		
		// stack label over shape
		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(shape, labelGroup);

		getChildren().addAll(stackPane);
	}
	
	
	private void calculateBoundsAndResize() {
		shape.resize(labelGroup.getLayoutBounds().getWidth() + PADDING,
				labelGroup.getLayoutBounds().getHeight() + PADDING);
	}
	
	/**
	 * Checks if the given text is a multiline text
	 * @param text a text element which is supposedly a child of this visual
	 * @return <code>true</code> if the given text is a multiline text (aka the description field)
	 */
	public boolean isMultiLine(Text text) {
		return (text==descriptionText);
	}
	
	public GeometryNode<RoundedRectangle> getShape() {
		return shape;
	}
	
	public void setColor(Color color) {
		shape.setFill(color);
	}


	public void setTitle(String title) {
		this.titleText.setText(title);
	}

	public Text getTitleText() {
		return titleText;
	}

	public void setDescription(String description) {
		this.descriptionText.setText(description);
		
	}
	
	public Text getDescriptionText() {
		return descriptionText;
	}


	public Node startEditing(Node entry) {
		TextInputControl inputControl = null;
		int idx = 0;
		if (entry==titleText) {
			inputControl = new TextField(titleText.getText());
			
			// TODO listeners to extend the size
			
		} else if (entry==descriptionText) {
			inputControl = new TextArea(descriptionText.getText());
			idx = 1;
			
			// TODO listeners
		} else {
			throw new IllegalArgumentException("Invalid entry");
		}
		
		ObservableList<Node> children = labelGroup.getChildren();
		children.remove(idx);
		children.add(idx, inputControl);
		
		return inputControl;
	}


	public void endEditing(Node entry) {
		int idx = 0;
		if (entry==titleText) {
		} else if (entry==descriptionText) {
			idx = 1;
		} else {
			throw new IllegalArgumentException("Invalid entry");
		}
		
		ObservableList<Node> children = labelGroup.getChildren();
		children.remove(idx);
		children.add(idx, entry);
	}
}
