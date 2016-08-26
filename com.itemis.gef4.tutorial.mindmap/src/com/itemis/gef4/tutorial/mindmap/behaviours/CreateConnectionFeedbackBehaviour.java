package com.itemis.gef4.tutorial.mindmap.behaviours;

import java.util.Collection;
import java.util.List;

import org.eclipse.gef.mvc.behaviors.AbstractBehavior;
import org.eclipse.gef.mvc.parts.IContentPart;
import org.eclipse.gef.mvc.parts.IFeedbackPart;
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

	// observing the model
	private ChangeListener<Type> typeListener = new ChangeListener<Type>() {

		@Override
		public void changed(ObservableValue<? extends Type> observable, Type oldValue, Type newValue) {
		}
	};
	private ChangeListener<IContentPart<?, ?>> sourceListener = new ChangeListener<IContentPart<?, ?>>() {

		@SuppressWarnings("unchecked")
		@Override
		public void changed(ObservableValue<? extends IContentPart<?, ?>> observable, IContentPart<?, ?> oldValue,
				IContentPart<?, ?> newValue) {
			
			System.out.println("Source istener: newsource: " + newValue);
			if (newValue==null) {
				clearFeedback();
			} else {
				addFeedback((IVisualPart<Node, ? extends Node>) newValue);
			}
		}

	};

	@Override
	protected void addFeedback(IVisualPart<Node, ? extends Node> target) {
		System.out.println("Add Feedback " + target);
		super.addFeedback(target);
	}

	@Override
	protected void addFeedback(List<? extends IVisualPart<Node, ? extends Node>> targets) {
		// TODO Auto-generated method stub
		super.addFeedback(targets);
	}

	@Override
	protected void addHandles(IVisualPart<Node, ? extends Node> target) {
		System.out.println("Add Handles");
		super.addHandles(target);
	}

	@Override
	protected void addHandles(List<? extends IVisualPart<Node, ? extends Node>> targets) {
		System.out.println("Add Handles taregts");
		super.addHandles(targets);
	}

	@Override
	protected void clearFeedback() {
		System.out.println("Clear Feedback");
		super.clearFeedback();
	}

	@Override
	protected void clearHandles() {
		// TODO Auto-generated method stub
		super.clearHandles();
	}

	@Override
	protected void doActivate() {
		System.out.println("Do Activate");

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
		System.out.println("Do Deactivate");

		ItemCreationModel creationModel = getCreationModel();
		creationModel.getTypeProperty().removeListener(typeListener);
		creationModel.getSourceProperty().removeListener(sourceListener);

		super.doDeactivate();
	}

	@Override
	protected List<IFeedbackPart<Node, ? extends Node>> getFeedback(
			Collection<? extends IVisualPart<Node, ? extends Node>> targets) {
		System.out.println("Get Feedback targets");
		return super.getFeedback(targets);
	}

	@Override
	protected List<IFeedbackPart<Node, ? extends Node>> getFeedback(IVisualPart<Node, ? extends Node> target) {
		System.out.println("Get Feedback");
		return super.getFeedback(target);
	}

	@Override
	protected IFeedbackPartFactory<Node> getFeedbackPartFactory(IViewer<Node> viewer) {
		return getFeedbackPartFactory(viewer, CREATE_CONNECTION_FEEDBACK_PART_FACTORY);
	}
}
