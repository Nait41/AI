package tree.action;

import tree.Node;

public class DownAction extends Action {
    @Override
    public Node apply(Node node) {
        return apply(node, 1, 0);
    }

    @Override
    public boolean isPossible(Node node) {
        return node.getEmptyIndexes().getKey() != 2;
    }
}
