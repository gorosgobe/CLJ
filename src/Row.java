import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Row<T> {

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
        if (index < 0 || index >= 1) {
            throw new IndexOutOfBoundsException();
        }
        return values.get(index);
    }

    public void addValue(T value) {
        values.add(value);
    }
}
