package com.itemis.gef4.tutorial.mindmap.policies;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gef.mvc.fx.policies.IFXOnClickPolicy;
import org.eclipse.gef.mvc.policies.AbstractInteractionPolicy;

import com.itemis.gef4.tutorial.mindmap.operations.ChangeMindMapNodeColorOperation;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ShowContextMenuOnClickPolicy extends AbstractInteractionPolicy<Node> implements IFXOnClickPolicy {

	@Override
	public void click(MouseEvent e) {
		
		if (!e.isSecondaryButtonDown()) {
			return;
		}
		
		
		Menu colorMenu = new Menu("Change Color");
		Color[] colors = {Color.ALICEBLUE, Color.BURLYWOOD, Color.YELLOW, Color.RED, Color.CHOCOLATE, Color.GREENYELLOW, Color.WHITE};
		String[] names = {"ALICEBLUE", "BURLYWOOD", "YELLOW", "RED", "CHOCOLATE", "GREENYELLOW", "WHITE"};
		
		for (int i=0; i<colors.length; i++) {
			colorMenu.getItems().add(getMenuItem(names[i], colors[i]));
		}
		
		
		ContextMenu ctxMenu = new ContextMenu(colorMenu);
		ctxMenu.show((Node) e.getTarget(), e.getScreenX(), e.getScreenY());
	}
	
	private MenuItem getMenuItem(String name, Color color) {
		Rectangle graphic = new Rectangle(20, 20);
		graphic.setFill(color);
		graphic.setStroke(Color.BLACK);
		MenuItem item = new MenuItem(name, graphic);
		item.setOnAction((e) -> submitColor(color));
		return item;
	}
	
	private void submitColor(Color color) {
		if (getHost() instanceof MindMapNodePart) {
			MindMapNodePart host = (MindMapNodePart) getHost();
			
			ChangeMindMapNodeColorOperation op = new ChangeMindMapNodeColorOperation(host, color);
			
			try {
				host.getRoot().getViewer().getDomain().execute(op, null);
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	
	
}
