
public class Metric {
    public String name;
    public double coefficient;
    public boolean higherIsBetter;
    public double minRange;
    public double maxRange;
    public String unit;

    public Metric(String name, double coefficient, boolean higherIsBetter,
                  double minRange, double maxRange, String unit) {
        this.name = name;
        this.coefficient = coefficient;
        this.higherIsBetter = higherIsBetter;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.unit = unit;
    }
}