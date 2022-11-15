/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.tools;

import gui.GUIWindow;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;
import tools.SearchGenerator;

/**
 *
 * @author Administrator
 */
public class 代码查询工具 extends javax.swing.JFrame {

    /**
     * Creates new form 锻造控制台
     */
    public 代码查询工具() {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("image/Icon.png"));
        setIconImage(icon.getImage());
        setTitle("【代码查询工具，可关闭】");
        initComponents();
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(75);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(275);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
    }

    public void init() {
        jTextFieldItemIDSearch.setText("");
        jComboBoxItemIDSearch.setSelectedIndex(0);
        modelClear();
        setSize(400, 300);
    }

    public void modelClear() {
        DefaultTableModel model = ((DefaultTableModel) jTable1.getModel());
        int count = model.getRowCount();
        for (int i = 0; i < count; i++) {
            model.removeRow(0);
        }
    }

    public boolean addModel(int type, Map<Integer, String> data, boolean show) {
        return addModel(SearchGenerator.SearchType.valueOf(SearchGenerator.SearchType.nameOf(type)), data, show);
    }

    public boolean addModel(SearchGenerator.SearchType type, Map<Integer, String> data, boolean show) {
        if (data == null || data.isEmpty()) {
            if (show) {
                JOptionPane.showMessageDialog(null, "未找到。");
            }
            return false;
        }
        DefaultTableModel model = ((DefaultTableModel) jTable1.getModel());
        for (int i : data.keySet()) {
            model.addRow(new Object[]{
                type.name(),
                data.get(i),
                i
            });
        }
        return true;
    }

    public void search() {
        modelClear();
        String str = jTextFieldItemIDSearch.getText();
        int type = jComboBoxItemIDSearch.getSelectedIndex();
        if (type == 0) {
            boolean find = false;
            for (int i = 1; i <= SearchGenerator.职业; i++) {
                boolean show = addModel(i, SearchGenerator.getSearchData(i, str), i == SearchGenerator.职业 ? !find : false);
                if (!find) {
                    find = show;
                }
            }
        } else {
            addModel(type, SearchGenerator.getSearchData(type, str), true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxItemIDSearch = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldItemIDSearch = new javax.swing.JTextField();
        jButtonItemIDSearch = new javax.swing.JButton();
        jScrollPane1ItemID = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("搜索类型:");
        jPanel4.add(jLabel1);

        jComboBoxItemIDSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "全部", "道具", "NPC", "地图", "怪物", "任务", "技能", "职业", "服务端包头", "客户端包头", "发型", "脸型", "SN" }));
        jComboBoxItemIDSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxItemIDSearchActionPerformed(evt);
            }
        });
        jPanel4.add(jComboBoxItemIDSearch);

        jLabel2.setText("搜索关键字:");
        jPanel4.add(jLabel2);

        jTextFieldItemIDSearch.setColumns(10);
        jPanel4.add(jTextFieldItemIDSearch);

        jButtonItemIDSearch.setText("搜索");
        jButtonItemIDSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonItemIDSearchActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonItemIDSearch);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "类型", "名称或ID", "值"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1ItemID.setViewportView(jTable1);

        jPanel4.add(jScrollPane1ItemID);

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 480));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxItemIDSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxItemIDSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxItemIDSearchActionPerformed

    private void jButtonItemIDSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonItemIDSearchActionPerformed
        search();
    }//GEN-LAST:event_jButtonItemIDSearchActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(代码查询工具.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        代码查询工具.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
            // UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new 代码查询工具().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonItemIDSearch;
    private javax.swing.JComboBox jComboBoxItemIDSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1ItemID;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldItemIDSearch;
    // End of variables declaration//GEN-END:variables
}
