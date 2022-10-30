import domain.TablesData;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import tree.Node;
import tree.search.Search;
import tree.search.UnidirectionalSearch;

import java.util.ArrayList;

public class SearchController {

    protected void setValueInTable(TableView<TablesData> currentTable, int row, int column, String newValue){
        if (column == 0){
            currentTable.getItems().get(row).setFirstColumn(newValue);
        } else if (column == 1){
            currentTable.getItems().get(row).setSecondColumn(newValue);
        } else if (column == 2){
            currentTable.getItems().get(row).setThirdColumn(newValue);
        }
    }

    protected void SetNodeInTable(TableView<TablesData> table, Node node) {
        if (node != null) {
            for (int i = 0; i < 3; ++i)
                for (int j = 0; j < 3; ++j)
                    table.getItems().get(i).setColumn(j, node.getState().get(i).get(j).toString());
        } else {
            for (int i = 0; i < 3; ++i)
                for (int j = 0; j < 3; ++j)
                    table.getItems().get(i).setColumn(j, "-");
        }
        table.refresh();
    }

    protected void SetInfoToLabel(Label label, String info) {
        label.setText(label.getText().replaceAll("\\d", "") + info);
    }

    protected void SetChilds(ArrayList<TableView<TablesData>> tableList, UnidirectionalSearch search, Node node, int offset) {
        ArrayList<Node> childs = node.getChilds();
        for (int i = 0; i < 4; ++i) {
            if (i < childs.size()) {
                SetNodeInTable(tableList.get(i + offset), childs.get(i));
                if (search.Visited(childs.get(i)))
                    tableList.get(i + offset).setStyle("-fx-background-color: #FF0000");
                else
                    tableList.get(i + 1).setStyle("-fx-background-color: #FFF8DC");
            } else {
                SetNodeInTable(tableList.get(i + offset), null);
                tableList.get(i + offset).setStyle("-fx-background-color: #FFF8DC");
            }
        }
    }
}
