package lk.ticket.service.userThread;

import lk.ticket.model.UserModule;
import lk.ticket.service.ticketPool.TicketPoolServiceImp;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements Runnable{
    private static final Logger logger = Logger.getLogger(CustomerService.class);
    private final TicketPoolServiceImp ticketPoolServiceImp;
    private String returnMessage;
    private boolean purchaseTicket;
    private int ticketCount;

    @Autowired
    private UserModule userModule;

    public CustomerService(TicketPoolServiceImp ticketPoolServiceImp) {
        this.ticketPoolServiceImp = ticketPoolServiceImp;
    }

    @Override
    public void run() {
        returnMessage = ticketPoolServiceImp.removeTicket(ticketCount, purchaseTicket);
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public boolean isPurchaseTicket() {
        return purchaseTicket;
    }

    public void setPurchaseTicket(boolean purchaseTicket) {
        this.purchaseTicket = purchaseTicket;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
}
