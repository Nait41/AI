package tree.search;

import tree.Node;

public abstract class Search {
    protected Node initNode;
    protected Node goalNode;
    protected boolean isOver;

    public Search(Node initNode, Node goalNode) {
        this.initNode = initNode;
        this.goalNode = goalNode;
        isOver = initNode.equals(goalNode);
    }

    public boolean isOver() {
        return isOver;
    }

    public abstract boolean next();
    public abstract int getStepCount();
    public abstract int getNodesCount();
}
