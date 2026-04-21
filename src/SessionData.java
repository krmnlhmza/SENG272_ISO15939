import java.util.HashMap;
import java.util.Map;

public class SessionData {
    public String username;
    public String school;
    public String sessionName;
    public String qualityType;
    public String mode;
    public Scenario selectedScenario;
    public Map<String, Double> collectedValues = new HashMap<>();
    public Map<String, Double> calculatedScores = new HashMap<>();
}