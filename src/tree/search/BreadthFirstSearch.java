package tree.search;

import tree.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch extends UnidirectionalSearch {
    protected Queue<Node> searchQueue;

    public BreadthFirstSearch(Node initNode, Node goalNode) {
        super(initNode, goalNode);
        searchQueue = new LinkedList<>();
        searchQueue.offer(initNode);
    }

    public boolean Next() {
        if (!searchQueue.isEmpty() && !isOver) {
            stepCount++;
            currentNode = searchQueue.poll();
            waitingNodes.remove(currentNode);
            visitedNodes.put(currentNode,currentNode);
            ArrayList<Node> childNodes = currentNode.getRemainingValidChilds();
            for (var childNode : childNodes) {
                if (childNode.equals(goalNode)) {
                    solutionNode = childNode;
                    isOver = true;
                    break;
                } else if (!IsWaiting(childNode) && !Visited(childNode)) {
                    waitingNodes.put(childNode,childNode);
                    searchQueue.offer(childNode);
                }
            }
        } else {
            isOver = searchQueue.isEmpty();
        }
        return !isOver;
    }
}
