package kmeans;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Tests {

    DataPoint A = new DataPoint(1.0, 1.0);
    DataPoint B = new DataPoint(2.0, 1.0);
    DataPoint C = new DataPoint(2.0, 2.0);
    DataPoint D = new DataPoint(1.0, 3.0);
    DataPoint E = new DataPoint(3.0, 4.0);
    DataPoint F = new DataPoint(4.0, 4.0);
    DataPoint G = new DataPoint(5.0, 3.0);
    DataPoint H = new DataPoint(6.0, 5.0);
    DataPoint C1 = new DataPoint(2.0, 3.0);
    DataPoint C2 = new DataPoint(4.0, 3.0);
    List<DataPoint> list = new ArrayList<>();

    @Test
    public void KMeans2() {
        list.add(A);
        list.add(B);
        list.add(C);
        list.add(D);
        list.add(E);
        list.add(F);
        list.add(G);
        list.add(H);

        KMeans kMeans = new KMeans(2, list, (k, points) -> {
            List<DataPoint> list = new ArrayList<>();
            list.add(C1);
            list.add(C2);
            return list;
        });

        System.out.println(kMeans);


    }


}
