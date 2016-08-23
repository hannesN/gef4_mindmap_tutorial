# Step 3 - Building the visual representation

Now we need the visual representations of your model. Visuals are graphic elements, which,know nothing of the model.


1. create a new plugin project or import the exmaple
2. Add dependencys to the  Manifest: org.eclipse.gef4.geometry,  org.eclipse.gef4.fx;bundle-version="1.0.0"
3. Create a class: MindMapNodeVisuals extendeing javafx.scene.group
4. Create an JavaFApplication class, to test, hot it looks…
5. Got o manufest-runtime and export out package so other plugins can use our visuals
