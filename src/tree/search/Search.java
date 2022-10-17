package tree.search;

import tree.Node;

public abstract class Search {
    protected Node initNode;
    protected Node goalNode;
    protected Node currentNode;
    protected boolean isOver;

    public Search(Node initNode, Node goalNode) {
        this.initNode = initNode;
        this.goalNode = goalNode;
        currentNode = initNode;
        isOver = false;
    }

    protected boolean IsGoal(Node node) {
        return node.equals(goalNode);
    }

    public boolean IsOver() {
        return isOver;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public abstract boolean Next();
}
