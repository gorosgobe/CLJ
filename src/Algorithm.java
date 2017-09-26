
@FunctionalInterface
public interface Algorithm<T extends Comparable<T>> {

    Node<T> buildTree(Dataset<T> dataset, Attribute<T> classifyingAttribute, AttributeSelector<T> selector);

}
