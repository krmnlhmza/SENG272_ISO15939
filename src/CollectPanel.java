import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CollectPanel extends JPanel {
    private SessionData sessionData;
    private JTable table;
    private DefaultTableModel tableModel;

    public CollectPanel(SessionData sessionData) {
        this.sessionData = sessionData;
        setLayout(new BorderLayout());
        String[] columns = {"Metric", "Direction", "Range", "Value", "Score (1-5)", "Coeff / Unit"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return column == 3; }
        };
        table = new JTable(tableModel);

        tableModel.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int col = e.getColumn();
            if (col == 3) {
                calculateScore(row);
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void loadData() {
        tableModel.setRowCount(0);
        sessionData.collectedValues.clear();
        sessionData.calculatedScores.clear();

        if (sessionData.selectedScenario != null) {
            for (Dimension d : sessionData.selectedScenario.dimensions) {
                for (Metric m : d.metrics) {
                    String dirStr = m.higherIsBetter ? "Higher↑" : "Lower↓";
                    String rangeStr = m.minRange + "-" + m.maxRange;
                    double defaultVal = m.minRange + (m.maxRange - m.minRange) / 2.0;
                    tableModel.addRow(new Object[]{m.name, dirStr, rangeStr, String.valueOf(defaultVal), "", m.coefficient + "/" + m.unit});
                    calculateScore(tableModel.getRowCount() - 1, m, defaultVal);
                }
            }
        }
    }

    private void calculateScore(int row) {
        try {
            double val = Double.parseDouble(tableModel.getValueAt(row, 3).toString());
            String metricName = tableModel.getValueAt(row, 0).toString();
            Metric targetMetric = null;
            for (Dimension d : sessionData.selectedScenario.dimensions) {
                for (Metric m : d.metrics) {
                    if (m.name.equals(metricName)) targetMetric = m;
                }
            }
            if (targetMetric != null) calculateScore(row, targetMetric, val);
        } catch (NumberFormatException ignored) {}
    }

    private void calculateScore(int row, Metric m, double value) {
        double score;
        if (m.higherIsBetter) {
            score = 1 + (value - m.minRange) / (m.maxRange - m.minRange) * 4;
        } else {
            score = 5 - (value - m.minRange) / (m.maxRange - m.minRange) * 4;
        }
        score = Math.max(1.0, Math.min(5.0, score));
        score = Math.round(score * 2) / 2.0;

        tableModel.setValueAt(String.valueOf(score), row, 4);
        sessionData.collectedValues.put(m.name, value);
        sessionData.calculatedScores.put(m.name, score);
    }
}