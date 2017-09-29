package kmeans;


import java.util.List;

public interface Initialiser {

    List<DataPoint> createInitialCentroids(int k, List<DataPoint> points);

}
