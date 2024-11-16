package lk.ticket.model.configuration;

/**
 * This Controller module class. Here initiate all parameters
 *  <p>
 * Author - DISSANAYAKA MUDIYANSELAGE DHANANJIKA NIWARTHANI
 * UoW ID - W1959653
 * IIT ID - 20223058
 */
public class ConfigurationModule {
    private int totalTickets;
    private int maxTicketCapacity;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    /**
     *  This method is used to display the Configuration variable values
     * */
    @Override
    public String toString() {
        return "Configuration[Total Number of Tickets - " + totalTickets + ", Ticket Release Rate - " + ticketReleaseRate + ", Customer Retrieval Rate - " + customerRetrievalRate + ", Maximum Ticket Capacity - " + maxTicketCapacity + "]" ;
    }

    //Getters & Setters
    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }
}
