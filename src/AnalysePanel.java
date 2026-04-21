import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AnalysePanel extends JPanel {
    private SessionData sessionData;
    private JPanel progressPanel;
    private RadarChartPanel radarPanel;
    private JLabel gapLabel;

    public AnalysePanel(SessionData sessionData) {
        this.sessionData = sessionData;
        setLayout(new BorderLayout());

        progressPanel = new JPanel();
        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));
        progressPanel.setBorder(BorderFactory.createTitledBorder("Dimension Scores"));

        radarPanel = new RadarChartPanel();
        radarPanel.setPreferredSize(new java.awt.Dimension(300, 300));

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
        Map<String, Double> dimScores = new HashMap<>();

        double lowestScore = 6.0;
        String lowestDim = "";

        if (sessionData.selectedScenario != null) {
            for (Dimension d : sessionData.selectedScenario.dimensions) {
                double num = 0;
                double den = 0;
                for (Metric m : d.metrics) {
                    Double s = sessionData.calculatedScores.get(m.name);
                    if (s != null) {
                        num += s * m.coefficient;
                        den += m.coefficient;
                    }
                }
                double finalScore = den > 0 ? num / den : 0;
                dimScores.put(d.name, finalScore);

                if (finalScore < lowestScore) {
                    lowestScore = finalScore;
                    lowestDim = d.name;
                }

                JPanel row = new JPanel(new BorderLayout());
                row.add(new JLabel(d.name + " (" + String.format("%.2f", finalScore) + ") "), BorderLayout.WEST);
                JProgressBar pb = new JProgressBar(0, 500);
                pb.setValue((int)(finalScore * 100));
                pb.setStringPainted(true);
                pb.setString(String.format("%.2f", finalScore) + " / 5.0");
                if (finalScore < 2.5) pb.setForeground(Color.RED);
                else if (finalScore < 4.0) pb.setForeground(Color.ORANGE);
                else pb.setForeground(Color.GREEN);
                row.add(pb, BorderLayout.CENTER);
                progressPanel.add(row);
                progressPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 5)));
            }
        }

        radarPanel.setScores(dimScores);

        if (!lowestDim.isEmpty()) {
            double gap = 5.0 - lowestScore;
            String level = lowestScore >= 4 ? "Excellent" : (lowestScore >= 3 ? "Good" : (lowestScore >= 2 ? "Needs Improvement" : "Poor"));
            gapLabel.setText("<html><b>Gap Analysis:</b> Dimension with lowest score is <b>" + lowestDim + "</b> (" + String.format("%.2f", lowestScore) + "). " +
                    "Gap value: " + String.format("%.2f", gap) + ". Quality level: " + level + ".<br/>" +
                    "<i>This dimension has the lowest score and requires the most improvement.</i></html>");
        }

        revalidate();
        repaint();
    }
}