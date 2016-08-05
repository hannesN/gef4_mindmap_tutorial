package com.itemis.gef4.tutorial.mindmap;

import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.common.adapt.inject.AdaptableScopes;
import org.eclipse.gef4.common.adapt.inject.AdapterMap;
import org.eclipse.gef4.common.adapt.inject.AdapterMaps;
import org.eclipse.gef4.mvc.behaviors.ContentBehavior;
import org.eclipse.gef4.mvc.behaviors.HoverBehavior;
import org.eclipse.gef4.mvc.behaviors.SelectionBehavior;
import org.eclipse.gef4.mvc.fx.behaviors.FXFocusBehavior;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultFocusFeedbackPartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultHoverFeedbackPartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultHoverHandlePartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultSelectionFeedbackPartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXDefaultSelectionHandlePartFactory;
import org.eclipse.gef4.mvc.fx.parts.FXRootPart;
import org.eclipse.gef4.mvc.fx.policies.FXFocusAndSelectOnClickPolicy;
import org.eclipse.gef4.mvc.fx.providers.ShapeBoundsProvider;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.models.ContentModel;
import org.eclipse.gef4.mvc.models.FocusModel;
import org.eclipse.gef4.mvc.models.HoverModel;
import org.eclipse.gef4.mvc.models.SelectionModel;

import com.google.common.reflect.TypeToken;
import com.google.inject.Binder;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.itemis.gef4.tutorial.mindmap.palette.parts.PaletteContentsFactory;
import com.itemis.gef4.tutorial.mindmap.palette.parts.PaletteEntryPart;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class MindMapPaletteModuleExtension {

	public static final String PALETTE_VIEWER_ROLE = "paletteViewer";

	public void configure(MindMapModule module) {

		// palette
		bindPaletteViewerAdapters(
				AdapterMaps.getAdapterMapBinder(module.binder(), FXViewer.class, PALETTE_VIEWER_ROLE));
		bindPaletteViewerRootPartAdapters(
				AdapterMaps.getAdapterMapBinder(module.binder(), FXRootPart.class, PALETTE_VIEWER_ROLE));

		bindPaletteEntryPartAdapters(AdapterMaps.getAdapterMapBinder(module.binder(), PaletteEntryPart.class));
		// AdapterMaps.getAdapterMapBinder(binder(),
		// PaletteModelPart.class).addBinding(AdapterKey.defaultRole()).to(FXHoverOnHoverPolicy.class);
	}

	private void bindPaletteEntryPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {

		// geometry provider for selection feedback
		adapterMapBinder
				.addBinding(AdapterKey.role(FXDefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER))
				.to(ShapeBoundsProvider.class);
		// geometry provider for hover feedback
		adapterMapBinder.addBinding(AdapterKey.role(FXDefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER))
				.to(ShapeBoundsProvider.class);

		// observes selection model and generates feedback
		// adapterMapBinder.addBinding(AdapterKey.get(new
		// TypeToken<SelectionBehavior<Node>>() {
		// })).to(new TypeLiteral<SelectionBehavior<Node>>() {
		// });

		// insert clicked part into selection and focus model
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
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ContentModel.class);

		adapterMapBinder.addBinding(AdapterKey.get(new TypeToken<FocusModel<Node>>() {
		})).to(new TypeLiteral<FocusModel<Node>>() {
		});

		adapterMapBinder.addBinding(AdapterKey.get(new TypeToken<HoverModel<Node>>() {
		})).to(new TypeLiteral<HoverModel<Node>>() {
		});

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

	protected void bindFXDomainAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		// bind another FXViwer for the role of PALETTE_VIEWER_ROLE
		adapterMapBinder.addBinding(AdapterKey.role(PALETTE_VIEWER_ROLE)).to(FXViewer.class);
	}

}
