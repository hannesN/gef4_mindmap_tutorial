# Step 6 Creating new connections

Connections a more complicated to create, because, we need to choose the source and the target node sequentially.

In the first step, we will create the funtionlity without any feedback and will extend it in future steps.

## Extending the ItemCreationModel

* Just add *Connection* to the enumeration.
* Add a source property, which stores the host of the first click


## Extending the Palette

First we need a new button to create a connection. We'll put it below the "*New Node* button.
In the listener, we set the type to *Connection*.

## Create CreateConnectionOnClickPolicy

To create a new connection, we have to listen on click events on our *MindMapNodeParts*.

Now register the policy to the *MindMapNodeParts* in the module.