package lk.ticket.service.userThread;

import lk.ticket.model.UserModule;
import lk.ticket.service.ticketPool.TicketPoolServiceImp;
import org.apache.log4j.Logger;

public class CustomerService implements Runnable{
    private static final Logger logger = Logger.getLogger(CustomerService.class);
    private final TicketPoolServiceImp ticketPoolServiceImp;
    private String returnMessage;
    private boolean purchaseTicket;
    private int ticketCount;

    public CustomerService(TicketPoolServiceImp ticketPoolServiceImp) {
        this.ticketPoolServiceImp = ticketPoolServiceImp;
    }

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
