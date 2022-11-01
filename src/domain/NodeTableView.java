package domain;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Pair;
import tree.Node;

import java.util.ArrayList;

public class NodeTableView extends TableView<TablesData> {
    private Node node;

    public NodeTableView() {
        super();
        ArrayList<TableColumn<TablesData, String>> columnsList = new ArrayList<>() {{
            add(new TableColumn<>(""));
            add(new TableColumn<>(""));
            add(new TableColumn<>(""));
        }};
        columnsList.get(0).setCellValueFactory(new PropertyValueFactory<>("firstColumn"));
        columnsList.get(1).setCellValueFactory(new PropertyValueFactory<>("secondColumn"));
        columnsList.get(2).setCellValueFactory(new PropertyValueFactory<>("thirdColumn"));

        for (var column : columnsList) {
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            column.setPrefWidth(32);
            getColumns().add(column);
            getItems().add(new TablesData());
            column.setSortable(false);
            column.setReorderable(false);
            column.setResizable(false);

            column.setOnEditCommit(event -> {
                try {
                    if (node != null) {
                        int new_digit = Integer.parseInt(event.getNewValue());
                        if (new_digit < 0 || new_digit > 8)
                            throw new NumberFormatException();
                        int old_digit = Integer.parseInt(event.getOldValue());
                        Pair<Integer, Integer> fstPos = node.getDigitPosition(new_digit);
                        Pair<Integer, Integer> secPos = node.getDigitPosition(old_digit);
                        setValue(fstPos.getKey(), fstPos.getValue(), event.getOldValue());
                        setValue(secPos.getKey(), secPos.getValue(), event.getNewValue());
                        node.swap(fstPos, secPos);
                    }
                } catch (NumberFormatException ignored) {}
                refresh();
            });
        }
        getStylesheets().add("./styles/tableScrollBar.css");
        setNode(null);
    }

    public void setValue(int row, int column, String newValue) {
        if (column == 0){
            getItems().get(row).setFirstColumn(newValue);
        } else if (column == 1){
            getItems().get(row).setSecondColumn(newValue);
        } else if (column == 2){
            getItems().get(row).setThirdColumn(newValue);
        } else
            throw new IllegalArgumentException();
    }

    public void setNode(Node node) {
        this.node = node;
        if (node != null) {
            for (int i = 0; i < 3; ++i)
                for (int j = 0; j < 3; ++j)
                    setValue(i, j, node.getState().get(i).get(j).toString());
        } else {
            for (int i = 0; i < 3; ++i)
                for (int j = 0; j < 3; ++j)
                    setValue(i, j, "-");
        }
        refresh();
    }
}
