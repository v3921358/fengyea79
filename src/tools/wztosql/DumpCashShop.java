package tools.wztosql;

import provider.MapleDataProviderFactory;
import java.io.File;
import java.util.Iterator;
import java.util.Collection;
import java.sql.SQLException;
import client.inventory.MapleInventoryType;
import java.util.List;
import provider.MapleDataTool;
import provider.MapleData;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import server.CashItemFactory;
import server.MapleItemInformationProvider;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import tools.FileoutputUtil;
import database.DBConPool;
import server.CashItemInfo.CashModInfo;
import provider.MapleDataProvider;

public class DumpCashShop
{
    private static final MapleDataProvider data;
    
    public static final CashModInfo getModInfo(final int sn) {
        CashModInfo ret = null;
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("SELECT * FROM cashshop_modified_items WHERE serial = ?")) {
            ps.setInt(1, sn);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret = new CashModInfo(sn, rs.getInt("discount_price"), rs.getInt("mark"), rs.getInt("showup") > 0, rs.getInt("itemid"), rs.getInt("priority"), rs.getInt("package") > 0, rs.getInt("period"), rs.getInt("gender"), rs.getInt("count"), rs.getInt("meso"), rs.getInt("unk_1"), rs.getInt("unk_2"), rs.getInt("unk_3"), rs.getInt("extra_flags"), rs.getInt("mod"));
                }
            }
        }
        catch (Exception ex) {
            FileoutputUtil.outError("logs/資料庫異常.txt", (Throwable)ex);
        }
        return ret;
    }
    
    public static void main(final String[] args) {
        MapleItemInformationProvider.getInstance().load();
        final CashModInfo m = getModInfo(20000393);
        CashItemFactory.getInstance().initialize();
        final Collection<CashModInfo> list = CashItemFactory.getInstance().getAllModInfo();
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            final List<Integer> itemids = new ArrayList<Integer>();
            final List<Integer> qq = new ArrayList<Integer>();
            final Map<Integer, Map<String, Integer>> itemConmes = new HashMap<Integer, Map<String, Integer>>();
            for (final MapleData field : DumpCashShop.data.getData("Commodity.img").getChildren()) {
                final Map<String, Integer> itemConme = new HashMap<String, Integer>();
                final int itemId = MapleDataTool.getIntConvert("ItemId", field, 0);
                final int sn = MapleDataTool.getIntConvert("SN", field, 0);
                final int count = MapleDataTool.getIntConvert("Count", field, 0);
                final int price = MapleDataTool.getIntConvert("Price", field, 0);
                final int priority = MapleDataTool.getIntConvert("Priority", field, 0);
                final int period = MapleDataTool.getIntConvert("Period", field, 0);
                final int gender = MapleDataTool.getIntConvert("Gender", field, -1);
                final int meso = MapleDataTool.getIntConvert("Meso", field, 0);
                if (price == 0) {
                    continue;
                }
                if ((sn / 10000000 == 2 || sn / 10000000 == 3 || sn / 10000000 == 5 || sn / 10000000 == 6 || sn / 10000000 == 7) && itemConmes.containsKey(Integer.valueOf(itemId)) && (int)Integer.valueOf(((Map<String, Integer>)itemConmes.get(Integer.valueOf(itemId))).get("price")) > price) {
                    continue;
                }
                itemConme.put("itemId", Integer.valueOf(itemId));
                itemConme.put("sn", Integer.valueOf(sn));
                itemConme.put("count", Integer.valueOf(count));
                itemConme.put("price", Integer.valueOf(price));
                itemConme.put("priority", Integer.valueOf(priority));
                itemConme.put("period", Integer.valueOf(period));
                itemConme.put("gender", Integer.valueOf(gender));
                itemConme.put("meso", Integer.valueOf(meso));
                itemConmes.put(Integer.valueOf(itemId), itemConme);
            }
            final Map<Integer, List<String>> dics = new HashMap<Integer, List<String>>();
            for (final Map<String, Integer> field2 : itemConmes.values()) {
            }
        }
        catch (SQLException e) {
            FileoutputUtil.outError("logs/資料庫異常.txt", (Throwable)e);
        }
    }
    
    static {
        data = MapleDataProviderFactory.getDataProvider(new File(((System.getProperty("wzpath") != null) ? System.getProperty("wzpath") : "") + "wz/Etc.wz"));
    }
}
