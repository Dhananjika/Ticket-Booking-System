package lk.ticket.service.userThread;

import lk.ticket.service.ticketPool.TicketPoolServiceImp;
import org.apache.log4j.Logger;

/**
 * CustomerService class implemented by Runnable interface
 * <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */
public class CustomerService implements Runnable{
    private static final Logger logger = Logger.getLogger(CustomerService.class);
    private final TicketPoolServiceImp ticketPoolServiceImp;
    private String returnMessage;
    private boolean purchaseTicket;
    private int ticketCount;

    public CustomerService(TicketPoolServiceImp ticketPoolServiceImp) {
        this.ticketPoolServiceImp = ticketPoolServiceImp;
    }

    /**
     *  This method is used to separately execute thread.
     *  This will continue until all the tickets are purchased.
     *
     *  @in ticketCount, thread name, purchaseTicket boolean value
     *  @out remove Tickets from Ticket Pool
     * */
    @Override
    public void run() {
        returnMessage = ticketPoolServiceImp.removeTicket(ticketCount, purchaseTicket,Thread.currentThread().getName());
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setPurchaseTicket(boolean purchaseTicket) {
        this.purchaseTicket = purchaseTicket;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
}
