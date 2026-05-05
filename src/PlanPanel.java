import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PlanPanel extends JPanel {

    private final SessionData sessionData;
    private JTable table;
    private DefaultTableModel tableModel;

    public PlanPanel(SessionData sessionData) {
        this.sessionData = sessionData;
        setLayout(new BorderLayout());

        String[] columns = {"Dimension", "Metric", "Coefficient", "Direction", "Range", "Unit"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) {

                return false;
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void loadData() {
        tableModel.setRowCount(0);
        if (sessionData.selectedScenario == null) return;

        for (Dimension d : sessionData.selectedScenario.dimensions) {
            for (int i = 0; i < d.metrics.size(); i++) {
                Metric m = d.metrics.get(i);

                String dimStr = (i == 0)
                        ? d.name + " (Coefficient: " + d.coefficient + ")"
                        : "";
                String dirStr = m.higherIsBetter ? "Higher\u2191" : "Lower\u2193";
                String rangeStr = m.minRange + " - " + m.maxRange;

                tableModel.addRow(new Object[]{
                        dimStr, m.name, m.coefficient, dirStr, rangeStr, m.unit
                });
            }
        }
    }
}