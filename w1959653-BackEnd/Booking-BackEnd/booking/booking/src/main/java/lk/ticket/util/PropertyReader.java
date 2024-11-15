package lk.ticket.util;

import lk.ticket.exception.ApplicationException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class is used to read property file
 *  <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */
public class PropertyReader {
    private static final Logger logger = Logger.getLogger(PropertyReader.class);

    /**
     *  This method is used to read the application.properties file
     *
     *  @in  application.properties file
     *  @Exception Exception
     *  @out Details in application.properties file
     * */
    public static Properties loadPropertyFile() throws ApplicationException {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("application.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ApplicationException("Unable to Read Application Property File");
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
    public static String getPropertyValue(String key) throws ApplicationException{
        try {
            Properties properties = loadPropertyFile();
            String value = null;
            if (properties == null){
                throw new ApplicationException("Unable to Read  Property For Key " + key);
            }
            value = properties.getProperty(key);
            return value;
        } catch (Exception e) {
            if (e instanceof ApplicationException) {
                throw (ApplicationException) e;
            } else {
                throw new ApplicationException("Unable to Read  Property For Key " + key);
            }
        }
    }
}
