package darkvault;

import darkvault.database.DatabaseManager;
import darkvault.gui.LoginFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Test DB connection before showing UI
        if (!DatabaseManager.testConnection()) {
            JOptionPane.showMessageDialog(null,
                "Cannot connect to database.\nCheck MySQL is running and credentials are correct.",
                "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Launch on the Swing event thread (important!)
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}