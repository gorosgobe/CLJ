package decisiontrees;

import java.util.List;

/**
 * The class representing the node of the decision tree
 * @param <T> the type of the object that the decision node holds. Must implement the comparable interface.
 *
 * @author gorosgobe
 */
public class DecisionNode<T extends Comparable<T>> {

    /** The name of the attribute*/
    private String attributeName;
    /** A list of pairs of Objects of type <em>T</em> and the associated decision tree*/
    private List<Pair<T, DecisionNode<T>>> list;
    /** The value the node holds*/
    private T value;

    /**
     * Creates a decision node. A decision node is considered a leaf if it only has a value.
     * @param attributeName the name of the attribute
     * @param list the list of pairs of objects and decision trees
     * @param value the value
     */
    public DecisionNode(String attributeName, List<Pair<T, DecisionNode<T>>> list, T value) {
        this.attributeName = attributeName;
        this.list = list;
        this.value = value;
    }

    /**
     * Creates an empty decision node.
     */
    public DecisionNode() {
    }

    /**
     * Gets the attribute name of the decision node
     * @return the attribute name
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * Sets the attribute name of the decision node
     * @param attributeName the new attribute name
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * Gets the list of pairs of values and decision trees within the node
     * @return the list of pairs of values and decision trees within the node
     */
    public List<Pair<T, DecisionNode<T>>> getList() {
        return list;
    }

    /**
     * Sets the list of pairs of values and decision trees within the node
     * @param list the new list
     */
    public void setList(List<Pair<T, DecisionNode<T>>> list) {
        this.list = list;
    }

    /**
     * Gets the value held by the node
     * @return the value held by the node
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the value held by the node
     * @param value the new value
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Returns whether the node is a leaf
     * @return is the node a leaf?
     */
    private boolean isLeaf() {
        return value != null && list == null && attributeName == null;
    }

    @Override
    public String toString() {
        if (isLeaf()) {
            return "Leaf \"" + value + "\"";
        } else {
            return "decisiontrees.DecisionNode " + attributeName + "\n " + list;
        }

    }
}