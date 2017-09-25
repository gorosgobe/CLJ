import java.util.Arrays;
import java.util.List;

public class Attribute<T extends Comparable<T>> implements Comparable<Attribute<T>> {

    private final String attributeName;
    private final List<T> attributeValues;

    public Attribute(String attributeName, List<T> attributeValues) {
        this.attributeName = attributeName;
        this.attributeValues = attributeValues;
    }

    @SafeVarargs
    public Attribute(String attributeName, T... attributeValues) {
        this.attributeName = attributeName;
        this.attributeValues = Arrays.asList(attributeValues);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public List<T> getAttributeValues() {
        return attributeValues;
    }

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
