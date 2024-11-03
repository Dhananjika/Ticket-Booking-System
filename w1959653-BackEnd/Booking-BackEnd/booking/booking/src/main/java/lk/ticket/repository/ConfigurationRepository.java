package lk.ticket.repository;

import lk.ticket.model.Configuration;
import lk.ticket.util.ConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class ConfigurationRepository {
    private static final Logger logger = Logger.getLogger(ConfigurationRepository.class);

    /**
     *  This method is used to submit the configuration details
     *
     *  @in  totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity
     *  @throws Exception
     *  @out resultMessage
     * */

    public String submitConfiguration(Configuration configuration, String user, String date){
        logger.info(configuration + ", User - " + user + ", created date - " + date);
        logger.info("Inside the SubmitConfiguration method in ConfigurationRepository");
        Connection con = null;
        PreparedStatement ps = null;
        String result = null;
        try {
            con = ConnectionManager.getConnection();
            String query = "INSERT INTO configuration (total_ticket, maximum_ticket, ticket_release_rate, customer_retrieval_rate, created_by, created_date) VALUES (?, ?, ?, ?, ?, ?);";

            ps = con.prepareStatement(query);
            ps.setInt(1, configuration.getTotalTickets());
            ps.setInt(2, configuration.getMaxTicketCapacity());
            ps.setInt(3, configuration.getTicketReleaseRate());
            ps.setInt(4, configuration.getCustomerRetrievalRate());
            ps.setString(5, user);
            ps.setString(6, date);

            int psCount = ps.executeUpdate();
            ConnectionManager.commit(con);
            result = (psCount > 0) ? "Successful" : "Unsuccessful";
            logger.info("Insert Data into configuration table " + result);
        }catch (SQLException e) {
            ConnectionManager.rollback(con);
            logger.error("An SQLException occurred while inserting data into configuration table" + e.getMessage());
            e.printStackTrace();
            return "Unsuccessful";
        }catch (Exception e) {
            logger.error("An Exception occurred while inserting data into configuration table" + e.getMessage());
            e.printStackTrace();
            return "Unsuccessful";
        }finally {
            ConnectionManager.close(con);
            ConnectionManager.close(ps);
        }
        return result;
    }
}
