package decisiontrees;

import java.util.stream.Collectors;

/**
 * Represents a dummy next attribute selector. Selects the next available attribute in order that is not the
 * classifying attribute.
 * @param <T> the type of the values held by the attributes. Must implement comparable
 *
 * @author gorosgobe
 */
public class NextAttributeSelector<T extends Comparable<T>> implements AttributeSelector<T> {

    @Override
    public Attribute<T> nextAttribute(Dataset<T> dataset, Attribute<T> attribute) {
        return dataset.getHeader().getAttributes().parallelStream()
                .filter(i -> !i.getAttributeName().equals(attribute.getAttributeName()))
                .collect(Collectors.toList()).get(0);
    }
}
