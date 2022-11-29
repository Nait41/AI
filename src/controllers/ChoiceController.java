package controllers;

import apps.AStarApp;
import apps.BSApp;
import apps.ChoiceApp;
import apps.DFSApp;
import domain.NodeTableView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tree.Node;
import tree.search.heuristics.Heuristic1;
import tree.search.heuristics.Heuristic2;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ChoiceController extends BasicController {

    @FXML
    public Button aStarH1Button;

    @FXML
    public Button aStarH2Button;

    @FXML
    private Button bsButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button dfsButton;

    @FXML
    private AnchorPane mainPanel;

    @FXML
    private NodeTableView initStateTable;

    @FXML
    private NodeTableView goalStateTable;

    @FXML
    void initialize() throws FileNotFoundException, InterruptedException {
        tableInit();
        dfsButton.setOnAction(ActionEvent -> {
            //DFSApp dfsApp = new DFSApp(initNode, goalNode);
            DFSApp dfsApp = new DFSApp(ChoiceApp.initNode, ChoiceApp.goalNode);
            try {
                dfsApp.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) dfsButton.getScene().getWindow();
            stage.close();
        });

        bsButton.setOnAction(ActionEvent -> {
            //BSApp bsApp = new BSApp(initNode, goalNode);
            BSApp bsApp = new BSApp(ChoiceApp.initNode, ChoiceApp.goalNode);
            try {
                bsApp.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) bsButton.getScene().getWindow();
            stage.close();
        });

        aStarH1Button.setOnAction(ActionEvent -> {
            //AStarApp aStarApp = new AStarApp(initNode, goalNode);
            AStarApp aStarApp = new AStarApp(ChoiceApp.initNode, ChoiceApp.goalNode, new Heuristic1());
            try {
                aStarApp.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) aStarH1Button.getScene().getWindow();
            stage.close();
        });

        aStarH2Button.setOnAction(ActionEvent -> {
            //AStarApp aStarApp = new AStarApp(initNode, goalNode);
            AStarApp aStarApp = new AStarApp(ChoiceApp.initNode, ChoiceApp.goalNode, new Heuristic2());
            try {
                aStarApp.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) aStarH2Button.getScene().getWindow();
            stage.close();
        });

        closeButton.setOnAction(ActionEvent -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    /*public void preset(Node initNode, Node goalNode) {
        this.initNode = initNode;
        this.goalNode = goalNode;
        initStateTable.setNode(initNode);
        goalStateTable.setNode(goalNode);
    }*/

    protected void tableInit() {
        initStateTable.setNode(ChoiceApp.initNode);
        goalStateTable.setNode(ChoiceApp.goalNode);
        initStateTable.setEditable(true);
        goalStateTable.setEditable(true);
    }
}
