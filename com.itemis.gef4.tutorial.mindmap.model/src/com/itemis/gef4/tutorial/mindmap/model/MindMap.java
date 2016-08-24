package com.itemis.gef4.tutorial.mindmap.model;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * A mindmap is the container of all our nodes and connections
 * 
 * @author hniederhausen
 *
 */
public class MindMap extends AbstractMindMapModel {
	
	private static final long serialVersionUID = 4667064215236604843L;


	public static final String PROP_NODES = "nodes";
	
	public static final String PROP_CONNECTIONS = "connections";
	
	private List<MindMapNode> nodes = Lists.newArrayList();
	
	private List<Connection> connections = Lists.newArrayList();
	
	
	public List<MindMapNode> getNodes() {
		return nodes;
	}
	
	public void addNode(MindMapNode node) {
		nodes.add(node);
		pcs.firePropertyChange(PROP_NODES, null, node);
	}
	
	public void addNode(MindMapNode node, int idx) {
		nodes.add(idx, node);
		pcs.firePropertyChange(PROP_NODES, null, node);
	}

	public void removeNode(MindMapNode node) {
		nodes.remove(node);
		pcs.firePropertyChange(PROP_NODES, node, null);
	}

	public Collection<? extends Object> getConnections() {
		return connections;
	}
	
	public void addConnection(Connection conn, int idx) {
		connections.add(idx, conn);
		pcs.firePropertyChange(PROP_CONNECTIONS, null, conn);
	}
	
	public void addConnection(Connection conn) {
		connections.add(conn);
		pcs.firePropertyChange(PROP_CONNECTIONS, null, conn);
	}
	
	public void removeConnection(Connection conn) {
		connections.remove(conn);
		pcs.firePropertyChange(PROP_CONNECTIONS, conn, null);
	}
}
