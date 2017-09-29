package decisiontrees;

import java.util.stream.Collectors;

public class NextAttributeSelector<T extends Comparable<T>> implements AttributeSelector<T> {

    @Override
    public Attribute<T> nextAttribute(Dataset<T> dataset, Attribute<T> attribute) {
        return dataset.getHeader().getAttributes().parallelStream()
                .filter(i -> !i.getAttributeName().equals(attribute.getAttributeName()))
                .collect(Collectors.toList()).get(0);
    }
}
