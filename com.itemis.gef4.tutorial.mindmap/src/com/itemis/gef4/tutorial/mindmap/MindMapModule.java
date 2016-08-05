package com.itemis.gef4.tutorial.mindmap;

import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.common.adapt.inject.AdapterMaps;
import org.eclipse.gef4.mvc.fx.MvcFxModule;
import org.eclipse.gef4.mvc.fx.behaviors.FXFocusBehavior;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultFocusFeedbackPartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultHoverFeedbackPartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultSelectionFeedbackPartFactory;
import org.eclipse.gef4.mvc.fx.policies.FXFocusAndSelectOnClickPolicy;
import org.eclipse.gef4.mvc.fx.policies.FXHoverOnHoverPolicy;
import org.eclipse.gef4.mvc.fx.providers.ShapeBoundsProvider;
import org.eclipse.gef4.mvc.fx.providers.ShapeOutlineProvider;
import org.eclipse.gef4.mvc.parts.IContentPartFactory;

import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapContentsFactory;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodeAnchorProvider;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;
import com.itemis.gef4.tutorial.mindmap.policies.CreateNodeOnClickPolicy;
import com.itemis.gef4.tutorial.mindmap.policies.FocusAndSelectOnClickPolicy;

import javafx.scene.Node;

/**
 * The MindMapModule configures the whole dependency injection and adapter framework.
 * 
 * Here you register you policiys either for a specific type @see bindMindMapNodePart 
 * or for the whole application, like the setting of the ContentsFactory
 * 
 * @author hniederhausen
 *
 */
public class MindMapModule extends MvcFxModule {
	
	private MindMapPaletteModuleExtension extension; 
	
	public MindMapModule() {
		extension = new MindMapPaletteModuleExtension();
	}
	
	@Override
	protected void configure() {
		super.configure();
		
		bindMindMapNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), MindMapNodePart.class));
		
		extension.configure(this);
	}

	@Override
	public Binder binder() {
		return super.binder();
	}
	
	@Override
	protected void bindSelectionModel() {
	}
	
	
	@Override
	protected void bindFXDomainAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindFXDomainAdapters(adapterMapBinder);
		
		extension.bindFXDomainAdapters(adapterMapBinder);
	}

	@Override
	protected void bindIContentPartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindIContentPartFactoryAsContentViewerAdapter(adapterMapBinder);
		
		// tell the magic behind, which contentpartfactory it should use to create the parts
		binder().bind(new TypeLiteral<IContentPartFactory<Node>>() {}).toInstance(new MindMapContentsFactory());
	}
	
	@Override
	protected void bindFocusFeedbackPartFactoryAsContentViewerAdapter(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.role(FXFocusBehavior.FOCUS_FEEDBACK_PART_FACTORY))
			.to(FXDefaultFocusFeedbackPartFactory.class);
	}
	
	@Override
	protected void bindFocusAndSelectOnClickPolicyAsFXRootPartAdapter(
			MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FocusAndSelectOnClickPolicy.class);
	}
	
	@Override
	protected void bindContentViewerRootPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindContentViewerRootPartAdapters(adapterMapBinder);
		
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CreateNodeOnClickPolicy.class);
	}
	
	protected void bindMindMapNodePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		
		// provides a hover feedback to the shape, on mouse over. We need at least one of such a provider
		// or the anchor wouldn't find a IGeomtry interface to calculate the anchor - this is really weird
		AdapterKey<?> key = AdapterKey.role(FXDefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER);
		adapterMapBinder.addBinding(key).to(ShapeBoundsProvider.class);
		
		key = AdapterKey.role(FXDefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER);
		adapterMapBinder.addBinding(key).to(ShapeOutlineProvider.class);
		
		key = AdapterKey.role(MindMapNodeAnchorProvider.ANCHOR_GEOMETRY_PROVIDER);
		adapterMapBinder.addBinding(key).to(ShapeOutlineProvider.class);
		
		
		// bind anchor provider
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(MindMapNodeAnchorProvider.class);
		
		// bind the focus policy
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FXFocusAndSelectOnClickPolicy.class);
	}
}
