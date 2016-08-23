package com.itemis.gef4.tutorial.mindmap;

import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.common.adapt.inject.AdapterMaps;
import org.eclipse.gef4.mvc.fx.MvcFxModule;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultHoverFeedbackPartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultSelectionFeedbackPartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultSelectionHandlePartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXSquareSegmentHandlePart;
import org.eclipse.gef4.mvc.fx.policies.FXFocusAndSelectOnClickPolicy;
import org.eclipse.gef4.mvc.fx.policies.FXHoverOnHoverPolicy;
import org.eclipse.gef4.mvc.fx.policies.FXResizeTranslateFirstAnchorageOnHandleDragPolicy;
import org.eclipse.gef4.mvc.fx.policies.FXTransformPolicy;
import org.eclipse.gef4.mvc.fx.policies.FXTranslateSelectedOnDragPolicy;
import org.eclipse.gef4.mvc.fx.providers.ShapeBoundsProvider;
import org.eclipse.gef4.mvc.fx.providers.ShapeOutlineProvider;
import org.eclipse.gef4.mvc.parts.IContentPartFactory;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapContentsFactory;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodeAnchorProvider;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;
import com.itemis.gef4.tutorial.mindmap.policies.MindMapNodeResizePolicy;

import javafx.scene.Node;

/**
 * The MindMapModule configures the whole dependency injection and adapter
 * framework.
 * 
 * Here you register you policies either for a specific type @see
 * bindMindMapNodePart or for the whole application, like the setting of the
 * ContentsFactory
 * 
 * @author hniederhausen
 *
 */
public class MindMapModule extends MvcFxModule {

	@Override
	protected void configure() {
		super.configure();

		bindMindMapNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), MindMapNodePart.class));

		// with this binding we create the handles
		bindFXSquareSegmentHandlePartPartAdapter(
				AdapterMaps.getAdapterMapBinder(binder(), FXSquareSegmentHandlePart.class));
	}

	@Override
	protected void bindIContentPartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindIContentPartFactoryAsContentViewerAdapter(adapterMapBinder);

		// tell the magic behind, which content part factory it should use to
		// create the parts
		binder().bind(new TypeLiteral<IContentPartFactory<Node>>() {
		}).toInstance(new MindMapContentsFactory());

	}

	@Override
	protected void bindAbstractContentPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindAbstractContentPartAdapters(adapterMapBinder);
		// some policies who manage the behavior on mouse events on all of the
		// content parts
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FXFocusAndSelectOnClickPolicy.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FXHoverOnHoverPolicy.class);
	}

	protected void bindMindMapNodePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {

		// provides a hover feedback to the shape, on mouse over. We need at least one of such a provider
		AdapterKey<?> role = AdapterKey.role(FXDefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER);
		adapterMapBinder.addBinding(role).to(ShapeBoundsProvider.class);

		role = AdapterKey.role(FXDefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER);
		adapterMapBinder.addBinding(role).to(ShapeOutlineProvider.class);

		// shape outline provider used in the anchor provider
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ShapeOutlineProvider.class);
		
		// bind anchor provider
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(MindMapNodeAnchorProvider.class);

		// bind drag policies
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FXTransformPolicy.class);
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FXTranslateSelectedOnDragPolicy.class);

		// bind our resize policy
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(MindMapNodeResizePolicy.class);

		// specify the factory to create the geometry object for the selection
		// handles
		role = AdapterKey.role(FXDefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER);
		adapterMapBinder.addBinding(role).to(ShapeOutlineProvider.class);

	}

	protected void bindFXSquareSegmentHandlePartPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {

		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(FXResizeTranslateFirstAnchorageOnHandleDragPolicy.class);
	}
}
