import java.util.Arrays;
import java.util.List;

public class Attribute<T> {

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

}
