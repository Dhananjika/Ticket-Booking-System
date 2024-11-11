package lk.ticket.util;

import lk.ticket.exception.ApplicationException;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static final Logger logger = Logger.getLogger(PropertyReader.class);

    /**
     *  This method is used to read the application.properties file
     *
     *  @in  application.properties file
     *  @Exception Exception
     *  @out Details in application.properties file
     * */
    public static Properties loadPropertyFile() {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("application.properties");
        try {
            properties.load(inputStream);
        } catch (Exception e) {
            ApplicationException applicationException = new ApplicationException("An Error Occurred while loading application.properties " + e.getMessage());
            logger.error(applicationException.getMessage());
            return null;
        }
       return properties;
    }

    /**
     *  This method is used to get the property value
     *
     *  @in  property Key
     *  @Exception Exception
     *  @out Value of property Key
     * */
    public static String getPropertyValue(String key){
        try {
            Properties properties = loadPropertyFile();
            String value = null;
            if (properties != null){
                value = properties.getProperty(key);
            }
            return value;
        } catch (Exception e) {
            ApplicationException applicationException = new ApplicationException("An Error Occurred while getting Property Value For " + key + " "  + e.getMessage());
            logger.error(applicationException.getMessage());
            return null;
        }
    }
}
