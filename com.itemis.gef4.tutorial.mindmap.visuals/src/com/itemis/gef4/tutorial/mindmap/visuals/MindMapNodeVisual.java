package com.itemis.gef4.tutorial.mindmap.visuals;

import org.eclipse.gef.fx.nodes.GeometryNode;
import org.eclipse.gef.geometry.planar.RoundedRectangle;

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

	private StackPane stackPane;
	
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
				calculateBoundsAndResize();
			}
		});

		descriptionText = new Text();
		descriptionText.setTextOrigin(VPos.TOP);
		descriptionText.setWrappingWidth(100);
		// resize shape when the label changes
		descriptionText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				calculateBoundsAndResize();
			}
		});

		labelGroup.getChildren().addAll(titleText, descriptionText);

		stackPane = new StackPane();
		stackPane.getChildren().addAll(shape, labelGroup);

		
		getChildren().addAll(stackPane);
		
	}

	public Node getShape() {
		return shape;
	}
	
	private void calculateBoundsAndResize() {
		System.out.println("Calculate Bound Size"+labelGroup.getLayoutBounds().getWidth());
		stackPane.resize(labelGroup.getLayoutBounds().getWidth() + PADDING,
				labelGroup.getLayoutBounds().getHeight() + PADDING);
	}

	public void resizeShape(double width, double height) {
		System.out.println("Resizing to "+width);
		descriptionText.setWrappingWidth(width-30);
		shape.resize(width, height);
	}

	public void setColor(Color color) {
		shape.setFill(color);
	}

	public void setTitle(String title) {
		this.titleText.setText(title);
	}

	public void setDescription(String description) {
		this.descriptionText.setText(description);
	}

	public Text getTitleText() {
		return titleText;
	}

	public Text getDescriptionText() {
		return descriptionText;
	}

	public double getMinimumHeight() {
		return descriptionText.getBoundsInLocal().getHeight() + titleText.getBoundsInLocal().getHeight();
	}

	public double getMinimumWidth() {
		System.out.println("Mimimum Width: "+titleText.getBoundsInLocal().getWidth());
		return titleText.getBoundsInLocal().getWidth();
	}

	public Node startEditing(Node readOnlyField) {
		TextInputControl inputControl = null;
		int idx = 0;
		double width = shape.getBoundsInLocal().getWidth();
		double height = titleText.getBoundsInLocal().getHeight();
		if (readOnlyField == titleText) {
			inputControl = new TextField(titleText.getText());
			// TODO listeners to extend the size

		} else if (readOnlyField == descriptionText) {
			inputControl = new TextArea(descriptionText.getText());
			idx = 1;
			height = shape.getBoundsInLocal().getHeight();

			// TODO listeners
		} else {
			throw new IllegalArgumentException("Invalid entry");
		}
		
		inputControl.setPrefSize(width, height);

		ObservableList<Node> children = labelGroup.getChildren();
		children.remove(idx);
		children.add(idx, inputControl);

		return inputControl;
	}

	public void endEditing(Node readOnlyField) {
		int idx = 0;
		if (readOnlyField == titleText) {
		} else if (readOnlyField == descriptionText) {
			idx = 1;
		} else {
			throw new IllegalArgumentException("Invalid entry");
		}

		ObservableList<Node> children = labelGroup.getChildren();
		children.remove(idx);
		children.add(idx, readOnlyField);
	}
}
