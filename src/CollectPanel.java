import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CollectPanel extends JPanel {

    private final SessionData sessionData;
    private JTable table;
    private DefaultTableModel tableModel;
    private final JLabel statusLabel;


    private boolean updatingFromCode = false;

    public CollectPanel(SessionData sessionData) {
        this.sessionData = sessionData;
        setLayout(new BorderLayout());

        String[] columns = {"Metric", "Direction", "Range", "Value", "Score (1-5)", "Coeff / Unit"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) {

                return column == 3;
            }
        };
        table = new JTable(tableModel);

        tableModel.addTableModelListener(e -> {
            if (updatingFromCode) return;
            int row = e.getFirstRow();
            int col = e.getColumn();
            if (col == 3 && row >= 0) {
                handleValueEdit(row);
            }
        });

        statusLabel = new JLabel(" ");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }


    public void loadData() {
        tableModel.setRowCount(0);
        sessionData.collectedValues.clear();
        sessionData.calculatedScores.clear();
        statusLabel.setText(" ");

        if (sessionData.selectedScenario == null) return;

        for (Dimension d : sessionData.selectedScenario.dimensions) {
            for (Metric m : d.metrics) {
                String dirStr = m.higherIsBetter ? "Higher\u2191" : "Lower\u2193";
                String rangeStr = m.minRange + " - " + m.maxRange;
                double defaultVal = m.minRange + (m.maxRange - m.minRange) / 2.0;

                tableModel.addRow(new Object[]{
                        m.name, dirStr, rangeStr,
                        String.valueOf(defaultVal), "",
                        m.coefficient + " / " + m.unit
                });
                applyScore(tableModel.getRowCount() - 1, m, defaultVal);
            }
        }
    }

    private void handleValueEdit(int row) {
        String raw = String.valueOf(tableModel.getValueAt(row, 3));
        String metricName = String.valueOf(tableModel.getValueAt(row, 0));
        Metric target = findMetric(metricName);
        if (target == null) return;

        double value;
        try {
            value = Double.parseDouble(raw.trim());
        } catch (NumberFormatException ex) {
            statusLabel.setText("\u26A0 \"" + raw + "\" is not a valid number for " + metricName + ".");
            return;
        }

        if (value < target.minRange || value > target.maxRange) {
            statusLabel.setText("\u26A0 " + metricName + " value " + value
                    + " is outside range [" + target.minRange + ", " + target.maxRange
                    + "]. Score will be clamped.");
        } else {
            statusLabel.setText(" ");
        }

        applyScore(row, target, value);
    }


    private Metric findMetric(String metricName) {
        if (sessionData.selectedScenario == null) return null;
        for (Dimension d : sessionData.selectedScenario.dimensions) {
            for (Metric m : d.metrics) {
                if (m.name.equals(metricName)) return m;
            }
        }
        return null;
    }


    private void applyScore(int row, Metric m, double value) {
        double score;
        double span = m.maxRange - m.minRange;
        if (span <= 0) {

            score = 3.0;
        } else if (m.higherIsBetter) {
            score = 1 + (value - m.minRange) / span * 4;
        } else {
            score = 5 - (value - m.minRange) / span * 4;
        }
        score = Math.max(1.0, Math.min(5.0, score));
        score = Math.round(score * 2) / 2.0;   // round to nearest 0.5

        updatingFromCode = true;
        tableModel.setValueAt(String.valueOf(score), row, 4);
        updatingFromCode = false;

        sessionData.collectedValues.put(m.name, value);
        sessionData.calculatedScores.put(m.name, score);
    }
}