package cli;

/**
 * Customer class implemented by Runnable interface
 * <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */

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
                if (ticketPool.checkTicketAvailability() && ticketPool.isTicketsSold()) {
                    Logger.info(methodDetails + Thread.currentThread().getName() + " try to purchase tickets : No more tickets left to purchase.");
                    break;
                }
                ticketPool.removeTicket(Thread.currentThread().getName());
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                Logger.error(methodDetails + " An error occurred while interrupting the customer thread " + Thread.currentThread().getName() + " : " + e.getMessage());
                break;
            }
        }
    }
}
