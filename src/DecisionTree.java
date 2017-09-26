import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DecisionTree<T extends Comparable<T>> {

    //private final Dataset<T> dataset;
    //private final Attribute<T> resultAttribute;
    //private final AttributeSelector<T> selector;
    private Node<T> root;


    public DecisionTree(Dataset<T> dataset, Attribute<T> resultAttribute, AttributeSelector<T> selector) {
        this.root = buildTree(dataset, resultAttribute, selector);
    }

    public DecisionTree(Dataset<T> dataset, Attribute<T> resultAttribute, AttributeSelector<T> selector,
                        Algorithm<T> algorithm) {
        this.root = algorithm.buildTree(dataset, resultAttribute, selector);
    }

    private Node<T> buildTree(Dataset<T> dataset, Attribute<T> resultAttribute,
                                      AttributeSelector<T> selector) {
        //base case
        if (dataset.isEmpty()) {
            return null;
        }

        //leaf case
        Header<T> h = dataset.getHeader();
        int index = h.getIndexOfAttribute(resultAttribute);

        if (allRowsHaveSameValue(dataset, resultAttribute, index)) {
            //all values of the classification attribute are the same, return Leaf
            return new Node<>(null, null, dataset.getTable().getRow(0).getValue(index));
        }

        //node case
        Attribute<T> att = selector.nextAttribute(dataset, resultAttribute);
        List<Pair<T, Dataset<T>>> partitions = dataset.partition(att);
        List<Pair<T, Node<T>>> listOfPairsAndNodes = new ArrayList<>();
        Node<T> node = new Node<>();

        for (Pair<T, Dataset<T>> p : partitions) {
            Dataset<T> d = p.getSecond();
            listOfPairsAndNodes.add(new Pair<>(p.getFirst(), buildTree(d, resultAttribute, selector)));
        }
        node.setList(listOfPairsAndNodes);
        node.setAttributeName(att.getAttributeName());

        return node;

    }

    @Override
    public String toString() {
        return root.toString();
    }

    private static <T extends Comparable<T>> boolean allRowsHaveSameValue(
            Dataset<T> dataset, Attribute<T> resultAttribute, int index) {

        return allSame(dataset.getTable().getRows().stream()
                .map(i -> i.getValue(index))
                .collect(Collectors.toList()));
    }

    private static <T extends Comparable<T>> boolean allSame(List<T> values) {
        return values.stream().allMatch(i -> i.compareTo(values.get(0)) == 0);
    }




}
