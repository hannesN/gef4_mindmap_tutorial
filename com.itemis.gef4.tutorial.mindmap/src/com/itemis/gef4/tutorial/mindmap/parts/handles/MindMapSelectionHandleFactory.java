package com.itemis.gef4.tutorial.mindmap.parts.handles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.geometry.planar.BezierCurve;
import org.eclipse.gef.mvc.behaviors.IBehavior;
import org.eclipse.gef.mvc.fx.parts.FXDefaultSelectionHandlePartFactory;
import org.eclipse.gef.mvc.parts.IHandlePart;
import org.eclipse.gef.mvc.parts.IVisualPart;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;

import javafx.scene.Node;

public class MindMapSelectionHandleFactory extends FXDefaultSelectionHandlePartFactory {

	@Inject
	private Injector injector;

	@Override
	protected List<IHandlePart<Node, ? extends Node>> createSingleSelectionHandleParts(
			IVisualPart<Node, ? extends Node> target, IBehavior<Node> contextBehavior, Map<Object, Object> contextMap) {

		// get the default handle parts, e.g. the resize handles
		List<IHandlePart<Node, ? extends Node>> handleParts = super.createSingleSelectionHandleParts(target,
				contextBehavior, contextMap);

		handleParts.addAll(createHandles(target));

		return handleParts;
	}

	@Override
	protected List<IHandlePart<Node, ? extends Node>> createSingleSelectionHandlePartsForCurve(
			IVisualPart<Node, ? extends Node> target, IBehavior<Node> contextBehavior, Map<Object, Object> contextMap,
			Provider<BezierCurve[]> segmentsProvider) {

		// get the default handle parts, e.g. the resize handles
		List<IHandlePart<Node, ? extends Node>> handleParts = super.createSingleSelectionHandlePartsForCurve(target, contextBehavior, contextMap, segmentsProvider);

		handleParts.addAll(createHandles(target));

		return handleParts;
	}

	@Override
	protected List<IHandlePart<Node, ? extends Node>> createSingleSelectionHandlePartsForPolygonalOutline(
			IVisualPart<Node, ? extends Node> target, IBehavior<Node> contextBehavior, Map<Object, Object> contextMap,
			Provider<BezierCurve[]> segmentsProvider) {

		// get the default handle parts, e.g. the resize handles
		List<IHandlePart<Node, ? extends Node>> handleParts = super.createSingleSelectionHandlePartsForPolygonalOutline(target, contextBehavior, contextMap, segmentsProvider);

		handleParts.addAll(createHandles(target));

		return handleParts;
	}

	@Override
	protected List<IHandlePart<Node, ? extends Node>> createSingleSelectionHandlePartsForRectangularOutline(
			IVisualPart<Node, ? extends Node> target, IBehavior<Node> contextBehavior, Map<Object, Object> contextMap,
			Provider<BezierCurve[]> segmentsProvider) {

		// get the default handle parts, e.g. the resize handles
		List<IHandlePart<Node, ? extends Node>> handleParts = super.createSingleSelectionHandlePartsForRectangularOutline(target, contextBehavior, contextMap, segmentsProvider);

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
