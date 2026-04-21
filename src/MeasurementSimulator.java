import javax.swing.*;
import java.awt.*;

public class MeasurementSimulator extends JFrame {

    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JPanel stepIndicatorPanel;
    private JButton nextButton;
    private JButton prevButton;

    private ProfilePanel profilePanel;
    private DefinePanel definePanel;
    private PlanPanel planPanel;
    private CollectPanel collectPanel;
    private AnalysePanel analysePanel;

    private int currentStep = 0;
    private final String[] steps = {"Profile", "Define", "Plan", "Collect", "Analyse"};
    private JLabel[] stepLabels;

    private SessionData sessionData;
    private DataStore dataStore;

    public MeasurementSimulator() {
        super("ISO 15939 Measurement Process Simulator");
        sessionData = new SessionData();
        dataStore = new DataStore();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initStepIndicator();
        initCardPanel();
        initNavigationPanel();

        updateNavigationState();
        setVisible(true);
    }

    private void initStepIndicator() {
        stepIndicatorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        stepIndicatorPanel.setBackground(Color.WHITE);
        stepLabels = new JLabel[steps.length];

        for (int i = 0; i < steps.length; i++) {
            stepLabels[i] = new JLabel(steps[i]);
            stepLabels[i].setFont(new Font("Arial", Font.PLAIN, 14));
            stepLabels[i].setForeground(Color.GRAY);
            stepIndicatorPanel.add(stepLabels[i]);
            if (i < steps.length - 1) {
                JLabel arrow = new JLabel("→");
                arrow.setForeground(Color.LIGHT_GRAY);
                stepIndicatorPanel.add(arrow);
            }
        }
        add(stepIndicatorPanel, BorderLayout.NORTH);
        updateStepIndicator();
    }

    private void initCardPanel() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        profilePanel = new ProfilePanel(sessionData);
        definePanel = new DefinePanel(sessionData, dataStore);
        planPanel = new PlanPanel(sessionData);
        collectPanel = new CollectPanel(sessionData);
        analysePanel = new AnalysePanel(sessionData);

        cardPanel.add(profilePanel, steps[0]);
        cardPanel.add(definePanel, steps[1]);
        cardPanel.add(planPanel, steps[2]);
        cardPanel.add(collectPanel, steps[3]);
        cardPanel.add(analysePanel, steps[4]);

        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(cardPanel, BorderLayout.CENTER);
    }

    private void initNavigationPanel() {
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");

        prevButton.addActionListener(e -> navigate(-1));
        nextButton.addActionListener(e -> navigate(1));

        navPanel.add(prevButton);
        navPanel.add(nextButton);
        add(navPanel, BorderLayout.SOUTH);
    }

    private void navigate(int direction) {
        if (direction == 1) {
            if (currentStep == 0 && !profilePanel.validateData()) return;
            if (currentStep == 1 && !definePanel.validateData()) return;
        }

        currentStep += direction;

        if (currentStep == 2) planPanel.loadData();
        if (currentStep == 3) collectPanel.loadData();
        if (currentStep == 4) analysePanel.loadData();

        cardLayout.show(cardPanel, steps[currentStep]);
        updateNavigationState();
        updateStepIndicator();
    }

    private void updateNavigationState() {
        prevButton.setEnabled(currentStep > 0);
        nextButton.setEnabled(currentStep < steps.length - 1);
    }

    private void updateStepIndicator() {
        for (int i = 0; i < steps.length; i++) {
            if (i == currentStep) {
                stepLabels[i].setFont(new Font("Arial", Font.BOLD, 14));
                stepLabels[i].setForeground(Color.BLACK);
                stepLabels[i].setText(steps[i]);
            } else if (i < currentStep) {
                stepLabels[i].setFont(new Font("Arial", Font.PLAIN, 14));
                stepLabels[i].setForeground(new Color(0, 153, 0));
                stepLabels[i].setText(steps[i] + " ✓");
            } else {
                stepLabels[i].setFont(new Font("Arial", Font.PLAIN, 14));
                stepLabels[i].setForeground(Color.GRAY);
                stepLabels[i].setText(steps[i]);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MeasurementSimulator::new);
    }
}