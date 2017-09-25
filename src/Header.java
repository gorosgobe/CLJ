import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Header<T> {

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

    public List<T> getAttributeValues(int index) {
        if (index < 0 || index >= attributes.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return attributes.get(index).getAttributeValues();
    }

    @Override
    public String toString() {
        return attributes.stream()
                .map(Attribute::getAttributeName)
                .collect(Collectors.toList()).toString();
    }
}
