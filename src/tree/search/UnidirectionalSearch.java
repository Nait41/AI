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

    public Node GetNodeWithSameState(Node node) {
        if (waitingNodes.containsKey(node))
            return waitingNodes.get(node);
        else if (visitedNodes.containsKey(node))
            return waitingNodes.get(node);
        return null;
    }

    public Node GetSolutionNode() {
        return solutionNode;
    }

    public int GetNodesCount() {
        return waitingNodes.size() + visitedNodes.size();
    }

    public int GetStepCount() {
        return stepCount;
    }

    public boolean IsWaiting(Node node) {
        return waitingNodes.containsKey(node);
    }

    public boolean Visited(Node node) {
        return visitedNodes.containsKey(node);
    }

    public Node GetCurrentNode() {
        return currentNode;
    }
}
