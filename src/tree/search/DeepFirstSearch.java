package tree.search;

import tree.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class DeepFirstSearch extends Search {
    protected Node solutionNode;

    public DeepFirstSearch(Node initNode, Node goalNode) {
        super(initNode, goalNode);
    }

    public void Start() {
        boolean isGoal = false;
        HashSet<Node> nodes = new HashSet<>();
        Stack<Node> searchStack = new Stack<>();
        nodes.add(initNode);
        searchStack.add(initNode);
        while (!searchStack.isEmpty() && !isGoal) {
            Node node = searchStack.pop();
            ArrayList<Node> childs = node.getRemainingValidChilds();
            for (var child : childs) {
                if (IsGoal(child)) {
                    solutionNode = child;
                    isGoal = true;
                    break;
                } else if (!nodes.contains(child)) {
                    nodes.add(child);
                    searchStack.add(child);
                }
            }
        }
    }
}
