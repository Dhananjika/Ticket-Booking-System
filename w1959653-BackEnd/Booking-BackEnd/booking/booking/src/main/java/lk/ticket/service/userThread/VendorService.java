package lk.ticket.service.userThread;

import lk.ticket.model.UserModule;
import lk.ticket.service.ticketPool.TicketPoolServiceImp;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorService implements Runnable {
    private static final Logger logger = Logger.getLogger(VendorService.class);
    private final TicketPoolServiceImp ticketPoolServiceImp;
    private String returnMessage;
    private boolean addTickets;

    @Autowired
    private UserModule userModule;

    @Autowired
    public VendorService(TicketPoolServiceImp ticketPoolServiceImp) {
        this.ticketPoolServiceImp = ticketPoolServiceImp;
    }

    @Override
    public void run() {
        returnMessage = ticketPoolServiceImp.addTicket(addTickets);
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public boolean isAddTickets() {
        return addTickets;
    }

    public void setAddTickets(boolean addTickets) {
        this.addTickets = addTickets;
    }
}
