package lk.ticket.service.ticketPool;

import lk.ticket.model.ConfigurationModule;
import lk.ticket.model.UserModule;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TicketPoolServiceImp implements TicketPool{
    private static final Logger logger = Logger.getLogger(TicketPoolServiceImp.class);
    private final ConcurrentLinkedQueue<Integer> ticketQueue = new ConcurrentLinkedQueue<>();
    private final ConfigurationModule configurationModule;
    private final int poolSize;
    private int releasedTicketCount;

    @Autowired
    UserModule userModule;

    public TicketPoolServiceImp(ConfigurationModule configurationModule) {
        this.configurationModule = configurationModule;
        this.poolSize = configurationModule.getMaxTicketCapacity();
        logger.info(configurationModule);
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
    public String addTicket(boolean add){
        synchronized (this){
            String returnMessage = null;
            try{
                int ticketReleaseRate = configurationModule.getTicketReleaseRate();
                if(checkTicketAvailability()){
                    return "All Tickets Are Released. No More Tickets Available.";
                }
                while (checkPoolSize()) {
                    returnMessage = "Ticket Addition On Hold. Please Wait Until Customers Purchase Tickets To Free Up Space.";
                    logger.info(userModule.getUsername() + " try to add tickets : Ticket pool is full. Wait until customer purchase tickets.");
                    wait();
                }
                ticketReleaseRate = Math.min(poolSize - ticketQueue.size(), ticketReleaseRate);
                ticketReleaseRate = Math.min(ticketReleaseRate, configurationModule.getTotalTickets() - releasedTicketCount);

                if(ticketReleaseRate < configurationModule.getTicketReleaseRate() && !add){
                   return "Only " + ticketReleaseRate + " Tickets Can Be Added Right Now. Would You Like To Add Them?";
                }

                for (int i = 0; i < ticketReleaseRate; i++) {
                    ticketQueue.add(releasedTicketCount++);
                }
                logger.info(userModule.getUsername() + " added " + ticketReleaseRate +  " tickets. Available tickets in the pool: " + ticketQueue.size());
                returnMessage = ticketReleaseRate + " Tickets Added.";
                notifyAll();
            }catch (InterruptedException e){
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
    public String removeTicket(int purchaseTicketCount, boolean purchase){
        synchronized (this){
            String returnMessage = null;
            try {
                while (ticketQueue.isEmpty()) {
                    if (checkTicketAvailability() && ticketQueue.isEmpty()) {
                        return "Tickets Sold Out.";
                    }else {
                        logger.info(userModule.getUsername() + " try to purchase tickets : Ticket pool is empty. Wait until vendor release tickets.");
                        returnMessage = "Tickets Are Currently Unavailable. Please Wait Until More Tickets Become Available.";
                        wait();
                    }
                }

                if(purchaseTicketCount > configurationModule.getCustomerRetrievalRate() && !purchase){
                    int allowedPurchaseCount = Math.min(ticketQueue.size(), configurationModule.getCustomerRetrievalRate());
                    return "You Can Purchase Up To " + allowedPurchaseCount + " Tickets At This Time. Would You Like To Proceed With Your Purchase?";
                }

                for (int i = 0; i < purchaseTicketCount; i++) {
                    if (!ticketQueue.isEmpty()) {
                        ticketQueue.remove();
                    }
                }
                logger.info(userModule.getUsername() + " purchased " +  purchaseTicketCount + " tickets. Remaining tickets in the pool: " + ticketQueue.size());
                returnMessage = purchaseTicketCount + " Tickets Purchased.";
                notifyAll();
            }catch (InterruptedException e){
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
}
