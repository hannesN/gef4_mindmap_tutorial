package com.itemis.gef4.tutorial.mindmap.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 * Abstract class providing the {@link PropertyChangeSupport} functionlity
 * @author hniederhausen
 *
 */
public class AbstractMindMapModel implements Serializable {
	private static final long serialVersionUID = 5583209800172806088L;

	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
	
	
	

}
