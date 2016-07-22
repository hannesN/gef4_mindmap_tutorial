package com.itemis.gef4.tutorial.mindmap.palette.model;

import javafx.scene.Node;

public class PaletteEntry {

	private final String label;
	
	private final Node visual;

	public PaletteEntry(String label, Node visual) {
		super();
		this.label = label;
		this.visual = visual;
	}

	public Node getVisual() {
		return visual;
	}
	
	public String getLabel() {
		return label;
	}
	
}
