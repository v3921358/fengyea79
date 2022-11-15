package handling.login;

import java.util.WeakHashMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import tools.FileoutputUtil;
import client.MapleClient;
import constants.ServerConfig;
import handling.world.MapleParty;
import java.util.Map;
import handling.mina.ServerConnection;

public class LoginServer
{
    public static short port;
    private static ServerConnection acceptor;
    private static Map<Integer, Integer> load;
    private static int usersOn;
    private static boolean finishedShutdown;
    private static final Map<Integer, String> LoginMacs;
    private static final Map<Integer, String> LoginKey;
    private static final Map<Integer, String> ClientKey;
    private static final Map<Integer, String> ServerKey;
    private static AccountStorage clients;
    private static final Map<Integer, Long> ChangeChannelTime;
    private static final Map<Integer, Long> EnterGameTime;
    
    public static final void addChannel(final int channel) {
        LoginServer.load.put(Integer.valueOf(channel), Integer.valueOf(0));
    }
    
    public static final void removeChannel(final int channel) {
        LoginServer.load.remove(Integer.valueOf(channel));
    }
    
    public static final void setup() {
        LoginServer.port = 8484;
        (LoginServer.acceptor = new ServerConnection((int)LoginServer.port, 0, 0)).run();
        System.out.println("[正在启动] 服务器IP:" + MapleParty.IP地址);
        System.out.println("[正在启动] 登录端口:" + Short.toString(LoginServer.port) + "");
    }
    
    public static final void shutdown() {
        if (LoginServer.finishedShutdown) {
            System.out.println("[登录器服务器] 已经关闭了，无法执行此操作。");
            return;
        }
        System.out.println("[登录器服务器] 关闭中");
        LoginServer.acceptor.close();
        System.out.println("[登录器服务器] 关闭完成");
        LoginServer.finishedShutdown = true;
    }
    
    public static final String getServerName() {
        return ServerConfig.SERVERNAME;
    }
    
    public static final Map<Integer, Integer> getLoad() {
        return LoginServer.load;
    }
    
    public static void setLoad(final Map<Integer, Integer> load_, final int usersOn_) {
        LoginServer.load = load_;
        LoginServer.usersOn = usersOn_;
    }
    
    public static final int getUsersOn() {
        return LoginServer.usersOn;
    }
    
    public static final boolean isShutdown() {
        return LoginServer.finishedShutdown;
    }
    
    public static final void setOn() {
        LoginServer.finishedShutdown = false;
    }
    
    public static boolean getAutoReg() {
        return ServerConfig.AUTO_REGISTER;
    }
    
    public static void setAutoReg(final boolean x) {
        ServerConfig.AUTO_REGISTER = x;
    }
    
    public static String getTouDing() {
        return ServerConfig.TOUDING;
    }
    
    public static void setTouDing(final String x) {
        ServerConfig.TOUDING = x;
    }
    
    public static boolean getNMGB() {
        return ServerConfig.NMGB;
    }
    
    public static void setNMGB(final boolean x) {
        ServerConfig.NMGB = x;
    }
    
    public static boolean getPDCS() {
        return ServerConfig.PDCS;
    }
    
    public static void setPDCS(final boolean x) {
        ServerConfig.PDCS = x;
    }
    
    public static AccountStorage getClientStorage() {
        if (LoginServer.clients == null) {
            LoginServer.clients = new AccountStorage();
        }
        return LoginServer.clients;
    }
    
    public static final void removeClient(final MapleClient c) {
        getClientStorage().deregisterAccount(c);
    }
    
    public static final String getLoginMac(final MapleClient c) {
        String macs = null;
        if (LoginServer.LoginMacs.containsKey(c.getAccID())) {
            macs = (String)LoginServer.LoginMacs.get(c.getAccID());
        }
        return macs;
    }
    
    public static final String removeLoginMac(final MapleClient c) {
        final String macs = null;
        if (LoginServer.LoginMacs.containsKey(c.getAccID())) {
            LoginServer.LoginMacs.remove(c.getAccID());
        }
        return macs;
    }
    
    public static boolean CanLoginKey(final String key, final int AccID) {
        return LoginServer.LoginKey.get(Integer.valueOf(AccID)) == null || (LoginServer.LoginKey.containsValue(key) && ((String)LoginServer.LoginKey.get(Integer.valueOf(AccID))).equals(key));
    }
    
    public static boolean RemoveLoginKey(final int AccID) {
        try {
            LoginServer.LoginKey.remove(Integer.valueOf(AccID));
        }
        catch (Exception ex) {
            FileoutputUtil.outError("logs/移除Key異常.txt", (Throwable)ex);
        }
        return true;
    }
    
    public static boolean addLoginKey(final String key, final int AccID) {
        try {
            if (LoginServer.LoginKey.get(Integer.valueOf(AccID)) == null) {
                LoginServer.LoginKey.put(Integer.valueOf(AccID), key);
                return true;
            }
            LoginServer.LoginKey.remove(Integer.valueOf(AccID));
            LoginServer.LoginKey.put(Integer.valueOf(AccID), key);
        }
        catch (Exception ex) {
            FileoutputUtil.outError("logs/添加Key異常.txt", (Throwable)ex);
        }
        return true;
    }
    
    public static String getLoginKey(final int AccID) {
        return (String)LoginServer.LoginKey.get(Integer.valueOf(AccID));
    }
    
    public static boolean CanServerKey(final String key, final int AccID) {
        return LoginServer.ServerKey.get(Integer.valueOf(AccID)) == null || (LoginServer.ServerKey.containsValue(key) && ((String)LoginServer.ServerKey.get(Integer.valueOf(AccID))).equals(key));
    }
    
