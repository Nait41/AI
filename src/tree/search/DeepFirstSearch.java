package tree.search;

import tree.Node;

import java.util.ArrayList;
import java.util.Stack;

public class DeepFirstSearch extends UnidirectionalSearch {
    protected Stack<Node> searchStack;

    public DeepFirstSearch(Node initNode, Node goalNode) {
        super(initNode, goalNode);
        searchStack = new Stack<>();
        searchStack.push(initNode);
    }

    public boolean Next() {
        if (!searchStack.isEmpty() && !isOver) {
            stepCount++;
            currentNode = searchStack.pop();
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
                    searchStack.push(childNode);
                }
            }
        } else {
            isOver = searchStack.isEmpty();
        }
        return !isOver;
    }
}
