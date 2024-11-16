package lk.ticket.service.ticketPool;

import lk.ticket.model.configuration.ConfigurationModule;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This is the Ticket Pool Service Class. Here handel all the business logics of the ticket Pool.
 * This is inherited by TicketPoolService interface.
 *  <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */
@Service
public class TicketPoolServiceImp implements TicketPoolService {
    private static final Logger logger = Logger.getLogger(TicketPoolServiceImp.class);
    private final ConcurrentLinkedQueue<Integer> ticketQueue = new ConcurrentLinkedQueue<>();
    private ConfigurationModule configurationModule;
    private int poolSize;
    private int releasedTicketCount;

    /**
     *  This method is used to set configuration module and pool size.
     *  synchronized block used.
     *
     *  @in  ConfigurationModule
     *  @out configurationModule, pool size
     * */
    public void setConfigurationModule(ConfigurationModule configurationModule) {
        synchronized (this) {
            if (this.configurationModule == null) {
                this.configurationModule = configurationModule;
                this.poolSize = configurationModule.getMaxTicketCapacity();
            }
        }
    }

    /**
     *  This method is used to add tickets to the pool.
     *  synchronized block used.
     *  Only one vendor can add tickets at one time.
     *
     *  @in  ticketReleaseRate
     *  @Exception InterruptedException
     *  @out ticketQueue
     * */
    @Override
    public String addTicket(boolean add, String vendorName){
        synchronized (this){
            String returnMessage = null;
            try{
                int ticketReleaseRate = configurationModule.getTicketReleaseRate();
                if(checkTicketAvailability()){
                    return "All Tickets Are Released. No More Tickets Available.";
                }
                if (checkPoolSize()) {
                    logger.info( vendorName + " try to add tickets : Ticket pool is full. Wait until customer purchase tickets.");
                    return "Ticket Addition On Hold. Please Wait Until Customers Purchase Tickets To Free Up Space.";
                }

                ticketReleaseRate = Math.min(poolSize - ticketQueue.size(), ticketReleaseRate);
                ticketReleaseRate = Math.min(ticketReleaseRate, configurationModule.getTotalTickets() - releasedTicketCount);

                if(ticketReleaseRate < configurationModule.getTicketReleaseRate() && !add){
                   return "Only " + ticketReleaseRate + " Tickets Can Be Added Right Now. Would You Like To Add Them?";
                }

                for (int i = 0; i < ticketReleaseRate; i++) {
                    ticketQueue.add(releasedTicketCount++);
                }
                logger.info(vendorName + " added " + ticketReleaseRate +  " tickets. Available tickets in the pool: " + ticketQueue.size());
                returnMessage = ticketReleaseRate + " Tickets Added.";
                notifyAll();
            }catch (Exception e){
                logger.error("An error occurred while adding tickets to pool : " + e.getMessage());
                return "Add Tickets Failed!";
            }
            return returnMessage;
        }
    }


    /**
     *  This method is used to remove tickets from the pool.
     *  synchronized block used.
     *  Only one customer can purchase tickets at one time.
     *
     *  @in  ticketReleaseRate
     *  @Exception InterruptedException
     *  @out ticketQueue
     * */
    @Override
    public String removeTicket(int purchaseTicketCount, boolean purchase, String customerName){
        synchronized (this){
            String returnMessage = null;
            try {
                if (checkTicketAvailability() && ticketQueue.isEmpty()) {
                   return "Tickets Sold Out.";
                }else if(!checkTicketAvailability() && ticketQueue.isEmpty()){
                   logger.info(customerName + " try to purchase tickets : Ticket pool is empty. Wait until vendor release tickets.");
                   return "Tickets Are Currently Unavailable. Please Wait Until More Tickets Become Available.";
                }

                if((purchaseTicketCount > configurationModule.getCustomerRetrievalRate() && !purchase)
                        || purchaseTicketCount > ticketQueue.size()){
                    int allowedPurchaseCount = Math.min(ticketQueue.size(), configurationModule.getCustomerRetrievalRate());
                    return "You Can Purchase Up To " + allowedPurchaseCount + " Tickets At This Time. Would You Like To Proceed With Your Purchase?";
                }

                purchaseTicketCount = Math.min(ticketQueue.size(), configurationModule.getCustomerRetrievalRate());
                for (int i = 0; i < purchaseTicketCount; i++) {
                    if (!ticketQueue.isEmpty()) {
                        ticketQueue.remove();
                    }
                }
                logger.info(customerName + " purchased " +  purchaseTicketCount + " tickets. Remaining tickets in the pool: " + ticketQueue.size());
                returnMessage = purchaseTicketCount + " Tickets Purchased.";
                notifyAll();
            }catch (Exception e){
                logger.error(" An error occurred while removing tickets to pool : " + e.getMessage());
                return "Remove Tickets Failed!";
            }
            return returnMessage;
        }
    }

    /**
     *  This method is used to check the tickets are available or not.
     *
     *  @in  released Ticket Count, total ticket count, ticketQueue
     *  @out ticket available or not
     * */
    @Override
    public synchronized boolean checkTicketAvailability() {
        return releasedTicketCount == configurationModule.getTotalTickets();
    }

    /**
     *  This method is used to check the pool is full or not.
     *
     *  @in  poolSize, ticketQueue
     *  @out pool is full or not
     * */
    public synchronized boolean checkPoolSize(){
        return poolSize == ticketQueue.size();
    }

    /**
     *  This method is used to get the number of tickets in the system.
     *
     *  @out number of tickets in the pool
     * */
    public synchronized int getAvailableTicketsCount(){
        return ticketQueue.size();
    }

    public synchronized int getReleasedTicketCount() {
        return releasedTicketCount;
    }
}
