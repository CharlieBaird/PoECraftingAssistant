package crafting.modplanner;

import crafting.UI.MouseFocusListener;

public class PlannerFrame extends javax.swing.JFrame {

    public PlannerFrame() {
        initComponents();
        
        jPanel1.addMouseListener(new MouseFocusListener(jPanel1));
        jPanel2.addMouseListener(new MouseFocusListener(jPanel2));
        
        ItemBaseComboBox box = new ItemBaseComboBox(jPanel2);
        jPanel2.add(box);
        
        ModViewerPanel viewer = new ModViewerPanel();
        viewer.generate(box.getSelectedItem());
        
        this.requestFocusInWindow();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PoE Crafting Assistant Mod Planner");
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
        setSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new java.awt.GridLayout());

        jPanel1.setBackground(new java.awt.Color(40, 40, 40));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jPanel2.setBackground(new java.awt.Color(50, 50, 50));
        jPanel2.setMinimumSize(new java.awt.Dimension(150, 600));
        jPanel2.setPreferredSize(new java.awt.Dimension(150, 600));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 10));
        jPanel1.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(60, 60, 60));
        jPanel3.setMinimumSize(new java.awt.Dimension(650, 600));
        jPanel3.setPreferredSize(new java.awt.Dimension(650, 600));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3);

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
