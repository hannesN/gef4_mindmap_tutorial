package com.itemis.gef4.tutorial.mindmap.behaviours;

import org.eclipse.gef.mvc.behaviors.AbstractBehavior;
import org.eclipse.gef.mvc.parts.IContentPart;
import org.eclipse.gef.mvc.parts.IFeedbackPartFactory;
import org.eclipse.gef.mvc.parts.IVisualPart;
import org.eclipse.gef.mvc.viewer.IViewer;

import com.itemis.gef4.tutorial.mindmap.models.ItemCreationModel;
import com.itemis.gef4.tutorial.mindmap.models.ItemCreationModel.Type;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

public class CreateConnectionFeedbackBehaviour extends AbstractBehavior<Node> {

	/**
	 * The adapter role for the {@link IFeedbackPartFactory} that is used to
	 * generate hover feedback parts.
	 */
	public static final String CREATE_CONNECTION_FEEDBACK_PART_FACTORY = "CREATE_CONNECTION_FEEDBACK_PART_FACTORY";

	private boolean clearable = false;
	
	// observing the model
	private ChangeListener<Type> typeListener = new ChangeListener<Type>() {

		@Override
		public void changed(ObservableValue<? extends Type> observable, Type oldValue, Type newValue) {

			// clearing feedback if creation type changes
			clearFeedback();

			// now check if we create a new node and actvate the feedback
			if (newValue == Type.Node) {
				// for the node creation we use the host as feedback anchor
				addFeedback(getHost());
			}

		}
	};
	private ChangeListener<IContentPart<?, ?>> sourceListener = new ChangeListener<IContentPart<?, ?>>() {

		@SuppressWarnings("unchecked")
		@Override
		public void changed(ObservableValue<? extends IContentPart<?, ?>> observable, IContentPart<?, ?> oldValue,
				IContentPart<?, ?> newValue) {

			if (newValue == null) {
				clearFeedback();
			} else {
				addFeedback((IVisualPart<Node, ? extends Node>) newValue);
			}
		}

	};

	@Override
	protected void clearFeedback() {
		if(!clearable)
			return;
		System.out.println("Clearin Feedback");
		try {
			super.clearFeedback();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}
	
	@Override
	protected void addFeedback(IVisualPart<Node, ? extends Node> target) {
		clearable=true;
		super.addFeedback(target);
	}

	@Override
	protected void doActivate() {

		ItemCreationModel creationModel = getCreationModel();

		creationModel.getTypeProperty().addListener(typeListener);
		creationModel.getSourceProperty().addListener(sourceListener);

		super.doActivate();
	}

	private ItemCreationModel getCreationModel() {
		return getHost().getRoot().getViewer().getAdapter(ItemCreationModel.class);
	}

	@Override
	protected void doDeactivate() {
		ItemCreationModel creationModel = getCreationModel();
		creationModel.getTypeProperty().removeListener(typeListener);
		creationModel.getSourceProperty().removeListener(sourceListener);

		super.doDeactivate();
	}

	@Override
	protected IFeedbackPartFactory<Node> getFeedbackPartFactory(IViewer<Node> viewer) {
		return getFeedbackPartFactory(viewer, CREATE_CONNECTION_FEEDBACK_PART_FACTORY);
	}
}
