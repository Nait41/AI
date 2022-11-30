package tree;

import javafx.util.Pair;
import tree.action.*;

import java.util.ArrayList;
import java.util.Objects;

public class Node {

    private ArrayList<ArrayList<Integer>> state;
    private Pair<Integer,Integer> emptyIndexes;
    private Node parent;
    private Action generatingAction;
    private int pathCost = 0;
    private int depth = 0;
    static private final ArrayList<Action> createActions = new ArrayList<>(){{
        add(new LeftAction());
        add(new RightAction());
        add(new UpAction());
        add(new DownAction());
    }};

    public Node(ArrayList<ArrayList<Integer>> state,
                Pair<Integer,Integer> emptyIndexes)
    {
        this.state = state;
        this.emptyIndexes = emptyIndexes;
    }

    public Node(ArrayList<ArrayList<Integer>> state,
                Pair<Integer,Integer> emptyIndexes,
                Node parent,
                Action generatingAction)
    {
        this(state, emptyIndexes);
        this.parent = parent;
        this.generatingAction = generatingAction;
        pathCost = parent.pathCost + 1;
        depth = parent.depth + 1;
    }

    public ArrayList<Node> getChilds() {
        ArrayList<Node> childs = new ArrayList<>();
        for (var action : createActions) {
            if (generatingAction == null || !action.equals(generatingAction.getOpposite())) {
                Node node = action.apply(this);
                if (node != null)
                    childs.add(node);
            }
        }
        return childs;
    }

    public ArrayList<ArrayList<Integer>> getState() {
        return state;
    }

    public Pair<Integer, Integer> getEmptyIndexes() {
        return emptyIndexes;
    }

    public Node getParent() {
        return parent;
    }

    public Action getGeneratingAction() {
        return generatingAction;
    }

    public int getPathCost() {
        return pathCost;
    }

    public int getDepth() {
        return depth;
    }

    public Pair<Integer, Integer> getDigitPosition(int digit) {
        if (digit == 0)
            return getEmptyIndexes();
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j)
                if (state.get(i).get(j).equals(digit))
                    return new Pair<>(i,j);
        return null;
    }

    public void swap(Pair<Integer, Integer> fstPos, Pair<Integer, Integer> secPos) {
        if (isPosInRange(fstPos) && isPosInRange(secPos)) {
            Integer fstVal = state.get(fstPos.getKey()).get(fstPos.getValue());
            Integer secVal = state.get(secPos.getKey()).get(secPos.getValue());
            state.get(fstPos.getKey()).set(fstPos.getValue(), secVal);
            state.get(secPos.getKey()).set(secPos.getValue(), fstVal);
            if (fstPos.equals(emptyIndexes))
                emptyIndexes = secPos;
            else if (secPos.equals(emptyIndexes))
                emptyIndexes = fstPos;
        }
    }

    private boolean isPosInRange(Pair<Integer, Integer> pos) {
        return pos.getKey() >= 0 && pos.getKey() <= 8 && pos.getValue() >= 0 && pos.getValue() <= 8;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Node node))
            return false;

        for (int rowIndex = 0; rowIndex < state.size(); ++rowIndex)
            for (int columnIndex = 0; columnIndex < state.size(); ++columnIndex)
                if (!Objects.equals(node.state.get(rowIndex).get(columnIndex), state.get(rowIndex).get(columnIndex)))
                    return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
}
