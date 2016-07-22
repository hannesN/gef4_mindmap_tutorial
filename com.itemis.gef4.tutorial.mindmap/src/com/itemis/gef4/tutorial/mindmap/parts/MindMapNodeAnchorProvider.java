package com.itemis.gef4.tutorial.mindmap.parts;

import java.util.Map;

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
				
				@Override
				protected IGeometry computeValue() {
					@SuppressWarnings("serial")
					Map<AdapterKey<?extends Provider<IGeometry>>, Provider<IGeometry>> geomProviders = host.getAdapters(new TypeToken<Provider<IGeometry>>() {});
					
					if (geomProviders.isEmpty()) {
						throw new IllegalStateException("No geometry providers found for "+host.getClass());
					}
					return geomProviders.values().iterator().next().get();
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
