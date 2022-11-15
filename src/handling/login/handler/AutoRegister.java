package handling.login.handler;

import handling.login.LoginServer;
import client.LoginCrypto;
import java.util.Calendar;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import tools.FileoutputUtil;
import database.DBConPool;

public class AutoRegister
{
    private static final int ACCOUNTS_PER_MAC = 5;
    public static boolean autoRegister;
    public static boolean success;
    public static boolean mac;
    
    public static boolean getAccountExists(final String login) {
        boolean accountExists = false;
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            final PreparedStatement ps = con.prepareStatement("SELECT name FROM accounts WHERE name = ?");
            ps.setString(1, login);
            final ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                accountExists = true;
            }
        }
        catch (SQLException ex) {
            System.err.println("[getAccountExists]" + ex);
            FileoutputUtil.outError("logs/資料庫異常.txt", (Throwable)ex);
        }
        return accountExists;
    }
    
    public static void createAccount(final String login, final String pwd, final String eip) {
        final String sockAddr = eip;
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            ResultSet rs;
            try (final PreparedStatement ipc = con.prepareStatement("SELECT Macs FROM accounts WHERE macs = ?")) {
                ipc.setString(1, "00-00-00-00-00-00");
                rs = ipc.executeQuery();
                Label_0461: {
                    if (rs.first()) {
                        if (!rs.last()) {
                            break Label_0461;
                        }
                    }
                }
            }
            rs.close();
        }
        catch (SQLException ex2) {
            System.err.println("[createAccount]" + ex2);
            FileoutputUtil.outError("logs/資料庫異常.txt", (Throwable)ex2);
        }
    }
    
    static {
        AutoRegister.autoRegister = LoginServer.getAutoReg();
        AutoRegister.success = false;
        AutoRegister.mac = true;
    }
}
