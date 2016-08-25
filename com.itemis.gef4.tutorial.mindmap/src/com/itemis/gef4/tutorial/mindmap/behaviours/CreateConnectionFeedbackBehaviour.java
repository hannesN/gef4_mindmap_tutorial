package com.itemis.gef4.tutorial.mindmap.behaviours;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.gef.mvc.behaviors.AbstractBehavior;
import org.eclipse.gef.mvc.parts.IContentPart;
import org.eclipse.gef.mvc.parts.IFeedbackPart;
import org.eclipse.gef.mvc.parts.IFeedbackPartFactory;
import org.eclipse.gef.mvc.parts.IHandlePartFactory;
import org.eclipse.gef.mvc.parts.IVisualPart;
import org.eclipse.gef.mvc.viewer.IViewer;

import com.itemis.gef4.tutorial.mindmap.models.ItemCreationModel;
import com.itemis.gef4.tutorial.mindmap.models.ItemCreationModel.Type;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

public class CreateConnectionFeedbackBehaviour extends AbstractBehavior<Node> {

	// observing the model
	private ChangeListener<Type> typeListener = new ChangeListener<Type>() {

		@Override
		public void changed(ObservableValue<? extends Type> observable, Type oldValue, Type newValue) {
			System.out.println("Type Listener: " + newValue.toString());
			addFeedback(getHost().getRoot());
		}
	};
	private ChangeListener<IContentPart<?, ?>> sourceListener = new ChangeListener<IContentPart<?, ?>>() {

		@Override
		public void changed(ObservableValue<? extends IContentPart<?, ?>> observable, IContentPart<?, ?> oldValue,
				IContentPart<?, ?> newValue) {
			System.out.println("Source istener: newsource: " + newValue);
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
		System.out.println("Get FeedbackPartFactory");
		return super.getFeedbackPartFactory(viewer);
	}

	@Override
	protected IFeedbackPartFactory<Node> getFeedbackPartFactory(IViewer<Node> viewer, String role) {
		System.out.println("Get FeedbackPartFactory with role: " + role);
		return super.getFeedbackPartFactory(viewer, role);
	}

	@Override
	protected Map<Set<IVisualPart<Node, ? extends Node>>, List<IFeedbackPart<Node, ? extends Node>>> getFeedbackPerTargetSet() {
		System.out.println("Get Feedback Per target Set");
		return super.getFeedbackPerTargetSet();
	}

	@Override
	protected IHandlePartFactory<Node> getHandlePartFactory(IViewer<Node> viewer) {
		System.out.println("Get Handle PartFactory");
		return super.getHandlePartFactory(viewer);
	}

	@Override
	protected IHandlePartFactory<Node> getHandlePartFactory(IViewer<Node> viewer, String role) {
		System.out.println("Get Handle PartFactory with role: " + role);
		return super.getHandlePartFactory(viewer, role);
	}

	@Override
	public IVisualPart<Node, ? extends Node> getHost() {
		System.out.println("Get Host");
		return super.getHost();
	}

	@Override
	protected boolean hasFeedback(Collection<? extends IVisualPart<Node, ? extends Node>> targets) {
		System.out.println("Has Feedback targets");
		return super.hasFeedback(targets);
	}

	@Override
	protected boolean hasFeedback(IVisualPart<Node, ? extends Node> target) {
		System.out.println("Get Has Feedack");
		return super.hasFeedback(target);
	}
	

}
