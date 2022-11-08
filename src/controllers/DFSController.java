package controllers;

import apps.ChoiceApp;
import domain.NodeTableView;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tree.Node;
import tree.search.DeepFirstSearch;
import tree.search.UnidirectionalSearch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class DFSController extends SearchController {

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

    ArrayList<NodeTableView> tableList = new ArrayList<>();

    @FXML
    private Button runAuto;

    @FXML
    private Button runStep;

    private Node initNode;
    private Node goalNode;
    private UnidirectionalSearch search;

    public void preset(Node initNode, Node goalNode) {
        this.initNode = initNode;
        this.goalNode = goalNode;
        search = new DeepFirstSearch(initNode, goalNode);
        mainTable_1.setNode(initNode);
    }

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
        tableInit();
        runAuto.setOnAction(ActionEvent -> {
            runAuto.setDisable(true);
            runStep.setDisable(true);
            closeButton.setDisable(true);
            MyService service = new MyService();
            service.setOnSucceeded((EventHandler<WorkerStateEvent>) t -> {
                NewValueSetting(search.getSolutionNode());
                showAlert();
                closeButton.setDisable(false);
            });
            service.start();
        });

        runStep.setOnAction(ActionEvent -> {
            if (search.next()) {
                NewValueSetting(search.getCurrentNode());
            }
            else {
                showAlert();
                runAuto.setDisable(true);
                runStep.setDisable(true);
            }
        });

        closeButton.setOnAction(ActionEvent -> {
            ChoiceApp choiceApp = new ChoiceApp(initNode, goalNode);
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
