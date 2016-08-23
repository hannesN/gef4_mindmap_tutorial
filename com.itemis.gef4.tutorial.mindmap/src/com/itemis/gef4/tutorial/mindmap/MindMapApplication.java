package com.itemis.gef4.tutorial.mindmap;

import org.eclipse.gef.common.adapt.AdapterKey; 
import org.eclipse.gef.fx.nodes.InfiniteCanvas;
import org.eclipse.gef.mvc.fx.domain.FXDomain;
import org.eclipse.gef.mvc.fx.viewer.FXViewer;
import org.eclipse.gef.mvc.models.ContentModel;

import com.google.inject.Guice;
import com.itemis.gef4.tutorial.mindmap.model.MindMap;
import com.itemis.gef4.tutorial.mindmap.model.MindMapFactory;

import javafx.application.Application;
import javafx.scene.Scene;
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
		// TODO Auto-generated method stub
		InfiniteCanvas canvas = getContentViewer().getCanvas();
		
		primaryStage.setScene(new Scene(canvas));
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
