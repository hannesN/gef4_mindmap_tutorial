package com.itemis.gef4.tutorial.mindmap.parts.handles;

import java.net.URL;

import org.eclipse.gef.fx.nodes.HoverOverlayImageView;

import javafx.scene.image.Image;

public class CreateMindMapConnectionHandlePart extends AbstractMindMapHandlePart<HoverOverlayImageView>{
	
	public static final String IMG_ADD = "/add_conn.gif";
	public static final String IMG_ADD_DISABLED = "/add_conn_disabled.gif";
	
	
	@Override
	protected HoverOverlayImageView createVisual() {
		URL overlayImageResource = CreateMindMapConnectionHandlePart.class.getResource(IMG_ADD);
		if (overlayImageResource == null) {
			throw new IllegalStateException("Cannot find resource <" + IMG_ADD + ">.");
		}
		Image overlayImage = new Image(overlayImageResource.toExternalForm());

		URL baseImageResource = CreateMindMapConnectionHandlePart.class.getResource(IMG_ADD_DISABLED);
		if (baseImageResource == null) {
			throw new IllegalStateException("Cannot find resource <" + IMG_ADD_DISABLED + ">.");
		}
		Image baseImage = new Image(baseImageResource.toExternalForm());

		HoverOverlayImageView blendImageView = new HoverOverlayImageView();
		blendImageView.baseImageProperty().set(baseImage);
		blendImageView.overlayImageProperty().set(overlayImage);
		return blendImageView;
	}
}
