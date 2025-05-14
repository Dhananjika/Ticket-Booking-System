package lk.ticket.repository.ticket;

import lk.ticket.model.ticket.TicketPoolModule;
import lk.ticket.util.ConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class TicketPoolRepository {
    Logger logger = Logger.getLogger(TicketPoolRepository.class);

    public void insertTicketPoolDetails(int releasedTicketCount, int queueSize, int eventID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementInsert = null;
        PreparedStatement preparedStatementUpdate = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();
            String query = "SELECT * FROM ticket_pool WHERE event_id = ?";
            String insert = "INSERT INTO ticket_pool (event_id,released_count,queue_size) VALUES(?,?,?)";
            String update = "UPDATE ticket_pool SET released_count = ? , queue_size = ? WHERE event_id = ?";
            if (connection != null) {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, eventID);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    preparedStatementUpdate = connection.prepareStatement(update);
                    preparedStatementUpdate.setInt(1, releasedTicketCount);
                    preparedStatementUpdate.setInt(2, queueSize);
                    preparedStatementUpdate.setInt(3, eventID);
                    preparedStatementUpdate.executeUpdate();
                    ConnectionManager.commit(connection);
                    logger.info("update ticket pool details table success");
                }else {
                    preparedStatementInsert = connection.prepareStatement(insert);
                    preparedStatementInsert.setInt(1, eventID);
                    preparedStatementInsert.setInt(2, releasedTicketCount);
                    preparedStatementInsert.setInt(3, queueSize);
                    preparedStatementInsert.executeUpdate();
                    ConnectionManager.commit(connection);
                    logger.info("insert ticket pool details table success");
                }
            }
        } catch (Exception e) {
            logger.error("Error while inserting ticket pool details. - "+ e.getMessage());
            throw new RuntimeException(e);
        }finally{
            ConnectionManager.close(connection);
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(preparedStatementInsert);
            ConnectionManager.close(preparedStatementUpdate);
            ConnectionManager.close(resultSet);
        }
    }

    public TicketPoolModule getTicketPoolDetails(int eventID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        TicketPoolModule ticketPoolModule = new TicketPoolModule();
        try {
            connection = ConnectionManager.getConnection();
            String query = "SELECT * FROM ticket_pool WHERE event_id = ?";
            if (connection != null) {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, eventID);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    ticketPoolModule.setReleasedTicketCount(resultSet.getInt("released_count"));
                    ticketPoolModule.setTicketQueueSize(resultSet.getInt("queue_size"));
                }
            }
        } catch (Exception e) {
            logger.error("Error while getting ticket pool details from database. - "+ e.getMessage());
            throw new RuntimeException(e);
        }finally{
            ConnectionManager.close(connection);
            ConnectionManager.close(preparedStatement);
            ConnectionManager.close(resultSet);
        }
        return ticketPoolModule;
    }
}
