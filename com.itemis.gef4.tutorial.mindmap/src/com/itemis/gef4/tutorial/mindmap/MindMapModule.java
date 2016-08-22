package com.itemis.gef4.tutorial.mindmap;

import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.common.adapt.inject.AdaptableScopes;
import org.eclipse.gef4.common.adapt.inject.AdapterMap;
import org.eclipse.gef4.common.adapt.inject.AdapterMaps;
import org.eclipse.gef4.mvc.behaviors.ContentBehavior;
import org.eclipse.gef4.mvc.behaviors.HoverBehavior;
import org.eclipse.gef4.mvc.behaviors.SelectionBehavior;
import org.eclipse.gef4.mvc.fx.MvcFxModule;
import org.eclipse.gef4.mvc.fx.behaviors.FXFocusBehavior;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultFocusFeedbackPartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultHoverFeedbackPartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultHoverHandlePartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultSelectionFeedbackPartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultSelectionHandlePartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXRootPart;
import org.eclipse.gef4.mvc.fx.policies.FXFocusAndSelectOnClickPolicy;
import org.eclipse.gef4.mvc.fx.providers.ShapeBoundsProvider;
import org.eclipse.gef4.mvc.fx.providers.ShapeOutlineProvider;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.models.ContentModel;
import org.eclipse.gef4.mvc.models.FocusModel;
import org.eclipse.gef4.mvc.models.HoverModel;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.parts.IContentPartFactory;

import com.google.common.reflect.TypeToken;
import com.google.inject.Binder;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.itemis.gef4.tutorial.mindmap.model.ConnectionCreationModel;
import com.itemis.gef4.tutorial.mindmap.model.EditModel;
import com.itemis.gef4.tutorial.mindmap.palette.parts.PaletteContentsFactory;
import com.itemis.gef4.tutorial.mindmap.palette.parts.PaletteEntryPart;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapContentsFactory;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodeAnchorProvider;
import com.itemis.gef4.tutorial.mindmap.parts.MindMapNodePart;
import com.itemis.gef4.tutorial.mindmap.policies.CreateNodeOnClickPolicy;
import com.itemis.gef4.tutorial.mindmap.policies.DirectEditingPolicy;
import com.itemis.gef4.tutorial.mindmap.policies.FocusAndSelectOnClickPolicy;

