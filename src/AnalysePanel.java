import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class AnalysePanel extends JPanel {

    private final SessionData sessionData;
    private final JPanel progressPanel;
    private final RadarChartPanel radarPanel;
    private final JLabel gapLabel;

    public AnalysePanel(SessionData sessionData) {
        this.sessionData = sessionData;
        setLayout(new BorderLayout());

        progressPanel = new JPanel();
        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));
        progressPanel.setBorder(BorderFactory.createTitledBorder("Dimension Scores (Weighted Average)"));

        radarPanel = new RadarChartPanel();
        radarPanel.setPreferredSize(new java.awt.Dimension(300, 300));
        radarPanel.setBorder(BorderFactory.createTitledBorder("Radar Chart"));

        gapLabel = new JLabel();
        gapLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.add(new JScrollPane(progressPanel));
        topPanel.add(radarPanel);

        add(topPanel, BorderLayout.CENTER);
        add(gapLabel, BorderLayout.SOUTH);
    }


    public void loadData() {
        progressPanel.removeAll();
        gapLabel.setText("");


        if (sessionData.selectedScenario == null
                || sessionData.selectedScenario.dimensions.isEmpty()) {
            gapLabel.setText("<html><i>No scenario data to analyse.</i></html>");
            radarPanel.setScores(new HashMap<>());
            revalidate();
            repaint();
            return;
        }


        Map<String, Double> dimScores = new LinkedHashMap<>();
        double lowestScore = Double.POSITIVE_INFINITY;
        String lowestDim = "";

        for (Dimension d : sessionData.selectedScenario.dimensions) {
            double weightedSum = 0;
            double totalCoeff = 0;

            for (Metric m : d.metrics) {
                Double s = sessionData.calculatedScores.get(m.name);
                if (s != null) {
                    weightedSum += s * m.coefficient;
                    totalCoeff += m.coefficient;
                }
            }


            double dimScore = (totalCoeff > 0) ? (weightedSum / totalCoeff) : 0.0;
            dimScores.put(d.name, dimScore);

            if (dimScore < lowestScore) {
                lowestScore = dimScore;
                lowestDim = d.name;
            }

            progressPanel.add(buildDimensionRow(d.name, dimScore));
            progressPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 5)));
        }

        radarPanel.setScores(dimScores);
        writeGapAnalysis(lowestDim, lowestScore);

        revalidate();
        repaint();
    }


    private JPanel buildDimensionRow(String name, double score) {
        JPanel row = new JPanel(new BorderLayout());
        row.add(new JLabel(name + " (" + String.format("%.2f", score) + ") "), BorderLayout.WEST);



        JProgressBar pb = new JProgressBar(0, 50);
        pb.setValue((int) Math.round(score * 10));
        pb.setStringPainted(true);
        pb.setString(String.format("%.2f", score) + " / 5.0");

        if (score < 2.5)      pb.setForeground(Color.RED);
        else if (score < 4.0) pb.setForeground(Color.ORANGE);
        else                  pb.setForeground(new Color(0, 153, 0));

        row.add(pb, BorderLayout.CENTER);
        return row;
    }


    private void writeGapAnalysis(String lowestDim, double lowestScore) {
        if (lowestDim.isEmpty() || Double.isInfinite(lowestScore)) return;

        double gap = 5.0 - lowestScore;
        String level;
        if      (lowestScore >= 4.0) level = "Excellent";
        else if (lowestScore >= 3.0) level = "Good";
        else if (lowestScore >= 2.0) level = "Needs Improvement";
        else                         level = "Poor";

        gapLabel.setText(
                "<html><b>Gap Analysis:</b> Dimension with the lowest score is <b>" + lowestDim + "</b> ("
                        + String.format("%.2f", lowestScore) + "). "
                        + "Gap value: " + String.format("%.2f", gap) + ". "
                        + "Quality level: " + level + ".<br/>"
                        + "<i>This dimension has the lowest score and requires the most improvement.</i></html>"
        );
    }
}