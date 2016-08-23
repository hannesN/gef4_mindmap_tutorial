package com.itemis.gef4.tutorial.mindmap;

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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
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

		pane.setPrefSize(800, 600);
		
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
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
				if (newValue) {
					model.setPressedButton(addNodeButton);
					model.setType(Type.Node);
				} else {
					model.clearSettings();
				}
			}
			
		});
		
		return new VBox(addNodeButton);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
