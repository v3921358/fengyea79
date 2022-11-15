/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.border.*;
import client.LoginCrypto;
import client.MapleCharacter;
import client.MapleClient;
import client.inventory.Equip;
import client.inventory.ItemFlag;
import client.inventory.MapleInventoryType;
//import com.alee.laf.WebLookAndFeel;
import constants.GameConstants;
import constants.ServerConfig;
import constants.WorldConstants;
import database.DatabaseConnection;
import gui.tools.一键还原;
import gui.tools.代码查询工具;
import gui.tools.删除自添加NPC工具;
import gui.tools.活动控制台;
import gui.tools.游戏抽奖工具;
import handling.RecvPacketOpcode;
import handling.SendPacketOpcode;
import handling.channel.ChannelServer;
import handling.login.LoginServer;
import handling.login.handler.AutoRegister;
import handling.world.World;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import static java.awt.SystemColor.text;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.Document;
import static jdk.nashorn.internal.objects.ArrayBufferView.buffer;
import scripting.NPCConversationManager;
//import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
//import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import scripting.PortalScriptManager;
import scripting.ReactorScriptManager;
import server.*;
import server.Timer.EventTimer;
import server.life.MapleMonsterInformationProvider;
import server.maps.MapleMap;
import server.quest.MapleQuest;
//import sun.swing.table.DefaultTableCellHeaderRenderer;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;
import static jdk.nashorn.internal.objects.ArrayBufferView.buffer;
import static jdk.nashorn.internal.objects.ArrayBufferView.buffer;
import static jdk.nashorn.internal.objects.ArrayBufferView.buffer;
import merchant.merchant_main;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import static server.MapleCarnivalChallenge.getJobNameById;
//import jutil.MapleStoryDate;
//import jutil.MapleStoryJUtil;

/**
 *
 * @author Administrator
 */
public class FengYeDuan extends javax.swing.JFrame {

    private ImageIcon bgImg = new ImageIcon(this.getClass().getClassLoader().getResource("image/qqq.jpg"));// 图片路径不要写错了
    private JLabel imgLabel = new JLabel(bgImg);
    public static Map<String, Integer> ConfigValuesMap = new HashMap<>();
    private static FengYeDuan instance = new FengYeDuan();
    private Map<Windows, javax.swing.JFrame> windows = new HashMap<>();
    
    boolean 调试模式 = false;
    boolean 自动注册 = false;
    String 服务器名字 = "获取中";
    String 经验倍数 = "获取中";
    boolean 开启服务端 = false;
    private boolean searchServer = false;
    String accname = "null", pwd = "null", money = "null", rmb = "null", dj = "null", dy = "null", ljzz = "null";
    String mima = "123456";
    int accid = 0;
    private static int codeid = 220000;
    private static String authCode = "";
    
    public static boolean etcCheck = false;
    public static boolean useCheck = false;
    public static int etcSlotMax = 100;
    public static int useSlotMax = 100;
    public static String exclude = "";
    

    private String 获取网络时间(String httpbaiducom) {
        try {
            URL url = new URL(httpbaiducom);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate();// 读取网站日期时间
            Date date = new Date(ld);// 转换为标准时间对象
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 输出北京时间
            return sdf.format(date);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
            rs.close();
            ps.close();
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

    private void 刷新魔族突袭开关() {
        String 显示 = "";
        int S = gui.FengYeDuan.ConfigValuesMap.get("魔族突袭开关");
        if (S <= 0) {
            this.魔族突袭开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.魔族突袭开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
//        魔族突袭开关.setText(显示);
    }

    private void 刷新魔族攻城开关() {
        String 显示 = "";
        int S = gui.FengYeDuan.ConfigValuesMap.get("魔族攻城开关");
        if (S <= 0) {
            this.魔族攻城开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.魔族攻城开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
//        魔族攻城开关.setText(显示);
    }

    private void 刷新幸运职业开关() {
        String 显示 = "";
        int S = gui.FengYeDuan.ConfigValuesMap.get("幸运职业开关");
        if (S <= 0) {
            this.幸运职业开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.幸运职业开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
//        幸运职业开关.setText(显示);
    }

    private void 刷新神秘商人开关() {
        String 显示 = "";
        int S = gui.FengYeDuan.ConfigValuesMap.get("神秘商人开关");
        if (S <= 0) {
            this.神秘商人开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.神秘商人开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
//        神秘商人开关.setText(显示);
    }

    private void 刷新公告广播() {
 for (int i = ((DefaultTableModel) (this.广播信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.广播信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM 广播信息");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 广播信息.getModel()).insertRow(广播信息.getRowCount(), new Object[]{
                    rs.getString("id"),
                    rs.getString("广播")
                });
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
        }
        广播信息.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 广播信息.getSelectedRow();
                String a = 广播信息.getValueAt(i, 0).toString();
                String a1 = 广播信息.getValueAt(i, 1).toString();
                广播序号.setText(a);
                广播文本.setText(a1);
            }
        });
    }                             

    private void 刷物品() {
        try {
            String 名字;
            if ("玩家名字".equals(个人发送物品玩家名字.getText())) {
                名字 = "";
            } else {
                名字 = 个人发送物品玩家名字.getText();
            }
            int 物品ID;
            if ("物品ID".equals(个人发送物品代码.getText())) {
                物品ID = 0;
            } else {
                物品ID = Integer.parseInt(个人发送物品代码.getText());
            }
            int 数量;
            if ("数量".equals(个人发送物品数量.getText())) {
                数量 = 0;
            } else {
                数量 = Integer.parseInt(个人发送物品数量.getText());
            }
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            MapleInventoryType type = GameConstants.getInventoryType(物品ID);
            for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (mch.getName().equals(名字)) {
                        if (数量 >= 0) {
                            if (!MapleInventoryManipulator.checkSpace(mch.getClient(), 物品ID, 数量, "")) {
                                return;
                            }
                            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)
                                    || type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100) {
                                final Equip item = (Equip) (ii.getEquipById(物品ID));
                                if (ii.isCash(物品ID)) {
                                    item.setUniqueId(1);
                                }
                                final String name = ii.getName(物品ID);
                                if (物品ID / 10000 == 114 && name != null && name.length() > 0) { //medal
                                    final String msg = "你已获得称号 <" + name + ">";
                                    mch.getClient().getPlayer().dropMessage(5, msg);
                                }
                                MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                            } else {
                                MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 数量, "", null, (byte) 0);
                            }
                        } else {
                            MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -数量, true, false);
                        }
                        mch.getClient().sendPacket(MaplePacketCreator.getShowItemGain(物品ID, (short) 数量, true));
                    }
                }
            }
            个人发送物品玩家名字.setText("");
            个人发送物品代码.setText("");
            个人发送物品数量.setText("");
            //福利提示语言.setText("[信息]:发送成功。");
        } catch (Exception e) {
            //福利提示语言.setText("[信息]:错误!" + e);
        }
    }

    private static class MapleStoryDate {

        public MapleStoryDate() {
        }

        private String getCurrentTime() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    private static class MapleStoryJUtil {

        public MapleStoryJUtil() {
        }

        private String getUUID() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    
    public class HomePanel extends JPanel {

        ImageIcon icon;
        Image img;

        public HomePanel() {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("image/logo.png"));
            this.img = icon.getImage();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(this.img, 0, 0, getWidth(), getHeight(), this);
        }
    }

    //导入gif
    public class HomePanel2 extends JPanel {

        ImageIcon icon;
        Image img;

        public HomePanel2() {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("image/long.gif"));
            this.img = icon.getImage();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(this.img, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Creates new form KinMS
     */
    public static final FengYeDuan getInstance() {

// 重定向System.out和System.err
        return instance;
    }

    public FengYeDuan() {

        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("image/Icon.png"));
        setIconImage(icon.getImage());
        setTitle("枫叶-8.0 Ver.079服务端");
        //控制台预加载配置
        GetConfigValues();
        initComponents();
        刷新信息();
        刷新蓝蜗牛开关();
        刷新蘑菇仔开关();
        刷新绿水灵开关();
        刷新漂漂猪开关();
        刷新小青蛇开关();
        刷新红螃蟹开关();
        刷新大海龟开关();
        刷新章鱼怪开关();
        刷新顽皮猴开关();
        刷新星精灵开关();
        刷新胖企鹅开关();
        刷新白雪人开关();
        刷新石头人开关();
        刷新紫色猫开关();
        刷新大灰狼开关();
        刷新小白兔开关();
        刷新喷火龙开关();
        刷新火野猪开关();
        刷新青鳄鱼开关();
        刷新花蘑菇开关();
 

        initview();//初始化控件信息

        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);

        //jPanel1.setOpaque(false);
        jPanel6.setOpaque(false);
        输出窗口.setEditable(false);
        输出窗口.setLineWrap(true);        //激活自动换行功能 
        输出窗口.setWrapStyleWord(true);            // 激活断行不断字功能
        jTabbedPane2.setOpaque(false);
        
        jTextField1.setText(authCode);
        刷新特殊功能();
        
    }
    
    private void 刷新特殊功能() {
        //叠加信息
        etcCheck = ConfigValuesMap.get("其他栏叠加开关") != null && ConfigValuesMap.get("其他栏叠加开关") == 1;
        useCheck = ConfigValuesMap.get("消耗栏叠加开关") != null && ConfigValuesMap.get("消耗栏叠加开关") == 1;
        etcSlotMax = ConfigValuesMap.get("其他栏叠加数量") == null ? 100:ConfigValuesMap.get("其他栏叠加数量");
        useSlotMax = ConfigValuesMap.get("消耗栏叠加数量") == null ? 100:ConfigValuesMap.get("消耗栏叠加数量");
        java.util.List<String> list = new ArrayList<>();
        for (String key:ConfigValuesMap.keySet()) {
            if (key.startsWith("叠加排除项")) {
                list.add(key.substring(key.indexOf("叠加排除项") + 5));
            }
        }
        for (int i = 0; i < list.size(); i++) {
            exclude += list.get(i);
            if (i != list.size() - 1) {
                exclude += ",";
            }
        }
        this.jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource(etcCheck?"ON4.png":"OFF4.png")));//物品叠加图片第二个地方
        this.jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource(useCheck?"ON4.png":"OFF4.png")));
        this.jTextField5.setText(exclude);
        this.jTextField4.setText(useSlotMax + "");
        this.jTextField3.setText(etcSlotMax + "");
        
        //经验倍率
        ServerConfig.levelCheck1 = ConfigValuesMap.get("阶段一等级经验开关") != null && ConfigValuesMap.get("阶段一等级经验开关") == 1;
        ServerConfig.levelCheck2 = ConfigValuesMap.get("阶段二等级经验开关") != null && ConfigValuesMap.get("阶段二等级经验开关") == 1;
        ServerConfig.levelCheck3 = ConfigValuesMap.get("阶段三等级经验开关") != null && ConfigValuesMap.get("阶段三等级经验开关") == 1;
        
        ServerConfig.minLevel1 = ServerConfig.levelCheck1 && ConfigValuesMap.get("阶段一最小等级") != null?ConfigValuesMap.get("阶段一最小等级"):ServerConfig.minLevel1;
        ServerConfig.maxLevel1 = ServerConfig.levelCheck1 && ConfigValuesMap.get("阶段一最大等级") != null?ConfigValuesMap.get("阶段一最大等级"):ServerConfig.maxLevel1;
        ServerConfig.BeiShu1 = ServerConfig.levelCheck1 && ConfigValuesMap.get("阶段一经验倍数") != null?ConfigValuesMap.get("阶段一经验倍数"):ServerConfig.BeiShu1;
        ServerConfig.minLevel2 = ServerConfig.levelCheck2 && ConfigValuesMap.get("阶段二最小等级") != null?ConfigValuesMap.get("阶段二最小等级"):ServerConfig.minLevel2;
        ServerConfig.maxLevel2 = ServerConfig.levelCheck2 && ConfigValuesMap.get("阶段二最大等级") != null?ConfigValuesMap.get("阶段二最大等级"):ServerConfig.maxLevel2;
        ServerConfig.BeiShu2 = ServerConfig.levelCheck2 && ConfigValuesMap.get("阶段二经验倍数") != null?ConfigValuesMap.get("阶段二经验倍数"):ServerConfig.BeiShu2;
        ServerConfig.minLevel3 = ServerConfig.levelCheck3 && ConfigValuesMap.get("阶段三最小等级") != null?ConfigValuesMap.get("阶段三最小等级"):ServerConfig.minLevel3;
        ServerConfig.maxLevel3 = ServerConfig.levelCheck3 && ConfigValuesMap.get("阶段三最大等级") != null?ConfigValuesMap.get("阶段三最大等级"):ServerConfig.maxLevel3;
        ServerConfig.BeiShu3 = ServerConfig.levelCheck3 && ConfigValuesMap.get("阶段三经验倍数") != null?ConfigValuesMap.get("阶段三经验倍数"):ServerConfig.BeiShu3;
        
        this.jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource(ServerConfig.levelCheck1?"ON4.png":"OFF4.png")));//物品叠加图片第二个地方
        this.jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource(ServerConfig.levelCheck2?"ON4.png":"OFF4.png")));
        this.jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource(ServerConfig.levelCheck3?"ON4.png":"OFF4.png")));
        this.jTextField6.setText(ServerConfig.minLevel1+"");
        this.jTextField7.setText(ServerConfig.maxLevel1 + "");
        this.jTextField8.setText(ServerConfig.BeiShu1 + "");
        this.jTextField9.setText(ServerConfig.minLevel2+"");
        this.jTextField10.setText(ServerConfig.maxLevel2 + "");
        this.jTextField11.setText(ServerConfig.BeiShu2 + "");
        this.jTextField12.setText(ServerConfig.minLevel3+"");
        this.jTextField13.setText(ServerConfig.maxLevel3 + "");
        this.jTextField14.setText(ServerConfig.BeiShu3 + "");
        
        //测谎
        WorldConstants.LieDetector = ConfigValuesMap.get("定时测谎开关") != null && ConfigValuesMap.get("定时测谎开关") == 1;
        WorldConstants.MobLieDetector = ConfigValuesMap.get("怪物数量测谎开关") != null && ConfigValuesMap.get("怪物数量测谎开关") == 1;
        this.jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource(WorldConstants.LieDetector?"ON4.png":"OFF4.png")));
        this.jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource(WorldConstants.MobLieDetector?"ON4.png":"OFF4.png")));
        
        WorldConstants.LieDetectorNum = WorldConstants.LieDetector && ConfigValuesMap.get("定时测谎分钟数") != null?ConfigValuesMap.get("定时测谎分钟数"):WorldConstants.LieDetectorNum;
        WorldConstants.MobLieDetectorNum = WorldConstants.MobLieDetector && ConfigValuesMap.get("怪物测谎数量") != null?ConfigValuesMap.get("怪物测谎数量"):WorldConstants.MobLieDetectorNum;
        this.jTextField15.setText(WorldConstants.MobLieDetectorNum + "");
        this.jTextField16.setText(WorldConstants.LieDetectorNum + "");
        
        //商城折扣
        WorldConstants.isDiscount = ConfigValuesMap.get("商城折扣开关") != null && ConfigValuesMap.get("商城折扣开关") == 1;
        this.jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource(WorldConstants.isDiscount?"ON4.png":"OFF4.png")));
        WorldConstants.discount = WorldConstants.isDiscount && ConfigValuesMap.get("商城折扣数值") != null?ConfigValuesMap.get("商城折扣数值"):WorldConstants.discount;
        this.jTextField17.setText(WorldConstants.discount + "");
    }
    
    private void 刷新信息() {
        刷新公告广播();
        刷新魔族突袭开关();
        刷新魔族攻城开关();
        刷新幸运职业开关();
        刷新神秘商人开关();
        刷新冒险家职业开关();
        刷新战神职业开关();
        刷新骑士团职业开关();
        刷新冒险家等级上限();
        刷新骑士团等级上限();
        刷新账号信息();
        刷新角色信息();
        刷新泡点金币开关();
        刷新泡点点券开关();
        刷新泡点经验开关();
        刷新泡点抵用开关();
        刷新泡点豆豆开关();
        刷新泡点设置();
        刷新过图存档时间();
        刷新地图名称开关();
        刷新登陆帮助();
        刷新怪物状态开关();
        刷新越级打怪开关();
        刷新回收地图开关();
        刷新玩家聊天开关();
        刷新滚动公告开关();
        刷新指令通知开关();
        刷新管理隐身开关();
        刷新管理加速开关();
        刷新游戏指令开关();
        刷新游戏喇叭开关();
        刷新丢出金币开关();
        刷新丢出物品开关();
        刷新雇佣商人开关();
        刷新上线提醒开关();
        刷新升级快讯();
        刷新玩家交易开关();
        刷新欢迎弹窗开关();
        刷新禁止登陆开关();
        刷新吸怪检测开关();
        刷新经验加成表();
        刷新屠令广播开关();
        UpdateMaxCharacterSlots();
       
    }

    //初始化表内数据 表结构
    @Override
    public void setVisible(boolean bln) {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) (size.getWidth() - getWidth()) / 2, (int) (size.getHeight() - getHeight()) / 2);
        super.setVisible(bln);
        try {
            //inivalue.初始化账号表(0);
            //inivalue.初始化角色表(0);
            //inivalue.初始化爆率表(0);
        } catch (Exception ex) {
            System.out.println("初始            initDropData();\n"
                    + "            initDropDataGlobal();化角色訊息錯誤:" + ex);
            JOptionPane.showMessageDialog(null, "初始化角色訊息錯誤, 請確認MySQL是否正常啟動");

        }
    }

    public static String[] DEFAULT_FONT = new String[]{
        "Table.font",
        "TableHeader.font",
        "CheckBox.font",
        "Tree.font",
        "Viewport.font",
        "ProgressBar.font",
        "RadioButtonMenuItem.font",
        "ToolBar.font",
        "ColorChooser.font",
        "ToggleButton.font",
        "Panel.font",
        "TextArea.font",
        "Menu.font",
        "TableHeader.font" // ,"TextField.font"
        ,
         "OptionPane.font",
        "MenuBar.font",
        "Button.font",
        "Label.font",
        "PasswordField.font",
        "ScrollPane.font",
        "MenuItem.font",
        "ToolTip.font",
        "List.font",
        "EditorPane.font",
        "Table.font",
        "TabbedPane.font",
        "RadioButton.font",
        "CheckBoxMenuItem.font",
        "TextPane.font",
        "PopupMenu.font",
        "TitledBorder.font",
        "ComboBox.font"
    };

    public void actionPerformed(ActionEvent e) {
        //计时开始
        Dis tt = new Dis();
        tt.start();
    }
    //计时开始
    int t1 = 0;
    int year = Calendar.getInstance().get(Calendar.YEAR);//年
    int month = Calendar.getInstance().get(Calendar.MONTH) + 1;//月
    int date = Calendar.getInstance().get(Calendar.DATE);//日
    int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);//小时
    int minute = Calendar.getInstance().get(Calendar.MINUTE);//分钟
    int second = Calendar.getInstance().get(Calendar.SECOND); //毫秒

    private class Dis extends Thread {

        public Dis() {

        }

        public void run() {

            while (true) {
                t1++;
                jLabel25.setText(t1 / 60 + " 分钟");

                try {
                    Thread.sleep(1000);
                    if (hour == 0) {
                        //if(结算 == false){
                         //   每日排行结算();
                       // }
                   // }else{
                    //  结算 = false;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void GetConfigValues() {
        //动态数据库连接
        Connection con = DatabaseConnection.getConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT id, name, val FROM ConfigValues")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int aInt = rs.getInt("id");
                    String name = rs.getString("name");
                    int val = rs.getInt("val");
                    if (aInt == codeid) {
                        authCode = name;
                    } else {
                        ConfigValuesMap.put(name, val);
                    }
                }
            }
            ps.close();
        } catch (SQLException ex) {
            System.err.println("读取动态数据库出错：" + ex.getMessage());
        }
    }
    
    public void initview() {

        /*  363: 344 */
        try {
            LoopedStreams ls = new LoopedStreams();
            PrintStream ps = new PrintStream(ls.getOutputStream());
            System.setOut(ps);
            System.setErr(ps);
            startConsoleReaderThread(ls.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
        }
        ((JPanel) getContentPane()).setOpaque(true); // 将JFrame上自带的面板设置为透明，否则背景图片
        UIManager.put("TabbedPane.contentOpaque", true);
//        JtabbedPane2.setOpaque(false);
    }

    void startConsoleReaderThread(InputStream inStream) {
        final BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
        new Thread(new Runnable() {
            public void run() {
                StringBuffer sb = new StringBuffer();
                try {
                    String s;
                    while ((s = br.readLine()) != null) {
                        boolean caretAtEnd = false;
                        sb.setLength(0);
                        FengYeDuan.this.输出窗口.append(new StringBuilder().append("").append(s).toString() + '\n');
                        if (!caretAtEnd) {
                        }
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "从BufferedReader读取错误：" + e);

                    System.exit(1);
                }
            }
        }).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        canvas1 = new java.awt.Canvas();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jTabbedPaneBackendCSPoints = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jPanel72 = new javax.swing.JPanel();
        禁止登陆开关 = new javax.swing.JButton();
        滚动公告开关 = new javax.swing.JButton();
        玩家聊天开关 = new javax.swing.JButton();
        游戏升级快讯 = new javax.swing.JButton();
        丢出金币开关 = new javax.swing.JButton();
        丢出物品开关 = new javax.swing.JButton();
        游戏指令开关 = new javax.swing.JButton();
        上线提醒开关 = new javax.swing.JButton();
        管理隐身开关 = new javax.swing.JButton();
        管理加速开关 = new javax.swing.JButton();
        游戏喇叭开关 = new javax.swing.JButton();
        玩家交易开关 = new javax.swing.JButton();
        雇佣商人开关 = new javax.swing.JButton();
        欢迎弹窗开关 = new javax.swing.JButton();
        登陆帮助开关 = new javax.swing.JButton();
        越级打怪开关 = new javax.swing.JButton();
        怪物状态开关 = new javax.swing.JButton();
        地图名称开关 = new javax.swing.JButton();
        过图存档开关 = new javax.swing.JButton();
        指令通知开关 = new javax.swing.JButton();
        吸怪检测开关 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        屠令广播开关 = new javax.swing.JButton();
        回收地图开关 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        输出窗口 = new javax.swing.JTextArea();
        jButton16 = new javax.swing.JButton();
        jTextField22 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        startserverbutton = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        startserverbutton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSN = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldNum = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldCSPointsValue = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldSN = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldAccount = new javax.swing.JTextField();
        jButtonSNSearchByAccount = new javax.swing.JButton();
        jButtonLock = new javax.swing.JButton();
        jButtonSNModify = new javax.swing.JButton();
        jTextFieldType = new javax.swing.JTextField();
        jButtonSNDelete = new javax.swing.JButton();
        jButtonSNSearch = new javax.swing.JButton();
        jButtonUnlock = new javax.swing.JButton();
        jButtonUsed = new javax.swing.JButton();
        jButtonUnused = new javax.swing.JButton();
        jButtonOderByUseTime = new javax.swing.JButton();
        jPanel53 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldProductionNum = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jComboBoxMoneyb = new javax.swing.JComboBox<>();
        jButtonSNProduction = new javax.swing.JButton();
        jButtonSNRefresh = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jTextFieldSNValue = new javax.swing.JTextField();
        jPanel93 = new javax.swing.JPanel();
        jScrollPane136 = new javax.swing.JScrollPane();
        经验加成表 = new javax.swing.JTable();
        经验加成表序号 = new javax.swing.JTextField();
        经验加成表类型 = new javax.swing.JTextField();
        经验加成表数值 = new javax.swing.JTextField();
        经验加成表修改 = new javax.swing.JButton();
        jLabel384 = new javax.swing.JLabel();
        jLabel385 = new javax.swing.JLabel();
        jLabel386 = new javax.swing.JLabel();
        游戏经验加成说明 = new javax.swing.JButton();
        jPanel66 = new javax.swing.JPanel();
        jPanel69 = new javax.swing.JPanel();
        开启双倍经验 = new javax.swing.JButton();
        双倍经验持续时间 = new javax.swing.JTextField();
        jLabel359 = new javax.swing.JLabel();
        开启双倍爆率 = new javax.swing.JButton();
        双倍爆率持续时间 = new javax.swing.JTextField();
        jLabel360 = new javax.swing.JLabel();
        开启双倍金币 = new javax.swing.JButton();
        双倍金币持续时间 = new javax.swing.JTextField();
        jLabel361 = new javax.swing.JLabel();
        jPanel70 = new javax.swing.JPanel();
        开启三倍经验 = new javax.swing.JButton();
        三倍经验持续时间 = new javax.swing.JTextField();
        jLabel362 = new javax.swing.JLabel();
        开启三倍爆率 = new javax.swing.JButton();
        三倍爆率持续时间 = new javax.swing.JTextField();
        jLabel348 = new javax.swing.JLabel();
        开启三倍金币 = new javax.swing.JButton();
        三倍金币持续时间 = new javax.swing.JTextField();
        jLabel349 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel52 = new javax.swing.JPanel();
        游戏广播 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        广播信息 = new javax.swing.JTable();
        刷新广告 = new javax.swing.JButton();
        删除广播 = new javax.swing.JButton();
        发布广告 = new javax.swing.JButton();
        修改广播 = new javax.swing.JButton();
        广播序号 = new javax.swing.JTextField();
        广播文本 = new javax.swing.JTextField();
        jPanel51 = new javax.swing.JPanel();
        jPanel74 = new javax.swing.JPanel();
        蓝蜗牛开关 = new javax.swing.JButton();
        蘑菇仔开关 = new javax.swing.JButton();
        绿水灵开关 = new javax.swing.JButton();
        漂漂猪开关 = new javax.swing.JButton();
        小青蛇开关 = new javax.swing.JButton();
        红螃蟹开关 = new javax.swing.JButton();
        大海龟开关 = new javax.swing.JButton();
        章鱼怪开关 = new javax.swing.JButton();
        顽皮猴开关 = new javax.swing.JButton();
        星精灵开关 = new javax.swing.JButton();
        胖企鹅开关 = new javax.swing.JButton();
        白雪人开关 = new javax.swing.JButton();
        石头人开关 = new javax.swing.JButton();
        紫色猫开关 = new javax.swing.JButton();
        大灰狼开关 = new javax.swing.JButton();
        喷火龙开关 = new javax.swing.JButton();
        火野猪开关 = new javax.swing.JButton();
        小白兔开关 = new javax.swing.JButton();
        青鳄鱼开关 = new javax.swing.JButton();
        花蘑菇开关 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jTextMaxLevel = new javax.swing.JTextField();
        修改冒险家等级上限 = new javax.swing.JButton();
        jLabel253 = new javax.swing.JLabel();
        骑士团等级上限 = new javax.swing.JTextField();
        jLabel252 = new javax.swing.JLabel();
        修改骑士团等级上限 = new javax.swing.JButton();
        jTextFieldMaxCharacterNumber = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jButtonMaxCharacter = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        冒险家职业开关 = new javax.swing.JButton();
        战神职业开关 = new javax.swing.JButton();
        骑士团职业开关 = new javax.swing.JButton();
        jPanel54 = new javax.swing.JPanel();
        幸运职业开关 = new javax.swing.JButton();
        神秘商人开关 = new javax.swing.JButton();
        魔族突袭开关 = new javax.swing.JButton();
        魔族攻城开关 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton44 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        重载副本按钮 = new javax.swing.JButton();
        重载爆率按钮 = new javax.swing.JButton();
        重载反应堆按钮 = new javax.swing.JButton();
        重载传送门按钮 = new javax.swing.JButton();
        重载商城按钮 = new javax.swing.JButton();
        重载商店按钮 = new javax.swing.JButton();
        重载包头按钮 = new javax.swing.JButton();
        重载任务 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel59 = new javax.swing.JPanel();
        z2 = new javax.swing.JButton();
        z3 = new javax.swing.JButton();
        z1 = new javax.swing.JButton();
        z4 = new javax.swing.JButton();
        z5 = new javax.swing.JButton();
        z6 = new javax.swing.JButton();
        a1 = new javax.swing.JTextField();
        jLabel235 = new javax.swing.JLabel();
        jPanel57 = new javax.swing.JPanel();
        全服发送物品数量 = new javax.swing.JTextField();
        全服发送物品代码 = new javax.swing.JTextField();
        给予物品1 = new javax.swing.JButton();
        jLabel217 = new javax.swing.JLabel();
        jLabel234 = new javax.swing.JLabel();
        jPanel58 = new javax.swing.JPanel();
        全服发送装备装备加卷 = new javax.swing.JTextField();
        全服发送装备装备制作人 = new javax.swing.JTextField();
        全服发送装备装备力量 = new javax.swing.JTextField();
        全服发送装备装备MP = new javax.swing.JTextField();
        全服发送装备装备智力 = new javax.swing.JTextField();
        全服发送装备装备运气 = new javax.swing.JTextField();
        全服发送装备装备HP = new javax.swing.JTextField();
        全服发送装备装备攻击力 = new javax.swing.JTextField();
        全服发送装备装备给予时间 = new javax.swing.JTextField();
        全服发送装备装备可否交易 = new javax.swing.JTextField();
        全服发送装备装备敏捷 = new javax.swing.JTextField();
        全服发送装备物品ID = new javax.swing.JTextField();
        全服发送装备装备魔法力 = new javax.swing.JTextField();
        全服发送装备装备魔法防御 = new javax.swing.JTextField();
        全服发送装备装备物理防御 = new javax.swing.JTextField();
        给予装备1 = new javax.swing.JButton();
        jLabel219 = new javax.swing.JLabel();
        jLabel220 = new javax.swing.JLabel();
        jLabel221 = new javax.swing.JLabel();
        jLabel222 = new javax.swing.JLabel();
        jLabel223 = new javax.swing.JLabel();
        jLabel224 = new javax.swing.JLabel();
        jLabel225 = new javax.swing.JLabel();
        jLabel226 = new javax.swing.JLabel();
        jLabel227 = new javax.swing.JLabel();
        jLabel228 = new javax.swing.JLabel();
        jLabel229 = new javax.swing.JLabel();
        jLabel230 = new javax.swing.JLabel();
        jLabel231 = new javax.swing.JLabel();
        jLabel232 = new javax.swing.JLabel();
        jLabel233 = new javax.swing.JLabel();
        发送装备玩家姓名 = new javax.swing.JTextField();
        给予装备2 = new javax.swing.JButton();
        jLabel246 = new javax.swing.JLabel();
        jLabel244 = new javax.swing.JLabel();
        jPanel60 = new javax.swing.JPanel();
        个人发送物品数量 = new javax.swing.JTextField();
        个人发送物品玩家名字 = new javax.swing.JTextField();
        个人发送物品代码 = new javax.swing.JTextField();
        给予物品 = new javax.swing.JButton();
        jLabel240 = new javax.swing.JLabel();
        jLabel241 = new javax.swing.JLabel();
        jLabel242 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane134 = new javax.swing.JScrollPane();
        在线泡点设置 = new javax.swing.JTable();
        泡点序号 = new javax.swing.JTextField();
        泡点类型 = new javax.swing.JTextField();
        泡点值 = new javax.swing.JTextField();
        泡点值修改 = new javax.swing.JButton();
        jLabel322 = new javax.swing.JLabel();
        jLabel326 = new javax.swing.JLabel();
        jLabel327 = new javax.swing.JLabel();
        jPanel75 = new javax.swing.JPanel();
        泡点金币开关 = new javax.swing.JButton();
        泡点经验开关 = new javax.swing.JButton();
        泡点点券开关 = new javax.swing.JButton();
        泡点抵用开关 = new javax.swing.JButton();
        泡点豆豆开关 = new javax.swing.JButton();
        jLabel65 = new javax.swing.JLabel();
        jLabel328 = new javax.swing.JLabel();
        福利提示语言2 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jTabbedPane9 = new javax.swing.JTabbedPane();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane26 = new javax.swing.JScrollPane();
        反应堆 = new javax.swing.JTable();
        jPanel61 = new javax.swing.JPanel();
        jButton26 = new javax.swing.JButton();
        反应堆序列号 = new javax.swing.JTextField();
        反应堆代码 = new javax.swing.JTextField();
        反应堆物品 = new javax.swing.JTextField();
        反应堆概率 = new javax.swing.JTextField();
        新增反应堆物品 = new javax.swing.JButton();
        删除反应堆物品1 = new javax.swing.JButton();
        查找反应堆掉落 = new javax.swing.JTextField();
        jButton36 = new javax.swing.JButton();
        查找物品 = new javax.swing.JTextField();
        jButton37 = new javax.swing.JButton();
        jLabel274 = new javax.swing.JLabel();
        jLabel275 = new javax.swing.JLabel();
        jLabel277 = new javax.swing.JLabel();
        jLabel278 = new javax.swing.JLabel();
        jLabel279 = new javax.swing.JLabel();
        jLabel280 = new javax.swing.JLabel();
        修改反应堆物品 = new javax.swing.JButton();
        重载箱子反应堆按钮 = new javax.swing.JButton();
        jLabel282 = new javax.swing.JLabel();
        删除反应堆物品代码 = new javax.swing.JTextField();
        删除反应堆物品 = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        世界爆物 = new javax.swing.JTable();
        世界爆物序列号 = new javax.swing.JTextField();
        世界爆物物品代码 = new javax.swing.JTextField();
        世界爆物爆率 = new javax.swing.JTextField();
        添加世界爆物 = new javax.swing.JButton();
        删除世界爆物 = new javax.swing.JButton();
        jLabel210 = new javax.swing.JLabel();
        jLabel202 = new javax.swing.JLabel();
        jLabel209 = new javax.swing.JLabel();
        世界爆物名称 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        修改世界爆物 = new javax.swing.JButton();
        刷新世界爆物 = new javax.swing.JButton();
        jLabel323 = new javax.swing.JLabel();
        查询物品掉落代码1 = new javax.swing.JTextField();
        查询物品掉落 = new javax.swing.JButton();
        jLabel321 = new javax.swing.JLabel();
        删除指定的掉落1 = new javax.swing.JTextField();
        删除指定的掉落按键 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
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
        jPanel18 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        怪物爆物 = new javax.swing.JTable();
        jLabel213 = new javax.swing.JLabel();
        怪物爆物序列号 = new javax.swing.JTextField();
        jLabel120 = new javax.swing.JLabel();
        怪物爆物怪物代码 = new javax.swing.JTextField();
        jLabel211 = new javax.swing.JLabel();
        怪物爆物物品代码 = new javax.swing.JTextField();
        jLabel212 = new javax.swing.JLabel();
        怪物爆物爆率 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        怪物爆物物品名称 = new javax.swing.JTextField();
        添加怪物爆物 = new javax.swing.JButton();
        删除怪物爆物 = new javax.swing.JButton();
        修改怪物爆物 = new javax.swing.JButton();
        刷新怪物爆物 = new javax.swing.JButton();
        jLabel324 = new javax.swing.JLabel();
        查询物品掉落代码 = new javax.swing.JTextField();
        查询物品掉落1 = new javax.swing.JButton();
        jLabel316 = new javax.swing.JLabel();
        jLabel325 = new javax.swing.JLabel();
        删除指定的掉落 = new javax.swing.JTextField();
        删除指定的掉落按键1 = new javax.swing.JButton();
        刷新怪物卡片 = new javax.swing.JButton();
        查询怪物掉落代码 = new javax.swing.JTextField();
        查询怪物掉落 = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jTabbedPane8 = new javax.swing.JTabbedPane();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        账号信息 = new javax.swing.JTable();
        jPanel30 = new javax.swing.JPanel();
        抵用 = new javax.swing.JTextField();
        账号 = new javax.swing.JTextField();
        点券 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        修改账号点券抵用 = new javax.swing.JButton();
        账号ID = new javax.swing.JTextField();
        jLabel206 = new javax.swing.JLabel();
        jLabel312 = new javax.swing.JLabel();
        管理1 = new javax.swing.JTextField();
        jLabel353 = new javax.swing.JLabel();
        QQ = new javax.swing.JTextField();
        jLabel357 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldMoneyB = new javax.swing.JTextField();
        jPanel32 = new javax.swing.JPanel();
        注册的账号 = new javax.swing.JTextField();
        注册的密码 = new javax.swing.JTextField();
        jButton35 = new javax.swing.JButton();
        jLabel111 = new javax.swing.JLabel();
        jLabel201 = new javax.swing.JLabel();
        jButton32 = new javax.swing.JButton();
        账号提示语言 = new javax.swing.JLabel();
        刷新账号信息 = new javax.swing.JButton();
        离线账号 = new javax.swing.JButton();
        解封 = new javax.swing.JButton();
        已封账号 = new javax.swing.JButton();
        在线账号 = new javax.swing.JButton();
        删除账号 = new javax.swing.JButton();
        封锁账号 = new javax.swing.JButton();
        解卡 = new javax.swing.JButton();
        显示在线账号 = new javax.swing.JLabel();
        jButton1AccountSearch = new javax.swing.JButton();
        账号操作 = new javax.swing.JTextField();
        角色信息1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        角色信息 = new javax.swing.JTable();
        刷新角色信息 = new javax.swing.JButton();
        显示管理角色 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        删除角色 = new javax.swing.JButton();
        角色昵称 = new javax.swing.JTextField();
        等级 = new javax.swing.JTextField();
        力量 = new javax.swing.JTextField();
        敏捷 = new javax.swing.JTextField();
        智力 = new javax.swing.JTextField();
        运气 = new javax.swing.JTextField();
        HP = new javax.swing.JTextField();
        MP = new javax.swing.JTextField();
        金币1 = new javax.swing.JTextField();
        地图 = new javax.swing.JTextField();
        GM = new javax.swing.JTextField();
        jLabel182 = new javax.swing.JLabel();
        jLabel183 = new javax.swing.JLabel();
        jLabel184 = new javax.swing.JLabel();
        jLabel185 = new javax.swing.JLabel();
        jLabel186 = new javax.swing.JLabel();
        jLabel187 = new javax.swing.JLabel();
        jLabel189 = new javax.swing.JLabel();
        jLabel190 = new javax.swing.JLabel();
        jLabel191 = new javax.swing.JLabel();
        jLabel192 = new javax.swing.JLabel();
        jLabel193 = new javax.swing.JLabel();
        角色ID = new javax.swing.JTextField();
        卡号自救1 = new javax.swing.JButton();
        卡号自救2 = new javax.swing.JButton();
        jLabel203 = new javax.swing.JLabel();
        查看技能 = new javax.swing.JButton();
        查看背包 = new javax.swing.JButton();
        卡家族解救 = new javax.swing.JButton();
        脸型 = new javax.swing.JTextField();
        发型 = new javax.swing.JTextField();
        jLabel214 = new javax.swing.JLabel();
        离线角色 = new javax.swing.JButton();
        在线角色 = new javax.swing.JButton();
        显示在线玩家 = new javax.swing.JLabel();
        roleNameEdit = new javax.swing.JTextField();
        jButtonAccountSearch = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        角色背包 = new javax.swing.JPanel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel39 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        角色背包穿戴 = new javax.swing.JTable();
        背包物品名字1 = new javax.swing.JTextField();
        身上穿戴序号1 = new javax.swing.JTextField();
        背包物品代码1 = new javax.swing.JTextField();
        jLabel276 = new javax.swing.JLabel();
        jLabel283 = new javax.swing.JLabel();
        jLabel287 = new javax.swing.JLabel();
        删除穿戴装备 = new javax.swing.JButton();
        jPanel40 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        角色装备背包 = new javax.swing.JTable();
        装备背包物品名字 = new javax.swing.JTextField();
        装备背包物品序号 = new javax.swing.JTextField();
        装备背包物品代码 = new javax.swing.JTextField();
        jLabel288 = new javax.swing.JLabel();
        jLabel289 = new javax.swing.JLabel();
        jLabel290 = new javax.swing.JLabel();
        删除装备背包 = new javax.swing.JButton();
        jPanel41 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        角色消耗背包 = new javax.swing.JTable();
        消耗背包物品名字 = new javax.swing.JTextField();
        消耗背包物品序号 = new javax.swing.JTextField();
        消耗背包物品代码 = new javax.swing.JTextField();
        jLabel291 = new javax.swing.JLabel();
        jLabel292 = new javax.swing.JLabel();
        jLabel293 = new javax.swing.JLabel();
        删除消耗背包 = new javax.swing.JButton();
        jPanel42 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        角色设置背包 = new javax.swing.JTable();
        设置背包物品名字 = new javax.swing.JTextField();
        设置背包物品序号 = new javax.swing.JTextField();
        设置背包物品代码 = new javax.swing.JTextField();
        jLabel294 = new javax.swing.JLabel();
        jLabel295 = new javax.swing.JLabel();
        jLabel296 = new javax.swing.JLabel();
        删除设置背包 = new javax.swing.JButton();
        jPanel43 = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        角色其他背包 = new javax.swing.JTable();
        其他背包物品名字 = new javax.swing.JTextField();
        其他背包物品序号 = new javax.swing.JTextField();
        其他背包物品代码 = new javax.swing.JTextField();
        jLabel297 = new javax.swing.JLabel();
        jLabel298 = new javax.swing.JLabel();
        jLabel299 = new javax.swing.JLabel();
        删除其他背包 = new javax.swing.JButton();
        jPanel44 = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        角色特殊背包 = new javax.swing.JTable();
        特殊背包物品名字 = new javax.swing.JTextField();
        特殊背包物品序号 = new javax.swing.JTextField();
        特殊背包物品代码 = new javax.swing.JTextField();
        jLabel300 = new javax.swing.JLabel();
        jLabel301 = new javax.swing.JLabel();
        jLabel302 = new javax.swing.JLabel();
        删除特殊背包 = new javax.swing.JButton();
        jPanel45 = new javax.swing.JPanel();
        jScrollPane21 = new javax.swing.JScrollPane();
        角色游戏仓库 = new javax.swing.JTable();
        游戏仓库物品名字 = new javax.swing.JTextField();
        游戏仓库物品序号 = new javax.swing.JTextField();
        游戏仓库物品代码 = new javax.swing.JTextField();
        jLabel303 = new javax.swing.JLabel();
        jLabel304 = new javax.swing.JLabel();
        jLabel305 = new javax.swing.JLabel();
        删除游戏仓库 = new javax.swing.JButton();
        jPanel46 = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        角色商城仓库 = new javax.swing.JTable();
        商城仓库物品名字 = new javax.swing.JTextField();
        商城仓库物品序号 = new javax.swing.JTextField();
        商城仓库物品代码 = new javax.swing.JTextField();
        jLabel306 = new javax.swing.JLabel();
        jLabel307 = new javax.swing.JLabel();
        jLabel308 = new javax.swing.JLabel();
        删除商城仓库 = new javax.swing.JButton();
        jPanel48 = new javax.swing.JPanel();
        jScrollPane30 = new javax.swing.JScrollPane();
        角色点券拍卖行 = new javax.swing.JTable();
        拍卖行物品名字1 = new javax.swing.JTextField();
        角色点券拍卖行序号 = new javax.swing.JTextField();
        拍卖行物品代码1 = new javax.swing.JTextField();
        jLabel354 = new javax.swing.JLabel();
        jLabel355 = new javax.swing.JLabel();
        jLabel356 = new javax.swing.JLabel();
        删除拍卖行1 = new javax.swing.JButton();
        jPanel47 = new javax.swing.JPanel();
        jScrollPane23 = new javax.swing.JScrollPane();
        角色金币拍卖行 = new javax.swing.JTable();
        拍卖行物品名字 = new javax.swing.JTextField();
        角色金币拍卖行序号 = new javax.swing.JTextField();
        拍卖行物品代码 = new javax.swing.JTextField();
        jLabel309 = new javax.swing.JLabel();
        jLabel310 = new javax.swing.JLabel();
        jLabel311 = new javax.swing.JLabel();
        删除拍卖行 = new javax.swing.JButton();
        技能 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        技能信息 = new javax.swing.JTable();
        技能代码 = new javax.swing.JTextField();
        技能目前等级 = new javax.swing.JTextField();
        技能最高等级 = new javax.swing.JTextField();
        技能名字 = new javax.swing.JTextField();
        jLabel86 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        修改技能 = new javax.swing.JButton();
        技能序号 = new javax.swing.JTextField();
        jLabel188 = new javax.swing.JLabel();
        jLabel204 = new javax.swing.JLabel();
        jLabel205 = new javax.swing.JLabel();
        删除技能 = new javax.swing.JButton();
        修改技能1 = new javax.swing.JButton();
        jPanel50 = new javax.swing.JPanel();
        jScrollPane24 = new javax.swing.JScrollPane();
        家族信息 = new javax.swing.JTable();
        刷新家族信息 = new javax.swing.JButton();
        jLabel194 = new javax.swing.JLabel();
        家族ID = new javax.swing.JTextField();
        家族名称 = new javax.swing.JTextField();
        jLabel195 = new javax.swing.JLabel();
        家族族长 = new javax.swing.JTextField();
        jLabel196 = new javax.swing.JLabel();
        jLabel198 = new javax.swing.JLabel();
        家族成员2 = new javax.swing.JTextField();
        jLabel199 = new javax.swing.JLabel();
        家族成员3 = new javax.swing.JTextField();
        jLabel200 = new javax.swing.JLabel();
        家族成员4 = new javax.swing.JTextField();
        jLabel313 = new javax.swing.JLabel();
        家族成员5 = new javax.swing.JTextField();
        jLabel314 = new javax.swing.JLabel();
        家族GP = new javax.swing.JTextField();
        jButton34 = new javax.swing.JButton();
        jPanel65 = new javax.swing.JPanel();
        jScrollPane107 = new javax.swing.JScrollPane();
        封IP = new javax.swing.JTable();
        jScrollPane108 = new javax.swing.JScrollPane();
        封MAC = new javax.swing.JTable();
        刷新封IP = new javax.swing.JButton();
        刷新封MAC = new javax.swing.JButton();
        删除IP代码 = new javax.swing.JTextField();
        删除MAC = new javax.swing.JButton();
        删除IP = new javax.swing.JButton();
        删MAC代码 = new javax.swing.JTextField();
        jLabel346 = new javax.swing.JLabel();
        jLabel347 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel25 = new javax.swing.JPanel();
        帽子 = new javax.swing.JButton();
        脸饰 = new javax.swing.JButton();
        眼饰 = new javax.swing.JButton();
        长袍 = new javax.swing.JButton();
        上衣 = new javax.swing.JButton();
        裙裤 = new javax.swing.JButton();
        鞋子 = new javax.swing.JButton();
        手套 = new javax.swing.JButton();
        武器 = new javax.swing.JButton();
        戒指 = new javax.swing.JButton();
        飞镖 = new javax.swing.JButton();
        披风 = new javax.swing.JButton();
        骑宠 = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        喜庆物品 = new javax.swing.JButton();
        通讯物品 = new javax.swing.JButton();
        卷轴 = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        会员卡 = new javax.swing.JButton();
        表情 = new javax.swing.JButton();
        个人商店 = new javax.swing.JButton();
        纪念日 = new javax.swing.JButton();
        游戏 = new javax.swing.JButton();
        效果 = new javax.swing.JButton();
        jPanel31 = new javax.swing.JPanel();
        宠物 = new javax.swing.JButton();
        宠物服饰 = new javax.swing.JButton();
        其他 = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        主题馆 = new javax.swing.JButton();
        读取热销产品 = new javax.swing.JButton();
        活动 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        charTable = new javax.swing.JTable();
        jPanel33 = new javax.swing.JPanel();
        商品数量 = new javax.swing.JTextField();
        商品编码 = new javax.swing.JTextField();
        商品代码 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        商品价格 = new javax.swing.JTextField();
        商品时间 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        商品库存 = new javax.swing.JTextField();
        商品折扣 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabelUseNX = new javax.swing.JLabel();
        每日限购 = new javax.swing.JTextField();
        货币类型 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        点券购买 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jPanel67 = new javax.swing.JPanel();
        jButton27 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        修改 = new javax.swing.JButton();
        添加 = new javax.swing.JButton();
        jScrollPane132 = new javax.swing.JScrollPane();
        商城扩充价格 = new javax.swing.JTable();
        商城扩充价格修改 = new javax.swing.JTextField();
        修改背包扩充价格 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        显示类型 = new javax.swing.JTextField();
        商品出售状态 = new javax.swing.JTextField();
        jButton19 = new javax.swing.JButton();
        jTextField17 = new javax.swing.JTextField();
        jButton20 = new javax.swing.JButton();
        jPanel35 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jScrollPane25 = new javax.swing.JScrollPane();
        游戏商店2 = new javax.swing.JTable();
        jPanel56 = new javax.swing.JPanel();
        删除商品 = new javax.swing.JButton();
        新增商品 = new javax.swing.JButton();
        商品序号 = new javax.swing.JTextField();
        商店代码 = new javax.swing.JTextField();
        商品物品代码 = new javax.swing.JTextField();
        商品售价金币 = new javax.swing.JTextField();
        jLabel268 = new javax.swing.JLabel();
        jLabel269 = new javax.swing.JLabel();
        jLabel271 = new javax.swing.JLabel();
        jLabel272 = new javax.swing.JLabel();
        修改商品 = new javax.swing.JButton();
        商品名称 = new javax.swing.JTextField();
        jLabel273 = new javax.swing.JLabel();
        jPanel55 = new javax.swing.JPanel();
        查询商店2 = new javax.swing.JButton();
        查询商店 = new javax.swing.JTextField();
        jLabel270 = new javax.swing.JLabel();
        jButton33 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        sendNotice = new javax.swing.JButton();
        sendWinNotice = new javax.swing.JButton();
        sendMsgNotice = new javax.swing.JButton();
        sendNpcTalkNotice = new javax.swing.JButton();
        noticeText = new javax.swing.JTextField();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        公告发布喇叭代码 = new javax.swing.JTextField();
        jButton45 = new javax.swing.JButton();
        jLabel259 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel68 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jPanel71 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jTextField9 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jTextField12 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jButton14 = new javax.swing.JButton();
        jPanel73 = new javax.swing.JPanel();
        jButton15 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jTextField16 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jButton18 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jTabbedPane2.setBackground(new java.awt.Color(248, 248, 248));
        jTabbedPane2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane2.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel56.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        jLabel57.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        jLabel58.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        jLabel59.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        jTabbedPaneBackendCSPoints.setBackground(new java.awt.Color(250, 250, 250));
        jTabbedPaneBackendCSPoints.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N

        jPanel12.setBackground(new java.awt.Color(250, 250, 250));

        jPanel72.setBackground(new java.awt.Color(248, 248, 248));
        jPanel72.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        禁止登陆开关.setBackground(new java.awt.Color(0, 204, 255));
        禁止登陆开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        禁止登陆开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        禁止登陆开关.setText("游戏登陆");
        禁止登陆开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于限制玩家登陆游戏<br> <br> <br> ");
        禁止登陆开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                禁止登陆开关ActionPerformed(evt);
            }
        });

        滚动公告开关.setBackground(new java.awt.Color(0, 204, 255));
        滚动公告开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        滚动公告开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/图片/pp/OFF3.png"))); // NOI18N
        滚动公告开关.setText("滚动公告");
        滚动公告开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏顶部滚动公告<br> <br> <br> ");
        滚动公告开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                滚动公告开关ActionPerformed(evt);
            }
        });

        玩家聊天开关.setBackground(new java.awt.Color(0, 204, 255));
        玩家聊天开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        玩家聊天开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        玩家聊天开关.setText("玩家聊天");
        玩家聊天开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏内玩家是否可以聊天说话<br> <br> <br> ");
        玩家聊天开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                玩家聊天开关ActionPerformed(evt);
            }
        });

        游戏升级快讯.setBackground(new java.awt.Color(0, 153, 255));
        游戏升级快讯.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        游戏升级快讯.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/图片/pp/OFF3.png"))); // NOI18N
        游戏升级快讯.setText("升级快讯");
        游戏升级快讯.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制玩家升级了刷公告庆祝<br> <br> <br> ");
        游戏升级快讯.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                游戏升级快讯ActionPerformed(evt);
            }
        });

        丢出金币开关.setBackground(new java.awt.Color(0, 204, 255));
        丢出金币开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        丢出金币开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/图片/pp/OFF3.png"))); // NOI18N
        丢出金币开关.setText("丢出金币");
        丢出金币开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制玩家游戏内是否可以丢金币<br> <br> <br> ");
        丢出金币开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                丢出金币开关ActionPerformed(evt);
            }
        });

        丢出物品开关.setBackground(new java.awt.Color(0, 204, 255));
        丢出物品开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        丢出物品开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/图片/pp/OFF3.png"))); // NOI18N
        丢出物品开关.setText("丢出物品");
        丢出物品开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏内玩家是否可以丢物品<br> <br> <br> ");
        丢出物品开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                丢出物品开关ActionPerformed(evt);
            }
        });

        游戏指令开关.setBackground(new java.awt.Color(0, 204, 255));
        游戏指令开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        游戏指令开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        游戏指令开关.setText("管理指令");
        游戏指令开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制GM号是否可以用GM命令<br> <br> <br> ");
        游戏指令开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                游戏指令开关ActionPerformed(evt);
            }
        });

        上线提醒开关.setBackground(new java.awt.Color(0, 204, 255));
        上线提醒开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        上线提醒开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        上线提醒开关.setText("登录公告");
        上线提醒开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>玩家上线是否提示欢迎公告<br> <br>");
        上线提醒开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                上线提醒开关ActionPerformed(evt);
            }
        });

        管理隐身开关.setBackground(new java.awt.Color(0, 204, 255));
        管理隐身开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        管理隐身开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        管理隐身开关.setText("管理隐身");
        管理隐身开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于管理员号上线默认是否开启隐身BUFF<br> <br> <br> ");
        管理隐身开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                管理隐身开关ActionPerformed(evt);
            }
        });

        管理加速开关.setBackground(new java.awt.Color(0, 204, 255));
        管理加速开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        管理加速开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        管理加速开关.setText("管理加速");
        管理加速开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于管理员号上线默认是否开启轻功BUFF<br> <br> <br> ");
        管理加速开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                管理加速开关ActionPerformed(evt);
            }
        });

        游戏喇叭开关.setBackground(new java.awt.Color(0, 153, 255));
        游戏喇叭开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        游戏喇叭开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        游戏喇叭开关.setText("游戏喇叭");
        游戏喇叭开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制是否让玩家使用游戏喇叭功能<br> <br> <br> ");
        游戏喇叭开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                游戏喇叭开关ActionPerformed(evt);
            }
        });

        玩家交易开关.setBackground(new java.awt.Color(0, 204, 255));
        玩家交易开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        玩家交易开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        玩家交易开关.setText("玩家交易");
        玩家交易开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于限制游戏内玩家交易功能<br> <br>");
        玩家交易开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                玩家交易开关ActionPerformed(evt);
            }
        });

        雇佣商人开关.setBackground(new java.awt.Color(0, 153, 255));
        雇佣商人开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        雇佣商人开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        雇佣商人开关.setText("雇佣商人");
        雇佣商人开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>是否允许玩家在自由摆摊<br> <br>");
        雇佣商人开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                雇佣商人开关ActionPerformed(evt);
            }
        });

        欢迎弹窗开关.setBackground(new java.awt.Color(0, 204, 255));
        欢迎弹窗开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        欢迎弹窗开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        欢迎弹窗开关.setText("欢迎弹窗");
        欢迎弹窗开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>进入游戏是否弹出欢迎公告<br> <br>");
        欢迎弹窗开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                欢迎弹窗开关ActionPerformed(evt);
            }
        });

        登陆帮助开关.setBackground(new java.awt.Color(0, 204, 255));
        登陆帮助开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        登陆帮助开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        登陆帮助开关.setText("登陆帮助");
        登陆帮助开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>进游戏是否提示登录帮助<br> <br>");
        登陆帮助开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                登陆帮助开关ActionPerformed(evt);
            }
        });

        越级打怪开关.setBackground(new java.awt.Color(0, 204, 255));
        越级打怪开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        越级打怪开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        越级打怪开关.setText("越级打怪");
        越级打怪开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>超越本身等级打高级怪物不MISS<br> <br>");
        越级打怪开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                越级打怪开关ActionPerformed(evt);
            }
        });

        怪物状态开关.setBackground(new java.awt.Color(0, 204, 255));
        怪物状态开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        怪物状态开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        怪物状态开关.setText("怪物状态");
        怪物状态开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于游戏内怪物状态释放技能是否提示<br> <br>");
        怪物状态开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                怪物状态开关ActionPerformed(evt);
            }
        });

        地图名称开关.setBackground(new java.awt.Color(0, 204, 255));
        地图名称开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        地图名称开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        地图名称开关.setText("地图名称");
        地图名称开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>过地图是否提示地图名称<br> <br>");
        地图名称开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                地图名称开关ActionPerformed(evt);
            }
        });

        过图存档开关.setBackground(new java.awt.Color(0, 204, 255));
        过图存档开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        过图存档开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        过图存档开关.setText("过图存档");
        过图存档开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>是否开启 玩家每过一张图保存当前玩家数据<br> <br>");
        过图存档开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                过图存档开关ActionPerformed(evt);
            }
        });

        指令通知开关.setBackground(new java.awt.Color(0, 204, 255));
        指令通知开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        指令通知开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        指令通知开关.setText("指令通知");
        指令通知开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>角色上线是否提示命令代码<br> <br> <br> ");
        指令通知开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                指令通知开关ActionPerformed(evt);
            }
        });

        吸怪检测开关.setBackground(new java.awt.Color(0, 204, 255));
        吸怪检测开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        吸怪检测开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        吸怪检测开关.setText("吸怪检测");
        吸怪检测开关.setToolTipText("");
        吸怪检测开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                吸怪检测开关ActionPerformed(evt);
            }
        });

        jTextField2.setBackground(new java.awt.Color(204, 204, 255));
        jTextField2.setFont(new java.awt.Font("微软雅黑", 1, 14)); // NOI18N
        jTextField2.setText("输入公告内容");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(51, 102, 255));
        jButton13.setFont(new java.awt.Font("微软雅黑", 1, 14)); // NOI18N
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/2630205.png"))); // NOI18N
        jButton13.setText("发送屏幕中央公告");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        屠令广播开关.setBackground(new java.awt.Color(0, 204, 255));
        屠令广播开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        屠令广播开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/图片/pp/OFF3.png"))); // NOI18N
        屠令广播开关.setText("屠令广播");
        屠令广播开关.setToolTipText("");
        屠令广播开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                屠令广播开关ActionPerformed(evt);
            }
        });

        回收地图开关.setBackground(new java.awt.Color(0, 204, 255));
        回收地图开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        回收地图开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF3.png"))); // NOI18N
        回收地图开关.setText("回收地图");
        回收地图开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于游戏地图回收开关<br> <br> <br> ");
        回收地图开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                回收地图开关ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel72Layout = new javax.swing.GroupLayout(jPanel72);
        jPanel72.setLayout(jPanel72Layout);
        jPanel72Layout.setHorizontalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel72Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField2)
                    .addGroup(jPanel72Layout.createSequentialGroup()
                        .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(地图名称开关, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(玩家交易开关, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(禁止登陆开关, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(上线提醒开关, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(玩家聊天开关)
                            .addComponent(欢迎弹窗开关)
                            .addComponent(过图存档开关)
                            .addComponent(指令通知开关))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(游戏升级快讯)
                            .addComponent(吸怪检测开关)
                            .addComponent(雇佣商人开关)
                            .addComponent(回收地图开关))
                        .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel72Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel72Layout.createSequentialGroup()
                                        .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(登陆帮助开关)
                                            .addComponent(游戏喇叭开关))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(滚动公告开关)
                                            .addComponent(越级打怪开关)))
                                    .addGroup(jPanel72Layout.createSequentialGroup()
                                        .addComponent(屠令广播开关)
                                        .addGap(18, 18, 18)
                                        .addComponent(丢出金币开关))))
                            .addGroup(jPanel72Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(管理隐身开关)
                                .addGap(18, 18, 18)
                                .addComponent(游戏指令开关, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel72Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(怪物状态开关)
                                    .addComponent(丢出物品开关)
                                    .addComponent(管理加速开关)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel72Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(52, 52, 52))
        );
        jPanel72Layout.setVerticalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel72Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(禁止登陆开关, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(玩家聊天开关, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(游戏升级快讯, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(屠令广播开关, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(丢出金币开关, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(丢出物品开关, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(玩家交易开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(欢迎弹窗开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(游戏喇叭开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(滚动公告开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(怪物状态开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(回收地图开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(地图名称开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(过图存档开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(登陆帮助开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(越级打怪开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(管理加速开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(吸怪检测开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(上线提醒开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(指令通知开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(雇佣商人开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(管理隐身开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(游戏指令开关, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        输出窗口.setEditable(false);
        输出窗口.setColumns(20);
        输出窗口.setFont(new java.awt.Font("宋体", 1, 15)); // NOI18N
        输出窗口.setRows(5);
        输出窗口.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        输出窗口.setFocusTraversalPolicyProvider(true);
        输出窗口.setInheritsPopupMenu(true);
        输出窗口.setSelectedTextColor(new java.awt.Color(51, 0, 51));
        jScrollPane2.setViewportView(输出窗口);

        jButton16.setBackground(new java.awt.Color(0, 0, 204));
        jButton16.setFont(new java.awt.Font("微软雅黑", 1, 12)); // NOI18N
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/图片/pp/关机.png"))); // NOI18N
        jButton16.setText("关闭服务端");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jTextField22.setForeground(new java.awt.Color(255, 51, 51));
        jTextField22.setText("5");
        jTextField22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField22ActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel29.setText(" 分钟");

        startserverbutton.setBackground(new java.awt.Color(51, 51, 255));
        startserverbutton.setFont(new java.awt.Font("微软雅黑", 1, 12)); // NOI18N
        startserverbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/图片/pp/开机.png"))); // NOI18N
        startserverbutton.setText("启动服务端");
        startserverbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startserverbuttonActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel28.setText("游戏已开放：");

        jLabel25.setForeground(new java.awt.Color(255, 51, 0));
        jLabel25.setText("读取中");

        startserverbutton1.setBackground(new java.awt.Color(51, 51, 255));
        startserverbutton1.setFont(new java.awt.Font("微软雅黑", 1, 12)); // NOI18N
        startserverbutton1.setText("修改启动码");
        startserverbutton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startserverbutton1ActionPerformed(evt);
            }
        });

        jTextField1.setText("1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(startserverbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addGap(6, 6, 6)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(startserverbutton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton16)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel72, javax.swing.GroupLayout.PREFERRED_SIZE, 1195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jPanel72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startserverbutton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startserverbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        jTabbedPaneBackendCSPoints.addTab("综合开关", jPanel12);

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));

        jTableSN.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "类型", "数值", "卡号", "是否使用", "绑定账号", "使用角色", "使用时间", "是否禁用", "生成时间"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableSN.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(jTableSN);
        jTableSN.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jTableSN.getColumnModel().getColumnCount() > 0) {
            jTableSN.getColumnModel().getColumn(0).setPreferredWidth(8);
            jTableSN.getColumnModel().getColumn(1).setPreferredWidth(8);
            jTableSN.getColumnModel().getColumn(2).setPreferredWidth(8);
            jTableSN.getColumnModel().getColumn(3).setPreferredWidth(224);
            jTableSN.getColumnModel().getColumn(4).setPreferredWidth(8);
            jTableSN.getColumnModel().getColumn(5).setPreferredWidth(32);
            jTableSN.getColumnModel().getColumn(6).setPreferredWidth(32);
            jTableSN.getColumnModel().getColumn(7).setPreferredWidth(100);
            jTableSN.getColumnModel().getColumn(8).setPreferredWidth(8);
            jTableSN.getColumnModel().getColumn(9).setPreferredWidth(100);
        }

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel14.setToolTipText("增删改查");

        jLabel12.setText("序  号：");

        jTextFieldNum.setEditable(false);

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("类  型：");
        jLabel8.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jLabel9.setText("数  值：");

        jLabel13.setText("卡  号：");

        jLabel10.setText("绑定账号：");

        jTextFieldAccount.setEditable(false);
        jTextFieldAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAccountActionPerformed(evt);
            }
        });

        jButtonSNSearchByAccount.setText("账号查找");
        jButtonSNSearchByAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSNSearchByAccountActionPerformed(evt);
            }
        });

        jButtonLock.setText("禁用");
        jButtonLock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLockActionPerformed(evt);
            }
        });

        jButtonSNModify.setText("修改");
        jButtonSNModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSNModifyActionPerformed(evt);
            }
        });

        jTextFieldType.setEditable(false);

        jButtonSNDelete.setText("删除");
        jButtonSNDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSNDeleteActionPerformed(evt);
            }
        });

        jButtonSNSearch.setText("卡号查找");
        jButtonSNSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSNSearchActionPerformed(evt);
            }
        });

        jButtonUnlock.setText("解禁");
        jButtonUnlock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUnlockActionPerformed(evt);
            }
        });

        jButtonUsed.setText("已使用");
        jButtonUsed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUsedActionPerformed(evt);
            }
        });

        jButtonUnused.setText("未使用");
        jButtonUnused.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUnusedActionPerformed(evt);
            }
        });

        jButtonOderByUseTime.setText("数值大小排序");
        jButtonOderByUseTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOderByUseTimeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonUsed, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextFieldNum, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButtonSNModify, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonSNDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonUnused, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(jTextFieldType)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9)
                            .addComponent(jTextFieldCSPointsValue, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .addComponent(jButtonUnlock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jButtonLock, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonSNSearch)
                                .addGap(4, 4, 4))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jTextFieldSN, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)))
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jTextFieldAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jButtonSNSearchByAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jButtonOderByUseTime)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel13)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCSPointsValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldSN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonLock)
                    .addComponent(jButtonSNModify)
                    .addComponent(jButtonSNSearchByAccount)
                    .addComponent(jButtonSNDelete)
                    .addComponent(jButtonSNSearch)
                    .addComponent(jButtonUnlock))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonUsed)
                    .addComponent(jButtonUnused)
                    .addComponent(jButtonOderByUseTime))
                .addContainerGap())
        );

        jLabel14.setText("数量：");

        jLabel15.setText("类型：");

        jComboBoxMoneyb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "点券", "抵用券", "元宝" }));

        jButtonSNProduction.setText("生成");
        jButtonSNProduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSNProductionActionPerformed(evt);
            }
        });

        jButtonSNRefresh.setText("查看所有");
        jButtonSNRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSNRefreshActionPerformed(evt);
            }
        });

        jButton12.setText("导出");

        jLabel17.setText("面值：");

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel53Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSNValue))
                    .addGroup(jPanel53Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxMoneyb, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel53Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldProductionNum, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonSNRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(jButtonSNProduction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldProductionNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSNRefresh))
                .addGap(18, 18, 18)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jComboBoxMoneyb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSNProduction))
                .addGap(18, 18, 18)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jTextFieldSNValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneBackendCSPoints.addTab("后台充值", jPanel1);

        jPanel93.setBackground(new java.awt.Color(250, 250, 250));
        jPanel93.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "游戏经验加成", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel93.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        经验加成表.setFont(new java.awt.Font("幼圆", 0, 20)); // NOI18N
        经验加成表.setForeground(new java.awt.Color(102, 102, 255));
        经验加成表.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "类型", "数值"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        经验加成表.getTableHeader().setReorderingAllowed(false);
        jScrollPane136.setViewportView(经验加成表);

        jPanel93.add(jScrollPane136, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 630, 390));

        经验加成表序号.setEditable(false);
        jPanel93.add(经验加成表序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 500, 70, 30));

        经验加成表类型.setEditable(false);
        jPanel93.add(经验加成表类型, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 500, 230, 30));
        jPanel93.add(经验加成表数值, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 500, 100, 30));

        经验加成表修改.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        经验加成表修改.setText("修改");
        经验加成表修改.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                经验加成表修改ActionPerformed(evt);
            }
        });
        jPanel93.add(经验加成表修改, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 500, 100, 30));

        jLabel384.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel384.setText("数值；");
        jPanel93.add(jLabel384, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 480, -1, -1));

        jLabel385.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel385.setText("类型；");
        jPanel93.add(jLabel385, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 480, -1, -1));

        jLabel386.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel386.setText("序号；");
        jPanel93.add(jLabel386, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 480, -1, -1));

        游戏经验加成说明.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        游戏经验加成说明.setText("说明");
        游戏经验加成说明.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                游戏经验加成说明ActionPerformed(evt);
            }
        });
        jPanel93.add(游戏经验加成说明, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 500, 100, 30));

        jTabbedPaneBackendCSPoints.addTab("经验加成", jPanel93);

        jPanel66.setBackground(new java.awt.Color(250, 250, 250));
        jPanel66.setBorder(javax.swing.BorderFactory.createTitledBorder("活动经验"));

        jPanel69.setBackground(new java.awt.Color(250, 250, 250));
        jPanel69.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "2倍率活动", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel69.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        开启双倍经验.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        开启双倍经验.setText("开启双倍经验");
        开启双倍经验.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启双倍经验ActionPerformed(evt);
            }
        });
        jPanel69.add(开启双倍经验, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 140, 40));
        jPanel69.add(双倍经验持续时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 120, -1));

        jLabel359.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel359.setText("持续时间/h");
        jPanel69.add(jLabel359, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, -1, 20));

        开启双倍爆率.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        开启双倍爆率.setText("开启双倍爆率");
        开启双倍爆率.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启双倍爆率ActionPerformed(evt);
            }
        });
        jPanel69.add(开启双倍爆率, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, 140, 40));
        jPanel69.add(双倍爆率持续时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 120, -1));

        jLabel360.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel360.setText("持续时间/h");
        jPanel69.add(jLabel360, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, -1, 20));

        开启双倍金币.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        开启双倍金币.setText("开启双倍金币");
        开启双倍金币.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启双倍金币ActionPerformed(evt);
            }
        });
        jPanel69.add(开启双倍金币, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, 140, 40));
        jPanel69.add(双倍金币持续时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 120, -1));

        jLabel361.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel361.setText("持续时间/h");
        jPanel69.add(jLabel361, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, -1, 20));

        jPanel70.setBackground(new java.awt.Color(250, 250, 250));
        jPanel70.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "3倍率活动", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel70.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        开启三倍经验.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        开启三倍经验.setText("开启三倍经验");
        开启三倍经验.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启三倍经验ActionPerformed(evt);
            }
        });
        jPanel70.add(开启三倍经验, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 140, 40));
        jPanel70.add(三倍经验持续时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 120, -1));

        jLabel362.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel362.setText("持续时间/h");
        jPanel70.add(jLabel362, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, 20));

        开启三倍爆率.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        开启三倍爆率.setText("开启三倍爆率");
        开启三倍爆率.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启三倍爆率ActionPerformed(evt);
            }
        });
        jPanel70.add(开启三倍爆率, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, 140, 40));
        jPanel70.add(三倍爆率持续时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 120, -1));

        jLabel348.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel348.setText("持续时间/h");
        jPanel70.add(jLabel348, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, -1, 20));

        开启三倍金币.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        开启三倍金币.setText("开启三倍金币");
        开启三倍金币.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启三倍金币ActionPerformed(evt);
            }
        });
        jPanel70.add(开启三倍金币, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, 140, 40));
        jPanel70.add(三倍金币持续时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 120, -1));

        jLabel349.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel349.setText("持续时间/h");
        jPanel70.add(jLabel349, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, -1, 20));

        jLabel3.setFont(new java.awt.Font("宋体", 1, 24)); // NOI18N
        jLabel3.setText("功能说明：本功能无需重启服务端立即生效");

        jLabel4.setFont(new java.awt.Font("宋体", 1, 24)); // NOI18N
        jLabel4.setText("单位换算 h=小时 时间到期自动解除倍率");

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel66Layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel66Layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(jLabel4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel70, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel69, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel70, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneBackendCSPoints.addTab("活动经验", jPanel66);

        jPanel52.setBackground(new java.awt.Color(250, 250, 250));

        游戏广播.setBackground(new java.awt.Color(250, 250, 250));
        游戏广播.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        广播信息.setBackground(new java.awt.Color(153, 153, 153));
        广播信息.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        广播信息.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "序号", "5分钟一次随机广播内容"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(广播信息);
        if (广播信息.getColumnModel().getColumnCount() > 0) {
            广播信息.getColumnModel().getColumn(0).setMinWidth(80);
            广播信息.getColumnModel().getColumn(0).setPreferredWidth(80);
            广播信息.getColumnModel().getColumn(0).setMaxWidth(80);
        }

        游戏广播.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 800, 440));

        刷新广告.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        刷新广告.setText("刷新广播");
        刷新广告.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新广告ActionPerformed(evt);
            }
        });

        删除广播.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除广播.setText("删除广播");
        删除广播.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除广播ActionPerformed(evt);
            }
        });

        发布广告.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        发布广告.setText("新增广播");
        发布广告.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                发布广告ActionPerformed(evt);
            }
        });

        修改广播.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        修改广播.setText("修改广播");
        修改广播.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改广播ActionPerformed(evt);
            }
        });

        广播序号.setEditable(false);
        广播序号.setFont(new java.awt.Font("幼圆", 1, 18)); // NOI18N

        广播文本.setFont(new java.awt.Font("幼圆", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(游戏广播, javax.swing.GroupLayout.DEFAULT_SIZE, 1219, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel52Layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(刷新广告, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(删除广播, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(发布广告, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(修改广播, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(广播序号, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(广播文本, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addComponent(游戏广播, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(广播文本, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(广播序号, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(刷新广告)
                    .addComponent(删除广播)
                    .addComponent(发布广告)
                    .addComponent(修改广播))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPaneBackendCSPoints.addTab("游戏广播", jPanel52);

        jPanel51.setBackground(new java.awt.Color(250, 250, 250));

        jPanel74.setBackground(new java.awt.Color(250, 250, 250));
        jPanel74.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "风之大陆", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 1, 12))); // NOI18N
        jPanel74.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        蓝蜗牛开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        蓝蜗牛开关.setText("蓝蜗牛");
        蓝蜗牛开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                蓝蜗牛开关ActionPerformed(evt);
            }
        });
        jPanel74.add(蓝蜗牛开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 180, 40));

        蘑菇仔开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        蘑菇仔开关.setText("蘑菇仔");
        蘑菇仔开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                蘑菇仔开关ActionPerformed(evt);
            }
        });
        jPanel74.add(蘑菇仔开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 170, 40));

        绿水灵开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        绿水灵开关.setText("绿水灵");
        绿水灵开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                绿水灵开关ActionPerformed(evt);
            }
        });
        jPanel74.add(绿水灵开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, 170, 40));

        漂漂猪开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        漂漂猪开关.setText("漂漂猪");
        漂漂猪开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                漂漂猪开关ActionPerformed(evt);
            }
        });
        jPanel74.add(漂漂猪开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 80, 170, 40));

        小青蛇开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        小青蛇开关.setText("小青蛇");
        小青蛇开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                小青蛇开关ActionPerformed(evt);
            }
        });
        jPanel74.add(小青蛇开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 80, 170, 40));

        红螃蟹开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        红螃蟹开关.setText("红螃蟹");
        红螃蟹开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                红螃蟹开关ActionPerformed(evt);
            }
        });
        jPanel74.add(红螃蟹开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 80, 170, 40));

        大海龟开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        大海龟开关.setText("大海龟");
        大海龟开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                大海龟开关ActionPerformed(evt);
            }
        });
        jPanel74.add(大海龟开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 180, 40));

        章鱼怪开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        章鱼怪开关.setText("章鱼怪");
        章鱼怪开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                章鱼怪开关ActionPerformed(evt);
            }
        });
        jPanel74.add(章鱼怪开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 170, 40));

        顽皮猴开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        顽皮猴开关.setText("顽皮猴");
        顽皮猴开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                顽皮猴开关ActionPerformed(evt);
            }
        });
        jPanel74.add(顽皮猴开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, 170, 40));

        星精灵开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        星精灵开关.setText("星精灵");
        星精灵开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                星精灵开关ActionPerformed(evt);
            }
        });
        jPanel74.add(星精灵开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 170, 40));

        胖企鹅开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        胖企鹅开关.setText("胖企鹅");
        胖企鹅开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                胖企鹅开关ActionPerformed(evt);
            }
        });
        jPanel74.add(胖企鹅开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 20, 170, 40));

        白雪人开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        白雪人开关.setText("白雪人");
        白雪人开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                白雪人开关ActionPerformed(evt);
            }
        });
        jPanel74.add(白雪人开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 20, 180, 40));

        石头人开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        石头人开关.setText("石头人");
        石头人开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                石头人开关ActionPerformed(evt);
            }
        });
        jPanel74.add(石头人开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 80, 180, 40));

        紫色猫开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        紫色猫开关.setText("紫色猫");
        紫色猫开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                紫色猫开关ActionPerformed(evt);
            }
        });
        jPanel74.add(紫色猫开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 180, 40));

        大灰狼开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        大灰狼开关.setText("大灰狼");
        大灰狼开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                大灰狼开关ActionPerformed(evt);
            }
        });
        jPanel74.add(大灰狼开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 170, 40));

        喷火龙开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        喷火龙开关.setText("喷火龙");
        喷火龙开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                喷火龙开关ActionPerformed(evt);
            }
        });
        jPanel74.add(喷火龙开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 140, 180, 40));

        火野猪开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        火野猪开关.setText("火野猪");
        火野猪开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                火野猪开关ActionPerformed(evt);
            }
        });
        jPanel74.add(火野猪开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 140, 170, 40));

        小白兔开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        小白兔开关.setText("小白兔");
        小白兔开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                小白兔开关ActionPerformed(evt);
            }
        });
        jPanel74.add(小白兔开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 140, 170, 40));

        青鳄鱼开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        青鳄鱼开关.setText("青鳄鱼");
        青鳄鱼开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                青鳄鱼开关ActionPerformed(evt);
            }
        });
        jPanel74.add(青鳄鱼开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 180, 40));

        花蘑菇开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        花蘑菇开关.setText("花蘑菇");
        花蘑菇开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                花蘑菇开关ActionPerformed(evt);
            }
        });
        jPanel74.add(花蘑菇开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, 170, 40));

        jLabel11.setFont(new java.awt.Font("宋体", 1, 12)); // NOI18N
        jLabel11.setText("游戏大区请勿全部都开启,会炸客户端的，每个区所建立的角色是不一样的,进入游戏后其他没有变化");
        jPanel74.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 190, -1, -1));

        jLabel63.setFont(new java.awt.Font("宋体", 1, 12)); // NOI18N
        jLabel63.setText("本页所有功能都需要重启服务端生效，请务必在开启服务端之前配置好");
        jPanel74.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 220, -1, -1));

        jPanel17.setBackground(new java.awt.Color(250, 250, 250));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "等级上限", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel17.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextMaxLevel.setText("250");
        jTextMaxLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextMaxLevelActionPerformed(evt);
            }
        });
        jPanel17.add(jTextMaxLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 80, 40));

        修改冒险家等级上限.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        修改冒险家等级上限.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801511.png"))); // NOI18N
        修改冒险家等级上限.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改冒险家等级上限ActionPerformed(evt);
            }
        });
        jPanel17.add(修改冒险家等级上限, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, 70, 40));

        jLabel253.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel253.setText("冒险家等级上限");
        jPanel17.add(jLabel253, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, -1, 60));

        骑士团等级上限.setText("250");
        骑士团等级上限.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                骑士团等级上限ActionPerformed(evt);
            }
        });
        jPanel17.add(骑士团等级上限, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 90, 40));

        jLabel252.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel252.setText("骑士团等级上限");
        jPanel17.add(jLabel252, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, 60));

        修改骑士团等级上限.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        修改骑士团等级上限.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801511.png"))); // NOI18N
        修改骑士团等级上限.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改骑士团等级上限ActionPerformed(evt);
            }
        });
        jPanel17.add(修改骑士团等级上限, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 70, 40));

        jTextFieldMaxCharacterNumber.setText("3");
        jTextFieldMaxCharacterNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMaxCharacterNumberActionPerformed(evt);
            }
        });
        jPanel17.add(jTextFieldMaxCharacterNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 40, 80, 40));

        jLabel19.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel19.setText("最大创建角色个数");
        jPanel17.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 50, 130, 20));

        jButtonMaxCharacter.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jButtonMaxCharacter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801511.png"))); // NOI18N
        jButtonMaxCharacter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMaxCharacterActionPerformed(evt);
            }
        });
        jPanel17.add(jButtonMaxCharacter, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 40, 70, 40));

        jPanel7.setBackground(new java.awt.Color(250, 250, 250));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "职业开关", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        冒险家职业开关.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        冒险家职业开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        冒险家职业开关.setText("冒险家");
        冒险家职业开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">开启:</font></strong><br> \n开启后玩家可以创建冒险家职业。<br> \n<strong><font color=\"#FF0000\">关闭:</font></strong><br> \n关闭后玩家不能创建冒险家职业。<br> <br>  \n");
        冒险家职业开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                冒险家职业开关ActionPerformed(evt);
            }
        });
        jPanel7.add(冒险家职业开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 170, 40));

        战神职业开关.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        战神职业开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        战神职业开关.setText("战神");
        战神职业开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">开启:</font></strong><br> \n开启后玩家可以创建战神职业。<br> \n<strong><font color=\"#FF0000\">关闭:</font></strong><br> \n关闭后玩家不能创建战神职业。<br> <br>  ");
        战神职业开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                战神职业开关ActionPerformed(evt);
            }
        });
        jPanel7.add(战神职业开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 40, 180, 40));

        骑士团职业开关.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        骑士团职业开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        骑士团职业开关.setText("骑士团");
        骑士团职业开关.setToolTipText("<html>\n<strong><font color=\"#FF0000\">开启:</font></strong><br> \n开启后玩家可以创建骑士团职业。<br> \n<strong><font color=\"#FF0000\">关闭:</font></strong><br> \n关闭后玩家不能创建骑士团职业。<br> <br>  ");
        骑士团职业开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                骑士团职业开关ActionPerformed(evt);
            }
        });
        jPanel7.add(骑士团职业开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 190, 40));

        jPanel54.setBackground(new java.awt.Color(250, 250, 250));
        jPanel54.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "其他开关", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel54.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        幸运职业开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        幸运职业开关.setText("幸运职业");
        幸运职业开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                幸运职业开关ActionPerformed(evt);
            }
        });
        jPanel54.add(幸运职业开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, 38));

        神秘商人开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        神秘商人开关.setText("神秘商人");
        神秘商人开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                神秘商人开关ActionPerformed(evt);
            }
        });
        jPanel54.add(神秘商人开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, -1, 37));

        魔族突袭开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        魔族突袭开关.setText("魔族袭击");
        魔族突袭开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                魔族突袭开关ActionPerformed(evt);
            }
        });
        jPanel54.add(魔族突袭开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, -1, 37));

        魔族攻城开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF2.png"))); // NOI18N
        魔族攻城开关.setText("魔族攻城");
        魔族攻城开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                魔族攻城开关ActionPerformed(evt);
            }
        });
        jPanel54.add(魔族攻城开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, -1, 38));

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 1119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, 838, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jPanel74, javax.swing.GroupLayout.PREFERRED_SIZE, 1145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel74, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jTabbedPaneBackendCSPoints.addTab("游戏设置", jPanel51);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel56, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(917, 917, 917))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jTabbedPaneBackendCSPoints, javax.swing.GroupLayout.PREFERRED_SIZE, 1224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jTabbedPaneBackendCSPoints, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(183, 183, 183)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("首页功能", jPanel3);

        jPanel8.setBackground(new java.awt.Color(250, 250, 250));

        jPanel6.setBackground(new java.awt.Color(250, 250, 250));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "工具系列", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        jButton44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3994506.png"))); // NOI18N
        jButton44.setText("抽奖管理");
        jButton44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton44ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3800871.png"))); // NOI18N
        jButton7.setText("保存数据");
        jButton7.setMaximumSize(new java.awt.Dimension(93, 23));
        jButton7.setMinimumSize(new java.awt.Dimension(93, 23));
        jButton7.setPreferredSize(new java.awt.Dimension(93, 23));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/商店管理.png"))); // NOI18N
        jButton8.setText("保存雇佣");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/111.png"))); // NOI18N
        jButton46.setText("活动控制台");
        jButton46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton46ActionPerformed(evt);
            }
        });

        jButton31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/魔方.png"))); // NOI18N
        jButton31.setText("代码查询");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jButton39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/问题.png"))); // NOI18N
        jButton39.setText("删除NPC");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(290, 290, 290)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton46, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(250, 250, 250));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "重载系列", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        重载副本按钮.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/更新.png"))); // NOI18N
        重载副本按钮.setText("重载副本");
        重载副本按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载副本按钮ActionPerformed(evt);
            }
        });

        重载爆率按钮.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/4031041.png"))); // NOI18N
        重载爆率按钮.setText("重载爆率");
        重载爆率按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载爆率按钮ActionPerformed(evt);
            }
        });

        重载反应堆按钮.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/更多设置.png"))); // NOI18N
        重载反应堆按钮.setText("重载反应堆");
        重载反应堆按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载反应堆按钮ActionPerformed(evt);
            }
        });

        重载传送门按钮.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/1802034.png"))); // NOI18N
        重载传送门按钮.setText("重载传送门");
        重载传送门按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载传送门按钮ActionPerformed(evt);
            }
        });

        重载商城按钮.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/自定义购物中心.png"))); // NOI18N
        重载商城按钮.setText("重载商城");
        重载商城按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载商城按钮ActionPerformed(evt);
            }
        });

        重载商店按钮.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/商店管理.png"))); // NOI18N
        重载商店按钮.setText("重载商店");
        重载商店按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载商店按钮ActionPerformed(evt);
            }
        });

        重载包头按钮.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/更多设置.png"))); // NOI18N
        重载包头按钮.setText("重载包头");
        重载包头按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载包头按钮ActionPerformed(evt);
            }
        });

        重载任务.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/信息日志.png"))); // NOI18N
        重载任务.setText("重载任务");
        重载任务.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载任务ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(重载任务, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(重载爆率按钮, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                        .addComponent(重载商城按钮, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(重载商店按钮, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(290, 290, 290)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(重载传送门按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(重载反应堆按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(重载包头按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(重载副本按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(重载商店按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(重载反应堆按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(重载传送门按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(重载商城按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(重载包头按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(重载爆率按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(重载任务, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(重载副本按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );

        jTabbedPane2.addTab("常用工具", jPanel8);

        jTabbedPane7.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(250, 250, 250));

        jPanel59.setBackground(new java.awt.Color(250, 250, 250));
        jPanel59.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "全服发送福利", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 12))); // NOI18N
        jPanel59.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        z2.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        z2.setText("发送抵用");
        z2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z2ActionPerformed(evt);
            }
        });
        jPanel59.add(z2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 100, 30));

        z3.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        z3.setText("发送金币");
        z3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z3ActionPerformed(evt);
            }
        });
        jPanel59.add(z3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 100, 30));

        z1.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        z1.setText("发送点券");
        z1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z1ActionPerformed(evt);
            }
        });
        jPanel59.add(z1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 100, 30));

        z4.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        z4.setText("发送经验");
        z4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z4ActionPerformed(evt);
            }
        });
        jPanel59.add(z4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 100, 30));

        z5.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        z5.setText("发送人气");
        z5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z5ActionPerformed(evt);
            }
        });
        jPanel59.add(z5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 100, 30));

        z6.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        z6.setText("发送豆豆");
        z6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z6ActionPerformed(evt);
            }
        });
        jPanel59.add(z6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 100, 30));

        a1.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        a1.setText("2000");
        a1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a1ActionPerformed(evt);
            }
        });
        jPanel59.add(a1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 130, 30));

        jLabel235.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel235.setText("数量");
        jPanel59.add(jLabel235, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 60, 30));

        jPanel57.setBackground(new java.awt.Color(255, 255, 255));
        jPanel57.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "全服发送福利", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 12))); // NOI18N
        jPanel57.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jPanel57.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        全服发送物品数量.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送物品数量ActionPerformed(evt);
            }
        });
        jPanel57.add(全服发送物品数量, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 100, 30));

        全服发送物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送物品代码ActionPerformed(evt);
            }
        });
        jPanel57.add(全服发送物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 110, 30));

        给予物品1.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        给予物品1.setText("给予物品");
        给予物品1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                给予物品1ActionPerformed(evt);
            }
        });
        jPanel57.add(给予物品1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 100, 30));

        jLabel217.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel217.setText("物品数量：");
        jPanel57.add(jLabel217, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

        jLabel234.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel234.setText("物品代码：");
        jPanel57.add(jLabel234, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jPanel58.setBackground(new java.awt.Color(250, 250, 250));
        jPanel58.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "全服发送福利", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 12))); // NOI18N
        jPanel58.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jPanel58.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        全服发送装备装备加卷.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备加卷ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备加卷, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 100, 30));

        全服发送装备装备制作人.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备制作人ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备制作人, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 100, 30));

        全服发送装备装备力量.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备力量ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备力量, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 100, 30));

        全服发送装备装备MP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备MPActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备MP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 100, 30));

        全服发送装备装备智力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备智力ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备智力, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 100, 30));

        全服发送装备装备运气.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备运气ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备运气, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, 100, 30));

        全服发送装备装备HP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备HPActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备HP, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 100, 30));

        全服发送装备装备攻击力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备攻击力ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备攻击力, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 100, 30));

        全服发送装备装备给予时间.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备给予时间ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备给予时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, 100, 30));

        全服发送装备装备可否交易.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备可否交易ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备可否交易, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, 100, 30));

        全服发送装备装备敏捷.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备敏捷ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备敏捷, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 100, 30));

        全服发送装备物品ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备物品IDActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备物品ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, 30));

        全服发送装备装备魔法力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备魔法力ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备魔法力, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 100, 30));

        全服发送装备装备魔法防御.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备魔法防御ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备魔法防御, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 100, 30));

        全服发送装备装备物理防御.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备物理防御ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备物理防御, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 100, 30));

        给予装备1.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        给予装备1.setText("个人发送");
        给予装备1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                给予装备1ActionPerformed(evt);
            }
        });
        jPanel58.add(给予装备1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, 100, 30));

        jLabel219.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel219.setText("能否交易：填0");
        jPanel58.add(jLabel219, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 150, -1, -1));

        jLabel220.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel220.setText("HP加成：");
        jPanel58.add(jLabel220, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, -1, -1));

        jLabel221.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel221.setText("魔法攻击力：");
        jPanel58.add(jLabel221, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        jLabel222.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel222.setText("装备代码：");
        jPanel58.add(jLabel222, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel223.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel223.setText("MP加成：");
        jPanel58.add(jLabel223, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jLabel224.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel224.setText("物理攻击力：");
        jPanel58.add(jLabel224, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, -1, -1));

        jLabel225.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel225.setText("可砸卷次数：");
        jPanel58.add(jLabel225, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, -1, -1));

        jLabel226.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel226.setText("装备署名：");
        jPanel58.add(jLabel226, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, -1));

        jLabel227.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel227.setText("装备力量：");
        jPanel58.add(jLabel227, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jLabel228.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel228.setText("装备敏捷：");
        jPanel58.add(jLabel228, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, -1, -1));

        jLabel229.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel229.setText("装备智力：");
        jPanel58.add(jLabel229, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, -1, -1));

        jLabel230.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel230.setText("装备运气：");
        jPanel58.add(jLabel230, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, -1, -1));

        jLabel231.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel231.setText("魔法防御：");
        jPanel58.add(jLabel231, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        jLabel232.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel232.setText("物理防御：");
        jPanel58.add(jLabel232, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, -1, -1));

        jLabel233.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel233.setText("限时时间：");
        jPanel58.add(jLabel233, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, -1, -1));

        发送装备玩家姓名.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                发送装备玩家姓名ActionPerformed(evt);
            }
        });
        jPanel58.add(发送装备玩家姓名, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 100, 30));

        给予装备2.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        给予装备2.setText("全服发送");
        给予装备2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                给予装备2ActionPerformed(evt);
            }
        });
        jPanel58.add(给予装备2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, 100, 30));

        jLabel246.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel246.setText("玩家名字：");
        jPanel58.add(jLabel246, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, -1, -1));

        jLabel244.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel244.setText("个人发送需要填写名字");
        jPanel58.add(jLabel244, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, -1, -1));

        jPanel60.setBackground(new java.awt.Color(255, 255, 255));
        jPanel60.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "个人发送物品", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 12))); // NOI18N
        jPanel60.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jPanel60.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        个人发送物品数量.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                个人发送物品数量ActionPerformed(evt);
            }
        });
        jPanel60.add(个人发送物品数量, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 80, 30));

        个人发送物品玩家名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                个人发送物品玩家名字ActionPerformed(evt);
            }
        });
        jPanel60.add(个人发送物品玩家名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 120, 30));

        个人发送物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                个人发送物品代码ActionPerformed(evt);
            }
        });
        jPanel60.add(个人发送物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 110, 30));

        给予物品.setBackground(new java.awt.Color(255, 255, 255));
        给予物品.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        给予物品.setText("给予物品");
        给予物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                给予物品ActionPerformed(evt);
            }
        });
        jPanel60.add(给予物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, 100, 30));

        jLabel240.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel240.setText("物品数量：");
        jPanel60.add(jLabel240, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, -1, -1));

        jLabel241.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel241.setText("玩家名字：");
        jPanel60.add(jLabel241, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel242.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel242.setText("物品代码：");
        jPanel60.add(jLabel242, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel60, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel60, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addComponent(jPanel58, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );

        jTabbedPane7.addTab("福利道具发送", jPanel4);

        jPanel9.setBackground(new java.awt.Color(250, 250, 250));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "玩家在线泡点", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        在线泡点设置.setBackground(new java.awt.Color(153, 153, 153));
        在线泡点设置.setFont(new java.awt.Font("幼圆", 0, 20)); // NOI18N
        在线泡点设置.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "类型", "数值"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        在线泡点设置.getTableHeader().setReorderingAllowed(false);
        jScrollPane134.setViewportView(在线泡点设置);

        jPanel9.add(jScrollPane134, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 470, 260));

        泡点序号.setEditable(false);
        jPanel9.add(泡点序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 70, 30));

        泡点类型.setEditable(false);
        jPanel9.add(泡点类型, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 380, 110, 30));
        jPanel9.add(泡点值, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 380, 120, 30));

        泡点值修改.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        泡点值修改.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801511.png"))); // NOI18N
        泡点值修改.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                泡点值修改ActionPerformed(evt);
            }
        });
        jPanel9.add(泡点值修改, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 380, 80, 30));

        jLabel322.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel322.setText("类型数值：");
        jPanel9.add(jLabel322, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 360, -1, -1));

        jLabel326.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel326.setForeground(new java.awt.Color(255, 0, 153));
        jLabel326.setText("提示：修改泡点时间需30分钟生效,其它设置即时生效。");
        jPanel9.add(jLabel326, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        jLabel327.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel327.setText("泡点奖励类型：");
        jPanel9.add(jLabel327, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 360, -1, -1));

        jPanel75.setBackground(new java.awt.Color(255, 255, 255));
        jPanel75.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "在线泡点设置", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N

        泡点金币开关.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        泡点金币开关.setText("泡点金币");
        泡点金币开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                泡点金币开关ActionPerformed(evt);
            }
        });

        泡点经验开关.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        泡点经验开关.setText("泡点经验");
        泡点经验开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                泡点经验开关ActionPerformed(evt);
            }
        });

        泡点点券开关.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        泡点点券开关.setText("泡点点券");
        泡点点券开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                泡点点券开关ActionPerformed(evt);
            }
        });

        泡点抵用开关.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        泡点抵用开关.setText("泡点抵用");
        泡点抵用开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                泡点抵用开关ActionPerformed(evt);
            }
        });

        泡点豆豆开关.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        泡点豆豆开关.setText("泡点豆豆");
        泡点豆豆开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                泡点豆豆开关ActionPerformed(evt);
            }
        });

        jLabel65.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 0, 153));
        jLabel65.setText("提示：在线泡点开关，无需重启服务端，即时生效");

        javax.swing.GroupLayout jPanel75Layout = new javax.swing.GroupLayout(jPanel75);
        jPanel75.setLayout(jPanel75Layout);
        jPanel75Layout.setHorizontalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel75Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(泡点豆豆开关, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel75Layout.createSequentialGroup()
                        .addComponent(泡点金币开关, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(泡点经验开关, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel75Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(泡点点券开关, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(泡点抵用开关, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel75Layout.setVerticalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel75Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(泡点金币开关, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(泡点经验开关, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(泡点点券开关, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(泡点抵用开关, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(泡点豆豆开关, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.add(jPanel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, 360, 270));

        jLabel328.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel328.setText("序号：");
        jPanel9.add(jLabel328, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, -1, -1));

        福利提示语言2.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        福利提示语言2.setText("[信息]：");
        jPanel9.add(福利提示语言2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 570, 800, 25));

        jLabel60.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel60.setText("金币==数值乘等级 列如：金币数值10，实际泡点所得金币等于10乘当前等级");
        jPanel9.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 430, 510, -1));

        jLabel61.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel61.setText("经验==数值乘等级 列如：经验数值10，实际泡点所得经验等于10乘当前等级");
        jPanel9.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 460, 500, -1));

        jLabel62.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel62.setText("其中：点卷/抵用卷/豆豆 这三个数值都是固定数值，设置10泡点所得就是10");
        jPanel9.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 490, 520, -1));

        jTabbedPane7.addTab("福利在线泡点", jPanel9);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane7)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jTabbedPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("全服福利", jPanel10);

        jTabbedPane9.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane9.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N

        jPanel16.setBackground(new java.awt.Color(250, 250, 250));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("反应堆/箱子爆率管理"));
        jPanel16.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N

        反应堆.setBackground(new java.awt.Color(102, 102, 102));
        反应堆.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        反应堆.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序列号", "反应堆", "物品代码", "概率", "物品名字"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        反应堆.getTableHeader().setReorderingAllowed(false);
        jScrollPane26.setViewportView(反应堆);

        jPanel61.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "反应堆编辑", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel61.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton26.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jButton26.setText("刷新列表信息");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        jPanel61.add(jButton26, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 140, 160, 40));

        反应堆序列号.setEditable(false);
        反应堆序列号.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel61.add(反应堆序列号, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 70, 30));

        反应堆代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel61.add(反应堆代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 90, 30));

        反应堆物品.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel61.add(反应堆物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 90, 30));

        反应堆概率.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        反应堆概率.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                反应堆概率ActionPerformed(evt);
            }
        });
        jPanel61.add(反应堆概率, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 90, 30));

        新增反应堆物品.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        新增反应堆物品.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/新增.png"))); // NOI18N
        新增反应堆物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                新增反应堆物品ActionPerformed(evt);
            }
        });
        jPanel61.add(新增反应堆物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 80, 30));

        删除反应堆物品1.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        删除反应堆物品1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/删除.png"))); // NOI18N
        删除反应堆物品1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除反应堆物品1ActionPerformed(evt);
            }
        });
        jPanel61.add(删除反应堆物品1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 80, 30));

        查找反应堆掉落.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel61.add(查找反应堆掉落, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 120, 90, 30));

        jButton36.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jButton36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/查找.png"))); // NOI18N
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });
        jPanel61.add(jButton36, new org.netbeans.lib.awtextra.AbsoluteConstraints(699, 120, 80, 30));

        查找物品.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel61.add(查找物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, 90, 30));

        jButton37.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jButton37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/查找.png"))); // NOI18N
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });
        jPanel61.add(jButton37, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 80, 80, 30));

        jLabel274.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel274.setText("掉落概率:");
        jPanel61.add(jLabel274, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 70, 30));

        jLabel275.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel275.setText("序号:");
        jPanel61.add(jLabel275, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, 30));

        jLabel277.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel277.setText("物品代码:");
        jPanel61.add(jLabel277, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 70, 30));

        jLabel278.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel278.setText("反应堆:");
        jPanel61.add(jLabel278, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 80, 50, 30));

        jLabel279.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel279.setText("反应堆:");
        jPanel61.add(jLabel279, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 120, 60, 30));

        jLabel280.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel280.setText("物品代码:");
        jPanel61.add(jLabel280, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 80, 70, 30));

        修改反应堆物品.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        修改反应堆物品.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/更改.png"))); // NOI18N
        修改反应堆物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改反应堆物品ActionPerformed(evt);
            }
        });
        jPanel61.add(修改反应堆物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 80, 30));

        重载箱子反应堆按钮.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        重载箱子反应堆按钮.setText("重载箱子反应堆");
        重载箱子反应堆按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载箱子反应堆按钮ActionPerformed(evt);
            }
        });
        jPanel61.add(重载箱子反应堆按钮, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 80, 160, 40));

        jLabel282.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel282.setText("删除指定反应堆的所有物品");
        jPanel61.add(jLabel282, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, 190, 20));
        jPanel61.add(删除反应堆物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 90, 30));

        删除反应堆物品.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        删除反应堆物品.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/删除.png"))); // NOI18N
        删除反应堆物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除反应堆物品ActionPerformed(evt);
            }
        });
        jPanel61.add(删除反应堆物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 160, 80, 30));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane26)
                    .addComponent(jPanel61, javax.swing.GroupLayout.DEFAULT_SIZE, 1196, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel61, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jTabbedPane9.addTab("箱子爆率", jPanel16);

        jPanel28.setBackground(new java.awt.Color(250, 250, 250));
        jPanel28.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "全局爆物/(10000=1%)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        世界爆物.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        世界爆物.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序列号", "物品代码", "爆率", "物品名"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        世界爆物.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(世界爆物);

        jPanel28.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 1150, 370));

        世界爆物序列号.setEditable(false);
        世界爆物序列号.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        世界爆物序列号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                世界爆物序列号ActionPerformed(evt);
            }
        });
        jPanel28.add(世界爆物序列号, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 450, 100, -1));

        世界爆物物品代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        世界爆物物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                世界爆物物品代码ActionPerformed(evt);
            }
        });
        jPanel28.add(世界爆物物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 490, 100, 30));

        世界爆物爆率.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        世界爆物爆率.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                世界爆物爆率ActionPerformed(evt);
            }
        });
        jPanel28.add(世界爆物爆率, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 530, 100, 30));

        添加世界爆物.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        添加世界爆物.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/新增.png"))); // NOI18N
        添加世界爆物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                添加世界爆物ActionPerformed(evt);
            }
        });
        jPanel28.add(添加世界爆物, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 480, 70, 30));

        删除世界爆物.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        删除世界爆物.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/删除.png"))); // NOI18N
        删除世界爆物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除世界爆物ActionPerformed(evt);
            }
        });
        jPanel28.add(删除世界爆物, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 560, 70, 30));

        jLabel210.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel210.setText("序列号:");
        jPanel28.add(jLabel210, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 450, 50, 30));

        jLabel202.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel202.setText("物品代码:");
        jPanel28.add(jLabel202, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 490, 60, 30));

        jLabel209.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel209.setText("爆率:");
        jPanel28.add(jLabel209, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 530, 40, 30));

        世界爆物名称.setEditable(false);
        世界爆物名称.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        世界爆物名称.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                世界爆物名称ActionPerformed(evt);
            }
        });
        jPanel28.add(世界爆物名称, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 570, 100, 30));

        jLabel40.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel40.setText("物品名:");
        jPanel28.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 569, 50, 30));

        修改世界爆物.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        修改世界爆物.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/更改.png"))); // NOI18N
        修改世界爆物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改世界爆物ActionPerformed(evt);
            }
        });
        jPanel28.add(修改世界爆物, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 520, 70, 30));

        刷新世界爆物.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        刷新世界爆物.setText("刷新世界爆物");
        刷新世界爆物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新世界爆物ActionPerformed(evt);
            }
        });
        jPanel28.add(刷新世界爆物, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 520, 270, 30));

        jLabel323.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel323.setText("指定物品查询掉落");
        jPanel28.add(jLabel323, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 490, -1, -1));

        查询物品掉落代码1.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        查询物品掉落代码1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查询物品掉落代码1ActionPerformed(evt);
            }
        });
        jPanel28.add(查询物品掉落代码1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 510, 120, 30));

        查询物品掉落.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        查询物品掉落.setText("查询物品掉落");
        查询物品掉落.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查询物品掉落ActionPerformed(evt);
            }
        });
        jPanel28.add(查询物品掉落, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 550, 120, 30));

        jLabel321.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel321.setText("删除指定物品掉落");
        jPanel28.add(jLabel321, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 490, -1, -1));

        删除指定的掉落1.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel28.add(删除指定的掉落1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 510, 120, 30));

        删除指定的掉落按键.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        删除指定的掉落按键.setText("删除指定掉落");
        删除指定的掉落按键.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除指定的掉落按键ActionPerformed(evt);
            }
        });
        jPanel28.add(删除指定的掉落按键, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 550, 120, 30));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane9.addTab("全局爆率", jPanel19);

        jPanel20.setBackground(new java.awt.Color(250, 250, 250));

        钓鱼管理.setBackground(new java.awt.Color(255, 255, 255));
        钓鱼管理.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "钓鱼管理", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        钓鱼管理.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        钓鱼管理.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        钓鱼物品.setBackground(new java.awt.Color(102, 102, 102));
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

        钓鱼管理.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 1170, 380));
        钓鱼管理.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, -1));
        钓鱼管理.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, -1));

        jPanel91.setBackground(new java.awt.Color(250, 250, 250));
        jPanel91.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "钓鱼编辑", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel91.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jPanel91.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        修改钓鱼物品.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        修改钓鱼物品.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/更改.png"))); // NOI18N
        修改钓鱼物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改钓鱼物品ActionPerformed(evt);
            }
        });
        jPanel91.add(修改钓鱼物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, -1, 30));

        刷新钓鱼物品.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        刷新钓鱼物品.setText("刷新钓鱼物品");
        刷新钓鱼物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新钓鱼物品ActionPerformed(evt);
            }
        });
        jPanel91.add(刷新钓鱼物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 100, -1, 30));

        钓鱼物品代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        钓鱼物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                钓鱼物品代码ActionPerformed(evt);
            }
        });
        jPanel91.add(钓鱼物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 110, 30));

        新增钓鱼物品.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        新增钓鱼物品.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/新增.png"))); // NOI18N
        新增钓鱼物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                新增钓鱼物品ActionPerformed(evt);
            }
        });
        jPanel91.add(新增钓鱼物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 150, -1, 30));

        钓鱼物品概率.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel91.add(钓鱼物品概率, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 110, 30));

        钓鱼物品名称.setEditable(false);
        钓鱼物品名称.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel91.add(钓鱼物品名称, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 110, 30));

        删除钓鱼物品.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        删除钓鱼物品.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/删除.png"))); // NOI18N
        删除钓鱼物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除钓鱼物品ActionPerformed(evt);
            }
        });
        jPanel91.add(删除钓鱼物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, -1, 30));

        钓鱼物品序号.setEditable(false);
        钓鱼物品序号.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        钓鱼物品序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                钓鱼物品序号ActionPerformed(evt);
            }
        });
        jPanel91.add(钓鱼物品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 110, 30));

        jLabel379.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel379.setText("物品名字");
        jPanel91.add(jLabel379, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 70, 30));

        jLabel380.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel380.setText("序列号");
        jPanel91.add(jLabel380, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 50, 30));

        jLabel381.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel381.setText("物品代码");
        jPanel91.add(jLabel381, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 70, 30));

        jLabel382.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel382.setText("垂钓概率");
        jPanel91.add(jLabel382, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 70, 30));

        钓鱼管理.add(jPanel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 430, 810, 200));

        ZEVMS2提示框1.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        ZEVMS2提示框1.setText("[信息]：");
        钓鱼管理.add(ZEVMS2提示框1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 725, 1260, 30));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(钓鱼管理, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(钓鱼管理, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane9.addTab("钓鱼物品", jPanel20);

        jPanel18.setBackground(new java.awt.Color(250, 250, 250));
        jPanel18.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N

        jPanel27.setBackground(new java.awt.Color(250, 250, 250));
        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "怪物爆物/(10000=1%)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        怪物爆物.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        怪物爆物.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序列号", "怪物代码", "物品代码", "爆率", "物品名字"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        怪物爆物.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(怪物爆物);

        jPanel27.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 1170, 400));

        jLabel213.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel213.setText("序列号:");

        怪物爆物序列号.setEditable(false);
        怪物爆物序列号.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        jLabel120.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel120.setText("怪物代码:");

        怪物爆物怪物代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        jLabel211.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel211.setText("物品代码:");

        怪物爆物物品代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        jLabel212.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel212.setText("爆率:");

        怪物爆物爆率.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        jLabel39.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel39.setText("物品名:");

        怪物爆物物品名称.setEditable(false);
        怪物爆物物品名称.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        添加怪物爆物.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        添加怪物爆物.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/新增.png"))); // NOI18N
        添加怪物爆物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                添加怪物爆物ActionPerformed(evt);
            }
        });

        删除怪物爆物.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        删除怪物爆物.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/删除.png"))); // NOI18N
        删除怪物爆物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除怪物爆物ActionPerformed(evt);
            }
        });

        修改怪物爆物.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        修改怪物爆物.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/更改.png"))); // NOI18N
        修改怪物爆物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改怪物爆物ActionPerformed(evt);
            }
        });

        刷新怪物爆物.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        刷新怪物爆物.setText("刷新怪物爆物");
        刷新怪物爆物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新怪物爆物ActionPerformed(evt);
            }
        });

        jLabel324.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel324.setText("指定物品查询掉落");

        查询物品掉落代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        查询物品掉落代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查询物品掉落代码ActionPerformed(evt);
            }
        });

        查询物品掉落1.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        查询物品掉落1.setText("查询物品掉落");
        查询物品掉落1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查询物品掉落1ActionPerformed(evt);
            }
        });

        jLabel316.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel316.setText("指定怪物物品掉落");

        jLabel325.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel325.setText("删除指定物品掉落");

        删除指定的掉落.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        删除指定的掉落按键1.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        删除指定的掉落按键1.setText("删除指定掉落");
        删除指定的掉落按键1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除指定的掉落按键1ActionPerformed(evt);
            }
        });

        刷新怪物卡片.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        刷新怪物卡片.setText("刷新卡片");
        刷新怪物卡片.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新怪物卡片ActionPerformed(evt);
            }
        });

        查询怪物掉落代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        查询怪物掉落.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        查询怪物掉落.setText("查询怪物掉落");
        查询怪物掉落.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查询怪物掉落ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(jLabel213, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(怪物爆物序列号, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(怪物爆物物品名称, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                        .addComponent(jLabel120, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(怪物爆物怪物代码, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel211, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(怪物爆物物品代码, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                        .addComponent(添加怪物爆物, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(77, 77, 77)
                                        .addComponent(修改怪物爆物, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(79, 79, 79)))
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(删除怪物爆物, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                        .addComponent(jLabel212, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(怪物爆物爆率, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(66, 66, 66)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel324, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel325, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel316, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(查询物品掉落代码, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(查询怪物掉落代码, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                .addComponent(删除指定的掉落, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(查询物品掉落1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(查询怪物掉落, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(删除指定的掉落按键1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(刷新怪物卡片, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(刷新怪物爆物, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)))
                .addGap(99, 99, 99))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(怪物爆物序列号, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel213, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(怪物爆物物品名称, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(查询物品掉落代码, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(查询物品掉落1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel324, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel212, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(怪物爆物爆率, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(怪物爆物物品代码)
                            .addComponent(jLabel211, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(怪物爆物怪物代码)
                            .addComponent(jLabel120, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(添加怪物爆物, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(修改怪物爆物, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(删除怪物爆物, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(112, 112, 112))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(删除指定的掉落按键1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(删除指定的掉落, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel325, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(查询怪物掉落代码, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel316, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(查询怪物掉落, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(刷新怪物爆物, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(刷新怪物卡片, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane9.addTab("物品爆率", jPanel18);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 1227, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jTabbedPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("物品爆率", jPanel15);

        jTabbedPane8.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N

        jPanel23.setBackground(new java.awt.Color(250, 250, 250));

        账号信息.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        账号信息.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "账号ID", "账号", "IP地址", "MAC地址", "绑定QQ", "点券", "元宝", "抵用", "最近上线", "在线", "封号", "GM"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        账号信息.setColumnSelectionAllowed(true);
        账号信息.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(账号信息);
        账号信息.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "账号修改", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        抵用.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel30.add(抵用, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, 110, 30));

        账号.setEditable(false);
        账号.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel30.add(账号, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 100, 30));

        点券.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel30.add(点券, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, 100, 30));

        jLabel55.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel55.setText("抵用:");
        jPanel30.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 20, 60, -1));

        jLabel131.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel131.setText("点券:");
        jPanel30.add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, -1, -1));

        修改账号点券抵用.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        修改账号点券抵用.setText("修改");
        修改账号点券抵用.setToolTipText("<html>\n点击账号后可修改账号的<strong><font color=\"#FF0000\">抵用券</font></strong><strong>和<font color=\"#FF0000\">点券</font></strong>");
        修改账号点券抵用.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改账号点券抵用ActionPerformed(evt);
            }
        });
        jPanel30.add(修改账号点券抵用, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 40, 100, 30));

        账号ID.setEditable(false);
        账号ID.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel30.add(账号ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 70, 30));

        jLabel206.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel206.setText("ID:");
        jPanel30.add(jLabel206, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel312.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel312.setText("管理:");
        jPanel30.add(jLabel312, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 20, -1, -1));

        管理1.setEditable(false);
        管理1.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel30.add(管理1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 40, 70, 30));

        jLabel353.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel353.setText("账号:");
        jPanel30.add(jLabel353, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, -1));

        QQ.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel30.add(QQ, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 120, 30));

        jLabel357.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel357.setText("绑定QQ:");
        jPanel30.add(jLabel357, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 80, -1));

        jLabel16.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel16.setText("元宝:");
        jPanel30.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, -1, -1));
        jPanel30.add(jTextFieldMoneyB, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 40, 90, 30));

        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "注册/修改", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel32.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        注册的账号.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        注册的账号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                注册的账号ActionPerformed(evt);
            }
        });
        jPanel32.add(注册的账号, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 100, 30));

        注册的密码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        注册的密码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                注册的密码ActionPerformed(evt);
            }
        });
        jPanel32.add(注册的密码, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, 100, 30));

        jButton35.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jButton35.setText("注册");
        jButton35.setToolTipText("<html>\n输入<strong><font color=\"#FF0000\">账号</font></strong><strong>和<strong><font color=\"#FF0000\">密码</font></strong><strong>即可注册账号");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });
        jPanel32.add(jButton35, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 70, 30));

        jLabel111.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel111.setText("账号:");
        jPanel32.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 30));

        jLabel201.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel201.setText("密码:");
        jPanel32.add(jLabel201, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, 30));

        jButton32.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jButton32.setText("改密");
        jButton32.setToolTipText("<html>\n输入账号修改<strong><font color=\"#FF0000\">密码</font></strong><strong>");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });
        jPanel32.add(jButton32, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 70, 30));

        账号提示语言.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        账号提示语言.setText("[信息]：");
        jPanel32.add(账号提示语言, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, 489, 25));

        刷新账号信息.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        刷新账号信息.setText("全部账号");
        刷新账号信息.setToolTipText("显示所有玩家账号");
        刷新账号信息.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新账号信息ActionPerformed(evt);
            }
        });

        离线账号.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        离线账号.setText("离线账号");
        离线账号.setToolTipText("显示离线账号");
        离线账号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                离线账号ActionPerformed(evt);
            }
        });

        解封.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        解封.setText("解封账号");
        解封.setToolTipText("<html>\n在文本框<strong><font color=\"#FF0000\">操作的账号</font></strong>中输入账号即可解封已经被封禁的账号<br>\n");
        解封.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                解封ActionPerformed(evt);
            }
        });

        已封账号.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        已封账号.setText("已封账号");
        已封账号.setToolTipText("显示已经被封禁的账号");
        已封账号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                已封账号ActionPerformed(evt);
            }
        });

        在线账号.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        在线账号.setText("在线账号");
        在线账号.setToolTipText("显示在线账号");
        在线账号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                在线账号ActionPerformed(evt);
            }
        });

        删除账号.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        删除账号.setText("删除账号");
        删除账号.setToolTipText("<html>\n在文本框<strong><font color=\"#FF0000\">操作的账号</font></strong>中输入账号即可删除账号<br>");
        删除账号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除账号ActionPerformed(evt);
            }
        });

        封锁账号.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        封锁账号.setText("封锁账号");
        封锁账号.setToolTipText("<html>\n在文本框<strong><font color=\"#FF0000\">操作的账号</font></strong>中输入账号即可封禁账号<br>");
        封锁账号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                封锁账号ActionPerformed(evt);
            }
        });

        解卡.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        解卡.setText("解卡账号");
        解卡.setToolTipText("<html>\n在文本框<strong><font color=\"#FF0000\">操作的账号</font></strong>中输入账号即可解卡账号<br>");
        解卡.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                解卡ActionPerformed(evt);
            }
        });

        显示在线账号.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N

        jButton1AccountSearch.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jButton1AccountSearch.setText("查账号");
        jButton1AccountSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1AccountSearchActionPerformed(evt);
            }
        });

        账号操作.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(刷新账号信息)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(在线账号)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(离线账号)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(已封账号)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(删除账号)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(解卡)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(封锁账号)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(解封)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1AccountSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(账号操作, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(显示在线账号, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, 1279, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, 1217, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(刷新账号信息, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(在线账号, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(离线账号, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(已封账号, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(删除账号, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(解卡, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(封锁账号, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(解封, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1AccountSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(账号操作, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(显示在线账号, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane8.addTab("账号管理", jPanel23);

        角色信息1.setBackground(new java.awt.Color(250, 250, 250));
        角色信息1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色信息.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        角色信息.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        角色信息.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "角色ID", "账号ID", "角色昵称", "职业", "等级", "力量", "敏捷", "智力", "运气", "MaxHP", "MaxMP", "金币", "所在地图", "状态", "GM", "发型", "脸型"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色信息.setName(""); // NOI18N
        角色信息.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(角色信息);

        角色信息1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1210, 450));

        刷新角色信息.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        刷新角色信息.setText("刷新");
        刷新角色信息.setToolTipText("显示所有角色");
        刷新角色信息.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新角色信息ActionPerformed(evt);
            }
        });
        角色信息1.add(刷新角色信息, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 110, 30));

        显示管理角色.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        显示管理角色.setText("管理角色");
        显示管理角色.setToolTipText("显示所有GM管理员");
        显示管理角色.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                显示管理角色ActionPerformed(evt);
            }
        });
        角色信息1.add(显示管理角色, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, 110, 30));

        jButton38.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jButton38.setText("修改");
        jButton38.setToolTipText("<html>\n修改角色信息<strong><font color=\"#FF0000\">文本框不可留空</font></strong><strong>");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });
        角色信息1.add(jButton38, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 550, 100, 30));

        删除角色.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        删除角色.setText("删除角色");
        删除角色.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除角色ActionPerformed(evt);
            }
        });
        角色信息1.add(删除角色, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 460, 130, 30));

        角色昵称.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色昵称.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                角色昵称ActionPerformed(evt);
            }
        });
        角色信息1.add(角色昵称, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 550, 70, 30));

        等级.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        等级.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                等级ActionPerformed(evt);
            }
        });
        角色信息1.add(等级, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 550, 40, 30));

        力量.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        力量.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                力量ActionPerformed(evt);
            }
        });
        角色信息1.add(力量, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 550, 40, 30));

        敏捷.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        敏捷.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                敏捷ActionPerformed(evt);
            }
        });
        角色信息1.add(敏捷, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 550, 40, 30));

        智力.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        智力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                智力ActionPerformed(evt);
            }
        });
        角色信息1.add(智力, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 550, 40, 30));

        运气.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        运气.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                运气ActionPerformed(evt);
            }
        });
        角色信息1.add(运气, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 550, 40, 30));

        HP.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        HP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HPActionPerformed(evt);
            }
        });
        角色信息1.add(HP, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 550, 50, 30));

        MP.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        MP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MPActionPerformed(evt);
            }
        });
        角色信息1.add(MP, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 550, 50, 30));

        金币1.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        金币1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                金币1ActionPerformed(evt);
            }
        });
        角色信息1.add(金币1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 550, 100, 30));

        地图.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        地图.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                地图ActionPerformed(evt);
            }
        });
        角色信息1.add(地图, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 550, 100, 30));

        GM.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        GM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GMActionPerformed(evt);
            }
        });
        角色信息1.add(GM, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 550, 70, 30));

        jLabel182.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel182.setText("GM等级：");
        角色信息1.add(jLabel182, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 530, -1, -1));

        jLabel183.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel183.setText("角色ID:");
        角色信息1.add(jLabel183, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, -1, -1));

        jLabel184.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel184.setText("等级:");
        角色信息1.add(jLabel184, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 530, -1, -1));

        jLabel185.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel185.setText("力量:");
        角色信息1.add(jLabel185, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 530, -1, -1));

        jLabel186.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel186.setText("敏捷:");
        角色信息1.add(jLabel186, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 530, -1, -1));

        jLabel187.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel187.setText("智力:");
        角色信息1.add(jLabel187, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 530, -1, -1));

        jLabel189.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel189.setText("MaxHP:");
        角色信息1.add(jLabel189, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 530, -1, -1));

        jLabel190.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel190.setText("MaxMP:");
        角色信息1.add(jLabel190, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 530, -1, -1));

        jLabel191.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel191.setText("金币:");
        角色信息1.add(jLabel191, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 530, -1, -1));

        jLabel192.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel192.setText("发型:");
        角色信息1.add(jLabel192, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 520, -1, 30));

        jLabel193.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel193.setText("角色昵称:");
        角色信息1.add(jLabel193, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 530, -1, -1));

        角色ID.setEditable(false);
        角色ID.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                角色IDActionPerformed(evt);
            }
        });
        角色信息1.add(角色ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, 40, 30));

        卡号自救1.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        卡号自救1.setText("卡发/脸型解救");
        卡号自救1.setToolTipText("<html>\n角色卡<strong><font color=\"#FF0000\">发型</font></strong><strong>或者<strong><font color=\"#FF0000\">脸型</font></strong><strong>时候可用此功能\n");
        卡号自救1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                卡号自救1ActionPerformed(evt);
            }
        });
        角色信息1.add(卡号自救1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 460, 130, 30));

        卡号自救2.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        卡号自救2.setText("卡物品解救");
        卡号自救2.setToolTipText("<html>\n次卡号解救会对角色进行<strong><font color=\"#FF0000\">清空物品</font></strong><strong>处理");
        卡号自救2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                卡号自救2ActionPerformed(evt);
            }
        });
        角色信息1.add(卡号自救2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 490, 130, 30));

        jLabel203.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel203.setText("运气:");
        角色信息1.add(jLabel203, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 530, -1, -1));

        查看技能.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        查看技能.setText("查看角色技能");
        查看技能.setToolTipText("<html>\n选择角色后，点击此功能，可查看角色所有<strong><font color=\"#FF0000\">技能信息</font></strong><strong>");
        查看技能.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查看技能ActionPerformed(evt);
            }
        });
        角色信息1.add(查看技能, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 490, 130, 30));

        查看背包.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        查看背包.setText("查看角色背包");
        查看背包.setToolTipText("<html>\n选择角色后，点击此功能，可查看角色所有<strong><font color=\"#FF0000\">物品信息</font></strong><strong>");
        查看背包.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查看背包ActionPerformed(evt);
            }
        });
        角色信息1.add(查看背包, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 490, 130, 30));

        卡家族解救.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        卡家族解救.setText("卡家族解救");
        卡家族解救.setToolTipText("<html>\n角色卡<strong><font color=\"#FF0000\">发型</font></strong><strong>或者<strong><font color=\"#FF0000\">脸型</font></strong><strong>时候可用此功能\n");
        卡家族解救.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                卡家族解救ActionPerformed(evt);
            }
        });
        角色信息1.add(卡家族解救, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 460, 130, 30));

        脸型.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        脸型.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                脸型ActionPerformed(evt);
            }
        });
        角色信息1.add(脸型, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 550, 70, 30));

        发型.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        发型.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                发型ActionPerformed(evt);
            }
        });
        角色信息1.add(发型, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 550, 70, 30));

        jLabel214.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel214.setText("所在地图:");
        角色信息1.add(jLabel214, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 530, -1, -1));

        离线角色.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        离线角色.setText("离线角色");
        离线角色.setToolTipText("显示所有GM管理员");
        离线角色.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                离线角色ActionPerformed(evt);
            }
        });
        角色信息1.add(离线角色, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 490, 110, 30));

        在线角色.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        在线角色.setText("在线角色");
        在线角色.setToolTipText("显示所有GM管理员");
        在线角色.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                在线角色ActionPerformed(evt);
            }
        });
        角色信息1.add(在线角色, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 460, 110, 30));

        显示在线玩家.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        角色信息1.add(显示在线玩家, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 495, 130, 30));
        角色信息1.add(roleNameEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 480, 190, 30));

        jButtonAccountSearch.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jButtonAccountSearch.setText("角色名查找");
        jButtonAccountSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAccountSearchActionPerformed(evt);
            }
        });
        角色信息1.add(jButtonAccountSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 480, 160, 30));

        jLabel18.setText("脸型:");
        角色信息1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 530, -1, -1));

        jTabbedPane8.addTab("角色信息", 角色信息1);

        角色背包.setBackground(new java.awt.Color(250, 250, 250));
        角色背包.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane5.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane5.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N

        jPanel39.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "角色穿戴装备信息", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel39.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色背包穿戴.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色背包穿戴.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色背包穿戴.getTableHeader().setReorderingAllowed(false);
        jScrollPane15.setViewportView(角色背包穿戴);

        jPanel39.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 860, 480));

        背包物品名字1.setEditable(false);
        背包物品名字1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                背包物品名字1ActionPerformed(evt);
            }
        });
        jPanel39.add(背包物品名字1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 550, 150, 30));

        身上穿戴序号1.setEditable(false);
        身上穿戴序号1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                身上穿戴序号1ActionPerformed(evt);
            }
        });
        jPanel39.add(身上穿戴序号1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 550, 110, 30));

        背包物品代码1.setEditable(false);
        背包物品代码1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                背包物品代码1ActionPerformed(evt);
            }
        });
        jPanel39.add(背包物品代码1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 550, 110, 30));

        jLabel276.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel276.setText("序号:");
        jPanel39.add(jLabel276, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, -1, 20));

        jLabel283.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel283.setText("物品名字:");
        jPanel39.add(jLabel283, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 530, -1, 20));

        jLabel287.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel287.setText("物品代码:");
        jPanel39.add(jLabel287, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 530, -1, 20));

        删除穿戴装备.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除穿戴装备.setText("删除");
        删除穿戴装备.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除穿戴装备ActionPerformed(evt);
            }
        });
        jPanel39.add(删除穿戴装备, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 550, -1, 30));

        jTabbedPane5.addTab("身上穿戴", jPanel39);

        jPanel40.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "装备背包", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel40.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色装备背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色装备背包.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色装备背包.getTableHeader().setReorderingAllowed(false);
        jScrollPane16.setViewportView(角色装备背包);

        jPanel40.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 850, 480));

        装备背包物品名字.setEditable(false);
        装备背包物品名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                装备背包物品名字ActionPerformed(evt);
            }
        });
        jPanel40.add(装备背包物品名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 550, 150, 30));

        装备背包物品序号.setEditable(false);
        装备背包物品序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                装备背包物品序号ActionPerformed(evt);
            }
        });
        jPanel40.add(装备背包物品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 550, 110, 30));

        装备背包物品代码.setEditable(false);
        装备背包物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                装备背包物品代码ActionPerformed(evt);
            }
        });
        jPanel40.add(装备背包物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 550, 110, 30));

        jLabel288.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel288.setText("序号；");
        jPanel40.add(jLabel288, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, -1, 20));

        jLabel289.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel289.setText("物品名字；");
        jPanel40.add(jLabel289, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 530, -1, 20));

        jLabel290.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel290.setText("物品代码；");
        jPanel40.add(jLabel290, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 530, -1, 20));

        删除装备背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除装备背包.setText("删除");
        删除装备背包.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除装备背包ActionPerformed(evt);
            }
        });
        jPanel40.add(删除装备背包, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 550, -1, 30));

        jTabbedPane5.addTab("装备背包", jPanel40);

        jPanel41.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "消耗背包", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel41.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色消耗背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色消耗背包.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字", "物品数量"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色消耗背包.getTableHeader().setReorderingAllowed(false);
        jScrollPane17.setViewportView(角色消耗背包);

        jPanel41.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 860, 490));

        消耗背包物品名字.setEditable(false);
        消耗背包物品名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                消耗背包物品名字ActionPerformed(evt);
            }
        });
        jPanel41.add(消耗背包物品名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 550, 150, 30));

        消耗背包物品序号.setEditable(false);
        消耗背包物品序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                消耗背包物品序号ActionPerformed(evt);
            }
        });
        jPanel41.add(消耗背包物品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 550, 110, 30));

        消耗背包物品代码.setEditable(false);
        消耗背包物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                消耗背包物品代码ActionPerformed(evt);
            }
        });
        jPanel41.add(消耗背包物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 550, 110, 30));

        jLabel291.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel291.setText("序号；");
        jPanel41.add(jLabel291, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 530, -1, 20));

        jLabel292.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel292.setText("物品名字；");
        jPanel41.add(jLabel292, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 530, -1, 20));

        jLabel293.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel293.setText("物品代码；");
        jPanel41.add(jLabel293, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 530, -1, 20));

        删除消耗背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除消耗背包.setText("删除");
        删除消耗背包.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除消耗背包ActionPerformed(evt);
            }
        });
        jPanel41.add(删除消耗背包, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 550, -1, 30));

        jTabbedPane5.addTab("消耗背包", jPanel41);

        jPanel42.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "设置背包", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel42.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色设置背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色设置背包.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字", "物品数量"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色设置背包.getTableHeader().setReorderingAllowed(false);
        jScrollPane18.setViewportView(角色设置背包);

        jPanel42.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 860, 490));

        设置背包物品名字.setEditable(false);
        设置背包物品名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                设置背包物品名字ActionPerformed(evt);
            }
        });
        jPanel42.add(设置背包物品名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 550, 150, 30));

        设置背包物品序号.setEditable(false);
        设置背包物品序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                设置背包物品序号ActionPerformed(evt);
            }
        });
        jPanel42.add(设置背包物品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 550, 110, 30));

        设置背包物品代码.setEditable(false);
        设置背包物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                设置背包物品代码ActionPerformed(evt);
            }
        });
        jPanel42.add(设置背包物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 550, 110, 30));

        jLabel294.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel294.setText("序号；");
        jPanel42.add(jLabel294, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 530, -1, 20));

        jLabel295.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel295.setText("物品名字；");
        jPanel42.add(jLabel295, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 530, -1, 20));

        jLabel296.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel296.setText("物品代码；");
        jPanel42.add(jLabel296, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 530, -1, 20));

        删除设置背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除设置背包.setText("删除");
        删除设置背包.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除设置背包ActionPerformed(evt);
            }
        });
        jPanel42.add(删除设置背包, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 550, -1, 30));

        jTabbedPane5.addTab("设置背包", jPanel42);

        jPanel43.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "其他背包", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel43.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色其他背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色其他背包.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字", "物品数量"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色其他背包.getTableHeader().setReorderingAllowed(false);
        jScrollPane19.setViewportView(角色其他背包);

        jPanel43.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 860, 490));

        其他背包物品名字.setEditable(false);
        其他背包物品名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                其他背包物品名字ActionPerformed(evt);
            }
        });
        jPanel43.add(其他背包物品名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 550, 150, 30));

        其他背包物品序号.setEditable(false);
        其他背包物品序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                其他背包物品序号ActionPerformed(evt);
            }
        });
        jPanel43.add(其他背包物品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 550, 110, 30));

        其他背包物品代码.setEditable(false);
        其他背包物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                其他背包物品代码ActionPerformed(evt);
            }
        });
        jPanel43.add(其他背包物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 550, 110, 30));

        jLabel297.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel297.setText("序号；");
        jPanel43.add(jLabel297, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 530, -1, 20));

        jLabel298.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel298.setText("物品名字；");
        jPanel43.add(jLabel298, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 530, -1, 20));

        jLabel299.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel299.setText("物品代码；");
        jPanel43.add(jLabel299, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 530, -1, 20));

        删除其他背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除其他背包.setText("删除");
        删除其他背包.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除其他背包ActionPerformed(evt);
            }
        });
        jPanel43.add(删除其他背包, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 550, -1, 30));

        jTabbedPane5.addTab("其他背包", jPanel43);

        jPanel44.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "特殊背包", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel44.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色特殊背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色特殊背包.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字", "物品数量"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色特殊背包.getTableHeader().setReorderingAllowed(false);
        jScrollPane20.setViewportView(角色特殊背包);

        jPanel44.add(jScrollPane20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 860, 490));

        特殊背包物品名字.setEditable(false);
        特殊背包物品名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                特殊背包物品名字ActionPerformed(evt);
            }
        });
        jPanel44.add(特殊背包物品名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 550, 150, 30));

        特殊背包物品序号.setEditable(false);
        特殊背包物品序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                特殊背包物品序号ActionPerformed(evt);
            }
        });
        jPanel44.add(特殊背包物品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 550, 110, 30));

        特殊背包物品代码.setEditable(false);
        特殊背包物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                特殊背包物品代码ActionPerformed(evt);
            }
        });
        jPanel44.add(特殊背包物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 550, 110, 30));

        jLabel300.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel300.setText("序号；");
        jPanel44.add(jLabel300, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, -1, 20));

        jLabel301.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel301.setText("物品名字；");
        jPanel44.add(jLabel301, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 530, -1, 20));

        jLabel302.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel302.setText("物品代码；");
        jPanel44.add(jLabel302, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 530, -1, 20));

        删除特殊背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除特殊背包.setText("删除");
        删除特殊背包.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除特殊背包ActionPerformed(evt);
            }
        });
        jPanel44.add(删除特殊背包, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 550, -1, 30));

        jTabbedPane5.addTab("特殊背包", jPanel44);

        jPanel45.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "游戏仓库", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel45.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色游戏仓库.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色游戏仓库.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字", "物品数量"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色游戏仓库.getTableHeader().setReorderingAllowed(false);
        jScrollPane21.setViewportView(角色游戏仓库);

        jPanel45.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 860, 490));

        游戏仓库物品名字.setEditable(false);
        游戏仓库物品名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                游戏仓库物品名字ActionPerformed(evt);
            }
        });
        jPanel45.add(游戏仓库物品名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 550, 150, 30));

        游戏仓库物品序号.setEditable(false);
        游戏仓库物品序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                游戏仓库物品序号ActionPerformed(evt);
            }
        });
        jPanel45.add(游戏仓库物品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 550, 110, 30));

        游戏仓库物品代码.setEditable(false);
        游戏仓库物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                游戏仓库物品代码ActionPerformed(evt);
            }
        });
        jPanel45.add(游戏仓库物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 550, 110, 30));

        jLabel303.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel303.setText("序号；");
        jPanel45.add(jLabel303, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 530, -1, 20));

        jLabel304.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel304.setText("物品名字；");
        jPanel45.add(jLabel304, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 530, -1, 20));

        jLabel305.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel305.setText("物品代码；");
        jPanel45.add(jLabel305, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 530, -1, 20));

        删除游戏仓库.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除游戏仓库.setText("删除");
        删除游戏仓库.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除游戏仓库ActionPerformed(evt);
            }
        });
        jPanel45.add(删除游戏仓库, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 550, -1, 30));

        jTabbedPane5.addTab("游戏仓库", jPanel45);

        jPanel46.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "商城仓库", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel46.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色商城仓库.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色商城仓库.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字", "物品数量"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色商城仓库.getTableHeader().setReorderingAllowed(false);
        jScrollPane22.setViewportView(角色商城仓库);

        jPanel46.add(jScrollPane22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 860, 490));

        商城仓库物品名字.setEditable(false);
        商城仓库物品名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                商城仓库物品名字ActionPerformed(evt);
            }
        });
        jPanel46.add(商城仓库物品名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 550, 150, 30));

        商城仓库物品序号.setEditable(false);
        商城仓库物品序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                商城仓库物品序号ActionPerformed(evt);
            }
        });
        jPanel46.add(商城仓库物品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 550, 110, 30));

        商城仓库物品代码.setEditable(false);
        商城仓库物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                商城仓库物品代码ActionPerformed(evt);
            }
        });
        jPanel46.add(商城仓库物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 550, 110, 30));

        jLabel306.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel306.setText("序号；");
        jPanel46.add(jLabel306, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 530, -1, 20));

        jLabel307.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel307.setText("物品名字；");
        jPanel46.add(jLabel307, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 530, -1, 20));

        jLabel308.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel308.setText("物品代码；");
        jPanel46.add(jLabel308, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 530, -1, 20));

        删除商城仓库.setText("删除");
        删除商城仓库.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除商城仓库ActionPerformed(evt);
            }
        });
        jPanel46.add(删除商城仓库, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 550, -1, 30));

        jTabbedPane5.addTab("商城仓库", jPanel46);

        jPanel48.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "点券拍卖行", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel48.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色点券拍卖行.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色点券拍卖行.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字", "物品数量"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色点券拍卖行.getTableHeader().setReorderingAllowed(false);
        jScrollPane30.setViewportView(角色点券拍卖行);

        jPanel48.add(jScrollPane30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 860, 490));

        拍卖行物品名字1.setEditable(false);
        拍卖行物品名字1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                拍卖行物品名字1ActionPerformed(evt);
            }
        });
        jPanel48.add(拍卖行物品名字1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 550, 150, 30));

        角色点券拍卖行序号.setEditable(false);
        角色点券拍卖行序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                角色点券拍卖行序号ActionPerformed(evt);
            }
        });
        jPanel48.add(角色点券拍卖行序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 550, 110, 30));

        拍卖行物品代码1.setEditable(false);
        拍卖行物品代码1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                拍卖行物品代码1ActionPerformed(evt);
            }
        });
        jPanel48.add(拍卖行物品代码1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 550, 110, 30));

        jLabel354.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel354.setText("序号；");
        jPanel48.add(jLabel354, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 530, -1, 20));

        jLabel355.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel355.setText("物品名字；");
        jPanel48.add(jLabel355, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 530, -1, 20));

        jLabel356.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel356.setText("物品代码；");
        jPanel48.add(jLabel356, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 530, -1, 20));

        删除拍卖行1.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除拍卖行1.setText("删除");
        删除拍卖行1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除拍卖行1ActionPerformed(evt);
            }
        });
        jPanel48.add(删除拍卖行1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 550, -1, 30));

        jTabbedPane5.addTab("点券拍卖行", jPanel48);

        jPanel47.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "金币拍卖行", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel47.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色金币拍卖行.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色金币拍卖行.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色金币拍卖行.getTableHeader().setReorderingAllowed(false);
        jScrollPane23.setViewportView(角色金币拍卖行);

        jPanel47.add(jScrollPane23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 860, 490));

        拍卖行物品名字.setEditable(false);
        拍卖行物品名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                拍卖行物品名字ActionPerformed(evt);
            }
        });
        jPanel47.add(拍卖行物品名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 550, 150, 30));

        角色金币拍卖行序号.setEditable(false);
        角色金币拍卖行序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                角色金币拍卖行序号ActionPerformed(evt);
            }
        });
        jPanel47.add(角色金币拍卖行序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 550, 110, 30));

        拍卖行物品代码.setEditable(false);
        拍卖行物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                拍卖行物品代码ActionPerformed(evt);
            }
        });
        jPanel47.add(拍卖行物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 550, 110, 30));

        jLabel309.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel309.setText("序号；");
        jPanel47.add(jLabel309, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, -1, 20));

        jLabel310.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel310.setText("物品名字；");
        jPanel47.add(jLabel310, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 530, -1, 20));

        jLabel311.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel311.setText("物品代码；");
        jPanel47.add(jLabel311, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 530, -1, 20));

        删除拍卖行.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除拍卖行.setText("删除");
        删除拍卖行.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除拍卖行ActionPerformed(evt);
            }
        });
        jPanel47.add(删除拍卖行, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 550, -1, 30));

        jTabbedPane5.addTab("金币拍卖行", jPanel47);

        角色背包.add(jTabbedPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 640));

        jTabbedPane8.addTab("角色道具信息", 角色背包);

        技能.setBackground(new java.awt.Color(250, 250, 250));
        技能.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "角色技能", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        技能.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        技能信息.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        技能信息.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "技能名字", "技能代码", "目前等级", "最高等级"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        技能信息.getTableHeader().setReorderingAllowed(false);
        jScrollPane14.setViewportView(技能信息);

        技能.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 870, 430));

        技能代码.setEditable(false);
        技能代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        技能.add(技能代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 490, 120, 30));

        技能目前等级.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        技能.add(技能目前等级, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 490, 120, 30));

        技能最高等级.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        技能.add(技能最高等级, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 490, 120, 30));

        技能名字.setEditable(false);
        技能名字.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        技能名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                技能名字ActionPerformed(evt);
            }
        });
        技能.add(技能名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 490, 120, 30));

        jLabel86.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel86.setText("技能代码:");
        技能.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 470, -1, -1));

        jLabel89.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel89.setText("目前等级:");
        技能.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 470, -1, -1));

        jLabel107.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel107.setText("最高等级:");
        技能.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 470, -1, -1));

        修改技能.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        修改技能.setText("修改");
        修改技能.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改技能ActionPerformed(evt);
            }
        });
        技能.add(修改技能, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 530, 120, 40));

        技能序号.setEditable(false);
        技能序号.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        技能.add(技能序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 490, 80, 30));

        jLabel188.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel188.setText("技能名字:");
        技能.add(jLabel188, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 470, -1, -1));

        jLabel204.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        jLabel204.setText("序号:");
        技能.add(jLabel204, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 470, -1, -1));

        jLabel205.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        jLabel205.setText("提示:技能无法超出正常范围值。");
        技能.add(jLabel205, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 580, 360, 30));

        删除技能.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        删除技能.setText("删除");
        删除技能.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除技能ActionPerformed(evt);
            }
        });
        技能.add(删除技能, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 530, 120, 40));

        修改技能1.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        修改技能1.setText("刷新");
        修改技能1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改技能1ActionPerformed(evt);
            }
        });
        技能.add(修改技能1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 530, 120, 40));

        jTabbedPane8.addTab("角色技能信息", 技能);

        jPanel50.setBackground(new java.awt.Color(250, 250, 250));

        家族信息.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族信息.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "家族ID", "家族名称", "族长/角色ID", "成员2", "成员3", "成员4", "成员5", "家族GP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        家族信息.getTableHeader().setReorderingAllowed(false);
        jScrollPane24.setViewportView(家族信息);

        刷新家族信息.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        刷新家族信息.setText("刷新家族信息");
        刷新家族信息.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新家族信息ActionPerformed(evt);
            }
        });

        jLabel194.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel194.setText("家族ID:");

        家族ID.setEditable(false);
        家族ID.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                家族IDActionPerformed(evt);
            }
        });

        家族名称.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族名称.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                家族名称ActionPerformed(evt);
            }
        });

        jLabel195.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel195.setText("家族名称:");

        家族族长.setEditable(false);
        家族族长.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族族长.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                家族族长ActionPerformed(evt);
            }
        });

        jLabel196.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel196.setText("家族族长；");

        jLabel198.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel198.setText("家族成员2；");

        家族成员2.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族成员2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                家族成员2ActionPerformed(evt);
            }
        });

        jLabel199.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel199.setText("家族成员3；");

        家族成员3.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族成员3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                家族成员3ActionPerformed(evt);
            }
        });

        jLabel200.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel200.setText("家族成员4；");

        家族成员4.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族成员4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                家族成员4ActionPerformed(evt);
            }
        });

        jLabel313.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel313.setText("家族成员5；");

        家族成员5.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族成员5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                家族成员5ActionPerformed(evt);
            }
        });

        jLabel314.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jLabel314.setText("家族GP；");

        家族GP.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族GP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                家族GPActionPerformed(evt);
            }
        });

        jButton34.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jButton34.setText("更改家族GP点数");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane24))
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(刷新家族信息, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel194, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(家族ID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel50Layout.createSequentialGroup()
                                .addComponent(家族名称, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(家族族长, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(家族成员2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(家族成员3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(家族成员4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel50Layout.createSequentialGroup()
                                .addComponent(jLabel195)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel196)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel198)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel199)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel200)))))
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel50Layout.createSequentialGroup()
                                .addComponent(家族成员5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(家族GP, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel50Layout.createSequentialGroup()
                                .addComponent(jLabel313)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel314))))
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(351, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane24, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel194)
                        .addComponent(jLabel195)
                        .addComponent(jLabel196)
                        .addComponent(jLabel198)
                        .addComponent(jLabel199)
                        .addComponent(jLabel200)
                        .addComponent(jLabel313)
                        .addComponent(jLabel314))
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(家族ID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(家族名称, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(家族族长, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(家族成员2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(家族成员3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(家族成员4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(家族成员5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(家族GP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(22, 22, 22)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(刷新家族信息, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jTabbedPane8.addTab("游戏家族", jPanel50);

        jPanel65.setBackground(new java.awt.Color(250, 250, 250));
        jPanel65.setBorder(javax.swing.BorderFactory.createTitledBorder("MAC/IP封禁"));

        封IP.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        封IP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序列号", "IP地址"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane107.setViewportView(封IP);

        封MAC.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        封MAC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序列号", "MAC地址"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane108.setViewportView(封MAC);

        刷新封IP.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        刷新封IP.setText("刷新");
        刷新封IP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新封IPActionPerformed(evt);
            }
        });

        刷新封MAC.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        刷新封MAC.setText("刷新");
        刷新封MAC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新封MACActionPerformed(evt);
            }
        });

        删除MAC.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        删除MAC.setText("删除");
        删除MAC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除MACActionPerformed(evt);
            }
        });

        删除IP.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        删除IP.setText("删除");
        删除IP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除IPActionPerformed(evt);
            }
        });

        jLabel346.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel346.setText("序号；");

        jLabel347.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel347.setText("序号；");

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel347)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(删MAC代码, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(删除MAC, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(刷新封MAC, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(jLabel346)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(删除IP代码, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(删除IP, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(刷新封IP, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160))
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addComponent(jScrollPane108, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane107, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane108, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                    .addComponent(jScrollPane107))
                .addGap(20, 20, 20)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(删MAC代码, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel347, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(删除MAC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(刷新封MAC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(删除IP代码, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel346, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(删除IP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(刷新封IP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane8.addTab("MAC/IP封禁", jPanel65);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jTabbedPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 1224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("人物信息", jPanel21);

        jPanel22.setBackground(new java.awt.Color(250, 250, 250));

        jTabbedPane3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        帽子.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        帽子.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801313.png"))); // NOI18N
        帽子.setText("帽子");
        帽子.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">帽子</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">帽子</font></strong>分类下的所有商品<br> ");
        帽子.setPreferredSize(new java.awt.Dimension(30, 27));
        帽子.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                帽子ActionPerformed(evt);
            }
        });
        jPanel25.add(帽子, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 40));

        脸饰.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        脸饰.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801290.png"))); // NOI18N
        脸饰.setText("脸饰");
        脸饰.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">脸饰</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">脸饰</font></strong>分类下的所有商品<br> ");
        脸饰.setPreferredSize(new java.awt.Dimension(30, 27));
        脸饰.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                脸饰ActionPerformed(evt);
            }
        });
        jPanel25.add(脸饰, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 80, 40));

        眼饰.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        眼饰.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801294.png"))); // NOI18N
        眼饰.setText("眼饰");
        眼饰.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">眼饰</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">眼饰</font></strong>分类下的所有商品<br> ");
        眼饰.setPreferredSize(new java.awt.Dimension(30, 27));
        眼饰.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                眼饰ActionPerformed(evt);
            }
        });
        jPanel25.add(眼饰, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 0, 80, 40));

        长袍.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        长袍.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801289.png"))); // NOI18N
        长袍.setText("长袍");
        长袍.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">长袍</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">长袍</font></strong>分类下的所有商品<br> ");
        长袍.setPreferredSize(new java.awt.Dimension(30, 27));
        长袍.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                长袍ActionPerformed(evt);
            }
        });
        jPanel25.add(长袍, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 80, 40));

        上衣.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        上衣.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801297.png"))); // NOI18N
        上衣.setText("上衣");
        上衣.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">上衣</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">上衣</font></strong>分类下的所有商品<br> ");
        上衣.setPreferredSize(new java.awt.Dimension(30, 27));
        上衣.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                上衣ActionPerformed(evt);
            }
        });
        jPanel25.add(上衣, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 0, 80, 40));

        裙裤.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        裙裤.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801286.png"))); // NOI18N
        裙裤.setText("裙裤");
        裙裤.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">裙裤</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">裙裤</font></strong>分类下的所有商品<br> ");
        裙裤.setPreferredSize(new java.awt.Dimension(30, 27));
        裙裤.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                裙裤ActionPerformed(evt);
            }
        });
        jPanel25.add(裙裤, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, 80, 40));

        鞋子.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        鞋子.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801291.png"))); // NOI18N
        鞋子.setText("鞋子");
        鞋子.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">鞋子</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">鞋子</font></strong>分类下的所有商品<br> ");
        鞋子.setPreferredSize(new java.awt.Dimension(30, 27));
        鞋子.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                鞋子ActionPerformed(evt);
            }
        });
        jPanel25.add(鞋子, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 0, 80, 40));

        手套.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        手套.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801295.png"))); // NOI18N
        手套.setText("手套");
        手套.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">手套</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">手套</font></strong>分类下的所有商品<br> ");
        手套.setPreferredSize(new java.awt.Dimension(30, 27));
        手套.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                手套ActionPerformed(evt);
            }
        });
        jPanel25.add(手套, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 80, 40));

        武器.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        武器.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801296.png"))); // NOI18N
        武器.setText("武器");
        武器.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">武器</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">武器</font></strong>分类下的所有商品<br> ");
        武器.setPreferredSize(new java.awt.Dimension(30, 27));
        武器.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                武器ActionPerformed(evt);
            }
        });
        jPanel25.add(武器, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 0, 80, 40));

        戒指.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        戒指.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801293.png"))); // NOI18N
        戒指.setText("戒指");
        戒指.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">戒指</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">戒指</font></strong>分类下的所有商品<br> ");
        戒指.setPreferredSize(new java.awt.Dimension(30, 27));
        戒指.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                戒指ActionPerformed(evt);
            }
        });
        jPanel25.add(戒指, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 0, 80, 40));

        飞镖.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        飞镖.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801288.png"))); // NOI18N
        飞镖.setText("飞镖");
        飞镖.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">飞镖</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">飞镖</font></strong>分类下的所有商品<br> ");
        飞镖.setPreferredSize(new java.awt.Dimension(30, 27));
        飞镖.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                飞镖ActionPerformed(evt);
            }
        });
        jPanel25.add(飞镖, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 80, 40));

        披风.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        披风.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801287.png"))); // NOI18N
        披风.setText("披风");
        披风.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">披风</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">披风</font></strong>分类下的所有商品<br> ");
        披风.setPreferredSize(new java.awt.Dimension(30, 27));
        披风.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                披风ActionPerformed(evt);
            }
        });
        jPanel25.add(披风, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 80, 40));

        骑宠.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        骑宠.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801292.png"))); // NOI18N
        骑宠.setText("骑宠");
        骑宠.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">骑宠</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">骑宠</font></strong>分类下的所有商品<br> ");
        骑宠.setPreferredSize(new java.awt.Dimension(30, 27));
        骑宠.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                骑宠ActionPerformed(evt);
            }
        });
        jPanel25.add(骑宠, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 0, 80, 40));

        jTabbedPane3.addTab("装备", jPanel25);

        喜庆物品.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        喜庆物品.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801309.png"))); // NOI18N
        喜庆物品.setText("喜庆物品");
        喜庆物品.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">喜庆物品</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">喜庆物品</font></strong>分类下的所有商品<br> ");
        喜庆物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                喜庆物品ActionPerformed(evt);
            }
        });

        通讯物品.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        通讯物品.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801315.png"))); // NOI18N
        通讯物品.setText("通讯物品");
        通讯物品.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">通讯物品</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">通讯物品</font></strong>分类下的所有商品<br> ");
        通讯物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                通讯物品ActionPerformed(evt);
            }
        });

        卷轴.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        卷轴.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3801310.png"))); // NOI18N
        卷轴.setText("卷轴");
        卷轴.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">卷轴</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">卷轴</font></strong>分类下的所有商品<br> ");
        卷轴.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                卷轴ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(喜庆物品, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(通讯物品, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(卷轴, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(卷轴, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(喜庆物品, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(通讯物品, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane3.addTab("消耗", jPanel26);

        jPanel29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        会员卡.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        会员卡.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/日志.png"))); // NOI18N
        会员卡.setText("会员卡");
        会员卡.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">会员卡</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">会员卡</font></strong>分类下的所有商品<br> ");
        会员卡.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                会员卡ActionPerformed(evt);
            }
        });
        jPanel29.add(会员卡, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 40));

        表情.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        表情.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/111.png"))); // NOI18N
        表情.setText("表情");
        表情.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">表情</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">表情</font></strong>分类下的所有商品<br> ");
        表情.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                表情ActionPerformed(evt);
            }
        });
        jPanel29.add(表情, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 100, 40));

        个人商店.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        个人商店.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/商店管理.png"))); // NOI18N
        个人商店.setText("个人商店");
        个人商店.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">个人商店</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">个人商店</font></strong>分类下的所有商品<br> ");
        个人商店.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                个人商店ActionPerformed(evt);
            }
        });
        jPanel29.add(个人商店, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 100, 40));

        纪念日.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        纪念日.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/提交问题反馈.png"))); // NOI18N
        纪念日.setText("纪念日");
        纪念日.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                纪念日ActionPerformed(evt);
            }
        });
        jPanel29.add(纪念日, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 100, 40));

        游戏.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        游戏.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/3994264.png"))); // NOI18N
        游戏.setText("游戏");
        游戏.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">游戏</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">游戏</font></strong>分类下的所有商品<br> ");
        游戏.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                游戏ActionPerformed(evt);
            }
        });
        jPanel29.add(游戏, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 100, 40));

        效果.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        效果.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/1802034.png"))); // NOI18N
        效果.setText("效果");
        效果.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">效果</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">效果</font></strong>分类下的所有商品<br> ");
        效果.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                效果ActionPerformed(evt);
            }
        });
        jPanel29.add(效果, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 100, 40));

        jTabbedPane3.addTab("其他", jPanel29);

        宠物.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        宠物.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/4030011.png"))); // NOI18N
        宠物.setText("宠物");
        宠物.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">宠物</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">宠物</font></strong>分类下的所有商品<br> ");
        宠物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                宠物ActionPerformed(evt);
            }
        });

        宠物服饰.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        宠物服饰.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/自定义BUFF.png"))); // NOI18N
        宠物服饰.setText("宠物服饰");
        宠物服饰.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">宠物服饰</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">宠物服饰</font></strong>分类下的所有商品<br> ");
        宠物服饰.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                宠物服饰ActionPerformed(evt);
            }
        });

        其他.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        其他.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/关于.png"))); // NOI18N
        其他.setText("其他");
        其他.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                其他ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(宠物, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(宠物服饰, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(其他, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(其他, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(宠物服饰, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(宠物, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane3.addTab("宠物", jPanel31);

        jPanel24.setBackground(new java.awt.Color(250, 250, 250));

        主题馆.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        主题馆.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/2040335.png"))); // NOI18N
        主题馆.setText("主题馆");
        主题馆.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">主题馆</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">主体馆</font></strong>分类下的所有商品<br> ");
        主题馆.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                主题馆ActionPerformed(evt);
            }
        });

        读取热销产品.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        读取热销产品.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/2000001.png"))); // NOI18N
        读取热销产品.setText("热销产品");
        读取热销产品.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">热销产品</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">热销产品</font></strong>分类下的所有商品<br> ");
        读取热销产品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                读取热销产品ActionPerformed(evt);
            }
        });

        活动.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        活动.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/2614075.png"))); // NOI18N
        活动.setText("活动");
        活动.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">活动</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">活动</font></strong>分类下的所有商品<br> ");
        活动.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                活动ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image2/代码查询器.png"))); // NOI18N
        jButton9.setText("每日特卖");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(读取热销产品, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(主题馆)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(活动, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(读取热销产品)
                        .addComponent(主题馆, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(活动, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane3.addTab("热销产品", jPanel24);

        charTable.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        charTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "商品编码", "物品代码", "道具名称", "数量", "价格", "天/限时", "出售状态", "上/下架", "已售出", "库存", "反馈%", "每日限购", "仅点券购买"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        charTable.setToolTipText("");
        charTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(charTable);

        jPanel33.setBackground(new java.awt.Color(250, 250, 250));
        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "添加值", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel33.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        商品数量.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel33.add(商品数量, new org.netbeans.lib.awtextra.AbsoluteConstraints(235, 55, 65, -1));

        商品编码.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        商品编码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                商品编码ActionPerformed(evt);
            }
        });
        jPanel33.add(商品编码, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 25, 65, 20));

        商品代码.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel33.add(商品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 55, 65, -1));

        jLabel30.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel30.setText("商品数量:");
        jPanel33.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, -1, 30));

        jLabel31.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel31.setText("商品代码:");
        jPanel33.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, 30));

        商品价格.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel33.add(商品价格, new org.netbeans.lib.awtextra.AbsoluteConstraints(234, 25, 65, -1));

        商品时间.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        商品时间.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                商品时间ActionPerformed(evt);
            }
        });
        jPanel33.add(商品时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 55, 65, -1));

        jLabel32.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel32.setText("商品库存:");
        jPanel33.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, 90, 30));

        jLabel33.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel33.setText("限时时间:");
        jPanel33.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, -1, 30));

        jLabel34.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel34.setText("商品编码:");
        jPanel33.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 30));

        jLabel35.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel35.setText("商品价格:");
        jPanel33.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 90, 30));

        商品库存.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel33.add(商品库存, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 25, 65, -1));

        商品折扣.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel33.add(商品折扣, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 25, 65, -1));

        jLabel37.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel37.setText("商品反馈:");
        jPanel33.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 90, 30));

        jLabelUseNX.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabelUseNX.setText("点券购买:");
        jPanel33.add(jLabelUseNX, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, 70, 30));

        每日限购.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel33.add(每日限购, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 55, 65, -1));

        货币类型.setEditable(false);
        货币类型.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        货币类型.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                货币类型ActionPerformed(evt);
            }
        });
        jPanel33.add(货币类型, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 25, 70, -1));

        jLabel41.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel41.setText("每日限购:");
        jPanel33.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 50, 90, 30));

        点券购买.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                点券购买ActionPerformed(evt);
            }
        });
        jPanel33.add(点券购买, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 50, 70, -1));

        jLabel43.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel43.setText("货币类型:");
        jPanel33.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 90, 30));

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel67.setBackground(new java.awt.Color(250, 250, 250));
        jPanel67.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "修改值", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel67.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N
        jPanel67.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton27.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jButton27.setForeground(new java.awt.Color(0, 153, 204));
        jButton27.setText("删除");
        jButton27.setToolTipText("<html>\n<strong><font color=\"#FF0000\">删除；</font></strong><br>\n1.选择物品<br>\n2.删除<br>");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        jPanel67.add(jButton27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 70, 25));

        jButton25.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jButton25.setForeground(new java.awt.Color(51, 51, 255));
        jButton25.setText("上架");
        jButton25.setToolTipText("<html>\n<strong><font color=\"#FF0000\">上架；</font></strong><br>\n1.选择物品<br>\n2.上架/下架<br>");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        jPanel67.add(jButton25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 70, 25));

        jButton28.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jButton28.setForeground(new java.awt.Color(153, 102, 255));
        jButton28.setText("下架");
        jButton28.setToolTipText("<html>\n<strong><font color=\"#FF0000\">下架；</font></strong><br>\n1.选择物品<br>\n2.上架/下架<br>");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        jPanel67.add(jButton28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 70, 25));

        jButton2.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 0, 255));
        jButton2.setText("刷新");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel67.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 70, 25));

        修改.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        修改.setForeground(new java.awt.Color(51, 51, 51));
        修改.setText("修改");
        修改.setToolTipText("<html>\n<strong><font color=\"#FF0000\">修改；</font></strong><br> \n1.在列表中选择需要修改的物品<br>\n2.在文本框中输入修改值<br>\n");
        修改.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改ActionPerformed(evt);
            }
        });
        jPanel67.add(修改, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 70, 25));

        添加.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        添加.setForeground(new java.awt.Color(0, 204, 204));
        添加.setText("添加");
        添加.setToolTipText("<html>\n<strong><font color=\"#FF0000\">添加；</font></strong><br> \n1.选择物品分类<br>\n2.输入商品代码<br>\n3.输入商品数量<br>\n4.输入商品价格<br>\n5.输入限时时间(0代表永久)<br>\n6.选择出售状态<br>");
        添加.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                添加ActionPerformed(evt);
            }
        });
        jPanel67.add(添加, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 70, 25));

        商城扩充价格.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        商城扩充价格.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "背包扩充价格"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        商城扩充价格.getTableHeader().setReorderingAllowed(false);
        jScrollPane132.setViewportView(商城扩充价格);

        商城扩充价格修改.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N

        修改背包扩充价格.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        修改背包扩充价格.setText("修改");
        修改背包扩充价格.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改背包扩充价格ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jButton3.setForeground(new java.awt.Color(204, 0, 0));
        jButton3.setText("重载商城");
        jButton3.setToolTipText("<html>\n<strong><font color=\"#FF0000\">重载商城；</font></strong><br>\n在商城控制台中的修改需要重载才能在游戏中生效");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        显示类型.setEditable(false);
        显示类型.setFont(new java.awt.Font("幼圆", 1, 14)); // NOI18N
        显示类型.setForeground(new java.awt.Color(255, 0, 51));
        显示类型.setText("测试字体");
        显示类型.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                显示类型ActionPerformed(evt);
            }
        });

        商品出售状态.setEditable(false);
        商品出售状态.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        商品出售状态.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                商品出售状态ActionPerformed(evt);
            }
        });

        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF4.png"))); // NOI18N
        jButton19.setText("全部商城折扣");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setText("确认修改");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addContainerGap(1096, Short.MAX_VALUE)
                        .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(384, 384, 384))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel22Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel22Layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel22Layout.createSequentialGroup()
                                        .addGap(81, 81, 81)
                                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField17)
                                            .addComponent(jButton20, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))))
                                .addGap(49, 49, 49)
                                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(商品出售状态, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                    .addComponent(jScrollPane132, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(修改背包扩充价格, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(商城扩充价格修改)
                                    .addComponent(显示类型, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)))
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel67, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                        .addGap(2, 2, 2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jPanel67, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addComponent(商城扩充价格修改, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(修改背包扩充价格))
                            .addComponent(jScrollPane132, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(显示类型, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(商品出售状态, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton20))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("游戏商城", jPanel22);

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));

        jPanel36.setBackground(new java.awt.Color(250, 250, 250));

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1227, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        游戏商店2.setBackground(new java.awt.Color(153, 153, 153));
        游戏商店2.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        游戏商店2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "商店ID", "物品代码", "销售金币", "物品名称"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        游戏商店2.getTableHeader().setReorderingAllowed(false);
        jScrollPane25.setViewportView(游戏商店2);

        jPanel56.setBackground(new java.awt.Color(250, 250, 250));
        jPanel56.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "查询商品出售物品", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel56.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        删除商品.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        删除商品.setText("删除");
        删除商品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除商品ActionPerformed(evt);
            }
        });
        jPanel56.add(删除商品, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 70, -1, 30));

        新增商品.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        新增商品.setText("新增");
        新增商品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                新增商品ActionPerformed(evt);
            }
        });
        jPanel56.add(新增商品, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 70, -1, 30));

        商品序号.setEditable(false);
        商品序号.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel56.add(商品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 80, 30));

        商店代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        商店代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                商店代码ActionPerformed(evt);
            }
        });
        jPanel56.add(商店代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 80, 30));

        商品物品代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel56.add(商品物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 90, 30));

        商品售价金币.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel56.add(商品售价金币, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 100, 30));

        jLabel268.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel268.setText("出售金币");
        jPanel56.add(jLabel268, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, -1, -1));

        jLabel269.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel269.setText("序号");
        jPanel56.add(jLabel269, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel271.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel271.setText("物品名称");
        jPanel56.add(jLabel271, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, -1, -1));

        jLabel272.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel272.setText("商店ID");
        jPanel56.add(jLabel272, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, -1));

        修改商品.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        修改商品.setText("修改");
        修改商品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改商品ActionPerformed(evt);
            }
        });
        jPanel56.add(修改商品, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 70, -1, 30));

        商品名称.setEditable(false);
        商品名称.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        商品名称.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                商品名称ActionPerformed(evt);
            }
        });
        jPanel56.add(商品名称, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 70, 100, 30));

        jLabel273.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel273.setText("物品代码");
        jPanel56.add(jLabel273, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, -1, -1));

        jPanel55.setBackground(new java.awt.Color(250, 250, 250));
        jPanel55.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "查询商品出售物品", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel55.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        查询商店2.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        查询商店2.setText("查询商店");
        查询商店2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查询商店2ActionPerformed(evt);
            }
        });
        jPanel55.add(查询商店2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 50, -1, 30));

        查询商店.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel55.add(查询商店, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 110, 30));

        jLabel270.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jLabel270.setText("商店ID");
        jPanel55.add(jLabel270, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, -1, 30));

        jButton33.setFont(new java.awt.Font("微软雅黑", 0, 15)); // NOI18N
        jButton33.setText("查看全部");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });
        jPanel55.add(jButton33, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 40, 258, 63));

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane25)
                    .addComponent(jPanel55, javax.swing.GroupLayout.DEFAULT_SIZE, 1095, Short.MAX_VALUE)
                    .addComponent(jPanel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel56, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                    .addContainerGap(631, Short.MAX_VALUE)
                    .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(48, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("游戏商店", jPanel35);

        jPanel11.setBackground(new java.awt.Color(250, 250, 250));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("游戏公告"));

        sendNotice.setText("蓝色提示公告");
        sendNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendNoticeActionPerformed(evt);
            }
        });

        sendWinNotice.setText("顶部滚动公告");
        sendWinNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendWinNoticeActionPerformed(evt);
            }
        });

        sendMsgNotice.setText("弹窗公告");
        sendMsgNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMsgNoticeActionPerformed(evt);
            }
        });

        sendNpcTalkNotice.setText("蓝色公告事项");
        sendNpcTalkNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendNpcTalkNoticeActionPerformed(evt);
            }
        });

        noticeText.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        noticeText.setText("游戏即将维护,请安全下线！造成不便请谅解！");

        jLabel117.setFont(new java.awt.Font("幼圆", 0, 24)); // NOI18N
        jLabel117.setText("1、不得散布谣言，扰乱社会秩序，破坏社会稳定的信息 ");

        jLabel118.setFont(new java.awt.Font("幼圆", 0, 24)); // NOI18N
        jLabel118.setText("2、不得散布赌博、暴力、凶杀、恐怖或者教唆犯罪的信息");

        jLabel119.setFont(new java.awt.Font("幼圆", 0, 24)); // NOI18N
        jLabel119.setText("3、不得侮辱或者诽谤他人，侵害他人合法权益");

        jLabel106.setFont(new java.awt.Font("幼圆", 0, 24)); // NOI18N
        jLabel106.setText("4、不得含有法律、行政法规禁止的其他内容");

        公告发布喇叭代码.setForeground(new java.awt.Color(255, 51, 102));
        公告发布喇叭代码.setText("5120027");
        公告发布喇叭代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                公告发布喇叭代码ActionPerformed(evt);
            }
        });

        jButton45.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jButton45.setText("屏幕正中公告");
        jButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton45ActionPerformed(evt);
            }
        });

        jLabel259.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel259.setText("喇叭代码");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(noticeText)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(89, 89, 89))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(sendNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sendWinNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sendMsgNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sendNpcTalkNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton45, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel259)
                    .addComponent(公告发布喇叭代码, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(400, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(noticeText, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(公告发布喇叭代码, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(sendNpcTalkNotice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(sendWinNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(sendMsgNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(sendNotice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel259)))
                .addGap(29, 29, 29)
                .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );

        jTabbedPane2.addTab("游戏公告", jPanel11);

        jPanel68.setBackground(new java.awt.Color(250, 250, 250));
        jPanel68.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "物品叠加", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF4.png"))); // NOI18N
        jButton1.setText("其他栏");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField3.setText("其他栏叠加数量");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(255, 102, 102));
        jLabel5.setText("修改好点击确定修改即时生效，无需重启服务端");

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF4.png"))); // NOI18N
        jButton4.setText("消耗栏");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextField4.setText("消耗栏叠加数量");
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel7.setText("叠加数量排除项:");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jButton5.setText("确定修改");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel68Layout = new javax.swing.GroupLayout(jPanel68);
        jPanel68.setLayout(jPanel68Layout);
        jPanel68Layout.setHorizontalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel68Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel68Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel68Layout.createSequentialGroup()
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel68Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))))
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel68Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
        );
        jPanel68Layout.setVerticalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel68Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel68Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79))
        );

        jPanel71.setBackground(new java.awt.Color(250, 250, 250));
        jPanel71.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "分阶经验", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        jLabel27.setForeground(new java.awt.Color(255, 102, 102));
        jLabel27.setText("修改好点击确定修改即时生效，无需重启服务端");

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF4.png"))); // NOI18N
        jButton6.setText("一阶等级经验");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel6.setText("经验：");

        jLabel2.setText("-");

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF4.png"))); // NOI18N
        jButton10.setText("二阶等级经验");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel22.setText("-");

        jLabel21.setText("经验：");

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF4.png"))); // NOI18N
        jButton11.setText("三阶等级经验");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel26.setText("-");

        jLabel24.setText("经验：");

        jButton14.setText("确定修改");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel71Layout = new javax.swing.GroupLayout(jPanel71);
        jPanel71.setLayout(jPanel71Layout);
        jPanel71Layout.setHorizontalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel71Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel71Layout.createSequentialGroup()
                        .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel71Layout.createSequentialGroup()
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(58, 58, 58)
                        .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel71Layout.createSequentialGroup()
                                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel71Layout.createSequentialGroup()
                                .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 25, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel71Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel71Layout.createSequentialGroup()
                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(330, 330, 330))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel71Layout.createSequentialGroup()
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(236, 236, 236))))))
        );
        jPanel71Layout.setVerticalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel71Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE)
                    .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel21)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addComponent(jButton14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel73.setBackground(new java.awt.Color(250, 250, 250));
        jPanel73.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "自动测谎", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF4.png"))); // NOI18N
        jButton15.setText("定时测谎");
        jButton15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton15MouseClicked(evt);
            }
        });
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/OFF4.png"))); // NOI18N
        jButton17.setText("杀怪测谎");
        jButton17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton17MouseClicked(evt);
            }
        });
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jTextField16.setText("定时分钟数");
        jTextField16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField16ActionPerformed(evt);
            }
        });

        jTextField15.setText("怪物数量");
        jTextField15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField15ActionPerformed(evt);
            }
        });

        jButton18.setText("确定修改");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jLabel23.setText("分钟测谎一次");

        jLabel36.setForeground(new java.awt.Color(255, 102, 102));
        jLabel36.setText("修改好点击确定修改即时生效，无需重启服务端");

        jLabel42.setText("个怪测谎一次");

        javax.swing.GroupLayout jPanel73Layout = new javax.swing.GroupLayout(jPanel73);
        jPanel73.setLayout(jPanel73Layout);
        jPanel73Layout.setHorizontalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel73Layout.createSequentialGroup()
                .addGroup(jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel73Layout.createSequentialGroup()
                        .addComponent(jButton17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel73Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jButton15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23)
                    .addComponent(jLabel42))
                .addContainerGap(84, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel73Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel73Layout.createSequentialGroup()
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(150, 150, 150))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel73Layout.createSequentialGroup()
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73))))
        );
        jPanel73Layout.setVerticalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel73Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jButton18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel68, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                .addComponent(jPanel73, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel71, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(197, 197, 197))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel68, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel73, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(jPanel71, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );

        jTabbedPane2.addTab("特殊功能", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1238, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 718, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton45ActionPerformed
        sendNotice(4);
        System.out.println("[公告系统] 发送公告成功！");
        JOptionPane.showMessageDialog(null, "发送公告成功！");
    }//GEN-LAST:event_jButton45ActionPerformed

    private void 公告发布喇叭代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_公告发布喇叭代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_公告发布喇叭代码ActionPerformed

    private void sendNpcTalkNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendNpcTalkNoticeActionPerformed
        sendNotice(3);
        System.out.println("[公告系统] 发送黄色滚动公告成功！");
        JOptionPane.showMessageDialog(null, "发送黄色滚动公告成功！");
    }//GEN-LAST:event_sendNpcTalkNoticeActionPerformed

    private void sendMsgNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMsgNoticeActionPerformed
        sendNotice(2);
        System.out.println("[公告系统] 发送红色提示公告成功！");
        JOptionPane.showMessageDialog(null, "发送红色提示公告成功！");
    }//GEN-LAST:event_sendMsgNoticeActionPerformed

    private void sendWinNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendWinNoticeActionPerformed
        sendNotice(1);
        System.out.println("[公告系统] 发送弹窗公告成功！");
        JOptionPane.showMessageDialog(null, "发送弹窗公告成功！");
    }//GEN-LAST:event_sendWinNoticeActionPerformed

    private void sendNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendNoticeActionPerformed
        sendNotice(0);
        System.out.println("[公告系统] 发送蓝色公告事项公告成功！");
        JOptionPane.showMessageDialog(null, "发送蓝色公告事项公告成功！");
    }//GEN-LAST:event_sendNoticeActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed

        查询商店(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton33ActionPerformed

    private void 查询商店2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查询商店2ActionPerformed
        查询商店(1);
    }//GEN-LAST:event_查询商店2ActionPerformed

    private void 商品名称ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_商品名称ActionPerformed

    }//GEN-LAST:event_商品名称ActionPerformed

    private void 修改商品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改商品ActionPerformed
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.商品物品代码.getText().matches("[0-9]+");
        boolean result1 = this.商店代码.getText().matches("[0-9]+");
        boolean result2 = this.商品售价金币.getText().matches("[0-9]+");

        if (result && result1 && result2) {
            if (Integer.parseInt(this.商店代码.getText()) < 0 && Integer.parseInt(this.商品物品代码.getText()) < 0 && Integer.parseInt(this.商品售价金币.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "请填写正确的值");
            }
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE shopitems SET itemid = ?,price = ?,shopid = ?WHERE shopitemid = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM shopitems WHERE shopitemid = ?");
                ps1.setInt(1, Integer.parseInt(this.商品序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString1 = null;
                    String sqlString2 = null;
                    String sqlString3 = null;
                    sqlString1 = "update shopitems set itemid='" + this.商品物品代码.getText() + "' where shopitemid=" + this.商品序号.getText() + ";";
                    PreparedStatement itemid = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                    itemid.executeUpdate(sqlString1);

                    sqlString2 = "update shopitems set price='" + this.商品售价金币.getText() + "' where shopitemid=" + this.商品序号.getText() + ";";
                    PreparedStatement price = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    price.executeUpdate(sqlString2);

                    sqlString3 = "update shopitems set shopid='" + this.商店代码.getText() + "' where shopitemid=" + this.商品序号.getText() + ";";
                    PreparedStatement shopid = DatabaseConnection.getConnection().prepareStatement(sqlString3);
                    shopid.executeUpdate(sqlString3);

                    查询商店(1);
                }
                rs.close();
                ps1.close();
                JOptionPane.showMessageDialog(null, "[信息]:商店商品修改成功。");
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:选择你要修改的商品,并填写<商店ID><物品代码><售价金币>。");
        }
    }//GEN-LAST:event_修改商品ActionPerformed

    private void 商店代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_商店代码ActionPerformed

    }//GEN-LAST:event_商店代码ActionPerformed

    private void 新增商品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_新增商品ActionPerformed

        boolean result = this.商品物品代码.getText().matches("[0-9]+");
        boolean result1 = this.商店代码.getText().matches("[0-9]+");
        boolean result2 = this.商品售价金币.getText().matches("[0-9]+");

        if (result && result1 && result2) {
            if (Integer.parseInt(this.商店代码.getText()) < 0 && Integer.parseInt(this.商品物品代码.getText()) < 0 && Integer.parseInt(this.商品售价金币.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "[信息]:请填写正确的值。");
                return;
            }
            try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO shopitems (shopid ,itemid ,price ,pitch ,position ,reqitem ,reqitemq) VALUES ( ?, ?, ?, ?, ?, ?, ?)")) {
                ps.setInt(1, Integer.parseInt(this.商店代码.getText()));
                ps.setInt(2, Integer.parseInt(this.商品物品代码.getText()));
                ps.setInt(3, Integer.parseInt(this.商品售价金币.getText()));
                ps.setInt(4, 0);
                ps.setInt(5, 0);
                ps.setInt(6, 0);
                ps.setInt(7, 0);
                ps.executeUpdate();
                ps.close();
                查询商店(1);
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "[信息]:新增商店商品成功。");
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:输入<商店ID><物品代码><售价>。");
        }
    }//GEN-LAST:event_新增商品ActionPerformed

    private void 删除商品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除商品ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.商品序号.getText().matches("[0-9]+");
        if (result == true) {
            int 商城SN编码 = Integer.parseInt(this.商品序号.getText());
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM shopitems WHERE shopitemid = ?");
                ps1.setInt(1, 商城SN编码);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from shopitems where shopitemid =" + 商城SN编码 + "";
                    ps1.executeUpdate(sqlstr);
                    查询商店(1);
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "[信息]:删除商店商品成功。");
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请选择你要删除的商品。");
        }
    }//GEN-LAST:event_删除商品ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        JOptionPane.showMessageDialog(null, "[信息]:商城重载开始。");
        CashItemFactory.getInstance().clearCashShop();
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            cserv.broadcastPacket((MaplePacketCreator.serverNotice(0, ";商城重新载入商品成功，维护完毕，开放进入。")));
        }
        JOptionPane.showMessageDialog(null, "[信息]:商城重载成功。");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void 修改背包扩充价格ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改背包扩充价格ActionPerformed
        boolean result1 = this.商城扩充价格修改.getText().matches("[0-9]+");
        if (result1) {
            if (Integer.parseInt(this.商城扩充价格修改.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "[信息]:请输入正确的修改值。");
                return;
            }
            PreparedStatement ps1 = null;
            ResultSet rs = null;
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM configvalues WHERE id = ?");
                ps1.setInt(1, 1);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from configvalues where id =999";
                    ps1.executeUpdate(sqlstr);

                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class
                    .getName()).log(Level.SEVERE, null, ex);
            }
            try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO configvalues (id, name,Val) VALUES ( ?, ?, ?)")) {
                ps.setInt(1, 999);
                ps.setString(2, "商城扩充价格");
                ps.setInt(3, Integer.parseInt(this.商城扩充价格修改.getText()));
                ps.executeUpdate();
                刷新商城扩充价格();
                server.Start.GetConfigValues();
                JOptionPane.showMessageDialog(null, "[信息]:商城扩充背包价格修改成功，已经生效。");

            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class
                    .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_修改背包扩充价格ActionPerformed

    private void 添加ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_添加ActionPerformed
        boolean result1 = this.商品编码.getText().matches("[0-9]+");
        boolean result2 = this.商品数量.getText().matches("[0-9]+");
        boolean result3 = this.商品价格.getText().matches("[0-9]+");
        boolean result4 = this.商品时间.getText().matches("[0-9]+") || this.商品时间.getText().contains(",");
        boolean result5 = this.商品库存.getText().matches("[0-9]+");
        boolean result6 = this.每日限购.getText().matches("[0-9]+");
        boolean result7 = this.商品折扣.getText().matches("[0-9]+");
        boolean result8 = this.商品代码.getText().matches("[0-9]+");
        boolean result9 = this.点券购买.getText().matches("[0-9]+");

        if (!result1 && !result2 && !result3 && !result4 && !result5 && !result6 && !result7 && !result8 && !result9) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入正确的数据。");
            return;
        }
        if (商品编码.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请点击商品分类选择添加类型。");
            return;
        }
        if (商品代码.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入添加的商品代码。");
            return;
        }
        if (商品价格.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入商品价格。");
            return;
        }
        if (Integer.parseInt(this.商品价格.getText()) > 999999999) {
            JOptionPane.showMessageDialog(null, "[信息]:商品数量不能大于999999999。");
            return;
        }
        if (商品时间.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入商品的给予时间，0 代表无限制。");
            return;
        }
        if (商品数量.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入商品的商品数量。");
            return;
        }

        if (点券购买.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入点券购买的类型。");
            return;
        }
        if (Integer.parseInt(this.商品数量.getText()) > 100) {
            JOptionPane.showMessageDialog(null, "[信息]:商品数量不能大于100。");
            return;
        }
        int 商品出售状态2;
        if (商品出售状态.getText().equals("")) {
            商品出售状态2 = -1;
        } else {
            商品出售状态2 = Integer.parseInt(this.商品出售状态.getText());
        }
        //支持以多个逗号分割的插入 2021/07/30 guozi
        int serial = Integer.parseInt(this.商品编码.getText());
        String [] ItemidList = this.商品代码.getText().split(",");
        String[] split = this.商品时间.getText().split(",");

        for (int i = 0; i < ItemidList.length; i++) {
            try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO cashshop_modified_items (serial, showup,itemid,priority,period,gender,count,meso,discount_price,mark, unk_1, unk_2, unk_3, use_nx, period_hour, day_limit) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                ps.setInt(1, serial + i + 1);
                ps.setInt(2, 1);
                ps.setInt(3, Integer.parseInt(ItemidList[i]));
                ps.setInt(4, 0);
                ps.setInt(5, Integer.parseInt(split[0]));
                ps.setInt(6, 2);
                ps.setInt(7, Integer.parseInt(this.商品数量.getText()));
                ps.setInt(8, 0);
                ps.setInt(9, Integer.parseInt(this.商品价格.getText()));
                ps.setInt(10, 商品出售状态2);
                ps.setInt(11, 0);
                ps.setInt(12, 0);
                ps.setInt(13, 0);
                ps.setInt(14, Integer.parseInt(this.点券购买.getText()));
                ps.setInt(15, split.length > 1 ? Integer.parseInt(split[1]):0);
                ps.setInt(16, Integer.parseInt(this.每日限购.getText()));
                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!商品库存.getText().equals("")) {
                int SN库存 = Getcharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 2);
                if (SN库存 == -1) {
                    Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 2, -2);
                }
                Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 2, -SN库存);
                Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 2, Integer.parseInt(this.商品库存.getText()));
            }
            if (!商品折扣.getText().equals("")) {
                int SN库存 = Getcharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 3);
                if (SN库存 == -1) {
                    Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 3, -2);
                }
                Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 3, -SN库存);
                Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 3, Integer.parseInt(this.商品折扣.getText()));
            }
        }

        JOptionPane.showMessageDialog(null, "[信息]:新物品载入成功。");
        int n = JOptionPane.showConfirmDialog(this, "是否刷新？\r\n刷新所耗时间会根据物品数量，服务器配置决定。", "信息", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            刷新();
        }
    }//GEN-LAST:event_添加ActionPerformed

    private void 修改ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改ActionPerformed
        boolean result1 = this.商品编码.getText().matches("[0-9]+");
        boolean result2 = this.商品数量.getText().matches("[0-9]+");
        boolean result3 = this.商品价格.getText().matches("[0-9]+");
        boolean result4 = this.商品时间.getText().matches("[0-9]+") || this.商品时间.getText().contains(",");
        boolean result5 = this.商品库存.getText().matches("[0-9]+");
        boolean result6 = this.每日限购.getText().matches("[0-9]+");
        boolean result7 = this.商品折扣.getText().matches("[0-9]+");
        boolean result8 = this.商品代码.getText().matches("[0-9]+");
        boolean result9 = this.点券购买.getText().matches("[0-9]+");
        if (!result1 && !result2 && !result3 && !result4 && !result5 && !result6 && !result7 && !result8 && !result9) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入正确的数据。");
            return;
        }
        if (商品编码.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请点击商品分类选择添加类型。");
            return;
        }
        if (商品代码.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入添加的商品代码。");
            return;
        }
        if (商品价格.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入商品价格。");
            return;
        }
        if (Integer.parseInt(this.商品价格.getText()) > 999999999) {
            JOptionPane.showMessageDialog(null, "[信息]:商品数量不能大于999999999。");
            return;
        }
        if (商品时间.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入商品的给予时间，0 代表无限制。");
            return;
        }
        if (商品数量.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入商品的商品数量。");
            return;
        }
        if (点券购买.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入是否点券购买类型，1为仅点券，2为点券和抵用卷均可。");
            return;
        }
        if (Integer.parseInt(this.商品数量.getText()) > 100) {
            JOptionPane.showMessageDialog(null, "[信息]:商品数量不能大于100。");
            return;
        }
        int 商品出售状态2;
        if (商品出售状态.getText().equals("")) {
            商品出售状态2 = -1;
        } else {
            商品出售状态2 = Integer.parseInt(this.商品出售状态.getText());
        }
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        try {
            //清楚table数据
            for (int i = ((DefaultTableModel) (this.charTable.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.charTable.getModel())).removeRow(i);
            }
            String[] split = this.商品时间.getText().split(",");
            //ps = DatabaseConnection.getConnection().prepareStatement("UPDATE cashshop_modified_items SET showup = ?, itemid = ?, priority = ?, period = ?, gender = ?, count = ?, meso = ?, discount_price = ?, mark = ?, unk_1 = ?, unk_2 = ?, unk_3 = ? WHERE serial = ?");
            ps = DatabaseConnection.getConnection().prepareStatement("UPDATE cashshop_modified_items SET  itemid = ?, period = ?, count = ?, discount_price = ?, mark = ?, use_nx = ?, period_hour = ?, day_limit = ? WHERE serial = ?");
            ps.setInt(1, Integer.parseInt(this.商品代码.getText()));
            ps.setInt(2, Integer.parseInt(split[0]));
            ps.setInt(3, Integer.parseInt(this.商品数量.getText()));
            ps.setInt(4, Integer.parseInt(this.商品价格.getText()));
            ps.setInt(5, Integer.parseInt(this.商品出售状态.getText()));
            ps.setInt(6, Integer.parseInt(this.点券购买.getText()));
            ps.setInt(7, split.length > 1 ? Integer.parseInt(split[1]):0);
            ps.setInt(8, Integer.parseInt(this.每日限购.getText()));
            ps.setInt(9, Integer.parseInt(this.商品编码.getText()));
            ps.executeUpdate();
            ps.close();
            if (!商品库存.getText().equals("")) {
                int SN库存 = Getcharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 2);
                if (SN库存 == -1) {
                    Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 2, -2);
                }
                Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 2, -SN库存);
                Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 2, Integer.parseInt(this.商品库存.getText()));
            } else {
                删除SN库存2();
            }
            if (!商品折扣.getText().equals("")) {
                int SN库存 = Getcharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 3);
                if (SN库存 == -1) {
                    Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 3, -2);
                }
                Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 3, -SN库存);
                Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 3, Integer.parseInt(this.商品折扣.getText()));
            } else {
                删除SN库存3();
            }

            if (!每日限购.getText().equals("")) {
                int SN库存 = Getcharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 4);
                if (SN库存 == -1) {
                    Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 4, -2);
                }
                Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 4, -SN库存);
                Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 4, Integer.parseInt(this.每日限购.getText()));
            } else {
                删除SN库存4();
            }
            JOptionPane.showMessageDialog(null, "[信息]:修改物品载入成功。");
            int n = JOptionPane.showConfirmDialog(this, "是否刷新？\r\n刷新所耗时间会根据物品数量，服务器配置决定。", "信息", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                刷新();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Start.class
                .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_修改ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int n = JOptionPane.showConfirmDialog(this, "是否刷新？\r\n刷新所耗时间会根据物品数量，服务器配置决定。", "信息", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            initCharacterPannel();
        }
        JOptionPane.showMessageDialog(null, "[信息]:刷新商城物品列表。");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // TODO add your handling code here:
        int n = JOptionPane.showConfirmDialog(this, "确定为[ " + 商品编码.getText() + " 商品]    下架?", "上架商品提示消息", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            下架();
            //刷新();
        }
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
        int n = JOptionPane.showConfirmDialog(this, "确定为[ " + 商品编码.getText() + " 商品]    上架?", "上架商品提示消息", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            上架();
            //刷新();
        }
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        String 输出 = "";
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        int 商城SN编码 = Integer.parseInt(this.商品编码.getText());
        try {
            ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM cashshop_modified_items WHERE serial = ?");
            ps1.setInt(1, 商城SN编码);
            rs1 = ps1.executeQuery();
            if (rs1.next()) {
                String sqlstr = " delete from cashshop_modified_items where serial =" + 商城SN编码 + ";";
                ps1.executeUpdate(sqlstr);

                JOptionPane.showMessageDialog(null, "[信息]:成功删除商品。");
            } else {
                JOptionPane.showMessageDialog(null, "[信息]:删除商品失败具。");

            }
            rs1.close();
            ps1.close();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        删除SN库存();
        int n = JOptionPane.showConfirmDialog(this, "是否刷新？\r\n刷新所耗时间会根据物品数量，服务器配置决定。", "信息", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            刷新();
        }
    }//GEN-LAST:event_jButton27ActionPerformed

    private void 商品出售状态ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_商品出售状态ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_商品出售状态ActionPerformed

    private void 显示类型ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_显示类型ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_显示类型ActionPerformed

    private void 点券购买ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_点券购买ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_点券购买ActionPerformed

    private void 货币类型ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_货币类型ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_货币类型ActionPerformed

    private void 商品编码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_商品编码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_商品编码ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        JOptionPane.showMessageDialog(null, "[信息]:未启用。");
        //JOptionPane.showMessageDialog(this, "未启用");  // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void 活动ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_活动ActionPerformed
        读取商品(10200000, 10300000, 1, 3);
    }//GEN-LAST:event_活动ActionPerformed

    private void 读取热销产品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_读取热销产品ActionPerformed
        读取商品(10000000, 10100000, 1, 1);
    }//GEN-LAST:event_读取热销产品ActionPerformed

    private void 主题馆ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_主题馆ActionPerformed
        读取商品(10100000, 10200000, 1, 2);
    }//GEN-LAST:event_主题馆ActionPerformed

    private void 其他ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_其他ActionPerformed
        读取商品(60200000, 60300000, 5, 3);
    }//GEN-LAST:event_其他ActionPerformed

    private void 宠物服饰ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_宠物服饰ActionPerformed
        读取商品(60100000, 60200000, 5, 2);
    }//GEN-LAST:event_宠物服饰ActionPerformed

    private void 宠物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_宠物ActionPerformed
        读取商品(60000000, 60100000, 5, 1);
    }//GEN-LAST:event_宠物ActionPerformed

    private void 效果ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_效果ActionPerformed
        读取商品(50500000, 50600000, 4, 4);
    }//GEN-LAST:event_效果ActionPerformed

    private void 游戏ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_游戏ActionPerformed
        读取商品(50400000, 50500000, 4, 5);
    }//GEN-LAST:event_游戏ActionPerformed

    private void 纪念日ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_纪念日ActionPerformed
        读取商品(50300000, 50400000, 4, 6);
    }//GEN-LAST:event_纪念日ActionPerformed

    private void 个人商店ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_个人商店ActionPerformed
        读取商品(50200000, 50300000, 4, 3);
    }//GEN-LAST:event_个人商店ActionPerformed

    private void 表情ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_表情ActionPerformed
        读取商品(50100000, 50200000, 4, 2);
    }//GEN-LAST:event_表情ActionPerformed

    private void 会员卡ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_会员卡ActionPerformed
        读取商品(50000000, 50100000, 4, 1);
    }//GEN-LAST:event_会员卡ActionPerformed

    private void 卷轴ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_卷轴ActionPerformed
        读取商品(30200000, 30300000, 3, 3);
    }//GEN-LAST:event_卷轴ActionPerformed

    private void 通讯物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_通讯物品ActionPerformed
        读取商品(30100000, 30200000, 3, 2);
    }//GEN-LAST:event_通讯物品ActionPerformed

    private void 喜庆物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_喜庆物品ActionPerformed
        读取商品(30000000, 30100000, 3, 1);
    }//GEN-LAST:event_喜庆物品ActionPerformed

    private void 骑宠ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_骑宠ActionPerformed
        读取商品(21200000, 21300000, 2, 8);
    }//GEN-LAST:event_骑宠ActionPerformed

    private void 披风ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_披风ActionPerformed
        读取商品(21100000, 21200000, 2, 3);
    }//GEN-LAST:event_披风ActionPerformed

    private void 飞镖ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_飞镖ActionPerformed
        读取商品(21000000, 21100000, 2, 4);
    }//GEN-LAST:event_飞镖ActionPerformed

    private void 戒指ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_戒指ActionPerformed
        读取商品(20900000, 21000000, 2, 9);
    }//GEN-LAST:event_戒指ActionPerformed

    private void 武器ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_武器ActionPerformed
        读取商品(20800000, 20900000, 2, 12);
    }//GEN-LAST:event_武器ActionPerformed

    private void 手套ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_手套ActionPerformed
        读取商品(20700000, 20800000, 2, 11);
    }//GEN-LAST:event_手套ActionPerformed

    private void 鞋子ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_鞋子ActionPerformed
        读取商品(20600000, 20700000, 2, 7);
    }//GEN-LAST:event_鞋子ActionPerformed

    private void 裙裤ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_裙裤ActionPerformed
        读取商品(20500000, 20600000, 2, 2);
    }//GEN-LAST:event_裙裤ActionPerformed

    private void 上衣ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_上衣ActionPerformed
        读取商品(20400000, 20500000, 2, 13);
    }//GEN-LAST:event_上衣ActionPerformed

    private void 长袍ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_长袍ActionPerformed
        读取商品(20300000, 20400000, 2, 5);
    }//GEN-LAST:event_长袍ActionPerformed

    private void 眼饰ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_眼饰ActionPerformed
        读取商品(20200000, 20300000, 2, 10);
    }//GEN-LAST:event_眼饰ActionPerformed

    private void 脸饰ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_脸饰ActionPerformed
        读取商品(20100000, 20200000, 2, 6);
    }//GEN-LAST:event_脸饰ActionPerformed

    private void 帽子ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_帽子ActionPerformed
        读取商品(20000000, 20100000, 2, 1);
    }//GEN-LAST:event_帽子ActionPerformed

    private void 删除IPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除IPActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.删除IP代码.getText().matches("[0-9]+");

        if (result1) {
            if (Integer.parseInt(this.删除IP代码.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "请填写正确的值");
            }
            try {

                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM ipbans WHERE ipbanid = ?");
                ps1.setInt(1, Integer.parseInt(this.删除IP代码.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from ipbans where ipbanid =" + Integer.parseInt(this.删除IP代码.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新封IP();
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入数字 ");
        }
    }//GEN-LAST:event_删除IPActionPerformed

    private void 删除MACActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除MACActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.删MAC代码.getText().matches("[0-9]+");

        if (result1) {
            if (Integer.parseInt(this.删MAC代码.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "请填写正确的值");
            }
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM macbans WHERE macbanid = ?");
                ps1.setInt(1, Integer.parseInt(this.删MAC代码.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from macbans where macbanid =" + Integer.parseInt(this.删MAC代码.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新封MAC();
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入数字 ");
        }
    }//GEN-LAST:event_删除MACActionPerformed

    private void 刷新封MACActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新封MACActionPerformed
        刷新封MAC();
    }//GEN-LAST:event_刷新封MACActionPerformed

    private void 刷新封IPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新封IPActionPerformed
        刷新封IP();
    }//GEN-LAST:event_刷新封IPActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        try {

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE guilds SET GP =" + 家族GP.getText().toString() + " WHERE guildid = " + 家族ID.getText().toString() + "");
            ps.executeUpdate();
            ps.close();
            System.out.println("update guild gp !");
            刷新家族信息();
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
    }//GEN-LAST:event_jButton34ActionPerformed

    private void 家族GPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_家族GPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_家族GPActionPerformed

    private void 家族成员5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_家族成员5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_家族成员5ActionPerformed

    private void 家族成员4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_家族成员4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_家族成员4ActionPerformed

    private void 家族成员3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_家族成员3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_家族成员3ActionPerformed

    private void 家族成员2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_家族成员2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_家族成员2ActionPerformed

    private void 家族族长ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_家族族长ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_家族族长ActionPerformed

    private void 家族名称ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_家族名称ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_家族名称ActionPerformed

    private void 家族IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_家族IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_家族IDActionPerformed

    private void 刷新家族信息ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新家族信息ActionPerformed
        刷新家族信息();
    }//GEN-LAST:event_刷新家族信息ActionPerformed

    private void 修改技能1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改技能1ActionPerformed
        JOptionPane.showMessageDialog(null, "[信息]:查看玩家技能信息。");
        刷新技能信息();
    }//GEN-LAST:event_修改技能1ActionPerformed

    private void 删除技能ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除技能ActionPerformed

        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.技能序号.getText().matches("[0-9]+");

        if (result1) {
            if (Integer.parseInt(this.技能序号.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "请填写正确的值");
            }
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM skills WHERE id = ?");
                ps1.setInt(1, Integer.parseInt(this.技能序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from skills where id =" + Integer.parseInt(this.技能序号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新技能信息();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的技能");
        }
    }//GEN-LAST:event_删除技能ActionPerformed

    private void 修改技能ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改技能ActionPerformed
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.技能序号.getText().matches("[0-9]+");

        if (result1) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE skills SET skilllevel = ?,masterlevel = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM skills WHERE id = ?");
                ps1.setInt(1, Integer.parseInt(this.技能序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString1 = null;
                    String sqlString2 = null;

                    sqlString1 = "update skills set skilllevel='" + this.技能目前等级.getText() + "' where id=" + this.技能序号.getText() + ";";
                    PreparedStatement skilllevel = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                    skilllevel.executeUpdate(sqlString1);

                    sqlString2 = "update skills set masterlevel='" + this.技能最高等级.getText() + "' where id=" + this.技能序号.getText() + ";";
                    PreparedStatement masterlevel = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    masterlevel.executeUpdate(sqlString2);

                    刷新技能信息();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要修改的技能");
        }// TODO add your handling code here:
    }//GEN-LAST:event_修改技能ActionPerformed

    private void 技能名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_技能名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_技能名字ActionPerformed

    private void 删除拍卖行ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除拍卖行ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_删除拍卖行ActionPerformed

    private void 拍卖行物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_拍卖行物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_拍卖行物品代码ActionPerformed

    private void 角色金币拍卖行序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_角色金币拍卖行序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_角色金币拍卖行序号ActionPerformed

    private void 拍卖行物品名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_拍卖行物品名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_拍卖行物品名字ActionPerformed

    private void 删除拍卖行1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除拍卖行1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_删除拍卖行1ActionPerformed

    private void 拍卖行物品代码1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_拍卖行物品代码1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_拍卖行物品代码1ActionPerformed

    private void 角色点券拍卖行序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_角色点券拍卖行序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_角色点券拍卖行序号ActionPerformed

    private void 拍卖行物品名字1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_拍卖行物品名字1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_拍卖行物品名字1ActionPerformed

    private void 删除商城仓库ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除商城仓库ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.商城仓库物品序号.getText().matches("[0-9]+");
        if (result == true) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM csitems WHERE inventoryitemid = ?");
                ps1.setInt(1, Integer.parseInt(this.商城仓库物品序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from csitems where inventoryitemid =" + Integer.parseInt(this.商城仓库物品序号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新角色商城仓库();
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的物品");
        }
    }//GEN-LAST:event_删除商城仓库ActionPerformed

    private void 商城仓库物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_商城仓库物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_商城仓库物品代码ActionPerformed

    private void 商城仓库物品序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_商城仓库物品序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_商城仓库物品序号ActionPerformed

    private void 商城仓库物品名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_商城仓库物品名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_商城仓库物品名字ActionPerformed

    private void 删除游戏仓库ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除游戏仓库ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_删除游戏仓库ActionPerformed

    private void 游戏仓库物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_游戏仓库物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_游戏仓库物品代码ActionPerformed

    private void 游戏仓库物品序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_游戏仓库物品序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_游戏仓库物品序号ActionPerformed

    private void 游戏仓库物品名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_游戏仓库物品名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_游戏仓库物品名字ActionPerformed

    private void 删除特殊背包ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除特殊背包ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.特殊背包物品序号.getText().matches("[0-9]+");
        if (result == true) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE inventoryitemid = ?");
                ps1.setInt(1, Integer.parseInt(this.特殊背包物品序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from inventoryitems where inventoryitemid =" + Integer.parseInt(this.特殊背包物品序号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新角色特殊背包();
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的物品");
        }
    }//GEN-LAST:event_删除特殊背包ActionPerformed

    private void 特殊背包物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_特殊背包物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_特殊背包物品代码ActionPerformed

    private void 特殊背包物品序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_特殊背包物品序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_特殊背包物品序号ActionPerformed

    private void 特殊背包物品名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_特殊背包物品名字ActionPerformed

    }//GEN-LAST:event_特殊背包物品名字ActionPerformed

    private void 删除其他背包ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除其他背包ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.其他背包物品序号.getText().matches("[0-9]+");
        if (result == true) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE inventoryitemid = ?");
                ps1.setInt(1, Integer.parseInt(this.其他背包物品序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from inventoryitems where inventoryitemid =" + Integer.parseInt(this.其他背包物品序号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新角色其他背包();
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的物品");
        }
    }//GEN-LAST:event_删除其他背包ActionPerformed

    private void 其他背包物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_其他背包物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_其他背包物品代码ActionPerformed

    private void 其他背包物品序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_其他背包物品序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_其他背包物品序号ActionPerformed

    private void 其他背包物品名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_其他背包物品名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_其他背包物品名字ActionPerformed

    private void 删除设置背包ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除设置背包ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.设置背包物品序号.getText().matches("[0-9]+");
        if (result == true) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE inventoryitemid = ?");
                ps1.setInt(1, Integer.parseInt(this.设置背包物品序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from inventoryitems where inventoryitemid =" + Integer.parseInt(this.设置背包物品序号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新角色设置背包();
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的物品");
        }
    }//GEN-LAST:event_删除设置背包ActionPerformed

    private void 设置背包物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_设置背包物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_设置背包物品代码ActionPerformed

    private void 设置背包物品序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_设置背包物品序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_设置背包物品序号ActionPerformed

    private void 设置背包物品名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_设置背包物品名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_设置背包物品名字ActionPerformed

    private void 删除消耗背包ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除消耗背包ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.消耗背包物品序号.getText().matches("[0-9]+");
        if (result == true) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE inventoryitemid = ?");
                ps1.setInt(1, Integer.parseInt(this.消耗背包物品序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from inventoryitems where inventoryitemid =" + Integer.parseInt(this.消耗背包物品序号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新角色消耗背包();
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的物品");
        }
    }//GEN-LAST:event_删除消耗背包ActionPerformed

    private void 消耗背包物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_消耗背包物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_消耗背包物品代码ActionPerformed

    private void 消耗背包物品序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_消耗背包物品序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_消耗背包物品序号ActionPerformed

    private void 消耗背包物品名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_消耗背包物品名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_消耗背包物品名字ActionPerformed

    private void 删除装备背包ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除装备背包ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.装备背包物品序号.getText().matches("[0-9]+");
        if (result == true) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE inventoryitemid = ?");
                ps1.setInt(1, Integer.parseInt(this.装备背包物品序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from inventoryitems where inventoryitemid =" + Integer.parseInt(this.装备背包物品序号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新角色装备背包();
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的物品");
        }
    }//GEN-LAST:event_删除装备背包ActionPerformed

    private void 装备背包物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_装备背包物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_装备背包物品代码ActionPerformed

    private void 装备背包物品序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_装备背包物品序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_装备背包物品序号ActionPerformed

    private void 装备背包物品名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_装备背包物品名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_装备背包物品名字ActionPerformed

    private void 删除穿戴装备ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除穿戴装备ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.身上穿戴序号1.getText().matches("[0-9]+");
        if (result == true) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE inventoryitemid = ?");
                ps1.setInt(1, Integer.parseInt(this.身上穿戴序号1.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from inventoryitems where inventoryitemid =" + Integer.parseInt(this.身上穿戴序号1.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新角色背包穿戴();
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的物品");
        }
    }//GEN-LAST:event_删除穿戴装备ActionPerformed

    private void 背包物品代码1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_背包物品代码1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_背包物品代码1ActionPerformed

    private void 身上穿戴序号1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_身上穿戴序号1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_身上穿戴序号1ActionPerformed

    private void 背包物品名字1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_背包物品名字1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_背包物品名字1ActionPerformed

    private void jButtonAccountSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAccountSearchActionPerformed
        // TODO add your handling code here:
        for (int i = ((DefaultTableModel) (this.角色信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色信息.getModel())).removeRow(i);
        }

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM characters WHERE name like  '%" + roleNameEdit.getText() + "%'");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 在线 = "";
                if (World.Find.findChannel(rs.getString("name")) > 0) {
                    在线 = "在线";
                } else {
                    在线 = "离线";
                }
                ((DefaultTableModel) 角色信息.getModel()).insertRow(角色信息.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    rs.getInt("accountid"),
                    rs.getString("name"),
                    getJobNameById(rs.getInt("job")),
                    rs.getInt("level"),
                    rs.getInt("str"),
                    rs.getInt("dex"),
                    rs.getInt("int"),
                    rs.getInt("luk"),
                    rs.getInt("maxhp"),
                    rs.getInt("maxmp"),
                    rs.getInt("meso"),
                    rs.getInt("map"),
                    在线,
                    rs.getInt("gm"),
                    rs.getInt("hair"),
                    rs.getInt("face")
                });
            }
            rs.close();
            ps.close();
            账号提示语言.setText("[信息]:查找账号 " + this.账号操作.getText() + " 成功。");
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        账号信息.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 账号信息.getSelectedRow();
                String a = 账号信息.getValueAt(i, 0).toString();
                String a1 = 账号信息.getValueAt(i, 1).toString();
                String a2 = 账号信息.getValueAt(i, 5).toString();
                String a3 = 账号信息.getValueAt(i, 6).toString();
                账号ID.setText(a);
                账号操作.setText(a1);
                账号.setText(a1);
                点券.setText(a2);
                抵用.setText(a3);
                刷新角色信息2();
            }
        });
    }//GEN-LAST:event_jButtonAccountSearchActionPerformed

    private void 在线角色ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_在线角色ActionPerformed
        显示在线玩家.setText("在线玩家; " + 在线玩家() + "");
        for (int i = ((DefaultTableModel) (this.角色信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM characters order by id desc");
            rs = ps.executeQuery();
            while (rs.next()) {
                if (World.Find.findChannel(rs.getString("name")) > 0) {
                    ((DefaultTableModel) 角色信息.getModel()).insertRow(角色信息.getRowCount(), new Object[]{
                        rs.getInt("id"),
                        rs.getInt("accountid"),
                        rs.getString("name"),
                        getJobNameById(rs.getInt("job")),
                        rs.getInt("level"),
                        rs.getInt("str"),
                        rs.getInt("dex"),
                        rs.getInt("int"),
                        rs.getInt("luk"),
                        rs.getInt("maxhp"),
                        rs.getInt("maxmp"),
                        rs.getInt("meso"),
                        rs.getInt("map"),
                        "在线",
                        rs.getInt("gm"),
                        rs.getInt("hair"),
                        rs.getInt("face"
                        )});
                    }
                }
                rs.close();
                ps.close();
                JOptionPane.showMessageDialog(null, "[信息]:显示游戏所有在线角色信息。");

            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_在线角色ActionPerformed

    private void 离线角色ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_离线角色ActionPerformed
        显示在线玩家.setText("在线玩家; " + 在线玩家() + "");
        for (int i = ((DefaultTableModel) (this.角色信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM characters order by id desc");
            rs = ps.executeQuery();
            while (rs.next()) {
                if (World.Find.findChannel(rs.getString("name")) <= 0) {
                    ((DefaultTableModel) 角色信息.getModel()).insertRow(角色信息.getRowCount(), new Object[]{
                        rs.getInt("id"),
                        rs.getInt("accountid"),
                        rs.getString("name"),
                        getJobNameById(rs.getInt("job")),
                        rs.getInt("level"),
                        rs.getInt("str"),
                        rs.getInt("dex"),
                        rs.getInt("int"),
                        rs.getInt("luk"),
                        rs.getInt("maxhp"),
                        rs.getInt("maxmp"),
                        rs.getInt("meso"),
                        rs.getInt("map"),
                        "在线",
                        rs.getInt("gm"),
                        rs.getInt("hair"),
                        rs.getInt("face"
                        )});
                    }
                }
                rs.close();
                ps.close();
                JOptionPane.showMessageDialog(null, "[信息]:显示游戏所有离线角色信息。");

            } catch (SQLException ex) {
                Logger.getLogger(Start.class
                    .getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_离线角色ActionPerformed

    private void 发型ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_发型ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_发型ActionPerformed

    private void 脸型ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_脸型ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_脸型ActionPerformed

    private void 卡家族解救ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_卡家族解救ActionPerformed
        显示在线玩家.setText("在线玩家; " + 在线玩家() + "");
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.角色ID.getText().matches("[0-9]+");
        if (result1) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE characters SET (guildid = ?,guildrank = ?,allianceRank = ?)WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM characters WHERE id = ?");
                ps1.setInt(1, Integer.parseInt(this.角色ID.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString1 = null;
                    String sqlString2 = null;
                    String sqlString3 = null;
                    sqlString1 = "update characters set guildid='0' where id=" + this.角色ID.getText() + ";";
                    PreparedStatement hair = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                    hair.executeUpdate(sqlString1);
                    sqlString2 = "update characters set guildrank='5' where id=" + this.角色ID.getText() + ";";
                    PreparedStatement face = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    face.executeUpdate(sqlString2);
                    sqlString3 = "update characters set allianceRank='5' where id=" + this.角色ID.getText() + ";";
                    PreparedStatement allianceRank = DatabaseConnection.getConnection().prepareStatement(sqlString3);
                    allianceRank.executeUpdate(sqlString3);
                    JOptionPane.showMessageDialog(null, "[信息]:解卡家族角色成功。");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请选择卡家族的角色。");
        }
    }//GEN-LAST:event_卡家族解救ActionPerformed

    private void 查看背包ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查看背包ActionPerformed
        显示在线玩家.setText("在线玩家; " + 在线玩家() + "");
        boolean result1 = this.角色ID.getText().matches("[0-9]+");
        if (!result1) {
            JOptionPane.showMessageDialog(null, "[信息]:请选择角色。");
            return;
        }
        if (账号ID.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请先选择账号，再选择账号下的角色，接下来才可以查看游戏仓库。");
            return;
        }
        JOptionPane.showMessageDialog(null, "[信息]:查询速度跟角色信息量有关，请耐心等候。");
        刷新角色背包穿戴();
        刷新角色装备背包();
        刷新角色消耗背包();
        刷新角色设置背包();
        刷新角色其他背包();
        刷新角色特殊背包();
        刷新角色游戏仓库();
        刷新角色商城仓库();
        //        刷新角色金币拍卖行();
        //        刷新角色点券拍卖行();
        JOptionPane.showMessageDialog(null, "[信息]:请转到角色道具信息面板查看。");
    }//GEN-LAST:event_查看背包ActionPerformed

    private void 查看技能ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查看技能ActionPerformed

        JOptionPane.showMessageDialog(null, "[信息]:查看玩家技能信息。");
        刷新技能信息();
    }//GEN-LAST:event_查看技能ActionPerformed

    private void 卡号自救2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_卡号自救2ActionPerformed
        显示在线玩家.setText("在线玩家; " + 在线玩家() + "");
        String 输出 = "";
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.角色ID.getText().matches("[0-9]+");
        if (result1) {
            int n = JOptionPane.showConfirmDialog(this, "你确定要解卡物品自救这个角色吗？", "信息", JOptionPane.YES_NO_OPTION);
            if (n != JOptionPane.YES_OPTION) {
                return;
            }
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE characterid = ?");
                ps1.setInt(1, Integer.parseInt(this.角色ID.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr2 = " delete from inventoryitems where characterid =" + Integer.parseInt(this.角色ID.getText()) + "";
                    ps1.executeUpdate(sqlstr2);
                    JOptionPane.showMessageDialog(null, "[信息]:角色已经进行38处理。");
                    刷新角色信息();
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请选择要38处理的角色。");
        }
    }//GEN-LAST:event_卡号自救2ActionPerformed

    private void 卡号自救1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_卡号自救1ActionPerformed
        显示在线玩家.setText("在线玩家; " + 在线玩家() + "");
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.角色ID.getText().matches("[0-9]+");
        if (result1) {
            int n = JOptionPane.showConfirmDialog(this, "你确定要解卡发型脸型自救这个角色吗？", "信息", JOptionPane.YES_NO_OPTION);
            if (n != JOptionPane.YES_OPTION) {
                return;
            }
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE characters SET (hair = ?,face = ?)WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM characters WHERE id = ?");
                ps1.setInt(1, Integer.parseInt(this.角色ID.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString1 = null;
                    String sqlString2 = null;
                    sqlString1 = "update characters set hair='30000' where id=" + this.角色ID.getText() + ";";
                    PreparedStatement hair = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                    hair.executeUpdate(sqlString1);
                    sqlString2 = "update characters set face='20000' where id=" + this.角色ID.getText() + ";";
                    PreparedStatement face = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    face.executeUpdate(sqlString2);
                    JOptionPane.showMessageDialog(null, "[信息]:解救成功，发型脸型初始化。");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请选择卡发型脸型的角色。");
        }
    }//GEN-LAST:event_卡号自救1ActionPerformed

    private void 角色IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_角色IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_角色IDActionPerformed

    private void GMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GMActionPerformed

    private void 地图ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_地图ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_地图ActionPerformed

    private void 金币1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_金币1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_金币1ActionPerformed

    private void MPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MPActionPerformed

    private void HPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HPActionPerformed

    private void 运气ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_运气ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_运气ActionPerformed

    private void 智力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_智力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_智力ActionPerformed

    private void 敏捷ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_敏捷ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_敏捷ActionPerformed

    private void 力量ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_力量ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_力量ActionPerformed

    private void 等级ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_等级ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_等级ActionPerformed

    private void 角色昵称ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_角色昵称ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_角色昵称ActionPerformed

    private void 删除角色ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除角色ActionPerformed
        String 输出 = "";
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.角色ID.getText().matches("[0-9]+");

        if (result1) {
            int n = JOptionPane.showConfirmDialog(this, "你确定要删除这个角色吗？", "信息", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                try {
                    ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM characters WHERE id = ?");
                    ps1.setInt(1, Integer.parseInt(this.角色ID.getText()));
                    rs = ps1.executeQuery();
                    if (rs.next()) {
                        String sqlstr = " delete from characters where id =" + Integer.parseInt(this.角色ID.getText()) + "";
                        ps1.executeUpdate(sqlstr);
                        String sqlstr2 = " delete from inventoryitems where characterid =" + Integer.parseInt(this.角色ID.getText()) + "";
                        ps1.executeUpdate(sqlstr2);
                        String sqlstr3 = " delete from auctionitems where characterid =" + Integer.parseInt(this.角色ID.getText()) + "";
                        ps1.executeUpdate(sqlstr3);
                        String sqlstr31 = " delete from auctionitems1 where characterid =" + Integer.parseInt(this.角色ID.getText()) + "";
                        ps1.executeUpdate(sqlstr31);
                        String sqlstr4 = " delete from csitems where accountid =" + Integer.parseInt(this.角色ID.getText()) + "";
                        ps1.executeUpdate(sqlstr4);
                        String sqlstr5 = " delete from bank_item where cid =" + Integer.parseInt(this.角色ID.getText()) + "";
                        ps1.executeUpdate(sqlstr5);
                        String sqlstr6 = " delete from bossrank where cid =" + Integer.parseInt(this.角色ID.getText()) + "";
                        ps1.executeUpdate(sqlstr6);
                        String sqlstr7 = " delete from skills where characterid =" + Integer.parseInt(this.角色ID.getText()) + "";
                        ps1.executeUpdate(sqlstr7);
                        JOptionPane.showMessageDialog(null, "[信息]:成功删除角色 " + Integer.parseInt(this.角色ID.getText()) + " ，以及所有相关信息。");
                        刷新角色信息();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请选择删除的角色。");
        }
    }//GEN-LAST:event_删除角色ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean A = this.等级.getText().matches("[0-9]+");
        boolean B = this.GM.getText().matches("[0-9]+");
        boolean C = this.地图.getText().matches("[0-9]+");
        boolean D = this.金币1.getText().matches("[0-9]+");
        boolean E = this.MP.getText().matches("[0-9]+");
        boolean F = this.HP.getText().matches("[0-9]+");
        boolean G = this.运气.getText().matches("[0-9]+");
        boolean H = this.智力.getText().matches("[0-9]+");
        boolean Y = this.敏捷.getText().matches("[0-9]+");
        boolean J = this.力量.getText().matches("[0-9]+");
        if (角色昵称.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "角色昵称不能留空");
            return;
        }
        if (World.Find.findChannel(角色昵称.getText()) > 0) {
            JOptionPane.showMessageDialog(null, "请先将角色离线后再修改。");
            return;
        }
        int n = JOptionPane.showConfirmDialog(this, "你确定要修改这个角色吗？", "信息", JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            ps = DatabaseConnection.getConnection().prepareStatement("UPDATE characters SET name = ?,level = ?, str = ?, dex = ?, luk = ?, `int` = ?,  maxhp = ?, maxmp = ?, meso = ?, map = ?, gm = ?, hair = ?, face = ? WHERE id = ?");
            ps.setString(1, this.角色昵称.getText());
            ps.setInt(2, Integer.parseInt(this.等级.getText()));
            ps.setInt(3, Integer.parseInt(this.力量.getText()));
            ps.setInt(4, Integer.parseInt(this.敏捷.getText()));
            ps.setInt(5, Integer.parseInt(this.运气.getText()));
            ps.setInt(6, Integer.parseInt(this.智力.getText()));

            ps.setInt(7, Integer.parseInt(this.HP.getText()));
            ps.setInt(8, Integer.parseInt(this.MP.getText()));
            ps.setInt(9, Integer.parseInt(this.金币1.getText()));
            ps.setInt(10, Integer.parseInt(this.地图.getText()));
            ps.setInt(11, Integer.parseInt(this.GM.getText()));
            ps.setInt(12, Integer.parseInt(this.发型.getText()));
            ps.setInt(13, Integer.parseInt(this.脸型.getText()));
            ps.setInt(14, Integer.parseInt(this.角色ID.getText()));
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "[信息]:角色信息修改成功。");
            刷新角色信息();
            /*
            ps1.setInt(1, Integer.parseInt(this.角色ID.getText()));
            rs = ps1.executeQuery();
            if (rs.next()) {
                String sqlString1 = null;
                String sqlString2 = null;
                String sqlString3 = null;
                String sqlString4 = null;
                String sqlString5 = null;
                String sqlString6 = null;
                String sqlString7 = null;
                String sqlString8 = null;
                String sqlString9 = null;
                String sqlString10 = null;
                String sqlString11 = null;
                String sqlString12 = null;
                String sqlString13 = null;
                sqlString1 = "update characters set name='" + this.角色昵称.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement name = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                name.executeUpdate(sqlString1);
                sqlString2 = "update characters set level='" + this.等级.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement level = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                level.executeUpdate(sqlString2);

                sqlString3 = "update characters set str='" + this.力量.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement str = DatabaseConnection.getConnection().prepareStatement(sqlString3);
                str.executeUpdate(sqlString3);

                sqlString4 = "update characters set dex='" + this.敏捷.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement dex = DatabaseConnection.getConnection().prepareStatement(sqlString4);
                dex.executeUpdate(sqlString4);

                sqlString5 = "update characters set luk='" + this.智力.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement luk = DatabaseConnection.getConnection().prepareStatement(sqlString5);
                luk.executeUpdate(sqlString5);

                sqlString6 = "update characters set `int`='" + this.运气.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement executeUpdate = DatabaseConnection.getConnection().prepareStatement(sqlString6);
                executeUpdate.executeUpdate(sqlString6);

                sqlString7 = "update characters set maxhp='" + this.HP.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement maxhp = DatabaseConnection.getConnection().prepareStatement(sqlString7);
                maxhp.executeUpdate(sqlString7);

                sqlString8 = "update characters set maxmp='" + this.MP.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement maxmp = DatabaseConnection.getConnection().prepareStatement(sqlString8);
                maxmp.executeUpdate(sqlString8);

                sqlString9 = "update characters set meso='" + this.金币1.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement meso = DatabaseConnection.getConnection().prepareStatement(sqlString9);
                meso.executeUpdate(sqlString9);

                sqlString10 = "update characters set map='" + this.地图.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement map = DatabaseConnection.getConnection().prepareStatement(sqlString10);
                map.executeUpdate(sqlString10);

                sqlString11 = "update characters set gm='" + this.GM.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement gm = DatabaseConnection.getConnection().prepareStatement(sqlString11);
                gm.executeUpdate(sqlString11);

                sqlString12 = "update characters set hair='" + this.发型.getText() + "' where id=" + this.发型.getText() + ";";
                PreparedStatement hair = DatabaseConnection.getConnection().prepareStatement(sqlString12);
                hair.executeUpdate(sqlString12);

                sqlString13 = "update characters set face='" + this.脸型.getText() + "' where id=" + this.脸型.getText() + ";";
                PreparedStatement face = DatabaseConnection.getConnection().prepareStatement(sqlString13);
                face.executeUpdate(sqlString13);
                JOptionPane.showMessageDialog(null, "[信息]:角色信息修改成功。");
                刷新角色信息();
            }
            */
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton38ActionPerformed

    private void 显示管理角色ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_显示管理角色ActionPerformed
        显示在线玩家.setText("在线玩家; " + 在线玩家() + "");
        for (int i = ((DefaultTableModel) (this.角色信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM characters  WHERE gm >0 ");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 在线 = "";
                if (World.Find.findChannel(rs.getString("name")) > 0) {
                    在线 = "在线";
                } else {
                    在线 = "离线";
                }
                ((DefaultTableModel) 角色信息.getModel()).insertRow(角色信息.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    rs.getInt("accountid"),
                    rs.getString("name"),
                    getJobNameById(rs.getInt("job")),
                    rs.getInt("level"),
                    rs.getInt("str"),
                    rs.getInt("dex"),
                    rs.getInt("int"),
                    rs.getInt("luk"),
                    rs.getInt("maxhp"),
                    rs.getInt("maxmp"),
                    rs.getInt("meso"),
                    rs.getInt("map"),
                    在线,
                    rs.getInt("gm"),
                    rs.getInt("hair"),
                    rs.getInt("face"
                    )});
                }
                rs.close();
                ps.close();
                JOptionPane.showMessageDialog(null, "[信息]:显示游戏所有管理员角色信息。");
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_显示管理角色ActionPerformed

    private void 刷新角色信息ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新角色信息ActionPerformed
        JOptionPane.showMessageDialog(null, "[信息]:显示游戏所有玩家角色信息。");
        刷新角色信息();
        显示在线玩家.setText("在线玩家; " + 在线玩家() + "");
    }//GEN-LAST:event_刷新角色信息ActionPerformed

    private void jButton1AccountSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1AccountSearchActionPerformed
        显示在线账号.setText("账号在线; " + 在线账号() + "");
        查找账号();
    }//GEN-LAST:event_jButton1AccountSearchActionPerformed

    private void 解卡ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_解卡ActionPerformed
        显示在线账号.setText("账号在线; " + 在线账号() + "");
        if (账号操作.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请输入需要解卡的账号 ");
            return;
        }
        String account = 账号操作.getText();
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps;

            ps = con.prepareStatement("Update accounts set loggedin = ? Where name = ?");
            ps.setInt(1, 0);
            ps.setString(2, account);
            ps.execute();
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
        账号提示语言.setText("[信息]:解卡账号 " + this.账号操作.getText() + " 成功。");
        刷新账号信息();
    }//GEN-LAST:event_解卡ActionPerformed

    private void 封锁账号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_封锁账号ActionPerformed
        显示在线账号.setText("账号在线; " + 在线账号() + "");
        if (账号操作.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请输入需要封锁的账号 ");
            return;
        }
        String account = 账号操作.getText();
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps;

            ps = con.prepareStatement("Update accounts set banned = ? Where name = ?");
            ps.setInt(1, 1);
            ps.setString(2, account);
            ps.execute();
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
        账号提示语言.setText("[信息]:封锁账号 " + this.账号操作.getText() + " 成功。");
        刷新账号信息();
    }//GEN-LAST:event_封锁账号ActionPerformed

    private void 删除账号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除账号ActionPerformed
        显示在线账号.setText("账号在线; " + 在线账号() + "");
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        try {
            int n = JOptionPane.showConfirmDialog(this, "你确定要删除这个账号吗？", "信息", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM accounts ");
                //ps1.setInt(1, Integer.parseInt(this.账号信息.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " Delete from accounts where name ='" + this.账号操作.getText() + "'";
                    账号提示语言.setText("[信息]:删除账号 " + this.账号操作.getText() + " 成功。");
                    ps1.executeUpdate(sqlstr);
                    刷新账号信息();
                }
                rs.close();
                ps1.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_删除账号ActionPerformed

    private void 在线账号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_在线账号ActionPerformed
        显示在线账号.setText("账号在线; " + 在线账号() + "");
        账号提示语言.setText("[信息]:显示游戏所有在线玩家账号信息。");
        for (int i = ((DefaultTableModel) (this.账号信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.账号信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM accounts  WHERE loggedin > 0 ");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 封号 = "";
                if (rs.getInt("banned") == 0) {
                    封号 = "正常";
                } else {
                    封号 = "封禁";
                }
                String 在线 = "";
                if (rs.getInt("loggedin") == 0) {
                    在线 = "不在线";
                } else {
                    在线 = "在线";
                }
                String QQ = "";
                if (rs.getString("qq") != null) {
                    QQ = rs.getString("qq");
                } else {
                    QQ = "未绑定QQ";
                }
                ((DefaultTableModel) 账号信息.getModel()).insertRow(账号信息.getRowCount(), new Object[]{
                    rs.getInt("id"), //账号ID
                    rs.getString("name"), //账号
                    rs.getString("SessionIP"), //账号IP地址
                    rs.getString("macs"), ///账号MAC地址
                    QQ,//注册时间
                    rs.getInt("ACash"),//点券
                    rs.getInt("moneyb"),//元宝
                    rs.getInt("mPoints"),//抵用
                    rs.getString("lastlogin"),//最近上线
                    在线,
                    封号,
                    rs.getInt("gm")
                });
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        读取显示账号();
    }//GEN-LAST:event_在线账号ActionPerformed

    private void 已封账号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_已封账号ActionPerformed
        显示在线账号.setText("账号在线; " + 在线账号() + "");
        账号提示语言.setText("[信息]:显示游戏所有已被封禁的玩家账号信息。");
        for (int i = ((DefaultTableModel) (this.账号信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.账号信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM accounts WHERE banned > 0 ");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 封号 = "";
                if (rs.getInt("banned") == 0) {
                    封号 = "正常";
                } else {
                    封号 = "封禁";
                }
                String 在线 = "";
                if (rs.getInt("loggedin") == 0) {
                    在线 = "不在线";
                } else {
                    在线 = "在线";
                }
                String QQ = "";
                if (rs.getString("qq") != null) {
                    QQ = rs.getString("qq");
                } else {
                    QQ = "未绑定QQ";
                }
                ((DefaultTableModel) 账号信息.getModel()).insertRow(账号信息.getRowCount(), new Object[]{
                    rs.getInt("id"), //账号ID
                    rs.getString("name"), //账号
                    rs.getString("SessionIP"), //账号IP地址
                    rs.getString("macs"), ///账号MAC地址
                    QQ,//注册时间
                    rs.getInt("ACash"),//点券
                    rs.getInt("mPoints"),//抵用
                    rs.getString("lastlogin"),//最近上线
                    在线,
                    封号,
                    rs.getInt("gm")
                });
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        读取显示账号();
    }//GEN-LAST:event_已封账号ActionPerformed

    private void 解封ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_解封ActionPerformed
        显示在线账号.setText("账号在线; " + 在线账号() + "");
        if (账号操作.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请输入需要解封的账号 ");
            return;
        }
        String account = 账号操作.getText();
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps;

            ps = con.prepareStatement("Update accounts set banned = ? Where name = ?");
            ps.setInt(1, 0);
            ps.setString(2, account);
            ps.execute();
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
        账号提示语言.setText("[信息]:解封账号 " + account + " 成功。");
        //JOptionPane.showMessageDialog(null, "账号解封成功");
        刷新账号信息();
    }//GEN-LAST:event_解封ActionPerformed

    private void 离线账号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_离线账号ActionPerformed
        显示在线账号.setText("账号在线; " + 在线账号() + "");
        账号提示语言.setText("[信息]:显示游戏所有离线玩家账号信息。");
        for (int i = ((DefaultTableModel) (this.账号信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.账号信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM accounts  WHERE loggedin = 0 ");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 封号 = "";
                if (rs.getInt("banned") == 0) {
                    封号 = "正常";
                } else {
                    封号 = "封禁";
                }
                String 在线 = "";
                if (rs.getInt("loggedin") == 0) {
                    在线 = "不在线";
                } else {
                    在线 = "在线";
                }
                String QQ = "";
                if (rs.getString("qq") != null) {
                    QQ = rs.getString("qq");
                } else {
                    QQ = "未绑定QQ";
                }
                ((DefaultTableModel) 账号信息.getModel()).insertRow(账号信息.getRowCount(), new Object[]{
                    rs.getInt("id"), //账号ID
                    rs.getString("name"), //账号
                    rs.getString("SessionIP"), //账号IP地址
                    rs.getString("macs"), ///账号MAC地址
                    QQ,//注册时间
                    rs.getInt("ACash"),//点券
                    rs.getInt("mPoints"),//抵用
                    rs.getString("lastlogin"),//最近上线
                    在线,
                    封号,
                    rs.getInt("gm")
                });

            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        读取显示账号();
    }//GEN-LAST:event_离线账号ActionPerformed

    private void 刷新账号信息ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新账号信息ActionPerformed
        账号提示语言.setText("[信息]:显示游戏所有玩家账号信息。");
        刷新账号信息();
        显示在线账号.setText("账号在线; " + 在线账号() + "");
    }//GEN-LAST:event_刷新账号信息ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        ChangePassWord();
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        注册新账号();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton35ActionPerformed

    private void 注册的密码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_注册的密码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_注册的密码ActionPerformed

    private void 注册的账号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_注册的账号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_注册的账号ActionPerformed

    private void 修改账号点券抵用ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改账号点券抵用ActionPerformed
        boolean result1 = this.点券.getText().matches("[0-9]+");
        boolean result2 = this.抵用.getText().matches("[0-9]+");
        //boolean result3 = this.管理1.getText().matches("[0-9]+");
        //boolean result4 = this.QQ.getText().matches("[0-9]+");
        boolean moneyb = this.jTextFieldMoneyB.getText().matches("[0-9]+");
        PreparedStatement ps = null;

        if (result1 && result2 && moneyb) {
            try {
                int AccountID = Integer.parseInt(this.账号ID.getText());
                int ACash = Integer.parseInt(this.点券.getText());
                int nMoneyB = Integer.parseInt(this.jTextFieldMoneyB.getText());
                int nDY = Integer.parseInt(this.抵用.getText());
                //int nGM = Integer.parseInt(this.管理1.getText());
                String strQQ = this.QQ.getText();

                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE accounts SET ACash = ?, moneyb = ?, mPoints = ?, qq = ?  WHERE id = ?");

                ps.setInt(1, ACash);
                ps.setInt(2, nMoneyB);
                ps.setInt(3, nDY);
                ps.setString(4, strQQ);
                ps.setInt(5, AccountID);
                ps.executeUpdate();
                ps.close();
                刷新账号信息();
                账号提示语言.setText("[信息]:修改账号 " + this.账号.getText() + " / 点券→" + ACash +" / 元宝→" + nMoneyB + " / 抵用券→" + nDY + " 成功。");
                /*
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString2 = null;
                    String sqlString3 = null;
                    String sqlString4 = null;
                    String sqlString5 = null;
                    sqlString2 = "update accounts set ACash=" + Integer.parseInt(this.点券.getText()) + " where id ='" + Integer.parseInt(this.账号ID.getText()) + "';";
                    PreparedStatement priority = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    priority.executeUpdate(sqlString2);
                    sqlString3 = "update accounts set mPoints=" + Integer.parseInt(this.抵用.getText()) + " where id='" + Integer.parseInt(this.账号ID.getText()) + "';";
                    PreparedStatement period = DatabaseConnection.getConnection().prepareStatement(sqlString3);
                    period.executeUpdate(sqlString3);
                    sqlString4 = "update accounts set gm=" + Integer.parseInt(this.管理1.getText()) + " where id='" + Integer.parseInt(this.账号ID.getText()) + "';";
                    PreparedStatement gm = DatabaseConnection.getConnection().prepareStatement(sqlString4);
                    gm.executeUpdate(sqlString4);
                    sqlString5 = "update accounts set qq=" + Integer.parseInt(this.QQ.getText()) + " where id='" + Integer.parseInt(this.账号ID.getText()) + "';";
                    PreparedStatement qq = DatabaseConnection.getConnection().prepareStatement(sqlString5);
                    qq.executeUpdate(sqlString5);
                    刷新账号信息();
                    账号提示语言.setText("[信息]:修改账号 " + this.账号操作.getText() + " / 点券→" + Integer.parseInt(this.点券.getText()) + " / 抵用券→" + Integer.parseInt(this.抵用.getText()) + " 成功。");
                }
                */
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            账号提示语言.setText("[信息]:请选择要修改的账号,数据不能为空，或者数值填写不对。");
        }
    }//GEN-LAST:event_修改账号点券抵用ActionPerformed

    private void 查询怪物掉落ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查询怪物掉落ActionPerformed
        boolean result = this.查询怪物掉落代码.getText().matches("[0-9]+");
        if (result) {
            if (Integer.parseInt(this.查询怪物掉落代码.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "[信息]:请填写正确的值。");
                return;
            }
            for (int i = ((DefaultTableModel) (this.怪物爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.怪物爆物.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;

                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM drop_data WHERE dropperid =  " + Integer.parseInt(this.查询怪物掉落代码.getText()) + " && itemid !=0");//&& itemid !=0
                rs = ps.executeQuery();
                while (rs.next()) {

                    ((DefaultTableModel) 怪物爆物.getModel()).insertRow(怪物爆物.getRowCount(), new Object[]{
                        rs.getInt("id"),
                        rs.getInt("dropperid"),
                        rs.getInt("itemid"),
                        rs.getInt("chance"),
                        MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                    });
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            怪物爆物.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int i = 怪物爆物.getSelectedRow();
                    String a = 怪物爆物.getValueAt(i, 0).toString();
                    String a1 = 怪物爆物.getValueAt(i, 1).toString();
                    String a2 = 怪物爆物.getValueAt(i, 2).toString();
                    String a3 = 怪物爆物.getValueAt(i, 3).toString();
                    String a4 = 怪物爆物.getValueAt(i, 4).toString();
                    怪物爆物序列号.setText(a);
                    怪物爆物怪物代码.setText(a1);
                    怪物爆物物品代码.setText(a2);
                    怪物爆物爆率.setText(a3);
                    怪物爆物物品名称.setText(a4);
                    //怪物爆物任务.setText(a5);

                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入你要查找的怪物代码。");
        }
    }//GEN-LAST:event_查询怪物掉落ActionPerformed

    private void 刷新怪物卡片ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新怪物卡片ActionPerformed
        刷新怪物卡片();
    }//GEN-LAST:event_刷新怪物卡片ActionPerformed

    private void 删除指定的掉落按键1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除指定的掉落按键1ActionPerformed
        String 输出 = "";
        PreparedStatement ps1 = null;
        ResultSet rs = null;

        boolean result = this.删除指定的掉落.getText().matches("[0-9]+");
        if (result == true) {
            int 商城SN编码 = Integer.parseInt(this.删除指定的掉落.getText());
            try {
                // for (int i = ((DefaultTableModel) (this.怪物爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
                    //   ((DefaultTableModel) (this.怪物爆物.getModel())).removeRow(i);
                    //}
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM drop_data WHERE itemid = ?");
                ps1.setInt(1, 商城SN编码);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from drop_data where itemid =" + 商城SN编码 + "";
                    ps1.executeUpdate(sqlstr);
                    JOptionPane.showMessageDialog(null, "[信息]:成功删除 " + 商城SN编码 + " 物品。");
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            刷新怪物爆物();
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入你要查找的物品代码。");
        }
    }//GEN-LAST:event_删除指定的掉落按键1ActionPerformed

    private void 查询物品掉落1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查询物品掉落1ActionPerformed
        boolean result = this.查询物品掉落代码.getText().matches("[0-9]+");
        if (result) {
            if (Integer.parseInt(this.查询物品掉落代码.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "[信息]:请填写正确的值。");
                return;
            }
            for (int i = ((DefaultTableModel) (this.怪物爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.怪物爆物.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;

                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM drop_data WHERE itemid =  " + Integer.parseInt(this.查询物品掉落代码.getText()) + "");
                rs = ps.executeQuery();
                while (rs.next()) {

                    ((DefaultTableModel) 怪物爆物.getModel()).insertRow(怪物爆物.getRowCount(), new Object[]{
                        rs.getInt("id"),
                        rs.getInt("dropperid"),
                        rs.getInt("itemid"),
                        rs.getInt("chance"),
                        MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                    });
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            怪物爆物.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int i = 怪物爆物.getSelectedRow();
                    String a = 怪物爆物.getValueAt(i, 0).toString();
                    String a1 = 怪物爆物.getValueAt(i, 1).toString();
                    String a2 = 怪物爆物.getValueAt(i, 2).toString();
                    String a3 = 怪物爆物.getValueAt(i, 3).toString();
                    //String a4 = 怪物爆物.getValueAt(i, 4).toString();
                    怪物爆物序列号.setText(a);
                    怪物爆物怪物代码.setText(a1);
                    怪物爆物物品代码.setText(a2);
                    怪物爆物爆率.setText(a3);
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入你要查找的物品代码。");
        }
    }//GEN-LAST:event_查询物品掉落1ActionPerformed

    private void 查询物品掉落代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查询物品掉落代码ActionPerformed

    }//GEN-LAST:event_查询物品掉落代码ActionPerformed

    private void 刷新怪物爆物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新怪物爆物ActionPerformed
        JOptionPane.showMessageDialog(null, "[信息]:刷新怪物物品掉落数据。");
        刷新怪物爆物();
    }//GEN-LAST:event_刷新怪物爆物ActionPerformed

    private void 修改怪物爆物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改怪物爆物ActionPerformed
        boolean result1 = this.怪物爆物怪物代码.getText().matches("[0-9]+");
        boolean result2 = this.怪物爆物物品代码.getText().matches("[0-9]+");
        boolean result3 = this.怪物爆物爆率.getText().matches("[0-9]+");
        //PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        if (result1 && result2 && result3) {
            try {
                //ps = DatabaseConnection.getConnection().prepareStatement("UPDATE drop_data SET dropperid = ?, itemid = ?, chance = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM drop_data WHERE id = ?");
                ps1.setInt(1, Integer.parseInt(this.怪物爆物序列号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString2 = null;
                    String sqlString3 = null;
                    String sqlString4 = null;
                    sqlString2 = "update drop_data set dropperid='" + this.怪物爆物怪物代码.getText() + "' where id=" + this.怪物爆物序列号.getText() + ";";
                    PreparedStatement dropperid = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    dropperid.executeUpdate(sqlString2);
                    sqlString3 = "update drop_data set itemid='" + this.怪物爆物物品代码.getText() + "' where id=" + this.怪物爆物序列号.getText() + ";";
                    PreparedStatement itemid = DatabaseConnection.getConnection().prepareStatement(sqlString3);
                    itemid.executeUpdate(sqlString3);
                    sqlString4 = "update drop_data set chance='" + this.怪物爆物爆率.getText() + "' where id=" + this.怪物爆物序列号.getText() + ";";
                    PreparedStatement chance = DatabaseConnection.getConnection().prepareStatement(sqlString4);
                    chance.executeUpdate(sqlString4);
                    JOptionPane.showMessageDialog(null, "[信息]:修改成功。");
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请选择你要修改的数据。");
        }
    }//GEN-LAST:event_修改怪物爆物ActionPerformed

    private void 删除怪物爆物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除怪物爆物ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;

        boolean result = this.怪物爆物序列号.getText().matches("[0-9]+");
        if (result == true) {
            int 商城SN编码 = Integer.parseInt(this.怪物爆物序列号.getText());

            try {
                //清楚table数据
                for (int i = ((DefaultTableModel) (this.怪物爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
                    ((DefaultTableModel) (this.怪物爆物.getModel())).removeRow(i);
                }
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM drop_data WHERE id = ?");
                ps1.setInt(1, 商城SN编码);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from drop_data where id =" + 商城SN编码 + "";
                    ps1.executeUpdate(sqlstr);
                    JOptionPane.showMessageDialog(null, "[信息]:删除爆物成功。");
                    刷新指定怪物爆物();
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_删除怪物爆物ActionPerformed

    private void 添加怪物爆物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_添加怪物爆物ActionPerformed
        boolean result1 = this.怪物爆物怪物代码.getText().matches("[0-9]+");
        boolean result2 = this.怪物爆物物品代码.getText().matches("[0-9]+");
        boolean result3 = this.怪物爆物爆率.getText().matches("[0-9]+");
        if (result1 && result2 && result3) {
            if (Integer.parseInt(this.怪物爆物怪物代码.getText()) < 0 && Integer.parseInt(this.怪物爆物物品代码.getText()) < 0 && Integer.parseInt(this.怪物爆物爆率.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "[信息]:请填写正确的值。");
                return;
            }
            try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO drop_data ( dropperid,itemid,minimum_quantity,maximum_quantity,chance) VALUES ( ?, ?, ?, ?, ?)")) {
                ps.setInt(1, Integer.parseInt(this.怪物爆物怪物代码.getText()));
                ps.setInt(2, Integer.parseInt(this.怪物爆物物品代码.getText()));
                ps.setInt(3, 1);
                ps.setInt(4, 1);
                ps.setInt(5, Integer.parseInt(this.怪物爆物爆率.getText()));
                ps.executeUpdate();
                ps.close();
                JOptionPane.showMessageDialog(null, "[信息]:添加成功。");
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入<怪物代码><物品代码><物品爆率>的格式来添加。");
        }
    }//GEN-LAST:event_添加怪物爆物ActionPerformed

    private void 钓鱼物品序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_钓鱼物品序号ActionPerformed

    }//GEN-LAST:event_钓鱼物品序号ActionPerformed

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
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null,"[信息]:请选择你要删除的钓鱼物品。");
        }
    }//GEN-LAST:event_删除钓鱼物品ActionPerformed

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
                ps.close();
                JOptionPane.showMessageDialog(null,"[信息]:新增钓鱼奖励成功。");
                刷新钓鱼();
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null,"[信息]:请输入<物品代码><概率>。");
        }
    }//GEN-LAST:event_新增钓鱼物品ActionPerformed

    private void 钓鱼物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_钓鱼物品代码ActionPerformed

    }//GEN-LAST:event_钓鱼物品代码ActionPerformed

    private void 刷新钓鱼物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新钓鱼物品ActionPerformed
        JOptionPane.showMessageDialog(null,"[信息]:刷新钓鱼奖励成功。");
        刷新钓鱼();
    }//GEN-LAST:event_刷新钓鱼物品ActionPerformed

    private void 修改钓鱼物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改钓鱼物品ActionPerformed
        //PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.钓鱼物品序号.getText().matches("[0-9]+");
        if (result1) {
            try {
                //ps = DatabaseConnection.getConnection().prepareStatement("UPDATE 钓鱼物品 SET itemid = ?,chance = ?WHERE id = ?");
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
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null,"[信息]:输入<物品代码><概率>。");
        }
    }//GEN-LAST:event_修改钓鱼物品ActionPerformed

    private void 删除指定的掉落按键ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除指定的掉落按键ActionPerformed
        String 输出 = "";
        PreparedStatement ps1 = null;
        ResultSet rs = null;

        boolean result = this.删除指定的掉落.getText().matches("[0-9]+");
        if (result == true) {
            int 商城SN编码 = Integer.parseInt(this.删除指定的掉落.getText());
            try {
                // for (int i = ((DefaultTableModel) (this.怪物爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
                    //   ((DefaultTableModel) (this.怪物爆物.getModel())).removeRow(i);
                    //}
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM drop_data WHERE itemid = ?");
                ps1.setInt(1, 商城SN编码);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from drop_data where itemid =" + 商城SN编码 + "";
                    ps1.executeUpdate(sqlstr);
                    JOptionPane.showMessageDialog(null, "[信息]:成功删除 " + 商城SN编码 + " 物品。");
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            刷新怪物爆物();
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入你要查找的物品代码。");
        }
    }//GEN-LAST:event_删除指定的掉落按键ActionPerformed

    private void 查询物品掉落ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查询物品掉落ActionPerformed
        boolean result = this.查询物品掉落代码.getText().matches("[0-9]+");
        if (result) {
            if (Integer.parseInt(this.查询物品掉落代码.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "[信息]:请填写正确的值。");
                return;
            }
            for (int i = ((DefaultTableModel) (this.怪物爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.怪物爆物.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;

                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM drop_data WHERE itemid =  " + Integer.parseInt(this.查询物品掉落代码.getText()) + "");
                rs = ps.executeQuery();
                while (rs.next()) {

                    ((DefaultTableModel) 怪物爆物.getModel()).insertRow(怪物爆物.getRowCount(), new Object[]{
                        rs.getInt("id"),
                        rs.getInt("dropperid"),
                        rs.getInt("itemid"),
                        rs.getInt("chance"),
                        MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                    });
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            怪物爆物.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int i = 怪物爆物.getSelectedRow();
                    String a = 怪物爆物.getValueAt(i, 0).toString();
                    String a1 = 怪物爆物.getValueAt(i, 1).toString();
                    String a2 = 怪物爆物.getValueAt(i, 2).toString();
                    String a3 = 怪物爆物.getValueAt(i, 3).toString();
                    //String a4 = 怪物爆物.getValueAt(i, 4).toString();
                    怪物爆物序列号.setText(a);
                    怪物爆物怪物代码.setText(a1);
                    怪物爆物物品代码.setText(a2);
                    怪物爆物爆率.setText(a3);
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入你要查找的物品代码。");
        }
    }//GEN-LAST:event_查询物品掉落ActionPerformed

    private void 查询物品掉落代码1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查询物品掉落代码1ActionPerformed

    }//GEN-LAST:event_查询物品掉落代码1ActionPerformed

    private void 刷新世界爆物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新世界爆物ActionPerformed
        JOptionPane.showMessageDialog(null, "[信息]:刷新世界物品掉落数据。");
        刷新世界爆物();
    }//GEN-LAST:event_刷新世界爆物ActionPerformed

    private void 修改世界爆物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改世界爆物ActionPerformed

        boolean result2 = this.世界爆物物品代码.getText().matches("[0-9]+");
        boolean result3 = this.世界爆物爆率.getText().matches("[0-9]+");
        //PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        if (result2 && result3) {
            try {
                //ps = DatabaseConnection.getConnection().prepareStatement("UPDATE drop_data_global SET dropperid = ?, itemid = ?, chance = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM drop_data_global WHERE id = ?");
                ps1.setInt(1, Integer.parseInt(this.世界爆物序列号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString2 = null;
                    String sqlString3 = null;
                    sqlString2 = "update drop_data_global set itemid='" + this.世界爆物物品代码.getText() + "' where id=" + this.世界爆物序列号.getText() + ";";
                    PreparedStatement dropperid = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    dropperid.executeUpdate(sqlString2);
                    sqlString3 = "update drop_data_global set chance='" + this.世界爆物爆率.getText() + "' where id=" + this.世界爆物序列号.getText() + ";";
                    PreparedStatement itemid = DatabaseConnection.getConnection().prepareStatement(sqlString3);
                    itemid.executeUpdate(sqlString3);
                    JOptionPane.showMessageDialog(null, "[信息]:修改成功。");
                    刷新世界爆物();
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请选择你要修改的数据。");
        }
    }//GEN-LAST:event_修改世界爆物ActionPerformed

    private void 世界爆物名称ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_世界爆物名称ActionPerformed

    }//GEN-LAST:event_世界爆物名称ActionPerformed

    private void 删除世界爆物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除世界爆物ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.世界爆物序列号.getText().matches("[0-9]+");
        if (result == true) {
            int 商城SN编码 = Integer.parseInt(this.世界爆物序列号.getText());
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM drop_data_global WHERE id = ?");
                ps1.setInt(1, 商城SN编码);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from drop_data_global where id =" + 商城SN编码 + "";
                    ps1.executeUpdate(sqlstr);
                    JOptionPane.showMessageDialog(null, "[信息]:删除成功。");
                    刷新世界爆物();
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请选择你要删除的物品。");
        }
    }//GEN-LAST:event_删除世界爆物ActionPerformed

    private void 添加世界爆物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_添加世界爆物ActionPerformed
        boolean result1 = this.世界爆物物品代码.getText().matches("[0-9]+");
        boolean result2 = this.世界爆物爆率.getText().matches("[0-9]+");
        if (result1 && result2) {
            if (Integer.parseInt(this.世界爆物物品代码.getText()) < 0 && Integer.parseInt(this.世界爆物爆率.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "[信息]:请填写正确的值。");
                return;
            }
            PreparedStatement ps1 = null;
            ResultSet rs = null;
            try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO drop_data_global (continent,dropType,itemid,minimum_quantity,maximum_quantity,questid,chance) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                ps.setInt(1, 1);
                ps.setInt(2, 1);
                ps.setInt(3, Integer.parseInt(this.世界爆物物品代码.getText()));
                ps.setInt(4, 1);
                ps.setInt(5, 1);
                ps.setInt(6, 0);
                ps.setInt(7, Integer.parseInt(this.世界爆物爆率.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "[信息]:世界爆物添加成功。");
                刷新世界爆物();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入<物品代码>，<物品爆率> 。");
        }
    }//GEN-LAST:event_添加世界爆物ActionPerformed

    private void 世界爆物爆率ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_世界爆物爆率ActionPerformed

    }//GEN-LAST:event_世界爆物爆率ActionPerformed

    private void 世界爆物物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_世界爆物物品代码ActionPerformed

    }//GEN-LAST:event_世界爆物物品代码ActionPerformed

    private void 世界爆物序列号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_世界爆物序列号ActionPerformed

    }//GEN-LAST:event_世界爆物序列号ActionPerformed

    private void 删除反应堆物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除反应堆物品ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.删除反应堆物品代码.getText().matches("[0-9]+");

        if (result1) {
            if (Integer.parseInt(this.删除反应堆物品代码.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "请填写正确的值");
            }
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM reactordrops WHERE itemid = ?");
                ps1.setInt(1, Integer.parseInt(this.删除反应堆物品代码.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from reactordrops where itemid =" + Integer.parseInt(this.删除反应堆物品代码.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    JOptionPane.showMessageDialog(null, "成功删除 " + Integer.parseInt(this.删除反应堆物品代码.getText()) + " 物品，重载后生效。");
                    刷新反应堆();

                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入你要删除的反应堆代码 ");
        }
    }//GEN-LAST:event_删除反应堆物品ActionPerformed

    private void 重载箱子反应堆按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载箱子反应堆按钮ActionPerformed
        // TODO add your handling code here:
        ReactorScriptManager.getInstance().clearDrops();
        System.out.println("[重载系统] 箱子反应堆重载成功。");
        JOptionPane.showMessageDialog(null, "箱子反应堆重载成功。");
    }//GEN-LAST:event_重载箱子反应堆按钮ActionPerformed

    private void 修改反应堆物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改反应堆物品ActionPerformed
        //PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.反应堆代码.getText().matches("[0-9]+");
        boolean result1 = this.反应堆物品.getText().matches("[0-9]+");
        boolean result2 = this.反应堆概率.getText().matches("[0-9]+");

        if (result && result1 && result2) {
            if (Integer.parseInt(this.反应堆代码.getText()) < 0 && Integer.parseInt(this.反应堆物品.getText()) < 0 && Integer.parseInt(this.反应堆概率.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "请填写正确的值");
            }
            try {
                //ps = DatabaseConnection.getConnection().prepareStatement("UPDATE reactordrops SET reactorid = ?,itemid = ?,chance = ?WHERE reactordropid = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM reactordrops WHERE reactordropid = ?");
                ps1.setInt(1, Integer.parseInt(this.反应堆序列号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString1 = null;
                    String sqlString2 = null;
                    String sqlString3 = null;
                    sqlString1 = "update reactordrops set reactorid='" + this.反应堆代码.getText() + "' where reactordropid=" + this.反应堆序列号.getText() + ";";
                    PreparedStatement itemid = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                    itemid.executeUpdate(sqlString1);

                    sqlString2 = "update reactordrops set itemid='" + this.反应堆物品.getText() + "' where reactordropid=" + this.反应堆序列号.getText() + ";";
                    PreparedStatement price = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    price.executeUpdate(sqlString2);

                    sqlString3 = "update reactordrops set chance='" + this.反应堆概率.getText() + "' where reactordropid=" + this.反应堆序列号.getText() + ";";
                    PreparedStatement shopid = DatabaseConnection.getConnection().prepareStatement(sqlString3);
                    shopid.executeUpdate(sqlString3);
                    shopid.close();

                    刷新反应堆();
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要修改的数据");
        }
    }//GEN-LAST:event_修改反应堆物品ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        boolean result2 = this.查找反应堆掉落.getText().matches("[0-9]+");
        if (result2) {
            for (int i = ((DefaultTableModel) (this.反应堆.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.反应堆.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;

                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM reactordrops WHERE itemid = " + Integer.parseInt(查找物品.getText()));
                rs = ps.executeQuery();
                while (rs.next()) {
                    ((DefaultTableModel) 反应堆.getModel()).insertRow(反应堆.getRowCount(), new Object[]{
                        rs.getInt("reactordropid"),
                        rs.getInt("reactorid"),
                        rs.getInt("itemid"),
                        rs.getInt("chance"),
                        MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                    });

                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入你要查找的物品代码 ");
        }
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        boolean result2 = this.查找反应堆掉落.getText().matches("[0-9]+");
        if (result2) {
            for (int i = ((DefaultTableModel) (this.反应堆.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.反应堆.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;
                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM reactordrops WHERE reactorid = " + Integer.parseInt(查找反应堆掉落.getText()));
                rs = ps.executeQuery();
                while (rs.next()) {
                    ((DefaultTableModel) 反应堆.getModel()).insertRow(反应堆.getRowCount(), new Object[]{rs.getInt("reactordropid"), rs.getInt("reactorid"), rs.getInt("itemid"), rs.getInt("chance"), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))});
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入你要查找的反应堆 ");
        }
    }//GEN-LAST:event_jButton36ActionPerformed

    private void 删除反应堆物品1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除反应堆物品1ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.反应堆序列号.getText().matches("[0-9]+");

        if (result1) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM reactordrops WHERE reactordropid = ?");
                ps1.setInt(1, Integer.parseInt(this.反应堆序列号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from reactordrops where reactordropid =" + Integer.parseInt(this.反应堆序列号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新反应堆();
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的物品 ");
        }
    }//GEN-LAST:event_删除反应堆物品1ActionPerformed

    private void 新增反应堆物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_新增反应堆物品ActionPerformed

        boolean result2 = this.反应堆代码.getText().matches("[0-9]+");

        if (result2) {
            try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO reactordrops ( reactorid ,itemid ,chance ,questid ) VALUES ( ?, ?, ?, ?)")) {
                ps.setInt(1, Integer.parseInt(this.反应堆代码.getText()));
                ps.setInt(2, Integer.parseInt(this.反应堆物品.getText()));
                ps.setInt(3, Integer.parseInt(this.反应堆概率.getText()));
                ps.setInt(4, -1);
                ps.executeUpdate();
                ps.close();
                刷新反应堆();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入反应堆代码，物品代码，掉落概率 ");
        }
    }//GEN-LAST:event_新增反应堆物品ActionPerformed

    private void 反应堆概率ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_反应堆概率ActionPerformed

    }//GEN-LAST:event_反应堆概率ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        刷新反应堆();
    }//GEN-LAST:event_jButton26ActionPerformed

    private void 泡点豆豆开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_泡点豆豆开关ActionPerformed
        int 泡点豆豆开关 = FengYeDuan.ConfigValuesMap.get("泡点豆豆开关");
        if (泡点豆豆开关 <= 0) {
            按键开关("泡点豆豆开关", 711);
            刷新泡点豆豆开关();
        } else {
            按键开关("泡点豆豆开关", 711);
            刷新泡点豆豆开关();
        }
    }//GEN-LAST:event_泡点豆豆开关ActionPerformed

    private void 泡点抵用开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_泡点抵用开关ActionPerformed
        int 泡点抵用开关 = FengYeDuan.ConfigValuesMap.get("泡点抵用开关");
        if (泡点抵用开关 <= 0) {
            按键开关("泡点抵用开关", 707);
            刷新泡点抵用开关();
        } else {
            按键开关("泡点抵用开关", 707);
            刷新泡点抵用开关();
        }
    }//GEN-LAST:event_泡点抵用开关ActionPerformed

    private void 泡点点券开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_泡点点券开关ActionPerformed
        int 泡点点券开关 = FengYeDuan.ConfigValuesMap.get("泡点点券开关");
        if (泡点点券开关 <= 0) {
            按键开关("泡点点券开关", 703);
            刷新泡点点券开关();
        } else {
            按键开关("泡点点券开关", 703);
            刷新泡点点券开关();
        }
    }//GEN-LAST:event_泡点点券开关ActionPerformed

    private void 泡点经验开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_泡点经验开关ActionPerformed

        int 泡点经验开关 = FengYeDuan.ConfigValuesMap.get("泡点经验开关");
        if (泡点经验开关 <= 0) {
            按键开关("泡点经验开关", 705);
            刷新泡点经验开关();
        } else {
            按键开关("泡点经验开关", 705);
            刷新泡点经验开关();
        }
    }//GEN-LAST:event_泡点经验开关ActionPerformed

    private void 泡点金币开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_泡点金币开关ActionPerformed
        int 泡点金币开关 = FengYeDuan.ConfigValuesMap.get("泡点金币开关");
        if (泡点金币开关 <= 0) {
            按键开关("泡点金币开关", 701);
            刷新泡点金币开关();
        } else {
            按键开关("泡点金币开关", 701);
            刷新泡点金币开关();
        }
    }//GEN-LAST:event_泡点金币开关ActionPerformed

    private void 泡点值修改ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_泡点值修改ActionPerformed
        //PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.泡点值.getText().matches("[0-9]+");
        if (result1) {
            try {
                //ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");

                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM configvalues WHERE id = ?");

                ps1.setInt(1, Integer.parseInt(this.泡点序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString1 = null;
                    sqlString1 = "update configvalues set Val = '" + this.泡点值.getText() + "' where id= " + this.泡点序号.getText() + ";";
                    PreparedStatement Val = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                    Val.executeUpdate(sqlString1);
                    刷新泡点设置();
                    FengYeDuan.GetConfigValues();
                    福利提示语言2.setText("[信息]:修改成功已经生效。");
                }
                rs.close();
                ps1.close();

            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            福利提示语言2.setText("[信息]:请选择你要修改的值。");
        }
    }//GEN-LAST:event_泡点值修改ActionPerformed

    private void 给予物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_给予物品ActionPerformed
        刷物品();       // TODO add your handling code here:
    }//GEN-LAST:event_给予物品ActionPerformed

    private void 个人发送物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_个人发送物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_个人发送物品代码ActionPerformed

    private void 个人发送物品玩家名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_个人发送物品玩家名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_个人发送物品玩家名字ActionPerformed

    private void 个人发送物品数量ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_个人发送物品数量ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_个人发送物品数量ActionPerformed

    private void 给予装备2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_给予装备2ActionPerformed
        刷装备2(1);
    }//GEN-LAST:event_给予装备2ActionPerformed

    private void 发送装备玩家姓名ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_发送装备玩家姓名ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_发送装备玩家姓名ActionPerformed

    private void 给予装备1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_给予装备1ActionPerformed
        刷装备2(2);        // TODO add your handling code here:
    }//GEN-LAST:event_给予装备1ActionPerformed

    private void 全服发送装备装备物理防御ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备物理防御ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备物理防御ActionPerformed

    private void 全服发送装备装备魔法防御ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备魔法防御ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备魔法防御ActionPerformed

    private void 全服发送装备装备魔法力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备魔法力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备魔法力ActionPerformed

    private void 全服发送装备物品IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备物品IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备物品IDActionPerformed

    private void 全服发送装备装备敏捷ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备敏捷ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备敏捷ActionPerformed

    private void 全服发送装备装备可否交易ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备可否交易ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备可否交易ActionPerformed

    private void 全服发送装备装备给予时间ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备给予时间ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备给予时间ActionPerformed

    private void 全服发送装备装备攻击力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备攻击力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备攻击力ActionPerformed

    private void 全服发送装备装备HPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备HPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备HPActionPerformed

    private void 全服发送装备装备运气ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备运气ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备运气ActionPerformed

    private void 全服发送装备装备智力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备智力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备智力ActionPerformed

    private void 全服发送装备装备MPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备MPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备MPActionPerformed

    private void 全服发送装备装备力量ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备力量ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备力量ActionPerformed

    private void 全服发送装备装备制作人ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备制作人ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备制作人ActionPerformed

    private void 全服发送装备装备加卷ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备加卷ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备加卷ActionPerformed

    private void 给予物品1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_给予物品1ActionPerformed
        刷物品2();    // TODO add your handling code here:
    }//GEN-LAST:event_给予物品1ActionPerformed

    private void 全服发送物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送物品代码ActionPerformed

    }//GEN-LAST:event_全服发送物品代码ActionPerformed

    private void 全服发送物品数量ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送物品数量ActionPerformed

    }//GEN-LAST:event_全服发送物品数量ActionPerformed

    private void a1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_a1ActionPerformed

    private void z6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z6ActionPerformed
        发送福利(6);
    }//GEN-LAST:event_z6ActionPerformed

    private void z5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z5ActionPerformed
        发送福利(5);
    }//GEN-LAST:event_z5ActionPerformed

    private void z4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z4ActionPerformed
        发送福利(4);
    }//GEN-LAST:event_z4ActionPerformed

    private void z1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z1ActionPerformed
        发送福利(1);        // TODO add your handling code here:
    }//GEN-LAST:event_z1ActionPerformed

    private void z3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z3ActionPerformed
        发送福利(3);
    }//GEN-LAST:event_z3ActionPerformed

    private void z2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z2ActionPerformed
        发送福利(2);
    }//GEN-LAST:event_z2ActionPerformed

    private void 重载任务ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载任务ActionPerformed
        // TODO add your handling code here:
        MapleQuest.clearQuests();
        System.out.println("[重载系统] 任务重载成功。");
        JOptionPane.showMessageDialog(null, "任务重载成功。");
    }//GEN-LAST:event_重载任务ActionPerformed

    private void 重载包头按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载包头按钮ActionPerformed

        System.out.println("[重载系统] 包头重载成功。");
        JOptionPane.showMessageDialog(null, "包头重载成功。");
    }//GEN-LAST:event_重载包头按钮ActionPerformed

    private void 重载商店按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载商店按钮ActionPerformed
        // TODO add your handling code here:
        MapleShopFactory.getInstance().clear();
        System.out.println("[重载系统] 商店重载成功。");
        JOptionPane.showMessageDialog(null, "商店重载成功。");
    }//GEN-LAST:event_重载商店按钮ActionPerformed

    private void 重载商城按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载商城按钮ActionPerformed
        // TODO add your handling code here:
        CashItemFactory.getInstance().clearCashShop();
        System.out.println("[重载系统] 商城重载成功。");
        JOptionPane.showMessageDialog(null, "商城重载成功。");
    }//GEN-LAST:event_重载商城按钮ActionPerformed

    private void 重载传送门按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载传送门按钮ActionPerformed
        // TODO add your handling code here:
        PortalScriptManager.getInstance().clearScripts();
        System.out.println("[重载系统] 传送门重载成功。");
        JOptionPane.showMessageDialog(null, "传送门重载成功。");
    }//GEN-LAST:event_重载传送门按钮ActionPerformed

    private void 重载反应堆按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载反应堆按钮ActionPerformed
        // TODO add your handling code here:
        ReactorScriptManager.getInstance().clearDrops();
        System.out.println("[重载系统] 反应堆重载成功。");
        JOptionPane.showMessageDialog(null, "反应堆重载成功。");
    }//GEN-LAST:event_重载反应堆按钮ActionPerformed

    private void 重载爆率按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载爆率按钮ActionPerformed
        // TODO add your handling code here:
        MapleMonsterInformationProvider.getInstance().clearDrops();
        System.out.println("[重载系统] 爆率重载成功。");
        JOptionPane.showMessageDialog(null, "爆率重载成功。");
    }//GEN-LAST:event_重载爆率按钮ActionPerformed

    private void 重载副本按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载副本按钮ActionPerformed
        // TODO add your handling code here:
        for (ChannelServer instance1 : ChannelServer.getAllInstances()) {
            if (instance1 != null) {
                instance1.reloadEvents();
            }
        }
        System.out.println("[重载系统] 副本重载成功。");
        JOptionPane.showMessageDialog(null, "副本重载成功。");
    }//GEN-LAST:event_重载副本按钮ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        openWindow(Windows.删除自添加NPC工具);
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        openWindow(Windows.代码查询工具);
        if (!LoginServer.isShutdown() || searchServer) {
            return;
        }
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton46ActionPerformed
        // TODO add your handling code here:

        openWindow(Windows.活动控制台);
    }//GEN-LAST:event_jButton46ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        int p = 0;
        for (handling.channel.ChannelServer cserv : handling.channel.ChannelServer.getAllInstances()) {
            p++;
            cserv.closeAllMerchants();
        }
        merchant_main.getInstance().save_data();
        String 输出 = "[保存雇佣商人系统] 雇佣商人保存" + p + "个频道成功";
        JOptionPane.showMessageDialog(null, "雇佣商人保存" + p + "个频道成功");
        printChatLog(输出);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int p = 0;
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                p++;
                chr.saveToDB(true, true);
            }
        }
        String 输出 = "[保存数据系统] 保存" + p + "个成功。";
        JOptionPane.showMessageDialog(null, 输出);
        printChatLog(输出);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton44ActionPerformed
        openWindow(Windows.游戏抽奖工具);
    }//GEN-LAST:event_jButton44ActionPerformed

    private void 魔族攻城开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_魔族攻城开关ActionPerformed
        按键开关("魔族攻城开关", 2404);
        刷新魔族攻城开关();
    }//GEN-LAST:event_魔族攻城开关ActionPerformed

    private void 魔族突袭开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_魔族突袭开关ActionPerformed
        按键开关("魔族突袭开关", 2400);
        刷新魔族突袭开关();
    }//GEN-LAST:event_魔族突袭开关ActionPerformed

    private void 神秘商人开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_神秘商人开关ActionPerformed
        按键开关("神秘商人开关", 2406);
        刷新神秘商人开关();
    }//GEN-LAST:event_神秘商人开关ActionPerformed

    private void 幸运职业开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_幸运职业开关ActionPerformed
        按键开关("幸运职业开关", 749);
        刷新幸运职业开关();
    }//GEN-LAST:event_幸运职业开关ActionPerformed

    private void 骑士团职业开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_骑士团职业开关ActionPerformed
        按键开关("骑士团职业开关", 2001);
        刷新骑士团职业开关();
    }//GEN-LAST:event_骑士团职业开关ActionPerformed

    private void 战神职业开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_战神职业开关ActionPerformed
        按键开关("战神职业开关", 2002);
        刷新战神职业开关();
    }//GEN-LAST:event_战神职业开关ActionPerformed

    private void 冒险家职业开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_冒险家职业开关ActionPerformed
        按键开关("冒险家职业开关", 2000);
        刷新冒险家职业开关();
    }//GEN-LAST:event_冒险家职业开关ActionPerformed

    private void jButtonMaxCharacterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMaxCharacterActionPerformed
        // TODO add your handling code here:
        if (jTextFieldMaxCharacterNumber.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "不能为空");
            return;
        }
        boolean result2 = this.jTextFieldMaxCharacterNumber.getText().matches("[0-9]+");
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        if (result2) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM configvalues WHERE id = ?");
                ps1.setInt(1, 400000);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString2 = null;
                    sqlString2 = "update configvalues set Val='" + this.jTextFieldMaxCharacterNumber.getText() + "' where id = 400000;";
                    PreparedStatement dropperid = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    dropperid.executeUpdate(sqlString2);
                    //UpdateMaxCharacterSlots();
                    //FengYeDuan.GetConfigValues();
                    //刷新冒险家等级上限();
                    dropperid.close();
                    JOptionPane.showMessageDialog(null, "修改成功");
                }
                rs.close();
                ps1.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "只能填数字0-9");
        }
    }//GEN-LAST:event_jButtonMaxCharacterActionPerformed

    private void jTextFieldMaxCharacterNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMaxCharacterNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMaxCharacterNumberActionPerformed

    private void 修改骑士团等级上限ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改骑士团等级上限ActionPerformed
        if (骑士团等级上限.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "不能为空");
            return;
        }
        boolean result2 = this.骑士团等级上限.getText().matches("[0-9]+");
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        if (result2) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM configvalues WHERE id = ?");
                ps1.setInt(1, 2301);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString2 = null;
                    sqlString2 = "update configvalues set Val='" + this.骑士团等级上限.getText() + "' where id = 2301;";
                    PreparedStatement dropperid = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    dropperid.executeUpdate(sqlString2);
                    FengYeDuan.GetConfigValues();
                    刷新骑士团等级上限();
                    JOptionPane.showMessageDialog(null, "修改成功");
                }
                rs.close();
                ps1.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_修改骑士团等级上限ActionPerformed

    private void 骑士团等级上限ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_骑士团等级上限ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_骑士团等级上限ActionPerformed

    private void 修改冒险家等级上限ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改冒险家等级上限ActionPerformed

        if (jTextMaxLevel.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "不能为空");
            return;
        }
        boolean result2 = this.jTextMaxLevel.getText().matches("[0-9]+");
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        if (result2) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM configvalues WHERE id = ?");
                ps1.setInt(1, 2300);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString2 = null;
                    sqlString2 = "update configvalues set Val='" + this.jTextMaxLevel.getText() + "' where id = 2300;";
                    PreparedStatement dropperid = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    dropperid.executeUpdate(sqlString2);
                    FengYeDuan.GetConfigValues();
                    刷新冒险家等级上限();
                    JOptionPane.showMessageDialog(null, "修改成功");
                }
                rs.close();
                ps1.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_修改冒险家等级上限ActionPerformed

    private void jTextMaxLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextMaxLevelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextMaxLevelActionPerformed

    private void 花蘑菇开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_花蘑菇开关ActionPerformed
        按键开关("花蘑菇开关", 2219);
        刷新花蘑菇开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_花蘑菇开关ActionPerformed

    private void 青鳄鱼开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_青鳄鱼开关ActionPerformed
        按键开关("青鳄鱼开关", 2218);
        刷新青鳄鱼开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_青鳄鱼开关ActionPerformed

    private void 小白兔开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_小白兔开关ActionPerformed
        按键开关("小白兔开关", 2215);
        刷新小白兔开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_小白兔开关ActionPerformed

    private void 火野猪开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_火野猪开关ActionPerformed
        按键开关("火野猪开关", 2217);
        刷新火野猪开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_火野猪开关ActionPerformed

    private void 喷火龙开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_喷火龙开关ActionPerformed
        按键开关("喷火龙开关", 2216);
        刷新喷火龙开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_喷火龙开关ActionPerformed

    private void 大灰狼开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_大灰狼开关ActionPerformed
        按键开关("大灰狼开关", 2214);
        刷新大灰狼开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_大灰狼开关ActionPerformed

    private void 紫色猫开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_紫色猫开关ActionPerformed
        按键开关("紫色猫开关", 2213);
        刷新紫色猫开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_紫色猫开关ActionPerformed

    private void 石头人开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_石头人开关ActionPerformed
        按键开关("石头人开关", 2212);
        刷新石头人开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_石头人开关ActionPerformed

    private void 白雪人开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_白雪人开关ActionPerformed
        按键开关("白雪人开关", 2211);
        刷新白雪人开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_白雪人开关ActionPerformed

    private void 胖企鹅开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_胖企鹅开关ActionPerformed
        按键开关("胖企鹅开关", 2210);
        刷新胖企鹅开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_胖企鹅开关ActionPerformed

    private void 星精灵开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_星精灵开关ActionPerformed
        按键开关("星精灵开关", 2209);
        刷新星精灵开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_星精灵开关ActionPerformed

    private void 顽皮猴开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_顽皮猴开关ActionPerformed
        按键开关("顽皮猴开关", 2208);
        刷新顽皮猴开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_顽皮猴开关ActionPerformed

    private void 章鱼怪开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_章鱼怪开关ActionPerformed
        按键开关("章鱼怪开关", 2207);
        刷新章鱼怪开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_章鱼怪开关ActionPerformed

    private void 大海龟开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_大海龟开关ActionPerformed
        按键开关("大海龟开关", 2206);
        刷新大海龟开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_大海龟开关ActionPerformed

    private void 红螃蟹开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_红螃蟹开关ActionPerformed
        按键开关("红螃蟹开关", 2205);
        刷新红螃蟹开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_红螃蟹开关ActionPerformed

    private void 小青蛇开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_小青蛇开关ActionPerformed
        按键开关("小青蛇开关", 2204);
        刷新小青蛇开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_小青蛇开关ActionPerformed

    private void 漂漂猪开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_漂漂猪开关ActionPerformed
        按键开关("漂漂猪开关", 2203);
        刷新漂漂猪开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_漂漂猪开关ActionPerformed

    private void 绿水灵开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_绿水灵开关ActionPerformed
        按键开关("绿水灵开关", 2202);
        刷新绿水灵开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_绿水灵开关ActionPerformed

    private void 蘑菇仔开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_蘑菇仔开关ActionPerformed
        按键开关("蘑菇仔开关", 2201);
        刷新蘑菇仔开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_蘑菇仔开关ActionPerformed

    private void 蓝蜗牛开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_蓝蜗牛开关ActionPerformed
        按键开关("蓝蜗牛开关", 2200);
        刷新蓝蜗牛开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_蓝蜗牛开关ActionPerformed

    private void 修改广播ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改广播ActionPerformed
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        try {
            ps = DatabaseConnection.getConnection().prepareStatement("UPDATE 广播信息 SET 广播 = ? WHERE id = ?");
            ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM 广播信息  WHERE id = ? ");
            ps1.setInt(1, Integer.parseInt(广播序号.getText()));
            rs = ps1.executeQuery();
            if (rs.next()) {
                String sqlString1 = null;
                sqlString1 = "update 广播信息 set 广播 = '" + 广播文本.getText() + "' where id = " + Integer.parseInt(广播序号.getText()) + ";";
                PreparedStatement a1 = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                a1.executeUpdate(sqlString1);
                刷新公告广播();
                JOptionPane.showMessageDialog(null, "修改成功。");
            }
            rs.close();
            ps1.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_修改广播ActionPerformed

    private void 发布广告ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_发布广告ActionPerformed
        if (广播文本.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请填写广告信息哦。");
            return;
        }
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO 广播信息 ( 广播 ) VALUES ( ? )")) {
            ps.setString(1, 广播文本.getText());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
        }
        刷新公告广播();
        JOptionPane.showMessageDialog(null, "发布完成。");
    }//GEN-LAST:event_发布广告ActionPerformed

    private void 删除广播ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除广播ActionPerformed

        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.广播序号.getText().matches("[0-9]+");

        if (result1) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM 广播信息 WHERE id = ?");
                ps1.setInt(1, Integer.parseInt(this.广播序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from 广播信息 where id =" + Integer.parseInt(this.广播序号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新公告广播();
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_删除广播ActionPerformed

    private void 刷新广告ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新广告ActionPerformed
        刷新公告广播();
    }//GEN-LAST:event_刷新广告ActionPerformed

    private void 开启三倍金币ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启三倍金币ActionPerformed
        boolean result1 = this.三倍金币持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (三倍金币持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始金币 = Integer.parseInt(ServerProperties.getProperty("FengYeDuan.mesoRate"));
            int 三倍金币活动 = 原始金币 * 3;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.三倍金币持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "金币";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(三倍金币活动);
            }
            World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, 20, "[倍率活动] : 游戏开始 3 倍打怪金币活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            JOptionPane.showMessageDialog(null, "成功开启三倍金币活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启三倍金币ActionPerformed

    private void 开启三倍爆率ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启三倍爆率ActionPerformed
        boolean result1 = this.三倍爆率持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (三倍爆率持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始爆率 = Integer.parseInt(ServerProperties.getProperty("FengYeDuan.dropRate"));
            int 三倍爆率活动 = 原始爆率 * 3;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.三倍经验持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "爆率";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(三倍爆率活动);
            }
            World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, 20, "[倍率活动] : 游戏开始 3 倍打怪爆率活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            JOptionPane.showMessageDialog(null, "成功开启三倍爆率活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启三倍爆率ActionPerformed

    private void 开启三倍经验ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启三倍经验ActionPerformed
        boolean result1 = this.三倍经验持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (三倍经验持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始经验 = Integer.parseInt(ServerProperties.getProperty("FengYeDuan.expRate"));
            int 三倍经验活动 = 原始经验 * 3;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.三倍经验持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "经验";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(三倍经验活动);
            }
            World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, 20, "[倍率活动] : 游戏开始 3 倍打怪经验活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            JOptionPane.showMessageDialog(null, "成功开启三倍经验活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启三倍经验ActionPerformed

    private void 开启双倍金币ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启双倍金币ActionPerformed
        boolean result1 = this.双倍金币持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (双倍金币持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始金币 = Integer.parseInt(ServerProperties.getProperty("FengYeDuan.mesoRate"));
            int 双倍金币活动 = 原始金币 * 2;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.双倍金币持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "金币";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(双倍金币活动);
            }
            World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, 20, "[倍率活动] : 游戏开始 2 倍打怪金币活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            JOptionPane.showMessageDialog(null, "成功开启双倍金币活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启双倍金币ActionPerformed

    private void 开启双倍爆率ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启双倍爆率ActionPerformed
        boolean result1 = this.双倍爆率持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (双倍爆率持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始爆率 = Integer.parseInt(ServerProperties.getProperty("FengYeDuan.dropRate"));
            int 双倍爆率活动 = 原始爆率 * 2;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.双倍经验持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "爆率";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(双倍爆率活动);
            }
            World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, 20, "[倍率活动] : 游戏开始 2 倍打怪爆率活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            JOptionPane.showMessageDialog(null, "成功开启双倍爆率活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启双倍爆率ActionPerformed

    //活动经验设置
    private void 开启双倍经验ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启双倍经验ActionPerformed
        boolean result1 = this.双倍经验持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (双倍经验持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始经验 = Integer.parseInt(ServerProperties.getProperty("FengYeDuan.expRate"));
            int 双倍经验活动 = 原始经验 * 2;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.双倍经验持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "经验";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(双倍经验活动);
            }
            World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, 20, "[倍率活动] : 游戏开始 2 倍打怪经验活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            // World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(9, 20, "[倍率活动] : 游戏开始 2 倍打怪经验活动，将持续 \" + hours + \" 小时，请各位玩家狂欢吧！").getBytes());
            JOptionPane.showMessageDialog(null, "成功开启双倍经验活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启双倍经验ActionPerformed

    private void 游戏经验加成说明ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_游戏经验加成说明ActionPerformed
        JOptionPane.showMessageDialog(null, "<相关说明文>\r\n\r\n"
            + "1:相对应数值为0则为关闭经验加成。\r\n"
            + "2:人气经验 = 人气 * 人气经验加成数值。\r\n"
            + "\r\n");
    }//GEN-LAST:event_游戏经验加成说明ActionPerformed

    private void 经验加成表修改ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_经验加成表修改ActionPerformed

        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.经验加成表序号.getText().matches("[0-9]+");
        if (result1) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM configvalues WHERE id = ?");
                ps1.setInt(1, Integer.parseInt(this.经验加成表序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString1 = null;
                    sqlString1 = "update configvalues set Val = '" + this.经验加成表数值.getText() + "' where id= " + this.经验加成表序号.getText() + ";";
                    PreparedStatement Val = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                    Val.executeUpdate(sqlString1);
                    刷新经验加成表();
                    FengYeDuan.GetConfigValues();
                    JOptionPane.showMessageDialog(null, "修改成功已经生效");
                }
                rs.close();
                ps1.close();
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要修改的值");
        }
    }//GEN-LAST:event_经验加成表修改ActionPerformed

    private void jButtonSNRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSNRefreshActionPerformed
        // TODO add your handling code here:
        String sql = "SELECT * FROM nxcodez ORDER BY ID DESC";
        NXDisplay(sql);
    }//GEN-LAST:event_jButtonSNRefreshActionPerformed

    private void jButtonSNProductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSNProductionActionPerformed
        // TODO add your handling code here:
        int productionNum = Integer.parseInt(jTextFieldProductionNum.getText());
        int i = 0;

        try {
            String currentTime = new MapleStoryDate().getCurrentTime();
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            MapleStoryJUtil mj = new MapleStoryJUtil();
            int index = jComboBoxMoneyb.getSelectedIndex();
            /*
            String strType = "";
            if(index == 0){
                strType = "点券";
            }else if(index == 1){
                strType = "抵用券";
            }else if(index == 2){
                strType = "元宝";
            }*/
            for(i=0; i< productionNum; i++)
            {
                ps = con.prepareStatement("INSERT INTO nxcodez(type,value, sn) VALUES(?, ?, ?)");
                ps.setInt(1, index);
                ps.setString(2, jTextFieldSNValue.getText());
                ps.setString(3, mj.getUUID());
                //ps.setString(4, currentTime);
                //ps.setInt(3, ret);
                ps.execute();
            }
            JOptionPane.showMessageDialog(null, "[信息]:插入卡号"  + this.jTextFieldProductionNum.getText() + "个成功。");
            String sql = "SELECT * FROM nxcodez ORDER BY ID DESC";
            NXDisplay(sql);
            //jLabelPromote.setText("插入卡号 " + this.jTextFieldProductionNum.getText() + "个成功。");
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonSNProductionActionPerformed

    private void jButtonOderByUseTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOderByUseTimeActionPerformed
        // TODO add your handling code here:
        String sql = "SELECT * FROM nxcodez WHERE isused=1 ORDER BY value DESC";
        NXDisplay(sql);
    }//GEN-LAST:event_jButtonOderByUseTimeActionPerformed

    private void jButtonUnusedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUnusedActionPerformed
        // TODO add your handling code here:
        String sql = "SELECT * FROM nxcodez WHERE isused=0 ORDER BY ID DESC";
        NXDisplay(sql);
    }//GEN-LAST:event_jButtonUnusedActionPerformed

    private void jButtonUsedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUsedActionPerformed
        // TODO add your handling code here:

        String sql = "SELECT * FROM nxcodez WHERE isused=1 ORDER BY inserttime DESC";
        NXDisplay(sql);
    }//GEN-LAST:event_jButtonUsedActionPerformed

    private void jButtonUnlockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUnlockActionPerformed
        // TODO add your handling code here:
        try{
            String strNum = jTextFieldNum.getText();//要解禁的卡号
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("UPDATE nxcodez SET isforbid=0 WHERE id=?");
            ps.setInt(1, Integer.parseInt(strNum));
            ps.execute();
            ps.close();
            JOptionPane.showMessageDialog(null, "[信息]:禁用卡号成功。");
            String sql = "SELECT * FROM nxcodez ORDER BY ID DESC";
            NXDisplay(sql);
        }catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonUnlockActionPerformed

    private void jButtonSNSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSNSearchActionPerformed
        // TODO add your handling code here:

        String SN = jTextFieldSN.getText();//要查找的卡号
        //JOptionPane.showMessageDialog(null, "[信息]：查找卡号成功。");
        String sql = "SELECT * FROM nxcodez WHERE sn='" + SN +"' ORDER BY ID DESC";
        NXDisplay(sql);
    }//GEN-LAST:event_jButtonSNSearchActionPerformed

    private void jButtonSNDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSNDeleteActionPerformed
        // TODO add your handling code here:

        try{
            String strNum = jTextFieldNum.getText();//要删除的序号
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("DELETE FROM nxcodez WHERE id=?");
            ps.setInt(1, Integer.parseInt(strNum));
            ps.execute();
            JOptionPane.showMessageDialog(null, "[信息]:删除卡号成功。");
            String sql = "SELECT * FROM nxcodez ORDER BY ID DESC";
            NXDisplay(sql);
        }catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonSNDeleteActionPerformed

    private void jButtonSNModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSNModifyActionPerformed
        // TODO add your handling code here:
        try{
            String strNum = jTextFieldNum.getText();//序号
            String strValue = jTextFieldCSPointsValue.getText();
            String strSN = jTextFieldSN.getText();
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            //ResultSet rs = null;
            ps = con.prepareStatement("UPDATE nxcodez SET value=?, SN=? WHERE id=?");

            ps.setString(1, strValue);
            ps.setString(2, strSN);
            ps.setInt(3, Integer.parseInt(strNum));
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "[信息]:修改卡号成功。");
            String sql = "SELECT * FROM nxcodez ORDER BY ID DESC";
            NXDisplay(sql);
        }catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonSNModifyActionPerformed

    private void jButtonLockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLockActionPerformed
        // TODO add your handling code here:
        try{
            String strNum = jTextFieldNum.getText();//要禁用的序号
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("UPDATE nxcodez SET isforbid=1 WHERE id=?");
            ps.setInt(1, Integer.parseInt(strNum));
            ps.execute();
            ps.close();
            JOptionPane.showMessageDialog(null, "[信息]:禁用卡号成功。");
            String sql = "SELECT * FROM nxcodez ORDER BY ID DESC";
            NXDisplay(sql);
        }catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonLockActionPerformed

    private void jButtonSNSearchByAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSNSearchByAccountActionPerformed
        // TODO add your handling code here:

        String account = jTextFieldAccount.getText();//要查找的账号
        //JOptionPane.showMessageDialog(null, "[信息]：查找卡号成功。");
        String sql = "SELECT * FROM nxcodez WHERE accountid='" + account +"' ORDER BY ID DESC";
        NXDisplay(sql);
    }//GEN-LAST:event_jButtonSNSearchByAccountActionPerformed

    private void jTextFieldAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAccountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAccountActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void startserverbutton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startserverbutton1ActionPerformed
        // TODO add your handling code here:
        try (Connection con = DatabaseConnection.getConnection()) {
            String code = jTextField1.getText();
            if (code == null || code.equals("")) {
                JOptionPane.showMessageDialog(null, "[信息]:请填写授权码。");
            }
            PreparedStatement ps = con.prepareStatement("delete from configvalues where id = ?");
            ps.setInt(1, codeid);
            ps.execute();
            ps = con.prepareStatement("insert into configvalues values (?, ?, 1)");
            ps.setInt(1, codeid);
            ps.setString(2, code);
            ps.execute();
            ps.close();
            JOptionPane.showMessageDialog(null, "[信息]:授权码修改成功。");
            authCode = code;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "[信息]:修改授权码失败。");
        }
    }//GEN-LAST:event_startserverbutton1ActionPerformed

    private void startserverbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startserverbuttonActionPerformed

        if (开启服务端 == false) {
            try {
                if (!checkAuth()) {
                    return;
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        } else {
            System.out.println("枫叶服务端正在运行中！");
            return;
        }
        new Thread(new Runnable() {
            public void run() {

                Start.是否控制台启动 = true;
                Start.main(null);

                // startserverbutton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
                String 输出 = "[启动完毕] 开服成功，可以进入游戏了!";
                startserverbutton.setText("正在运行中...");
                printChatLog(输出);
            }
        }).start();
        Dis tt = new Dis();
        tt.start();
        开启服务端 = true;
    }//GEN-LAST:event_startserverbuttonActionPerformed

    private void jTextField22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField22ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        重启服务器();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void 回收地图开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_回收地图开关ActionPerformed
        按键开关("回收地图开关", 2029);
        刷新回收地图开关();
    }//GEN-LAST:event_回收地图开关ActionPerformed

    private void 屠令广播开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_屠令广播开关ActionPerformed
        按键开关("屠令广播开关", 2016);
        刷新屠令广播开关();
    }//GEN-LAST:event_屠令广播开关ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        sendNoticeGG();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void 吸怪检测开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_吸怪检测开关ActionPerformed
        按键开关("吸怪检测开关", 2130);
        刷新吸怪检测开关();
    }//GEN-LAST:event_吸怪检测开关ActionPerformed

    private void 指令通知开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_指令通知开关ActionPerformed
        按键开关("指令通知开关", 2028);
        刷新指令通知开关();
    }//GEN-LAST:event_指令通知开关ActionPerformed

    private void 过图存档开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_过图存档开关ActionPerformed
        按键开关("过图存档开关", 2140);
        刷新过图存档时间();
    }//GEN-LAST:event_过图存档开关ActionPerformed

    private void 地图名称开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_地图名称开关ActionPerformed
        按键开关("地图名称开关", 2136);
        刷新地图名称开关();
    }//GEN-LAST:event_地图名称开关ActionPerformed

    private void 怪物状态开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_怪物状态开关ActionPerformed
        按键开关("怪物状态开关", 2061);
        刷新怪物状态开关();
    }//GEN-LAST:event_怪物状态开关ActionPerformed

    private void 越级打怪开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_越级打怪开关ActionPerformed
        按键开关("越级打怪开关", 2125);
        刷新越级打怪开关();
    }//GEN-LAST:event_越级打怪开关ActionPerformed

    private void 登陆帮助开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_登陆帮助开关ActionPerformed
        按键开关("登陆帮助开关", 2058);
        刷新登陆帮助();
    }//GEN-LAST:event_登陆帮助开关ActionPerformed

    private void 欢迎弹窗开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_欢迎弹窗开关ActionPerformed
        按键开关("欢迎弹窗开关", 2015);
        刷新欢迎弹窗开关();
    }//GEN-LAST:event_欢迎弹窗开关ActionPerformed

    private void 雇佣商人开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_雇佣商人开关ActionPerformed
        按键开关("雇佣商人开关", 2020);
        刷新雇佣商人开关();
    }//GEN-LAST:event_雇佣商人开关ActionPerformed

    private void 玩家交易开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_玩家交易开关ActionPerformed
        按键开关("玩家交易开关", 2011);
        刷新玩家交易开关();
    }//GEN-LAST:event_玩家交易开关ActionPerformed

    private void 游戏喇叭开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_游戏喇叭开关ActionPerformed
        按键开关("游戏喇叭开关", 2009);
        刷新游戏喇叭开关();
    }//GEN-LAST:event_游戏喇叭开关ActionPerformed

    private void 管理加速开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_管理加速开关ActionPerformed
        按键开关("管理加速开关", 2007);
        刷新管理加速开关();
    }//GEN-LAST:event_管理加速开关ActionPerformed

    private void 管理隐身开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_管理隐身开关ActionPerformed
        按键开关("管理隐身开关", 2006);
        刷新管理隐身开关();
    }//GEN-LAST:event_管理隐身开关ActionPerformed

    private void 上线提醒开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_上线提醒开关ActionPerformed
        按键开关("上线提醒开关", 2021);
        刷新上线提醒开关();
    }//GEN-LAST:event_上线提醒开关ActionPerformed

    private void 游戏指令开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_游戏指令开关ActionPerformed
        按键开关("游戏指令开关", 2008);
        刷新游戏指令开关();
    }//GEN-LAST:event_游戏指令开关ActionPerformed

    private void 丢出物品开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_丢出物品开关ActionPerformed
        按键开关("丢出物品开关", 2012);
        刷新丢出物品开关();
    }//GEN-LAST:event_丢出物品开关ActionPerformed

    private void 丢出金币开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_丢出金币开关ActionPerformed
        按键开关("丢出金币开关", 2010);
        刷新丢出金币开关();
    }//GEN-LAST:event_丢出金币开关ActionPerformed

    private void 游戏升级快讯ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_游戏升级快讯ActionPerformed
        按键开关("升级快讯开关", 2003);
        刷新升级快讯();
    }//GEN-LAST:event_游戏升级快讯ActionPerformed

    private void 玩家聊天开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_玩家聊天开关ActionPerformed
        按键开关("玩家聊天开关", 2024);
        刷新玩家聊天开关();
    }//GEN-LAST:event_玩家聊天开关ActionPerformed

    private void 滚动公告开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_滚动公告开关ActionPerformed
        按键开关("滚动公告开关", 2026);
        刷新滚动公告开关();
    }//GEN-LAST:event_滚动公告开关ActionPerformed

    private void 禁止登陆开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_禁止登陆开关ActionPerformed
        按键开关("禁止登陆开关", 2013);
        刷新禁止登陆开关();
    }//GEN-LAST:event_禁止登陆开关ActionPerformed

    private void 商品时间ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_商品时间ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_商品时间ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        etcCheck = !etcCheck;
        this.jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource(etcCheck?"ON4.png":"OFF4.png")));
        ConfigValuesMap.put("其他栏叠加开关", etcCheck?1:0);
        按键开关2("其他栏叠加开关", 221002, etcCheck?1:0);
        MapleItemInformationProvider.getInstance().clearSlotMaxCache();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        useCheck = !useCheck;
        this.jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource(useCheck?"ON4.png":"OFF4.png")));
        ConfigValuesMap.put("消耗栏叠加开关", etcCheck?1:0);
        按键开关2("消耗栏叠加开关", 221003, etcCheck?1:0);
        MapleItemInformationProvider.getInstance().clearSlotMaxCache();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        String text1 = jTextField3.getText();
        String text2 = jTextField4.getText();
        String text3 = jTextField5.getText();
        if (!text1.matches("[0-9]+") || !text2.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(null, "数量输入不正确");
        } else if (text3 != null && !"".equals(text3) && !text3.matches("[0-9]+") && !text3.contains(",")) {
            JOptionPane.showMessageDialog(null, "排除项输入不正确，请将物品id用逗号隔开");
        } else {
            etcSlotMax = Integer.parseInt(text1);
            useSlotMax = Integer.parseInt(text2);
            if (etcSlotMax > 25000 || useSlotMax > 25000) {
                JOptionPane.showMessageDialog(null, "数量输入不正确，不能超过25000");
                return;
            }
            exclude = text3;
            String[] split = text3.split(",");
            java.util.List<String> keys = new ArrayList<>();
            for (String key:ConfigValuesMap.keySet()) {
                if (key.startsWith("叠加排除项")) {
                    keys.add(key);
                }
            }
            for (String key:keys) {
                ConfigValuesMap.remove(key);
            }
            try (Connection con = DatabaseConnection.getConnection()) {
                //ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");
                PreparedStatement ps1 = con.prepareStatement("delete FROM configvalues WHERE Name like '叠加排除项%'");
                ps1.execute();
                ps1.close();
                //ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
            int x = 1;
            for (String key:split) {
                ConfigValuesMap.put("叠加排除项"+key, 1);
                按键开关2("叠加排除项"+key, 221003 + x, 1);
                x++;
            }
            ConfigValuesMap.put("其他栏叠加数量", etcSlotMax);
            按键开关2("其他栏叠加数量", 221000, etcSlotMax);
            ConfigValuesMap.put("消耗栏叠加数量", useSlotMax);
            按键开关2("消耗栏叠加数量", 221001, useSlotMax);
            JOptionPane.showMessageDialog(null, "修改成功");
            MapleItemInformationProvider.getInstance().clearSlotMaxCache();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        ServerConfig.levelCheck1 = !ServerConfig.levelCheck1;
        this.jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource(ServerConfig.levelCheck1?"ON4.png":"OFF4.png")));
        ConfigValuesMap.put("阶段一等级经验开关", ServerConfig.levelCheck1?1:0);
        按键开关2("阶段一等级经验开关", 220001, ServerConfig.levelCheck1?1:0);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        ServerConfig.levelCheck2 = !ServerConfig.levelCheck2;
        this.jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource(ServerConfig.levelCheck2?"ON4.png":"OFF4.png")));
        ConfigValuesMap.put("阶段二等级经验开关", ServerConfig.levelCheck2?1:0);
        按键开关2("阶段二等级经验开关", 220002, ServerConfig.levelCheck2?1:0);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        ServerConfig.levelCheck3 = !ServerConfig.levelCheck3;
        this.jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource(ServerConfig.levelCheck3?"ON4.png":"OFF4.png")));
        ConfigValuesMap.put("阶段三等级经验开关", ServerConfig.levelCheck3?1:0);
        按键开关2("阶段三等级经验开关", 220003, ServerConfig.levelCheck3?1:0);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        String minLevel1 = this.jTextField6.getText();
        String maxLevel1 = this.jTextField7.getText();
        String expLevel1 = this.jTextField8.getText();
        String minLevel2 = this.jTextField9.getText();
        String maxLevel2 = this.jTextField10.getText();
        String expLevel2 = this.jTextField11.getText();
        String minLevel3 = this.jTextField12.getText();
        String maxLevel3 = this.jTextField13.getText();
        String expLevel3 = this.jTextField14.getText();
        
        if (ServerConfig.levelCheck1) {
            if (minLevel1.matches("[0-9]+") && maxLevel1.matches("[0-9]+") && expLevel1.matches("[0-9]+") 
                    && Integer.parseInt(minLevel1) < Integer.parseInt(maxLevel1)) {
                ServerConfig.minLevel1 = Integer.parseInt(minLevel1);
                ServerConfig.maxLevel1 = Integer.parseInt(maxLevel1);
                ServerConfig.BeiShu1 = Integer.parseInt(expLevel1);
                ConfigValuesMap.put("阶段一最小等级", ServerConfig.minLevel1);
                ConfigValuesMap.put("阶段一最大等级", ServerConfig.maxLevel1);
                ConfigValuesMap.put("阶段一经验倍数", ServerConfig.BeiShu1);
                按键开关2("阶段一最小等级", 220004, ServerConfig.minLevel1);
                按键开关2("阶段一最大等级", 220005, ServerConfig.maxLevel1);
                按键开关2("阶段一经验倍数", 220006, ServerConfig.BeiShu1);
                JOptionPane.showMessageDialog(null, "一阶段修改成功");
            } else {
                JOptionPane.showMessageDialog(null, "一阶段填写格式/数值不正确或者没有填写完");
            }
        } else {
            JOptionPane.showMessageDialog(null, "一阶段未开启");
        }
        if (ServerConfig.levelCheck2) {
            if (minLevel2.matches("[0-9]+") && maxLevel2.matches("[0-9]+") && expLevel2.matches("[0-9]+") 
                    && Integer.parseInt(minLevel2) < Integer.parseInt(maxLevel2)) {
                if (maxLevel1.matches("[0-9]+") && (Integer.parseInt(minLevel2) <= Integer.parseInt(maxLevel1) || Integer.parseInt(maxLevel2) <= Integer.parseInt(maxLevel1))) {
                    JOptionPane.showMessageDialog(null, "二阶段的等级必须大于一阶段最大等级");
                    return;
                }
                ServerConfig.minLevel2 = Integer.parseInt(minLevel2);
                ServerConfig.maxLevel2 = Integer.parseInt(maxLevel2);
                ServerConfig.BeiShu2 = Integer.parseInt(expLevel2);
                ConfigValuesMap.put("阶段二最小等级", ServerConfig.minLevel2);
                ConfigValuesMap.put("阶段二最大等级", ServerConfig.maxLevel2);
                ConfigValuesMap.put("阶段二经验倍数", ServerConfig.BeiShu2);
                按键开关2("阶段二最小等级", 220007, ServerConfig.minLevel2);
                按键开关2("阶段二最大等级", 220008, ServerConfig.maxLevel2);
                按键开关2("阶段二经验倍数", 220009, ServerConfig.BeiShu2);
                JOptionPane.showMessageDialog(null, "二阶段修改成功");
            } else {
                JOptionPane.showMessageDialog(null, "二阶段填写格式/数值不正确或者没有填写完");
            }
        } else {
            JOptionPane.showMessageDialog(null, "二阶段未开启");
        }
        if (ServerConfig.levelCheck3) {
            if (minLevel3.matches("[0-9]+") && maxLevel3.matches("[0-9]+") && expLevel3.matches("[0-9]+") 
                    && Integer.parseInt(minLevel3) < Integer.parseInt(maxLevel3)) {
                if (maxLevel2.matches("[0-9]+") && (Integer.parseInt(minLevel3) <= Integer.parseInt(maxLevel2) || Integer.parseInt(maxLevel3) <= Integer.parseInt(maxLevel2))) {
                    JOptionPane.showMessageDialog(null, "三阶段的等级必须大于二阶段最大等级");
                    return;
                }
                ServerConfig.minLevel3 = Integer.parseInt(minLevel3);
                ServerConfig.maxLevel3 = Integer.parseInt(maxLevel3);
                ServerConfig.BeiShu3 = Integer.parseInt(expLevel3);
                ConfigValuesMap.put("阶段三最小等级", ServerConfig.minLevel3);
                ConfigValuesMap.put("阶段三最大等级", ServerConfig.maxLevel3);
                ConfigValuesMap.put("阶段三经验倍数", ServerConfig.BeiShu3);
                按键开关2("阶段三最小等级", 220010, ServerConfig.minLevel3);
                按键开关2("阶段三最大等级", 220011, ServerConfig.maxLevel3);
                按键开关2("阶段三经验倍数", 220012, ServerConfig.BeiShu3);
                JOptionPane.showMessageDialog(null, "三阶段修改成功");
            } else {
                JOptionPane.showMessageDialog(null, "三阶段填写格式/数值不正确或者没有填写完");
            }
        } else {
            JOptionPane.showMessageDialog(null, "三阶段未开启");
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        WorldConstants.LieDetector = !WorldConstants.LieDetector;
        this.jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource(WorldConstants.LieDetector?"ON4.png":"OFF4.png")));
        ConfigValuesMap.put("定时测谎开关", WorldConstants.LieDetector?1:0);
        按键开关2("定时测谎开关", 222001, WorldConstants.LieDetector?1:0);
        MapleItemInformationProvider.getInstance().clearSlotMaxCache();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        WorldConstants.MobLieDetector = !WorldConstants.MobLieDetector;
        this.jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource(WorldConstants.MobLieDetector?"ON4.png":"OFF4.png")));
        ConfigValuesMap.put("怪物数量测谎开关", WorldConstants.MobLieDetector?1:0);
        按键开关2("怪物数量测谎开关", 222002, WorldConstants.MobLieDetector?1:0);
        MapleItemInformationProvider.getInstance().clearSlotMaxCache();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jTextField15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15ActionPerformed

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16ActionPerformed

    private void jButton17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton17MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton17MouseClicked

    private void jButton15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton15MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton15MouseClicked

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        String text1 = jTextField16.getText();
        String text2 = jTextField15.getText();
        if (!text1.matches("[0-9]+") || !text2.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(null, "数值输入不正确");
        } else {
            int int1 = Integer.parseInt(text1);
            int int2 = Integer.parseInt(text2);
            if (int1 > 1440 || int2 > 2000000000) {
                JOptionPane.showMessageDialog(null, "数值输入不正确，定时分钟数不能超过1440分钟，怪物数量不能超过20E");
                return;
            }
            
            ConfigValuesMap.put("定时测谎分钟数", int1);
            按键开关2("定时测谎分钟数", 222003, int1);
            ConfigValuesMap.put("怪物测谎数量", int2);
            按键开关2("怪物测谎数量", 222004, int2);
            JOptionPane.showMessageDialog(null, "修改成功");
            WorldConstants.LieDetectorNum = int1;
            WorldConstants.MobLieDetectorNum = int2;
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        WorldConstants.isDiscount = !WorldConstants.isDiscount;
        this.jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource(WorldConstants.isDiscount?"ON4.png":"OFF4.png")));
        ConfigValuesMap.put("商城折扣开关", WorldConstants.isDiscount?1:0);
        按键开关2("商城折扣开关", 223001, WorldConstants.isDiscount?1:0);
        MapleItemInformationProvider.getInstance().clearSlotMaxCache();
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        String text1 = jTextField17.getText();
        if (!text1.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(null, "数值输入不正确");
        } else {
            int int1 = Integer.parseInt(text1);
            if (int1 > 100) {
                JOptionPane.showMessageDialog(null, "数值输入不正确，折扣不能超过100");
                return;
            }
            
            ConfigValuesMap.put("商城折扣数值", int1);
            按键开关2("商城折扣数值", 223002, int1);
            WorldConstants.discount = int1;
            JOptionPane.showMessageDialog(null, "修改成功");
        }
    }//GEN-LAST:event_jButton20ActionPerformed

    private static String testConnect(String path, String params) {
        InputStream in = null;
        java.net.HttpURLConnection conn = null;
        String msg = "";// 保存调用http服务后的响应信息
        try {//实例化url
            java.net.URL url = new java.net.URL(path + params);//根据url获取HttpURLConnection
            conn = (java.net.HttpURLConnection) url.openConnection();//设置请求的参数
            in = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            isr.close();
            in.close();
            msg = sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != conn) {
                conn.disconnect();
            }
        }
        return msg;
    }
    
    private static boolean checkAuth() throws UnsupportedEncodingException {
        String ip = getLocalIP() + "";
        //授权码
        String code = authCode;
        if (code == null || code.equals("")) {
            System.out.println("请先填写启动码......");
            return false;
        }
        //授权服务端的IP+端口
        String path = "http://47.99.141.130:7777/auth/checkAuth?";
        String params = "ip="+ URLEncoder.encode(ip, StandardCharsets.UTF_8.name())+"&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8.name());
        final String s = testConnect(path, params);
        if (s != null) {
            System.out.println(s);
            if (s.contains("授权有效")) {
                return true;
            }
        }
        return false;
    }
    
    public static String getLocalIP() {
        String ipAddrStr = "";
        byte[] ipAddr;
        try {
            ipAddr = InetAddress.getLocalHost().getAddress();
        } catch (UnknownHostException e) {
            return null;
        }
        for (int i = 0; i < ipAddr.length; i++) {
            if (i > 0) {
                ipAddrStr += ".";
            }
            ipAddrStr += ipAddr[i] & 0xFF;
        }
        return ipAddrStr;
    }
    
    private void NXDisplay(String sql) {
        for (int i = ((DefaultTableModel) (this.jTableSN.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.jTableSN.getModel())).removeRow(i);
        }
        
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int nType = rs.getInt("type");
                String strType = "点券"; //默认是点券
                if(nType == 1){
                    strType = "抵用券";
                }else if(nType == 2){
                    strType = "元宝";
                }
                
                String strIsused = (rs.getInt("isused") == 0) ? "未使用" :"已使用" ;
                
                String strIsForbid = (rs.getInt("isforbid") == 0) ? "正常" : "禁用";
                 
                ((DefaultTableModel) jTableSN.getModel()).insertRow(jTableSN.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    strType,
                    rs.getInt("value"),
                    rs.getString("sn"),
                    strIsused,
                    rs.getInt("accountid"),
                    rs.getString("rolename"),
                    rs.getString("usedtime"),
                    strIsForbid,
                    rs.getString("inserttime")
                });
            }
            ps.close();
            //JOptionPane.showMessageDialog(null, "[信息]:查找全部卡号成功。");
            
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        jTableSN.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = jTableSN.getSelectedRow();
                String a = jTableSN.getValueAt(i, 0).toString();
                String a1 = jTableSN.getValueAt(i, 1).toString();
                String a2 = jTableSN.getValueAt(i, 2).toString();
                String a3 = jTableSN.getValueAt(i, 3).toString();
                String a5 = jTableSN.getValueAt(i, 5).toString();
                jTextFieldNum.setText(a);
                jTextFieldType.setText(a1);
                jTextFieldCSPointsValue.setText(a2);
                jTextFieldSN.setText(a3);
                jTextFieldAccount.setText(a5);
                
            }
        });
        
    }
    
     private void 刷物品2() {
        try {
            int 数量;
            int 物品ID;
            物品ID = Integer.parseInt(全服发送物品代码.getText());
            数量 = Integer.parseInt(全服发送物品数量.getText());
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            MapleInventoryType type = GameConstants.getInventoryType(物品ID);
            for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (数量 >= 0) {
                        if (!MapleInventoryManipulator.checkSpace(mch.getClient(), 物品ID, 数量, "")) {
                            return;
                        }
                        if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)
                                || type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100) {
                            final Equip item = (Equip) (ii.getEquipById(物品ID));
                            if (ii.isCash(物品ID)) {
                                item.setUniqueId(1);
                            }
                            final String name = ii.getName(物品ID);
                            if (物品ID / 10000 == 114 && name != null && name.length() > 0) { //medal
                                final String msg = "你已获得称号 <" + name + ">";
                                mch.getClient().getPlayer().dropMessage(5, msg);
                            }
                            MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                        } else {
                            MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 数量, "", null, (byte) 0);
                        }
                    } else {
                        MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -数量, true, false);
                    }
//                    mch.getClient().getSession.write(MaplePacketCreator.getShowItemGain(物品ID, (short) 数量, true));
                    mch.getClient().getSession().write(MaplePacketCreator.getShowItemGain(物品ID, (short) 数量, true));
                }
            }
            全服发送物品代码.setText("");
            全服发送物品数量.setText("");
            JOptionPane.showMessageDialog(null, "[信息]:发送成功。");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "[信息]:错误!" + e);
        }
    }
    
    private void 刷装备2(int a) {
        try {
            int 物品ID;
            if ("物品ID".equals(全服发送装备物品ID.getText())) {
                物品ID = 0;
            } else {
                物品ID = Integer.parseInt(全服发送装备物品ID.getText());
            }
            int 力量;
            if ("力量".equals(全服发送装备装备力量.getText())) {
                力量 = 0;
            } else {
                力量 = Integer.parseInt(全服发送装备装备力量.getText());
            }
            int 敏捷;
            if ("敏捷".equals(全服发送装备装备敏捷.getText())) {
                敏捷 = 0;
            } else {
                敏捷 = Integer.parseInt(全服发送装备装备敏捷.getText());
            }
            int 智力;
            if ("智力".equals(全服发送装备装备智力.getText())) {
                智力 = 0;
            } else {
                智力 = Integer.parseInt(全服发送装备装备智力.getText());
            }
            int 运气;
            if ("运气".equals(全服发送装备装备运气.getText())) {
                运气 = 0;
            } else {
                运气 = Integer.parseInt(全服发送装备装备运气.getText());
            }
            int HP;
            if ("HP设置".equals(全服发送装备装备HP.getText())) {
                HP = 0;
            } else {
                HP = Integer.parseInt(全服发送装备装备HP.getText());
            }
            int MP;
            if ("MP设置".equals(全服发送装备装备MP.getText())) {
                MP = 0;
            } else {
                MP = Integer.parseInt(全服发送装备装备MP.getText());
            }
            int 可加卷次数;
            if ("加卷次数".equals(全服发送装备装备加卷.getText())) {
                可加卷次数 = 0;
            } else {
                可加卷次数 = Integer.parseInt(全服发送装备装备加卷.getText());
            }

            String 制作人名字;
            if ("制作人".equals(全服发送装备装备制作人.getText())) {
                制作人名字 = "";
            } else {
                制作人名字 = 全服发送装备装备制作人.getText();
            }
            int 给予时间;
            if ("给予物品时间".equals(全服发送装备装备给予时间.getText())) {
                给予时间 = 0;
            } else {
                给予时间 = Integer.parseInt(全服发送装备装备给予时间.getText());
            }
            String 是否可以交易 = 全服发送装备装备可否交易.getText();
            int 攻击力;
            if ("攻击力".equals(全服发送装备装备攻击力.getText())) {
                攻击力 = 0;
            } else {
                攻击力 = Integer.parseInt(全服发送装备装备攻击力.getText());
            }
            int 魔法力;
            if ("魔法力".equals(全服发送装备装备魔法力.getText())) {
                魔法力 = 0;
            } else {
                魔法力 = Integer.parseInt(全服发送装备装备魔法力.getText());
            }
            int 物理防御;
            if ("物理防御".equals(全服发送装备装备物理防御.getText())) {
                物理防御 = 0;
            } else {
                物理防御 = Integer.parseInt(全服发送装备装备物理防御.getText());
            }
            int 魔法防御;
            if ("魔法防御".equals(全服发送装备装备魔法防御.getText())) {
                魔法防御 = 0;
            } else {
                魔法防御 = Integer.parseInt(全服发送装备装备魔法防御.getText());
            }
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            MapleInventoryType type = GameConstants.getInventoryType(物品ID);
            for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (a == 1) {
                        if (1 >= 0) {
                            if (!MapleInventoryManipulator.checkSpace(mch.getClient(), 物品ID, 1, "")) {
                                return;
                            }
                            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)
                                    || type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100) {
                                final Equip item = (Equip) (ii.getEquipById(物品ID));
                                if (ii.isCash(物品ID)) {
                                    item.setUniqueId(1);
                                }
                                if (力量 > 0 && 力量 <= 32767) {
                                    item.setStr((short) (力量));
                                }
                                if (敏捷 > 0 && 敏捷 <= 32767) {
                                    item.setDex((short) (敏捷));
                                }
                                if (智力 > 0 && 智力 <= 32767) {
                                    item.setInt((short) (智力));
                                }
                                if (运气 > 0 && 运气 <= 32767) {
                                    item.setLuk((short) (运气));
                                }
                                if (攻击力 > 0 && 攻击力 <= 32767) {
                                    item.setWatk((short) (攻击力));
                                }
                                if (魔法力 > 0 && 魔法力 <= 32767) {
                                    item.setMatk((short) (魔法力));
                                }
                                if (物理防御 > 0 && 物理防御 <= 32767) {
                                    item.setWdef((short) (物理防御));
                                }
                                if (魔法防御 > 0 && 魔法防御 <= 32767) {
                                    item.setMdef((short) (魔法防御));
                                }
                                if (HP > 0 && HP <= 30000) {
                                    item.setHp((short) (HP));
                                }
                                if (MP > 0 && MP <= 30000) {
                                    item.setMp((short) (MP));
                                }
                                if ("可以交易".equals(是否可以交易)) {
                                    short flag = item.getFlag();
                                    if (item.getType() == MapleInventoryType.EQUIP.getType()) {
                                        flag |= ItemFlag.KARMA_EQ.getValue();
                                    } else {
                                        flag |= ItemFlag.KARMA_USE.getValue();
                                    }
                                    item.setFlag((byte) flag);
                                }
                                if (给予时间 > 0) {
                                    item.setExpiration(System.currentTimeMillis() + (给予时间 * 24 * 60 * 60 * 1000));
                                }
                                if (可加卷次数 > 0) {
                                    item.setUpgradeSlots((byte) (可加卷次数));
                                }
                                if (制作人名字 != null) {
                                    item.setOwner(制作人名字);
                                }
                                final String name = ii.getName(物品ID);
                                if (物品ID / 10000 == 114 && name != null && name.length() > 0) { //medal
                                    final String msg = "你已获得称号 <" + name + ">";
                                    mch.getClient().getPlayer().dropMessage(5, msg);
                                }
                                MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                            } else {
                           //     MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 1, "", null, 给予时间, "");
                                MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 1, "", null, (byte) 0);

                            }
                        } else {
                            MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -1, true, false);
                        }
                        mch.getClient().getSession().write(MaplePacketCreator.getShowItemGain(物品ID, (short) 1, true));
                    } else if (mch.getName().equals(发送装备玩家姓名.getText())) {
                        if (1 >= 0) {
                            if (!MapleInventoryManipulator.checkSpace(mch.getClient(), 物品ID, 1, "")) {
                                return;
                            }
                            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)
                                    || type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100) {
                                final Equip item = (Equip) (ii.getEquipById(物品ID));
                                if (ii.isCash(物品ID)) {
                                    item.setUniqueId(1);
                                }
                                if (力量 > 0 && 力量 <= 32767) {
                                    item.setStr((short) (力量));
                                }
                                if (敏捷 > 0 && 敏捷 <= 32767) {
                                    item.setDex((short) (敏捷));
                                }
                                if (智力 > 0 && 智力 <= 32767) {
                                    item.setInt((short) (智力));
                                }
                                if (运气 > 0 && 运气 <= 32767) {
                                    item.setLuk((short) (运气));
                                }
                                if (攻击力 > 0 && 攻击力 <= 32767) {
                                    item.setWatk((short) (攻击力));
                                }
                                if (魔法力 > 0 && 魔法力 <= 32767) {
                                    item.setMatk((short) (魔法力));
                                }
                                if (物理防御 > 0 && 物理防御 <= 32767) {
                                    item.setWdef((short) (物理防御));
                                }
                                if (魔法防御 > 0 && 魔法防御 <= 32767) {
                                    item.setMdef((short) (魔法防御));
                                }
                                if (HP > 0 && HP <= 30000) {
                                    item.setHp((short) (HP));
                                }
                                if (MP > 0 && MP <= 30000) {
                                    item.setMp((short) (MP));
                                }
                                if ("可以交易".equals(是否可以交易)) {
                                    short flag = item.getFlag();
                                    if (item.getType() == MapleInventoryType.EQUIP.getType()) {
                                        flag |= ItemFlag.KARMA_EQ.getValue();
                                    } else {
                                        flag |= ItemFlag.KARMA_USE.getValue();
                                    }
                                    item.setFlag((byte) flag);
                                }
                                if (给予时间 > 0) {
                                    item.setExpiration(System.currentTimeMillis() + (给予时间 * 24 * 60 * 60 * 1000));
                                }
                                if (可加卷次数 > 0) {
                                    item.setUpgradeSlots((byte) (可加卷次数));
                                }
                                if (制作人名字 != null) {
                                    item.setOwner(制作人名字);
                                }
                                final String name = ii.getName(物品ID);
                                if (物品ID / 10000 == 114 && name != null && name.length() > 0) { //medal
                                    final String msg = "你已获得称号 <" + name + ">";
                                    mch.getClient().getPlayer().dropMessage(5, msg);
                                }
                                MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                            } else {
                    //            MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 1, "", null, 给予时间, "");
                                MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 1, "", null, (byte) 0);
                            }
                        } else {
                            MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -1, true, false);
                        }
                        mch.getClient().getSession().write(MaplePacketCreator.getShowItemGain(物品ID, (short) 1, true));

                    }
                }
            }
            JOptionPane.showMessageDialog(null, "[信息]:发送成功。");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "[信息]:错误!" + e);
        }
    }
    
    private void 发送福利(int a) {
        boolean result1 = this.a1.getText().matches("[0-9]+");
        if (result1) {
            int 数量;
            if ("100000000".equals(a1.getText())) {
                数量 = 100;
            } else {
                数量 = Integer.parseInt(a1.getText());
            }
            if (数量 <= 0 || 数量 > 999999999) {
                return;
            }
            String 类型 = "";
            for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {

                    switch (a) {
                        case 1:
                            类型 = "点券";
                            mch.modifyCSPoints(1, 数量, true);
                            break;
                        case 2:
                            类型 = "抵用券";
                            mch.modifyCSPoints(2, 数量, true);
                            break;
                        case 3:
                            类型 = "金币";
                            mch.gainMeso(数量, true);
                            break;
                        case 4:
                            类型 = "经验";
                            mch.gainExp(数量, true, true, false);
                            break;
                        case 5:
                            类型 = "人气";
                            mch.addFame(数量);
                            break;
                        case 6:
                            类型 = "豆豆";
                            mch.gainBeans(数量);
                            break;
                        default:
                            break;
                    }
                    mch.startMapEffect("管理员发放 " + 数量 + " " + 类型 + "给在线的所有玩家！", 5121009);
                }
            }
            JOptionPane.showMessageDialog(null, "[信息]:发放 " + 数量 + " " + 类型 + "给在线的所有玩家。");
            a1.setText("");
            JOptionPane.showMessageDialog(null, "发送成功");
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入要发送数量。");
        }
    }
    
     public void 刷新反应堆() {
        for (int i = ((DefaultTableModel) (this.反应堆.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.反应堆.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM reactordrops ");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 反应堆.getModel()).insertRow(反应堆.getRowCount(), new Object[]{rs.getInt("reactordropid"), rs.getInt("reactorid"), rs.getInt("itemid"), rs.getInt("chance"), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))});
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        反应堆.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 反应堆.getSelectedRow();
                String a = 反应堆.getValueAt(i, 0).toString();
                String a1 = 反应堆.getValueAt(i, 1).toString();
                String a2 = 反应堆.getValueAt(i, 2).toString();
                String a3 = 反应堆.getValueAt(i, 3).toString();
                反应堆序列号.setText(a);
                反应堆代码.setText(a1);
                反应堆物品.setText(a2);
                反应堆概率.setText(a3);
            }
        });
    }
        //新增
     public void openWindow(final Windows w) {
         //System.err.println("AAA1");
        if (!windows.containsKey(w)) {
            switch (w) {
                    
                    case 一键还原:
                    windows.put(w, new 一键还原());
                    break;
                    case 代码查询工具:
                    windows.put(w, new 代码查询工具());
                    break;
                    case 活动控制台:
                        //System.err.println("AAA");
                        windows.put(w, new 活动控制台());
                    break;
                    case 游戏抽奖工具:
                        windows.put(w, new 游戏抽奖工具());
                    break;
                    case 删除自添加NPC工具:
                        windows.put(w, new 删除自添加NPC工具());
                    break;
                    
                default:
                    return;
            }
            windows.get(w).setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        }
        windows.get(w).setVisible(true);
    }
     
      public enum Windows {
        一键还原,
        代码查询工具,
        活动控制台,
        游戏抽奖工具,
        删除自添加NPC工具,
        ;
    }
      
       public void 刷新封MAC() {
        for (int i = ((DefaultTableModel) (this.封MAC.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.封MAC.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM macbans");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 封MAC.getModel()).insertRow(封MAC.getRowCount(), new Object[]{
                    rs.getInt("macbanid"),
                    rs.getString("mac"),
                    NPCConversationManager.MAC取账号(rs.getString("mac"))
                });
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void 刷新封IP() {
        for (int i = ((DefaultTableModel) (this.封IP.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.封IP.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM ipbans");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 封IP.getModel()).insertRow(封IP.getRowCount(), new Object[]{
                    rs.getInt("ipbanid"),
                    rs.getString("ip"),
                    NPCConversationManager.IP取账号(rs.getString("ip"))
                });
            }
        } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
      
       public void 删除SN库存() {
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        try {
            ps2 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM character7 WHERE Name = ?");
            ps2.setInt(1, Integer.parseInt(this.商品编码.getText()));
            rs2 = ps2.executeQuery();
            if (rs2.next()) {
                String sqlstr = " delete from character7 where Name =" + Integer.parseInt(this.商品编码.getText()) + ";";
                ps2.executeUpdate(sqlstr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void 删除SN库存2() {
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        try {
            ps2 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM character7 WHERE Name = ?  &&  channel = 2");
            ps2.setInt(1, Integer.parseInt(this.商品编码.getText()));
            rs2 = ps2.executeQuery();
            if (rs2.next()) {
                String sqlstr = " delete from character7 where Name =" + Integer.parseInt(this.商品编码.getText()) + ";";
                ps2.executeUpdate(sqlstr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void 删除SN库存3() {
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        try {
            ps2 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM character7 WHERE Name = ?  &&  channel = 3");
            ps2.setInt(1, Integer.parseInt(this.商品编码.getText()));
            rs2 = ps2.executeQuery();
            if (rs2.next()) {
                String sqlstr = " delete from character7 where Name =" + Integer.parseInt(this.商品编码.getText()) + ";";
                ps2.executeUpdate(sqlstr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void 删除SN库存4() {
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        try {
            ps2 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM character7 WHERE Name = ?  &&  channel = 4");
            ps2.setInt(1, Integer.parseInt(this.商品编码.getText()));
            rs2 = ps2.executeQuery();
            if (rs2.next()) {
                String sqlstr = " delete from character7 where Name =" + Integer.parseInt(this.商品编码.getText()) + ";";
                ps2.executeUpdate(sqlstr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Gaincharacter7(String Name, int Channale, int Piot) {
        try {
            int ret = Getcharacter7(Name, Channale);
            if (ret == -1) {
                ret = 0;
                PreparedStatement ps = null;
                try {
                    ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO character7 (channel, Name,Point) VALUES (?, ?, ?)");
                    ps.setInt(1, Channale);
                    ps.setString(2, Name);
                    ps.setInt(3, ret);
                    ps.execute();
                } catch (SQLException e) {
                    System.out.println("xxxxxxxx:" + e);
                } finally {
                    try {
                        if (ps != null) {
                            ps.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("xxxxxxxxzzzzzzz:" + e);
                    }
                }
            }
            ret += Piot;
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE character7 SET `Point` = ? WHERE Name = ? and channel = ?");
            ps.setInt(1, ret);
            ps.setString(2, Name);
            ps.setInt(3, Channale);
            ps.execute();
            ps.close();
        } catch (SQLException sql) {
            System.err.println("Getcharacter7!!55" + sql);
        }
    }

    public static int Getcharacter7(String Name, int Channale) {
        int ret = -1;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM character7 WHERE channel = ? and Name = ?");
            ps.setInt(1, Channale);
            ps.setString(2, Name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            ret = rs.getInt("Point");
            rs.close();
            ps.close();
        } catch (SQLException ex) {
        }
        return ret;
    }

    public static int Get商城物品() {
        int ret = -1;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM cashshop_modified_items WHERE serial = ?");
            int serial = 0;
            ps.setInt(1, serial);
            ResultSet rs = ps.executeQuery();
            rs.next();
            ret = rs.getInt("meso");
            rs.close();
            ps.close();
        } catch (SQLException ex) {
        }
        return ret;
    }

    public static void Gain商城物品(int Piot, int Piot1) {
        try {
            int ret = Get商城物品();
            if (ret == -1) {
                ret = 0;
                PreparedStatement ps = null;
                try {
                    ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO cashshop_modified_items (serial,meso) VALUES (?, ?)");
                    int serial = 0;
                    ps.setInt(1, serial);
                    ps.setInt(2, ret);
                    ps.execute();
                } catch (SQLException e) {
                    System.out.println("xxxxxxxx:" + e);
                } finally {
                    try {
                        if (ps != null) {
                            ps.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("xxxxxxxxzzzzzzz:" + e);
                    }
                }
            }
            ret += Piot;
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE cashshop_modified_items SET `meso` = ? WHERE serial = ?");
            ps.setInt(1, ret);
            int serial = 0;
            ps.setInt(2, serial);
            ps.execute();
            ps.close();
        } catch (SQLException sql) {
            System.err.println("獲取錯誤!!55" + sql);
        }
    }
    
     public void 刷新商城扩充价格() {
        for (int i = ((DefaultTableModel) (this.商城扩充价格.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.商城扩充价格.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM configvalues WHERE id = 999 ");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 商城扩充价格.getModel()).insertRow(商城扩充价格.getRowCount(), new Object[]{rs.getString("Val")});

            }
        } catch (SQLException ex) {
            Logger.getLogger(FengYeDuan.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }    
      
      public void 上架() {

        try {
            int SN_ = Integer.parseInt(String.valueOf(this.charTable.getValueAt(this.charTable.getSelectedRow(), 0)));
            //清楚table数据
            for (int i = ((DefaultTableModel) (this.charTable.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.charTable.getModel())).removeRow(i);
            }
            int OnSale_ = 1;
            CashItemInfo merchandise = new CashItemInfo(SN_, OnSale_);
            int success = update上下架(merchandise);
            if (success == 0) {
                JOptionPane.showMessageDialog(null, "[信息]:上架失败。");
            } else {
                initCharacterPannel();
                JOptionPane.showMessageDialog(null, "[信息]:上架成功。");
            }
        } catch (NumberFormatException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "[信息]:上架失败，请选中你要上架的道具。");
        }
    }

    public void 下架() {
        try {
            int SN_ = Integer.parseInt(String.valueOf(this.charTable.getValueAt(this.charTable.getSelectedRow(), 0)));
            //清楚table数据
            for (int i = ((DefaultTableModel) (this.charTable.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.charTable.getModel())).removeRow(i);
            }
            int OnSale_ = 0;
            CashItemInfo merchandise = new CashItemInfo(SN_, OnSale_);
            int success = update上下架(merchandise);
            if (success == 0) {
                JOptionPane.showMessageDialog(null, "[信息]:下架失败。");
            } else {
                initCharacterPannel();
                JOptionPane.showMessageDialog(null, "[信息]:下架成功。");
            }
        } catch (NumberFormatException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "[信息]:下架失败，请选中你要上架的道具。");
        }
    }

    public static int update上下架(CashItemInfo merchandise) {//修改
        PreparedStatement ps = null;
        int resulet = 0;
        Connection conn = DatabaseConnection.getConnection();
        int i = 0;
        try {
            ps = conn.prepareStatement("update cashshop_modified_items set showup = ? where serial = ?");//itemid
            ps.setInt(++i, merchandise.getOnSale());
            ps.setInt(++i, merchandise.getSN());
            resulet = ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(Start.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return resulet;
    }

    public void 读取商品(final int a, int b, int c, int d) {
        for (int i = ((DefaultTableModel) (this.charTable.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.charTable.getModel())).removeRow(i);
        }
        商品编码.setText("" + a + "");
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            PreparedStatement pse;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM cashshop_modified_items WHERE serial >= " + a + " && serial < " + b + "");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 上架状态 = "";
                if (rs.getInt("showup") == 0) {
                    上架状态 = "已经下架↓";
                } else {
                    上架状态 = "已经上架↑";
                }
                String 出售状态2 = "";
                switch (rs.getInt("mark")) {
                    case -1:
                        出售状态2 = "无";
                        break;
                    case 0:
                        出售状态2 = "NEW";
                        break;
                    case 1:
                        出售状态2 = "Sale";
                        break;
                    case 2:
                        出售状态2 = "HOT";
                        break;
                    case 3:
                        出售状态2 = "Event";
                        break;
                    default:
                        break;
                }
                String 类型 = "";
                if ("".equals(NPCConversationManager.SN取类型(rs.getInt("serial")))) {
                    类型 = "点券";
                } else {
                    类型 = "点/抵用券";
                }
//                System.out.println( MapleItemInformationProvider.getInstance().getName(1302000));
                ((DefaultTableModel) charTable.getModel()).insertRow(charTable.getRowCount(), new Object[]{
                    rs.getInt("serial"),
                    rs.getInt("itemid"),
                    //itemName,
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("count"),
                    rs.getInt("discount_price"),
                    rs.getInt("period") + "," + rs.getInt("period_hour"),
                    出售状态2,
                    上架状态,
                    NPCConversationManager.SN取出售(rs.getInt("serial")),
                    NPCConversationManager.SN取库存(rs.getInt("serial")),
                    NPCConversationManager.SN取折扣(rs.getInt("serial")),
                    rs.getInt("day_limit") < 0 ? "不限购":rs.getInt("day_limit"),
                    rs.getInt("use_nx") == 1 ? "仅点券" : "点券/抵用卷"
                });

            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT `serial` FROM cashshop_modified_items WHERE serial >= " + a + " && serial <" + b + " ORDER BY `serial` DESC LIMIT 1");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String SN = rs.getString("serial");
                    int sns = Integer.parseInt(SN);
                    sns++;
                    商品编码.setText("" + sns);
                    ps.close();
                }
            }
            ps.close();
        } catch (SQLException ex) {
            System.err.println("出错读取商品：" + ex.getMessage());
        }
        if (c == 1 && d == 1) {
            显示类型.setText("热销产品");
            JOptionPane.showMessageDialog(null, "[信息]:显示热销产品，双击后可在热销产品下添加商品。");
        } else if (c == 1 && d == 2) {
            显示类型.setText("主题馆");
            JOptionPane.showMessageDialog(null, "[信息]:显示主题馆，双击后可在主题馆下添加商品。");
        } else if (c == 1 && d == 3) {
            显示类型.setText("活动");
            JOptionPane.showMessageDialog(null, "[信息]:显示活动，双击后可在活动下添加商品。");
        } else if (c == 2 && d == 1) {
            显示类型.setText("帽子");
            JOptionPane.showMessageDialog(null, "[信息]:显示帽子，双击后可在帽子下添加商品。");
        } else if (c == 2 && d == 2) {
            显示类型.setText("裙裤");
            JOptionPane.showMessageDialog(null, "[信息]:显示裙裤，双击后可在裙裤下添加商品。");
        } else if (c == 2 && d == 3) {
            显示类型.setText("披风");
            JOptionPane.showMessageDialog(null, "[信息]:显示披风，双击后可在披风下添加商品。");
        } else if (c == 2 && d == 4) {
            显示类型.setText("飞镖");
            JOptionPane.showMessageDialog(null, "[信息]:显示飞镖，双击后可在飞镖下添加商品。");
        } else if (c == 2 && d == 5) {
            显示类型.setText("长袍");
            JOptionPane.showMessageDialog(null, "[信息]:显示长袍，双击后可在长袍下添加商品。");
        } else if (c == 2 && d == 6) {
            显示类型.setText("脸饰");
            JOptionPane.showMessageDialog(null, "[信息]:显示脸饰，双击后可在脸饰下添加商品。");
        } else if (c == 2 && d == 7) {
            显示类型.setText("鞋子");
            JOptionPane.showMessageDialog(null, "[信息]:显示鞋子，双击后可在鞋子下添加商品。");
        } else if (c == 2 && d == 8) {
            显示类型.setText("骑宠");
            JOptionPane.showMessageDialog(null, "[信息]:显示骑宠，双击后可在骑宠下添加商品。");
        } else if (c == 2 && d == 9) {
            显示类型.setText("戒指");
            JOptionPane.showMessageDialog(null, "[信息]:显示戒指，双击后可在戒指下添加商品。");
        } else if (c == 2 && d == 10) {
            显示类型.setText("眼饰");
            JOptionPane.showMessageDialog(null, "[信息]:显示眼饰，双击后可在眼饰下添加商品。");
        } else if (c == 2 && d == 11) {
            显示类型.setText("手套");
            JOptionPane.showMessageDialog(null, "[信息]:显示手套，双击后可在手套下添加商品。");
        } else if (c == 2 && d == 12) {
            显示类型.setText("武器");
            JOptionPane.showMessageDialog(null, "[信息]:显示武器，双击后可在武器下添加商品。");
        } else if (c == 2 && d == 13) {
            显示类型.setText("上衣");
            JOptionPane.showMessageDialog(null, "[信息]:显示上衣，双击后可在上衣下添加商品。");
        } else if (c == 3 && d == 1) {
            显示类型.setText("喜庆物品");
            JOptionPane.showMessageDialog(null, "[信息]:显示喜庆物品，双击后可在喜庆物品下添加商品。");
        } else if (c == 3 && d == 2) {
            显示类型.setText("通讯物品");
            JOptionPane.showMessageDialog(null, "[信息]:显示通讯物品，双击后可在通讯物品下添加商品。");
        } else if (c == 3 && d == 3) {
            显示类型.setText("卷轴");
            JOptionPane.showMessageDialog(null, "[信息]:显示卷轴，双击后可在卷轴下添加商品。");
        } else if (c == 4 && d == 1) {
            显示类型.setText("会员卡");
            JOptionPane.showMessageDialog(null, "[信息]:显示会员卡，双击后可在会员卡下添加商品。");
        } else if (c == 4 && d == 2) {
            显示类型.setText("表情");
            JOptionPane.showMessageDialog(null, "[信息]:显示表情，双击后可在表情下添加商品。");
        } else if (c == 4 && d == 3) {
            显示类型.setText("个人商店");
            JOptionPane.showMessageDialog(null, "[信息]:显示个人商店，双击后可在个人商店下添加商品。");
        } else if (c == 4 && d == 4) {
            显示类型.setText("效果");
            JOptionPane.showMessageDialog(null, "[信息]:显示效果，双击后可在效果下添加商品。");
        } else if (c == 4 && d == 5) {
            显示类型.setText("游戏");
            JOptionPane.showMessageDialog(null, "[信息]:显示游戏，双击后可在游戏下添加商品。");
        } else if (c == 4 && d == 6) {
            显示类型.setText("纪念日");
            JOptionPane.showMessageDialog(null, "[信息]:显示纪念日，双击后可在纪念日下添加商品。");
        } else if (c == 5 && d == 1) {
            显示类型.setText("宠物");
            JOptionPane.showMessageDialog(null, "[信息]:显示宠物，双击后可在宠物下添加商品。");
        } else if (c == 5 && d == 2) {
            显示类型.setText("宠物服饰");
            JOptionPane.showMessageDialog(null, "[信息]:显示宠物服饰，双击后可在宠物服饰下添加商品。");
        } else if (c == 5 && d == 3) {
            显示类型.setText("其他");
            JOptionPane.showMessageDialog(null, "[信息]:显示其他，双击后可在其他下添加商品。");
        } else {
            显示类型.setText("XXXX");
            JOptionPane.showMessageDialog(null, "[信息]:XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX。");
        }
    }

    public void 刷新() {
        if ("热销产品".equals(显示类型.getText())) {
            读取商品(10000000, 10100000, 1, 1);
        } else if ("主题馆".equals(显示类型.getText())) {
            读取商品(10100000, 10200000, 1, 2);
        } else if ("活动".equals(显示类型.getText())) {
            读取商品(10200000, 10300000, 1, 3);
        } else if ("帽子".equals(显示类型.getText())) {
            读取商品(20000000, 20100000, 2, 1);
        } else if ("裙裤".equals(显示类型.getText())) {
            读取商品(20500000, 20600000, 2, 2);
        } else if ("披风".equals(显示类型.getText())) {
            读取商品(21100000, 21200000, 2, 3);
        } else if ("飞镖".equals(显示类型.getText())) {
            读取商品(21000000, 21100000, 2, 4);
        } else if ("长袍".equals(显示类型.getText())) {
            读取商品(20300000, 20400000, 2, 5);
        } else if ("脸饰".equals(显示类型.getText())) {
            读取商品(20100000, 20200000, 2, 6);
        } else if ("鞋子".equals(显示类型.getText())) {
            读取商品(20600000, 20700000, 2, 7);
        } else if ("骑宠".equals(显示类型.getText())) {
            读取商品(21200000, 21300000, 2, 8);
        } else if ("戒指".equals(显示类型.getText())) {
            读取商品(20900000, 21000000, 2, 9);
        } else if ("眼饰".equals(显示类型.getText())) {
            读取商品(20200000, 20300000, 2, 10);
        } else if ("手套".equals(显示类型.getText())) {
            读取商品(20700000, 20800000, 2, 11);
        } else if ("武器".equals(显示类型.getText())) {
            读取商品(20800000, 20900000, 2, 12);
        } else if ("上衣".equals(显示类型.getText())) {
            读取商品(20400000, 20500000, 2, 13);
        } else if ("喜庆物品".equals(显示类型.getText())) {
            读取商品(30000000, 30100000, 3, 1);
        } else if ("通讯物品".equals(显示类型.getText())) {
            读取商品(30100000, 30200000, 3, 2);
        } else if ("卷轴".equals(显示类型.getText())) {
            读取商品(30200000, 30300000, 3, 3);
        } else if ("会员卡".equals(显示类型.getText())) {
            读取商品(50000000, 50100000, 4, 1);
        } else if ("表情".equals(显示类型.getText())) {
            读取商品(50100000, 50200000, 4, 2);
        } else if ("个人商店".equals(显示类型.getText())) {
            读取商品(50200000, 50300000, 4, 3);
        } else if ("效果".equals(显示类型.getText())) {
            读取商品(50500000, 50600000, 4, 4);
        } else if ("纪念日".equals(显示类型.getText())) {
            读取商品(50300000, 50400000, 4, 6);
        } else if ("游戏".equals(显示类型.getText())) {
            读取商品(50400000, 50500000, 4, 5);
        } else if ("宠物".equals(显示类型.getText())) {
            读取商品(60000000, 60100000, 5, 1);
        } else if ("宠物服饰".equals(显示类型.getText())) {
            读取商品(60100000, 60200000, 5, 2);
        } else if ("其他".equals(显示类型.getText())) {
            读取商品(60200000, 60300000, 5, 3);
        } else if ("".equals(显示类型.getText())) {
            initCharacterPannel();
        }
    }

    public void initCharacterPannel() {
        long start = System.currentTimeMillis();
        for (int i = ((DefaultTableModel) (this.charTable.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.charTable.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            PreparedStatement pse;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM cashshop_modified_items ");//WHERE serial > 10000000 && serial < 10100000 商城数据库表

            rs = ps.executeQuery();
            while (rs.next()) {
                String itemName = "";
                itemName = MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"));
                String 上架状态 = "";
                if (rs.getInt("showup") == 0) {
                    上架状态 = "已经下架↓";
                } else {
                    上架状态 = "已经上架↑";
                }
                String 出售状态2 = "";
                switch (rs.getInt("mark")) {
                    case -1:
                        出售状态2 = "无";
                        break;
                    case 0:
                        出售状态2 = "NEW";
                        break;
                    case 1:
                        出售状态2 = "Sale";
                        break;
                    case 2:
                        出售状态2 = "HOT";
                        break;
                    case 3:
                        出售状态2 = "Event";
                        break;
                    default:
                        break;
                }
                ((DefaultTableModel) charTable.getModel()).insertRow(charTable.getRowCount(), new Object[]{
                    rs.getInt("serial"),
                    rs.getInt("itemid"),
                    "非详细分类不显示名称",
                    //itemName,
                    rs.getInt("count"),
                    rs.getInt("discount_price"),
                    rs.getInt("period") + "," + rs.getInt("period_hour"),
                    出售状态2,
                    上架状态,
                    NPCConversationManager.SN取出售(rs.getInt("serial")),
                    NPCConversationManager.SN取库存(rs.getInt("serial")),
                    NPCConversationManager.SN取折扣(rs.getInt("serial")),
                    rs.getInt("day_limit") < 0 ? "不限购":rs.getInt("day_limit")
                });
            }
            rs.close();
            ps.close();
            long now = System.currentTimeMillis() - start;

        } catch (SQLException ex) {
            Logger.getLogger(Start.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        charTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = charTable.getSelectedRow();
                String a1 = charTable.getValueAt(i, 0).toString();
                String a2 = charTable.getValueAt(i, 1).toString();
                String a3 = charTable.getValueAt(i, 3).toString();
                String a4 = charTable.getValueAt(i, 4).toString();
                String a5 = charTable.getValueAt(i, 5).toString();
                String a6 = charTable.getValueAt(i, 6).toString();
                String a7 = charTable.getValueAt(i, 7).toString();
                String a8 = charTable.getValueAt(i, 8).toString();
                String a9 = charTable.getValueAt(i, 9).toString();
                String a10 = charTable.getValueAt(i, 10).toString();
                String a11 = charTable.getValueAt(i, 11).toString();

                商品编码.setText(a1);
                商品代码.setText(a2);
                商品数量.setText(a3);
                商品价格.setText(a4);
                商品时间.setText(a5);
                商品库存.setText(a9);
                商品折扣.setText(a10);
                每日限购.setText(a11);

                if (null != charTable.getValueAt(i, 6).toString()) {
                    switch (charTable.getValueAt(i, 6).toString()) {
                        case "无":
                            商品出售状态.setText("-1");
                            break;
                        case "NEW":
                            商品出售状态.setText("0");
                            break;
                        case "Sale":
                            商品出售状态.setText("1");
                            break;
                        case "HOT":
                            商品出售状态.setText("2");
                            break;
                        case "Event":
                            商品出售状态.setText("3");
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }
     
     public void 查询商店(int lx) {

        boolean result = this.查询商店.getText().matches("[0-9]+");
        if (lx == 0) {
            result = true;
        }
        if (result) {
            if (lx != 0) {
                if (Integer.parseInt(this.查询商店.getText()) < 0) {
                    JOptionPane.showMessageDialog(null, "[信息]:请填写正确的值。");
                    return;
                }
            }
            for (int i = ((DefaultTableModel) (this.游戏商店2.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.游戏商店2.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;
                ResultSet rs = null;
                if (lx == 0) {
                    ps = con.prepareStatement("SELECT * FROM shopitems");
                } else {
                    ps = con.prepareStatement("SELECT * FROM shopitems WHERE shopid = " + Integer.parseInt(this.查询商店.getText()) + " ");
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    ((DefaultTableModel) 游戏商店2.getModel()).insertRow(游戏商店2.getRowCount(), new Object[]{
                        rs.getInt("shopitemid"),
                        rs.getInt("shopid"),
                        rs.getInt("itemid"),
                        rs.getInt("price"),
                        MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                    });
                }
                rs.close();
                ps.close();
                JOptionPane.showMessageDialog(null, "[信息]:商城物品查询成功。");
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            游戏商店2.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int i = 游戏商店2.getSelectedRow();
                    String a = 游戏商店2.getValueAt(i, 0).toString();
                    String a1 = 游戏商店2.getValueAt(i, 1).toString();
                    String a2 = 游戏商店2.getValueAt(i, 2).toString();
                    String a3 = 游戏商店2.getValueAt(i, 3).toString();
                    //String a4 = 游戏商店2.getValueAt(i, 4).toString();
                    商品序号.setText(a);
                    商店代码.setText(a1);
                    商品物品代码.setText(a2);
                    商品售价金币.setText(a3);
                    // 商品名称.setText(a4);
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入你需要查询的商店ID。");
        }
    }
    
     public static int 在线玩家() {
        int p = 0;
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr != null) {
                    p++;
                }
            }
        }
        return p;
    }
    
    public void 读取显示账号() {
        账号信息.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 账号信息.getSelectedRow();
                String a = 账号信息.getValueAt(i, 0).toString();
                String account = 账号信息.getValueAt(i, 1).toString();
                String ACash = 账号信息.getValueAt(i, 5).toString();
                String moneyb = 账号信息.getValueAt(i, 6).toString();
                String DY = 账号信息.getValueAt(i, 7).toString();
                //if (账号信息.getValueAt(i, 4).toString() != null) {
                String a4 = 账号信息.getValueAt(i, 4).toString();
                QQ.setText(a4);
                //}
                String GM = 账号信息.getValueAt(i, 11).toString();
                账号ID.setText(a);
                //账号操作.setText(account);
                账号.setText(account);

                点券.setText(ACash);
                jTextFieldMoneyB.setText(moneyb);
                抵用.setText(DY);
                管理1.setText(GM);
                账号提示语言.setText("[信息]:显示账号 " + 账号.getText() + " 下角色信息。");
                刷新角色信息2();
            }
        });
    }
    
    public static int 在线账号() {
        int data = 0;
        int p = 0;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT loggedin as DATA FROM accounts WHERE loggedin > 0");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data = rs.getInt("DATA");
                    p += 1;
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("在线账号、出错");
        }
        return p;
    }
    
      private void ChangePassWord() {
        String account = 注册的账号.getText();
        String password = 注册的密码.getText();

        if (password.length() > 12) {
            账号提示语言.setText("[信息]:修改密码失败，密码过长。");
            return;
        }
        if (!AutoRegister.getAccountExists(account)) {
            账号提示语言.setText("[信息]:修改密码失败，账号不存在。");
            return;
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps;
            ps = con.prepareStatement("Update accounts set password = ? Where name = ?");
            ps.setString(1, LoginCrypto.hexSha1(password));
            ps.setString(2, account);
            ps.execute();
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
        账号提示语言.setText("[信息]:修改密码成功。账号: " + account + " 密码: " + password + "");
    }
    public void 注册新账号() {
        boolean result1 = this.注册的账号.getText().matches("[0-9]+");
        boolean result2 = this.注册的密码.getText().matches("[0-9]+");
        if (注册的账号.getText().equals("") || 注册的密码.getText().equals("")) {
            账号提示语言.setText("[信息]:请填写注册的账号密码");
            return;
        } else {
            Connection con;
            String account = 注册的账号.getText();
            String password = 注册的密码.getText();

            if (password.length() > 10) {
                账号提示语言.setText("[信息]:注册失败，密码过长");
                return;
            }
            if (AutoRegister.getAccountExists(account)) {
                账号提示语言.setText("[信息]:注册失败，账号已存在");
                return;
            }
            try {
                con = DatabaseConnection.getConnection();
            } catch (Exception ex) {
                System.out.println(ex);
                return;
            }
            try {
                PreparedStatement ps = con.prepareStatement("INSERT INTO accounts (name, password) VALUES (?,?)");
                ps.setString(1, account);
                ps.setString(2, LoginCrypto.hexSha1(password));
                ps.executeUpdate();
                ps.close();
                刷新账号信息();
                账号提示语言.setText("[信息]:注册成功。账号: " + account + " 密码: " + password + "");
            } catch (SQLException ex) {
                System.out.println(ex);
                return;
            }
        }
    }

    private void 刷新账号信息() {
        for (int i = ((DefaultTableModel) (this.账号信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.账号信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM accounts order by id desc");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 封号 = "";
                if (rs.getInt("banned") == 0) {
                    封号 = "正常";
                } else {
                    封号 = "封禁";
                }
                String 在线 = "";
                if (rs.getInt("loggedin") == 0) {
                    // Font fnA = new Font("细明本",Font.PLAIN,12);
                    在线 = "不在线";
                } else {
                    在线 = "在线";
                }
                String QQ = "";
                if (rs.getString("qq") != null) {
                    QQ = rs.getString("qq");
                } else {
                    QQ = "未绑定QQ";
                }
                ((DefaultTableModel) 账号信息.getModel()).insertRow(账号信息.getRowCount(), new Object[]{
                    rs.getInt("id"), //账号ID
                    rs.getString("name"), //账号
                    rs.getString("SessionIP"), //账号IP地址
                    rs.getString("macs"), ///账号MAC地址
                    QQ,
                    rs.getInt("ACash"),//点券
                    rs.getInt("moneyb"),//元宝
                    rs.getInt("mPoints"),//抵用
                    rs.getString("lastlogin"),//最近上线
                    //rs.getInt("loggedin"),//在线
                    //rs.getInt("banned")//封号
                    在线,
                    封号,
                    rs.getInt("gm")
                });
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        读取显示账号();

    }

    private void 查找QQ() {

        for (int i = ((DefaultTableModel) (this.账号信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.账号信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM accounts WHERE qq =  '" + 账号操作.getText() + " ' ");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 封号 = "";
                if (rs.getInt("banned") == 0) {
                    封号 = "正常";
                } else {
                    封号 = "封禁";
                }
                String 在线 = "";
                if (rs.getInt("loggedin") == 0) {
                    // Font fnA = new Font("细明本",Font.PLAIN,12);
                    在线 = "不在线";
                } else {
                    在线 = "在线";
                }
                String QQ = "";
                if (rs.getString("qq") != null) {
                    QQ = rs.getString("qq");
                } else {
                    QQ = "未绑定QQ";
                }
                ((DefaultTableModel) 账号信息.getModel()).insertRow(账号信息.getRowCount(), new Object[]{
                    rs.getInt("id"), //账号ID
                    rs.getString("name"), //账号
                    rs.getString("SessionIP"), //账号IP地址
                    rs.getString("macs"), ///账号MAC地址
                    QQ,//注册时间
                    rs.getInt("ACash"),//点券
                    rs.getInt("mPoints"),//抵用
                    rs.getString("lastlogin"),//最近上线
                    在线,
                    封号,
                    rs.getInt("gm")
                });
            }
            账号提示语言.setText("[信息]:查找账号 " + this.账号操作.getText() + " 成功。");
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        账号信息.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 账号信息.getSelectedRow();
                String a = 账号信息.getValueAt(i, 0).toString();
                String a1 = 账号信息.getValueAt(i, 1).toString();
                String a2 = 账号信息.getValueAt(i, 5).toString();
                String a3 = 账号信息.getValueAt(i, 6).toString();
                账号ID.setText(a);
                账号操作.setText(a1);
                账号.setText(a1);
                点券.setText(a2);
                抵用.setText(a3);
                刷新角色信息2();
            }
        });
    }

    private void 查找账号() {

        for (int i = ((DefaultTableModel) (this.账号信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.账号信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM accounts WHERE name like  '%" + 账号操作.getText() + "%'");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 封号 = "";
                if (rs.getInt("banned") == 0) {
                    封号 = "正常";
                } else {
                    封号 = "封禁";
                }
                String 在线 = "";
                if (rs.getInt("loggedin") == 0) {
                    // Font fnA = new Font("细明本",Font.PLAIN,12);
                    在线 = "不在线";
                } else {
                    在线 = "在线";
                }
                String QQ = "";
                if (rs.getString("qq") != null) {
                    QQ = rs.getString("qq");
                } else {
                    QQ = "未绑定QQ";
                }
                ((DefaultTableModel) 账号信息.getModel()).insertRow(账号信息.getRowCount(), new Object[]{
                    rs.getInt("id"), //账号ID
                    rs.getString("name"), //账号
                    rs.getString("SessionIP"), //账号IP地址
                    rs.getString("macs"), ///账号MAC地址
                    QQ,//注册时间
                    rs.getInt("ACash"),//点券
                    rs.getInt("moneyb"),
                    rs.getInt("mPoints"),//抵用
                    rs.getString("lastlogin"),//最近上线
                    在线,
                    封号,
                    rs.getInt("gm")
                });
            }
            rs.close();
            ps.close();
            账号提示语言.setText("[信息]:查找账号 " + this.账号操作.getText() + " 成功。");
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        账号信息.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 账号信息.getSelectedRow();
                String a = 账号信息.getValueAt(i, 0).toString();
                String a1 = 账号信息.getValueAt(i, 1).toString();
                String a2 = 账号信息.getValueAt(i, 5).toString();
                String a3 = 账号信息.getValueAt(i, 6).toString();
                账号ID.setText(a);
                账号操作.setText(a1);
                账号.setText(a1);
                点券.setText(a2);
                抵用.setText(a3);
                刷新角色信息2();
            }
        });
    }

    private void 刷新技能信息() {
        boolean result1 = this.角色ID.getText().matches("[0-9]+");
        if (result1) {
            for (int i = ((DefaultTableModel) (this.技能信息.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.技能信息.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;

                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM skills  WHERE characterid =" + this.角色ID.getText() + "");
                rs = ps.executeQuery();
                while (rs.next()) {
                    MapleDataProvider data = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("net.sf.odinms.wzpath") + "/String.wz"));
                    MapleData itemsData;
                    int itemId;
                    String itemName = "";
                    itemsData = data.getData("Skill.img");
                    for (MapleData itemFolder : itemsData.getChildren()) {
                        itemId = Integer.parseInt(itemFolder.getName());
                        if (rs.getInt("skillid") == itemId) {
                            itemName = MapleDataTool.getString("name", itemFolder, "NO-NAME");
                        }
                    }
                    ((DefaultTableModel) 技能信息.getModel()).insertRow(技能信息.getRowCount(), new Object[]{
                        rs.getInt("id"),
                        itemName,
                        rs.getInt("skillid"),
                        rs.getInt("skilllevel"),
                        rs.getInt("masterlevel")
                    });
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            技能信息.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int i = 技能信息.getSelectedRow();
                    String a = 技能信息.getValueAt(i, 0).toString();
                    // String a1 = 技能信息.getValueAt(i, 1).toString();
                    String a2 = 技能信息.getValueAt(i, 2).toString();
                    String a3 = 技能信息.getValueAt(i, 3).toString();
                    String a4 = 技能信息.getValueAt(i, 4).toString();
                    技能序号.setText(a);
                    // 技能名字.setText(a1);
                    技能代码.setText(a2);
                    技能目前等级.setText(a3);
                    技能最高等级.setText(a4);
                    //出售状态.setText(a8);
                    //jTextField9.setText(a9);
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请先点击你想查看的角色。");
        }
    }

    private void 刷新角色信息() {
        String 输出 = "";
        for (int i = ((DefaultTableModel) (this.角色信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM characters order by accountid desc");
            rs = ps.executeQuery();

            while (rs.next()) {
                String 在线 = "";
                if (World.Find.findChannel(rs.getString("name")) > 0) {
                    在线 = "在线";
                } else {
                    在线 = "离线";
                }
                ((DefaultTableModel) 角色信息.getModel()).insertRow(角色信息.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    rs.getInt("accountid"),
                    rs.getString("name"),
                    getJobNameById(rs.getInt("job")),
                    rs.getInt("level"),
                    rs.getInt("str"),
                    rs.getInt("dex"),
                    rs.getInt("int"),
                    rs.getInt("luk"),
                    rs.getInt("maxhp"),
                    rs.getInt("maxmp"),
                    rs.getInt("meso"),
                    rs.getInt("map"),
                    在线,
                    rs.getInt("gm"),
                    rs.getInt("hair"),
                    rs.getInt("face")
                });

            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色信息.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色信息.getSelectedRow();
                String a = 角色信息.getValueAt(i, 0).toString();
                String a1 = 角色信息.getValueAt(i, 2).toString();
                String a2 = 角色信息.getValueAt(i, 4).toString();
                String a3 = 角色信息.getValueAt(i, 5).toString();
                String a4 = 角色信息.getValueAt(i, 6).toString();
                String a5 = 角色信息.getValueAt(i, 7).toString();
                String a6 = 角色信息.getValueAt(i, 8).toString();
                String a7 = 角色信息.getValueAt(i, 9).toString();
                String a8 = 角色信息.getValueAt(i, 10).toString();
                String a9 = 角色信息.getValueAt(i, 11).toString();
                String a10 = 角色信息.getValueAt(i, 12).toString();
                String a11 = 角色信息.getValueAt(i, 14).toString();
                String a12 = 角色信息.getValueAt(i, 15).toString();
                String a13 = 角色信息.getValueAt(i, 16).toString();
                角色ID.setText(a);
                角色昵称.setText(a1);
                等级.setText(a2);
                力量.setText(a3);
                敏捷.setText(a4);
                智力.setText(a5);
                运气.setText(a6);
                HP.setText(a7);
                MP.setText(a8);
                金币1.setText(a9);
                地图.setText(a10);
                GM.setText(a11);
                发型.setText(a12);
                脸型.setText(a13);
                //  个人发送物品玩家名字.setText(a1);
                //  发送装备玩家姓名.setText(a1);
            }
        });
    }

    private void 刷新角色背包穿戴() {
        for (int i = ((DefaultTableModel) (this.角色背包穿戴.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色背包穿戴.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid =" + this.角色ID.getText() + " && inventorytype = -1");
            rs = ps.executeQuery();
            while (rs.next()) {

                ((DefaultTableModel) 角色背包穿戴.getModel()).insertRow(角色背包穿戴.getRowCount(), new Object[]{
                    rs.getInt("inventoryitemid"),
                    rs.getInt("itemid"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("quantity")});
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色背包穿戴.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色背包穿戴.getSelectedRow();
                String a = 角色背包穿戴.getValueAt(i, 0).toString();
                String a1 = 角色背包穿戴.getValueAt(i, 1).toString();
                String a2 = 角色背包穿戴.getValueAt(i, 2).toString();
                身上穿戴序号1.setText(a);
                背包物品代码1.setText(a1);
                背包物品名字1.setText(a2);
            }
        });
    }

    private void 刷新角色装备背包() {
        for (int i = ((DefaultTableModel) (this.角色装备背包.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色装备背包.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid =" + this.角色ID.getText() + " && inventorytype = 1");
            rs = ps.executeQuery();
            while (rs.next()) {

                ((DefaultTableModel) 角色装备背包.getModel()).insertRow(角色装备背包.getRowCount(), new Object[]{
                    rs.getInt("inventoryitemid"),
                    rs.getInt("itemid"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("quantity")});
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色装备背包.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色装备背包.getSelectedRow();
                String a = 角色装备背包.getValueAt(i, 0).toString();
                String a1 = 角色装备背包.getValueAt(i, 1).toString();
                String a2 = 角色装备背包.getValueAt(i, 2).toString();
                装备背包物品序号.setText(a);
                装备背包物品代码.setText(a1);
                装备背包物品名字.setText(a2);
            }
        });
    }

    private void 刷新角色消耗背包() {
        for (int i = ((DefaultTableModel) (this.角色消耗背包.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色消耗背包.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid =" + this.角色ID.getText() + " && inventorytype = 2");
            rs = ps.executeQuery();
            while (rs.next()) {

                ((DefaultTableModel) 角色消耗背包.getModel()).insertRow(角色消耗背包.getRowCount(), new Object[]{
                    rs.getInt("inventoryitemid"),
                    rs.getInt("itemid"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("quantity")});
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色消耗背包.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色消耗背包.getSelectedRow();
                String a = 角色消耗背包.getValueAt(i, 0).toString();
                String a1 = 角色消耗背包.getValueAt(i, 1).toString();
                //String a2 = 角色消耗背包.getValueAt(i, 2).toString();
                消耗背包物品序号.setText(a);
                消耗背包物品代码.setText(a1);
                //消耗背包物品名字.setText(a2);
            }
        });
    }

    private void 刷新角色特殊背包() {
        for (int i = ((DefaultTableModel) (this.角色特殊背包.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色特殊背包.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid =" + this.角色ID.getText() + " && inventorytype = 5");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 角色特殊背包.getModel()).insertRow(角色特殊背包.getRowCount(), new Object[]{
                    rs.getInt("inventoryitemid"),
                    rs.getInt("itemid"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("quantity")});
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色特殊背包.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色特殊背包.getSelectedRow();
                String a = 角色特殊背包.getValueAt(i, 0).toString();
                String a1 = 角色特殊背包.getValueAt(i, 1).toString();
                //String a2 = 角色特殊背包.getValueAt(i, 2).toString();
                特殊背包物品序号.setText(a);
                特殊背包物品代码.setText(a1);
                //特殊背包物品名字.setText(a2);
            }
        });
    }

    private void 刷新角色游戏仓库() {
        for (int i = ((DefaultTableModel) (this.角色游戏仓库.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色游戏仓库.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE accountid =" + this.账号ID.getText());
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 角色游戏仓库.getModel()).insertRow(角色游戏仓库.getRowCount(), new Object[]{
                    rs.getInt("inventoryitemid"),
                    rs.getInt("itemid"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("quantity")});
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色游戏仓库.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色游戏仓库.getSelectedRow();
                String a = 角色游戏仓库.getValueAt(i, 0).toString();
                String a1 = 角色游戏仓库.getValueAt(i, 1).toString();
                //String a2 = 角色游戏仓库.getValueAt(i, 2).toString();
                游戏仓库物品序号.setText(a);
                游戏仓库物品代码.setText(a1);
                //游戏仓库物品名字.setText(a2);
            }
        });
    }

    private void 刷新角色商城仓库() {
        for (int i = ((DefaultTableModel) (this.角色商城仓库.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色商城仓库.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM csitems WHERE accountid =" + this.账号ID.getText());
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 角色商城仓库.getModel()).insertRow(角色商城仓库.getRowCount(), new Object[]{
                    rs.getInt("inventoryitemid"),
                    rs.getInt("itemid"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("quantity")});
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色商城仓库.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色商城仓库.getSelectedRow();
                String a = 角色商城仓库.getValueAt(i, 0).toString();
                String a1 = 角色商城仓库.getValueAt(i, 1).toString();
                //String a2 = 角色商城仓库.getValueAt(i, 2).toString();
                商城仓库物品序号.setText(a);
                商城仓库物品代码.setText(a1);
                //商城仓库物品名字.setText(a2);
            }
        });
    }

    private void 刷新角色点券拍卖行() {
        for (int i = ((DefaultTableModel) (this.角色点券拍卖行.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色点券拍卖行.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM auctionitems WHERE characterid =" + this.角色ID.getText());
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 角色点券拍卖行.getModel()).insertRow(角色点券拍卖行.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("characterName")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色点券拍卖行.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色点券拍卖行.getSelectedRow();
                String a = 角色点券拍卖行.getValueAt(i, 0).toString();
                角色点券拍卖行序号.setText(a);
            }
        });
    }

    private void 刷新角色金币拍卖行() {
        for (int i = ((DefaultTableModel) (this.角色金币拍卖行.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色金币拍卖行.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM auctionitems1 WHERE characterid =" + this.角色ID.getText());
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 角色金币拍卖行.getModel()).insertRow(角色金币拍卖行.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("characterName")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色金币拍卖行.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色金币拍卖行.getSelectedRow();
                String a = 角色金币拍卖行.getValueAt(i, 0).toString();
                角色金币拍卖行序号.setText(a);
            }
        });
    }

    private void 刷新角色其他背包() {
        for (int i = ((DefaultTableModel) (this.角色其他背包.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色其他背包.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid =" + this.角色ID.getText() + " && inventorytype = 4");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 角色其他背包.getModel()).insertRow(角色其他背包.getRowCount(), new Object[]{
                    rs.getInt("inventoryitemid"),
                    rs.getInt("itemid"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("quantity")});
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色其他背包.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色其他背包.getSelectedRow();
                String a = 角色其他背包.getValueAt(i, 0).toString();
                String a1 = 角色其他背包.getValueAt(i, 1).toString();
                //String a2 = 角色其他背包.getValueAt(i, 2).toString();
                其他背包物品序号.setText(a);
                其他背包物品代码.setText(a1);
                //其他背包物品名字.setText(a2);
            }
        });
    }

    private void 刷新角色设置背包() {
        for (int i = ((DefaultTableModel) (this.角色设置背包.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色设置背包.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid =" + this.角色ID.getText() + " && inventorytype = 3");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 角色设置背包.getModel()).insertRow(角色设置背包.getRowCount(), new Object[]{
                    rs.getInt("inventoryitemid"),
                    rs.getInt("itemid"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("quantity")});
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色设置背包.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色设置背包.getSelectedRow();
                String a = 角色设置背包.getValueAt(i, 0).toString();
                String a1 = 角色设置背包.getValueAt(i, 1).toString();
                String a2 = 角色设置背包.getValueAt(i, 2).toString();
                设置背包物品序号.setText(a);
                设置背包物品代码.setText(a1);
                设置背包物品名字.setText(a2);
            }
        });
    }

    private void 刷新角色信息2() {
        for (int i = ((DefaultTableModel) (this.角色信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM characters WHERE accountid =" + this.账号ID.getText() + "");
            rs = ps.executeQuery();

            while (rs.next()) {
                String 在线 = "";
                if (World.Find.findChannel(rs.getString("name")) > 0) {
                    在线 = "在线";
                } else {
                    在线 = "离线";
                }
                ((DefaultTableModel) 角色信息.getModel()).insertRow(角色信息.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    rs.getInt("accountid"),
                    rs.getString("name"),
                    getJobNameById(rs.getInt("job")),
                    rs.getInt("level"),
                    rs.getInt("str"),
                    rs.getInt("dex"),
                    rs.getInt("int"),
                    rs.getInt("luk"),
                    
                    rs.getInt("maxhp"),
                    rs.getInt("maxmp"),
                    rs.getInt("meso"),
                    rs.getInt("map"),
                    在线,
                    rs.getInt("gm"),
                    rs.getInt("hair"),
                    rs.getInt("face")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色信息.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色信息.getSelectedRow();
                String a = 角色信息.getValueAt(i, 0).toString();
                String a1 = 角色信息.getValueAt(i, 2).toString();
                String a2 = 角色信息.getValueAt(i, 4).toString();
                String a3 = 角色信息.getValueAt(i, 5).toString();
                String a4 = 角色信息.getValueAt(i, 6).toString();
                String a5 = 角色信息.getValueAt(i, 7).toString();
                String a6 = 角色信息.getValueAt(i, 8).toString();
                String a7 = 角色信息.getValueAt(i, 9).toString();
                String a8 = 角色信息.getValueAt(i, 10).toString();
                String a9 = 角色信息.getValueAt(i, 11).toString();
                String a10 = 角色信息.getValueAt(i, 12).toString();
                String a11 = 角色信息.getValueAt(i, 14).toString();
                String a12 = 角色信息.getValueAt(i, 15).toString();
                String a13 = 角色信息.getValueAt(i, 16).toString();
                角色ID.setText(a);
                角色昵称.setText(a1);
                等级.setText(a2);
                力量.setText(a3);
                敏捷.setText(a4);
                智力.setText(a5);
                运气.setText(a6);
                HP.setText(a7);
                MP.setText(a8);
                金币1.setText(a9);
                地图.setText(a10);
                GM.setText(a11);
                发型.setText(a12);
                脸型.setText(a13);
                //出售状态.setText(a8);
                //jTextField9.setText(a9);
            }
        });
    }

    
     public void 刷新怪物卡片() {
        for (int i = ((DefaultTableModel) (this.怪物爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.怪物爆物.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM drop_data WHERE itemid >=2380000&& itemid <2390000");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 怪物爆物.getModel()).insertRow(怪物爆物.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    rs.getInt("dropperid"),
                    //MapleLifeFactory.getMonster(rs.getInt("dropperid")),
                    rs.getInt("itemid"),
                    rs.getInt("chance"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                });
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        怪物爆物.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 怪物爆物.getSelectedRow();
                String a = 怪物爆物.getValueAt(i, 0).toString();
                String a1 = 怪物爆物.getValueAt(i, 1).toString();
                String a2 = 怪物爆物.getValueAt(i, 2).toString();
                String a3 = 怪物爆物.getValueAt(i, 3).toString();
                //String a4 = 怪物爆物.getValueAt(i, 4).toString();
                怪物爆物序列号.setText(a);
                怪物爆物怪物代码.setText(a1);
                怪物爆物物品代码.setText(a2);
                怪物爆物爆率.setText(a3);
                //怪物爆物物品名称.setText(a4);
            }
        });
    }
    
    public void 刷新世界爆物() {

        for (int i = ((DefaultTableModel) (this.世界爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.世界爆物.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM drop_data_global WHERE itemid !=0");
            rs = ps.executeQuery();
            while (rs.next()) {

                ((DefaultTableModel) 世界爆物.getModel()).insertRow(世界爆物.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    rs.getInt("itemid"),
                    rs.getString("chance"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                });
            }
            rs.close();
            ps.close();
            世界爆物.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int i = 世界爆物.getSelectedRow();
                    String a = 世界爆物.getValueAt(i, 0).toString();
                    String a1 = 世界爆物.getValueAt(i, 1).toString();
                    String a2 = 世界爆物.getValueAt(i, 2).toString();
                    世界爆物序列号.setText(a);
                    世界爆物物品代码.setText(a1);
                    世界爆物爆率.setText(a2);
                }
            });

        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void 刷新指定怪物爆物() {
        boolean result = this.查询怪物掉落代码.getText().matches("[0-9]+");
        if (result) {
            if (Integer.parseInt(this.查询怪物掉落代码.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "请填写正确的值");
            }
            for (int i = ((DefaultTableModel) (this.怪物爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.怪物爆物.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;

                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM drop_data WHERE dropperid =  " + Integer.parseInt(this.怪物爆物怪物代码.getText()) + "");
                rs = ps.executeQuery();
                while (rs.next()) {
                    ((DefaultTableModel) 怪物爆物.getModel()).insertRow(怪物爆物.getRowCount(), new Object[]{rs.getInt("id"), rs.getInt("dropperid"), rs.getInt("itemid"), rs.getInt("chance"), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))});
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            怪物爆物.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int i = 怪物爆物.getSelectedRow();
                    String a = 怪物爆物.getValueAt(i, 0).toString();
                    String a1 = 怪物爆物.getValueAt(i, 1).toString();
                    String a2 = 怪物爆物.getValueAt(i, 2).toString();
                    String a3 = 怪物爆物.getValueAt(i, 3).toString();
                    String a4 = 怪物爆物.getValueAt(i, 4).toString();
                    怪物爆物序列号.setText(a);
                    怪物爆物怪物代码.setText(a1);
                    怪物爆物物品代码.setText(a2);
                    怪物爆物爆率.setText(a3);
                    怪物爆物物品名称.setText(a4);
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "请输入要查询的怪物代码 ");
        }
    }

    public void 刷新怪物爆物() {
        for (int i = ((DefaultTableModel) (this.怪物爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.怪物爆物.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM drop_data WHERE itemid !=0");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 怪物爆物.getModel()).insertRow(怪物爆物.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    rs.getInt("dropperid"),
                    //MapleLifeFactory.getMonster(rs.getInt("dropperid")),
                    rs.getInt("itemid"),
                    rs.getInt("chance"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        怪物爆物.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 怪物爆物.getSelectedRow();
                String a = 怪物爆物.getValueAt(i, 0).toString();
                String a1 = 怪物爆物.getValueAt(i, 1).toString();
                String a2 = 怪物爆物.getValueAt(i, 2).toString();
                String a3 = 怪物爆物.getValueAt(i, 3).toString();
                //String a4 = 怪物爆物.getValueAt(i, 4).toString();
                怪物爆物序列号.setText(a);
                怪物爆物怪物代码.setText(a1);
                怪物爆物物品代码.setText(a2);
                怪物爆物爆率.setText(a3);
                //怪物爆物物品名称.setText(a4);

            }
        });
    }
    
     public void 刷新泡点设置() {
        for (int i = ((DefaultTableModel) (this.在线泡点设置.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.在线泡点设置.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM configvalues WHERE id = 700 || id = 702 || id = 704 || id = 706 || id = 708 || id = 712");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 在线泡点设置.getModel()).insertRow(在线泡点设置.getRowCount(), new Object[]{rs.getString("id"), rs.getString("name"), rs.getString("Val")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
        }
        在线泡点设置.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 在线泡点设置.getSelectedRow();
                String a = 在线泡点设置.getValueAt(i, 0).toString();
                String a1 = 在线泡点设置.getValueAt(i, 1).toString();
                String a2 = 在线泡点设置.getValueAt(i, 2).toString();
                泡点序号.setText(a);
                泡点类型.setText(a1);
                泡点值.setText(a2);
            }
        });
    }
     
    
    
      private void 刷新泡点金币开关() {
        String 泡点金币开关显示 = "";
        int 泡点金币开关 = FengYeDuan.ConfigValuesMap.get("泡点金币开关");
        if (泡点金币开关 <= 0) {
            泡点金币开关显示 = "泡点金币:开启";
        } else {
            泡点金币开关显示 = "泡点金币:关闭";
        }
        泡点金币开关(泡点金币开关显示);
    }

    private void 刷新泡点点券开关() {
        String 泡点点券开关显示 = "";
        int 泡点点券开关 = FengYeDuan.ConfigValuesMap.get("泡点点券开关");
        if (泡点点券开关 <= 0) {
            泡点点券开关显示 = "泡点点券:开启";
        } else {
            泡点点券开关显示 = "泡点点券:关闭";
        }
        泡点点券开关(泡点点券开关显示);
    }

    private void 刷新泡点经验开关() {
        String 泡点经验开关显示 = "";
        int 泡点经验开关 = FengYeDuan.ConfigValuesMap.get("泡点经验开关");
        if (泡点经验开关 <= 0) {
            泡点经验开关显示 = "泡点经验:开启";
        } else {
            泡点经验开关显示 = "泡点经验:关闭";
        }
        泡点经验开关(泡点经验开关显示);
    }

    private void 刷新泡点抵用开关() {
        String 泡点抵用开关显示 = "";
        int 泡点抵用开关 = FengYeDuan.ConfigValuesMap.get("泡点抵用开关");
        if (泡点抵用开关 <= 0) {
            泡点抵用开关显示 = "泡点抵用:开启";
        } else {
            泡点抵用开关显示 = "泡点抵用:关闭";
        }
        泡点抵用开关(泡点抵用开关显示);
    }

    private void 刷新泡点豆豆开关() {
        String 泡点豆豆开关显示 = "";
        int 泡点豆豆开关 = FengYeDuan.ConfigValuesMap.get("泡点豆豆开关");
        if (泡点豆豆开关 <= 0) {
            泡点豆豆开关显示 = "泡点豆豆:开启";
        } else {
            泡点豆豆开关显示 = "泡点豆豆:关闭";
        }
        泡点豆豆开关(泡点豆豆开关显示);
    }
     
    private void 泡点点券开关(String str) {
        泡点点券开关.setText(str);
    }

    private void 泡点经验开关(String str) {
        泡点经验开关.setText(str);
    }

    private void 泡点抵用开关(String str) {
        泡点抵用开关.setText(str);
    }
   
    private void 泡点金币开关(String str) {
        泡点金币开关.setText(str);
    }
    
    private void 泡点豆豆开关(String str) {
        泡点豆豆开关.setText(str);
    }
    //新增开始
   private void 蓝蜗牛开关(String str) {
        蓝蜗牛开关.setText(str);
    }

    private void 蘑菇仔开关(String str) {
        蘑菇仔开关.setText(str);
    }

    private void 绿水灵开关(String str) {
        绿水灵开关.setText(str);
    }

    private void 漂漂猪开关(String str) {
        漂漂猪开关.setText(str);
    }

    private void 小青蛇开关(String str) {
        小青蛇开关.setText(str);
    }

    private void 红螃蟹开关(String str) {
        红螃蟹开关.setText(str);
    }

    private void 大海龟开关(String str) {
        大海龟开关.setText(str);
    }

    private void 章鱼怪开关(String str) {
        章鱼怪开关.setText(str);
    }

    private void 顽皮猴开关(String str) {
        顽皮猴开关.setText(str);
    }

    private void 星精灵开关(String str) {
        星精灵开关.setText(str);
    }

    private void 胖企鹅开关(String str) {
        胖企鹅开关.setText(str);
    }

    private void 白雪人开关(String str) {
        白雪人开关.setText(str);
    }

    private void 紫色猫开关(String str) {
        紫色猫开关.setText(str);
    }

    private void 大灰狼开关(String str) {
        大灰狼开关.setText(str);
    }

    private void 小白兔开关(String str) {
        小白兔开关.setText(str);
    }

    private void 喷火龙开关(String str) {
        喷火龙开关.setText(str);
    }

    private void 火野猪开关(String str) {
        火野猪开关.setText(str);
    }

    private void 青鳄鱼开关(String str) {
        青鳄鱼开关.setText(str);
    }

    private void 花蘑菇开关(String str) {
        花蘑菇开关.setText(str);
    }
    private void 石头人开关(String str) {
        石头人开关.setText(str);
    }

    private void 刷新花蘑菇开关() {
        String 花蘑菇显示 = "";
        int 花蘑菇 = FengYeDuan.ConfigValuesMap.get("花蘑菇开关");
        if (花蘑菇 <= 0) {
             this.花蘑菇开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
             this.花蘑菇开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //花蘑菇开关(花蘑菇显示);
    }

    private void 刷新火野猪开关() {
        String 火野猪显示 = "";
        int 火野猪 = FengYeDuan.ConfigValuesMap.get("火野猪开关");
        if (火野猪 <= 0) {
             this.火野猪开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
             this.火野猪开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //火野猪开关(火野猪显示);
    }

    private void 刷新青鳄鱼开关() {
        String 青鳄鱼显示 = "";
        int 青鳄鱼 = FengYeDuan.ConfigValuesMap.get("青鳄鱼开关");
        if (青鳄鱼 <= 0) {
             this.青鳄鱼开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
             this.青鳄鱼开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //青鳄鱼开关(青鳄鱼显示);
    }

    private void 刷新喷火龙开关() {
        String 喷火龙显示 = "";
        int 喷火龙 = FengYeDuan.ConfigValuesMap.get("喷火龙开关");
        if (喷火龙 <= 0) {
             this.喷火龙开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
             this.喷火龙开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //喷火龙开关(喷火龙显示);
    }

    private void 刷新小白兔开关() {
        String 小白兔显示 = "";
        int 小白兔 = FengYeDuan.ConfigValuesMap.get("小白兔开关");
        if (小白兔 <= 0) {
             this.小白兔开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
             this.小白兔开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //小白兔开关(小白兔显示);
    }

    private void 刷新大灰狼开关() {
        String 大灰狼显示 = "";
        int 大灰狼 = FengYeDuan.ConfigValuesMap.get("大灰狼开关");
        if (大灰狼 <= 0) {
             this.大灰狼开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
             this.大灰狼开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //大灰狼开关(大灰狼显示);
    }

    private void 刷新紫色猫开关() {
        String 紫色猫显示 = "";
        int 紫色猫 = FengYeDuan.ConfigValuesMap.get("紫色猫开关");
        if (紫色猫 <= 0) {
             this.紫色猫开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
             this.紫色猫开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //紫色猫开关(紫色猫显示);
    }


    private void 刷新石头人开关() {
        String 石头人显示 = "";
        int 石头人 = FengYeDuan.ConfigValuesMap.get("石头人开关");
        if (石头人 <= 0) {
             this.石头人开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
             this.石头人开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //石头人开关(石头人显示);
    }

    private void 刷新白雪人开关() {
        String 白雪人显示 = "";
        int 白雪人 = FengYeDuan.ConfigValuesMap.get("白雪人开关");
        if (白雪人 <= 0) {
             this.白雪人开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
             this.白雪人开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //白雪人开关(白雪人显示);
    }

    private void 刷新胖企鹅开关() {
        String 胖企鹅显示 = "";
        int 胖企鹅 = FengYeDuan.ConfigValuesMap.get("胖企鹅开关");
        if (胖企鹅 <= 0) {
            this.胖企鹅开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.胖企鹅开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //胖企鹅开关(胖企鹅显示);
    }

    private void 刷新星精灵开关() {
        String 星精灵显示 = "";
        int 星精灵 = FengYeDuan.ConfigValuesMap.get("星精灵开关");
        if (星精灵 <= 0) {
            this.星精灵开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.星精灵开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //星精灵开关(星精灵显示);
    }

    private void 刷新顽皮猴开关() {
        String 顽皮猴显示 = "";
        int 顽皮猴 = FengYeDuan.ConfigValuesMap.get("顽皮猴开关");
        if (顽皮猴 <= 0) {
            this.顽皮猴开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.顽皮猴开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //顽皮猴开关(顽皮猴显示);
    }

    private void 刷新章鱼怪开关() {
        String 章鱼怪显示 = "";
        int 章鱼怪 = FengYeDuan.ConfigValuesMap.get("章鱼怪开关");
        if (章鱼怪 <= 0) {
            this.章鱼怪开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.章鱼怪开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //章鱼怪开关(章鱼怪显示);
    }

    private void 刷新大海龟开关() {
        String 大海龟显示 = "";
        int 大海龟 = FengYeDuan.ConfigValuesMap.get("大海龟开关");
        if (大海龟 <= 0) {
            this.大海龟开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.大海龟开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //大海龟开关(大海龟显示);
    }

    private void 刷新红螃蟹开关() {
        String 红螃蟹显示 = "";
        int 红螃蟹 = FengYeDuan.ConfigValuesMap.get("红螃蟹开关");
        if (红螃蟹 <= 0) {
            this.红螃蟹开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.红螃蟹开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //红螃蟹开关(红螃蟹显示);
    }

    private void 刷新小青蛇开关() {
        String 小青蛇显示 = "";
        int 小青蛇 = FengYeDuan.ConfigValuesMap.get("小青蛇开关");
        if (小青蛇 <= 0) {
            this.小青蛇开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.小青蛇开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //小青蛇开关(小青蛇显示);
    }

    private void 刷新蓝蜗牛开关() {
        String 蓝蜗牛显示 = "";
        int 蓝蜗牛 = FengYeDuan.ConfigValuesMap.get("蓝蜗牛开关");
        if (蓝蜗牛 <= 0) {
            this.蓝蜗牛开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.蓝蜗牛开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //蓝蜗牛开关(蓝蜗牛显示);
    }

    private void 刷新漂漂猪开关() {
        String 漂漂猪显示 = "";
        int 漂漂猪 = FengYeDuan.ConfigValuesMap.get("漂漂猪开关");
        if (漂漂猪 <= 0) {
            this.漂漂猪开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.漂漂猪开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //漂漂猪开关(漂漂猪显示);
    }

    private void 刷新绿水灵开关() {
        String 绿水灵显示 = "";
        int 绿水灵 = FengYeDuan.ConfigValuesMap.get("绿水灵开关");
        if (绿水灵 <= 0) {
            this.绿水灵开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.绿水灵开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //绿水灵开关(绿水灵显示);
    }

    private void 刷新蘑菇仔开关() {
        String 蘑菇仔显示 = "";
        int 蘑菇仔 = FengYeDuan.ConfigValuesMap.get("蘑菇仔开关");
        if (蘑菇仔 <= 0) {
            this.蘑菇仔开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.蘑菇仔开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
        //蘑菇仔开关(蘑菇仔显示);
    }
    
    private void 刷新指令通知开关() {
        String 刷新指令通知开关显示 = "";
        int 指令通知开关 = FengYeDuan.ConfigValuesMap.get("指令通知开关");
        if (指令通知开关 <= 0) {
            this.指令通知开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.指令通知开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        指令通知开关(刷新指令通知开关显示);
    }
       
        private void 刷新玩家聊天开关() {
        String 刷新玩家聊天开关显示 = "";
        int 玩家聊天开关 = FengYeDuan.ConfigValuesMap.get("玩家聊天开关");
        if (玩家聊天开关 <= 0) {
            this.玩家聊天开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.玩家聊天开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        玩家聊天开关(刷新玩家聊天开关显示);
    }
    
        private void 刷新禁止登陆开关() {
        String 刷新禁止登陆开关显示 = "";
        int 禁止登陆开关 = FengYeDuan.ConfigValuesMap.get("禁止登陆开关");
        if (禁止登陆开关 <= 0) {
            this.禁止登陆开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        } else {
            this.禁止登陆开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        }
        //禁止登陆开关(刷新禁止登陆开关显示);
    }

    
        private void 刷新升级快讯() {
        String 升级快讯显示 = "";
        int 升级快讯 = FengYeDuan.ConfigValuesMap.get("升级快讯开关");
        if (升级快讯 <= 0) {
            this.游戏升级快讯.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.游戏升级快讯.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        游戏升级快讯(升级快讯显示);
    }
        
        private void 刷新丢出金币开关() {
        String 刷新丢出金币开关显示 = "";
        int 丢出金币开关 = FengYeDuan.ConfigValuesMap.get("丢出金币开关");
        if (丢出金币开关 <= 0) {
            this.丢出金币开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.丢出金币开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        丢出金币开关(刷新丢出金币开关显示);
    }

    private void 刷新玩家交易开关() {
        String 刷新玩家交易开关显示 = "";
        int 玩家交易开关 = FengYeDuan.ConfigValuesMap.get("玩家交易开关");
        if (玩家交易开关 <= 0) {
            this.玩家交易开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.玩家交易开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        玩家交易开关(刷新玩家交易开关显示);
    }

    private void 刷新丢出物品开关() {
        String 刷新丢出物品开关显示 = "";
        int 丢出物品开关 = FengYeDuan.ConfigValuesMap.get("丢出物品开关");
        if (丢出物品开关 <= 0) {
            this.丢出物品开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.丢出物品开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        丢出物品开关(刷新丢出物品开关显示);
    }
    
        private void 刷新游戏指令开关() {
        String 刷新游戏指令开关显示 = "";
        int 游戏指令开关 = FengYeDuan.ConfigValuesMap.get("游戏指令开关");
        if (游戏指令开关 <= 0) {
            this.游戏指令开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        } else {
            this.游戏指令开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        }
//        游戏指令开关(刷新游戏指令开关显示);
    }
    
       private void 刷新上线提醒开关() {
        String 刷新上线提醒开关显示 = "";
        int 上线提醒开关 = FengYeDuan.ConfigValuesMap.get("上线提醒开关");
        if (上线提醒开关 <= 0) {
            this.上线提醒开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.上线提醒开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        上线提醒开关(刷新上线提醒开关显示);
    }
    
       private void 刷新回收地图开关() {
        String 刷新回收地图开关显示 = "";
        int 回收地图开关 = FengYeDuan.ConfigValuesMap.get("回收地图开关");
        if (回收地图开关 <= 0) {
            this.回收地图开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.回收地图开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        回收地图开关(刷新回收地图开关显示);
    }
    
        private void 刷新管理隐身开关() {
        String 刷新管理隐身开关显示 = "";
        int 管理隐身开关 = FengYeDuan.ConfigValuesMap.get("管理隐身开关");
        if (管理隐身开关 <= 0) {
            this.管理隐身开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.管理隐身开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        管理隐身开关(刷新管理隐身开关显示);
    }

    private void 刷新管理加速开关() {
        String 刷新管理加速开关显示 = "";
        int 管理加速开关 = FengYeDuan.ConfigValuesMap.get("管理加速开关");
        if (管理加速开关 <= 0) {
            this.管理加速开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.管理加速开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        管理加速开关(刷新管理加速开关显示);
    }
    
        private void 刷新雇佣商人开关() {
        String 刷新雇佣商人开关显示 = "";
        int 雇佣商人开关 = FengYeDuan.ConfigValuesMap.get("雇佣商人开关");
        if (雇佣商人开关 <= 0) {
            this.雇佣商人开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.雇佣商人开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        雇佣商人开关(刷新雇佣商人开关显示);
    }
    
        private void 刷新欢迎弹窗开关() {
        String 刷新欢迎弹窗开关显示 = "";
        int 欢迎弹窗开关 = FengYeDuan.ConfigValuesMap.get("欢迎弹窗开关");
        if (欢迎弹窗开关 <= 0) {
            this.欢迎弹窗开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.欢迎弹窗开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        欢迎弹窗开关(刷新欢迎弹窗开关显示);
    }
    
       private void 刷新滚动公告开关() {
        String 刷新滚动公告开关显示 = "";
        int 滚动公告开关 = FengYeDuan.ConfigValuesMap.get("滚动公告开关");
        if (滚动公告开关 <= 0) {
            this.滚动公告开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.滚动公告开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        滚动公告开关(刷新滚动公告开关显示);
    }

    private void 刷新游戏喇叭开关() {
        String 刷新游戏喇叭开关显示 = "";
        int 游戏喇叭开关 = FengYeDuan.ConfigValuesMap.get("游戏喇叭开关");
        if (游戏喇叭开关 <= 0) {
            this.游戏喇叭开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.游戏喇叭开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        游戏喇叭开关(刷新游戏喇叭开关显示);
    }
    
        private void 滚动公告开关(String str) {
        滚动公告开关.setText(str);
    }

    private void 回收地图开关(String str) {
        回收地图开关.setText(str);
    }
    
        private void 玩家聊天开关(String str) {
        玩家聊天开关.setText(str);
    }
    
        private void 上线提醒开关(String str) {
        上线提醒开关.setText(str);
    }

    private void 指令通知开关(String str) {
        指令通知开关.setText(str);
    }

    private void 雇佣商人开关(String str) {
        雇佣商人开关.setText(str);
    }
    
        private void 欢迎弹窗开关(String str) {
        欢迎弹窗开关.setText(str);
    }
        
        private void 管理隐身开关(String str) {
        管理隐身开关.setText(str);
    }

    private void 管理加速开关(String str) {
        管理加速开关.setText(str);
    }

    private void 游戏指令开关(String str) {
        游戏指令开关.setText(str);
    }

    private void 游戏喇叭开关(String str) {
        游戏喇叭开关.setText(str);
    }

    private void 丢出金币开关(String str) {
        丢出金币开关.setText(str);
    }

    private void 玩家交易开关(String str) {
        玩家交易开关.setText(str);
    }

    private void 丢出物品开关(String str) {
        丢出物品开关.setText(str);
    }

    private void 禁止登陆开关(String str) {
        禁止登陆开关.setText(str);
    }

    private void 游戏升级快讯(String str) {
        游戏升级快讯.setText(str);
    }
    
    private void 冒险家等级上限(String str) {
        jTextMaxLevel.setText(str);
    }

    private void 骑士团等级上限(String str) {
        骑士团等级上限.setText(str);
    }
    
    private void 游戏冒险家职业开关(String str) {
        冒险家职业开关.setText(str);
    }

    private void 游戏骑士团职业开关(String str) {
        骑士团职业开关.setText(str);
    }

    private void 游戏战神职业开关(String str) {
        战神职业开关.setText(str);
    }
    
    private void 屠令广播开关(String str) {
        屠令广播开关.setText(str);
    }
    
    private void UpdateMaxCharacterNumber(String num) {
        jTextFieldMaxCharacterNumber.setText(num);
    }

    private void 刷新登陆帮助() {
        String 显示 = "";
        int S = FengYeDuan.ConfigValuesMap.get("登陆帮助开关");
        if (S <= 0) {
            this.登陆帮助开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.登陆帮助开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        登陆帮助开关.setText(显示);
    }
    
    private void 刷新怪物状态开关() {
        String 显示 = "";
        int S = FengYeDuan.ConfigValuesMap.get("怪物状态开关");
        if (S <= 0) {
            this.怪物状态开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.怪物状态开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        怪物状态开关.setText(显示);
    }
    
     private void 刷新越级打怪开关() {
        String 显示 = "";
        int S = FengYeDuan.ConfigValuesMap.get("越级打怪开关");
        if (S <= 0) {
            this.越级打怪开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.越级打怪开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        越级打怪开关.setText(显示);
    }
    
    private void 刷新地图名称开关() {
        String 显示 = "";
        int S = FengYeDuan.ConfigValuesMap.get("地图名称开关");
        if (S <= 0) {
            this.地图名称开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.地图名称开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        地图名称开关.setText(显示);
    }
    
    
    private void 刷新过图存档时间() {
        String 显示 = "";
        int S = FengYeDuan.ConfigValuesMap.get("过图存档开关");
        if (S <= 0) {
            this.过图存档开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.过图存档开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        过图存档开关.setText(显示);
    }
    
    private void 刷新吸怪检测开关() {
        String 显示 = "";
        int S = FengYeDuan.ConfigValuesMap.get("吸怪检测开关");
        if (S <= 0) {
            this.吸怪检测开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.吸怪检测开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        吸怪检测开关.setText(显示);
    }
    
    private void 刷新冒险家等级上限() {
        String 冒险家等级上限显示 = "";
        int 冒险家等级上限 = FengYeDuan.ConfigValuesMap.get("冒险家等级上限");

        冒险家等级上限显示 = "" + 冒险家等级上限;

        冒险家等级上限(冒险家等级上限显示);
    }

    private void 刷新骑士团等级上限() {
        String 骑士团等级上限显示 = "";
        int 骑士团等级上限 = FengYeDuan.ConfigValuesMap.get("骑士团等级上限");

        骑士团等级上限显示 = "" + 骑士团等级上限;

        骑士团等级上限(骑士团等级上限显示);
    }
    
    private void 刷新冒险家职业开关() {
        String 冒险家职业开关显示 = "";
        int 冒险家职业开关 = FengYeDuan.ConfigValuesMap.get("冒险家职业开关");
        if (冒险家职业开关 <= 0) {
            this.冒险家职业开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.冒险家职业开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
//        游戏冒险家职业开关(冒险家职业开关显示);
    }

    private void 刷新骑士团职业开关() {
        String 骑士团职业开关显示 = "";
        int 骑士团职业开关 = FengYeDuan.ConfigValuesMap.get("骑士团职业开关");
        if (骑士团职业开关 <= 0) {
            this.骑士团职业开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.骑士团职业开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
//        游戏骑士团职业开关(骑士团职业开关显示);
    }

    private void 刷新战神职业开关() {
        String 战神职业开关显示 = "";
        int 战神职业开关 = FengYeDuan.ConfigValuesMap.get("战神职业开关");
        if (战神职业开关 <= 0) {
            this.战神职业开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON2.png")));
        } else {
            this.战神职业开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF2.png")));
        }
//        游戏战神职业开关(战神职业开关显示);
    }
    
    private void 刷新屠令广播开关() {
        String 屠令广播显示 = "";
        int 屠令广播 = FengYeDuan.ConfigValuesMap.get("屠令广播开关");
        if (屠令广播 <= 0) {
            this.屠令广播开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("ON3.png")));
        } else {
            this.屠令广播开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("OFF3.png")));
        }
//        屠令广播开关(屠令广播显示);
    }
    
    private void UpdateMaxCharacterSlots() {
        int MaxNum = FengYeDuan.ConfigValuesMap.get("MaxCharactersNum");
        UpdateMaxCharacterNumber(Integer.toString(MaxNum));
    }
    //新增结束
    
    
   
    
    private static ScheduledFuture<?> ts = null;
    private int minutesLeft = 0;
    private static Thread t = null;

    private void 重启服务器() {
        try {
            String 输出 = "关闭服务器倒数时间";
            minutesLeft = Integer.parseInt(jTextField22.getText());
            if (ts == null && (t == null || !t.isAlive())) {
                t = new Thread(ShutdownServer.getInstance());
                ts = EventTimer.getInstance().register(new Runnable() {

                    @Override
                    public void run() {
                        if (minutesLeft == 0) {
                            ShutdownServer.getInstance();
                            t.start();
                            ts.cancel(false);
                            return;
                        }
                        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, "本服将在 " + minutesLeft + "分钟后关闭. 请尽速关闭雇佣商人 并下线，以免造成损失."));;
                        System.out.println("本服将在 " + minutesLeft + "分钟后关闭.");
                        minutesLeft--;
                    }
                }, 60000);
            }
            jTextField22.setText("关闭服务器倒数时间");
            printChatLog(输出);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + e);
        }
    }

    
   
    
    public void 按键开关(String a, int b) {
        int 检测开关 = FengYeDuan.ConfigValuesMap.get(a);
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        if (检测开关 > 0) {
            try {
                //ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");
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
                    dropperid.close();
                }
                rs.close();
                ps1.close();
                //ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                //ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");
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
                    dropperid.close();
                }
                rs.close();
                ps1.close();
                //ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FengYeDuan.GetConfigValues();
    }
    
    public void 按键开关2(String name, int id, int val) {
        int oval = FengYeDuan.ConfigValuesMap.get(name);
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        try {
            //ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");
            ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM configvalues WHERE id = ?");
            ps1.setInt(1, id);
            rs = ps1.executeQuery();
            if (rs.next()) {
                String sqlString2 = null;
                String sqlString3 = null;
                String sqlString4 = null;
                sqlString2 = "update configvalues set Val= '"+val+"' where id= '" + id + "';";
                PreparedStatement dropperid = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                dropperid.executeUpdate(sqlString2);
                dropperid.close();
            } else {
                ps1 = DatabaseConnection.getConnection().prepareStatement("insert into configvalues values (?,?,?)");
                ps1.setInt(1, id);
                ps1.setString(2, name);
                ps1.setInt(3, val);
                ps1.execute();
            }
            rs.close();
            ps1.close();
            //ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    //新增结束
    
    
   
    

    private void printChatLog(String str) {
        输出窗口.setText(输出窗口.getText() + str + "\r\n");
    }

    private void sendNoticeGG() {
        try {
            String str = jTextField2.getText();
            String 输出 = "";
            for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    mch.startMapEffect(str, 5121009);
                    输出 = "[公告]:" + str;
                }
            }
            jTextField2.setText("");
            printChatLog(输出);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + e);
        }
    }
    
    private void sendNotice(int a) {
        try {
            String str = noticeText.getText();
            String 输出 = "";
            for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    switch (a) {
                        case 0:
                            //顶端公告
                            World.Broadcast.broadcastMessage(MaplePacketCreator.getItemNotice(str.toString()));
                            break;
                        case 1:
                            //顶端公告
                            World.Broadcast.broadcastMessage(MaplePacketCreator.serverMessage(str.toString()));
                            break;
                        case 2:
                            //弹窗公告
                            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(1, str));
                            break;
                        case 3:
                            //聊天蓝色公告
                            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, str));
                            break;
                        case 4:
                            mch.startMapEffect(str, Integer.parseInt(公告发布喇叭代码.getText()));
                            break;
                        default:
                            break;
                    }
                }
                公告发布喇叭代码.setText("5120027");
            }
        } catch (Exception e) {
        }
    }
    
    private void 刷新家族信息() {
        /*
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM characters");
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        for (int i = ((DefaultTableModel) (this.家族信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.家族信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM guilds");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 家族信息.getModel()).insertRow(家族信息.getRowCount(), new Object[]{
                    rs.getInt("guildid"),
                    rs.getString("name"),
                    //ooors.getInt("leader"),
                    //    NPCConversationManager.角色ID取名字(rs.getInt("leader")),
                    rs.getString("rank1title"),
                    rs.getString("rank2title"),
                    rs.getString("rank3title"),
                    rs.getString("rank4title"),
                    rs.getString("rank5title"),
                    rs.getString("GP")
                });
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        家族信息.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 家族信息.getSelectedRow();
                String a1 = 家族信息.getValueAt(i, 0).toString();
                String a2 = 家族信息.getValueAt(i, 1).toString();
                String a3 = 家族信息.getValueAt(i, 2).toString();
                String a4 = 家族信息.getValueAt(i, 3).toString();
                String a5 = 家族信息.getValueAt(i, 4).toString();
                String a6 = 家族信息.getValueAt(i, 5).toString();
                String a7 = 家族信息.getValueAt(i, 6).toString();
                String a8 = 家族信息.getValueAt(i, 7).toString();

                家族ID.setText(a1);
                家族名称.setText(a2);
                家族族长.setText(a3);
                家族成员2.setText(a4);
                家族成员3.setText(a5);
                家族成员4.setText(a6);
                家族成员5.setText(a7);
                家族GP.setText(a8);
            }
        });
    }
    
     public void 刷新经验加成表() {
        for (int i = ((DefaultTableModel) (this.经验加成表.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.经验加成表.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM configvalues WHERE id = 150 ||  id = 151  ||  id=152  ||  id=153  ");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 经验加成表.getModel()).insertRow(经验加成表.getRowCount(), new Object[]{rs.getString("id"), rs.getString("name"), rs.getString("Val")});
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FengYeDuan.class.getName()).log(Level.SEVERE, null, ex);
        }
        经验加成表.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 经验加成表.getSelectedRow();
                String a = 经验加成表.getValueAt(i, 0).toString();
                String a1 = 经验加成表.getValueAt(i, 1).toString();
                String a2 = 经验加成表.getValueAt(i, 2).toString();
                经验加成表序号.setText(a);
                经验加成表类型.setText(a1);
                经验加成表数值.setText(a2);
            }
        });
    }
    
/*
    private void sendNotice(int type) {
        try {
            String str = jTextField1.getText();
            byte[] p = null;
            String 输出 = "";
            if (type == 0) {
                for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr.getName().equals(str) && chr.getMapId() != 0) {
                            chr.getClient().getSession().close();
                            chr.getClient().disconnect(true, false);
                            输出 = "[解卡系统] 成功断开" + str + "玩家！";
                        } else {
                            输出 = "[解卡系统] 玩家名字输入错误或者该玩家没有在线！";
                        }
                    }
                }
            }
            jTextField1.setText("");
            printChatLog(输出);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + e);
        }
    }
*/
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {


        try {
            //加载皮肤
            //System.setProperty("sun.java2d.noddraw", "true");
//            UIManager.put("RootPane.setupButtonVisible", false);
//            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
//            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
//            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
//            UIManager.put("RootPane.setupButtonVisible", false);
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.frameBorderStyle = org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            javax.swing.UIManager.put("RootPane.setupButtonVisible", false);
           // org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
            
            //顺便加载一下字体
            for (int i = 0; i < DEFAULT_FONT.length; i++) {
                UIManager.put(DEFAULT_FONT[i], new Font("微软雅黑", Font.PLAIN, 14));
            }
        } catch (Exception e) {
            //TODO exception
        }
        //线程操作

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FengYeDuan().setVisible(true);
                System.out.println("初始化枫叶端完成，<开服一条龙,作者QQ: 1848350048>");
            }
        }
        );

    }*/
   public static void main(final String[] args) throws Exception {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;//设置本属性将改变窗口边框样式定义
            UIManager.put("RootPane.setupButtonVisible", false);//关闭设置
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
            //顺便加载一下字体
            for (int i = 0; i < DEFAULT_FONT.length; i++) {
                UIManager.put(DEFAULT_FONT[i], new Font("微软雅黑", Font.PLAIN, 14));
            }
        } catch (Exception e) {
            System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "]" + e);
        }
        EventQueue.invokeLater((Runnable) new Runnable() {
            @Override
            public void run() {
               new FengYeDuan().setVisible(true);
 System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][★★★★★★★★★★★★★★★★★★★]");
 System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][★相信未来相信自已 — 怪哥哥★★★★★]");
 System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][★给你快乐的人,且行且珍惜 — 怪哥哥★]");
 System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][★热爱生活,热爱世界 — 怪哥哥★★★★]");
 System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][★★★★★★★★★★★★★★★★★★★]");

            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField GM;
    private javax.swing.JTextField HP;
    private javax.swing.JTextField MP;
    private javax.swing.JTextField QQ;
    private javax.swing.JLabel ZEVMS2提示框1;
    private javax.swing.JTextField a1;
    private java.awt.Canvas canvas1;
    private javax.swing.JTable charTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton1AccountSearch;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonAccountSearch;
    private javax.swing.JButton jButtonLock;
    private javax.swing.JButton jButtonMaxCharacter;
    private javax.swing.JButton jButtonOderByUseTime;
    private javax.swing.JButton jButtonSNDelete;
    private javax.swing.JButton jButtonSNModify;
    private javax.swing.JButton jButtonSNProduction;
    private javax.swing.JButton jButtonSNRefresh;
    private javax.swing.JButton jButtonSNSearch;
    private javax.swing.JButton jButtonSNSearchByAccount;
    private javax.swing.JButton jButtonUnlock;
    private javax.swing.JButton jButtonUnused;
    private javax.swing.JButton jButtonUsed;
    private javax.swing.JComboBox<String> jComboBoxMoneyb;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel183;
    private javax.swing.JLabel jLabel184;
    private javax.swing.JLabel jLabel185;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel192;
    private javax.swing.JLabel jLabel193;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel198;
    private javax.swing.JLabel jLabel199;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel200;
    private javax.swing.JLabel jLabel201;
    private javax.swing.JLabel jLabel202;
    private javax.swing.JLabel jLabel203;
    private javax.swing.JLabel jLabel204;
    private javax.swing.JLabel jLabel205;
    private javax.swing.JLabel jLabel206;
    private javax.swing.JLabel jLabel209;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel210;
    private javax.swing.JLabel jLabel211;
    private javax.swing.JLabel jLabel212;
    private javax.swing.JLabel jLabel213;
    private javax.swing.JLabel jLabel214;
    private javax.swing.JLabel jLabel217;
    private javax.swing.JLabel jLabel219;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel221;
    private javax.swing.JLabel jLabel222;
    private javax.swing.JLabel jLabel223;
    private javax.swing.JLabel jLabel224;
    private javax.swing.JLabel jLabel225;
    private javax.swing.JLabel jLabel226;
    private javax.swing.JLabel jLabel227;
    private javax.swing.JLabel jLabel228;
    private javax.swing.JLabel jLabel229;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel230;
    private javax.swing.JLabel jLabel231;
    private javax.swing.JLabel jLabel232;
    private javax.swing.JLabel jLabel233;
    private javax.swing.JLabel jLabel234;
    private javax.swing.JLabel jLabel235;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel240;
    private javax.swing.JLabel jLabel241;
    private javax.swing.JLabel jLabel242;
    private javax.swing.JLabel jLabel244;
    private javax.swing.JLabel jLabel246;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel252;
    private javax.swing.JLabel jLabel253;
    private javax.swing.JLabel jLabel259;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel268;
    private javax.swing.JLabel jLabel269;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel270;
    private javax.swing.JLabel jLabel271;
    private javax.swing.JLabel jLabel272;
    private javax.swing.JLabel jLabel273;
    private javax.swing.JLabel jLabel274;
    private javax.swing.JLabel jLabel275;
    private javax.swing.JLabel jLabel276;
    private javax.swing.JLabel jLabel277;
    private javax.swing.JLabel jLabel278;
    private javax.swing.JLabel jLabel279;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel280;
    private javax.swing.JLabel jLabel282;
    private javax.swing.JLabel jLabel283;
    private javax.swing.JLabel jLabel287;
    private javax.swing.JLabel jLabel288;
    private javax.swing.JLabel jLabel289;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel290;
    private javax.swing.JLabel jLabel291;
    private javax.swing.JLabel jLabel292;
    private javax.swing.JLabel jLabel293;
    private javax.swing.JLabel jLabel294;
    private javax.swing.JLabel jLabel295;
    private javax.swing.JLabel jLabel296;
    private javax.swing.JLabel jLabel297;
    private javax.swing.JLabel jLabel298;
    private javax.swing.JLabel jLabel299;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel300;
    private javax.swing.JLabel jLabel301;
    private javax.swing.JLabel jLabel302;
    private javax.swing.JLabel jLabel303;
    private javax.swing.JLabel jLabel304;
    private javax.swing.JLabel jLabel305;
    private javax.swing.JLabel jLabel306;
    private javax.swing.JLabel jLabel307;
    private javax.swing.JLabel jLabel308;
    private javax.swing.JLabel jLabel309;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel310;
    private javax.swing.JLabel jLabel311;
    private javax.swing.JLabel jLabel312;
    private javax.swing.JLabel jLabel313;
    private javax.swing.JLabel jLabel314;
    private javax.swing.JLabel jLabel316;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel321;
    private javax.swing.JLabel jLabel322;
    private javax.swing.JLabel jLabel323;
    private javax.swing.JLabel jLabel324;
    private javax.swing.JLabel jLabel325;
    private javax.swing.JLabel jLabel326;
    private javax.swing.JLabel jLabel327;
    private javax.swing.JLabel jLabel328;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel346;
    private javax.swing.JLabel jLabel347;
    private javax.swing.JLabel jLabel348;
    private javax.swing.JLabel jLabel349;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel353;
    private javax.swing.JLabel jLabel354;
    private javax.swing.JLabel jLabel355;
    private javax.swing.JLabel jLabel356;
    private javax.swing.JLabel jLabel357;
    private javax.swing.JLabel jLabel359;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel360;
    private javax.swing.JLabel jLabel361;
    private javax.swing.JLabel jLabel362;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel379;
    private javax.swing.JLabel jLabel380;
    private javax.swing.JLabel jLabel381;
    private javax.swing.JLabel jLabel382;
    private javax.swing.JLabel jLabel384;
    private javax.swing.JLabel jLabel385;
    private javax.swing.JLabel jLabel386;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelUseNX;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel73;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel91;
    private javax.swing.JPanel jPanel93;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane107;
    private javax.swing.JScrollPane jScrollPane108;
    private javax.swing.JScrollPane jScrollPane132;
    private javax.swing.JScrollPane jScrollPane134;
    private javax.swing.JScrollPane jScrollPane136;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTabbedPane jTabbedPane8;
    private javax.swing.JTabbedPane jTabbedPane9;
    private javax.swing.JTabbedPane jTabbedPaneBackendCSPoints;
    private javax.swing.JTable jTableSN;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField jTextFieldAccount;
    private javax.swing.JTextField jTextFieldCSPointsValue;
    private javax.swing.JTextField jTextFieldMaxCharacterNumber;
    private javax.swing.JTextField jTextFieldMoneyB;
    private javax.swing.JTextField jTextFieldNum;
    private javax.swing.JTextField jTextFieldProductionNum;
    private javax.swing.JTextField jTextFieldSN;
    private javax.swing.JTextField jTextFieldSNValue;
    private javax.swing.JTextField jTextFieldType;
    private javax.swing.JTextField jTextMaxLevel;
    private javax.swing.JTextField noticeText;
    private javax.swing.JTextField roleNameEdit;
    private javax.swing.JButton sendMsgNotice;
    private javax.swing.JButton sendNotice;
    private javax.swing.JButton sendNpcTalkNotice;
    private javax.swing.JButton sendWinNotice;
    private static javax.swing.JButton startserverbutton;
    private static javax.swing.JButton startserverbutton1;
    private javax.swing.JButton z1;
    private javax.swing.JButton z2;
    private javax.swing.JButton z3;
    private javax.swing.JButton z4;
    private javax.swing.JButton z5;
    private javax.swing.JButton z6;
    private javax.swing.JTextField 三倍爆率持续时间;
    private javax.swing.JTextField 三倍经验持续时间;
    private javax.swing.JTextField 三倍金币持续时间;
    private javax.swing.JButton 上线提醒开关;
    private javax.swing.JButton 上衣;
    private javax.swing.JTable 世界爆物;
    private javax.swing.JTextField 世界爆物名称;
    private javax.swing.JTextField 世界爆物序列号;
    private javax.swing.JTextField 世界爆物爆率;
    private javax.swing.JTextField 世界爆物物品代码;
    private javax.swing.JButton 丢出物品开关;
    private javax.swing.JButton 丢出金币开关;
    private javax.swing.JTextField 个人发送物品代码;
    private javax.swing.JTextField 个人发送物品数量;
    private javax.swing.JTextField 个人发送物品玩家名字;
    private javax.swing.JButton 个人商店;
    private javax.swing.JButton 主题馆;
    private javax.swing.JButton 会员卡;
    private javax.swing.JButton 修改;
    private javax.swing.JButton 修改世界爆物;
    private javax.swing.JButton 修改冒险家等级上限;
    private javax.swing.JButton 修改反应堆物品;
    private javax.swing.JButton 修改商品;
    private javax.swing.JButton 修改广播;
    private javax.swing.JButton 修改怪物爆物;
    private javax.swing.JButton 修改技能;
    private javax.swing.JButton 修改技能1;
    private javax.swing.JButton 修改背包扩充价格;
    private javax.swing.JButton 修改账号点券抵用;
    private javax.swing.JButton 修改钓鱼物品;
    private javax.swing.JButton 修改骑士团等级上限;
    private javax.swing.JTextField 全服发送物品代码;
    private javax.swing.JTextField 全服发送物品数量;
    private javax.swing.JTextField 全服发送装备物品ID;
    private javax.swing.JTextField 全服发送装备装备HP;
    private javax.swing.JTextField 全服发送装备装备MP;
    private javax.swing.JTextField 全服发送装备装备制作人;
    private javax.swing.JTextField 全服发送装备装备力量;
    private javax.swing.JTextField 全服发送装备装备加卷;
    private javax.swing.JTextField 全服发送装备装备可否交易;
    private javax.swing.JTextField 全服发送装备装备攻击力;
    private javax.swing.JTextField 全服发送装备装备敏捷;
    private javax.swing.JTextField 全服发送装备装备智力;
    private javax.swing.JTextField 全服发送装备装备物理防御;
    private javax.swing.JTextField 全服发送装备装备给予时间;
    private javax.swing.JTextField 全服发送装备装备运气;
    private javax.swing.JTextField 全服发送装备装备魔法力;
    private javax.swing.JTextField 全服发送装备装备魔法防御;
    private javax.swing.JTextField 公告发布喇叭代码;
    private javax.swing.JButton 其他;
    private javax.swing.JTextField 其他背包物品代码;
    private javax.swing.JTextField 其他背包物品名字;
    private javax.swing.JTextField 其他背包物品序号;
    private javax.swing.JButton 冒险家职业开关;
    private javax.swing.JTextField 删MAC代码;
    private javax.swing.JButton 删除IP;
    private javax.swing.JTextField 删除IP代码;
    private javax.swing.JButton 删除MAC;
    private javax.swing.JButton 删除世界爆物;
    private javax.swing.JButton 删除其他背包;
    private javax.swing.JButton 删除反应堆物品;
    private javax.swing.JButton 删除反应堆物品1;
    private javax.swing.JTextField 删除反应堆物品代码;
    private javax.swing.JButton 删除商品;
    private javax.swing.JButton 删除商城仓库;
    private javax.swing.JButton 删除广播;
    private javax.swing.JButton 删除怪物爆物;
    private javax.swing.JButton 删除技能;
    private javax.swing.JButton 删除拍卖行;
    private javax.swing.JButton 删除拍卖行1;
    private javax.swing.JTextField 删除指定的掉落;
    private javax.swing.JTextField 删除指定的掉落1;
    private javax.swing.JButton 删除指定的掉落按键;
    private javax.swing.JButton 删除指定的掉落按键1;
    private javax.swing.JButton 删除消耗背包;
    private javax.swing.JButton 删除游戏仓库;
    private javax.swing.JButton 删除特殊背包;
    private javax.swing.JButton 删除穿戴装备;
    private javax.swing.JButton 删除装备背包;
    private javax.swing.JButton 删除角色;
    private javax.swing.JButton 删除设置背包;
    private javax.swing.JButton 删除账号;
    private javax.swing.JButton 删除钓鱼物品;
    private javax.swing.JButton 刷新世界爆物;
    private javax.swing.JButton 刷新家族信息;
    private javax.swing.JButton 刷新封IP;
    private javax.swing.JButton 刷新封MAC;
    private javax.swing.JButton 刷新广告;
    private javax.swing.JButton 刷新怪物卡片;
    private javax.swing.JButton 刷新怪物爆物;
    private javax.swing.JButton 刷新角色信息;
    private javax.swing.JButton 刷新账号信息;
    private javax.swing.JButton 刷新钓鱼物品;
    private javax.swing.JTextField 力量;
    private javax.swing.JButton 卡号自救1;
    private javax.swing.JButton 卡号自救2;
    private javax.swing.JButton 卡家族解救;
    private javax.swing.JButton 卷轴;
    private javax.swing.JTextField 双倍爆率持续时间;
    private javax.swing.JTextField 双倍经验持续时间;
    private javax.swing.JTextField 双倍金币持续时间;
    private javax.swing.JTable 反应堆;
    private javax.swing.JTextField 反应堆代码;
    private javax.swing.JTextField 反应堆序列号;
    private javax.swing.JTextField 反应堆概率;
    private javax.swing.JTextField 反应堆物品;
    private javax.swing.JTextField 发型;
    private javax.swing.JButton 发布广告;
    private javax.swing.JTextField 发送装备玩家姓名;
    private javax.swing.JButton 吸怪检测开关;
    private javax.swing.JTextField 商品代码;
    private javax.swing.JTextField 商品价格;
    private javax.swing.JTextField 商品出售状态;
    private javax.swing.JTextField 商品名称;
    private javax.swing.JTextField 商品售价金币;
    private javax.swing.JTextField 商品序号;
    private javax.swing.JTextField 商品库存;
    private javax.swing.JTextField 商品折扣;
    private javax.swing.JTextField 商品数量;
    private javax.swing.JTextField 商品时间;
    private javax.swing.JTextField 商品物品代码;
    private javax.swing.JTextField 商品编码;
    private javax.swing.JTextField 商城仓库物品代码;
    private javax.swing.JTextField 商城仓库物品名字;
    private javax.swing.JTextField 商城仓库物品序号;
    private javax.swing.JTable 商城扩充价格;
    private javax.swing.JTextField 商城扩充价格修改;
    private javax.swing.JTextField 商店代码;
    private javax.swing.JButton 喜庆物品;
    private javax.swing.JButton 喷火龙开关;
    private javax.swing.JButton 回收地图开关;
    private javax.swing.JTable 在线泡点设置;
    private javax.swing.JButton 在线角色;
    private javax.swing.JButton 在线账号;
    private javax.swing.JTextField 地图;
    private javax.swing.JButton 地图名称开关;
    private javax.swing.JButton 大海龟开关;
    private javax.swing.JButton 大灰狼开关;
    private javax.swing.JButton 宠物;
    private javax.swing.JButton 宠物服饰;
    private javax.swing.JTextField 家族GP;
    private javax.swing.JTextField 家族ID;
    private javax.swing.JTable 家族信息;
    private javax.swing.JTextField 家族名称;
    private javax.swing.JTextField 家族成员2;
    private javax.swing.JTextField 家族成员3;
    private javax.swing.JTextField 家族成员4;
    private javax.swing.JTextField 家族成员5;
    private javax.swing.JTextField 家族族长;
    private javax.swing.JTable 封IP;
    private javax.swing.JTable 封MAC;
    private javax.swing.JButton 封锁账号;
    private javax.swing.JButton 小白兔开关;
    private javax.swing.JButton 小青蛇开关;
    private javax.swing.JButton 屠令广播开关;
    private javax.swing.JButton 已封账号;
    private javax.swing.JButton 帽子;
    private javax.swing.JButton 幸运职业开关;
    private javax.swing.JTable 广播信息;
    private javax.swing.JTextField 广播序号;
    private javax.swing.JTextField 广播文本;
    private javax.swing.JButton 开启三倍爆率;
    private javax.swing.JButton 开启三倍经验;
    private javax.swing.JButton 开启三倍金币;
    private javax.swing.JButton 开启双倍爆率;
    private javax.swing.JButton 开启双倍经验;
    private javax.swing.JButton 开启双倍金币;
    private javax.swing.JTable 怪物爆物;
    private javax.swing.JTextField 怪物爆物序列号;
    private javax.swing.JTextField 怪物爆物怪物代码;
    private javax.swing.JTextField 怪物爆物爆率;
    private javax.swing.JTextField 怪物爆物物品代码;
    private javax.swing.JTextField 怪物爆物物品名称;
    private javax.swing.JButton 怪物状态开关;
    private javax.swing.JButton 戒指;
    private javax.swing.JButton 战神职业开关;
    private javax.swing.JButton 手套;
    private javax.swing.JPanel 技能;
    private javax.swing.JTextField 技能代码;
    private javax.swing.JTable 技能信息;
    private javax.swing.JTextField 技能名字;
    private javax.swing.JTextField 技能序号;
    private javax.swing.JTextField 技能最高等级;
    private javax.swing.JTextField 技能目前等级;
    private javax.swing.JButton 披风;
    private javax.swing.JTextField 抵用;
    private javax.swing.JTextField 拍卖行物品代码;
    private javax.swing.JTextField 拍卖行物品代码1;
    private javax.swing.JTextField 拍卖行物品名字;
    private javax.swing.JTextField 拍卖行物品名字1;
    private javax.swing.JButton 指令通知开关;
    private javax.swing.JButton 效果;
    private javax.swing.JTextField 敏捷;
    private javax.swing.JButton 新增反应堆物品;
    private javax.swing.JButton 新增商品;
    private javax.swing.JButton 新增钓鱼物品;
    private javax.swing.JButton 星精灵开关;
    private javax.swing.JLabel 显示在线玩家;
    private javax.swing.JLabel 显示在线账号;
    private javax.swing.JButton 显示管理角色;
    private javax.swing.JTextField 显示类型;
    private javax.swing.JTextField 智力;
    private javax.swing.JTextField 查找反应堆掉落;
    private javax.swing.JTextField 查找物品;
    private javax.swing.JButton 查看技能;
    private javax.swing.JButton 查看背包;
    private javax.swing.JTextField 查询商店;
    private javax.swing.JButton 查询商店2;
    private javax.swing.JButton 查询怪物掉落;
    private javax.swing.JTextField 查询怪物掉落代码;
    private javax.swing.JButton 查询物品掉落;
    private javax.swing.JButton 查询物品掉落1;
    private javax.swing.JTextField 查询物品掉落代码;
    private javax.swing.JTextField 查询物品掉落代码1;
    private javax.swing.JButton 欢迎弹窗开关;
    private javax.swing.JButton 武器;
    private javax.swing.JTextField 每日限购;
    private javax.swing.JTextField 泡点值;
    private javax.swing.JButton 泡点值修改;
    private javax.swing.JTextField 泡点序号;
    private javax.swing.JButton 泡点抵用开关;
    private javax.swing.JButton 泡点点券开关;
    private javax.swing.JTextField 泡点类型;
    private javax.swing.JButton 泡点经验开关;
    private javax.swing.JButton 泡点豆豆开关;
    private javax.swing.JButton 泡点金币开关;
    private javax.swing.JTextField 注册的密码;
    private javax.swing.JTextField 注册的账号;
    private javax.swing.JButton 活动;
    private javax.swing.JTextField 消耗背包物品代码;
    private javax.swing.JTextField 消耗背包物品名字;
    private javax.swing.JTextField 消耗背包物品序号;
    private javax.swing.JButton 添加;
    private javax.swing.JButton 添加世界爆物;
    private javax.swing.JButton 添加怪物爆物;
    private javax.swing.JButton 游戏;
    private javax.swing.JTextField 游戏仓库物品代码;
    private javax.swing.JTextField 游戏仓库物品名字;
    private javax.swing.JTextField 游戏仓库物品序号;
    private javax.swing.JButton 游戏升级快讯;
    private javax.swing.JTable 游戏商店2;
    private javax.swing.JButton 游戏喇叭开关;
    private javax.swing.JPanel 游戏广播;
    private javax.swing.JButton 游戏指令开关;
    private javax.swing.JButton 游戏经验加成说明;
    private javax.swing.JButton 滚动公告开关;
    private javax.swing.JButton 漂漂猪开关;
    private javax.swing.JButton 火野猪开关;
    private javax.swing.JTextField 点券;
    private javax.swing.JTextField 点券购买;
    private javax.swing.JTextField 特殊背包物品代码;
    private javax.swing.JTextField 特殊背包物品名字;
    private javax.swing.JTextField 特殊背包物品序号;
    private javax.swing.JButton 玩家交易开关;
    private javax.swing.JButton 玩家聊天开关;
    private javax.swing.JButton 登陆帮助开关;
    private javax.swing.JButton 白雪人开关;
    private javax.swing.JButton 眼饰;
    private javax.swing.JButton 石头人开关;
    private javax.swing.JButton 神秘商人开关;
    private javax.swing.JButton 禁止登陆开关;
    private javax.swing.JLabel 福利提示语言2;
    private javax.swing.JButton 离线角色;
    private javax.swing.JButton 离线账号;
    private javax.swing.JButton 章鱼怪开关;
    private javax.swing.JTextField 等级;
    private javax.swing.JTextField 管理1;
    private javax.swing.JButton 管理加速开关;
    private javax.swing.JButton 管理隐身开关;
    private javax.swing.JButton 紫色猫开关;
    private javax.swing.JButton 红螃蟹开关;
    private javax.swing.JButton 纪念日;
    private javax.swing.JTable 经验加成表;
    private javax.swing.JButton 经验加成表修改;
    private javax.swing.JTextField 经验加成表序号;
    private javax.swing.JTextField 经验加成表数值;
    private javax.swing.JTextField 经验加成表类型;
    private javax.swing.JButton 给予物品;
    private javax.swing.JButton 给予物品1;
    private javax.swing.JButton 给予装备1;
    private javax.swing.JButton 给予装备2;
    private javax.swing.JButton 绿水灵开关;
    private javax.swing.JTextField 背包物品代码1;
    private javax.swing.JTextField 背包物品名字1;
    private javax.swing.JButton 胖企鹅开关;
    private javax.swing.JTextField 脸型;
    private javax.swing.JButton 脸饰;
    private javax.swing.JButton 花蘑菇开关;
    private javax.swing.JButton 蓝蜗牛开关;
    private javax.swing.JButton 蘑菇仔开关;
    private javax.swing.JButton 表情;
    private javax.swing.JTextField 装备背包物品代码;
    private javax.swing.JTextField 装备背包物品名字;
    private javax.swing.JTextField 装备背包物品序号;
    private javax.swing.JButton 裙裤;
    private javax.swing.JTextField 角色ID;
    private javax.swing.JTable 角色信息;
    private javax.swing.JPanel 角色信息1;
    private javax.swing.JTable 角色其他背包;
    private javax.swing.JTable 角色商城仓库;
    private javax.swing.JTextField 角色昵称;
    private javax.swing.JTable 角色消耗背包;
    private javax.swing.JTable 角色游戏仓库;
    private javax.swing.JTable 角色点券拍卖行;
    private javax.swing.JTextField 角色点券拍卖行序号;
    private javax.swing.JTable 角色特殊背包;
    private javax.swing.JPanel 角色背包;
    private javax.swing.JTable 角色背包穿戴;
    private javax.swing.JTable 角色装备背包;
    private javax.swing.JTable 角色设置背包;
    private javax.swing.JTable 角色金币拍卖行;
    private javax.swing.JTextField 角色金币拍卖行序号;
    private javax.swing.JButton 解卡;
    private javax.swing.JButton 解封;
    private javax.swing.JTextField 设置背包物品代码;
    private javax.swing.JTextField 设置背包物品名字;
    private javax.swing.JTextField 设置背包物品序号;
    private javax.swing.JButton 读取热销产品;
    private javax.swing.JTextField 账号;
    private javax.swing.JTextField 账号ID;
    private javax.swing.JTable 账号信息;
    private javax.swing.JLabel 账号提示语言;
    private javax.swing.JTextField 账号操作;
    private javax.swing.JTextField 货币类型;
    private javax.swing.JButton 越级打怪开关;
    private javax.swing.JTextField 身上穿戴序号1;
    private javax.swing.JTextArea 输出窗口;
    private javax.swing.JButton 过图存档开关;
    private javax.swing.JTextField 运气;
    private javax.swing.JButton 通讯物品;
    private javax.swing.JButton 重载任务;
    private javax.swing.JButton 重载传送门按钮;
    private javax.swing.JButton 重载副本按钮;
    private javax.swing.JButton 重载包头按钮;
    private javax.swing.JButton 重载反应堆按钮;
    private javax.swing.JButton 重载商城按钮;
    private javax.swing.JButton 重载商店按钮;
    private javax.swing.JButton 重载爆率按钮;
    private javax.swing.JButton 重载箱子反应堆按钮;
    private javax.swing.JTextField 金币1;
    private javax.swing.JTable 钓鱼物品;
    private javax.swing.JTextField 钓鱼物品代码;
    private javax.swing.JTextField 钓鱼物品名称;
    private javax.swing.JTextField 钓鱼物品序号;
    private javax.swing.JTextField 钓鱼物品概率;
    private javax.swing.JPanel 钓鱼管理;
    private javax.swing.JButton 长袍;
    private javax.swing.JButton 雇佣商人开关;
    private javax.swing.JButton 青鳄鱼开关;
    private javax.swing.JButton 鞋子;
    private javax.swing.JButton 顽皮猴开关;
    private javax.swing.JButton 飞镖;
    private javax.swing.JTextField 骑士团等级上限;
    private javax.swing.JButton 骑士团职业开关;
    private javax.swing.JButton 骑宠;
    private javax.swing.JButton 魔族攻城开关;
    private javax.swing.JButton 魔族突袭开关;
    // End of variables declaration//GEN-END:variables


}
