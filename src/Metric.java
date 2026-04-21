public class Metric {
    String name;
    double coefficient;
    boolean higherIsBetter;
    double minRange;
    double maxRange;
    String unit;

    public Metric(String name, double coefficient, boolean higherIsBetter, double minRange, double maxRange, String unit) {
        this.name = name;
        this.coefficient = coefficient;
        this.higherIsBetter = higherIsBetter;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.unit = unit;
    }
}