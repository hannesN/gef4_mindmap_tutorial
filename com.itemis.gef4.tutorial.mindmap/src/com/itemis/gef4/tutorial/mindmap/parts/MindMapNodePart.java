package com.itemis.gef4.tutorial.mindmap.parts;

import java.util.Collections;
import java.util.List;

import org.eclipse.gef4.geometry.planar.AffineTransform;
import org.eclipse.gef4.geometry.planar.Rectangle;
import org.eclipse.gef4.mvc.fx.parts.AbstractFXContentPart;
import org.eclipse.gef4.mvc.fx.policies.FXTransformPolicy;
import org.eclipse.gef4.mvc.parts.ITransformableContentPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.itemis.gef4.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef4.tutorial.mindmap.visuals.MindMapNodeVisual;

import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;

public class MindMapNodePart extends AbstractFXContentPart<MindMapNodeVisual> implements ITransformableContentPart<Node, MindMapNodeVisual>, IDirectEditablePart {

	@Override
	public MindMapNode getContent() {
		return (MindMapNode) super.getContent();
	}
	
	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		return Collections.emptyList();
	}

	@Override
	protected MindMapNodeVisual createVisual() {
		return new MindMapNodeVisual();
	}

	@Override
	protected void doRefreshVisual(MindMapNodeVisual visual) {
		
		MindMapNode node = getContent();
		
		visual.setTitle(node.getTite());
		visual.setDescription(node.getDescription());
		visual.setColor(node.getColor());
		
		Rectangle rec = node.getBounds();
		
		Affine affine = getAdapter(FXTransformPolicy.TRANSFORM_PROVIDER_KEY).get();
		affine.setTx(rec.getX());
		affine.setTy(rec.getY());
		
		visual.getShape().resize(rec.getWidth(), rec.getHeight());
	}
	
	@Override
	public void transformContent(AffineTransform transform) {
		Rectangle bounds = getContent().getBounds();
		bounds = bounds.getTranslated(transform.getTranslateX(), transform.getTranslateY());
		getContent().setBounds(bounds);
	}

	@Override
	public List<Node> getEditableEntries() {
		MindMapNodeVisual visual = getVisual();
		return Lists.newArrayList(visual.getTitleText(), visual.getDescriptionText());
	}

	@Override
	public Node startEditing(Node entry) {
		return getVisual().startEditing(entry);
	}

	@Override
	public void cancelEditing(Node entry) {
		getVisual().endEditing(entry);		
	}

	@Override
	public void submitEditing(Node entry, Object newValue) {
		if (newValue instanceof String) {
			String contentValue = (String) newValue;
			if (entry==getVisual().getTitleText()) {
				getContent().setTite(contentValue);
			} else if (entry == getVisual().getDescriptionText()) {
				getContent().setDescription(contentValue);
			} else {
				throw new IllegalArgumentException("Invalid entry");
			}
		}
		doRefreshVisual(getVisual());
		getVisual().endEditing(entry);
		
	}

	@Override
	public Object getEntryValue(Node entry) {
		if (entry instanceof Text) {
			return ((Text) entry).getText();
		} else if (entry instanceof TextInputControl) {
			return ((TextInputControl) entry).getText();
		}
		throw new IllegalArgumentException("Only Text ot TextInputControls nodes are allowed");
	}
	
}
