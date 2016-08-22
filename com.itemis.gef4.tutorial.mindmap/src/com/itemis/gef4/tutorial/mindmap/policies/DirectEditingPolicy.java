package com.itemis.gef4.tutorial.mindmap.policies;

import org.eclipse.gef4.mvc.fx.policies.IFXOnClickPolicy;
import org.eclipse.gef4.mvc.policies.AbstractInteractionPolicy;

import com.itemis.gef4.tutorial.mindmap.parts.IDirectEditablePart;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class DirectEditingPolicy extends AbstractInteractionPolicy<Node> implements IFXOnClickPolicy {

	@Override
	public void click(MouseEvent e) {

		if (e.getClickCount() != 2) {
			return;
		}

		// EditModel editModel = getHost().getAdapter(EditModel.class);
		// if (editModel.getHost()!=null) {
		// return;
		// }

		IDirectEditablePart host = (IDirectEditablePart) getHost();

		Node target = (Node) e.getTarget();
		if (host.getEditableEntries().contains(target)) {
			Node editorNode = host.startEditing(target);

			// editModel.setHost(host);
			// editModel.setField(target);
			//
			editorNode.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					System.out.println("Key pressed: Code: " + event.getCode());
					if (event.getCode() == KeyCode.ESCAPE) {
						host.cancelEditing(target);
					}

					if (event.getCode() == KeyCode.ENTER && event.isAltDown()) {
						Object newValue = host.getEntryValue((Node) event.getTarget());
						host.submitEditing(target, newValue);

						// TODO operation doing this fun stuff
					}

				}

			});
			editorNode.focusedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

					if (!newValue) {
						Task<Boolean> task = new Task<Boolean>() {

							@Override
							protected Boolean call() throws Exception {
								Thread.sleep(200); // we wait if we get the
													// focus right back, like in
													// clicking inside the
													// textfield

								if (!editorNode.focusedProperty().getValue()) {
									Platform.runLater(new Runnable() {

										@Override
										public void run() {
											host.cancelEditing(target);
										}
									});

								}

								return true;
							}
						};
						new Thread(task).start();
					}
				}
			});
			editorNode.requestFocus();

		}

	}

}
