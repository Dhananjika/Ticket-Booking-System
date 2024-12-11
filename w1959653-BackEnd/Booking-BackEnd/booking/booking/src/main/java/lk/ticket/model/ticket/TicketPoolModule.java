package lk.ticket.model.ticket;

public class TicketPoolModule {
    private int releasedTicketCount;
    private int ticketQueueSize;

    public TicketPoolModule() {}

    public String toString(){
        return "TicketPoolModule [releasedTicketCount=" + releasedTicketCount + ", ticketQueueSize=" + ticketQueueSize + "]";
    }

    public int getReleasedTicketCount() {
        return releasedTicketCount;
    }

    public void setReleasedTicketCount(int releasedTicketCount) {
        this.releasedTicketCount = releasedTicketCount;
    }

    public int getTicketQueueSize() {
        return ticketQueueSize;
    }

    public void setTicketQueueSize(int ticketQueueSize) {
        this.ticketQueueSize = ticketQueueSize;
    }
}
