package cli;

/**
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */

//Customer class implemented by Runnable interface
public class Customer implements Runnable {
    private final TicketPool ticketPool;

    public Customer(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    /**
     *  This method is used to separately execute thread.
     *  This will continue until all the tickets are purchased.
     *  Here thread sleep time set to 400 milliseconds.
     *  This will make wait threads for 400 milliseconds to execute.
     *
     *  @Exception InterruptedException
     *  @out remove Tickets from Ticket Pool
     * */
    @Override
    public void run() {
        String methodDetails = "[Customer] -- [run] : ";
        while (true) {
            try {
                ticketPool.removeTicket();
                if (ticketPool.checkTicketAvailability()) {
                    Logger.info(methodDetails + "No more tickets available to purchase.");
                    break;
                }
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Logger.error(methodDetails + " An error occurred while interrupting the customer thread " + Thread.currentThread().getName() + " : " + e.getMessage());
                break;
            }
        }
    }
}
