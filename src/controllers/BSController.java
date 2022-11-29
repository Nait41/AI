package controllers;

import apps.BSApp;
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
import tree.search.BidirectionalBreadthFirstSearch;
import tree.search.BidirectionalSearch;
import tree.search.BreadthFirstSearch;
import tree.search.UnidirectionalSearch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BSController extends SearchController {
    @FXML
    public Label directDepthLabel;

    @FXML
    public Label reverseDepthLabel;

    @FXML
    public Label directCostInfo;

    @FXML
    public Label reverseCostInfo;

    @FXML
    public Label stepInfo;

    @FXML
    private Button closeButton;

    @FXML
    private AnchorPane mainPanel;

    @FXML
    private NodeTableView mainTable_1;

    @FXML
    private NodeTableView mainTable_10;

    @FXML
    private NodeTableView mainTable_2;

    @FXML
    private NodeTableView mainTable_3;

    @FXML
    private NodeTableView mainTable_4;

    @FXML
    private NodeTableView mainTable_5;

    @FXML
    private NodeTableView mainTable_6;

    @FXML
    private NodeTableView mainTable_7;

    @FXML
    private NodeTableView mainTable_8;

    @FXML
    private NodeTableView mainTable_9;

    ArrayList<NodeTableView> tableList = new ArrayList<>();

    @FXML
    private Button runAuto;

    @FXML
    private Button runStep;

    private BidirectionalSearch search;
    private UpdatedData directData;
    private UpdatedData reverseData;

    public void tableInit() {
        tableList.add(mainTable_1);
        tableList.add(mainTable_2);
        tableList.add(mainTable_3);
        tableList.add(mainTable_4);
        tableList.add(mainTable_5);
        tableList.add(mainTable_6);
        tableList.add(mainTable_7);
        tableList.add(mainTable_8);
        tableList.add(mainTable_9);
        tableList.add(mainTable_10);
        for (var table : tableList){
            table.setEditable(false);
        }
    }

    @FXML
    void initialize() throws FileNotFoundException, InterruptedException {
        search = new BidirectionalBreadthFirstSearch(BSApp.initNode, BSApp.goalNode);
        mainTable_1.setNode(BSApp.initNode);
        mainTable_6.setNode(BSApp.goalNode);
        tableInit();
        directData = new UpdatedData(directCostInfo, directDepthLabel, mainTable_1, tableList.subList(1,5));
        reverseData = new UpdatedData(reverseCostInfo, reverseDepthLabel, mainTable_6, tableList.subList(6,10));
        runAuto.setOnAction(ActionEvent -> {
            runAuto.setDisable(true);
            runStep.setDisable(true);
            closeButton.setDisable(true);
            BSController.MyService service = new BSController.MyService();
            service.setOnSucceeded((EventHandler<WorkerStateEvent>) t -> {
                NewValueSetting( search.getDirectSolutionNode(), search.getReverseSolutionNode());
                showAlert();
                closeButton.setDisable(false);
            });
            service.start();
        });

        runStep.setOnAction(ActionEvent -> {
            if (search.next()) {
                NewValueSetting(search.getDirectSearch().getCurrentNode(), search.getReverseSearch().getCurrentNode());
            }
            else {
                showAlert();
                runAuto.setDisable(true);
                runStep.setDisable(true);
            }
        });

        closeButton.setOnAction(ActionEvent -> {
            ChoiceApp choiceApp = new ChoiceApp(BSApp.initNode, BSApp.goalNode);
            try {
                choiceApp.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    private void NewValueSetting(Node directNode, Node reverseNode) {
        NewValueSetting(directNode, search.getDirectSearch(), directData);
        NewValueSetting(reverseNode, search.getReverseSearch(), reverseData);
        setInfoToLabel(stepInfo, Integer.toString(search.getStepCount()));
    }

    private void NewValueSetting(Node Node, UnidirectionalSearch search, UpdatedData data) {
        if (Node != null) {
            data.table.setNode(Node);
            setInfoToLabel(data.depthLabel, Integer.toString(Node.getDepth()));
            setInfoToLabel(data.costInfo, Integer.toString(Node.getPathCost()));
            setChilds(data.tables, search, Node);
        } else {
            setInfoToLabel(data.depthLabel, "-");
            setInfoToLabel(data.costInfo, "-");
        }
    }

    @Override
    protected void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(search.getDirectSolutionNode() != null && search.getReverseSolutionNode() != null ?
                "Решение успешно найдено!" : "Решения не существует!");
        alert.showAndWait();
    }

    protected class MyService extends Service {
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

    protected class UpdatedData {
        @FXML
        public Label costInfo;
        @FXML
        public Label depthLabel;
        @FXML
        public NodeTableView table;
        public List tables;

        public UpdatedData(Label costInfo, Label depthLabel, NodeTableView table, List tables) {
            this.costInfo = costInfo;
            this.depthLabel = depthLabel;
            this.table = table;
            this.tables = tables;
        }
    }
}
