# Step 2 - The Application

Goal is to create a mindmap application, where each node has a title, a description and a color.

So lets create a model, containing or Mindmap.

1. Create a new plugin project (or import the example project)
2. add to imported-packes in manifest, to be able to use the google commons colection: com.google.common.collect
3. We use the PropertyChangeSupport, for Databinding
4. A factory creates an example
5. Got o manufest-runtime and export out package so other plugins can use our model
