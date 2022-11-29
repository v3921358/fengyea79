package database;

import server.ServerProperties;
import com.alibaba.druid.pool.DruidDataSource;

public class DBConPool
{
    private static DruidDataSource dataSource;
    public static final int RETURN_GENERATED_KEYS = 1;
    public static String dbUser;
    public static String dbPass;
    public static String dbHost;
    public static String dbName;
    public static int dbport;
    
    public static void InitDB() {
        DBConPool.dbName = ServerProperties.getProperty("FengYeDuan.db.name", DBConPool.dbName);
        DBConPool.dbHost = ServerProperties.getProperty("FengYeDuan.db.host", DBConPool.dbHost);
        DBConPool.dbport = ServerProperties.getProperty("FengYeDuan.db.port", DBConPool.dbport);
        DBConPool.dbUser = ServerProperties.getProperty("FengYeDuan.db.user", DBConPool.dbUser);
        DBConPool.dbPass = ServerProperties.getProperty("FengYeDuan.db.password", DBConPool.dbPass);
    }
    
    public static DBConPool getInstance() {
        return InstanceHolder.instance;
    }
    
    private DBConPool() {
    }
    
    public DruidDataSource getDataSource() {
        if (DBConPool.dataSource == null) {
            this.InitDBConPool();
        }
        return DBConPool.dataSource;
    }
    
    private void InitDBConPool() {
        (DBConPool.dataSource = new DruidDataSource()).setName("mysql_pool");
        DBConPool.dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        DBConPool.dataSource.setUrl("jdbc:mysql://127.0.0.1:" + DBConPool.dbport + "/" + DBConPool.dbName + "?useUnicode=true&characterEncoding=UTF8");
        DBConPool.dataSource.setUsername(DBConPool.dbUser);
        DBConPool.dataSource.setPassword(DBConPool.dbPass);
        DBConPool.dataSource.setInitialSize(300);
        DBConPool.dataSource.setMinIdle(500);
        DBConPool.dataSource.setMaxActive(3000);
        DBConPool.dataSource.setTimeBetweenEvictionRunsMillis(60000L);
        DBConPool.dataSource.setMinEvictableIdleTimeMillis(300000L);
        DBConPool.dataSource.setValidationQuery("SELECT 'x'");
        DBConPool.dataSource.setTestOnBorrow(false);
        DBConPool.dataSource.setTestOnReturn(false);
        DBConPool.dataSource.setTestWhileIdle(true);
        DBConPool.dataSource.setMaxWait(60000L);
        DBConPool.dataSource.setUseUnfairLock(true);
    }
    
    static {
        DBConPool.dataSource = null;
        DBConPool.dbUser = "";
        DBConPool.dbPass = "root";
        DBConPool.dbHost = "localhost";
        DBConPool.dbName = "maplestory";
        DBConPool.dbport = 3306;
        InitDB();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("[数据库信息] 找不到JDBC驱动.");
            System.exit(0);
        }
    }
    
    private static class InstanceHolder
    {
        public static final DBConPool instance;
        
        static {
            instance = new DBConPool();
        }
    }
}
