package decisiontrees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Header<T extends Comparable<T>> {

    private final List<Attribute<T>> attributes;

    public Header(List<Attribute<T>> attributes) {
        this.attributes = attributes;
    }

    @SafeVarargs
    public Header(Attribute<T>... attributes) {
        this.attributes = Arrays.asList(attributes);
    }

    public Header() {
        this.attributes = new ArrayList<>();
    }

    public List<Attribute<T>> getAttributes() {
        return attributes;
    }

    public String getAttributeName(int index) {
        if (index < 0 || index >= attributes.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return attributes.get(index).getAttributeName();
    }

    public void addAttribute(Attribute<T> attribute) {
        attributes.add(attribute);
    }

    public void removeAttribute(int index) {
        if (index < 0 || index >= attributes.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        attributes.remove(index);
    }

    public void removeAttributeWithValue(T value) {

        for (Attribute<T> att : attributes) {
            if (att.hasValue(value)) {
                attributes.remove(att);
                return;
            }
        }
    }

    public List<T> getAttributeValues(int index) {
        if (index < 0 || index >= attributes.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return attributes.get(index).getAttributeValues();
    }

    public int getIndexOfAttribute(Attribute<T> attribute) {
        for (int i = 0; i < attributes.size(); i++) {
            if (attribute.compareTo(attributes.get(i)) == 0) {
                return i;
            }
        }
        return -1;
    }

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
        return attributes.stream()
                .map(Attribute::getAttributeName)
                .collect(Collectors.toList()).toString();
    }
}
