package darkvault.gui;

public class LoginFrame extends javax.swing.JFrame {

    private final darkvault.service.AuthService authService =
        new darkvault.service.AuthService();

    // ── Shared colors ────────────────────────────────────────────────────────
    private static final java.awt.Color BG        = new java.awt.Color(46, 46, 48);
    private static final java.awt.Color BG_DARK   = new java.awt.Color(36, 36, 38);
    private static final java.awt.Color BTN_GREY  = new java.awt.Color(78, 80, 82);
    private static final java.awt.Color FIELD_BG  = new java.awt.Color(58, 58, 60);
    private static final java.awt.Color BORDER_CLR= new java.awt.Color(90, 90, 92);
    private static final java.awt.Color RED       = new java.awt.Color(201, 29, 52);
    private static final java.awt.Color TEXT_MUTED= new java.awt.Color(140, 140, 140);

    public LoginFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("DarkVault — Login");
        setResizable(false);
        setSize(680, 480);
        getContentPane().setBackground(BG);
        getRootPane().setDefaultButton(loginButton);

        // Apply grey button style (overrides red set in generated block)
        styleButton(loginButton);
        styleButton(registerButton);

        // Style input fields
        styleField(usernameField);
        styleField(passwordField);

        setLocationRelativeTo(null);
    }

    // ── Styling helpers ───────────────────────────────────────────────────────

    private void styleButton(javax.swing.JButton btn) {
        btn.setBackground(BTN_GREY);
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setPreferredSize(new java.awt.Dimension(130, 38));
    }

    private void styleField(javax.swing.JTextField field) {
        field.setBackground(FIELD_BG);
        field.setForeground(java.awt.Color.WHITE);
        field.setCaretColor(java.awt.Color.WHITE);
        field.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 14));
        field.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(BORDER_CLR, 1),
            javax.swing.BorderFactory.createEmptyBorder(4, 10, 4, 10)));
    }

    // ── Generated layout ──────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        Heading       = new javax.swing.JLabel();
        Tagline       = new javax.swing.JLabel();
        separatorLine = new javax.swing.JSeparator();
        Username      = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        Password      = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        loginButton   = new javax.swing.JButton();
        registerButton= new javax.swing.JButton();
        statusLabel   = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Heading — red only here as you requested
        Heading.setFont(new java.awt.Font("Audiowide", java.awt.Font.PLAIN, 42));
        Heading.setForeground(RED);
        Heading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Heading.setText("DarkVault");

        Tagline.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 13));
        Tagline.setForeground(TEXT_MUTED);
        Tagline.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Tagline.setText("Secure Password Manager");

        separatorLine.setForeground(new java.awt.Color(70, 70, 72));

        Username.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 13));
        Username.setForeground(java.awt.Color.WHITE);
        Username.setText("Username");

        usernameField.setColumns(20);

        Password.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 13));
        Password.setForeground(java.awt.Color.WHITE);
        Password.setText("Master Password");

        passwordField.addActionListener(this::passwordFieldActionPerformed);

        // Buttons — background set here, overridden to grey in styleButton()
        loginButton.setText("Login");
        loginButton.addActionListener(this::loginButtonActionPerformed);

        registerButton.setText("Register");
        registerButton.addActionListener(this::registerButtonActionPerformed);

        statusLabel.setForeground(TEXT_MUTED);
        statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusLabel.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 12));

        javax.swing.GroupLayout layout =
            new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Heading,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Tagline,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(120)
                .addComponent(separatorLine,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(120))
            .addGroup(layout.createSequentialGroup()
                .addGap(100)
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Username)
                    .addComponent(Password))
                .addGap(18)
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usernameField,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        360, Short.MAX_VALUE)
                    .addComponent(passwordField))
                .addGap(100))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)
                .addComponent(loginButton,
                    javax.swing.GroupLayout.PREFERRED_SIZE, 130,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12)
                .addComponent(registerButton,
                    javax.swing.GroupLayout.PREFERRED_SIZE, 130,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100))
            .addComponent(statusLabel,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32)
                .addComponent(Heading)
                .addGap(4)
                .addComponent(Tagline)
                .addGap(16)
                .addComponent(separatorLine,
                    javax.swing.GroupLayout.PREFERRED_SIZE, 2,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36)
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(Username)
                    .addComponent(usernameField,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 34,
                        javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14)
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(Password)
                    .addComponent(passwordField,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 34,
                        javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36)
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginButton)
                    .addComponent(registerButton))
                .addGap(16)
                .addComponent(statusLabel)
                .addContainerGap(Short.MAX_VALUE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    // ── Button actions ────────────────────────────────────────────────────────

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            showStatus("Please fill in both fields.", false);
            return;
        }
        darkvault.model.User user = authService.login(username, password);
        if (user != null) {
            new DashboardFrame(user).setVisible(true);
            dispose();
        } else {
            showStatus("Invalid username or password.", false);
            passwordField.setText("");
        }
    }

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (username.isEmpty()) {
            showStatus("Username cannot be empty.", false); return;
        }
        if (password.length() < 6) {
            showStatus("Password must be at least 6 characters.", false); return;
        }
        boolean success = authService.register(username, password);
        if (success) {
            showStatus("Registered! You can now log in.", true);
        } else {
            showStatus("Username already exists.", false);
        }
    }

    private void showStatus(String msg, boolean success) {
        statusLabel.setForeground(success
            ? new java.awt.Color(80, 200, 120)
            : new java.awt.Color(220, 80, 80));
        statusLabel.setText(msg);
    }

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {
        loginButtonActionPerformed(evt); // Enter in password = login
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info :
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException |
                 javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName())
                .log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel Heading;
    private javax.swing.JLabel Password;
    private javax.swing.JLabel Tagline;
    private javax.swing.JLabel Username;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton registerButton;
    private javax.swing.JSeparator separatorLine;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JTextField usernameField;
    // End of variables declaration
}
