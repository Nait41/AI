package tree.search;

import tree.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch extends UnidirectionalSearch<Queue<Node>> {
    public BreadthFirstSearch(Node initNode, Node goalNode) {
        super(initNode, goalNode);
        border = new LinkedList<>();
        border.offer(initNode);
    }

    public boolean next() {
        if (!border.isEmpty() && !isOver) {
            stepCount++;
            currentNode = border.poll();
            waitingNodes.remove(currentNode);
            visitedNodes.add(currentNode);
            ArrayList<Node> childNodes = currentNode.getChilds();
            for (var childNode : childNodes) {
                if (isGoal(childNode)) {
                    solutionNode = childNode;
                    isOver = true;
                    break;
                } else if (!isRepetition(childNode)) {
                    waitingNodes.add(childNode);
                    border.offer(childNode);
                }
            }
        } else {
            isOver = border.isEmpty();
        }
        return !isOver;
    }
}
