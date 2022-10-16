package tree.action;

import tree.Node;

public class RightAction extends Action {
    @Override
    public Node Apply(Node node) {
        return Apply(node, 0, 1);
    }

    @Override
    boolean IsPossible(Node node) {
        return node.getEmptyIndexes().getValue() != 2;
    }
}
