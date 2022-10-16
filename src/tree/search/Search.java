package tree.search;

import tree.Node;

public abstract class Search {
    protected Node initNode;
    protected Node goalNode;

    public Search(Node initNode, Node goalNode) {
        this.initNode = initNode;
        this.goalNode = goalNode;
    }

    protected boolean IsGoal(Node node) {
        return node.equals(goalNode);
    }

    public abstract void Start();
}
