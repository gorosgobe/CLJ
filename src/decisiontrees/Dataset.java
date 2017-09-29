package decisiontrees;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class representing a dataset.
 * @param <T> the type of the objects the dataset holds. Must implement Comparable
 *
 * @author gorosgobe
 */
public class Dataset<T extends Comparable<T>> {

    /** The header of the dataset*/
    private final Header<T> header;
    /** The table of the dataset*/
    private final Table<T> table;

    /**
     * Constructs a new Dataset.
     * @param header the header of the dataset
     * @param table the table of the dataset
     */
    public Dataset(Header<T> header, Table<T> table) {
        this.header = header;
        this.table = table;

        if (!isEmpty()) {
            if (header.getAttributes().size() != table.getRow(0).getValues().size()) {
                throw new IllegalArgumentException("decisiontrees.Header and row size must be equal");
            }
        }
    }

    /**
     * Gets the header of the dataset
     * @return the header
     */
    public Header<T> getHeader() {
        return header;
    }

    /**
     * Gets the table of the dataset
     * @return the table
     */
    public Table<T> getTable() {
        return table;
    }

    /**
     * Partitions the dataset according to the supplied partition attribute
     * @param partitionAttribute the attribute to partition with
     * @return a list of pairs holding the value and the dataset partitions
     */
    public List<Pair<T, Dataset<T>>> partition(Attribute<T> partitionAttribute) {

        List<Pair<T, Dataset<T>>> resultList = new ArrayList<>();

        for (T value : partitionAttribute.getAttributeValues()) {
            resultList.add(createPartition(value));
        }

        return resultList;
    }

    /**
     * Creates an individual partition of the dataset with the given value of an attribute
     * @param value the value of the attribute
     * @return the pair composed by the value and the partitioned dataset
     */
    private Pair<T, Dataset<T>> createPartition(T value) {

        int index = header.getIndexOfAttributeWithValue(value);

        //table with the rows that have at index "index" the argument "value"
        Table<T> t = new Table<>(table.getRows().parallelStream()
                .filter(i -> i.getValue(index).compareTo(value) == 0)
                .map(i -> i.getRowWithoutIndex(index))
                .collect(Collectors.toList()));

        Attribute<T> attToRemove = header.getAttributes().get(index);

        //header with the attribute that has the value removed
        Header<T> h = new Header<>(header.getAttributes().parallelStream()
                .filter(i -> i.compareTo(attToRemove) != 0)
                .collect(Collectors.toList()));

        return new Pair<>(value, new Dataset<>(h, t));
    }

    /**
     * Returns whether the dataset is empty
     * @return is the dataset empty?
     */
    public boolean isEmpty() {
        return table.getRows().isEmpty();
    }

    @Override
    public String toString() {
        return "decisiontrees.Header: " + header + "\n" + "decisiontrees.Table: \n" + table;
    }

}
