import domain.TablesData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class BSController {

    @FXML
    private Button closeButton;

    @FXML
    private AnchorPane mainPanel;

    @FXML
    private TableView<TablesData> mainTable_1;

    @FXML
    private TableView<TablesData> mainTable_10;

    @FXML
    private TableView<TablesData> mainTable_2;

    @FXML
    private TableView<TablesData> mainTable_3;

    @FXML
    private TableView<TablesData> mainTable_4;

    @FXML
    private TableView<TablesData> mainTable_5;

    @FXML
    private TableView<TablesData> mainTable_6;

    @FXML
    private TableView<TablesData> mainTable_7;

    @FXML
    private TableView<TablesData> mainTable_8;

    @FXML
    private TableView<TablesData> mainTable_9;

    ArrayList<TableView<TablesData>> tableList = new ArrayList<>();

    @FXML
    private Button runAuto;

    @FXML
    private Button runStep;

    public BSController(){
    }

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

        runAuto.setOnAction(ActionEvent -> {

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
}
