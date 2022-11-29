package tree.search;

import sets.AccessibleHashSet;
import tree.Node;

import java.util.HashMap;

public abstract class UnidirectionalSearch<Border> extends Search {
    protected Node solutionNode;
    protected Node currentNode;
    protected AccessibleHashSet<Node> waitingNodes;
    protected AccessibleHashSet<Node> visitedNodes;
    protected int stepCount;
    protected Border border;

    public UnidirectionalSearch(Node initNode, Node goalNode) {
        super(initNode, goalNode);
        currentNode = initNode;
        stepCount = 0;
        visitedNodes = new AccessibleHashSet<>();
        waitingNodes = new AccessibleHashSet<>();
        waitingNodes.add(initNode);
    }

    public Node getNodeWithSameState(Node node) {
        if (waitingNodes.contains(node))
            return waitingNodes.get(node);
        else if (visitedNodes.contains(node))
            return visitedNodes.get(node);
        return null;
    }

    public Node getSolutionNode() {
        return solutionNode;
    }

    public int getNodesCount() {
        return waitingNodes.size() + visitedNodes.size();
    }

    public int getStepCount() {
        return stepCount;
    }

    public boolean isWaiting(Node node) {
        return waitingNodes.contains(node);
    }

    protected boolean isRepetition(Node node) {
        return isWaiting(node) || visited(node);
    }

    public boolean visited(Node node) {
        return visitedNodes.contains(node);
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public Border getBorder() {
        return border;
    }
}
