import { t } from "@angular/core/src/render3";

export class EventModule {
  eventId: number;
  eventName: string;
  eventLocation: string;
  eventType: string;
  eventDate: string;
  eventTime: string;
  eventNormalTicketPrice: number;
  eventVIPTicketPrice: number;
  eventImage: string;
  buttonName: string;
  systemStatus: string;
  configurationStatus: string;
  availableTickets: number;

  constructor() {
    this.eventId = 0;
    this.eventName = null;
    this.eventLocation = null;
    this.eventType = null;
    this.eventDate = null;
    this.eventTime = null;
    this.eventNormalTicketPrice = 0;
    this.eventVIPTicketPrice = 0;
    this.eventImage = null;
    this.buttonName = null;
    this.availableTickets = 0;
  }
}
