package decisiontrees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Row<T extends Comparable<T>> {

    private final List<T> values;

    public Row(List<T> values) {
        this.values = values;
    }

    public Row(T... values) {
        this.values = Arrays.asList(values);
    }

    public Row() {
        this.values = new ArrayList<>();
    }

    public List<T> getValues() {
        return values;
    }

    public T getValue(int index) {
        if (index < 0 || index >= getValues().size()) {
            throw new IndexOutOfBoundsException();
        }
        return values.get(index);
    }

    public void addValue(T value) {
        values.add(value);
    }

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
