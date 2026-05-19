package darkvault.gui;

public class GeneratorPanel extends javax.swing.JPanel {

    // ── Shared colors ────────────────────────────────────────────────────────
    private static final java.awt.Color BG        = new java.awt.Color(52, 53, 55);
    private static final java.awt.Color BG_DARK   = new java.awt.Color(40, 41, 43);
    private static final java.awt.Color CARD_BG   = new java.awt.Color(58, 60, 62);
    private static final java.awt.Color BTN_GREY  = new java.awt.Color(78, 80, 82);
    private static final java.awt.Color FIELD_BG  = new java.awt.Color(62, 64, 66);
    private static final java.awt.Color BORDER_CLR= new java.awt.Color(80, 82, 84);
    private static final java.awt.Color RED       = new java.awt.Color(201, 29, 52);
    private static final java.awt.Color TEXT_MUTED= new java.awt.Color(160, 160, 162);
    private static final java.awt.Color TEXT_WHITE= java.awt.Color.WHITE;

    // ── Components ───────────────────────────────────────────────────────────
    private javax.swing.JTextField  outputField;
    private javax.swing.JSlider     lengthSlider;
    private javax.swing.JLabel      lengthValueLabel;
    private javax.swing.JCheckBox   upperBox, digitBox, symbolBox;
    private javax.swing.JLabel      strengthLabel, feedbackLabel;
    private javax.swing.JProgressBar strengthBar;
    private javax.swing.JTextField  analyzerField;
    private javax.swing.JButton     generateBtn, copyBtn;

    public GeneratorPanel() {
        buildUI();
    }

    // ── Build entire UI in code (no initComponents needed) ───────────────────

