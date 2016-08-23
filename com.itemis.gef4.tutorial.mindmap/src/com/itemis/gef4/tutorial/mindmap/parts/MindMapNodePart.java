package com.itemis.gef4.tutorial.mindmap.parts;

import java.util.Collections;
import java.util.List;

import org.eclipse.gef.geometry.planar.AffineTransform;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.parts.AbstractFXContentPart;
import org.eclipse.gef.mvc.fx.policies.FXTransformPolicy;
import org.eclipse.gef.mvc.parts.IResizableContentPart;
import org.eclipse.gef.mvc.parts.ITransformableContentPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.itemis.gef4.tutorial.mindmap.model.MindMapNode;
import com.itemis.gef4.tutorial.mindmap.models.AbstractInlineEditableField;
import com.itemis.gef4.tutorial.mindmap.models.IInlineEditableField;
import com.itemis.gef4.tutorial.mindmap.models.InlineEditableTextField;
import com.itemis.gef4.tutorial.mindmap.visuals.MindMapNodeVisual;

import javafx.scene.Node;
import javafx.scene.transform.Affine;

public class MindMapNodePart extends AbstractFXContentPart<MindMapNodeVisual> implements ITransformableContentPart<Node, MindMapNodeVisual> , 
				IResizableContentPart<Node, MindMapNodeVisual>, IInlineEditablePart {

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
		Rectangle rec = node.getBounds();
		
		
		visual.setTitle(node.getTite());
		visual.setDescription(node.getDescription());
		visual.setColor(node.getColor());

		visual.resizeShape(rec.getWidth(), rec.getHeight());
		
		Affine affine = getAdapter(FXTransformPolicy.TRANSFORM_PROVIDER_KEY).get();
		affine.setTx(rec.getX());
		affine.setTy(rec.getY());

	}
	
	
	@Override
	public void transformContent(AffineTransform transform) {
		Rectangle bounds = getContent().getBounds();
		bounds = bounds.getTranslated(transform.getTranslateX(), transform.getTranslateY());
		getContent().setBounds(bounds);
	}


	@Override
	public void resizeContent(Dimension size) {
		getContent().getBounds().setSize(size);		
	}

	@Override
	public List<AbstractInlineEditableField> getEditableFields() {
		
		List<AbstractInlineEditableField> fields = Lists.newArrayList();
		
		fields.add(new InlineEditableTextField("title", getVisual().getTitleText()));
		fields.add(new InlineEditableTextField("description", getVisual().getDescriptionText()));
		
		return fields;
	}

	@Override
	public void startEditing(IInlineEditableField field) {

		Node editor = getVisual().startEditing(field.getReadOnlyNode());
		field.setEditorNode(editor);
		
	}

	@Override
	public void endEditing(IInlineEditableField field) {
		getVisual().endEditing(field.getReadOnlyNode());
		field.setEditorNode(null);
		doRefreshVisual(getVisual());
	}

	@Override
	public void submitEditing(IInlineEditableField field, Object value) {
		if ("title".equals(field.getPropertyName())) {
			getContent().setTite((String) value);
		} else if ("description".equals(field.getPropertyName())) {
			getContent().setDescription((String) value);
		}
		doRefreshVisual(getVisual());
	}
}
