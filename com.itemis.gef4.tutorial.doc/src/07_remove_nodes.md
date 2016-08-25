Step 8 - Removing nodes

Goal: We want to be able to remove node from the Mind Map. We will create an icon whcih is apearing in the top right corner when hovering over a node. On click the node will be removed.

0. First we steel the icsons shamelessly from the GEF Logo Example. Copy the whole images folder into the mindmap project and add it to the build.properties. Also set the images folder as source path, so we can load them via the class loader.

1. The delete icon to show needs also a part. For a better overview, we will put handle parts in to the package `parts.handles`.
Create a Part class `DeleteMindMapNodeHandlePart` and a `MindMapHandleRootPart`, which contains the handles , using a VBox.

2. Create a factory, which creates our handles. We will need a factory for the selection handles and the hover handles, although the code is almost the same, their context is different.

