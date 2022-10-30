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

    public boolean Next() {
        if (!isOver) {
            directSearch.Next();
            reverseSearch.Next();
            CheckForSolution();
        }
        return !isOver;
    }

    public UnidirectionalSearch GetDirectSearch() {
        return directSearch;
    }

    public UnidirectionalSearch GetReverseSearch() {
        return reverseSearch;
    }

    public Node GetDirectSolutionNode() {
        return directSolutionNode;
    }

    public Node GetReverseSolutionNode() {
        return reverseSolutionNode;
    }

    public int GetNodesCount() {
        return directSearch.GetNodesCount() + reverseSearch.GetNodesCount();
    }

    public int GetStepCount() {
        return directSearch.GetStepCount() + reverseSearch.GetStepCount();
    }

    private void CheckForSolution() {
        if (directSearch.IsWaiting(reverseSearch.GetCurrentNode()) || directSearch.Visited(reverseSearch.GetCurrentNode())) {
            directSolutionNode = directSearch.GetNodeWithSameState(reverseSearch.GetCurrentNode());
            reverseSolutionNode = reverseSearch.GetCurrentNode();
            isOver = true;
        } else if (reverseSearch.IsWaiting(directSearch.GetCurrentNode()) || reverseSearch.Visited(directSearch.GetCurrentNode())) {
            directSolutionNode = directSearch.GetCurrentNode();
            reverseSolutionNode = reverseSearch.GetNodeWithSameState(directSearch.GetCurrentNode());
            isOver = true;
        }
    }
}
