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
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();
            if (connection != null) {
                String sql = "select * from event";
                preparedStatement = connection.prepareStatement(sql);
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
                    events.add(eventModule);
                }
            }
        }catch (Exception e){
            logger.error("An error occurred while retrieving events "+ e.getMessage());
        }finally {
            ConnectionManager.close(resultSet);
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(connection);
        }
        return events;
    }
}
