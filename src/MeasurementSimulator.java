import javax.swing.*;
import java.awt.*;


public class MeasurementSimulator {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            AppController controller = new AppController();
            controller.startApplication();
        });
    }
}


class UserProfile {
    private String username;
    private String school;
    private String sessionName;

    public void setProfileData(String username, String school, String sessionName) {
        this.username = username;
        this.school = school;
        this.sessionName = sessionName;
    }

    public boolean isValid() {
        return username != null && !username.trim().isEmpty() &&
                school != null && !school.trim().isEmpty() &&
                sessionName != null && !sessionName.trim().isEmpty();
    }
}

class SessionData {
    private String qualityType;
    private String mode;
    private String scenario;

    public void setDefinitionData(String qualityType, String mode, String scenario) {
        this.qualityType = qualityType;
        this.mode = mode;
        this.scenario = scenario;
    }
}

class AppController {
    private MainFrame mainFrame;
    private UserProfile userProfile;
    private SessionData sessionData;

    public AppController() {
        this.userProfile = new UserProfile();
        this.sessionData = new SessionData();
        this.mainFrame = new MainFrame(this);
    }

    public void startApplication() {
        mainFrame.setVisible(true);
    }

    // Step 1 to Step 2 Transition
    public void submitProfileAndNext(String username, String school, String session) {
        userProfile.setProfileData(username, school, session);
        if (userProfile.isValid()) {
            mainFrame.showStep("DEFINE", 1);
        } else {
            mainFrame.showError("All fields must be filled in before proceeding to the next step.\nPlease check your inputs."); // [cite: 30, 31]
        }
    }

    // Step 2 to Step 3 Transition
    public void submitDefinitionAndNext(String qualityType, String mode, String scenario) {
        if (qualityType == null || mode == null || scenario == null) {
            mainFrame.showError("Please select a Quality Type, Mode, and Scenario to proceed.");
        } else {
            sessionData.setDefinitionData(qualityType, mode, scenario);
            mainFrame.showStep("PLAN", 2);
        }
    }
}

class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private StepIndicatorPanel stepIndicator;
    private AppController controller;

    public MainFrame(AppController controller) {
        this.controller = controller;
        setTitle("ISO 15939 Measurement Process Simulator"); // [cite: 1]
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        stepIndicator = new StepIndicatorPanel();
        add(stepIndicator, BorderLayout.NORTH);


        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);


        cardPanel.add(new ProfilePanel(controller), "PROFILE");
        cardPanel.add(new DefinePanel(controller), "DEFINE");


        cardPanel.add(createPlaceholderPanel("Plan Measurement (To be implemented)"), "PLAN");
        cardPanel.add(createPlaceholderPanel("Collect Data (To be implemented)"), "COLLECT");
        cardPanel.add(createPlaceholderPanel("Analyse Results (To be implemented)"), "ANALYSE");

        add(cardPanel, BorderLayout.CENTER);
    }

    public void showStep(String stepName, int stepIndex) {
        cardLayout.show(cardPanel, stepName);
        stepIndicator.updateStep(stepIndex);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.WARNING_MESSAGE);
    }

    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(new JLabel(text));
        return panel;
    }
}


class StepIndicatorPanel extends JPanel {
    private String[] steps = {"Profile", "Define", "Plan", "Collect", "Analyse"}; // [cite: 92]
    private JLabel[] labels;

    public StepIndicatorPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
        setBackground(new Color(240, 240, 240));
        labels = new JLabel[steps.length];

        for (int i = 0; i < steps.length; i++) {
            labels[i] = new JLabel(steps[i]);
            labels[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
            add(labels[i]);
            if (i < steps.length - 1) {
                JLabel arrow = new JLabel(" \u2794 ");
                arrow.setForeground(Color.GRAY);
                add(arrow);
            }
        }
        updateStep(0);
    }

