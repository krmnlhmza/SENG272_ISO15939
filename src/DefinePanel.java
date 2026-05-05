import javax.swing.*;
import java.awt.*;
import java.util.List;


public class DefinePanel extends JPanel {

    private final SessionData sessionData;
    private final DataStore dataStore;

    private JRadioButton prodRb, procRb;
    private JRadioButton customRb, healthRb, eduRb;
    private JComboBox<String> scenarioCombo;
    private JLabel customNoteLabel;
    private List<Scenario> currentScenarios;

    public DefinePanel(SessionData sessionData, DataStore dataStore) {
        this.sessionData = sessionData;
        this.dataStore = dataStore;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(buildQualityTypePanel());
        add(buildModePanel());
        add(buildScenarioPanel());


        updateScenarios("Education");
    }

    private JPanel buildQualityTypePanel() {
        JPanel typePanel = new JPanel();
        typePanel.setBorder(BorderFactory.createTitledBorder("Quality Type"));
        ButtonGroup typeGroup = new ButtonGroup();

        prodRb = new JRadioButton("Product Quality", true);
        procRb = new JRadioButton("Process Quality");
        typeGroup.add(prodRb);
        typeGroup.add(procRb);
        typePanel.add(prodRb);
        typePanel.add(procRb);
        return typePanel;
    }

    private JPanel buildModePanel() {
        JPanel modePanel = new JPanel();
        modePanel.setBorder(BorderFactory.createTitledBorder("Mode"));
        ButtonGroup modeGroup = new ButtonGroup();

        customRb = new JRadioButton("Custom");
        healthRb = new JRadioButton("Health");
        eduRb    = new JRadioButton("Education", true);
        modeGroup.add(customRb);
        modeGroup.add(healthRb);
        modeGroup.add(eduRb);
        modePanel.add(customRb);
        modePanel.add(healthRb);
        modePanel.add(eduRb);

        eduRb.addActionListener(e    -> updateScenarios("Education"));
        healthRb.addActionListener(e -> updateScenarios("Health"));
        customRb.addActionListener(e -> updateScenarios("Custom"));
        return modePanel;
    }

    private JPanel buildScenarioPanel() {
        JPanel scenarioPanel = new JPanel();
        scenarioPanel.setBorder(BorderFactory.createTitledBorder("Scenario"));
        scenarioCombo = new JComboBox<>();
        customNoteLabel = new JLabel(" ");
        customNoteLabel.setForeground(new Color(150, 100, 0));
        scenarioPanel.add(scenarioCombo);
        scenarioPanel.add(customNoteLabel);
        return scenarioPanel;
    }


    private void updateScenarios(String mode) {
        scenarioCombo.removeAllItems();
        currentScenarios = dataStore.getScenarios(mode);

        for (Scenario s : currentScenarios) {
            scenarioCombo.addItem(s.name);
        }


        if ("Custom".equals(mode) && currentScenarios.isEmpty()) {
            customNoteLabel.setText("(Custom mode has no preset scenarios — choose Health or Education.)");
        } else {
            customNoteLabel.setText(" ");
        }
    }


    public boolean validateData() {
        if (scenarioCombo.getItemCount() == 0 || scenarioCombo.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please pick a mode that has at least one scenario, then select one to continue.",
                    "No Scenario Selected", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        sessionData.qualityType = prodRb.isSelected() ? "Product Quality" : "Process Quality";

        if (customRb.isSelected())      sessionData.mode = "Custom";
        else if (healthRb.isSelected()) sessionData.mode = "Health";
        else                            sessionData.mode = "Education";

        int idx = scenarioCombo.getSelectedIndex();
        sessionData.selectedScenario = currentScenarios.get(idx);
        return true;
    }
}