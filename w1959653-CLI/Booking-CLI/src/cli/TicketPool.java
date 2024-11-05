package cli;

import java.util.LinkedList;

public class TicketPool {
    private final LinkedList<Integer> ticketList = new LinkedList<>();
    private final Configuration configuration;
    private int releasedTicketCount;
    private final int poolSize;

    public TicketPool(Configuration configuration) {
        this.configuration = configuration;
        this.poolSize = configuration.getMaxTicketCapacity();
    }

    public synchronized void addTicket(){
        String methodDetails = "[TicketPool] -- addTicket : ";
        try{
            int ticketReleaseRate = configuration.getTicketReleaseRate();
            while (ticketList.size() == poolSize) {
                Logger.warn(methodDetails + "Ticket pool is full. Wait until customer purchase tickets");
                wait();
            }
            ticketReleaseRate = Math.min(poolSize - ticketList.size(), ticketReleaseRate);
            for (int i = 0; i < ticketReleaseRate; i++) {
                if (releasedTicketCount >= configuration.getTotalTickets()) {
                    notifyAll();
                    return;
                }
                ticketList.add(releasedTicketCount++);
            }
            Logger.info(methodDetails + ticketReleaseRate + " Tickets added. Total: " + ticketList.size());
            notifyAll();
        }catch (InterruptedException e){
            Logger.error(methodDetails + " An error occurred while adding tickets to pool : " + e.getMessage());
        }
    }


    public synchronized void removeTicket(){
        String methodDetails = "[TicketPool] -- removeTicket : ";
        try {
            int customerRetrievalRate = configuration.getCustomerRetrievalRate();
            while (ticketList.isEmpty()) {
                if (releasedTicketCount >= configuration.getTotalTickets()) {
                    Logger.warn(methodDetails + " No more tickets available for this event.");
                    notifyAll();
                }else {
                    Logger.warn(methodDetails + "Ticket pool is empty. Wait until vendor release tickets");
                    wait();
                }
            }

            customerRetrievalRate = Math.min(ticketList.size(), customerRetrievalRate);

            for (int i = 0; i < customerRetrievalRate; i++) {
                if (!ticketList.isEmpty()) {
                    ticketList.removeFirst();
                }
            }
            Logger.info(methodDetails + customerRetrievalRate + " Tickets purchased. Remaining: " + ticketList.size());
            notifyAll();
        }catch (InterruptedException e){
            Logger.error(methodDetails + " An error occurred while removing tickets to pool : " + e.getMessage());
        }
    }

    public boolean checkTicketAvailability() {
        return releasedTicketCount >= configuration.getTotalTickets() && ticketList.isEmpty();
    }

}
