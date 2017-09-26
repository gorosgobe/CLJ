import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class Tests {

    Row<String> row0 = new Row<>("sunny", "hot", "high", "calm", "bad");
    Row<String> row1 = new Row<>("sunny", "hot", "high", "windy", "bad");
    Row<String> row2 = new Row<>("overcast", "hot",  "high",   "calm",  "good");
    Row<String> row3 = new Row<>("rainy",    "mild", "high",   "calm",  "good");
    Row<String> row4 = new Row<>("rainy",    "cool", "normal", "calm",  "good");
    Row<String> row5 = new Row<>("rainy",    "cool", "normal", "windy", "bad");
    Row<String> row6 = new Row<>("overcast", "cool", "normal", "windy", "good");
    Row<String> row7 = new Row<>("sunny",    "mild", "high",   "calm",  "bad");
    Row<String> row8 = new Row<>("sunny",    "cool", "normal", "calm",  "good");
    Row<String> row9 = new Row<>("rainy",    "mild", "normal", "calm",  "good");
    Row<String> row10 = new Row<>("sunny",    "mild", "normal", "windy", "good");
    Row<String> row11 = new Row<>("overcast", "mild", "high",   "windy", "good");
    Row<String> row12 = new Row<>("overcast", "hot",  "normal", "calm",  "good");
    Row<String> row13 = new Row<>("rainy",    "mild", "high",   "windy", "bad");

    Table<String> table = new Table<>(row0, row1, row2, row3, row4, row5, row6, row7, row8, row9, row10,
            row11, row12, row13);

    Attribute<String> outlook = new Attribute<>("outlook", "sunny", "overcast", "rainy");
    Attribute<String> temp = new Attribute<>("temp", "hot", "mild", "cool");
    Attribute<String> humidity = new Attribute<>("humidity", "high", "normal");
    Attribute<String> wind = new Attribute<>("wind", "windy", "calm");
    Attribute<String> result = new Attribute<>("result", "good", "bad");

    Header<String> header = new Header<>(outlook, temp, humidity, wind, result);

    Dataset<String> dataset = new Dataset<>(header, table);

    @Test
    public void attributeTest1() {

       Attribute<String> att1 = new Attribute<>("att1", "string", "hi");
       Attribute<String> att2 = new Attribute<>("att2", "string", "hi");
       Attribute<String> att3 = new Attribute<>("att1", "string", "hi");
       Attribute<String> att4 = new Attribute<>("att2", "string", "hi", "hello");

        Assert.assertTrue(att1.compareTo(att3) == 0);
        Assert.assertTrue(att2.compareTo(att1) == -1);
        Assert.assertTrue(att3.compareTo(att2) == -1);
        Assert.assertTrue(att4.compareTo(att2) == -1);

    }

    @Test
    public void partitionTest() {

        List<Pair<String, Dataset<String>>> partitions = dataset.partition(outlook);

        //first partition
        Pair<String, Dataset<String>> p1 = partitions.get(0);
        Assert.assertTrue(p1.getFirst().equals("sunny"));
        Dataset<String> d1 = p1.getSecond();
        Header<String> h1 = new Header<>(temp, humidity, wind, result);
        Assert.assertTrue(d1.getHeader().equals(h1));

        Row<String> r1 = new Row<>("hot", "high", "calm", "bad");
        Row<String> r2 = new Row<>("hot", "high", "windy", "bad");
        Row<String> r3 = new Row<>("mild", "high", "calm", "bad");
        Row<String> r4 = new Row<>("cool", "normal", "calm", "good");
        Row<String> r5 = new Row<>("mild", "normal", "windy", "good");

        Table<String> t1 = new Table<>(r1, r2, r3, r4, r5);
        Assert.assertTrue(d1.getTable().equals(t1));

        //second partition
        Pair<String, Dataset<String>> p2 = partitions.get(1);
        Assert.assertTrue(p2.getFirst().equals("overcast"));
        Dataset<String> d2 = p2.getSecond();
        Header<String> h2 = new Header<>(temp, humidity, wind, result);
        Assert.assertTrue(d2.getHeader().equals(h2));

        Row<String> r21 = new Row<>("hot", "high", "calm", "good");
        Row<String> r22 = new Row<>("cool", "normal", "windy", "good");
        Row<String> r23 = new Row<>("mild", "high", "windy", "good");
        Row<String> r24 = new Row<>("hot", "normal", "calm", "good");

        Table<String> t2 = new Table<>(r21, r22, r23, r24);
        Assert.assertTrue(d2.getTable().equals(t2));

        //third partition
        Pair<String, Dataset<String>> p3 = partitions.get(2);
        Assert.assertTrue(p3.getFirst().equals("rainy"));
        Dataset<String> d3 = p3.getSecond();
        Header<String> h3 = new Header<>(temp, humidity, wind, result);
        Assert.assertTrue(d3.getHeader().equals(h3));

        Row<String> r31 = new Row<>("mild", "high", "calm", "good");
        Row<String> r32 = new Row<>("cool", "normal", "calm", "good");
        Row<String> r33 = new Row<>("cool", "normal", "windy", "bad");
        Row<String> r34 = new Row<>("mild", "normal", "calm", "good");
        Row<String> r35 = new Row<>("mild", "high", "windy", "bad");

        Table<String> t3 = new Table<>(r31, r32, r33, r34, r35);
        Assert.assertTrue(d3.getTable().equals(t3));
    }

    @Test
    public void decisionTreeTest() {
        DecisionTree<String> tree = new DecisionTree<>(dataset, result, new NextAttributeSelector<>());

        String s = "Node outlook\n [(sunny, Node temp\n [(hot, Leaf \"bad\"), (mild, Node " +
                "humidity\n [(high, Leaf \"bad\"), (normal, Leaf \"good\")]), (cool, Leaf \"good\")]), " +
                "(overcast, Leaf \"good\"), (rainy, Node temp\n [(hot, null), (mild, Node " +
                "humidity\n [(high, Node wind\n [(windy, Leaf \"bad\"), (calm, Leaf \"good\")]), " +
                "(normal, Leaf \"good\")]), (cool, Node humidity\n [(high, null), (normal, Node " +
                "wind\n [(windy, Leaf \"bad\"), (calm, Leaf \"good\")])])])]";

        Assert.assertEquals(tree.toString(), s);

    }

    @Test
    public void entropyTest() {
        Assert.assertEquals(0.940, InformationGainSelector.entropy(dataset, result), 0.001);
    }

    @Test
    public void gainTest() {
        Assert.assertEquals(0.246, InformationGainSelector.gain(dataset, outlook, result), 0.001);
    }

    @Test
    public void gainSelectorTest() {
        InformationGainSelector<String> gainSelector = new InformationGainSelector<>();
        Attribute<String> att = gainSelector.nextAttribute(dataset, result);
        Assert.assertEquals(outlook, att);

        String s = "Node outlook\n " +
                "[(sunny, Node humidity\n " +
                "[(high, Leaf \"bad\"), (normal, Leaf \"good\")]), (overcast, Leaf \"good\"), (rainy, Node wind\n " +
                "[(windy, Leaf \"bad\"), (calm, Leaf \"good\")])]";
        DecisionTree<String> tree = new DecisionTree<>(dataset, result, new InformationGainSelector<>());
        Assert.assertEquals(s, tree.toString());
    }
}
