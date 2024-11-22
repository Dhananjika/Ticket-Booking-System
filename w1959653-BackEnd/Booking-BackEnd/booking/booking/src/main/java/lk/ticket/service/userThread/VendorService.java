package lk.ticket.service.userThread;

import lk.ticket.model.systemControl.SystemControlModule;
import lk.ticket.repository.configuration.SystemControlRepository;
import lk.ticket.service.ticketPool.TicketPoolServiceImp;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
    private final SystemControlRepository systemControlRepository;
    private String returnMessage;
    private boolean addTickets;
    private final int id;

    public VendorService(TicketPoolServiceImp ticketPoolServiceImp, int id) {
        this.ticketPoolServiceImp = ticketPoolServiceImp;
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
            returnMessage = ticketPoolServiceImp.addTicket(addTickets,Thread.currentThread().getName());
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
