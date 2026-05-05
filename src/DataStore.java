import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore {

    private final Map<String, List<Scenario>> scenariosByMode;

    public DataStore() {
        scenariosByMode = new HashMap<>();
        initEducationScenarios();
        initHealthScenarios();
        initCustomScenarios();
    }


    private void initEducationScenarios() {
        List<Scenario> eduScenarios = new ArrayList<>();


        Scenario sc = new Scenario("Scenario C - Team Alpha");

        Dimension cUsability = new Dimension("Usability", 25);
        cUsability.addMetric(new Metric("SUS score", 50, true, 0, 100, "points"));
        cUsability.addMetric(new Metric("Onboarding time", 50, false, 0, 60, "min"));

        Dimension cPerf = new Dimension("Perf. Efficiency", 20);
        cPerf.addMetric(new Metric("Video start time", 50, false, 0, 15, "sec"));
        cPerf.addMetric(new Metric("Concurrent exams", 50, true, 0, 600, "users"));

        Dimension cAccess = new Dimension("Accessibility", 20);
        cAccess.addMetric(new Metric("WCAG compliance", 50, true, 0, 100, "%"));
        cAccess.addMetric(new Metric("Screen reader score", 50, true, 0, 100, "%"));

        Dimension cReliability = new Dimension("Reliability", 20);
        cReliability.addMetric(new Metric("Uptime", 50, true, 95, 100, "%"));
        cReliability.addMetric(new Metric("MTTR", 50, false, 0, 120, "min"));

        Dimension cFunc = new Dimension("Func. Suitability", 15);
        cFunc.addMetric(new Metric("Feature completion", 50, true, 0, 100, "%"));
        cFunc.addMetric(new Metric("Assignment submit rate", 50, true, 0, 100, "%"));

        sc.addDimension(cUsability);
        sc.addDimension(cPerf);
        sc.addDimension(cAccess);
        sc.addDimension(cReliability);
        sc.addDimension(cFunc);
        eduScenarios.add(sc);


        Scenario sd = new Scenario("Scenario D - Team Beta");

        Dimension dUsability = new Dimension("Usability", 30);
        dUsability.addMetric(new Metric("SUS score", 60, true, 0, 100, "points"));
        dUsability.addMetric(new Metric("Help-page visits", 40, false, 0, 200, "visits"));

        Dimension dPerf = new Dimension("Perf. Efficiency", 25);
        dPerf.addMetric(new Metric("Page load time", 50, false, 0, 8, "sec"));
        dPerf.addMetric(new Metric("Concurrent users", 50, true, 0, 1000, "users"));

        Dimension dReliability = new Dimension("Reliability", 25);
        dReliability.addMetric(new Metric("Uptime", 60, true, 95, 100, "%"));
        dReliability.addMetric(new Metric("Crash rate", 40, false, 0, 10, "%"));

        Dimension dFunc = new Dimension("Func. Suitability", 20);
        dFunc.addMetric(new Metric("Feature completion", 100, true, 0, 100, "%"));

        sd.addDimension(dUsability);
        sd.addDimension(dPerf);
        sd.addDimension(dReliability);
        sd.addDimension(dFunc);
        eduScenarios.add(sd);

        scenariosByMode.put("Education", eduScenarios);
    }


    private void initHealthScenarios() {
        List<Scenario> healthScenarios = new ArrayList<>();


        Scenario sh1 = new Scenario("Health Scenario 1 - Patient Portal");

        Dimension h1Usability = new Dimension("Usability", 30);
        h1Usability.addMetric(new Metric("Booking time", 60, false, 0, 10, "min"));
        h1Usability.addMetric(new Metric("SUS score", 40, true, 0, 100, "points"));

        Dimension h1Security = new Dimension("Security", 35);
        h1Security.addMetric(new Metric("Encryption coverage", 50, true, 0, 100, "%"));
        h1Security.addMetric(new Metric("Auth failure rate", 50, false, 0, 5, "%"));

        Dimension h1Reliability = new Dimension("Reliability", 35);
        h1Reliability.addMetric(new Metric("Uptime", 60, true, 95, 100, "%"));
        h1Reliability.addMetric(new Metric("MTTR", 40, false, 0, 60, "min"));

        sh1.addDimension(h1Usability);
        sh1.addDimension(h1Security);
        sh1.addDimension(h1Reliability);
        healthScenarios.add(sh1);


        Scenario sh2 = new Scenario("Health Scenario 2 - Hospital Info System");

        Dimension h2Perf = new Dimension("Perf. Efficiency", 30);
        h2Perf.addMetric(new Metric("Record fetch time", 50, false, 0, 5, "sec"));
        h2Perf.addMetric(new Metric("Concurrent doctors", 50, true, 0, 500, "users"));

        Dimension h2Reliability = new Dimension("Reliability", 40);
        h2Reliability.addMetric(new Metric("Uptime", 60, true, 95, 100, "%"));
        h2Reliability.addMetric(new Metric("Data loss rate", 40, false, 0, 2, "%"));

        Dimension h2Compatibility = new Dimension("Compatibility", 30);
        h2Compatibility.addMetric(new Metric("HL7 compliance", 50, true, 0, 100, "%"));
        h2Compatibility.addMetric(new Metric("Browser support", 50, true, 0, 100, "%"));

        sh2.addDimension(h2Perf);
        sh2.addDimension(h2Reliability);
        sh2.addDimension(h2Compatibility);
        healthScenarios.add(sh2);

        scenariosByMode.put("Health", healthScenarios);
    }


    private void initCustomScenarios() {
        scenariosByMode.put("Custom", new ArrayList<>());
    }

    public List<Scenario> getScenarios(String mode) {
        return scenariosByMode.getOrDefault(mode, new ArrayList<>());
    }
}