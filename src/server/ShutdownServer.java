package server;

import java.util.Set;
import java.util.Iterator;
import handling.cashshop.CashShopServer;
import handling.login.LoginServer;
import handling.world.World.Family;
import handling.world.World.Alliance;
import handling.world.World.Guild;
import merchant.merchant_main;
import handling.channel.ChannelServer;
import handling.world.World;

public class ShutdownServer implements Runnable, ShutdownServerMBean
{
    private static final ShutdownServer instance;
    public static boolean running;
    
    public static ShutdownServer getInstance() {
        return ShutdownServer.instance;
    }
    
    @Override
    public void run() {
        synchronized (this) {
            if (ShutdownServer.running) {
                return;
            }
            ShutdownServer.running = true;
        }
        World.isShutDown = true;
        int ret = 0;
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            ret += cserv.closeAllMerchant();
        }
        System.out.println("共储存了 " + ret + " 个精灵商人");
        ret = 0;
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            ret += cserv.closeAllPlayerShop();
        }
        merchant_main.getInstance().save_data();
        System.out.println("共储存了 " + ret + " 个人雇佣商店");
        Guild.save();
        System.out.println("公会资料储存完毕");
        Alliance.save();
        System.out.println("联盟资料储存完毕");
        Family.save();
        System.out.println("家族资料储存完毕");
        server.Timer.EventTimer.getInstance().stop();
        server.Timer.WorldTimer.getInstance().stop();
        server.Timer.MapTimer.getInstance().stop();
        server.Timer.MobTimer.getInstance().stop();
        server.Timer.BuffTimer.getInstance().stop();
        server.Timer.CloneTimer.getInstance().stop();
        server.Timer.EtcTimer.getInstance().stop();
        server.Timer.PingTimer.getInstance().stop();
        System.out.println("Timer 关闭完成");
        final Set<Integer> channels = ChannelServer.getAllChannels();
        for (final Integer channel : channels) {
        }
        try {
            LoginServer.shutdown();
            System.out.println("[登陆本地服务器关闭完成]");
        }
        catch (Exception e2) {
            System.out.println("[登陆本地服务器关闭失败]");
        }
        try {
            CashShopServer.shutdown();
            System.out.println("[购物商城关闭完成]\r\n\r\n本地服务器关闭完成，感谢使用@枫叶服务端！！！");
        }
        catch (Exception e2) {
            System.out.println("[购物商城关闭失败]");
        }
    }
    
    @Override
    public void shutdown() {
        this.run();
    }
    
    static {
        instance = new ShutdownServer();
        ShutdownServer.running = false;
    }
}
