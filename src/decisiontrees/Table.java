package decisiontrees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class representing the table of the dataset.
 * @param <T> the type of the object represented in each cell. Must implement Comparable.
 *
 * @author gorosgobe
 */
public class Table<T extends Comparable<T>> {

    /** The list with the rows of the table*/
    private final List<Row<T>> table;

    /**
     * Constructs an empty table
     */
    public Table() {
        this.table = new ArrayList<>();
    }

    /**
     * Constructs a table with the supplied rows
     * @param table the list of rows to fill up the table with
     */
    public Table(List<Row<T>> table) {
        this.table = table;
    }

    /**
     * Constructs a table from rows.
     * @param rows the rows to fill up the table with
     */
    @SafeVarargs
    public Table(Row<T>... rows) {
        this.table = Arrays.asList(rows);
    }

    /**
     * Gets the rows of the table
     * @return the list of rows
     */
    public List<Row<T>> getRows() {
        return table;
    }

    /**
     * Gets the row at the specified index
     * @param index the index of the row
     * @return the row at that index
     */
    public Row<T> getRow(int index) {
        if (index < 0 || index >= table.size()) {
            throw new IndexOutOfBoundsException();
        }
        return table.get(index);
    }

    /**
     * Adds a row to the table
     * @param row the row to add to the table
     */
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
