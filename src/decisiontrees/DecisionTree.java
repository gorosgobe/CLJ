package decisiontrees;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DecisionTree<T extends Comparable<T>> {

    private DecisionNode<T> root;


    public DecisionTree(Dataset<T> dataset, Attribute<T> resultAttribute, AttributeSelector<T> selector) {
        this.root = buildTree(dataset, resultAttribute, selector);
    }

    public DecisionTree(Dataset<T> dataset, Attribute<T> resultAttribute, AttributeSelector<T> selector,
                        Algorithm<T> algorithm) {
        this.root = algorithm.buildTree(dataset, resultAttribute, selector);
    }

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

    private static <T extends Comparable<T>> boolean allRowsHaveSameValue(
            Dataset<T> dataset, int index) {

        return allSame(dataset.getTable().getRows().parallelStream()
                .map(i -> i.getValue(index))
                .collect(Collectors.toList()));
    }

    private static <T extends Comparable<T>> boolean allSame(List<T> values) {
        return values.parallelStream().allMatch(i -> i.compareTo(values.get(0)) == 0);
    }




}
