package tree.search;

import tree.Node;

public class BidirectionalBreadthFirstSearch extends BidirectionalSearch {
    public BidirectionalBreadthFirstSearch(Node initNode, Node goalNode) {
        super(initNode, goalNode);
        directSearch = new BreadthFirstSearch(initNode, goalNode);
        reverseSearch = new BreadthFirstSearch(goalNode, initNode);
    }
}
