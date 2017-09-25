import java.util.List;

public class Dataset<T> {

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

    public List<Pair<T, Dataset<T>>> partition(Attribute<T> attributeToPartition) {
        return null;
    }

    @Override
    public String toString() {
        return "Header: " + header + "\n" + "Table: \n" + table;
    }
}
