package tree.search;

import tree.Node;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Search {
    protected Node initNode;
    protected Node goalNode;
    protected boolean isOver;

    public Search(Node initNode, Node goalNode) {
        this.initNode = initNode;
        this.goalNode = goalNode;
        isOver = initNode.equals(goalNode);
    }

    public boolean IsOver() {
        return isOver;
    }

    public abstract boolean Next();
    public abstract int GetStepCount();
    public abstract int GetNodesCount();
}
