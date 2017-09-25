import org.junit.Test;

public class Tests {

    @Test
    public void partitionTest1() {

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

    }
}
