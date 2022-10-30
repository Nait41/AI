import domain.TablesData;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
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
    private Button closeButton;

    @FXML
    private AnchorPane mainPanel;

    @FXML
    private TableView<TablesData> mainTable_1;

    @FXML
    private TableView<TablesData> mainTable_2;

    @FXML
    private TableView<TablesData> mainTable_3;

    @FXML
    private TableView<TablesData> mainTable_4;

    @FXML
    private TableView<TablesData> mainTable_5;

    ArrayList<TableView<TablesData>> tableList = new ArrayList<>();

    @FXML
    private Button runAuto;

    @FXML
    private Button runStep;

    public void tableInit() {
        tableList.add(mainTable_1);
        tableList.add(mainTable_2);
        tableList.add(mainTable_3);
        tableList.add(mainTable_4);
        tableList.add(mainTable_5);
        for (var table : tableList){
            ArrayList<TableColumn<TablesData, String>> columnsList = new ArrayList<>() {{
                add(new TableColumn<>(""));
                add(new TableColumn<>(""));
                add(new TableColumn<>(""));
            }};
            columnsList.get(0).setCellValueFactory(new PropertyValueFactory<TablesData,String>("firstColumn"));
            columnsList.get(1).setCellValueFactory(new PropertyValueFactory<TablesData,String>("secondColumn"));
            columnsList.get(2).setCellValueFactory(new PropertyValueFactory<TablesData,String>("thirdColumn"));
            for (var column : columnsList) {
                column.setPrefWidth(32);
                table.getColumns().add(column);
                table.getItems().add(new TablesData());
            }
        }
    }

    // TODO temp
    ArrayList<ArrayList<Integer>> initState = new ArrayList<>() {{
        add(new ArrayList<>(){{add(6); add(0); add(8);}});
        add(new ArrayList<>(){{add(5); add(2); add(1);}});
        add(new ArrayList<>(){{add(4); add(3); add(7);}});
    }};
    ArrayList<ArrayList<Integer>> goalState = new ArrayList<>() {{
        add(new ArrayList<>(){{add(1); add(2); add(3);}});
        add(new ArrayList<>(){{add(8); add(0); add(4);}});
        add(new ArrayList<>(){{add(7); add(6); add(5);}});
    }};
    Node initNode = new Node(initState, new Pair<>(0,1));
    Node goalNode = new Node(goalState, new Pair<>(1,1));
    UnidirectionalSearch search = new DeepFirstSearch(initNode,goalNode);

    @FXML
    void initialize() throws FileNotFoundException, InterruptedException {
        tableInit();
        for (var table : tableList) {
            for (var column : table.getColumns()) {
                column.setSortable(false);
                column.setReorderable(false);
            }
            SetNodeInTable(table, null);
        }
        SetNodeInTable(mainTable_1, initNode);

        runAuto.setOnAction(ActionEvent -> {
            runAuto.setDisable(true);
            runStep.setDisable(true);
            closeButton.setDisable(true);
            MyService service = new MyService();
            service.setOnSucceeded((EventHandler<WorkerStateEvent>) t -> {
                SetNodeInTable(mainTable_1, search.GetSolutionNode());
                SetInfoToLabel(depthLabel, Integer.toString(search.GetSolutionNode().getDepth()));
                SetInfoToLabel(costInfo, Integer.toString(search.GetStepCount()));
                SetChilds(tableList, search, search.GetSolutionNode(), 1);
                closeButton.setDisable(false);
            });
            service.start();
        });

        runStep.setOnAction(ActionEvent -> {
            if (search.Next()) {
                SetNodeInTable(mainTable_1, search.GetCurrentNode());
                SetInfoToLabel(depthLabel, Integer.toString(search.GetCurrentNode().getDepth()));
                SetInfoToLabel(costInfo, Integer.toString(search.GetStepCount()));
                SetChilds(tableList, search, search.GetCurrentNode(), 1);
            }
            else {
                SetNodeInTable(mainTable_1, search.GetSolutionNode());
                SetInfoToLabel(depthLabel, Integer.toString(search.GetSolutionNode().getDepth()));
                SetInfoToLabel(costInfo, Integer.toString(search.GetStepCount()));
                SetChilds(tableList, search, search.GetSolutionNode(), 1);
                runAuto.setDisable(true);
                runStep.setDisable(true);
            }
        });

        closeButton.setOnAction(ActionEvent -> {
            ChoiceApp choiceApp = new ChoiceApp();
            try {
                choiceApp.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    private class MyService extends Service {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    if (!search.IsOver()) {
                        while (search.Next());
                    }
                    return null;
                }
            };
        }
    }
}
