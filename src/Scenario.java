import java.util.ArrayList;
import java.util.List;

public class Scenario {
    String name;
    List<Dimension> dimensions;

    public Scenario(String name) {
        this.name = name;
        this.dimensions = new ArrayList<>();
    }

    public void addDimension(Dimension dimension) {
        dimensions.add(dimension);
    }
}