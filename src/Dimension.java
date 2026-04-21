import java.util.ArrayList;
import java.util.List;

public class Dimension {
    String name;
    double coefficient;
    List<Metric> metrics;

    public Dimension(String name, double coefficient) {
        this.name = name;
        this.coefficient = coefficient;
        this.metrics = new ArrayList<>();
    }

    public void addMetric(Metric metric) {
        metrics.add(metric);
    }
}