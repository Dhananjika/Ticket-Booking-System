package lk.ticket.service.userThread;

import lk.ticket.service.ticketPool.TicketPoolServiceImp;
import org.apache.log4j.Logger;

/**
 * VendorService class implemented by Runnable interface
 * <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */
public class VendorService implements Runnable {
    private static final Logger logger = Logger.getLogger(VendorService.class);
    private final TicketPoolServiceImp ticketPoolServiceImp;
    private String returnMessage;
    private boolean addTickets;

    public VendorService(TicketPoolServiceImp ticketPoolServiceImp) {
        this.ticketPoolServiceImp = ticketPoolServiceImp;
    }

    /**
     *  This method is used to separately execute thread.
     *  This will continue until all the tickets are released.
     *
     *  @in Thread name, addTickets boolean value
     *  @out add tickets to ticket pool
     * */
    @Override
    public void run() {
        returnMessage = ticketPoolServiceImp.addTicket(addTickets,Thread.currentThread().getName());
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setAddTickets(boolean addTickets) {
        this.addTickets = addTickets;
    }
}
