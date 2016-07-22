package com.itemis.gef4.tutorial.mindmap.model;

import java.util.List;

import org.eclipse.gef4.geometry.planar.Point;

import com.google.common.collect.Lists;


/**
 * The connection class represents a onnection between two nodes.
 * 
 * @author hniederhausen
 *
 */
public class Connection extends AbstractMindMapModel {
	private static final long serialVersionUID = -8600969251499506881L;

		public static final String PROP_BEND_POINTS = "bendPoints";
		
		private MindMapNode source;
		private MindMapNode target;
		private boolean connected;
		
		List<Point> bendPoints = Lists.newArrayList();
		
		
		public MindMapNode getSource() {
			return source;
		}
		
		public void setSource(MindMapNode source) {
			this.source = source;
		}
		
		public MindMapNode getTarget() {
			return target;
		}
		
		public void setTarget(MindMapNode target) {
			this.target = target;
		}
		
		public void connect(MindMapNode source, MindMapNode target) {
			if (source == null || target == null || source == target) {
				throw new IllegalArgumentException();
			}
			disconnect();
			this.source = source;
			this.target = target;
			reconnect();
		}
		
		public void disconnect() {
			if (connected) {
				source.removeOutgoingConnection(this);
				target.removeIncomingConnection(this);
				connected = false;
			}
		}
		
		public void reconnect() {
			if (!connected) {
				source.addOutgoingConnection(this);
				target.addIncomingConnection(this);
				connected = true;
			}
		}
}
