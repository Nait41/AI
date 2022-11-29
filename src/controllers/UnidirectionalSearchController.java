package controllers;

import domain.NodeTableView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import tree.Node;
import tree.search.DeepFirstSearch;
import tree.search.UnidirectionalSearch;

import java.util.ListIterator;

public abstract class UnidirectionalSearchController<Border> extends SearchController {
    protected ListIterator<Node> borderIt;
    protected UnidirectionalSearch<Border> search;
    protected boolean wasBorderNext;

    @FXML
    protected NodeTableView borderTable;

    @Override
    protected void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(search.getSolutionNode() != null ?
                "Решение успешно найдено!" : "Решения не существует!");
        alert.showAndWait();
    }

    protected void nextBorder() {
        if (borderIt.hasNext()) {
            borderTable.setNode(borderIt.next());
            wasBorderNext = true;
        }
    }

    protected void prevBorder() {
        if (borderIt.hasPrevious()) {
            borderTable.setNode(borderIt.previous());
            wasBorderNext = false;
        }
    }

    protected abstract void NewValueSetting(Node node);
}
