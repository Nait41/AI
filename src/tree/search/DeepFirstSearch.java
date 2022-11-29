package tree.search;

import tree.Node;

import java.util.ArrayList;
import java.util.Stack;

public class DeepFirstSearch extends UnidirectionalSearch<Stack<Node>> {
    public DeepFirstSearch(Node initNode, Node goalNode) {
        super(initNode, goalNode);
        border = new Stack<>();
        border.push(initNode);
    }

    public boolean next() {
        if (!border.isEmpty() && !isOver) {
            stepCount++;
            currentNode = border.pop();
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
                    border.push(childNode);
                }
            }
        } else {
            isOver = border.isEmpty();
        }
        return !isOver;
    }
}
