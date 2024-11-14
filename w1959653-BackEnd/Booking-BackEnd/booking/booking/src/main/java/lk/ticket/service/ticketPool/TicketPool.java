package lk.ticket.service.ticketPool;

public interface TicketPool {
    String addTicket(boolean add);
    String removeTicket(int purchaseTicketCount, boolean purchase);
    boolean checkTicketAvailability();
}
