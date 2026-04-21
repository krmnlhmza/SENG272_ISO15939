import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore {
    Map<String, List<Scenario>> scenariosByMode;

    public DataStore() {
        scenariosByMode = new HashMap<>();
        initData();
    }

    private void initData() {
        List<Scenario> eduScenarios = new ArrayList<>();
        Scenario sc = new Scenario("Scenario C - Team Alpha");

        Dimension d1 = new Dimension("Usability", 25);
        d1.addMetric(new Metric("SUS score", 50, true, 0, 100, "points"));
        d1.addMetric(new Metric("Onboarding time", 50, false, 0, 60, "min"));

        Dimension d2 = new Dimension("Perf. Efficiency", 20);
        d2.addMetric(new Metric("Video start time", 50, false, 0, 15, "sec"));
        d2.addMetric(new Metric("Concurrent exams", 50, true, 0, 600, "users"));

        Dimension d3 = new Dimension("Accessibility", 20);
        d3.addMetric(new Metric("WCAG compliance", 50, true, 0, 100, "%"));
        d3.addMetric(new Metric("Screen reader score", 50, true, 0, 100, "%"));

        Dimension d4 = new Dimension("Reliability", 20);
        d4.addMetric(new Metric("Uptime", 50, true, 95, 100, "%"));
        d4.addMetric(new Metric("MTTR", 50, false, 0, 120, "min"));

        Dimension d5 = new Dimension("Func. Suitability", 15);
        d5.addMetric(new Metric("Feature completion", 50, true, 0, 100, "%"));
        d5.addMetric(new Metric("Assignment submit rate", 50, true, 0, 100, "%"));

        sc.addDimension(d1);
        sc.addDimension(d2);
        sc.addDimension(d3);
        sc.addDimension(d4);
        sc.addDimension(d5);

        eduScenarios.add(sc);

        Scenario sd = new Scenario("Scenario D - Team Beta");
        sd.addDimension(d1);
        sd.addDimension(d2);
        eduScenarios.add(sd);

        List<Scenario> healthScenarios = new ArrayList<>();
        Scenario sh1 = new Scenario("Health Scenario 1 - Patient Portal");
        Dimension hd1 = new Dimension("Usability", 40);
        hd1.addMetric(new Metric("Booking time", 100, false, 0, 10, "min"));
        sh1.addDimension(hd1);
        healthScenarios.add(sh1);

        scenariosByMode.put("Education", eduScenarios);
        scenariosByMode.put("Health", healthScenarios);
        scenariosByMode.put("Custom", new ArrayList<>());
    }

    public List<Scenario> getScenarios(String mode) {
        return scenariosByMode.getOrDefault(mode, new ArrayList<>());
    }
}