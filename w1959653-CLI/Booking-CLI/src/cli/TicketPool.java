package cli;

import java.util.Vector;

/**
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */

public class TicketPool {
    private final Vector<Integer> ticketQueue = new Vector<>(); // Thread-Safe Data Structure (Vector) use for store released tickets
    private final Configuration configuration;
    private int releasedTicketCount; // Total number of tickets issued by the vendor
    private final int poolSize; // size of the pool
    private boolean ticketsSold = false;


    public TicketPool(Configuration configuration) {
        this.configuration = configuration;
        this.poolSize = configuration.getMaxTicketCapacity();
    }

    /**
     *  This method is used to add tickets to the pool.
     *  This method is synchronized.
     *  Only one vendor can add tickets at one time.
     *
     *  @in  ticketReleaseRate
     *  @Exception InterruptedException
     *  @out ticketQueue
     * */

    public synchronized void addTicket(String vendorName){
        String methodDetails = "[TicketPool] -- [addTicket] : ";
        try{
            int ticketReleaseRate = configuration.getTicketReleaseRate();
            while (checkPoolSize()) {
                System.out.println();
                Logger.info(methodDetails + vendorName + " try to add tickets.");
                Logger.warn(methodDetails + "Ticket pool is full. Wait until customer purchase tickets");
                wait();
            }
            if(checkTicketAvailability()){
                System.out.println();
                Logger.info(methodDetails + vendorName + " try to add tickets.");
                Logger.warn(methodDetails + "No more tickets left to release.");
                return;
            }
            ticketReleaseRate = Math.min(poolSize - ticketQueue.size(), ticketReleaseRate);
            for (int i = 0; i < ticketReleaseRate; i++) {
                if (releasedTicketCount >= configuration.getTotalTickets()) {
                    notifyAll();
                    return;
                }
                ticketQueue.add(releasedTicketCount++);
            }
            Logger.info(methodDetails + vendorName + " added " + ticketReleaseRate +  " tickets. Total: " + ticketQueue.size());
            notifyAll();
        }catch (InterruptedException e){
            Logger.error(methodDetails + " An error occurred while adding tickets to pool : " + e.getMessage());
        }
    }


    /**
     *  This method is used to remove tickets from the pool.
     *  This method is synchronized.
     *  Only one customer can purchase tickets at one time.
     *
     *  @in  ticketReleaseRate
     *  @Exception InterruptedException
     *  @out ticketQueue
     * */
    public synchronized void removeTicket(String customerName){
        String methodDetails = "[TicketPool] -- [removeTicket] : ";
        try {
            int customerRetrievalRate = configuration.getCustomerRetrievalRate();
            while (ticketQueue.isEmpty()) {
                if (checkTicketAvailability() && ticketQueue.isEmpty()) {
                    ticketsSold = true;
                    System.out.println();
                    Logger.info(methodDetails + customerName + " try to purchase tickets.");
                    Logger.warn(methodDetails + "No more tickets left to release.");
                    return;
                }else {
                    System.out.println();
                    Logger.info(methodDetails + customerName + " try to purchase tickets.");
                    Logger.warn(methodDetails + "Ticket pool is empty. Wait until vendor release tickets");
                    wait();
                }
            }

            customerRetrievalRate = Math.min(ticketQueue.size(), customerRetrievalRate);

            for (int i = 0; i < customerRetrievalRate; i++) {
                if (!ticketQueue.isEmpty()) {
                    ticketQueue.remove(0);
                }
            }
            Logger.info(methodDetails + customerName + " purchased " +  customerRetrievalRate + " tickets. Remaining: " + ticketQueue.size());
            notifyAll();
        }catch (InterruptedException e){
            Logger.error(methodDetails + " An error occurred while removing tickets to pool : " + e.getMessage());
        }
    }

    /**
     *  This method is used to check the tickets are available or not.
     *
     *  @in  released Ticket Count, total ticket count, ticketQueue
     *  @out ticket available or not
     * */
    public synchronized boolean checkTicketAvailability() {
        return releasedTicketCount == configuration.getTotalTickets();
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

    public synchronized boolean isTicketsSold() {
        return ticketsSold;
    }

}
