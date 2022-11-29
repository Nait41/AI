package tree.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Queue;
import java.util.PriorityQueue;
import javafx.util.Pair;
import sets.AccessibleHashSet;
import tree.Node;
import tree.search.heuristics.HeuristicI;

public class AStarSearch extends Search{
    protected Node solutionNode;
    protected Node currentNode;
    protected int currentHeuristic;
    protected Queue<Pair<Node, Integer>> waitingNodes;
    protected AccessibleHashSet<Node> visitedNodes;
    protected HeuristicI Heuristic;
    protected int stepCount;

    public AStarSearch(Node initNode, Node goalNode, HeuristicI Heuristic) {
        super(initNode, goalNode);
        currentNode = initNode;
        stepCount = 0;
        this.Heuristic = Heuristic;
        //visitedNodes = new HashMap<>();
        //waitingNodes = new HashMap<>();
        //waitingNodes.put(initNode,initNode);
        visitedNodes = new AccessibleHashSet<>();
        waitingNodes = new PriorityQueue<>(new CustomHeuristicComparator());
        addToQueue(initNode);
    }

    @Override
    public boolean next() {
        passVisited();
        if (!waitingNodes.isEmpty() && !isOver){
            stepCount++;
            Pair<Node, Integer> currentPair = waitingNodes.poll();
            currentNode = currentPair.getKey();
            currentHeuristic = currentPair.getValue();
            visitedNodes.add(currentNode);
            ArrayList<Node> childNodes = currentNode.getChilds();
            for (var childNode : childNodes) {
                if (childNode.equals(goalNode)) {
                    solutionNode = childNode;
                    isOver = true;
                    break;
                } else if (!visited(childNode)) {
                    addToQueue(childNode);
                }
            }
        }
        else{
            isOver = waitingNodes.isEmpty();
        }
        return !isOver;
    }

    protected void passVisited(){
        while (!waitingNodes.isEmpty() && visitedNodes.contains(waitingNodes.peek().getKey())) {
            waitingNodes.poll();
        }
    }

    public Node getSolutionNode() {
        return solutionNode;
    }

    @Override
    public int getStepCount() { return stepCount; }

    @Override
    public int getNodesCount() { return waitingNodes.size() + visitedNodes.size(); }

    public boolean visited(Node node) {
        //return visitedNodes.containsKey(node);
        return visitedNodes.contains(node);
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    protected void addToQueue(Node node){
        waitingNodes.add(new Pair<>(node, Heuristic.compute(node, goalNode)));
    }

    protected static class CustomHeuristicComparator implements Comparator<Pair<Node, Integer>> {
        @Override
        public int compare(Pair<Node, Integer> o1, Pair<Node, Integer> o2) {
            return o1.getKey().getPathCost() + o1.getValue() > o2.getKey().getPathCost() + o2.getValue()  ? 1 : -1;
        }
    }
}
