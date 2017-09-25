import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table<T> {

    private final List<Row<T>> table;

    public Table() {
        this.table = new ArrayList<>();
    }

    public Table(List<Row<T>> table) {
        this.table = table;
    }

    @SafeVarargs
    public Table(Row<T>... rows) {
        this.table = Arrays.asList(rows);
    }

    public List<Row<T>> getTable() {
        return table;
    }

    public Row<T> getRow(int index) {
        if (index < 0 || index >= table.size()) {
            throw new IndexOutOfBoundsException();
        }
        return table.get(index);
    }

    public void addRow(Row<T> row) {
        table.add(row);
    }
}
