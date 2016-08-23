package com.itemis.gef4.tutorial.mindmap.model;

import java.io.Serializable;
import java.util.List;

import org.eclipse.gef.geometry.planar.Rectangle;

import com.google.common.collect.Lists;

import javafx.scene.paint.Color;

/**
 * 
 * 
 * @author hniederhausen
 *
 */
public class MindMapNode extends AbstractMindMapModel implements Serializable {

	private static final long serialVersionUID = 1589923629517266408L;

	public static final String PROP_TITLE = "title";
	public static final String PROP_DESCRIPTION = "description";
	public static final String PROP_COLOR = "color";
	public static final String PROP_BOUNDS = "bounds";
	
	public static final String PROP_INCOMING_CONNECTIONS = "incomingConnections";
	public static final String PROP_OUTGOGING_CONNECTIONS = "outgoingConnections";

	/**
	 * The title of the node
	 */
	private String title;

	/**
	 * he description of the node, which is optional
	 */
	private String description;

	/**
	 * The background color of the node
	 */
	private Color color;
	
	/**
	 * The size and position of the visual representation
	 */
	private Rectangle bounds;

	private List<Connection> incomingConnections = Lists.newArrayList();
	private List<Connection> outgoingConnections = Lists.newArrayList();

	public String getTite() {
		return title;
	}

	public void setTite(String title) {
		pcs.firePropertyChange(PROP_TITLE, this.title, (this.title = title));
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		pcs.firePropertyChange(PROP_DESCRIPTION, this.description, (this.description = description));
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		pcs.firePropertyChange(PROP_COLOR, this.color, (this.color = color));
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void setBounds(Rectangle bounds) {
		pcs.firePropertyChange(PROP_BOUNDS, this.bounds, (this.bounds = bounds.getCopy()));
	}
	
	public List<Connection> getOutgoingConnections() {
		return outgoingConnections;
	}

	public void addOutgoingConnection(Connection conn) {
		outgoingConnections.add(conn);
		pcs.firePropertyChange(PROP_OUTGOGING_CONNECTIONS, null, conn);
	}

	public void removeOutgoingConnection(Connection conn) {
		outgoingConnections.remove(conn);
		pcs.firePropertyChange(PROP_OUTGOGING_CONNECTIONS, conn, null);
	}
	
	public List<Connection> getIncomingConnections() {
		return incomingConnections;
	}

	public void addIncomingConnection(Connection conn) {
		incomingConnections.add(conn);
		pcs.firePropertyChange(PROP_INCOMING_CONNECTIONS, null, conn);
	}

	public void removeIncomingConnection(Connection conn) {
		incomingConnections.remove(conn);
		pcs.firePropertyChange(PROP_INCOMING_CONNECTIONS, conn, null);
	}

}
