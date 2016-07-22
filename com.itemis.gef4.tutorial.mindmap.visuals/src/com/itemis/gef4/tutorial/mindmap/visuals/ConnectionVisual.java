package com.itemis.gef4.tutorial.mindmap.visuals;

import org.eclipse.gef4.fx.nodes.Connection;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class ConnectionVisual extends Connection {

	public static class ArrowHead extends Polygon {
		public ArrowHead() {
			super(0, 0, 10, 3, 10, -3);
		}
	}

	public ConnectionVisual() {
		ArrowHead endDecoration = new ArrowHead();
		endDecoration.setFill(Color.BLACK);
		setEndDecoration(endDecoration);
	}
	
}
