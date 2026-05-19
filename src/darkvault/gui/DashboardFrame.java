package darkvault.gui;

public class DashboardFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger(DashboardFrame.class.getName());

    // ── Shared colors ────────────────────────────────────────────────────────
    private static final java.awt.Color BG        = new java.awt.Color(52, 53, 55);
    private static final java.awt.Color BG_DARK   = new java.awt.Color(40, 41, 43);
    private static final java.awt.Color TAB_BAR   = new java.awt.Color(36, 37, 39);
    private static final java.awt.Color TAB_ACTIVE = new java.awt.Color(60, 62, 64);
    private static final java.awt.Color TAB_HOVER  = new java.awt.Color(50, 51, 53);
    private static final java.awt.Color RED        = new java.awt.Color(201, 29, 52);
    private static final java.awt.Color BORDER_CLR = new java.awt.Color(65, 67, 69);

    private darkvault.model.User currentUser;

    public DashboardFrame(darkvault.model.User user) {
        this.currentUser = user;
        initComponents();

        setResizable(true);
        setSize(960, 680);
        setMinimumSize(new java.awt.Dimension(860, 580));
        setTitle("DarkVault");
        getContentPane().setBackground(BG);

        // Welcome label — red as requested
        welcomeLabel.setText("Welcome, " + user.getUsername() + "!");
        welcomeLabel.setForeground(RED);

        // Apply custom tab UI
        mainTabbedPane.setUI(new DarkTabbedPaneUI());
        mainTabbedPane.setBackground(TAB_BAR);
        mainTabbedPane.setForeground(java.awt.Color.WHITE);
        mainTabbedPane.setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 13));
        mainTabbedPane.setOpaque(true);
        mainTabbedPane.setBorder(null);

        // UIManager overrides for areas the custom UI doesn't cover
        javax.swing.UIManager.put("TabbedPane.tabAreaBackground", TAB_BAR);
        javax.swing.UIManager.put("TabbedPane.background",        TAB_BAR);
        javax.swing.UIManager.put("TabbedPane.foreground",        java.awt.Color.WHITE);
        javax.swing.UIManager.put("TabbedPane.selectedForeground",java.awt.Color.WHITE);
        javax.swing.UIManager.put("TabbedPane.contentOpaque",     false);
        javax.swing.UIManager.put("TabbedPane.focus",             TAB_ACTIVE);

        // Add tabs
        mainTabbedPane.addTab("Vault  ",
            new darkvault.gui.VaultPanel(user));
        mainTabbedPane.addTab("Generator  ",
            new darkvault.gui.GeneratorPanel());

        setLocationRelativeTo(null);
    }

    private DashboardFrame() {
        throw new UnsupportedOperationException("Use DashboardFrame(User)");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        mainTabbedPane = new javax.swing.JTabbedPane();
        welcomeLabel   = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // These get overridden in constructor — just neutral defaults
        mainTabbedPane.setBackground(new java.awt.Color(36, 37, 39));
        mainTabbedPane.setForeground(java.awt.Color.WHITE);

        welcomeLabel.setFont(new java.awt.Font("Audiowide", java.awt.Font.PLAIN, 22));
        welcomeLabel.setForeground(new java.awt.Color(201, 29, 52));
        welcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcomeLabel.setText("Welcome!");

        javax.swing.GroupLayout layout =
            new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(welcomeLabel,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainTabbedPane,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16)
                .addComponent(welcomeLabel, 38, 38, 38)
                .addGap(8)
                .addComponent(mainTabbedPane,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>

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
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new DashboardFrame().setVisible(true));
    }

    // Variables declaration - do not modify
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JLabel      welcomeLabel;
    // End of variables declaration

    // ── Custom tab UI ─────────────────────────────────────────────────────────
    private static class DarkTabbedPaneUI
            extends javax.swing.plaf.basic.BasicTabbedPaneUI {

        // Grey for inactive, slightly lighter grey for selected — no red tab bg
        private static final java.awt.Color BAR      = new java.awt.Color(36, 37, 39);
        private static final java.awt.Color INACTIVE = new java.awt.Color(48, 49, 51);
        private static final java.awt.Color ACTIVE   = new java.awt.Color(62, 64, 66);
        private static final java.awt.Color ACCENT   = new java.awt.Color(201, 29, 52);
        private static final java.awt.Color SEP      = new java.awt.Color(70, 72, 74);

        @Override
        protected void installDefaults() {
            super.installDefaults();
            tabAreaInsets        = new java.awt.Insets(0, 0, 0, 0);
            contentBorderInsets  = new java.awt.Insets(0, 0, 0, 0);
            selectedTabPadInsets = new java.awt.Insets(0, 0, 0, 0);
            tabInsets            = new java.awt.Insets(0, 22, 0, 22);
        }

        @Override
        protected void paintTabBackground(java.awt.Graphics g,
                int tabPlacement, int tabIndex,
                int x, int y, int w, int h, boolean isSelected) {
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
            g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            // Selected = slightly lighter grey; inactive = dark grey
            g2.setColor(isSelected ? ACTIVE : INACTIVE);
            g2.fillRect(x, y, w, h);
        }

        @Override
        protected void paintTabBorder(java.awt.Graphics g,
                int tabPlacement, int tabIndex,
                int x, int y, int w, int h, boolean isSelected) {
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
            if (isSelected) {
                // Red accent line at bottom of selected tab — only accent use
                g2.setColor(ACCENT);
                g2.fillRect(x, y + h - 3, w, 3);
            } else {
                // Thin grey separator between tabs
                g2.setColor(SEP);
                g2.fillRect(x + w - 1, y + 10, 1, h - 20);
            }
        }

        @Override
        protected void paintContentBorder(java.awt.Graphics g,
                int tabPlacement, int selectedIndex) {
            // No border around content area
        }

        @Override
        protected void paintFocusIndicator(java.awt.Graphics g,
                int tabPlacement, java.awt.Rectangle[] rects, int tabIndex,
                java.awt.Rectangle iconRect, java.awt.Rectangle textRect,
                boolean isSelected) {
            // No dotted focus ring
        }

        @Override
        protected int calculateTabHeight(int tabPlacement,
                int tabIndex, int fontHeight) {
            return 42;
        }

        @Override
        protected void paintTabArea(java.awt.Graphics g,
                int tabPlacement, int selectedIndex) {
            // Fill entire tab bar with the dark bar color
            java.awt.Rectangle bounds = tabPane.getBounds();
            g.setColor(BAR);
            g.fillRect(0, 0, bounds.width, calculateTabAreaHeight(
                tabPlacement, runCount, maxTabHeight));
            super.paintTabArea(g, tabPlacement, selectedIndex);
        }
    }
}
