package com.itemis.gef4.tutorial.mindmap.parts.handles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.geometry.planar.BezierCurve;
import org.eclipse.gef.mvc.behaviors.IBehavior;
import org.eclipse.gef.mvc.fx.parts.FXDefaultHoverHandlePartFactory;
import org.eclipse.gef.mvc.parts.IHandlePart;
import org.eclipse.gef.mvc.parts.IVisualPart;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.itemis.gef4.tutorial.mindmap.model.MindMapNode;

import javafx.scene.Node;

public class MindMapHoverHandleFactory extends FXDefaultHoverHandlePartFactory {

	@Inject
	private Injector injector;
	
	@Override
	protected List<IHandlePart<Node, ? extends Node>> createHoverHandlePartsForPolygonalOutline(
			IVisualPart<Node, ? extends Node> target, IBehavior<Node> contextBehavior, Map<Object, Object> contextMap,
			Provider<BezierCurve[]> segmentsProvider) {

		
		List<IHandlePart<Node, ? extends Node>> handles = new ArrayList<>();
		if (target instanceof MindMapNode) {
			// create root handle part
			DeleteMindMapNodeHandlePart parentHp = new DeleteMindMapNodeHandlePart();
			injector.injectMembers(parentHp);
			handles.add(parentHp);

		}
		return handles;
	}
	
	@Override
	protected List<IHandlePart<Node, ? extends Node>> createHoverHandlePartsForRectangularOutline(
			IVisualPart<Node, ? extends Node> target, IBehavior<Node> contextBehavior, Map<Object, Object> contextMap,
			Provider<BezierCurve[]> segmentsProvider) {
		
		return createHoverHandlePartsForPolygonalOutline(target, contextBehavior, contextMap, segmentsProvider);
	}
}
