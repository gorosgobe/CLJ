
@FunctionalInterface
public interface AttributeSelector<T extends Comparable<T>> {

    Attribute<T> nextAttribute(Dataset<T> dataset, Attribute<T> attribute);

}