    public static boolean RemoveServerKey(final int AccID) {
        try {
            LoginServer.ServerKey.remove(Integer.valueOf(AccID));
        }
        catch (Exception ex) {
            FileoutputUtil.outError("logs/移除Key異常.txt", (Throwable)ex);
        }
        return true;
    }
    
    public static boolean addServerKey(final String key, final int AccID) {
        try {
            if (LoginServer.ServerKey.get(Integer.valueOf(AccID)) == null) {
                LoginServer.ServerKey.put(Integer.valueOf(AccID), key);
                return true;
            }
            LoginServer.ServerKey.remove(Integer.valueOf(AccID));
            LoginServer.ServerKey.put(Integer.valueOf(AccID), key);
        }
        catch (Exception ex) {
            FileoutputUtil.outError("logs/添加Key異常.txt", (Throwable)ex);
        }
        return true;
    }
    
    public static String getServerKey(final int AccID) {
        return (String)LoginServer.ServerKey.get(Integer.valueOf(AccID));
    }
    
    public static boolean CanClientKey(final String key, final int AccID) {
        return LoginServer.ClientKey.get(Integer.valueOf(AccID)) == null || (LoginServer.ClientKey.containsValue(key) && ((String)LoginServer.ClientKey.get(Integer.valueOf(AccID))).equals(key));
    }
    
    public static boolean RemoveClientKey(final int AccID) {
        try {
            LoginServer.ClientKey.remove(Integer.valueOf(AccID));
        }
        catch (Exception ex) {
            FileoutputUtil.outError("logs/移除Key異常.txt", (Throwable)ex);
        }
        return true;
    }
    
    public static boolean addClientKey(final String key, final int AccID) {
        try {
            if (LoginServer.ClientKey.get(Integer.valueOf(AccID)) == null) {
                LoginServer.ClientKey.put(Integer.valueOf(AccID), key);
                return true;
            }
            LoginServer.ClientKey.remove(Integer.valueOf(AccID));
            LoginServer.ClientKey.put(Integer.valueOf(AccID), key);
        }
        catch (Exception ex) {
            FileoutputUtil.outError("logs/添加Key異常.txt", (Throwable)ex);
        }
        return true;
    }
    
    public static String getClientKey(final int AccID) {
        return (String)LoginServer.ClientKey.get(Integer.valueOf(AccID));
    }
    
    public static boolean getDiscounied() {
        return ServerConfig.DISCOUNTED;
    }
    
    public static void setDiscounied(final boolean x) {
        ServerConfig.DISCOUNTED = x;
    }
    
    public static int getRSGS() {
        return ServerConfig.RSGS;
    }
    
    public static void setRSGS(final int x) {
        ServerConfig.RSGS = x;
    }
    
    public static void forceRemoveClient(final MapleClient client) {
        forceRemoveClient(client, true);
    }
    
    public static void forceRemoveClient(final MapleClient client, final boolean remove) {
        final Collection<MapleClient> cls = getClientStorage().getAllClientsThreadSafe();
        for (final MapleClient c : cls) {
            if (c == null) {
                continue;
            }
            if (c.getAccID() != client.getAccID() && c != client) {
                continue;
            }
            if (c != client) {
                c.unLockDisconnect();
            }
            if (!remove) {
                continue;
            }
            removeClient(c);
        }
    }
    
    public static long getLoginAgainTime(final int accid) {
        return (long)Long.valueOf(LoginServer.ChangeChannelTime.get(Integer.valueOf(accid)));
    }
    
    public static void addLoginAgainTime(final int accid) {
        LoginServer.ChangeChannelTime.put(Integer.valueOf(accid), System.currentTimeMillis());
    }
    
    public static boolean canLoginAgain(final int accid) {
        final long lastTime = System.currentTimeMillis();
        if (LoginServer.ChangeChannelTime.containsKey(Integer.valueOf(accid))) {
            final long lastSelectCharTime = (long)Long.valueOf(LoginServer.ChangeChannelTime.get(Integer.valueOf(accid)));
            if (lastSelectCharTime + 40000L > lastTime) {
                return false;
            }
        }
        return true;
    }
    
    public static long getEnterGameAgainTime(final int accid) {
        return (long)Long.valueOf(LoginServer.EnterGameTime.get(Integer.valueOf(accid)));
    }
    
    public static void addEnterGameAgainTime(final int accid) {
        LoginServer.EnterGameTime.put(Integer.valueOf(accid), System.currentTimeMillis());
    }
    
    public static boolean canEnterGameAgain(final int accid) {
        final long lastTime = System.currentTimeMillis();
        if (LoginServer.EnterGameTime.containsKey(Integer.valueOf(accid))) {
            final long lastSelectCharTime = (long)Long.valueOf(LoginServer.EnterGameTime.get(Integer.valueOf(accid)));
            if (lastSelectCharTime + 60000L > lastTime) {
                return false;
            }
        }
        return true;
    }
    
    static {
        LoginServer.port = 8484;
        LoginServer.load = new HashMap<Integer, Integer>();
        LoginServer.usersOn = 0;
        LoginServer.finishedShutdown = true;
        LoginMacs = new WeakHashMap<Integer, String>();
        LoginKey = new HashMap<Integer, String>();
        ClientKey = new HashMap<Integer, String>();
        ServerKey = new HashMap<Integer, String>();
        ChangeChannelTime = new HashMap<Integer, Long>();
        EnterGameTime = new HashMap<Integer, Long>();
    }
}
