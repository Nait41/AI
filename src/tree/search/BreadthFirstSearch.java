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

    public boolean next() {
        if (!searchQueue.isEmpty() && !isOver) {
            stepCount++;
            currentNode = searchQueue.poll();
            waitingNodes.remove(currentNode);
            //visitedNodes.put(currentNode,currentNode);
            visitedNodes.add(currentNode);
            //ArrayList<Node> childNodes = currentNode.getRemainingValidChilds();
            ArrayList<Node> childNodes = currentNode.getChilds();
            for (var childNode : childNodes) {
                if (isGoal(childNode)) {
                    solutionNode = childNode;
                    isOver = true;
                    break;
                } else if (!isRepetition(childNode)) {
                    //waitingNodes.put(childNode,childNode);
                    waitingNodes.add(childNode);
                    searchQueue.offer(childNode);
                }
            }
        } else {
            isOver = searchQueue.isEmpty();
        }
        return !isOver;
    }
}
