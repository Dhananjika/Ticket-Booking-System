package cli;

public class Customer implements Runnable {
    private final TicketPool ticketPool;

    public Customer(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        String methodDetails = "[Customer] -- run : ";
        while (true) {
            try {
                ticketPool.removeTicket();
                if (ticketPool.checkTicketAvailability()) {
                    Logger.info(methodDetails + "No more tickets available to purchase.");
                    break;
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Logger.error(methodDetails + " An error occurred while interrupting the customer thread " + Thread.currentThread().getName() + " : " + e.getMessage());
                break;
            }
        }
    }
}
