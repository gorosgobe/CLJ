package decisiontrees;

import java.util.Arrays;
import java.util.List;

/**
 * Class representing an attribute composed of the name of the attribute and the possible values it can take
 * @param <T> the type of the values of the attribute. Must implement comparable.
 *
 * @author gorosgobe
 */
public class Attribute<T extends Comparable<T>> implements Comparable<Attribute<T>> {

    /** The name of the attribute*/
    private final String attributeName;
    /** The possible values of the attribute*/
    private final List<T> attributeValues;

    /**
     * Constructs an attribute with the supplied arguments
     * @param attributeName the name of the attribute
     * @param attributeValues the possible values of the attribute
     */
    public Attribute(String attributeName, List<T> attributeValues) {
        this.attributeName = attributeName;
        this.attributeValues = attributeValues;
    }

    /**
     * Constructs an attribute with the supplied arguments
     * @param attributeName the name of the attribute
     * @param attributeValues the possible values of the attribute
     */
    @SafeVarargs
    public Attribute(String attributeName, T... attributeValues) {
        this.attributeName = attributeName;
        this.attributeValues = Arrays.asList(attributeValues);
    }

    /**
     * Gets the name of the attribute
     * @return the name of the attribute
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * Gets the values of the attribute
     * @return the values of the attribute
     */
    public List<T> getAttributeValues() {
        return attributeValues;
    }

    /**
     * Does the attribute have the given value? Uses equals()
     * @param value the value to look for
     * @return Does the attribute have the given value?
     */
    public boolean hasValue(T value) {
        for (T t : attributeValues) {
            if (value.compareTo(t) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + attributeName + ", " + attributeValues + ")";
    }

    @Override
    public int compareTo(Attribute<T> attribute) {

        if (attribute.getAttributeValues().size() != this.getAttributeValues().size()) {
            return -1;
        }

        if (attribute.getAttributeName().equals(attributeName)) {
            for (int i = 0; i < attributeValues.size(); i++) {
                if (attribute.getAttributeValues().get(i).compareTo(this.attributeValues.get(i)) != 0) {
                    //attribute values are not the same
                    return -1;
                }
            }
            //all fields are equal
            return 0;
        }

        return -1;
    }
}
