package com.itemis.gef4.tutorial.mindmap.model;

import org.eclipse.gef4.geometry.planar.Rectangle;

import javafx.scene.paint.Color;

public class MindMapFactory {

	public MindMap createExample() {
		MindMap mindMap = new MindMap();
		
		MindMapNode center = new MindMapNode();
		center.setTite("The Core Idea");
		center.setDescription("This is my Core idea");
		center.setColor(Color.GREENYELLOW);
		center.setBounds(new Rectangle(250,  50, 100, 100));
		
		mindMap.addNode(center);
		
		MindMapNode child = null;
		for (int i=0; i<5; i++) {
			child = new MindMapNode();
			child.setTite("Association #"+i);
			child.setDescription("I just realized, this is related to the core idea!");
			child.setColor(Color.ALICEBLUE);
			
			child.setBounds(new Rectangle(50+(i*200),  250, 100, 100));
			mindMap.addNode(child);
			
			Connection conn = new Connection();
			conn.connect(center, child);
		}
		
		MindMapNode child2 = new MindMapNode();
		child2.setTite("Association #4-2");
		child2.setDescription("I just realized, this is related to the last idea!");
		child2.setColor(Color.LIGHTGRAY);
		child2.setBounds(new Rectangle(250,  550, 100, 100));
		mindMap.addNode(child2);
		
		Connection conn = new Connection();
		conn.connect(child, child2);
		
		return mindMap;
		
	}
	
}
