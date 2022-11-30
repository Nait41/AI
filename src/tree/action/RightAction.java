package tree.action;

import tree.Node;

public class RightAction extends Action {
    @Override
    public Node apply(Node node) {
        return apply(node, 0, 1);
    }

    @Override
    public boolean isPossible(Node node) {
        return node.getEmptyIndexes().getValue() != 2;
    }

    @Override
    public Action getOpposite() {
        return new LeftAction();
    }
}
