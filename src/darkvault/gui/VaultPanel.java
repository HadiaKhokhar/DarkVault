package darkvault.gui;

public class VaultPanel extends javax.swing.JPanel {

    // ── Shared colors ────────────────────────────────────────────────────────
    private static final java.awt.Color BG        = new java.awt.Color(52, 53, 55);
    private static final java.awt.Color BG_DARK   = new java.awt.Color(40, 41, 43);
    private static final java.awt.Color BTN_GREY  = new java.awt.Color(78, 80, 82);
    private static final java.awt.Color FIELD_BG  = new java.awt.Color(62, 64, 66);
    private static final java.awt.Color BORDER_CLR= new java.awt.Color(80, 82, 84);
    private static final java.awt.Color TBL_EVEN  = new java.awt.Color(58, 60, 62);
    private static final java.awt.Color TBL_ODD   = new java.awt.Color(50, 52, 54);
    private static final java.awt.Color HDR_BG    = new java.awt.Color(36, 37, 39);
    private static final java.awt.Color RED       = new java.awt.Color(201, 29, 52);
    private static final java.awt.Color TEXT_MUTED= new java.awt.Color(140, 140, 142);

    private final darkvault.model.User currentUser;
    private final darkvault.service.VaultService vaultService;
    private javax.swing.table.DefaultTableModel tableModel;

