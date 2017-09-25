
public class DecisionTree<T extends Comparable<T>> {

    private final Dataset<T> dataset;
    private final Attribute<T> resultAttribute;
    private final AttributeSelector<T> selector;

    public DecisionTree(Dataset<T> dataset, Attribute<T> resultAttribute, AttributeSelector<T> selector) {
        this.dataset = dataset;
        this.resultAttribute = resultAttribute;
        this.selector = selector;
    }
}
