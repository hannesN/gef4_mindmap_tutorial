package com.itemis.gef4.tutorial.mindmap.palette.parts;

import java.util.Collections;
import java.util.List;

import org.eclipse.gef4.common.collections.CollectionUtils;
import org.eclipse.gef4.mvc.fx.parts.AbstractFXContentPart;

import com.google.common.collect.SetMultimap;
import com.itemis.gef4.tutorial.mindmap.palette.model.PaletteEntry;

import javafx.scene.Group;

public class PaletteEntryPart extends AbstractFXContentPart<Group>{

	@Override
	public PaletteEntry getContent() {
		return (PaletteEntry) super.getContent();
	}
	
	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return CollectionUtils.emptySetMultimap();
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		return Collections.emptyList();
	}

	@Override
	protected Group createVisual() {
		Group group = new Group();
		group.getChildren().add(getContent().getVisual());
		return group;
	}

	@Override
	protected void doRefreshVisual(Group visual) {
	}

	
}
