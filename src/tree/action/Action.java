package tree.action;

import javafx.util.Pair;
import tree.Node;

import java.util.ArrayList;

public abstract class Action {

    public abstract Node Apply(Node node);
    abstract boolean IsPossible(Node node);

    Node Apply(Node node, int rowOffset, int columnOffset) {
        if (IsPossible(node)) {
            Pair<Integer, Integer> newEmptyIndexes = new Pair<>(node.getEmptyIndexes().getKey() + rowOffset,
                    node.getEmptyIndexes().getValue() + columnOffset);
            ArrayList<ArrayList<Integer>> newState = new ArrayList<>(node.getState().size());
            for (int rowIndex = 0; rowIndex < node.getState().size(); ++rowIndex) {
                newState.add(new ArrayList<>(node.getState().size()));
                for (Integer elem : node.getState().get(rowIndex))
                    newState.get(rowIndex).add(elem);
            }
            Swap(newState, node.getEmptyIndexes(), newEmptyIndexes);
            return new Node(newState, newEmptyIndexes, node, this, node.getPathCost() + 1);
        }
        return null;
    }

    private void Swap(ArrayList<ArrayList<Integer>> state, Pair<Integer, Integer> fstPos, Pair<Integer, Integer> secPos) {
        int fstValue = state.get(fstPos.getKey()).get(fstPos.getValue());
        int secValue = state.get(secPos.getKey()).get(secPos.getValue());
        state.get(fstPos.getKey()).set(fstPos.getValue(), secValue);
        state.get(secPos.getKey()).set(secPos.getValue(), fstValue);
    }
}
