import domain.TablesData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ChoiceController {

    @FXML
    private Button bsButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button dfsButton;

    @FXML
    private AnchorPane mainPanel;

    @FXML
    void initialize() throws FileNotFoundException, InterruptedException {
        dfsButton.setOnAction(ActionEvent -> {
            DFSApp dfsApp = new DFSApp();
            try {
                dfsApp.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) dfsButton.getScene().getWindow();
            stage.close();
        });

        bsButton.setOnAction(ActionEvent -> {
            BSApp bsApp = new BSApp();
            try {
                bsApp.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) dfsButton.getScene().getWindow();
            stage.close();
        });

        closeButton.setOnAction(ActionEvent -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }
}
