package controllers;

import apps.ChoiceApp;
import apps.AStarApp;
import domain.NodeTableView;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import tree.Node;
import tree.search.AStarSearch;
import tree.search.DeepFirstSearch;
import tree.search.heuristics.HeuristicI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AStarController extends SearchController {
    @FXML
    public Label heuristicLabel;

    @FXML
    private Label depthLabel;

    @FXML
    private Label costInfo;

    @FXML
    private Label stepInfo;

    @FXML
    private Button closeButton;

    @FXML
    private AnchorPane mainPanel;

    @FXML
    private NodeTableView mainTable_1;

    @FXML
    private NodeTableView mainTable_2;

    @FXML
    private NodeTableView mainTable_3;

    @FXML
    private NodeTableView mainTable_4;

    @FXML
    private NodeTableView mainTable_5;

    @FXML
    private NodeTableView borderTable;

    @FXML
    private Button borderNextButton;

    @FXML
    private Label borderLabel;

    @FXML
    private Label nodesCountInfo;

    ArrayList<NodeTableView> tableList = new ArrayList<>();
    protected Iterator<Pair<Node, Integer>> borderIt;

    @FXML
    private Button runAuto;

    @FXML
    private Button runStep;

    private AStarSearch search;

    public void tableInit() {
        tableList.add(mainTable_1);
        tableList.add(mainTable_2);
        tableList.add(mainTable_3);
        tableList.add(mainTable_4);
        tableList.add(mainTable_5);
        for (var table : tableList){
            table.setEditable(false);
        }
    }

    @FXML
    void initialize() throws FileNotFoundException, InterruptedException {
        search = new AStarSearch(AStarApp.initNode, AStarApp.goalNode, AStarApp.Heuristic);
        mainTable_1.setNode(AStarApp.initNode);
        borderIt = search.getBorder().iterator();
        borderTable.setNode(borderIt.next().getKey());
        tableInit();
        runAuto.setOnAction(ActionEvent -> {
            runAuto.setDisable(true);
            runStep.setDisable(true);
            closeButton.setDisable(true);
            AStarController.MyService service = new AStarController.MyService();
            service.setOnSucceeded((EventHandler<WorkerStateEvent>) t -> {
                NewValueSetting(search.getSolutionNode());
                showAlert();
                borderTable.setNode(null);
                borderNextButton.setDisable(true);
                closeButton.setDisable(false);
            });
            service.start();
        });

        runStep.setOnAction(ActionEvent -> {
            if (search.next()) {
                borderIt = search.getBorder().iterator();
                nextBorder();
                NewValueSetting(search.getCurrentNode());
            }
            else {
                showAlert();
                borderTable.setNode(null);
                borderNextButton.setDisable(true);
                runAuto.setDisable(true);
                runStep.setDisable(true);
            }
        });

        borderNextButton.setOnAction(ActionEvent -> {
            nextBorder();
        });

        closeButton.setOnAction(ActionEvent -> {
            ChoiceApp choiceApp = new ChoiceApp(AStarApp.initNode, AStarApp.goalNode);
            try {
                choiceApp.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    private void NewValueSetting(Node node) {
        if (node != null) {
            mainTable_1.setNode(node);

            setInfoToLabel(depthLabel, Integer.toString(node.getDepth()));
            setInfoToLabel(costInfo, Integer.toString(node.getPathCost()));
            setChilds(tableList.subList(1,5), search, node);
        } else {
            setInfoToLabel(depthLabel, "-");
            setInfoToLabel(costInfo, "-");
        }
        setInfoToLabel(stepInfo, Integer.toString(search.getStepCount()));
        setInfoToLabel(nodesCountInfo, Integer.toString(search.getNodesCount()));
        setInfoToLabel(heuristicLabel, Integer.toString(search.getHeuristic()));
    }

    @Override
    protected void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(search.getSolutionNode() != null ?
                "Решение успешно найдено!" : "Решения не существует!");
        alert.showAndWait();
    }

    protected void setChilds(List<NodeTableView> tableList, AStarSearch search, Node node) {
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

    protected void nextBorder() {
        if (borderIt.hasNext()) {
            borderTable.setNode(borderIt.next().getKey());
        }
    }

    private class MyService extends Service {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    if (!search.isOver()) {
                        while (search.next());
                    }
                    return null;
                }
            };
        }
    }
}
