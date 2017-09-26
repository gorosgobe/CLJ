import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InformationGainSelector<T extends Comparable<T>> implements AttributeSelector<T> {

    @Override
    public Attribute<T> nextAttribute(Dataset<T> dataset, Attribute<T> attribute) {

        List<Attribute<T>> attributes = dataset.getHeader().getAttributes();
        List<Pair<Double, Attribute<T>>> gainToAtt = new ArrayList<>();

        for (int i = 0; i < attributes.size(); i++) {
            if (attributes.get(i).compareTo(attribute) == 0) {
                //attribute is classification attribute, we ignore
                continue;
            }
            double g = gain(dataset, attribute, attributes.get(i));
            gainToAtt.add(new Pair<>(g, attributes.get(i)));
        }

        double maxGain = gainToAtt.stream()
                .map(i -> i.getFirst())
                .reduce(Double::max).orElse(0.0);

        return gainToAtt.stream()
                .filter(i -> i.getFirst().compareTo(maxGain) == 0)
                .collect(Collectors.toList()).get(0).getSecond();

    }

    public static <T extends Comparable<T>> double gain(Dataset<T> dataset, Attribute<T> classificationAtt,
                                                         Attribute<T> partitioningAttribute) {
        double entropyDatasetClassAtt = entropy(dataset, classificationAtt);

        double sum = 0;
        for (int i = 0; i < partitioningAttribute.getAttributeValues().size(); i++) {
            double p = probability(dataset, partitioningAttribute, partitioningAttribute.getAttributeValues().get(i));
            List<Pair<T, Dataset<T>>> partition = dataset.partition(partitioningAttribute);
            Dataset<T> d = partition.get(i).getSecond();
            sum += p * entropy(d, classificationAtt);
        }

        return entropyDatasetClassAtt - sum;
    }

    public static <T extends Comparable<T>> double entropy(Dataset<T> dataset, Attribute<T> attribute) {

        if (dataset.isEmpty()) {
            return 0;
        }

        double sum = 0;
        for (int i = 0; i < attribute.getAttributeValues().size(); i++) {
            double p = probability(dataset, attribute, attribute.getAttributeValues().get(i));
            sum += -xlogx(p);
        }

        return sum;
    }

    private static double xlogx(double value) {
        if (value <= 1e-100) {
            return 0;
        } else {
            return value * log2(value);
        }
    }

    private static double log2(double value) {
        return Math.log10(value) / Math.log10(2.);
    }

    private static <T extends Comparable<T>> double probability(Dataset<T> dataset, Attribute<T> attribute, T value) {
        //pre- dataset is not empty

        int index = dataset.getHeader().getIndexOfAttribute(attribute);
        List<Row<T>> rows = dataset.getTable().getRows();
        double totalNumberOfRows = rows.size();
        double count = 0;

        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getValue(index).compareTo(value) == 0) {
                count++;
            }
        }

        return count / totalNumberOfRows;
    }
}
