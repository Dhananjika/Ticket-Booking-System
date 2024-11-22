export class ConfigurationModule {
  totalTickets: number;
  maxTicketCapacity: number;
  ticketReleaseRate: number;
  customerRetrievalRate: number;

  constructor() {
    this.totalTickets = 0;
    this.maxTicketCapacity = 0;
    this.ticketReleaseRate = 0;
    this.customerRetrievalRate = 0;
  }
}
