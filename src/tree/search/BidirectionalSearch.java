package tree.search;

import tree.Node;

public abstract class BidirectionalSearch extends Search {
    protected UnidirectionalSearch directSearch;
    protected UnidirectionalSearch reverseSearch;
    protected Node directSolutionNode;
    protected Node reverseSolutionNode;

    public BidirectionalSearch(Node initNode, Node goalNode) {
        super(initNode, goalNode);
    }

    public boolean next() {
        if (!isOver) {
            directSearch.next();
            reverseSearch.next();
            checkForSolution();
        }
        return !isOver;
    }

    public UnidirectionalSearch getDirectSearch() {
        return directSearch;
    }

    public UnidirectionalSearch getReverseSearch() {
        return reverseSearch;
    }

    public Node getDirectSolutionNode() {
        return directSolutionNode;
    }

    public Node getReverseSolutionNode() {
        return reverseSolutionNode;
    }

    public int getNodesCount() {
        return directSearch.getNodesCount() + reverseSearch.getNodesCount();
    }

    public int getStepCount() {
        return directSearch.getStepCount() + reverseSearch.getStepCount();
    }

    private void checkForSolution() {
        if (directSearch.isRepetition(reverseSearch.getCurrentNode())) {
            directSolutionNode = directSearch.getNodeWithSameState(reverseSearch.getCurrentNode());
            reverseSolutionNode = reverseSearch.getCurrentNode();
            isOver = true;
        } else if (reverseSearch.isRepetition(directSearch.getCurrentNode())) {
            directSolutionNode = directSearch.getCurrentNode();
            reverseSolutionNode = reverseSearch.getNodeWithSameState(directSearch.getCurrentNode());
            isOver = true;
        } else if (directSearch.isOver()) {
            directSolutionNode = directSearch.getSolutionNode();
            isOver = true;
        } else if (reverseSearch.isOver()) {
            reverseSolutionNode = reverseSearch.getSolutionNode();
            isOver = true;
        }
    }
}