import javafx.scene.Node;
import javafx.scene.paint.Color;

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
	
	// this is the role for our palette viewer
	public static final String PALETTE_VIEWER_ROLE = "paletteViewer"; 
	
	public MindMapModule() {
	}
	
	@Override
	protected void configure() {
		super.configure();
		bindEditModel();
		
		bindMindMapNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), MindMapNodePart.class));
		
		// This specifies another FXViwer with the PALETTE_VIEWER_ROLE.
		// it will bind its own behaviours in this method
		bindPaletteViewerAdapters(AdapterMaps.getAdapterMapBinder(binder(), FXViewer.class, PALETTE_VIEWER_ROLE));
		// we also need a rootpart for a viewer which is defined here.
		bindPaletteViewerRootPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), FXRootPart.class, PALETTE_VIEWER_ROLE));

		bindPaletteEntryPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), PaletteEntryPart.class));
	}
	
	/**
	 * Try to bind edit model but it doesn't work right now
	 */
	protected void bindEditModel() {
		binder().bind(new TypeLiteral<EditModel>() {
		}).in(AdaptableScopes.typed(FXViewer.class));
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
		
		// bind another FXViwer for the role of PALETTE_VIEWER_ROLE
				adapterMapBinder.addBinding(AdapterKey.role(PALETTE_VIEWER_ROLE)).to(FXViewer.class);
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

		// the model to store direct editing states
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(EditModel.class);
		
		// the model to store the temporary data for creating a new connection
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ConnectionCreationModel.class);
		
		// the policy which creates a new node
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
		
		// bind the DirectEditingPolicy for editing the title and description
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(DirectEditingPolicy.class);
	}
	
	
	
	/**
	 * Binds Geometryproviders and Policys to the paletteentryparts
	 * 
	 * @param adapterMapBinder the binder to store the bindings
	 */
	private void bindPaletteEntryPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {

		// geometry provider for selection feedback
		adapterMapBinder
				.addBinding(AdapterKey.role(FXDefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER))
				.to(ShapeBoundsProvider.class);
		// geometry provider for hover feedback
		adapterMapBinder.addBinding(AdapterKey.role(FXDefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER))
				.to(ShapeBoundsProvider.class);

		// insert clicked part into selection and focus model using GEFs standard selection policy
		 adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FXFocusAndSelectOnClickPolicy.class);
	}

	/**
	 * Adds (default) {@link AdapterMap} bindings for {@link FXViewer} and all
	 * sub-classes. May be overwritten by sub-classes to change the default
	 * bindings.
	 *
	 * @param adapterMapBinder
	 *            The {@link MapBinder} to be used for the binding registration.
	 *            In this case, will be obtained from
	 *            {@link AdapterMaps#getAdapterMapBinder(Binder, Class)} using
	 *            {@link FXViewer} as a key.
	 *
	 * @see AdapterMaps#getAdapterMapBinder(Binder, Class)
	 */
	@SuppressWarnings("serial")
	protected void bindPaletteViewerAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// bind models
		
		// each viewer needs a content model
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ContentModel.class);

		adapterMapBinder.addBinding(AdapterKey.get(new TypeToken<FocusModel<Node>>() {
		})).to(new TypeLiteral<FocusModel<Node>>() {
		});

		adapterMapBinder.addBinding(AdapterKey.get(new TypeToken<HoverModel<Node>>() {
		})).to(new TypeLiteral<HoverModel<Node>>() {
		});

		// the selection model contains the current selections in the viewer
		adapterMapBinder.addBinding(AdapterKey.get(new TypeToken<SelectionModel<Node>>() {
		})).to(new TypeLiteral<SelectionModel<Node>>() {
		});

		// bind root part
		adapterMapBinder.addBinding(AdapterKey.role(PALETTE_VIEWER_ROLE)).to(FXRootPart.class)
				.in(AdaptableScopes.typed(FXViewer.class));

		// add hover feedback factory
		adapterMapBinder.addBinding(AdapterKey.role(SelectionBehavior.SELECTION_FEEDBACK_PART_FACTORY))
				.to(FXDefaultSelectionFeedbackPartFactory.class);

		adapterMapBinder.addBinding(AdapterKey.role(SelectionBehavior.SELECTION_HANDLE_PART_FACTORY))
				.to(FXDefaultSelectionHandlePartFactory.class);

		adapterMapBinder.addBinding(AdapterKey.role(HoverBehavior.HOVER_FEEDBACK_PART_FACTORY))
				.to(FXDefaultHoverFeedbackPartFactory.class);

		adapterMapBinder.addBinding(AdapterKey.role(HoverBehavior.HOVER_HANDLE_PART_FACTORY))
				.to(FXDefaultHoverHandlePartFactory.class);

		adapterMapBinder.addBinding(AdapterKey.role(FXFocusBehavior.FOCUS_FEEDBACK_PART_FACTORY))
				.to(FXDefaultFocusFeedbackPartFactory.class);
		// bind content part factory
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(PaletteContentsFactory.class);
		adapterMapBinder.addBinding(AdapterKey.role(FXDefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_COLOR_PROVIDER))
				.toInstance(new Provider<Color>() {
					@Override
					public Color get() {
						return Color.WHITE;
					}
				});
	}

	@SuppressWarnings("serial")
	protected void bindPaletteViewerRootPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.get(new TypeToken<ContentBehavior<Node>>() {
		})).to(new TypeLiteral<ContentBehavior<Node>>() {
		});

		// observes selection model and generates feedback
		adapterMapBinder.addBinding(AdapterKey.get(new TypeToken<SelectionBehavior<Node>>() {
		})).to(new TypeLiteral<SelectionBehavior<Node>>() {
		});

		// clear selection and focus when clicked into background
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FXFocusAndSelectOnClickPolicy.class);

	}

}
