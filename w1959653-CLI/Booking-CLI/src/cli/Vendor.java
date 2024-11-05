package cli;

/**
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */

//Vendor class implemented by Runnable interface
public class Vendor implements Runnable {
    private final TicketPool ticketPool;

    public Vendor(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    /**
     *  This method is used to separately execute thread.
     *  This will continue until all the tickets are released.
     *  Here thread sleep time set to 200 milliseconds.
     *  This will make wait threads for 200 milliseconds to execute.
     *
     *  @Exception InterruptedException
     *  @out add tickets to ticket pool
     * */
    @Override
    public void run() {
        String methodDetails = "[Vendor] -- [run] : ";
        while (true) {
            try {
                ticketPool.addTicket();
                Thread.sleep(200);
                if (ticketPool.checkTicketAvailability()) {
                    Logger.info(methodDetails + "No more tickets left to release.");
                    break;
                }
            } catch (InterruptedException e) {
                Logger.error(methodDetails + " An error occurred while interrupting the vendor thread " + Thread.currentThread().getName() + " : " + e.getMessage());
                break;
            }
        }
    }
}
