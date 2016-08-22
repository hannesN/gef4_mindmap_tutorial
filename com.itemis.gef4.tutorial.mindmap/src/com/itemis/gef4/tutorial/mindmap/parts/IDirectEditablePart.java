package com.itemis.gef4.tutorial.mindmap.parts;

import java.util.List;

import javafx.scene.Node;

/**
 * An interface specifying, that a part provides visuals for direct editing.
 * @author hniederhausen
 *
 */
public interface IDirectEditablePart {

	/**
	 * 
	 * @return a list of visuals, which can be directly edited
	 */
	public List<Node> getEditableEntries();

	/**
	 * Starts the editing of the entry, by exchanging the read only node with a suitable
	 * editor. 
	 * @param entry the original visual in the part
	 * @return the editor node
	 */
	public Node startEditing(Node entry);
	
	/**
	 * 
	 * @param entry the original or editor visual which value is demanded
	 * @return the content value for the entry 
	 */
	public Object getEntryValue(Node entry);
	
	/**
	 * Cancels the editing and switches back to the read only node.
	 * @param entry the original visual in the part
	 */
	public void cancelEditing(Node entry);
	
	/**
	 * Submits the new values from the editor and switches back to the read only editor
	 * 
	 * @param entry the original visual in the part
	 * @param newValue the new value to set
	 */
	public void submitEditing(Node entry, Object newValue);
}
