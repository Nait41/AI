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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class BSController extends SearchController {
    @FXML
    public Label directDepthLabel;

    @FXML
    public Label reverseDepthLabel;

    @FXML
    public Label directCostInfo;

    @FXML
    public Label reserveCostInfo;

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

    //private Node initNode;
    //private Node goalNode;
    private BidirectionalSearch search;

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
            //ChoiceApp choiceApp = new ChoiceApp(initNode, goalNode);
            try {
                choiceApp.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    /*public void preset(Node initNode, Node goalNode) {
        this.initNode = initNode;
        this.goalNode = goalNode;
        search = new BidirectionalBreadthFirstSearch(initNode, goalNode);
        mainTable_1.setNode(initNode);
        mainTable_6.setNode(goalNode);
    }*/

    private void NewValueSetting(Node directNode, Node reverseNode) {
        if (directNode != null) {
            mainTable_1.setNode(directNode);
            setInfoToLabel(directDepthLabel, Integer.toString(directNode.getDepth()));
            setInfoToLabel(directCostInfo, Integer.toString(reverseNode.getPathCost()));
            setChilds(tableList.subList(1,5), search.getDirectSearch(), directNode);
        } else {
            setInfoToLabel(directDepthLabel, "-");
            setInfoToLabel(directCostInfo, "-");
        }
        if (reverseNode != null) {
            mainTable_6.setNode(reverseNode);
            setInfoToLabel(reverseDepthLabel, Integer.toString(reverseNode.getDepth()));
            setInfoToLabel(reserveCostInfo, Integer.toString(reverseNode.getPathCost()));
            setChilds(tableList.subList(6,10), search.getReverseSearch(), reverseNode);
        } else {
            setInfoToLabel(reverseDepthLabel, "-");
            setInfoToLabel(reserveCostInfo, "-");
        }
        setInfoToLabel(stepInfo, Integer.toString(search.getStepCount()));
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
}
