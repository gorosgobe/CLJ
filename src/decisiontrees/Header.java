package decisiontrees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class representing the header of a dataset, holding the attributes present in it.
 * @param <T> the type of the values in the attributes the header holds.
 *
 * @author gorosgobe
 */
public class Header<T extends Comparable<T>> {

    /** A list containing all the attributes the header holds*/
    private final List<Attribute<T>> attributes;

    /**
     * Constructs a new header.
     * @param attributes the list of attributes
     */
    public Header(List<Attribute<T>> attributes) {
        this.attributes = attributes;
    }

    /**
     * Constructs a new header.
     * @param attributes the attributes
     */
    @SafeVarargs
    public Header(Attribute<T>... attributes) {
        this.attributes = Arrays.asList(attributes);
    }

    /**
     * Constructs a new empty header.
     */
    public Header() {
        this.attributes = new ArrayList<>();
    }

    /**
     * Gets the attributes in the header.
     * @return the list of attributes.
     */
    public List<Attribute<T>> getAttributes() {
        return attributes;
    }

    /**
     * Gets the name of the attribute at a given index.
     * @param index the index of the attribute to get the name of
     * @return the name of the attribute at the specified index
     */
    public String getAttributeName(int index) {
        if (index < 0 || index >= attributes.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return attributes.get(index).getAttributeName();
    }

    /**
     * Adds an attribute to the header.
     * @param attribute the attribute to add
     */
    public void addAttribute(Attribute<T> attribute) {
        attributes.add(attribute);
    }

    /**
     * Removes an attribute at a given index
     * @param index the index of the attribute to remove
     */
    public void removeAttribute(int index) {
        if (index < 0 || index >= attributes.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        attributes.remove(index);
    }

    /**
     * Removes the attribute with the given value
     * @param value the value of the attribute to remove
     */
    public void removeAttributeWithValue(T value) {

        for (Attribute<T> att : attributes) {
            if (att.hasValue(value)) {
                attributes.remove(att);
                return;
            }
        }
    }

    /**
     * Gets the attribute values of the attribute at the specified index
     * @param index the index of the attribute to get the values from
     * @return the attribute values
     */
    public List<T> getAttributeValues(int index) {
        if (index < 0 || index >= attributes.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return attributes.get(index).getAttributeValues();
    }

    /**
     * Gets the index of the supplied attribute. Returns -1 in case of failure.
     * @param attribute the attribute to get the index of
     * @return
     */
    public int getIndexOfAttribute(Attribute<T> attribute) {
        for (int i = 0; i < attributes.size(); i++) {
            if (attribute.compareTo(attributes.get(i)) == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets the index of the attribute with the supplied value. Returns -1 in case of failure.
     * @param value the value of the attribute to get the index from
     * @return the index of the attribute with the supplied value
     */
    public int getIndexOfAttributeWithValue(T value) {

        for (int i = 0; i < attributes.size(); i++) {
            if (attributes.get(i).hasValue(value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Header<?> header = (Header<?>) o;

        return attributes.equals(header.attributes);
    }

    @Override
    public int hashCode() {
        return attributes.hashCode();
    }

    @Override
    public String toString() {
        return attributes.parallelStream()
                .map(Attribute::getAttributeName)
                .collect(Collectors.toList()).toString();
    }
}
