package lk.ticket.repository.event;

import lk.ticket.model.event.EventModule;
import lk.ticket.util.ConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventRepository {
    private static final Logger logger = Logger.getLogger(EventRepository.class);

    public List<EventModule> getEvents() {
        List<EventModule> events = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        try {
            connection = ConnectionManager.getConnection();
            if (connection != null) {
                String sql = "select * from event";
                String activeEventSql = "SELECT system_status FROM ticket.configuration WHERE event_id=?;";
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    preparedStatement2 = connection.prepareStatement(activeEventSql);
                    preparedStatement2.setInt(1, resultSet.getInt("event_id"));
                    resultSet2 = preparedStatement2.executeQuery();

                    while (resultSet2.next()) {
                        if (resultSet2.getString("system_status").equals("A") || resultSet2.getString("system_status").equals("R")) {
                            EventModule eventModule = new EventModule();
                            eventModule.setEventId(resultSet.getInt("event_id"));
                            eventModule.setEventName(resultSet.getString("event_name"));
                            eventModule.setEventType(resultSet.getString("event_type"));
                            eventModule.setEventLocation(resultSet.getString("location"));
                            eventModule.setEventTime(resultSet.getString("event_time"));
                            eventModule.setEventDate(resultSet.getString("event_date"));
                            eventModule.setEventNormalTicketPrice(resultSet.getInt("event_normal_ticket_price"));
                            eventModule.setEventVIPTicketPrice(resultSet.getInt("event_vip_ticket_price"));
                            eventModule.setEventImage("assets/event/" + resultSet.getString("event_image") + ".jpg");
                            eventModule.setSystemStatus(resultSet2.getString("system_status"));
                            logger.info(eventModule);
                            events.add(eventModule);
                        }
                    }
                }
            }
        }catch (Exception e){
            logger.error("An error occurred while retrieving events "+ e.getMessage());
        }finally {
            ConnectionManager.close(resultSet2);
            ConnectionManager.close(resultSet);
            ConnectionManager.close(preparedStatement2);
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
        return events;
    }

    public List<EventModule> getVendorEvents(String userName) {
        List<EventModule> eventsForVendor = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();
            if (connection != null) {
                String sql = "select * from event inner join vendor on event.event_id = vendor.event_id inner join register on vendor.vendor_id = register.vendor_id inner join configuration on event.event_id = configuration.event_id where username = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, userName);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    EventModule eventModule = new EventModule();
                    eventModule.setEventId(resultSet.getInt("event_id"));
                    eventModule.setEventName(resultSet.getString("event_name"));
                    eventModule.setEventType(resultSet.getString("event_type"));
                    eventModule.setEventLocation(resultSet.getString("location"));
                    eventModule.setEventTime(resultSet.getString("event_time"));
                    eventModule.setEventDate(resultSet.getString("event_date"));
                    eventModule.setEventNormalTicketPrice(resultSet.getInt("event_normal_ticket_price"));
                    eventModule.setEventVIPTicketPrice(resultSet.getInt("event_vip_ticket_price"));
                    eventModule.setEventImage("assets/event/" + resultSet.getString("event_image") + ".jpg");
                    eventModule.setSystemStatus(resultSet.getString("system_status"));
                    eventModule.setConfigurationStatus(resultSet.getString("config_status"));
                    logger.info(eventModule);
                    eventsForVendor.add(eventModule);
                }
            }
        }catch (Exception e){
            logger.error("An error occurred while retrieving events for vendor "+ e.getMessage());
        }finally {
            ConnectionManager.close(resultSet);
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
        return eventsForVendor;
    }
}
