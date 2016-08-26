package com.itemis.gef4.tutorial.mindmap;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.mvc.fx.domain.FXDomain;
import org.eclipse.gef.mvc.fx.viewer.FXViewer;
import org.eclipse.gef.mvc.models.ContentModel;

import com.google.inject.Guice;
import com.itemis.gef4.tutorial.mindmap.model.MindMap;
import com.itemis.gef4.tutorial.mindmap.model.MindMapFactory;
import com.itemis.gef4.tutorial.mindmap.models.ItemCreationModel;
import com.itemis.gef4.tutorial.mindmap.models.ItemCreationModel.Type;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This application start a stand alone gef4 application with an example 
 * model.
 * 
 * @author hniederhausen
 *
 */
public class MindMapApplication extends Application {

	private Stage primaryStage;
	private FXDomain domain;
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		MindMapModule module = new MindMapModule();
		this.primaryStage = primaryStage;
		// create domain using guice
		this.domain = Guice.createInjector(module).getInstance(FXDomain.class);

		// create viewers
		hookViewers();

		// set-up stage
		primaryStage.setResizable(true);
		primaryStage.setWidth(640);
		primaryStage.setHeight(480);
		primaryStage.setTitle("GEF4 Mindmap");
		primaryStage.sizeToScene();
		primaryStage.show();

		// activate domain
		domain.activate();

		// load contents
		populateViewerContents();

	}

	private void populateViewerContents() {
		MindMapFactory fac = new MindMapFactory();
		
		MindMap map = fac.createSingleNodeExample();
		
		FXViewer viewer = getContentViewer();
		
		viewer.getAdapter(ContentModel.class).getContents().setAll(map);
		
	}

	private FXViewer getContentViewer() {
		FXViewer viewer = domain.getAdapter(AdapterKey.get(FXViewer.class, FXDomain.CONTENT_VIEWER_ROLE));
		return viewer;
	}

	private void hookViewers() {
		
		BorderPane pane = new BorderPane(getContentViewer().getCanvas());
		pane.setLeft(createPaletteNode());
		pane.setTop(createToolbarNode());

		pane.setPrefSize(800, 600);
		
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
	}

	private Node createToolbarNode() {
		
		Button undoButton = new Button("Undo");
		undoButton.setDisable(true);
		
		undoButton.setOnAction((e) -> {
			try {
				domain.getOperationHistory().undo(domain.getUndoContext(), null, null);
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			}
		});
			
		
		Button redoButton = new Button("Redo");
		redoButton.setDisable(true);
		redoButton.setOnAction((e) -> {
			try {
				domain.getOperationHistory().redo(domain.getUndoContext(), null, null);
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			}
		});
		
		
		
		
		// add listener, so we can activate the buttons if undo/redo is possible
		domain.getOperationHistory().addOperationHistoryListener(new IOperationHistoryListener() {
			
			@Override
			public void historyNotification(OperationHistoryEvent event) {
				IUndoContext undoContext = domain.getUndoContext();
				undoButton.setDisable(!event.getHistory().canUndo(undoContext));
				redoButton.setDisable(!event.getHistory().canRedo(undoContext));
			}
		});
		
		
		return new HBox(undoButton, redoButton);
	}
	
	private Node createPaletteNode() {
		
		// the toggleGroup makes sure, we only select one 
		ToggleGroup toggleGroup = new ToggleGroup();
		
		ToggleButton addNodeButton = new ToggleButton("New Node");
		addNodeButton.setToggleGroup(toggleGroup);
		addNodeButton.setPrefHeight(80);
		
		addNodeButton.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				ItemCreationModel model = getContentViewer().getAdapter(ItemCreationModel.class);
				model.clearSettings();
				if (newValue) {
					model.setPressedButton(addNodeButton);
					model.setType(Type.Node);
				}
			}
			
		});
		
		ToggleButton addConnectionButton = new ToggleButton("New Connection");
		addConnectionButton.setToggleGroup(toggleGroup);
		addConnectionButton.setPrefHeight(80);
		
		addConnectionButton.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				ItemCreationModel model = getContentViewer().getAdapter(ItemCreationModel.class);
				model.clearSettings();
				if (newValue) {
					model.setPressedButton(addConnectionButton);
					model.setType(Type.Connection);
				}
			}
		});
		
		return new VBox(addNodeButton, addConnectionButton);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
