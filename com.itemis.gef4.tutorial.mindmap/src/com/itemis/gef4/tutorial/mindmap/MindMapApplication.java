package com.itemis.gef4.tutorial.mindmap;

import java.util.Collections;
import java.util.List;

import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.fx.nodes.InfiniteCanvas;
import org.eclipse.gef4.mvc.fx.domain.FXDomain;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.models.ContentModel;

import com.google.inject.Guice;
import com.itemis.gef4.tutorial.mindmap.model.MindMap;
import com.itemis.gef4.tutorial.mindmap.model.MindMapFactory;
import com.itemis.gef4.tutorial.mindmap.palette.model.PaletteModel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This application start a stand alone gef4 application with an example model.
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
		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
		primaryStage.setTitle("GEF4 Mindmap");
		primaryStage.sizeToScene();
		primaryStage.show();

		// activate domain
		domain.activate();

		// load contents
		populateViewerContents();
		
		primaryStage.sizeToScene();

	}

	private void populateViewerContents() {
		MindMapFactory fac = new MindMapFactory();

		MindMap map = fac.createExample();

		getContentViewer().getAdapter(ContentModel.class).getContents().setAll(map);
		
		List<PaletteModel> paletteModel = Collections.singletonList(new PaletteModel());
		getPaletteViewer().getAdapter(ContentModel.class).getContents().setAll(paletteModel);

	}

	protected FXViewer getPaletteViewer() {
		FXViewer viewer = domain.getAdapter(AdapterKey.get(FXViewer.class, MindMapPaletteModuleExtension.PALETTE_VIEWER_ROLE));
		return viewer;
	}

	private FXViewer getContentViewer() {
		FXViewer viewer = domain.getAdapter(AdapterKey.get(FXViewer.class, FXDomain.CONTENT_VIEWER_ROLE));
		return viewer;
	}

	private void hookViewers() {

		System.out.println("Creating viewers");
		InfiniteCanvas canvas = getContentViewer().getCanvas();
		InfiniteCanvas palletteCanvas = getPaletteViewer().getCanvas();

		palletteCanvas.setShowGrid(false);
		palletteCanvas.setZoomGrid(false);

		// disable horizontal scrollbar for palette
		palletteCanvas.setHorizontalScrollBarPolicy(ScrollBarPolicy.NEVER);

		
		BorderPane viewersPane = new BorderPane();
		viewersPane.setLeft(palletteCanvas);
//		viewersPane.setTop(new Text("Huhu?!?"));
		viewersPane.setCenter(canvas);

		Scene scene = new Scene(viewersPane);
		primaryStage.setScene(scene);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
