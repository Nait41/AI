package tree.search;

import tree.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class DeepFirstSearch extends Search {
    protected Node solutionNode;
    protected HashSet<Node> nodes;
    protected Stack<Node> searchStack;

    public DeepFirstSearch(Node initNode, Node goalNode) {
        super(initNode, goalNode);
        nodes = new HashSet<>();
        searchStack = new Stack<>();
        nodes.add(initNode);
        searchStack.add(initNode);
    }

    public boolean Next() {
        if (!searchStack.isEmpty() && !isOver) {
            currentNode = searchStack.pop();
            ArrayList<Node> childs = currentNode.getRemainingValidChilds();
            for (var child : childs) {
                if (IsGoal(child)) {
                    solutionNode = child;
                    isOver = true;
                    break;
                } else if (!nodes.contains(child)) {
                    nodes.add(child);
                    searchStack.add(child);
                }
            }
        } else if (searchStack.isEmpty()) {
            isOver = true;
        }
        return !isOver;
    }
}
