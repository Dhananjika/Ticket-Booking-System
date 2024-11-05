public class Vendor implements Runnable {
    private final TicketPool ticketPool;

    public Vendor(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        String methodDetails = "[Vendor] -- run : ";
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
