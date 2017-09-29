package decisiontrees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class representing a row.
 * @param <T> the type of the objects in the row. Must implement Comparable
 *
 * @author gorosgobe
 */
public class Row<T extends Comparable<T>> {

    /** The list of values the row holds*/
    private final List<T> values;

    /**
     * Constructs a row with the given values
     * @param values the list of values
     */
    public Row(List<T> values) {
        this.values = values;
    }

    /**
     * Constructs a row with the given values
     * @param values the values
     */
    public Row(T... values) {
        this.values = Arrays.asList(values);
    }

    /**
     * Constructs an empty row
     */
    public Row() {
        this.values = new ArrayList<>();
    }

    /**
     * Gets the values in the row
     * @return the values
     */
    public List<T> getValues() {
        return values;
    }

    public T getValue(int index) {
        if (index < 0 || index >= getValues().size()) {
            throw new IndexOutOfBoundsException();
        }
        return values.get(index);
    }

    /**
     * Adds a value to the row
     * @param value the value to add
     */
    public void addValue(T value) {
        values.add(value);
    }

    /**
     * Gets the row removing the column at the specified index
     * @param index the index of the column to remove
     * @return the row with the column removed
     */
    public Row<T> getRowWithoutIndex(int index) {

        List<T> list = new ArrayList<>();

        for (int i = 0; i < getValues().size(); i++) {
            if (i != index) {
                list.add(getValue(i));
            }
        }

        return new Row<>(list);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Row<?> row = (Row<?>) o;

        return values.equals(row.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
