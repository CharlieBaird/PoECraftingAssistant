package crafting.UI;

import crafting.filters.Subfilter;
import crafting.filters.Filter;
import crafting.PoECraftingAssistant;
import crafting.Settings;
import static crafting.PoECraftingAssistant.establishHotkeyShortcut;
import crafting.Utility;
import crafting.filtertypes.FilterBase;
import crafting.filtertypes.logicgroups.And;
import crafting.itemconfig.ItemConfigPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Main extends javax.swing.JFrame {
    
    Font font = null;   
        
    public Main() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jFileChooser1 = new javax.swing.JFileChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        Window = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        SelectFilterPanel = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        ChangeFilterPanel = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        jScrollPane1.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(20, 20, 20));
        setMinimumSize(new java.awt.Dimension(1174, 807));
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        Window.setBackground(new java.awt.Color(255, 0, 0));
        Window.setForeground(new java.awt.Color(255, 255, 255));
        Window.setMaximumSize(null);
        Window.setMinimumSize(new java.awt.Dimension(1158, 768));
        Window.setPreferredSize(new java.awt.Dimension(1158, 768));

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(null);
        jPanel2.setMinimumSize(new java.awt.Dimension(1158, 768));
        jPanel2.setPreferredSize(new java.awt.Dimension(1158, 768));

        jPanel7.setBackground(new java.awt.Color(20, 20, 20));
        jPanel7.setMaximumSize(null);
        jPanel7.setMinimumSize(new java.awt.Dimension(1158, 500));
        jPanel7.setPreferredSize(new java.awt.Dimension(1158, 500));

        jPanel8.setBackground(new java.awt.Color(20, 20, 20));
        jPanel8.setMaximumSize(null);
        jPanel8.setMinimumSize(new java.awt.Dimension(1158, 571));
        jPanel8.setPreferredSize(new java.awt.Dimension(1158, 571));

        SelectFilterPanel.setBackground(new java.awt.Color(30, 30, 30));
        SelectFilterPanel.setMinimumSize(new java.awt.Dimension(315, 200));
        SelectFilterPanel.setPreferredSize(new java.awt.Dimension(315, 200));

        jPanel9.setBackground(new java.awt.Color(30, 30, 30));
        jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(70, 70, 70)));
        jPanel9.setMinimumSize(new java.awt.Dimension(400, 42));
        jPanel9.setPreferredSize(new java.awt.Dimension(400, 42));
        jPanel9.setLayout(new javax.swing.BoxLayout(jPanel9, javax.swing.BoxLayout.LINE_AXIS));

        jPanel14.setBackground(new java.awt.Color(30, 30, 30));
        jPanel14.setPreferredSize(new java.awt.Dimension(20, 40));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel14);

        jTextField1.setBackground(new java.awt.Color(30, 30, 30));
        jTextField1.setFont(getNewFont(16f));
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setBorder(null);
        jTextField1.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField1.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jTextField1.setMaximumSize(new java.awt.Dimension(300, 300));
        jTextField1.setMinimumSize(new java.awt.Dimension(10, 40));
        jTextField1.setPreferredSize(new java.awt.Dimension(250, 40));
        jTextField1.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        jPanel9.add(jTextField1);

        jButton8.setBackground(new java.awt.Color(40, 40, 40));
        jButton8.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/plusbuttontransparentsmall.png"))); // NOI18N
        jButton8.setToolTipText("Create new subfilter");
        jButton8.setContentAreaFilled(false);
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.setFocusable(false);
        jButton8.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton8.setMaximumSize(new java.awt.Dimension(35, 35));
        jButton8.setMinimumSize(new java.awt.Dimension(35, 35));
        jButton8.setOpaque(true);
        jButton8.setPreferredSize(new java.awt.Dimension(40, 40));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton8);

        jPanel6.setBackground(new java.awt.Color(30, 30, 30));
        jPanel6.setMaximumSize(new java.awt.Dimension(500, 600));
        jPanel6.setMinimumSize(new java.awt.Dimension(500, 600));
        jPanel6.setPreferredSize(new java.awt.Dimension(500, 600));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 4));

        javax.swing.GroupLayout SelectFilterPanelLayout = new javax.swing.GroupLayout(SelectFilterPanel);
        SelectFilterPanel.setLayout(SelectFilterPanelLayout);
        SelectFilterPanelLayout.setHorizontalGroup(
            SelectFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        SelectFilterPanelLayout.setVerticalGroup(
            SelectFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectFilterPanelLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ChangeFilterPanel.setBackground(new java.awt.Color(30, 30, 30));
        ChangeFilterPanel.setMinimumSize(new java.awt.Dimension(723, 638));
        ChangeFilterPanel.setPreferredSize(new java.awt.Dimension(723, 638));

        jButton9.setBackground(new java.awt.Color(40, 40, 40));
        jButton9.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/plusbuttontransparentsmall.png"))); // NOI18N
        jButton9.setToolTipText("Create new subfilter");
        jButton9.setContentAreaFilled(false);
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.setFocusable(false);
        jButton9.setMaximumSize(null);
        jButton9.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton9.setOpaque(true);
        jButton9.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(30, 30, 30));
        jPanel11.setAutoscrolls(true);
        jPanel11.setMaximumSize(new java.awt.Dimension(3000, 3000));
        jPanel11.setMinimumSize(new java.awt.Dimension(723, 560));
        jPanel11.setPreferredSize(new java.awt.Dimension(723, 560));
        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 3));

        javax.swing.GroupLayout ChangeFilterPanelLayout = new javax.swing.GroupLayout(ChangeFilterPanel);
        ChangeFilterPanel.setLayout(ChangeFilterPanelLayout);
        ChangeFilterPanelLayout.setHorizontalGroup(
            ChangeFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChangeFilterPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ChangeFilterPanelLayout.setVerticalGroup(
            ChangeFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChangeFilterPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel15.setBackground(new java.awt.Color(30, 30, 30));
        jPanel15.setMaximumSize(new java.awt.Dimension(200, 0));
        jPanel15.setMinimumSize(new java.awt.Dimension(200, 0));
        jPanel15.setPreferredSize(new java.awt.Dimension(200, 10));
        jPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        jPanel10.setBackground(new java.awt.Color(88, 0, 0));
        jPanel10.setMinimumSize(new java.awt.Dimension(320, 32));
        jPanel10.setPreferredSize(new java.awt.Dimension(320, 32));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel13.setBackground(new java.awt.Color(88, 0, 0));
        jPanel13.setMaximumSize(new java.awt.Dimension(1100, 40));
        jPanel13.setMinimumSize(new java.awt.Dimension(1050, 40));
        jPanel13.setPreferredSize(null);
        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jButton5.setBackground(new java.awt.Color(127, 3, 3));
        jButton5.setFont(getNewFont(12f));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Folder");
        jButton5.setToolTipText("Open saved folder");
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.setFocusable(false);
        jButton5.setMaximumSize(null);
        jButton5.setMinimumSize(new java.awt.Dimension(100, 32));
        jButton5.setPreferredSize(new java.awt.Dimension(100, 32));
        jButton5.setRequestFocusEnabled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton5);

        jButton4.setBackground(new java.awt.Color(127, 3, 3));
        jButton4.setFont(getNewFont(12f));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("New");
        jButton4.setToolTipText("Create new filter");
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setFocusable(false);
        jButton4.setMaximumSize(null);
        jButton4.setMinimumSize(new java.awt.Dimension(100, 32));
        jButton4.setPreferredSize(new java.awt.Dimension(100, 32));
        jButton4.setRequestFocusEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton4);

        jButton1.setBackground(new java.awt.Color(127, 3, 3));
        jButton1.setFont(getNewFont(12f));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Open");
        jButton1.setToolTipText("Open filter");
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusable(false);
        jButton1.setMaximumSize(null);
        jButton1.setMinimumSize(new java.awt.Dimension(100, 32));
        jButton1.setPreferredSize(new java.awt.Dimension(100, 32));
        jButton1.setRequestFocusEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton1);

        jButton7.setBackground(new java.awt.Color(127, 3, 3));
        jButton7.setFont(getNewFont(12f));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Save");
        jButton7.setToolTipText("Save filter");
        jButton7.setContentAreaFilled(false);
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.setFocusable(false);
        jButton7.setMaximumSize(null);
        jButton7.setMinimumSize(new java.awt.Dimension(100, 32));
        jButton7.setPreferredSize(new java.awt.Dimension(100, 32));
        jButton7.setRequestFocusEnabled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton7);

        jPanel10.add(jPanel13, java.awt.BorderLayout.WEST);

        jPanel16.setBackground(new java.awt.Color(88, 0, 0));
        jPanel16.setMaximumSize(new java.awt.Dimension(100, 40));
        jPanel16.setMinimumSize(new java.awt.Dimension(10, 40));
        jPanel16.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel16.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButton11.setBackground(new java.awt.Color(127, 3, 3));
        jButton11.setFont(getNewFont(12f));
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Mod list");
        jButton11.setToolTipText("Open list of mods");
        jButton11.setContentAreaFilled(false);
        jButton11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton11.setFocusable(false);
        jButton11.setMinimumSize(new java.awt.Dimension(100, 32));
        jButton11.setPreferredSize(new java.awt.Dimension(100, 32));
        jButton11.setRequestFocusEnabled(false);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel16.add(jButton11);

        jPanel10.add(jPanel16, java.awt.BorderLayout.EAST);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(SelectFilterPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ChangeFilterPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ChangeFilterPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SelectFilterPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        jPanel5.setBackground(new java.awt.Color(30, 30, 30));
        jPanel5.setMinimumSize(new java.awt.Dimension(1158, 102));
        jPanel5.setPreferredSize(new java.awt.Dimension(1158, 102));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(30, 30, 30));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel12.setBackground(new java.awt.Color(30, 30, 30));
        jPanel12.setMaximumSize(new java.awt.Dimension(3, 10));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel12);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/gear5.png"))); // NOI18N
        jButton3.setToolTipText("Open Settings");
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setFocusPainted(false);
        jButton3.setFocusable(false);
        jButton3.setMaximumSize(new java.awt.Dimension(60, 60));
        jButton3.setMinimumSize(new java.awt.Dimension(60, 60));
        jButton3.setPreferredSize(new java.awt.Dimension(60, 60));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        jPanel3.setBackground(new java.awt.Color(30, 30, 30));
        jPanel3.setMaximumSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3);

        jButton10.setBackground(new java.awt.Color(20, 20, 20));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/testfilter.png"))); // NOI18N
        jButton10.setToolTipText("Test Filter off of Copied Clipboard");
        jButton10.setContentAreaFilled(false);
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.setFocusable(false);
        jButton10.setMaximumSize(new java.awt.Dimension(60, 60));
        jButton10.setMinimumSize(new java.awt.Dimension(60, 60));
        jButton10.setPreferredSize(new java.awt.Dimension(60, 60));
        jButton10.setRequestFocusEnabled(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10runChaosSpam(evt);
            }
        });
        jPanel1.add(jButton10);

        jPanel4.setBackground(new java.awt.Color(30, 30, 30));
        jPanel4.setMaximumSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel4);

        jButton2.setBackground(new java.awt.Color(20, 20, 20));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/chaos.png"))); // NOI18N
        jButton2.setToolTipText("Run Filter");
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setFocusable(false);
        jButton2.setMaximumSize(new java.awt.Dimension(60, 60));
        jButton2.setMinimumSize(new java.awt.Dimension(60, 60));
        jButton2.setPreferredSize(new java.awt.Dimension(60, 60));
        jButton2.setRequestFocusEnabled(false);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runChaosSpam(evt);
            }
        });
        jPanel1.add(jButton2);

        jPanel5.add(jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout WindowLayout = new javax.swing.GroupLayout(Window);
        Window.setLayout(WindowLayout);
        WindowLayout.setHorizontalGroup(
            WindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WindowLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        WindowLayout.setVerticalGroup(
            WindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WindowLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        getContentPane().add(Window);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void runChaosSpam(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runChaosSpam
//        PoECraftingAssistant.runChaosSpam(mainFrame);
    }//GEN-LAST:event_runChaosSpam

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser chooser = new JFileChooser(Utility.getResourcesPath() + "/src/resources/filters");
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String path = file.toPath().toString();
            
            Filter loaded = null;
            loaded = Filter.loadFilters(path);
            
            if (loaded == null) // Errored, wrong serial ID
            {
                JOptionPane.showMessageDialog(Main.mainFrame, "Invalid Filter. Filters from previous PoE Crafting Assistant\nversions cannot be opened.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Filter.saveFilters();
            Filter.reset_openFilter();
            updateLeftTab();

            for (int i=0; i<FilterNamePanel.filterpanels.size(); i++)
            {
                FilterNamePanel.filterpanels.get(i).remove();
            }

            FilterNamePanel.filterpanels.clear();
            
            Filter.singleton = loaded;

            updateLeftTab();
            itemConfigPanel.updateFromFilter();
            
            if (FilterNamePanel.filterpanels.size() >= 1)
            {
                FilterNamePanel.filterpanels.get(0).open();
            }
            
            jButton2.setVisible(true);
            jButton10.setVisible(true);
            jButton7.setVisible(true);
            
            ModifierPanel.updateTierViews();
        }
        
        for (FilterNamePanel fnp : FilterNamePanel.filterpanels)
        {
            if (fnp.active)
            {
                fnp.open();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String name = (String)JOptionPane.showInputDialog(
            this,
            "Enter the New Filter's Name",
            "PoE Crafting Assistant",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            "New Filter");

        if (name != null && !name.equals(""))
        {
            Filter.saveFilters();

            Filter.reset_newFilter();

            for (int i=0; i<FilterNamePanel.filterpanels.size(); i++)
            {
                FilterNamePanel.filterpanels.get(i).remove();
            }

            FilterNamePanel.filterpanels.clear();

            jTextField1.setText(name);

            Filter.singleton.setName(name);
            Filter.saveFilters();

            jTextField1.setVisible(true);
            jButton8.setVisible(true);
            jButton2.setVisible(true);
            jButton10.setVisible(true);
            jButton7.setVisible(true);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String path = Utility.getResourcesPath() + "/src/resources/filters";
        File file = new File(path);
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        jPanel6.requestFocusInWindow();
        
        for (FilterNamePanel fnp : FilterNamePanel.filterpanels)
        {
            if (fnp.active)
            {
                Subfilter f = fnp.filter;
                f.filters.add(new And());
                genFilterPanel(f);
            }
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        
        if (Filter.singleton.filters.size() <= 20)
        {
            Subfilter filter = new Subfilter();
            Filter.singleton.filters.add(filter);
            genPanel(filter);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        requestFocusInWindow();
        Settings.singleton.OpenSettings();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        
        if (Filter.singleton != null)
        {
            Filter.saveFilters();
        }
        
        jButton7.setText("Saved!");
        
        new Thread(new Runnable() {
            @Override
            public void run()  {
                try  { Thread.sleep( 3000 ); }
                catch (InterruptedException ie)  {}
                jButton7.setText("Save");
            }
        }).start();
        
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10runChaosSpam(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10runChaosSpam
        requestFocusInWindow();
        PoECraftingAssistant.testFilter(this, jButton10);
    }//GEN-LAST:event_jButton10runChaosSpam

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        requestFocusInWindow();
        PoECraftingAssistant.runChaosSpam(mainFrame);
    }//GEN-LAST:event_jButton2MousePressed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    public void updateLeftTab()
    {
        jTextField1.setText(Filter.getName());
        
        jTextField1.setVisible(true);
        jButton8.setVisible(true);
        
        for (Subfilter f : Filter.singleton.filters)
        {
            genPanel(f);
        }
    }
    
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChangeFilterPanel;
    private javax.swing.JPanel SelectFilterPanel;
    private javax.swing.JPanel Window;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    
    private void changeComponentColor(JComponent comp, Color c)
    {
        comp.setBackground(c);
    }

    public Font getNewFont(float size)
    {        
        try {
            InputStream is = getClass().getResourceAsStream("/resources/Volkhov-Regular.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            is.close();
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        genv.registerFont(font);
        font = font.deriveFont(size);
        
        return this.font;
    }
    
    private void genPanel(Subfilter filter)
    {
        new FilterNamePanel(this, jPanel6, filter);

        Filter.saveFilters();
        
        jTextField1.setVisible(true);
        jButton8.setVisible(true);
        
        this.validate();
    }
    
    public void preload()
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        
        jTextField1.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10)
                {
                    jPanel9.requestFocusInWindow();
                }
            }
        });
        
        jTextField1.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                File old = new File(Utility.getResourcesPath() + "/src/resources/filters/" + Filter.getName() + ".cbfilter");
                old.delete();

                Filter.singleton.setName(jTextField1.getText());
                Filter.saveFilters();
            }
        });
        
        jPanel10.add(Box.createHorizontalGlue());
        
        JPanel padding = new JPanel();
        padding.setMaximumSize(new Dimension(5,10));
        padding.setOpaque(false);
        jPanel15.add(padding);
        itemConfigPanel = new ItemConfigPanel();
        jPanel15.add(itemConfigPanel);
    }
    
    public ItemConfigPanel itemConfigPanel;
    
    public void postload()
    {
        
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel() {
                @Override
                public void provideErrorFeedback(Component component) {}
            });
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        jTextField1.setVisible(false);
        jButton8.setVisible(false);
        jButton9.setVisible(false);
        jButton2.setVisible(false);
        jButton10.setVisible(false);
        jButton7.setVisible(false);
        
        jPanel6.addMouseListener(new MouseFocusListener(jPanel6));
        jPanel11.addMouseListener(new MouseFocusListener(jPanel11));
        ChangeFilterPanel.addMouseListener(new MouseFocusListener(ChangeFilterPanel));
        jPanel5.addMouseListener(new MouseFocusListener(jPanel5));
        jPanel10.addMouseListener(new MouseFocusListener(jPanel10));
        Window.addMouseListener(new MouseFocusListener(Window));
        jPanel2.addMouseListener(new MouseFocusListener(jPanel2));
        jPanel7.addMouseListener(new MouseFocusListener(jPanel7));
        SelectFilterPanel.addMouseListener(new MouseFocusListener(SelectFilterPanel));
        jPanel9.addMouseListener(new MouseFocusListener(jPanel9));
        jPanel14.addMouseListener(new MouseFocusListener(jPanel14));
        
        establishHotkeyShortcut();
        
        addComponentListener(new ResizeListener());
        
        requestFocusInWindow();
        
        this.addWindowFocusListener(new WindowFocusListener() {
        @Override
        public void windowGainedFocus(WindowEvent e) {
//            System.out.println("Window gained focus");
//            if (e.getOppositeWindow() == null) {
//                if (Settings.singleton.disableOnFocus)
//                    PoECraftingAssistant.stop();
//            }
        }

        @Override
        public void windowLostFocus(WindowEvent e) {
//            if (e.getOppositeWindow() != null) {
//                PoECraftingAssistant.stop();
//            }
        }
    });
    }
    
    public Main(String title)
    {
        super(title);
        
        initComponents();
    }
    
    public static Main mainFrame = null;
    
    public static void main() {
        java.awt.EventQueue.invokeLater(() -> {
            mainFrame = new Main("PoE Crafting Assistant");
            mainFrame.setLocationRelativeTo(null);
            mainFrame.preload();
            mainFrame.setVisible(true);
            mainFrame.postload();
//            mainFrame.repaint();
        });
    }
    
    public void hideAddButton()
    {
        jButton9.setVisible(false);
    }
    
    
    
    public void genFilterPanel(Subfilter filter)
    {   
        int index = 0;
        
        if (filter.filters.isEmpty())
        {
            filter.filters.add(new And());
        }
        
        FilterTypePanel.clear(true);
        
        for (FilterBase fb : filter.filters)
        {
            new FilterTypePanel(this, jPanel11, fb, filter, index);
            index++;
        }
        
        if (FilterTypePanel.filtertypepanels.size() >= 1)
            jButton9.setVisible(true);
        
//        jPanel11.pack();
        jPanel11.repaint();
        jPanel11.validate();
    }
    
    public static void setChaosIcon(URL path)
    {
        mainFrame.jButton2.setIcon(new javax.swing.ImageIcon(path)); // NOI18N
    }
}
