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
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;

import javafx.scene.Node;

public class MindMapHoverHandleFactory extends FXDefaultHoverHandlePartFactory {

	@Inject
	private Injector injector;

	@Override
	protected List<IHandlePart<Node, ? extends Node>> createHoverHandlePartsForPolygonalOutline(
			IVisualPart<Node, ? extends Node> target, IBehavior<Node> contextBehavior, Map<Object, Object> contextMap,
			Provider<BezierCurve[]> segmentsProvider) {

		// get the default handle parts, e.g. the resize handles
		List<IHandlePart<Node, ? extends Node>> handleParts = super.createHoverHandlePartsForPolygonalOutline(target,
				contextBehavior, contextMap, segmentsProvider);

		handleParts.addAll(createHandles(target));

		return handleParts;
	}

	@Override
	protected List<IHandlePart<Node, ? extends Node>> createHoverHandlePartsForRectangularOutline(
			IVisualPart<Node, ? extends Node> target, IBehavior<Node> contextBehavior, Map<Object, Object> contextMap,
			Provider<BezierCurve[]> segmentsProvider) {

		// get the default handle parts, e.g. the resize handles
		List<IHandlePart<Node, ? extends Node>> handleParts = super.createHoverHandlePartsForPolygonalOutline(target, contextBehavior, contextMap, segmentsProvider);

		handleParts.addAll(createHandles(target));

		return handleParts;
	}

	@Override
	protected List<IHandlePart<Node, ? extends Node>> createHoverHandlePartsForCurve(
			IVisualPart<Node, ? extends Node> target, IBehavior<Node> contextBehavior, Map<Object, Object> contextMap,
			Provider<BezierCurve[]> segmentsProvider) {
	
		// get the default handle parts, e.g. the resize handles
		List<IHandlePart<Node, ? extends Node>> handleParts = super.createHoverHandlePartsForCurve(target, contextBehavior, contextMap, segmentsProvider);

		handleParts.addAll(createHandles(target));

		return handleParts;
	}

	private List<IHandlePart<Node, ? extends Node>> createHandles(IVisualPart<Node, ? extends Node> target) {
		List<IHandlePart<Node, ? extends Node>> handles = new ArrayList<>();

		if (target instanceof MindMapNodePart) {
			// create root handle part

			MindMapHandleRootPart parentHp = new MindMapHandleRootPart();
			injector.injectMembers(parentHp);

			DeleteMindMapNodeHandlePart delHp = new DeleteMindMapNodeHandlePart();
			injector.injectMembers(delHp);
			parentHp.addChild(delHp);

			CreateMindMapConnectionHandlePart creConnHp = new CreateMindMapConnectionHandlePart();
			injector.injectMembers(creConnHp);
			parentHp.addChild(creConnHp);
			
			handles.add(parentHp);

		}
		return handles;
	}

}
