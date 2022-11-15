/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.tools;

import database.DatabaseConnection;
import gui.FengYeDuan;
import handling.world.MapleParty;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;
import server.MapleItemInformationProvider;

/**
 *
 * @author Administrator
 */
public class 活动控制台 extends javax.swing.JFrame {

    public 活动控制台() {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("image/Icon2.png"));
        setIconImage(icon.getImage());
        setTitle("游戏活动管理控制台 可关闭 ");
        initComponents();
        刷新野外BOSS刷新时间();
        刷新魔族突袭开关();
        刷新魔族攻城开关();
        刷新神秘商人开关();
        刷新野外通缉开关();
        刷新幸运职业开关();
        刷新幸运职业();
        刷新神秘商人时间();
        刷新周末倍率开关();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanel20 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        魔族突袭开关 = new javax.swing.JButton();
        jLabel270 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        魔族攻城开关 = new javax.swing.JButton();
        jLabel264 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        野外通缉开关 = new javax.swing.JButton();
        jLabel275 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        幸运职业开关 = new javax.swing.JButton();
        jLabel269 = new javax.swing.JLabel();
        jLabel267 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        神秘商人开关 = new javax.swing.JButton();
        jLabel274 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        周末倍率开关 = new javax.swing.JButton();
        jLabel272 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        每日送货开关3 = new javax.swing.JButton();
        jLabel273 = new javax.swing.JLabel();
        钓鱼管理 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        钓鱼物品 = new javax.swing.JTable();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jPanel91 = new javax.swing.JPanel();
        修改钓鱼物品 = new javax.swing.JButton();
        刷新钓鱼物品 = new javax.swing.JButton();
        钓鱼物品代码 = new javax.swing.JTextField();
        新增钓鱼物品 = new javax.swing.JButton();
        钓鱼物品概率 = new javax.swing.JTextField();
        钓鱼物品名称 = new javax.swing.JTextField();
        删除钓鱼物品 = new javax.swing.JButton();
        钓鱼物品序号 = new javax.swing.JTextField();
        jLabel379 = new javax.swing.JLabel();
        jLabel380 = new javax.swing.JLabel();
        jLabel381 = new javax.swing.JLabel();
        jLabel382 = new javax.swing.JLabel();
        ZEVMS2提示框1 = new javax.swing.JLabel();
        BOSS相关 = new javax.swing.JPanel();
        jScrollPane104 = new javax.swing.JScrollPane();
        野外BOSS刷新时间 = new javax.swing.JTable();
        刷新野外BOSS刷新时间 = new javax.swing.JButton();
        野外BOSS序号 = new javax.swing.JTextField();
        野外BOSS刷新时间值 = new javax.swing.JTextField();
        野外BOSS = new javax.swing.JTextField();
        刷新野外BOSS刷新时间修改 = new javax.swing.JButton();
        jLabel323 = new javax.swing.JLabel();
        jLabel324 = new javax.swing.JLabel();
        jLabel325 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        神秘商人出现时间 = new javax.swing.JTextField();
        jLabel343 = new javax.swing.JLabel();
        修改神秘商人 = new javax.swing.JButton();
        幸运职业代码 = new javax.swing.JTextField();
        jLabel344 = new javax.swing.JLabel();
        幸运职业修改 = new javax.swing.JButton();

        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "魔族突袭", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        魔族突袭开关.setText("开关");
        魔族突袭开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                魔族突袭开关ActionPerformed(evt);
            }
        });
        jPanel9.add(魔族突袭开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 30, 80, 30));

        jLabel270.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel270.setForeground(new java.awt.Color(51, 102, 255));
        jLabel270.setText("开启后，每日22:00 - 22:10，蝙蝠魔会偷袭在野外的冒险家，高于30级，落单弱小的玩家偷袭概率最高");
        jPanel9.add(jLabel270, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 840, 20));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "魔族攻城", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        魔族攻城开关.setText("开关");
        魔族攻城开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                魔族攻城开关ActionPerformed(evt);
            }
        });
        jPanel4.add(魔族攻城开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 30, 80, 30));

        jLabel264.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel264.setForeground(new java.awt.Color(51, 102, 255));
        jLabel264.setText("开启后，周末晚上 21:10 之后魔族会进行攻城，从林中之城开始攻向明珠港，射手村，废弃都市，魔法密林。");
        jPanel4.add(jLabel264, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 870, 30));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "野外通缉", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        野外通缉开关.setText("开关");
        野外通缉开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                野外通缉开关ActionPerformed(evt);
            }
        });
        jPanel15.add(野外通缉开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 30, 80, 30));

        jLabel275.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel275.setForeground(new java.awt.Color(51, 102, 255));
        jLabel275.setText("开启后，服务端会在启动后 1 小时候发布通缉令，通缉目标被击杀后会 1 小时再次发");
        jPanel15.add(jLabel275, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 820, 30));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "幸运职业", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        幸运职业开关.setText("开关");
        幸运职业开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                幸运职业开关ActionPerformed(evt);
            }
        });
        jPanel8.add(幸运职业开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 30, 80, 30));

        jLabel269.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel269.setForeground(new java.awt.Color(51, 102, 255));
        jLabel269.setText("开启后，给予指定的职业增加50%的额外狩猎经验，每日 11:00 23:00 会随机重置指定的职业，二转后生效。");
        jPanel8.add(jLabel269, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 880, 30));

        jLabel267.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel267.setForeground(new java.awt.Color(255, 0, 51));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "神秘商人", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        神秘商人开关.setText("开关");
        神秘商人开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                神秘商人开关ActionPerformed(evt);
            }
        });
        jPanel14.add(神秘商人开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 30, 80, 30));

        jLabel274.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel274.setForeground(new java.awt.Color(51, 102, 255));
        jLabel274.setText("开启后，服务端会开始召唤神秘商人，商人每次只会待 5 分钟，消失并告知下一次出现的信息（9900001.js）");
        jPanel14.add(jLabel274, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 870, 30));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "周末随机双倍活动", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        周末倍率开关.setText("开关");
        周末倍率开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                周末倍率开关ActionPerformed(evt);
            }
        });
        jPanel11.add(周末倍率开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 30, 80, 30));

        jLabel272.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel272.setForeground(new java.awt.Color(51, 102, 255));
        jLabel272.setText("开启后，周六，周日凌晨会随机开启24小时2倍经验，爆率，或者经验爆率活动。");
        jPanel11.add(jLabel272, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 820, 30));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "每日送货", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        每日送货开关3.setText("开关");
        每日送货开关3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                每日送货开关3ActionPerformed(evt);
            }
        });
        jPanel12.add(每日送货开关3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 30, 130, -1));

        jLabel273.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel273.setForeground(new java.awt.Color(255, 0, 51));
        jLabel273.setText("开启后，每日12:00之后开始送货，从明珠港开始，到废弃都市结束。");
        jPanel12.add(jLabel273, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 820, 30));

        jPanel11.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 1190, 80));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 981, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel267, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel267, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 138, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("活动控制台", new javax.swing.ImageIcon(getClass().getClassLoader().getResource("image/1.png")), jPanel20); // NOI18N

        钓鱼管理.setBackground(new java.awt.Color(255, 255, 255));
        钓鱼管理.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "钓鱼管理", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        钓鱼管理.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        钓鱼物品.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        钓鱼物品.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "代码", "概率", "物品名称"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        钓鱼物品.getTableHeader().setReorderingAllowed(false);
        jScrollPane10.setViewportView(钓鱼物品);

        钓鱼管理.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 770, 380));
        钓鱼管理.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, -1));
        钓鱼管理.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, -1));

        jPanel91.setBackground(new java.awt.Color(255, 255, 255));
        jPanel91.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "钓鱼编辑", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel91.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        修改钓鱼物品.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        修改钓鱼物品.setText("修改");
        修改钓鱼物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改钓鱼物品ActionPerformed(evt);
            }
        });
        jPanel91.add(修改钓鱼物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, -1, 30));

        刷新钓鱼物品.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        刷新钓鱼物品.setText("刷新钓鱼物品");
        刷新钓鱼物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新钓鱼物品ActionPerformed(evt);
            }
        });
        jPanel91.add(刷新钓鱼物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, 30));

        钓鱼物品代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        钓鱼物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                钓鱼物品代码ActionPerformed(evt);
            }
        });
        jPanel91.add(钓鱼物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 110, 30));

        新增钓鱼物品.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        新增钓鱼物品.setText("新增");
        新增钓鱼物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                新增钓鱼物品ActionPerformed(evt);
            }
        });
        jPanel91.add(新增钓鱼物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, -1, 30));

        钓鱼物品概率.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel91.add(钓鱼物品概率, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 100, 30));

        钓鱼物品名称.setEditable(false);
        钓鱼物品名称.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel91.add(钓鱼物品名称, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, 150, 30));

        删除钓鱼物品.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除钓鱼物品.setText("删除");
        删除钓鱼物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除钓鱼物品ActionPerformed(evt);
            }
        });
        jPanel91.add(删除钓鱼物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, -1, 30));

        钓鱼物品序号.setEditable(false);
        钓鱼物品序号.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        钓鱼物品序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                钓鱼物品序号ActionPerformed(evt);
            }
        });
        jPanel91.add(钓鱼物品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 80, 30));

        jLabel379.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel379.setText("物品名字；");
        jPanel91.add(jLabel379, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, -1));

        jLabel380.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel380.setText("序列号；");
        jPanel91.add(jLabel380, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel381.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel381.setText("物品代码；");
        jPanel91.add(jLabel381, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, -1, -1));

        jLabel382.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel382.setText("垂钓概率；");
        jPanel91.add(jLabel382, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, -1));

        钓鱼管理.add(jPanel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, 480, 130));

        ZEVMS2提示框1.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        ZEVMS2提示框1.setText("[信息]：");
        钓鱼管理.add(ZEVMS2提示框1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 725, 1260, 30));

        jTabbedPane6.addTab("渔场钓鱼管理", new javax.swing.ImageIcon(getClass().getResource("/provider/WzXML/1.png")), 钓鱼管理); // NOI18N

        BOSS相关.setBackground(new java.awt.Color(255, 255, 255));
        BOSS相关.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BOSS刷新时间", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        BOSS相关.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        野外BOSS刷新时间.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "野外BOSS", "刷新时间/分"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        野外BOSS刷新时间.getTableHeader().setReorderingAllowed(false);
        jScrollPane104.setViewportView(野外BOSS刷新时间);

        BOSS相关.add(jScrollPane104, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 460, 610));

        刷新野外BOSS刷新时间.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        刷新野外BOSS刷新时间.setText("刷新");
        刷新野外BOSS刷新时间.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新野外BOSS刷新时间ActionPerformed(evt);
            }
        });
        BOSS相关.add(刷新野外BOSS刷新时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 180, 90, 30));

        野外BOSS序号.setEditable(false);
        BOSS相关.add(野外BOSS序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, 100, 30));
        BOSS相关.add(野外BOSS刷新时间值, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 100, 110, 30));

        野外BOSS.setEditable(false);
        BOSS相关.add(野外BOSS, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 100, 210, 30));

        刷新野外BOSS刷新时间修改.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        刷新野外BOSS刷新时间修改.setText("修改");
        刷新野外BOSS刷新时间修改.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新野外BOSS刷新时间修改ActionPerformed(evt);
            }
        });
        BOSS相关.add(刷新野外BOSS刷新时间修改, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 180, 90, 30));

        jLabel323.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel323.setText("刷新时间；");
        BOSS相关.add(jLabel323, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 80, -1, -1));

        jLabel324.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel324.setText("序号；");
        BOSS相关.add(jLabel324, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 80, -1, -1));

        jLabel325.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel325.setText("BOSS；");
        BOSS相关.add(jLabel325, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 80, -1, -1));

        jTabbedPane6.addTab("野外BOSS刷新时间", new javax.swing.ImageIcon(getClass().getResource("/gui/1.png")), BOSS相关); // NOI18N

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextPane1.setEditable(false);
        jTextPane1.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jTextPane1.setForeground(new java.awt.Color(51, 0, 255));
        jTextPane1.setText("[每日送货] 每日 12:00 - 23:59\n[魔族偷袭] 每日 22:00 - 22:10\n[魔族攻城] 周日 21:00 - 21:30\n[每日答题] 周一至周五 20:10 - 20:20 周末 20:10 - 20:59\n[神秘商人] 完全随机出现，无规律\n[野外通缉] 系统发布一个后，玩家完成后 1 小时刷新\n[幸运职业] 11:00 23:00 随机抽取职业群，增加 50% 狩猎经验\n[周末狂欢] 周六，周日凌晨随机开启2倍经验，2倍爆率，2倍经验和爆率\n[喜从天降] 周日，22:30 会在 2 频道市场狂欢发放物品\n[鱼来鱼往] 周一至周五 21:30 - 21:40 周末 21:30 - 21:59 在水下世界举行\n");
        jTextPane1.setToolTipText("");
        jScrollPane1.setViewportView(jTextPane1);

        jPanel18.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 620, 540));
        jPanel18.add(神秘商人出现时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 50, 110, 30));

        jLabel343.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel343.setText("神秘商人；");
        jPanel18.add(jLabel343, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 30, -1, 20));

        修改神秘商人.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        修改神秘商人.setText("修改");
        修改神秘商人.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改神秘商人ActionPerformed(evt);
            }
        });
        jPanel18.add(修改神秘商人, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 50, 70, 30));
        jPanel18.add(幸运职业代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 210, 110, 30));

        jLabel344.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel344.setText("幸运职业；");
        jPanel18.add(jLabel344, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 190, -1, 20));

        幸运职业修改.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        幸运职业修改.setText("修改");
        幸运职业修改.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                幸运职业修改ActionPerformed(evt);
            }
        });
        jPanel18.add(幸运职业修改, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 210, 70, 30));

        jTabbedPane6.addTab("预览", jPanel18);

        getContentPane().add(jTabbedPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 690));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void 修改钓鱼物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改钓鱼物品ActionPerformed
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.钓鱼物品序号.getText().matches("[0-9]+");
        if (result1) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE 钓鱼物品 SET itemid = ?,chance = ?WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM 钓鱼物品 WHERE id = ?");
                ps1.setInt(1, Integer.parseInt(this.钓鱼物品序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString1 = null;
                    String sqlString2 = null;
                    sqlString1 = "update 钓鱼物品 set itemid='" + this.钓鱼物品代码.getText() + "' where id=" + this.钓鱼物品序号.getText() + ";";
                    PreparedStatement name = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                    name.executeUpdate(sqlString1);
                    sqlString2 = "update 钓鱼物品 set chance='" + this.钓鱼物品概率.getText() + "' where id=" + this.钓鱼物品序号.getText() + ";";
                    PreparedStatement level = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    level.executeUpdate(sqlString2);
                    //角色提示语言.setText("[信息]:修改钓鱼物品成功。");
                    JOptionPane.showMessageDialog(null, "修改钓鱼物品成功。");
                    刷新钓鱼();
                }
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null,"[信息]:输入<物品代码><概率>。");
        }
    }//GEN-LAST:event_修改钓鱼物品ActionPerformed

    private void 刷新钓鱼物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新钓鱼物品ActionPerformed
        JOptionPane.showMessageDialog(null,"[信息]:刷新钓鱼奖励成功。");
        刷新钓鱼();
    }//GEN-LAST:event_刷新钓鱼物品ActionPerformed

    private void 钓鱼物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_钓鱼物品代码ActionPerformed

    }//GEN-LAST:event_钓鱼物品代码ActionPerformed

    private void 新增钓鱼物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_新增钓鱼物品ActionPerformed
        boolean result1 = this.钓鱼物品代码.getText().matches("[0-9]+");
        boolean result2 = this.钓鱼物品概率.getText().matches("[0-9]+");

        if (result1 && result2) {
            if (Integer.parseInt(this.钓鱼物品代码.getText()) < 0 && Integer.parseInt(this.钓鱼物品概率.getText()) < 0) {
                JOptionPane.showMessageDialog(null,"[信息]:请填写正确的值。");
                return;
            }
            try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO 钓鱼物品 (itemid, chance ,expiration) VALUES (?, ?, ?)")) {
                ps.setInt(1, Integer.parseInt(this.钓鱼物品代码.getText()));
                ps.setInt(2, Integer.parseInt(this.钓鱼物品概率.getText()));
                ps.setInt(3, 1);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null,"[信息]:新增钓鱼奖励成功。");
                刷新钓鱼();
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null,"[信息]:请输入<物品代码><概率>。");
        }
    }//GEN-LAST:event_新增钓鱼物品ActionPerformed

    private void 删除钓鱼物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除钓鱼物品ActionPerformed
        String 输出 = "";
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.钓鱼物品序号.getText().matches("[0-9]+");
        if (result1) {
            try {
                //清楚table数据
                for (int i = ((DefaultTableModel) (this.钓鱼物品.getModel())).getRowCount() - 1; i >= 0; i--) {
                    ((DefaultTableModel) (this.钓鱼物品.getModel())).removeRow(i);
                }
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM 钓鱼物品 WHERE id = ?");
                ps1.setInt(1, Integer.parseInt(this.钓鱼物品序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from 钓鱼物品 where id =" + Integer.parseInt(this.钓鱼物品序号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    JOptionPane.showMessageDialog(null,"[信息]:删除钓鱼奖励物品成功。");
                    刷新钓鱼();
                }
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null,"[信息]:请选择你要删除的钓鱼物品。");
        }
    }//GEN-LAST:event_删除钓鱼物品ActionPerformed

    private void 钓鱼物品序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_钓鱼物品序号ActionPerformed

    }//GEN-LAST:event_钓鱼物品序号ActionPerformed

    private void 刷新野外BOSS刷新时间ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新野外BOSS刷新时间ActionPerformed
        刷新野外BOSS刷新时间();
    }//GEN-LAST:event_刷新野外BOSS刷新时间ActionPerformed

    private void 刷新野外BOSS刷新时间修改ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新野外BOSS刷新时间修改ActionPerformed
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.野外BOSS刷新时间值.getText().matches("[0-9]+");

        if (result1) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");

                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM configvalues WHERE id = ?");

                ps1.setInt(1, Integer.parseInt(this.野外BOSS序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString1 = null;
                    sqlString1 = "update configvalues set Val = '" + this.野外BOSS刷新时间值.getText() + "' where id= " + this.野外BOSS序号.getText() + ";";
                    PreparedStatement Val = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                    Val.executeUpdate(sqlString1);
                    刷新野外BOSS刷新时间();
                    server.Start.GetConfigValues();
                    JOptionPane.showMessageDialog(null, "修改成功，已经生效");
                }
            } catch (SQLException ex) {
                Logger.getLogger(活动控制台.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要修改的刷新时间");
        }
    }//GEN-LAST:event_刷新野外BOSS刷新时间修改ActionPerformed

    private void 魔族突袭开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_魔族突袭开关ActionPerformed
        按键开关("魔族突袭开关", 2400);
        刷新魔族突袭开关();
    }//GEN-LAST:event_魔族突袭开关ActionPerformed

    private void 魔族攻城开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_魔族攻城开关ActionPerformed
        按键开关("魔族攻城开关", 2404);
        刷新魔族攻城开关();
    }//GEN-LAST:event_魔族攻城开关ActionPerformed

    private void 野外通缉开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_野外通缉开关ActionPerformed
        按键开关("野外通缉开关", 2407);
        刷新野外通缉开关();
    }//GEN-LAST:event_野外通缉开关ActionPerformed

    private void 幸运职业开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_幸运职业开关ActionPerformed
        按键开关("幸运职业开关", 749);
        刷新幸运职业开关();
    }//GEN-LAST:event_幸运职业开关ActionPerformed

    private void 修改神秘商人ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改神秘商人ActionPerformed
        boolean result1 = 神秘商人出现时间.getText().matches("[0-9]+");
        if (result1) {
            MapleParty.神秘商人时间 = Integer.parseInt(神秘商人出现时间.getText());
            刷新神秘商人时间();
            JOptionPane.showMessageDialog(null, "修改成功，神秘商人出现时间变更为 " + 神秘商人出现时间.getText());
        }
    }//GEN-LAST:event_修改神秘商人ActionPerformed

    private void 幸运职业修改ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_幸运职业修改ActionPerformed
        boolean result1 = 幸运职业代码.getText().matches("[0-9]+");
        if (result1) {
            MapleParty.幸运职业 = Integer.parseInt(幸运职业代码.getText());
            刷新幸运职业();
            JOptionPane.showMessageDialog(null, "修改成功，幸运职业变更为 " + 幸运职业代码.getText());
        }
    }//GEN-LAST:event_幸运职业修改ActionPerformed

    private void 神秘商人开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_神秘商人开关ActionPerformed
        按键开关("神秘商人开关", 2406);
        刷新神秘商人开关();
    }//GEN-LAST:event_神秘商人开关ActionPerformed

    private void 周末倍率开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_周末倍率开关ActionPerformed
        按键开关("周末倍率开关", 2405);
        刷新周末倍率开关();
    }//GEN-LAST:event_周末倍率开关ActionPerformed

    private void 每日送货开关3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_每日送货开关3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_每日送货开关3ActionPerformed
  
    private void 刷新周末倍率开关() {
        String 显示 = "";
        int S = gui.FengYeDuan.ConfigValuesMap.get("周末倍率开关");
        if (S <= 0) {
            显示 = "开启";
        } else {
            显示 = "关闭";
        }
        周末倍率开关.setText(显示);
    }
    
    private void 刷新神秘商人时间() {

        神秘商人出现时间.setText("" + MapleParty.神秘商人时间 + "");
    }
    private void 刷新幸运职业() {

        幸运职业代码.setText("" + MapleParty.幸运职业 + "");
    }
    
    private void 刷新神秘商人开关() {
        String 显示 = "";
        int S = gui.FengYeDuan.ConfigValuesMap.get("神秘商人开关");
        if (S <= 0) {
            显示 = "开启";
        } else {
            显示 = "关闭";
        }
        神秘商人开关.setText(显示);
    }
    
     private void 刷新幸运职业开关() {
        String 显示 = "";
        int S = gui.FengYeDuan.ConfigValuesMap.get("幸运职业开关");
        if (S <= 0) {
            显示 = "开启";
        } else {
            显示 = "关闭";
        }
        幸运职业开关.setText(显示);
    }
    
    private void 刷新野外通缉开关() {
        String 显示 = "";
        int S = gui.FengYeDuan.ConfigValuesMap.get("野外通缉开关");
        if (S <= 0) {
            显示 = "开启";
        } else {
            显示 = "关闭";
        }
        野外通缉开关.setText(显示);
    }
    
    
     private void 刷新魔族突袭开关() {
        String 显示 = "";
        int S = gui.FengYeDuan.ConfigValuesMap.get("魔族突袭开关");
        if (S <= 0) {
            显示 = "开启";
        } else {
            显示 = "关闭";
        }
        魔族突袭开关.setText(显示);
    }
     
     private void 刷新魔族攻城开关() {
        String 显示 = "";
        int S = gui.FengYeDuan.ConfigValuesMap.get("魔族攻城开关");
        if (S <= 0) {
            显示 = "开启";
        } else {
            显示 = "关闭";
        }
        魔族攻城开关.setText(显示);
    }
    
         private void 刷新钓鱼() {
        for (int i = ((DefaultTableModel) (this.钓鱼物品.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.钓鱼物品.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM 钓鱼物品");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 钓鱼物品.getModel()).insertRow(钓鱼物品.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    rs.getInt("itemid"),
                    rs.getInt("chance"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))//itemid
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
        }
        钓鱼物品.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 钓鱼物品.getSelectedRow();
                String a = 钓鱼物品.getValueAt(i, 0).toString();
                String a1 = 钓鱼物品.getValueAt(i, 1).toString();
                String a2 = 钓鱼物品.getValueAt(i, 2).toString();
                //String a3 = 钓鱼物品.getValueAt(i, 3).toString();
                钓鱼物品序号.setText(a);
                钓鱼物品代码.setText(a1);
                钓鱼物品概率.setText(a2);
                //钓鱼物品名称.setText(a3);
            }
        });
    }
         
         public void 刷新野外BOSS刷新时间() {
        for (int i = ((DefaultTableModel) (this.野外BOSS刷新时间.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.野外BOSS刷新时间.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM configvalues WHERE id >= 10000 && id < 20000");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 野外BOSS刷新时间.getModel()).insertRow(野外BOSS刷新时间.getRowCount(), new Object[]{rs.getString("id"), rs.getString("name"), rs.getString("Val")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(活动控制台.class.getName()).log(Level.SEVERE, null, ex);
        }
        野外BOSS刷新时间.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 野外BOSS刷新时间.getSelectedRow();
                String a = 野外BOSS刷新时间.getValueAt(i, 0).toString();
                String a1 = 野外BOSS刷新时间.getValueAt(i, 1).toString();
                String a2 = 野外BOSS刷新时间.getValueAt(i, 2).toString();
                野外BOSS序号.setText(a);
                野外BOSS.setText(a1);
                野外BOSS刷新时间值.setText(a2);
            }
        });
    }
    

        public void 按键开关(String a, int b) {
        int 检测开关 = FengYeDuan.ConfigValuesMap.get(a);
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        if (检测开关 > 0) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM configvalues WHERE id = ?");
                ps1.setInt(1, b);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString2 = null;
                    String sqlString3 = null;
                    String sqlString4 = null;
                    sqlString2 = "update configvalues set Val= '0' where id= '" + b + "';";
                    PreparedStatement dropperid = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    dropperid.executeUpdate(sqlString2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM configvalues WHERE id = ?");
                ps1.setInt(1, b);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString2 = null;
                    String sqlString3 = null;
                    String sqlString4 = null;
                    sqlString2 = "update configvalues set Val= '1' where id='" + b + "';";
                    PreparedStatement dropperid = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    dropperid.executeUpdate(sqlString2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FengYeDuan.GetConfigValues();
    }
    
   public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(活动控制台.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(活动控制台.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(活动控制台.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(活动控制台.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        活动控制台.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
            // UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new 活动控制台().setVisible(true);
            }
        });
    }
         
         
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BOSS相关;
    private javax.swing.JLabel ZEVMS2提示框1;
    private javax.swing.JLabel jLabel264;
    private javax.swing.JLabel jLabel267;
    private javax.swing.JLabel jLabel269;
    private javax.swing.JLabel jLabel270;
    private javax.swing.JLabel jLabel272;
    private javax.swing.JLabel jLabel273;
    private javax.swing.JLabel jLabel274;
    private javax.swing.JLabel jLabel275;
    private javax.swing.JLabel jLabel323;
    private javax.swing.JLabel jLabel324;
    private javax.swing.JLabel jLabel325;
    private javax.swing.JLabel jLabel343;
    private javax.swing.JLabel jLabel344;
    private javax.swing.JLabel jLabel379;
    private javax.swing.JLabel jLabel380;
    private javax.swing.JLabel jLabel381;
    private javax.swing.JLabel jLabel382;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel91;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane104;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JButton 修改神秘商人;
    private javax.swing.JButton 修改钓鱼物品;
    private javax.swing.JButton 删除钓鱼物品;
    private javax.swing.JButton 刷新野外BOSS刷新时间;
    private javax.swing.JButton 刷新野外BOSS刷新时间修改;
    private javax.swing.JButton 刷新钓鱼物品;
    private javax.swing.JButton 周末倍率开关;
    private javax.swing.JTextField 幸运职业代码;
    private javax.swing.JButton 幸运职业修改;
    private javax.swing.JButton 幸运职业开关;
    private javax.swing.JButton 新增钓鱼物品;
    private javax.swing.JButton 每日送货开关3;
    private javax.swing.JTextField 神秘商人出现时间;
    private javax.swing.JButton 神秘商人开关;
    private javax.swing.JTextField 野外BOSS;
    private javax.swing.JTable 野外BOSS刷新时间;
    private javax.swing.JTextField 野外BOSS刷新时间值;
    private javax.swing.JTextField 野外BOSS序号;
    private javax.swing.JButton 野外通缉开关;
    private javax.swing.JTable 钓鱼物品;
    private javax.swing.JTextField 钓鱼物品代码;
    private javax.swing.JTextField 钓鱼物品名称;
    private javax.swing.JTextField 钓鱼物品序号;
    private javax.swing.JTextField 钓鱼物品概率;
    private javax.swing.JPanel 钓鱼管理;
    private javax.swing.JButton 魔族攻城开关;
    private javax.swing.JButton 魔族突袭开关;
    // End of variables declaration//GEN-END:variables
}
