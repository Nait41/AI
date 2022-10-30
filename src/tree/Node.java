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
    private int createIndex = 0;
    static private final ArrayList<Action> createActions;

    static {
        createActions = new ArrayList<>();
        createActions.add(new LeftAction());
        createActions.add(new RightAction());
        createActions.add(new UpAction());
        createActions.add(new DownAction());
    }

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

    public Node getNextValidChild() {
        Node child = null;
        while (child == null && createIndex < 4) {
            child = createActions.get(createIndex).Apply(this);
            ++createIndex;
        }
        return child;
    }

    public ArrayList<Node> getRemainingValidChilds() {
        ArrayList<Node> childs = new ArrayList<>();
        Node child;
        do {
            child = getNextValidChild();
            if (child != null)
                childs.add(child);
        } while (child != null);
        return childs;
    }

    public ArrayList<Node> getChilds() {
        ArrayList<Node> childs = new ArrayList<>();
        for (var action : createActions) {
            Node node = action.Apply(this);
            if (node != null)
                childs.add(node);
        }
        return childs;
    }

    public ArrayList<ArrayList<Integer>> getState() {
        return state;
    }

    public void setState(ArrayList<ArrayList<Integer>> state) {
        this.state = state;
    }

    public Pair<Integer, Integer> getEmptyIndexes() {
        return emptyIndexes;
    }

    public void setEmptyIndexes(Pair<Integer, Integer> emptyIndexes) {
        this.emptyIndexes = emptyIndexes;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Action getGeneratingAction() {
        return generatingAction;
    }

    public void setGeneratingAction(Action generatingAction) {
        this.generatingAction = generatingAction;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public int getCreateIndex() {
        return createIndex;
    }

    public void setCreateIndex(int createIndex) {
        this.createIndex = createIndex;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
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
