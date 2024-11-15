package lk.ticket.service.userThread;

import lk.ticket.model.UserModule;
import lk.ticket.service.ticketPool.TicketPoolServiceImp;
import org.apache.log4j.Logger;

public class VendorService implements Runnable {
    private static final Logger logger = Logger.getLogger(VendorService.class);
    private final TicketPoolServiceImp ticketPoolServiceImp;
    private String returnMessage;
    private boolean addTickets;

    public VendorService(TicketPoolServiceImp ticketPoolServiceImp) {
        this.ticketPoolServiceImp = ticketPoolServiceImp;
    }

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
