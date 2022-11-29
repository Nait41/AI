package controllers;

import apps.ChoiceApp;
import apps.DFSApp;
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
import java.util.ListIterator;
import java.util.Stack;

public class DFSController extends UnidirectionalSearchController<Stack<Node>> {
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
    public Label borderLabel;

    @FXML
    public Button borderNextButton;

    @FXML
    public Button borderPrevButton;

    @FXML
    private Button runAuto;

    @FXML
    private Button runStep;

    @FXML
    public Label nodesCountInfo;

    ArrayList<NodeTableView> tableList = new ArrayList<>();

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
        search = new DeepFirstSearch(DFSApp.initNode, DFSApp.goalNode);
        mainTable_1.setNode(DFSApp.initNode);
        borderIt = search.getBorder().listIterator();
        borderTable.setNode(borderIt.next());
        wasBorderNext = true;
        tableInit();
        runAuto.setOnAction(ActionEvent -> {
            runAuto.setDisable(true);
            runStep.setDisable(true);
            closeButton.setDisable(true);
            MyService service = new MyService();
            service.setOnSucceeded((EventHandler<WorkerStateEvent>) t -> {
                NewValueSetting(search.getSolutionNode());
                showAlert();
                borderTable.setNode(null);
                borderNextButton.setDisable(true);
                borderPrevButton.setDisable(true);
                closeButton.setDisable(false);
            });
            service.start();
        });

        runStep.setOnAction(ActionEvent -> {
            if (search.next()) {
                borderIt = search.getBorder().listIterator();
                nextBorder();
                NewValueSetting(search.getCurrentNode());
            }
            else {
                showAlert();
                runAuto.setDisable(true);
                runStep.setDisable(true);
                borderTable.setNode(null);
                borderNextButton.setDisable(true);
                borderPrevButton.setDisable(true);
            }
        });

        closeButton.setOnAction(ActionEvent -> {
            ChoiceApp choiceApp = new ChoiceApp(DFSApp.initNode, DFSApp.goalNode);
            try {
                choiceApp.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });

        borderNextButton.setOnAction(ActionEvent -> {
            if (!wasBorderNext) {
                nextBorder();
            }
            nextBorder();
        });

        borderPrevButton.setOnAction(ActionEvent -> {
            if (wasBorderNext) {
                prevBorder();
            }
            prevBorder();
        });
    }

    protected void NewValueSetting(Node node) {
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
