import javax.swing.*;
import java.util.List;

public class DefinePanel extends JPanel {
    private SessionData sessionData;
    private DataStore dataStore;
    private JRadioButton prodRb, procRb, customRb, healthRb, eduRb;
    private JComboBox<String> scenarioCombo;
    private List<Scenario> currentScenarios;

    public DefinePanel(SessionData sessionData, DataStore dataStore) {
        this.sessionData = sessionData;
        this.dataStore = dataStore;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel typePanel = new JPanel();
        typePanel.setBorder(BorderFactory.createTitledBorder("Quality Type"));
        ButtonGroup typeGroup = new ButtonGroup();
        prodRb = new JRadioButton("Product Quality", true);
        procRb = new JRadioButton("Process Quality");
        typeGroup.add(prodRb); typeGroup.add(procRb);
        typePanel.add(prodRb); typePanel.add(procRb);

        JPanel modePanel = new JPanel();
        modePanel.setBorder(BorderFactory.createTitledBorder("Mode"));
        ButtonGroup modeGroup = new ButtonGroup();
        customRb = new JRadioButton("Custom");
        healthRb = new JRadioButton("Health");
        eduRb = new JRadioButton("Education", true);
        modeGroup.add(customRb); modeGroup.add(healthRb); modeGroup.add(eduRb);
        modePanel.add(customRb); modePanel.add(healthRb); modePanel.add(eduRb);

        JPanel scenarioPanel = new JPanel();
        scenarioPanel.setBorder(BorderFactory.createTitledBorder("Scenario"));
        scenarioCombo = new JComboBox<>();
        scenarioPanel.add(scenarioCombo);

        add(typePanel);
        add(modePanel);
        add(scenarioPanel);

        eduRb.addActionListener(e -> updateScenarios("Education"));
        healthRb.addActionListener(e -> updateScenarios("Health"));
        customRb.addActionListener(e -> updateScenarios("Custom"));

        updateScenarios("Education");
    }

    private void updateScenarios(String mode) {
        scenarioCombo.removeAllItems();
        currentScenarios = dataStore.getScenarios(mode);
        for (Scenario s : currentScenarios) {
            scenarioCombo.addItem(s.name);
        }
    }

    public boolean validateData() {
        if (scenarioCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid scenario.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        sessionData.qualityType = prodRb.isSelected() ? "Product Quality" : "Process Quality";
        if (customRb.isSelected()) sessionData.mode = "Custom";
        else if (healthRb.isSelected()) sessionData.mode = "Health";
        else sessionData.mode = "Education";

        int idx = scenarioCombo.getSelectedIndex();
        sessionData.selectedScenario = currentScenarios.get(idx);
        return true;
    }
}