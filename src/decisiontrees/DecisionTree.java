package decisiontrees;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class representing a Decision Tree.
 * @param <T> the type that the nodes in the decision tree will hold. Must implement the comparable interface.
 *
 * @author gorosgobe
 */
public class DecisionTree<T extends Comparable<T>> {

    /** The root of the decision tree*/
    private DecisionNode<T> root;

    /**
     * Creates a decision tree from the given dataset, classifying in terms of the given attribute and choosing the
     * attributes to partition with via an attribute selector.
     * @param dataset the dataset to train the decision tree with
     * @param resultAttribute the attribute to classify
     * @param selector the next attribute selector
     */
    public DecisionTree(Dataset<T> dataset, Attribute<T> resultAttribute, AttributeSelector<T> selector) {
        this.root = buildTree(dataset, resultAttribute, selector);
    }

    /**
     * Creates a decision tree from the given dataset, classifying in terms of the given attribute and choosing the
     * attributes to partition with via an attribute selector. Takes an <em>Algorithm</em>, which builds the tree.
     * @param dataset the dataset to train the decision tree with
     * @param resultAttribute the attribute to classify
     * @param selector the next attribute selector
     * @param algorithm the algorithm that will build the tree
     */
    public DecisionTree(Dataset<T> dataset, Attribute<T> resultAttribute, AttributeSelector<T> selector,
                        Algorithm<T> algorithm) {
        this.root = algorithm.buildTree(dataset, resultAttribute, selector);
    }

    /**
     * Builds the decision tree recursively
     * @param dataset the dataset to build the tree with
     * @param resultAttribute the classifying attribute
     * @param selector the next attribute selector
     * @return the decision node built
     */
    private DecisionNode<T> buildTree(Dataset<T> dataset, Attribute<T> resultAttribute,
                                      AttributeSelector<T> selector) {
        //base case
        if (dataset.isEmpty()) {
            return null;
        }

        //leaf case
        Header<T> h = dataset.getHeader();
        int index = h.getIndexOfAttribute(resultAttribute);

        if (allRowsHaveSameValue(dataset, index)) {
            //all values of the classification attribute are the same, return Leaf
            return new DecisionNode<>(null, null, dataset.getTable().getRow(0).getValue(index));
        }

        //decisionNode case
        Attribute<T> att = selector.nextAttribute(dataset, resultAttribute);
        List<Pair<T, Dataset<T>>> partitions = dataset.partition(att);
        List<Pair<T, DecisionNode<T>>> listOfPairsAndNodes = new ArrayList<>();
        DecisionNode<T> decisionNode = new DecisionNode<>();

        for (Pair<T, Dataset<T>> p : partitions) {
            Dataset<T> d = p.getSecond();
            listOfPairsAndNodes.add(new Pair<>(p.getFirst(), buildTree(d, resultAttribute, selector)));
        }
        decisionNode.setList(listOfPairsAndNodes);
        decisionNode.setAttributeName(att.getAttributeName());

        return decisionNode;

    }

    @Override
    public String toString() {
        return root.toString();
    }

    /**
     * Returns whether all the rows have the same value at the given index
     * @param dataset the dataset to examine
     * @param index the index to check
     * @return whether all the rows have the same value at the given index
     */
    private static boolean allRowsHaveSameValue(
            Dataset<?> dataset, int index) {

        return allSame(dataset.getTable().getRows().parallelStream()
                .map(i -> i.getValue(index))
                .collect(Collectors.toList()));
    }

    /**
     * Returns whether all the elements in a list are the same
     * @param values the list with the values to check equality against
     * @param <T> the type of the elements in the list. Must implement comparable
     * @return are all elements in the list the same?
     */
    private static <T extends Comparable<T>> boolean allSame(List<T> values) {
        return values.parallelStream().allMatch(i -> i.compareTo(values.get(0)) == 0);
    }




}
