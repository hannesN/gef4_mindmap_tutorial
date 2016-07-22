package com.itemis.gef4.tutorial.mindmap.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * A mindmap is the container of all our nodes and connections
 * 
 * @author hniederhausen
 *
 */
public class MindMap extends AbstractMindMapModel {
	
	private static final long serialVersionUID = 4667064215236604843L;


	public static final String PROP_NODES = "nodes";
	
	
	private List<MindMapNode> nodes = Lists.newArrayList();
	
	
	public List<MindMapNode> getNodes() {
		return nodes;
	}
	
	public void addNode(MindMapNode node) {
		nodes.add(node);
		pcs.firePropertyChange(PROP_NODES, null, node);
	}

	public void removeNode(MindMapNode node) {
		nodes.remove(node);
		pcs.firePropertyChange(PROP_NODES, node, null);
	}

	public Collection<? extends Object> getConnections() {
		Set<Connection> connSet = Sets.newHashSet();
		for (MindMapNode node : nodes) {
			connSet.addAll(node.getOutgoingConnections());
		}
		return Lists.newArrayList(connSet);
	}
	
}
