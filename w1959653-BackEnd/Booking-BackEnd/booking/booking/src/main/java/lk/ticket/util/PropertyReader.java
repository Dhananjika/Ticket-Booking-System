package lk.ticket.util;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static final Logger logger = Logger.getLogger(PropertyReader.class);

    /* This method is used to read the application.properties file */
    public static Properties loadPropertyFile() {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("application.properties");
        try {
            properties.load(inputStream);
        } catch (Exception e) {
            logger.error("An Error Occurred while loading application.properties " + e.getMessage());
            e.printStackTrace();
            return null;
        }
       return properties;
    }

    /* This method is used to get the property value */
    public static String getPropertyValue(String key){
        try {
            Properties properties = loadPropertyFile();
            String value = null;
            if (properties != null){
                value = properties.getProperty(key);
            }
            return value;
        } catch (Exception e) {
            logger.error("An Error Occurred while getting Property Value For " + key + " "  + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
