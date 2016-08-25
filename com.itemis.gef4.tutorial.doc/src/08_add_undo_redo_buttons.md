# Step 9
 
 Now that we are able to delete and create nodes, it is time to add some buttons, to undo our changes. With the editing domain and the use of operations we already have the facility to add the funtionality to our UI.
 The only thing missing is a little button bar and the use of some shortcuts to trigger the undo or redo operation.
 
 ## Create buttons
 
1. To create the buttons, we add a new HBox to the top of our `BorderPane`.  
2. Add a listener to the editDomains Operation history and set the disabled state for each button
3. The action listener run the method `undo`or `redo`of the operation history to trigger the actions.

That's it

## Keyboard Shortcuts

We are used to a lot of common short cuts. One of the most common is CMD-Z (on Mac) or (Ctrl-Z). To redo we implement the short cut  Shift-CMD-Z (on Mac) or Shift-Ctrl-Z on Windows.

to be able to do that, we need to create a new Policy which listens to key pressed events.

1. Create class `ShortcutHandlingPolicy`.
2. Add it to the abstract edit parts and the Rootpart.

 Sadly the implementation is dependent on a buggy KeyEvent. In the german keyboard layout you have to press CMD-Y to get the event for CMD-Z.