    private void buildUI() {
        setLayout(new java.awt.BorderLayout());
        setBackground(BG);

        // ── Left column: generator ───────────────────────────────────────────
        javax.swing.JPanel genCard = makeCard();
        genCard.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints g = new java.awt.GridBagConstraints();
        g.fill = java.awt.GridBagConstraints.HORIZONTAL;
        g.insets = new java.awt.Insets(6, 8, 6, 8);

        // Section title
        javax.swing.JLabel genTitle = sectionTitle("Password Generator");
        g.gridx = 0; g.gridy = 0; g.gridwidth = 3;
        genCard.add(genTitle, g);

        // Output field
        outputField = new javax.swing.JTextField("Click Generate");
        outputField.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 15));
        outputField.setBackground(FIELD_BG);
        outputField.setForeground(TEXT_WHITE);
        outputField.setCaretColor(TEXT_WHITE);
        outputField.setEditable(true);
        outputField.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(BORDER_CLR, 1),
            javax.swing.BorderFactory.createEmptyBorder(6, 12, 6, 12)));
        g.gridy = 1;
        genCard.add(outputField, g);

        // Length row
        javax.swing.JLabel lenLabel = bodyLabel("Length:");
        g.gridy = 2; g.gridwidth = 1;
        genCard.add(lenLabel, g);

        lengthSlider = new javax.swing.JSlider(6, 32, 16);
        lengthSlider.setBackground(CARD_BG);
        lengthSlider.setForeground(TEXT_MUTED);
        lengthSlider.setMajorTickSpacing(4);
        lengthSlider.setPaintTicks(true);
        lengthSlider.setPaintLabels(true);
        javax.swing.UIManager.put("Slider.tickColor", TEXT_MUTED);
        g.gridx = 1; g.gridwidth = 1;
        genCard.add(lengthSlider, g);

        lengthValueLabel = new javax.swing.JLabel("16");
        lengthValueLabel.setForeground(RED);
        lengthValueLabel.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 15));
        lengthValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        g.gridx = 2; g.gridwidth = 1;
        genCard.add(lengthValueLabel, g);

        // Checkboxes
        upperBox  = styledCheckbox("Uppercase  A–Z");
        digitBox  = styledCheckbox("Digits  0–9");
        symbolBox = styledCheckbox("Symbols  !@#$");
        g.gridx = 0; g.gridy = 3; g.gridwidth = 1; genCard.add(upperBox, g);
        g.gridx = 1; genCard.add(digitBox, g);
        g.gridx = 2; genCard.add(symbolBox, g);

        // Buttons
        generateBtn = styledButton("Generate");
        copyBtn     = styledButton("Copy");

        javax.swing.JPanel btnRow = new javax.swing.JPanel(
            new java.awt.GridLayout(1, 2, 10, 0));
        btnRow.setBackground(CARD_BG);
        btnRow.add(generateBtn);
        btnRow.add(copyBtn);

        g.gridx = 0; g.gridy = 4; g.gridwidth = 3;
        genCard.add(btnRow, g);

        // ── Right column: strength analyzer ──────────────────────────────────
        javax.swing.JPanel analyzeCard = makeCard();
        analyzeCard.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints a = new java.awt.GridBagConstraints();
        a.fill = java.awt.GridBagConstraints.HORIZONTAL;
        a.insets = new java.awt.Insets(6, 8, 6, 8);
        a.gridwidth = 1;

        javax.swing.JLabel anaTitle = sectionTitle("Strength Analyzer");
        a.gridx = 0; a.gridy = 0; a.gridwidth = 2;
        analyzeCard.add(anaTitle, a);

        javax.swing.JLabel anaHint = bodyLabel(
            "Type or paste any password to check:");
        anaHint.setForeground(TEXT_MUTED);
        a.gridy = 1;
        analyzeCard.add(anaHint, a);

        analyzerField = new javax.swing.JTextField();
        analyzerField.setBackground(FIELD_BG);
        analyzerField.setForeground(TEXT_WHITE);
        analyzerField.setCaretColor(TEXT_WHITE);
        analyzerField.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
        analyzerField.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(BORDER_CLR, 1),
            javax.swing.BorderFactory.createEmptyBorder(6, 12, 6, 12)));
        a.gridy = 2;
        analyzeCard.add(analyzerField, a);

        // Strength label
        strengthLabel = new javax.swing.JLabel("Strength: —",
            javax.swing.SwingConstants.CENTER);
        strengthLabel.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 16));
        strengthLabel.setForeground(TEXT_MUTED);
        a.gridy = 3;
        analyzeCard.add(strengthLabel, a);

        // Progress bar
        strengthBar = new javax.swing.JProgressBar(0, 100);
        strengthBar.setValue(0);
        strengthBar.setBackground(new java.awt.Color(55, 57, 59));
        strengthBar.setForeground(BTN_GREY);
        strengthBar.setBorderPainted(false);
        strengthBar.setPreferredSize(new java.awt.Dimension(0, 10));
        a.gridy = 4;
        analyzeCard.add(strengthBar, a);

        // Feedback
        feedbackLabel = new javax.swing.JLabel(" ",
            javax.swing.SwingConstants.CENTER);
        feedbackLabel.setForeground(TEXT_MUTED);
        feedbackLabel.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 12));
        a.gridy = 5;
        analyzeCard.add(feedbackLabel, a);

        // ── Spacer filler at bottom of each card ─────────────────────────────
        javax.swing.JPanel genFill = new javax.swing.JPanel();
        genFill.setBackground(CARD_BG);
        g.gridy = 5; g.weighty = 1.0;
        g.fill = java.awt.GridBagConstraints.BOTH;
        genCard.add(genFill, g);

        javax.swing.JPanel anaFill = new javax.swing.JPanel();
        anaFill.setBackground(CARD_BG);
        a.gridy = 6; a.weighty = 1.0;
        a.fill = java.awt.GridBagConstraints.BOTH;
        analyzeCard.add(anaFill, a);

        // ── Assemble two-column layout ────────────────────────────────────────
        javax.swing.JPanel content = new javax.swing.JPanel(
            new java.awt.GridLayout(1, 2, 16, 0));
        content.setBackground(BG);
        content.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 16, 16, 16));
        content.add(genCard);
        content.add(analyzeCard);

        // Wrap in a scroll pane — if window is too narrow, scroll rather than clip
        javax.swing.JScrollPane contentScroll = new javax.swing.JScrollPane(content);
        contentScroll.setBorder(null);
        contentScroll.setBackground(BG);
        contentScroll.getViewport().setBackground(BG);
        contentScroll.setHorizontalScrollBarPolicy(
            javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        contentScroll.setVerticalScrollBarPolicy(
            javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Style scrollbar to match theme
        contentScroll.getHorizontalScrollBar().setUI(
            new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override protected void configureScrollBarColors() {
                    this.thumbColor = new java.awt.Color(78, 80, 82);
                    this.trackColor = new java.awt.Color(40, 41, 43);
                }
                @Override protected javax.swing.JButton createDecreaseButton(int o) { return zeroBtn(); }
                @Override protected javax.swing.JButton createIncreaseButton(int o) { return zeroBtn(); }
                private javax.swing.JButton zeroBtn() {
                    javax.swing.JButton b = new javax.swing.JButton();
                    b.setPreferredSize(new java.awt.Dimension(0,0));
                    return b;
                }
            });

        add(contentScroll, java.awt.BorderLayout.CENTER);

        // ── Wire up listeners ─────────────────────────────────────────────────
        lengthSlider.addChangeListener(e ->
            lengthValueLabel.setText(String.valueOf(lengthSlider.getValue())));

        outputField.getDocument().addDocumentListener(
            new javax.swing.event.DocumentListener() {
                public void insertUpdate(javax.swing.event.DocumentEvent e)  { syncAnalyzer(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e)  { syncAnalyzer(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { syncAnalyzer(); }
            });

        analyzerField.getDocument().addDocumentListener(
            new javax.swing.event.DocumentListener() {
                public void insertUpdate(javax.swing.event.DocumentEvent e)  { updateStrength(analyzerField.getText()); }
                public void removeUpdate(javax.swing.event.DocumentEvent e)  { updateStrength(analyzerField.getText()); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { updateStrength(analyzerField.getText()); }
            });

        generateBtn.addActionListener(e -> {
            String pwd = darkvault.util.PasswordGenerator.generate(
                lengthSlider.getValue(),
                upperBox.isSelected(),
                digitBox.isSelected(),
                symbolBox.isSelected());
            outputField.setText(pwd);
            analyzerField.setText(pwd);
        });

        copyBtn.addActionListener(e -> {
            String pwd = outputField.getText().trim();
            if (pwd.isEmpty() || pwd.equals("Click Generate")) return;
            java.awt.Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(new java.awt.datatransfer.StringSelection(pwd), null);
            copyBtn.setText("Copied!");
            javax.swing.Timer t = new javax.swing.Timer(1500,
                ev -> copyBtn.setText("Copy"));
            t.setRepeats(false);
            t.start();
        });
    }

    // ── Strength logic ────────────────────────────────────────────────────────

    private void syncAnalyzer() {
        // Keep analyzer in sync when output field changes
        String txt = outputField.getText();
        if (!analyzerField.getText().equals(txt)) {
            analyzerField.setText(txt);
        }
    }

    private void updateStrength(String password) {
        if (password == null || password.isEmpty()
                || password.equals("Click Generate")) {
            strengthLabel.setText("Strength: —");
            strengthLabel.setForeground(TEXT_MUTED);
            strengthBar.setValue(0);
            strengthBar.setForeground(BTN_GREY);
            feedbackLabel.setText(" ");
            return;
        }
        darkvault.util.StrengthAnalyzer.Strength s =
            darkvault.util.StrengthAnalyzer.analyze(password);
        String feedback =
            darkvault.util.StrengthAnalyzer.getFeedback(password);

        switch (s) {
            case WEAK:
                strengthLabel.setText("Strength: WEAK");
                strengthLabel.setForeground(new java.awt.Color(220, 80, 80));
                strengthBar.setValue(28);
                strengthBar.setForeground(new java.awt.Color(200, 60, 60));
                break;
            case MEDIUM:
                strengthLabel.setText("Strength: MEDIUM");
                strengthLabel.setForeground(new java.awt.Color(220, 160, 40));
                strengthBar.setValue(62);
                strengthBar.setForeground(new java.awt.Color(200, 150, 30));
                break;
            case STRONG:
                strengthLabel.setText("Strength: STRONG");
                strengthLabel.setForeground(new java.awt.Color(80, 190, 110));
                strengthBar.setValue(100);
                strengthBar.setForeground(new java.awt.Color(60, 170, 90));
                break;
        }
        feedbackLabel.setText(
            "<html><div style='text-align:center'>"
            + feedback.replace("\n", "<br>")
            + "</div></html>");
    }

    // ── UI factory helpers ────────────────────────────────────────────────────

    private javax.swing.JPanel makeCard() {
        javax.swing.JPanel card = new javax.swing.JPanel();
        card.setBackground(CARD_BG);
        card.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(BORDER_CLR, 1),
            javax.swing.BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        return card;
    }

    private javax.swing.JLabel sectionTitle(String text) {
        javax.swing.JLabel lbl = new javax.swing.JLabel(text);
        lbl.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 15));
        lbl.setForeground(TEXT_WHITE);
        lbl.setBorder(javax.swing.BorderFactory.createMatteBorder(
            0, 0, 1, 0, BORDER_CLR));
        return lbl;
    }

    private javax.swing.JLabel bodyLabel(String text) {
        javax.swing.JLabel lbl = new javax.swing.JLabel(text);
        lbl.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 13));
        lbl.setForeground(TEXT_WHITE);
        return lbl;
    }

    private javax.swing.JCheckBox styledCheckbox(String text) {
        javax.swing.JCheckBox cb = new javax.swing.JCheckBox(text, true);
        cb.setBackground(CARD_BG);
        cb.setForeground(TEXT_WHITE);
        cb.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 13));
        cb.setFocusPainted(false);
        return cb;
    }

    private javax.swing.JButton styledButton(String text) {
        javax.swing.JButton btn = new javax.swing.JButton(text);
        btn.setBackground(BTN_GREY);
        btn.setForeground(TEXT_WHITE);
        btn.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setPreferredSize(new java.awt.Dimension(0, 38));
        return btn;
    }
}
