package lk.ticket.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionManager {
    private static final Logger logger = Logger.getLogger(ConnectionManager.class);

    /* This method is used to get the Connection */
    public static Connection getConnection() {
        Connection connection = null;
        try{
            String connectionURL = PropertyReader.getPropertyValue("spring.datasource.url");
            connectionURL = (connectionURL != null)? connectionURL.trim() : connectionURL;

            String userName = PropertyReader.getPropertyValue("spring.datasource.username");
            userName = (userName != null)? userName.trim(): userName;

            String password = PropertyReader.getPropertyValue("spring.datasource.password");

            String driverClass = PropertyReader.getPropertyValue("spring.datasource.driver-class-name");
            driverClass = (driverClass != null)? driverClass.trim() : driverClass;
            Class.forName(driverClass);

            connection = DriverManager.getConnection(connectionURL, userName, password);
            connection.setAutoCommit(false);
        } catch (Exception e) {
            logger.error("An Error Occurred while getting Connection " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return connection;
    }

    /* This method is used to close the Connection */
    public static void close(Connection con) {
        try {
            if (con != null){
                con.close();
                logger.info("Connection close success");
            }else{
                logger.error("Connection is null");
            }
        } catch (Exception e) {
            logger.error("An Error Occurred while closing Connection " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* This method is used to close the PreparedStatement */
    public static void close(PreparedStatement ps) {
        try {
            if (ps != null){
                ps.close();
                logger.info("PreparedStatement close success");
            }else{
                logger.error("PreparedStatement is null");
            }
        } catch (Exception e) {
            logger.error("An Error Occurred while closing PreparedStatement " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* This method is used to close the ResultSet */
    public static void close(ResultSet rs) {
        try {
            if (rs != null){
                rs.close();
                logger.info("ResultSet close success");
            }else{
                logger.error("ResultSet is null");
            }
        } catch (Exception e) {
            logger.error("An Error Occurred while closing ResultSet " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* This method is used to undo changes (RollBack) */
    public static void rollback(Connection con) {
        try {
            if (con != null){
                con.rollback();
                logger.info("RollBack success");
            }else{
                logger.error("Connection is null");
            }
        } catch (Exception e) {
            logger.error("An Error Occurred while doing rollback " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* This method is used to commit changes */
    public static void commit(Connection con) {
        try {
            if (con != null){
                con.commit();
                logger.info("Commit success");
            }else{
                logger.error("Connection is null");
            }
        } catch (Exception e) {
            logger.error("An Error Occurred while commiting " + e.getMessage());
            e.printStackTrace();
        }
    }
}
