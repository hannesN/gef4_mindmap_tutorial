package com.itemis.gef4.tutorial.mindmap.palette.parts;

import java.util.List;

import org.eclipse.gef4.common.collections.CollectionUtils;
import org.eclipse.gef4.mvc.fx.parts.AbstractFXContentPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;

import com.google.common.collect.SetMultimap;
import com.itemis.gef4.tutorial.mindmap.palette.model.PaletteModel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class PaletteModelPart extends AbstractFXContentPart<VBox> {

	@Override
	public PaletteModel getContent() {
		return (PaletteModel) super.getContent();
	}

	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return CollectionUtils.emptySetMultimap();
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		return getContent().getEntries();
	}

	@Override
	protected VBox createVisual() {
		VBox vbox = new VBox();
		vbox.setPickOnBounds(true);
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(10);
		vbox.setAlignment(Pos.TOP_LEFT);

		return vbox;
	}

	@Override
	protected void doRefreshVisual(VBox visual) {
	}

	// thanks MVC Logo example! ..

	@Override
	protected void addChildVisual(IVisualPart<Node, ? extends Node> child, int index) {
		// wrap child.visual in group so that it is not resizable
		getVisual().getChildren().add(index, new Group(child.getVisual()));
	}

	@Override
	protected void removeChildVisual(IVisualPart<Node, ? extends Node> child, int index) {
		Node removed = getVisual().getChildren().remove(index);
		if (!(removed instanceof Group) || ((Group) removed).getChildren().get(0) != child.getVisual()) {
			throw new IllegalStateException("Child visual was not removed!");
		}
	}
}