package tree.search.heuristics;

import tree.Node;

public interface HeuristicI {
    int compute(Node currentNode, Node goalNode);
}
