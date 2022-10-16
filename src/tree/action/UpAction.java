package tree.action;

import tree.Node;

public class UpAction extends Action{
    @Override
    public Node Apply(Node node) {
        return Apply(node, -1, 0);
    }

    @Override
    boolean IsPossible(Node node) {
        return node.getEmptyIndexes().getKey() != 0;
    }
}
