package tree.search;

import sets.AccessibleHashSet;
import tree.Node;

import java.util.HashMap;

public abstract class UnidirectionalSearch extends Search {
    protected Node solutionNode;
    protected Node currentNode;
    //protected HashMap<Node, Node> waitingNodes;
    //protected HashMap<Node, Node> visitedNodes;
    protected AccessibleHashSet<Node> waitingNodes;
    protected AccessibleHashSet<Node> visitedNodes;
    protected int stepCount;

    public UnidirectionalSearch(Node initNode, Node goalNode) {
        super(initNode, goalNode);
        currentNode = initNode;
        stepCount = 0;
        //visitedNodes = new HashMap<>();
        //waitingNodes = new HashMap<>();
        //waitingNodes.put(initNode,initNode);
        visitedNodes = new AccessibleHashSet<>();
        waitingNodes = new AccessibleHashSet<>();
        waitingNodes.add(initNode);
    }

    public Node getNodeWithSameState(Node node) {
        /*if (waitingNodes.containsKey(node))
            return waitingNodes.get(node);
        else if (visitedNodes.containsKey(node))
            return waitingNodes.get(node);
        return null;*/
        if (waitingNodes.contains(node))
            return waitingNodes.get(node);
        else if (visitedNodes.contains(node))
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
        //return waitingNodes.containsKey(node);
        return waitingNodes.contains(node);
    }

    protected boolean isRepetition(Node node) {
        return isWaiting(node) || visited(node);
    }

    public boolean visited(Node node) {
        //return visitedNodes.containsKey(node);
        return visitedNodes.contains(node);
    }

    public Node getCurrentNode() {
        return currentNode;
    }
}
