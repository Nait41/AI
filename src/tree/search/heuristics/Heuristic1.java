package tree.search.heuristics;

import java.util.ArrayList;
import tree.Node;

public class Heuristic1 implements HeuristicI{
    @Override
    public int compute(Node currentNode, Node goalNode) {
        int path = 0;
        ArrayList<ArrayList<Integer>> currentState = currentNode.getState();
        ArrayList<ArrayList<Integer>> goalState = goalNode.getState();
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j)
                if (currentState.get(i).get(j) != goalState.get(i).get(j))
                    path++;
        return path;
    }
}
