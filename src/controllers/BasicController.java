package controllers;

import javafx.scene.control.Label;
import tree.Node;

public abstract class BasicController {
    //protected Node initNode;
    //protected Node goalNode;

    //public abstract void preset(Node initNode, Node goalNode);
    protected abstract void tableInit();

    protected void setInfoToLabel(Label label, String info) {
        label.setText(label.getText().replaceAll("\\d", "") + info);
    }
}
