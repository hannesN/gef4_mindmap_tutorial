package com.itemis.gef4.tutorial.mindmap.palette.model;

import java.util.List;

import com.google.common.collect.Lists;
import com.itemis.gef4.tutorial.mindmap.visuals.MindMapNodeVisual;


/**
 * Our model for the palette. No propertychange listeners here, because, nothing will change.
 * 
 * @author hniederhausen
 *
 */
public class PaletteModel {

	private static PaletteEntry NODE_ENTRY = new PaletteEntry("New Node", new MindMapNodeVisual());
	
	
	
	public List<PaletteEntry> getEntries() {
		return Lists.newArrayList(NODE_ENTRY);
	}
	
}
