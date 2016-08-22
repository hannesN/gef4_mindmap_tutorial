package com.itemis.gef4.tutorial.mindmap.parts;

import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.common.adapt.IAdaptable;
import org.eclipse.gef4.fx.anchors.DynamicAnchor;
import org.eclipse.gef4.fx.anchors.DynamicAnchor.AnchorageReferenceGeometry;
import org.eclipse.gef4.fx.anchors.IAnchor;
import org.eclipse.gef4.geometry.planar.IGeometry;
import org.eclipse.gef4.mvc.parts.IVisualPart;

import com.google.common.reflect.TypeToken;
import com.google.inject.Provider;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;

/**
 * 
 * @author hniederhausen
 *
 */
public class MindMapNodeAnchorProvider extends IAdaptable.Bound.Impl<IVisualPart<Node, ? extends Node>> implements Provider<IAnchor> {

	public static final String ANCHOR_GEOMETRY_PROVIDER = "ANCHOR_GEOMETRY_PROVIDER";
	
	private IVisualPart<Node, ? extends Node> host;
	private DynamicAnchor anchor;
	
	@Override
	public IAnchor get() {
		if (anchor == null) {
			Node anchorage = getAdaptable().getVisual();
			anchor = new DynamicAnchor(anchorage);
			
			// what exactly does that mean?
			anchor.getComputationParameter(AnchorageReferenceGeometry.class).bind(new ObjectBinding<IGeometry>() {
				{
					bind(anchorage.layoutBoundsProperty());
				}
				
				@SuppressWarnings("serial")
				@Override
				protected IGeometry computeValue() {
					return host.getAdapter(AdapterKey.get(new TypeToken<Provider<IGeometry>>() {}, ANCHOR_GEOMETRY_PROVIDER)).get();
				}
			});
		}
		return anchor;
	}
	
	@Override
	public IVisualPart<Node, ? extends Node> getAdaptable() {
		return host;
	}

	@Override
	public void setAdaptable(IVisualPart<Node, ? extends Node> adaptable) {
		this.host = adaptable;
	}

	@Override
	public ReadOnlyObjectProperty<IVisualPart<Node, ? extends Node>> adaptableProperty() {
		return null;
	}

}
