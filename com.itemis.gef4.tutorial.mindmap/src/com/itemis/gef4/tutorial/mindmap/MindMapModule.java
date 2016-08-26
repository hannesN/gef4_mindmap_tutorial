package com.itemis.gef4.tutorial.mindmap;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.inject.AdaptableScopes;
import org.eclipse.gef.common.adapt.inject.AdapterMaps;
import org.eclipse.gef.mvc.behaviors.HoverBehavior;
import org.eclipse.gef.mvc.behaviors.SelectionBehavior;
import org.eclipse.gef.mvc.fx.MvcFxModule;
import org.eclipse.gef.mvc.fx.behaviors.FXHoverBehavior;
import org.eclipse.gef.mvc.fx.parts.FXDefaultHoverFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.FXDefaultHoverHandlePartFactory;
import org.eclipse.gef.mvc.fx.parts.FXDefaultSelectionFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.FXDefaultSelectionHandlePartFactory;
import org.eclipse.gef.mvc.fx.parts.FXSquareSegmentHandlePart;
import org.eclipse.gef.mvc.fx.policies.FXFocusAndSelectOnClickPolicy;
import org.eclipse.gef.mvc.fx.policies.FXHoverOnHoverPolicy;
import org.eclipse.gef.mvc.fx.policies.FXResizeTranslateFirstAnchorageOnHandleDragPolicy;
import org.eclipse.gef.mvc.fx.policies.FXTransformPolicy;
import org.eclipse.gef.mvc.fx.policies.FXTranslateSelectedOnDragPolicy;
import org.eclipse.gef.mvc.fx.providers.FXTransformProvider;
import org.eclipse.gef.mvc.fx.providers.ShapeBoundsProvider;
import org.eclipse.gef.mvc.fx.providers.ShapeOutlineProvider;
import org.eclipse.gef.mvc.fx.viewer.FXViewer;
import org.eclipse.gef.mvc.parts.IContentPartFactory;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.itemis.gef4.tutorial.mindmap.behaviours.CreateConnectionFeedbackBehaviour;
import com.itemis.gef4.tutorial.mindmap.models.InlineEditModel;
import com.itemis.gef4.tutorial.mindmap.models.ItemCreationModel;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapContentsFactory;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodeAnchorProvider;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;
import com.itemis.gef4.tutorial.mindmap.parts.feedback.CreateNodeFeedbackPart;
import com.itemis.gef4.tutorial.mindmap.parts.feedback.CreationFeedbackPartFactory;
import com.itemis.gef4.tutorial.mindmap.parts.handles.DeleteMindMapNodeHandlePart;
import com.itemis.gef4.tutorial.mindmap.parts.handles.MindMapHoverHandleFactory;
import com.itemis.gef4.tutorial.mindmap.parts.handles.MindMapSelectionHandleFactory;
import com.itemis.gef4.tutorial.mindmap.policies.CreateConnectionOnClickPolicy;
import com.itemis.gef4.tutorial.mindmap.policies.CreateNodeOnClickPolicy;
import com.itemis.gef4.tutorial.mindmap.policies.DeleteMindMapNodeOnClickPolicy;
import com.itemis.gef4.tutorial.mindmap.policies.InlineEditPolicy;
import com.itemis.gef4.tutorial.mindmap.policies.MindMapNodeResizePolicy;
import com.itemis.gef4.tutorial.mindmap.policies.ShortcutHandlingPolicy;
import com.itemis.gef4.tutorial.mindmap.policies.ShowContextMenuOnClickPolicy;

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

		// binding our self makde models to the scope of FXViewer
		bindModels();

		bindMindMapNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), MindMapNodePart.class));

		// registering the click policy for our delete handle
		bindDeleteMindMapNodeHandlePart(AdapterMaps.getAdapterMapBinder(binder(), DeleteMindMapNodeHandlePart.class));
		
		bindCreateNodeFeedbackPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), CreateNodeFeedbackPart.class));
		
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
		
		// bind the shortcut policy here
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ShortcutHandlingPolicy.class);
		
		
	}

	@Override
	protected void bindContentViewerRootPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindContentViewerRootPartAdapters(adapterMapBinder);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CreateNodeOnClickPolicy.class);

		// hover behavior
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FXHoverBehavior.class);
		
		// bind the shortcut policy here
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ShortcutHandlingPolicy.class);
		
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CreateConnectionFeedbackBehaviour.class);
	}

	@Override
	protected void bindContentViewerAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindContentViewerAdapters(adapterMapBinder);
		bindInlineEditModelAsContentViewerAdapter(adapterMapBinder);
		bindItemCreationEditModelAsContentViewerAdapter(adapterMapBinder);

		// bind the connection creation Feedback
		adapterMapBinder
				.addBinding(AdapterKey.role(CreateConnectionFeedbackBehaviour.CREATE_CONNECTION_FEEDBACK_PART_FACTORY))
				.to(CreationFeedbackPartFactory.class);
	}

	@Override
	protected void bindHoverHandlePartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// adding our handle factory
		adapterMapBinder.addBinding(AdapterKey.role(HoverBehavior.HOVER_HANDLE_PART_FACTORY))
				.to(MindMapHoverHandleFactory.class);
	}

	@Override
	protected void bindSelectionHandlePartFactoryAsContentViewerAdapter(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.role(SelectionBehavior.SELECTION_HANDLE_PART_FACTORY))
				.to(MindMapSelectionHandleFactory.class);
	}

	protected void bindFXSquareSegmentHandlePartPartAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
	
		adapterMapBinder.addBinding(AdapterKey.defaultRole())
				.to(FXResizeTranslateFirstAnchorageOnHandleDragPolicy.class);
	}

	protected void bindInlineEditModelAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		AdapterKey<InlineEditModel> key = AdapterKey.get(InlineEditModel.class);
		adapterMapBinder.addBinding(key).to(InlineEditModel.class);
	}

	protected void bindItemCreationEditModelAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		AdapterKey<ItemCreationModel> key = AdapterKey.get(ItemCreationModel.class);
		adapterMapBinder.addBinding(key).to(ItemCreationModel.class);
	}

	protected void bindMindMapNodePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
	
		// provides a hover feedback to the shape, on mouse over.
		AdapterKey<?> role = AdapterKey.role(FXDefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER);
		adapterMapBinder.addBinding(role).to(ShapeOutlineProvider.class);
		
		// add a bounds provider for the HoverHandleFactory
		role = AdapterKey.role(FXDefaultHoverHandlePartFactory.HOVER_HANDLES_GEOMETRY_PROVIDER);
		adapterMapBinder.addBinding(role).to(ShapeBoundsProvider.class);
	
		role = AdapterKey.role(FXDefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER);
		adapterMapBinder.addBinding(role).to(ShapeBoundsProvider.class);
		
		// add a bounds provider for the SelectionHandleFactory
		role = AdapterKey.role(FXDefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER);
		adapterMapBinder.addBinding(role).to(ShapeBoundsProvider.class);
	
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
		adapterMapBinder.addBinding(role).to(ShapeBoundsProvider.class);
	
		// adding the inline edit policy to the part to listen to double clicks
		// on "fields"
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(InlineEditPolicy.class);
	
		// adding the connection creation
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CreateConnectionOnClickPolicy.class);
	
		// binding the context menu to the part
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ShowContextMenuOnClickPolicy.class);
	}
	
	protected void bindCreateNodeFeedbackPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.role(FXTransformPolicy.TRANSFORM_PROVIDER_KEY.getRole()))
		.to(FXTransformProvider.class);
	}

	/**
	 * Registers the delete policy for the {@link DeleteMindMapNodeHandlePart}
	 * @param adapterMapBinder
	 */
	protected void bindDeleteMindMapNodeHandlePart(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(DeleteMindMapNodeOnClickPolicy.class);
	}
	
	protected void bindModels() {
		binder().bind(InlineEditModel.class).in(AdaptableScopes.typed(FXViewer.class));
		binder().bind(ItemCreationModel.class).in(AdaptableScopes.typed(FXViewer.class));
	}
}
