import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Dataset<T extends Comparable<T>> {

    private final Header<T> header;
    private final Table<T> table;

    public Dataset(Header<T> header, Table<T> table) {
        this.header = header;
        this.table = table;

        if (header.getAttributes().size() != table.getRow(0).getValues().size()) {
            throw new IllegalArgumentException("Header and row size must be equal");
        }
    }

    public Header<T> getHeader() {
        return header;
    }

    public Table<T> getTable() {
        return table;
    }

    public List<Pair<T, Dataset<T>>> partition(Attribute<T> partitionAttribute) {

        List<Pair<T, Dataset<T>>> resultList = new ArrayList<>();

        for (T value : partitionAttribute.getAttributeValues()) {
            resultList.add(createPartition(value));
        }

        return resultList;
    }

    private Pair<T, Dataset<T>> createPartition(T value) {

        int index = header.getIndexOfAttributeWithValue(value);

        //table with the rows removed that have at index "index" the argument "value"
        Table<T> t = new Table<>(table.getTable().stream()
                .filter(i -> i.getValue(index).compareTo(value) == 0)
                .map(i -> i.getRowWithoutIndex(index))
                .collect(Collectors.toList()));


        Attribute<T> attToRemove = header.getAttributes().get(index);

        //header with the attribute that has the value removed
        Header<T> h = new Header<>(header.getAttributes().stream()
                .filter(i -> i.compareTo(attToRemove) != 0)
                .collect(Collectors.toList()));

        return new Pair<>(value, new Dataset<>(h, t));
    }

    @Override
    public String toString() {
        return "Header: " + header + "\n" + "Table: \n" + table;
    }
}
