package lk.ticket.service.ticketPool;

public interface TicketPool {
    String addTicket(boolean add, String vendorName);
    String removeTicket(int purchaseTicketCount, boolean purchase, String customerName);
    boolean checkTicketAvailability();
}
