package decisiontrees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table<T extends Comparable<T>> {

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

    public List<Row<T>> getRows() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table<?> table1 = (Table<?>) o;

        return table.equals(table1.table);
    }

    @Override
    public int hashCode() {
        return table.hashCode();
    }

    @Override
    public String toString() {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Row<T> row : table) {
            count++;
            if (count == table.size()) {
                sb.append(row);
            } else {
                sb.append(row);
                sb.append(",\n");
            }
        }
        sb.append(']');

        return sb.toString();
    }

}
