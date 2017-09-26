import java.util.List;

public class InformationGainSelector<T extends Comparable<T>> implements AttributeSelector<T> {

    @Override
    public Attribute<T> nextAttribute(Dataset<T> dataset, Attribute<T> attribute) {
        
        return null;
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
