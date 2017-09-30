package kmeans;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class KMeansVisualise extends JFrame {

    private KMeans kMeans;

    public KMeansVisualise(KMeans kMeans, String title) {
        super(title);
        this.kMeans = kMeans;

        JFreeChart a = ChartFactory.createScatterPlot(title, "x", "y", createDataset());
        ChartPanel panel = new ChartPanel(a);
        setContentPane(panel);
    }

    private XYDataset createDataset() {

        XYSeriesCollection dataset = new XYSeriesCollection();

        List<Cluster> clusters = kMeans.getClusters();

        int count = 0;
        for (Cluster c : clusters) {
            XYSeries series = new XYSeries(count);
            for (DataPoint d : c.getDataPoints()) {
                Double d1 = d.getComponents().get(0);
                Double d2 = d.getComponents().get(1);
                series.add(d1, d2);
            }
            count++;
            dataset.addSeries(series);
        }
        return dataset;
    }

    public void showGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 700));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
