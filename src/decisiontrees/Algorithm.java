package decisiontrees;

/**
 * An interface representing a tree building algorithm
 * @param <T> the type of the object that the tree will hold. Must implement Comparable
 *
 * @author gorosgobe
 */
@FunctionalInterface
public interface Algorithm<T extends Comparable<T>> {

    /**
     * Builds a decision node that will be the root of a decision tree
     * @param dataset the dataset supplied
     * @param classifyingAttribute the attribute to classify with
     * @param selector the attribute selector
     * @return the root of the decision tree
     */
    DecisionNode<T> buildTree(Dataset<T> dataset, Attribute<T> classifyingAttribute, AttributeSelector<T> selector);

}
