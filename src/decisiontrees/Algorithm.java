package decisiontrees;

@FunctionalInterface
public interface Algorithm<T extends Comparable<T>> {

    DecisionNode<T> buildTree(Dataset<T> dataset, Attribute<T> classifyingAttribute, AttributeSelector<T> selector);

}
