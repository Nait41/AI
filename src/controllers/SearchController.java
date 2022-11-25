package controllers;

import domain.NodeTableView;
import tree.Node;
import tree.search.UnidirectionalSearch;

import java.util.ArrayList;
import java.util.List;

public abstract class SearchController extends BasicController {

    protected abstract void showAlert();

    protected void setChilds(List<NodeTableView> tableList, UnidirectionalSearch search, Node node) {
        if (node != null) {
            ArrayList<Node> childs = node.getChilds();
            for (int i = 0; i < 4; ++i) {
                tableList.get(i).setStyle("-fx-background-color: #FFF8DC");
                if (i < childs.size()) {
                    tableList.get(i ).setNode(childs.get(i));
                    if (search.visited(childs.get(i)))
                        tableList.get(i).setStyle("-fx-background-color: #FF0000");
                } else {
                    tableList.get(i).setNode(null);
                    tableList.get(i).setStyle("-fx-background-color: #FFF8DC");
                }
            }
        }
    }
}
