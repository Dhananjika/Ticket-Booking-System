package lk.ticket.service.userThread;

import lk.ticket.model.systemControl.SystemControlModule;
import lk.ticket.repository.systemControl.SystemControlRepository;
import lk.ticket.service.ticketPool.TicketPoolService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * CustomerService class implemented by Runnable interface
 * <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */
public class CustomerService implements Runnable{
    private static final Logger logger = Logger.getLogger(CustomerService.class);
    private final TicketPoolService ticketPoolService;
    private final SystemControlRepository systemControlRepository;
    private String returnMessage;
    private boolean purchaseTicket;
    private int ticketCount;
    private final int id;

    public CustomerService(TicketPoolService ticketPoolService, int id) {
        this.ticketPoolService = ticketPoolService;
        this.id = id;
        this.systemControlRepository = new SystemControlRepository();
    }

    @Autowired


    /**
     *  This method is used to separately execute thread.
     *  This will continue until all the tickets are purchased.
     *
     *  @in ticketCount, thread name, purchaseTicket boolean value
     *  @out remove Tickets from Ticket Pool
     * */
    @Override
    public void run() {
        SystemControlModule systemControlModuleExist = systemControlRepository.getSystemConfiguration(id);
        logger.info(systemControlModuleExist);
        if (systemControlModuleExist.getSystemStatus().equals("A") || systemControlModuleExist.getSystemStatus().equals("R")) {
            returnMessage = ticketPoolService.removeTicket(ticketCount, purchaseTicket,Thread.currentThread().getName());
        }else{
            returnMessage = "System is not started";
        }
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
