package constants;

import gui.FengYeDuan;
import server.ServerProperties;

public class WorldConstants
{
    public static Option WORLD;
    public static boolean ADMIN_ONLY;
    public static boolean JZSD;
    public static boolean WUYANCHI;
    public static boolean LieDetector;
    public static boolean DropItem;
    public static int USER_LIMIT;
    public static int MAX_CHAR_VIEW;
    public static boolean GMITEMS;
    public static boolean CS_ENABLE;
    public static int EXP_RATE;
    public static int MESO_RATE;
    public static int DROP_RATE;
    public static byte FLAG;
    public static int CHANNEL_COUNT;
    public static String WORLD_TIP;
    public static String SCROLL_MESSAGE;
    public static boolean AVAILABLE;
    public static final int gmserver = -1;
    public static final byte recommended = -1;
    public static final String recommendedmsg;
    public static int discount;
    public static boolean MobLieDetector;
    public static boolean isDiscount;
    public static int LieDetectorNum;
    public static int MobLieDetectorNum;
    
    public static Option[] values() {
        return ServerConstants.TESPIA ? TespiaWorldOption.values() : WorldOption.values();
    }
    
    public static Option valueOf(final String name) {
        return ServerConstants.TESPIA ? TespiaWorldOption.valueOf(name) : WorldOption.valueOf(name);
    }
    
    public static Option getById(final int g) {
        for (final Option e : values()) {
            if (e.getWorld() == g) {
                return e;
            }
        }
        return null;
    }
    
    public static boolean isExists(final int id) {
        return getById(id) != null;
    }
    
    public static String getNameById(final int serverid) {
        if (getById(serverid) == null) {
            System.err.println("World doesn't exists exception. ID: " + serverid);
            return "";
        }
        return getById(serverid).name();
    }
    
    public static void loadSetting() {
        WorldConstants.ADMIN_ONLY = ServerProperties.getProperty("FengYeDuan.admin", WorldConstants.ADMIN_ONLY);
        WorldConstants.FLAG = ServerProperties.getProperty("FengYeDuan.flag", WorldConstants.FLAG);
        WorldConstants.EXP_RATE = ServerProperties.getProperty("FengYeDuan.expRate", WorldConstants.EXP_RATE);
        WorldConstants.MESO_RATE = ServerProperties.getProperty("FengYeDuan.mesoRate", WorldConstants.MESO_RATE);
        WorldConstants.DROP_RATE = ServerProperties.getProperty("FengYeDuan.dropRat", WorldConstants.DROP_RATE);
        WorldConstants.WORLD_TIP = ServerProperties.getProperty("FengYeDuan.eventMessage", WorldConstants.WORLD_TIP);
        WorldConstants.SCROLL_MESSAGE = ServerProperties.getProperty("FengYeDuan.serverMessage", WorldConstants.SCROLL_MESSAGE);
        WorldConstants.CHANNEL_COUNT = ServerProperties.getProperty("FengYeDuan.channel.count", WorldConstants.CHANNEL_COUNT);
        WorldConstants.USER_LIMIT = (int)Integer.valueOf(FengYeDuan.ConfigValuesMap.get("?????????????????????"));
        WorldConstants.MAX_CHAR_VIEW = ServerProperties.getProperty("FengYeDuan.maxCharView", WorldConstants.MAX_CHAR_VIEW);
        WorldConstants.GMITEMS = ServerProperties.getProperty("FengYeDuan.gmitems", WorldConstants.GMITEMS);
        WorldConstants.CS_ENABLE = ServerProperties.getProperty("FengYeDuan.cashshop.enable", WorldConstants.CS_ENABLE);
    }
    
    static {
        WorldConstants.WORLD = WorldOption.?????????;
        WorldConstants.ADMIN_ONLY = true;
        WorldConstants.JZSD = false;
        WorldConstants.WUYANCHI = true;
        WorldConstants.LieDetector = false;
        WorldConstants.DropItem = true;
        WorldConstants.USER_LIMIT = 10000;
        WorldConstants.MAX_CHAR_VIEW = 20;
        WorldConstants.GMITEMS = false;
        WorldConstants.CS_ENABLE = true;
        WorldConstants.EXP_RATE = 1;
        WorldConstants.MESO_RATE = 1;
        WorldConstants.DROP_RATE = 1;
        WorldConstants.FLAG = 3;
        WorldConstants.CHANNEL_COUNT = 2;
        WorldConstants.WORLD_TIP = "????????????????????????????????????!";
        WorldConstants.SCROLL_MESSAGE = "";
        WorldConstants.AVAILABLE = true;
        recommendedmsg = "";
        loadSetting();
    }
    
    public enum WorldOption implements Option
    {
        ?????????(0), 
        ?????????(1), 
        ?????????(2), 
        ?????????(3), 
        ?????????(4), 
        ?????????(5), 
        ?????????(6), 
        ?????????(7), 
        ?????????(8), 
        ?????????(9), 
        ?????????(10), 
        ?????????(121);
        
        private final int world;
        
        private WorldOption(final int world) {
            this.world = world;
        }
        
        @Override
        public int getWorld() {
            return this.world;
        }
    }
    
    public enum TespiaWorldOption implements Option
    {
        ?????????("t0");
        
        private final int world;
        private final String worldName;
        
        private TespiaWorldOption(final String world) {
            this.world = Integer.parseInt(world.replaceAll("t", ""));
            this.worldName = world;
        }
        
        @Override
        public int getWorld() {
            return this.world;
        }
    }
    
    public interface Option
    {
        int getWorld();
        
        String name();
    }
}
