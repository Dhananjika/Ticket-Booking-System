package lk.ticket.repository.event;

import lk.ticket.model.event.EventModule;
import lk.ticket.model.event.VendorModule;
import lk.ticket.util.ConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EventRepository {
    private static final Logger logger = Logger.getLogger(EventRepository.class);

    public String addEvent(EventModule event){
        logger.info("method called.");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getConnection();
            String sql = "INSERT INTO ticket.event (event_id, event_name, event_type, location, event_date, event_time) VALUES(?, ?, ?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, event.getEventId());
            preparedStatement.setString(2, event.getEventName());
            preparedStatement.setString(3, event.getEventType());
            preparedStatement.setString(4, event.getEventLocation());
            preparedStatement.setString(5, event.getEventDate());
            preparedStatement.setString(6, event.getEventTime());
            int psCount = preparedStatement.executeUpdate();
            ConnectionManager.commit(connection);

            if(psCount == 1){
                addConfigurationDetails(event.getEventId());
                return "Event added successfully";
            }
        }catch (SQLException se) {
            ConnectionManager.rollback(connection);
            logger.error("An error occurred while trying to insert the event details to event table" + se.getMessage());
            return "Event adding failed";
        }catch (Exception e) {
            logger.error("An error occurred while trying to insert the event details to event table" + e.getMessage());
            return "Event adding failed";
        }finally {
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
        return "Event adding failed";
    }

    public String addVendors(List<VendorModule> vendors){
        logger.info("method called.");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getConnection();
            String sql = "INSERT INTO ticket.vendor (vendor_id, vendor_name, email, event_id) VALUES(?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(sql);

            for(VendorModule vendor : vendors){
                preparedStatement.setString(1, vendor.getVendorID());
                preparedStatement.setString(2, vendor.getVendorName());
                preparedStatement.setString(3, vendor.getVendorEmail());
                preparedStatement.setInt(4, vendor.getEventID());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            ConnectionManager.commit(connection);
            return "Vendors added successfully";
        }catch (SQLException se) {
            ConnectionManager.rollback(connection);
            logger.error("An error occurred while trying to insert the vendor details to vendor table" + se.getMessage());
            return "Vendor adding failed";
        }catch (Exception e) {
            logger.error("An error occurred while trying to insert the vendor details to vendor table" + e.getMessage());
            return "Vendor adding failed";
        }finally {
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
    }

    public void addConfigurationDetails(int id){
        logger.info("method called.");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getConnection();

            if(connection != null){
                String sql = "INSERT INTO ticket.configuration (event_id) VALUES(?);";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                int psCount = preparedStatement.executeUpdate();
                ConnectionManager.commit(connection);

                if(psCount == 1){
                    logger.info("Configuration details added successfully");
                }
            }
        }catch (SQLException se) {
            ConnectionManager.rollback(connection);
            logger.error("An error occurred while trying to insert the configuration details to configuration table" + se.getMessage());
        }catch (Exception e) {
            logger.error("An error occurred while trying to insert the configuration details to configuration table" + e.getMessage());
        }finally {
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
    }
}

