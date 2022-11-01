package tree.search;

import tree.Node;

import java.util.HashMap;

public abstract class UnidirectionalSearch extends Search {
    protected Node solutionNode;
    protected Node currentNode;
    protected HashMap<Node, Node> waitingNodes;
    protected HashMap<Node, Node> visitedNodes;
    protected int stepCount;

    public UnidirectionalSearch(Node initNode, Node goalNode) {
        super(initNode, goalNode);
        currentNode = initNode;
        stepCount = 0;
        visitedNodes = new HashMap<>();
        waitingNodes = new HashMap<>();
        waitingNodes.put(initNode,initNode);
    }

    public Node getNodeWithSameState(Node node) {
        if (waitingNodes.containsKey(node))
            return waitingNodes.get(node);
        else if (visitedNodes.containsKey(node))
            return waitingNodes.get(node);
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
        return waitingNodes.containsKey(node);
    }

    public boolean visited(Node node) {
        return visitedNodes.containsKey(node);
    }

    public Node getCurrentNode() {
        return currentNode;
    }
}
