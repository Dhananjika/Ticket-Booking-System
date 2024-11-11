package lk.ticket.util;

import org.apache.log4j.Logger;

import java.sql.*;

public class ConnectionManager {
    private static final Logger logger = Logger.getLogger(ConnectionManager.class);

    /**
     *  This method is used to get the Connection
     *
     *  @Exception SQLException, ClassNotFoundException
     *  @out The database connection
     * */
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

            assert connectionURL != null;
            connection = DriverManager.getConnection(connectionURL, userName, password);
            connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("An Error Occurred while getting Connection " + e.getMessage());
            return null;
        }
        return connection;
    }

    /**
     *  This method is used to close the Connection
     *
     *  @Exception SQLException
     *  @out close the database Connection
     * */
    public static void close(Connection con) {
        try {
            if (con != null){
                con.close();
                logger.info("Connection close success");
            }else{
                logger.error("Connection is null");
            }
        } catch (SQLException e) {
            logger.error("An Error Occurred while closing Connection " + e.getMessage());
        }
    }

    /**
     *  This method is used to close the Prepared Statement
     *
     *  @Exception SQLException
     *  @out close the Prepared Statement
     * */
    public static void close(PreparedStatement ps) {
        try {
            if (ps != null){
                ps.close();
                logger.info("PreparedStatement close success");
            }else{
                logger.error("PreparedStatement is null");
            }
        } catch (SQLException e) {
            logger.error("An Error Occurred while closing PreparedStatement " + e.getMessage());
        }
    }

    /**
     *  This method is used to close the Result Set
     *
     *  @Exception SQLException
     *  @out close the Result Set
     * */
    public static void close(ResultSet rs) {
        try {
            if (rs != null){
                rs.close();
                logger.info("ResultSet close success");
            }else{
                logger.error("ResultSet is null");
            }
        } catch (SQLException e) {
            logger.error("An Error Occurred while closing ResultSet " + e.getMessage());
        }
    }

    /**
     *  This method is used to undo changes (RollBack)
     *
     *  @Exception SQLException
     *  @out undo changes (RollBack)
     * */
    public static void rollback(Connection con) {
        try {
            if (con != null){
                con.rollback();
                logger.info("RollBack success");
            }else{
                logger.error("Connection is null");
            }
        } catch (SQLException e) {
            logger.error("An Error Occurred while doing rollback " + e.getMessage());
        }
    }

    /**
     *  This method is used to commit changes
     *
     *  @Exception SQLException
     *  @out commit changes
     * */
    public static void commit(Connection con) {
        try {
            if (con != null){
                con.commit();
                logger.info("Commit success");
            }else{
                logger.error("Connection is null");
            }
        } catch (SQLException e) {
            logger.error("An Error Occurred while commiting " + e.getMessage());
        }
    }
}
