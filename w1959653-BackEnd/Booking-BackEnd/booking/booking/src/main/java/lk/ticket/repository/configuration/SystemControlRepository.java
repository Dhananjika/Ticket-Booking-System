package lk.ticket.repository.configuration;

import lk.ticket.model.systemControl.SystemControlModule;
import lk.ticket.util.ConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class SystemControlRepository {
    private static final Logger logger = Logger.getLogger(SystemControlRepository.class);

    public boolean configurationExists(int id) {
        logger.info("method called");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();
            String sql = "select * from configuration where event_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if(resultSet.getString("config_status").equals("I")) {
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred while checking if configuration exists " + e.getMessage());
        }finally {
            ConnectionManager.close(resultSet);
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
        return false;
    }

    public String addConfiguration(SystemControlModule systemControlModule, int id) {
        logger.info("method called");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        logger.info(systemControlModule);

        try {
            connection = ConnectionManager.getConnection();

            if (connection != null) {
                String sql = "UPDATE ticket.configuration SET system_status=?, config_status=?, stop_release_count=?, stop_pool_size=? WHERE event_id=?;";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, systemControlModule.getSystemStatus());
                preparedStatement.setString(2, systemControlModule.getConfigurationStatus());
                preparedStatement.setInt(3, systemControlModule.getSystemStoppedReleasedTicketCount());
                preparedStatement.setInt(4, systemControlModule.getSystemStoppedPoolSize());
                preparedStatement.setInt(5, id);

                int psCount = preparedStatement.executeUpdate();
                ConnectionManager.commit(connection);

                if(psCount > 0) {
                    logger.info("configuration table updated successfully");
                    return "Configuration table updated successfully";
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred while updating configuration table " + e.getMessage());
        }finally {
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
        return null;
    }

    public SystemControlModule getSystemConfiguration(int id) {
        logger.info("method called");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        SystemControlModule systemControlModule = new SystemControlModule();

        try {
            connection = ConnectionManager.getConnection();
            if (connection != null) {
                String sql = "select * from configuration where event_id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    systemControlModule.setSystemStatus(resultSet.getString("system_status"));
                    systemControlModule.setConfigurationStatus(resultSet.getString("config_status"));
                    systemControlModule.setSystemStoppedReleasedTicketCount(resultSet.getInt("stop_release_count"));
                    systemControlModule.setSystemStoppedPoolSize(resultSet.getInt("stop_pool_size"));
                }

            }
            logger.info(systemControlModule);
        } catch (Exception e) {
            logger.error("An error occurred while getting system configuration " + e.getMessage());
        }finally {
            ConnectionManager.close(resultSet);
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
        return systemControlModule;
    }
}