    public VaultPanel(darkvault.model.User user) {
        initComponents();
        this.currentUser = user;
        this.vaultService = new darkvault.service.VaultService();
        this.setBackground(BG);

        // Scrollbar
        tableScrollPane.setVerticalScrollBarPolicy(
            javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tableScrollPane.setHorizontalScrollBarPolicy(
            javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tableScrollPane.getVerticalScrollBar().setUI(
            new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = BTN_GREY;   // grey thumb, not red
                    this.trackColor = BG_DARK;
                }
                @Override
                protected javax.swing.JButton createDecreaseButton(int o) {
                    return zeroButton();
                }
                @Override
                protected javax.swing.JButton createIncreaseButton(int o) {
                    return zeroButton();
                }
                private javax.swing.JButton zeroButton() {
                    javax.swing.JButton b = new javax.swing.JButton();
                    b.setPreferredSize(new java.awt.Dimension(0, 0));
                    b.setMinimumSize(new java.awt.Dimension(0, 0));
                    b.setMaximumSize(new java.awt.Dimension(0, 0));
                    return b;
                }
            });

        tableScrollPane.getVerticalScrollBar()
            .setPreferredSize(new java.awt.Dimension(8, 0));
        tableScrollPane.getViewport().setBackground(TBL_EVEN);
        tableScrollPane.setBackground(BG_DARK);
        // Grey border, not red
        tableScrollPane.setBorder(
            javax.swing.BorderFactory.createLineBorder(BORDER_CLR, 1));

        // Buttons — all grey
        styleButton(searchButton);
        styleButton(clearSearchButton);
        styleButton(addButton);
        styleButton(deleteButton);
        styleButton(showPasswordButton);

        // Search field styling — grey border
        searchField.setBackground(FIELD_BG);
        searchField.setForeground(java.awt.Color.WHITE);
        searchField.setCaretColor(java.awt.Color.WHITE);
        searchField.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 13));
        searchField.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(BORDER_CLR, 1),
            javax.swing.BorderFactory.createEmptyBorder(4, 10, 4, 10)));

        setupTable();
        loadCredentials();
    }

    // ── Table setup ───────────────────────────────────────────────────────────

    private void setupTable() {
        String[] columns = {"ID", "Site Name", "Username", "Password (Encrypted)", "Notes"};
        tableModel = new javax.swing.table.DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        credentialTable.setModel(tableModel);
        credentialTable.setRowHeight(36);
        credentialTable.getTableHeader().setReorderingAllowed(false);

        // Hide ID column
        credentialTable.getColumnModel().getColumn(0).setMaxWidth(0);
        credentialTable.getColumnModel().getColumn(0).setMinWidth(0);
        credentialTable.getColumnModel().getColumn(0).setPreferredWidth(0);

        // Column widths
        credentialTable.getColumnModel().getColumn(1).setPreferredWidth(20); // Site
        credentialTable.getColumnModel().getColumn(2).setPreferredWidth(20); // Username
        credentialTable.getColumnModel().getColumn(3).setPreferredWidth(35); // Password
        credentialTable.getColumnModel().getColumn(4).setPreferredWidth(25); // Notes


        // Table appearance
        credentialTable.setBackground(TBL_EVEN);
        credentialTable.setForeground(java.awt.Color.WHITE);
        credentialTable.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 14));
        credentialTable.setSelectionBackground(RED);   // red for selected row only
        credentialTable.setSelectionForeground(java.awt.Color.WHITE);
        credentialTable.setGridColor(new java.awt.Color(65, 67, 69));
        credentialTable.setShowHorizontalLines(true);
        credentialTable.setShowVerticalLines(false);
        credentialTable.setIntercellSpacing(new java.awt.Dimension(0, 1));
        credentialTable.setFillsViewportHeight(true);
        credentialTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Header renderer — dark grey background, red bottom accent line
        javax.swing.table.DefaultTableCellRenderer headerRenderer =
            new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public java.awt.Component getTableCellRendererComponent(
                        javax.swing.JTable table, Object value,
                        boolean isSelected, boolean hasFocus,
                        int row, int column) {
                    javax.swing.JLabel lbl = (javax.swing.JLabel)
                        super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    lbl.setBackground(HDR_BG);
                    lbl.setForeground(java.awt.Color.WHITE);
                    lbl.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 13));
                    // Red only as the bottom accent line on header
                    lbl.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                        javax.swing.BorderFactory.createMatteBorder(
                            0, 0, 2, 0, RED),
                        javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10)));
                    lbl.setOpaque(true);
                    return lbl;
                }
            };

        for (int i = 0; i < credentialTable.getColumnModel().getColumnCount(); i++) {
            credentialTable.getColumnModel().getColumn(i)
                .setHeaderRenderer(headerRenderer);
        }
        credentialTable.getTableHeader()
            .setPreferredSize(new java.awt.Dimension(0, 40));
        credentialTable.getTableHeader().setOpaque(false);

        // Alternating row renderer
        credentialTable.setDefaultRenderer(Object.class,
            new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public java.awt.Component getTableCellRendererComponent(
                        javax.swing.JTable table, Object value,
                        boolean isSelected, boolean hasFocus,
                        int row, int column) {
                    super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                    if (isSelected) {
                        setBackground(RED);
                        setForeground(java.awt.Color.WHITE);
                    } else if (row % 2 == 0) {
                        setBackground(TBL_EVEN);
                        setForeground(java.awt.Color.WHITE);
                    } else {
                        setBackground(TBL_ODD);
                        setForeground(java.awt.Color.WHITE);
                    }
                    setBorder(javax.swing.BorderFactory
                        .createEmptyBorder(0, 10, 0, 10));
                    return this;
                }
            });
    }

    // ── Data ──────────────────────────────────────────────────────────────────

    private void loadCredentials() {
        tableModel.setRowCount(0);
        java.util.List<darkvault.model.Credential> list =
            vaultService.getCredentials(currentUser.getId());
        for (darkvault.model.Credential c : list) {
            tableModel.addRow(new Object[]{
                c.getId(), c.getSiteName(), c.getUsername(),
                c.getEncryptedPassword(), c.getNotes()
            });
        }
        statusLabel.setText("Total entries: " + list.size());
    }

    // ── Styling helpers ───────────────────────────────────────────────────────

    private void styleButton(javax.swing.JButton btn) {
        btn.setBackground(BTN_GREY);
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setPreferredSize(new java.awt.Dimension(140, 36));
    }

    private javax.swing.JLabel styledLabel(String text) {
        javax.swing.JLabel lbl = new javax.swing.JLabel(text);
        lbl.setForeground(TEXT_MUTED);
        lbl.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 12));
        return lbl;
    }

    // ── Styled dialogs ────────────────────────────────────────────────────────

    private void showStyledWarning(String message) {
        javax.swing.JDialog dialog = makeDialog("Warning", 400, 160);

        javax.swing.JLabel msg = new javax.swing.JLabel(
            "<html><div style='text-align:center'>" + message + "</div></html>",
            javax.swing.SwingConstants.CENTER);
        msg.setForeground(java.awt.Color.WHITE);
        msg.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 13));
        msg.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 20, 6, 20));

        javax.swing.JButton okBtn = new javax.swing.JButton("OK");
        styleButton(okBtn);
        okBtn.addActionListener(e -> dialog.dispose());

        javax.swing.JPanel btnPanel = new javax.swing.JPanel(
            new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 8));
        btnPanel.setBackground(BG);
        btnPanel.add(okBtn);

        dialog.getContentPane().add(msg, java.awt.BorderLayout.CENTER);
        dialog.getContentPane().add(btnPanel, java.awt.BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private boolean showStyledConfirm(String message) {
        javax.swing.JDialog dialog = makeDialog("Confirm", 400, 165);
        final boolean[] result = {false};

        javax.swing.JLabel msg = new javax.swing.JLabel(
            "<html><div style='text-align:center'>" + message + "</div></html>",
            javax.swing.SwingConstants.CENTER);
        msg.setForeground(java.awt.Color.WHITE);
        msg.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 13));
        msg.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 20, 6, 20));

        javax.swing.JButton yesBtn = new javax.swing.JButton("Delete");
        javax.swing.JButton noBtn  = new javax.swing.JButton("Cancel");
        // Delete button: slightly red-tinted grey to signal danger
        yesBtn.setBackground(new java.awt.Color(110, 40, 45));
        yesBtn.setForeground(java.awt.Color.WHITE);
        yesBtn.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 13));
        yesBtn.setFocusPainted(false);
        yesBtn.setBorderPainted(false);
        yesBtn.setContentAreaFilled(false);
        yesBtn.setOpaque(true);
        yesBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        yesBtn.setPreferredSize(new java.awt.Dimension(110, 36));
        styleButton(noBtn);

        yesBtn.addActionListener(e -> { result[0] = true;  dialog.dispose(); });
        noBtn .addActionListener(e -> dialog.dispose());

        javax.swing.JPanel btnPanel = new javax.swing.JPanel(
            new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 12, 8));
        btnPanel.setBackground(BG);
        btnPanel.add(noBtn);
        btnPanel.add(yesBtn);

        dialog.getContentPane().add(msg, java.awt.BorderLayout.CENTER);
        dialog.getContentPane().add(btnPanel, java.awt.BorderLayout.SOUTH);
        dialog.setVisible(true);
        return result[0];
    }

    // Makes a base dark dialog with title bar — used by all styled dialogs
    private javax.swing.JDialog makeDialog(String title, int w, int h) {
        java.awt.Window parent =
            javax.swing.SwingUtilities.getWindowAncestor(this);
        javax.swing.JDialog dialog = new javax.swing.JDialog(
            (java.awt.Frame) parent, title, true);
        dialog.setSize(w, h);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);

        javax.swing.JPanel root = new javax.swing.JPanel(
            new java.awt.BorderLayout());
        root.setBackground(BG);

        javax.swing.JLabel titleLbl = new javax.swing.JLabel("  " + title);
        titleLbl.setOpaque(true);
        titleLbl.setBackground(BG_DARK);
        titleLbl.setForeground(java.awt.Color.WHITE);
        titleLbl.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 14));
        titleLbl.setPreferredSize(new java.awt.Dimension(0, 38));
        // Grey bottom border on title bar, not red
        titleLbl.setBorder(javax.swing.BorderFactory.createMatteBorder(
            0, 0, 1, 0, BORDER_CLR));

        root.add(titleLbl, java.awt.BorderLayout.NORTH);
        dialog.setContentPane(root);
        return dialog;
    }

    // ── Generated layout ──────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        searchField        = new javax.swing.JTextField();
        searchButton       = new javax.swing.JButton();
        clearSearchButton  = new javax.swing.JButton();
        showPasswordButton = new javax.swing.JButton();
        Search             = new javax.swing.JLabel();
        addButton          = new javax.swing.JButton();
        deleteButton       = new javax.swing.JButton();
        statusLabel        = new javax.swing.JLabel();
        tableScrollPane    = new javax.swing.JScrollPane();
        credentialTable    = new javax.swing.JTable();

        searchField.setColumns(22);
        searchField.addActionListener(this::searchFieldActionPerformed);

        searchButton.setText("Search");
        searchButton.addActionListener(this::searchButtonActionPerformed);

        clearSearchButton.setText("Clear");
        clearSearchButton.addActionListener(this::clearSearchButtonActionPerformed);

        showPasswordButton.setText("Show Password");
        showPasswordButton.addActionListener(this::showPasswordButtonActionPerformed);

        Search.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 13));
        Search.setForeground(java.awt.Color.WHITE);
        Search.setText("Search:");

        addButton.setText("+ Add New");
        addButton.addActionListener(this::addButtonActionPerformed);

        deleteButton.setText("Delete");
        deleteButton.addActionListener(this::deleteButtonActionPerformed);

        statusLabel.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 12));
        statusLabel.setForeground(TEXT_MUTED);
        statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusLabel.setText("Loading...");

        credentialTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{}, new String[]{"ID","Site","User","Pass","Notes"}));
        credentialTable.getTableHeader().setReorderingAllowed(false);
        tableScrollPane.setViewportView(credentialTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20)
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Search)
                        .addGap(10)
                           .addComponent(searchField,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 180,
                                Short.MAX_VALUE)
                        .addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(searchButton)
                        .addGap(8)
                        .addComponent(clearSearchButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addGap(10)
                        .addComponent(showPasswordButton)
                        .addGap(10)
                        .addComponent(deleteButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(tableScrollPane,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusLabel,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16)
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(Search)
                    .addComponent(searchField,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton)
                    .addComponent(clearSearchButton))
                .addGap(10)
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(showPasswordButton)
                    .addComponent(deleteButton))
                .addGap(10)
                .addComponent(tableScrollPane,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE, Short.MAX_VALUE)
                .addGap(4)
                .addComponent(statusLabel, 20, 20, 20)
                .addGap(8))
        );
    }// </editor-fold>

    // ── Button actions ────────────────────────────────────────────────────────

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String query = searchField.getText().trim();
        if (query.isEmpty()) { loadCredentials(); return; }
        tableModel.setRowCount(0);
        java.util.List<darkvault.model.Credential> results =
            vaultService.searchCredentials(currentUser.getId(), query);
        for (darkvault.model.Credential c : results) {
            tableModel.addRow(new Object[]{
                c.getId(), c.getSiteName(), c.getUsername(),
                c.getEncryptedPassword(), c.getNotes()
            });
        }
        statusLabel.setText("Found " + results.size()
            + " result(s) for: " + query);
    }

    private void clearSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        searchField.setText("");
        loadCredentials();
    }

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
        java.awt.Window parentWindow =
            javax.swing.SwingUtilities.getWindowAncestor(this);
        javax.swing.JDialog dialog = new javax.swing.JDialog(
            (java.awt.Frame) parentWindow, "Add New Credential", true);
        dialog.setSize(420, 430);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        javax.swing.JPanel mainPanel = new javax.swing.JPanel(
            new java.awt.BorderLayout());
        mainPanel.setBackground(BG);

        // Title bar — grey, not red
        javax.swing.JLabel titleLabel = new javax.swing.JLabel(
            "  Add New Credential", javax.swing.SwingConstants.LEFT);
        titleLabel.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 14));
        titleLabel.setForeground(java.awt.Color.WHITE);
        titleLabel.setBackground(BG_DARK);
        titleLabel.setOpaque(true);
        titleLabel.setPreferredSize(new java.awt.Dimension(0, 44));
        titleLabel.setBorder(javax.swing.BorderFactory.createMatteBorder(
            0, 0, 1, 0, BORDER_CLR));   // grey separator, not red
        mainPanel.add(titleLabel, java.awt.BorderLayout.NORTH);

        javax.swing.JTextField siteField  = new javax.swing.JTextField();
        javax.swing.JTextField userField  = new javax.swing.JTextField();
        javax.swing.JPasswordField passField = new javax.swing.JPasswordField();
        javax.swing.JTextField notesField = new javax.swing.JTextField();

        javax.swing.JPanel formPanel = new javax.swing.JPanel(
            new java.awt.GridLayout(8, 1, 0, 8));
        formPanel.setBackground(BG);
        formPanel.setBorder(
            javax.swing.BorderFactory.createEmptyBorder(18, 22, 10, 22));

        for (Object[] pair : new Object[][]{
                {"Site / App Name:", siteField},
                {"Username / Email:", userField},
                {"Password:", passField},
                {"Notes (optional):", notesField}}) {

            formPanel.add(styledLabel((String) pair[0]));

            javax.swing.JTextField field = (javax.swing.JTextField) pair[1];
            field.setBackground(FIELD_BG);
            field.setForeground(java.awt.Color.WHITE);
            field.setCaretColor(java.awt.Color.WHITE);
            field.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 13));
            // Grey border on form fields
            field.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(BORDER_CLR, 1),
                javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
            formPanel.add(field);
        }
        mainPanel.add(formPanel, java.awt.BorderLayout.CENTER);

        // Buttons — both grey
        javax.swing.JButton okBtn     = new javax.swing.JButton("Save");
        javax.swing.JButton cancelBtn = new javax.swing.JButton("Cancel");
        styleButton(okBtn);
        styleButton(cancelBtn);

        javax.swing.JPanel btnPanel = new javax.swing.JPanel(
            new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 14, 10));
        btnPanel.setBackground(BG_DARK);
        btnPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(
            1, 0, 0, 0, BORDER_CLR));
        btnPanel.add(cancelBtn);
        btnPanel.add(okBtn);
        mainPanel.add(btnPanel, java.awt.BorderLayout.SOUTH);

        dialog.setContentPane(mainPanel);
        dialog.getRootPane().setDefaultButton(okBtn);

        final boolean[] saved = {false};
        okBtn.addActionListener(e -> {
            String site = siteField.getText().trim();
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword());
            if (site.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                showStyledWarning("Site, Username and Password are required.");
                return;
            }
            boolean result = vaultService.addCredential(
                currentUser.getId(), site, user, pass,
                notesField.getText().trim());
            if (result) { saved[0] = true; dialog.dispose(); }
            else showStyledWarning("Error saving to database. Try again.");
        });
        cancelBtn.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
        if (saved[0]) {
            loadCredentials();
            statusLabel.setText("Credential saved successfully.");
        }
    }

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = credentialTable.getSelectedRow();
        if (row == -1) { showStyledWarning("Please select a row to delete."); return; }
        int    id   = (int)    tableModel.getValueAt(row, 0);
        String site = (String) tableModel.getValueAt(row, 1);
        if (showStyledConfirm("Delete credentials for:<br><b>" + site + "</b>?")) {
            vaultService.deleteCredential(id);
            loadCredentials();
            statusLabel.setText("Deleted: " + site);
        }
    }

    private void showPasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int row = credentialTable.getSelectedRow();
        if (row == -1) { showStyledWarning("Please select a row first."); return; }

        String encryptedPass = (String) tableModel.getValueAt(row, 3);
        String site          = (String) tableModel.getValueAt(row, 1);
        String plainPass     = vaultService.decryptPassword(encryptedPass);

        javax.swing.JDialog dialog = makeDialog("Password for: " + site, 400, 175);

        javax.swing.JTextField passDisplay = new javax.swing.JTextField(plainPass);
        passDisplay.setEditable(false);
        passDisplay.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 15));
        passDisplay.setBackground(FIELD_BG);
        passDisplay.setForeground(java.awt.Color.WHITE);
        passDisplay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        passDisplay.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));

        javax.swing.JPanel centerPanel = new javax.swing.JPanel(
            new java.awt.BorderLayout());
        centerPanel.setBackground(BG);
        centerPanel.setBorder(
            javax.swing.BorderFactory.createEmptyBorder(14, 22, 6, 22));
        centerPanel.add(passDisplay, java.awt.BorderLayout.CENTER);
        dialog.getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);

        javax.swing.JButton closeBtn = new javax.swing.JButton("Close");
        styleButton(closeBtn);
        closeBtn.addActionListener(e -> dialog.dispose());

        javax.swing.JPanel btnPanel = new javax.swing.JPanel(
            new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 8));
        btnPanel.setBackground(BG);
        btnPanel.add(closeBtn);
        dialog.getContentPane().add(btnPanel, java.awt.BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {
        searchButtonActionPerformed(evt);
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel Search;
    private javax.swing.JButton addButton;
    private javax.swing.JButton clearSearchButton;
    private javax.swing.JTable credentialTable;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JButton showPasswordButton;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JScrollPane tableScrollPane;
    // End of variables declaration
}
