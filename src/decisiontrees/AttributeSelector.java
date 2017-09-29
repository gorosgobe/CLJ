package decisiontrees;

/**
 * Represents a next attribute selector
 * @param <T> the type the decision tree holds, must implement comparable
 *
 * @author gorosgobe
 */
@FunctionalInterface
public interface AttributeSelector<T extends Comparable<T>> {

    /**
     * Selects the next attribute to use
     * @param dataset the decision tree's dataset
     * @param attribute the classifying attribute
     * @return the next attribute
     */
    Attribute<T> nextAttribute(Dataset<T> dataset, Attribute<T> attribute);

}
