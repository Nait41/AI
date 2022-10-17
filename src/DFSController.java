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
import tree.search.Search;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class DFSController {

    @FXML
    private Button closeButton;

    @FXML
    private AnchorPane mainPanel;

    @FXML
    private Label costInfoForDirect;

    @FXML
    private Label costInfoReverse;

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
        for (int i = 0; i < tableList.size(); i++){
            TableColumn firstColumn = new TableColumn("");
            TableColumn secondColumn = new TableColumn("");
            TableColumn thirdColumn = new TableColumn("");
            firstColumn.setCellValueFactory(new PropertyValueFactory<TablesData,String>("firstColumn"));
            secondColumn.setCellValueFactory(new PropertyValueFactory<TablesData,String>("secondColumn"));
            thirdColumn.setCellValueFactory(new PropertyValueFactory<TablesData,String>("thirdColumn"));
            firstColumn.setPrefWidth(32);
            secondColumn.setPrefWidth(32);
            thirdColumn.setPrefWidth(32);
            tableList.get(i).getColumns().add(firstColumn);
            tableList.get(i).getColumns().add(secondColumn);
            tableList.get(i).getColumns().add(thirdColumn);
            TablesData tablesDataForFirst = new TablesData();
            tablesDataForFirst.setFirstColumn("1");
            tablesDataForFirst.setSecondColumn("1");
            tablesDataForFirst.setThirdColumn("1");
            tableList.get(i).getItems().add(tablesDataForFirst);
            TablesData tablesDataForSecond = new TablesData();
            tablesDataForSecond.setFirstColumn("1");
            tablesDataForSecond.setSecondColumn("1");
            tablesDataForSecond.setThirdColumn("1");
            tableList.get(i).getItems().add(tablesDataForSecond);
            TablesData tablesDataForThird = new TablesData();
            tablesDataForThird.setFirstColumn("1");
            tablesDataForThird.setSecondColumn("1");
            tablesDataForThird.setThirdColumn("1");
            tableList.get(i).getItems().add(tablesDataForThird);
        }
    }

    public void setValueInTable(TableView<TablesData> currentTable, int row, int column, String newValue){
        if (column == 0){
            currentTable.getItems().get(row).setFirstColumn(newValue);
        } else if (column == 1){
            currentTable.getItems().get(row).setSecondColumn(newValue);
        } else if (column == 2){
            currentTable.getItems().get(row).setThirdColumn(newValue);
        }
    }

    @FXML
    void initialize() throws FileNotFoundException, InterruptedException {
        tableInit();
        setValueInTable(mainTable_1, 0, 1, "2");
        mainTable_1.setDisable(true);
        runAuto.setOnAction(ActionEvent -> {
            runAuto.setDisable(true);
            //mainTable_1.setDisable(true);
            //runAuto.setStyle("-fx-background-color: #ff0000; ");
            MyService service = new MyService();
            service.setOnSucceeded((EventHandler<WorkerStateEvent>) t -> {
                //runAuto.setStyle("-fx-background-color: #FFA500; ");
                //mainTable_1.setDisable(false);
                runAuto.setDisable(false);
            });
            service.start();
        });

        runStep.setOnAction(ActionEvent -> {

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
        protected void SetTableData(TableView<TablesData> table, Node node) {
            for (int i = 0; i < 3; ++i)
                for (int j = 0; j < 3; ++j)
                    table.getItems().get(i).setColumn(j, node.getState().get(i).get(j).toString());
            table.refresh();
        }

        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
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
                    Pair<Integer,Integer> initEmptyIndexes = new Pair<>(0,1);
                    Pair<Integer,Integer> goalEmptyIndexes = new Pair<>(1,1);
                    /*ArrayList<ArrayList<Integer>> initState = new ArrayList<>() {{
                        add(new ArrayList<>(){{add(7); add(4); add(2);}});
                        add(new ArrayList<>(){{add(3); add(5); add(8);}});
                        add(new ArrayList<>(){{add(6); add(0); add(1);}});
                    }};
                    ArrayList<ArrayList<Integer>> goalState = new ArrayList<>() {{
                        add(new ArrayList<>(){{add(1); add(2); add(3);}});
                        add(new ArrayList<>(){{add(4); add(0); add(5);}});
                        add(new ArrayList<>(){{add(6); add(7); add(8);}});
                    }};
                    Pair<Integer,Integer> initEmptyIndexes = new Pair<>(2,1);
                    Pair<Integer,Integer> goalEmptyIndexes = new Pair<>(1,1);*/
                    Node initNode = new Node(initState,initEmptyIndexes);
                    Node goalNode = new Node(goalState,goalEmptyIndexes);
                    Search search = new DeepFirstSearch(initNode,goalNode);
                    while (search.Next()) {
                        /*for (int i = 0; i < 3; ++i) {
                            mainTable_1.getItems().get(i).
                                    setFirstColumn(search.getCurrentNode().getState().get(i).get(0).toString());
                            mainTable_1.getItems().get(i).
                                    setSecondColumn(search.getCurrentNode().getState().get(i).get(1).toString());
                            mainTable_1.getItems().get(i).
                                    setThirdColumn(search.getCurrentNode().getState().get(i).get(2).toString());
                        }
                        mainTable_1.refresh();*/
                    }
                    SetTableData(mainTable_1, search.getCurrentNode());
                    return null;
                }
            };
        }
    }
}
