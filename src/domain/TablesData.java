package domain;

public class TablesData {
    String firstColumn;
    String secondColumn;
    String thirdColumn;

    public String getFirstColumn() {
        return firstColumn;
    }

    public void setFirstColumn(String firstColumn) {
        this.firstColumn = firstColumn;
    }

    public String getSecondColumn() {
        return secondColumn;
    }

    public void setSecondColumn(String secondColumn) {
        this.secondColumn = secondColumn;
    }

    public String getThirdColumn() {
        return thirdColumn;
    }

    public void setThirdColumn(String thirdColumn) {
        this.thirdColumn = thirdColumn;
    }

    public void setColumn(int columnIndex, String columnData) throws IllegalArgumentException {
        if (columnIndex == 0)
            setFirstColumn(columnData);
        else if (columnIndex == 1)
            setSecondColumn(columnData);
        else if (columnIndex == 2)
            setThirdColumn(columnData);
        else
            throw new IllegalArgumentException();
    }
}
