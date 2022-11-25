package controllers;

import apps.ChoiceApp;
import domain.NodeTableView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tree.Node;
import tree.search.UnidirectionalSearch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AStarController extends SearchController {
    @FXML
    private Label depthLabel;

    @FXML
    private Label costInfo;

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

        });

        runStep.setOnAction(ActionEvent -> {

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

    @Override
    protected void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(search.getSolutionNode() != null ?
                "Решение успешно найдено!" : "Решения не существует!");
        alert.showAndWait();
    }
}
