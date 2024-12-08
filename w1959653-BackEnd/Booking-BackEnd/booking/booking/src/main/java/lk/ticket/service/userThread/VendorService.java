package lk.ticket.service.userThread;

import lk.ticket.model.systemControl.SystemControlModule;
import lk.ticket.repository.configuration.SystemControlRepository;
import lk.ticket.service.ticketPool.TicketPoolService;
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
    private final TicketPoolService ticketPoolService;
    private final SystemControlRepository systemControlRepository;
    private String returnMessage;
    private boolean addTickets;
    private final int id;

    public VendorService(TicketPoolService ticketPoolService, int id) {
        this.ticketPoolService = ticketPoolService;
        this.id = id;
        this.systemControlRepository = new SystemControlRepository();
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
        SystemControlModule systemControlModuleExist = systemControlRepository.getSystemConfiguration(id);
        logger.info(systemControlModuleExist);
        if ((systemControlModuleExist.getSystemStatus().equals("A"))){
            returnMessage = ticketPoolService.addTicket(addTickets,Thread.currentThread().getName());
        }else{
            returnMessage = "System is not started";
        }

    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setAddTickets(boolean addTickets) {
        this.addTickets = addTickets;
    }
}
