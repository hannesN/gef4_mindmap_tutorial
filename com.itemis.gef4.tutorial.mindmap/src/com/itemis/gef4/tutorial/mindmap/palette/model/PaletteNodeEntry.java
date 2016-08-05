package com.itemis.gef4.tutorial.mindmap.palette.model;

import com.itemis.gef4.tutorial.mindmap.model.MindMapNode;

import javafx.scene.Node;

public class PaletteNodeEntry {

	private final String label;
	
	private final Node icon;
	
	private final MindMapNode content;

	public PaletteNodeEntry(String label, Node icon, MindMapNode content) {
		super();
		this.label = label;
		this.icon = icon;
		this.content = content;
	}

	public Node getVisual() {
		return icon;
	}
	
	public MindMapNode getContent() {
		return content;
	}
	
	public String getLabel() {
		return label;
	}
	
}
