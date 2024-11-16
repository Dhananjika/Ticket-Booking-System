package lk.ticket.service.ticketPool;

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
}
