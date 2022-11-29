package provider;

import server.ServerProperties;
import provider.WzXML.XMLWZFile;
import java.io.File;

public class MapleDataProviderFactory
{
    private static final String wzPath = ServerProperties.getProperty("server.wzpath");
    
    private static MapleDataProvider getWZ(final Object in) {
        return getWZ(in, false);
    }
    
    private static MapleDataProvider getWZ(final Object in, final boolean provideImages) {
        if (in instanceof File) {
            final File fileIn = (File)in;
            return new XMLWZFile(fileIn);
        }
        throw new IllegalArgumentException("Can't create data provider for input " + in);
    }
    
    public static MapleDataProvider getDataProvider(final File in) {
        return getWZ(in);
    }
    
    public static MapleDataProvider getDataProvider(final String path) {
        return getWZ(fileInWZPath(path));
    }
    
    public static File fileInWZPath(final String filename) {
        return new File(wzPath, filename);
    }

}
