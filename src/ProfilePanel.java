import javax.swing.*;
import java.awt.*;


public class ProfilePanel extends JPanel {

    private final JTextField userField, schoolField, sessionField;
    private final SessionData sessionData;

    public ProfilePanel(SessionData sessionData) {
        this.sessionData = sessionData;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        userField = new JTextField("Muhammed Hamza Karamanlı", 20);
        add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("School:"), gbc);
        gbc.gridx = 1;
        schoolField = new JTextField("Çankaya University", 20);
        add(schoolField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Session Name:"), gbc);
        gbc.gridx = 1;
        sessionField = new JTextField("SENG272 Final Project", 20);
        add(sessionField, gbc);
    }

    public boolean validateData() {
        if (userField.getText().trim().isEmpty()) {
            warn("Please enter your username to continue.");
            userField.requestFocus();
            return false;
        }
        if (schoolField.getText().trim().isEmpty()) {
            warn("Please enter your school name to continue.");
            schoolField.requestFocus();
            return false;
        }
        if (sessionField.getText().trim().isEmpty()) {
            warn("Please enter a session name to continue.");
            sessionField.requestFocus();
            return false;
        }

        sessionData.username    = userField.getText().trim();
        sessionData.school      = schoolField.getText().trim();
        sessionData.sessionName = sessionField.getText().trim();
        return true;
    }

    private void warn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Missing Information", JOptionPane.WARNING_MESSAGE);
    }
}