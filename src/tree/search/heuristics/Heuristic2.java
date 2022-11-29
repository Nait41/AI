package tree.search.heuristics;

import static java.lang.Math.abs;
import javafx.util.Pair;
import java.util.ArrayList;
import tree.Node;

public class Heuristic2 implements HeuristicI{
    @Override
    public int compute(Node currentNode, Node goalNode) {
        int path = 0;
        ArrayList<ArrayList<Integer>> currentState = currentNode.getState();
        Pair<Integer, Integer> goalCords;
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j) {
                goalCords = goalNode.getDigitPosition(currentState.get(i).get(j));
                path += abs(i - goalCords.getKey()) + abs(j - goalCords.getValue());
            }
        return path;
    }
}
