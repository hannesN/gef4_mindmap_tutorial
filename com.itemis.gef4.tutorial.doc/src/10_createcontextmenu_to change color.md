# Step 11 Change the node colors

In this step we want to add a context menu to the *MindMapNodes*. We will create another *IFXonClick* Policy which will open a context menu.
To change the color, we need to create a new operation, which will be exceuted, so we can undo the change.

## Creating the operation

Let's begin with the operation. The implememtation is straigt foreward. We have three properties: *newColor*, *oldColor* and the *MindMapNodePart*.

The execute and undo methods set the according colors in the node and refresh the parts visual. And that's it.

## Creating the policy

Next create a class *ShowContextMenuOnClickPolicy* in the operations package. It will create the context menu end will react in click actions

Bind the policy to the MindMapNodeParts in the MindMapModul.