    public void updateStep(int activeIndex) {
        for (int i = 0; i < labels.length; i++) {
            if (i == activeIndex) {
                labels[i].setFont(new Font("SansSerif", Font.BOLD, 15)); // Highlight active [cite: 90]
                labels[i].setForeground(new Color(0, 102, 204));
                labels[i].setText(steps[i]);
            } else if (i < activeIndex) {
                labels[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
                labels[i].setForeground(new Color(34, 139, 34)); // Green check [cite: 91]
                labels[i].setText(steps[i] + " \u2714");
            } else {
                labels[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
                labels[i].setForeground(Color.GRAY);
                labels[i].setText(steps[i]);
            }
        }
    }
}

// UI Component: Step 1 - Profile [cite: 23]
class ProfilePanel extends JPanel {
    private JTextField txtUsername, txtSchool, txtSession;

    public ProfilePanel(AppController controller) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;


        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Step 1: Session Profile");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++; add(new JLabel("Username:"), gbc); // [cite: 26]
        gbc.gridx = 1; txtUsername = new JTextField(20); add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy++; add(new JLabel("School:"), gbc); // [cite: 27]
        gbc.gridx = 1; txtSchool = new JTextField(20); add(txtSchool, gbc);

        gbc.gridx = 0; gbc.gridy++; add(new JLabel("Session Name:"), gbc); // [cite: 28]
        gbc.gridx = 1; txtSession = new JTextField(20); add(txtSession, gbc);

        // Next Button
        gbc.gridx = 1; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        JButton btnNext = new JButton("Next Step");
        add(btnNext, gbc);

        btnNext.addActionListener(e -> {
            controller.submitProfileAndNext(
                    txtUsername.getText(),
                    txtSchool.getText(),
                    txtSession.getText()
            );
        });
    }
}


class DefinePanel extends JPanel {
    private ButtonGroup typeGroup, modeGroup;
    private JComboBox<String> cmbScenario;

    public DefinePanel(AppController controller) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titleLabel = new JLabel("Step 2: Define Quality Dimensions"); // [cite: 32]
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
        add(Box.createVerticalStrut(20));


        JPanel pnlType = new JPanel();
        pnlType.setBorder(BorderFactory.createTitledBorder("2a. Quality Type"));
        typeGroup = new ButtonGroup();
        JRadioButton rbProduct = new JRadioButton("Product Quality"); // [cite: 37]
        JRadioButton rbProcess = new JRadioButton("Process Quality"); // [cite: 37]
        typeGroup.add(rbProduct); typeGroup.add(rbProcess);
        pnlType.add(rbProduct); pnlType.add(rbProcess);
        add(pnlType);


        JPanel pnlMode = new JPanel();
        pnlMode.setBorder(BorderFactory.createTitledBorder("2b. Mode"));
        modeGroup = new ButtonGroup();
        JRadioButton rbCustom = new JRadioButton("Custom"); // [cite: 41]
        JRadioButton rbHealth = new JRadioButton("Health"); // [cite: 41]
        JRadioButton rbEducation = new JRadioButton("Education"); // [cite: 41]
        modeGroup.add(rbCustom); modeGroup.add(rbHealth); modeGroup.add(rbEducation);
        pnlMode.add(rbCustom); pnlMode.add(rbHealth); pnlMode.add(rbEducation);
        add(pnlMode);


        JPanel pnlScenario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlScenario.setBorder(BorderFactory.createTitledBorder("2c. Scenario"));
        String[] scenarios = {"Scenario C - Team Alpha", "Scenario D - Team Beta"}; // [cite: 44, 45]
        cmbScenario = new JComboBox<>(scenarios);
        pnlScenario.add(new JLabel("Select Scenario: "));
        pnlScenario.add(cmbScenario);
        add(pnlScenario);

        add(Box.createVerticalStrut(20));


        JButton btnNext = new JButton("Next Step");
        btnNext.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(btnNext);

        btnNext.addActionListener(e -> {
            String selectedType = getSelectedButtonText(typeGroup);
            String selectedMode = getSelectedButtonText(modeGroup);
            String selectedScenario = (String) cmbScenario.getSelectedItem();
            controller.submitDefinitionAndNext(selectedType, selectedMode, selectedScenario);
        });
    }

    private String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (java.util.Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) return button.getText();
        }
        return null;
    }
}