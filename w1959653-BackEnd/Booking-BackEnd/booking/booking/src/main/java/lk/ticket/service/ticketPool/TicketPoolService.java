package lk.ticket.service.ticketPool;

import lk.ticket.model.configuration.ConfigurationModule;

/**
 * This is the TicketPoolService interface. This stores all abstract methods of ticket pool
 *  <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */
public interface TicketPoolService {
    String addTicket(boolean add, String vendorName);
    String removeTicket(int purchaseTicketCount, boolean purchase, String customerName);
    boolean checkTicketAvailability();
    boolean checkPoolSize();
    int getAvailableTicketsCount();
    boolean resumeTicketPool(int releasedTicketCount, int queueSize);
    int getReleasedTicketCount();

    void setConfigurationModule(ConfigurationModule configurationModule, int eventID);
}
