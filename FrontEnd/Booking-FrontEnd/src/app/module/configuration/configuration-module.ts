export class ConfigurationModule {
  totalTickets: number;
  maxTicketCapacity: number;
  ticketReleaseRate: number;
  customerRetrievalRate: number;
  eventId: number;

  constructor() {
    this.totalTickets = 0;
    this.maxTicketCapacity = 0;
    this.ticketReleaseRate = 0;
    this.customerRetrievalRate = 0;
    this.eventId = 0;
  }

  dispaly(): void {
    console.log(
      "Configuration[Total Number of Tickets - " +
        this.totalTickets +
        ", Ticket Release Rate - " +
        this.ticketReleaseRate +
        ", Customer Retrieval Rate - " +
        this.customerRetrievalRate +
        ", Maximum Ticket Capacity - " +
        this.maxTicketCapacity +
        ", Event ID - " +
        this.eventId +
        "]",
    );
  }
}
