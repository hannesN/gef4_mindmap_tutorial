package com.itemis.gef4.tutorial.mindmap.visuals;
import org.eclipse.gef4.fx.nodes.GeometryNode;
import org.eclipse.gef4.geometry.planar.RoundedRectangle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MindMapNodeVisual extends Group {
	
	protected static final double PADDING = 40;

	private Text title;
	
	private Text description;
	
	private GeometryNode<RoundedRectangle> shape;

	private VBox labelGroup;
	
	public MindMapNodeVisual() {
		shape = new GeometryNode<>(new RoundedRectangle(0, 0, 70, 30, 8, 8));
		shape.setFill(Color.LIGHTGREEN);
		shape.setStroke(Color.BLACK);

		labelGroup = new VBox(5);
		labelGroup.setPadding(new Insets(10, 20, 10, 20));
		
		// create label
		title = new Text();
		title.setTextOrigin(VPos.TOP);

		// resize shape when the label changes
		title.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				double width = Math.max(120, title.getLayoutBounds().getWidth());
				System.out.println(width);
				description.setWrappingWidth(width);
				calculateBoundsAndResize();
			}
		});
		
		description = new Text();
		description.setTextOrigin(VPos.TOP);

		// resize shape when the label changes
		description.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				calculateBoundsAndResize();
			}
		});

		
		labelGroup.getChildren().addAll(title, description);
		
		// stack label over shape
		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(shape, labelGroup);

		getChildren().addAll(stackPane);
	}
	
	
	private void calculateBoundsAndResize() {
		
		
		
		shape.resize(labelGroup.getLayoutBounds().getWidth() + PADDING,
				labelGroup.getLayoutBounds().getHeight() + PADDING);
	}
	
	public GeometryNode<RoundedRectangle> getShape() {
		return shape;
	}
	
	public void setColor(Color color) {
		shape.setFill(color);
	}


	public void setTitle(String title) {
		this.title.setText(title);
	}


	public void setDescription(String description) {
		this.description.setText(description);
	}

	public double getMinimumHeight() {
		return Math.max(description.getBoundsInLocal().getHeight(), title.getBoundsInLocal().getHeight());
	}
	
	public double getMinimumWidth() {
		return Math.max(description.getBoundsInLocal().getWidth(), title.getBoundsInLocal().getWidth());
	}
	
}
